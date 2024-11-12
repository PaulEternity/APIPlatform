package com.paul.paulapigateway;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.paul.paulapiclientsdk.utils.SignUtils;
import com.paul.paulapicommon.model.dto.RequestParamsField;
import com.paul.paulapicommon.model.entity.InterfaceInfo;
import com.paul.paulapicommon.model.entity.User;
import com.paul.paulapicommon.model.enums.InterfaceInfoStatusEnum;
import com.paul.paulapicommon.model.vo.UserVO;
import com.paul.paulapicommon.sercive.InnerInterfaceInfoService;
import com.paul.paulapicommon.sercive.InnerUserInterfaceInfoService;
import com.paul.paulapicommon.sercive.InnerUserService;
import com.paul.paulapigateway.common.ErrorCode;
import com.paul.paulapigateway.exception.BusinessException;
import com.paul.paulapigateway.utils.RedissonLockUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.method.MethodDescription;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static com.paul.paulapiclientsdk.utils.SignUtils.getSign;

@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1");
    private static final long FIVE_MINUTES = 5L * 60;
    private static final String INTERFACE_HOST = "http://localhost:8123";


    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerInterfaceInfoService innerInterfaceInfoService;

    @DubboReference
    private InnerUserInterfaceInfoService innerUserInterfaceInfoService;

    @Resource
    private RedissonLockUtil redissonLockUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = Objects.requireNonNull(request.getMethod()).toString();
        String sourceAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());
        // 2. 访问控制 - 黑白名单
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            response.setStatusCode(HttpStatus.FORBIDDEN);
            return response.setComplete();
        }
        return verifyParameters(exchange, chain);
    }

    private Mono<Void> verifyParameters(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String path = INTERFACE_HOST + request.getPath().value();
        // 3. 用户鉴权（判断 ak、sk 是否合法）
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        String nonce = headers.getFirst("nonce");
        String timestamp = headers.getFirst("timestamp");
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        if (StringUtils.isAnyBlank(body, sign, accessKey, timestamp)) {
            throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请求参数不完整，无法访问");
        }
        //防重发XHR
        long currentTime = System.currentTimeMillis() / 1000;
        // 时间和当前时间不能超过 5 分钟
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "会话已过期");
        }

        try {
            User invokeUser = innerUserService.getInvokeUser(accessKey);
            if (invokeUser == null) {
                throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "请先登录");
            }
            if (!invokeUser.getAccessKey().equals(accessKey)) {
                throw new BusinessException(ErrorCode.FORBIDDEN_ERROR, "请先获取密钥");
            }
            if (!getSign(body, invokeUser.getSecretKey()).equals(sign)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "无权限访问");
            }

            String method = Objects.requireNonNull(request.getMethod()).toString();
            String uri = request.getURI().toString().trim();
            if (StringUtils.isBlank(uri)) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR);
            }
            InterfaceInfo interfaceInfo = innerInterfaceInfoService.getInterfaceInfo(method, uri);
            if (interfaceInfo == null) {
                throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "接口不存在");
            }
            if (interfaceInfo.getStatus() == InterfaceInfoStatusEnum.OFFLINE.getValue()) {
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "接口已下线");
            }
            MultiValueMap<String, String> queryParams = request.getQueryParams();
            String requestParam = interfaceInfo.getRequestHeader();
            List<RequestParamsField> list = new Gson().fromJson(requestParam,
                    new TypeToken<HashMap<String, Object>>() {
                    }.getType());
            if("POST".equals(method)) {
                Object cacheBody = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY)
            }

        } catch (Exception e) {
            log.error("getInvokeUser error", e);
        }


    }

    public Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, long interfaceInfoId, long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂
            DataBufferFactory bufferFactory = originalResponse.bufferFactory();
            // 拿到响应码
            HttpStatus statusCode = originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值里写数据
                            // 拼接字符串
                            return super.writeWith(
                                    fluxBody.map(dataBuffer -> {
                                        // 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            innerUserInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        // 构建日志
                                        StringBuilder sb2 = new StringBuilder(200);
                                        List<Object> rspArgs = new ArrayList<>();
                                        rspArgs.add(originalResponse.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return bufferFactory.wrap(content);
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }


    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
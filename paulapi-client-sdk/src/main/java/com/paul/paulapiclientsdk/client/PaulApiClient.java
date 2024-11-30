package com.paul.paulapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.paul.paulapiclientsdk.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

import static com.paul.paulapiclientsdk.utils.SignUtils.getSign;


/**
 * 调用第三方接口的客户端
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaulApiClient {

    private String accessKey;
    private String secretKey;


}

//
//public PaulApiClient(String accessKey, String secretKey) {
//    this.accessKey = accessKey;
//    this.secretKey = secretKey;
//}
//
//public String getNameByGet(String name) {
//    HashMap<String, Object> paramMap = new HashMap<>();
//    paramMap.put("name", name);
//    String result = HttpUtil.get(GATEWAY_HOST + "/api/name/", paramMap);
//    System.out.println(result);
//    return result;
//}
//
//public String getNameByPost(@RequestParam String name) {
//    HashMap<String, Object> paramMap = new HashMap<>();
//    paramMap.put("name", name);
//    String result = HttpUtil.post(GATEWAY_HOST + "/api/name/", paramMap);
//    System.out.println(result);
//    return result;
//
//}
//
//public Map<String, String> getHeaderMap(String body) {
//    Map<String, String> hashMap = new HashMap<>();
//    hashMap.put("accessKey", accessKey);
//    hashMap.put("nonce", RandomUtil.randomString(4));
//    hashMap.put("body", body);
//    hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
//    hashMap.put("sign", getSign(body, secretKey));
//    return hashMap;
//}
//
//
//public String getUserNameByPost(User user) {
//    String json = JSONUtil.toJsonStr(user);
//    HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name/user")
//            .addHeaders(getHeaderMap(json))
//            .body(json)
//            .execute();
//    System.out.println(httpResponse.getStatus());
//    String result = httpResponse.body();
//    System.out.println(result);
//    return result;
//
//}
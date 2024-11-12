package com.paul.paulapigateway.utils;

import com.paul.paulapigateway.common.ErrorCode;
import com.paul.paulapigateway.exception.BusinessException;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
public class RedissonLockUtil {

    @Resource
    private RedissonClient redissonClient;

    public <T> T redissonDistributedLocks(String lockName, Supplier<T> supplier, ErrorCode errorCode, String errorMessage) {
        RLock lock = redissonClient.getLock(lockName);
        try {
            if (lock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                return supplier.get();
            }
            throw new BusinessException(errorCode.getCode(), errorMessage);
        } catch (InterruptedException e) {

            throw new BusinessException(ErrorCode.PARAMS_ERROR, errorMessage);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                log.error("unlock" + Thread.currentThread().getId());
                lock.unlock();
            }
        }
    }

    public <T> T redissonDistributedLocks(long waitTime, long leaseTime, TimeUnit unit, String lockName, Supplier<T> supplier, ErrorCode errorCode, String errorMessage) {
        RLock lock = redissonClient.getLock(lockName);
        try {
            if (lock.tryLock(waitTime, leaseTime, unit)) {
                return supplier.get();
            }
            throw new BusinessException(errorCode.getCode(), errorMessage);
        } catch (InterruptedException e) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
                log.info("unlock" + Thread.currentThread().getName());
            }
        }
    }

    public <T> T redissonDistributedLocks(long time, TimeUnit unit, String lockName, Supplier<T> supplier, ErrorCode errorCode, String errorMessage) {
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (rLock.tryLock(time, unit)) {
                return supplier.get();
            }
            throw new BusinessException(errorCode.getCode(), errorMessage);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                log.info("unLock: " + Thread.currentThread().getId());
                rLock.unlock();
            }
        }
    }

    public <T> T redissonDistributedLocks(String lockName, Supplier<T> supplier, ErrorCode errorCode) {
        return redissonDistributedLocks(lockName, supplier, errorCode, errorCode.getMessage());
    }

    public <T> T redissonDistributedLocks(String lockName, Supplier<T> supplier, String errorMessage) {
        return redissonDistributedLocks(lockName, supplier, ErrorCode.OPERATION_ERROR, errorMessage);
    }

    public <T> T redissonDistributedLocks(String lockName, Supplier<T> supplier) {
        return redissonDistributedLocks(lockName, supplier, ErrorCode.OPERATION_ERROR);
    }

    public void redissonDistributedLocks(String lockName, Runnable runnable, ErrorCode errorCode, String errorMessage) {
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (rLock.tryLock(0, -1, TimeUnit.MILLISECONDS)) {
                runnable.run();
            } else {
                throw new BusinessException(errorCode.getCode(), errorMessage);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                log.info("lockName:{},unLockId:{} ", lockName, Thread.currentThread().getId());
                rLock.unlock();
            }
        }
    }

    public void redissonDistributedLocks(String lockName, Runnable runnable, ErrorCode errorCode) {
        redissonDistributedLocks(lockName, runnable, errorCode, errorCode.getMessage());
    }

    public void redissonDistributedLocks(String lockName, Runnable runnable, String errorMessage) {
        redissonDistributedLocks(lockName, runnable, ErrorCode.OPERATION_ERROR, errorMessage);
    }

    public void redissonDistributedLocks(String lockName, Runnable runnable) {
        redissonDistributedLocks(lockName, runnable, ErrorCode.OPERATION_ERROR, ErrorCode.OPERATION_ERROR.getMessage());
    }

    public void redissonDistributedLocks(long waitTime, long leaseTime, TimeUnit unit, String lockName, Runnable runnable, ErrorCode errorCode, String errorMessage) {
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (rLock.tryLock(waitTime, leaseTime, unit)) {
                runnable.run();
            } else {
                throw new BusinessException(errorCode.getCode(), errorMessage);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                log.info("unLock: " + Thread.currentThread().getId());
                rLock.unlock();
            }
        }
    }

    public void redissonDistributedLocks(long time, TimeUnit unit, String lockName, Runnable runnable, ErrorCode errorCode, String errorMessage) {
        RLock rLock = redissonClient.getLock(lockName);
        try {
            if (rLock.tryLock(time, unit)) {
                runnable.run();
            } else {
                throw new BusinessException(errorCode.getCode(), errorMessage);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, e.getMessage());
        } finally {
            if (rLock.isHeldByCurrentThread()) {
                log.info("unLock: " + Thread.currentThread().getId());
                rLock.unlock();
            }
        }
    }


}

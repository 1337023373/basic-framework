package com.hengheng.common.cache;

/**
 * @author lyw
 * @version 1.0
 */
public class RedisKeys {
    /**
     * 验证码Key
     */
    public static String getCaptchaKey(String key) {
        return String.format("hengheng:sys:captcha:%s", key);
    }

    /**
     * accessToken Key
     */
    public static String getAccessTokenKey(String accessToken) {
        return String.format("hengheng:sys:access:%s", accessToken);
    }

    public static String getAccessTokenKey(Long id) {
        return String.format("hengheng:sys:access:userId:%s", id);
    }


    /**
     * 默认文件存储配置 key
     *
     * @return
     */
    public static String getDefaultFileStorageKey() {
        return "hengheng:sys:file:storage:default";
    }

    /**
     * 获取文件存储信息
     *
     * @param id
     * @return
     */
    public static String getFileStorageById(Long id) {
        return String.format("hengheng:sys:file:storage:byId:%s", id);
    }

    public static String getHomeStatisticKey() {
        return "hengheng:sys:home:statistic";
    }
}

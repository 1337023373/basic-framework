package com.hengheng.sys.service.impl;

import com.hengheng.common.cache.RedisCache;
import com.hengheng.common.cache.RedisKeys;
import com.hengheng.common.exception.ServerException;
import com.hengheng.sys.pojo.vo.SysCaptchaVO;
import com.hengheng.sys.service.SysCaptchaService;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author lkj
 * @Date 2025/6/18 17:10
 * @Version 1.0
 */
@Service
@Slf4j
public class SysCaptchaServiceImpl implements SysCaptchaService {
    @Resource
    private RedisCache redisCache;

    @Override
    public SysCaptchaVO generate() {
        //  生成验证码key
        String key = UUID.randomUUID().toString();

        //  生成验证码
        SpecCaptcha captcha = new SpecCaptcha(150, 40);
        captcha.setLen(5);
        captcha.setCharType(Captcha.TYPE_DEFAULT);
        String image = captcha.toBase64();

        //  保存到缓存
        String redisKey = RedisKeys.getCaptchaKey(key);
        redisCache.set(redisKey, captcha.text(), 300);

        //  封装返回数据
        SysCaptchaVO sysCaptchaVO = new SysCaptchaVO();
        sysCaptchaVO.setKey(key);
        sysCaptchaVO.setImage(image);
        return sysCaptchaVO;
    }

    @Override
    public Boolean validate(String key, String captcha) {
        //判断是否为空
        if (StringUtils.isBlank(key) || StringUtils.isBlank(captcha)) {
            return false;
        }
        //通过key，从redis拿到数据，并比较
        String redisKey = getRedisKey(key);
        //如果redis取值为空也返回
        if (StringUtils.isBlank(redisKey)) {
            log.error("登陆时，redis验证码为空");
            return false;
        }
        //匹配大小写比较
        return captcha.equalsIgnoreCase(redisKey);
    }

    private String getRedisKey(String key) {
        key = RedisKeys.getCaptchaKey(key);
        String captcha = redisCache.get(key).toString();
        //获取到key之后，就删除redis中的key，防止code反复使用
        if (captcha != null) {
            redisCache.delete(key);
        }
        return captcha;
    }
}

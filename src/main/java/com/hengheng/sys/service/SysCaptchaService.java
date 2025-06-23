package com.hengheng.sys.service;

import com.hengheng.sys.pojo.vo.SysCaptchaVO;

/**
 * @Author lkj
 * @Date 2025/6/18 17:05
 * @Version 1.0
 */
public interface SysCaptchaService {
    /**
     * @description 生成验证码
     * @param
     * @author  lkj
     * @date  2025/6/19
     * @return
     */
    SysCaptchaVO generate();

    /**
     * @description 校验验证码
     * @param
     * @author  lkj
     * @date  2025/6/19
     * @return
     */
    Boolean validate(String key, String captcha);
}

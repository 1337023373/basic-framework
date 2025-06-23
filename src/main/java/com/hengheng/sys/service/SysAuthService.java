package com.hengheng.sys.service;

import com.hengheng.sys.pojo.vo.SysAccountLoginVO;
import com.hengheng.sys.pojo.vo.SysTokenVO;

/**
 * @Author lkj
 * @Date 2025/6/18 17:05
 * @Version 1.0
 */
public interface SysAuthService {
    SysTokenVO loginByAccount(SysAccountLoginVO login);

    void logout(String accessToken);

}

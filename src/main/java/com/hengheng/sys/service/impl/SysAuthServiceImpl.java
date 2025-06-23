package com.hengheng.sys.service.impl;

import com.hengheng.common.base.UserDetail;
import com.hengheng.common.cache.TokenStoreCache;
import com.hengheng.common.constant.ErrorCode;
import com.hengheng.common.exception.ServerException;
import com.hengheng.common.utils.DozerUtils;
import com.hengheng.common.utils.TokenUtils;
import com.hengheng.sys.pojo.vo.SysAccountLoginVO;
import com.hengheng.sys.pojo.vo.SysTokenVO;
import com.hengheng.sys.pojo.vo.SysUserVO;
import com.hengheng.sys.service.SysAuthService;
import com.hengheng.sys.service.SysCaptchaService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lkj
 * @Date 2025/6/18 17:17
 * @Version 1.0
 */
@Service
public class SysAuthServiceImpl implements SysAuthService {
    @Resource
    private SysCaptchaService sysCaptchaService;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private TokenStoreCache tokenStoreCache;

    @Override
    public SysTokenVO loginByAccount(SysAccountLoginVO login) {
        //验证码校验
        //留个口子，手动添加是否需要验证码校验
        if (login.isCheckCaptcha()) {
            sysCaptchaService.validate(login.getKey(), login.getCaptcha());
        }
        //鉴权
        Authentication authentication;
        try {
            //用户认证
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
        } catch (AuthenticationException e) {
            throw new ServerException(ErrorCode.ACCOUNT_PASSWORD_ERROR);
        }
        // 验证通过添加用户信息
        UserDetail user = (UserDetail) authentication.getPrincipal();
        //生成token
        String accessToken = TokenUtils.generator();
        //保存用户信息到redis
        tokenStoreCache.saveUser(accessToken, user);
        return new SysTokenVO(accessToken, DozerUtils.map(user, SysUserVO.class));
    }

    @Override
    public void logout(String accessToken) {
        //获取用户信息
        UserDetail user = tokenStoreCache.getUser(accessToken);
        //删除用户信息
        tokenStoreCache.deleteUser(accessToken, user.getId());
    }

}

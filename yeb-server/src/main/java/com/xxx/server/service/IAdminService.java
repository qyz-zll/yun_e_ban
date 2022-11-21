package com.xxx.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.RespBean;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangLeiLei
 * @since 2022-11-17
 */
public interface IAdminService extends IService<Admin> {
/**
 * 登录之后返回token
 */
    RespBean login(String username, String password, HttpServletRequest request);

}

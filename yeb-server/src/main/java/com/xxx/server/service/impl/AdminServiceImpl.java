package com.xxx.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.server.config.security.JwtTokenUtil;
import com.xxx.server.mapper.AdminMapper;
import com.xxx.server.pojo.Admin;
import com.xxx.server.pojo.RespBean;
import com.xxx.server.service.IAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangLeiLei
 * @since 2022-11-17
 */
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements IAdminService {

    @Autowired
    private  AdminMapper adminMapper;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    /**
     * 登录之后返回token
     */
    @Override
    public RespBean login(String username, String password, HttpServletRequest request){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (null == userDetails || !passwordEncoder.matches(password,userDetails.getPassword()) ){
            return  RespBean.error(("用户名或者密码不正确"));
        }
        if (!userDetails.isEnabled()){
            return RespBean.error("账号被禁用,请联系管理员！");
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        String token = jwtTokenUtil.generateToken(userDetails);
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("token",token);
        tokenMap.put("tokenHead",tokenHead);

        return RespBean.success("登录成功",tokenMap);
    }




    @Override
    public Admin getAdminByUserName(String username) {
       return adminMapper.selectOne(new QueryWrapper<Admin>()
               .eq("username",username)
               .eq("enabled",true));
    }

}

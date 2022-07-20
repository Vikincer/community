package org.vikinc.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vikinc.community.dto.DTOAccessToken;
import org.vikinc.community.dto.DTOGithubUser;
import org.vikinc.community.dto.User;
import org.vikinc.community.exception.CustomizeErrorCode;
import org.vikinc.community.exception.CustomizeException;
import org.vikinc.community.mapper.UserMapper;
import org.vikinc.community.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 获取github OAuth 权限
 */

@Controller
public class AuthorizeController {

    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientID;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String redirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           HttpServletRequest request,
                           HttpServletResponse response){

        DTOAccessToken DTOAccessToken = new DTOAccessToken();
        DTOAccessToken.setClient_id(clientID);
        DTOAccessToken.setClient_secret(clientSecret);
        DTOAccessToken.setCode(code);
        DTOAccessToken.setRedirect_uri(redirectUri);
        String accessToken = githubProvider.getAccessToken(DTOAccessToken);
        DTOGithubUser DTOGithubUser = githubProvider.getGithubUser(accessToken);
//        System.out.println(githubUser.toString());
        if(DTOGithubUser != null){
            User user = userMapper.getByaccountId(DTOGithubUser.getId());
            if(user == null){
                //登录成功 创建token
                String token = UUID.randomUUID().toString();

                //创建User对象 写入数据库
                user.setAccountId(DTOGithubUser.getId().toString());
                user.setName(DTOGithubUser.getLogin());
                user.setToken(token);
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(DTOGithubUser.getAvatarUrl());
                userMapper.insert(user);
                //将token写入cookie 前端验证
                response.addCookie(new Cookie("token",token));
                return "redirect:/";
            }
            response.addCookie(new Cookie("token",user.getToken()));
            request.getSession().setAttribute("user", DTOGithubUser);
            return "redirect:/";
        }else {
            //登录失败
            throw new CustomizeException(CustomizeErrorCode.NO_LOGIN_GITHUB);
        }
    }
}

package org.vikinc.community.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vikinc.community.dto.User;
import org.vikinc.community.mapper.UserMapper;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HelloController {
    public final static Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/hello")
    public String hello(@RequestParam(name = "name") String name, Model model){
        model.addAttribute("name",name);
        return "index";
    }

    @RequestMapping("/test")
    public void test(){
        List<User> userList = userMapper.getUserList();
        logger.info(userList.toString());
    }
}

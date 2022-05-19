package com.yxjr.yxjrsqlitedemo.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.yxjr.yxjrsqlitedemo.entity.User;
import com.yxjr.yxjrsqlitedemo.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("")
public class LoginController{
    //跳到首页
    @RequestMapping({ "/index"})
    public String toIndex(Model model){
        User user = ShiroUtils.getSysUser();
        model.addAttribute("user",user);
        return "index";
    }
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "login";
    }
    //登录认证
    @PostMapping("/login")
    @ResponseBody
    public R login(String userName, String password){
        Subject subject = SecurityUtils.getSubject();
        //令牌
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            //登录认证
            subject.login(token);
            return R.ok("登录成功");
        }catch (UnknownAccountException e){ //返回null就会进入这里
            return R.failed("用户名不存在");
        }catch (IncorrectCredentialsException e){ //密码错误就会进入这里
            return R.failed("密码错误");
        }catch (Exception e){
            return R.failed("账户密码验证出错");
        }

    }

    //注销
    @RequestMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }
    @RequestMapping("/device")
    public String device(){
        return "Device/device";
    }

    @RequestMapping("/log")
    public String log(){
        return "Log/log";
    }
    @RequestMapping("/toUser")
    public String user(){
        return "User/user";
    }
    @RequestMapping("/uPackage")
    public String uPackage(){
        return "UploadPackage/uploadPackage";
    }
    @RequestMapping("/task")
    public String task(){
        return "Task/task";
    }
    @RequestMapping("/main")
    public String main(){
        return "main";
    }


}

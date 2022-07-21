package com.yxjr.yxjrDevOps.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.yxjr.yxjrDevOps.common.MD5Utils;
import com.yxjr.yxjrDevOps.common.VerifyUtil;
import com.yxjr.yxjrDevOps.entity.User;
import com.yxjr.yxjrDevOps.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
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

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;


@Controller
@RequestMapping("")
public class LoginController {

    @RequestMapping()
    public String Index(Model model) {
        User user = ShiroUtils.getSysUser();
        model.addAttribute("user", user);
        return "index";
    }

    //跳到首页
    @RequestMapping("index")
    public String toIndex(Model model) {
        User user = ShiroUtils.getSysUser();
        model.addAttribute("user", user);
        return "index";
    }

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    //登录认证
    @PostMapping("/login")
    @ResponseBody
    public R login(String userName, String password, String verify, HttpServletRequest request) {
        try {
            HttpSession session=request.getSession();
            if (StringUtils.isBlank(verify)) {
                return R.failed("请输入验证码");
            }
            if(!session.getAttribute("imagecode").toString().equals(verify)){
                return R.failed("验证码输入错误，请重新输入。");
            }
            Subject subject = SecurityUtils.getSubject();
            //令牌
            password = MD5Utils.encrypt(userName, password);
            UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
            //登录认证
            subject.login(token);
            return R.ok("登录成功");
        } catch (UnknownAccountException e) { //返回null就会进入这里
            return R.failed("用户名不存在");
        } catch (IncorrectCredentialsException e) { //密码错误就会进入这里
            return R.failed("密码错误");
        } catch (Exception e) {
            return R.failed("账户密码验证出错");
        }

    }

    @RequestMapping("/getCode")
    public void getCode(HttpServletResponse response, HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入session
        session.setAttribute("imagecode", objs[0]);

        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }

    //注销
    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }

    @RequestMapping("/device")
    public String device() {
        return "Device/device";
    }

    @RequestMapping("/log")
    public String log() {
        return "Log/log";
    }

    @RequestMapping("/toUser")
    public String user() {
        return "User/user";
    }

    @RequestMapping("/uPackage")
    public String uPackage() {
        return "UploadPackage/uploadPackage";
    }

    @RequestMapping("/task")
    public String task() {
        return "Task/task";
    }

    @RequestMapping("/main")
    public String main() {
        return "main";
    }


}

package com.yxjr.yxjrDevOps.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.ApiController;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yxjr.yxjrDevOps.entity.User;
import com.yxjr.yxjrDevOps.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * (User)表控制层
 *
 * @author makejava
 * @since 2022-05-09 11:04:29
 */
@Controller
@RequestMapping("user")
public class UserController extends ApiController {

    private final String prefix="User/";
    /**
     * 服务对象
     */
    @Resource
    private UserService userService;


    /**
     * 添加
     * @return
     */
    @RequestMapping("/add")
    public String add(){
        return prefix+"add";
    }


    /**
     * 修改
     * @return
     */
    @RequestMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("user",userService.getById(id));
        return prefix+"edit";
    }

    /**
     * 分页查询所有数据
     *
     * @param page 分页对象
     * @param user 查询实体
     * @return 所有数据
     */
    @GetMapping
    @ResponseBody
    public R selectAll(Page<User> page, User user) {
        return success(this.userService.page(page, new QueryWrapper<>(user)));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    @ResponseBody
    public R selectOne(@PathVariable Serializable id) {
        return success(this.userService.getById(id));
    }

    /**
     * 新增数据
     *
     * @param user 实体对象
     * @return 新增结果
     */
    @PostMapping
    @ResponseBody
    public R insert(User user) {
        return success(this.userService.save(user));
    }

    /**
     * 修改数据
     *
     * @param user 实体对象
     * @return 修改结果
     */
    @PutMapping
    @ResponseBody
    public R update(User user) {
        if(user.getId()==1){
            return R.failed("admin用户不允许修改密码");
        }
        return success(this.userService.updateById(user));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除结果
     */
    @DeleteMapping
    @ResponseBody
    public R deleteById(int id) {
        if(id == 1){
            return R.failed("admin用户不允许删除");
        }
        return success(this.userService.removeById(id));
    }
}


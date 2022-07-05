package com.yxjr.yxjrDevOps.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yxjr.yxjrDevOps.dao.UserDao;
import com.yxjr.yxjrDevOps.entity.User;
import com.yxjr.yxjrDevOps.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2022-05-09 11:04:30
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}


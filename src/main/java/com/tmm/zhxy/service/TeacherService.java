package com.tmm.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tmm.zhxy.pojo.LoginForm;
import com.tmm.zhxy.pojo.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_teacher】的数据库操作Service
* @createDate 2022-06-09 16:15:26
*/
public interface TeacherService extends IService<Teacher> {

    Teacher login(LoginForm loginForm);

    Teacher getOneTeacherById(Integer id);

    int deleteTeacherByIds(List<Integer> ids);

    boolean insertOrUpdateTeacher(Teacher teacher);

    IPage<Teacher> getPageByTeacher(Integer pageNo, Integer pageSize, Teacher teacher);
}

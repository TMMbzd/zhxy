package com.tmm.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tmm.zhxy.pojo.LoginForm;
import com.tmm.zhxy.pojo.Student;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_student】的数据库操作Service
* @createDate 2022-06-09 16:15:26
*/
public interface StudentService extends IService<Student> {

    Student login(LoginForm loginForm);

    Student getOneStudentById(Integer id);

    int deleteStudentByIds(List<Integer> ids);

    boolean insertOrUpdateStudent(Student student);

    IPage<Student> getPageByStudent(Integer pageNo, Integer pageSize, Student student);
}

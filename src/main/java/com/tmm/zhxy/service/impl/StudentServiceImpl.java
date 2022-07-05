package com.tmm.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tmm.zhxy.pojo.Admin;
import com.tmm.zhxy.pojo.LoginForm;
import com.tmm.zhxy.pojo.Student;
import com.tmm.zhxy.service.StudentService;
import com.tmm.zhxy.mapper.StudentMapper;
import com.tmm.zhxy.util.MD5;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_student】的数据库操作Service实现
* @createDate 2022-06-09 16:15:26
*/
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
    implements StudentService{

    @Override
    public Student login(LoginForm loginForm) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Student getOneStudentById(Integer id) {
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int deleteStudentByIds(List<Integer> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    @Override
    public boolean insertOrUpdateStudent(Student student) {
        return saveOrUpdate(student);
    }

    @Override
    public IPage<Student> getPageByStudent(Integer pageNo, Integer pageSize, Student student) {
        IPage<Student> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Student> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(student.getClazzName()),"clazz_name", student.getClazzName())
                .like(StringUtils.isNotBlank(student.getName()),"name", student.getName());
        queryWrapper.orderByAsc("id");
        return baseMapper.selectPage(page, queryWrapper);
    }
}





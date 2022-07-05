package com.tmm.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tmm.zhxy.pojo.Admin;
import com.tmm.zhxy.pojo.LoginForm;
import com.tmm.zhxy.pojo.Teacher;
import com.tmm.zhxy.service.TeacherService;
import com.tmm.zhxy.mapper.TeacherMapper;
import com.tmm.zhxy.util.MD5;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_teacher】的数据库操作Service实现
* @createDate 2022-06-09 16:15:26
*/
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher>
    implements TeacherService{

    @Override
    public Teacher login(LoginForm loginForm) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Teacher getOneTeacherById(Integer id) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("id", id);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public int deleteTeacherByIds(List<Integer> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    @Override
    public boolean insertOrUpdateTeacher(Teacher teacher) {
        return saveOrUpdate(teacher);
    }

    @Override
    public IPage<Teacher> getPageByTeacher(Integer pageNo, Integer pageSize, Teacher teacher) {
        IPage<Teacher> page = new Page<>(pageNo, pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like(StringUtils.isNotBlank(teacher.getClazzName()), "clazz_name", teacher.getClazzName())
                .like(StringUtils.isNotBlank(teacher.getName()), "name", teacher.getName());

        return baseMapper.selectPage(page, queryWrapper);
    }
}





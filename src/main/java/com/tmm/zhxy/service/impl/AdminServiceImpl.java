package com.tmm.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tmm.zhxy.pojo.Admin;
import com.tmm.zhxy.pojo.LoginForm;
import com.tmm.zhxy.pojo.Student;
import com.tmm.zhxy.service.AdminService;
import com.tmm.zhxy.mapper.AdminMapper;
import com.tmm.zhxy.util.MD5;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_admin】的数据库操作Service实现
* @createDate 2022-06-09 16:12:55
*/
@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin>
    implements AdminService{


    @Override
    public Admin login(LoginForm loginForm) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", loginForm.getUsername());
        queryWrapper.eq("password", MD5.encrypt(loginForm.getPassword()));
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public Admin getOneAdminById(Integer id) {
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        return baseMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<Admin> getPageByAdminName(Integer pageNo, Integer pageSize, String adminName) {

        IPage<Admin> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(adminName), "name", adminName);
        queryWrapper.orderByAsc();
        return baseMapper.selectPage(page, queryWrapper);
    }

    @Override
    public boolean insertOrUpdateAdmin(Admin admin) {
        return saveOrUpdate(admin);
    }

    @Override
    public List<Admin> getAllAdmin() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc();
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteAdminByIds(List<Integer> ids) {
        return baseMapper.deleteBatchIds(ids);
    }
}





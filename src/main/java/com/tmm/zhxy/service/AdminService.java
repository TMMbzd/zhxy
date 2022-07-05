package com.tmm.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tmm.zhxy.pojo.Admin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tmm.zhxy.pojo.LoginForm;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_admin】的数据库操作Service
* @createDate 2022-06-09 16:12:55
*/
public interface AdminService extends IService<Admin> {

    Admin login(LoginForm loginForm);

    Admin getOneAdminById(Integer id);
    IPage<Admin>  getPageByAdminName(Integer pageNo, Integer pageSize, String adminName);

    boolean insertOrUpdateAdmin(Admin admin);

    List<Admin> getAllAdmin();

    int deleteAdminByIds(List<Integer> ids);
}

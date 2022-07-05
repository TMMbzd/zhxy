package com.tmm.zhxy.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tmm.zhxy.pojo.Admin;
import com.tmm.zhxy.service.AdminService;
import com.tmm.zhxy.util.MD5;
import com.tmm.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/adminController")
public class AdminController {

    @Autowired
    private AdminService adminService;
    @DeleteMapping ("/deleteAdmin")
    public Result deleteAdmin(@RequestBody List<Integer> ids) {
        adminService.deleteAdminByIds(ids);
        return Result.ok().message("删除成功");
    }

    @GetMapping("/getAllAdmin/{pageNo}/{pageSize}")
    public Result getAllAdmin(@PathVariable("pageNo") Integer pageNo,
                              @PathVariable("pageSize") Integer pageSize,
                              String adminName) {
        return Result.ok(adminService.getPageByAdminName(pageNo, pageSize, adminName));
    }

    @PostMapping("/saveOrUpdateAdmin")
    public Result saveOrUpdateByAdmin(@RequestBody Admin admin) {
        Integer id = admin.getId();
        if (id == null) {
            admin.setPassword(MD5.encrypt(admin.getPassword()));
        }
        adminService.insertOrUpdateAdmin(admin);
        if(id != null) return Result.ok().message("修改成功");
        return Result.ok().message("添加成功");
    }

}

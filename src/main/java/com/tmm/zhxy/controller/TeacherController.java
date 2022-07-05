package com.tmm.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tmm.zhxy.pojo.Teacher;
import com.tmm.zhxy.service.TeacherService;
import com.tmm.zhxy.util.MD5;
import com.tmm.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/teacherController")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/getTeachers/{pageNo}/{pageSize}")
    public Result getTeachers(@PathVariable Integer pageNo,
                              @PathVariable Integer pageSize,
                              Teacher teacher) {

        IPage<Teacher> page = teacherService.getPageByTeacher(pageNo, pageSize, teacher);
        return Result.ok(page);
    }

    @DeleteMapping("/deleteTeacher")
    public Result deleteTeacher(@RequestBody List<Integer> ids) {
        teacherService.deleteTeacherByIds(ids);
        return Result.ok().message("删除成功");
    }

    @PostMapping("/saveOrUpdateTeacher")
    public Result saveOrUpdateTeacher(@RequestBody Teacher teacher) {
        Integer id = teacher.getId();
        if(id == null) {
            teacher.setPassword(MD5.encrypt(teacher.getPassword()));
        }
        teacherService.insertOrUpdateTeacher(teacher);
        if(id == null) {
            return Result.ok().message("添加成功");
        }else {
            return Result.ok().message("修改成功");
        }
    }
}

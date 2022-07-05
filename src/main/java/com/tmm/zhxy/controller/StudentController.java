package com.tmm.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.tmm.zhxy.pojo.Student;
import com.tmm.zhxy.service.StudentService;
import com.tmm.zhxy.util.MD5;
import com.tmm.zhxy.util.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/studentController")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/getStudentByOpr/{pageNo}/{pageSize}")
    public Result getStudentByOpr(@PathVariable("pageNo") Integer pageNo,
                                  @PathVariable("pageSize") Integer pageSize,
                                  Student student) {
        IPage<Student> page = studentService.getPageByStudent(pageNo, pageSize, student);
        return Result.ok(page);
    }

    @DeleteMapping("/delStudentById")
    public Result deleteStudentById(@RequestBody List<Integer> ids) {
        studentService.deleteStudentByIds(ids);
        return Result.ok().message("删除成功");
    }

    @PostMapping("/addOrUpdateStudent")
    public Result addOrUpdateStudent(@RequestBody Student student) {
        Integer id = student.getId();
        if(id == null) {
            student.setPassword(MD5.encrypt(student.getPassword()));
        }
        studentService.insertOrUpdateStudent(student);
        if(id == null) {
            return Result.ok().message("添加成功");
        }else {
            return Result.ok().message("修改成功");
        }
    }
}

package com.tmm.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tmm.zhxy.pojo.Admin;
import com.tmm.zhxy.pojo.Grade;
import com.tmm.zhxy.service.GradeService;
import com.tmm.zhxy.util.Result;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/gradeController")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @GetMapping("/getGrades")
    public Result getGrades() {
        List<Grade> grades = gradeService.getAllGrade();
        return Result.ok(grades);
    }

    @DeleteMapping("/deleteGrade")
    public Result deleteGrade(@RequestBody List<Integer> ids) {

        gradeService.deleteGradeByIds(ids);
        return Result.ok().message("删除成功");
    }

    @PostMapping("/saveOrUpdateGrade")
    public Result saveOrUpdateGrade(@RequestBody Grade grade) {
        Integer id = grade.getId();
        gradeService.insertOrUpdateGrade(grade);
        if(id == null) {
            return Result.ok().message("添加成功");
        }else {
            return Result.ok().message("修改成功");
        }
    }

    @GetMapping("/getGrades/{pageNo}/{pageSize}")
    public Result getGradesPage(@PathVariable("pageNo") Integer pageNo,
                                @PathVariable("pageSize") Integer pageSize,
                                String gradeName) {
        IPage<Grade> page = gradeService.getPageByGradeName(pageNo, pageSize, gradeName);
        return Result.ok(page);
    }


}

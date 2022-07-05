package com.tmm.zhxy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tmm.zhxy.pojo.Clazz;
import com.tmm.zhxy.service.ClazzService;
import com.tmm.zhxy.util.MD5;
import com.tmm.zhxy.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sms/clazzController")
public class ClazzController {

    @Autowired
    private ClazzService clazzService;

    @GetMapping("/getClazzs")
    public Result getClazzs() {
        List<Clazz> clazzes = clazzService.getAllClazz();
        return Result.ok(clazzes);
    }

    @DeleteMapping("/deleteClazz")
    public Result deleteClazz(@RequestBody List<Integer> ids) {
        clazzService.deleteClazzByIds(ids);
        return Result.ok().message("删除成功");
    }

    @PostMapping("/saveOrUpdateClazz")
    public Result saveOrUpdateClazz(@RequestBody Clazz clazz) {
        Integer id = clazz.getId();
        clazzService.insertOrUpdateClazz(clazz);
        if(id == null) {
            return Result.ok().message("添加成功");
        }else {
            return Result.ok().message("修改成功");
        }
    }

    @GetMapping("/getClazzsByOpr/{pageNo}/{pageSize}")
    public Result getClazzsPage(@PathVariable Integer pageNo,
                                @PathVariable Integer pageSize,
                                Clazz clazz) {
        IPage<Clazz> page = clazzService.getPageByClazz(pageNo, pageSize, clazz);
        return Result.ok(page);
    }
}

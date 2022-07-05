package com.tmm.zhxy.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.tmm.zhxy.pojo.Grade;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tmm.zhxy.pojo.LoginForm;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_grade】的数据库操作Service
* @createDate 2022-06-09 16:15:26
*/
public interface GradeService extends IService<Grade> {

    List<Grade> getAllGrade();

    int deleteGradeByIds(List<Integer> ids);

    boolean insertOrUpdateGrade(Grade grade);

    IPage<Grade> getPageByGradeName(Integer pageNo, Integer pageSize, String gradeName);
}

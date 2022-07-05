package com.tmm.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tmm.zhxy.pojo.Grade;
import com.tmm.zhxy.pojo.LoginForm;
import com.tmm.zhxy.service.GradeService;
import com.tmm.zhxy.mapper.GradeMapper;
import com.tmm.zhxy.util.MD5;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_grade】的数据库操作Service实现
* @createDate 2022-06-09 16:15:26
*/
@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade>
    implements GradeService{

    @Override
    public List<Grade> getAllGrade() {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.orderByAsc("name");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteGradeByIds(List<Integer> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    @Override
    public boolean insertOrUpdateGrade(Grade grade) {
        return saveOrUpdate(grade);
    }

    @Override
    public IPage<Grade> getPageByGradeName(Integer pageNo, Integer pageSize, String gradeName) {

        IPage<Grade> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Grade> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(gradeName), "name", gradeName);
        return baseMapper.selectPage(page, queryWrapper);
    }
}





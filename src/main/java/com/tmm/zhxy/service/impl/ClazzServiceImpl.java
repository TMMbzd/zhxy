package com.tmm.zhxy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tmm.zhxy.pojo.Clazz;
import com.tmm.zhxy.pojo.LoginForm;
import com.tmm.zhxy.service.ClazzService;
import com.tmm.zhxy.mapper.ClazzMapper;
import com.tmm.zhxy.util.MD5;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author TMM
* @description 针对表【tb_clazz】的数据库操作Service实现
* @createDate 2022-06-09 16:15:26
*/
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz>
    implements ClazzService{

    @Override
    public List<Clazz> getAllClazz() {
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("name");
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public int deleteClazzByIds(List<Integer> ids) {
        return baseMapper.deleteBatchIds(ids);
    }

    @Override
    public boolean insertOrUpdateClazz(Clazz clazz) {
        return saveOrUpdate(clazz);
    }

    @Override
    public IPage<Clazz> getPageByClazz(Integer pageNo, Integer pageSize, Clazz clazz) {
        IPage<Clazz> page = new Page<>(pageNo, pageSize);
        QueryWrapper<Clazz> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(clazz.getGradeName()),"grade_name", clazz.getGradeName());
        queryWrapper.like(StringUtils.isNotBlank(clazz.getName()),"name", clazz.getName());
        return baseMapper.selectPage(page, queryWrapper);
    }
}





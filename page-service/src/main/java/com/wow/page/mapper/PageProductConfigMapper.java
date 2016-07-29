package com.wow.page.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.wow.page.model.PageProductConfig;
import com.wow.page.model.PageProductConfigExample;
import com.wow.page.vo.PageProductVo;

public interface PageProductConfigMapper {
    int countByExample(PageProductConfigExample example);

    int deleteByExample(PageProductConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PageProductConfig record);

    int insertSelective(PageProductConfig record);

    List<PageProductConfig> selectByExample(PageProductConfigExample example);

    PageProductConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") PageProductConfig record, @Param("example") PageProductConfigExample example);

    int updateByExample(@Param("record") PageProductConfig record, @Param("example") PageProductConfigExample example);

    int updateByPrimaryKeySelective(PageProductConfig record);

    int updateByPrimaryKey(PageProductConfig record);

    List<PageProductConfig> selectByPageType(@Param("pageType") int pageType);
    
    List<PageProductVo> selectFindPageProduct(@Param("pageType") int pageType);
}
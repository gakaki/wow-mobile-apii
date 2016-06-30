package com.wow.attribute.mapper;

import com.wow.attribute.model.Attribute;
import com.wow.attribute.model.CategoryAttribute;
import com.wow.attribute.model.CategoryAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryAttributeMapper extends BaseMapper<CategoryAttribute> {
    int countByExample(CategoryAttributeExample example);

    int deleteByExample(CategoryAttributeExample example);

    int deleteByPrimaryKey(Integer id);

    int deleteByCategoryId(Integer id);

    int insert(CategoryAttribute record);

    int insertBatch(List<CategoryAttribute> categoryAttributes);

    int insertSelective(CategoryAttribute record);

    List<CategoryAttribute> selectByExample(CategoryAttributeExample example);

    List<CategoryAttribute> selectByCategoryId(Integer id);

    CategoryAttribute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") CategoryAttribute record, @Param("example") CategoryAttributeExample example);

    int updateByExample(@Param("record") CategoryAttribute record, @Param("example") CategoryAttributeExample example);

    int updateByPrimaryKeySelective(CategoryAttribute record);

    int updateByPrimaryKey(CategoryAttribute record);
}
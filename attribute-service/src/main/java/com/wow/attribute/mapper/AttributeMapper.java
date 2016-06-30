package com.wow.attribute.mapper;

import com.wow.attribute.model.Attribute;
import com.wow.attribute.model.AttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AttributeMapper extends BaseMapper<Attribute> {
    int countByExample(AttributeExample example);

    int deleteByExample(AttributeExample example);

    int deleteByPrimaryKey(Integer id);

    int deleteBatchByPrimaryKey(List attributeIds);

    int insert(Attribute record);

    int insertBatch(List<Attribute> attributes);

    int insertSelective(Attribute record);

    List<Attribute> selectAll();

    List<Attribute> selectByExample(AttributeExample example);

    Attribute selectByPrimaryKey(Integer id);

    Attribute selectAttributeByName(String attributeName);

    int updateByExampleSelective(@Param("record") Attribute record, @Param("example") AttributeExample example);

    int updateByExample(@Param("record") Attribute record, @Param("example") AttributeExample example);

    int updateByPrimaryKeySelective(Attribute record);

    int updateByPrimaryKey(Attribute record);
}
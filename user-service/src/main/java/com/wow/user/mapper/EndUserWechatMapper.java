package com.wow.user.mapper;

import com.wow.user.model.EndUserWechat;
import com.wow.user.model.EndUserWechatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EndUserWechatMapper {
    int countByExample(EndUserWechatExample example);

    int deleteByExample(EndUserWechatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(EndUserWechat record);

    int insertSelective(EndUserWechat record);

    List<EndUserWechat> selectByExample(EndUserWechatExample example);

    EndUserWechat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") EndUserWechat record, @Param("example") EndUserWechatExample example);

    int updateByExample(@Param("record") EndUserWechat record, @Param("example") EndUserWechatExample example);

    int updateByPrimaryKeySelective(EndUserWechat record);

    int updateByPrimaryKey(EndUserWechat record);
}
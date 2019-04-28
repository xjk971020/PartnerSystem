package cn.dezhi.welfare.partner.dao;

import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import org.apache.ibatis.annotations.Param;

public interface PartnerDoMapper {
    int deleteByPrimaryKey(Integer partnerId);

    int insert(PartnerDo record);

    int insertSelective(PartnerDo record);

    PartnerDo selectByPrimaryKey(Integer partnerId);

    int updateByPrimaryKeySelective(PartnerDo record);

    int updateByPrimaryKey(PartnerDo record);

    PartnerDo selectPartnerByPartnerName(@Param("email")String email);
}
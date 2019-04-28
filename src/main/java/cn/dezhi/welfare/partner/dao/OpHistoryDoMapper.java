package cn.dezhi.welfare.partner.dao;

import cn.dezhi.welfare.partner.entity.dataObject.OpHistoryDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OpHistoryDoMapper {
    int deleteByPrimaryKey(Integer historyId);

    int insert(OpHistoryDo record);

    int insertSelective(OpHistoryDo record);

    OpHistoryDo selectByPrimaryKey(Integer historyId);

    int updateByPrimaryKeySelective(OpHistoryDo record);

    int updateByPrimaryKey(OpHistoryDo record);

    List<OpHistoryDo> selectOpHistoryByPartnerId(@Param("partnerId") int partnerId);
}
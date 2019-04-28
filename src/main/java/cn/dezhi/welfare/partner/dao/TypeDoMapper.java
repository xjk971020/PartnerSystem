package cn.dezhi.welfare.partner.dao;

import cn.dezhi.welfare.partner.entity.dataObject.TypeDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TypeDoMapper {
    int deleteByPrimaryKey(Integer typeId);

    int insert(TypeDo record);

    int insertSelective(TypeDo record);

    TypeDo selectByPrimaryKey(Integer typeId);

    int updateByPrimaryKeySelective(TypeDo record);

    int updateByPrimaryKey(TypeDo record);

    int selectTypeIdByTypeName(@Param("typeName")String typeName);

    String selectTypeNameByTypeId(@Param("typeId")int typeId);

    List<TypeDo> selectAllType();
}
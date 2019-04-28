package cn.dezhi.welfare.partner.dao;

import cn.dezhi.welfare.partner.entity.dataObject.BrandDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface BrandDoMapper {
    int deleteByPrimaryKey(Integer brandId);

    int insert(BrandDo record);

    int insertSelective(BrandDo record);

    BrandDo selectByPrimaryKey(Integer brandId);

    int updateByPrimaryKeySelective(BrandDo record);

    int updateByPrimaryKey(BrandDo record);

    int selectBrandIdByBradnName(@Param("bradnName")String brandName);

    String selectBrandNameByBrandId(@Param("brandId")int brandId);

    List<BrandDo> selectAllBrand();
}
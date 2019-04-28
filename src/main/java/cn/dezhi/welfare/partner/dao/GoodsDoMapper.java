package cn.dezhi.welfare.partner.dao;

import cn.dezhi.welfare.partner.entity.dataObject.GoodsDo;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GoodsDoMapper {
    int deleteByPrimaryKey(Integer goodsId);

    int insert(GoodsDo record);

    int insertSelective(GoodsDo record);

    GoodsDo selectByPrimaryKey(int goodsId);

    int updateByPrimaryKeySelective(GoodsDo record);

    int updateByPrimaryKeyWithBLOBs(GoodsDo record);

    int updateByPrimaryKey(GoodsDo record);

    Map getGoodsCount();

    List<GoodsDo> getAllGoods();

    List<GoodsDo> selectGoodsByType(@Param("typeName")String typeName);

    List<GoodsDo> selectGoodsByBrand(@Param("brandName")String brandName);

    List<GoodsDo> selectGoodsByCheckStatus(@Param("checkStatus")Integer checkStatus);

    List<GoodsDo> selectGoodsByShelfStatus(@Param("shelfStatus")Integer shelfStatus);

    List<GoodsDo> selectGoodsByBarcode(@Param("goodsBarcode")String goodsBarcode);

    int deleteByManyId(int[] goodIds);

    int updateShelfStatusForMany(HashMap<String,Object> data);
}
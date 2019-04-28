package cn.dezhi.welfare.partner.service;

import cn.dezhi.welfare.partner.entity.dataObject.GoodsDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.GoodsVo;
import cn.dezhi.welfare.partner.response.CommonReturnType;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/4/18 -  20:01
 **/
public interface IGoodsService {

    /**
     * 通过id获取商品信息
     * @param goodId
     * @return
     */
    GoodsDo getGoodById(String goodId);

    /**
     * 新增一个商品
     * @param goodsVo
     * @return
     */
    boolean insertGood(GoodsVo goodsVo,int partnerId) throws IOException;

    /**
     * 获取商品总数信息
     * @return
     */
    Map getGoodsCount();

    /**
     * 根据商品id删除商品
     * @param goodId
     * @param partnerId
     * @return
     */
    CommonReturnType deleteGoodById(int goodId,int partnerId);

    /**
     * 分页获取商品条数
     * @param start
     * @param size
     * @return
     */
    PageInfo<GoodsVo> getGoodsByPage(int start, int size);

    /**
     * 转化goodsVo为goodsDo
     * @param goodsVo
     * @param goodsDo
     */
    void convertGoodsVoToGoodsDo(GoodsVo goodsVo, GoodsDo goodsDo);

    /**
     * 转化goodsDo为goodsVo
     * @param goodsDo
     * @param goodsVo
     */
    void convertGoodsDoToGoodsVo(GoodsDo goodsDo, GoodsVo goodsVo);

    /**
     * 根据分类名获取商品
     * @param typeName
     * @param curPage
     * @param size
     * @return
     */
    CommonReturnType selectGoodsByType(String typeName,int curPage,int size);

    /**
     * 根据品牌名获取商品
     * @param brandName
     * @return
     */
    CommonReturnType selectGoodsByBrand(String  brandName,int curPage,int size);

    /**
     * 根据审核状态获取商品
     * @param checkStatusStr
     * @return
     */
    CommonReturnType selectGoodsByCheckStatus(String checkStatusStr,int curPage,int size);

    /**
     * 根据上下架状态获取商品
     * @param shelfStatusStr
     * @param curPage
     * @param size
     * @return
     */
    CommonReturnType selectGoodsByShelfStatus(String shelfStatusStr,int curPage,int size);

    /**
     * 根据条形码获取商品
     * @param goodsBarcode
     * @param curPage
     * @param size
     * @return
     */
    CommonReturnType selectGoodsByBarcode(String goodsBarcode,int curPage,int size);

    /**
     * 批量删除数据
     * @param goodIds
     * @param partnerId
     * @return
     */
    CommonReturnType deleteByManyId(int[] goodIds,int partnerId);

    /**
     * 批量修改上下架状态
     * @param goodsIds
     * @param shelfStatus
     * @param partnerId
     * @return
     */
    CommonReturnType updateShelfStatusForMany(int[] goodsIds,String shelfStatus,int partnerId);
}

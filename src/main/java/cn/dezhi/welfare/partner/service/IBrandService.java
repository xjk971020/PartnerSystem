package cn.dezhi.welfare.partner.service;

import cn.dezhi.welfare.partner.entity.dataObject.BrandDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xjk
 * @date 2019/4/20 -  20:56
 **/
public interface IBrandService {
    /**
     * 根据brandId获取brand名
     * @param brandId
     * @return
     */
    String selectBrandNameByBrandId(int brandId);

    /**
     * 根据brandName获取brandId
     * @param brandName
     * @return
     */
    int selectBrandIdByBradnName(String brandName);

    /**
     * 获取所有的品牌
     * @return
     */
    List<BrandDo> selectAllBrand();

}

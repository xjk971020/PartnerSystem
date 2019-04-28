package cn.dezhi.welfare.partner.entity.viewObjcet;

import cn.dezhi.welfare.partner.entity.dataObject.BrandDo;
import cn.dezhi.welfare.partner.entity.dataObject.TypeDo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author xjk
 * @date 2019/4/18 -  19:24
 * 商品领域模型
 **/
@Data
public class GoodsVo {
    private Integer goodsId;

    @NotNull(message = "商品名称不能为空")
    private String goodsName;

    @NotNull(message = "商品属性不能为空")
    private String goodsAttr;

    @NotNull(message = "商品条形码不能为空")
    private String goodsBarcode;

    @NotNull(message = "商品分类不能为空")
    private String typeId;

    @NotNull(message = "商品品牌不能为空")
    private String brandId;

    private String checkStatus;

    private String shelfStatus;

    private Integer addTime;

    @NotNull(message = "商品图片不能为空")
    private String goodsPic;

    private String goodsSku;

    private String supplierSku;

    private String shortName;

    private BigDecimal netWeight;

    private BigDecimal grossWeight;

    private String shortDescription;

    private String bigPic;

    private BigDecimal goodsPrice;

    private String goodsChannel;

    private BigDecimal score;

    private String goodsLink;

    @NotNull(message = "商品描述不能为空")
    private String goodsDescription;

    private BrandDo brandDo;

    private TypeDo typeDo;
}

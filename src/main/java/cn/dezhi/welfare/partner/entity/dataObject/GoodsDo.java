package cn.dezhi.welfare.partner.entity.dataObject;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

import java.math.BigDecimal;

@Data
public class GoodsDo {
    @Field("goods_id")
    private Integer goodsId;

    @Field("goods_name")
    private String goodsName;

    @Field("goods_attr")
    private String goodsAttr;

    @Field("goods_barcode")
    private String goodsBarcode;

    @Field("type_id")
    private Integer typeId;

    @Field("brand_id")
    private Integer brandId;

    @Field("check_status")
    private Integer checkStatus;

    @Field("shelf_status")
    private Integer shelfStatus;

    @Field("add_time")
    private Integer addTime;

    @Field("good_pic")
    private String goodsPic;

    @Field("goods_sku")
    private String goodsSku;

    @Field("supplier_sku")
    private String supplierSku;

    @Field("short_name")
    private String shortName;

    @Field("net_weight")
    private BigDecimal netWeight;

    @Field("gross_weight")
    private BigDecimal grossWeight;

    @Field("short_description")
    private String shortDescription;

    @Field("big_pic")
    private String bigPic;

    @Field("goods_price")
    private BigDecimal goodsPrice;

    @Field("good_channel")
    private String goodsChannel;

    @Field("score")
    private BigDecimal score;

    @Field("goods_link")
    private String goodsLink;

    @Field("goods_desc")
    private String goodsDescription;

    private BrandDo brandDo;

    @Field
    private TypeDo typeDo;
}

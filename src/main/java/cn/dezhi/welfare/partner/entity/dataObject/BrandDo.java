package cn.dezhi.welfare.partner.entity.dataObject;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

@Data
public class BrandDo {
    private Integer brandId;

    @Field("brand_name")
    private String brandName;

    private String brandPic;
}
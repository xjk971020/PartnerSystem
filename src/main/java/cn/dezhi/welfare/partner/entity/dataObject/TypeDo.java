package cn.dezhi.welfare.partner.entity.dataObject;

import lombok.Data;
import org.apache.solr.client.solrj.beans.Field;

@Data
public class TypeDo {
    private Integer typeId;

    @Field("type_name")
    private String typeName;

    private Integer parentId;

    private Boolean levelId;

    private Integer sort;

}
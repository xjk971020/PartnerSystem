package cn.dezhi.welfare.partner.service;

import cn.dezhi.welfare.partner.entity.dataObject.TypeDo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xjk
 * @date 2019/4/20 -  20:54
 **/
public interface ITypeService {
    /**
     * 根据typeId获取type名
     * @param typeId
     * @return
     */
    String selectTypeNameByTypeId(int typeId);

    /**
     * 根据typeName获取typeId
     * @param typeName
     * @return
     */
    int selectTypeIdByTypeName(String typeName);

    /**
     * 获取所有分类
     * @return
     */
    List<TypeDo> selectAllType();
}

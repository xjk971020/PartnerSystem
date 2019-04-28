package cn.dezhi.welfare.partner.service.impl;

import cn.dezhi.welfare.partner.dao.TypeDoMapper;
import cn.dezhi.welfare.partner.entity.dataObject.TypeDo;
import cn.dezhi.welfare.partner.service.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xjk
 * @date 2019/4/20 -  20:55
 **/
@Service
public class TypeServiceImpl implements ITypeService {

    @Autowired
    private TypeDoMapper typeDoMapper;

    @Override
    public String selectTypeNameByTypeId(int typeId) {
        return typeDoMapper.selectTypeNameByTypeId(typeId);
    }

    @Override
    public int selectTypeIdByTypeName(String typeName) {
        return typeDoMapper.selectTypeIdByTypeName(typeName);
    }

    @Override
    public List<TypeDo> selectAllType() {
        return typeDoMapper.selectAllType();
    }
}

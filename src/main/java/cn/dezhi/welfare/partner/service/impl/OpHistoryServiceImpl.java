package cn.dezhi.welfare.partner.service.impl;

import cn.dezhi.welfare.partner.dao.OpHistoryDoMapper;
import cn.dezhi.welfare.partner.entity.dataObject.OpHistoryDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.OpHistoryVo;
import cn.dezhi.welfare.partner.response.CommonReturnType;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import cn.dezhi.welfare.partner.service.IOpHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xjk
 * @date 2019/4/27 -  19:19
 **/
@Service
public class OpHistoryServiceImpl implements IOpHistoryService {

    @Autowired
    private OpHistoryDoMapper opHistoryDoMapper;

    @Override
    public boolean insertOpHistory(OpHistoryDo opHistoryDo) {
        if (opHistoryDoMapper.insertSelective(opHistoryDo) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public CommonReturnType getSixOpHistory(int partnerId) {
        List<OpHistoryDo> opHistoryDos = opHistoryDoMapper.selectOpHistoryByPartnerId(partnerId);
        if (opHistoryDos == null || opHistoryDos.isEmpty()) {
            throw new BussinessException(EmBusinessError.OPEEATION_HISTORY_ERROR,"暂时没有操作记录");
        }
        List<OpHistoryVo> opHistoryVos = new ArrayList<>();
        opHistoryDos.forEach(opHistoryDo -> {
            OpHistoryVo opHistoryVo = new OpHistoryVo();
            convertOpHistoryDoToOpHistoryVo(opHistoryDo,opHistoryVo);
            opHistoryVos.add(opHistoryVo);
        });
        return CommonReturnType.create(opHistoryVos);
    }

    public void convertOpHistoryDoToOpHistoryVo(OpHistoryDo opHistoryDo, OpHistoryVo opHistoryVo) {
        BeanUtils.copyProperties(opHistoryDo,opHistoryVo);
        if (opHistoryDo.getHistoryType() == 0) {
            opHistoryVo.setHistoryType("商品");
        } else {
            opHistoryVo.setHistoryType("其他");
        }
    }
}

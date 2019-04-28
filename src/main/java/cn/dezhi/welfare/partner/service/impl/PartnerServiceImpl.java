package cn.dezhi.welfare.partner.service.impl;

import cn.dezhi.welfare.partner.dao.PartnerDoMapper;
import cn.dezhi.welfare.partner.entity.dataObject.PartnerDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.PartnerVo;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import cn.dezhi.welfare.partner.service.IMailService;
import cn.dezhi.welfare.partner.service.IPartnerService;
import cn.dezhi.welfare.partner.util.shiro.PasswordUtil;
import cn.dezhi.welfare.partner.validator.ValidationResult;
import cn.dezhi.welfare.partner.validator.ValidatorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author xjk
 * @date 2019/4/14 -  15:44
 **/
@Service
public class PartnerServiceImpl implements IPartnerService {

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private PartnerDoMapper partnerDoMapper;

    @Override
    public int insertPartner(PartnerDo partnerDo) {
        partnerDo.setCreateTime((int) System.currentTimeMillis()/10);
        partnerDo.setStatus(0);
        ValidationResult result = validator.validate(partnerDo);
        if (result.isHasErrors()) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,result.getErrMsg());
        }
        PasswordUtil.encryptPartnerDo(partnerDo);
        partnerDoMapper.insertSelective(partnerDo);
        return partnerDo.getPartnerId();
    }

    @Override
    public PartnerDo selectPartnerByPartnerName(String email) {
        return partnerDoMapper.selectPartnerByPartnerName(email);
    }

    @Override
    public PartnerDo selectByPrimaryKey(int partnerId) {
        return partnerDoMapper.selectByPrimaryKey(partnerId);
    }

    @Override
    public PartnerDo updatePassword(PartnerVo partnerVo, String newPassword) {
        PartnerDo temp = new PartnerDo();
        temp.setPartnerId(partnerVo.getPartnerId());
        temp.setPartnerPwd(PasswordUtil.encryptPassword(newPassword));
        if (partnerDoMapper.updateByPrimaryKeySelective(temp) > 0) {
            return temp;
        }
        return null;
    }

    @Override
    public PartnerDo updatePartner(PartnerDo partnerDo) {
//        ValidationResult result = validator.validate(partnerDo);
//        if (result.isHasErrors()) {
//            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,result.getErrMsg());
//        }
        partnerDoMapper.updateByPrimaryKeySelective(partnerDo);
        return partnerDo;
    }

    @Override
    public PartnerDo updatePartnerWithTime(PartnerDo partnerDo) {
        partnerDo.setLastLoginTime((int) (System.currentTimeMillis() / 1000));
        partnerDoMapper.updateByPrimaryKeySelective(partnerDo);
        return partnerDo;
    }


}

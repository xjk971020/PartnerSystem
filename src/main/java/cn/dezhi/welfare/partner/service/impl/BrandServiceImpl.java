package cn.dezhi.welfare.partner.service.impl;

import cn.dezhi.welfare.partner.dao.BrandDoMapper;
import cn.dezhi.welfare.partner.entity.dataObject.BrandDo;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author xjk
 * @date 2019/4/20 -  20:57
 **/
@Service
public class BrandServiceImpl implements IBrandService {

    @Autowired
    private BrandDoMapper brandDoMapper;

    @Override
    public String selectBrandNameByBrandId(int brandId) {
        return brandDoMapper.selectBrandNameByBrandId(brandId);
    }

    @Override
    public int selectBrandIdByBradnName(String brandName) {
        return brandDoMapper.selectBrandIdByBradnName(brandName);
    }

    @Override
    public List<BrandDo> selectAllBrand() {
        List<BrandDo> brandDos = brandDoMapper.selectAllBrand();
        return brandDos;
    }
}

package cn.dezhi.welfare.partner.service.impl;

import cn.dezhi.welfare.partner.dao.BrandDoMapper;
import cn.dezhi.welfare.partner.dao.GoodsDoMapper;
import cn.dezhi.welfare.partner.dao.OpHistoryDoMapper;
import cn.dezhi.welfare.partner.dao.TypeDoMapper;
import cn.dezhi.welfare.partner.dto.RedisEm;
import cn.dezhi.welfare.partner.dto.RedisTableEm;
import cn.dezhi.welfare.partner.entity.dataObject.GoodsDo;
import cn.dezhi.welfare.partner.entity.dataObject.OpHistoryDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.GoodsVo;
import cn.dezhi.welfare.partner.response.CommonReturnType;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import cn.dezhi.welfare.partner.service.IGoodsService;
import cn.dezhi.welfare.partner.util.FileUtil;
import cn.dezhi.welfare.partner.util.Tools;
import cn.dezhi.welfare.partner.util.redis.RedisKeyUtil;
import cn.dezhi.welfare.partner.util.redis.RedisService;
import cn.dezhi.welfare.partner.validator.ValidationResult;
import cn.dezhi.welfare.partner.validator.ValidatorImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author xjk
 * @date 2019/4/18 -  20:02
 **/
@Service
public class GoodsServiceImpl  implements IGoodsService {

    @Autowired
    private GoodsDoMapper goodsDoMapper;

    @Autowired
    private BrandDoMapper brandDoMapper;

    @Autowired
    private TypeDoMapper typeDoMapper;

    @Autowired
    private ValidatorImpl validator;

    @Autowired
    private ValueOperations<String,Object> valueOperations;

    @Autowired
    private RedisService redisService;

    @Autowired
    private OpHistoryDoMapper opHistoryDoMapper;

    @Override
    public GoodsDo getGoodById(String goodId) {
        String goodRedisKey = RedisKeyUtil.getKey(RedisTableEm.TABLE_NAME, RedisTableEm.TABLE_column ,goodId);
        GoodsDo goodsDo = (GoodsDo) valueOperations.get(goodRedisKey);
        if (goodsDo == null) {
            if (StringUtils.isNotBlank(goodId)) {
                if (Tools.isNumber(goodId)) {
                    goodsDo = goodsDoMapper.selectByPrimaryKey(Integer.parseInt(goodId));
                    if (goodsDo != null) {
                        valueOperations.set(goodRedisKey,goodsDo);
                        redisService.expire(goodRedisKey,RedisEm.DDFAULT_EXPIRE, TimeUnit.HOURS);
                        return goodsDo;
                    } else {
                        throw new BussinessException(EmBusinessError.GOODS_ERROR,"查无商品");
                    }
                } else {
                    throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,"id不合法");
                }
            } else {
                throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,"查询的商品id不能为空");
            }
        }
        return goodsDo;
    }

    @Override
    public boolean insertGood(GoodsVo goodsVo,int partnerId) throws IOException {
        String goodPicPath = FileUtil.getUploadPic(goodsVo.getBigPic(),"goodsPic\\");
        goodsVo.setAddTime((int) (System.currentTimeMillis()/1000));
        goodsVo.setBigPic(goodPicPath);
        ValidationResult result = validator.validate(goodsVo);
        if (result.isHasErrors()) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,result.getErrMsg());
        }
        GoodsDo goodsDo = new GoodsDo();
        convertGoodsVoToGoodsDo(goodsVo,goodsDo);
        if (goodsDoMapper.insertSelective(goodsDo) >  0) {
            goodsDo.setSupplierSku(goodsDo.getGoodsId() + goodsDo.getGoodsBarcode());
            goodsDoMapper.updateByPrimaryKey(goodsDo);
            OpHistoryDo opHistoryDo = new OpHistoryDo();
            opHistoryDo.setHistoryName("提交商品信息");
            opHistoryDo.setHistoryType(0);
            opHistoryDo.setOpDetails("提交成功");
            opHistoryDo.setOperatorId(partnerId);
            opHistoryDo.setOpTime((int) (System.currentTimeMillis()/1000));
            opHistoryDo.setOperatorType("合作伙伴");
            opHistoryDoMapper.insertSelective(opHistoryDo);
            return true;
        }
        return false;
    }

    @Override
    public Map getGoodsCount() {
        return goodsDoMapper.getGoodsCount();
    }

    @Override
    public CommonReturnType deleteGoodById(int goodId,int partnerId) {
        if (goodsDoMapper.deleteByPrimaryKey(goodId) > 0) {
            OpHistoryDo opHistoryDo = new OpHistoryDo();
            opHistoryDo.setHistoryName("删除商品");
            opHistoryDo.setHistoryType(0);
            opHistoryDo.setOpDetails("批除成功,id为" + goodId);
            opHistoryDo.setOperatorId(partnerId);
            opHistoryDo.setOpTime((int) (System.currentTimeMillis()/1000));
            opHistoryDo.setOperatorType("合作伙伴");
            opHistoryDoMapper.insertSelective(opHistoryDo);
            return CommonReturnType.create("删除成功");
        }
        return CommonReturnType.create("删除失败");
    }

    @Override
    public PageInfo<GoodsVo> getGoodsByPage(int start, int size) {
        if (start <= 0) {
            start = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        PageHelper.startPage(start,size);
        List<GoodsDo> goodsDoList = goodsDoMapper.getAllGoods();
        if (goodsDoList.isEmpty()) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,"没有商品");
        }
        List<GoodsVo> goodsVoList = new ArrayList<>();
        goodsDoList.forEach(goodsDo->{
            GoodsVo goodsVo = new GoodsVo();
            convertGoodsDoToGoodsVo(goodsDo,goodsVo);
            goodsVoList.add(goodsVo);
        });
        PageInfo<GoodsVo> pageInfo = new PageInfo<>(goodsVoList);
        return pageInfo;
    }

    @Override
    public void convertGoodsVoToGoodsDo(GoodsVo goodsVo, GoodsDo goodsDo) {
        BeanUtils.copyProperties(goodsVo,goodsVo);
        switch (goodsVo.getCheckStatus()) {
            case "未审核":
                goodsDo.setCheckStatus(0);
                break;
            case "通过":
                goodsDo.setCheckStatus(1);
                break;
            case "不通过":
                goodsDo.setCheckStatus(2);
                break;
            default:
                goodsDo.setCheckStatus(0);
        }
        switch (goodsVo.getShelfStatus()) {
            case "下架":
                goodsDo.setShelfStatus(0);
                break;
            case "上架":
                goodsDo.setShelfStatus(1);
                break;
            default:
                goodsDo.setCheckStatus(0);
        }
        int typeId  = typeDoMapper.selectTypeIdByTypeName(goodsVo.getBrandId());
        int brandId= brandDoMapper.selectBrandIdByBradnName(goodsVo.getTypeId());
        goodsDo.setBrandId(brandId);
        goodsDo.setBrandId(typeId);
    }

    @Override
    public void convertGoodsDoToGoodsVo(GoodsDo goodsDo, GoodsVo goodsVo) {
        BeanUtils.copyProperties(goodsDo,goodsVo);
        switch (goodsDo.getCheckStatus()) {
            case 0:
                goodsVo.setCheckStatus("未审核");
                break;
            case 1:
                goodsVo.setCheckStatus("通过");
                break;
            case 2:
                goodsVo.setCheckStatus("不通过");
                break;
            default:
                goodsVo.setCheckStatus("未审核");
        }
        switch (goodsDo.getShelfStatus()) {
            case 0:
                goodsVo.setShelfStatus("下架");
                break;
            case 1:
                goodsVo.setShelfStatus("上架");
                break;
            default:
                goodsVo.setCheckStatus("下架");
        }
        String brandName = brandDoMapper.selectBrandNameByBrandId(goodsDo.getBrandId());
        String typeName = typeDoMapper.selectTypeNameByTypeId(goodsDo.getTypeId());
        goodsVo.setBrandId(brandName);
        goodsVo.setBrandId(typeName);
    }

    @Override
    public CommonReturnType selectGoodsByType(String typeName,int curPage,int size) {
        if (curPage <= 0) {
            curPage = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        PageHelper.startPage(curPage,size);
        List<GoodsDo> goodsDoList = goodsDoMapper.selectGoodsByType(typeName);
        if (goodsDoList.isEmpty() || goodsDoList == null) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,"暂时无此分类商品");
        }
        List<GoodsVo> goodsVoList = new ArrayList<>();
        goodsDoList.forEach(goodsDo -> {
            GoodsVo goodsVo = new GoodsVo();
            convertGoodsDoToGoodsVo(goodsDo,goodsVo);
            goodsVoList.add(goodsVo);
        });
        PageInfo<GoodsVo> pageInfo = new PageInfo<>(goodsVoList);
        return CommonReturnType.create(pageInfo);
    }

    @Override
    public CommonReturnType selectGoodsByBrand(String  brandName,int curPage,int size) {
        if (curPage <= 0) {
            curPage = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        PageHelper.startPage(curPage,size);
        List<GoodsDo> goodsDoList = goodsDoMapper.selectGoodsByBrand(brandName);
        if (goodsDoList.isEmpty() || goodsDoList == null) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,"暂时无此品牌商品");
        }
        List<GoodsVo> goodsVoList = new ArrayList<>();
        goodsDoList.forEach(goodsDo -> {
            GoodsVo goodsVo = new GoodsVo();
            convertGoodsDoToGoodsVo(goodsDo,goodsVo);
            goodsVoList.add(goodsVo);
        });
        PageInfo<GoodsVo> pageInfo = new PageInfo(goodsVoList);
        return CommonReturnType.create(pageInfo);
    }

    @Override
    public CommonReturnType selectGoodsByCheckStatus(String checkStatusStr,int curPage,int size) {
        int checkStatus;
        switch (checkStatusStr) {
            case "未审核":
                checkStatus = 0;
                break;
            case "通过":
                checkStatus = 1;
                break;
            case "不通过":
                checkStatus = 2;
                break;
            default:
                checkStatus = 0;
        }
        if (curPage <= 0) {
            curPage = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        PageHelper.startPage(curPage,size);
        List<GoodsDo> goodsDoList = goodsDoMapper.selectGoodsByCheckStatus(checkStatus);
        if (goodsDoList.isEmpty() || goodsDoList == null) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,"暂无商品");
        }
        List<GoodsVo> goodsVoList = new ArrayList<>();
        goodsDoList.forEach(goodsDo -> {
            GoodsVo goodsVo = new GoodsVo();
            convertGoodsDoToGoodsVo(goodsDo,goodsVo);
            goodsVoList.add(goodsVo);
        });
        PageInfo<GoodsVo> pageInfo = new PageInfo(goodsVoList);
        return CommonReturnType.create(pageInfo);
    }

    @Override
    public CommonReturnType selectGoodsByShelfStatus(String shelfStatusStr,int curPage,int size) {
        int shelfStatus;
        switch (shelfStatusStr) {
            case "下架":
                shelfStatus = 0;
                break;
            case "上架":
                shelfStatus = 1;
                break;
            default:
                shelfStatus = 0;
        }
        if (curPage <= 0) {
            curPage = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        PageHelper.startPage(curPage,size);
        List<GoodsDo> goodsDoList = goodsDoMapper.selectGoodsByShelfStatus(shelfStatus);
        if (goodsDoList.isEmpty() || goodsDoList == null) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,"暂无商品");
        }
        List<GoodsVo> goodsVoList = new ArrayList<>();
        goodsDoList.forEach(goodsDo -> {
            GoodsVo goodsVo = new GoodsVo();
            convertGoodsDoToGoodsVo(goodsDo,goodsVo);
            goodsVoList.add(goodsVo);
        });
        PageInfo<GoodsVo> pageInfo = new PageInfo(goodsVoList);
        return CommonReturnType.create(pageInfo);
    }

    @Override
    public CommonReturnType selectGoodsByBarcode(String goodsBarcode, int curPage, int size) {
        if (curPage <= 0) {
            curPage = 1;
        }
        if (size <= 0) {
            size = 10;
        }
        PageHelper.startPage(curPage,size);
        List<GoodsDo> goodsDoList = goodsDoMapper.selectGoodsByBarcode(goodsBarcode);
        if (goodsDoList.isEmpty() || goodsDoList == null) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,"暂时无此条形码商品");
        }
        List<GoodsVo> goodsVoList = new ArrayList<>();
        goodsDoList.forEach(goodsDo -> {
            GoodsVo goodsVo = new GoodsVo();
            convertGoodsDoToGoodsVo(goodsDo,goodsVo);
            goodsVoList.add(goodsVo);
        });
        PageInfo<GoodsVo> pageInfo = new PageInfo(goodsVoList);
        return CommonReturnType.create(pageInfo);
    }

    @Override
    public CommonReturnType deleteByManyId(int[] goodIds,int partnerId) {
        int count = goodsDoMapper.deleteByManyId(goodIds);
        if (count == 0) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,"没有对应id的商品");
        }
        OpHistoryDo opHistoryDo = new OpHistoryDo();
        opHistoryDo.setHistoryName("删除商品");
        opHistoryDo.setHistoryType(0);
        opHistoryDo.setOpDetails("批量删除成功,id为" + goodIds.toString());
        opHistoryDo.setOperatorId(partnerId);
        opHistoryDo.setOpTime((int) (System.currentTimeMillis()/1000));
        opHistoryDo.setOperatorType("合作伙伴");
        opHistoryDoMapper.insertSelective(opHistoryDo);
        return CommonReturnType.create(count);
    }

    @Override
    public CommonReturnType updateShelfStatusForMany(int[] goodsIds,String shelfStatus,int partnerId) {
        HashMap<String,Object> data = new HashMap<>();
        int shelfStatusInt;
        switch (shelfStatus) {
            case "下架":
                shelfStatusInt = 0;
                break;
            case "上架":
                shelfStatusInt = 1;
                break;
            default:
                shelfStatusInt = 0;
        }
        data.put("ids",goodsIds);
        data.put("shelfStatus",shelfStatusInt);
        int count = goodsDoMapper.updateShelfStatusForMany(data);
        if (count == 0) {
            throw new BussinessException(EmBusinessError.GOODS_ERROR,"没有对应id的商品");
        }
        OpHistoryDo opHistoryDo = new OpHistoryDo();
        opHistoryDo.setHistoryName(shelfStatus + "商品");
        opHistoryDo.setHistoryType(0);
        opHistoryDo.setOpDetails("批量" + shelfStatus + "成功,id为" + goodsIds.toString());
        opHistoryDo.setOperatorId(partnerId);
        opHistoryDo.setOpTime((int) (System.currentTimeMillis()/1000));
        opHistoryDo.setOperatorType("合作伙伴");
        opHistoryDoMapper.insertSelective(opHistoryDo);
        return CommonReturnType.create(count);
    }

}

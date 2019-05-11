package cn.dezhi.welfare.partner.controller.admin;

import cn.dezhi.welfare.partner.controller.AbstractController;
import cn.dezhi.welfare.partner.entity.dataObject.BrandDo;
import cn.dezhi.welfare.partner.entity.dataObject.GoodsDo;
import cn.dezhi.welfare.partner.entity.dataObject.TypeDo;
import cn.dezhi.welfare.partner.entity.viewObjcet.GoodsVo;
import cn.dezhi.welfare.partner.entity.viewObjcet.PartnerVo;
import cn.dezhi.welfare.partner.response.CommonReturnType;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.service.IBrandService;
import cn.dezhi.welfare.partner.service.IGoodsService;
import cn.dezhi.welfare.partner.service.ITypeService;
import cn.dezhi.welfare.partner.service.impl.SolrService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/4/18 -  19:25
 **/
@RestController
@RequestMapping("/goods")
public class GoodsController extends AbstractController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IBrandService brandService;

    @Autowired
    private ITypeService typeService;

    @Autowired
    private SolrService solrService;

    @ApiOperation(notes = "获取首页信息",value = "首页")
    @GetMapping("/index")
    public CommonReturnType index(HttpServletRequest request) {
        PartnerVo partnerVo = this.getPartner(request);
        Map goodsData = goodsService.getGoodsCount();
        Map<String,Object> data = new HashMap<>(4);
        data.put("partner",partnerVo);
        data.put("countInfo",goodsData);
        return CommonReturnType.create(data);
    }

    @ApiOperation(notes = "增加一条商品",value = "获取商品参数添加商品")
    @PostMapping("/create")
    @Transactional(rollbackFor = BussinessException.class)
    public CommonReturnType createGood(@RequestBody GoodsVo goodsVo,HttpServletRequest request) {
        try {
            goodsService.insertGood(goodsVo,this.getPartner(request).getPartnerId());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return CommonReturnType.create("添加成功");
    }

    @ApiOperation(value = "根据商品id查询商品信息")
    @GetMapping("/get/{goodsId}")
    public CommonReturnType getGood(@PathVariable String goodsId) {
        GoodsVo goodsVo = new GoodsVo();
        GoodsDo goodsDo = goodsService.getGoodById(goodsId);
        goodsService.convertGoodsDoToGoodsVo(goodsDo,goodsVo);
        return CommonReturnType.create(goodsVo);
    }

    @ApiOperation(value = "获取对应分类的商品")
    @PostMapping("/typeName")
    public CommonReturnType getGoodsByTypeName(@RequestParam("typeName")String typeName,
                                                    @RequestParam(value = "curPage",defaultValue = "1",required = false)int curPage,
                                                    @RequestParam(value = "size",defaultValue = "10",required = false)int size) {
        return goodsService.selectGoodsByType(typeName,curPage,size);
    }

    @ApiOperation(value = "获取对应品牌的商品")
    @PostMapping("/brandName")
    public CommonReturnType getGoodsByBrandName(@RequestParam("brandName")String brandName,
                                                     @RequestParam(value = "curPage",defaultValue = "1",required = false)int curPage,
                                                     @RequestParam(value = "size",defaultValue = "10",required = false)int size) {
        return goodsService.selectGoodsByBrand(brandName,curPage,size);
    }

    @ApiOperation(value = "获取对应审核状态的商品")
    @PostMapping("/chackStatus")
    public CommonReturnType getGoodsByCheckStatus(@RequestParam("checkStatus")String checkStatus,
                                                      @RequestParam(value = "curPage",defaultValue = "1",required = false)int curPage,
                                                      @RequestParam(value = "size",defaultValue = "10",required = false)int size) {
        return goodsService.selectGoodsByCheckStatus(checkStatus,curPage,size);
    }

    @ApiOperation(value = "获取对应条形码的商品")
    @PostMapping("/barcode")
    public CommonReturnType selectGoodsByBarcode(@RequestParam("barcode")String barcode,
                                                  @RequestParam(value = "curPage",defaultValue = "1",required = false)int curPage,
                                                  @RequestParam(value = "size",defaultValue = "10",required = false)int size) {
        return goodsService.selectGoodsByBarcode(barcode,curPage,size);
    }

    @ApiOperation(value = "获取对应上下架状态的商品")
    @PostMapping("/shelfStatus")
    public CommonReturnType getGoodsByShelfStatus(@RequestParam("shelfStatus")String shelfStatus,
                                                     @RequestParam(value = "curPage",defaultValue = "1",required = false)int curPage,
                                                     @RequestParam(value = "size",defaultValue = "10",required = false)int size) {
        return goodsService.selectGoodsByShelfStatus(shelfStatus,curPage,size);
    }

    @ApiOperation(value = "根据商品id删除商品")
    @DeleteMapping("/delete/{goodsId}")
    public CommonReturnType deleteGood(@PathVariable int goodsId,HttpServletRequest request) {
        return goodsService.deleteGoodById(goodsId,this.getPartner(request).getPartnerId());
    }

    @ApiOperation(value = "获取分页所需要的商品信息以及分类和品牌信息")
    @GetMapping("/all")
    public CommonReturnType getPageGoods(@RequestParam(value = "curPage",defaultValue = "1",required = false)int curPage,
                                            @RequestParam(value = "size",defaultValue = "10",required = false)int size) {
        PageInfo<GoodsVo> pageInfo = goodsService.getGoodsByPage(curPage,size);
        List<BrandDo> brandDoList = brandService.selectAllBrand();
        List<TypeDo> typeDoList = typeService.selectAllType();
        Map<String,Object> data = new HashMap<>();
        data.put("pageInfo",pageInfo);
        data.put("brandInfo",brandDoList);
        data.put("typeInfo",typeDoList);;
        return CommonReturnType.create(data);
    }

    @ApiOperation(value = "批量删除商品")
    @DeleteMapping("/deleteMany")
    public CommonReturnType deleteGoodsMany(@RequestParam("ids")int[] goodIds,HttpServletRequest request) {
        return goodsService.deleteByManyId(goodIds,this.getPartner(request).getPartnerId());
    }

    @ApiOperation(value = "批量修改商品上下架的状态")
    @PostMapping("/updateShelfStatusFoeMany")
    public CommonReturnType updateShelfStatusForMany(@RequestParam("ids")int[] goodIds,
                                                          @RequestParam("shelfStatus") String shelfStatus,
                                                            HttpServletRequest request) {
        return goodsService.updateShelfStatusForMany(goodIds,shelfStatus,this.getPartner(request).getPartnerId());
    }

    @ApiOperation("使用solr进行全文搜索")
    @PostMapping("/search")
    public CommonReturnType solrSearch(@RequestParam("keyword")String keyword,
                                       @RequestParam(value = "current",defaultValue = "0",required = false) Integer current,
                                       @RequestParam(value = "size",defaultValue = "10",required = false) Integer size) {
        return solrService.searchGoods(keyword,current,size);
    }
}

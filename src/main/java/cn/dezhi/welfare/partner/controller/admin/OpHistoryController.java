package cn.dezhi.welfare.partner.controller.admin;

import cn.dezhi.welfare.partner.controller.AbstractController;
import cn.dezhi.welfare.partner.response.CommonReturnType;
import cn.dezhi.welfare.partner.service.IOpHistoryService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xjk
 * @date 2019/4/27 -  19:12
 * 操作历史纪录控制
 **/
@RestController
@RequestMapping("/history")
public class OpHistoryController extends AbstractController {

    @Autowired
    private IOpHistoryService opHistoryService;

    @ApiOperation(value = "获取最近六条操作记录")
    @GetMapping("/six")
    public CommonReturnType getSixHistoryOp(HttpServletRequest request) {
        return opHistoryService.getSixOpHistory(this.getPartner(request).getPartnerId());
    }
}

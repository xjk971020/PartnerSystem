package cn.dezhi.welfare.partner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author xjk
 * @date 2019/4/19 -  16:02
 **/
@Controller
public class TestContoller {
    @RequestMapping("/test")
    public String test() {
        return "test";
    }
}

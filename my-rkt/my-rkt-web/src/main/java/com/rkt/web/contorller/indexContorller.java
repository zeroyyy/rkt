package com.rkt.web.contorller;

import com.rkt.common.log.LogHelper;
import com.rkt.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by yjj on 2016/7/14.
 */
@Controller
@RequestMapping(value ="/index", method = {RequestMethod.GET,RequestMethod.POST})
public class indexContorller extends BaseController {

    @RequestMapping(value = "/init",method = {RequestMethod.GET,RequestMethod.POST})
    public ModelAndView index(RedirectAttributes redire,Model view){
        LogHelper.info("进入系统成功");
        redire.addAttribute("param", "FNMLNUYODNF");
        view.addAttribute("result", "欢迎进入rkt");
        return 	toVMSkipLayout("/index",view);
    }
}

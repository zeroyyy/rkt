package com.rkt.common.web.exception;

import com.rkt.common.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class ErrorController extends BaseController {

    @RequestMapping("/error500")
    @ResponseBody
    public ModelAndView error500(HttpServletRequest request){
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("reason", request.getAttribute("javax.servlet.error.message"));
        map.put("exception", request.getAttribute("javax.servlet.error.exception"));
        map.put("exception_type", request.getAttribute("javax.servlet.error.exception_type"));
        map.put("request_uri", request.getAttribute("javax.servlet.error.request_uri"));
        map.put("servlet_name", request.getAttribute("javax.servlet.error.servlet_name"));
        
        return toVM("common/error500",map);
    }
    
    @RequestMapping("/error404")
    @ResponseBody
    public ModelAndView error404(HttpServletRequest request){
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("status", request.getAttribute("javax.servlet.error.status_code"));
        map.put("reason", request.getAttribute("javax.servlet.error.message"));
        map.put("exception", request.getAttribute("javax.servlet.error.exception"));
        map.put("exception_type", request.getAttribute("javax.servlet.error.exception_type"));
        map.put("request_uri", request.getAttribute("javax.servlet.error.request_uri"));
        map.put("servlet_name", request.getAttribute("javax.servlet.error.servlet_name"));
        
        return toVM("common/error404",map);
    }

}

package com.netease.shopping.controller;

import com.netease.shopping.service.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * IndexController 中都是test demo
 */


@Controller
public class IndexController {

    @Autowired
    UtilService utilService;

    @RequestMapping(path={"/profile/{userId}"},method = {RequestMethod.GET})
    @ResponseBody
    public String profile(@PathVariable("userId") int userId,
                            @RequestParam("type") int type,
                          @RequestParam(value = "page", defaultValue = "1", required = false) String key ){
        return "profile"+1+"type"+type+"page"+key;
    }

    @RequestMapping(path={"/vm"},method = {RequestMethod.GET})
    public String template(Model model){
        model.addAttribute("var","vvvvvariable");
        List<String> list = new ArrayList<>();
        list.add("red"); list.add("green"); list.add("blue");
        model.addAttribute("colors",list);
        model.addAttribute("date",new java.sql.Date(new Date().getTime()));
        Map<String,String> map = new HashMap<>();
        map.put("1","red");
        map.put("2","green");
        model.addAttribute("map1", map);
        return "home";
    }


    @RequestMapping(path={"/request"},method = {RequestMethod.GET})
    @ResponseBody
    public String template1(Model model, HttpServletRequest request,
                            HttpServletResponse response,
                            HttpSession httpSession,
                            @CookieValue("JSESSIONID") String sessionId){
        StringBuilder sb = new StringBuilder();
        sb.append("Cookie"+sessionId);
        if(request.getCookies() !=null){
            for(Cookie cookie:request.getCookies()){
                sb.append("Cookie"+cookie.getName()+"Value"+cookie.getValue());
            }
        }

        sb.append(request.getMethod()+"<br>");
        sb.append(request.getQueryString()+"<br>");
        sb.append(request.getPathInfo()+"<br>");
        sb.append(request.getRequestURI()+"<br>");


        response.addCookie(new Cookie("userName","lzyu"));
        return sb.toString();
    }

//    @RequestMapping(path={"/redirect/{code}"},method = {RequestMethod.GET})
//    public String  redirect(@PathVariable("code") int code,
//                            HttpSession session){
//        session.setAttribute("msg","nihao");
//        return "redirect:/";
//    }

    @RequestMapping(path={"/admin"},method = {RequestMethod.GET})
    @ResponseBody
    public String admin(@RequestParam("key") String key){
        if("admin".equals(key)){
            return "hello admin";
        }
        throw new IllegalArgumentException("参数不对");
    }

    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e){
        return "error:"+e.getMessage();
    }
}

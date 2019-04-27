package com.netease.shopping.controller;

import com.alibaba.fastjson.JSON;
import com.netease.shopping.model.*;
import com.netease.shopping.service.AccountService;
import com.netease.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class AccountController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    AccountService accountService;

    @Autowired
    ProductService productService;

    @RequestMapping(path={"/account"},method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){
        User user = hostHolder.getUser();
        List<Account> accounts = accountService.selectAccounts(user.getId());
        List<ViewObject> vos = new ArrayList<>();
        BigDecimal sum = new BigDecimal("0");
        for(Account account:accounts){
            ViewObject vo = new ViewObject();
            vo.set("account", account);
            vos.add(vo);
            sum = sum.add(account.getPrice().multiply(new BigDecimal(String.valueOf(account.getQuantity()))));
        }
        model.addAttribute("vos",vos);
        model.addAttribute("sum",sum);
        return "account";
    }


    @RequestMapping(path={"/api/buy"},method = {RequestMethod.GET, RequestMethod.POST})
    public String buy(Model model, HttpServletRequest request){
        User user = hostHolder.getUser();

        // 获取json串
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = request.getReader();) {
            char[]buff = new char[1024];
            int len;
            while((len = reader.read(buff)) != -1) {
                sb.append(buff,0, len);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        String jsonStr = sb.toString();
        List<ProductJson> productJsons = JSON.parseArray(jsonStr, ProductJson.class);
        //System.out.println(sb.toString());
        // 加进账单表
        for(ProductJson product:productJsons){
            int id = Integer.valueOf(product.getId());
            int num = Integer.valueOf(product.getNumber());
            Account account = new Account();
            account.setUser_id(user.getId());
            account.setQuantity(num);
            account.setProduct(new Product(id));
            account.setPrice(productService.getSingleProduct(id).getPrice());
            account.setCreate_time(new Date());
            accountService.addAccount(account);
        }

        //
//        List<Account> accounts = accountService.selectAccounts(user.getId());
//        List<ViewObject> vos = new ArrayList<>();
//        BigDecimal sum = new BigDecimal("0");
//        for(Account account:accounts){
//            ViewObject vo = new ViewObject();
//            vo.set("account", account);
//            vos.add(vo);
//            sum = sum.add(account.getPrice().multiply(new BigDecimal(String.valueOf(account.getQuantity()))));
//        }
//        model.addAttribute("vos",vos);
//        model.addAttribute("sum",sum);
        return "settleAccount1";
    }
}

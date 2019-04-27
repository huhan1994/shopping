package com.netease.shopping.controller;

import com.netease.shopping.model.HostHolder;
import com.netease.shopping.model.Product;
import com.netease.shopping.model.User;
import com.netease.shopping.model.ViewObject;
import com.netease.shopping.service.AccountService;
import com.netease.shopping.service.ProductService;
import org.omg.CORBA.INTERNAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
public class HomeController {

    @Autowired
    ProductService productService;

    @Autowired
    AccountService accountService;

    @Autowired
    HostHolder hostHolder;

    private List<ViewObject> getBuyer_products(int offset, int limit, int status){
        List<Product> productList = productService.getLatestProduct(offset,limit);
        if(hostHolder.getUser()==null){
            // 没登陆
            //productList = productService.getLatestProduct(offset,limit);
        }else if(hostHolder.getUser().getMark()==0){
            // 买家
            int user_id = hostHolder.getUser().getId();
            if(status==0){
                // 所有产品
                List<Product> productList_buy = productService.getInbuyProduct(user_id,offset,limit);
                productList = getMark(productList,productList_buy);
            }else{
                // 未购买过的产品
                productList = productService.getUnbuyProduct(user_id,offset,limit);
                for(Product product:productList){
                    product.setStatus(0);
                }
            }
        }else{
            // 卖家
            List<Integer> pid_list = accountService.select_pid();
            productList = getMark1(productList,pid_list);
        }



        List<ViewObject> vos =new ArrayList<>();
        for(Product product:productList){
            ViewObject vo = new ViewObject();
            vo.set("product",product);
            vos.add(vo);
        }
        return vos;
    }

    private List<Product> getMark(List<Product> list, List<Product> inlist){
        HashSet<Integer> hashSet = new HashSet<>();
        for(Product product:inlist){
            hashSet.add(product.getId());
        }
        for (Product product:list){
            if(hashSet.contains(product.getId())){
                product.setStatus(1);
            }else {
                product.setStatus(0);
            }
        }
        return list;
    }

    private List<Product> getMark1(List<Product> list, List<Integer> inlist){
        HashSet<Integer> hashSet = new HashSet<>();
        for(int i:inlist){
            hashSet.add(i);
        }
        for (Product product:list){
            if(hashSet.contains(product.getId())){
                product.setStatus(1);
            }else {
                product.setStatus(0);
            }
        }
        return list;
    }

/*
    @RequestMapping(path={"/","/index"},method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model){

        model.addAttribute("vos", getBuyer_products(0,10,0));
        return "index";
    }
    */

    @RequestMapping(path={"/","/index"},method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "type",defaultValue = "0") int type){
        if(type==1){
            // type==1 显示未购买的内容
            model.addAttribute("vos", getBuyer_products(0,20,1));
        }else{
            model.addAttribute("vos", getBuyer_products(0,20,0));
        }
        return "index";
    }

}

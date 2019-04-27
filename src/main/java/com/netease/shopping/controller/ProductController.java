package com.netease.shopping.controller;

import com.netease.shopping.model.Account;
import com.netease.shopping.model.HostHolder;
import com.netease.shopping.model.Product;
import com.netease.shopping.model.ViewObject;
import com.netease.shopping.service.AccountService;
import com.netease.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    AccountService accountService;

    @RequestMapping(path={"/show"},method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model,
                        @RequestParam(value = "id",defaultValue = "0") int id){
        ViewObject vo = new ViewObject();
        Product product = productService.getSingleProduct(id);
        // 产品信息
        vo.set("product", product);
        // 查找账单表中对应用户是否买过此产品
        if(hostHolder.getUser()!=null){
            int user_id = hostHolder.getUser().getId();
            Account account = accountService.selectAccountCount(user_id,product.getId());
            if(account==null){
                vo.set("mark", 0);
            }else{
                vo.set("mark", 1);
                vo.set("old_price",account.getPrice());
            }
        }
        model.addAttribute("vo", vo);
        return "show";
    }
}

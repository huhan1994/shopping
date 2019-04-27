package com.netease.shopping.controller;

import com.netease.shopping.model.*;
import com.netease.shopping.service.AccountService;
import com.netease.shopping.service.CartService;
import com.netease.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 *  本来打算做购物车表的，但是demo直接使用前端搞定的购物车，遂此文件报废了，购物车信息直接记录在cookie中。
 */

@Controller
public class CartController {
    @Autowired
    ProductService productService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CartService cartService;

    @RequestMapping(path={"/add"},method = {RequestMethod.GET, RequestMethod.POST})
    public String add(HttpServletRequest request,
                        @CookieValue("product") String pro_list){
        String[] list = pro_list.split(":");
        int pid = Integer.valueOf(list[1]);
        int num = Integer.valueOf(list[7]);

        Cart cart = new Cart();
        // 添加进购物车
        if(hostHolder.getUser()!=null){
            int user_id = hostHolder.getUser().getId();
            cart.setUser_id(user_id);
            Product product = new Product(pid);
            cart.setProduct(product);
            cart.setQuantity(num);
            cartService.addCart(cart);
        }
        return "redirect:/show?id=" + pid;  //存疑
    }




    @RequestMapping(path={"/settleAccount"},method = {RequestMethod.GET, RequestMethod.POST})
    public String settleAccount(Model model,
                        HttpServletResponse response){
//        int user_id = hostHolder.getUser().getId();
//        List<Cart> carts = cartService.selectCarts(user_id);
//        List<ViewObject> vos = new ArrayList<>();
//
//        List<CartJson> list = new ArrayList<>();
//        for(Cart cart:carts){
//            CartJson cartJson = new CartJson();
//            cartJson.setId(cart.getId());
//            cartJson.setPrice(cart.getProduct().getPrice());
//            cartJson.setTitle(cart.getProduct().getName());
//            cartJson.setNum(cart.getQuantity());
//            list.add(cartJson);
//        }
//        String str0 = JSON.toJSONString(list);
//
//        model.addAttribute("vos",vos);
//
//        try {
//            String cookie = URLEncoder.encode(str0, "utf-8");
//            response.addCookie(new Cookie("product", cookie));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }

        return "settleAccount1";
    }
}

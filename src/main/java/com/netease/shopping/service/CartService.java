package com.netease.shopping.service;

import com.netease.shopping.dao.CartDAO;
import com.netease.shopping.model.Account;
import com.netease.shopping.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    @Autowired
    CartDAO cartDAO;

    public int addCart(Cart cart){
        return cartDAO.addCart(cart)>0?cart.getId():0;
    }

    public List<Cart> selectCarts(int user_id){
        return cartDAO.selectCarts(user_id);
    }
}

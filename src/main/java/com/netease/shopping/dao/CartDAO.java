package com.netease.shopping.dao;

import com.netease.shopping.model.Cart;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CartDAO {
    String TABLE_NAME = "cart";
    String INSET_FIELDS = " user_id, product_id, quantity ";
    String SELECT_FIELDS = " id, user_id, product_id, quantity";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{user_id},#{product.id},#{quantity})"})
    int addCart(Cart cart);


    List<Cart> selectCarts(int user_id);

}

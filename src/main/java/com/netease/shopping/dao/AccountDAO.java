package com.netease.shopping.dao;

import com.netease.shopping.model.Account;
import com.netease.shopping.model.Product;
import com.netease.shopping.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AccountDAO {
    String TABLE_NAME = "account";
    String INSET_FIELDS = " user_id, product_id, price, quantity, create_time ";
    String SELECT_FIELDS = " id, user_id, product_id, quantity, create_time";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{user_id},#{product.id},#{price},#{quantity},#{create_time})"})
    int addAccount(Account account);

    Account selectAccountCount(int user_id, int product_id);

    List<Account> selectAccounts(int user_id);

    @Select({"select ", "product_id", " from ", TABLE_NAME})
    List<Integer> select_pid();
}

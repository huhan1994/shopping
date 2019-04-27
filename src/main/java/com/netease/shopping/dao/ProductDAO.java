package com.netease.shopping.dao;

import com.netease.shopping.model.Product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface ProductDAO {

    String TABLE_NAME = "product";
    String INSET_FIELDS = " user_id, name, abstract, main_image, sub_image, detail, price, stock, status ";
    //String SELECT_FIELDS = " id, name, password, salt, mark";

    @Insert({"insert into ", TABLE_NAME, "(", INSET_FIELDS,
            ") values (#{user_id},#{name},#{abstractt},#{main_image},#{sub_image},#{detail},#{price},#{stock},#{status})"})
    int addProduct(Product product);

    List<Product> selectLatestProducts(int offset, int limit);  // 买家首页显示在售的产品

    List<Product> selectInbuyProducts(int user_id, int offset, int limit);  // 买家首页显示已买过的产品

    List<Product> selectUnbuyProducts(int user_id, int offset, int limit);  // 买家首页显示未买过的产品

    Product selectSingleProduct(int id);  // 查找指定id的产品

    List<Product> selectFabuProducts(int user_id, int offset, int limit); // 卖家发布的产品

    @Update({"Update product set name = #{name}, abstract=#{abstractt}, main_image=#{main_image}," +
            "sub_image=#{sub_image}, detail=#{detail}, price=#{price} where id = #{id}"})
    int updateProduct(Product product);

}

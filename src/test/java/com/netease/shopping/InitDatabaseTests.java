package com.netease.shopping;

import com.alibaba.fastjson.JSON;
import com.netease.shopping.dao.AccountDAO;
import com.netease.shopping.dao.ProductDAO;
import com.netease.shopping.dao.UserDAO;
import com.netease.shopping.model.*;
import com.netease.shopping.service.CartService;
import com.netease.shopping.service.ProductService;
import com.netease.shopping.util.ShoppingUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.Cookie;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDatabaseTests {

    @Autowired
    UserDAO userDAO;

    @Autowired
    ProductDAO productDAO;

    @Autowired
    ProductService productService;

    @Autowired
    AccountDAO accountDAO;

    @Autowired
    CartService cartService;

    @Test
    public void initDatabase_user() {
        for (int i = 5; i < 6; i++) {
            User user = new User();
            user.setMark(0);
            user.setName("buyer"+i);
            user.setSalt("");
            String password  = "buyer"+i;
            password = ShoppingUtil.MD5(password+user.getSalt());
            user.setPassword(password);
            userDAO.addUser(user);
        }
    }

    @Test
    public void initDatabase_seller() {
        for (int i = 2; i < 3; i++) {
            User user = new User();
            user.setMark(1);
            user.setName("seller");
            user.setSalt("");
            String password  = "seller";
            password = ShoppingUtil.MD5(password+user.getSalt());
            user.setPassword(password);
            userDAO.addUser(user);
        }
    }

    @Test
    public void initDatabase_account() {
        for (int i = 2; i < 3; i++) {
            Account account = new Account();
            account.setUser_id(3);
            Product product = new Product();
            product.setId(44);
            product.setUser_id(1);
            account.setProduct(product);
            account.setQuantity(2);
            account.setCreate_time(new Date());
            //accountDAO.addAccount(account);
        }
    }
    /*
    @Test
    public void initDatabase_product() {
        Random random = new Random();
        for (int i = 10; i < 20; i++) {
            Product product = new Product();
            product.setUser_id(1);
            product.setName("product"+i);
            product.setAbstractt("abstract"+i);
            product.setMain_image(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
            product.setSub_image("");
            product.setDetail("detail"+i);
            product.setPrice(new BigDecimal(i*10));
            product.setStock(10);
            product.setStatus(1);
            productDAO.addProduct(product);
        }
    }


    @Test
    public void select_product() {
        List<Product> res = productService.getLatestProduct(0,5);
        for(Product product:res){
            System.out.println(product.getMain_image());
        }
    }*/

    @Test
    public void updateDatabase_product() {
        Random random = new Random();
        Product product = new Product();
        product.setUser_id(6);
        product.setName("product");
        product.setAbstractt("abstract");
        product.setMain_image(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(1000)));
        product.setSub_image("");
        product.setDetail("detail");
        product.setPrice(new BigDecimal(2*10));
        product.setStock(10);
        product.setStatus(1);
        productDAO.updateProduct(product);
    }

    @Test
    public void test_account(){
        List<Integer> list = accountDAO.select_pid();
        System.out.println(list);
    }

    @Test
    public void testJson(){
        String json = ShoppingUtil.GetJsonString(0,"gogo");
        System.out.println(json);
    }

//    @Test
//    public void testJson2(){
//        List<Cart> carts = cartService.selectCarts(3);
//        List<CartJson> list = new ArrayList<>();
//        for(Cart cart:carts){
//            CartJson cartJson = new CartJson();
//            cartJson.setId(cart.getId());
//            cartJson.setPrice(cart.getProduct().getPrice());
//            cartJson.setTitle(cart.getProduct().getName());
//            cartJson.setNum(cart.getQuantity());
//            list.add(cartJson);
//        }
//        String str = JSON.toJSONString(list);
//        String ss = str.replace(",", "#");
//        System.out.println(ss);
//    }
}



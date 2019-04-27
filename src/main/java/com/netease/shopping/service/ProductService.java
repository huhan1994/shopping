package com.netease.shopping.service;

import com.netease.shopping.dao.ProductDAO;
import com.netease.shopping.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductDAO productDAO;

    public int addProduct(Product product){
        int val = productDAO.addProduct(product);
        return val>0?val:0;
    }

    public List<Product> getLatestProduct(int offset, int limit){
        return productDAO.selectLatestProducts(offset,limit);
    }

    public List<Product> getInbuyProduct(int user_id, int offset, int limit){
        return productDAO.selectInbuyProducts(user_id,offset,limit);
    }

    public List<Product> getUnbuyProduct(int user_id,int offset, int limit){
        return productDAO.selectUnbuyProducts(user_id,offset,limit);
    }

    public Product getSingleProduct(int id){
        return productDAO.selectSingleProduct(id);
    }

    public int updateProduct(int id,Product product){
        product.setId(id);
        int val = productDAO.updateProduct(product);
        return val>0?val:0;
    }

}

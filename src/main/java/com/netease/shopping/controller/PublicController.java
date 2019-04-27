package com.netease.shopping.controller;

import com.netease.shopping.model.*;
import com.netease.shopping.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

@Controller
public class PublicController {

    @Autowired
    HostHolder hostHolder;

    @Autowired
    ProductService productService;


    @RequestMapping(path={"/public1"},method = {RequestMethod.GET, RequestMethod.POST})
    public String seller_public1(){
        return "public1";
    }

    @RequestMapping(path={"/publicSubmit"},method = {RequestMethod.GET, RequestMethod.POST})
    public String publicSubmit(Model model,
                               @RequestParam(value = "title") String name,
                               @RequestParam(value = "summary") String summary,
                               @RequestParam(value = "pic") String pic,
                               @RequestParam(value = "image") String image,
                               @RequestParam(value ="path") String file,
                               @RequestParam(value = "detail") String detail,
                               @RequestParam(value = "price") double price){
        int user_id = hostHolder.getUser().getId();
        Product product = new Product();
        product.setUser_id(user_id);
        product.setName(name);
        product.setAbstractt(summary);
        product.setDetail(detail);
        product.setPrice(new BigDecimal(price));
        product.setStock(10); //库存
        product.setStatus(0); //产品状态

        if(pic.equals("url")){
            product.setMain_image(image);
        }else if (pic.equals("file")){
            String path = "http://localhost:8080/images/";
            file = getFileName(file);
            product.setSub_image(path+file);
        }

        productService.addProduct(product);

        // 此product可能无id
        model.addAttribute("product",product);
        return "publicSubmit";
    }

    @RequestMapping(path={"/api/publicUpload"},method = {RequestMethod.GET, RequestMethod.POST})
    public String publicUpload( HttpServletRequest request,
            @RequestParam(value ="file") MultipartFile file)throws IOException {
        // 将此文件保存到/images 路径下
        if(!file.isEmpty()){
            // 上传文件路径,此路径为部署后的项目路径。
            String path = request.getServletContext().getRealPath("/images/");
            String filename = file.getOriginalFilename();
            File filepath = new File(path,filename);
            //判断路径是否存在，如果不存在就创建一个
            if(!filepath.getParentFile().exists()){
                filepath.getParentFile().mkdirs();
            }
            //将上传文件保存到一个目标文件当中
            file.transferTo(new File(path+File.separator+filename));
            return "public1";
        }else{
            return "error";
        }
    }

    @RequestMapping(path={"/edit"},method = {RequestMethod.GET, RequestMethod.POST})
    public String edit( Model model,
                                @RequestParam(value = "id",defaultValue = "0") int id){
        // 更新产品信息
        ViewObject vo = new ViewObject();
        Product product = productService.getSingleProduct(id);
        // 产品信息
        vo.set("product", product);
        model.addAttribute("vo", vo);
        return "edit";
    }


    @RequestMapping(path={"/editSubmit"},method = {RequestMethod.GET, RequestMethod.POST})
    public String editSubmit(Model model,
                               @RequestParam(value = "title") String name,
                               @RequestParam(value = "summary") String summary,
                               @RequestParam(value = "pic") String pic,
                               @RequestParam(value = "image") String image,
                               @RequestParam(value ="path") String file,
                               @RequestParam(value = "detail") String detail,
                               @RequestParam(value = "price") double price,
                               @RequestParam(value="id") int id){
        int user_id = hostHolder.getUser().getId();
        Product product = new Product();
        product.setName(name);
        product.setAbstractt(summary);
        product.setDetail(detail);
        product.setPrice(new BigDecimal(price));


        if(pic.equals("url")){
                product.setMain_image(image);
        }else if (pic.equals("file")){
            String path = "http://localhost:8080/images/";
            product.setSub_image(path+file);
        }

        productService.updateProduct(id,product);

        model.addAttribute("id", id);

        return "editSubmit";
    }

    private String getFileName(String file){
        int i = file.length()-1;
        for(;i>0;i--){
            if(file.substring(i,i+1).equals("\\")){break;}
        }
        return file.substring(i+1,file.length());
    }
}

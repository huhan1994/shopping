package com.netease.shopping.service;

import com.netease.shopping.dao.LoginTicketDAO;
import com.netease.shopping.dao.UserDAO;
import com.netease.shopping.model.LoginTicket;
import com.netease.shopping.model.User;
import com.netease.shopping.util.ShoppingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public Map<String, Object> login(String username, String password){
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(username)) {
            map.put("msg", "用户名不能为空");
            return map;
        }

        if (StringUtils.isEmpty(password)) {
            map.put("msg", "密码不能为空");
            return map;
        }

        User user = userDAO.selectByName(username);

        // 验证密码
        if(user==null){
            map.put("msg","用户名不存在");
            return map;
        }
        if(!ShoppingUtil.MD5(password+user.getSalt()).equals(user.getPassword())){
            map.put("msg","密码不正确");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket",ticket);
        return map;
    }

    private String addLoginTicket(int userId) {
        LoginTicket ticket = new LoginTicket();
        ticket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000*3600*24);
        ticket.setExpired(date);
        ticket.setStatus(0);
        ticket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicketDAO.addTicket(ticket);
        return ticket.getTicket();
    }

    public void logout(String ticket){
        loginTicketDAO.updateStatus(ticket, 1);
    }
}

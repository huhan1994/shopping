package com.netease.shopping.service;

import com.netease.shopping.dao.AccountDAO;
import com.netease.shopping.model.Account;
import com.netease.shopping.model.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    @Autowired
    AccountDAO accountDAO;

    public int addAccount(Account account){
        return accountDAO.addAccount(account)>0?account.getId():0;
    }

    public Account selectAccountCount(int user_id, int product_id){
        Account account = accountDAO.selectAccountCount(user_id,product_id);
        if(account==null){
            return null;
        }else{
            return account;
        }
    }

    public List<Account> selectAccounts(int user_id){
        return accountDAO.selectAccounts(user_id);
    }

    public List<Integer> select_pid(){return  accountDAO.select_pid();}
}

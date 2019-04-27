package com.netease.shopping.service;

import org.springframework.stereotype.Service;

@Service
public class UtilService {

    public String getMessage(int id){
        return "Message"+id;
    }
}

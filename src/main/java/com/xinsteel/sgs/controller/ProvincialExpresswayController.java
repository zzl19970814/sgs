package com.xinsteel.sgs.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xinsteel.sgs.service.ProvincialExpresswayServiceImpl;
import com.xinsteel.sgs.service.impl.ProvincialExpresswayService;
import com.xinsteel.sgs.utils.HttpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/provincialExpressway")
public class ProvincialExpresswayController {

    @Autowired
    private ProvincialExpresswayServiceImpl provincialExpresswayService;

    @GetMapping("/sendMessage")
    public void SendMessage(){
         provincialExpresswayService.SendMessage();
    }
}

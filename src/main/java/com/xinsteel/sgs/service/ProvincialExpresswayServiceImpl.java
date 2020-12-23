package com.xinsteel.sgs.service;

import com.alibaba.fastjson.JSONObject;
import com.xinsteel.sgs.service.impl.ProvincialExpresswayService;
import com.xinsteel.sgs.utils.HttpRequest;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProvincialExpresswayServiceImpl implements ProvincialExpresswayService {
    @Override
    public String SendMessage() {
        String orderFormId = SelectOrderNo();
        String orderFormType = "1";
        String orderFormcreateTime;
        return null;
    }

    @Override
    public String SelectOrderNo() {
        String url = "http://172.16.4.238:8080/DataQuerySocket/service/XbToMesPactImportService/SalePlanSq";
        Map<String,String> data = new HashMap<>();
        data.put("pageIndex","1");
        data.put("pageSize","1");
        data.put("startTime","2020-08-10");
        data.put("endTime","2020-08-15");
        data.put("memo0","");
        data.put("memo1","");
        data.put("memo2","");
        data.put("memo3","");
        data.put("memo4","95");

        String json = "["+ JSONObject.toJSONString(data)+"]";
        System.out.println(json);
        String result =  HttpRequest.sendPost(url,json);
        JSONObject jsonObject1 = JSONObject.parseObject(result);
        String subJson = jsonObject1.getJSONArray("rows").getString(0);
        JSONObject jsonObject2 = JSONObject.parseObject(subJson);
        String orderNo = (String) jsonObject2.get("ORDERNO");
        System.out.println(orderNo);
        return orderNo;
    }
}

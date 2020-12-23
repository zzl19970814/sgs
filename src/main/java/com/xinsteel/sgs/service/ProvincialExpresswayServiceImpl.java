package com.xinsteel.sgs.service;

import com.alibaba.fastjson.JSONObject;
import com.xinsteel.sgs.service.impl.ProvincialExpresswayService;
import com.xinsteel.sgs.utils.HttpRequest;
import org.springframework.stereotype.Component;

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
        String url = "http://172.16.4.238:8080/DataQuerySocket/service/XbToMesPactImportService/SalePlan";
        String json = "[{\"pageIndex\":1,\"pageSize\":1,\"startTime\":\"2020-08-10\",\"endTime\":\"2020-08-13\",\"memo0\":\"\",\"memo1\":\"\",\"memo2\":\"\",\"memo3\":\"\",\"memo4\": \"95\"}]";
        String result =  HttpRequest.sendPost(url,json);
        JSONObject jsonObject = JSONObject.parseObject(result);
        String subJson = jsonObject.getJSONArray("rows").getString(0);
        JSONObject jsonObject1 = JSONObject.parseObject(subJson);
        String orderNo = (String) jsonObject1.get("ORDERNO");
        System.out.println(orderNo);
        return orderNo;
    }

}

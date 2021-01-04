package com.xinsteel.sgs.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xinsteel.sgs.entity.OrderInfo;
import com.xinsteel.sgs.entity.TransportInfo;
import com.xinsteel.sgs.service.OrderInfoService;
import com.xinsteel.sgs.service.ProvincialExpresswayService;
import com.xinsteel.sgs.service.TransportInfoService;
import com.xinsteel.sgs.utils.DateUtil;
import com.xinsteel.sgs.utils.HttpRequest;
import com.xinsteel.sgs.utils.Jsoup_xml;
import org.apache.axis.utils.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.*;


@Service
@Component
public class ProvincialExpresswayServiceImpl implements ProvincialExpresswayService {

    @Autowired
    OrderInfoService orderInfoService;

    @Autowired
    TransportInfoService transportInfoService;

    @Override
    public void sendMessage() {

        OrderInfo orderInfo = new OrderInfo();
        //         调用
//        String json = getJsonStringByOrderId("1");
        //         调用省高速接口，将数据上传到他们的接口
//        System.out.println(json);
//        String result =  HttpRequest.sendPost("",json);
        saveOrUpdateOrderInfoAndTransportInfo();

    }

    /**
     * 上传平台的方法
     */
    public void upload(){

    }

    @Override
    public void saveOrUpdateOrderInfoAndTransportInfo(){
        // 保存订单号
//        List<String> orderNoList = selectOrderNoList();
        //saveOrderIds(orderNoList);

        // 保存订单信息
        //saveOrderInfo();

        // 保存运单号
        //saveBillwaysIds();

        // 保存运单信息
        //saveTransportInfo();
        String url="http://2yxmkk.natappfree.cc/TMS_SetOrder/loginUlog?username=xgjt&password=1234qwer";
        String s=HttpRequest.sendPost(url,"");
        System.out.println(s);

        JSONObject jsonObject = JSONObject.parseObject(s);
        String TMS_TOKEN = jsonObject.getString("data");

        sendPlatform(TMS_TOKEN);
    }


    /**
     * 上传平台
     */
    public void sendPlatform(String TMS_TOKEN){
        List<OrderInfo> orderInfoList = orderInfoService.list(null);
        for (OrderInfo orderInfo : orderInfoList) {
            String orderId = orderInfo.getOrderFormId();
            System.out.println(orderId);
            String json = getJsonStringByOrderId(orderId);
            System.out.println(json);


            String url="http://2yxmkk.natappfree.cc/TMS_SetOrder/setOrderFormInfoToEnd?TMS_TOKEN="+TMS_TOKEN;

           String s = HttpRequest.sendPost(url, json);
            System.out.println(s);

        }
    }


    /**
     * 根据数据库运单号查运单信息并保存
     */
    public void saveTransportInfo(){
        List<TransportInfo> transportInfos = transportInfoService.list(null);
        for (TransportInfo transportInfo : transportInfos) {
            String waybillId = transportInfo.getWaybillId();
            TransportInfo transportInfo1 = selectTransportInfo(waybillId);
            if (transportInfo1 != null){
                transportInfoService.saveOrUpdate(transportInfo1);
            }
        }
    }


    /**
     * 根据两个数据库表的差值保存运单号
     */
    public void saveBillwaysIds(){
        List<String> orderIds = getOrderIds();
        for (String orderId : orderIds) {

            // 调用接口查运单号
            Map<String, String> stringMap = parseBillIds(orderId);
            if (stringMap!=null){

                for (String key: stringMap.keySet()
                ) {

                    TransportInfo transportInfo = new TransportInfo();
                    transportInfo.setWaybillId(key);
                    transportInfo.setOId(stringMap.get(key));
                    transportInfoService.saveOrUpdate(transportInfo);
                }
            }
        }
    }


    /**
     * 根据数据库orderId保存订单信息
     */
    public void saveOrderInfo(){
        List<OrderInfo> orderInfoList = orderInfoService.list(null);
        for (OrderInfo orderInfo : orderInfoList) {
            String orderId = orderInfo.getOrderFormId();
            OrderInfo parseOrderInfo = parseOrderInfo(orderId);
            orderInfoService.saveOrUpdate(parseOrderInfo);
        }
    }

    /**
     * 获取数据表中的OrderId差值
     * @return orderIds
     */
    public List<String> getOrderIds(){
        List<String> orderIds = new ArrayList<>();
        List<OrderInfo> list = orderInfoService.list(null);
        List<TransportInfo> list1 = transportInfoService.list(null);
        Set<String> transportOrderIdSet = new HashSet<>();
        for (TransportInfo transport: list1
             ) {
            transportOrderIdSet.add(transport.getOId());
        }
        for (OrderInfo order: list
             ) {
            orderIds.add(order.getOrderFormId());
        }
        List<String> transportIdsList = new ArrayList<>(transportOrderIdSet);

        orderIds.removeAll(transportIdsList);

        return orderIds;
    }
    /**
     * 保存解析订单信息
     */
    public String getOrderInfoByOrderId(String orderId) {

        String url = "http://172.16.4.238:8080/DataQuerySocket/service/XbToMesPactImportService/GetOrderData";
        Map<String, Object> data = new HashMap<>();

        data.put("memo0", orderId);
        String json = "[" + JSONObject.toJSONString(data) + "]";
        System.out.println(json);
        String result = HttpRequest.sendPost(url, json);

        System.out.println("sdfsadfasdfasgsgsaefwr+++++++++++"+result);
        return result;
    }


    /**
     * 解析orderInfo
     * @param orderId
     * @return
     */
    public OrderInfo parseOrderInfo(String orderId) {
        String result = getOrderInfoByOrderId(orderId);
        JSONObject resultJson = JSONObject.parseObject(result);
        JSONObject jsonObject1 = resultJson.getJSONArray("rows").getJSONObject(0);


        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderFormId(orderId.trim());
        orderInfo.setOrderFormHandle(jsonObject1.getString("ORDERFORMHANDLE"));
        System.out.println("========"+jsonObject1.getString("ORDERFORMHANDLE"));

        orderInfo.setOrderFormEstimate(jsonObject1.getString("ORDERFORMESTIMATE").trim());

        orderInfo.setOrderFormCreatetime(jsonObject1.getString("ORDERFORMCREATETIME").trim());
        String s = jsonObject1.getString("MATERIALNMBER");

        orderInfo.setMaterialNumber(s);

        orderInfo.setOrderFormEndTime(jsonObject1.getString("ORDERFORMENDTIME").trim());
        orderInfo.setMaterialName(jsonObject1.getString("MATERIALNAME").trim());
        orderInfo.setEndThing(jsonObject1.getString("ENDTHING").trim());
        orderInfo.setCustomerUserName(jsonObject1.getString("CUSTOMERUSER").trim());

        return orderInfo;
    }


    /**
     * 解析billIds
     * @param orderId
     * @return
     */
    public Map<String, String> parseBillIds(String orderId) {

        String result = getOrderInfoByOrderId(orderId);

        JSONObject resultJson = JSONObject.parseObject(result);
        JSONObject jsonObject1 = resultJson.getJSONArray("rows").getJSONObject(0);

         String waybillids = jsonObject1.getString("WAYBILLID");
         if (!StringUtils.isEmpty(waybillids)){

             String[] split = waybillids.split(",");

             Map<String, String> map = new HashMap();
             for (String s : split) {

                 map.put(s, orderId);
             }
             return map;
         }
         return null;


    }

    /**
     * 保存orderId号
     */
    public void saveOrderIds(List<String> orderIds){
        for (String orderId: orderIds
             ) {
            OrderInfo orderInfo = new OrderInfo();
            orderInfo.setOrderFormId(orderId.trim());

            List<String> orderIdList = new ArrayList<>();
            List<OrderInfo> orderInfoList = orderInfoService.list(null);
            for (OrderInfo orderInfo1 : orderInfoList) {
                String tempId = orderInfo1.getOrderFormId();
                orderIdList.add(tempId);
            }
            if (!orderIdList.contains(orderId)){

                orderInfoService.save(orderInfo);
            }

        }
    }




    /**
     * 查询mes中的运单信息
     */
    public TransportInfo selectTransportInfo(String billId){
        try {
            // 提取运单号前8位的日期
            String substring = billId.substring(0, 8);
            System.out.println(substring);

            // 4天内的订单
            String formatDate = DateUtil.formatDate(DateUtil.addDays(new Date(), -6));
            String replace = formatDate.replace("-", "");
            System.out.println(replace);

            // 判断是否是在4天内
            if (Long.parseLong(substring) >= Long.parseLong(replace)){

                String urlWsdl = "http://172.16.4.68/MES_WS/Service.asmx?wsdl";
                String targetNamespace = "http://tempuri.org/";
                String soap = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\n" +
                        "   <soap:Header/>\n" +
                        "   <soap:Body>\n" +
                        "      <tem:GetJhhBySjData_2>\n" +
                        "         <!--Optional:-->\n" +
                        "         <tem:cJhh>"+billId+"</tem:cJhh>\n" +
                        "      </tem:GetJhhBySjData_2>\n" +
                        "   </soap:Body>\n" +
                        "</soap:Envelope>";
                CloseableHttpClient httpClient = HttpClients.createDefault();
                HttpPost httpPost = new HttpPost(urlWsdl);
                httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
                httpPost.setHeader("SOAPAction", targetNamespace + "GetJhhBySjData_2");
                InputStreamEntity entity = new InputStreamEntity(new ByteArrayInputStream(soap.getBytes()));
                httpPost.setEntity(entity);
                CloseableHttpResponse response = httpClient.execute(httpPost);
                TransportInfo transportInfo = new TransportInfo();
                if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                    HttpEntity responseEntity = response.getEntity();
                    String back = EntityUtils.toString(responseEntity);
                    System.out.println("httpClient返回soap：" + back);
                    try {

                        Map<String, String> trMessage = Jsoup_xml.getTrMessage(back);
                        transportInfo.setWaybillId(trMessage.get("jhh"));
                        transportInfo.setTare(trMessage.get("pz"));
                        transportInfo.setNetWeight(trMessage.get("mz"));
                        transportInfo.setGrossWeight(trMessage.get("jz"));
                        transportInfo.setWaybillStartTime(trMessage.get("psj"));
                        transportInfo.setWholeName(trMessage.get("ch"));

                        return transportInfo;
                    }catch (Exception e){
                        return null;
                    }


                } else {
                    System.out.println("HttpClinet返回状态码：" + response.getStatusLine().getStatusCode());
                    return null;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 获取公司的近半年的订单号
     * @return
     */
    public List<String> selectOrderNoList() {
        String url = "http://172.16.4.238:8080/DataQuerySocket/service/XbToMesPactImportService/SalePlan";
        Map<String,Object> data = new HashMap<>();
        data.put("pageIndex","1");
        data.put("pageSize","10000");
        data.put("startTime",DateUtil.formatDate(DateUtil.addDays(new Date(), -5)));
        data.put("endTime", DateUtil.formatDate(new Date()));
        data.put("memo0","");
        data.put("memo1","");
        data.put("memo2","");
        data.put("memo3","");
        data.put("memo4","8601240032");

        String json = "["+ JSONObject.toJSONString(data)+"]";
        System.out.println(json);
        String result =  HttpRequest.sendPost(url,json);
        JSONObject jsonObject1 = JSONObject.parseObject(result);
        ArrayList<String> list = new ArrayList<>();

        JSONArray subJson = jsonObject1.getJSONArray("rows");
        for (int i = 0; i <subJson.size() ; i++) {
            JSONObject jsonObject = subJson.getJSONObject(i);

            String orderNo = jsonObject.getString("ORDERNO");
//            System.out.println(orderNo);
            if (!StringUtils.isEmpty(orderNo)){

                list.add(orderNo);
            }

        }
        System.out.println(list);

        return list;
    }

    /**
     * 上传的json
     * @param orderId id
     * @return
     */
    private  String getJsonStringByOrderId(String orderId){
        OrderInfo orderInfo = orderInfoService.getById(orderId);

        Map<String, Object> orderFormMap = new HashMap<>();
        orderFormMap.put("orderFormId",orderInfo.getOrderFormId() == null ? "null" : orderInfo.getOrderFormId());
        orderFormMap.put("orderFormType", orderInfo.getOrderFormType() == null ? "null":orderInfo.getOrderFormType());
        orderFormMap.put("orderFormcreateTime", orderInfo.getOrderFormCreatetime() == null ? "null":orderInfo.getOrderFormCreatetime());
        orderFormMap.put("orderFormEndTime", orderInfo.getOrderFormEndTime() == null ? "null":orderInfo.getOrderFormEndTime());
        orderFormMap.put("startThing", orderInfo.getStartThing() == null ? "null":orderInfo.getStartThing());
        orderFormMap.put("startThinglat", orderInfo.getStartThingLat() == null ? "null":orderInfo.getStartThingLat());
        orderFormMap.put("startThinglng", orderInfo.getStartThingLng() == null ? "null":orderInfo.getStartThingLng());
        orderFormMap.put("endThing", orderInfo.getEndThing() == null ? "null":orderInfo.getEndThing());
        orderFormMap.put("endThinglat", orderInfo.getEndThingLat() == null ? "null":orderInfo.getEndThingLat());
        orderFormMap.put("endThinglng", orderInfo.getEndThingLng() == null ? "null":orderInfo.getEndThingLng());
        orderFormMap.put("orderFormHandle", orderInfo.getOrderFormHandle().trim().equals("订单生效") ? "8":"13");
        orderFormMap.put("orderFormEstimate", orderInfo.getOrderFormEstimate() == null ? "null":orderInfo.getOrderFormEstimate());
        orderFormMap.put("orderFormEvaluation", orderInfo.getOrderFormEvaluation() == null ? "null":orderInfo.getOrderFormEvaluation());
        orderFormMap.put("materialName", orderInfo.getMaterialName() == null ? "null":orderInfo.getMaterialName());
        orderFormMap.put("materialNmber", orderInfo.getMaterialNumber() == null ? "null":orderInfo.getMaterialNumber());

        Map<String, Object> userMap = new HashMap<>();
        userMap.put("DESCRIPTION", orderInfo.getCustomerUserName() == null ? "null":orderInfo.getCustomerUserName());
        userMap.put("EMAIL", orderInfo.getCustomerUserEmail() == null ? "null":orderInfo.getCustomerUserEmail());
        userMap.put("MOBILE", orderInfo.getCustomerUserMobile() == null ? "null":orderInfo.getCustomerUserMobile());

        orderFormMap.put("customerUser", userMap);

        Map<String, Object> carrierMap = new HashMap<>();
        carrierMap.put("DESCRIPTION", orderInfo.getCarrierUserName() == null ? "null":orderInfo.getCarrierUserName());
        carrierMap.put("EMAIL", orderInfo.getCarrierUserEmail() == null ? "null":orderInfo.getCarrierUserEmail());
        carrierMap.put("MOBILE", orderInfo.getCarrierUserMobile() == null ? "null":orderInfo.getCarrierUserMobile());

        orderFormMap.put("carrierUser", carrierMap);

        List<Map<String, Object>> billList = new ArrayList<>();

        // 根据oid查询所有的物流信息
        QueryWrapper<TransportInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("o_id", orderId);
        List<TransportInfo> transportInfoList = transportInfoService.list(wrapper);

        for (TransportInfo info : transportInfoList
        ) {
            Map<String, Object> temp = new HashMap<>();
            temp.put("waybillId", info.getWaybillId() == null ? "null":info.getWaybillId());
            temp.put("tare", info.getTare() == null ? "null":info.getTare());
            temp.put("netWeight", info.getNetWeight() == null ? "null":info.getNetWeight());
            temp.put("grossWeight", info.getGrossWeight() == null ? "null":info.getGrossWeight());
            String waybillStartTime = info.getWaybillStartTime();
            String time = "";
            if (!StringUtils.isEmpty(waybillStartTime)){
                String[] ts = waybillStartTime.split("T");
                String substring = ts[1].substring(0, 8);
                time = ts[0] + " " + substring;
            }
            temp.put("waybillStartTime", time);
            temp.put("waybillEndTime", info.getWaybillEndTime() == null ? "null":info.getWaybillEndTime());
            temp.put("waybillHandle", info.getWaybillHandle() == null ? "null":info.getWaybillHandle());
            temp.put("leadseal", info.getLeadseal() == null ? "null":info.getLeadseal());
            temp.put("wholeName", info.getWholeName() == null ? "null":info.getWholeName());

            Map<String, String> driverUserMap = new HashMap<>();
            driverUserMap.put("DESCRIPTION", info.getDriverUserName() == null ? "null":info.getDriverUserName());
            driverUserMap.put("EMAIL", info.getDriverUserEmail() == null ? "null":info.getDriverUserEmail());
            driverUserMap.put("MOBILE", info.getDriverUserMobile() == null ? "null":info.getDriverUserMobile());

            temp.put("driverUser", driverUserMap);
            // 添加
            billList.add(temp);
            // 清空
            temp = new HashMap<>();
            driverUserMap = new HashMap<>();
        }

        orderFormMap.put("waybill", billList);

        String json =  JSONObject.toJSONString(orderFormMap);
        System.out.println(json);
        return json;
    }
}

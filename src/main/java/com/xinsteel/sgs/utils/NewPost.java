package com.xinsteel.sgs.utils;

/**
 * 发送post请求
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class NewPost {
    public static void post(JSONObject json, String url) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault();
            System.out.println(json.toString());

            HttpPost httpPost = new HttpPost(url);
            httpPost.addHeader("Content-Type", "application/json;charset=UTF-8");

            // 解决中文乱码问题
            StringEntity stringEntity = new StringEntity(json.toString(), "UTF-8");
            stringEntity.setContentEncoding("UTF-8");

            httpPost.setEntity(stringEntity);

            System.out.println("Executing request " + httpPost.getRequestLine());

            ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
                @Override
                public String handleResponse(final HttpResponse response)
                        throws ClientProtocolException, IOException {//
                    int status = response.getStatusLine().getStatusCode();
                    if (status >= 200 && status < 300) {

                        HttpEntity entity = response.getEntity();

                        return entity != null ? EntityUtils.toString(entity) : null;
                    } else {
                        throw new ClientProtocolException(
                                "Unexpected response status: " + status);
                    }
                }
            };
            String responseBody = httpclient.execute(httpPost, responseHandler);
            System.out.println("----------------------------------------");
            System.out.println(responseBody);

        } catch (Exception e) {
            System.out.println(e);
        }


    }

    public static void main(String[] args) {
        JSONObject obj = new JSONObject();
        //obj.put("TMS_TOKEN", "5b8dd8ad5f8b42ca9e3653534f6104d7");
        String json="{\"orderFormHandle\":\"8\",\"customerUser\":{\"DESCRIPTION\":\"江西省高速公路投资集团材料有限公司\"},\"endThing\":\"萍乡省高速（江西高速公路）\",\"materialName\":\"光圆钢筋(HPB300)\",\"orderFormEstimate\":\"2020-12-25\",\"waybill\":[{\"waybillStartTime\":\"\",\"waybillId\":\"202012170103\",\"driverUser\":{}},{\"waybillStartTime\":\"\",\"waybillId\":\"202012170104\",\"driverUser\":{}},{\"waybillStartTime\":\"\",\"waybillId\":\"202012170105\",\"driverUser\":{}}],\"orderFormId\":\"W212020120601563\",\"startThinglng\":\"114.932823\",\"startThing\":\"新余钢铁集团有限公司\",\"carrierUser\":{},\"orderFormcreateTime\":\"2020-12-16 09:07:31\",\"orderFormEndTime\":\"2020-12-25\",\"startThinglat\":\"27.791332\"}\n";
        JSONObject jsonObject = JSONObject.parseObject(json);
        //obj.put("data",json);

        post(jsonObject, "http://iehsw5.natappfree.cc/TMS_SetOrder/setOrderFormInfoToEnd?TMS_TOKEN=5b8dd8ad5f8b42ca9e3653534f6104d7");
    }
}


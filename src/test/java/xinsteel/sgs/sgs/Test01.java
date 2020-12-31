package xinsteel.sgs.sgs;

import com.xinsteel.sgs.Application;
import com.xinsteel.sgs.utils.Jsoup_xml;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.soap.SOAPConstants;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;


@SpringBootTest(classes = {Application.class})
public class Test01 {

    @Test
    public void test09(){
        String url="http://124.70.153.219:8102/TMS_SetOrder/loginUlog?username=xgjt&password=1234qwer";
        String s=com.xinsteel.sgs.utils.HttpRequest.sendPost(url,"");
        System.out.println(s);
    }

    @Test
    public void test08(){
        CloseableHttpAsyncClient httpClient = HttpAsyncClients.createDefault();
        try {
            String urlWsdl = "http://172.16.4.68/MES_WS/Service.asmx?wsdl";
            String targetNamespace = "http://tempuri.org/";
            String soap = "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:tem=\"http://tempuri.org/\">\n" +
                    "   <soap:Header/>\n" +
                    "   <soap:Body>\n" +
                    "      <tem:GetJhhBySjData_2>\n" +
                    "         <!--Optional:-->\n" +
                    "         <tem:cJhh>202008110158</tem:cJhh>\n" +
                    "      </tem:GetJhhBySjData_2>\n" +
                    "   </soap:Body>\n" +
                    "</soap:Envelope>";

            httpClient.start();
            HttpPost httpPost = new HttpPost(urlWsdl);
            httpPost.setHeader("Content-Type", "text/xml;charset=UTF-8");
            httpPost.setHeader("SOAPAction", targetNamespace + "GetJhhBySjData_2");
            InputStreamEntity entity = new InputStreamEntity(new ByteArrayInputStream(soap.getBytes()));
            httpPost.setEntity(entity);
            Future<HttpResponse> future = httpClient.execute(httpPost, null);
            HttpResponse response = future.get();
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String back = EntityUtils.toString(responseEntity);
                System.out.println("HttpAsyncClient返回soap：" + back);
//                System.out.println("HttpAsyncClient返回结果：" + parseResult(back));
            } else {
                System.out.println("HttpAsyncClient返回状态码：" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {httpClient.close();} catch ( IOException e) {e.printStackTrace();}
        }
    }

    /**
     * 解析结果
     * @param s
     * @return
     */
    private static String parseResult(String s) {
        String result = "";
        try {
            Reader file = new StringReader(s);
            SAXReader reader = new SAXReader();

            Map<String, String> map = new HashMap<String, String>();
            map.put("ns", "http://tempuri.org/");
            reader.getDocumentFactory().setXPathNamespaceURIs(map);
            Document dc = reader.read(file);
            result = dc.selectSingleNode("//ns:GetJhhBySjData_2Result").getText().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void  test07(){
        try {
            String billId = "202012250008";
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
            if (response.getStatusLine().getStatusCode() == org.apache.http.HttpStatus.SC_OK) {
                HttpEntity responseEntity = response.getEntity();
                String back = EntityUtils.toString(responseEntity);
                System.out.println("httpClient返回soap：" + back);
                Map<String, String> trMessage = Jsoup_xml.getTrMessage(back);
                if (trMessage != null){

                    for (String key: trMessage.keySet()
                    ) {
                        System.out.println(key+"===>"+trMessage.get(key));
                    }
                }
//                System.out.println("httpClient返回结果：" + parseResult(back));
            } else {
                System.out.println("HttpClinet返回状态码：" + response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    @Test
    public void test05(){
        try {
            String url = "http://172.16.4.68/MES_WS/Service.asmx?wsdl";
            String targetNamespace = "http://tempuri.org/";
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(url));
            call.setEncodingStyle("utf-8");
            //设置SOAPAction
            call.setUseSOAPAction(true);
            call.setSOAPActionURI("http://tempuri.org/GetJhhBySjData_2");
            //设置Soap协议版本
            call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
//            call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);
            call.addParameter(new QName(targetNamespace, "cJhh"), XMLType.XSD_STRING, ParameterMode.IN);
            call.setOperationName(new QName(targetNamespace, "GetJhhBySjData_2"));
            String result = (String) call.invoke(new Object[]{"202012250001"});
            System.out.println("call结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test04() {
        try{
        // webserviceURL
                String service_url = "http://172.16.4.68/MES_WS/Service.asmx?wsdl";
        Service service = new Service();
        Call call = (Call) service.createCall();
        call.setTargetEndpointAddress(new java.net.URL(service_url));
// 设置要调用的方法
// http://intelink.net/是wsdl中definitions根节点的targetNamespace属性值
        call.setOperationName(new QName("http://tempuri.org/","GetJhhBySjData_2"));
// 该方法需要的参数
        call.addParameter("cJhh", org.apache.axis.encoding.XMLType.XSD_STRING, javax.xml.rpc.ParameterMode.IN);
// 方法的返回值类型
        call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);
// call.setUseSOAPAction(true); //call.setSOAPActionURI("http://intelink.net/GetStrByJobno");
// 调用该方法, new Object[] { CustNo, passwd, Jobno}为参数列表
        String xmlStr = call.invoke(new Object[] { "202011010087" }).toString();
            System.out.println(xmlStr);
    } catch (Exception e) {
        e.printStackTrace();
    }

}


    @Test
    public void test03(){
        try {
            String endpoint = "http://172.16.4.68/MES_WS/Service.asmx?wsdl";
            // 直接引用远程的wsdl文件
            // 以下都是套路
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);
            call.setOperationName("GetJhhBySjData_2");// WSDL里面描述的接口名称
            call.addParameter("cJhh",
                    XMLType.XSD_STRING,
                    javax.xml.rpc.ParameterMode.IN);// 接口的参数
            call.setReturnType(org.apache.axis.encoding.XMLType.XSD_STRING);// 设置返回类型
            String temp = "202012250001";
            String result = (String) call.invoke(new Object[] { temp });
            // 给方法传递参数，并且调用方法
            System.out.println("result is " + result);
        } catch (Exception e) {
            System.err.println(e.toString());
        }

    }

    @Test
    public void test02(){
        //天气预报发布的的url
        String url = "http://172.16.4.68/MES_WS/Service.asmx";
        //对应的发布地址
        String namespace = "http://tempuri.org/";
        //提供的某个方法
        String methodName = "GetJhhBySjData_2";
        //soapAction  就是 namespace+methodName 注意中间有有个/
        String soapActionURI = "http://tempuri.org/GetJhhBySjData_2";
        //new一个服务
        Service service = new Service();
        Call call;
        try {
            //创建一个Call  然后设置一些信息
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(url);
            call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
            call.setEncodingStyle("utf-8");
            call.setUseSOAPAction(true);
            call.setSOAPActionURI(soapActionURI);
            call.setOperationName(new QName(namespace, methodName));
            //添加参数    byProvinceName 为参数名  XMLType.XSD_STRING 为参数类型   ParameterMode.IN 为入参的意思
            call.addParameter(new QName(namespace, "cJhh"),
                    XMLType.XSD_STRING,ParameterMode.IN);
            //返回的参数设置类型  XSD_STRING String类型
//            call.setReturnType(XMLType.XSD_STRING);
//            call.setReturnType(XMLType.);
            call.setUseSOAPAction(true);

            call.setReturnType(XMLType.SOAP_STRING);
            String no = "202012250001";


            //请注意代码不完整,直接调用必报错 ,call.invoke() 调用方法    new Object[]{}  参数存放在{}中
            System.out.println( call.invoke(new Object[]{no}).toString());
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}


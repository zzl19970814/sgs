package xinsteel.sgs.sgs;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.soap.SOAP11Constants;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.axis.soap.SOAPConstants;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.junit.Test;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import java.net.URL;



public class Test02 {
    private static String url = "http://172.16.4.68/MES_WS/Service.asmx?wsdl";
    private static String targetNamespace = "http://tempuri.org/";
    /**
     * call直接调用
     * @param param
     */
    public static void call(String param) {
        try {
            Service service = new Service();
            Call call = (Call) service.createCall();
            call.setTargetEndpointAddress(new URL(url));
            call.setEncodingStyle("utf-8");
            //设置SOAPAction
            call.setUseSOAPAction(true);
            call.setSOAPActionURI("http://tempuri.org/GetJhhBySjData_2");
            //设置Soap协议版本
            call.setSOAPVersion(SOAPConstants.SOAP11_CONSTANTS);
            //call.setSOAPVersion(SOAPConstants.SOAP12_CONSTANTS);

            call.addParameter(new QName(targetNamespace, "cJhh"), XMLType.XSD_STRING, ParameterMode.IN);
            call.setOperationName(new QName(targetNamespace, "GetJhhBySjData_2"));
            String result = (String) call.invoke(new Object[]{param});
            System.out.println("call结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * axiom方式
     * @param param
     */
    public static void axiom(String param) {
        try {
            ServiceClient client = new ServiceClient();
            Options options = client.getOptions();
            EndpointReference endpointReference = new EndpointReference(url);
            options.setTo(endpointReference);
            // 设置SOAPAction
            options.setAction("http://tempuri.org/GetJhhBySjData_2");
            // 设置soap版本
            options.setSoapVersionURI(SOAP11Constants.SOAP_ENVELOPE_NAMESPACE_URI);
            //options.setSoapVersionURI(SOAP12Constants.SOAP_ENVELOPE_NAMESPACE_URI);

            OMFactory factory = OMAbstractFactory.getOMFactory();
            OMNamespace namespace = factory.createOMNamespace(targetNamespace, "");
            OMElement method = factory.createOMElement("GetJhhBySjData_2", namespace);
            OMElement value = factory.createOMElement("cJhh", namespace);
            value.addChild(factory.createOMText(value, param));
            method.addChild(value);
            method.build();

            OMElement result = client.sendReceive(method);
            System.out.println(result);
            System.out.println("axiom结果:" + result.getFirstElement().getText());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
//        call("202008110158");
        axiom("202008110158");
    }

    @Test
    public void test333() {

    }
}

package json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author yxzheng
 * @Date 2020/4/22
 */
public class TestDemo {
    public static String string = "1";

    @Test
    public void test(){

        String paramStr = "{\n" +
                "    \"SINORESPONSE\": {\n" +
                "        \"RETURNINFO\": {\n" +
                "            \"SERVICENAME\": \""+"ORDER_STATUS_QUERY"+"\",\n" +
                "            \"RESULTMESSAGE\": \""+"成功"+"\",\n" +
                "            \"RESULTCODE\": \""+"0"+"\"\n" +
                "        },\n" +
                "        \"SERVICEDATA\": [{\n" +
                "            \"rownum\": \"\",\n" +
                "            \"dfosStatusTime\": \"\",\n" +
                "            \"_class\": \""+"xxx"+"\",\n" +
                "            \"dfosBlNo\": \"\",\n" +
                "            \"dfosOrderId\": \""+"010152640987"+"\",\n" +
                "            \"rowState\": \"\",\n" +
                "            \"dfosCustomerBkNo\": \""+"004502121263"+"\",\n" +
                "            \"dfosCustomsDeclaration\":\"\"\n" +
                "        }]\n" +
                "    }\n" +
                "}";
        JSONObject data = JSONObject.parseObject(paramStr);
        System.out.println(data.get("xxx"));
        JSONObject serviceData = new JSONObject();
        Map sinoresponse = MapUtils.getMap(data, "SINORESPONSE");
        Map returninfo = MapUtils.getMap(sinoresponse, "RETURNINFO");
        JSONArray servicedata = (JSONArray) MapUtils.getObject(sinoresponse, "SERVICEDATA");

        if ("0".equals(MapUtils.getString(returninfo, "RESULTCODE"))
                && CollectionUtils.isNotEmpty(servicedata)) {
            servicedata.getJSONObject(0).put("xxx",11);
        }
        // 对JSONObject内部层进行修改，外层所指相应改变
        System.out.println(sinoresponse);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(
                "<html>\n" +
                "<body>\n" +
                "    <p>你好，我是小郑，我正在测试发送邮件。</p>\n" +
                "    <p><img src=\"cid:hello\"></p>\n" +
                "</body>\n" +
                "</html>");
        String str = "收货人-企业名称&收货人-联系人&收货人-联系人&111111&136@qq.com&&";
        List<String> list = Arrays.asList(StringUtils.split(str, "&"));
        System.out.println(list);

        string = "2";
        System.out.println(string);
    }

}

package wechat

import chanjarster.weixin.util.http.Utf8ResponseHandler
import grails.transaction.Transactional
import org.apache.http.client.methods.CloseableHttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.CloseableHttpClient
import org.apache.http.impl.client.HttpClients

@Transactional
class SmsService {

    public static final CloseableHttpClient httpclient = HttpClients.createDefault();
    public static grailsApplication=new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()


    public   String send(def number,def msg){
        // number="13928080276"

        def user=grailsApplication.config.sms.mobset.user
        def passwd=grailsApplication.config.sms.mobset.passwd

        msg=java.net.URLEncoder.encode(msg,"GB2312")
        def url="http://web.mobset.com/SDK/Sms_Send.asp?CorpID=121505&LoginName=${user}&Passwd=${passwd}&send_no=${number}&msg=${msg}"


        HttpGet httpGet = new HttpGet(url)
        CloseableHttpResponse response = httpclient.execute(httpGet);
        String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
    }

    /**
     * 创建指定数量的随机字符串
     * @param numberFlag 是否是数字
     * @param length
     * @return
     *
     */
    public static String createRandom(boolean numberFlag, int length) {
        String retStr = "";
        String strTable = numberFlag ? "1234567890" : "1234567890abcdefghijkmnpqrstuvwxyz";
        int len = strTable.length();
        boolean bDone = true;
        while (bDone) {
            retStr = "";
            int count = 0;
            for (int i = 0; i < length; i++) {
                double dblR = Math.random() * len;
                int intR = (int) Math.floor(dblR);
                char c = strTable.charAt(intR);
                if (('0' <= c) && (c <= '9')) {
                    count++;
                }
                retStr += strTable.charAt(intR);
            }
            if (count >= 2) {
                bDone = false;
            }
        }

        return retStr;

    }
}

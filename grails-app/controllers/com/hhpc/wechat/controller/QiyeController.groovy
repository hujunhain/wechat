package com.hhpc.wechat.controller

import me.chanjar.weixin.cp.api.WxCpMessageHandler
import me.chanjar.weixin.cp.api.WxCpMessageRouter
import me.chanjar.weixin.cp.bean.WxCpXmlMessage
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil
import me.chanjar.weixin.mp.bean.WxMpXmlMessage
import org.apache.commons.lang3.StringUtils

import javax.servlet.http.HttpServletResponse
import javax.xml.bind.JAXBException

class QiyeController {
    def wxCpConfigStorage //WxCpConfigStorage
    def application
    def wxCpService


    def index() {
        println "cPConfig::"+wxCpConfigStorage
        println "application:"+application?.config?.qiye?.corpId
        println "grailsapplicationXXXX:"+wxCpService?.wxCpConfigStorage
       // render "abc"+wxCpConfigStorage+" <br>wxCpService:"+wxCpService

       //todo 一次
        WxCpMessageHandler handler = new WxCpMessageHandler() {
            @Override public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context) {
                WxCpXmlOutTextMessage m = WxCpXmlOutMessage
                        .TEXT()
                        .content("测试加密消息")
                        .fromUser(wxMessage.getToUserName())
                        .toUser(wxMessage.getFromUserName())
                        .build();
                return m;
            }
        };

       def wxCpMessageRouter = new WxCpMessageRouter();
        wxCpMessageRouter
                .rule()
                .async(false)
                .content("哈哈") // 拦截内容为“哈哈”的消息
                .handler(handler)
                .end();

    // end todo


        String msgSignature = params["msg_signature"];
        String nonce = params["nonce"];
        String timestamp = params["timestamp"];
        String echostr = params["echostr"];

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        if (StringUtils.isNotBlank(echostr)) {
            if (!wxCpService.checkSignature(msgSignature, timestamp, nonce, echostr)) {
                // 消息签名不正确，说明不是公众平台发过来的消息
                response.getWriter().println("非法请求");
                return;
            }
            WxCpCryptUtil cryptUtil = new WxCpCryptUtil(wxCpConfigStorage);
            String plainText = cryptUtil.decrypt(echostr);
            // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(plainText);
            return;
        }


        WxCpXmlMessage inMessage = WxCpXmlMessage.fromEncryptedXml(groovy.xml.XmlUtil.serialize(request.XML), wxCpConfigStorage, timestamp, nonce, msgSignature);
       // WxMpXmlMessage message = WxMpXmlMessage.fromXml(groovy.xml.XmlUtil.serialize(request.XML));

        WxCpXmlOutMessage outMessage = wxCpMessageRouter.route(inMessage);

        if (outMessage != null) {
           // response.getWriter().write(outMessage.toEncryptedXml(wxCpConfigStorage));

            render outMessage.toEncryptedXml(wxCpConfigStorage)
        }

        return;
    }
}

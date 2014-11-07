package com.hhpc.wechat.controller.cp

import com.hhpc.wechat.domain.Area
import com.hhpc.wechat.domain.MergerSms
import com.hhpc.wechat.domain.SendSms
import com.hhpc.wechat.domain.SmsStatus
import me.chanjar.weixin.common.api.WxConsts
import me.chanjar.weixin.cp.api.WxCpMessageHandler
import me.chanjar.weixin.cp.api.WxCpMessageRouter
import me.chanjar.weixin.cp.bean.WxCpUser
import me.chanjar.weixin.cp.bean.WxCpXmlMessage
import me.chanjar.weixin.cp.bean.WxCpXmlOutMessage
import me.chanjar.weixin.cp.bean.WxCpXmlOutTextMessage
import me.chanjar.weixin.cp.util.crypto.WxCpCryptUtil
import me.chanjar.weixin.mp.api.WxMpMessageHandler
import me.chanjar.weixin.mp.bean.WxMpXmlMessage
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage
import me.chanjar.weixin.mp.bean.result.WxMpUser
import org.apache.commons.lang3.StringUtils

import javax.servlet.http.HttpServletResponse
import javax.xml.bind.JAXBException

class CuXiaoController {
    def wxCpConfigStorage //WxCpConfigStorage
    def application
    def wxCpService

    def userService
    def SmsService

    def parseSmsService


    def index() {
        println "cPConfig::"+wxCpConfigStorage
        ß

        println "corpId:"+wxCpService?.wxCpConfigStorage?.corpId
        println "aesKey:"+wxCpService?.wxCpConfigStorage?.aesKey
        println "token:"+wxCpService?.wxCpConfigStorage?.token
        println "agentId:"+wxCpService?.wxCpConfigStorage?.agentId
        println "accessToken:"+wxCpService?.wxCpConfigStorage?.accessToken

       // render "abc"+wxCpConfigStorage+" <br>wxCpService:"+wxCpService

       //todo 一次
        WxCpMessageHandler handler = new WxCpMessageHandler() {
            @Override public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context) {
                WxCpXmlOutTextMessage m = WxCpXmlOutMessage
                        .TEXT()
                        .content("测试加密消息:"+wxMessage.content)
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

        StringBuffer sb = new StringBuffer();

        wxCpMessageRouter
                .rule().async(false).rContent("\\d{1,2}").handler(new WxMsgMessageHandler()).end()
                .rule().async(false).rContent("1\\d{10}.*").msgType(WxConsts.XML_MSG_TEXT).handler(new WxWangMessageHandler()).end()
                .rule().async(false).event(WxConsts.EVT_SUBSCRIBE).handler(new WxSubMessageHandler()).end()
                .rule().async(false).event(WxConsts.EVT_UNSUBSCRIBE).handler(new WxUnSubMessageHandler()).end()
                .rule().async(false).handler(new WxEchoMessageHandler(sb, WxConsts.XML_MSG_TEXT)).end()
        //   router.rule().async(false).msgType(WxConsts.XML_MSG_TEXT).handler(handler(sb, WxConsts.XML_MSG_TEXT)).end()



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



    public class WxWangMessageHandler  implements WxCpMessageHandler{

        @Override
        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context) {

            println "进入短信发送适配器。。。。。。。"

            def openid= wxMessage.fromUserName
            def sendNum=wxMessage.getContent()
            def msg="你还不能发送手机短信！"
            if("ok_buslMPDuu-h6amU0XGX49f_Qc"==openid||"ok_busih96pQuC0C1iUuh2KC_iA0"==openid) {
                //获取 权限

                def sms = "拨动祝福的琴弦，为您唱出最美妙的生日歌，点亮幸福的烛光，在您生日之际，（青岛啤酒）华海鹏城衷心的祝您生日快乐，身体健康！"

                if(sendNum.length()>11){
                    sms= sendNum.substring(11)
                    sendNum= sendNum.substring(0,11)
                }
                def smsId = smsService.send(sendNum, sms)

                SendSms sendSms=new SendSms()
                sendSms.openid=openid
                sendSms.msg=sms
                sendSms.smsId=smsId

                sendSms.save()

                println "smsId    :"+smsId
                // def status=smsService.status(smsId)
                msg="短信已经发送"+"("+sendNum+")"
            }


            WxCpXmlOutTextMessage m = WxCpXmlOutMessage.TEXT()
             .content(msg)
            .fromUser(wxMessage.toUserName)
            .toUser(wxMessage.fromUserName)
            .build();

            return m;
        }
    }
    public class WxMsgMessageHandler implements WxCpMessageHandler{

        @Override
        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context) {

            println "报数短信查询适配器。。。。。。。"

            def openid= wxMessage.fromUserName
            def msgContent=wxMessage.getContent().toInteger()


            //获取 权限

            def msgList=  MergerSms.list( [max: msgContent, offset: 0, sort: "id", order: "desc"])
            def msg=""
            msgList.each {
                msg+= it.message+"\n"
            }

            WxCpXmlOutTextMessage m = WxCpXmlOutMessage
                    .TEXT()
               .content(msg)
             .fromUser(wxMessage.toUserName)
             .toUser(wxMessage.fromUserName)
            .build();


            return m;
        }
    }
    /*
    subscribe(订阅)
     */
    public  class WxSubMessageHandler implements WxCpMessageHandler {
        @Override
        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context) {

            println "进入订阅微信适配器。。。。。。。"

            def fromUserName= wxMessage.fromUserName
            WxCpUser userinfo=WxCpUser.findByUserId(fromUserName)


            def wxuserinfo=wxCpService.userGet(fromUserName)
            if(!userinfo)userinfo=wxuserinfo
            else{
                userinfo.userId=wxuserinfo.userId
                userinfo.name=wxuserinfo.name
                userinfo.departIds=wxuserinfo.departIds
                userinfo.email=wxuserinfo.email
                userinfo.gender=wxuserinfo.gender
                userinfo.mobile=wxuserinfo.mobile
                userinfo.position=wxuserinfo.position
                userinfo.tel=wxuserinfo.tel

            }
            println "ss"
            println "ss"
            println "s"
            println "fromUserName:"+fromUserName+" userinfo.id"+userinfo.id



           // userinfo.createDate=new Date();

            if (!userinfo.hasErrors() && userinfo.save()) {
                println "save ok????"
            }
            else {
                userinfo.errors.allErrors.each {
                    println "save userinfo error:"+it
                }
            }

            userService.saveCpUser(userinfo)

            WxCpXmlOutTextMessage m = WxCpXmlOutMessage.TEXT()
            .content(userinfo.name+"欢迎关注华海鹏城")
             .fromUser(wxMessage.toUserName)
            .toUser(wxMessage.fromUserName)
            .build()
            return m;
        }
    }
    /*
  subscribe(取消订阅)
   */
    public  class WxUnSubMessageHandler implements WxCpMessageHandler {
        @Override
        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context) {

            def fromUserName= wxMessage.fromUserName

            //   def userinfo=weChatService.userInfo(fromUserName,'zh')
            println "进入取消订阅适配器。。。。。。。"


            WxCpUser userinfo=WxCpUser.findByUserId(fromUserName)
         //   println "UU"+userinfo.subscribe
          //  userinfo.setSubscribe(false)
            println("un sub%%******:::"+fromUserName+" id:"+userinfo.id)
            userService.saveCpUser(userinfo)
            if (!userinfo.hasErrors() && userinfo.save()) {
                println "save ok????"
            }
            else {
                userinfo.errors.allErrors.each {
                    println "save userinfo error:"+it
                }
            }

           // println "tt::::::::"+userinfo.subscribe
            WxCpXmlOutTextMessage m = WxCpXmlOutMessage.TEXT()
            .content("你发送的消息：subscribe"+userinfo.name)
            .fromUser(wxMessage.toUserName)
            .toUser(wxMessage.fromUserName)
            .build()
            return m;
        }
    }
    public  class WxEchoMessageHandler implements WxCpMessageHandler {

        private StringBuffer sb;
        private String echoStr;

        public WxEchoMessageHandler(StringBuffer sb, String echoStr) {
            this.sb = sb;
            this.echoStr = echoStr;
        }

        @Override
        public WxCpXmlOutMessage handle(WxCpXmlMessage wxMessage, Map<String, Object> context) {

            println "进入消息接收显示适配器。。。。。。。"

            sb.append(this.echoStr).append(',').append("count:"+wxMessage.getContent()+" touser:"+wxMessage.getToUserName());

            println "XXXXXXXXX"

            wxMessage.save();

            def msgContent=wxMessage.getContent()

            def resultSms=msgContent


            if(msgContent)msgContent=msgContent.replaceAll('，',',').replaceAll('＃','#').replaceAll('﹟','#').replaceAll('【','[').replaceAll('】',']').replace('［','[').replace('］',']').replace('「','[').replace('」',']').replaceAll('　', "").replaceAll("／","/").replaceAll(/\s/,"").replaceAll("\\(\\d/\\d\\)","").replaceAll('（','(').replaceAll('）',')').replaceAll(' ','').toUpperCase()

            println "msgContent:"+msgContent
            def     findRemark= msgContent =~/^([A-Z]{1,2})(\d{4}).*#$/  // 字母开头日期 ＃结尾
            if(findRemark.matches())
            {
                println "find@@@@@@@@@@@@@@@@@@@@@@@@@@"
                MergerSms mergerSms=new MergerSms();
                def mergerStatus=SmsStatus.findByName("合并成功");
                def  finder2_=msgContent =~ /^([A-Z]{1,2}).*/  //匹配以一个字母后面四个数字开始，以名字( 一到五个字符) # 结尾
                if(finder2_.find()) {
                    //    println "/匹配日期数字之前的非数字字符:"+finder2_.group(1)
                    mergerSms.area = Area.findBySmsCode(finder2_.group(1));

                }
                mergerSms.message=msgContent
                mergerSms.date=new Date()
                mergerSms.number=wxMessage.fromUserName
                mergerSms.infos="接收成功"
                mergerSms.status=mergerStatus
                mergerSms.wxMessageId=wxMessage.id

                if (!mergerSms.hasErrors() && mergerSms.save()) {
                    //println "save ok!"
                    try {
                        resultSms= parseSmsService.parseSms(mergerSms)
                    }catch (Exception e){
                        resultSms=e.message
                    }
                }
                else {
                    mergerSms.errors.allErrors.each {
                        println "save mergerSms error:"+it
                    }
                }


            }
            else{
                println "not find smscont"
            }

            def msg=resultSms?"你发送的消息："+resultSms:""
            WxCpXmlOutTextMessage m = WxCpXmlOutMessage
                    .TEXT()
            .content(msg)
            .fromUser(wxMessage.toUserName)
            .toUser(wxMessage.fromUserName)
            .build()
            return m;
        }
    }
}

package com.hhpc.wechat.controller

import chanjarster.weixin.api.WxConsts
import chanjarster.weixin.api.WxMessageHandler
import chanjarster.weixin.api.WxMessageRouter
import chanjarster.weixin.bean.WxMenu
import chanjarster.weixin.bean.WxXmlMessage
import chanjarster.weixin.bean.WxXmlOutMessage
import chanjarster.weixin.bean.WxXmlOutTextMessage
import chanjarster.weixin.bean.result.WxUser
import com.hhpc.wechat.domain.Area
import com.hhpc.wechat.domain.MergerSms
import com.hhpc.wechat.domain.SmsStatus

import javax.persistence.*

class WechatController {



    def weChatService
    def parseSmsService


   def indexX(){

       WxXmlMessage getmsg= WxXmlMessage.get(1)

       println "*****%%#@@%%%%% msg:"+getmsg.id+" cont:"+getmsg.content
       WxUser user=WxUser.get(1)
       println "userId:"+user.id+" user openid"+user.openid
       println "  "+WxUser.findByOpenid(user.openid)

       render("abc"+getmsg)
   }




    String menus() {
        weChatService.menuDelete();

        WxMenu menu = new WxMenu();
        WxMenu.WxMenuButton button1 = new WxMenu.WxMenuButton();
        ;
        button1.setName("发图");


        WxMenu.WxMenuButton button2 = new WxMenu.WxMenuButton();
        //   button2.setType("click");
        button2.setName("扫码");
        //   button2.setKey("V1001_TODAY_SINGER");

        WxMenu.WxMenuButton button3 = new WxMenu.WxMenuButton();
        button3.setName("菜单");

        menu.getButton().add(button1);
        menu.getButton().add(button2);
        menu.getButton().add(button3);

        WxMenu.WxMenuButton button11 = new WxMenu.WxMenuButton();
        button11.setType("pic_sysphoto");
        button11.setName("系统拍照发图");
        button11.setKey("rselfmenu_1_0");


        WxMenu.WxMenuButton button12 = new WxMenu.WxMenuButton();
        button12.setType("pic_photo_or_album");
        button12.setName("拍照或者相册发图");
        button12.setKey("rselfmenu_1_2");

        WxMenu.WxMenuButton button13 = new WxMenu.WxMenuButton();
        button13.setType("pic_weixin");
        button13.setName("微信相册发图");
        button13.setKey("rselfmenu_1_3");

        button1.getSub_button().add(button11);
        button1.getSub_button().add(button12);
        button1.getSub_button().add(button13);


        WxMenu.WxMenuButton button21 = new WxMenu.WxMenuButton();
        button21.setType("scancode_waitmsg");
        button21.setName("扫码带提示");
        button21.setKey("rselfmenu_2_1");

        WxMenu.WxMenuButton button22 = new WxMenu.WxMenuButton();
        button22.setType("scancode_push");
        button22.setName("扫码推事件");
        button22.setKey("rselfmenu_2_2");



        button2.getSub_button().add(button21);
        button2.getSub_button().add(button22);
        button2.getSub_button().add(button13);

        WxMenu.WxMenuButton button30 = new WxMenu.WxMenuButton();
        button30.setType("view");
        button30.setName("注册");
        button30.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=${weChatService.wxConfig.appId}&redirect_uri=http://${weChatService.serverIp}/user/create&response_type=code&scope=snsapi_base&state=1#wechat_redirect");

        println button30.url

        WxMenu.WxMenuButton button31 = new WxMenu.WxMenuButton();
        button31.setType("view");
        button31.setName("搜索");
        button31.setUrl("http://www.soso.com/");

        WxMenu.WxMenuButton button32 = new WxMenu.WxMenuButton();
        button32.setType("view");
        button32.setName("视频");
        button32.setUrl("http://v.qq.com/");

        WxMenu.WxMenuButton button33 = new WxMenu.WxMenuButton();
        button33.setType("click");
        button33.setName("赞一下我们");
        button33.setKey("V1001_GOOD");

        button3.getSub_button().add(button30);
        button3.getSub_button().add(button31);
        button3.getSub_button().add(button32);
        // button3.getSub_button().add(button33);

        weChatService.menuCreate(menu)
        println "munu****************"
        render weChatService.menuGet();
    }

    /**
     * 微信消息的处理
     *
     * @param request
     * @param out
     * @throws IOException
     */
    def  index() {
        /* 消息的接收、处理、响应 */

        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        if(request.get){
            redirect(action: "valid", params: params)
        }
        else {
            WxMessageRouter router = new WxMessageRouter();
            StringBuffer sb = new StringBuffer();

            router
                    .rule().async(false).event(WxConsts.EVT_SUBSCRIBE).handler(new WxSubMessageHandler()).end()
                    .rule().async(false).event(WxConsts.EVT_UNSUBSCRIBE).handler(new WxUnSubMessageHandler()).end()
                    .rule().async(false).handler(new WxEchoMessageHandler(sb, WxConsts.XML_MSG_TEXT)).end()
            //   router.rule().async(false).msgType(WxConsts.XML_MSG_TEXT).handler(handler(sb, WxConsts.XML_MSG_TEXT)).end()

            WxXmlMessage message = WxXmlMessage.fromXml(groovy.xml.XmlUtil.serialize(request.XML));
            // WxXmlMessage message = WxXmlMessage.fromXml(request.getInputStream());
            WxXmlOutMessage outmsg = router.route(message);



            println "message:" + message.toUserName + " cont:" + message.content + " time:" + message.createTime
            println "outmsg:" + outmsg?.toXml()
            println "sb:" + sb
            // 响应消息
            //   PrintWriter out = response.getWriter();
            //   out.print( outmsg.toXml());
            //  out.close();
            render outmsg?.toXml()
            ///  String openid =message.fromUserName
            //  WxCustomMessage messages = WxCustomMessage.TEXT().toUser(openid).content("Hello World").build();
            //   weChatService.customMessageSend(messages);

        }
    }

    //微信公众平台验证url是否有效使用的接口
    def  valid(){

        String echostr = request.getParameter("echostr");
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");

        println "timestamp:"+timestamp

        println "signature:"+signature
        println "nonce aa:"+nonce
        if (weChatService.checkSignature(timestamp, nonce, signature)) {
            // 消息合法消息验证不合法
            println "消息合法"
            render echostr;
        }
        else {
            println "消息不合法"
            render 'erorer'
        }

    }
    /*
    subscribe(订阅)
     */
    public  class WxSubMessageHandler implements WxMessageHandler {
        @Override
        public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context) {

            def fromUserName= wxMessage.fromUserName
            def userinfo=weChatService.userInfo(fromUserName,'zh')
            userinfo.createDate=new Date();
               if(!WxUser.findByOpenid(userinfo.openid)) {
                   userinfo.createDate=new Date()
                   userinfo.save()
               }
            userinfo.regionDate=new Date()
            userinfo.update();

            WxXmlOutTextMessage m = new WxXmlOutTextMessage();
            m.setContent("你发送的消息：subscribe"+userinfo.nickname);
            m.setCreateTime(1122l);
            m.setFromUserName(wxMessage.toUserName);
            m.setToUserName(wxMessage.fromUserName);
            return m;
        }
    }
    /*
  subscribe(取消订阅)
   */
    public  class WxUnSubMessageHandler implements WxMessageHandler {
        @Override
        public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context) {

            def fromUserName= wxMessage.fromUserName

         //   def userinfo=weChatService.userInfo(fromUserName,'zh')

           def userinfo=WxUser.findByOpenid(fromUserName)
            userinfo.subscribe=2

            userinfo.update();

            WxXmlOutTextMessage m = new WxXmlOutTextMessage();
            m.setContent("你发送的消息：subscribe"+userinfo.nickname);
            m.setCreateTime(1122l);
            m.setFromUserName(wxMessage.toUserName);
            m.setToUserName(wxMessage.fromUserName);
            return m;
        }
    }
    public  class WxEchoMessageHandler implements WxMessageHandler {

        private StringBuffer sb;
        private String echoStr;

        public WxEchoMessageHandler(StringBuffer sb, String echoStr) {
            this.sb = sb;
            this.echoStr = echoStr;
        }

        @Override
        public WxXmlOutMessage handle(WxXmlMessage wxMessage, Map<String, Object> context) {
            sb.append(this.echoStr).append(',').append("count:"+wxMessage.getContent()+" touser:"+wxMessage.getToUserName());

            println "XXXXXXXXX"

            wxMessage.save();

           def resultSms

            def msgContent=wxMessage.getContent()
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


            WxXmlOutTextMessage m = new WxXmlOutTextMessage();


            m.setContent("你发送的消息："+resultSms);
            m.setCreateTime(1122l);
            m.setFromUserName(wxMessage.toUserName);
            m.setToUserName(wxMessage.fromUserName);
            return m;
        }
    }
}

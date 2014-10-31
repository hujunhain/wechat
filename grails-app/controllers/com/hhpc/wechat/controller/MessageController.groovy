package com.hhpc.wechat.controller

//import chanjarster.weixin.bean.WxXmlOutTextMessage
//import chanjarster.weixin.bean.result.WxUser
import com.hhpc.wechat.domain.MergerSms
import me.chanjar.weixin.mp.bean.result.WxMpUser

class MessageController {
    def parseSmsService
    def userService



    def list={
        def code=params['code']
        response.setCharacterEncoding("UTF-8");
        def openid= userService.getOpenidByCode(code)
        def userinfo= WxMpUser.findByOpenId(openid)

        def max=params.max?:10
        def offset=params.offset?:0

        //获取 权限

        def msgList=  MergerSms.list( [max: max, offset: offset, sort: "id", order: "desc"])

        [msgList:msgList]
    }
    def index() {
        MergerSms sms=MergerSms.get(107929);

        println "status.id:::::::::"+sms.status.id
        def resultSms="OK"
       try {
           parseSmsService.parseSms(sms)
       }catch (Exception e){
           resultSms=e.message
       }

        render(sms.area)
    }
}

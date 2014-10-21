package com.hhpc.wechat.controller

import chanjarster.weixin.bean.WxXmlOutTextMessage
import chanjarster.weixin.bean.result.WxUser
import com.hhpc.wechat.domain.MergerSms

class MessageController {
    def parseSmsService

    def list={
        def offset=params.int("offset",0)
        def code=params['code']
        response.setCharacterEncoding("UTF-8");
        def openid= userService.getOpenidByCode(code)
        def userinfo= WxUser.findByOpenid(openid)

        //获取 权限

        def msgList=  MergerSms.list( [max: msgContent, offset: 0, sort: "id", order: "desc"])

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

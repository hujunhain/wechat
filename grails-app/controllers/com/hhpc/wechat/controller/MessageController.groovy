package com.hhpc.wechat.controller

import com.hhpc.wechat.domain.MergerSms

class MessageController {
    def parseSmsService

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

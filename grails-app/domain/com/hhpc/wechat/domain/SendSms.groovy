package com.hhpc.wechat.domain

class SendSms {

    String msg
    String openid
    String smsId
    String createTime


    static mapping = {
        table 'wx_send_sms'

    }

    static constraints = {
    }

    public SendSms(){

        createTime=new Date()
    }
}

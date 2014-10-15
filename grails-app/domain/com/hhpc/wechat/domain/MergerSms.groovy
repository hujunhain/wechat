package com.hhpc.wechat.domain

import chanjarster.weixin.bean.WxXmlMessage

class MergerSms {
    Date date;//接收时间
    String message//短信内容
    String number//发送短信的号码
    String infos;//处理结果
    SmsStatus status;//状态
    int parseCount //解析次数
    Area area;//区域
    String remark // 备注
    Integer wxMessageId

    static hasMany = [sells: PosSell]

    static mapping = {
        sells lazy: false // lazily fetch the sells
    }

    public MergerSms() {
        date = new Date();
        parseCount = 0
//        def flag=TDIf.get(2)
        //        parseFlag= flag
    }

    static constraints = {
        message(maxSize: 900)
        remark(maxSize: 620, nullable: true)
        number(maxSize: 100);
        status(nullable: true)
        area(nullable: true)
        wxMessageId(nullable: true)

    }
}

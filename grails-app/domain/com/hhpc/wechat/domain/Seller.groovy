package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class Seller {
    String name;//促销姓名
    static belongsTo = PosInfo  //终端
    static hasMany = [posInfos: PosInfo]
    TDIf status;//在职状态
    String mobile // 手机号码
    Date createTime
   // MergerSms lastSms // 最新一条记录
    String postName //职位
    String deptName //区域部门
    Integer wxUserId //微信id

    static constraints = {
        mobile(nullable: true, maxSize: 50)
       // lastSms(nullable: true)
        wxUserId(nullable: true)
        postName(nullable: true)
        deptName(nullable: true)
    }

    public Seller() {
        createTime = new Date();

    }

    String toString() {
        name
    }

}
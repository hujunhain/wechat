package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class PosSell {
    SubPosInfo posInfo//场所
    Brand brand;//品牌
    RoomType roomType;// 类型
    int roomNum;  //数目
    Date sellDate;   //日期
    Seller seller; //促销
    MergerSms sms;//解析的短信
    Date createTime;

    static belongsTo = [MergerSms]

    static hasMany = [items: SellItem]
    static constraints = {
    }
    static mapping = {
        items lazy: false
        items cascade: "all-delete-orphan"//will auto delete in a belongsTo relationship
    }

    public PosSell() {
        createTime = new Date()
    }
}


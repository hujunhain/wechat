package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class Brand {
    String name;//品牌名称
    String corp //公司名称
    String ourFlag //0-我方，1-竞品
    String shortName;//品种最短名称(用于短信)

    AlcoholType alcoholType;//酒类型
    static def hasMany = [breeds: Breed] //多个品种
   // static def belongsTo = [corp: BrandCorp]//属于一个品牌

    String toString() {
        name
    }

    static constraints = {
        alcoholType(nullable: false)

    }
}


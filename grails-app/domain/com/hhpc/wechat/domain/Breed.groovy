package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class Breed {
    String name;//品种名称
    String alias // 别名、简称
    String shortName;//品种最短名称(用于短信)
    String code;//编码
    String sellType//销售类型

    static def belongsTo = [brand: Brand]//属于一个品牌

    AlcoholType type;//酒类型
    String standard; //规则

    Float specNum//规格(每个单位的个数)
    Unit unit;   //单位
    String imgPath //产品图片路径
    String remark //备注

   // Breed breed //不为null表示为礼品

    Integer seqNum = 0;//排序编码

    static constraints = {
        type(nullable: false)
        brand()

        name(maxSize: 60)
        code(maxSize: 8, nullable: true)
        alias(nullable: true)
        shortName(maxSize: 10, nullable: true)
        standard(maxSize: 18, nullable: true)
        sellType(inList: ["夜场", "商场"], maxSize: 8)
       // breed(nullable: true)
        unit(nullable: true)

        imgPath(nullable: true)
        remark(nullable: true)
        specNum(nullable: true)
        seqNum(nullable: true)

    }


    String toString() {
        sellType.equals("商场") ? code + "-" + name : alias
        //  if(standard) return standard+brand.name+
    }

    static mapping = {

        brand fetch: 'join'
        type fetch: 'join'
      //  unit fetch: 'join'
      //  sort "seqNum"
    }



    AlcoholType getType() {
        return type
    }
}
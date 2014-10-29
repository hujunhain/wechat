package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class SubArea {
    String name //片区名称
    String code //片区编码
    String remark //   描述

    User manager //  城市经理
    User promotion //促销主管
    User statUser;//统计 --四川外区片区统计员只能看到自己片区的，下单只能看到自己下过的

    static def belongsTo = [area: Area]//属于一个区域

    static mapping = {
        table 'z_hc_area'
       
    }


    static constraints = {
        code(nullable: true)
        manager(nullable: true)
        promotion(nullable: true)
        statUser(nullable: true)
        remark(nullable: true, maxSize: 200)
    }

    String toString() {
        name
    }
}

package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class Dealer {
    String name  // 名称
    String fullName//全称

    String code //经销商编码
    String beerCode;//啤酒编码
    String wineCode//红酒编码

    String phoneNum//联系电话
    String mobileNum//手机
    String bossName//联系人
    String faxNum //传真
   // DealerCash newCash //最新余额
    TDIf isCooperate //是否与我方合作

    User user //对应用户账户

    static belongsTo = Area //区域

    static def hasMany = [posinfos: PosInfo, addresses: DealerAddress, areas: Area]     //经销商可以有多个终端


    static constraints = {
        name(maxSize: 40)
        fullName(nullable: true)
        code(maxSize: 4)
        beerCode(maxSize: 4)
        wineCode(maxSize: 4)
        addresses()
        bossName(maxSize: 30)
        phoneNum(nullable: true)
        faxNum(nullable: true)
        mobileNum()

        isCooperate(nullable: false)
        user(nullable: true)

    }

    String toString() {
        name
    }


}

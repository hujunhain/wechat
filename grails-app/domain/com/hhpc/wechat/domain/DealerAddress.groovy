package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class DealerAddress {

    String address;//地址
    String des//位置描述

    static belongsTo = [dealer: Dealer]

    static constraints = {
        des(nullable: true)
    }

    String toString() {
        address
    }
}

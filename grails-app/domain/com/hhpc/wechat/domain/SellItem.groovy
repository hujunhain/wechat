package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class SellItem {
    Breed breed;
    Integer sellNum;//数目
    static belongsTo = [posSell: PosSell]
    static hasMany = [gifts: SellItemGift]//赠品

    static constraints = {
    }
    static mapping = {
       // table 'people'
        sellNum column:'count'

    }

    String toString() {
        "" + breed.name + ":" + count
    }
}

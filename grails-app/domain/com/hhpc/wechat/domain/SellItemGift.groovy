package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class SellItemGift {

    Breed breed;
    int sellNum;//数目
    static belongsTo = [sellItem: SellItem]
    static constraints = {
    }
    static mapping = {
        sellNum column:'count'

    }

    String toString() {
        "" + breed.name + ":" + count
    }
}

package com.hhpc.wechat.domain

/**
 * Created by Administrator on 2014/10/10.
 */
import grails.persistence.*

@Entity
class Unit {
    String name;
    String standard;//"规格型号"
    Integer specNum

    static constraints = {
        name(nullable: false)
        specNum(nullable: false)
    }

    String toString() {
        name
    }
}


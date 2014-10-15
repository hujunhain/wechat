package com.hhpc.wechat.domain

/**
 * Created by Administrator on 2014/10/10.
 */
import grails.persistence.*

@Entity
class AlcoholType {
    String name;//类型名称
    String toString() {
        name
    }
}

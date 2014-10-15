package com.hhpc.wechat.domain


/**
 * Created by Administrator on 2014/9/30.
 */
//是否字典表
/*
1--是
2--否
 */
import grails.persistence.*

@Entity
class TDIf {

    String name
    static constraints = {
        name(maxSize: 4)
    }
    static mapping = {
        table 'T_D_if'
    }

    String toString() {
        name
    }
}

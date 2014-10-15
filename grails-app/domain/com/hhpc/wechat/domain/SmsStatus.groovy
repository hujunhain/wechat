package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class SmsStatus {
    int flagId
    String name

    static constraints = {

        name(maxSize: 40)
    }

    String toString() {
        name
    }
}

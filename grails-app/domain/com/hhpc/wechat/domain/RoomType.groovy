package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class RoomType {
    String name
    String fullName
    static constraints = {
        fullName(maxSize: 12, nullable: true)
    }

    String toString() {
        name
    }
}

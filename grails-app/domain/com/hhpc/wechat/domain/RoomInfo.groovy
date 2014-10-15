package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class RoomInfo {
    transient domainModifyLogService
    static belongTo = {PosInfo}
    RoomType type;
    PosInfo posInfo;
    Integer num;     //数目
    Integer containNum;//容纳人数
    Integer minFee;//最低消费
    static constraints = {
        minFee(nullable: true)
        containNum(nullable: true)


    }

    static mapping = {
        //  version false
    }

    def  beforeDelete() {
        println "ddddddddddd isDirty:" + this.isDirty()
        //   domainModifyLogService.recordDirty(this)
    }
    def beforeUpdate() {
        //     version=version+1

        if (this.isDirty()) {

            def     controllerName = RequestContextHolder.currentRequestAttributes().params["controller"]
            def    actionName = RequestContextHolder.currentRequestAttributes().params["action"]
            //    domainModifyLogService.recordDirty(this)
            println "controllerName:"+controllerName +"actionName:"+actionName

        }
    }

    public String toString() {
        "" + type?.name + ":" + num
    }
}

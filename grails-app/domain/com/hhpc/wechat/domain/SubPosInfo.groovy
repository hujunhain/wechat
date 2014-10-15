package com.hhpc.wechat.domain

class SubPosInfo {
    static belongsTo = [posInfo: PosInfo]
    String name;
    TDIf isManager// 区域经理是否发送

    static constraints = {

        name(maxSize: 60)
        isManager(nullable: true)
    }
    static mapping = {

    }
    String toString() {
        name
    }
}

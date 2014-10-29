package com.hhpc.wechat.domain

import grails.persistence.*

@Entity
class Area {
    def static reportable = [:]


    String name //区域名称
    String code //区域编码
    String smsCode // 短信编码
    Integer seqNum = 0;//排序编码
  //  static def belongsTo = [company: Company]//属于一个公司
    static def hasMany = [subAreas: SubArea, dealers: Dealer] //多个片区
    User statUser;//总部统计
    User actUser;//财务部审核员

    static mapping = {
        // table 'AREA_TABLE'
        table 'z_hc_zone'
       // name column: 'area_Name'
       // id column: 'area_id'
       // smsCode column: 'sms_Code'

        sort "seqNum"
    }

    static constraints = {
        code(maxSize: 3)
        name(maxSize: 20)
        statUser(nullable: true)
        actUser(nullable: true)
        seqNum(nullable: true)
        smsCode(nullable: true, maxSize: 4)
    }

    public String getName() {
        name
    }

    String toString() {
        name
    }
}
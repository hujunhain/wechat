package com.hhpc.wechat.controller

class QiyeController {
    def cPConfig
    def application
    def index() {
        println "cPConfig::"+cPConfig
        println "application:"+application?.config.qiye.corpId
        render "abc"+cPConfig
    }
}

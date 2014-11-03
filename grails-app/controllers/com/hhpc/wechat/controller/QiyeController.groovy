package com.hhpc.wechat.controller

class QiyeController {
    def cPConfig
    def index() {
        println "cPConfig::"+cPConfig
        render "abc"+cPConfig
    }
}

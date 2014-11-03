package com.hhpc.wechat.controller

class QiyeController {
    def cPConfig
    def application
    def wxCpService
    def index() {
        println "cPConfig::"+cPConfig
        println "application:"+application?.config?.qiye?.corpId
        println "grailsapplicationXXXX:"+wxCpService.wxCpConfigStorage.corpId
        render "abc"+cPConfig
    }
}

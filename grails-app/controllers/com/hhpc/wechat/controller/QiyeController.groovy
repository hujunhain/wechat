package com.hhpc.wechat.controller

class QiyeController {
    def cPConfig
    def application
    def grailsapplication
    def index() {
        println "cPConfig::"+cPConfig
        println "application:"+application?.config?.qiye?.corpId
        println "grailsapplication:"+grailsapplication?.config?.qiye?.corpId
        render "abc"+cPConfig
    }
}

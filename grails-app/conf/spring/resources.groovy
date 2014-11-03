import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage


// Place your Spring DSL code here
beans = {
       def grailsApplication=new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()

    cPConfig(WxCpInMemoryConfigStorage) {
        corpId:grailsApplication.config.qiye.corpId;      // 设置微信企业号的appid
        corpSecret:grailsApplication.config.qiye.corpSecret;  // 设置微信企业号的app corpSecret
        agentId:grailsApplication.config.qiye.agentId;     // 设置微信企业号应用ID
        token:grailsApplication.config.qiye.token;       // 设置微信企业号应用的token
        aesKey:grailsApplication.config.qiye.aesKey;      // 设置微信企业号应用的EncodingAESKey
    }




}

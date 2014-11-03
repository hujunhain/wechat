import me.chanjar.weixin.cp.api.WxCpInMemoryConfigStorage
import me.chanjar.weixin.cp.api.WxCpServiceImpl


// Place your Spring DSL code here
beans = {
     //  def grailsApplication=new org.codehaus.groovy.grails.commons.DefaultGrailsApplication()

    cPConfig(WxCpInMemoryConfigStorage) {
        corpId=application.config.qiye.corpId;      // 设置微信企业号的appid
        corpSecret=application.config.qiye.corpSecret;  // 设置微信企业号的app corpSecret
        agentId=application.config.qiye.agentId;     // 设置微信企业号应用ID
        token=application.config.qiye.token;       // 设置微信企业号应用的token
        aesKey=application.config.qiye.aesKey;      // 设置微信企业号应用的EncodingAESKey
    }

     wxCpService(WxCpServiceImpl){
         wxCpConfigStorage:ref("cPConfig")
     }




}

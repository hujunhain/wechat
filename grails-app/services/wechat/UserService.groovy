package wechat

//import chanjarster.weixin.bean.result.WxUser
//import chanjarster.weixin.util.http.SimpleGetRequestExecutor
import com.hhpc.wechat.domain.Seller
import grails.converters.JSON
import grails.transaction.Transactional
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor
import me.chanjar.weixin.cp.bean.WxCpUser
import me.chanjar.weixin.mp.bean.result.WxMpUser
import org.codehaus.groovy.grails.web.json.JSONElement

@Transactional
class UserService {

    def weChatService
    def wxCpService

    def serviceMethod() {

    }
/*
服务号用
 */
   def getMpOpenidByCode(def code){

       def url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=${weChatService.wxConfig.appId}&secret=${weChatService.wxConfig.secret}&code=${code}&grant_type=authorization_code"
       println ""+url
       def jsonStr ="{openid:def}"
       if(code)jsonStr=weChatService.wxService.execute(new SimpleGetRequestExecutor(), url, null);
       JSONElement jsonObject = JSON.parse(jsonStr)

       def openid=jsonObject.get("openid")

       return openid
   }

    /*
    企业号用
     */
    def getCpUseridByCode(def code){
        def url= "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token=${wxCpService.wxCpConfigStorage.accessToken}&code=${code}&agentid=${wxCpService.wxCpConfigStorage.agentId}"
       // def url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=${wxCpService..wxConfig.appId}&secret=${weChatService.wxConfig.secret}&code=${code}&grant_type=authorization_code"
        println ""+url
        def jsonStr ="{openid:def}"
        if(code)jsonStr=wxCpService.execute(new SimpleGetRequestExecutor(), url, null);
        println "cp user jsonStr:"+jsonStr
        JSONElement jsonObject = JSON.parse(jsonStr)

        def userId=jsonObject.get("UserId")

        return userId
    }

    def saveCpUser(WxCpUser user) {


        WxCpUser.withTransaction { status ->
            println "status:" + status
            user.save();
            println "bbbbb  status:" + status
        }

    }

    def saveMpUser(WxMpUser user) {

//        Account.withTransaction { status ->
//                 def source = Account.get(params.from)
//                 def dest = Account.get(params.to)
//                def amount = params.amount.toInteger()
//                 if(source.active)
//                     {
//                         source.balance -= amount
//                         if(dest.active) {
//                             dest.amount += amount
//                         } else { status.setRollbackOnly() }
//                     }
//        }
        WxMpUser.withTransaction { status ->
            println "status:" + status
            user.save();
            println "bbbbb  status:" + status
        }

    }
}

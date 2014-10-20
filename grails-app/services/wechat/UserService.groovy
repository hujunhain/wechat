package wechat

import chanjarster.weixin.bean.result.WxUser
import com.hhpc.wechat.domain.Seller
import grails.transaction.Transactional

@Transactional
class UserService {

    def serviceMethod() {

    }



    def save(WxUser user) {

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
        WxUser.withTransaction { status ->
            println "status:" + status
            user.save();
            println "bbbbb  status:" + status
        }

    }
}

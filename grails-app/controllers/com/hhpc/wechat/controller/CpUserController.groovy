package com.hhpc.wechat.controller

import com.hhpc.wechat.domain.Seller
import com.hhpc.wechat.domain.TDIf
import grails.converters.JSON
import me.chanjar.weixin.cp.bean.WxCpUser
import me.chanjar.weixin.mp.bean.result.WxMpUser

class CpUserController {

    def weChatService
    def userService
    def SmsService
    def wxCpService



    def subscribe={


        def code=params['code']
        response.setCharacterEncoding("UTF-8");

        println "BB"
        println "BB"
        println "BB"
        println "BB"

        def userId= userService.getCpUseridByCode(code)

        // wxCpService.userAuthenticated(userId)
        def userinfo= WxCpUser.findByUserId(userId)

        if(!userinfo)
            userinfo=wxCpService.userGet(userId)

        def seller=Seller.findByWxUserId(userinfo.id)

        println "BBB userId:"+userId+" id:"+userinfo.id
        if(seller?.status?.name=="是"){

            render "你的微信号和手机号已经绑定且有效，不用再绑定注册了！！！"
        }

        [userinfo:userinfo];

    }

    def index() {
        def restRrl=""
      def deptList=  wxCpService.departGet()
        deptList.each{dept->
            def userList=wxCpService.departGetUsers(dept.id,true,null)
            println  "XXXX dept id::"+dept.id
            restRrl+="<br>"+ "dept id::"+dept.id+" name:"+dept.name
            userList.each{userInfo->
                println "userInfo userId::"+userInfo.userId+" userInfo weiXinId"+userInfo.weiXinId+"userInfo.name:"+userInfo.name
                restRrl+="<br>"+"userInfo userId::"+userInfo.userId+" userInfo weiXinId"+userInfo.weiXinId+"userInfo.name:"+userInfo.name
            }
        }

        render( restRrl)

    }
    def manager={
        def offset=params.int("offset",0)
        def code=params['code']
        response.setCharacterEncoding("UTF-8");
        def openid= userService.getCpUseridByCode(code)
        def userinfo= WxCpUser.findByUserId(openid)
        //findAllByWxUserIdIsNotNull
        def sellerList=Seller.list( [max: 10, offset: offset, sort: "id", order: "desc"])
        println "sellerList:"+sellerList.size()
        def sellerCount=10
        [sellerList:sellerList,sellerCount:sellerCount]
    }
    def sendCode={
        //render {abc:'123'} as JSON

        println "^^^^^^sendCodesendCodesendCodesendCodesendCodesendCode&&^^^^^"
        println "params       :"+params

        // def userinfo=  WxMpUser.get(7)
        def openid=params["openid"]
        def smsIdx=params.int('smsIdx')
        def phoneNum=params['phoneNum']

        def rmd= SmsService.createRandom(true,4);
        def  msg ="华海鹏城微信注册验证码:"+rmd

        def smsId= smsService.send(phoneNum,rmd)

        render([msg:"ok",status:1,code:200,smsCode:rmd,smsIdx:smsIdx+1] as JSON )
    }
    def audit={
        println "audit:"+params

        params.list('sellerId').eachWithIndex{sellerId,idx->
            println "it"+sellerId

            def seller= Seller.get(sellerId)
            def deptName=params.list('deptName').get(idx)
            def statusId=params.list('statusId').get(idx)
            seller.deptName=deptName
            seller.status=TDIf.get(statusId)

            if (!seller.hasErrors() && seller.save(flush: true)) {
                println "seller save ok!"+seller.id
            }
            else {
                seller.errors.allErrors.each {
                    println "save seller error:"+it
                }
            }
            println "sellerIdsellerIdsellerId:::::::::::::::::"+sellerId
        }
        render ""+params
    }
    def save( ) {

        def openid=params["openid"]
        def phoneNum=params['phoneNum']
        def realName=params['realName']
        def postName=params['postName']
        def deptName=params['deptName']

        println "openid:"+openid+"phoneNum:"+phoneNum
        def userinfo=  WxMpUser.findByOpenId(openid)
        userinfo.realName=realName
        userinfo.phoneNum=phoneNum
        userinfo.regionDate=new Date();
        userinfo.postName=postName
        userinfo.deptName=deptName

        userService.save(userinfo)
        println "save:::::"+userinfo


        def seller=Seller.findByNameAndMobile(realName,phoneNum)
        if(!seller) seller=new Seller()

        seller.deptName=deptName
        seller.postName=postName
        seller.mobile=phoneNum
        seller.status= TDIf.get(2) //否
        seller.wxUserId=userinfo.id
        seller.name=realName

        if (!seller.hasErrors() && seller.save()) {
            println "seller save ok!"+seller.id
        }
        else {
            seller.errors.allErrors.each {
                println "save seller error:"+it
            }
        }

        println "seller save:::"+seller.id






        redirect(action:"show" ,id:userinfo.id)
    }

    def show() {
        def id=params['id']

        def userinfo=  WxMpUser.get(id)
        userinfo.realName="A:"+userinfo.realName
        //  userService.save(userinfo)

        println "userinfo  id:"+userinfo?.id+" name:"+userinfo.realName


        //  println "smsId::"+smsId

        [userinfo:userinfo]

    }


    def create= {
        // 将请求、响应的编码均设置为UTF-8（防止中文乱码）
        //  request.setCharacterEncoding("UTF-8");
        def code=params['code']
        response.setCharacterEncoding("UTF-8");
        //@RequestParam("code") String code,Map<String, Object> mode

        // def code=request.getParameter("code")
        println "A"
        println "A"
        println "A"
        println "A"

        def userId= userService.getCpUseridByCode(code)

       // wxCpService.userAuthenticated(userId)
        // else openid='ok_busih96pQuC0C1iUuh2KC_iA0'
        def userinfo= WxCpUser.findByUserId(userId)

        def seller=Seller.findByWxUserId(userinfo.id)

        println "BBB userId:"+userId+" id:"+userinfo.id
        if(seller?.status?.name=="是"){

            render "你的微信号和手机号已经绑定且有效，不用再绑定注册了！！！"
        }

        [openid:userId,nickname:userinfo.name,id:userinfo.id];

    }


}
package com.hhpc.wechat.controller

import chanjarster.weixin.bean.result.WxUser
import chanjarster.weixin.util.http.SimpleGetRequestExecutor
import com.hhpc.wechat.domain.Seller
import com.hhpc.wechat.domain.TDIf
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement
import wechat.UserService


class UserController {

    def weChatService
    def userService
    def SmsService

    def index() {


    }
    def manager={

        def code=params['code']
        response.setCharacterEncoding("UTF-8");
         def openid= userService.getOpenidByCode(code)
        def userinfo= WxUser.findByOpenid(openid)

       def sellerList=Seller.findAllByWxUserIdIsNotNull( [max: 3, offset: 2, sort: "id", order: "desc"])
        println "sellerList:"+sellerList.size()
        [sellerList:sellerList]
    }
    def sendCode={
        //render {abc:'123'} as JSON

        println "^^^^^^sendCodesendCodesendCodesendCodesendCodesendCode&&^^^^^"
        println "params       :"+params

       // def userinfo=  WxUser.get(7)
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

        for (sellerId in params.list('sellerId')) {
            println "sellerIdsellerIdsellerId:::::::::::::::::"+sellerId
        }

    }
    def save( ) {

       def openid=params["openid"]
       def phoneNum=params['phoneNum']
       def realName=params['realName']
       def postName=params['postName']
       def deptName=params['deptName']

        println "openid:"+openid+"phoneNum:"+phoneNum
        def userinfo=  WxUser.findByOpenid(openid)
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

        def userinfo=  WxUser.get(id)
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

        def openid= userService.getOpenidByCode(code)
        if(code)openid=jsonObject.get("openid")
        else openid='ok_busih96pQuC0C1iUuh2KC_iA0'
        def userinfo= WxUser.findByOpenid(openid)

        [openid:openid,nickname:userinfo.nickname,id:userinfo.id];

    }


}
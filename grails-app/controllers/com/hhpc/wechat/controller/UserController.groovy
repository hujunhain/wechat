package com.hhpc.wechat.controller

import chanjarster.weixin.bean.result.WxUser
import chanjarster.weixin.util.http.SimpleGetRequestExecutor
import com.hhpc.wechat.domain.Seller
import com.hhpc.wechat.domain.TDIf
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement


class UserController {

    def weChatService
    def userService
    def SmsService

    def index() {


    }
    def manager={

        def code=params['code']
        response.setCharacterEncoding("UTF-8");
        def url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=${weChatService.wxConfig.appId}&secret=${weChatService.wxConfig.secret}&code=${code}&grant_type=authorization_code"
        println ""+url
        def jsonStr ="{openid:def}"
        if(code)jsonStr=weChatService.wxService.execute(new SimpleGetRequestExecutor(), url, null);
        JSONElement jsonObject = JSON.parse(jsonStr)

        def openid=jsonObject.get("openid")
        def userinfo= WxUser.findByOpenid(openid)

       def sellerList=Seller.findAllByWxUserIdIsNotNull()
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
        def smsId= smsService.send(phoneNum,rmd)

        render([msg:"ok",status:1,code:200,smsCode:rmd,smsIdx:smsIdx+1] as JSON )
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
        seller.save()


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
        println "code:::::::"+code
        def url="https://api.weixin.qq.com/sns/oauth2/access_token?appid=${weChatService.wxConfig.appId}&secret=${weChatService.wxConfig.secret}&code=${code}&grant_type=authorization_code"
        println ""+url
        def jsonStr ="{openid:def}"
        if(code)jsonStr=weChatService.wxService.execute(new SimpleGetRequestExecutor(), url, null);
        JSONElement jsonObject = JSON.parse(jsonStr)


        //  println "  access_token:"+jsonObject.getString("access_token")
        //  println "  refresh_token:"+jsonObject.getString("refresh_token")
        def openid
        if(code)openid=jsonObject.get("openid")
        else openid='ok_busih96pQuC0C1iUuh2KC_iA0'
        def userinfo= WxUser.findByOpenid(openid)

        [openid:openid,nickname:userinfo.nickname,id:userinfo.id];

    }


}
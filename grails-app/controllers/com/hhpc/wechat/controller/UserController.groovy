package com.hhpc.wechat.controller

import chanjarster.weixin.bean.result.WxUser
import chanjarster.weixin.util.http.SimpleGetRequestExecutor
import grails.converters.JSON
import org.codehaus.groovy.grails.web.json.JSONElement


class UserController {

    def weChatService

    def index() {


    }
    def save( ) {

       def openid=params["openid"]
       def phoneNum=params['phoneNum']
       def realName=params['realName']
        println "openid:"+openid+"phoneNum:"+phoneNum
        def userinfo=  WxUser.findByOpenid(openid)
        userinfo.realName=realName
        userinfo.phoneNum=phoneNum
        userinfo.regionDate=new Date();
        userinfo.save()
        println "save:::::"+userinfo

        redirect(action:"show" ,id:userinfo.id)
    }

    def show() {
        def id=params['id']

        def userinfo=  WxUser.get(id)

        println "userinfo  id:"+userinfo?.id+" name:"+userinfo.realName
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
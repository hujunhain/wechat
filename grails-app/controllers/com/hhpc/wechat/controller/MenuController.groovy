package com.hhpc.wechat.controller

import me.chanjar.weixin.common.bean.WxMenu

class MenuController {

    def wxCpService
    def weChatService




    def index() {


        wxCpService.menuDelete();

        WxMenu menu = new WxMenu();
        WxMenu.WxMenuButton button1 = new WxMenu.WxMenuButton();
        ;
        button1.setName("发图");


        WxMenu.WxMenuButton button2 = new WxMenu.WxMenuButton();
        //   button2.setType("click");
        button2.setName("扫码");
        //   button2.setKey("V1001_TODAY_SINGER");

        WxMenu.WxMenuButton button3 = new WxMenu.WxMenuButton();
        button3.setName("促销");

        menu.getButtons().add(button1);
        menu.getButtons().add(button2);
        menu.getButtons().add(button3);

        WxMenu.WxMenuButton button11 = new WxMenu.WxMenuButton();
        button11.setType("pic_sysphoto");
        button11.setName("系统拍照发图");
        button11.setKey("rselfmenu_1_0");


        WxMenu.WxMenuButton button12 = new WxMenu.WxMenuButton();
        button12.setType("pic_photo_or_album");
        button12.setName("拍照或者相册发图");
        button12.setKey("rselfmenu_1_2");

        WxMenu.WxMenuButton button13 = new WxMenu.WxMenuButton();
        button13.setType("pic_weixin");
        button13.setName("微信相册发图");
        button13.setKey("rselfmenu_1_3");

        button1.getSubButtons().add(button11);
        button1.getSubButtons().add(button12);
        button1.getSubButtons().add(button13);


        WxMenu.WxMenuButton button21 = new WxMenu.WxMenuButton();
        button21.setType("scancode_waitmsg");
        button21.setName("扫码带提示");
        button21.setKey("rselfmenu_2_1");

        WxMenu.WxMenuButton button22 = new WxMenu.WxMenuButton();
        button22.setType("scancode_push");
        button22.setName("扫码推事件");
        button22.setKey("rselfmenu_2_2");



        button2.getSubButtons().add(button21);
        button2.getSubButtons().add(button22);
        button2.getSubButtons().add(button13);

        WxMenu.WxMenuButton button30 = new WxMenu.WxMenuButton();
        button30.setType("view");
        button30.setName("注册");
        button30.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=${wxCpService.wxCpConfigStorage.corpId}&redirect_uri=http://${weChatService.serverIp}/user/create&response_type=code&scope=snsapi_base&state=1#wechat_redirect");

        //println button30.url

        WxMenu.WxMenuButton button31 = new WxMenu.WxMenuButton();
        button31.setType("view");
        button31.setName("管理");
        button31.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=${wxCpService.wxCpConfigStorage.corpId}&redirect_uri=http://${weChatService.serverIp}/user/manager&response_type=code&scope=snsapi_base&state=1#wechat_redirect");

        WxMenu.WxMenuButton button32 = new WxMenu.WxMenuButton();
        button32.setType("view");
        button32.setName("消息");
        button32.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=${wxCpService.wxCpConfigStorage.corpId}&redirect_uri=http://${weChatService.serverIp}/message/list&response_type=code&scope=snsapi_base&state=1#wechat_redirect");

        WxMenu.WxMenuButton button33 = new WxMenu.WxMenuButton();
        button33.setType("click");
        button33.setName("赞一下我们");
        button33.setKey("V1001_GOOD");

        button3.getSubButtons().add(button30);
        button3.getSubButtons().add(button31);
        button3.getSubButtons().add(button32);
        // button3.getSub_button().add(button33);

        wxCpService.menuCreate(menu)
        println "munu****************"
        render wxCpService.menuGet().toJson();
    }
}

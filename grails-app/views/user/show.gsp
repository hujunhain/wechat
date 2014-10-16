<%--
  Created by IntelliJ IDEA.
  User: hujun
  Date: 14-10-15
  Time: 下午3:04
--%>

<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
<html>
<head>
    <title>用户显示</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">


    <asset:stylesheet href="jquery/jquery.mobile-1.4.4.min.css"/>
    <asset:javascript src="jquery/jquery-1.11.1.min.js"/>
    <asset:javascript src="jquery/jquery.mobile-1.4.4.min.js"/>

</head>
<body>
<div data-role="page" data-control-title="Platforms" id="page3">
    <div data-theme="a" data-role="header">
        <h3>
            ${name}
        </h3>
    </div>
    <div data-role="content">



        <label for="userInfo.userName">ID:${userinfo?.id}</label>
        <label for="userInfo.userName">姓名:${userinfo?.getRealName()}</label>
        <label for="userInfo.userName">手机:${userinfo?.getPhoneNum()}</label>
        <label for="userInfo.userName">昵称:${userinfo?.getNickname()}</label>
        <label for="userInfo.userName">性别:${userinfo?.getSex()}</label>
        <label for="userInfo.userName">省份:${userinfo?.getProvince()}</label>
        <label for="userInfo.userName">城市:${userinfo?.getCity()}</label>
        <label for="userInfo.userName">头像 :${userinfo?.getHeadimgurl()}</label>

        <div style="width: 288px; height: 200px; position: relative; background-color: #fbfbfb; border: 1px solid #b8b8b8;"
             data-controltype="image">
            <img src="${userinfo?.getHeadimgurl()}" alt="image">
        </div>

    </div>
</div>
</body>
</html>
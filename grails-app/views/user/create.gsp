
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>注册验证</title>

    <meta name="viewport" content="width=device-width, initial-scale=1">

    <asset:stylesheet href="jquery/jquery.mobile-1.4.4.min.css"/>
    <asset:javascript src="jquery/jquery-1.11.1.min.js"/>
    <asset:javascript src="jquery/jquery.mobile-1.4.4.min.js"/>
</head>
<body>
<div data-role="page" id="pageone">
    <div data-role="header">
        <h1>注册验证</h1>
    </div>
    <div data-role="content">
        <form method="get" commandName="customer"
              action="/user/save">

            <input type="hidden" name="openid" id="userInfo.code" value="${openid}">
            <input type="hidden" name="id" id="userInfo.id" value="${id}">



            <div class="ui-grid-a">
                <div class="ui-block-a" style="width:40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <label for="nickname">微信:</label>
                    </div>
                </div>

                <div class="ui-block-b" style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <input type="text" name="nickname" id="nickname" value="${nickname}">
                    </div>
                </div>

                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="realName">姓名:</label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <input type="text" name="realName" id="realName" value="${userName}">
                    </div>
                </div>
                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="phoneNum">手机号码: </label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <input type="number" data-clear-btn="true" name="phoneNum" pattern="[0-9]*" id="phoneNum"
                               value="">
                    </div>
                </div>
                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="userDept">部门:</label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        部门
                    </div>
                </div>
                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="userStat">职位: </label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        职位
                    </div>
                </div>
            </div>

            <button class="ui-btn ui-shadow">Button</button>
        </form>
    </div>
</div>
</body>
</html>
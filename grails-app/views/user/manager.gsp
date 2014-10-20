
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
        <h1>用户管理</h1>
    </div>
    <div role="main" class="ui-content">
        <div class="ui-grid-c">
            <div class="ui-block-a"><div class="ui-bar ui-bar-a" style="height:60px">Block A</div></div>
            <div class="ui-block-b"><div class="ui-bar ui-bar-a" style="height:60px">Block B</div></div>
            <div class="ui-block-c"><div class="ui-bar ui-bar-a" style="height:60px">Block C</div></div>
            <div class="ui-block-d"><div class="ui-bar ui-bar-a" style="height:60px">Block D</div></div>
        </div><!-- /grid-c -->
        <div class="ui-grid-c">
            <div class="ui-block-a"><div class="ui-bar ui-bar-a" style="height:60px">Block A</div></div>
            <div class="ui-block-b"><div class="ui-bar ui-bar-a" style="height:60px">Block B</div></div>
            <div class="ui-block-c"><div class="ui-bar ui-bar-a" style="height:60px">Block C</div></div>
            <div class="ui-block-d"><div class="ui-bar ui-bar-a" style="height:60px">Block D</div></div>
        </div><!-- /grid-c -->
        <div class="ui-grid-c">
            <div class="ui-block-a"><div class="ui-bar ui-bar-a" style="height:60px">Block A</div></div>
            <div class="ui-block-b"><div class="ui-bar ui-bar-a" style="height:60px">Block B</div></div>
            <div class="ui-block-c"><div class="ui-bar ui-bar-a" style="height:60px">Block C</div></div>
            <div class="ui-block-d"><div class="ui-bar ui-bar-a" style="height:60px">Block D</div></div>
        </div><!-- /grid-c -->
        <form method="get" id="form" commandName="customer"
              action="/user/saveXX" data-ajax=“false">
            <% sellerList.each { seller -> %>
            <p><%="Hello ${seller}!" %></p>
            <%}%>


            </form>
        </div>
    </div>
</body>
</html>



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
        </div>
        <% sellerList.each { seller -> %>

        <div class="ui-grid-c">
           <input type="hidden" id="sellerId" value="${seller.id}">
            <div class="ui-block-a" style="width: 20%"><div class="ui-bar ui-bar-a" style="height:60px">${seller.name}</div></div>
            <div class="ui-block-b"  style="width: 20%"><div class="ui-bar ui-bar-a" style="height:60px">${seller.deptName}</div></div>
            <div class="ui-block-c"  style="width: 20%"><div class="ui-bar ui-bar-a" style="height:60px">${seller.mobile}</div></div>
            <div class="ui-block-d" style="width: 20%" > <div class="ui-bar ui-bar-a" style="height:60px">
                <select name=“statusId"  id=“statusId"  id="slider-flip-m"  data-mini="true">
                    <option value="1" ${seller.status=="是"?"selected":"" } >No</option>
                    <option value="2" ${seller.status=="否"?"selected":"" } >否</option>
                </select>

                </div></div>

    </div>
        <%}%>
        <form method="get" id="form" commandName="customer"
              action="/user/saveXX" data-ajax=“false">



            </form>
        </div>
    </div>
</body>
</html>


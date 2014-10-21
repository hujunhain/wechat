
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

        <form method="get" id="form" commandName="customer"
              action="/user/audit" data-ajax=“false">
            <% sellerList.each { seller -> %>

            <div class="ui-grid-b">

                <input type="hidden" id="sellerId" name="sellerId" value="${seller.id}" />

                <div class="ui-block-a" style="width: 60%">
                    <div style="top:0; left:0; width:100%; min-height:100%;  border:0;height:60px">
                        ${seller.name}:${seller.mobile}</div></div>
                <div class="ui-block-b"  style="width: 25%">

                    <div style="top:0; left:0; width:100%; min-height:100%;  border:0;">


                        <input data-role='none' style="width:60px; "  type="text" name="deptName" id="deptName"  value="${seller?.deptName}">
                    </div>
                </div>

                <div class="ui-block-c" style="width: 15%" >
                    <div  style="top:0; left:0; width:100%; min-height:100%;  border:0;height:60px">
                        <select data-role='none' name="statusId" id="statusId">
                            <option value="1" ${seller.status.name=="是"?"selected":""}>是</option>
                            <option value="2" ${seller.status.name=="否"?"selected":""} >否</option>
                        </select>

                    </div></div>

            </div>
            <%}%>



            <input type="submit" value="提交">
        </form>
        <g:paginate next="下一页" prev="前一页"
                    maxsteps="0"
                    action="manager" total="${200}" />
    </div>
</div>
</body>
</html>



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

    <div role="main" class="ui-content">

        <form method="get" id="form" commandName="customer"
              action="/user/audit" data-ajax=“false">
            <% msgList.each { msg -> %>


                <input type="hidden" id="sellerId" name="sellerId" value="${msg.id}" />



                    <p>    ${msg.message}</p>


             <%}%>



            <input type="submit" value="提交">
        </form>
        <g:paginate next="下一页" prev="前一页"
                    maxsteps="0"
                    action="list" total="${200}" />
    </div>
</div>
</body>
</html>


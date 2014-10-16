
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

                        <label for="userStat">职位: </label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <select name="postName" id="postName">
                            <option value="促销主管">促销主管</option>
                            <option value="促销组长">促销组长</option>
                            <option value="促销员">促销员</option>
                        </select>
                    </div>
                </div>
                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="deptName">部门:</label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <input type="text" name="deptName" id="deptName" placeholder="所属区域" value="">
                       <!-- <select name="deptName" id="deptName">
                            <optgroup label="成都">
                                <option value="1">成都A</option>
                                <option value="2">成都B</option>
                                <option value="42">成都酒吧街</option>
                            </optgroup>
                            <optgroup label="川外">
                                <optgroup label="川中南区域">
                                    <option value="7">成郊西南区</option>
                                    <option value="11">自内区域</option>
                                    <option value="43">城郊东北</option>
                                    <option value="44">温江区域</option>
                                    <option value="45">阿坝区域</option>
                                    <option value="46">宜宾区域</option>
                                </optgroup>
                                <optgroup label="川东区域">
                                    <option value="8">达州市区</option>
                                    <option value="13">南充区域</option>
                                    <option value="19">遂宁区域</option>
                                    <option value="47">达州郊县</option>

                                </optgroup>
                                <optgroup label="川西区域">
                                    <option value="14">乐山区域</option>
                                    <option value="15">攀枝花区</option>
                                    <option value="48">眉山区域</option>
                                    <option value="49">雅安区域</option>

                                </optgroup>
                            </optgroup>
                        </select>
                        -->
                    </div>
                </div>



            </div>

            <button class="ui-btn ui-shadow">Button</button>
        </form>
    </div>
</div>
</body>
</html>
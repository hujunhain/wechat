
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
        <form method="get" id="form" commandName="customer"
              action="/user/save" data-ajax=“false">

            <input type="hidden" name="openid" id="userInfo.code" value="${openid}">
            <input type="hidden" name="id" id="userInfo.id" value="${id}">
            <input type="hidden" name="id" id="sendCode" value="">
            <input type="hidden" name="smsIdx" id="smsIdx" value="0">




            <div class="ui-grid-a">
                <div class="ui-block-a" style="width:40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <label for="nickname">微信昵称:</label>
                    </div>
                </div>

                <div class="ui-block-b" style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <input type="text" name="nickname" id="nickname" value="${nickname}">
                    </div>
                </div>

                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="realName">真实姓名:</label></div>
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
                            <option value="促销员">促销员</option>
                            <option value="促销组长">促销组长</option>
                            <option value="促销主管">促销主管</option>


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

                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="deptName">手机验证码:</label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <input type="text" name="smsCode" id="smsCode" placeholder="验证码" value="">

                        </div>

                </div>



                <div class="ui-block-a" style="width: 60%;align:center">


                            <a href="#" id="codeagain"  class="ui-shadow ui-btn ui-corner-all " >获取验证码</a>

                    </div>

                <div class="ui-block-b"  style="width:40%">


                        <a href="#" id="submit" class="ui-shadow ui-btn ui-corner-all">提交</a>
                    </div>








            </div>


        </form>
    </div>
</div>
</body>
<script>

    $(function(){
        $('#submit').click(function(){
            if($("#realName").val()==""){
                alert("请输入真实姓名！");
                $("#realName").focus()
                return false
            }
            if($("#phoneNum").val()==""){
                alert("请输入手机号码！");
                return false
            }
            if($("#smsCode").val()==""){
                alert("请输入手机验证号码！");
                return false
            }
           if( $("#sendCode").val()==""){
               alert('请获取手机验证短信');
               return false
           }
            if($("#sendCode").val()!=$("#smsCode").val()){
                alert('请输入正确的验证号码');
                return false
            }
            $('#form').submit()
        })
    })

    //重新获取验证码
    $('#codeagain').click(function() {


       var validateReg = /^((\+?86)|(\(\+86\)))?1\d{10}$/;
        if( !validateReg.test($("#phoneNum").val())){
           alert("请输入正确的手机号码！");
            return false
        }
        if($("#smsIdx").val()==3){
            alert("手机短信验证次数超过3次，不能再验证了，请联系系统管理员！");
            return false
        }

        var o = this;
        $.ajax({
            url:"/user/sendCode?jsoncallback=?",
            type:"get",
            data: {smsIdx:$("#smsIdx").val(),phoneNum:$("#phoneNum").val()},
            //  dataType: "json",
            success: function (data) {
                if(data.status == 1 && data.code == 200){
                    alert("验证码已发送至您的手机");
                    $("#sendCode").attr("value",data.smsCode)
                    $("#smsIdx").attr("value",data.smsIdx)

                    get_code_time(o);
                } else {

                    if(data.msg != ""){
                        alert("msg:"+data.msg);
                    } else {
                        alert("短信验证码发送失败!!!");
                    }
                }
            },
            error: function(XMLHttpRequest, textStatus, errorThrown){
                alert("err:" +XMLHttpRequest.status + XMLHttpRequest.responseText);
            }
        });
    });
    var clicked = false;
    var wait = 120;
    get_code_time = function (o) {
        if (wait == 0) {
          //  o.removeAttribute("disabled");

            $(o).removeClass('ui-disabled');
            clicked = false;
            o.innerText = "获取验证码";
            wait = 120;
        } else {
           // o.setAttribute("disabled", true);
            $(o).addClass('ui-disabled');
            clicked = true;
            o.innerText = "(" + wait + ")秒后重新获取";
            wait--;
            setTimeout(function() {
                get_code_time(o)
            }, 1000)
        }
    }
</script>
</html>
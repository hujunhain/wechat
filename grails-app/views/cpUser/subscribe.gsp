
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

            <input type="hidden" name="userid" id="userInfo.userId" value="${userinfo.userId}">
            <input type="hidden" name="id" id="userInfo.id" value="${userinfo.id}">
            <input type="hidden" name="id" id="sendCode" value="">
            <input type="hidden" name="smsIdx" id="smsIdx" value="0">


            <div class="ui-grid-a">
                <div class="ui-block-a" style="width:40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <label for="name">姓名:</label>
                    </div>
                </div>

                <div class="ui-block-b" style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <input type="text" name="name" id="name" value="${userinfo.name}">
                    </div>
                </div>

                <div class="ui-block-a" style="width:40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <label for="userinfo.weiXinId">微信号:</label>
                    </div>
                </div>

                <div class="ui-block-b" style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <input type="text" name="userinfo.weiXinId" id="userinfo.weiXinId" value="${userinfo.weiXinId}">
                    </div>
                </div>


                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="mobile">手机号码: </label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <input type="number" data-clear-btn="true" name="mobile" id="mobile" pattern="[0-9]*"
                               value="${userinfo.mobile}">
                    </div>
                </div>
                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="userStat">职位: </label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <input type="text" name="position" id="userinfo.position" placeholder="职位" value="${userinfo.position}">


                    </div>
                </div>
                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="deptName">部门:</label></div>
                </div>
                <div class="ui-block-b"  style="width:60%">
                    <div class="ui-bar ui-bar-a" style="height:60px">
                        <input type="text" name="departIds" id="departIds" placeholder="所属部门" value="${userinfo.departIds}">

                    </div>
                </div>

                <div class="ui-block-a" style="width: 40%">
                    <div class="ui-bar ui-bar-a" style="height:60px">

                        <label for="smsCode">手机验证码:</label></div>
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
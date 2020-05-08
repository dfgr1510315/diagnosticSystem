document.querySelector('.img__btn').addEventListener('click', function() {
    document.querySelector('.content').classList.toggle('s--signup')
});
let path_name = '/diagnostic_system';
let pasw_model = /^[\w]{6,12}$/;
let email_model = /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/;
function login() {
    let email = $("#email").val();
    let password = $("#password").val();
    $.ajax({
        url: path_name+"/user/login",
        type: "POST",
        dataType: "json",
        data: {
            email: email,
            password: password
        },
        success: function (result) {
            if(result.code===200){
                //保存token用来判断用户是否登录，和身份是否属实
        /*        var expire= new Date();
                expire.setTime(expire.getTime() + (60 * 1000));*/
                $.cookie('token', result.data.token,{ expires: 1, path: path_name });
                $.cookie('username', result.data.username,{ expires: 365, path: path_name });
                //$.cookie('user', result.data.user);
                //转向主页面
                window.location.href=path_name+"/backstage.html"
                //alert(result.data.token);
            }else{
                alert("用户名或者密码错误！");
            }
        }
    });
}

function register() {
    let email = $("#register_email");
    let password = $("#register_password");
    let username = $("#register_username");
    if (!email_model.test(email.val())){
        alert('请输入有效的邮箱地址');
        return;
    }
    if (!pasw_model.test(password.val())) {
        alert('密码以字母开头，长度在6~18之间，只能包含字母、数字和下划线');
        return
    }
    $.ajax({
        url: path_name+"/user/register",
        type: "POST",
        dataType: "json",
        data: {
            email: email.val(),
            password: password.val(),
            username: username.val()
        },
        success: function (result) {
            alert(result.data);
            if(result.code===200){
                $('.m--in').click();
                email.val('');
                password.val('');
                username.val('');
            }
        }
    });
}
//login_script、Interceptor
let path_name = '/diagnostic_system';
$(document).ready(function(){
    let loading = $('.loading').wrapInner('<div></div>'),
        min = 20,
        max = 70,
        minMove = 10,
        maxMove = 20;
    //startAnimation(loading,min,max,minMove,maxMove);
    //freshToken();
});


function freshToken(result) {
    if (result.code===401||result.code===404){
        alert(result.message);
        top.location.href=path_name+"/login.html"
    }
}

/*function freshToken() {
    //console.log($.cookie('token'));
    $.ajax({
        // 自定义的headers字段，会出现option请求，在GET请求之前，后台要记得做检验。
        headers: {
            token: $.cookie('token')
        },
        url: "/user/verification",
        type: 'GET',
        dataType: 'json',
        success : function (result) {
            //console.log(result);
            if(result.code!==200){
                //alert("加载到的数据："+result+",并进行渲染页面.....");
                top.location.href="/login.html"
            }
        },
        complete: function (xhr) {
            /!*
                获取相关Http Response header
                getResponseHeader(key)：获取指定头信息
                getAllResponseHeaders()：获取全部可默认可获取的头信息
            *!/
            $.cookie('token',xhr.getResponseHeader('token'),{ expires: 1, path: '/' });
        }
    })
}*/

function logout() {
    /*var keys = document.cookie.match(/[^ =;]+(?=\=)/g);
    if(keys) {
        for(var i = keys.length; i--;)
            document.cookie = keys[i] + '=0;expires=' + new Date(0).toUTCString()
    }*/
    $.cookie('token','');
    $.cookie('username','');
    //返回登录页面或者主页
    window.location.href = path_name+"/login.html";
}

//设置CSS变量并根据需要生成跨距
function setCSSVars(elem, min, max, minMove, maxMove) {
    let width = Math.ceil(elem.width()),
        text = elem.text();
    for(let i = 1; i < width; i++) {
        let num = Math.floor(Math.random() * (max - min + 1)) + min,
            numMove = Math.floor(Math.random() * (maxMove - minMove + 1)) + minMove,
            dir = (i % 2 === 0) ? 1 : -1,
            spanCurrent = elem.find('span:eq(' + i + ')'),
            span = spanCurrent.length ? spanCurrent : $('<span />');
        span.css({
            '--x': i - 1 + 'px',
            '--move-y': num * dir + 'px',
            '--move-y-s': ((i % 2 === 0) ? num * dir - numMove : num * dir + numMove) + 'px',
            '--delay': i * 10 + 'ms'
        });
        if(!spanCurrent.length) {
            elem.append(span.text(text));
        }
    }
}

//开始动画
function startAnimation(elem,min,max,minMove,maxMove) {
    elem.removeClass('start');
    setCSSVars(elem, min, max, minMove, maxMove);
    void elem[0].offsetWidth;
    elem.addClass('start');
}
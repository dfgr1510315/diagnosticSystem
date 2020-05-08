$(document).ready(function(){
    loadUsername();
});

function loadUsername() {
/*    console.log('修改:'+$.cookie('username'));
    console.log('username:'+$('#username').html());*/
    $('#username').html('<i class="iconfont icon-yonghu1"></i>'+$.cookie('username'));
}
function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split("&");
    for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if(pair[0] === variable){return pair[1];}
    }
    return '';
}

//检查图片
function checkImage() {
    let inputFile = $(".layui-upload-file");
    let fileName = inputFile.val();
    fileName=fileName.replace("C:\\fakepath\\","");
    let flag=true;
    if(fileName===""){
        flag=false;
        alert("请选择图片");
    }
    else{
        var size = inputFile[0].files[0].size;
        if(size/1000>1000){
            flag=false;
            alert("图片大小不能超过1000KB");
        }
    }
 /*   if(flag){
        onLoadImage();
    }
    else{//清除input type=file的显示内容
        inputFile.val("");
    }*/
    return flag;
}

//预览图片
/*function onLoadImage() {
    image=$(".layui-upload-file").get(0).files[0];
    var reader = new FileReader();
    //将文件以Data URL形式读入页面
    reader.readAsDataURL(image);
    reader.onload=function(e){
        //显示文件
        $("#onLoadImage").append('<img src="' + this.result +'" alt="" style="height: 135px;margin-top: -58px;" />');
    }
}*/

function addOnchange() {
    $('.layui-upload-file').attr('onchange','checkImage()').attr('accept','image/png, image/jpeg, image/jpg');

}
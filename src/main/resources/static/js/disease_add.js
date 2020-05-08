let image;
$(document).ready(function () {
    getTypeList();
    getParts();
});

var cuploadCreate = new Cupload ({
    ele: '#diseasePic',
    num: 3
});

function getTypeList() {
    $.ajax({
        url: path_name+"/crop/getTypeList",
        type: "POST",
        dataType: "json",
        headers: {
            token: $.cookie('token')
        },
        success: function (result) {
            //console.log(result);
            freshToken(result);
            if(result.code===200){
                addSelectList($('#type'),result.data);
            }
        }
    });
}

function getCropList(){
    clearCropList();
    let type = $('#type').val();
    if (type==='')return;
    $.ajax({
        url: path_name+"/crop/getCropList",
        type: "POST",
        dataType: "json",
        headers: {
            token: $.cookie('token')
        },
        data: {
            type: type,
            name: ''
        },
        success: function (result) {
            freshToken(result);
            if (result.code === 200) {
                addSelectList($('#crop'),result.data.list);
            }
        }
    });
}

function getParts() {
    $.ajax({
        url: path_name+"/crop/getParts",
        type: "POST",
        dataType: "json",
        headers: {
            token: $.cookie('token')
        },
        success: function (result) {
            freshToken(result);
            if(result.code===200){
                addSelectList($('#parts'),result.data);
            }
        }
    });
}

function addSelectList(event,data){
    data.forEach(function (data) {
        event.append('<option value="' + data.id + '">' + data.name + '</option>')
    });
    refreshList()
}

function clearCropList(){
    let crop = $('#crop');
    crop.children().remove();
    crop.append('<option value="">作物名称</option>');
    refreshList()
}

function refreshList(){
    layui.use(['form', 'laydate'], function () {
        var form = layui.form;
        form.render();
        $('#type').next().children('dl').children().attr('onclick','getCropList()');
    });
}

function submitData(src) {
    let diseaseName = $('#diseaseName').val();
    let features = $('#features').val();
    let desc = $('#desc').val();
    let crop = $('#crop').val();
    let parts = $('#parts').val();
    if (diseaseName===''){
        alert('请输入病害名称');return
    }if (features===''){
        alert('请输入基本特征');return
    }if (desc===''){
        alert('请输入病害相关信息');return
    }if ($('#type').val()===''){
        alert('请选择作物类型');return
    }if (crop===''){
        alert('请选择作物');return
    }if (parts===''){
        alert('请选择病变部位');return
    }
    $.ajax({
        type: 'POST',
        url: path_name+"/disease/addDisease",
        dataType: 'json',
        headers: {
            token: $.cookie('token')
        },
        data: {
            name : diseaseName,
            image : src,
            features : features,
            information : desc,
            crop : crop,
            parts : parts
        },
        success: function (result) {
            freshToken(result);
            if(result.code===200&&result.data>0){
                alert('提交成功');
                window.location.reload();
            }else {
                alert('提交失败')
            }
        }
    });
}

function submitUpdate() {
    var formData = new FormData();
    for (var i=0;i<cuploadCreate.inputFile.length;i++){
        formData.append('file', cuploadCreate.inputFile[i]);
    }
    $.ajax({
        type: 'POST',
        url: path_name+'/upload/uploadImage/diseaseImage',
        dataType: 'json',
        headers: {
            token: $.cookie('token')
        },
        data: formData,
        processData: false,
        contentType: false,
        success: function (result) {
            freshToken(result);
            let src = '';
            for (let i=0;i<result.length;i++){
                if (result[i].code===200){
                    src+=result[i].data+'|';
                }else if (result[i].code===500){
                    alert(result[i].data)
                }
            }
            submitData(src.replace(/[|]$/,""));
        }
    });
}



/*        layui.use(['upload','form'], function() {
            //拖拽上传
            layui.upload.render({
                elem: '#diseasePic',
                url: '/upload/uploadImage/diseaseImage',
                auto: false, //选择文件后不自动上传
                bindAction: '#submitData',
                headers: {
                    token: $.cookie('token')
                },
                choose: function(obj){
                    var files = obj.pushFile();
                    //console.log(files);
                    //预读本地文件，如果是多文件，则会遍历。(不支持ie8/9)
                    obj.preview(function(index, file, result){
                        console.log(index); //得到文件索引
                        console.log(file); //得到文件对象
                        console.log(result); //得到文件base64编码，比如图片
                    });
                },
                done: function(result) {
                    //submitData(result.data)
                }
            });
        });*/
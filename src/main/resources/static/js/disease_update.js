let id = getQueryVariable('id');
let diseaseData = parent.$("tr[disease=" + id + "]").children();
let diseaseName = diseaseData.eq(0).text();
//let src = diseaseData.eq(1).children().attr('src');
let src = getSrc();
let features = diseaseData.eq(2).text();
let desc;
let crop = diseaseData.eq(3).attr('crop');
let parts = diseaseData.eq(5).attr('parts');
let _diseaseName = $('#diseaseName');
let _features = $('#features');
let _desc = $('#desc');
let _crop = $('#crop');
let _parts = $('#parts');
$(document).ready(function () {
    getOldData();
});

var cuploadCreate = new Cupload ({
    ele: '#diseasePic',
    num: 3,
    data:src
});

function getSrc() {
    let imgs = diseaseData.eq(1).children();
    let src = [];
    for (let i=0;i<imgs.length;i++){
        src[i] = imgs[i].src;
    }
    return src;
}

function getOldData() {
    _diseaseName.val(diseaseName);
    $("#onLoadImage").html('<img src="' + src + '" alt="" style="height:135px;margin-top:-58px;" />');
    _features.val(features);
    getInformation(id);
    let type = $('#type');
    addSelectList(type, parent.typeMap);
    type.val(diseaseData.eq(4).attr('type'));
    getCropList(true);
    addSelectList(_parts, parent.partsMap);
    _parts.val(parts);
}

function getInformation(id) {
    $.ajax({
        type: 'POST',
        url: path_name+"/disease/getInformation",
        dataType: 'json',
        headers: {
            token: $.cookie('token')
        },
        data: {
            id: id
        },
        success: function (result) {
            freshToken(result);
            if (result.code === 200) {
                _desc.val(result.data);
                desc = result.data
            }
        }
    });
}

function getCropList(isDefault) {
    clearCropList();
    let type = $('#type').val();
    if (type === '') return;
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
            //console.log(result);
            freshToken(result);
            if (result.code === 200) {
                result.data.list.forEach(function (data) {
                    _crop.append('<option value="' + data.id + '">' + data.name + '</option>')
                });
                if (isDefault) _crop.val(crop);
                refreshList()
            }
        }
    });
}

function submitData(newSrc) {
    if (_diseaseName.val() === '') {
        alert('请输入病害名称');
        return
    }
    if (_features.val() === '') {
        alert('请输入基本特征');
        return
    }
    if (_desc.val() === '') {
        alert('请输入病害相关信息');
        return
    }
    if ($('#type').val() === '') {
        alert('请选择作物类型');
        return
    }
    if (_crop.val() === '') {
        alert('请选择作物');
        return
    }
    if (_parts.val() === '') {
        alert('请选择病变部位');
        return
    }
    let newName = diseaseName === _diseaseName.val() ? '' : _diseaseName.val();
    let newFeatures = features === _features.val() ? '' : _features.val();
    let newDesc = desc === _desc.val() ? '' : _desc.val();
    let newCrop = crop === _crop.val() ? '' : _crop.val();
    let newParts = parts === _parts.val() ? '' : _parts.val();
    $.ajax({
        type: 'POST',
        url: path_name+"/disease/diseaseUpdate",
        dataType: 'json',
        headers: {
            token: $.cookie('token')
        },
        data: {
            id: id,
            name: newName,
            image: newSrc,
            features: newFeatures,
            information: newDesc,
            cropID: newCrop,
            parts: newParts
        },
        success: function (result) {
            freshToken(result);
            if (result.code === 200 && result.data === 1) {
                alert('修改成功');
                parent.location.reload();
            } else alert('未知错误')
        }
    });
}

function clearCropList() {
    _crop.children().remove();
    _crop.append('<option value="">作物名称</option>');
    refreshList()
}

function addSelectList(event, data) {
    data.forEach(function (name, id) {
        event.append('<option value="' + id + '">' + name + '</option>')
    });
}

function refreshList() {
    layui.use(['form', 'laydate'], function () {
        var form = layui.form;
        form.render();
        $('#type').next().children('dl').children().attr('onclick', 'getCropList(false)');
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
            let _src = '';
            cuploadCreate.oldImage.forEach(function(item){
                _src+=item+'|';
            });
            for (let i=0;i<result.length;i++){
                if (result[i].code===200){
                    _src+=result[i].data+'|';
                }else if (result[i].code===500) {
                    alert(result[i].data)
                }
            }
            //console.log(_src.replace(/[|]$/,""));
            submitData(_src.replace(/[|]$/,""));
        }
    });
}



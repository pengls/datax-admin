/**
 * 数据源管理
 * pengl
 * 2018/11/22
 */
(function ($, window, document) {
    window.dts = window.dts || {};
    var table = {};
    var rootPath = common.root();

    dts.init = function () {
        initTable();
    };

    dts.edit = function () {
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        if(checkResult.length > 1){
            common.alertError('只能选择一条记录进行操作');
            return false;
        }
        var dsId = checkResult[0];
        var rowData = dtable.getCheckRowData('dsTable', dsId);
        $("#dtsInfoTitle").html("编辑数据源【"+rowData.dsId+"】");
        fixFormData(rowData);
        $("#createtimeDiv,#dsTypeDiv").show();
        lockForm();
        $("#dtsWin").modal('show');
    };

    dts.test = function () {
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        if(checkResult.length > 1){
            common.alertError('只能选择一条记录进行测试');
            return false;
        }
        var dsId = checkResult[0];
        $.post(rootPath + "/dts/test",{dsId : dsId}, function (data) {
            if(data.flag){
                common.alertSuc('数据源连接成功');
            }else{
                common.alertError(data.msg);
            }
        }, "json");


    };

    dts.save = function() {
        var formData = getFormData();
        if(verifyFormData(formData)){
            $.post(rootPath + "/dts/save", formData,  function (data) {
                if(data.flag){
                    common.alertSuc('数据源保存成功', function () {
                        $("#dtsWin").modal('hide');
                        dtable.reload(table);
                    });
                }else{
                    common.alertError(data.msg);
                }
            }, "json");
        }
    }

    dts.del = function () {
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        //询问框
        layer.confirm('是否删除选中的数据源？', {
            icon: 3
        }, function(){
            $.post(rootPath + "/dts/del",{dsIds : checkResult.join(',')}, function (data) {
                if(data.flag){
                    common.alertSuc('成功删除：'+ data.msg + '个数据源', function () {
                        dtable.reload(table);
                    });
                }else{
                    common.alertError(data.msg);
                }
            }, "json");
        }, function(){

        });
    };

    dts.add = function () {
        clearForm();
        $("#dtsInfoTitle").html("新增数据源");
        $("#createtimeDiv,#dsTypeDiv").hide();
        $("#dtsWin").modal('show');
    };

    function initTable() {
        table = dtable.init({
            "refer" : "dsTable",
            "ajax": {
                url : rootPath + "/dts/list",
                type :"POST"
            },
            "columns": [
                { "data": "dsId"},
                { "data": "dsDesc"},
                { "data": "dsUser"},
                { "data": "dsPass"},
                { "data": "dsType"},
                { "data": "dsUrl"},
                { "data": "dsStatus"},
                { "data": "createTime"}
            ],
            "columnDefs": [
                {
                    "orderable": false,
                    "render": function ( data, type, row ) {
                        return '<input type ="checkbox" name="ckbox" data-flag="icheck" class="square-blue" value="' + data + '" />';
                    },
                    "targets":  0
                },
                {
                    "render": function ( data, type, row ) {
                        return dtable.subStr(data, 30);
                    },
                    "targets":  5
                },
                {
                    "render": function ( data, type, row ) {
                        return data == 1 ? '<span class="label label-success">有效</span>' : '<span class="label label-danger">无效</span>';
                    },
                    "targets":  6
                },
                {
                    "render": function ( data, type, row ) {
                        var _date = new Date();
                        _date.setTime(data);
                        return _date.format("yyyy-MM-dd hh:mm:ss");
                    },
                    "targets":  7
                }
            ],
            "order": [[7, 'desc']]
        });
    }
    //清除表单数据
    function clearForm() {
        $("#dts-form").find(':input[name]:not(:radio)').val('');
        $(":radio[name='dsStatus'][value='1']").iCheck('check');
    }
    //锁定表格字段
    function lockForm() {
        $("#createTime,#dsTypeDiv").attr('readonly', 'readonly').attr('disabled','disabled');
        $(":radio[name='dsType']").attr('readonly', 'readonly').attr('disabled','disabled')
    }
    //填充表单
    function fixFormData(data) {
        $("#primKey").val(data.dsId);
        $("#dsDesc").val(data.dsDesc);
        $("#dsUser").val(data.dsUser);
        $("#dsUrl").val(data.dsUrl);
        $("#dsPass").val(data.dsPass);
        $(":radio[name='dsStatus'][value='"+data.dsStatus+"']").iCheck('check');
        $(":radio[name='dsType'][value='"+data.dsType+"']").iCheck('check');
        var _date = new Date();
        _date.setTime(data.createTime);
        $("#createTime").val(_date.format("yyyy-MM-dd hh:mm:ss"));
        $("#dsSchema").val(data.dsSchema);
    }
    //获取表单数据
    function getFormData() {
        return {
            dsId : $("#primKey").val(),
            dsDesc : $("#dsDesc").val(),
            dsUser : $("#dsUser").val(),
            dsUrl : $("#dsUrl").val(),
            dsPass : $("#dsPass").val(),
            dsStatus : $(":radio[name='dsStatus']:checked").val(),
            dsType : $(":radio[name='dsType']:checked").val(),
            createTime : $("#createTime").val(),
            dsSchema : $("#dsSchema").val()
        }
    }
    //表单验证
    function verifyFormData(formData) {
        if(formData.dsDesc.trim().length == 0){
            common.alertError('请填写数据源名称！');
            return false;
        }
        if(formData.dsUser.trim().length == 0){
            common.alertError('请填写用户名！');
            return false;
        }
        if(formData.dsPass.trim().length == 0){
            common.alertError('请填写密码！');
            return false;
        }
        if(formData.dsUrl.trim().length == 0){
            common.alertError('请填写JDBC URL！');
            return false;
        }
        return true;
    }
    dts.init();
})(jQuery, window, document);
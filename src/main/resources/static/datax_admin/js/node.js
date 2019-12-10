/**
 * 节点管理
 * pengl
 * 2018/11/22
 */
(function ($, window, document) {
    window.node = window.node || {};
    var table = {};
    var rootPath = common.root();

    node.init = function () {
        initTable();
    };

    node.edit = function () {
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        if(checkResult.length > 1){
            common.alertError('只能选择一条记录进行操作');
            return false;
        }
        var dsId = checkResult[0];
        var rowData = dtable.getCheckRowData('nodeTable', dsId);
        $("#nodeInfoTitle").html("编辑节点【"+rowData.ip+"】");
        fixFormData(rowData);
        lockForm();
        $("#nodeWin").modal('show');
    };

    node.test = function () {
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        if(checkResult.length > 1){
            common.alertError('只能选择一条记录进行测试');
            return false;
        }
        var nodeId = checkResult[0];
        $.post(rootPath + "/node/test",{nodeId : nodeId}, function (res) {
            if(res.flag){
                common.alertSuc('节点连接成功');
            }else{
                common.alertError(res.resMsg);
            }
        }, "json");


    };

    node.save = function() {
        var formData = getFormData();
        if(verifyFormData(formData)){
            $.post(rootPath + "/node/save", formData,  function (res) {
                if(res.data){
                    common.alertSuc('节点保存成功', function () {
                        $("#nodeWin").modal('hide');
                        dtable.reload(table);
                    });
                }else{
                    common.alertError(res.resMsg);
                }
            }, "json");
        }
    }

    node.del = function () {
        var checkResult = dtable.check('ckbox');
        if(!checkResult){
            return;
        }
        //询问框
        layer.confirm('是否删除选中的节点？', {
            icon: 3
        }, function(){
            $.post(rootPath + "/node/del",{nodeIds : checkResult.join(',')}, function (data) {
                if(data.flag){
                    common.alertSuc('成功删除节点', function () {
                        dtable.reload(table);
                    });
                }else{
                    common.alertError(data.msg);
                }
            }, "json");
        }, function(){

        });
    };

    node.add = function () {
        clearForm();
        $("#nodeInfoTitle").html("新增节点");
        $("#nodeWin").modal('show');
    };

    function initTable() {
        table = dtable.init({
            "refer" : "nodeTable",
            "ajax": {
                url : rootPath + "/node/list",
                type :"POST"
            },
            "columns": [
                { "data": "id"},
                { "data": "ip"},
                { "data": "port"},
                { "data": "type"},
                { "data": "user"},
                { "data": "pass"},
                { "data": "status"},
                { "data": "defaultPath"}
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
                        if(data == 1){
                            return '<span class="label label-success">SFTP</span>';
                        }else if(data == 2){
                            return '<span class="label label-warning">FTP</span>';
                        }else{
                            return '<span class="label label-danger">未知</span>';
                        }
                    },
                    "targets":  3
                },
                {
                    "render": function ( data, type, row ) {
                        return data == 1 ? '<span class="label label-success">有效</span>' : '<span class="label label-danger">无效</span>';
                    },
                    "targets":  6
                }
            ],
            "order": [[1, 'desc']]
        });
    }
    //清除表单数据
    function clearForm() {
        $("#node-form").find(':input[name]:not(:radio)').val('');
        $(":radio[name='nodeStatus'][value='1']").iCheck('check');
        $(":radio[name='nodeType'][value='1']").iCheck('check');
    }
    //锁定表格字段
    function lockForm() {
    }
    //填充表单
    function fixFormData(data) {
        $("#primKey").val(data.id);
        $("#nodeIp").val(data.ip);
        $("#nodePort").val(data.port);
        $("#nodeLoginUser").val(data.user);
        $("#nodeLoginPass").val(data.pass);
        $("#nodeDefaultPath").val(data.defaultPath);
        $("#nodeDataxPath").val(data.dataxPath);
        $(":radio[name='nodeType'][value='"+data.type+"']").iCheck('check');
        $(":radio[name='nodeStatus'][value='"+data.status+"']").iCheck('check');
    }
    //获取表单数据
    function getFormData() {
        return {
            id : $("#primKey").val(),
            ip : $("#nodeIp").val(),
            port : $("#nodePort").val(),
            user : $("#nodeLoginUser").val(),
            pass : $("#nodeLoginPass").val(),
            type : $(":radio[name='nodeType']:checked").val(),
            status : $(":radio[name='nodeStatus']:checked").val(),
            defaultPath : $("#nodeDefaultPath").val() || '/',
            dataxPath : $("#nodeDataxPath").val()
        }
    }
    //表单验证
    function verifyFormData(formData) {
        if(formData.ip.trim().length == 0){
            common.alertError('请填写节点IP！');
            return false;
        }
        if(formData.port.trim().length == 0){
            common.alertError('请填写节点端口！');
            return false;
        }
        if(formData.user.trim().length == 0){
            common.alertError('请填写登录用户名！');
            return false;
        }
        if(formData.pass.trim().length == 0){
            common.alertError('请填写登录密码！');
            return false;
        }
        return true;
    }
    node.init();
})(jQuery, window, document);
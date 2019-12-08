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
        $("#nodeInfoTitle").html("编辑节点【"+rowData.dsId+"】");
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
        $.post(rootPath + "/node/test",{nodeId : nodeId}, function (data) {
            if(data.flag){
                common.alertSuc('节点连接成功');
            }else{
                common.alertError(data.msg);
            }
        }, "json");


    };

    node.save = function() {
        var formData = getFormData();
        if(verifyFormData(formData)){
            $.post(rootPath + "/node/save", formData,  function (data) {
                if(data.flag){
                    common.alertSuc('节点保存成功', function () {
                        $("#nodeWin").modal('hide');
                        dtable.reload(table);
                    });
                }else{
                    common.alertError(data.msg);
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
                    common.alertSuc('成功删除：'+ data.msg + '个节点', function () {
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
                { "data": "nodeId"},
                { "data": "nodeIp"},
                { "data": "nodePort"},
                { "data": "nodeType"},
                { "data": "nodeLoginUser"},
                { "data": "nodeLoginPass"},
                { "data": "nodeStatus"},
                { "data": "nodeDefaultPath"}
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
        $("#primKey").val(data.nodeId);
        $("#nodeIp").val(data.nodeIp);
        $("#nodePort").val(data.nodePort);
        $("#nodeLoginUser").val(data.nodeLoginUser);
        $("#nodeLoginPass").val(data.nodeLoginPass);
        $("#nodeDefaultPath").val(data.nodeDefaultPath);
        $("#nodeDataxPath").val(data.nodeDataxPath);
        $(":radio[name='nodeType'][value='"+data.nodeType+"']").iCheck('check');
        $(":radio[name='nodeStatus'][value='"+data.nodeStatus+"']").iCheck('check');
    }
    //获取表单数据
    function getFormData() {
        return {
            nodeId : $("#primKey").val(),
            nodeIp : $("#nodeIp").val(),
            nodePort : $("#nodePort").val(),
            nodeLoginUser : $("#nodeLoginUser").val(),
            nodeLoginPass : $("#nodeLoginPass").val(),
            nodeType : $(":radio[name='nodeType']:checked").val(),
            nodeStatus : $(":radio[name='nodeStatus']:checked").val(),
            nodeDefaultPath : $("#nodeDefaultPath").val() || '/',
            nodeDataxPath : $("#nodeDataxPath").val()
        }
    }
    //表单验证
    function verifyFormData(formData) {
        if(formData.nodeIp.trim().length == 0){
            common.alertError('请填写节点IP！');
            return false;
        }
        if(formData.nodePort.trim().length == 0){
            common.alertError('请填写节点端口！');
            return false;
        }
        if(formData.nodeLoginUser.trim().length == 0){
            common.alertError('请填写登录用户名！');
            return false;
        }
        if(formData.nodeLoginPass.trim().length == 0){
            common.alertError('请填写登录密码！');
            return false;
        }
        return true;
    }
    node.init();
})(jQuery, window, document);
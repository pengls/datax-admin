<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>数据同步管理</title>

<#import "../common/common.macro.ftl" as netCommon>
<@netCommon.commonStyle />
<link rel="stylesheet" href="${request.contextPath}/adminlte/plugins/datatables/dataTables.bootstrap.css">

</head>
<body class="hold-transition skin-blue sidebar-mini">
	<div class="wrapper">
		
		<@netCommon.commonHeader />

		<@netCommon.commonLeft "node" />

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    Datax节点管理
                    <small>节点列表</small>
                </h1>
            </section>
            <section class="content container-fluid">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box box-widget">
                            <div class="box-header with-border">
                                <div class="user-block">
                                    <label class="form-inline"><input type="text" class="form-control" id="globalSearch" placeholder="全局搜索(支持正则匹配)" /></label>
                                    <button type='button' class='btn btn-default' onclick="node.add();"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;新增</button>
                                    <button type='button' class='btn btn-default' onclick="node.edit();"><i class="fa fa-edit"></i>&nbsp;&nbsp;&nbsp;&nbsp;编辑</button>
                                    <button type='button' class='btn btn-default' onclick="node.del();"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;删除</button>
                                    <button type='button' class='btn btn-default' onclick="node.test();"><i class="fa fa-database"></i>&nbsp;&nbsp;&nbsp;&nbsp;节点测试</button>
                                </div>
                                <!-- /.box-tools -->
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table id="nodeTable" class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" data-flag="icheck" class="square-blue" allcheck="true" /></th>
                                        <th>IP</th>
                                        <th>端口</th>
                                        <th>类型</th>
                                        <th>登录用户</th>
                                        <th>登录密码</th>
                                        <th>状态</th>
                                        <th>默认路径</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    </tbody>
                                </table>
                            </div>
                            <!-- /.box-body -->
                        </div>
                        <!-- /.box -->
                    </div>
                    <!-- / .col -->
                </div>
                <!-- /.row -->
            </section>
            <!-- /.content -->
        </div>
        <!-- /.content-wrapper -->

		<@netCommon.commonFooter />

	</div>
	<!-- ./wrapper -->

    <div id="nodeWin" class="modal fade in" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <li class="fa fa-remove"></li>
                    </button>
                    <h5 class="modal-title" id="nodeInfoTitle"></h5>
                </div>
                <div class="modal-body">
                    <form id="node-form" name="node-form" class="form-horizontal bv-form" novalidate="novalidate">
                        <input type="hidden" name="primKey" id="primKey" />
                        <div class="box-body">
                            <div class="col-md-6">
                                <div class="form-group has-feedback">
                                    <label for="nodeIp" class="col-sm-4 control-label">节点IP</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="nodeIp" name="nodeIp" placeholder="IP" />
                                    </div>
                                </div>
                                <div class="form-group has-feedback">
                                    <label for="nodePort" class="col-sm-4 control-label">节点端口</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="nodePort" name="nodePort" placeholder="端口" />
                                    </div>
                                </div>
                                <div class="form-group has-feedback">
                                    <label class="col-sm-4 control-label">类型</label>
                                    <div class="col-sm-8">
                                        <label class="control-label">
                                            <input type="radio" name="nodeType" data-flag="icheck" class="square-blue" value="1"/>
                                            SFTP
                                        </label> &nbsp;
                                        <label class="control-label">
                                            <input type="radio" name="nodeType" data-flag="icheck" class="square-blue" value="2"/>
                                            FTP
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="nodeDefaultPath" class="col-sm-4 control-label">默认JOB存储路径</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="nodeDefaultPath" name="nodeDefaultPath" placeholder="默认用户路径" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="nodeLoginUser" class="col-sm-4 control-label">登录用户名</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="nodeLoginUser" name="nodeLoginUser" placeholder="登录用户名" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-4 control-label">状态</label>
                                    <div class="col-sm-8">
                                        <label class="control-label">
                                            <input type="radio" name="nodeStatus" data-flag="icheck" class="square-blue" value="1"/>
                                            有效
                                        </label> &nbsp;
                                        <label class="control-label">
                                            <input type="radio" name="nodeStatus" data-flag="icheck" class="square-blue" value="-1"/>
                                            无效
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="nodeLoginPass" class="col-sm-4 control-label">登录密码</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="nodeLoginPass" name="nodeLoginPass" placeholder="登录密码" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="nodeDefaultPath" class="col-sm-4 control-label">Datax安装路径</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="nodeDataxPath" name="nodeDataxPath" placeholder="Datax安装路径" />
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer text-right">
                            <button type="button" class="btn btn-default" data-btn-type="cancel" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" data-btn-type="save" onclick="node.save();">提交</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
	

	<@netCommon.commonScript/>

    <script src="${request.contextPath}/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
    <script src="${request.contextPath}/adminlte/plugins/daterangepicker/moment.min.js"></script>
    <script src="${request.contextPath}/datax_admin/js/node.js"></script>
</body>
</html>

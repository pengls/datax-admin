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

		<@netCommon.commonLeft "dts" />

        <!-- Content Wrapper. Contains page content -->
        <div class="content-wrapper">
            <!-- Content Header (Page header) -->
            <section class="content-header">
                <h1>
                    数据源管理
                    <small>数据源列表</small>
                </h1>
            </section>
            <section class="content container-fluid">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="box box-widget">
                            <div class="box-header with-border">
                                <div class="user-block" id="operBtns">
                                    <label class="form-inline"><input type="text" class="form-control" id="globalSearch" placeholder="全局搜索(支持正则匹配)" /></label>
                                    <button type='button' class='btn btn-default' onclick="dts.add();"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;新增</button>
                                    <button type='button' class='btn btn-default' onclick="dts.edit();"><i class="fa fa-edit"></i>&nbsp;&nbsp;&nbsp;&nbsp;编辑</button>
                                    <button type='button' class='btn btn-default' onclick="dts.del();"><i class="fa fa-trash-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;删除</button>
                                    <button type='button' class='btn btn-default' onclick="dts.test();"><i class="fa fa-database"></i>&nbsp;&nbsp;&nbsp;&nbsp;数据源测试</button>
                                </div>
                                <!-- /.box-tools -->
                            </div>
                            <!-- /.box-header -->
                            <div class="box-body">
                                <table id="dsTable" class="table table-bordered table-striped">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" data-flag="icheck" class="square-blue" allcheck="true" /></th>
                                        <th>数据源名称</th>
                                        <th>用户名</th>
                                        <th>密码</th>
                                        <th>类型</th>
                                        <th>URL</th>
                                        <th>状态</th>
                                        <th>创建时间</th>
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

    <div id="dtsWin" class="modal fade in" aria-labelledby="myModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        <li class="fa fa-remove"></li>
                    </button>
                    <h5 class="modal-title" id="dtsInfoTitle"></h5>
                </div>
                <div class="modal-body">
                    <form id="dts-form" name="dts-form" class="form-horizontal bv-form" novalidate="novalidate">
                        <input type="hidden" name="primKey" id="primKey" />
                        <div class="box-body">
                            <div class="col-md-6">
                                <div class="form-group has-feedback">
                                    <label for="dsDesc" class="col-sm-4 control-label">数据源名称</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="dsDesc" name="dsDesc" placeholder="数据源名称" />
                                    </div>
                                </div>
                                <div class="form-group has-feedback">
                                    <label for="dsPass" class="col-sm-4 control-label">密码</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="dsPass" name="dsPass" placeholder="密码" />
                                    </div>
                                </div>
                                <div class="form-group has-feedback">
                                    <label class="col-sm-4 control-label">状态</label>
                                    <div class="col-sm-8">
                                        <label class="control-label">
                                            <input type="radio" name="dsStatus" data-flag="icheck" class="square-blue" value="1"/>
                                            有效
                                        </label> &nbsp;
                                        <label class="control-label">
                                            <input type="radio" name="dsStatus" data-flag="icheck" class="square-blue" value="-1"/>
                                            无效
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group" id="createtimeDiv">
                                    <label for="createTime" class="col-sm-4 control-label">创建时间</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="createTime" name="createTime" placeholder="创建时间" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dsSchema" class="col-sm-4 control-label">Schema</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="dsSchema" name="dsSchema" placeholder="Schema" />
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group has-feedback">
                                    <label for="dsUser" class="col-sm-4 control-label">用户名</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="dsUser" name="dsUser" placeholder="用户名" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="dsUrl" class="col-sm-4 control-label">JDBC URL</label>
                                    <div class="col-sm-8">
                                        <input type="text" class="form-control" id="dsUrl" name="dsUrl" placeholder="JDBC URL" />
                                    </div>
                                </div>
                                <div class="form-group has-feedback" id="dsTypeDiv">
                                    <label for="dsType" class="col-sm-4 control-label">类型(自动识别)</label>
                                    <div class="col-sm-8">
                                        <label class="control-label">
                                            <input type="radio" name="dsType" data-flag="icheck" class="square-blue" value="mysql"/>
                                            Mysql
                                        </label>
                                        <label class="control-label">
                                            <input type="radio" name="dsType" data-flag="icheck" class="square-blue" value="oracle"/>
                                            Oracle
                                        </label>
                                        <label class="control-label">
                                            <input type="radio" name="dsType" data-flag="icheck" class="square-blue" value="postgresql"/>
                                            PostgreSQL
                                        </label>
                                        <label class="control-label">
                                            <input type="radio" name="dsType" data-flag="icheck" class="square-blue" value="sqlserver"/>
                                            SqlServer
                                        </label>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer text-right">
                            <button type="button" class="btn btn-default" data-btn-type="cancel" data-dismiss="modal">取消</button>
                            <button type="button" class="btn btn-primary" data-btn-type="save" onclick="dts.save();">提交</button>
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
    <script src="${request.contextPath}/datax_admin/js/dts.js"></script>
</body>
</html>

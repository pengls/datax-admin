/**
 * @author： pengl
 * @Date：2017/10/16 22:43
 * @Description：公共引入脚本
 */
(function ($, window, document) {
    window.common = window.common || {};

    /** 获取项目根路径 **/
    common.root = function(){
        return "/datax-admin";
    };
    /**
     * @Description：模态消息提示框（正常大小）
     * @param：type ：default / warning / info / danger / success
     * @author：pengl
     * @Date：2017/10/16 22:05
     */
    common.model = function(title, msg, type){
        $('#myModal').removeClass("modal-warning modal-info modal-danger modal-success");
        type = type=='default'?'':type;
        $('#myModal').addClass('modal-' + type);
        $('#myModal .modal-dialog');
        $('#myModal .modal-title').html(title);
        $('#myModal .modal-body').html(msg);
        $('#myModal').modal('show');
    };
    /**
     * @Description：模态消息提示框（小型弹窗）
     * @param：type ：default / warning / info / danger / success
     * @author：pengl
     * @Date：2017/10/16 22:05
     */
    common.minModel = function(title, msg, type){
        $('#myModal').removeClass("modal-warning modal-info modal-danger modal-success");
        type = type=='default'?'':type;
        $('#myModal').addClass('modal-' + type).addClass('bs-example-modal-sm');
        $('#myModal .modal-dialog').addClass('modal-sm');
        $('#myModal .modal-title').html(title);
        $('#myModal .modal-body').html(msg);
        $('#myModal').modal('show');
    };
    /**
     * @Description：Noty消息框简单封装
     * @param：type ：alert, success, error, warning, info
     * @author：pengl
     * @Date：2017/10/16 22:05
     */
    common.noty = function(option){
        var defaultOption = {
            type : "info",
            text : "默认消息",
            layout : "topCenter",
            theme : "metroui",
            timeout : 4500
        };
        option = $.extend(true,{}, defaultOption, option);
        return new Noty(option).show();
    };
    
    common.alertError = function (msg, timeout) {
        if(!timeout){
            timeout = 2500;
        }
        layer.msg(msg, {icon: 2, time: timeout, shade: 0.3});
    };
    
    common.alertSuc = function (msg, suc) {
        layer.msg(msg, {icon: 1, time: 2000, shade: 0.3}, suc);
    };
    
    /** 图形验证码更换 **/
    common.kaptchaImage = function(imageId){
        if(!imageId){
            imageId = 'googleCode';
        }
        $('#'+imageId).hide().attr('src', common.root()+'/defultKaptcha?' + Math.floor(Math.random()*1000) ).fadeIn();
    };
    /**
     * 初始化ichecks
     */
    common.initIcheck = function(){
        var ichecks = $('body').find('[data-flag="icheck"]');
        if (ichecks.length > 0) {
            ichecks.each(function (){
                var cls = $(this).attr("class") ? $(this).attr("class") : "square-green";
                $(this).iCheck({
                    checkboxClass: 'icheckbox_' + cls,
                    radioClass: 'iradio_' + cls
                });
            });
        }
    }
    /**
     * 初始化select2
     */
    common.initSelect2 = function(){
        $(".select2").select2({
            theme: "classic"
        })
    };
    /**
     * 初始化tooltip
     */
    common.initTooltip = function(){
        $('[data-toggle="tooltip"]').tooltip();
    };
    /** 初始化表单插件 **/
    common.initForm = function(){
        common.initIcheck();
        common.initSelect2();
        common.initTooltip
    };
})(jQuery, window, document);

var loadi;
$(function(){
    //设置公共ajax请求参数
    $.ajaxSetup({
        contentType:"application/x-www-form-urlencoded;charset=utf-8",
        timeout: 30000,
        beforeSend : function(){
            loadi = layer.msg('数据处理中..', {
                icon: 16,
                shade: 0.3,
                time: -1,
                offset: '200px'
            });
        },
        complete:function(XMLHttpRequest,textStatus){
            layer.close(loadi);
            var respstatus = XMLHttpRequest.getResponseHeader("respstatus");
            if(respstatus == "nologin"){
                layer.msg('当前会话已失效，请重新登录后操作！', {icon: 2, time: 300000, shade: 0.3}, function () {
                    //window.location.href = common.root() + "/login/index";
                });
            }
        },
        error : function(){
            layer.msg("网络异常", {offset: '200px'});
        }
    });
    common.initForm();
});
/*
 * Translated default messages for the jQuery validation plugin.
 * Locale: ZH (Chinese, 中文 (Zhōngwén), 汉语, 漢語)
 */
$.extend($.validator.messages, {
    required: "这是必填字段",
    remote: "请修正此字段",
    email: "请输入有效的电子邮件地址",
    url: "请输入有效的网址",
    date: "请输入有效的日期",
    dateISO: "请输入有效的日期 (YYYY-MM-DD)",
    number: "请输入有效的数字",
    digits: "只能输入数字",
    creditcard: "请输入有效的信用卡号码",
    equalTo: "你的输入不相同",
    extension: "请输入有效的后缀",
    maxlength: $.validator.format("最多可以输入 {0} 个字符"),
    minlength: $.validator.format("最少要输入 {0} 个字符"),
    rangelength: $.validator.format("请输入长度在 {0} 到 {1} 之间的字符串"),
    range: $.validator.format("请输入范围在 {0} 到 {1} 之间的数值"),
    max: $.validator.format("请输入不大于 {0} 的数值"),
    min: $.validator.format("请输入不小于 {0} 的数值")
});
$.validator.setDefaults({
    errorPlacement : function(error,element) {
        _Login.errorTip(error[0].innerHTML);
    }
});
/**
 * 手机号码验证
 */
$.validator.addMethod("isPhone", function(value, element) {
    var reg = /^(13[0-9]|15[1|0|2|3|5|6|7|8|9]|14[5|7]|18[0|1|2|3|4|5|8|6|9|7]|177|170|173|199)\d{8}$/;
    return this.optional(element) || (reg.test(value));
}, "请填写正确的手机号码");
/**
 * 电信手机号码验证
 */
$.validator.addMethod("isDXPhone", function(value, element) {
    var reg = /^(18[0|1|9]|177|153|133|173|199)\d{8}$/;
    return this.optional(element) || (reg.test(value));
}, "请填写正确的手机号码");
/** 去除空格 **/
String.prototype.trim = function(){
    if(this==null||this==""){
        return "";
    }else{
        var local = "";
        for(var i=0;i<this.length;i++){
            if(this.charCodeAt(i)!=32){
                local += this.charAt(i);
            }
        }
        return local;
    }
};
/** 拼接字符串 **/
function StringBuffer(str){
    this._string_ = new Array();
    this._string_.push(str);
};
StringBuffer.prototype.append = function(str){
    this._string_.push(str);
};
StringBuffer.prototype.toString = function(){
    return this._string_.join('');
};

/**
 * 日期格式化
 * @param format
 * @returns
 */
Date.prototype.format = function(format) {
    var o = {
        "M+" : this.getMonth() + 1,
        "d+" : this.getDate(),
        "h+" : this.getHours(),
        "m+" : this.getMinutes(),
        "s+" : this.getSeconds(),
        "q+" : Math.floor((this.getMonth() + 3) / 3),
        "S" : this.getMilliseconds()
    }
    if (/(y+)/.test(format))
        format = format.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(format))
            format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
                : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};

function logout(){
    layer.confirm('您确定要退出登录？', {
        icon: 3
    }, function(){
        $.post(common.root() + "/getLogoutUrl",function (data) {
            if(data.flag){
                var url = data.msg;
                if(url.indexOf('?')<0){
                    window.location.href=url+"?service="+encodeURIComponent(window.location.href.replace("#",""));
                }else{
                    window.location.href=url+"&service="+encodeURIComponent(window.location.href.replace("#",""));
                }
            }else{
                common.alertError('获取登出连接失败！');
            }
        }, "json");
    }, function(){

    });
}
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@include file="/WEB-INF/common/common.jsp" %>
<html lang="en">
<%@include file="/WEB-INF/common/head.jsp"%>

<!--TIPS-->
<!--You may remove all ID or Class names which contain "demo-", they are only used for demonstration. -->
<body>
<div id="container" class="effect aside-float aside-bright mainnav-lg">

    <!--NAVBAR-->
    <!--===================================================-->
   <%@include file="/WEB-INF/common/header.jsp"%>

    <!--jqgrid相关文件开始-->
    <script src="js/nifty-demo.min.js"></script>
    <link href="lib/Guriddo_jqGrid_JS_5.2.1/css/ui.jqgrid-bootstrap.css" rel="stylesheet">
    <script src="lib/Guriddo_jqGrid_JS_5.2.1/js/i18n/grid.locale-cn.js"></script>
    <script src="lib/Guriddo_jqGrid_JS_5.2.1/js/jquery.jqGrid.min.js"></script>
    <script src="js/bootstrap.min.js"></script>
    <script>
        $.jgrid.defaults.width =1900;
        $.jgrid.defaults.styleUI = 'Bootstrap';
    </script>
    <!--jqgrid相关文件结束-->

    <!--===================================================-->
    <!--END NAVBAR-->
    <div class="boxed">
        <!--CONTENT CONTAINER-->
        <!--===================================================-->
        <div id="content-container">
            <!--Page Title-->
            <!-- 内容标题 -->
            <div id="page-title">
                <h1 class="page-header text-overflow">房源信息</h1>
                <!--Searchbox-->
                <div class="searchbox">
                    <div class="input-group custom-search-form">
                        <input type="text" class="form-control" placeholder="Search..">
                            <span class="input-group-btn">
                                <button class="text-muted" type="button"><i class="demo-pli-magnifi-glass"></i></button>
                            </span>
                    </div>
                </div>
            </div>
            <!--End page title-->
            <!--Page content-->
            <!--页面内容->
            <!--===================================================-->
            <div id="page-content">
                <div class="btn-group">
                    <button  onclick="addTask()" class="btn btn-success">加入任务</button>
                    <span class="pull-left btn btn-primary btn-file">
					                        上传房源<form id="fileForm" action="${ctx}/houseInfo/upload?countName=${param.countName}" method="post" enctype="multipart/form-data">
                                                        <input type="file" id="file" name="file">
                                                    </form>
					                        </span>
                    <button  onclick="delAll()" class="btn btn-danger">删除所有</button>
                </div>

                       <table id="jqGrid"></table>
                       <div id="jqGridPager"></div>
                <script type="text/javascript">

                    function delAll(){
                        bootbox.confirm("确定删除?", function(result) {
                            if (result) {
                                location.href='${ctx}/houseInfo/delAll';
                            }
                        });
                    }

                    //添加任务
                    function addTask(){
                        //获取选中行
                       var ids= $("#jqGrid").jqGrid('getGridParam',"selarrrow");
                        if(ids.length>0){
                            for(var i in ids){
                                $.get('${ctx}/task/add?houseInfoId='+ids[i]);
                            }
                            $.niftyNoty({
                                type: 'success',
                                message : '<strong>操作成功!</strong> ',
                                container : 'floating',
                                timer : 500
                            });
                        }
                    }

                    $(function(){
                        //上传
                        $('#file').change(function(){
                            if(this.value){
                                document.getElementById('fileForm').submit();
                            }

                        });


                        //初始化表格
                        $("#jqGrid").jqGrid({
                            url: '${ctx}/houseInfo/list?countName=${param.countName}',
                            mtype: "POST",
                            styleUI : 'Bootstrap',
                            datatype: "json",
                            colModel: [
                                { label: '房源编号', name: 'houseInfoId', key: true, width: 75 ,editable: true,
                                    editoptions : {readonly : true}
                                },
                                { label: '所属账号', name: 'countName', width: 150 ,editable: true},
                                { label: '小区', name: 'xiaoqu', width: 150,editable: true },
                                { label: '标题', name: 'biaoti', width: 150 ,editable: true},
                                { label:'租金', name: 'zujin', width: 150 ,editable: true},
                                { label:'上传时间', name: 'createTime', width: 220 ,editable: true},
                                { label:'室', name: 'shi', width: 150 ,editable: true},
                                { label:'厅', name: 'ting', width: 150 ,editable: true},
                                { label:'卫', name: 'wei', width: 150 ,editable: true},
                                { label:'楼', name: 'lou', width: 150 ,editable: true},
                                { label:'层', name: 'ceng', width: 150 ,editable: true},
                                { label:'电梯', name: 'dianti', width: 150 ,editable: true},
                                { label:'朝向', name: 'chaoxiang', width: 150 ,editable: true},
                                { label:'配置', name: 'peizhi', width: 150 ,editable: true},
                                { label:'面积', name: 'mianji', width: 150 ,editable: true},
                                { label:'户数', name: 'hushu', width: 150 ,editable: true},
                                { label:'主次卧', name: 'zhuciwo', width: 150 ,editable: true},
                                { label:'图片地址', name: 'imgUrl', width: 150 ,editable: true},
                                { label:'模板', name: 'template', width: 150 ,editable: true}
                                
                            ],
                            viewrecords: true,
                            loadonce: true,
                            height: 900,
                            rowNum: 200,
                            rownumbers: true,
                            rownumWidth: 25,
                            multiselect: true,
                            pager: "#jqGridPager"
                        });

                        $('#jqGrid').navGrid('#jqGridPager',
                                // the buttons to appear on the toolbar of the grid
                                { edit: true, add: true, del: true, search: false, refresh: false, view: false, position: "left", cloneToTop: false },
                                // options for the Edit Dialog
                                {
                                    url: '${ctx}/houseInfo/update',
                                    editCaption: "编辑",
                                    topinfo:'编辑房源信息',
                                    recreateForm: true,
                                    checkOnUpdate : true,
                                    checkOnSubmit : true,
                                    closeAfterEdit: true,
                                    onclickSubmit:function(params, posdata){

                                    },
                                    errorTextFormat: function (data) {
                                        return 'Error: ' + data.responseText
                                    }
                                },
                                // options for the Add Dialog
                                {
                                    url: '${ctx}/houseInfo/update',
                                    closeAfterAdd: true,
                                    recreateForm: true,
                                    errorTextFormat: function (data) {
                                        return 'Error: ' + data.responseText
                                    }
                                },
                                // options for the Delete Dailog
                                {
                                    url: '${ctx}/houseInfo/delete',
                                    errorTextFormat: function (data) {
                                        return 'Error: ' + data.responseText
                                    }
                                });

                        $('#jqGrid').navGrid('#jqGridPager').navButtonAdd('#pager1',{
                            caption:"Add",
                            buttonicon:"ui-icon-add",
                            onClickButton: function(){
                                alert("Adding Row");
                            },
                            position:"last"
                        });



                    });





                    /*$(document).ready(function () {
                        $("#jqGrid").jqGrid({
                            url: 'data.json',
                            // we set the changes to be made at client side using predefined word clientArray
                            editurl: 'clientArray',
                            datatype: "json",
                            colModel: [
                                {
                                    label: 'Customer ID',
                                    name: 'CustomerID',
                                    width: 75,
                                    key: true,
                                    editable: true,
                                    editrules : { required: true}
                                },
                                {
                                    label: 'Company Name',
                                    name: 'CompanyName',
                                    width: 140,
                                    editable: true // must set editable to true if you want to make the field editable
                                },
                                {
                                    label : 'Phone',
                                    name: 'Phone',
                                    width: 100,
                                    editable: true
                                },
                                {
                                    label: 'Postal Code',
                                    name: 'PostalCode',
                                    width: 80,
                                    editable: true
                                },
                                {
                                    label: 'City',
                                    name: 'City',
                                    width: 140,
                                    editable: true
                                }
                            ],
                            sortname: 'CustomerID',
                            sortorder : 'asc',
                            loadonce: true,
                            viewrecords: true,
                            height: 450,
                            rowNum: 50,
                            pager: "#jqGridPager"
                        });

                        $('#jqGrid').navGrid('#jqGridPager',
                                // the buttons to appear on the toolbar of the grid
                                { edit: true, add: true, del: true, search: false, refresh: false, view: false, position: "left", cloneToTop: false },
                                // options for the Edit Dialog
                                {
                                    editCaption: "The Edit Dialog",
                                    recreateForm: true,
                                    checkOnUpdate : true,
                                    checkOnSubmit : true,
                                    closeAfterEdit: true,
                                    errorTextFormat: function (data) {
                                        return 'Error: ' + data.responseText
                                    }
                                },
                                // options for the Add Dialog
                                {
                                    closeAfterAdd: true,
                                    recreateForm: true,
                                    errorTextFormat: function (data) {
                                        return 'Error: ' + data.responseText
                                    }
                                },
                                // options for the Delete Dailog
                                {
                                    errorTextFormat: function (data) {
                                        return 'Error: ' + data.responseText
                                    }
                                });
                    });*/
                </script>


            </div>
            <!--End page content-->
        </div>
        <!--===================================================-->
        <!--END CONTENT CONTAINER-->
        <!--ASIDE-->
        <!--===================================================-->
        <%@include file="/WEB-INF/common/right.jsp"%>
        <!--===================================================-->
        <!--END ASIDE-->
        <!--MAIN NAVIGATION-->
        <!--===================================================-->
        <!--左侧页面-->
        <%@include file="/WEB-INF/common/left.jsp"%>
        <!--===================================================-->
        <!--END MAIN NAVIGATION-->
    </div>

    <!-- FOOTER -->
    <!-- 底部页面 -->
    <!--===================================================-->
    <%@include file="/WEB-INF/common/footer.jsp"%>
    <!--===================================================-->
    <!-- END FOOTER -->

    <!-- SCROLL PAGE BUTTON -->
    <!--===================================================-->
    <button class="scroll-top btn">
        <i class="pci-chevron chevron-up"></i>
    </button>
    <!--===================================================-->
</div>
<!--===================================================-->
<!-- END OF CONTAINER -->
<!-- SETTINGS - DEMO PURPOSE ONLY -->
<!-- 设置 -->
<!--===================================================-->
<%@include file="/WEB-INF/common/settings.jsp"%>
<!--===================================================-->
<!-- END SETTINGS -->
<%--<script>
    function addTask(countName,houseInfoId) {
        $.get('${ctx}/task/add?countName='+countName+'&houseInfoId='+houseInfoId);
    }
</script>--%>
</body>
</html>

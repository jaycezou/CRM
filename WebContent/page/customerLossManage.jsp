<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>客户关系管理系统</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/jquery-easyui-1.3.3/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/jquery-easyui-1.3.3/themes/icon.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery-easyui-1.3.3/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery-easyui-1.3.3/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jquery-easyui-1.3.3/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript">
	function searchCustomerLoss(){
		$("#dg").datagrid("load",{
			"cusName":$("#s_cusName").val(),
			"cusManager":$("#s_cusManager").val(),
			"state":$("#s_state").combobox("getValue")
		});
	}
	var url = "";
	function openCustomerLossAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle", "添加客户流失信息");
		url = "${pageContext.request.contextPath}/customerLoss/save.do";
	}
	function openCustomerLossModifyDialog(){
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length!=1){
			$.messager.alert("系统提示", "请选择一条需要编辑的数据！");
			return;
		}
		var row = selectedRows[0];
		$("#fm").form("load", row);
		url = "${pageContext.request.contextPath}/customerLoss/save.do?id="+row.id;
		$("#dlg").dialog("open").dialog("setTitle", "编辑客户流失信息");
	}
	function saveCustomerLoss(){
		$("#fm").form("submit", {
			url: url,
			onSubmit: function(){
				if($("#roleName").combobox("getValue")==""){
					$.messager.alert("系统提示", "请选择客户流失角色");
					return false;
				}
				return $("#fm").form("validate");
			},
			success: function(result){
				var result = eval("("+result+")");
				if(result.success){
					$.messager.alert("系统提示", "保存成功");
					closeCustomerLossDialog();
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示", "保存失败");
					return;
				}
			}
		});
	}
	function deleteCustomerLoss(){
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length==0){
			$.messager.alert("系统提示", "请选择需要删除的数据！");
			return;
		}
		var idsStr = [];
		for(var i=0; i<selectedRows.length; i++){
			idsStr.push(selectedRows[i].id);
		}
		var ids = idsStr.join(",");
		$.messager.confirm("系统提示","您确定要删除这<font color='red'>"+selectedRows.length+"</font>条数据吗？",function(r){
			if(r){
				$.ajax({
					url: '${pageContext.request.contextPath}/customerLoss/delete.do',
					type: 'post',
					data: {ids: ids},
					dataType: 'json',
					success: function(result){
						if(result.success){
							$.messager.alert("系统提示", "数据已经成功删除！");
							$("#dg").datagrid("reload");
						}else{
							$.messager.alert("系统提示", "数据删除失败，请联系管理员！");
						}
					}
				});
			}
		});
	}
	function closeCustomerLossDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	function resetValue(){
		$("#cusName").val("");
		$("#password").val("");
		$("#trueName").val("");
		$("#email").val("");
		$("#phone").val("");
		$("#roleName").combobox("setValue","");
	}
	function formatState(value, row, index){
		if(value==1){
			return "确认流失";
		}else if(value==0){
			return "暂缓流失";
		}
	}
	function formatAction(value, row, index){
		if(row.state==1){
			return "客户确认流失";
		}else{
			return "<a href='javascript:openCustomerReprieve("+row.id+")'>暂缓流失</a>";
		}
	}
	function openCustomerReprieve(id){
		window.parent.openTab('客户流失暂缓措施管理','customerReprieveManage.jsp?lossId='+id,'icon-khlsgl');
	}
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="客户流失管理" class="easyui-datagrid" data-options="fitColumns:true,pagination:true,rownumbers:true,
	url:'${pageContext.request.contextPath }/customerLoss/list.do',fit:true,toolbar:'#tb'">
	<thead>
		<tr>
		<th field="cb" checkbox="true" align="center"></th>
		<th field="id" width="50" align="center">编号</th>
		<th field="cusNo" width="50" align="center" hidden="true">客户编号</th>
		<th field="cusName" width="100" align="cusName">客户名称</th>
		<th field="cusManager" width="100" align="center">客户经理</th>
		<th field="lastOrderTime" width="100" align="center">上次下单日期</th>
		<th field="confirmLossTime" width="100" align="center">确认流失日期</th>
		<th field="state" width="800" align="center" formatter="formatState">状态</th>
		<th field="lossReason" width="200" align="center">流失原因</th>
		<th field="act" width="200" align="center" formatter="formatAction">流失原因</th>
		</tr>
	</thead>	
</table>
<div id="tb">
	<div>
		<a href="javascript: openCustomerLossAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript: openCustomerLossModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript: deleteCustomerLoss()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		&nbsp;客户名称：<input type="text" id="s_cusName" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()">
		&nbsp;客户经理：<input type="text" id="s_cusManager" size="20" onkeydown="if(event.keyCode==13) searchCustomerLoss()">
		&nbsp;状态：<select id="s_state" class="easyui-combobox" editable="false" panelHeight="auto" >
			<option value="">请选择...</option>
			<option value="0">暂缓流失</option>
			<option value="1">确认流失</option>
			</select>
		<a href="javascript:searchCustomerLoss()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
</div>
</body>
</html>
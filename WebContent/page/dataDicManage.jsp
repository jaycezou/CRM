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
	function searchDataDic(){
		$("#dg").datagrid("load",{
			"dataDicName":$("#s_dataDicName").combobox("getValue"),
			"dataDicValue":$("#s_dataDicValue").val()
		});
	}
	$(function(){
		$("#s_dataDicName").combobox({
			onSelect: function(e){
				if(e.dataDicName=="请选择···")
					$(this).combobox("setValue","");
			}
		});
	});
	var url = "";
	function openDataDicAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle", "添加数据字典信息");
		url = "${pageContext.request.contextPath}/dataDic/save.do";
	}
	function openDataDicModifyDialog(){
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length!=1){
			$.messager.alert("系统提示", "请选择一条需要编辑的数据！");
			return;
		}
		var row = selectedRows[0];
		$("#fm").form("load", row);
		url = "${pageContext.request.contextPath}/dataDic/save.do?id="+row.id;
		$("#dlg").dialog("open").dialog("setTitle", "编辑数据字典信息");
	}
	function saveDataDic(){
		var dataDicName = $("#dataDicName").combobox("getText");
		var dataDicValue = $("#dataDicValue").val();
		if($("#dataDicName").combobox("getText")==""){
			$.messager.alert("系统提示", "请选择或填写数据字典名");
			return false;
		}else if(!$("#fm").form("validate")){
			return false;
		}
		$.post(url,{dataDicName:dataDicName,dataDicValue:dataDicValue},function(result){
			var result = eval("("+result+")");
			if(result.success){
				$.messager.alert("系统提示", "保存成功");
				closeDataDicDialog();
				$("#dg").datagrid("reload");
			}else{
				$.messager.alert("系统提示", "保存失败");
				return;
			}
		});
	}
	function deleteDataDic(){
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
					url: '${pageContext.request.contextPath}/dataDic/delete.do',
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
	function closeDataDicDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	function resetValue(){
		$("#dataDicName").combobox("setValue","");
		$("#dataDicValue").val("");
	}
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="数据字典管理" class="easyui-datagrid" data-options="fitColumns:true,pagination:true,rownumbers:true,
	url:'${pageContext.request.contextPath }/dataDic/list.do',fit:true,toolbar:'#tb'">
	<thead>
		<tr>
		<th field="cb" checkbox="true" align="center"></th>
		<th field="id" width="50" align="center">编号</th>
		<th field="dataDicName" width="200" align="center">数据字典名</th>
		<th field="dataDicValue" width="200" align="center">数据字典值</th>
		</tr>
	</thead>	
</table>
<div id="tb">
	<div>
		<a href="javascript: openDataDicAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">添加</a>
		<a href="javascript: openDataDicModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript: deleteDataDic()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		&nbsp;数据字典名：<input type="text" class="easyui-combobox" id="s_dataDicName" data-options="panelHeight:'auto',editable:false,valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath }/dataDic/findDataDicName.do'">
		&nbsp;&nbsp;数据字典值：<input type="text" id="s_dataDicValue" size="20" onkeydown="if(event.keyCode==13) searchDataDic()">
		<a href="javascript:searchDataDic()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
</div>
<div id="dlg" class="easyui-dialog" style="width: 620px; height:150px; padding: 10px 20px;" closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
	<table cellpadding="5px;">
		<tr>
			<td>数据字典名：</td>
			<td>
			<input type="text" class="easyui-combobox" id="dataDicName" name="dataDicName" data-options="panelHeight:'auto',required:true,valueField:'dataDicName',textField:'dataDicName',url:'${pageContext.request.contextPath }/dataDic/findDataDicName.do'">&nbsp;<font color="red">*</font>
			</td>
			<td>&nbsp;</td>
			<td>数据字典值：</td>
			<td><input type="text" id="dataDicValue" name="dataDicValue" class="easyui-validatebox" required="true" style="width: 154px;">&nbsp;<font color="red">*</font></td>
		</tr>
	</table>
	</form>
</div>
<div id="dlg-buttons">
	<a href="javascript: saveDataDic()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript: closeDataDicDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>
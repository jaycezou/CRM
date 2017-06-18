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
<script type="text/javascript" src="${pageContext.request.contextPath }/js/common.js"></script>
<script type="text/javascript">
	$(function(){
		$("#assignMan").combobox({
			onSelect: function(record){
				if(record!=""){
					$("#assignTime").val(getCurrentDateTime());
				}else{
					$("#assignTime").val("");
				}
			}
		});
	});
	function searchSaleChance(){
		$("#dg").datagrid("load",{
			"customerName":$("#s_customerName").val(),
			"overView":$("#s_overView").val(),
			"createMan":$("#s_createMan").val(),
			"state":$("#s_state").combobox("getValue")
		});
	}
	function formatState(value, row, index){
		if(value==1){
			return "已分配";
		}else{
			return "未分配";
		}
	}
	var url = "";
	function openSaleChanceAddDialog(){
		$("#dlg").dialog("open").dialog("setTitle", "添加销售机会信息");
		//创建人赋值
		$("#createMan").val("${currentUser.trueName}");
		$("#createTime").val(getCurrentDateTime());
		url = "${pageContext.request.contextPath}/saleChance/save.do";
	}
	function openSaleChanceModifyDialog(){
		var selectedRows = $("#dg").datagrid("getSelections");
		if(selectedRows.length!=1){
			$.messager.alert("系统提示", "请选择一条需要编辑的数据！");
			return;
		}
		var row = selectedRows[0];
		$("#fm").form("load", row);
		url = "${pageContext.request.contextPath}/saleChance/save.do?id="+row.id;
		$("#dlg").dialog("open").dialog("setTitle", "编辑销售机会信息");
	}
	function saveSaleChance(){
		$("#fm").form("submit", {
			url: url,
			onSubmit: function(){
				return $("#fm").form("validate");
			},
			success: function(result){
				var result = eval("("+result+")");
				if(result.success){
					$.messager.alert("系统提示", "保存成功");
					closeSaleChanceDialog();
					$("#dg").datagrid("reload");
				}else{
					$.messager.alert("系统提示", "保存失败");
					return;
				}
			}
		});
	}
	function deleteSaleChance(){
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
					url: '${pageContext.request.contextPath}/saleChance/delete.do',
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
	function closeSaleChanceDialog(){
		$("#dlg").dialog("close");
		resetValue();
	}
	function resetValue(){
		$("#customerName").val("");
		$("#chanceSource").val("");
		$("#linkMan").val("");
		$("#linkPhone").val("");
		$("#cgjl").numberbox("setValue","");
		$("#overView").val("");
		$("#description").val("");
		$("#createMan").val("");
		$("#createTime").val("");
		$("#assignMan").combobox("setValue","");
		$("#assignTime").val("");
	}
</script>
</head>
<body style="margin: 1px;">
<table id="dg" title="销售机会信息管理" class="easyui-datagrid" data-options="fitColumns:true,pagination:true,rownumbers:true,
	url:'${pageContext.request.contextPath }/saleChance/list.do',fit:true,toolbar:'#tb'">
	<thead>
		<tr>
		<th field="cb" checkbox="true" align="center"></th>
		<th field="id" width="50" align="center">编号</th>
		<th field="chanceSource" width="100" align="center" hidden="true">机会来源</th>
		<th field="customerName" width="50" align="center">客户名称</th>
		<th field="cgjl" width="100" align="center" hidden="true">成功几率</th>
		<th field="overView" width="200" align="center">概要</th>
		<th field="linkMan" width="100" align="center">联系人</th>
		<th field="linkPhone" width="100" align="center" hidden="true">联系电话</th>
		<th field="description" width="100" align="center" hidden="true">机会描述</th>
		<th field="createMan" width="100" align="center">创建人 </th>
		<th field="createTime" width="100" align="center">创建时间</th>
		<th field="assignMan" width="100" align="center" hidden="true">指派人</th>
		<th field="assignTime" width="100" align="center" hidden="true">指派时间</th>
		<th field="state" width="100" align="center" formatter="formatState">状态</th><!-- 分配状态 0 未分配 1 已分配 -->
		<th field="devResult" width="100" align="center" hidden="true">客户开发状态</th><!-- 客户开发状态 0 未开发 1 开发中 2 开发成功 3 开发失败 -->
		</tr>
	</thead>	
</table>
<div id="tb">
	<div>
		<a href="javascript: openSaleChanceAddDialog()" class="easyui-linkbutton" iconCls="icon-add" plain="true">创建</a>
		<a href="javascript: openSaleChanceModifyDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">修改</a>
		<a href="javascript: deleteSaleChance()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
	</div>
	<div>
		&nbsp;客户名称：<input type="text" id="s_customerName" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()">
		&nbsp;概要：<input type="text" id="s_overView" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()">
		&nbsp;创建人：<input type="text" id="s_createMan" size="20" onkeydown="if(event.keyCode==13) searchSaleChance()">
		&nbsp;状态：<select type="text" class="easyui-combobox" id="s_state" editable="false" panelHeight="auto" style="width:100px;">
				<option value="">请选择···</option>
				<option value="0">未分配</option>
				<option value="1">已分配</option>
			</select>
		<a href="javascript:searchSaleChance()" class="easyui-linkbutton" iconCls="icon-search" plain="true">查询</a>
	</div>
</div>
<div id="dlg" class="easyui-dialog" style="width: 700px; height:450px; padding: 10px 20px;" closed="true" buttons="#dlg-buttons">
	<form id="fm" method="post">
	<table cellpadding="8px;">
		<tr>
			<td>客户名称：</td>
			<td><input type="text" id="customerName" name="customerName" class="easyui-validatebox" required="true">&nbsp;<font color="red">*</font></td>
			<td>&nbsp;</td>
			<td>机会来源：</td>
			<td><input type="text" id="chanceSource" name="chanceSource" class="easyui-validatebox"></td>
		</tr>
		<tr>
			<td>联系人：</td>
			<td><input type="text" id="linkMan" name="linkMan" class="easyui-validatebox"></td>
			<td>&nbsp;</td>
			<td>联系电话：</td>
			<td><input type="text" id="linkPhone" name="linkPhone" class="easyui-validatebox"></td>
		</tr>
		<tr>
			<td>成功几率（%）：</td>
			<td>
				<input type="text" id="cgjl" name="cgjl" class="easyui-numberbox" data-options="min:0, max:100, required:true">&nbsp;<font color="red">*</font>
			</td>
			<td colspan="3">&nbsp;</td>
		</tr>
		<tr>
			<td>概要：</td>
			<td colspan="4">
				<input type="text" id="overView" name="overView" style="width: 450px">
			</td>
		</tr>
		<tr>
			<td>机会描述：</td>
			<td colspan="4">
				<textarea id="description" name="description" rows="5" cols="62"></textarea>
			</td>
		</tr>
		<tr>
			<td>创建人：</td>
			<td><input readonly type="text" id="createMan" name="createMan">&nbsp;<font color="red">*</font></td>
			<td>&nbsp;</td>
			<td>创建时间：</td>
			<td><input readonly type="text" id="createTime" name="createTime">&nbsp;<font color="red">*</font></td>
		</tr>
		<tr>
			<td>指派给：</td>
			<td>
			<input type="text" class="easyui-combobox" id="assignMan" name="assignMan" data-options="panelHeight:'auto',editable:false,valueField:'trueName',textField:'trueName',url:'${pageContext.request.contextPath }/user/customerManagerComboboxList.do'">
			</td>
			<td>&nbsp;</td>
			<td>指派时间：</td>
			<td>
			<input readonly="readonly" type="text" id="assignTime" name="assignTime"></td>
			</td>
		</tr>
	</table>
	</form>
</div>
<div id="dlg-buttons">
	<a href="javascript: saveSaleChance()" class="easyui-linkbutton" iconCls="icon-ok">保存</a>
	<a href="javascript: closeSaleChanceDialog()" class="easyui-linkbutton" iconCls="icon-cancel">关闭</a>
</div>
</body>
</html>
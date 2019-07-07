        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="GBK"%>
        <div class="easyui-layout" data-options="fit:true">
        <!-- Begin of toolbar -->
        <div id="wu-toolbar">
        <div class="wu-toolbar-button">
        <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">���</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">�޸�</a>
        <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">ɾ��</a>

        </div>
        <div class="wu-toolbar-search">

        <label>�˵����ƣ�</label><input class="wu-text" style="width:100px">
        <a href="#" class="easyui-linkbutton" iconCls="icon-search">����</a>
        </div>
        </div>
        <!-- End of toolbar -->
        <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
        </div>
        <!-- Begin of easyui-dialog -->
        <div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
                <form id="add-form" method="post">
                <table>
                <tr>
                <td width="60" align="right">�˵�����:</td>
                <td><input type="text" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'����д�˵�����'"/>

                </td>
                </tr>
                <tr>
                <td align="right">�ϼ��˵�:</td>
                <td>
                <select name = "parentId" class="easyui-combobox" panelHeight="auto" style="width:268px">

                <c:forEach items="${topList }" var="menu">
                        <option value="${menu.id}">${menu.name}</option>
                </c:forEach>

                </select>
                </td>
                </tr>
                <tr>
                <td align="right">�˵�URL:</td>
                <td><input type="text" name="url" class="wu-text" /></td>
                </tr>
                <tr>
                <td valign="top" align="right">�˵�ͼ��:</td>
                <td><input type="text" name="icon" class="wu-text " data-options="required:true, missingMessage:'����д�˵�ͼ��'"/>
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="selectIcon()" plain="true">ѡ��</a>
                </td>
                </tr>
                </table>
                </form>
                </div>
                <!--ѡ��ͼ�굯��-->

                <div id="select-icon-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:750px; padding:10px;">

               <table id="icons-table">

                </table>

                </div>
        <!-- End of easyui-dialog -->
        <script type="text/javascript">
        /**
        * Name ��Ӽ�¼
        */
        function add(){
        var validate = $("#add-form").form("validate");
        if(!validate){
        $.messager.alert("��Ϣ����","���������������!","warning");
        return;
        }
        var data = $('#add-form').serialize();
        $.ajax({
        url:'../admin/menu/add',
            dataType:'json',
            type:"post",
            data:data,

        success:function(data){
        if(data.type == 'success'){
        $.messager.alert('��Ϣ��ʾ','��ӳɹ���','info');
        $('#add-dialog').dialog('close');
        }
        else
        {
        $.messager.alert('��Ϣ��ʾ',data.msg,'warning');
        }
        }
        });
        }

        function selectIcon() {
                if($("#icons-table").children().length <= 0){
                $.ajax({
                url:"../admin/menu/get_icons",
                dateType:'json',
                type:'post',

                success:function(data){
                if(data.type == 'success'){
                var icons  = data.content;
                var table = '';
                for(var i=0;i<icons.length;i++){
                var tbody = '<td><a class="easyui-linkbutton" iconCls="' + icons[i] + '">����</td>" '
                if(i == 0){
                table += '<tr>' + tbody;
                continue;
                }
                if(i != 0 && i%12 ==0){
                table += tbody + '</tr><tr>';
                continue;
                }
                table +=  tbody;
                }
                table += '</tr>';

                }
                else
                {
                $.messager.alert('��Ϣ��ʾ',data.msg,'warning');
                }
                }
                });
                }
                $.ajax({
                url:"../admin/menu/get_icons",
                dateType:'json',
                type:'post',

                success:function(data){
                if(data.type == 'success'){
                    var icons  = data.content;
                    var table = '';
                    for(var i=0;i<icons.length;i++){
                           var tbody = '<td><a class="easyui-linkbutton" iconCls="' + icons[i] + '"></td>" '
                           if(i == 0){
                                table += '<tr>' + tbody;
                           }
                           if(i != 0 && i%12 ==0){
                                table += tbody + '</tr><tr>'
                           }
                    }
                    table += '</tr>';
                $("#icons-table").append(table);

                }
                else
                {
                $.messager.alert('��Ϣ��ʾ',data.msg,'warning');
                }
                }
                });
                $('#select-icon-dialog').dialog({
                closed: false,
                modal:true,
                title: "ѡ��icon��Ϣ",
                buttons: [{
                text: 'ȷ��',
                iconCls: 'icon-ok',
                handler: add
                }, {
                text: 'ȡ��',
                iconCls: 'icon-cancel',
                handler: function () {
                $('#add-dialog').dialog('close');
                }
                }]
                });
                }

        /**
        * Name �޸ļ�¼
        */
        function edit(){
        $('#wu-form-2').form('submit', {
        url:'',
        success:function(data){
        if(data){
        $.messager.alert('��Ϣ��ʾ','�ύ�ɹ���','info');
        $('#wu-dialog-2').dialog('close');
        }
        else
        {
        $.messager.alert('��Ϣ��ʾ','�ύʧ�ܣ�','info');
        }
        }
        });
        }

        /**
        * Name ɾ����¼
        */
        function remove(){
        $.messager.confirm('��Ϣ��ʾ','ȷ��Ҫɾ���ü�¼��', function(result){
        if(result){
        var items = $('#wu-datagrid-2').datagrid('getSelections');
        var ids = [];
        $(items).each(function(){
        ids.push(this.productid);
        });
        //alert(ids);return;
        $.ajax({
        url:'',
        data:'',
        success:function(data){
        if(data){
        $.messager.alert('��Ϣ��ʾ','ɾ���ɹ���','info');
        }
        else
        {
        $.messager.alert('��Ϣ��ʾ','ɾ��ʧ�ܣ�','info');
        }
        }
        });
        }
        });
        }

        /**
        * Name ����Ӵ���
        */
        function openAdd(){
        $('#add-form').form('clear');
        $('#add-dialog').dialog({
        closed: false,
        modal:true,
        title: "��Ӳ˵���Ϣ",
        buttons: [{
        text: 'ȷ��',
        iconCls: 'icon-ok',
        handler: add
        }, {
        text: 'ȡ��',
        iconCls: 'icon-cancel',
        handler: function () {
        $('#add-dialog').dialog('close');
        }
        }]
        });
        }

        /**
        * Name ���޸Ĵ���
        */
        function openEdit(){
        $('#wu-form-2').form('clear');
        var item = $('#wu-datagrid-2').datagrid('getSelected');
        //alert(item.productid);return;
        $.ajax({
        url:'',
        data:'',
        success:function(data){
        if(data){
        $('#wu-dialog-2').dialog('close');
        }
        else{
        //��ֵ
        $('#wu-form-2').form('load', data)
        }
        }
        });
        $('#wu-dialog-2').dialog({
        closed: false,
        modal:true,
        title: "�޸���Ϣ",
        buttons: [{
        text: 'ȷ��',
        iconCls: 'icon-ok',
        handler: edit
        }, {
        text: 'ȡ��',
        iconCls: 'icon-cancel',
        handler: function () {
        $('#wu-dialog-2').dialog('close');
        }
        }]
        });
        }

        /**
        * Name ��ҳ������
        */
        function pagerFilter(data){
        if (typeof data.length == 'number' && typeof data.splice == 'function'){// is array
        data = {
        total: data.length,
        rows: data
        }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
        onSelectPage:function(pageNum, pageSize){
        opts.pageNumber = pageNum;
        opts.pageSize = pageSize;
        pager.pagination('refresh',{pageNumber:pageNum,pageSize:pageSize});
        dg.datagrid('loadData',data);
        }
        });
        if (!data.originalRows){
        data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
        }

        /**
        * Name ��������
        */
        $('#data-datagrid').datagrid({
        url:'../admin/menu/list',
        loadFilter:pagerFilter,
        rownumbers:true,
        singleSelect:true,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        fit:true,
        columns:[[
        { checkbox:true},
        { field:'name',title:'�˵�����',width:100,sortable:true},
        { field:'URL',title:'�˵�URL',width:180,sortable:true},
        { field:'icon',title:'ͼ��icon',width:100},
        { field:'listprice',title:'listprice',width:100},
        { field:'attr1',title:'attr1',width:100},
        { field:'itemid',title:'itemid',width:100},
        { field:'status',title:'status',width:100}
        ]]
        });
        </script>
/**
 *
 */
package org.adempiere.model.tree.spi.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.service.IDeveloperModeBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Menu;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.MTree;
import org.compiere.model.MTreeNode;
import org.compiere.model.X_AD_Menu;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;

/**
 * @author tsa
 *
 */
public class MenuTreeSupport extends DefaultPOTreeSupport
{
	@Override
	public String getNodeInfoSelectSQL(final MTree tree, final List<Object> sqlParams)
	{
		final Properties ctx = tree.getCtx();

		final StringBuilder sqlDeveloperMode = new StringBuilder();
		if (Services.get(IDeveloperModeBL.class).isEnabled())
		{
			sqlDeveloperMode
					.append("\n, (select min(t.TableName) from AD_Tab tt inner join AD_Table t on (t.AD_Table_ID=tt.AD_Table_ID) where tt.AD_Window_ID=AD_Menu.AD_Window_ID and tt.SeqNo=10 and tt.IsActive='Y') as AD_Window_TableName");
			sqlDeveloperMode.append("\n, (select coalesce(process.JasperReport, process.Classname) from AD_Process process where process.AD_Process_ID=AD_Menu.AD_Process_ID) as AD_Process_ClassName");
			sqlDeveloperMode.append("\n, (select form.Classname from AD_Form form where form.AD_Form_ID=AD_Menu.AD_Form_ID) as AD_Form_ClassName");
			sqlDeveloperMode.append("\n");
		}

		final StringBuilder sql = new StringBuilder();
		final boolean base = Env.isBaseLanguage(ctx, "AD_Menu");
		if (base)
		{
			sql.append("SELECT AD_Menu.AD_Menu_ID AS Node_ID"
					+ ", AD_Menu.Name"
					+ ", AD_Menu.Description"
					+ ", AD_Menu.IsSummary"
					+ ", AD_Menu.Action"
					+ ", NULL AS " + COLUMNNAME_PrintColor
					+ ", AD_Menu.AD_Window_ID, AD_Menu.AD_Process_ID, AD_Menu.AD_Form_ID, AD_Menu.AD_Workflow_ID, AD_Menu.AD_Task_ID, AD_Menu.AD_Workbench_ID"
					+ ", AD_Menu.InternalName "
					+ ", AD_Menu."+I_AD_Menu.COLUMNNAME_IsCreateNew
					+ ", AD_Menu."+I_AD_Menu.COLUMNNAME_WEBUI_NameBrowse
					+ ", AD_Menu."+I_AD_Menu.COLUMNNAME_WEBUI_NameNew
					+ ", AD_Menu."+I_AD_Menu.COLUMNNAME_WEBUI_NameNewBreadcrumb
					+ " " + sqlDeveloperMode
					+ "\n FROM AD_Menu ");
		}
		else
		{
			sql.append("SELECT AD_Menu.AD_Menu_ID AS Node_ID"
					+ ", t.Name"
					+ ", t.Description"
					+ ", AD_Menu.IsSummary"
					+ ", AD_Menu.Action"
					+ ", NULL AS " + COLUMNNAME_PrintColor
					+ ", AD_Menu.AD_Window_ID, AD_Menu.AD_Process_ID, AD_Menu.AD_Form_ID, AD_Menu.AD_Workflow_ID, AD_Menu.AD_Task_ID, AD_Menu.AD_Workbench_ID"
					+ ", AD_Menu.InternalName "
					+ ", AD_Menu."+I_AD_Menu.COLUMNNAME_IsCreateNew
					+ ", COALESCE(t." + I_AD_Menu.COLUMNNAME_WEBUI_NameBrowse + ", AD_Menu." + I_AD_Menu.COLUMNNAME_WEBUI_NameBrowse + ") AS " + I_AD_Menu.COLUMNNAME_WEBUI_NameBrowse
					+ ", COALESCE(t." + I_AD_Menu.COLUMNNAME_WEBUI_NameNew + ", AD_Menu." + I_AD_Menu.COLUMNNAME_WEBUI_NameNew + ") AS " + I_AD_Menu.COLUMNNAME_WEBUI_NameNew
					+ ", COALESCE(t." + I_AD_Menu.COLUMNNAME_WEBUI_NameNewBreadcrumb + ", AD_Menu." + I_AD_Menu.COLUMNNAME_WEBUI_NameNewBreadcrumb + ") AS " + I_AD_Menu.COLUMNNAME_WEBUI_NameNewBreadcrumb
					+ " " + sqlDeveloperMode
					+ "\n FROM AD_Menu, AD_Menu_Trl t");
		}
		sql.append(" WHERE 1=1 ");
		if (!base)
		{
			sql.append("\n AND AD_Menu.AD_Menu_ID=t.AD_Menu_ID AND t.AD_Language=").append(DB.TO_STRING(Env.getAD_Language(ctx)));
		}
		if (!tree.isEditable())
		{
			sql.append("\n AND AD_Menu.IsActive='Y' ");
		}

		//
		// Per element (Window, Process etc) where clauses
		final StringBuilder windowSql = new StringBuilder("1=1");
		final List<Object> windowSqlParams = new ArrayList<>();
		final StringBuilder processSql = new StringBuilder("1=1");
		final List<Object> processSqlParams = new ArrayList<>();
		final StringBuilder workflowSql = new StringBuilder("1=1");
		final List<Object> workflowSqlParams = new ArrayList<>();
		final StringBuilder formSql = new StringBuilder("1=1");
		final List<Object> formSqlParams = new ArrayList<>();
		final StringBuilder taskSql = new StringBuilder("1=1");
		final List<Object> taskSqlParams = new ArrayList<>();

		//
		// Do not show Beta
		final IUserRolePermissions userRolePermissions = tree.getUserRolePermissions();
		final boolean useBetaFunctions = userRolePermissions.hasPermission(IUserRolePermissions.PERMISSION_UseBetaFunctions);
		if (!useBetaFunctions)
		{
			// task 09088: the client doesn't "want" to use beta functions and the role doesn't override this, so we filter out features that are not marked as beta
			windowSql.append("AND w.").append(I_AD_Window.COLUMNNAME_IsBetaFunctionality).append("=?");
			windowSqlParams.add(false);
			processSql.append("AND p.").append(I_AD_Process.COLUMNNAME_IsBetaFunctionality).append("=?");
			processSqlParams.add(false);
			workflowSql.append("AND wf.").append(I_AD_Workflow.COLUMNNAME_IsBetaFunctionality).append("=?");
			workflowSqlParams.add(false);
			formSql.append("AND f.").append(I_AD_Form.COLUMNNAME_IsBetaFunctionality).append("=?");
			formSqlParams.add(false);
		}

		//
		// In R/O Menu - Show only defined Forms
		if (!tree.isEditable())
		{
			final boolean isSwingTree = tree.isClientTree();
			formSql.append(" AND f.").append(isSwingTree ? "Classname" : "JSPURL").append(" IS NOT NULL");
		}

		//
		// Apply per element filters
		{
			sql.append("\n AND (AD_Menu.AD_Window_ID IS NULL OR EXISTS (select 1 from AD_Window w where AD_Menu.AD_Window_ID=w.AD_Window_ID AND ").append(windowSql).append("))");
			sqlParams.addAll(windowSqlParams);
			//
			sql.append("\n AND (AD_Menu.AD_Process_ID IS NULL OR EXISTS (select 1 from AD_Process p where AD_Menu.AD_Process_ID=p.AD_Process_ID AND ").append(processSql).append("))");
			sqlParams.addAll(processSqlParams);
			//
			sql.append("\n AND (AD_Menu.AD_Workflow_ID IS NULL OR EXISTS (select 1 from AD_Workflow wf where AD_Menu.AD_Workflow_ID=wf.AD_Workflow_ID AND ").append(workflowSql).append("))");
			sqlParams.addAll(workflowSqlParams);
			//
			sql.append("\n AND (AD_Menu.AD_Form_ID IS NULL OR EXISTS (select 1 from AD_Form f where AD_Menu.AD_Form_ID=f.AD_Form_ID AND ").append(formSql).append("))");
			sqlParams.addAll(formSqlParams);
			//
			sql.append("\n AND (AD_Menu.AD_Task_ID IS NULL OR EXISTS (select 1 from AD_Task t where AD_Menu.AD_Task_ID=t.AD_Task_ID AND ").append(taskSql).append("))");
			sqlParams.addAll(taskSqlParams);
		}

		return sql.toString();
	}

	@Override
	public MTreeNode loadNodeInfo(final MTree tree, final ResultSet rs) throws SQLException
	{
		final MTreeNode info = super.loadNodeInfo(tree, rs);

		// me16: also load the menu entry's internal name
		final String internalName = rs.getString(I_AD_Menu.COLUMNNAME_InternalName);
		info.setInternalName(internalName);

		final String action = rs.getString(I_AD_Menu.COLUMNNAME_Action);
		info.setImageIndicator(action);

		final int AD_Window_ID = rs.getInt(I_AD_Menu.COLUMNNAME_AD_Window_ID);
		final int AD_Process_ID = rs.getInt(I_AD_Menu.COLUMNNAME_AD_Process_ID);
		final int AD_Form_ID = rs.getInt(I_AD_Menu.COLUMNNAME_AD_Form_ID);
		final int AD_Workflow_ID = rs.getInt(I_AD_Menu.COLUMNNAME_AD_Workflow_ID);
		final int AD_Task_ID = rs.getInt(I_AD_Menu.COLUMNNAME_AD_Task_ID);
		final boolean isCreateNewRecord = DisplayType.toBoolean(rs.getString(I_AD_Menu.COLUMNNAME_IsCreateNew));
		final String webuiNameBrowse = rs.getString(I_AD_Menu.COLUMNNAME_WEBUI_NameBrowse);
		final String webuiNameNew = rs.getString(I_AD_Menu.COLUMNNAME_WEBUI_NameNew);
		final String webuiNameNewBreadcrumb = rs.getString(I_AD_Menu.COLUMNNAME_WEBUI_NameNewBreadcrumb);
		
		info.setAD_Window_ID(AD_Window_ID);
		info.setAD_Process_ID(AD_Process_ID);
		info.setAD_Form_ID(AD_Form_ID);
		info.setAD_Workflow_ID(AD_Workflow_ID);
		info.setAD_Task_ID(AD_Task_ID);
		info.setIsCreateNewRecord(isCreateNewRecord);
		info.setWEBUI_NameBrowse(webuiNameBrowse);
		info.setWEBUI_NameNew(webuiNameNew);
		info.setWEBUI_NameNewBreadcrumb(webuiNameNewBreadcrumb);

		if (info.getAllowsChildren() || action == null)
		{
			return info;
		}

		if (!isCheckRoleAccessWhileLoading())
		{
			return info;
		}

		//
		// Check role access
		final IUserRolePermissions role = tree.getUserRolePermissions();
		Boolean access = null;
		if (X_AD_Menu.ACTION_Window.equals(action))
		{
			access = role.checkWindowAccess(AD_Window_ID);

			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				final String tableName = rs.getString("AD_Window_TableName"); // table name of first window tab
				if (!Check.isEmpty(tableName, true))
				{
					info.setName(info.getName() + " (" + tableName + ")");
				}
			}
		}
		else if (X_AD_Menu.ACTION_Process.equals(action))
		{
			access = role.checkProcessAccess(AD_Process_ID);

			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				final String classname = rs.getString("AD_Process_ClassName");
				if (!Check.isEmpty(classname, true))
				{
					info.setName(info.getName() + " (" + classname + ")");
				}
			}
		}
		else if (X_AD_Menu.ACTION_Report.equals(action))
		{
			access = role.checkProcessAccess(AD_Process_ID);

			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				final String classname = rs.getString("AD_Process_ClassName");
				if (!Check.isEmpty(classname, true))
				{
					info.setName(info.getName() + " (" + classname + ")");
				}
			}
		}
		else if (X_AD_Menu.ACTION_Form.equals(action))
		{
			access = role.checkFormAccess(AD_Form_ID);

			if (Services.get(IDeveloperModeBL.class).isEnabled())
			{
				final String classname = rs.getString("AD_Form_ClassName");
				if (!Check.isEmpty(classname, true))
				{
					info.setName(info.getName() + " (" + classname + ")");
				}
			}
		}
		else if (X_AD_Menu.ACTION_WorkFlow.equals(action))
		{
			access = role.checkWorkflowAccess(AD_Workflow_ID);
		}
		else if (X_AD_Menu.ACTION_Task.equals(action))
		{
			access = role.checkTaskAccess(AD_Task_ID);
		}
		//
		if (access == null // rw or ro for Role
				&& !tree.isEditable())
		{
			return null;
		}
		
		return info;
	}
}

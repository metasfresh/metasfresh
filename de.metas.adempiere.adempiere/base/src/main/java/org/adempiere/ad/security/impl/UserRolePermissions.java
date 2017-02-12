package org.adempiere.ad.security.impl;

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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.security.ISecurityRuleEngine;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsBuilder;
import org.adempiere.ad.security.TableAccessLevel;
import org.adempiere.ad.security.permissions.Access;
import org.adempiere.ad.security.permissions.Constraint;
import org.adempiere.ad.security.permissions.Constraints;
import org.adempiere.ad.security.permissions.ElementPermissions;
import org.adempiere.ad.security.permissions.GenericPermissions;
import org.adempiere.ad.security.permissions.LoginOrgConstraint;
import org.adempiere.ad.security.permissions.OrgPermission;
import org.adempiere.ad.security.permissions.OrgPermissions;
import org.adempiere.ad.security.permissions.OrgResource;
import org.adempiere.ad.security.permissions.Permission;
import org.adempiere.ad.security.permissions.StartupWindowConstraint;
import org.adempiere.ad.security.permissions.TableColumnPermissions;
import org.adempiere.ad.security.permissions.TablePermissions;
import org.adempiere.ad.security.permissions.TableRecordPermissions;
import org.adempiere.ad.security.permissions.UserPreferenceLevelConstraint;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.IRolePermLoggingBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.AccessSqlParser;
import org.compiere.model.I_AD_PInstance_Log;
import org.compiere.model.MPrivateAccess;
import org.compiere.process.DocAction;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import de.metas.document.engine.IDocActionOptionsContext;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;

@Immutable
class UserRolePermissions implements IUserRolePermissions
{
	private static final transient Logger logger = LogManager.getLogger(UserRolePermissions.class);

	/** Permissions name (i.e. role name) */
	private final String name;
	private final int AD_Role_ID;
	private final UserRolePermissionsIncludesList includes;
	private final ImmutableSet<Integer> all_AD_Role_IDs;
	/** User */
	private final int AD_User_ID;
	private final int AD_Client_ID;
	private final TableAccessLevel userLevel;

	/** Positive List of Organizational Access */
	private final OrgPermissions orgPermissions;

	/** List of Table Access */
	private final TablePermissions tablePermissions;
	/** List of Column Access */
	private final TableColumnPermissions columnPermissions;
	/** List of Record Access (including dependent permissions) */
	private final TableRecordPermissions recordPermissions;

	/** Table Access Info */
	private final TablesAccessInfo tablesAccessInfo = TablesAccessInfo.instance;

	/** Window Access */
	private final ElementPermissions windowPermissions;
	/** Process Access */
	private final ElementPermissions processPermissions;
	/** Task Access */
	private final ElementPermissions taskPermissions;
	/** Workflow Access */
	private final ElementPermissions workflowPermissions;
	/** Form Access */
	private final ElementPermissions formPermissions;

	private final GenericPermissions miscPermissions;

	private final ConcurrentHashMap<ArrayKey, Set<String>> docActionsAllowed = new ConcurrentHashMap<>();

	/** Permission constraints */
	private final Constraints constraints;

	private final int menu_AD_Tree_ID;

	UserRolePermissions(final UserRolePermissionsBuilder builder)
	{
		super();
		name = builder.getName();
		AD_Role_ID = builder.getAD_Role_ID();
		includes = builder.getUserRolePermissionsIncluded();
		all_AD_Role_IDs = ImmutableSet.copyOf(includes.getAllRoleIdsIncluding(AD_Role_ID));
		AD_User_ID = builder.getAD_User_ID();

		AD_Client_ID = builder.getAD_Client_ID();
		Check.assume(AD_Client_ID >= 0, "AD_Client_ID shall be set but it was {}", AD_Client_ID);

		userLevel = builder.getUserLevel();

		orgPermissions = builder.getOrgPermissions();

		tablePermissions = builder.getTablePermissions();
		columnPermissions = builder.getColumnPermissions();
		recordPermissions = builder.getRecordPermissions();
		windowPermissions = builder.getWindowPermissions();
		processPermissions = builder.getProcessPermissions();
		taskPermissions = builder.getTaskPermissions();
		workflowPermissions = builder.getWorkflowPermissions();
		formPermissions = builder.getFormPermissions();

		miscPermissions = builder.getMiscPermissions();
		constraints = builder.getConstraints();

		menu_AD_Tree_ID = builder.getMenu_Tree_ID();
	}

	@Override
	public IUserRolePermissionsBuilder asNewBuilder()
	{
		return new UserRolePermissionsBuilder()
				.setAD_Role_ID(getAD_Role_ID())
				.setAlreadyIncludedRolePermissions(includes)
				.setAD_Client_ID(getAD_Client_ID())
				.setAD_User_ID(getAD_User_ID())
				.setUserLevel(userLevel)
				.setMenu_AD_Tree_ID(getMenu_Tree_ID())
				//
				.setOrgPermissions(orgPermissions)
				.setTablePermissions(tablePermissions)
				.setColumnPermissions(columnPermissions)
				.setRecordPermissions(recordPermissions)
				.setWindowPermissions(windowPermissions)
				.setProcessPermissions(processPermissions)
				.setTaskPermissions(taskPermissions)
				.setWorkflowPermissions(workflowPermissions)
				.setFormPermissions(formPermissions)
				.setMiscPermissions(miscPermissions)
				//
				.setConstraints(constraints)
				//
				;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("name", name)
				.add("AD_Role_ID", AD_Role_ID)
				.add("AD_User_ID", AD_User_ID)
				.add("AD_Client_ID", AD_Client_ID)
				.toString();
	}

	@Override
	public String toStringX()
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("@AD_Role_ID@").append("=").append(getName())
				.append(" - ").append("@IsCanExport@").append("=@").append(DisplayType.toBooleanString(isCanExport())).append("@")
				.append(" - ").append("@IsCanReport@").append("=@").append(DisplayType.toBooleanString(isCanReport())).append("@") //
				;

		// All included roles
		if (!includes.isEmpty())
		{
			sb.append(Env.NL).append("@Included_Role_ID@: ");
			for (final UserRolePermissionsInclude include : includes)
			{
				sb.append(Env.NL).append(include.getUserRolePermissions().getName());
			}
		}

		sb.append(Env.NL).append(Env.NL);
		Joiner.on(Env.NL + Env.NL)
				.skipNulls()
				.appendTo(sb, miscPermissions, constraints, orgPermissions, tablePermissions, columnPermissions, recordPermissions
		// don't show followings because they could be to big, mainly when is not a manual role:
		// , windowPermissions
		// , processPermissions
		// , taskPermissions
		// , formPermissions
		// , workflowPermissions
		);

		return sb.toString();
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public int getAD_Role_ID()
	{
		return AD_Role_ID;
	}

	@Override
	public Set<Integer> getAll_AD_Role_IDs()
	{
		return all_AD_Role_IDs;
	}

	@Override
	public boolean isAccessAllOrgs()
	{
		return hasPermission(PERMISSION_AccessAllOrgs);
	}

	@Override
	public int getAD_Client_ID()
	{
		return AD_Client_ID;
	}

	@Override
	public boolean hasPermission(final Permission permission)
	{
		return miscPermissions.hasPermission(permission);
	}

	@Override
	public boolean isCanReport()
	{
		return hasPermission(PERMISSION_CanReport);
	}

	@Override
	public boolean isCanExport()
	{
		return hasPermission(PERMISSION_CanExport);
	}

	@Override
	public int getStartup_AD_Form_ID()
	{
		return getConstraint(StartupWindowConstraint.class)
				.or(StartupWindowConstraint.NULL)
				.getAD_Form_ID();
	}

	@Override
	public <T extends Constraint> Optional<T> getConstraint(final Class<T> constraintType)
	{
		return constraints.getConstraint(constraintType);
	}

	@Override
	public TableAccessLevel getUserLevel()
	{
		return userLevel;
	}

	@Override
	public boolean isPersonalAccess()
	{
		return hasPermission(PERMISSION_PersonalAccess);
	}

	@Override
	public UserPreferenceLevelConstraint getPreferenceLevel()
	{
		return getConstraint(UserPreferenceLevelConstraint.class)
				.or(UserPreferenceLevelConstraint.NONE);
	}

	/**
	 * Get Logged in user
	 *
	 * @return AD_User_ID user requesting info
	 */
	@Override
	public int getAD_User_ID()
	{
		return AD_User_ID;
	}	// getAD_User_ID

	@Override
	public boolean isSystemAdministrator()
	{
		if (getAD_Role_ID() != SYSTEM_ROLE_ID)
		{
			return false;
		}

		// Shall have at access to system organization
		if (!isOrgAccess(OrgPermission.AD_Org_ID_System, true))
		{
			return false;
		}
		return true;
	}

	/**************************************************************************
	 * Get Client Where Clause Value
	 *
	 * @param rw read write
	 * @return "AD_Client_ID=0" or "AD_Client_ID IN(0,1)"
	 */
	@Override
	public String getClientWhere(final boolean rw)
	{
		// All Orgs - use Client of Role
		if (isAccessAllOrgs())
		{
			if (rw || getAD_Client_ID() == 0)
			{
				return "AD_Client_ID=" + getAD_Client_ID();
			}
			return "AD_Client_ID IN (0," + getAD_Client_ID() + ")";
		}

		// Get Client from Org List
		return orgPermissions.getClientWhere(rw);
	}

	/**
	 * Access to Client
	 *
	 * @param AD_Client_ID client
	 * @param rw read write access
	 * @return true if access
	 */
	@Override
	public boolean isClientAccess(final int AD_Client_ID, final boolean rw)
	{
		if (AD_Client_ID == 0 && !rw)
		{
			return true;
		}
		//
		// Check Access All Orgs:
		if (isAccessAllOrgs())
		{
			// User has access to given AD_Client_ID if the role is defined on that AD_Client_ID
			return getAD_Client_ID() == AD_Client_ID;
		}
		//
		return orgPermissions.isClientAccess(AD_Client_ID, rw);
	}

	@Override
	public int getMenu_Tree_ID()
	{
		return menu_AD_Tree_ID;
	}

	@Override
	public final int getOrg_Tree_ID()
	{
		return orgPermissions.getOrg_Tree_ID();
	}

	private Set<Integer> getOrgAccess(final String tableName, final boolean rw)
	{
		if (isAccessAllOrgs())
		{
			return ORGACCESS_ALL;
		}

		final Set<Integer> adOrgIds = orgPermissions.getOrgAccess(rw);

		Services.get(ISecurityRuleEngine.class).filterOrgs(this, tableName, rw, adOrgIds);

		return adOrgIds;
	}

	@Override
	public Set<OrgResource> getLoginOrgs()
	{
		final LoginOrgConstraint loginOrgConstraint = getConstraint(LoginOrgConstraint.class)
				.or(LoginOrgConstraint.DEFAULT);

		return orgPermissions.getResourcesWithAccessThatMatch(Access.LOGIN, loginOrgConstraint.asOrgResourceMatcher());
	}

	@Override
	public Set<KeyNamePair> getLoginClients()
	{
		final Set<KeyNamePair> clientsList = new TreeSet<>();
		for (final OrgResource orgResource : getLoginOrgs())
		{
			clientsList.add(orgResource.asClientKeyNamePair());
		}

		return clientsList;
	}

	@Deprecated
	@Override
	public String getOrgWhere(final boolean rw)
	{
		return getOrgWhere(null, rw);
	}

	@Override
	public String getOrgWhere(final String tableName, final boolean rw)
	{
		final Set<Integer> adOrgIds = getOrgAccess(tableName, rw);
		if (adOrgIds == ORGACCESS_ALL)
		{
			return "1=1"; // no org filter
		}

		//
		final StringBuilder sb = new StringBuilder();
		final Iterator<Integer> it = adOrgIds.iterator();
		boolean oneOnly = true;
		while (it.hasNext())
		{
			if (sb.length() > 0)
			{
				sb.append(",");
				oneOnly = false;
			}
			sb.append(it.next());
		}
		if (oneOnly)
		{
			if (sb.length() > 0)
			{
				return "AD_Org_ID=" + sb.toString();
			}
			else
			{
				logger.error("No Access Org records");
				return "AD_Org_ID=-1";	// No Access Record
			}
		}
		return "AD_Org_ID IN (" + sb.toString() + ")";
	}	// getOrgWhereValue

	/**
	 * Access to Org
	 *
	 * @param AD_Org_ID org
	 * @param rw read write access
	 * @return true if access
	 */
	@Override
	public boolean isOrgAccess(final int AD_Org_ID, final boolean rw)
	{
		// Readonly access to "*" organization is always granted
		if (AD_Org_ID == OrgPermission.AD_Org_ID_System && !rw)
		{
			return true;
		}

		final Set<Integer> orgs = getOrgAccess(null, rw); // tableName=n/a
		if (orgs == ORGACCESS_ALL)
		{
			return true;
		}
		return orgs.contains(AD_Org_ID);
	}	// isOrgAccess

	@Override
	public String getAD_Org_IDs_AsString()
	{
		return orgPermissions.getAD_Org_IDs_AsString();
	}

	@Override
	public Set<Integer> getAD_Org_IDs_AsSet()
	{
		return orgPermissions.getAD_Org_IDs_AsSet();
	}

	/**
	 * Can Report on table
	 *
	 * @param AD_Table_ID table
	 * @return true if access
	 */
	@Override
	public boolean isCanReport(final int AD_Table_ID)
	{
		if (!isCanReport())   						// Role Level block
		{
			logger.warn("Role denied");
			return false;
		}
		if (!isTableAccess(AD_Table_ID, true))
		{
			return false;
		}
		//
		return tablePermissions.isCanReport(AD_Table_ID);
	}

	/**
	 * Can Export Table
	 *
	 * @param AD_Table_ID
	 * @return true if access
	 */
	@Override
	public boolean isCanExport(final int AD_Table_ID)
	{
		if (!isCanExport())   						// Role Level block
		{
			logger.warn("Role denied");
			return false;
		}
		if (!isTableAccess(AD_Table_ID, true))    // ro=true
		{
			return false;
		}
		if (!isCanReport(AD_Table_ID))
		{
			return false;
		}
		//
		return tablePermissions.isCanExport(AD_Table_ID);
	}

	/**
	 * Access to Table
	 *
	 * @param AD_Table_ID table
	 * @param ro check read only access otherwise read write access level
	 * @return has RO/RW access to table
	 */
	@Override
	public boolean isTableAccess(final int AD_Table_ID, final boolean ro)
	{
		if (!isTableAccessLevel(AD_Table_ID, ro))
		{
			return false;
		}
		return tablePermissions.isTableAccess(AD_Table_ID, ro);
	}

	/**
	 * Access to Table based on Role User Level Table Access Level
	 *
	 * @param AD_Table_ID table
	 * @param ro check read only access otherwise read write access level
	 * @return has RO/RW access to table
	 */
	private boolean isTableAccessLevel(final int AD_Table_ID, final boolean ro)
	{
		if (ro)
		{
			return true;
		}

		final TableAccessLevel roleAccessLevel = tablesAccessInfo.getTableAccessLevel(AD_Table_ID);
		if (roleAccessLevel == null)
		{
			logger.debug("NO - No AccessLevel - AD_Table_ID={}", AD_Table_ID);
			return false;
		}

		final TableAccessLevel userLevel = getUserLevel();
		return roleAccessLevel.hasCommonLevels(userLevel);
	}

	/**
	 * Access to Column
	 *
	 * @param AD_Table_ID table
	 * @param AD_Column_ID column
	 * @param ro read only
	 * @return true if access
	 */
	@Override
	public boolean isColumnAccess(final int AD_Table_ID, final int AD_Column_ID, final boolean ro)
	{
		if (!isTableAccess(AD_Table_ID, ro))
		{
			return false;
		}

		return columnPermissions.isColumnAccess(AD_Table_ID, AD_Column_ID, ro);
	}

	/**
	 * Access to Record (no check of table)
	 *
	 * @param AD_Table_ID table
	 * @param Record_ID record
	 * @param ro read only
	 * @return boolean
	 */
	@Override
	public boolean isRecordAccess(final int AD_Table_ID, final int Record_ID, final boolean ro)
	{
		// if (!isTableAccess(AD_Table_ID, ro)) // No Access to Table
		// return false;
		return recordPermissions.isRecordAccess(AD_Table_ID, Record_ID, ro);
	}

	/**
	 * Get Window Access
	 *
	 * @param AD_Window_ID window
	 * @return null in no access, TRUE if r/w and FALSE if r/o
	 */
	@Override
	public Boolean getWindowAccess(final int AD_Window_ID)
	{
		final Boolean access = checkWindowAccess(AD_Window_ID);
		Services.get(IRolePermLoggingBL.class).logWindowAccess(getAD_Role_ID(), AD_Window_ID, access);
		return access;
	}

	@Override
	public Boolean checkWindowAccess(final int AD_Window_ID)
	{
		final Boolean retValue = windowPermissions.getReadWritePermission(AD_Window_ID);
		return retValue;
	}

	/**
	 * Get Process Access
	 *
	 * @param AD_Process_ID process
	 * @return null in no access, TRUE if r/w and FALSE if r/o
	 */
	@Override
	public Boolean getProcessAccess(final int AD_Process_ID)
	{
		final Boolean access = checkProcessAccess(AD_Process_ID);
		Services.get(IRolePermLoggingBL.class).logProcessAccess(getAD_Role_ID(), AD_Process_ID, access);
		return access;
	}

	@Override
	public Boolean checkProcessAccess(final int AD_Process_ID)
	{
		final Boolean retValue = processPermissions.getReadWritePermission(AD_Process_ID);
		return retValue;
	}

	/**
	 * Get Task Access
	 *
	 * @param AD_Task_ID task
	 * @return null in no access, TRUE if r/w and FALSE if r/o
	 */
	@Override
	public Boolean getTaskAccess(final int AD_Task_ID)
	{
		final Boolean access = checkTaskAccess(AD_Task_ID);
		Services.get(IRolePermLoggingBL.class).logTaskAccess(getAD_Role_ID(), AD_Task_ID, access);
		return access;
	}

	@Override
	public Boolean checkTaskAccess(final int AD_Task_ID)
	{
		final Boolean retValue = taskPermissions.getReadWritePermission(AD_Task_ID);
		return retValue;
	}

	/**
	 * Get Form Access
	 *
	 * @param AD_Form_ID form
	 * @return null in no access, TRUE if r/w and FALSE if r/o
	 */
	@Override
	public Boolean getFormAccess(final int AD_Form_ID)
	{
		final Boolean access = checkFormAccess(AD_Form_ID);
		Services.get(IRolePermLoggingBL.class).logTaskAccess(getAD_Role_ID(), AD_Form_ID, access);
		return access;
	}

	@Override
	public Boolean checkFormAccess(final int AD_Form_ID)
	{
		Boolean retValue = formPermissions.getReadWritePermission(AD_Form_ID);
		retValue = Services.get(ISecurityRuleEngine.class).checkFormAccess(this, retValue, AD_Form_ID);

		//
		return retValue;
	}

	/**
	 * Get Workflow Access
	 *
	 * @param AD_Workflow_ID workflow
	 * @return null in no access, TRUE if r/w and FALSE if r/o
	 */
	@Override
	public Boolean getWorkflowAccess(final int AD_Workflow_ID)
	{
		final Boolean access = checkWorkflowAccess(AD_Workflow_ID);
		Services.get(IRolePermLoggingBL.class).logWorkflowAccess(getAD_Role_ID(), AD_Workflow_ID, access);
		return access;
	}

	@Override
	public Boolean checkWorkflowAccess(final int AD_Workflow_ID)
	{
		final Boolean retValue = workflowPermissions.getReadWritePermission(AD_Workflow_ID);
		return retValue;
	}

	@Override
	public String addAccessSQL(final String sql, final String TableNameIn, final boolean fullyQualified, final boolean rw)
	{
		// Cut off last ORDER BY clause
		final String sqlSelectFromWhere;
		final String sqlOrderByAndOthers;
		final int idxOrderBy = sql.lastIndexOf(" ORDER BY ");
		if (idxOrderBy >= 0)
		{
			sqlSelectFromWhere = sql.substring(0, idxOrderBy);
			sqlOrderByAndOthers = "\n" + sql.substring(idxOrderBy);
		}
		else
		{
			sqlSelectFromWhere = sql;
			sqlOrderByAndOthers = null;
		}

		final String sqlAccessSqlWhereClause = buildAccessSQL(sqlSelectFromWhere, TableNameIn, fullyQualified, rw);
		if (Check.isEmpty(sqlAccessSqlWhereClause, true))
		{
			logger.trace("Final SQL (no access sql applied): {}", sql);
			return sql;
		}

		final String sqlFinal;
		if (sqlOrderByAndOthers == null)
		{
			sqlFinal = sqlSelectFromWhere + " " + sqlAccessSqlWhereClause;
		}
		else
		{
			sqlFinal = sqlSelectFromWhere + " " + sqlAccessSqlWhereClause + sqlOrderByAndOthers;
		}

		logger.trace("Final SQL: {}", sqlFinal);
		return sqlFinal;
	}	// addAccessSQL

	private final String buildAccessSQL(final String sqlSelectFromWhere, final String TableNameIn, final boolean fullyQualified, final boolean rw)
	{
		final StringBuilder sqlAcessSqlWhereClause = new StringBuilder();

		// Parse SQL
		final AccessSqlParser asp = new AccessSqlParser(sqlSelectFromWhere);
		final AccessSqlParser.TableInfo[] ti = asp.getTableInfo(asp.getMainSqlIndex());

		// Do we have to add WHERE or AND
		if (asp.getMainSql().indexOf(" WHERE ") == -1)
		{
			sqlAcessSqlWhereClause.append(" WHERE ");
		}
		else
		{
			sqlAcessSqlWhereClause.append(" AND ");
		}

		// Use First Table
		String tableName = "";
		if (ti.length > 0)
		{
			tableName = ti[0].getSynonym();
			if (tableName.length() == 0)
			{
				tableName = ti[0].getTableName();
			}
		}
		if (TableNameIn != null && !tableName.equals(TableNameIn))
		{
			String msg = "TableName not correctly parsed - TableNameIn=" + TableNameIn + " - " + asp;
			if (ti.length > 0)
			{
				msg += " - #1 " + ti[0];
			}
			msg += "\n SQL=" + sqlSelectFromWhere;
			final AdempiereException ex = new AdempiereException(msg);
			logger.warn(ex.getLocalizedMessage(), ex);
			tableName = TableNameIn;
		}

		if (!tableName.equals(I_AD_PInstance_Log.Table_Name))
		{ // globalqss, bug 1662433
 // Client Access
			sqlAcessSqlWhereClause.append("\n /* security-client */ ");
			if (fullyQualified)
			{
				sqlAcessSqlWhereClause.append(tableName).append(".");
			}
			sqlAcessSqlWhereClause.append(getClientWhere(rw));

			// Org Access
			if (!isAccessAllOrgs())
			{
				sqlAcessSqlWhereClause.append("\n /* security-org */ ");
				sqlAcessSqlWhereClause.append(" AND ");
				if (fullyQualified)
				{
					sqlAcessSqlWhereClause.append(tableName).append(".");
				}
				sqlAcessSqlWhereClause.append(getOrgWhere(tableName, rw));
			}
		}
		else
		{
			sqlAcessSqlWhereClause.append("\n /* no security */ 1=1");
		}

		// ** Data Access **
		for (int i = 0; i < ti.length; i++)
		{
			final String TableName = ti[i].getTableName();

			// [ 1644310 ] Rev. 1292 hangs on start
			if (TableName.toUpperCase().endsWith("_TRL"))
			{
				continue;
			}
			if (tablesAccessInfo.isView(TableName))
			{
				continue;
			}

			final int AD_Table_ID = tablesAccessInfo.getAD_Table_ID(TableName);
			// Data Table Access
			if (AD_Table_ID > 0 && !isTableAccess(AD_Table_ID, !rw))
			{
				sqlAcessSqlWhereClause.append("\n /* security-tableAccess-NO */ AND 1=3"); // prevent access at all
				logger.debug("No access to AD_Table_ID={} - {} - {}", AD_Table_ID, TableName, sqlAcessSqlWhereClause);
				break;	// no need to check further
			}

			// Data Column Access

			// Data Record Access
			String keyColumnNameFQ = "";
			if (fullyQualified)
			{
				keyColumnNameFQ = ti[i].getSynonym();	// table synonym
				if (keyColumnNameFQ.length() == 0)
				{
					keyColumnNameFQ = TableName;
				}
				keyColumnNameFQ += ".";
			}
			// keyColumnName += TableName + "_ID"; // derived from table
			final String keyColumnName = tablesAccessInfo.getIdColumnName(TableName);
			if (keyColumnName == null)
			{
				continue;
			}
			keyColumnNameFQ += keyColumnName;

			// log.debug("addAccessSQL - " + TableName + "(" + AD_Table_ID + ") " + keyColumnName);
			final String recordWhere = getRecordWhere(AD_Table_ID, keyColumnNameFQ, rw);
			if (recordWhere.length() > 0)
			{
				sqlAcessSqlWhereClause.append("\n /* security-record */ AND ").append(recordWhere);
				logger.trace("Record access: {}", recordWhere);
			}
		}   	// for all table info

		// Dependent Records (only for main SQL)
		recordPermissions.addRecordDependentAccessSql(sqlAcessSqlWhereClause, asp, tableName, rw);

		return sqlAcessSqlWhereClause.toString();
	}

	/**
	 * VIEW - Can I view record in Table with given TableLevel. <code>
	 * 	TableLevel			S__ 100		4	System info
	 * 						SCO	111		7	System shared info
	 * 						SC_ 110		6	System/Client info
	 * 						_CO	011		3	Client shared info
	 * 						_C_	011		2	Client shared info
	 * 						__O	001		1	Organization info
	 *  </code>
	 *
	 * @param ctx context
	 * @param tableAcessLevel AccessLevel
	 * @return true/false
	 *         Access error info (AccessTableNoUpdate, AccessTableNoView) is saved in the log
	 *         see org.compiere.model.MTabVO#loadTabDetails(MTabVO, ResultSet)
	 **/
	@Override
	public boolean canView(final TableAccessLevel tableAcessLevel)
	{
		final TableAccessLevel userAccessLevel = getUserLevel();

		if (tableAcessLevel.canBeAccessedBy(userAccessLevel))
		{
			return true;
		}

		// Notification
		final String tableAcessLevelTrl = Services.get(IMsgBL.class).getMsg(Env.getCtx(), tableAcessLevel.getAD_Message());
		final String userAccessLevelTrl = Services.get(IMsgBL.class).getMsg(Env.getCtx(), userAccessLevel.getAD_Message());
		MetasfreshLastError.saveWarning(logger, "AccessTableNoView", "Required=" + tableAcessLevel + "(" + tableAcessLevelTrl + ") != UserLevel=" + userAccessLevelTrl);
		return false;
	}	// canView

	@Override
	public boolean canView(final int AD_Client_ID, final int AD_Org_ID, final int AD_Table_ID, final int Record_ID)
	{
		final boolean saveWarning = false;
		final boolean accessReadWrite = false;
		return canAccessRecord(AD_Client_ID, AD_Org_ID, AD_Table_ID, Record_ID, saveWarning, accessReadWrite);
	}

	@Override
	public boolean canUpdate(final int AD_Client_ID, final int AD_Org_ID, final int AD_Table_ID, final int Record_ID, final boolean saveWarning)
	{
		final boolean accessReadWrite = true;
		return canAccessRecord(AD_Client_ID, AD_Org_ID, AD_Table_ID, Record_ID, saveWarning, accessReadWrite);
	}

	private final boolean canAccessRecord(final int AD_Client_ID, final int AD_Org_ID, final int AD_Table_ID, final int Record_ID, final boolean createError, final boolean accessReadWrite)
	{
		final TableAccessLevel userLevel = getUserLevel();

		if (userLevel.isSystem())
		{
			return true;
		}

		boolean retValue = true;
		String whatMissing = "";

		// System == Client=0 & Org=0
		if (AD_Client_ID == 0 && AD_Org_ID == 0
				&& !userLevel.isSystem())
		{
			retValue = false;
			whatMissing += "S";
		}

		// Client == Client!=0 & Org=0
		else if (AD_Client_ID != 0 && AD_Org_ID == 0
				&& !userLevel.isClient())
		{
			if (userLevel.isOrganization() && isOrgAccess(AD_Org_ID, accessReadWrite))
			{
				;	// Client+Org with access to *
			}
			else
			{
				retValue = false;
				whatMissing += "C";
			}
		}

		// Organization == Client!=0 & Org!=0
		else if (AD_Client_ID != 0 && AD_Org_ID != 0
				&& !userLevel.isOrganization())
		{
			retValue = false;
			whatMissing += "O";
		}

		// Client Access: Verify if the role has access to the given client - teo_sarca, BF [ 1982398 ]
		if (retValue)
		{
			retValue = isClientAccess(AD_Client_ID, accessReadWrite); // r/w access
		}

		// Org Access: Verify if the role has access to the given organization - teo_sarca, patch [ 1628050 ]
		if (retValue)
		{
			retValue = isOrgAccess(AD_Org_ID, accessReadWrite); // r/w access
			whatMissing = "W";
		}

		// Data Access
		if (retValue)
		{
			retValue = isTableAccess(AD_Table_ID, !accessReadWrite);
		}

		if (retValue && Record_ID > 0)
		{
			retValue = isRecordAccess(AD_Table_ID, Record_ID, !accessReadWrite);
		}

		if (!retValue && createError)
		{
			MetasfreshLastError.saveWarning(logger, "AccessTableNoUpdate",
					"AD_Client_ID=" + AD_Client_ID
							+ ", AD_Org_ID=" + AD_Org_ID + ", UserLevel=" + userLevel
							+ " => missing=" + whatMissing);
			logger.warn(toString());
		}
		return retValue;
	}	// canUpdate

	/**
	 * Return Where clause for Record Access
	 *
	 * @param AD_Table_ID table
	 * @param keyColumnName (fully qualified) key column name
	 * @param rw true if read write
	 * @return where clause or ""
	 */
	private String getRecordWhere(final int AD_Table_ID, final String keyColumnName, final boolean rw)
	{
		final StringBuilder sb = recordPermissions.getRecordWhere(AD_Table_ID, keyColumnName, rw);

		// Don't ignore Privacy Access
		if (!isPersonalAccess())
		{
			final String lockedIDs = MPrivateAccess.getLockedRecordWhere(AD_Table_ID, getAD_User_ID());
			if (lockedIDs != null)
			{
				if (sb.length() > 0)
				{
					sb.append(" AND ");
				}
				sb.append(keyColumnName).append(lockedIDs);
			}
		}
		//
		return sb.toString();
	}	// getRecordWhere

	/**
	 * Show (Value) Preference Menu
	 *
	 * @return true if preference type is not None
	 */
	@Override
	public boolean isShowPreference()
	{
		return getPreferenceLevel().isShowPreference();
	}

	/**
	 * Retains only those DocActions on which current role has access.
	 *
	 * @param optionsCtx
	 * @param adClientId
	 */
	private void retainDocActionsWithAccess(final IDocActionOptionsContext optionsCtx)
	{
		final Set<String> docActions = optionsCtx.getDocActions();

		// Do nothing if there are no options to filter
		if (docActions.isEmpty())
		{
			return;
		}

		final int docTypeId = optionsCtx.getC_DocType_ID();
		if (docTypeId <= 0)
		{
			return;
		}

		final int adClientId = optionsCtx.getAD_Client_ID();
		final Set<String> allDocActionsAllowed = getAllowedDocActions(adClientId, docTypeId);
		final Set<String> docActionsAllowed = new LinkedHashSet<>(docActions);
		docActionsAllowed.retainAll(allDocActionsAllowed);
		optionsCtx.setDocActions(docActionsAllowed);
	}

	private final Set<String> getAllowedDocActions(final int adClientId, final int docTypeId)
	{
		final ArrayKey key = Util.mkKey(adClientId, docTypeId);
		return docActionsAllowed.computeIfAbsent(key, (k) -> retrieveAllowedDocActions(adClientId, docTypeId));
	}

	private final Set<String> retrieveAllowedDocActions(final int adClientId, final int docTypeId)
	{
		final List<Object> sqlParams = new ArrayList<>();
		sqlParams.add(adClientId);
		sqlParams.add(docTypeId);
		final String sql = "SELECT rl.Value FROM AD_Document_Action_Access a"
				+ " INNER JOIN AD_Ref_List rl ON (rl.AD_Reference_ID=135 and rl.AD_Ref_List_ID=a.AD_Ref_List_ID)"
				+ " WHERE a.IsActive='Y' AND a.AD_Client_ID=? AND a.C_DocType_ID=?" // #1,2
				+ " AND " + getIncludedRolesWhereClause("a.AD_Role_ID", sqlParams);

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableSet.Builder<String> options = ImmutableSet.builder();
			while (rs.next())
			{
				final String op = rs.getString(1);
				options.add(op);
			}

			return options.build();
		}
		catch (final SQLException e)
		{
			throw new DBException(e, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
			rs = null;
			pstmt = null;
		}
	}

	@Override
	public void applyActionAccess(final IDocActionOptionsContext optionsCtx)
	{
		retainDocActionsWithAccess(optionsCtx);

		final String targetDocAction = optionsCtx.getDocActionToUse();

		if (targetDocAction != null && !DocAction.ACTION_None.equals(targetDocAction))
		{
			final Set<String> options = optionsCtx.getDocActions();

			final Boolean access;
			if (options.contains(targetDocAction))
			{
				access = true;
			}
			else
			{
				access = null; // legacy
			}
			Services.get(IRolePermLoggingBL.class).logDocActionAccess(getAD_Role_ID(), optionsCtx.getC_DocType_ID(), targetDocAction, access);
		}

		optionsCtx.setDocActionToUse(targetDocAction);
	}

	/**
	 * Get Role Where Clause.
	 * It will look something like myalias.AD_Role_ID IN (?, ?, ?).
	 *
	 * @param roleColumnSQL role columnname or role column SQL (e.g. myalias.AD_Role_ID)
	 * @param params a list where the method will put SQL parameters.
	 *            If null, this method will generate a not parametrized query
	 * @return role SQL where clause
	 */
	@Override
	public String getIncludedRolesWhereClause(final String roleColumnSQL, final List<Object> params)
	{
		final StringBuilder whereClause = new StringBuilder();
		for (final int adRoleId : all_AD_Role_IDs)
		{
			if (whereClause.length() > 0)
			{
				whereClause.append(",");
			}
			if (params != null)
			{
				whereClause.append("?");
				params.add(adRoleId);
			}
			else
			{
				whereClause.append(adRoleId);
			}
		}
		//
		whereClause.insert(0, roleColumnSQL + " IN (").append(")");
		return whereClause.toString();
	}

	@Override
	public boolean isAllow_Info_Product()
	{
		return hasPermission(PERMISSION_InfoWindow_Product);
	}

	@Override
	public boolean isAllow_Info_BPartner()
	{
		return hasPermission(PERMISSION_InfoWindow_BPartner);
	}

	@Override
	public boolean isAllow_Info_Account()
	{
		return hasPermission(PERMISSION_InfoWindow_Account);
	}

	@Override
	public boolean isAllow_Info_Schedule()
	{
		return hasPermission(PERMISSION_InfoWindow_Schedule);
	}

	@Override
	public boolean isAllow_Info_MRP()
	{
		return hasPermission(PERMISSION_InfoWindow_MRP);
	}

	@Override
	public boolean isAllow_Info_CRP()
	{
		return hasPermission(PERMISSION_InfoWindow_CRP);
	}

	@Override
	public boolean isAllow_Info_Order()
	{
		return hasPermission(PERMISSION_InfoWindow_Order);
	}

	@Override
	public boolean isAllow_Info_Invoice()
	{
		return hasPermission(PERMISSION_InfoWindow_Invoice);
	}

	@Override
	public boolean isAllow_Info_InOut()
	{
		return hasPermission(PERMISSION_InfoWindow_InOut);
	}

	@Override
	public boolean isAllow_Info_Payment()
	{
		return hasPermission(PERMISSION_InfoWindow_Payment);
	}

	@Override
	public boolean isAllow_Info_CashJournal()
	{
		return hasPermission(PERMISSION_InfoWindow_CashJournal);
	}

	@Override
	public boolean isAllow_Info_Resource()
	{
		return hasPermission(PERMISSION_InfoWindow_Resource);
	}

	@Override
	public boolean isAllow_Info_Asset()
	{
		return hasPermission(PERMISSION_InfoWindow_Asset);
	}

}

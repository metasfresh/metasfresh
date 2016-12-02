package org.adempiere.ad.security;

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


import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.security.permissions.Constraint;
import org.adempiere.ad.security.permissions.InfoWindowPermission;
import org.adempiere.ad.security.permissions.OrgResource;
import org.adempiere.ad.security.permissions.Permission;
import org.adempiere.ad.security.permissions.ResourceAsPermission;
import org.adempiere.ad.security.permissions.UserPreferenceLevelConstraint;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;

import com.google.common.base.Optional;

import de.metas.document.engine.IDocActionOptionsContext;

public interface IUserRolePermissions
{
	int SYSTEM_ROLE_ID = 0;
	Set<Integer> ORGACCESS_ALL = Collections.unmodifiableSet(new HashSet<Integer>()); // NOTE: new instance to make sure it's unique

	Permission PERMISSION_AccessAllOrgs = ResourceAsPermission.ofName("IsAccessAllOrgs");
	Permission PERMISSION_CanReport = ResourceAsPermission.ofName("IsCanReport");
	Permission PERMISSION_CanExport = ResourceAsPermission.ofName("IsCanExport");
	Permission PERMISSION_PersonalAccess = ResourceAsPermission.ofName("IsPersonalAccess");
	Permission PERMISSION_PersonalLock = ResourceAsPermission.ofName("IsPersonalLock");
	Permission PERMISSION_OverwritePriceLimit = ResourceAsPermission.ofName("IsOverwritePriceLimit");
	Permission PERMISSION_ShowAcct = ResourceAsPermission.ofName("IsShowAcct");
	Permission PERMISSION_ChangeLog = ResourceAsPermission.ofName("IsChangeLog");
	Permission PERMISSION_MenuAvailable = ResourceAsPermission.ofName("IsMenuAvailable");
	Permission PERMISSION_AllowLoginDateOverride = ResourceAsPermission.ofName(Env.CTXNAME_IsAllowLoginDateOverride);
	Permission PERMISSION_UseBetaFunctions = ResourceAsPermission.ofName("UseBetaFunctions");
	//
	Permission PERMISSION_InfoWindow_Product = InfoWindowPermission.ofInfoWindowKey("InfoProduct");
	Permission PERMISSION_InfoWindow_BPartner = InfoWindowPermission.ofInfoWindowKey("InfoBPartner");
	Permission PERMISSION_InfoWindow_Account = InfoWindowPermission.ofInfoWindowKey("InfoAccount");
	Permission PERMISSION_InfoWindow_Schedule = InfoWindowPermission.ofInfoWindowKey("InfoSchedule");
	Permission PERMISSION_InfoWindow_MRP = InfoWindowPermission.ofInfoWindowKey("InfoMRP");
	Permission PERMISSION_InfoWindow_CRP = InfoWindowPermission.ofInfoWindowKey("InfoCRP");
	Permission PERMISSION_InfoWindow_Order = InfoWindowPermission.ofInfoWindowKey("InfoOrder");
	Permission PERMISSION_InfoWindow_Invoice = InfoWindowPermission.ofInfoWindowKey("InfoInvoice");
	Permission PERMISSION_InfoWindow_InOut = InfoWindowPermission.ofInfoWindowKey("InfoInOut");
	Permission PERMISSION_InfoWindow_Payment = InfoWindowPermission.ofInfoWindowKey("InfoPayment");
	Permission PERMISSION_InfoWindow_CashJournal = InfoWindowPermission.ofInfoWindowKey("InfoCashLine");
	Permission PERMISSION_InfoWindow_Resource = InfoWindowPermission.ofInfoWindowKey("InfoAssignment");
	Permission PERMISSION_InfoWindow_Asset = InfoWindowPermission.ofInfoWindowKey("InfoAsset");

	/** Access SQL Not Fully Qualified */
	public static final boolean SQL_NOTQUALIFIED = false;

	/** Access SQL Fully Qualified */
	public static final boolean SQL_FULLYQUALIFIED = true;

	/** Access SQL Read Only */
	public static final boolean SQL_RO = false;

	/** Access SQL Read Write */
	public static final boolean SQL_RW = true;

	IUserRolePermissionsBuilder asNewBuilder();

	/**
	 * @return user friendly extended string representation
	 */
	String toStringX();

	/** @return role name */
	String getName();
	int getAD_Role_ID();

	int getAD_Client_ID();

	int getAD_User_ID();
	
	boolean isSystemAdministrator();

	/**
	 * @return all AD_Role_IDs. It will contain:
	 *         <ul>
	 *         <li>this {@link #getAD_Role_ID()}
	 *         <li>and all directly or indirectly included roles
	 *         <li>substituted roles
	 *         </ul>
	 */
	Set<Integer> getAll_AD_Role_IDs();

	String getIncludedRolesWhereClause(String roleColumnSQL, List<Object> params);

	boolean hasPermission(Permission permission);

	<T extends Constraint> Optional<T> getConstraint(Class<T> constraintType);

	/*************************************************************************
	 * Appends where clause to SQL statement for Table
	 *
	 * @param SQL existing SQL statement
	 * @param TableNameIn Table Name or list of table names AAA, BBB or AAA a, BBB b
	 * @param fullyQualified fullyQualified names
	 * @param rw if false, includes System Data
	 * @return updated SQL statement
	 */
	String addAccessSQL(String SQL, String TableNameIn, boolean fullyQualified, boolean rw);

	Boolean checkWorkflowAccess(int AD_Workflow_ID);
	Boolean getWorkflowAccess(int AD_Workflow_ID);
	Boolean checkFormAccess(int AD_Form_ID);
	Boolean getFormAccess(int AD_Form_ID);
	Boolean checkTaskAccess(int AD_Task_ID);
	Boolean getTaskAccess(int AD_Task_ID);
	Boolean checkProcessAccess(int AD_Process_ID);
	Boolean getProcessAccess(int AD_Process_ID);
	Boolean checkWindowAccess(int AD_Window_ID);
	Boolean getWindowAccess(int AD_Window_ID);

	void applyActionAccess(IDocActionOptionsContext optionsCtx);

	boolean canView(TableAccessLevel tableAcessLevel);

	boolean canUpdate(int AD_Client_ID, int AD_Org_ID, int AD_Table_ID, int Record_ID, boolean createError);
	boolean isRecordAccess(int AD_Table_ID, int Record_ID, boolean ro);
	boolean isColumnAccess(int AD_Table_ID, int AD_Column_ID, boolean ro);
	boolean isTableAccess(int AD_Table_ID, boolean ro);
	boolean isCanExport(int AD_Table_ID);
	boolean isCanReport(int AD_Table_ID);
	boolean isOrgAccess(int AD_Org_ID, boolean rw);

	boolean isClientAccess(int AD_Client_ID, boolean rw);

	String getClientWhere(boolean rw);

	/**
	 * Get Org Where Clause Value
	 *
	 * @param rw read write
	 * @return "AD_Org_ID=0" or "AD_Org_ID IN(0,1)" or null (if access all org)
	 * @deprecated Please use {@link #getOrgWhere(String, boolean)}
	 */
	@Deprecated
	String getOrgWhere(boolean rw);
	String getOrgWhere(String tableName, boolean rw);
	String getAD_Org_IDs_AsString();
	
	//FRESH-560: Retrieve the org IDs also as a list
	Set<Integer> getAD_Org_IDs_AsSet();

	Set<KeyNamePair> getLoginClients();
	Set<OrgResource> getLoginOrgs();

	int getOrg_Tree_ID();
	
	int getMenu_Tree_ID();

	boolean isShowPreference();
	UserPreferenceLevelConstraint getPreferenceLevel();
	boolean isPersonalAccess();
	TableAccessLevel getUserLevel();
	int getStartup_AD_Form_ID();
	boolean isCanExport();
	boolean isCanReport();
	boolean isAccessAllOrgs();

	boolean isAllow_Info_Product();
	boolean isAllow_Info_BPartner();
	boolean isAllow_Info_Account();
	boolean isAllow_Info_Schedule();
	boolean isAllow_Info_MRP();
	boolean isAllow_Info_CRP();
	boolean isAllow_Info_Order();
	boolean isAllow_Info_Invoice();
	boolean isAllow_Info_InOut();
	boolean isAllow_Info_Payment();
	boolean isAllow_Info_CashJournal();
	boolean isAllow_Info_Resource();
	boolean isAllow_Info_Asset();
}

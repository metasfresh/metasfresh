package de.metas.security;

import de.metas.document.engine.DocActionOptionsContext;
import de.metas.i18n.BooleanWithReason;
import de.metas.organization.OrgId;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.Constraint;
import de.metas.security.permissions.ElementPermission;
import de.metas.security.permissions.InfoWindowPermission;
import de.metas.security.permissions.OrgResource;
import de.metas.security.permissions.Permission;
import de.metas.security.permissions.ResourceAsPermission;
import de.metas.security.permissions.UserMenuInfo;
import de.metas.security.permissions.UserPreferenceLevelConstraint;
import de.metas.user.UserId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.service.ClientId;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IUserRolePermissions
{
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
	Permission PERMISSION_IsAttachmentDeletionAllowed = ResourceAsPermission.ofName("IsAttachmentDeletionAllowed");

	@Deprecated
	Permission PERMISSION_AutoRoleLogin = ResourceAsPermission.ofName("IsAutoRoleLogin");
	@Deprecated
	Permission PERMISSION_TrlBox = ResourceAsPermission.ofName("TrlBox");
	@Deprecated
	Permission PERMISSION_InvoicingPriority = ResourceAsPermission.ofName("InvoicingPriority");
	@Deprecated
	Permission PERMISSION_MigrationScripts = ResourceAsPermission.ofName("MigrationScripts");

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
	boolean SQL_NOTQUALIFIED = false;

	/** Access SQL Fully Qualified */
	boolean SQL_FULLYQUALIFIED = true;

	/**
	 * @return user friendly extended string representation
	 */
	String toStringX();

	/** @return role name */
	String getName();

	RoleId getRoleId();

	ClientId getClientId();

	UserId getUserId();

	boolean isSystemAdministrator();

	/**
	 * @return all AD_Role_IDs. It will contain:
	 *         <ul>
	 *         <li>this {@link #getRoleId()}
	 *         <li>and all directly or indirectly included roles
	 *         <li>substituted roles
	 *         </ul>
	 */
	Set<RoleId> getAllRoleIds();

	String getIncludedRolesWhereClause(String roleColumnSQL, List<Object> params);

	boolean hasPermission(Permission permission);

	<T extends Constraint> Optional<T> getConstraint(Class<T> constraintType);

	/*************************************************************************
	 * Appends where clause to SQL statement for Table
	 *
	 * @param sql existing SQL statement
	 * @param tableNameIn Table Name or list of table names AAA, BBB or AAA a, BBB b
	 * @param fullyQualified fullyQualified names
	 * @param access read/write; if read, includes System Data
	 * @return updated SQL statement
	 */
	String addAccessSQL(String sql, String tableNameIn, boolean fullyQualified, Access access);

	/** @return window permissions; never return null */
	ElementPermission checkWindowPermission(AdWindowId AD_Window_ID);

	@Nullable Boolean getWindowAccess(AdWindowId AD_Window_ID);

	@Nullable Boolean checkWorkflowAccess(int AD_Workflow_ID);

	ElementPermission checkWorkflowPermission(int AD_Workflow_ID);

	@Nullable Boolean getWorkflowAccess(int AD_Workflow_ID);

	@Nullable Boolean checkFormAccess(int AD_Form_ID);

	ElementPermission checkFormPermission(int AD_Form_ID);

	@Nullable Boolean getFormAccess(int AD_Form_ID);

	@Nullable Boolean checkTaskAccess(int AD_Task_ID);

	ElementPermission checkTaskPermission(int AD_Task_ID);

	@Nullable Boolean getTaskAccess(int AD_Task_ID);

	//
	// Process
	// @formatter:off
	@Nullable Boolean checkProcessAccess(int AD_Process_ID);
	default boolean checkProcessAccessRW(final int AD_Process_ID) { return isReadWriteAccess(checkProcessAccess(AD_Process_ID)); }
	ElementPermission checkProcessPermission(int AD_Process_ID);
	@Nullable Boolean getProcessAccess(int AD_Process_ID);
	// @formatter:on

	void applyActionAccess(DocActionOptionsContext optionsCtx);

	boolean canView(TableAccessLevel tableAcessLevel);

	/**
	 * Checks if given record can be viewed by this role.
	 *
	 * @param clientId record's AD_Client_ID
	 * @param orgId record's AD_Org_ID
	 * @param AD_Table_ID record table
	 * @param Record_ID record id
	 * @return true if you can view
	 *
	 * @deprecated consider using {@link #checkCanView(ClientId, OrgId, int, int)}
	 **/
	@Deprecated
	boolean canView(ClientId clientId, OrgId orgId, int AD_Table_ID, int Record_ID);

	/**
	 * Checks if given record can be viewed by this role.
	 */
	BooleanWithReason checkCanView(ClientId clientId, OrgId orgId, int AD_Table_ID, int Record_ID);

	/**
	 * Checks if given record can be updated by this role.
	 *
	 * @param clientId record's AD_Client_ID
	 * @param orgId record's AD_Org_ID
	 * @param AD_Table_ID record table
	 * @param Record_ID record id
	 * @param createError true if a warning shall be logged and saved (AccessTableNoUpdate).
	 * @return true if you can update
	 **/
	@Deprecated
	boolean canUpdate(ClientId clientId, OrgId orgId, int AD_Table_ID, int Record_ID, boolean createError);

	/**
	 * Checks if given record can be updated by this role.
	 *
	 * @param clientId record's AD_Client_ID
	 * @param orgId record's AD_Org_ID
	 * @param AD_Table_ID record table
	 * @param Record_ID record id
	 **/
	BooleanWithReason checkCanUpdate(ClientId clientId, OrgId orgId, int AD_Table_ID, int Record_ID);

	BooleanWithReason checkCanCreateNewRecord(ClientId clientId, OrgId orgId, AdTableId adTableId);

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	boolean isColumnAccess(int AD_Table_ID, int AD_Column_ID, Access access);

	boolean isTableAccess(int AD_Table_ID, Access access);

	boolean isCanExport(int AD_Table_ID);

	boolean isCanReport(int AD_Table_ID);

	boolean isOrgAccess(OrgId OrgId, String tableName, Access access);

	String getClientWhere(@Nullable String tableName, @Nullable String tableAlias, Access access);

	Optional<String> getOrgWhere(@Nullable String tableName, Access access);

	String getAD_Org_IDs_AsString();

	// FRESH-560: Retrieve the org IDs also as a list
	Set<OrgId> getAD_Org_IDs_AsSet();

	Set<ClientId> getLoginClientIds();

	OrgIdAccessList getOrgAccess(@Nullable String tableName, Access access);

	Set<OrgResource> getLoginOrgs();

	UserMenuInfo getMenuInfo();

	boolean isShowPreference();

	UserPreferenceLevelConstraint getPreferenceLevel();

	TableAccessLevel getUserLevel();

	int getStartup_AD_Form_ID();

	boolean isCanExport();

	boolean isCanReport();

	//
	// Static Helpers
	//
	static boolean isReadWriteAccess(@Nullable final Boolean access)
	{
		return access != null && access;
	}
}

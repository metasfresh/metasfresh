package de.metas.security.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.concurrent.Immutable;

import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.adempiere.service.IRolePermLoggingBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import de.metas.document.DocTypeId;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocument;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.organization.OrgId;
import de.metas.security.ISecurityRuleEngine;
import de.metas.security.IUserRolePermissions;
import de.metas.security.RoleId;
import de.metas.security.TableAccessLevel;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.Constraint;
import de.metas.security.permissions.Constraints;
import de.metas.security.permissions.ElementPermission;
import de.metas.security.permissions.ElementPermissions;
import de.metas.security.permissions.GenericPermissions;
import de.metas.security.permissions.LoginOrgConstraint;
import de.metas.security.permissions.OrgPermissions;
import de.metas.security.permissions.OrgResource;
import de.metas.security.permissions.Permission;
import de.metas.security.permissions.StartupWindowConstraint;
import de.metas.security.permissions.TableColumnPermissions;
import de.metas.security.permissions.TablePermissions;
import de.metas.security.permissions.UserMenuInfo;
import de.metas.security.permissions.UserPreferenceLevelConstraint;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Immutable
@ToString(of = { "name", "roleId", "userId", "clientId" })
class UserRolePermissions implements IUserRolePermissions
{
	private static final transient Logger logger = LogManager.getLogger(UserRolePermissions.class);

	private static final Set<OrgId> ORGACCESS_ALL = Collections.unmodifiableSet(new HashSet<>()); // NOTE: new instance to make sure it's unique

	/** Permissions name (i.e. role name) */
	@Getter
	private final String name;
	@Getter
	private final RoleId roleId;
	@Getter(AccessLevel.PACKAGE)
	private final UserRolePermissionsIncludesList includes;
	@Getter
	private final ImmutableSet<RoleId> allRoleIds;
	/** User */
	@Getter
	private final UserId userId;
	@Getter
	private final ClientId clientId;
	@Getter
	private final TableAccessLevel userLevel;

	/** Positive List of Organizational Access */
	@Getter(AccessLevel.PACKAGE)
	private final OrgPermissions orgPermissions;

	/** List of Table Access */
	@Getter(AccessLevel.PACKAGE)
	private final TablePermissions tablePermissions;
	/** List of Column Access */
	@Getter(AccessLevel.PACKAGE)
	private final TableColumnPermissions columnPermissions;

	/** Table Access Info */
	private final TablesAccessInfo tablesAccessInfo = TablesAccessInfo.instance;

	/** Window Access */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions windowPermissions;
	/** Process Access */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions processPermissions;
	/** Task Access */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions taskPermissions;
	/** Workflow Access */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions workflowPermissions;
	/** Form Access */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions formPermissions;

	@Getter(AccessLevel.PACKAGE)
	private final GenericPermissions miscPermissions;

	private final ConcurrentHashMap<ArrayKey, Set<String>> docActionsAllowed = new ConcurrentHashMap<>();

	/** Permission constraints */
	@Getter(AccessLevel.PACKAGE)
	private final Constraints constraints;

	private final UserMenuInfo menuInfo;

	UserRolePermissions(final UserRolePermissionsBuilder builder)
	{
		name = builder.getName();
		roleId = builder.getRoleId();
		includes = builder.getUserRolePermissionsIncluded();
		allRoleIds = ImmutableSet.copyOf(includes.getAllRoleIdsIncluding(roleId));
		userId = builder.getUserId();

		clientId = builder.getClientId();
		Check.assumeNotNull(clientId, "clientId shall be set");

		userLevel = builder.getUserLevel();

		orgPermissions = builder.getOrgPermissions();

		tablePermissions = builder.getTablePermissions();
		columnPermissions = builder.getColumnPermissions();
		windowPermissions = builder.getWindowPermissions();
		processPermissions = builder.getProcessPermissions();
		taskPermissions = builder.getTaskPermissions();
		workflowPermissions = builder.getWorkflowPermissions();
		formPermissions = builder.getFormPermissions();

		miscPermissions = builder.getMiscPermissions();
		constraints = builder.getConstraints();

		menuInfo = builder.getMenuInfo();
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
				.appendTo(sb, miscPermissions, constraints, orgPermissions, tablePermissions, columnPermissions
				// don't show followings because they could be to big, mainly when is not a manual role:
				// , windowPermissions
				// , processPermissions
				// , taskPermissions
				// , formPermissions
				// , workflowPermissions
				);

		return sb.toString();
	}

	boolean isAccessAllOrgs()
	{
		return hasPermission(PERMISSION_AccessAllOrgs);
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

	boolean isPersonalAccess()
	{
		return hasPermission(PERMISSION_PersonalAccess);
	}

	@Override
	public UserPreferenceLevelConstraint getPreferenceLevel()
	{
		return getConstraint(UserPreferenceLevelConstraint.class)
				.or(UserPreferenceLevelConstraint.NONE);
	}

	@Override
	public boolean isSystemAdministrator()
	{
		return
		// System role:
		getRoleId().isSystem()
				// and Shall have at access to system organization:
				&& isOrgAccess(OrgId.ANY, Access.WRITE);
	}

	/**************************************************************************
	 * Get Client Where Clause Value
	 *
	 * @param rw read write
	 * @return "AD_Client_ID=0" or "AD_Client_ID IN(0,1)"
	 */
	@Override
	public String getClientWhere(final String tableName, final String tableAlias, final Access access)
	{
		// All Orgs - use Role's AD_Client_ID
		if (isAccessAllOrgs())
		{
			final Set<ClientId> adClientIds = ImmutableSet.of(getClientId());
			return OrgPermissions.buildClientWhere(tableName, tableAlias, access, adClientIds);
		}
		// Get Client from Org List
		else
		{
			return orgPermissions.getClientWhere(tableName, tableAlias, access);
		}
	}

	/**
	 * Access to Client
	 *
	 * @param AD_Client_ID client
	 * @param rw read write access
	 * @return true if access
	 */
	private boolean isClientAccess(@NonNull final ClientId clientId, @NonNull final Access access)
	{
		if (clientId.isSystem() && access.isReadOnly())
		{
			return true;
		}

		//
		// Check Access All Orgs:
		if (isAccessAllOrgs())
		{
			// User has access to given AD_Client_ID if the role is defined on that AD_Client_ID
			return ClientId.equals(getClientId(), clientId);
		}

		//
		return orgPermissions.isClientAccess(clientId, access);
	}

	@Override
	public UserMenuInfo getMenuInfo()
	{
		return menuInfo;
	}

	private Set<OrgId> getOrgAccess(final String tableName, final Access access)
	{
		if (isAccessAllOrgs())
		{
			return ORGACCESS_ALL;
		}

		final Set<OrgId> adOrgIds = orgPermissions.getOrgAccess(access);

		Services.get(ISecurityRuleEngine.class).filterOrgs(this, tableName, access, adOrgIds);

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

	@Override
	public String getOrgWhere(final String tableName, final Access access)
	{
		final Set<OrgId> adOrgIds = getOrgAccess(tableName, access);
		if (adOrgIds == ORGACCESS_ALL)
		{
			return "1=1"; // no org filter
		}

		//
		final StringBuilder sb = new StringBuilder();
		final Iterator<OrgId> it = adOrgIds.iterator();
		boolean oneOnly = true;
		while (it.hasNext())
		{
			if (sb.length() > 0)
			{
				sb.append(",");
				oneOnly = false;
			}
			final OrgId orgId = it.next();
			sb.append(orgId.getRepoId());
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
		else
		{
			return "AD_Org_ID IN (" + sb.toString() + ")";
		}
	}	// getOrgWhereValue

	/**
	 * Access to Org
	 *
	 * @param AD_Org_ID org
	 * @param rw read write access
	 * @return true if access
	 */
	@Override
	public boolean isOrgAccess(@NonNull final OrgId orgId, final Access access)
	{
		// Readonly access to "*" organization is always granted
		if (orgId.isAny() && access.isReadOnly())
		{
			return true;
		}

		final Set<OrgId> orgs = getOrgAccess(null, access); // tableName=n/a
		if (orgs == ORGACCESS_ALL)
		{
			return true;
		}
		return orgs.contains(orgId);
	}	// isOrgAccess

	@Override
	public String getAD_Org_IDs_AsString()
	{
		return orgPermissions.getAD_Org_IDs_AsString();
	}

	@Override
	public Set<OrgId> getAD_Org_IDs_AsSet()
	{
		return orgPermissions.getAD_Org_IDs_AsSet();
	}

	@Override
	public boolean isCanReport(final int AD_Table_ID)
	{
		if (!isCanReport())   						// Role Level block
		{
			logger.warn("Role denied");
			return false;
		}
		if (!isTableAccess(AD_Table_ID, Access.READ))
		{
			return false;
		}
		//
		return getTablePermissions().isCanReport(AD_Table_ID);
	}

	@Override
	public boolean isCanExport(final int AD_Table_ID)
	{
		if (!isCanExport())   						// Role Level block
		{
			logger.warn("Role denied");
			return false;
		}
		if (!isTableAccess(AD_Table_ID, Access.READ))
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
	 * @return has RO/RW access to table
	 */
	@Override
	public boolean isTableAccess(final int AD_Table_ID, @NonNull final Access access)
	{
		if (Access.WRITE.equals(access))
		{
			final TableAccessLevel roleAccessLevel = tablesAccessInfo.getTableAccessLevel(AD_Table_ID);
			if (roleAccessLevel == null)
			{
				logger.debug("NO - No AccessLevel - AD_Table_ID={}", AD_Table_ID);
				return false;
			}

			final TableAccessLevel userLevel = getUserLevel();
			if (!roleAccessLevel.hasCommonLevels(userLevel))
			{
				return false;
			}
		}

		//
		return tablePermissions.hasAccess(AD_Table_ID, access);
	}

	@Override
	public boolean isColumnAccess(final int AD_Table_ID, final int AD_Column_ID, final Access access)
	{
		if (!isTableAccess(AD_Table_ID, access))
		{
			return false;
		}

		return columnPermissions.isColumnAccess(AD_Table_ID, AD_Column_ID, access);
	}

	private boolean isRecordAccess(final int AD_Table_ID, final int Record_ID, final Access access)
	{
		final RecordAccessService userGroupRecordAccessService = Adempiere.getBean(RecordAccessService.class);
		return userGroupRecordAccessService.hasRecordPermission(
				getUserId(),
				getRoleId(),
				TableRecordReference.of(AD_Table_ID, Record_ID),
				access);
	}

	/**
	 * Get Window Access
	 *
	 * @param AD_Window_ID window
	 * @return null in no access, TRUE if r/w and FALSE if r/o
	 */
	@Override
	public Boolean getWindowAccess(@NonNull final AdWindowId AD_Window_ID)
	{
		final Boolean access = checkWindowPermission(AD_Window_ID).getReadWriteBoolean();
		Services.get(IRolePermLoggingBL.class).logWindowAccess(getRoleId(), AD_Window_ID.getRepoId(), access);
		return access;
	}

	@Override
	public ElementPermission checkWindowPermission(@NonNull final AdWindowId AD_Window_ID)
	{
		return windowPermissions.getPermission(AD_Window_ID.getRepoId());
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
		Services.get(IRolePermLoggingBL.class).logProcessAccess(getRoleId(), AD_Process_ID, access);
		return access;
	}

	@Override
	public Boolean checkProcessAccess(final int AD_Process_ID)
	{
		return checkProcessPermission(AD_Process_ID).getReadWriteBoolean();
	}

	@Override
	public ElementPermission checkProcessPermission(int AD_Process_ID)
	{
		return processPermissions.getPermission(AD_Process_ID);
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
		Services.get(IRolePermLoggingBL.class).logTaskAccess(getRoleId(), AD_Task_ID, access);
		return access;
	}

	@Override
	public Boolean checkTaskAccess(final int AD_Task_ID)
	{
		return checkTaskPermission(AD_Task_ID).getReadWriteBoolean();
	}

	@Override
	public ElementPermission checkTaskPermission(int AD_Task_ID)
	{
		return taskPermissions.getPermission(AD_Task_ID);
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
		Services.get(IRolePermLoggingBL.class).logTaskAccess(getRoleId(), AD_Form_ID, access);
		return access;
	}

	@Override
	public Boolean checkFormAccess(final int AD_Form_ID)
	{
		return checkFormPermission(AD_Form_ID).getReadWriteBoolean();
	}

	@Override
	public ElementPermission checkFormPermission(final int AD_Form_ID)
	{
		return formPermissions.getPermission(AD_Form_ID);
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
		Services.get(IRolePermLoggingBL.class).logWorkflowAccess(getRoleId(), AD_Workflow_ID, access);
		return access;
	}

	@Override
	public Boolean checkWorkflowAccess(final int AD_Workflow_ID)
	{
		return checkWorkflowPermission(AD_Workflow_ID).getReadWriteBoolean();
	}

	@Override
	public ElementPermission checkWorkflowPermission(int AD_Workflow_ID)
	{
		return workflowPermissions.getPermission(AD_Workflow_ID);
	}

	@Override
	public String addAccessSQL(
			final String sql,
			final String tableNameIn,
			final boolean fullyQualified,
			final Access access)
	{
		return new UserRolePermissionsSqlHelpers(this)
				.addAccessSQL(sql, tableNameIn, fullyQualified, access);
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
		// TODO: consider deleting it because it's not used
		final String tableAcessLevelTrl = Services.get(IMsgBL.class).getMsg(Env.getCtx(), tableAcessLevel.getAD_Message());
		final String userAccessLevelTrl = Services.get(IMsgBL.class).getMsg(Env.getCtx(), userAccessLevel.getAD_Message());
		MetasfreshLastError.saveWarning(logger, "AccessTableNoView", "Required=" + tableAcessLevel + "(" + tableAcessLevelTrl + ") != UserLevel=" + userAccessLevelTrl);
		return false;
	}	// canView

	@Override
	public boolean canView(final ClientId clientId, final OrgId orgId, final int AD_Table_ID, final int Record_ID)
	{
		final String errmsg = checkCanAccessRecord(clientId, orgId, AD_Table_ID, Record_ID, Access.READ);
		return errmsg == null;
	}

	@Override
	public String checkCanView(final ClientId clientId, final OrgId orgId, final int AD_Table_ID, final int Record_ID)
	{
		return checkCanAccessRecord(clientId, orgId, AD_Table_ID, Record_ID, Access.READ);
	}

	@Override
	public String checkCanCreateNewRecord(final ClientId clientId, final OrgId orgId, final int AD_Table_ID)
	{
		final int Record_ID = -1;
		return checkCanAccessRecord(clientId, orgId, AD_Table_ID, Record_ID, Access.WRITE);
	}

	@Override
	public String checkCanUpdate(final ClientId clientId, final OrgId orgId, final int AD_Table_ID, final int Record_ID)
	{
		return checkCanAccessRecord(clientId, orgId, AD_Table_ID, Record_ID, Access.WRITE);
	}

	@Override
	public boolean canUpdate(final ClientId clientId, final OrgId orgId, final int AD_Table_ID, final int Record_ID, final boolean saveWarning)
	{
		final String errmsg = checkCanUpdate(clientId, orgId, AD_Table_ID, Record_ID);
		if (errmsg == null)
		{
			return true;
		}
		else
		{
			if (saveWarning)
			{
				MetasfreshLastError.saveWarning(logger, "AccessTableNoUpdate", errmsg);
				logger.warn("No update access: {}, {}", errmsg, this);
			}
			return false;
		}
	}

	/** @return error message or <code>null</code> if OK */
	private final String checkCanAccessRecord(
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			final int AD_Table_ID,
			final int Record_ID,
			@NonNull final Access access)
	{
		final TableAccessLevel userLevel = getUserLevel();

		// If user level is system then it can access anything
		// TODO: check if we really need this rule here
		if (userLevel.isSystem())
		{
			return null; // OK
		}

		final List<String> missingAccesses = new ArrayList<>();

		// Check user level vs required level (based on AD_Client_ID/AD_Org_ID)
		if (access.isReadWrite())
		{
			final TableAccessLevel requiredLevel = TableAccessLevel.forClientOrg(clientId, orgId);
			if (!requiredLevel.canBeAccessedBy(userLevel))
			{
				missingAccesses.add(requiredLevel.toString());
			}
		}

		//
		// Client Access: Verify if the role has access to the given client - teo_sarca, BF [ 1982398 ]
		if (missingAccesses.isEmpty() && !isClientAccess(clientId, access))
		{
			missingAccesses.add("client access");
		}

		// Org Access: Verify if the role has access to the given organization - teo_sarca, patch [ 1628050 ]
		if (missingAccesses.isEmpty() && !isOrgAccess(orgId, access))
		{
			missingAccesses.add("organization access");
		}

		// Table Access
		if (missingAccesses.isEmpty() && !isTableAccess(AD_Table_ID, access))
		{
			missingAccesses.add("table access");
		}

		// Record Access
		if (Record_ID > 0 && missingAccesses.isEmpty() && !isRecordAccess(AD_Table_ID, Record_ID, access))
		{
			missingAccesses.add("record access");
		}

		if (!missingAccesses.isEmpty())
		{
			final String adMessage;
			if (access.isReadOnly())
			{
				adMessage = "AccessTableNoView";
			}
			else if (access.isReadWrite())
			{
				adMessage = "AccessTableNoUpdate";
			}
			else
			{
				adMessage = "AccessTableNo" + access.getName();
			}
			return "@" + adMessage + "@: " + Joiner.on(", ").join(missingAccesses);
		}

		return null; // OK
	}

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
	private void retainDocActionsWithAccess(final DocActionOptionsContext optionsCtx)
	{
		final Set<String> docActions = optionsCtx.getDocActions();

		// Do nothing if there are no options to filter
		if (docActions.isEmpty())
		{
			return;
		}

		final DocTypeId docTypeId = optionsCtx.getDocTypeId();
		if (docTypeId == null)
		{
			return;
		}

		final ClientId adClientId = optionsCtx.getAdClientId();
		final Set<String> allDocActionsAllowed = getAllowedDocActions(adClientId, docTypeId);
		final Set<String> docActionsAllowed = new LinkedHashSet<>(docActions);
		docActionsAllowed.retainAll(allDocActionsAllowed);
		optionsCtx.setDocActions(ImmutableSet.copyOf(docActionsAllowed));
	}

	private final Set<String> getAllowedDocActions(final ClientId adClientId, final DocTypeId docTypeId)
	{
		final ArrayKey key = Util.mkKey(adClientId, docTypeId);
		return docActionsAllowed.computeIfAbsent(key, (k) -> retrieveAllowedDocActions(adClientId, docTypeId));
	}

	private final Set<String> retrieveAllowedDocActions(final ClientId adClientId, final DocTypeId docTypeId)
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
	public void applyActionAccess(final DocActionOptionsContext optionsCtx)
	{
		retainDocActionsWithAccess(optionsCtx);

		final String targetDocAction = optionsCtx.getDocActionToUse();

		if (targetDocAction != null && !IDocument.ACTION_None.equals(targetDocAction))
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

			if (optionsCtx.getDocTypeId() != null)
			{
				Services.get(IRolePermLoggingBL.class).logDocActionAccess(getRoleId(), optionsCtx.getDocTypeId(), targetDocAction, access);
			}
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
		for (final RoleId adRoleId : allRoleIds)
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
				whereClause.append(adRoleId.getRepoId());
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

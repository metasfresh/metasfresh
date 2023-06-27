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

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableSet;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocument;
import de.metas.document.references.zoom_into.CustomizedWindowInfo;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMap;
import de.metas.document.references.zoom_into.CustomizedWindowInfoMapRepository;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.BooleanWithReason;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.logging.MetasfreshLastError;
import de.metas.organization.OrgId;
import de.metas.security.IUserRolePermissions;
import de.metas.security.OrgIdAccessList;
import de.metas.security.RoleGroup;
import de.metas.security.RoleId;
import de.metas.security.TableAccessLevel;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.Constraint;
import de.metas.security.permissions.Constraints;
import de.metas.security.permissions.ElementPermission;
import de.metas.security.permissions.ElementPermissions;
import de.metas.security.permissions.ElementResource;
import de.metas.security.permissions.GenericPermissions;
import de.metas.security.permissions.LoginOrgConstraint;
import de.metas.security.permissions.OrgPermissions;
import de.metas.security.permissions.OrgResource;
import de.metas.security.permissions.Permission;
import de.metas.security.permissions.StartupWindowConstraint;
import de.metas.security.permissions.TableColumnPermissions;
import de.metas.security.permissions.TableOrgPermissions;
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
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ClientId;
import org.adempiere.service.IRolePermLoggingBL;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Immutable
@ToString(of = { "name", "roleId", "userId", "clientId" })
class UserRolePermissions implements IUserRolePermissions
{
	private static final transient Logger logger = LogManager.getLogger(UserRolePermissions.class);

	private static final AdMessageKey MSG_AccessTableNoView = AdMessageKey.of("AccessTableNoView");
	private static final AdMessageKey MSG_AccessTableNoUpdate = AdMessageKey.of("AccessTableNoUpdate");

	/**
	 * Permissions name (i.e. role name)
	 */
	@Getter
	private final String name;
	@Getter
	private final RoleGroup roleGroup;
	@Getter
	private final RoleId roleId;
	@Getter(AccessLevel.PACKAGE)
	private final UserRolePermissionsIncludesList includes;
	@Getter
	private final ImmutableSet<RoleId> allRoleIds;
	/**
	 * User
	 */
	@Getter
	private final UserId userId;
	@Getter
	private final ClientId clientId;
	@Getter
	private final TableAccessLevel userLevel;

	/**
	 * Positive List of Organizational Access
	 */
	@Getter(AccessLevel.PACKAGE)
	private final OrgPermissions orgPermissions;
	@Getter(AccessLevel.PACKAGE)
	private final TableOrgPermissions tableOrgPermissions;

	/**
	 * List of Table Access
	 */
	@Getter(AccessLevel.PACKAGE)
	private final TablePermissions tablePermissions;
	/**
	 * List of Column Access
	 */
	@Getter(AccessLevel.PACKAGE)
	private final TableColumnPermissions columnPermissions;

	/**
	 * Table Access Info
	 */
	private final TablesAccessInfo tablesAccessInfo = TablesAccessInfo.instance;

	/**
	 * Window Access
	 */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions windowPermissions;
	/**
	 * Process Access
	 */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions processPermissions;
	/**
	 * Task Access
	 */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions taskPermissions;
	/**
	 * Workflow Access
	 */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions workflowPermissions;
	/**
	 * Form Access
	 */
	@Getter(AccessLevel.PACKAGE)
	private final ElementPermissions formPermissions;

	@Getter(AccessLevel.PACKAGE)
	private final GenericPermissions miscPermissions;

	private final ConcurrentHashMap<ArrayKey, Set<String>> docActionsAllowed = new ConcurrentHashMap<>();

	/**
	 * Permission constraints
	 */
	@Getter(AccessLevel.PACKAGE)
	private final Constraints constraints;

	private final UserMenuInfo menuInfo;

	UserRolePermissions(final UserRolePermissionsBuilder builder)
	{
		name = builder.getName();
		roleGroup = builder.getRoleGroup();
		roleId = builder.getRoleId();
		includes = builder.getUserRolePermissionsIncluded();
		allRoleIds = ImmutableSet.copyOf(includes.getAllRoleIdsIncluding(roleId));
		userId = builder.getUserId();

		clientId = builder.getClientId();
		Check.assumeNotNull(clientId, "clientId shall be set");

		userLevel = builder.getUserLevel();

		orgPermissions = builder.getOrgPermissions();
		tableOrgPermissions = builder.getTableOrgPermissions();

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

	private RecordAccessService recordAccessService()
	{
		return SpringContextHolder.instance.getBean(RecordAccessService.class);
	}

	private IRolePermLoggingBL rolePermLoggingBL()
	{
		return Services.get(IRolePermLoggingBL.class);
	}

	private CustomizedWindowInfoMap getCustomizedWindowInfoMap()
	{
		return SpringContextHolder.instance.getBean(CustomizedWindowInfoMapRepository.class).get();
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
				.appendTo(sb, miscPermissions, constraints, orgPermissions, tableOrgPermissions, tablePermissions, columnPermissions
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
				.orElse(StartupWindowConstraint.NULL)
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
				.orElse(UserPreferenceLevelConstraint.NONE);
	}

	@Override
	public boolean isSystemAdministrator()
	{
		return
				// System role:
				getRoleId().isSystem()
						// and Shall have at access to system organization:
						&& isOrgAccess(OrgId.ANY, null, Access.WRITE);
	}

	/**************************************************************************
	 * Get Client Where Clause Value
	 *
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

	@Override
	public OrgIdAccessList getOrgAccess(@Nullable final String tableName, final Access access)
	{
		final Optional<Set<OrgId>> orgsWithAccess = tableOrgPermissions.getOrgsWithAccess(tableName, access);

		if (orgsWithAccess.isPresent())
		{
			final Set<OrgId> orgIds = orgsWithAccess.get();

			if (orgIds.contains(OrgId.ANY))
			{
				return OrgIdAccessList.TABLE_ALL;
			}

			return OrgIdAccessList.ofSet(orgIds);
		}

		if (isAccessAllOrgs())
		{
			return OrgIdAccessList.ALL;
		}

		return OrgIdAccessList.ofSet(orgPermissions.getOrgAccess(access));
	}

	@Override
	public Set<OrgResource> getLoginOrgs()
	{
		final LoginOrgConstraint loginOrgConstraint = getConstraint(LoginOrgConstraint.class)
				.orElse(LoginOrgConstraint.DEFAULT);

		return orgPermissions.getResourcesWithAccessThatMatch(Access.LOGIN, loginOrgConstraint.asOrgResourceMatcher());
	}

	@Override
	public Set<ClientId> getLoginClientIds()
	{
		return getLoginOrgs()
				.stream()
				.map(OrgResource::getClientId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public Optional<String> getOrgWhere(@Nullable final String tableName, final Access access)
	{
		final OrgIdAccessList adOrgIds = getOrgAccess(tableName, access);

		if (adOrgIds.isAny())
		{
			return Optional.empty();
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
				return Optional.of("AD_Org_ID=" + sb);
			}
			else
			{
				logger.error("No Access Org records");
				return Optional.of("AD_Org_ID=-1");    // No Access Record
			}
		}
		else
		{
			return Optional.of("AD_Org_ID IN (" + sb + ")");
		}
	}

	/**
	 * Access to Org
	 *
	 * @return true if access
	 */
	@Override
	public boolean isOrgAccess(@NonNull final OrgId orgId, @Nullable final String tableName, final Access access)
	{
		// Readonly access to "*" organization is always granted
		if (orgId.isAny() && access.isReadOnly())
		{
			return true;
		}

		final OrgIdAccessList orgs = getOrgAccess(tableName, access);
		if (orgs.isAny())
		{
			return true;
		}

		return orgs.contains(orgId);
	}    // isOrgAccess

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
		if (!isCanReport())                        // Role Level block
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
		if (!isCanExport())                        // Role Level block
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
		return recordAccessService().hasRecordPermission(
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
		rolePermLoggingBL().logWindowAccess(getRoleId(), AD_Window_ID.getRepoId(), access);
		return access;
	}

	@Override
	public ElementPermission checkWindowPermission(@NonNull final AdWindowId adWindowId)
	{
		final CustomizedWindowInfo customizedWindowInfo = getCustomizedWindowInfoMap().getCustomizedWindowInfo(adWindowId).orElse(null);
		if (customizedWindowInfo == null)
		{
			return windowPermissions.getPermission(adWindowId.getRepoId());
		}
		else
		{
			final ElementResource resource = windowPermissions.elementResource(adWindowId.getRepoId());
			return customizedWindowInfo.getWindowIdsFromBaseToCustomization()
					.stream()
					.map(currentWindowId -> windowPermissions.getPermission(currentWindowId.getRepoId()).withResource(resource))
					.reduce(ElementPermission.none(resource), ElementPermission::mergeWith);
		}
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
		rolePermLoggingBL().logProcessAccess(getRoleId(), AD_Process_ID, access);
		return access;
	}

	@Override
	public Boolean checkProcessAccess(final int AD_Process_ID)
	{
		return checkProcessPermission(AD_Process_ID).getReadWriteBoolean();
	}

	@Override
	public ElementPermission checkProcessPermission(final int AD_Process_ID)
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
		rolePermLoggingBL().logTaskAccess(getRoleId(), AD_Task_ID, access);
		return access;
	}

	@Override
	public Boolean checkTaskAccess(final int AD_Task_ID)
	{
		return checkTaskPermission(AD_Task_ID).getReadWriteBoolean();
	}

	@Override
	public ElementPermission checkTaskPermission(final int AD_Task_ID)
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
		rolePermLoggingBL().logTaskAccess(getRoleId(), AD_Form_ID, access);
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
		rolePermLoggingBL().logWorkflowAccess(getRoleId(), AD_Workflow_ID, access);
		return access;
	}

	@Override
	public Boolean checkWorkflowAccess(final int AD_Workflow_ID)
	{
		return checkWorkflowPermission(AD_Workflow_ID).getReadWriteBoolean();
	}

	@Override
	public ElementPermission checkWorkflowPermission(final int AD_Workflow_ID)
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
	 * TableLevel			S__ 100		4	System info
	 * SCO	111		7	System shared info
	 * SC_ 110		6	System/Client info
	 * _CO	011		3	Client shared info
	 * _C_	011		2	Client shared info
	 * __O	001		1	Organization info
	 * </code>
	 *
	 * @return true/false
	 * Access error info (AccessTableNoUpdate, AccessTableNoView) is saved in the log
	 * see org.compiere.model.MTabVO#loadTabDetails(MTabVO, ResultSet)
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
		final IMsgBL msgBL = Services.get(IMsgBL.class);
		final String tableAcessLevelTrl = msgBL.getMsg(Env.getCtx(), tableAcessLevel.getAD_Message());
		final String userAccessLevelTrl = msgBL.getMsg(Env.getCtx(), userAccessLevel.getAD_Message());
		MetasfreshLastError.saveWarning(logger, "AccessTableNoView", "Required=" + tableAcessLevel + "(" + tableAcessLevelTrl + ") != UserLevel=" + userAccessLevelTrl);
		return false;
	}    // canView

	@Override
	public boolean canView(final ClientId clientId, final OrgId orgId, final int AD_Table_ID, final int Record_ID)
	{
		return checkCanAccessRecord(clientId, orgId, AD_Table_ID, Record_ID, Access.READ)
				.isTrue();
	}

	@Override
	public BooleanWithReason checkCanView(final ClientId clientId, final OrgId orgId, final int AD_Table_ID, final int Record_ID)
	{
		return checkCanAccessRecord(clientId, orgId, AD_Table_ID, Record_ID, Access.READ);
	}

	@Override
	@Nullable
	public BooleanWithReason checkCanCreateNewRecord(@NonNull final ClientId clientId, @NonNull final OrgId orgId, @NonNull final AdTableId adTableId)
	{
		final int Record_ID = -1;
		return checkCanAccessRecord(clientId, orgId, adTableId.getRepoId(), Record_ID, Access.WRITE);
	}

	@Override
	public BooleanWithReason checkCanUpdate(final ClientId clientId, final OrgId orgId, final int AD_Table_ID, final int Record_ID)
	{
		return checkCanAccessRecord(clientId, orgId, AD_Table_ID, Record_ID, Access.WRITE);
	}

	@Override
	public boolean canUpdate(final ClientId clientId, final OrgId orgId, final int AD_Table_ID, final int Record_ID, final boolean saveWarning)
	{
		final BooleanWithReason canUpdate = checkCanUpdate(clientId, orgId, AD_Table_ID, Record_ID);
		if (canUpdate.isTrue())
		{
			return true;
		}
		else
		{
			if (saveWarning)
			{
				final String reason = canUpdate.getReasonAsString();
				MetasfreshLastError.saveWarning(logger, "AccessTableNoUpdate", reason);
				logger.warn("No update access: {}, {}", reason, this);
			}
			return false;
		}
	}

	private BooleanWithReason checkCanAccessRecord(
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
			return BooleanWithReason.TRUE; // OK
		}

		final ArrayList<String> missingAccesses = new ArrayList<>();

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

		final String tableName = TableIdsCache.instance.getTableName(AdTableId.ofRepoId(AD_Table_ID));
		// Org Access: Verify if the role has access to the given organization - teo_sarca, patch [ 1628050 ]
		if (missingAccesses.isEmpty() && !isOrgAccess(orgId, tableName, access))
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
			final AdMessageKey adMessage;
			if (access.isReadOnly())
			{
				adMessage = MSG_AccessTableNoView;
			}
			else if (access.isReadWrite())
			{
				adMessage = MSG_AccessTableNoUpdate;
			}
			else
			{
				adMessage = AdMessageKey.of("AccessTableNo" + access.getName());
			}

			final ITranslatableString noAccessReason = TranslatableStrings.builder()
					.appendADMessage(adMessage)
					.append(": ")
					.append(Joiner.on(", ").join(missingAccesses))
					.build();

			return BooleanWithReason.falseBecause(noAccessReason);
		}
		else
		{
			return BooleanWithReason.TRUE; // OK
		}
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

	private Set<String> getAllowedDocActions(final ClientId adClientId, final DocTypeId docTypeId)
	{
		final ArrayKey key = Util.mkKey(adClientId, docTypeId);
		return docActionsAllowed.computeIfAbsent(key, (k) -> retrieveAllowedDocActions(adClientId, docTypeId));
	}

	private Set<String> retrieveAllowedDocActions(final ClientId adClientId, final DocTypeId docTypeId)
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
				rolePermLoggingBL().logDocActionAccess(getRoleId(), optionsCtx.getDocTypeId(), targetDocAction, access);
			}
		}

		optionsCtx.setDocActionToUse(targetDocAction);
	}

	/**
	 * Get Role Where Clause.
	 * It will look something like myalias.AD_Role_ID IN (?, ?, ?).
	 *
	 * @param roleColumnSQL role columnname or role column SQL (e.g. myalias.AD_Role_ID)
	 * @param params        a list where the method will put SQL parameters.
	 *                      If null, this method will generate a not parametrized query
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
}

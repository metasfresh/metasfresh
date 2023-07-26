package de.metas.security.impl;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwares;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.metas.money.CurrencyId;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Role_Included;
import org.compiere.model.I_AD_User_Roles;
import org.compiere.model.I_AD_User_Substitute;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.cache.CCache;
import de.metas.menu.AdMenuId;
import de.metas.organization.OrgId;
import de.metas.security.IRoleDAO;
import de.metas.security.IRolesTreeNode;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.RoleInclude;
import de.metas.security.TableAccessLevel;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.Constraints;
import de.metas.security.permissions.DocumentApprovalConstraint;
import de.metas.security.permissions.GenericPermissions;
import de.metas.security.permissions.LoginOrgConstraint;
import de.metas.security.permissions.StartupWindowConstraint;
import de.metas.security.permissions.UIDisplayedEntityTypes;
import de.metas.security.permissions.UserPreferenceLevelConstraint;
import de.metas.security.permissions.WindowMaxQueryRecordsConstraint;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

public class RoleDAO implements IRoleDAO
{
	private CCache<RoleId, Role> rolesCache = CCache.<RoleId, Role> builder()
			.tableName(I_AD_Role.Table_Name)
			.build();

	@Override
	public Role getById(@NonNull final RoleId roleId)
	{
		return rolesCache.getOrLoad(roleId, this::retrieveById);
	}

	public Collection<Role> getByIds(@NonNull final Set<RoleId> roleIds)
	{
		return rolesCache.getAllOrLoad(roleIds, this::retrieveByIds);
	}

	private Role retrieveById(@NonNull final RoleId roleId)
	{
		final I_AD_Role record = loadOutOfTrx(roleId, I_AD_Role.class);
		return toRole(record);
	}

	private Map<RoleId, Role> retrieveByIds(@NonNull final Collection<RoleId> roleIds)
	{
		return loadByRepoIdAwares(ImmutableSet.copyOf(roleIds), I_AD_Role.class)
				.stream()
				.map(record -> toRole(record))
				.collect(GuavaCollectors.toImmutableMapByKey(Role::getId));
	}

	private static Role toRole(final I_AD_Role record)
	{
		return Role.builder()
				.id(RoleId.ofRepoId(record.getAD_Role_ID()))
				//
				.name(record.getName())
				.description(record.getDescription())
				//
				.clientId(ClientId.ofRepoId(record.getAD_Client_ID()))
				//
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.accessAllOrgs(record.isAccessAllOrgs())
				.useUserOrgAccess(record.isUseUserOrgAccess())
				//
				.supervisorId(UserId.ofRepoIdOrNull(record.getSupervisor_ID()))
				//
				.userLevel(TableAccessLevel.forUserLevel(record.getUserLevel()))
				.permissions(extractPermissions(record))
				.constraints(extractConstraints(record))
				//
				.manualMaintainance(record.isManual())
				//
				// Menu:
				.menuTreeId(AdTreeId.ofRepoIdOrNull(record.getAD_Tree_Menu_ID()))
				.rootMenuId(AdMenuId.ofRepoIdOrNull(record.getRoot_Menu_ID()))
				//
				.orgTreeId(AdTreeId.ofRepoIdOrNull(record.getAD_Tree_Org_ID()))
				//
				.webuiRole(record.isWEBUI_Role())
				//
				.updatedBy(UserId.ofRepoId(record.getUpdatedBy()))
				//
				.build();
	}

	private static GenericPermissions extractPermissions(final I_AD_Role record)
	{
		final GenericPermissions.Builder rolePermissions = GenericPermissions.builder();

		rolePermissions.addPermissionIfCondition(record.isAccessAllOrgs(), IUserRolePermissions.PERMISSION_AccessAllOrgs);

		rolePermissions.addPermissionIfCondition(record.isCanReport(), IUserRolePermissions.PERMISSION_CanReport);
		rolePermissions.addPermissionIfCondition(record.isCanExport(), IUserRolePermissions.PERMISSION_CanExport);
		rolePermissions.addPermissionIfCondition(record.isPersonalAccess(), IUserRolePermissions.PERMISSION_PersonalAccess);
		rolePermissions.addPermissionIfCondition(record.isPersonalLock(), IUserRolePermissions.PERMISSION_PersonalLock);
		rolePermissions.addPermissionIfCondition(record.isOverwritePriceLimit(), IUserRolePermissions.PERMISSION_OverwritePriceLimit);
		rolePermissions.addPermissionIfCondition(record.isChangeLog(), IUserRolePermissions.PERMISSION_ChangeLog);
		rolePermissions.addPermissionIfCondition(record.isMenuAvailable(), IUserRolePermissions.PERMISSION_MenuAvailable);
		rolePermissions.addPermissionIfCondition(record.isAutoRoleLogin(), IUserRolePermissions.PERMISSION_AutoRoleLogin);
		rolePermissions.addPermissionIfCondition(record.isAllowLoginDateOverride(), IUserRolePermissions.PERMISSION_AllowLoginDateOverride);
		rolePermissions.addPermissionIfCondition(record.isRoleAlwaysUseBetaFunctions(), IUserRolePermissions.PERMISSION_UseBetaFunctions);
		rolePermissions.addPermissionIfCondition(record.isAttachmentDeletionAllowed(), IUserRolePermissions.PERMISSION_IsAttachmentDeletionAllowed);

		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Product(), IUserRolePermissions.PERMISSION_InfoWindow_Product);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_BPartner(), IUserRolePermissions.PERMISSION_InfoWindow_BPartner);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Account(), IUserRolePermissions.PERMISSION_InfoWindow_Account);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Schedule(), IUserRolePermissions.PERMISSION_InfoWindow_Schedule);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_MRP(), IUserRolePermissions.PERMISSION_InfoWindow_MRP);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_CRP(), IUserRolePermissions.PERMISSION_InfoWindow_CRP);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Order(), IUserRolePermissions.PERMISSION_InfoWindow_Order);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Invoice(), IUserRolePermissions.PERMISSION_InfoWindow_Invoice);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_InOut(), IUserRolePermissions.PERMISSION_InfoWindow_InOut);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Payment(), IUserRolePermissions.PERMISSION_InfoWindow_Payment);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_CashJournal(), IUserRolePermissions.PERMISSION_InfoWindow_CashJournal);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Resource(), IUserRolePermissions.PERMISSION_InfoWindow_Resource);
		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Asset(), IUserRolePermissions.PERMISSION_InfoWindow_Asset);

		//
		// Accounting module
		rolePermissions.addPermissionIfCondition(record.isShowAcct(), IUserRolePermissions.PERMISSION_ShowAcct);

		rolePermissions.addPermissionIfCondition(record.isAllow_Info_Account(), IUserRolePermissions.PERMISSION_InfoWindow_Account);
		rolePermissions.addPermissionIfCondition(record.isAllowedTrlBox(), IUserRolePermissions.PERMISSION_TrlBox);
		rolePermissions.addPermissionIfCondition(record.isAllowedMigrationScripts(), IUserRolePermissions.PERMISSION_MigrationScripts);

		return rolePermissions.build();
	}

	private static final Constraints extractConstraints(final I_AD_Role record)
	{
		return Constraints.builder()
				.addConstraint(UserPreferenceLevelConstraint.forPreferenceType(record.getPreferenceType()))
				.addConstraint(WindowMaxQueryRecordsConstraint.of(record.getMaxQueryRecords(), record.getConfirmQueryRecords()))
				.addConstraintIfNotEquals(StartupWindowConstraint.ofAD_Form_ID(record.getAD_Form_ID()), StartupWindowConstraint.NULL)
				.addConstraint(DocumentApprovalConstraint.of(record.isCanApproveOwnDoc(), record.getAmtApproval(), CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID())))
				.addConstraint(extractLoginOrgConstraint(record))
				.addConstraint(UIDisplayedEntityTypes.of(record.isShowAllEntityTypes()))
				.build();
	}

	private static final LoginOrgConstraint extractLoginOrgConstraint(final I_AD_Role record)
	{
		final OrgId loginOrgId = record.getLogin_Org_ID() > 0
				? OrgId.ofRepoIdOrNull(record.getLogin_Org_ID())
				: null;
		return LoginOrgConstraint.of(loginOrgId, record.isOrgLoginMandatory());
	}

	@Override
	public List<Role> getUserRoles(final UserId userId)
	{
		final ImmutableSet<RoleId> roleIds = getUserRoleIds(userId);
		if (roleIds.isEmpty())
		{
			return ImmutableList.of();
		}
		else
		{
			return ImmutableList.copyOf(getByIds(roleIds));
		}
	}

	@Override
	@Cached(cacheName = I_AD_Role.Table_Name + "#For#AD_User_ID")
	public ImmutableSet<RoleId> getUserRoleIds(@NonNull final UserId userId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_User_Roles.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_Roles.COLUMN_AD_User_ID, userId)
				.andCollect(I_AD_User_Roles.COLUMN_AD_Role_ID)
				.addOnlyActiveRecordsFilter()
				.orderBy(I_AD_Role.COLUMN_AD_Role_ID)
				.create()
				.listIds(RoleId::ofRepoId);
	}

	@Override
	public final Set<RoleId> getSubstituteRoleIds(@NonNull final UserId adUserId, @NonNull final LocalDate date)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_User_Substitute.class)
				.addOnlyActiveRecordsFilter()
				.addValidFromToMatchesFilter(I_AD_User_Substitute.COLUMN_ValidFrom, I_AD_User_Substitute.COLUMN_ValidTo, TimeUtil.asDate(date))
				.addEqualsFilter(I_AD_User_Substitute.COLUMN_Substitute_ID, adUserId)
				//
				// Collect users which can be substituted by given user
				.andCollect(I_AD_User_Substitute.COLUMN_AD_User_ID)
				.addOnlyActiveRecordsFilter()
				//
				// Collect user-role assignments of those users
				.andCollectChildren(I_AD_User_Roles.COLUMN_AD_User_ID, I_AD_User_Roles.class)
				.addOnlyActiveRecordsFilter()
				//
				// Collect the roles from those assignments
				.andCollect(I_AD_User_Roles.COLUMN_AD_Role_ID)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy(I_AD_Role.COLUMN_AD_Role_ID) // just to have a predictible order
				//
				.create()
				.listIds(RoleId::ofRepoId);
	}

	@Override
	@Cached(cacheName = I_AD_Role_Included.Table_Name + "#by#AD_Role_ID", expireMinutes = Cached.EXPIREMINUTES_Never)
	public List<RoleInclude> retrieveRoleIncludes(@NonNull final RoleId adRoleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Role_Included.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Role_Included.COLUMN_AD_Role_ID, adRoleId)
				.orderBy(I_AD_Role_Included.COLUMNNAME_SeqNo)
				.orderBy(I_AD_Role_Included.COLUMN_Included_Role_ID)
				//
				.create()
				.stream()
				.map(record -> toRoleInclude(record))
				.collect(ImmutableList.toImmutableList());
	}

	private static RoleInclude toRoleInclude(final I_AD_Role_Included record)
	{
		return RoleInclude.builder()
				.childRoleId(RoleId.ofRepoId(record.getIncluded_Role_ID()))
				.seqNo(record.getSeqNo())
				.build();
	}

	@Override
	public IRolesTreeNode retrieveRolesTree(final RoleId adRoleId, UserId substituteForUserId, LocalDate substituteDate)
	{
		return RolesTreeNode.of(adRoleId, substituteForUserId, substituteDate);
	}

	@Override
	public Collection<Role> retrieveAllRolesWithAutoMaintenance()
	{
		final Set<RoleId> roleIds = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Role.class)
				.addEqualsFilter(I_AD_Role.COLUMNNAME_IsManual, false)
				.addOnlyActiveRecordsFilter()
				.create()
				.listIds(RoleId::ofRepoId);

		return getByIds(roleIds);
	}

	@Override
	public Collection<Role> retrieveAllRolesWithUserAccess()
	{
		final Set<RoleId> roleIds = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Role.class)
				.addEqualsFilter(I_AD_Role.COLUMNNAME_IsManual, false)
				.addOnlyActiveRecordsFilter()
				.create()
				.setRequiredAccess(Access.READ)
				.listIds(RoleId::ofRepoId);

		return getByIds(roleIds);
	}

	@Override
	public String getRoleName(final RoleId adRoleId)
	{
		if (adRoleId == null)
		{
			return "?";
		}

		final Role role = getById(adRoleId);
		return role.getName();
	}

	@Override
	// @Cached(cacheName = I_AD_User_Roles.Table_Name + "#by#AD_Role_ID", expireMinutes = 0) // not sure if caching is needed...
	public Set<UserId> retrieveUserIdsForRoleId(final RoleId adRoleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Roles.class)
				.addEqualsFilter(I_AD_User_Roles.COLUMN_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_AD_User_Roles.COLUMNNAME_AD_User_ID, Integer.class)
				.stream()
				.map(UserId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public RoleId retrieveFirstRoleIdForUserId(final UserId userId)
	{
		final Integer firstRoleId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Roles.class)
				.addEqualsFilter(I_AD_User_Roles.COLUMN_AD_User_ID, userId)
				.addOnlyActiveRecordsFilter()
				.create()
				.first(I_AD_User_Roles.COLUMNNAME_AD_Role_ID, Integer.class);
		return firstRoleId == null ? null : RoleId.ofRepoIdOrNull(firstRoleId);
	}

	@Override
	public void createUserRoleAssignmentIfMissing(final UserId adUserId, final RoleId adRoleId)
	{
		if (hasUserRoleAssignment(adUserId, adRoleId))
		{
			return;
		}

		final I_AD_User_Roles userRole = InterfaceWrapperHelper.newInstance(I_AD_User_Roles.class);
		userRole.setAD_User_ID(adUserId.getRepoId());
		userRole.setAD_Role_ID(adRoleId.getRepoId());
		InterfaceWrapperHelper.save(userRole);

		Services.get(IUserRolePermissionsDAO.class).resetCacheAfterTrxCommit();
	}

	private boolean hasUserRoleAssignment(final UserId adUserId, final RoleId adRoleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Roles.class)
				.addEqualsFilter(I_AD_User_Roles.COLUMNNAME_AD_User_ID, adUserId)
				.addEqualsFilter(I_AD_User_Roles.COLUMN_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
	}

}

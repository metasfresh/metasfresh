package de.metas.security.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.document.DocTypeId;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.AdProcessId;
import de.metas.security.IRoleDAO;
import de.metas.security.IRolesTreeNode;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.Principal;
import de.metas.security.Role;
import de.metas.security.RoleId;
import de.metas.security.UserRolePermissionsEventBus;
import de.metas.security.UserRolePermissionsKey;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.ElementPermission;
import de.metas.security.permissions.ElementPermissions;
import de.metas.security.permissions.ElementResource;
import de.metas.security.permissions.OrgPermission;
import de.metas.security.permissions.OrgPermissions;
import de.metas.security.permissions.OrgResource;
import de.metas.security.permissions.PermissionsBuilder.CollisionPolicy;
import de.metas.security.permissions.TableColumnPermission;
import de.metas.security.permissions.TableColumnPermissions;
import de.metas.security.permissions.TableColumnResource;
import de.metas.security.permissions.TableOrgPermission;
import de.metas.security.permissions.TableOrgPermissions;
import de.metas.security.permissions.TableOrgResource;
import de.metas.security.permissions.TablePermission;
import de.metas.security.permissions.TablePermissions;
import de.metas.security.permissions.TableResource;
import de.metas.security.requests.CreateDocActionAccessRequest;
import de.metas.security.requests.CreateFormAccessRequest;
import de.metas.security.requests.CreateProcessAccessRequest;
import de.metas.security.requests.CreateRecordPrivateAccessRequest;
import de.metas.security.requests.CreateTaskAccessRequest;
import de.metas.security.requests.CreateWindowAccessRequest;
import de.metas.security.requests.CreateWorkflowAccessRequest;
import de.metas.security.requests.RemoveDocActionAccessRequest;
import de.metas.security.requests.RemoveFormAccessRequest;
import de.metas.security.requests.RemoveProcessAccessRequest;
import de.metas.security.requests.RemoveRecordPrivateAccessRequest;
import de.metas.security.requests.RemoveTaskAccessRequest;
import de.metas.security.requests.RemoveWindowAccessRequest;
import de.metas.security.requests.RemoveWorkflowAccessRequest;
import de.metas.user.UserGroupId;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.impl.TableIdsCache;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Column_Access;
import org.compiere.model.I_AD_Document_Action_Access;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Form_Access;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Private_Access;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_Included;
import org.compiere.model.I_AD_Role_OrgAccess;
import org.compiere.model.I_AD_Role_TableOrg_Access;
import org.compiere.model.I_AD_Table_Access;
import org.compiere.model.I_AD_Task;
import org.compiere.model.I_AD_Task_Access;
import org.compiere.model.I_AD_User_OrgAccess;
import org.compiere.model.I_AD_User_Roles;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_AD_Window_Access;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_AD_Workflow_Access;
import org.compiere.model.I_C_OrgAssignment;
import org.compiere.model.X_AD_Table_Access;
import org.compiere.util.DB;
import org.slf4j.Logger;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class UserRolePermissionsDAO implements IUserRolePermissionsDAO
{
	private static final transient Logger logger = LogManager.getLogger(UserRolePermissionsDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IRoleDAO roleDAO = Services.get(IRoleDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private static final ImmutableSet<String> ROLE_DEPENDENT_TABLENAMES = ImmutableSet.of(
			// I_AD_Role.Table_Name // NEVER include the AD_Role
			I_AD_User_Roles.Table_Name, // User to Role assignment (see https://github.com/metasfresh/metasfresh-webui-api/issues/482)
			// Included role
			I_AD_Role_Included.Table_Name,
			// Org Access
			I_AD_User_OrgAccess.Table_Name,
			I_AD_Role_OrgAccess.Table_Name,
			I_AD_Role_TableOrg_Access.Table_Name,
			// Access records
			I_AD_Window_Access.Table_Name,
			I_AD_Process_Access.Table_Name,
			I_AD_Form_Access.Table_Name,
			I_AD_Workflow_Access.Table_Name,
			I_AD_Task_Access.Table_Name,
			I_AD_Document_Action_Access.Table_Name,
			// Table/Record access
			I_AD_Table_Access.Table_Name);

	private final AtomicBoolean accountingModuleActive = new AtomicBoolean(false);

	private final AtomicLong version = new AtomicLong(1);

	/**
	 * Aggregated permissions per key
	 
	 */
	private final CCache<UserRolePermissionsKey, IUserRolePermissions> //
			permissionsByKey = CCache.<UserRolePermissionsKey, IUserRolePermissions>builder()
			.tableName(I_AD_Role.Table_Name)
			.build();

	/**
	 * Individual (not-aggregated) permissions per key
	 */
	private final CCache<UserRolePermissionsKey, UserRolePermissions> individialPermissionsByKey = CCache.<UserRolePermissionsKey, UserRolePermissions>builder()
			.tableName(I_AD_Role.Table_Name)
			.build();

	private final CCache<RoleOrgPermissionsCacheKey, OrgPermissions> roleOrgPermissionsCache = CCache.<RoleOrgPermissionsCacheKey, OrgPermissions>builder()
			.tableName(I_AD_Role_OrgAccess.Table_Name)
			.build();

	private final CCache<UserOrgPermissionsCacheKey, OrgPermissions> userOrgPermissionsCache = CCache.<UserOrgPermissionsCacheKey, OrgPermissions>builder()
			.tableName(I_AD_User_OrgAccess.Table_Name)
			.build();

	private final CCache<RoleId, ElementPermissions> windowPermissionsCache = CCache.<RoleId, ElementPermissions>builder()
			.tableName(I_AD_Window_Access.Table_Name)
			.build();

	private final CCache<RoleId, ElementPermissions> processPermissionsCache = CCache.<RoleId, ElementPermissions>builder()
			.tableName(I_AD_Process_Access.Table_Name)
			.build();

	private final CCache<RoleId, ElementPermissions> taskPermissionsCache = CCache.<RoleId, ElementPermissions>builder()
			.tableName(I_AD_Task_Access.Table_Name)
			.build();

	private final CCache<RoleId, ElementPermissions> formPermissionsCache = CCache.<RoleId, ElementPermissions>builder()
			.tableName(I_AD_Form_Access.Table_Name)
			.build();

	private final CCache<RoleId, ElementPermissions> workflowPermissionsCache = CCache.<RoleId, ElementPermissions>builder()
			.tableName(I_AD_Workflow_Access.Table_Name)
			.build();

	private final CCache<RoleId, TablePermissions> tablePermissionsCache = CCache.<RoleId, TablePermissions>builder()
			.tableName(I_AD_Table_Access.Table_Name)
			.build();

	private final CCache<RoleId, TableColumnPermissions> columnPermissionsCache = CCache.<RoleId, TableColumnPermissions>builder()
			.tableName(I_AD_Column_Access.Table_Name)
			.build();

	@Override
	public Set<String> getRoleDependentTableNames()
	{
		return ROLE_DEPENDENT_TABLENAMES;
	}

	@Override
	public long getCacheVersion()
	{
		return version.get();
	}

	@Override
	public void resetCacheAfterTrxCommit()
	{
		final ITrx trx = trxManager.getTrxOrNull(ITrx.TRXNAME_ThreadInherited);

		// If running out of transaction, reset the cache now
		if (trx == null)
		{
			logger.info("No current running transaction. Reseting the cache now.");
			resetCache(true);
		}
		else
		{
			final String TRXPROPERTY_ResetCache = getClass().getName() + ".ResetCache";

			final Supplier<Boolean> valueInitializer = () -> {

				trx.getTrxListenerManager()
						.newEventListener(TrxEventTiming.AFTER_COMMIT)
						.registerWeakly(false) // register "hard", because that's how it was before
						.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
						.registerHandlingMethod(innerTrx -> {

							logger.info("Reseting the cache because transaction was commited: {}", innerTrx);
							resetCache(true);
						});
				logger.info("Scheduled cache reset after trx commit: {}", trx);
				return Boolean.TRUE;
			};
			trx.getProperty(TRXPROPERTY_ResetCache, valueInitializer);
		}
	}

	@Override
	public void resetLocalCache()
	{
		final boolean broadcast = false;
		resetCache(broadcast);
	}

	private static final AtomicBoolean resetCacheRunning = new AtomicBoolean(false);

	private void resetCache(final boolean broadcast)
	{
		// If already running, do nothing (avoid StackOverflowError)
		final boolean alreadyRunning = resetCacheRunning.getAndSet(true);
		if (alreadyRunning)
		{
			return;
		}

		try
		{
			version.incrementAndGet();

			individialPermissionsByKey.reset();
			permissionsByKey.reset();

			final CacheMgt cacheManager = CacheMgt.get();
			cacheManager.resetLocal(I_AD_Role.Table_Name); // cache reset role itself
			ROLE_DEPENDENT_TABLENAMES.forEach(cacheManager::resetLocal);
			logger.info("Finished permissions cache reset");
		}
		finally
		{
			resetCacheRunning.compareAndSet(true, false);
		}

		if (broadcast)
		{
			UserRolePermissionsEventBus.fireCacheResetEvent();
		}
	}

	@Override
	public List<IUserRolePermissions> retrieveUserRolesPermissionsForUserWithOrgAccess(
			@NonNull final ClientId clientId,
			@NonNull final OrgId adOrgId,
			@NonNull final UserId adUserId,
			@NonNull final LocalDate date)
	{
		final ImmutableList.Builder<IUserRolePermissions> permissionsWithOrgAccess = ImmutableList.builder();
		for (final RoleId roleId : roleDAO.getUserRoleIds(adUserId))
		{
			final IUserRolePermissions permissions = getUserRolePermissions(roleId, adUserId, clientId, date);
			if (permissions.isOrgAccess(adOrgId, null, Access.READ)) // readonly access is fine for us
			{
				permissionsWithOrgAccess.add(permissions);
			}
		}

		return permissionsWithOrgAccess.build();
	}

	@Override
	public Optional<IUserRolePermissions> retrieveFirstUserRolesPermissionsForUserWithOrgAccess(
			final ClientId clientId,
			final OrgId adOrgId,
			final UserId adUserId,
			final LocalDate date)
	{
		for (final RoleId roleId : roleDAO.getUserRoleIds(adUserId))
		{
			final IUserRolePermissions permissions = getUserRolePermissions(roleId, adUserId, clientId, date);
			if (permissions.isOrgAccess(adOrgId, null, Access.READ)) // readonly access is fine for us
			{
				return Optional.of(permissions);
			}
		}

		return Optional.empty();
	}

	@Override
	public boolean isAdministrator(final ClientId clientId, final UserId adUserId, final LocalDate date)
	{
		return matchUserRolesPermissionsForUser(clientId, adUserId, date, IUserRolePermissions::isSystemAdministrator);
	}

	@Override
	public boolean matchUserRolesPermissionsForUser(
			final ClientId clientId,
			final UserId adUserId,
			final LocalDate date,
			final Predicate<IUserRolePermissions> matcher)
	{
		for (final RoleId roleId : roleDAO.getUserRoleIds(adUserId))
		{
			final IUserRolePermissions permissions = getUserRolePermissions(roleId, adUserId, clientId, date);

			if (matcher.test(permissions))
			{
				return true;
			}
		}

		return false;

	}

	@Override
	public IUserRolePermissions getUserRolePermissions(
			final RoleId adRoleId,
			final UserId adUserId,
			final ClientId adClientId,
			final LocalDate date)
	{
		final UserRolePermissionsKey key = UserRolePermissionsKey.of(adRoleId, adUserId, adClientId, date);
		return getUserRolePermissions(key);
	}

	@Override
	public IUserRolePermissions getUserRolePermissions(@NonNull final UserRolePermissionsKey key)
	{
		return permissionsByKey.getOrLoad(key, this::retrieveUserRolePermissions);
	}

	private IUserRolePermissions retrieveUserRolePermissions(@NonNull final UserRolePermissionsKey key)
	{
		final RoleId adRoleId = key.getRoleId();
		final UserId adUserId = key.getUserId();
		final ClientId adClientId = key.getClientId();
		final LocalDate date = key.getDate();

		try
		{
			final IRolesTreeNode rootRole = roleDAO.retrieveRolesTree(adRoleId, adUserId, date);
			return rootRole.aggregateBottomUp(new IRolesTreeNode.BottomUpAggregator<UserRolePermissions, UserRolePermissionsBuilder>()
			{
				@Override
				public UserRolePermissionsBuilder initialValue(final IRolesTreeNode node)
				{
					final UserRolePermissions permissions = getIndividualUserRolePermissions(node.getRoleId(), adUserId, adClientId);
					return UserRolePermissionsBuilder.of(UserRolePermissionsDAO.this, permissions);
				}

				@Override
				public void aggregateValue(final UserRolePermissionsBuilder aggregatedValue, final IRolesTreeNode childNode, final UserRolePermissions value)
				{
					aggregatedValue.includeUserRolePermissions(value, childNode.getSeqNo());
				}

				@Override
				public UserRolePermissions finalValue(final UserRolePermissionsBuilder aggregatedValue)
				{
					return aggregatedValue.build();
				}

				@Override
				public UserRolePermissions leafValue(final IRolesTreeNode node)
				{
					return getIndividualUserRolePermissions(node.getRoleId(), adUserId, adClientId);
				}
			});
		}
		catch (final Exception e)
		{
			throw new RolePermissionsNotFoundException("@AD_Role_ID@=" + adRoleId
					+ ", @AD_User_ID@=" + adUserId
					+ ", @AD_Client_ID@=" + adClientId
					+ ", @Date@=" + date, e);
		}
	}

	final UserRolePermissions getIndividualUserRolePermissions(final RoleId adRoleId, final UserId adUserId, final ClientId adClientId)
	{
		final UserRolePermissionsKey key = UserRolePermissionsKey.of(adRoleId, adUserId, adClientId, LocalDate.MIN);
		return individialPermissionsByKey.getOrLoad(key, () -> new UserRolePermissionsBuilder(this)
				.setRoleId(adRoleId)
				.setUserId(adUserId)
				.setClientId(adClientId)
				.build());
	}

	public OrgPermissions retrieveOrgPermissions(final Role role, final UserId adUserId)
	{
		final AdTreeId adTreeOrgId = role.getOrgTreeId();
		if (role.isUseUserOrgAccess())
		{
			return getUserOrgPermissions(adUserId, adTreeOrgId);
		}
		else
		{

			if (role.isAccessAllOrgs())
			{
				final ClientId clientId = role.getClientId();

				//
				// if role has acces all org, then behave as would be * access
				final OrgPermissions.Builder builder = OrgPermissions.builder(adTreeOrgId);

				// org *
				{
					final OrgResource resource = OrgResource.anyOrg(clientId);
					final OrgPermission permission = OrgPermission.ofResourceAndReadOnly(resource, false);
					builder.addPermission(permission);
				}

				//
				// now add all orgs
				final List<I_AD_Org> clientOrgs = orgDAO.retrieveClientOrgs(clientId.getRepoId());
				for (final I_AD_Org org : clientOrgs)
				{
					// skip inative orgs
					if (!org.isActive())
					{
						continue;
					}

					final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());
					final OrgResource orgResource = OrgResource.of(clientId, orgId, org.isSummary());
					final OrgPermission orgPermission = OrgPermission.ofResourceAndReadOnly(orgResource, false);
					builder.addPermission(orgPermission);
				}
				return builder.build();
			}
			else
			{
				return getRoleOrgPermissions(role.getId(), adTreeOrgId);
			}
		}

	}

	private OrgPermissions getUserOrgPermissions(UserId userId, AdTreeId adTreeOrgId)
	{
		return userOrgPermissionsCache.getOrLoad(
				UserOrgPermissionsCacheKey.of(userId, adTreeOrgId),
				this::retrieveUserOrgPermissions);
	}

	private OrgPermissions retrieveUserOrgPermissions(final UserOrgPermissionsCacheKey key)
	{
		final UserId adUserId = key.getUserId();
		final AdTreeId adTreeOrgId = key.getAdTreeOrgId();

		final ImmutableMap<OrgId, I_AD_Org> activeOrgsById = Maps.uniqueIndex(
				orgDAO.getAllActiveOrgs(),
				org -> OrgId.ofRepoId(org.getAD_Org_ID()));

		final OrgPermissions.Builder builder = OrgPermissions.builder(adTreeOrgId);
		if (activeOrgsById.isEmpty())
		{
			return builder.build();
		}

		final List<I_AD_User_OrgAccess> orgAccessesList = queryBL
				.createQueryBuilderOutOfTrx(I_AD_User_OrgAccess.class)
				.addEqualsFilter(I_AD_User_OrgAccess.COLUMNNAME_AD_User_ID, adUserId)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_User_OrgAccess.COLUMNNAME_AD_Org_ID, activeOrgsById.keySet())
				.create()
				.list();

		for (final I_AD_User_OrgAccess oa : orgAccessesList)
		{
			// NOTE: we are fetching the AD_Client_ID from OrgAccess and not from AD_Org (very important for Org=0 like) !
			final ClientId clientId = ClientId.ofRepoId(oa.getAD_Client_ID());
			final OrgId orgId = OrgId.ofRepoId(oa.getAD_Org_ID());
			final boolean isGroupingOrg = activeOrgsById.get(orgId).isSummary();
			final OrgResource resource = OrgResource.of(clientId, orgId, isGroupingOrg);
			final OrgPermission permission = OrgPermission.ofResourceAndReadOnly(resource, oa.isReadOnly());
			builder.addPermissionRecursively(permission);
		}

		return builder.build();
	}

	public TableOrgPermissions retrieveTableOrgPermissions(final RoleId adRoleId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);

		final TableOrgPermissions.Builder builder = TableOrgPermissions.builder();

		queryBL.createQueryBuilderOutOfTrx(I_AD_Role_TableOrg_Access.class)
				.addEqualsFilter(I_AD_Role_TableOrg_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(this::toTableOrgPermission)
				.forEach(permission -> builder.addPermission(permission, CollisionPolicy.Merge));

		return builder.build();
	}

	private TableOrgPermission toTableOrgPermission(@NonNull final I_AD_Role_TableOrg_Access record)
	{
		return TableOrgPermission.builder()
				.resource(extractTableOrgResource(record))
				.access(Access.ofCode(record.getAccess()))
				.build();
	}

	private TableOrgResource extractTableOrgResource(@NonNull final I_AD_Role_TableOrg_Access record)
	{
		final AdTableId adTableId = AdTableId.ofRepoId(record.getAD_Table_ID());
		@NonNull final String tableName = TableIdsCache.instance.getTableName(adTableId);
		return TableOrgResource.builder()
				.tableName(tableName)
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.build();
	}

	private OrgPermissions getRoleOrgPermissions(final RoleId adRoleId, final AdTreeId adTreeOrgId)
	{
		return roleOrgPermissionsCache.getOrLoad(
				RoleOrgPermissionsCacheKey.of(adRoleId, adTreeOrgId),
				this::retrieveRoleOrgPermissions);
	}

	private OrgPermissions retrieveRoleOrgPermissions(final RoleOrgPermissionsCacheKey key)
	{
		final RoleId adRoleId = key.getRoleId();
		final AdTreeId adTreeOrgId = key.getAdTreeOrgId();

		final ImmutableMap<OrgId, I_AD_Org> activeOrgsById = Maps.uniqueIndex(
				orgDAO.getAllActiveOrgs(),
				org -> OrgId.ofRepoId(org.getAD_Org_ID()));

		final OrgPermissions.Builder builder = OrgPermissions.builder(adTreeOrgId);
		if (activeOrgsById.isEmpty())
		{
			return builder.build();
		}

		final List<I_AD_Role_OrgAccess> orgAccessesList = queryBL
				.createQueryBuilderOutOfTrx(I_AD_Role_OrgAccess.class)
				.addEqualsFilter(I_AD_Role_OrgAccess.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_AD_Role_OrgAccess.COLUMNNAME_AD_Org_ID, activeOrgsById.keySet())
				.create()
				.list();

		for (final I_AD_Role_OrgAccess oa : orgAccessesList)
		{
			// NOTE: we are fetching the AD_Client_ID from OrgAccess and not from AD_Org (very important for Org=0 like) !
			final ClientId clientId = ClientId.ofRepoId(oa.getAD_Client_ID());
			final OrgId orgId = OrgId.ofRepoId(oa.getAD_Org_ID());
			final boolean isGroupingOrg = activeOrgsById.get(orgId).isSummary();
			final OrgResource resource = OrgResource.of(clientId, orgId, isGroupingOrg);
			final OrgPermission permission = OrgPermission.ofResourceAndReadOnly(resource, oa.isReadOnly());
			builder.addPermissionRecursively(permission);
		}

		return builder.build();
	}

	ElementPermissions getWindowPermissions(final RoleId adRoleId)
	{
		return windowPermissionsCache.getOrLoad(adRoleId, this::retrieveWindowPermissions0);
	}

	private ElementPermissions retrieveWindowPermissions0(final RoleId adRoleId)
	{
		return retrieveElementPermissions(adRoleId,
				I_AD_Window_Access.class,
				I_AD_Window.Table_Name,
				I_AD_Window_Access.COLUMNNAME_AD_Window_ID);
	}

	ElementPermissions getProcessPermissions(final RoleId adRoleId)
	{
		return processPermissionsCache.getOrLoad(adRoleId, this::retrieveProcessPermissions0);
	}

	private ElementPermissions retrieveProcessPermissions0(final RoleId adRoleId)
	{
		return retrieveElementPermissions(adRoleId,
				I_AD_Process_Access.class,
				I_AD_Process.Table_Name,
				I_AD_Process_Access.COLUMNNAME_AD_Process_ID);
	}

	ElementPermissions getTaskPermissions(final RoleId adRoleId)
	{
		return taskPermissionsCache.getOrLoad(adRoleId, this::retrieveTaskPermissions0);
	}

	private ElementPermissions retrieveTaskPermissions0(final RoleId adRoleId)
	{
		return retrieveElementPermissions(adRoleId,
				I_AD_Task_Access.class,
				I_AD_Task.Table_Name,
				I_AD_Task_Access.COLUMNNAME_AD_Task_ID);
	}

	ElementPermissions getFormPermissions(final RoleId adRoleId)
	{
		return formPermissionsCache.getOrLoad(adRoleId, this::retrieveFormPermissions0);
	}

	private ElementPermissions retrieveFormPermissions0(final RoleId adRoleId)
	{
		return retrieveElementPermissions(adRoleId,
				I_AD_Form_Access.class,
				I_AD_Form.Table_Name,
				I_AD_Form_Access.COLUMNNAME_AD_Form_ID);
	}

	ElementPermissions getWorkflowPermissions(final RoleId adRoleId)
	{
		return workflowPermissionsCache.getOrLoad(adRoleId, this::retrieveWorkflowPermissions0);
	}

	private ElementPermissions retrieveWorkflowPermissions0(final RoleId adRoleId)
	{
		return retrieveElementPermissions(adRoleId,
				I_AD_Workflow_Access.class,
				I_AD_Workflow.Table_Name,
				I_AD_Workflow_Access.COLUMNNAME_AD_Workflow_ID);
	}

	private <AccessTableType> ElementPermissions retrieveElementPermissions(
			final RoleId adRoleId,
			final Class<AccessTableType> accessTableClass,
			final String elementTableName,
			final String elementColumnName)
	{
		//
		// EntityType filter: filter out those elements where EntityType is not displayed
		final String accessTableName = InterfaceWrapperHelper.getTableName(accessTableClass);
		final IQueryFilter<AccessTableType> entityTypeFilter = TypedSqlQueryFilter.of("exists (select 1 from " + elementTableName + " t"
				+ " where t." + elementColumnName + "=" + accessTableName + "." + elementColumnName
				+ " and (" + EntityTypesCache.instance.getDisplayedInUIEntityTypeSQLWhereClause("t.EntityType") + ")"
				+ ")");

		final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";
		final String COLUMNNAME_IsReadWrite = "IsReadWrite";

		final List<Map<String, Object>> accessesList = queryBL
				.createQueryBuilderOutOfTrx(accessTableClass)
				.addEqualsFilter(COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.filter(entityTypeFilter)
				.create()
				.listDistinct(elementColumnName, COLUMNNAME_IsReadWrite);

		final ElementPermissions.Builder elementAccessesBuilder = ElementPermissions.builder()
				.setElementTableName(elementTableName);
		for (final Map<String, Object> accessItem : accessesList)
		{
			final int elementId = (int)accessItem.get(elementColumnName);
			final ElementResource resource = ElementResource.of(elementTableName, elementId);
			final boolean readWrite = StringUtils.toBoolean(accessItem.get(COLUMNNAME_IsReadWrite));
			final ElementPermission access = ElementPermission.ofReadWriteFlag(resource, readWrite);
			elementAccessesBuilder.addPermission(access);
		}

		return elementAccessesBuilder.build();
	}

	TablePermissions getTablePermissions(@NonNull final RoleId adRoleId)
	{
		return tablePermissionsCache.getOrLoad(adRoleId, this::retrieveTablePermissions0);
	}

	private TablePermissions retrieveTablePermissions0(@NonNull final RoleId adRoleId)
	{
		final List<I_AD_Table_Access> tableAccessRecords = queryBL
				.createQueryBuilderOutOfTrx(I_AD_Table_Access.class)
				.addEqualsFilter(I_AD_Table_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final TablePermissions.Builder permissionsCollector = TablePermissions.builder();

		// Default permission: allow all because actually this is an "exclude" list (if no include options were found).
		final HashSet<Access> defaultPermissionAccesses = new HashSet<>(TablePermission.ALL_ACCESSES);

		for (final I_AD_Table_Access tableAccessRecord : tableAccessRecords)
		{
			final TableResource resource = TableResource.ofAD_Table_ID(tableAccessRecord.getAD_Table_ID());
			final HashSet<Access> permissionAccesses = new HashSet<>();

			final String type = tableAccessRecord.getAccessTypeRule();
			final boolean exclude = tableAccessRecord.isExclude();
			if (X_AD_Table_Access.ACCESSTYPERULE_Accessing.equals(type))
			{
				final boolean readOnly = tableAccessRecord.isReadOnly();
				if (exclude)
				{
					// If you Exclude Access to a table and select Read Only,
					// you can only read data (otherwise no access).
					if (readOnly)
					{
						permissionAccesses.add(Access.READ);
						// permissionAccesses.remove(Access.WRITE); // not needed
					}
				}
				// include access
				else
				{
					permissionAccesses.add(Access.READ);
					if (!readOnly)
					{
						permissionAccesses.add(Access.WRITE);
					}

					// A include access implies that the default access is not granted
					defaultPermissionAccesses.remove(Access.READ);
					defaultPermissionAccesses.remove(Access.WRITE);
				}
			}
			else if (X_AD_Table_Access.ACCESSTYPERULE_Reporting.equals(type))
			{
				if (tableAccessRecord.isCanReport())
				{
					permissionAccesses.add(Access.REPORT);
				}

				// A include access implies that the default access is not granted
				if (!exclude)
				{
					defaultPermissionAccesses.remove(Access.REPORT);
				}
			}
			else if (X_AD_Table_Access.ACCESSTYPERULE_Exporting.equals(type))
			{
				if (tableAccessRecord.isCanExport())
				{
					permissionAccesses.add(Access.EXPORT);
				}

				// A include access implies that the default access is not granted
				if (!exclude)
				{
					defaultPermissionAccesses.remove(Access.EXPORT);
				}
			}
			else
			{
				throw new IllegalStateException("Unknown AccessRuleType: " + type);
			}

			final TablePermission permissions = TablePermission.builder()
					.resource(resource)
					.accesses(permissionAccesses)
					.build();
			permissionsCollector.addPermission(permissions, CollisionPolicy.Override);
		}

		//
		// Add default permission
		final TablePermission defaultPermissions = TablePermission.builder()
				.resource(TableResource.ANY_TABLE)
				.accesses(defaultPermissionAccesses)
				.build();
		permissionsCollector.addPermission(defaultPermissions, CollisionPolicy.Override);

		return permissionsCollector.build();
	}    // loadTableAccess

	TableColumnPermissions getTableColumnPermissions(final RoleId adRoleId)
	{
		return columnPermissionsCache.getOrLoad(adRoleId, this::retrieveTableColumnPermissions0);
	}

	private TableColumnPermissions retrieveTableColumnPermissions0(final RoleId adRoleId)
	{
		final List<I_AD_Column_Access> columnAccessList = queryBL
				.createQueryBuilderOutOfTrx(I_AD_Column_Access.class)
				.addEqualsFilter(I_AD_Column_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		// Default permission: allow all because actually this is an "exclude" list (if no include options were found).
		final TableColumnPermission.Builder defaultPermission = TableColumnPermission.ALL.asNewBuilder();

		final TableColumnPermissions.Builder builder = TableColumnPermissions.builder();

		for (final I_AD_Column_Access columnAccess : columnAccessList)
		{
			final TableColumnResource resource = TableColumnResource.of(columnAccess.getAD_Table_ID(), columnAccess.getAD_Column_ID());
			final TableColumnPermission.Builder permission = TableColumnPermission.builder()
					.setResource(resource)
					.removeAllAccesses();

			final boolean readOnly = columnAccess.isReadOnly();
			if (columnAccess.isExclude())
			{
				// If you Exclude Access to a column and select Read Only,
				// you can only read data (otherwise no access).
				if (readOnly)
				{
					permission.addAccess(Access.READ);
					// permission.removeAccess(Access.WRITE); // not needed
				}
			}
			// include access
			else
			{
				permission.addAccess(Access.READ);
				if (!readOnly)
				{
					permission.addAccess(Access.WRITE);
				}

				// A include access implies that the default access is not granted
				defaultPermission.removeAccess(Access.READ);
				defaultPermission.removeAccess(Access.WRITE);
			}

			builder.addPermission(permission.build(), CollisionPolicy.Override);
		}

		// Add default permission
		builder.addPermission(defaultPermission.build(), CollisionPolicy.Override);

		return builder.build();
	}    // loadColumnAccess

	@Override
	public void updateAccessRecordsForAllRoles()
	{
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, //
				"SELECT role_access_update()", // SQL
				new Object[] {} // SQL params
		);

		// Schedule cache reset
		resetCacheAfterTrxCommit();
	}

	@Override
	public String updateAccessRecords(final Role role)
	{
		if (role.isManualMaintainance())
		{
			return "-";
		}

		updateAccessRecords(role.getId(), role.getUpdatedBy());

		// TODO: improve the returned message
		return "OK";
	}

	@Override
	public void updateAccessRecords(@NonNull final RoleId roleId, @NonNull final UserId createdByUserId)
	{
		//
		// Delete/Create role access records
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, //
				"SELECT role_access_update(p_AD_Role_ID => " + roleId.getRepoId() + ", p_CreatedBy => " + createdByUserId.getRepoId() + ")", // SQL
				new Object[] {} // SQL params
		);

		// Schedule cache reset
		resetCacheAfterTrxCommit();
	}

	@Override
	public void deleteAccessRecords(final RoleId roleId)
	{
		// Delete role dependent records
		for (final String accessTableName : ROLE_DEPENDENT_TABLENAMES)
		{
			// Skip AD_Role dependent tables which does not have an AD_Role_ID column
			final boolean hasRoleColumn = !I_AD_User_OrgAccess.Table_Name.equals(accessTableName);
			if (!hasRoleColumn)
			{
				continue;
			}

			final int deleteCount = DB.executeUpdateAndThrowExceptionOnFail("DELETE FROM " + accessTableName + " WHERE AD_Role_ID=" + roleId.getRepoId(), ITrx.TRXNAME_ThreadInherited);
			logger.info("deleteAccessRecords({}): deleted {} rows from {}", roleId, deleteCount, accessTableName);
		}

		// Schedule cache reset
		resetCacheAfterTrxCommit();
	}

	@Override
	public void setAccountingModuleActive()
	{
		final boolean accountingModuleActiveOld = accountingModuleActive.getAndSet(true);

		// If flag changed, reset the cache
		if (!accountingModuleActiveOld)
		{
			// NOTE: don't broadcast because this is just a local change
			resetLocalCache();
		}
	}

	@Override
	public boolean isAccountingModuleActive()
	{
		return accountingModuleActive.get();
	}

	@Override
	public List<I_AD_Role_OrgAccess> retrieveRoleOrgAccessRecordsForOrg(@NonNull final OrgId adOrgId)
	{
		return queryBL
				.createQueryBuilder(I_AD_Role_OrgAccess.class)
				.addEqualsFilter(I_AD_Role_OrgAccess.COLUMNNAME_AD_Org_ID, adOrgId)
				// .addOnlyActiveRecordsFilter() // NOTE: retrieve all
				.create()
				.list(I_AD_Role_OrgAccess.class);
	}

	@Override
	public void createOrgAccess(@NonNull final RoleId adRoleId, @NonNull final OrgId adOrgId)
	{
		final I_AD_Role_OrgAccess roleOrgAccess = InterfaceWrapperHelper.newInstance(I_AD_Role_OrgAccess.class);
		roleOrgAccess.setAD_Org_ID(adOrgId.getRepoId());
		roleOrgAccess.setAD_Role_ID(adRoleId.getRepoId());
		roleOrgAccess.setIsReadOnly(false);
		InterfaceWrapperHelper.save(roleOrgAccess);
	}

	@Override
	public void createWindowAccess(final CreateWindowAccessRequest request)
	{
		changeWindowAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdWindowId(),
				windowAccess -> {
					windowAccess.setIsActive(true);
					windowAccess.setIsReadWrite(request.isReadWrite());
				});
	}

	@Override
	public void deleteWindowAccess(final RemoveWindowAccessRequest request)
	{
		changeWindowAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdWindowId(),
				windowAccess -> {
					windowAccess.setIsActive(false); // request to be deleted
				});
	}

	private void changeWindowAccess(
			@NonNull final RoleId roleId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AdWindowId adWindowId,
			@NonNull final Consumer<I_AD_Window_Access> updater)
	{
		I_AD_Window_Access windowAccess = queryBL
				.createQueryBuilder(I_AD_Window_Access.class)
				.addEqualsFilter(I_AD_Window_Access.COLUMNNAME_AD_Role_ID, roleId)
				.addEqualsFilter(I_AD_Window_Access.COLUMNNAME_AD_Window_ID, adWindowId)
				.create()
				.firstOnly(I_AD_Window_Access.class);

		if (windowAccess == null)
		{
			windowAccess = InterfaceWrapperHelper.newInstance(I_AD_Window_Access.class);
			InterfaceWrapperHelper.setValue(windowAccess, I_AD_Window_Access.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
			windowAccess.setAD_Org_ID(orgId.getRepoId());
			windowAccess.setAD_Role_ID(roleId.getRepoId());
			windowAccess.setAD_Window_ID(adWindowId.getRepoId());
		}

		updater.accept(windowAccess);

		if (windowAccess.isActive())
		{
			InterfaceWrapperHelper.save(windowAccess);
		}
		else
		{
			if (!InterfaceWrapperHelper.isNew(windowAccess))
			{
				InterfaceWrapperHelper.delete(windowAccess);
			}
		}

		//
		resetCacheAfterTrxCommit();
	}

	@Override
	public void createProcessAccess(final CreateProcessAccessRequest request)
	{
		changeProcessAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdProcessId(),
				access -> {
					access.setIsActive(true);
					access.setIsReadWrite(request.isReadWrite());
				});
	}

	@Override
	public void deleteProcessAccess(final RemoveProcessAccessRequest request)
	{
		changeProcessAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdProcessId(),
				access -> {
					access.setIsActive(false); // request to be deleted
				});
	}

	private void changeProcessAccess(
			@NonNull final RoleId roleId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final AdProcessId adProcessId,
			@NonNull final Consumer<I_AD_Process_Access> updater)
	{
		I_AD_Process_Access processAccess = queryBL
				.createQueryBuilder(I_AD_Process_Access.class)
				.addEqualsFilter(I_AD_Process_Access.COLUMN_AD_Role_ID, roleId)
				.addEqualsFilter(I_AD_Process_Access.COLUMN_AD_Process_ID, adProcessId)
				.create()
				.firstOnly(I_AD_Process_Access.class);

		if (processAccess == null)
		{
			processAccess = InterfaceWrapperHelper.newInstance(I_AD_Process_Access.class);
			InterfaceWrapperHelper.setValue(processAccess, I_AD_Process_Access.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
			processAccess.setAD_Org_ID(orgId.getRepoId());
			processAccess.setAD_Role_ID(roleId.getRepoId());
			processAccess.setAD_Process_ID(adProcessId.getRepoId());
		}

		updater.accept(processAccess);

		if (processAccess.isActive())
		{
			InterfaceWrapperHelper.save(processAccess);
		}
		else
		{
			if (!InterfaceWrapperHelper.isNew(processAccess))
			{
				InterfaceWrapperHelper.delete(processAccess);
			}
		}

		//
		resetCacheAfterTrxCommit();
	}

	@Override
	public void createFormAccess(final CreateFormAccessRequest request)
	{
		changeFormAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdFormId(),
				access -> {
					access.setIsActive(true);
					access.setIsReadWrite(request.isReadWrite());
				});
	}

	@Override
	public void deleteFormAccess(final RemoveFormAccessRequest request)
	{
		changeFormAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdFormId(),
				access -> {
					access.setIsActive(false); // request to be deleted
				});
	}

	private void changeFormAccess(
			@NonNull final RoleId roleId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			final int adFormId,
			@NonNull final Consumer<I_AD_Form_Access> updater)
	{
		Preconditions.checkArgument(adFormId > 0, "adFormId > 0");

		I_AD_Form_Access formAccess = queryBL
				.createQueryBuilder(I_AD_Form_Access.class)
				.addEqualsFilter(I_AD_Form_Access.COLUMNNAME_AD_Role_ID, roleId)
				.addEqualsFilter(I_AD_Form_Access.COLUMNNAME_AD_Form_ID, adFormId)
				.create()
				.firstOnly(I_AD_Form_Access.class);

		if (formAccess == null)
		{
			formAccess = InterfaceWrapperHelper.newInstance(I_AD_Form_Access.class);
			InterfaceWrapperHelper.setValue(formAccess, I_AD_Form_Access.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
			formAccess.setAD_Org_ID(orgId.getRepoId());
			formAccess.setAD_Role_ID(roleId.getRepoId());
			formAccess.setAD_Form_ID(adFormId);
		}

		updater.accept(formAccess);

		if (formAccess.isActive())
		{
			InterfaceWrapperHelper.save(formAccess);
		}
		else
		{
			if (!InterfaceWrapperHelper.isNew(formAccess))
			{
				InterfaceWrapperHelper.delete(formAccess);
			}
		}

		//
		resetCacheAfterTrxCommit();
	}

	@Override
	public void createTaskAccess(final CreateTaskAccessRequest request)
	{
		changeTaskAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdTaskId(),
				access -> {
					access.setIsActive(true);
					access.setIsReadWrite(request.isReadWrite());
				});
	}

	@Override
	public void deleteTaskAccess(final RemoveTaskAccessRequest request)
	{
		changeTaskAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdTaskId(),
				access -> {
					access.setIsActive(false); // request to be deleted
				});
	}

	private void changeTaskAccess(
			@NonNull final RoleId roleId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			final int adTaskId,
			@NonNull final Consumer<I_AD_Task_Access> updater)
	{
		Preconditions.checkArgument(adTaskId > 0, "adTaskId > 0");

		I_AD_Task_Access taskAccess = queryBL
				.createQueryBuilder(I_AD_Task_Access.class)
				.addEqualsFilter(I_AD_Task_Access.COLUMNNAME_AD_Role_ID, roleId)
				.addEqualsFilter(I_AD_Task_Access.COLUMNNAME_AD_Task_ID, adTaskId)
				.create()
				.firstOnly(I_AD_Task_Access.class);

		if (taskAccess == null)
		{
			taskAccess = InterfaceWrapperHelper.newInstance(I_AD_Task_Access.class);
			InterfaceWrapperHelper.setValue(taskAccess, I_AD_Task_Access.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
			taskAccess.setAD_Org_ID(orgId.getRepoId());
			taskAccess.setAD_Role_ID(roleId.getRepoId());
			taskAccess.setAD_Task_ID(adTaskId);
		}

		updater.accept(taskAccess);

		if (taskAccess.isActive())
		{
			InterfaceWrapperHelper.save(taskAccess);
		}
		else
		{
			if (!InterfaceWrapperHelper.isNew(taskAccess))
			{
				InterfaceWrapperHelper.delete(taskAccess);
			}
		}

		//
		resetCacheAfterTrxCommit();
	}

	@Override
	public void createWorkflowAccess(final CreateWorkflowAccessRequest request)
	{
		changeWorkflowAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdWorkflowId(),
				access -> {
					access.setIsActive(true);
					access.setIsReadWrite(request.isReadWrite());
				});
	}

	@Override
	public void deleteWorkflowAccess(final RemoveWorkflowAccessRequest request)
	{
		changeWorkflowAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getAdWorkflowId(),
				access -> {
					access.setIsActive(false); // request to be deleted
				});
	}

	private void changeWorkflowAccess(
			@NonNull final RoleId roleId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			final int adWorkflowId,
			@NonNull final Consumer<I_AD_Workflow_Access> updater)
	{
		Preconditions.checkArgument(adWorkflowId > 0, "adWorkflowId > 0");

		I_AD_Workflow_Access workflowAccess = queryBL
				.createQueryBuilder(I_AD_Workflow_Access.class)
				.addEqualsFilter(I_AD_Workflow_Access.COLUMNNAME_AD_Role_ID, roleId)
				.addEqualsFilter(I_AD_Workflow_Access.COLUMNNAME_AD_Workflow_ID, adWorkflowId)
				.create()
				.firstOnly(I_AD_Workflow_Access.class);

		if (workflowAccess == null)
		{
			workflowAccess = InterfaceWrapperHelper.newInstance(I_AD_Workflow_Access.class);
			InterfaceWrapperHelper.setValue(workflowAccess, I_AD_Workflow_Access.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
			workflowAccess.setAD_Org_ID(orgId.getRepoId());
			workflowAccess.setAD_Role_ID(roleId.getRepoId());
			workflowAccess.setAD_Workflow_ID(adWorkflowId);
		}

		updater.accept(workflowAccess);

		if (workflowAccess.isActive())
		{
			InterfaceWrapperHelper.save(workflowAccess);
		}
		else
		{
			if (!InterfaceWrapperHelper.isNew(workflowAccess))
			{
				InterfaceWrapperHelper.delete(workflowAccess);
			}
		}

		//
		resetCacheAfterTrxCommit();
	}

	@Override
	public void createDocumentActionAccess(final CreateDocActionAccessRequest request)
	{
		changeDocumentActionAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getDocTypeId(),
				request.getDocActionRefListId(),
				access -> access.setIsActive(true));
	}

	@Override
	public void deleteDocumentActionAccess(final RemoveDocActionAccessRequest request)
	{
		changeDocumentActionAccess(
				request.getRoleId(),
				request.getClientId(),
				request.getOrgId(),
				request.getDocTypeId(),
				request.getDocActionRefListId(),
				access -> {
					access.setIsActive(false); // request to be deleted
				});
	}

	private void changeDocumentActionAccess(
			@NonNull final RoleId roleId,
			@NonNull final ClientId clientId,
			@NonNull final OrgId orgId,
			@NonNull final DocTypeId docTypeId,
			final int docActionRefListId,
			@NonNull final Consumer<I_AD_Document_Action_Access> updater)
	{
		Preconditions.checkArgument(docActionRefListId > 0, "docActionRefListId > 0");

		I_AD_Document_Action_Access docActionAccess = queryBL
				.createQueryBuilder(I_AD_Document_Action_Access.class)
				.addEqualsFilter(I_AD_Document_Action_Access.COLUMNNAME_AD_Role_ID, roleId)
				.addEqualsFilter(I_AD_Document_Action_Access.COLUMNNAME_C_DocType_ID, docTypeId)
				.addEqualsFilter(I_AD_Document_Action_Access.COLUMNNAME_AD_Ref_List_ID, docActionRefListId)
				.create()
				.firstOnly(I_AD_Document_Action_Access.class);

		if (docActionAccess == null)
		{
			docActionAccess = InterfaceWrapperHelper.newInstance(I_AD_Document_Action_Access.class);
			InterfaceWrapperHelper.setValue(docActionAccess, I_AD_Document_Action_Access.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
			docActionAccess.setAD_Org_ID(orgId.getRepoId());
			docActionAccess.setAD_Role_ID(roleId.getRepoId());
			docActionAccess.setC_DocType_ID(docTypeId.getRepoId());
			docActionAccess.setAD_Ref_List_ID(docActionRefListId);
		}

		updater.accept(docActionAccess);

		if (docActionAccess.isActive())
		{
			InterfaceWrapperHelper.save(docActionAccess);
		}
		else
		{
			if (!InterfaceWrapperHelper.isNew(docActionAccess))
			{
				InterfaceWrapperHelper.delete(docActionAccess);
			}
		}

		//
		resetCacheAfterTrxCommit();
	}

	@Override
	public void createPrivateAccess(final CreateRecordPrivateAccessRequest request)
	{
		I_AD_Private_Access accessRecord = queryPrivateAccess(request.getPrincipal(), request.getRecordRef())
				.create()
				.firstOnly(I_AD_Private_Access.class);
		if (accessRecord == null)
		{
			accessRecord = InterfaceWrapperHelper.newInstance(I_AD_Private_Access.class);
			accessRecord.setAD_User_ID(UserId.toRepoId(request.getPrincipal().getUserId()));
			accessRecord.setAD_UserGroup_ID(UserGroupId.toRepoId(request.getPrincipal().getUserGroupId()));
			accessRecord.setAD_Table_ID(request.getRecordRef().getAD_Table_ID());
			accessRecord.setRecord_ID(request.getRecordRef().getRecord_ID());
		}

		accessRecord.setAD_Org_ID(OrgId.ANY.getRepoId());
		accessRecord.setIsActive(true);

		saveRecord(accessRecord);
	}

	@Override
	public void deletePrivateAccess(final RemoveRecordPrivateAccessRequest request)
	{
		queryPrivateAccess(request.getPrincipal(), request.getRecordRef())
				.create()
				.delete();
	}

	private IQueryBuilder<I_AD_Private_Access> queryPrivateAccess(
			@NonNull final Principal principal,
			@NonNull final TableRecordReference recordRef)
	{
		return queryBL
				.createQueryBuilder(I_AD_Private_Access.class)
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_AD_User_ID, principal.getUserId())
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_AD_UserGroup_ID, principal.getUserGroupId())
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_Record_ID, recordRef.getRecord_ID());
	}


	@Override
	public void deleteUserOrgAccessByUserId(final UserId userId)
	{
		queryBL.createQueryBuilder(I_AD_User_OrgAccess.class)
				.addEqualsFilter(I_AD_User_OrgAccess.COLUMNNAME_AD_User_ID, userId)
				.create()
				.delete();
	}

	@Override
	public void deleteUserOrgAssignmentByUserId(final UserId userId)
	{
		queryBL.createQueryBuilder(I_C_OrgAssignment.class)
				.addEqualsFilter(I_C_OrgAssignment.COLUMNNAME_AD_User_ID, userId)
				.create()
				.delete();
	}

	@Value(staticConstructor = "of")
	private static class RoleOrgPermissionsCacheKey
	{
		RoleId roleId;
		AdTreeId adTreeOrgId;
	}

	@Value(staticConstructor = "of")
	private static class UserOrgPermissionsCacheKey
	{
		UserId userId;
		AdTreeId adTreeOrgId;
	}
}

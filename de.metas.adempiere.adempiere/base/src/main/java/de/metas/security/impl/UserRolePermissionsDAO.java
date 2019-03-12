package de.metas.security.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.tree.AdTreeId;
import org.adempiere.service.ClientId;
import org.adempiere.service.IOrgDAO;
import org.adempiere.service.OrgId;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Column_Access;
import org.compiere.model.I_AD_Document_Action_Access;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Form_Access;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_AD_Private_Access;
import org.compiere.model.I_AD_Process;
import org.compiere.model.I_AD_Process_Access;
import org.compiere.model.I_AD_Record_Access;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_Included;
import org.compiere.model.I_AD_Role_OrgAccess;
import org.compiere.model.I_AD_Table_Access;
import org.compiere.model.I_AD_Task;
import org.compiere.model.I_AD_Task_Access;
import org.compiere.model.I_AD_User_OrgAccess;
import org.compiere.model.I_AD_User_Roles;
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_AD_Window_Access;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_AD_Workflow_Access;
import org.compiere.model.X_AD_Table_Access;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import de.metas.cache.CacheMgt;
import de.metas.document.DocTypeId;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.security.IRoleDAO;
import de.metas.security.IRolesTreeNode;
import de.metas.security.IUserRolePermissions;
import de.metas.security.IUserRolePermissionsBuilder;
import de.metas.security.IUserRolePermissionsDAO;
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
import de.metas.security.permissions.TablePermission;
import de.metas.security.permissions.TablePermission.Builder;
import de.metas.security.permissions.TablePermissions;
import de.metas.security.permissions.TableRecordPermission;
import de.metas.security.permissions.TableRecordPermissions;
import de.metas.security.permissions.TableResource;
import de.metas.security.requests.CreateDocActionAccessRequest;
import de.metas.security.requests.CreateFormAccessRequest;
import de.metas.security.requests.CreateProcessAccessRequest;
import de.metas.security.requests.CreateTaskAccessRequest;
import de.metas.security.requests.CreateWindowAccessRequest;
import de.metas.security.requests.CreateWorkflowAccessRequest;
import de.metas.security.requests.RemoveDocActionAccessRequest;
import de.metas.security.requests.RemoveFormAccessRequest;
import de.metas.security.requests.RemoveProcessAccessRequest;
import de.metas.security.requests.RemoveTaskAccessRequest;
import de.metas.security.requests.RemoveWindowAccessRequest;
import de.metas.security.requests.RemoveWorkflowAccessRequest;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

public class UserRolePermissionsDAO implements IUserRolePermissionsDAO
{
	private static final transient Logger logger = LogManager.getLogger(UserRolePermissionsDAO.class);

	private static final Set<String> ROLE_DEPENDENT_TABLENAMES = ImmutableSet.of(
			// I_AD_Role.Table_Name // NEVER include the AD_Role
			I_AD_User_Roles.Table_Name, // User to Role assignment (see https://github.com/metasfresh/metasfresh-webui-api/issues/482)
			// Included role
			I_AD_Role_Included.Table_Name,
			// Org Access
			I_AD_User_OrgAccess.Table_Name,
			I_AD_Role_OrgAccess.Table_Name,
			// Access records
			I_AD_Window_Access.Table_Name,
			I_AD_Process_Access.Table_Name,
			I_AD_Form_Access.Table_Name,
			I_AD_Workflow_Access.Table_Name,
			I_AD_Task_Access.Table_Name,
			I_AD_Document_Action_Access.Table_Name,
			// Table/Record access
			I_AD_Table_Access.Table_Name,
			I_AD_Record_Access.Table_Name);

	private final AtomicBoolean accountingModuleActive = new AtomicBoolean(false);

	private final AtomicLong version = new AtomicLong(1);

	/** Aggregated permissions per key */
	private CCache<UserRolePermissionsKey, IUserRolePermissions> //
	permissionsByKey = CCache.<UserRolePermissionsKey, IUserRolePermissions> builder()
			.tableName(I_AD_Role.Table_Name)
			.build();

	/** Individual (not-aggregated) permissions per key */
	private CCache<UserRolePermissionsKey, IUserRolePermissions> individialPermissionsByKey = CCache.<UserRolePermissionsKey, IUserRolePermissions> builder()
			.tableName(I_AD_Role.Table_Name)
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
		final ITrx trx = Services.get(ITrxManager.class).getTrxOrNull(ITrx.TRXNAME_ThreadInherited);

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
		final boolean rw = false; // readonly access is fine for us

		final ImmutableList.Builder<IUserRolePermissions> permissionsWithOrgAccess = ImmutableList.builder();
		for (final RoleId roleId : Services.get(IRoleDAO.class).getUserRoleIds(adUserId))
		{
			final IUserRolePermissions permissions = getUserRolePermissions(roleId, adUserId, clientId, date);
			if (permissions.isOrgAccess(adOrgId, rw))
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
		final boolean rw = false; // readonly access is fine for us

		for (final RoleId roleId : Services.get(IRoleDAO.class).getUserRoleIds(adUserId))
		{
			final IUserRolePermissions permissions = getUserRolePermissions(roleId, adUserId, clientId, date);
			if (permissions.isOrgAccess(adOrgId, rw))
			{
				return Optional.of(permissions);
			}
		}

		return Optional.absent();
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
		for (final RoleId roleId : Services.get(IRoleDAO.class).getUserRoleIds(adUserId))
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
			final IRolesTreeNode rootRole = Services.get(IRoleDAO.class).retrieveRolesTree(adRoleId, adUserId, date);
			return rootRole.aggregateBottomUp(new IRolesTreeNode.BottomUpAggregator<IUserRolePermissions, IUserRolePermissionsBuilder>()
			{
				@Override
				public IUserRolePermissionsBuilder initialValue(final IRolesTreeNode node)
				{
					return getIndividialUserRolePermissions(node.getRoleId(), adUserId, adClientId)
							.asNewBuilder();
				}

				@Override
				public void aggregateValue(final IUserRolePermissionsBuilder aggregatedValue, final IRolesTreeNode childNode, final IUserRolePermissions value)
				{
					aggregatedValue.includeUserRolePermissions(value, childNode.getSeqNo());
				}

				@Override
				public IUserRolePermissions finalValue(final IUserRolePermissionsBuilder aggregatedValue)
				{
					return aggregatedValue.build();
				}

				@Override
				public IUserRolePermissions leafValue(final IRolesTreeNode node)
				{
					return getIndividialUserRolePermissions(node.getRoleId(), adUserId, adClientId);
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

	final IUserRolePermissions getIndividialUserRolePermissions(final RoleId adRoleId, final UserId adUserId, final ClientId adClientId)
	{
		final UserRolePermissionsKey key = UserRolePermissionsKey.of(adRoleId, adUserId, adClientId, LocalDate.MIN);
		return individialPermissionsByKey.getOrLoad(key, () -> new UserRolePermissionsBuilder(isAccountingModuleActive())
				.setRoleId(adRoleId)
				.setUserId(adUserId)
				.setClientId(adClientId)
				.build());
	}

	@Override
	public OrgPermissions retrieveOrgPermissions(final Role role, final UserId adUserId)
	{
		final AdTreeId adTreeOrgId = role.getOrgTreeId();
		if (role.isUseUserOrgAccess())
		{
			return retrieveUserOrgPermissions(adUserId, adTreeOrgId);
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
				final List<I_AD_Org> clientOrgs = Services.get(IOrgDAO.class).retrieveClientOrgs(clientId.getRepoId());
				for (final I_AD_Org org : clientOrgs)
				{
					// skip inative orgs
					if (!org.isActive())
					{
						continue;
					}

					final OrgId orgId = OrgId.ofRepoId(org.getAD_Org_ID());
					final OrgResource orgResource = OrgResource.of(clientId, orgId);
					final OrgPermission orgPermission = OrgPermission.ofResourceAndReadOnly(orgResource, false);
					builder.addPermission(orgPermission);
				}
				return builder.build();
			}
			else
			{
				return retrieveRoleOrgPermissions(role.getId(), adTreeOrgId);
			}
		}

	}

	@Override
	@Cached(cacheName = I_AD_User_OrgAccess.Table_Name + "#by#AD_User_ID")
	public OrgPermissions retrieveUserOrgPermissions(final UserId adUserId, final AdTreeId adTreeOrgId)
	{
		final IQuery<I_AD_Org> activeOrgsQuery = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Org.class)
				.addOnlyActiveRecordsFilter()
				.create();

		final List<I_AD_User_OrgAccess> orgAccessesList = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_User_OrgAccess.class)
				.addEqualsFilter(I_AD_User_OrgAccess.COLUMNNAME_AD_User_ID, adUserId)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_AD_User_OrgAccess.COLUMN_AD_Org_ID, I_AD_Org.COLUMN_AD_Org_ID, activeOrgsQuery)
				.create()
				.list();

		final OrgPermissions.Builder builder = OrgPermissions.builder(adTreeOrgId);
		for (final I_AD_User_OrgAccess oa : orgAccessesList)
		{
			// NOTE: we are fetching the AD_Client_ID from OrgAccess and not from AD_Org (very important for Org=0 like) !
			final ClientId clientId = ClientId.ofRepoId(oa.getAD_Client_ID());
			final OrgId orgId = OrgId.ofRepoId(oa.getAD_Org_ID());
			final OrgResource resource = OrgResource.of(clientId, orgId);
			final OrgPermission permission = OrgPermission.ofResourceAndReadOnly(resource, oa.isReadOnly());
			builder.addPermissionRecursivelly(permission);
		}

		return builder.build();
	}

	@Override
	@Cached(cacheName = I_AD_Role_OrgAccess.Table_Name + "#by#AD_User_ID")
	public OrgPermissions retrieveRoleOrgPermissions(final RoleId adRoleId, final AdTreeId adTreeOrgId)
	{
		final IQuery<I_AD_Org> activeOrgsQuery = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Org.class)
				.addOnlyActiveRecordsFilter()
				.create();

		final List<I_AD_Role_OrgAccess> orgAccessesList = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Role_OrgAccess.class)
				.addEqualsFilter(I_AD_Role_OrgAccess.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_AD_Role_OrgAccess.COLUMN_AD_Org_ID, I_AD_Org.COLUMN_AD_Org_ID, activeOrgsQuery)
				.create()
				.list();

		final OrgPermissions.Builder builder = OrgPermissions.builder(adTreeOrgId);
		for (final I_AD_Role_OrgAccess oa : orgAccessesList)
		{
			// NOTE: we are fetching the AD_Client_ID from OrgAccess and not from AD_Org (very important for Org=0 like) !
			final ClientId clientId = ClientId.ofRepoId(oa.getAD_Client_ID());
			final OrgId orgId = OrgId.ofRepoId(oa.getAD_Org_ID());
			final OrgResource resource = OrgResource.of(clientId, orgId);
			final OrgPermission permission = OrgPermission.ofResourceAndReadOnly(resource, oa.isReadOnly());
			builder.addPermissionRecursivelly(permission);
		}

		return builder.build();
	}

	@Override
	@Cached(cacheName = I_AD_Window_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveWindowPermissions(final RoleId adRoleId, final ClientId adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Window_Access.class,
				I_AD_Window.Table_Name,
				I_AD_Window_Access.COLUMNNAME_AD_Window_ID);
	}

	@Override
	@Cached(cacheName = I_AD_Process_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveProcessPermissions(final RoleId adRoleId, final ClientId adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Process_Access.class,
				I_AD_Process.Table_Name,
				I_AD_Process_Access.COLUMNNAME_AD_Process_ID);
	}

	@Override
	@Cached(cacheName = I_AD_Task_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveTaskPermissions(final RoleId adRoleId, final ClientId adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Task_Access.class,
				I_AD_Task.Table_Name,
				I_AD_Task_Access.COLUMNNAME_AD_Task_ID);
	}

	@Override
	@Cached(cacheName = I_AD_Form_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveFormPermissions(final RoleId adRoleId, final ClientId adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Form_Access.class,
				I_AD_Form.Table_Name,
				I_AD_Form_Access.COLUMNNAME_AD_Form_ID);
	}

	@Override
	@Cached(cacheName = I_AD_Workflow_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveWorkflowPermissions(final RoleId adRoleId, final ClientId adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Workflow_Access.class,
				I_AD_Workflow.Table_Name,
				I_AD_Workflow_Access.COLUMNNAME_AD_Workflow_ID);
	}

	final <AccessTableType> ElementPermissions retrieveElementPermissions(final RoleId adRoleId, final ClientId adClientId, final Class<AccessTableType> accessTableClass, final String elementTableName, final String elementColumnName)
	{
		final Properties ctx = Env.getCtx();

		//
		// EntityType filter: filter out those elements where EntityType is not displayed
		final String accessTableName = InterfaceWrapperHelper.getTableName(accessTableClass);
		final IQueryFilter<AccessTableType> entityTypeFilter = TypedSqlQueryFilter.of("exists (select 1 from " + elementTableName + " t"
				+ " where t." + elementColumnName + "=" + accessTableName + "." + elementColumnName
				+ " and (" + EntityTypesCache.instance.getDisplayedInUIEntityTypeSQLWhereClause("t.EntityType") + ")"
				+ ")");

		final String COLUMNNAME_AD_Role_ID = "AD_Role_ID";
		final String COLUMNNAME_IsReadWrite = "IsReadWrite";

		final List<Map<String, Object>> accessesList = Services.get(IQueryBL.class)
				.createQueryBuilder(accessTableClass, ctx, ITrx.TRXNAME_None)
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
			final boolean readWrite = DisplayType.toBoolean(accessItem.get(COLUMNNAME_IsReadWrite), false);
			final ElementPermission access = ElementPermission.ofReadWriteFlag(resource, readWrite);
			elementAccessesBuilder.addPermission(access);
		}

		return elementAccessesBuilder.build();
	}

	@Override
	@Cached(cacheName = I_AD_Table_Access.Table_Name + "#Accesses")
	public TablePermissions retrieveTablePermissions(final RoleId adRoleId)
	{
		final Properties ctx = Env.getCtx();
		final List<I_AD_Table_Access> tableAccessRecords = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Table_Access.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Table_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();

		final TablePermissions.Builder builder = TablePermissions.builder();

		// Default permission: allow all because actually this is an "exclude" list (if no include options were found).
		final Builder defaultPermission = TablePermission.ALL.asNewBuilder();

		for (final I_AD_Table_Access tableAccessRecord : tableAccessRecords)
		{
			final TableResource resource = TableResource.ofAD_Table_ID(tableAccessRecord.getAD_Table_ID());
			final TablePermission.Builder permission = TablePermission.builder()
					.setResource(resource)
					.removeAllAccesses();

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
						permission.addAccess(Access.READ);
						// permission.removeAccess(Access.WRITE); // not needed
					}
					else
					{
						// nothing granted
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
			}
			else if (X_AD_Table_Access.ACCESSTYPERULE_Reporting.equals(type))
			{
				if (tableAccessRecord.isCanReport())
				{
					permission.addAccess(Access.REPORT);
				}

				// A include access implies that the default access is not granted
				if (!exclude)
				{
					defaultPermission.removeAccess(Access.REPORT);
				}
			}
			else if (X_AD_Table_Access.ACCESSTYPERULE_Exporting.equals(type))
			{
				if (tableAccessRecord.isCanExport())
				{
					permission.addAccess(Access.EXPORT);
				}

				// A include access implies that the default access is not granted
				if (!exclude)
				{
					defaultPermission.removeAccess(Access.EXPORT);
				}
			}
			else
			{
				throw new IllegalStateException("Unknown AccessRuleType: " + type);
			}

			builder.addPermission(permission.build(), CollisionPolicy.Override);
		}

		// Add default permission
		builder.addPermission(defaultPermission.build(), CollisionPolicy.Override);

		return builder.build();
	}	// loadTableAccess

	@Override
	@Cached(cacheName = I_AD_Column_Access.Table_Name + "#Accesses")
	public TableColumnPermissions retrieveTableColumnPermissions(final RoleId adRoleId)
	{
		final Properties ctx = Env.getCtx();
		final List<I_AD_Column_Access> columnAccessList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Column_Access.class, ctx, ITrx.TRXNAME_None)
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
				else
				{
					// nothing granted
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
	}	// loadColumnAccess

	@Override
	@Cached(cacheName = I_AD_Record_Access.Table_Name + "#Accesses")
	public TableRecordPermissions retrieveRecordPermissions(final RoleId adRoleId)
	{
		final Properties ctx = Env.getCtx();
		final List<I_AD_Record_Access> accessesList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Record_Access.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.orderBy()
				.addColumn(I_AD_Record_Access.COLUMNNAME_AD_Table_ID)
				.endOrderBy()
				.create()
				.list();

		final TableRecordPermissions.Builder recordAccessesBuilder = TableRecordPermissions.builder();

		for (final I_AD_Record_Access a : accessesList)
		{
			final TableRecordPermission access = TableRecordPermission.builder()
					.setFrom(a)
					.build();
			recordAccessesBuilder.addPermission(access);
		}

		return recordAccessesBuilder.build();
	}	// loadRecordAccess

	@Override
	public List<I_AD_Record_Access> retrieveRecordAccesses(final int adTableId, final int recordId, final ClientId adClientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Record_Access.class)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_Record_ID, recordId)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_AD_Client_ID, adClientId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_AD_Record_Access.class);
	}

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

			final int deleteCount = DB.executeUpdateEx("DELETE FROM " + accessTableName + " WHERE AD_Role_ID=" + roleId.getRepoId(), ITrx.TRXNAME_ThreadInherited);
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
		return Services.get(IQueryBL.class)
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
		I_AD_Window_Access windowAccess = Services.get(IQueryBL.class)
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
		I_AD_Process_Access processAccess = Services.get(IQueryBL.class)
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

		I_AD_Form_Access formAccess = Services.get(IQueryBL.class)
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

		I_AD_Task_Access taskAccess = Services.get(IQueryBL.class)
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

		I_AD_Workflow_Access workflowAccess = Services.get(IQueryBL.class)
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
				access -> {
					access.setIsActive(true);
				});
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

		I_AD_Document_Action_Access docActionAccess = Services.get(IQueryBL.class)
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
	public I_AD_Record_Access changeRecordAccess(
			@NonNull final RoleId roleId,
			final int adTableId,
			final int recordId,
			@NonNull final Consumer<I_AD_Record_Access> updater)
	{
		Preconditions.checkArgument(adTableId > 0, "adTableId > 0");
		Preconditions.checkArgument(recordId >= 0, "recordId >= 0");

		I_AD_Record_Access recordAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Record_Access.class)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_AD_Role_ID, roleId)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnly(I_AD_Record_Access.class);

		if (recordAccess == null)
		{
			final Role role = Services.get(IRoleDAO.class).getById(roleId);
			recordAccess = InterfaceWrapperHelper.newInstance(I_AD_Record_Access.class);
			InterfaceWrapperHelper.setValue(recordAccess, I_AD_Record_Access.COLUMNNAME_AD_Client_ID, role.getClientId().getRepoId());
			recordAccess.setAD_Org_ID(role.getOrgId().getRepoId());
			recordAccess.setAD_Role_ID(roleId.getRepoId());
			recordAccess.setAD_Table_ID(adTableId);
			recordAccess.setRecord_ID(recordId);

			recordAccess.setIsExclude(true);
			recordAccess.setIsReadOnly(false);
			recordAccess.setIsDependentEntities(false);
		}

		updater.accept(recordAccess);

		if (recordAccess.isActive())
		{
			InterfaceWrapperHelper.save(recordAccess);
		}
		else
		{
			if (!InterfaceWrapperHelper.isNew(recordAccess))
			{
				InterfaceWrapperHelper.delete(recordAccess);
			}
		}

		//
		resetCacheAfterTrxCommit();

		return recordAccess;
	}

	@Override
	public void createPrivateAccess(final UserId userId, final int adTableId, final int recordId)
	{
		changePrivateAccess(userId, adTableId, recordId, access -> {
			access.setIsActive(true);
		});
	}

	@Override
	public void deletePrivateAccess(final UserId userId, final int adTableId, final int recordId)
	{
		changePrivateAccess(userId, adTableId, recordId, access -> {
			access.setIsActive(false); // request to be deleted
		});
	}

	public void changePrivateAccess(
			@NonNull final UserId userId,
			final int adTableId,
			final int recordId,
			@NonNull final Consumer<I_AD_Private_Access> updater)
	{
		Preconditions.checkArgument(adTableId > 0, "adTableId > 0");
		Preconditions.checkArgument(recordId >= 0, "recordId >= 0");

		I_AD_Private_Access privateAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Private_Access.class)
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_AD_User_ID, userId)
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnly(I_AD_Private_Access.class);

		if (privateAccess == null)
		{
			privateAccess = InterfaceWrapperHelper.newInstance(I_AD_Private_Access.class);
			privateAccess.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_System);
			privateAccess.setAD_User_ID(userId.getRepoId());
			privateAccess.setAD_Table_ID(adTableId);
			privateAccess.setRecord_ID(recordId);
		}

		updater.accept(privateAccess);

		if (privateAccess.isActive())
		{
			InterfaceWrapperHelper.save(privateAccess);
		}
		else
		{
			if (!InterfaceWrapperHelper.isNew(privateAccess))
			{
				InterfaceWrapperHelper.delete(privateAccess);
			}
		}

		//
		resetCacheAfterTrxCommit();
	}

	@Override
	public Set<Integer> retrievePrivateAccessRecordIds(final UserId userId, final int adTableId)
	{
		final String sql = "SELECT Record_ID "
				+ " FROM " + I_AD_Private_Access.Table_Name
				+ " WHERE AD_User_ID=? AND AD_Table_ID=? AND IsActive=? "
				+ " ORDER BY Record_ID";
		final Object[] sqlParams = new Object[] { userId, adTableId, true };
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql, ITrx.TRXNAME_None);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final ImmutableSet.Builder<Integer> recordIds = ImmutableSet.builder();
			while (rs.next())
			{
				final int recordId = rs.getInt(1);
				recordIds.add(recordId);
			}

			return recordIds.build();
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
}

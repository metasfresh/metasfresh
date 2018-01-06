package org.adempiere.ad.security.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
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
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.security.IRolesTreeNode;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsBuilder;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.security.UserRolePermissionsEventBus;
import org.adempiere.ad.security.UserRolePermissionsKey;
import org.adempiere.ad.security.asp.IASPFiltersFactory;
import org.adempiere.ad.security.permissions.Access;
import org.adempiere.ad.security.permissions.ElementPermission;
import org.adempiere.ad.security.permissions.ElementPermissions;
import org.adempiere.ad.security.permissions.ElementResource;
import org.adempiere.ad.security.permissions.OrgPermission;
import org.adempiere.ad.security.permissions.OrgPermissions;
import org.adempiere.ad.security.permissions.OrgResource;
import org.adempiere.ad.security.permissions.PermissionsBuilder.CollisionPolicy;
import org.adempiere.ad.security.permissions.TableColumnPermission;
import org.adempiere.ad.security.permissions.TableColumnPermissions;
import org.adempiere.ad.security.permissions.TableColumnResource;
import org.adempiere.ad.security.permissions.TablePermission;
import org.adempiere.ad.security.permissions.TablePermission.Builder;
import org.adempiere.ad.security.permissions.TablePermissions;
import org.adempiere.ad.security.permissions.TableRecordPermission;
import org.adempiere.ad.security.permissions.TableRecordPermissions;
import org.adempiere.ad.security.permissions.TableResource;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.DBException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.IOrgDAO;
import org.adempiere.util.Services;
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
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
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
	public List<IUserRolePermissions> retrieveUserRolesPermissionsForUserWithOrgAccess(final Properties ctx, final int adUserId, final int adOrgId)
	{
		final boolean rw = false; // readonly access is fine for us
		final int adClientId = Env.getAD_Client_ID(ctx);
		final Date date = Env.getDate(ctx);

		final ImmutableList.Builder<IUserRolePermissions> permissionsWithOrgAccess = ImmutableList.builder();
		for (final I_AD_Role role : Services.get(IRoleDAO.class).retrieveRolesForUser(ctx, adUserId))
		{
			final int adRoleId = role.getAD_Role_ID();
			final IUserRolePermissions permissions = retrieveUserRolePermissions(adRoleId, adUserId, adClientId, date);
			if (permissions.isOrgAccess(adOrgId, rw))
			{
				permissionsWithOrgAccess.add(permissions);
			}
		}

		return permissionsWithOrgAccess.build();
	}

	@Override
	public Optional<IUserRolePermissions> retrieveFirstUserRolesPermissionsForUserWithOrgAccess(final Properties ctx, final int adUserId, final int adOrgId)
	{
		final boolean rw = false; // readonly access is fine for us
		final int adClientId = Env.getAD_Client_ID(ctx);
		final Date date = Env.getDate(ctx);

		for (final I_AD_Role role : Services.get(IRoleDAO.class).retrieveRolesForUser(ctx, adUserId))
		{
			final int adRoleId = role.getAD_Role_ID();
			final IUserRolePermissions permissions = retrieveUserRolePermissions(adRoleId, adUserId, adClientId, date);
			if (permissions.isOrgAccess(adOrgId, rw))
			{
				return Optional.of(permissions);
			}
		}

		return Optional.absent();
	}

	@Override
	public boolean isAdministrator(final Properties ctx, final int adUserId)
	{
		return matchUserRolesPermissionsForUser(ctx, adUserId, IUserRolePermissions::isSystemAdministrator);
	}

	@Override
	public boolean matchUserRolesPermissionsForUser(final Properties ctx, final int adUserId, final Predicate<IUserRolePermissions> matcher)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final Date date = Env.getDate(ctx);
		for (final I_AD_Role role : Services.get(IRoleDAO.class).retrieveRolesForUser(ctx, adUserId))
		{
			final int adRoleId = role.getAD_Role_ID();
			final IUserRolePermissions permissions = retrieveUserRolePermissions(adRoleId, adUserId, adClientId, date);

			if (matcher.test(permissions))
			{
				return true;
			}
		}

		return false;

	}

	@Override
	public IUserRolePermissions retrieveUserRolePermissions(final int adRoleId, final int adUserId, final int adClientId, final Date date)
	{
		final long dateMillis = UserRolePermissionsKey.normalizeDate(date);
		return retrieveUserRolePermissionsCached(adRoleId, adUserId, adClientId, dateMillis);
	}

	@Override
	public IUserRolePermissions retrieveUserRolePermissions(@NonNull final UserRolePermissionsKey key)
	{
		return retrieveUserRolePermissionsCached(key.getAD_Role_ID(), key.getAD_User_ID(), key.getAD_Client_ID(), key.getDateMillis());
	}

	@Cached(cacheName = I_AD_Role.Table_Name + "#AggregatedRolePermissions", expireMinutes = Cached.EXPIREMINUTES_Never)
	public IUserRolePermissions retrieveUserRolePermissionsCached(final int adRoleId, final int adUserId, final int adClientId, final long dateMillis)
	{
		final Date date = new Date(dateMillis);

		try
		{
			final IRolesTreeNode rootRole = Services.get(IRoleDAO.class).retrieveRolesTree(adRoleId, adUserId, date);
			return rootRole.aggregateBottomUp(new IRolesTreeNode.BottomUpAggregator<IUserRolePermissions, IUserRolePermissionsBuilder>()
			{
				@Override
				public IUserRolePermissionsBuilder initialValue(final IRolesTreeNode node)
				{
					return retrieveIndividialUserRolePermissions(node.getAD_Role_ID(), adUserId, adClientId)
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
					return retrieveIndividialUserRolePermissions(node.getAD_Role_ID(), adUserId, adClientId);
				}
			});
		}
		catch (Exception e)
		{
			throw new RolePermissionsNotFoundException("@AD_Role_ID@=" + adRoleId
					+ ", @AD_User_ID@=" + adUserId
					+ ", @AD_Client_ID@=" + adClientId
					+ ", @Date@=" + date, e);
		}
	}

	@Cached(cacheName = I_AD_Role.Table_Name + "#UserRolePermissions")
	IUserRolePermissions retrieveIndividialUserRolePermissions(final int adRoleId, final int adUserId, final int adClientId)
	{
		return new UserRolePermissionsBuilder(isAccountingModuleActive())
				.setAD_Role_ID(adRoleId)
				.setAD_User_ID(adUserId)
				.setAD_Client_ID(adClientId)
				.build();
	}

	@Override
	public OrgPermissions retrieveOrgPermissions(final I_AD_Role role, final int adUserId)
	{
		final int adTreeOrgId = role.getAD_Tree_Org_ID();
		if (role.isUseUserOrgAccess())
		{
			return retrieveUserOrgPermissions(adUserId, adTreeOrgId);
		}
		else
		{

			if (role.isAccessAllOrgs())
			{
				final int AD_Client_ID = role.getAD_Client_ID();
				final Properties ctx = InterfaceWrapperHelper.getCtx(role);

				//
				// if role has acces all org, then behave as would be * access
				final OrgPermissions.Builder builder = OrgPermissions.builder()
						.setOrg_Tree_ID(adTreeOrgId);

				// org *
				{
					final OrgResource resource = OrgResource.anyOrg(AD_Client_ID);
					final OrgPermission permission = OrgPermission.ofResourceAndReadOnly(resource, false);
					builder.addPermission(permission);
				}

				//
				// now add all orgs
				final List<I_AD_Org> clientOrgs = Services.get(IOrgDAO.class).retrieveClientOrgs(ctx, AD_Client_ID);
				for (final I_AD_Org org : clientOrgs)
				{
					// skip inative orgs
					if (!org.isActive())
					{
						continue;
					}

					final OrgResource orgResource = OrgResource.of(AD_Client_ID, org.getAD_Org_ID());
					final OrgPermission orgPermission = OrgPermission.ofResourceAndReadOnly(orgResource, false);
					builder.addPermission(orgPermission);
				}
				return builder.build();
			}
			else
			{
				return retrieveRoleOrgPermissions(role.getAD_Role_ID(), adTreeOrgId);
			}
		}

	}

	@Override
	@Cached(cacheName = I_AD_User_OrgAccess.Table_Name + "#by#AD_User_ID")
	public OrgPermissions retrieveUserOrgPermissions(final int adUserId, final int adTreeOrgId)
	{
		final Properties ctx = Env.getCtx();

		final IQuery<I_AD_Org> activeOrgsQuery = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.create();

		final List<I_AD_User_OrgAccess> orgAccessesList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_OrgAccess.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_User_OrgAccess.COLUMNNAME_AD_User_ID, adUserId)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_AD_User_OrgAccess.COLUMN_AD_Org_ID, I_AD_Org.COLUMN_AD_Org_ID, activeOrgsQuery)
				.create()
				.list();

		final OrgPermissions.Builder builder = OrgPermissions.builder()
				.setOrg_Tree_ID(adTreeOrgId);
		for (final I_AD_User_OrgAccess oa : orgAccessesList)
		{
			// NOTE: we are fetching the AD_Client_ID from OrgAccess and not from AD_Org (very important for Org=0 like) !
			final OrgResource resource = OrgResource.of(oa.getAD_Client_ID(), oa.getAD_Org_ID());
			final OrgPermission permission = OrgPermission.ofResourceAndReadOnly(resource, oa.isReadOnly());
			builder.addPermissionRecursivelly(permission);
		}

		return builder.build();
	}

	@Override
	@Cached(cacheName = I_AD_Role_OrgAccess.Table_Name + "#by#AD_User_ID")
	public OrgPermissions retrieveRoleOrgPermissions(final int adRoleId, final int adTreeOrgId)
	{
		final Properties ctx = Env.getCtx();

		final IQuery<I_AD_Org> activeOrgsQuery = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Org.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.create();

		final List<I_AD_Role_OrgAccess> orgAccessesList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Role_OrgAccess.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Role_OrgAccess.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.addInSubQueryFilter(I_AD_Role_OrgAccess.COLUMN_AD_Org_ID, I_AD_Org.COLUMN_AD_Org_ID, activeOrgsQuery)
				.create()
				.list();

		final OrgPermissions.Builder builder = OrgPermissions.builder()
				.setOrg_Tree_ID(adTreeOrgId);
		for (final I_AD_Role_OrgAccess oa : orgAccessesList)
		{
			// NOTE: we are fetching the AD_Client_ID from OrgAccess and not from AD_Org (very important for Org=0 like) !
			final OrgResource resource = OrgResource.of(oa.getAD_Client_ID(), oa.getAD_Org_ID());
			final OrgPermission permission = OrgPermission.ofResourceAndReadOnly(resource, oa.isReadOnly());
			builder.addPermissionRecursivelly(permission);
		}

		return builder.build();
	}

	@Override
	@Cached(cacheName = I_AD_Window_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveWindowPermissions(final int adRoleId, final int adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Window_Access.class,
				I_AD_Window.Table_Name,
				I_AD_Window_Access.COLUMNNAME_AD_Window_ID);
	}

	@Override
	@Cached(cacheName = I_AD_Process_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveProcessPermissions(final int adRoleId, final int adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Process_Access.class,
				I_AD_Process.Table_Name,
				I_AD_Process_Access.COLUMNNAME_AD_Process_ID);
	}

	@Override
	@Cached(cacheName = I_AD_Task_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveTaskPermissions(final int adRoleId, final int adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Task_Access.class,
				I_AD_Task.Table_Name,
				I_AD_Task_Access.COLUMNNAME_AD_Task_ID);
	}

	@Override
	@Cached(cacheName = I_AD_Form_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveFormPermissions(final int adRoleId, final int adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Form_Access.class,
				I_AD_Form.Table_Name,
				I_AD_Form_Access.COLUMNNAME_AD_Form_ID);
	}

	@Override
	@Cached(cacheName = I_AD_Workflow_Access.Table_Name + "#Accesses")
	public ElementPermissions retrieveWorkflowPermissions(final int adRoleId, final int adClientId)
	{
		return retrieveElementPermissions(adRoleId, adClientId,
				I_AD_Workflow_Access.class,
				I_AD_Workflow.Table_Name,
				I_AD_Workflow_Access.COLUMNNAME_AD_Workflow_ID);
	}

	final <AccessTableType> ElementPermissions retrieveElementPermissions(final int adRoleId, final int adClientId, final Class<AccessTableType> accessTableClass, final String elementTableName, final String elementColumnName)
	{
		final Properties ctx = Env.getCtx();
		final IQueryFilter<AccessTableType> aspFilter = Services.get(IASPFiltersFactory.class).getASPFiltersForClient(adClientId).getFilter(accessTableClass);

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
				.filter(aspFilter)
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
			final ElementPermission access = ElementPermission.of(resource, readWrite);
			elementAccessesBuilder.addPermission(access);
		}

		return elementAccessesBuilder.build();
	}

	@Override
	@Cached(cacheName = I_AD_Table_Access.Table_Name + "#Accesses")
	public TablePermissions retrieveTablePermissions(final int adRoleId)
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
	public TableColumnPermissions retrieveTableColumnPermissions(final int adRoleId)
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
			TableColumnPermission.Builder permission = TableColumnPermission.builder()
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
	public TableRecordPermissions retrieveRecordPermissions(final int adRoleId)
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
	public List<I_AD_Record_Access> retrieveRecordAccesses(final int adTableId, final int recordId, final int adClientId)
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
	public String updateAccessRecords(final I_AD_Role role)
	{
		if (role.isManual())
		{
			return "-";
		}

		//
		// Delete/Create role access records
		final int adRoleId = role.getAD_Role_ID();
		final int updatedBy = role.getUpdatedBy();
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, //
				"SELECT role_access_update(p_AD_Role_ID => " + adRoleId + ", p_CreatedBy => " + updatedBy + ")", // SQL
				new Object[] {} // SQL params
		);

		// Schedule cache reset
		resetCacheAfterTrxCommit();

		// TODO: improve the returned message
		return "OK";
	}

	@Override
	public void deleteAccessRecords(final I_AD_Role role)
	{
		final int adRoleId = role.getAD_Role_ID();

		// Delete role dependent records
		for (final String accessTableName : ROLE_DEPENDENT_TABLENAMES)
		{
			// Skip AD_Role dependent tables which does not have an AD_Role_ID column
			final boolean hasRoleColumn = !I_AD_User_OrgAccess.Table_Name.equals(accessTableName);
			if (!hasRoleColumn)
			{
				continue;
			}

			final int deleteCount = DB.executeUpdateEx("DELETE FROM " + accessTableName + " WHERE AD_Role_ID=" + adRoleId, ITrx.TRXNAME_ThreadInherited);
			logger.info("deleteAccessRecords({}): deleted {} rows from {}", role, adRoleId, deleteCount, accessTableName);
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
	public List<I_AD_Role_OrgAccess> retrieveRoleOrgAccessRecordsForOrg(final int adOrgId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Role_OrgAccess.class)
				.addEqualsFilter(I_AD_Role_OrgAccess.COLUMNNAME_AD_Org_ID, adOrgId)
				// .addOnlyActiveRecordsFilter() // NOTE: retrieve all
				.create()
				.list(I_AD_Role_OrgAccess.class);
	}

	@Override
	public void createOrgAccess(final int adRoleId, final int adOrgId)
	{
		final I_AD_Role_OrgAccess roleOrgAccess = InterfaceWrapperHelper.newInstance(I_AD_Role_OrgAccess.class);
		roleOrgAccess.setAD_Org_ID(adOrgId);
		roleOrgAccess.setAD_Role_ID(adRoleId);
		roleOrgAccess.setIsReadOnly(false);
		InterfaceWrapperHelper.save(roleOrgAccess);
	}

	@Override
	public void createWindowAccess(final I_AD_Role role, final int adWindowId, final boolean readWrite)
	{
		changeWindowAccess(role, adWindowId, windowAccess -> {
			windowAccess.setIsActive(true);
			windowAccess.setIsReadWrite(readWrite);
		});
	}

	@Override
	public void deleteWindowAccess(final I_AD_Role role, final int adWindowId)
	{
		changeWindowAccess(role, adWindowId, windowAccess -> {
			windowAccess.setIsActive(false); // request to be deleted
		});
	}

	private void changeWindowAccess(@NonNull final I_AD_Role role, final int adWindowId, @NonNull final Consumer<I_AD_Window_Access> updater)
	{
		Preconditions.checkArgument(adWindowId > 0, "adWindowId > 0");
		final int adRoleId = role.getAD_Role_ID();

		I_AD_Window_Access windowAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Window_Access.class)
				.addEqualsFilter(I_AD_Window_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addEqualsFilter(I_AD_Window_Access.COLUMNNAME_AD_Window_ID, adWindowId)
				.create()
				.firstOnly(I_AD_Window_Access.class);

		if (windowAccess == null)
		{
			windowAccess = InterfaceWrapperHelper.newInstance(I_AD_Window_Access.class);
			InterfaceWrapperHelper.setValue(windowAccess, I_AD_Window_Access.COLUMNNAME_AD_Client_ID, role.getAD_Client_ID());
			windowAccess.setAD_Org_ID(role.getAD_Org_ID());
			windowAccess.setAD_Role_ID(adRoleId);
			windowAccess.setAD_Window_ID(adWindowId);
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
	public void createProcessAccess(final I_AD_Role role, final int adProcessId, final boolean readWrite)
	{
		changeProcessAccess(role, adProcessId, access -> {
			access.setIsActive(true);
			access.setIsReadWrite(readWrite);
		});
	}

	@Override
	public void deleteProcessAccess(final I_AD_Role role, final int adProcessId)
	{
		changeProcessAccess(role, adProcessId, access -> {
			access.setIsActive(false); // request to be deleted
		});
	}

	private void changeProcessAccess(@NonNull final I_AD_Role role, final int adProcessId, @NonNull final Consumer<I_AD_Process_Access> updater)
	{
		Preconditions.checkArgument(adProcessId > 0, "adProcessId > 0");
		final int adRoleId = role.getAD_Role_ID();

		I_AD_Process_Access processAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Process_Access.class)
				.addEqualsFilter(I_AD_Process_Access.COLUMN_AD_Role_ID, adRoleId)
				.addEqualsFilter(I_AD_Process_Access.COLUMN_AD_Process_ID, adProcessId)
				.create()
				.firstOnly(I_AD_Process_Access.class);

		if (processAccess == null)
		{
			processAccess = InterfaceWrapperHelper.newInstance(I_AD_Process_Access.class);
			InterfaceWrapperHelper.setValue(processAccess, I_AD_Process_Access.COLUMNNAME_AD_Client_ID, role.getAD_Client_ID());
			processAccess.setAD_Org_ID(role.getAD_Org_ID());
			processAccess.setAD_Role_ID(adRoleId);
			processAccess.setAD_Process_ID(adProcessId);
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
	public void createFormAccess(final I_AD_Role role, final int adFormId, final boolean readWrite)
	{
		changeFormAccess(role, adFormId, access -> {
			access.setIsActive(true);
			access.setIsReadWrite(readWrite);
		});
	}

	@Override
	public void deleteFormAccess(final I_AD_Role role, final int adFormId)
	{
		changeFormAccess(role, adFormId, access -> {
			access.setIsActive(false); // request to be deleted
		});
	}

	private void changeFormAccess(@NonNull final I_AD_Role role, final int adFormId, @NonNull final Consumer<I_AD_Form_Access> updater)
	{
		Preconditions.checkArgument(adFormId > 0, "adFormId > 0");
		final int adRoleId = role.getAD_Role_ID();

		I_AD_Form_Access formAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Form_Access.class)
				.addEqualsFilter(I_AD_Form_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addEqualsFilter(I_AD_Form_Access.COLUMNNAME_AD_Form_ID, adFormId)
				.create()
				.firstOnly(I_AD_Form_Access.class);

		if (formAccess == null)
		{
			formAccess = InterfaceWrapperHelper.newInstance(I_AD_Form_Access.class);
			InterfaceWrapperHelper.setValue(formAccess, I_AD_Form_Access.COLUMNNAME_AD_Client_ID, role.getAD_Client_ID());
			formAccess.setAD_Org_ID(role.getAD_Org_ID());
			formAccess.setAD_Role_ID(adRoleId);
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
	public void createTaskAccess(final I_AD_Role role, final int adTaskId, final boolean readWrite)
	{
		changeTaskAccess(role, adTaskId, access -> {
			access.setIsActive(true);
			access.setIsReadWrite(readWrite);
		});
	}

	@Override
	public void deleteTaskAccess(final I_AD_Role role, final int adTaskId)
	{
		changeTaskAccess(role, adTaskId, access -> {
			access.setIsActive(false); // request to be deleted
		});
	}

	private void changeTaskAccess(@NonNull final I_AD_Role role, final int adTaskId, @NonNull final Consumer<I_AD_Task_Access> updater)
	{
		Preconditions.checkArgument(adTaskId > 0, "adTaskId > 0");
		final int adRoleId = role.getAD_Role_ID();

		I_AD_Task_Access taskAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Task_Access.class)
				.addEqualsFilter(I_AD_Task_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addEqualsFilter(I_AD_Task_Access.COLUMNNAME_AD_Task_ID, adTaskId)
				.create()
				.firstOnly(I_AD_Task_Access.class);

		if (taskAccess == null)
		{
			taskAccess = InterfaceWrapperHelper.newInstance(I_AD_Task_Access.class);
			InterfaceWrapperHelper.setValue(taskAccess, I_AD_Task_Access.COLUMNNAME_AD_Client_ID, role.getAD_Client_ID());
			taskAccess.setAD_Org_ID(role.getAD_Org_ID());
			taskAccess.setAD_Role_ID(adRoleId);
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
	public void createWorkflowAccess(final I_AD_Role role, final int adWorkflowId, final boolean readWrite)
	{
		changeWorkflowAccess(role, adWorkflowId, access -> {
			access.setIsActive(true);
			access.setIsReadWrite(readWrite);
		});
	}

	@Override
	public void deleteWorkflowAccess(final I_AD_Role role, final int adWorkflowId)
	{
		changeWorkflowAccess(role, adWorkflowId, access -> {
			access.setIsActive(false); // request to be deleted
		});
	}

	private void changeWorkflowAccess(@NonNull final I_AD_Role role, final int adWorkflowId, @NonNull final Consumer<I_AD_Workflow_Access> updater)
	{
		Preconditions.checkArgument(adWorkflowId > 0, "adWorkflowId > 0");
		final int adRoleId = role.getAD_Role_ID();

		I_AD_Workflow_Access workflowAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Workflow_Access.class)
				.addEqualsFilter(I_AD_Workflow_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addEqualsFilter(I_AD_Workflow_Access.COLUMNNAME_AD_Workflow_ID, adWorkflowId)
				.create()
				.firstOnly(I_AD_Workflow_Access.class);

		if (workflowAccess == null)
		{
			workflowAccess = InterfaceWrapperHelper.newInstance(I_AD_Workflow_Access.class);
			InterfaceWrapperHelper.setValue(workflowAccess, I_AD_Workflow_Access.COLUMNNAME_AD_Client_ID, role.getAD_Client_ID());
			workflowAccess.setAD_Org_ID(role.getAD_Org_ID());
			workflowAccess.setAD_Role_ID(adRoleId);
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
	public void createDocumentActionAccess(final I_AD_Role role, final int docTypeId, final int docActionRefListId)
	{
		changeDocumentActionAccess(role, docTypeId, docActionRefListId, access -> {
			access.setIsActive(true);
		});
	}

	@Override
	public void deleteDocumentActionAccess(final I_AD_Role role, final int docTypeId, final int docActionRefListId)
	{
		changeDocumentActionAccess(role, docTypeId, docActionRefListId, access -> {
			access.setIsActive(false); // request to be deleted
		});
	}

	private void changeDocumentActionAccess(@NonNull final I_AD_Role role, final int docTypeId, final int docActionRefListId, @NonNull final Consumer<I_AD_Document_Action_Access> updater)
	{
		Preconditions.checkArgument(docTypeId > 0, "docTypeId > 0");
		Preconditions.checkArgument(docActionRefListId > 0, "docActionRefListId > 0");

		final int adRoleId = role.getAD_Role_ID();

		I_AD_Document_Action_Access docActionAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Document_Action_Access.class)
				.addEqualsFilter(I_AD_Document_Action_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addEqualsFilter(I_AD_Document_Action_Access.COLUMNNAME_C_DocType_ID, docTypeId)
				.addEqualsFilter(I_AD_Document_Action_Access.COLUMNNAME_AD_Ref_List_ID, docActionRefListId)
				.create()
				.firstOnly(I_AD_Document_Action_Access.class);

		if (docActionAccess == null)
		{
			docActionAccess = InterfaceWrapperHelper.newInstance(I_AD_Document_Action_Access.class);
			InterfaceWrapperHelper.setValue(docActionAccess, I_AD_Document_Action_Access.COLUMNNAME_AD_Client_ID, role.getAD_Client_ID());
			docActionAccess.setAD_Org_ID(role.getAD_Org_ID());
			docActionAccess.setAD_Role_ID(adRoleId);
			docActionAccess.setC_DocType_ID(docTypeId);
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
	public I_AD_Record_Access changeRecordAccess(@NonNull final I_AD_Role role, final int adTableId, final int recordId, @NonNull final Consumer<I_AD_Record_Access> updater)
	{
		Preconditions.checkArgument(adTableId > 0, "adTableId > 0");
		Preconditions.checkArgument(recordId >= 0, "recordId >= 0");
		final int adRoleId = role.getAD_Role_ID();

		I_AD_Record_Access recordAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Record_Access.class)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_AD_Role_ID, adRoleId)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Record_Access.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnly(I_AD_Record_Access.class);

		if (recordAccess == null)
		{
			recordAccess = InterfaceWrapperHelper.newInstance(I_AD_Record_Access.class);
			InterfaceWrapperHelper.setValue(recordAccess, I_AD_Record_Access.COLUMNNAME_AD_Client_ID, role.getAD_Client_ID());
			recordAccess.setAD_Org_ID(role.getAD_Org_ID());
			recordAccess.setAD_Role_ID(adRoleId);
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
	public void createPrivateAccess(final int adUserId, final int adTableId, final int recordId)
	{
		changePrivateAccess(adUserId, adTableId, recordId, access -> {
			access.setIsActive(true);
		});
	}

	@Override
	public void deletePrivateAccess(final int adUserId, final int adTableId, final int recordId)
	{
		changePrivateAccess(adUserId, adTableId, recordId, access -> {
			access.setIsActive(false); // request to be deleted
		});
	}

	public void changePrivateAccess(final int adUserId, final int adTableId, final int recordId, @NonNull final Consumer<I_AD_Private_Access> updater)
	{
		Preconditions.checkArgument(adUserId >= 0, "adUserId >= 0");
		Preconditions.checkArgument(adTableId > 0, "adTableId > 0");
		Preconditions.checkArgument(recordId >= 0, "recordId >= 0");

		I_AD_Private_Access privateAccess = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Private_Access.class)
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_AD_User_ID, adUserId)
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_AD_Table_ID, adTableId)
				.addEqualsFilter(I_AD_Private_Access.COLUMNNAME_Record_ID, recordId)
				.create()
				.firstOnly(I_AD_Private_Access.class);

		if (privateAccess == null)
		{
			privateAccess = InterfaceWrapperHelper.newInstance(I_AD_Private_Access.class);
			privateAccess.setAD_Org_ID(Env.CTXVALUE_AD_Org_ID_System);
			privateAccess.setAD_User_ID(adUserId);
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
	public Set<Integer> retrievePrivateAccessRecordIds(final int adUserId, final int adTableId)
	{
		final String sql = "SELECT Record_ID "
				+ " FROM " + I_AD_Private_Access.Table_Name
				+ " WHERE AD_User_ID=? AND AD_Table_ID=? AND IsActive=? "
				+ " ORDER BY Record_ID";
		final Object[] sqlParams = new Object[] { adUserId, adTableId, true };
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

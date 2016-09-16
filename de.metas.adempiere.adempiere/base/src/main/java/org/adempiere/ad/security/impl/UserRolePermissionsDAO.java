package org.adempiere.ad.security.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.impl.TypedSqlQueryFilter;
import org.adempiere.ad.persistence.EntityTypesCache;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.security.IRolesTreeNode;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsBuilder;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
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
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.Predicate;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Column_Access;
import org.compiere.model.I_AD_Document_Action_Access;
import org.compiere.model.I_AD_Form;
import org.compiere.model.I_AD_Form_Access;
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
import org.compiere.model.I_AD_Window;
import org.compiere.model.I_AD_Window_Access;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_AD_Workflow_Access;
import org.compiere.model.X_AD_Role;
import org.compiere.model.X_AD_Table_Access;
import org.compiere.util.CacheMgt;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import de.metas.logging.LogManager;

public class UserRolePermissionsDAO implements IUserRolePermissionsDAO
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	@Override
	public void resetCache()
	{
		final CacheMgt cacheManager = CacheMgt.get();
		cacheManager.reset(I_AD_Role.Table_Name);
		cacheManager.reset(I_AD_Role_Included.Table_Name);
		cacheManager.reset(I_AD_User_OrgAccess.Table_Name);
		cacheManager.reset(I_AD_Window_Access.Table_Name);
		cacheManager.reset(I_AD_Process_Access.Table_Name);
		cacheManager.reset(I_AD_Task_Access.Table_Name);
		cacheManager.reset(I_AD_Form_Access.Table_Name);
		cacheManager.reset(I_AD_Workflow_Access.Table_Name);
		cacheManager.reset(I_AD_Document_Action_Access.Table_Name);
		cacheManager.reset(I_AD_Table_Access.Table_Name);
		cacheManager.reset(I_AD_Record_Access.Table_Name);
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
	public boolean matchUserRolesPermissionsForUser(final Properties ctx, final int adUserId, final Predicate<IUserRolePermissions> matcher)
	{
		final int adClientId = Env.getAD_Client_ID(ctx);
		final Date date = Env.getDate(ctx);
		for (final I_AD_Role role : Services.get(IRoleDAO.class).retrieveRolesForUser(ctx, adUserId))
		{
			final int adRoleId = role.getAD_Role_ID();
			final IUserRolePermissions permissions = retrieveUserRolePermissions(adRoleId, adUserId, adClientId, date);

			if (matcher.evaluate(permissions))
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
	public IUserRolePermissions retrieveUserRolePermissions(final UserRolePermissionsKey key)
	{
		Check.assumeNotNull(key, "Parameter key is not null");
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
					return retrieveIndividialUseRolePermissions(node.getAD_Role_ID(), adUserId, adClientId)
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
					return retrieveIndividialUseRolePermissions(node.getAD_Role_ID(), adUserId, adClientId);
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
	IUserRolePermissions retrieveIndividialUseRolePermissions(final int adRoleId, final int adUserId, final int adClientId)
	{
		return new UserRolePermissionsBuilder()
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
			return retrieveRoleOrgPermissions(role.getAD_Role_ID(), adTreeOrgId);
		}

	}

	@Override
	@Cached(cacheName = I_AD_User_OrgAccess.Table_Name + "#by#AD_User_ID")
	public OrgPermissions retrieveUserOrgPermissions(final int adUserId, final int adTreeOrgId)
	{
		final Properties ctx = Env.getCtx();
		final List<I_AD_User_OrgAccess> orgAccessesList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_OrgAccess.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_User_OrgAccess.COLUMNNAME_AD_User_ID, adUserId)
				.addOnlyActiveRecordsFilter()
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
		final List<I_AD_Role_OrgAccess> orgAccessesList = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Role_OrgAccess.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Role_OrgAccess.COLUMNNAME_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
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

	final <AccessTableType> ElementPermissions retrieveElementPermissions(final int adRoleId, final int adClientId,
			final Class<AccessTableType> accessTableClass,
			final String elementTableName,
			final String elementColumnName)
	{
		final Properties ctx = Env.getCtx();
		final IQueryFilter<AccessTableType> aspFilter = Services.get(IASPFiltersFactory.class).getASPFiltersForClient(adClientId).getFilter(accessTableClass);

		//
		// EntityType filter: filter out those elements where EntityType is not displayed
		final String accessTableName = InterfaceWrapperHelper.getTableName(accessTableClass);
		final IQueryFilter<AccessTableType> entityTypeFilter = new TypedSqlQueryFilter<>("exists (select 1 from " + elementTableName + " t"
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
	public String updateAccessRecords(final I_AD_Role role)
	{
		if (role.isManual())
		{
			return "-";
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(role);
		final int adRoleId = role.getAD_Role_ID();
		final int adClientId = role.getAD_Client_ID();
		final int adOrgId = role.getAD_Org_ID();
		final int updatedBy = role.getUpdatedBy();
		final String userLevel = role.getUserLevel();

		final String roleClientOrgUser = adRoleId + ","
				+ adClientId + "," + adOrgId + ",'Y', now(),"
				+ updatedBy + ", now()," + updatedBy
				+ ",'Y' ";	// IsReadWrite

		String sqlWindow = "INSERT INTO AD_Window_Access "
				+ "(AD_Window_ID, AD_Role_ID,"
				+ " AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) "
				+ "SELECT DISTINCT w.AD_Window_ID, " + roleClientOrgUser
				+ "FROM AD_Window w"
				+ " INNER JOIN AD_Tab t ON (w.AD_Window_ID=t.AD_Window_ID)"
				+ " INNER JOIN AD_Table tt ON (t.AD_Table_ID=tt.AD_Table_ID) "
				+ "WHERE t.SeqNo=(SELECT MIN(SeqNo) FROM AD_Tab xt "	// only check first tab
				+ "WHERE xt.AD_Window_ID=w.AD_Window_ID)"
				+ "AND tt.AccessLevel IN ";

		String sqlProcess = "INSERT INTO AD_Process_Access "
				+ "(AD_Process_ID, AD_Role_ID,"
				+ " AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) "
				+ "SELECT DISTINCT p.AD_Process_ID, " + roleClientOrgUser
				+ "FROM AD_Process p "
				+ "WHERE AccessLevel IN ";

		String sqlForm = "INSERT INTO AD_Form_Access "
				+ "(AD_Form_ID, AD_Role_ID,"
				+ " AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) "
				+ "SELECT f.AD_Form_ID, " + roleClientOrgUser
				+ "FROM AD_Form f "
				+ "WHERE AccessLevel IN ";

		String sqlWorkflow = "INSERT INTO AD_WorkFlow_Access "
				+ "(AD_WorkFlow_ID, AD_Role_ID,"
				+ " AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,IsReadWrite) "
				+ "SELECT w.AD_WorkFlow_ID, " + roleClientOrgUser
				+ "FROM AD_WorkFlow w "
				+ "WHERE AccessLevel IN ";

		String sqlDocAction = "INSERT INTO AD_Document_Action_Access "
				+ "(AD_Client_ID,AD_Org_ID,IsActive,Created,CreatedBy,Updated,UpdatedBy,"
				+ "C_DocType_ID , AD_Ref_List_ID, AD_Role_ID) "
				+ "(SELECT "
				+ adClientId + ",0,'Y', now(),"
				+ updatedBy + ", now()," + updatedBy
				+ ", doctype.C_DocType_ID, action.AD_Ref_List_ID, rol.AD_Role_ID "
				+ "FROM AD_Client client "
				+ "INNER JOIN C_DocType doctype ON (doctype.AD_Client_ID=client.AD_Client_ID) "
				+ "INNER JOIN AD_Ref_List action ON (action.AD_Reference_ID=135) "
				+ "INNER JOIN AD_Role rol ON (rol.AD_Client_ID=client.AD_Client_ID "
				+ "AND rol.AD_Role_ID=" + adRoleId
				+ ") )";

		/**
		 * Fill AD_xx_Access
		 * ---------------------------------------------------------------------------
		 * SCO# Levels S__ 100 4 System info
		 * SCO 111 7 System shared info
		 * SC_ 110 6 System/Client info
		 * _CO 011 3 Client shared info
		 * _C_ 011 2 Client
		 * __O 001 1 Organization info
		 * Roles:
		 * S 4,7,6
		 * _CO 7,6,3,2,1
		 * __O 3,1,7
		 */
		String roleAccessLevel = null;
		String roleAccessLevelWin = null;
		if (X_AD_Role.USERLEVEL_System.equals(userLevel))
			roleAccessLevel = "('4','7','6')";
		else if (X_AD_Role.USERLEVEL_Client.equals(userLevel))
			roleAccessLevel = "('7','6','3','2')";
		else if (X_AD_Role.USERLEVEL_ClientPlusOrganization.equals(userLevel))
			roleAccessLevel = "('7','6','3','2','1')";
		else
		// if (USERLEVEL_Organization.equals(getUserLevel()))
		{
			roleAccessLevel = "('3','1','7')";
			roleAccessLevelWin = roleAccessLevel
					+ " AND w.Name NOT LIKE '%(all)%'";
		}
		if (roleAccessLevelWin == null)
			roleAccessLevelWin = roleAccessLevel;
		//
		String whereDel = " WHERE AD_Role_ID=" + adRoleId;
		//
		int winDel = DB.executeUpdate("DELETE FROM AD_Window_Access" + whereDel, trxName);
		int win = DB.executeUpdate(sqlWindow + roleAccessLevelWin, trxName);
		int procDel = DB.executeUpdate("DELETE FROM AD_Process_Access" + whereDel, trxName);
		int proc = DB.executeUpdate(sqlProcess + roleAccessLevel, trxName);
		int formDel = DB.executeUpdate("DELETE FROM AD_Form_Access" + whereDel, trxName);
		int form = DB.executeUpdate(sqlForm + roleAccessLevel, trxName);
		int wfDel = DB.executeUpdate("DELETE FROM AD_WorkFlow_Access" + whereDel, trxName);
		int wf = DB.executeUpdate(sqlWorkflow + roleAccessLevel, trxName);
		int docactDel = DB.executeUpdate("DELETE FROM AD_Document_Action_Access" + whereDel, trxName);
		int docact = DB.executeUpdate(sqlDocAction, trxName);

		logger.debug("AD_Window_ID=" + winDel + "+" + win
				+ ", AD_Process_ID=" + procDel + "+" + proc
				+ ", AD_Form_ID=" + formDel + "+" + form
				+ ", AD_Workflow_ID=" + wfDel + "+" + wf
				+ ", AD_Document_Action_Access=" + docactDel + "+" + docact);

		// loadAccess(true);
		return "@AD_Window_ID@ #" + win
				+ " -  @AD_Process_ID@ #" + proc
				+ " -  @AD_Form_ID@ #" + form
				+ " -  @AD_Workflow_ID@ #" + wf
				+ " -  @DocAction@ #" + docact;

	}	// createAccessRecords

	@Override
	public void deleteAccessRecords(final I_AD_Role role)
	{
		final int adRoleId = role.getAD_Role_ID();
		final String trxName = InterfaceWrapperHelper.getTrxName(role);

		final String whereDel = " WHERE AD_Role_ID=" + adRoleId;
		//
		int winDel = DB.executeUpdate("DELETE FROM AD_Window_Access" + whereDel, trxName);
		int procDel = DB.executeUpdate("DELETE FROM AD_Process_Access" + whereDel, trxName);
		int formDel = DB.executeUpdate("DELETE FROM AD_Form_Access" + whereDel, trxName);
		int wfDel = DB.executeUpdate("DELETE FROM AD_WorkFlow_Access" + whereDel, trxName);
		int docactDel = DB.executeUpdate("DELETE FROM AD_Document_Action_Access" + whereDel, trxName);

		logger.debug("AD_Window_Access=" + winDel
				+ ", AD_Process_Access=" + procDel
				+ ", AD_Form_Access=" + formDel
				+ ", AD_Workflow_Access=" + wfDel
				+ ", AD_Document_Action_Access=" + docactDel);
	}

}

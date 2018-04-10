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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.security.IRoleDAO;
import org.adempiere.ad.security.IRolesTreeNode;
import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.ad.security.IUserRolePermissionsDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_AD_Role_Included;
import org.compiere.model.I_AD_User_Roles;
import org.compiere.model.I_AD_User_Substitute;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_AD_Role;
import de.metas.adempiere.util.CacheCtx;

public class RoleDAO implements IRoleDAO
{
	// private static final transient Logger logger = CLogMgt.getLogger(RoleDAO.class);

	@Override
	public I_AD_Role retrieveRole(final Properties ctx)
	{
		return retrieveRole(ctx, Env.getAD_Role_ID(ctx));
	}

	@Override
	// NOTE: we assume AD_Role table is configured in IModelCacheService.
	// @Cached(cacheName = I_AD_Role.Table_Name + "#By#AD_Role_ID")
	public I_AD_Role retrieveRole(@CacheCtx final Properties ctx, final int AD_Role_ID)
	{
		if (AD_Role_ID < 0)
		{
			return null;
		}
		// special Handling for System Role because of ID=ZERO
		else if (AD_Role_ID == IUserRolePermissions.SYSTEM_ROLE_ID)
		{
			// TODO: check retrieving from cache first
			return Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Role.class, ctx, ITrx.TRXNAME_None)
					.addEqualsFilter(I_AD_Role.COLUMNNAME_AD_Role_ID, IUserRolePermissions.SYSTEM_ROLE_ID)
					.create()
					.firstOnlyNotNull(I_AD_Role.class);
		}
		else
		// if (AD_Role_ID > 0)
		{
			return InterfaceWrapperHelper.create(ctx, AD_Role_ID, I_AD_Role.class, ITrx.TRXNAME_None);
		}
	}

	@Override
	@Cached(cacheName = I_AD_Role.Table_Name + "#For#AD_User_ID")
	public List<I_AD_Role> retrieveRolesForUser(@CacheCtx final Properties ctx, final int adUserId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Roles.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_User_Roles.COLUMN_AD_User_ID, adUserId)
				.andCollect(I_AD_User_Roles.COLUMN_AD_Role_ID)
				.addOnlyActiveRecordsFilter()
				//
				.orderBy()
				.addColumn(I_AD_Role.COLUMN_AD_Role_ID)
				.endOrderBy()
				//
				.create()
				.list(I_AD_Role.class);
	}

	@Override
	public final List<I_AD_Role> retrieveSubstituteRoles(final Properties ctx, final int adUserId, final Date date)
	{
		// final Date date = SystemTime.asDate();

		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Substitute.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addValidFromToMatchesFilter(I_AD_User_Substitute.COLUMN_ValidFrom, I_AD_User_Substitute.COLUMN_ValidTo, date)
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
				.orderBy()
				.addColumn(I_AD_Role.COLUMN_AD_Role_ID) // just to have a predictible order
				.endOrderBy()
				//
				.create().list(I_AD_Role.class);
	}

	@Override
	@Cached(cacheName = I_AD_Role_Included.Table_Name + "#by#AD_Role_ID", expireMinutes = Cached.EXPIREMINUTES_Never)
	public List<I_AD_Role_Included> retrieveRoleIncludes(@CacheCtx final Properties ctx, final int adRoleId)
	{
		final List<I_AD_Role_Included> roleIncludes = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Role_Included.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Role_Included.COLUMN_AD_Role_ID, adRoleId)
				//
				.orderBy()
				.addColumn(I_AD_Role_Included.COLUMNNAME_SeqNo)
				.addColumn(I_AD_Role_Included.COLUMN_Included_Role_ID)
				.endOrderBy()
				//
				.create()
				.list();
		return ImmutableList.copyOf(roleIncludes);
	}

	@Override
	public IRolesTreeNode retrieveRolesTree(final int adRoleId, int substitute_ForUserId, Date substituteDate)
	{
		return RolesTreeNode.of(adRoleId, substitute_ForUserId, substituteDate);
	}

	@Override
	public List<I_AD_Role> retrieveAllRolesWithAutoMaintenance(final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Role.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Role.COLUMNNAME_IsManual, false)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	public List<I_AD_Role> retrieveAllRolesWithUserAccess(final Properties ctx)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Role.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Role.COLUMNNAME_IsManual, false)
				.addOnlyActiveRecordsFilter()
				.create()
				.setApplyAccessFilterRW(IUserRolePermissions.SQL_RO)
				.list(I_AD_Role.class);
	}

	@Override
	public List<I_AD_Role> retrieveRolesForClient(final Properties ctx, final int adClientId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_Role.class, ctx, ITrx.TRXNAME_None)
				.addEqualsFilter(I_AD_Role.COLUMNNAME_AD_Client_ID, adClientId)
				.addOnlyActiveRecordsFilter()
				.create()
				.list();
	}

	@Override
	public String retrieveRoleName(final Properties ctx, final int adRoleId)
	{
		final I_AD_Role role = retrieveRole(ctx, adRoleId);
		return role == null ? "<" + adRoleId + ">" : role.getName();
	}

	@Override
	// @Cached(cacheName = I_AD_User_Roles.Table_Name + "#by#AD_Role_ID", expireMinutes = 0) // not sure if caching is needed...
	public List<Integer> retrieveUserIdsForRoleId(final int adRoleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Roles.class)
				.addEqualsFilter(I_AD_User_Roles.COLUMN_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.listDistinct(I_AD_User_Roles.COLUMNNAME_AD_User_ID, Integer.class);
	}

	@Override
	public int retrieveFirstRoleIdForUserId(final int adUserId)
	{
		final Integer firstRoleId = Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Roles.class)
				.addEqualsFilter(I_AD_User_Roles.COLUMN_AD_User_ID, adUserId)
				.addOnlyActiveRecordsFilter()
				.create()
				.first(I_AD_User_Roles.COLUMNNAME_AD_Role_ID, Integer.class);
		return firstRoleId == null ? -1 : firstRoleId;
	}

	@Override
	public void createUserRoleAssignmentIfMissing(final int adUserId, final int adRoleId)
	{
		if(hasUserRoleAssignment(adUserId, adRoleId))
		{
			return;
		}

		final I_AD_User_Roles userRole = InterfaceWrapperHelper.newInstance(I_AD_User_Roles.class);
		userRole.setAD_User_ID(adUserId);
		userRole.setAD_Role_ID(adRoleId);
		InterfaceWrapperHelper.save(userRole);

		Services.get(IUserRolePermissionsDAO.class).resetCacheAfterTrxCommit();
	}

	private boolean hasUserRoleAssignment(final int adUserId, final int adRoleId)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Roles.class)
				.addEqualsFilter(I_AD_User_Roles.COLUMNNAME_AD_User_ID, adUserId)
				.addEqualsFilter(I_AD_User_Roles.COLUMN_AD_Role_ID, adRoleId)
				.addOnlyActiveRecordsFilter()
				.create()
				.match();
	}

}

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.security.permissions.record_access;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.user.UserGroupId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.table.TableRecordIdDescriptor;
import org.adempiere.ad.table.api.ITableRecordIdDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User_Record_Access;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Repository
public class RecordAccessRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final ITableRecordIdDAO tableRecordIdDAO = Services.get(ITableRecordIdDAO.class);
	
	private final CCache<Integer, ImmutableSet<String>> handledTableNamesCache = CCache.<Integer, ImmutableSet<String>>builder()
			.tableName(I_AD_User_Record_Access.Table_Name)
			.initialCapacity(1)
			.expireMinutes(CCache.EXPIREMINUTES_Never)
			.build();

	public ImmutableSet<String> getHandledTableNames()
	{
		return handledTableNamesCache.getOrLoad(0, this::retrieveHandledTableNames);
	}

	private ImmutableSet<String> retrieveHandledTableNames()
	{
		final ImmutableSet<String> tableNames = tableRecordIdDAO.getTableRecordIdReferences(I_AD_User_Record_Access.Table_Name)
				.stream()
				.map(TableRecordIdDescriptor::getTargetTableName)
				.collect(ImmutableSet.toImmutableSet());

		return ImmutableSet.copyOf(tableNames);
	}

	void deleteByIds(final Collection<RecordAccessId> ids, @NonNull final UserId requestedBy)
	{
		if (ids.isEmpty())
		{
			return;
		}

		try (final IAutoCloseable ignored = Env.temporaryChangeLoggedUserId(requestedBy))
		{
			queryBL
					.createQueryBuilder(I_AD_User_Record_Access.class)
					.addInArrayFilter(I_AD_User_Record_Access.COLUMNNAME_AD_User_Record_Access_ID, ids)
					.create()
					.delete();
		}
	}

	IQueryBuilder<I_AD_User_Record_Access> query(@NonNull final RecordAccessQuery query)
	{
		final IQueryBuilder<I_AD_User_Record_Access> queryBuilder = queryBL.createQueryBuilder(I_AD_User_Record_Access.class);

		//
		// Records
		final ImmutableSet<TableRecordReference> recordRefs = query.getRecordRefs();
		if (!recordRefs.isEmpty())
		{
			final ICompositeQueryFilter<I_AD_User_Record_Access> recordRefsFilter = queryBuilder.addCompositeQueryFilter()
					.setJoinOr();
			for (final TableRecordReference recordRef : recordRefs)
			{
				recordRefsFilter.addCompositeQueryFilter()
						.setJoinAnd()
						.addEqualsFilter(I_AD_User_Record_Access.COLUMNNAME_AD_Table_ID, recordRef.getAD_Table_ID())
						.addEqualsFilter(I_AD_User_Record_Access.COLUMNNAME_Record_ID, recordRef.getRecord_ID());
			}
		}

		//
		// Permissions
		final ImmutableSet<Access> permissions = query.getPermissions();
		if (!permissions.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_AD_User_Record_Access.COLUMNNAME_Access, permissions);
		}

		//
		// Principals
		if (!query.getPrincipals().isEmpty())
		{
			final Set<UserId> userIds = new HashSet<>();
			final Set<UserGroupId> userGroupIds = new HashSet<>();

			for (final Principal principal : query.getPrincipals())
			{
				if (principal.getUserId() != null)
				{
					userIds.add(principal.getUserId());
				}
				else if (principal.getUserGroupId() != null)
				{
					userGroupIds.add(principal.getUserGroupId());
				}
				else
				{
					throw new AdempiereException("Invalid principal: " + principal); // shall not happen
				}
			}

			if (!userIds.isEmpty() || !userGroupIds.isEmpty())
			{
				final ICompositeQueryFilter<I_AD_User_Record_Access> principalsFilter = queryBuilder.addCompositeQueryFilter()
						.setJoinOr();
				if (!userIds.isEmpty())
				{
					principalsFilter.addInArrayFilter(I_AD_User_Record_Access.COLUMNNAME_AD_User_ID, userIds);
				}
				if (!userGroupIds.isEmpty())
				{
					principalsFilter.addInArrayFilter(I_AD_User_Record_Access.COLUMNNAME_AD_UserGroup_ID, userGroupIds);
				}
			}
		}

		//
		// Issuer
		if (query.getIssuer() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User_Record_Access.COLUMNNAME_PermissionIssuer, query.getIssuer().getCode());
		}

		//
		return queryBuilder;
	}
}

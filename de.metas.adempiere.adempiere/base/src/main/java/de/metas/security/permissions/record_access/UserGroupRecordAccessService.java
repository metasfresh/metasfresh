package de.metas.security.permissions.record_access;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User_Record_Access;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.listeners.CompositeUserGroupAccessChangeListener;
import de.metas.security.permissions.record_access.listeners.NullUserGroupAccessChangeListener;
import de.metas.security.permissions.record_access.listeners.UserGroupAccessChangeListener;
import de.metas.user.UserGroupId;
import de.metas.user.UserId;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Service
public class UserGroupRecordAccessService
{
	private static final Logger logger = LogManager.getLogger(UserGroupRecordAccessService.class);
	private final UserGroupAccessChangeListener listeners;

	public UserGroupRecordAccessService(
			@NonNull final Optional<List<UserGroupAccessChangeListener>> listeners)
	{
		this.listeners = listeners
				.map(CompositeUserGroupAccessChangeListener::of)
				.orElse(NullUserGroupAccessChangeListener.instance);

		logger.info("Listeners: {}", this.listeners);
	}

	@PostConstruct
	private void postConstruct()
	{
		listeners.setUserGroupRecordAccessService(this);
	}

	public void grantAccess(@NonNull final UserGroupRecordAccessGrantRequest request)
	{
		final UserGroupRecordAccess access = UserGroupRecordAccess.builder()
				.recordRef(request.getRecordRef())
				.principal(request.getPrincipal())
				.permission(request.getPermission())
				.build();

		saveAndFire(access);
	}

	public void revokeAccess(@NonNull final UserGroupRecordAccessRevokeRequest request)
	{
		final UserGroupRecordAccessQuery query = UserGroupRecordAccessQuery.builder()
				.recordRef(request.getRecordRef())
				.principal(request.getPrincipal())
				.permissions(request.isRevokeAllPermissions() ? ImmutableSet.of() : request.getPermissions())
				.build();
		final List<I_AD_User_Record_Access> accessRecords = query(query)
				.create()
				.list();

		final ImmutableSet<UserGroupRecordAccess> accesses = toUserGroupRecordAccessesSet(accessRecords);
		final Set<Integer> repoIds = extractRepoIds(accessRecords);
		deleteAndFire(accesses, repoIds);
	}

	public void copyAccess(@NonNull TableRecordReference to, @NonNull TableRecordReference from)
	{
		final HashMap<UserGroupRecordAccess, I_AD_User_Record_Access> toAccessRecordsToCheck = queryByRecord(to)
				.create()
				.stream()
				.collect(GuavaCollectors.toMapByKey(HashMap::new, record -> toUserGroupRecordAccess(record)));

		//
		// Grant accesses
		final List<UserGroupRecordAccess> fromAccesses = queryByRecord(from)
				.create()
				.stream()
				.map(record -> toUserGroupRecordAccess(record))
				.collect(ImmutableList.toImmutableList());
		for (final UserGroupRecordAccess fromAccess : fromAccesses)
		{
			final UserGroupRecordAccess toAccess = fromAccess.withRecordRef(to);
			final I_AD_User_Record_Access toAccessRecord = toAccessRecordsToCheck.remove(toAccess);
			saveAndFire(toAccess, toAccessRecord);
		}

		//
		// Revoke accesses
		if (!toAccessRecordsToCheck.isEmpty())
		{
			deleteAndFire(
					toAccessRecordsToCheck.keySet(),
					extractRepoIds(toAccessRecordsToCheck.values()));
		}
	}

	private void saveAndFire(@NonNull final UserGroupRecordAccess access)
	{
		final UserGroupRecordAccessQuery query = UserGroupRecordAccessQuery.builder()
				.recordRef(access.getRecordRef())
				.principal(access.getPrincipal())
				.permission(access.getPermission())
				.build();

		final I_AD_User_Record_Access existingRecord = query(query)
				.create()
				.firstOnly(I_AD_User_Record_Access.class);

		saveAndFire(access, existingRecord);
	}

	private void saveAndFire(@NonNull final UserGroupRecordAccess access, @Nullable final I_AD_User_Record_Access existingAccessRecord)
	{
		final I_AD_User_Record_Access accessRecord = existingAccessRecord != null ? existingAccessRecord : newRecord();
		updateRecord(accessRecord, access);
		if (!InterfaceWrapperHelper.hasChanges(accessRecord))
		{
			return;
		}

		saveRecord(accessRecord);
		listeners.onAccessGranted(access);
	}

	private void deleteAndFire(final Set<UserGroupRecordAccess> accesses, final Set<Integer> repoIds)
	{
		deleteByIds(repoIds);
		accesses.forEach(listeners::onAccessRevoked);
	}

	private IQueryBuilder<I_AD_User_Record_Access> queryByRecord(@NonNull final TableRecordReference recordRef)
	{
		return query(UserGroupRecordAccessQuery.builder()
				.recordRef(recordRef)
				.build());
	}

	private IQueryBuilder<I_AD_User_Record_Access> query(@NonNull final UserGroupRecordAccessQuery query)
	{
		final IQueryBuilder<I_AD_User_Record_Access> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_AD_User_Record_Access.class);

		final ImmutableSet<TableRecordReference> recordRefs = query.getRecordRefs();
		if (!recordRefs.isEmpty())
		{
			final ICompositeQueryFilter<I_AD_User_Record_Access> recordRefsFilter = queryBuilder.addCompositeQueryFilter()
					.setJoinOr();
			for (final TableRecordReference recordRef : recordRefs)
			{
				recordRefsFilter.addEqualsFilter(I_AD_User_Record_Access.COLUMN_AD_Table_ID, recordRef.getAD_Table_ID());
				recordRefsFilter.addEqualsFilter(I_AD_User_Record_Access.COLUMN_Record_ID, recordRef.getRecord_ID());
			}
		}

		final ImmutableSet<Access> permissions = query.getPermissions();
		if (!permissions.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_AD_User_Record_Access.COLUMN_Access, permissions);
		}

		if (query.getPrincipal() != null)
		{
			final Principal principal = query.getPrincipal();
			if (principal.getUserId() != null)
			{
				queryBuilder.addEqualsFilter(I_AD_User_Record_Access.COLUMN_AD_User_ID, principal.getUserId());
			}
			else if (principal.getUserGroupId() != null)
			{
				queryBuilder.addEqualsFilter(I_AD_User_Record_Access.COLUMN_AD_UserGroup_ID, principal.getUserGroupId());
			}
			else
			{
				throw new AdempiereException("Invalid pricipal: " + principal); // shall not happen
			}
		}

		return queryBuilder;
	}

	private I_AD_User_Record_Access newRecord()
	{
		return newInstance(I_AD_User_Record_Access.class);
	}

	private void updateRecord(@NonNull final I_AD_User_Record_Access toRecord, @NonNull final UserGroupRecordAccess from)
	{
		toRecord.setIsActive(true);
		toRecord.setAD_Table_ID(from.getRecordRef().getAD_Table_ID());
		toRecord.setRecord_ID(from.getRecordRef().getRecord_ID());
		toRecord.setAccess(from.getPermission().getCode());
		toRecord.setAD_User_ID(UserId.toRepoId(from.getPrincipal().getUserId()));
		toRecord.setAD_UserGroup_ID(UserGroupId.toRepoId(from.getPrincipal().getUserGroupId()));
	}

	private void deleteByIds(final Set<Integer> repoIds)
	{
		if (repoIds.isEmpty())
		{
			return;
		}

		Services.get(IQueryBL.class)
				.createQueryBuilder(I_AD_User_Record_Access.class)
				.addInArrayFilter(I_AD_User_Record_Access.COLUMN_AD_User_Record_Access_ID, repoIds)
				.create()
				.delete();
	}

	private static ImmutableSet<UserGroupRecordAccess> toUserGroupRecordAccessesSet(final Collection<I_AD_User_Record_Access> accessRecords)
	{
		return accessRecords.stream()
				.map(record -> toUserGroupRecordAccess(record))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static UserGroupRecordAccess toUserGroupRecordAccess(final I_AD_User_Record_Access record)
	{
		return UserGroupRecordAccess.builder()
				.recordRef(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.principal(Principal.builder()
						.userId(UserId.ofRepoIdOrNull(record.getAD_User_ID()))
						.userGroupId(UserGroupId.ofRepoIdOrNull(record.getAD_UserGroup_ID()))
						.build())
				.permission(Access.ofCode(record.getAccess()))
				.build();
	}

	private static ImmutableSet<Integer> extractRepoIds(Collection<I_AD_User_Record_Access> accessRecords)
	{
		return accessRecords.stream().map(I_AD_User_Record_Access::getAD_User_Record_Access_ID).collect(ImmutableSet.toImmutableSet());
	}

	public static String buildUserGroupRecordAccessSqlWhereClause(
			final int adTableId,
			@NonNull final String keyColumnNameFQ,
			@NonNull final UserId userId,
			@NonNull final Set<UserGroupId> userGroupIds)
	{
		final StringBuilder sql = new StringBuilder();
		sql.append(" EXISTS (SELECT 1 FROM " + I_AD_User_Record_Access.Table_Name + " z "
				+ " WHERE "
				+ " z.AD_Table_ID = " + adTableId
				+ " AND z.Record_ID=" + keyColumnNameFQ
				+ " AND z.IsActive='Y'");

		//
		// User or User Group
		sql.append(" AND (AD_User_ID=" + userId.getRepoId());
		if (!userGroupIds.isEmpty())
		{
			sql.append(" OR ").append(DB.buildSqlList("z.AD_UserGroup_ID", userGroupIds));
		}
		sql.append(")");

		//
		sql.append(" )"); // EXISTS

		//
		return sql.toString();
	}
}

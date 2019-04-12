package de.metas.security.permissions.record_access;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User_Record_Access;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.listeners.UserGroupAccessChangeEvent;
import de.metas.security.permissions.record_access.listeners.UserGroupAccessChangeEvent.UserGroupAccessChangeEventBuilder;
import de.metas.security.permissions.record_access.listeners.UserGroupAccessChangeEventDispatcher;
import de.metas.security.permissions.record_access.listeners.UserGroupAccessChangeListener;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
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
	private final UserGroupRepository userGroupsRepo;
	private final IEventBus eventBus;

	public UserGroupRecordAccessService(
			@NonNull final IEventBusFactory eventBusFactory,
			@NonNull final UserGroupRepository userGroupsRepo,
			@NonNull final Optional<List<UserGroupAccessChangeListener>> listeners)
	{
		this.userGroupsRepo = userGroupsRepo;
		eventBus = eventBusFactory.getEventBus(UserGroupAccessChangeEventDispatcher.TOPIC);
	}

	public void grantAccess(@NonNull final UserGroupRecordAccessGrantRequest request)
	{
		final UserGroupRecordAccessQuery query = UserGroupRecordAccessQuery.builder()
				.recordRef(request.getRecordRef())
				.principal(request.getPrincipal())
				.permissions(request.getPermissions())
				.build();

		final HashMap<UserGroupRecordAccess, I_AD_User_Record_Access> existingRecords = query(query)
				.create()
				.stream()
				.collect(GuavaCollectors.toMapByKey(HashMap::new, record -> toUserGroupRecordAccess(record)));

		final List<UserGroupRecordAccess> accesses = toUserGroupRecordAccessesList(request);
		for (final UserGroupRecordAccess access : accesses)
		{
			save(access, existingRecords.get(access));
		}

		fireEvent(UserGroupAccessChangeEvent.accessGrants(accesses));
	}

	private static List<UserGroupRecordAccess> toUserGroupRecordAccessesList(@NonNull final UserGroupRecordAccessGrantRequest request)
	{
		final ImmutableList.Builder<UserGroupRecordAccess> result = ImmutableList.builder();
		for (final Access permission : request.getPermissions())
		{
			result.add(UserGroupRecordAccess.builder()
					.recordRef(request.getRecordRef())
					.principal(request.getPrincipal())
					.permission(permission)
					.build());
		}

		return result.build();
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

		final Set<Integer> repoIds = extractRepoIds(accessRecords);
		deleteByIds(repoIds);

		final ImmutableSet<UserGroupRecordAccess> accesses = toUserGroupRecordAccessesSet(accessRecords);
		fireEvent(UserGroupAccessChangeEvent.accessRevokes(accesses));
	}

	public void copyAccess(@NonNull final TableRecordReference to, @NonNull final TableRecordReference from)
	{
		final HashMap<UserGroupRecordAccess, I_AD_User_Record_Access> toAccessRecordsToCheck = queryByRecord(to)
				.create()
				.stream()
				.collect(GuavaCollectors.toMapByKey(HashMap::new, record -> toUserGroupRecordAccess(record)));

		final UserGroupAccessChangeEventBuilder eventsCollector = UserGroupAccessChangeEvent.builder();

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
			save(toAccess, toAccessRecord);
			eventsCollector.accessGrant(toAccess);
		}

		//
		// Revoke accesses
		if (!toAccessRecordsToCheck.isEmpty())
		{
			deleteByIds(extractRepoIds(toAccessRecordsToCheck.values()));
			eventsCollector.accessRevokes(toAccessRecordsToCheck.keySet());
		}

		fireEvent(eventsCollector.build());
	}

	private void save(@NonNull final UserGroupRecordAccess access, @Nullable final I_AD_User_Record_Access existingAccessRecord)
	{
		final I_AD_User_Record_Access accessRecord = existingAccessRecord != null ? existingAccessRecord : newRecord();
		updateRecord(accessRecord, access);
		if (!InterfaceWrapperHelper.hasChanges(accessRecord))
		{
			return;
		}

		saveRecord(accessRecord);
	}

	private void fireEvent(@NonNull final UserGroupAccessChangeEvent event)
	{
		if (event.isEmpty())
		{
			return;
		}

		Services.get(ITrxManager.class)
				.runAfterCommit(() -> eventBus.postObject(event));
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
				recordRefsFilter.addCompositeQueryFilter()
						.setJoinAnd()
						.addEqualsFilter(I_AD_User_Record_Access.COLUMN_AD_Table_ID, recordRef.getAD_Table_ID())
						.addEqualsFilter(I_AD_User_Record_Access.COLUMN_Record_ID, recordRef.getRecord_ID());
			}
		}

		final ImmutableSet<Access> permissions = query.getPermissions();
		if (!permissions.isEmpty())
		{
			queryBuilder.addInArrayFilter(I_AD_User_Record_Access.COLUMN_Access, permissions);
		}

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
					throw new AdempiereException("Invalid pricipal: " + principal); // shall not happen
				}
			}

			if (!userIds.isEmpty() || !userGroupIds.isEmpty())
			{
				final ICompositeQueryFilter<I_AD_User_Record_Access> principalsFilter = queryBuilder.addCompositeQueryFilter()
						.setJoinOr();
				if (!userIds.isEmpty())
				{
					principalsFilter.addInArrayFilter(I_AD_User_Record_Access.COLUMN_AD_User_ID, userIds);
				}
				if (!userGroupIds.isEmpty())
				{
					principalsFilter.addInArrayFilter(I_AD_User_Record_Access.COLUMN_AD_UserGroup_ID, userGroupIds);
				}
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

	private static ImmutableSet<Integer> extractRepoIds(final Collection<I_AD_User_Record_Access> accessRecords)
	{
		return accessRecords.stream().map(I_AD_User_Record_Access::getAD_User_Record_Access_ID).collect(ImmutableSet.toImmutableSet());
	}

	public String buildUserGroupRecordAccessSqlWhereClause(
			final String tableName,
			final int adTableId,
			@NonNull final String keyColumnNameFQ,
			@NonNull final UserId userId,
			@NonNull final Set<UserGroupId> userGroupIds)
	{
		if (!isApplyUserGroupRecordAccess(tableName))
		{
			return null;
		}

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

	private boolean isApplyUserGroupRecordAccess(final String tableName)
	{
		// if(true) return false;
		// FIXME: HARDCODED
		return I_C_BPartner.Table_Name.equals(tableName)
				|| I_C_Order.Table_Name.equals(tableName);
	}

	public boolean hasRecordPermission(
			@NonNull final UserId userId,
			@NonNull final TableRecordReference recordRef,
			@NonNull final Access permission)
	{
		if (!isApplyUserGroupRecordAccess(recordRef.getTableName()))
		{
			return true;
		}

		final UserGroupRecordAccessQuery query = UserGroupRecordAccessQuery.builder()
				.recordRef(recordRef)
				.permission(permission)
				.principals(getPrincipals(userId))
				.build();

		return query(query)
				.addOnlyActiveRecordsFilter()
				.create()
				.match();
	}

	private Set<Principal> getPrincipals(@NonNull final UserId userId)
	{
		final ImmutableSet.Builder<Principal> principals = ImmutableSet.builder();
		principals.add(Principal.userId(userId));

		for (final UserGroupId userGroupId : userGroupsRepo.getAssignedGroupIdsByUserId(userId))
		{
			principals.add(Principal.userGroupId(userGroupId));
		}

		return principals.build();
	}
}

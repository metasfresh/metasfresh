package de.metas.security.permissions.record_access;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.event.IEventBus;
import de.metas.event.IEventBusFactory;
import de.metas.security.Principal;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.handlers.RecordAccessChangeEvent;
import de.metas.security.permissions.record_access.handlers.RecordAccessChangeEventDispatcher;
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
public class RecordAccessService
{
	private final RecordAccessConfigService configs;
	private final UserGroupRepository userGroupsRepo;
	private final IEventBus eventBus;

	public RecordAccessService(
			@NonNull final RecordAccessConfigService configs,
			@NonNull final UserGroupRepository userGroupsRepo,
			@NonNull final IEventBusFactory eventBusFactory)
	{
		this.userGroupsRepo = userGroupsRepo;
		this.configs = configs;

		eventBus = eventBusFactory.getEventBus(RecordAccessChangeEventDispatcher.TOPIC);
	}

	public boolean isFeatureEnabled(@NonNull final RecordAccessFeature feature)
	{
		return configs.isFeatureEnabled(feature);
	}

	public void grantAccess(@NonNull final RecordAccessGrantRequest request)
	{
		final RecordAccessQuery query = RecordAccessQuery.builder()
				.recordRef(request.getRecordRef())
				.principal(request.getPrincipal())
				.permissions(request.getPermissions())
				.build();

		final HashMap<RecordAccess, I_AD_User_Record_Access> existingRecords = query(query)
				.create()
				.stream()
				.collect(GuavaCollectors.toMapByKey(HashMap::new, record -> toUserGroupRecordAccess(record)));

		final List<RecordAccess> accesses = toUserGroupRecordAccessesList(request);
		for (final RecordAccess access : accesses)
		{
			save(access, existingRecords.get(access));
		}

		fireEvent(RecordAccessChangeEvent.accessGrants(accesses));
	}

	private static List<RecordAccess> toUserGroupRecordAccessesList(@NonNull final RecordAccessGrantRequest request)
	{
		final ImmutableList.Builder<RecordAccess> result = ImmutableList.builder();
		for (final Access permission : request.getPermissions())
		{
			result.add(RecordAccess.builder()
					.recordRef(request.getRecordRef())
					.principal(request.getPrincipal())
					.permission(permission)
					.build());
		}

		return result.build();
	}

	public void revokeAccess(@NonNull final RecordAccessRevokeRequest request)
	{
		final RecordAccessQuery query = RecordAccessQuery.builder()
				.recordRef(request.getRecordRef())
				.principal(request.getPrincipal())
				.permissions(request.isRevokeAllPermissions() ? ImmutableSet.of() : request.getPermissions())
				.build();
		final List<I_AD_User_Record_Access> accessRecords = query(query)
				.create()
				.list();

		final Set<Integer> repoIds = extractRepoIds(accessRecords);
		deleteByIds(repoIds);

		final ImmutableSet<RecordAccess> accesses = toUserGroupRecordAccessesSet(accessRecords);
		fireEvent(RecordAccessChangeEvent.accessRevokes(accesses));
	}

	public void copyAccess(
			@NonNull final TableRecordReference target,
			@Nullable final TableRecordReference grantFrom,
			@Nullable final TableRecordReference revokeFrom)
	{
		if (grantFrom == null && revokeFrom == null)
		{
			return;
		}

		//
		final HashSet<RecordAccess> accessGrants = new HashSet<>();
		final HashMap<RecordAccess, Integer> accessRevokes = new HashMap<>();

		//
		// Fetch existing accesses of our target record
		final ImmutableMap<RecordAccess, I_AD_User_Record_Access> existingTargetAccessRecords = queryByRecord(target)
				.create()
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(record -> toUserGroupRecordAccess(record)));

		//
		// Revoke accesses
		if (revokeFrom != null)
		{
			final List<RecordAccess> fromAccesses = queryByRecord(revokeFrom)
					.create()
					.stream()
					.map(record -> toUserGroupRecordAccess(record))
					.collect(ImmutableList.toImmutableList());
			for (final RecordAccess fromAccess : fromAccesses)
			{
				final RecordAccess targetAccess = fromAccess.withRecordRef(target);
				final I_AD_User_Record_Access targetAccessRecord = existingTargetAccessRecords.get(targetAccess);
				if (targetAccessRecord != null)
				{
					accessRevokes.put(targetAccess, targetAccessRecord.getAD_User_Record_Access_ID());
				}
			}
		}

		//
		// Grant accesses
		if (grantFrom != null)
		{
			final List<RecordAccess> fromAccesses = queryByRecord(grantFrom)
					.create()
					.stream()
					.map(record -> toUserGroupRecordAccess(record))
					.collect(ImmutableList.toImmutableList());
			for (final RecordAccess fromAccess : fromAccesses)
			{
				final RecordAccess targetAccess = fromAccess.withRecordRef(target);

				final boolean wasJustRevoked = accessRevokes.remove(targetAccess) != null;

				if (!existingTargetAccessRecords.containsKey(targetAccess)
						&& !wasJustRevoked)
				{
					accessGrants.add(targetAccess);
				}
			}
		}

		//
		// Stop here if nothing changed
		if (accessGrants.isEmpty() && accessRevokes.isEmpty())
		{
			return;
		}

		//
		// Persist to database
		{
			accessGrants.forEach(access -> save(access, null));
			deleteByIds(accessRevokes.values());
		}

		//
		// Fire event
		fireEvent(RecordAccessChangeEvent.builder()
				.accessGrants(accessGrants)
				.accessRevokes(accessRevokes.keySet())
				.build());
	}

	private void save(@NonNull final RecordAccess access, @Nullable final I_AD_User_Record_Access existingAccessRecord)
	{
		final I_AD_User_Record_Access accessRecord = existingAccessRecord != null ? existingAccessRecord : newRecord();
		updateRecord(accessRecord, access);
		if (!InterfaceWrapperHelper.hasChanges(accessRecord))
		{
			return;
		}

		saveRecord(accessRecord);
	}

	private void fireEvent(@NonNull final RecordAccessChangeEvent event)
	{
		if (event.isEmpty())
		{
			return;
		}

		Services.get(ITrxManager.class)
				.runAfterCommit(() -> eventBus.postObject(event));
	}

	@VisibleForTesting
	Set<RecordAccess> getAccessesByRecord(@NonNull final TableRecordReference recordRef)
	{
		return queryByRecord(recordRef)
				.create()
				.stream()
				.map(record -> toUserGroupRecordAccess(record))
				.collect(ImmutableSet.toImmutableSet());
	}

	private IQueryBuilder<I_AD_User_Record_Access> queryByRecord(@NonNull final TableRecordReference recordRef)
	{
		return query(RecordAccessQuery.builder()
				.recordRef(recordRef)
				.build());
	}

	private IQueryBuilder<I_AD_User_Record_Access> query(@NonNull final RecordAccessQuery query)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IQueryBuilder<I_AD_User_Record_Access> queryBuilder = queryBL.createQueryBuilder(I_AD_User_Record_Access.class);

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

	private void updateRecord(@NonNull final I_AD_User_Record_Access toRecord, @NonNull final RecordAccess from)
	{
		toRecord.setIsActive(true);
		toRecord.setAD_Table_ID(from.getRecordRef().getAD_Table_ID());
		toRecord.setRecord_ID(from.getRecordRef().getRecord_ID());
		toRecord.setAccess(from.getPermission().getCode());
		toRecord.setAD_User_ID(UserId.toRepoId(from.getPrincipal().getUserId()));
		toRecord.setAD_UserGroup_ID(UserGroupId.toRepoId(from.getPrincipal().getUserGroupId()));
	}

	private void deleteByIds(final Collection<Integer> repoIds)
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

	private static ImmutableSet<RecordAccess> toUserGroupRecordAccessesSet(final Collection<I_AD_User_Record_Access> accessRecords)
	{
		return accessRecords.stream()
				.map(record -> toUserGroupRecordAccess(record))
				.collect(ImmutableSet.toImmutableSet());
	}

	private static RecordAccess toUserGroupRecordAccess(final I_AD_User_Record_Access record)
	{
		final UserId userId = InterfaceWrapperHelper.isNull(record, I_AD_User_Record_Access.COLUMNNAME_AD_User_ID)
				? null
				: UserId.ofRepoId(record.getAD_User_ID());

		final UserGroupId userGroupId = InterfaceWrapperHelper.isNull(record, I_AD_User_Record_Access.COLUMNNAME_AD_UserGroup_ID)
				? null
				: UserGroupId.ofRepoId(record.getAD_UserGroup_ID());

		return RecordAccess.builder()
				.recordRef(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.principal(Principal.builder()
						.userId(userId)
						.userGroupId(userGroupId)
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
			@NonNull final Set<UserGroupId> userGroupIds,
			@NonNull final RoleId roleId)
	{
		if (!isApplyUserGroupRecordAccess(roleId, tableName))
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

	private boolean isApplyUserGroupRecordAccess(
			@NonNull final RoleId roleId,
			@NonNull final String tableName)
	{
		return configs
				.getByRoleId(roleId)
				.isTableHandled(tableName);
	}

	public boolean hasRecordPermission(
			@NonNull final UserId userId,
			@NonNull final RoleId roleId,
			@NonNull final TableRecordReference recordRef,
			@NonNull final Access permission)
	{
		if (!isApplyUserGroupRecordAccess(roleId, recordRef.getTableName()))
		{
			return true;
		}

		final RecordAccessQuery query = RecordAccessQuery.builder()
				.recordRef(recordRef)
				.permission(permission)
				.principals(getPrincipals(userId))
				.build();

		return query(query)
				.addOnlyActiveRecordsFilter()
				.create()
				.anyMatch();
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

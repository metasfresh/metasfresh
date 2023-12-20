package de.metas.security.permissions.record_access;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.security.Principal;
import de.metas.security.RoleId;
import de.metas.security.permissions.Access;
import de.metas.user.UserGroupId;
import de.metas.user.UserGroupRepository;
import de.metas.user.UserId;
import lombok.NonNull;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User_Record_Access;
import org.compiere.util.DB;
import org.compiere.util.Env;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final RecordAccessRepository recordAccessRepository;
	private final RecordAccessConfigService configs;
	private final UserGroupRepository userGroupsRepo;

	public RecordAccessService(
			@NonNull final RecordAccessRepository recordAccessRepository,
			@NonNull final RecordAccessConfigService configs,
			@NonNull final UserGroupRepository userGroupsRepo)
	{
		this.recordAccessRepository = recordAccessRepository;
		this.userGroupsRepo = userGroupsRepo;
		this.configs = configs;
	}

	public void grantAccess(@NonNull final RecordAccessGrantRequest request)
	{
		final RecordAccessQuery query = RecordAccessQuery.builder()
				.recordRef(request.getRecordRef())
				.principal(request.getPrincipal())
				.permissions(request.getPermissions())
				.issuer(request.getIssuer())
				.build();

		final ImmutableSet<RecordAccessId> existingRecordIds = recordAccessRepository.query(query)
				.create()
				.listIds(RecordAccessId::ofRepoId);
		recordAccessRepository.deleteByIds(existingRecordIds, request.getRequestedBy());

		final List<RecordAccess> accessesToSave = toUserGroupRecordAccessesList(request);
		saveNew(accessesToSave);
	}

	private static List<RecordAccess> toUserGroupRecordAccessesList(@NonNull final RecordAccessGrantRequest request)
	{
		final RecordAccess.RecordAccessBuilder recordAccessBuilder = RecordAccess.builder()
				.recordRef(request.getRecordRef())
				.principal(request.getPrincipal())
				.issuer(request.getIssuer())
				.createdBy(request.getRequestedBy())
				.description(request.getDescription())
				.parentId(request.getParentAccessId())
				.rootId(request.getRootAccessId());

		return request.getPermissions()
				.stream()
				.map(permission -> recordAccessBuilder
						.permission(permission)
						.build())
				.collect(ImmutableList.toImmutableList());
	}

	public void revokeAccess(@NonNull final RecordAccessRevokeRequest request)
	{
		final RecordAccessQuery query = RecordAccessQuery.builder()
				.recordRef(request.getRecordRef())
				.principal(request.getPrincipal())
				.permissions(request.isRevokeAllPermissions() ? ImmutableSet.of() : request.getPermissions())
				.issuer(request.getIssuer())
				.build();
		final List<I_AD_User_Record_Access> existingAccessRecords = recordAccessRepository.query(query)
				.create()
				.list();

		final Set<RecordAccessId> existingAccessRecordIds = extractIds(existingAccessRecords);
		recordAccessRepository.deleteByIds(existingAccessRecordIds, request.getRequestedBy());
	}

	void saveNew(@NonNull final Collection<RecordAccess> accessList)
	{
		accessList.forEach(this::saveNew);
	}

	void saveNew(@NonNull final RecordAccess access)
	{
		if (access.getId() != null)
		{
			throw new AdempiereException("Expected to be new: " + access);
		}

		final I_AD_User_Record_Access accessRecord = newInstance(I_AD_User_Record_Access.class);
		updateRecord(accessRecord, access);

		try (final IAutoCloseable ignored = Env.temporaryChangeLoggedUserId(access.getCreatedBy()))
		{
			saveRecord(accessRecord);
		}

		final RecordAccessId id = extractId(accessRecord);
		access.setId(id);

		if (access.getRootId() == null)
		{
			accessRecord.setRoot_ID(id.getRepoId());
			saveRecord(accessRecord);
			access.setRootId(id);
		}
	}

	public ImmutableList<RecordAccess> getAccessesByRecordAndIssuer(
			@NonNull final TableRecordReference recordRef,
			@NonNull final PermissionIssuer issuer)
	{
		final RecordAccessQuery query = RecordAccessQuery.builder()
				.recordRef(recordRef)
				.issuer(issuer)
				.build();

		return recordAccessRepository.query(query)
				.create()
				.stream()
				.map(RecordAccessService::toUserGroupRecordAccess)
				.collect(ImmutableList.toImmutableList());
	}


	private void updateRecord(@NonNull final I_AD_User_Record_Access toRecord, @NonNull final RecordAccess from)
	{
		toRecord.setIsActive(true);
		toRecord.setAD_Table_ID(from.getRecordRef().getAD_Table_ID());
		toRecord.setRecord_ID(from.getRecordRef().getRecord_ID());
		toRecord.setAccess(from.getPermission().getCode());
		toRecord.setAD_User_ID(UserId.toRepoId(from.getPrincipal().getUserId()));
		toRecord.setAD_UserGroup_ID(UserGroupId.toRepoId(from.getPrincipal().getUserGroupId()));
		toRecord.setPermissionIssuer(from.getIssuer().getCode());
		toRecord.setDescription(from.getDescription());

		toRecord.setRoot_ID(RecordAccessId.toRepoId(from.getRootId()));
		toRecord.setParent_ID(RecordAccessId.toRepoId(from.getParentId()));
	}


	@VisibleForTesting
	static RecordAccess toUserGroupRecordAccess(final I_AD_User_Record_Access record)
	{
		return RecordAccess.builder()
				.recordRef(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.principal(extractPrincipal(record))
				.permission(extractPermission(record))
				.issuer(PermissionIssuer.ofCode(record.getPermissionIssuer()))
				.createdBy(UserId.ofRepoId(record.getCreatedBy()))
				.description(record.getDescription())
				//
				.id(extractId(record))
				.parentId(RecordAccessId.ofRepoIdOrNull(record.getParent_ID()))
				.rootId(RecordAccessId.ofRepoIdOrNull(record.getRoot_ID()))
				//
				.build();
	}

	static Principal extractPrincipal(@NonNull final I_AD_User_Record_Access record)
	{
		final UserId userId = InterfaceWrapperHelper.isNull(record, I_AD_User_Record_Access.COLUMNNAME_AD_User_ID)
				? null
				: UserId.ofRepoId(record.getAD_User_ID());

		final UserGroupId userGroupId = InterfaceWrapperHelper.isNull(record, I_AD_User_Record_Access.COLUMNNAME_AD_UserGroup_ID)
				? null
				: UserGroupId.ofRepoId(record.getAD_UserGroup_ID());

		return Principal.builder()
				.userId(userId)
				.userGroupId(userGroupId)
				.build();
	}

	static Access extractPermission(final I_AD_User_Record_Access record)
	{
		return Access.ofCode(record.getAccess());
	}

	private static ImmutableSet<RecordAccessId> extractIds(final Collection<I_AD_User_Record_Access> accessRecords)
	{
		return accessRecords.stream()
				.map(RecordAccessService::extractId)
				.collect(ImmutableSet.toImmutableSet());
	}

	private static RecordAccessId extractId(final I_AD_User_Record_Access record)
	{
		return RecordAccessId.ofRepoId(record.getAD_User_Record_Access_ID());
	}

	@Nullable
	public String buildUserGroupRecordAccessSqlWhereClause(
			final String tableName,
			@NonNull final AdTableId adTableId,
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
		sql.append(" EXISTS (SELECT 1 FROM " + I_AD_User_Record_Access.Table_Name + " z " + " WHERE " + " z.AD_Table_ID = ").append(adTableId.getRepoId()).append(" AND z.Record_ID=").append(keyColumnNameFQ).append(" AND z.IsActive='Y'");

		//
		// User or User Group
		sql.append(" AND (AD_User_ID=").append(userId.getRepoId());
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

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
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

		return recordAccessRepository.query(query)
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

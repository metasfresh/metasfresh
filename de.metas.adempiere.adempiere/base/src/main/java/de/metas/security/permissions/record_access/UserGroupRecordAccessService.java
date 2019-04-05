package de.metas.security.permissions.record_access;

import static org.adempiere.model.InterfaceWrapperHelper.deleteAll;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nullable;
import javax.annotation.PostConstruct;

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

import de.metas.logging.LogManager;
import de.metas.security.permissions.Access;
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

	public void grantAccess(@NonNull final UserGroupRecordAccess request)
	{
		I_AD_User_Record_Access existingRecord = query(request)
				.create()
				.firstOnly(I_AD_User_Record_Access.class);
		updateSaveAndFire(request, existingRecord);
	}

	public void revokeAccess(@NonNull final UserGroupRecordAccess request)
	{
		final int deleteCount = query(request)
				.create()
				.delete();

		if (deleteCount > 0)
		{
			listeners.onAccessRevoked(request);
		}
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
			I_AD_User_Record_Access toAccessRecord = toAccessRecordsToCheck.remove(toAccess);
			updateSaveAndFire(toAccess, toAccessRecord);
		}

		//
		// Revoke accesses
		deleteAll(toAccessRecordsToCheck.values());
		toAccessRecordsToCheck.keySet().forEach(listeners::onAccessRevoked);
	}

	private void updateSaveAndFire(@NonNull final UserGroupRecordAccess access, @Nullable final I_AD_User_Record_Access existingAccessRecord)
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

	private IQueryBuilder<I_AD_User_Record_Access> queryByRecord(@NonNull final TableRecordReference recordRef)
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_User_Record_Access.class)
				.addEqualsFilter(I_AD_User_Record_Access.COLUMN_AD_Table_ID, recordRef.getAD_Table_ID())
				.addEqualsFilter(I_AD_User_Record_Access.COLUMN_Record_ID, recordRef.getRecord_ID());
	}

	private IQueryBuilder<I_AD_User_Record_Access> query(final UserGroupRecordAccess request)
	{
		final IQueryBuilder<I_AD_User_Record_Access> queryBuilder = queryByRecord(request.getRecordRef())
				.addEqualsFilter(I_AD_User_Record_Access.COLUMN_Access, request.getAccess().getCode());

		if (request.getUserId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User_Record_Access.COLUMN_AD_User_ID, request.getUserId());
		}
		else if (request.getUserGroupId() != null)
		{
			queryBuilder.addEqualsFilter(I_AD_User_Record_Access.COLUMN_AD_UserGroup_ID, request.getUserGroupId());
		}
		else
		{
			throw new AdempiereException("Invalid: " + request); // shall not happen
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
		toRecord.setAccess(from.getAccess().getCode());
		toRecord.setAD_User_ID(UserId.toRepoId(from.getUserId()));
		toRecord.setAD_UserGroup_ID(UserGroupId.toRepoId(from.getUserGroupId()));
	}

	private static UserGroupRecordAccess toUserGroupRecordAccess(final I_AD_User_Record_Access record)
	{
		return UserGroupRecordAccess.builder()
				.recordRef(TableRecordReference.of(record.getAD_Table_ID(), record.getRecord_ID()))
				.access(Access.ofCode(record.getAccess()))
				.userId(UserId.ofRepoIdOrNull(record.getAD_User_ID()))
				.userGroupId(UserGroupId.ofRepoIdOrNull(record.getAD_UserGroup_ID()))
				.build();
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

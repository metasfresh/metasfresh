package de.metas.security.permissions.record_access;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_User_Record_Access;
import org.compiere.util.DB;
import org.springframework.stereotype.Service;

import de.metas.user.UserGroupId;
import de.metas.user.UserId;
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
	public void grantAccess(@NonNull final UserGroupRecordAccess request)
	{
		I_AD_User_Record_Access record = query(request)
				.create()
				.firstOnly(I_AD_User_Record_Access.class);

		if (record == null)
		{
			record = createRecord(request);
		}

		record.setIsActive(true);

		saveRecord(record);
	}

	public void revokeAccess(@NonNull final UserGroupRecordAccess request)
	{
		query(request)
				.create()
				.delete();
	}

	private IQueryBuilder<I_AD_User_Record_Access> query(final UserGroupRecordAccess request)
	{
		final IQueryBuilder<I_AD_User_Record_Access> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_User_Record_Access.class)
				.addEqualsFilter(I_AD_User_Record_Access.COLUMN_AD_Table_ID, request.getRecordRef().getAD_Table_ID())
				.addEqualsFilter(I_AD_User_Record_Access.COLUMN_Record_ID, request.getRecordRef().getRecord_ID())
				.addEqualsFilter(I_AD_User_Record_Access.COLUMN_Access, request.getAccess().getName());

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

	private I_AD_User_Record_Access createRecord(final UserGroupRecordAccess request)
	{
		I_AD_User_Record_Access record;
		record = newInstanceOutOfTrx(I_AD_User_Record_Access.class);
		record.setAD_Table_ID(request.getRecordRef().getAD_Table_ID());
		record.setRecord_ID(request.getRecordRef().getRecord_ID());
		record.setAccess(request.getAccess().getName());
		record.setAD_User_ID(UserId.toRepoId(request.getUserId()));
		record.setAD_UserGroup_ID(UserGroupId.toRepoId(request.getUserGroupId()));
		return record;
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

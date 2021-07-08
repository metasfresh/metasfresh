package de.metas.user;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

import java.time.Instant;
import java.util.Set;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_UserGroup;
import org.compiere.model.I_AD_UserGroup_User_Assign;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;

import de.metas.cache.CCache;
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

@Repository
public class UserGroupRepository
{
	private final CCache<UserId, UserGroupUserAssignmentsCollection> //
	assignmentsByUserId = CCache.<UserId, UserGroupUserAssignmentsCollection> builder()
			.tableName(I_AD_UserGroup_User_Assign.Table_Name)
			.build();

	public UserGroup getById(@NonNull final UserGroupId id)
	{
		final I_AD_UserGroup record = loadOutOfTrx(id, I_AD_UserGroup.class);
		return toUserGroup(record);
	}

	private static UserGroup toUserGroup(@NonNull final I_AD_UserGroup record)
	{
		return UserGroup.builder()
				.id(UserGroupId.ofRepoId(record.getAD_UserGroup_ID()))
				.name(record.getName())
				.build();
	}

	public Set<UserGroupId> getAssignedGroupIdsByUserId(@NonNull final UserId userId)
	{
		return getAssignedGroupIdsByUserId(userId, SystemTime.asInstant());
	}

	public Set<UserGroupId> getAssignedGroupIdsByUserId(@NonNull final UserId userId, @NonNull final Instant date)
	{
		return assignmentsByUserId
				.getOrLoad(userId, this::retrieveUserAssignments)
				.getAssignedGroupIds(date);
	}

	private UserGroupUserAssignmentsCollection retrieveUserAssignments(@NonNull final UserId userId)
	{
		final ImmutableSet<UserGroupUserAssignment> assignments = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_UserGroup_User_Assign.class)
				.addEqualsFilter(I_AD_UserGroup_User_Assign.COLUMN_AD_User_ID, userId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(record -> toUserGroupUserAssignment(record))
				.collect(ImmutableSet.toImmutableSet());

		return UserGroupUserAssignmentsCollection.of(assignments);
	}

	private static UserGroupUserAssignment toUserGroupUserAssignment(final I_AD_UserGroup_User_Assign record)
	{
		return UserGroupUserAssignment.builder()
				.userId(UserId.ofRepoId(record.getAD_User_ID()))
				.userGroupId(UserGroupId.ofRepoId(record.getAD_UserGroup_ID()))
				.validDates(extractValidDates(record))
				.build();
	}

	private static Range<Instant> extractValidDates(final I_AD_UserGroup_User_Assign record)
	{
		final Instant validFrom = TimeUtil.asInstant(record.getValidFrom());
		final Instant validTo = TimeUtil.asInstant(record.getValidTo());

		if (validFrom == null)
		{
			if (validTo == null)
			{
				return Range.all();
			}
			else
			{
				return Range.atMost(validTo);
			}
		}
		else
		{
			if (validTo == null)
			{
				return Range.atLeast(validFrom);
			}
			else
			{
				return Range.closed(validFrom, validTo);
			}
		}
	}
}

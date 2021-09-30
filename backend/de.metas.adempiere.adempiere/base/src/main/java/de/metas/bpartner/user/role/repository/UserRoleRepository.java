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

package de.metas.bpartner.user.role.repository;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.user.role.UserAssignedRoleId;
import de.metas.bpartner.user.role.UserRole;
import de.metas.bpartner.user.role.UserRoleId;
import de.metas.cache.CCache;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_C_User_Assigned_Role;
import org.compiere.model.I_C_User_Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<UserId, List<UserAssignedRoleId>> assignedUserRoleCache = CCache
			.<UserId, List<UserAssignedRoleId>>builder()
			.tableName(I_C_User_Assigned_Role.Table_Name)
			.build();

	private final CCache<UserRoleId, I_C_User_Role> userRoleCache = CCache
			.<UserRoleId, I_C_User_Role>builder()
			.tableName(I_C_User_Role.Table_Name)
			.build();

	@NonNull
	public List<UserRole> getUserRoles(@NonNull final UserId userId)
	{
		final List<UserAssignedRoleId> assignedRoleIds = assignedUserRoleCache.getOrLoad(userId, this::getAssignedRoleIds);

		return assignedRoleIds.stream()
				.map(assignedRoleId -> {
					final I_C_User_Role role = userRoleCache.getOrLoad(assignedRoleId.getUserRoleId(), this::getUserRoleRecord);
					return toUserRole(assignedRoleId, role);
				})
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private List<UserAssignedRoleId> getAssignedRoleIds(@NonNull final UserId userId)
	{
		return queryBL.createQueryBuilder(I_C_User_Assigned_Role.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_User_Assigned_Role.COLUMNNAME_AD_User_ID, userId)
				.create()
				.stream()
				.map(record -> {
					final UserRoleId roleId = UserRoleId.ofRepoId(record.getC_User_Role_ID());
					return UserAssignedRoleId.ofRepoId(roleId, record.getC_User_Assigned_Role_ID());
				})
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private I_C_User_Role getUserRoleRecord(@NonNull final UserRoleId userRoleId)
	{
		return queryBL.createQueryBuilder(I_C_User_Role.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_User_Role.COLUMN_C_User_Role_ID, userRoleId)
				.create()
				.firstOnlyNotNull(I_C_User_Role.class);
	}

	@NonNull
	private UserRole toUserRole(@NonNull final UserAssignedRoleId assignedRoleId, @NonNull final I_C_User_Role role)
	{
		return UserRole.builder()
				.name(role.getName())
				.uniquePerBpartner(role.isUniqueForBPartner())
				.userAssignedRoleId(assignedRoleId)
				.build();
	}
}

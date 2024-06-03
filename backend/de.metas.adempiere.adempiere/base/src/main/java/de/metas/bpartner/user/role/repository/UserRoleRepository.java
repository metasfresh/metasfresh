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
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
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

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

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

		final Set<UserRoleId> roleIds = assignedRoleIds.stream()
				.map(UserAssignedRoleId::getUserRoleId)
				.collect(ImmutableSet.toImmutableSet());

		final Collection<I_C_User_Role> userRoles = userRoleCache.getAllOrLoad(roleIds, this::loadUserRoles);

		final Map<UserRoleId, I_C_User_Role> userRoleById = Maps.uniqueIndex(userRoles, (userRole) -> UserRoleId.ofRepoId(userRole.getC_User_Role_ID()));

		return assignedRoleIds.stream()
				.map(assignedRoleId -> {
					final I_C_User_Role role = userRoleById.get(assignedRoleId.getUserRoleId());
					return toUserRole(assignedRoleId, role);
				})
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Map<UserRoleId, I_C_User_Role> loadUserRoles(@NonNull final Set<UserRoleId> userRoleId)
	{
		return queryBL.createQueryBuilder(I_C_User_Role.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_C_User_Role.COLUMN_C_User_Role_ID, userRoleId)
				.create()
				.stream()
				.collect(Collectors.toMap(
						role -> UserRoleId.ofRepoId(role.getC_User_Role_ID()),
						Function.identity()
				));
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
	private UserRole toUserRole(@NonNull final UserAssignedRoleId assignedRoleId, @NonNull final I_C_User_Role role)
	{
		return UserRole.builder()
				.name(role.getName())
				.uniquePerBpartner(role.isUniqueForBPartner())
				.userAssignedRoleId(assignedRoleId)
				.build();
	}

	public List<UserId> getAssignedUsers(@NonNull final UserRoleId userRoleId)
	{
		return queryBL.createQueryBuilder(I_C_User_Assigned_Role.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_User_Assigned_Role.COLUMNNAME_C_User_Role_ID, userRoleId)
				.create()
				.listDistinct(I_C_User_Assigned_Role.COLUMNNAME_AD_User_ID, Integer.class)
				.stream()
				.map(UserId::ofRepoId)
				.collect(ImmutableList.toImmutableList());
	}
}

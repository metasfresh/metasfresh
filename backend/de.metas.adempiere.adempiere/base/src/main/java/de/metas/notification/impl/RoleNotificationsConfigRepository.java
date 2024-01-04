package de.metas.notification.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.notification.INotificationGroupNameRepository;
import de.metas.notification.IRoleNotificationsConfigRepository;
import de.metas.notification.NotificationGroupName;
import de.metas.notification.RoleNotificationsConfig;
import de.metas.notification.UserNotificationsGroup;
import de.metas.security.RoleId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_AD_Role;
import org.compiere.model.I_AD_Role_NotificationGroup;

import java.util.List;
import java.util.Objects;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class RoleNotificationsConfigRepository implements IRoleNotificationsConfigRepository
{
	private final CCache<RoleId, RoleNotificationsConfig> roleNotificationsConfigsByRoleId = CCache.<RoleId, RoleNotificationsConfig> builder()
			.tableName(I_AD_Role_NotificationGroup.Table_Name)
			.initialCapacity(10)
			.additionalTableNameToResetFor(I_AD_Role.Table_Name)
			.additionalTableNameToResetFor(I_AD_Role_NotificationGroup.Table_Name)
			.build();

	@Override
	public RoleNotificationsConfig getByRoleId(@NonNull final RoleId adRoleId)
	{
		return roleNotificationsConfigsByRoleId.getOrLoad(adRoleId, this::retrieveRoleNotificationsConfig);
	}

	private RoleNotificationsConfig retrieveRoleNotificationsConfig(@NonNull final RoleId adRoleId)
	{
		final List<UserNotificationsGroup> notificationGroups = Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Role_NotificationGroup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Role_NotificationGroup.COLUMN_AD_Role_ID, adRoleId)
				.create()
				.stream()
				.map(this::toNotificationGroup)
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		return RoleNotificationsConfig.builder()
				.roleId(adRoleId)
				.notificationGroups(notificationGroups)
				.build();
	}

	private UserNotificationsGroup toNotificationGroup(final I_AD_Role_NotificationGroup record)
	{
		final INotificationGroupNameRepository notificationGroupNamesRepo = Services.get(INotificationGroupNameRepository.class);
		final NotificationGroupName groupInternalName = notificationGroupNamesRepo.getById(record.getAD_NotificationGroup_ID());
		if (groupInternalName == null)
		{
			// group does not exist or it was deactivated
			return null;
		}

		return UserNotificationsGroup.builder()
				.groupInternalName(groupInternalName)
				.notificationTypes(UserNotificationsConfigRepository.toNotificationTypes(record.getNotificationType()))
				.build();
	}

	@Override
	public ImmutableSet<RoleId> getRoleIdsContainingNotificationGroupName(@NonNull final NotificationGroupName notificationGroupName)
	{
		final INotificationGroupNameRepository notificationGroupNamesRepo = Services.get(INotificationGroupNameRepository.class);
		final NotificationGroupId notificationGroupId = notificationGroupNamesRepo.getNotificationGroupId(notificationGroupName);

		return Services.get(IQueryBL.class)
				.createQueryBuilderOutOfTrx(I_AD_Role_NotificationGroup.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_AD_Role_NotificationGroup.COLUMN_AD_NotificationGroup_ID, notificationGroupId)
				.create()
				.listDistinct(I_AD_Role_NotificationGroup.COLUMNNAME_AD_Role_ID, Integer.class)
				.stream()
				.map(RoleId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}

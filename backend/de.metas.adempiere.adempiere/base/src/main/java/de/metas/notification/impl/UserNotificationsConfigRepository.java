package de.metas.notification.impl;

import java.util.List;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_NotificationGroup;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_AD_User_NotificationGroup;
import org.compiere.model.X_AD_User_NotificationGroup;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.email.EMailAddress;
import de.metas.notification.INotificationGroupNameRepository;
import de.metas.notification.IUserNotificationsConfigRepository;
import de.metas.notification.NotificationGroupName;
import de.metas.notification.NotificationType;
import de.metas.notification.UserNotificationsConfig;
import de.metas.notification.UserNotificationsGroup;
import de.metas.organization.OrgId;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;

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

public class UserNotificationsConfigRepository implements IUserNotificationsConfigRepository
{
	private final CCache<UserId, UserNotificationsConfig> userNotificationsConfigsByUserId = CCache.<UserId, UserNotificationsConfig> builder()
			.tableName(I_AD_User_NotificationGroup.Table_Name)
			.initialCapacity(100)
			.cacheMapType(CacheMapType.LRU)
			.additionalTableNameToResetFor(org.compiere.model.I_AD_User.Table_Name)
			.additionalTableNameToResetFor(I_AD_NotificationGroup.Table_Name)
			.build();

	@Override
	public UserNotificationsConfig getByUserId(final UserId adUserId)
	{
		return userNotificationsConfigsByUserId.getOrLoad(adUserId, this::retrieveUserNotificationsConfig);
	}

	private UserNotificationsConfig retrieveUserNotificationsConfig(final UserId adUserId)
	{
		final I_AD_User user = Services.get(IUserDAO.class).getById(adUserId);
		final UserId userInChargeId = UserId.ofRepoIdOrNull(user.getAD_User_InCharge_ID());

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final List<UserNotificationsGroup> userNotificationGroups = queryBL.createQueryBuilderOutOfTrx(I_AD_User_NotificationGroup.class)
				.addEqualsFilter(I_AD_User_NotificationGroup.COLUMN_AD_User_ID, adUserId)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream(I_AD_User_NotificationGroup.class)
				.map(notificationsGroupRecord -> toUserNotificationsGroup(notificationsGroupRecord))
				.filter(Objects::nonNull)
				.collect(ImmutableList.toImmutableList());

		final UserNotificationsGroup defaults = UserNotificationsGroup.prepareDefault()
				.notificationTypes(toNotificationTypes(user.getNotificationType()))
				.build();

		return UserNotificationsConfig.builder()
				.userId(UserId.ofRepoId(user.getAD_User_ID()))
				.userADLanguage(user.getAD_Language())
				.clientId(ClientId.ofRepoId(user.getAD_Client_ID()))
				.orgId(OrgId.ofRepoId(user.getAD_Org_ID()))
				.userNotificationGroups(userNotificationGroups)
				.defaults(defaults)
				.email(EMailAddress.ofNullableString(user.getEMail()))
				.userInChargeId(userInChargeId)
				.build();
	}

	private UserNotificationsGroup toUserNotificationsGroup(final I_AD_User_NotificationGroup notificationsGroupRecord)
	{
		if (!notificationsGroupRecord.isActive())
		{
			return null;
		}

		final INotificationGroupNameRepository notificationGroupNamesRepo = Services.get(INotificationGroupNameRepository.class);
		final NotificationGroupName groupInternalName = notificationGroupNamesRepo.getById(notificationsGroupRecord.getAD_NotificationGroup_ID());
		if (groupInternalName == null)
		{
			// group does not exist or it was deactivated
			return null;
		}

		return UserNotificationsGroup.builder()
				.groupInternalName(groupInternalName)
				.notificationTypes(toNotificationTypes(notificationsGroupRecord.getNotificationType()))
				.build();
	}

	static Set<NotificationType> toNotificationTypes(final String notificationTypeCode)
	{
		if (Check.isEmpty(notificationTypeCode, true) || X_AD_User_NotificationGroup.NOTIFICATIONTYPE_None.equals(notificationTypeCode))
		{
			return ImmutableSet.of();
		}
		else if (X_AD_User_NotificationGroup.NOTIFICATIONTYPE_Notice.equals(notificationTypeCode))
		{
			return ImmutableSet.of(NotificationType.Notice);
		}
		else if (X_AD_User_NotificationGroup.NOTIFICATIONTYPE_EMail.equals(notificationTypeCode))
		{
			return ImmutableSet.of(NotificationType.EMail);
		}
		else if (X_AD_User_NotificationGroup.NOTIFICATIONTYPE_EMailPlusNotice.equals(notificationTypeCode))
		{
			return ImmutableSet.of(NotificationType.Notice, NotificationType.EMail);
		}
		else if (X_AD_User_NotificationGroup.NOTIFICATIONTYPE_NotifyUserInCharge.equals(notificationTypeCode))
		{
			return ImmutableSet.of(NotificationType.NotifyUserInCharge);
		}
		else
		{
			throw new AdempiereException("Unknown notification type: " + notificationTypeCode);
		}
	}
}

package de.metas.notification.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.common.util.CoalesceUtil;
import de.metas.notification.IUserNotificationsConfigRepository;
import de.metas.notification.NotificationType;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_User_NotificationGroup;
import org.compiere.model.X_AD_User_NotificationGroup;

import javax.annotation.Nullable;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public void createOrUpdate(@NonNull final UserNotificationGroupCreateRequest request)
	{
		final I_AD_User_NotificationGroup userNotificationGroup = CoalesceUtil.coalesceSuppliersNotNull(
				() -> loadRecordBy(request.getUserId(), request.getNotificationGroupId()),
				() -> InterfaceWrapperHelper.newInstance(I_AD_User_NotificationGroup.class));
		userNotificationGroup.setAD_User_ID(request.getUserId().getRepoId());
		userNotificationGroup.setAD_Org_ID(request.getOrgId().getRepoId());
		userNotificationGroup.setAD_NotificationGroup_ID(request.getNotificationGroupId().getRepoId());
		userNotificationGroup.setNotificationType(fromNotificationType(request.getNotificationType()));
		saveRecord(userNotificationGroup);
	}

	@Nullable
	private I_AD_User_NotificationGroup loadRecordBy(@NonNull final UserId userId, @NonNull final NotificationGroupId notificationGroupId)
	{
		return queryBL.createQueryBuilder(I_AD_User_NotificationGroup.class)
				.addEqualsFilter(I_AD_User_NotificationGroup.COLUMNNAME_AD_User_ID, userId)
				.addEqualsFilter(I_AD_User_NotificationGroup.COLUMNNAME_AD_NotificationGroup_ID, notificationGroupId)
				.create()
				.first();
	}

	@Override
	public void delete(final UserNotificationGroupDeleteRequest request)
	{
		queryBL.createQueryBuilder(I_AD_User_NotificationGroup.class)
				.addEqualsFilter(I_AD_User_NotificationGroup.COLUMNNAME_AD_User_ID, request.getUserId())
				.addEqualsFilter(I_AD_User_NotificationGroup.COLUMNNAME_AD_NotificationGroup_ID, request.getNotificationGroupId())
				.create()
				.delete();
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

	@NonNull
	private static String fromNotificationType(@NonNull final NotificationType notificationType)
	{
		switch (notificationType)
		{
			case Notice:
				return X_AD_User_NotificationGroup.NOTIFICATIONTYPE_Notice;
			case EMail:
				return X_AD_User_NotificationGroup.NOTIFICATIONTYPE_EMail;
			case NotifyUserInCharge:
				return X_AD_User_NotificationGroup.NOTIFICATIONTYPE_NotifyUserInCharge;
			default:
				throw new AdempiereException("Unknown notification type: " + notificationType);
		}

	}
}

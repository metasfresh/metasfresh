package org.adempiere.user.api;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

@Value
public class UserNotificationsGroup
{
	public static final UserNotificationsGroupBuilder prepareDefault()
	{
		return builder().groupInternalName(DEFAULT_GroupInternalName);
	}

	private static NotificationGroupName DEFAULT_GroupInternalName = NotificationGroupName.of("default");

	private final NotificationGroupName groupInternalName;
	private final Set<NotificationType> notificationTypes;
	private final int userInChargeId;

	@Builder
	private UserNotificationsGroup(
			@NonNull final NotificationGroupName groupInternalName,
			@Singular final Set<NotificationType> notificationTypes,
			final int userInChargeId)
	{
		this.groupInternalName = groupInternalName;
		this.notificationTypes = ImmutableSet.copyOf(notificationTypes);
		this.userInChargeId = userInChargeId > 0 ? userInChargeId : -1;
	}

	public boolean isUserInChargeSet()
	{
		return userInChargeId > 0;
	}

	public boolean isNotifyUserInCharge()
	{
		return notificationTypes.contains(NotificationType.NotifyUserInCharge);
	}

	public boolean isNotifyByEMail()
	{
		return notificationTypes.contains(NotificationType.EMail);
	}

	public boolean isNotifyByInternalMessage()
	{
		return notificationTypes.contains(NotificationType.Notice);
	}

	public boolean hasAnyNotificationTypesExceptUserInCharge()
	{
		return hasAnyNotificationTypesExcept(NotificationType.NotifyUserInCharge);
	}

	public boolean hasAnyNotificationTypesExcept(final NotificationType typeToExclude)
	{
		final int notificationTypesCount = notificationTypes.size();
		if (notificationTypesCount <= 0)
		{
			return false;
		}
		else if (notificationTypesCount == 1)
		{
			return !notificationTypes.contains(typeToExclude);
		}
		else // notificationTypesCount > 1
		{
			return true;
		}
	}

}

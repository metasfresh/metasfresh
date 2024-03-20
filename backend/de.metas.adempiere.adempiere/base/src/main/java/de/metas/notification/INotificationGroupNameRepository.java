package de.metas.notification;

import de.metas.notification.impl.NotificationGroupId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;

import java.util.Set;

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

public interface INotificationGroupNameRepository extends ISingletonService
{
	NotificationGroupName getById(int notificationGroupId);

	NotificationGroupId getNotificationGroupId(final NotificationGroupName notificationGroupName);

	Set<NotificationGroupName> getAll();

	UserId getDeadletterUserId(NotificationGroupName notificationGroupName);

	boolean isNotifyOrgBpUsersOnly(NotificationGroupName notificationGroupName);
}

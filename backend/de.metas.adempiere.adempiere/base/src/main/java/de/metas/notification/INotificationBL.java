package de.metas.notification;

import java.util.List;

import de.metas.notification.spi.IRecordTextProvider;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.ISingletonService;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

public interface INotificationBL extends ISingletonService
{
	NotificationSenderTemplate newNotificationSender();

	void send(UserNotificationRequest request);

	void sendAfterCommit(UserNotificationRequest request);

	void sendAfterCommit(List<UserNotificationRequest> requests);

	void addCtxProvider(IRecordTextProvider ctxProvider);

	void setDefaultCtxProvider(IRecordTextProvider defaultCtxProvider);

	@NonNull
	UserNotificationsConfig getUserNotificationsConfig(UserId adUserId);

	RoleNotificationsConfig getRoleNotificationsConfig(RoleId adRoleId);
}

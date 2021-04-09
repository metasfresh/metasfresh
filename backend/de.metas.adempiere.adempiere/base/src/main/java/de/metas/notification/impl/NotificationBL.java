package de.metas.notification.impl;

import java.util.List;

import de.metas.notification.INotificationBL;
import de.metas.notification.IRoleNotificationsConfigRepository;
import de.metas.notification.IUserNotificationsConfigRepository;
import de.metas.notification.NotificationSenderTemplate;
import de.metas.notification.RoleNotificationsConfig;
import de.metas.notification.UserNotificationRequest;
import de.metas.notification.UserNotificationsConfig;
import de.metas.notification.spi.IRecordTextProvider;
import de.metas.notification.spi.impl.CompositeRecordTextProvider;
import de.metas.security.RoleId;
import de.metas.user.UserId;
import de.metas.util.Services;
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

public class NotificationBL implements INotificationBL
{
	private final CompositeRecordTextProvider ctxProviders = new CompositeRecordTextProvider();

	@Override
	public NotificationSenderTemplate newNotificationSender()
	{
		final NotificationSenderTemplate sender = new NotificationSenderTemplate();
		sender.setRecordTextProvider(ctxProviders);
		return sender;
	}

	@Override
	public void sendAfterCommit(@NonNull final UserNotificationRequest request)
	{
		newNotificationSender().sendAfterCommit(request);
	}

	@Override
	public void sendAfterCommit(@NonNull final List<UserNotificationRequest> requests)
	{
		if(requests.isEmpty())
		{
			return;
		}

		newNotificationSender().sendAfterCommit(requests);
	}

	@Override
	public void send(@NonNull final UserNotificationRequest request)
	{
		newNotificationSender().send(request);
	}

	@Override
	public void addCtxProvider(final IRecordTextProvider ctxProvider)
	{
		ctxProviders.addCtxProvider(ctxProvider);
	}

	@Override
	public void setDefaultCtxProvider(final IRecordTextProvider defaultCtxProvider)
	{
		ctxProviders.setDefaultCtxProvider(defaultCtxProvider);
	}

	@Override
	public UserNotificationsConfig getUserNotificationsConfig(final UserId adUserId)
	{
		return Services.get(IUserNotificationsConfigRepository.class).getByUserId(adUserId);
	}

	@Override
	public RoleNotificationsConfig getRoleNotificationsConfig(final RoleId adRoleId)
	{
		return Services.get(IRoleNotificationsConfigRepository.class).getByRoleId(adRoleId);
	}

}

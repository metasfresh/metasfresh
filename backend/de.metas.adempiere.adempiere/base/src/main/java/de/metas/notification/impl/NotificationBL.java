package de.metas.notification.impl;

import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;
import de.metas.notification.IRoleNotificationsConfigRepository;
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
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import java.util.List;

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
	private static final Logger logger = LogManager.getLogger(NotificationBL.class);
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
		try
		{
			newNotificationSender().sendAfterCommit(request);
		}
		catch (Exception ex)
		{
			logger.warn("Failed sending notification: {}", request, ex);
		}
	}

	@Override
	public void sendAfterCommit(@NonNull final List<UserNotificationRequest> requests)
	{
		try
		{
			if (requests.isEmpty())
			{
				return;
			}

			newNotificationSender().sendAfterCommit(requests);
		}
		catch (Exception ex)
		{
			logger.warn("Failed sending notifications: {}", requests, ex);
		}
	}

	@Override
	public void send(@NonNull final UserNotificationRequest request)
	{
		try
		{
			newNotificationSender().send(request);
		}
		catch (Exception ex)
		{
			logger.warn("Failed sending notification: {}", request, ex);
		}
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
		//to avoid cyclic dependency
		return SpringContextHolder.instance.getBean(UserNotificationsConfigService.class).getByUserId(adUserId);
	}

	@Override
	public RoleNotificationsConfig getRoleNotificationsConfig(final RoleId adRoleId)
	{
		return Services.get(IRoleNotificationsConfigRepository.class).getByRoleId(adRoleId);
	}

}

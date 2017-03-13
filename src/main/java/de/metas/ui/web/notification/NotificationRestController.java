package de.metas.ui.web.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.notification.json.JSONNotificationsList;
import de.metas.ui.web.session.UserSession;
import io.swagger.annotations.Api;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Api
@RestController
@RequestMapping(value = NotificationRestController.ENDPOINT)
public class NotificationRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/notifications";

	@Autowired
	private UserSession userSession;

	@Autowired
	private UserNotificationsService userNotificationsService;

	@RequestMapping(value = "/websocketEndpoint", method = RequestMethod.GET)
	public final String getWebsocketEndpoint()
	{
		userSession.assertLoggedIn();

		final int adUserId = userSession.getAD_User_ID();
		return userNotificationsService.getWebsocketEndpoint(adUserId);
	}

	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public JSONNotificationsList getNotifications(
			@RequestParam(name = "limit", defaultValue = "-1") final int limit //
	)
	{
		userSession.assertLoggedIn();

		final int adUserId = userSession.getAD_User_ID();
		final UserNotificationsList notifications = userNotificationsService.getNotifications(adUserId, limit);
		return JSONNotificationsList.of(notifications, userSession.getAD_Language());
	}

	@RequestMapping(value = "/unreadCount", method = RequestMethod.GET)
	public int getNotificationsUnreadCount()
	{
		userSession.assertLoggedIn();

		final int adUserId = userSession.getAD_User_ID();
		return userNotificationsService.getNotificationsUnreadCount(adUserId);
	}

	@RequestMapping(value = "/{notificationId}/read", method = RequestMethod.PUT)
	public void markAsRead(@PathVariable("notificationId") final String notificationId)
	{
		userSession.assertLoggedIn();

		final int adUserId = userSession.getAD_User_ID();
		userNotificationsService.markNotificationAsRead(adUserId, notificationId);
	}

	@RequestMapping(value = "/all/read", method = RequestMethod.PUT)
	public void markAllAsRead()
	{
		userSession.assertLoggedIn();

		final int adUserId = userSession.getAD_User_ID();
		userNotificationsService.markAllNotificationsAsRead(adUserId);
	}

}

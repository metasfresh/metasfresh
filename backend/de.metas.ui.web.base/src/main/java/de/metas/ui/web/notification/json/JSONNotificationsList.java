package de.metas.ui.web.notification.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

import de.metas.notification.UserNotificationsList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.GuavaCollectors;

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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class JSONNotificationsList
{
	public static final JSONNotificationsList EMPTY = new JSONNotificationsList();

	public static final JSONNotificationsList of(final UserNotificationsList notifications, final JSONOptions jsonOpts)
	{
		if (notifications.isEmpty())
		{
			return EMPTY;
		}

		return new JSONNotificationsList(notifications, jsonOpts);
	}

	@JsonProperty("totalCount")
	private final int totalCount;
	@JsonProperty("unreadCount")
	private final int unreadCount;
	@JsonProperty("notifications")
	private final List<JSONNotification> notifications;

	private JSONNotificationsList(final UserNotificationsList notifications, final JSONOptions jsonOpts)
	{
		super();
		totalCount = notifications.getTotalCount();
		unreadCount = notifications.getTotalUnreadCount();
		this.notifications = notifications.getNotifications()
				.stream()
				.map(notification -> JSONNotification.of(notification, jsonOpts))
				.collect(GuavaCollectors.toImmutableList());
	}

	private JSONNotificationsList()
	{
		super();
		totalCount = 0;
		unreadCount = 0;
		notifications = ImmutableList.of();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("totalCount", totalCount)
				.add("unreadCount", unreadCount)
				.add("notifications", notifications)
				.toString();
	}
	
	public int getTotalCount()
	{
		return totalCount;
	}
	
	public int getUnreadCount()
	{
		return unreadCount;
	}

	public List<JSONNotification> getNotifications()
	{
		return notifications;
	}
}

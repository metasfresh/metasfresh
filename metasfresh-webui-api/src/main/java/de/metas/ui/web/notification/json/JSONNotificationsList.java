package de.metas.ui.web.notification.json;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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
	public static final JSONNotificationsList EMPTY = new JSONNotificationsList(ImmutableList.of());

	public static final JSONNotificationsList of(final List<JSONNotification> notifications)
	{
		if (notifications == null || notifications.isEmpty())
		{
			return EMPTY;
		}
		return new JSONNotificationsList(notifications);
	}

	@JsonProperty("notifications")
	private final List<JSONNotification> notifications;

	private JSONNotificationsList(final List<JSONNotification> notifications)
	{
		super();
		this.notifications = ImmutableList.copyOf(notifications);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("notifications", notifications)
				.toString();
	}

	public List<JSONNotification> getNotifications()
	{
		return notifications;
	}
}

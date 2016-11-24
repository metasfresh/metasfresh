package de.metas.ui.web.notification.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

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

/**
 * Websocket notification event.
 *
 *
 * The JSON notification event will have following structure:
 *
 * <pre>
 * 	{
 * 		eventType: "New" | "Read"
 * 
 * 		notificationId: <the notification id>
 * 
 * 		// The actual notification.
 *		// NOTE: this field is optional and it will be present only when it makes sense (i.e. when eventType=New).
 * 		notification: {
 * 			// See Swagger for JSON notification structure.
 *		}
 *
 *		unreadCount: current number of notifications which are unread
 * }
 * </pre>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONNotificationEvent implements Serializable
{
	public static final JSONNotificationEvent eventNew(final JSONNotification notification, final int unreadCount)
	{
		return new JSONNotificationEvent(EventType.New, notification.getId(), notification, unreadCount);
	}

	public static final JSONNotificationEvent eventRead(final String notificationId, final int unreadCount)
	{
		final JSONNotification notification = null;
		return new JSONNotificationEvent(EventType.Read, notificationId, notification, unreadCount);
	}

	public static enum EventType
	{
		New, Read
	};

	@JsonProperty("eventType")
	private final EventType eventType;

	@JsonProperty("notificationId")
	private final String notificationId;

	@JsonProperty("notification")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	private final JSONNotification notification;

	@JsonProperty("unreadCount")
	@JsonInclude(JsonInclude.Include.NON_ABSENT)
	private final Integer unreadCount;

	private JSONNotificationEvent(final EventType eventType, final String notificationId, final JSONNotification notification, final Integer unreadCount)
	{
		super();
		this.eventType = eventType;
		this.notificationId = notificationId;
		this.notification = notification;
		this.unreadCount = unreadCount;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("eventType", eventType)
				.add("notificationId", notificationId)
				.add("notification", notification)
				.toString();
	}

	public EventType getEventType()
	{
		return eventType;
	}

	public String getNotificationId()
	{
		return notificationId;
	}

	public JSONNotification getNotification()
	{
		return notification;
	}

	public Integer getUnreadCount()
	{
		return unreadCount;
	}
}

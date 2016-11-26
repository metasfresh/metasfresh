package de.metas.ui.web.notification.json;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.notification.UserNotification;
import de.metas.ui.web.window.datatypes.json.JSONDate;

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

@SuppressWarnings("serial")
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public final class JSONNotification implements Serializable
{
	public static final JSONNotification of(final UserNotification notification, final String adLanguage)
	{
		return new JSONNotification(notification, adLanguage);
	}

	@JsonProperty("id")
	private final String id;
	@JsonProperty("message")
	private final String message;
	@JsonProperty("timestamp")
	private final String timestamp;
	@JsonProperty("important")
	private final boolean important;
	@JsonProperty("read")
	private final boolean read;
	
	@JsonProperty("target")
	private final JSONNotificationTarget target;

	private JSONNotification(final UserNotification notification, final String adLanguage)
	{
		super();

		id = notification.getId();
		message = notification.getMessage(adLanguage);
		timestamp = JSONDate.toJson(notification.getTimestamp());
		important = notification.isImportant();
		read = notification.isRead();
		
		target = JSONNotificationTarget.of(notification);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("message", message)
				.add("timestamp", timestamp)
				.add("important", important)
				.add("read", read)
				.toString();
	}

	public String getId()
	{
		return id;
	}

	public String getMessage()
	{
		return message;
	}

	public String getTimestamp()
	{
		return timestamp;
	}

	public boolean isImportant()
	{
		return important;
	}

	public boolean isRead()
	{
		return read;
	}
}

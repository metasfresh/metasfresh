package de.metas.ui.web.notification.json;

import java.io.Serializable;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.event.Event;
import de.metas.ui.web.window.datatypes.json.JSONDate;
import de.metas.ui.web.window.datatypes.json.JSONOptions;

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
public class JSONNotification implements Serializable
{
	public static final JSONNotification of(final Event event, final JSONOptions jsonOpts)
	{
		return new JSONNotification(event, jsonOpts);
	}

	@JsonProperty("id")
	private final String id;
	@JsonProperty("message")
	private final String message;
	@JsonProperty("timestamp")
	private final String timestamp;
	@JsonProperty("important")
	private final boolean important;

	private JSONNotification(final Event event, final JSONOptions jsonOpts)
	{
		super();

		id = event.getId();
		message = buildMessage(event, jsonOpts);
		timestamp = JSONDate.toJson(new java.util.Date()); // TODO: introduce Event.getTimestamp()
		important = false;
	}

	private static String buildMessage(final Event event, final JSONOptions jsonOpts)
	{
		// TODO: implement; see org.adempiere.ui.notifications.SwingEventNotifierFrame.toNotificationItem(Event)

		//
		// Build detail message
		final StringBuilder detailBuf = new StringBuilder();

		// Add plain detail if any
		final String detailPlain = event.getDetailPlain();
		if (!Check.isEmpty(detailPlain, true))
		{
			detailBuf.append(detailPlain.trim());
		}

		// Translate, parse and add detail (AD_Message).
		final String detailADMessage = event.getDetailADMessage();
		if (!Check.isEmpty(detailADMessage, true))
		{
			final String detailTrl = Services.get(IMsgBL.class)
					.getTranslatableMsgText(detailADMessage)
					.translate(jsonOpts.getAD_Language());
			if (!Check.isEmpty(detailTrl, true))
			{
				if (detailBuf.length() > 0)
				{
					detailBuf.append("\n");
				}
				detailBuf.append(detailTrl);
			}
		}

		return detailBuf.toString();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("message", message)
				.add("timestamp", timestamp)
				.add("important", important)
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
}

package de.metas.notification;

import org.adempiere.exceptions.AdempiereException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.event.Event;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public class UserNotificationUtils
{
	private static final String EVENT_PARAM_Notification = "userNotification";

	private static final ObjectMapper jsonMapper;
	static
	{
		jsonMapper = new ObjectMapper();
		jsonMapper.findAndRegisterModules();
	}

	public static Event toEvent(@NonNull final UserNotification notification)
	{
		final String notificationAsJson;
		try
		{
			notificationAsJson = jsonMapper.writeValueAsString(notification);
		}
		catch (final JsonProcessingException ex)
		{
			throw new AdempiereException("Failed converting notification to JSON", ex)
					.setParameter("notification", notification);
		}

		return Event.builder()
				.addRecipient_User_ID(notification.getRecipientUserId())
				.putProperty(EVENT_PARAM_Notification, notificationAsJson)
				.build();
	}

	public static UserNotification toUserNotification(@NonNull final Event event)
	{
		final String notificationAsJson = event.getPropertyAsString(EVENT_PARAM_Notification);
		try
		{
			return jsonMapper.readValue(notificationAsJson, UserNotification.class);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed converting event to " + UserNotification.class + ": " + event, ex);
		}
	}
}

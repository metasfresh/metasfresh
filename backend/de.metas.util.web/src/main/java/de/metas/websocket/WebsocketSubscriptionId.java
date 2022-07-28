package de.metas.websocket;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
public final class WebsocketSubscriptionId
{
	public static WebsocketSubscriptionId of(
			@NonNull final WebsocketSessionId sessionId,
			@NonNull final String subscriptionId)
	{
		return new WebsocketSubscriptionId(sessionId, subscriptionId);
	}

	@Getter
	private final WebsocketSessionId sessionId;
	private final String subscriptionId;

	private WebsocketSubscriptionId(
			@NonNull final WebsocketSessionId sessionId,
			@NonNull final String subscriptionId)
	{
		Check.assumeNotEmpty(subscriptionId, "subscriptionId is not empty");
		this.sessionId = sessionId;
		this.subscriptionId = subscriptionId;
	}

	/**
	 * @deprecated please use {@link #getAsString()}
	 */
	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return sessionId.getAsString() + "/" + subscriptionId;
	}

	public boolean isMatchingSessionId(final WebsocketSessionId sessionId)
	{
		return Objects.equals(this.sessionId, sessionId);
	}
}

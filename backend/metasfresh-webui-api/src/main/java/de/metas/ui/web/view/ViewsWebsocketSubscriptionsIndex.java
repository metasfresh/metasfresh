package de.metas.ui.web.view;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import de.metas.ui.web.websocket.WebSocketConfig;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
final class ViewsWebsocketSubscriptionsIndex
{
	private final ConcurrentHashMap<ViewId, SessionIds> sessionIdsByViewId = new ConcurrentHashMap<>();

	public void onTopicSubscribed(final String topicName, final String sessionId)
	{
		final ViewId viewId = extractViewIdOrNull(topicName);
		if (viewId == null)
		{
			return;
		}

		sessionIdsByViewId.compute(
				viewId,
				(k, existingSessionIds) -> SessionIds.addSessionId(existingSessionIds, sessionId));
	}

	public void onTopicUnsubscribed(final String topicName, final String sessionId)
	{
		final ViewId viewId = extractViewIdOrNull(topicName);
		if (viewId == null)
		{
			return;
		}

		sessionIdsByViewId.compute(
				viewId,
				(k, existingSessionIds) -> SessionIds.removeSessionId(existingSessionIds, sessionId));
	}

	private ViewId extractViewIdOrNull(final String topicName)
	{
		final String viewIdStr = WebSocketConfig.extractViewIdOrNull(topicName);
		return viewIdStr != null
				? ViewId.fromJson(viewIdStr)
				: null;
	}

	public boolean hasSubscriptions(final ViewId viewId)
	{
		final SessionIds sessionIds = sessionIdsByViewId.get(viewId);
		return !sessionIds.isEmpty();
	}

	@EqualsAndHashCode
	@ToString
	private static final class SessionIds
	{
		@NonNull
		public static SessionIds addSessionId(
				@Nullable final SessionIds sessionIds,
				@NonNull final String sessionIdToAdd)
		{
			final SessionIds sessionIdsEffective = sessionIds != null ? sessionIds : new SessionIds();
			sessionIdsEffective.addSessionId(sessionIdToAdd);
			return sessionIdsEffective;
		}

		@Nullable
		public static SessionIds removeSessionId(
				@Nullable final SessionIds sessionIds,
				@NonNull final String sessionIdToRemove)
		{
			if (sessionIds == null)
			{
				return null;
			}

			sessionIds.removeSessionId(sessionIdToRemove);
			return !sessionIds.isEmpty() ? sessionIds : null;
		}

		private final HashSet<String> sessionIds = new HashSet<>();

		public synchronized void addSessionId(@NonNull final String sessionId)
		{
			sessionIds.add(sessionId);
		}

		public synchronized void removeSessionId(@NonNull final String sessionId)
		{
			sessionIds.remove(sessionId);
		}

		public synchronized boolean isEmpty()
		{
			return sessionIds.isEmpty();
		}
	}
}

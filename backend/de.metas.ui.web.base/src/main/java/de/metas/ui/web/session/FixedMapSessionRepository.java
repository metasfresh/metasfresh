package de.metas.ui.web.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nullable;

import org.slf4j.Logger;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.events.SessionCreatedEvent;
import org.springframework.session.events.SessionDeletedEvent;
import org.springframework.session.events.SessionExpiredEvent;

import com.google.common.base.Stopwatch;

import de.metas.logging.LogManager;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Similar with {@link MapSessionRepository} but it's also firing session created/destroyed events.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString(of = { "defaultMaxInactiveInterval" })
/* package */class FixedMapSessionRepository implements SessionRepository<ExpiringSession>
{
	private static final Logger logger = LogManager.getLogger(FixedMapSessionRepository.class);

	private final Map<String, ExpiringSession> sessions = new ConcurrentHashMap<>();

	private final ApplicationEventPublisher applicationEventPublisher;
	private final Integer defaultMaxInactiveInterval;

	@Builder
	private FixedMapSessionRepository(
			@NonNull final ApplicationEventPublisher applicationEventPublisher,
			@Nullable final Integer defaultMaxInactiveInterval)
	{
		this.applicationEventPublisher = applicationEventPublisher;
		this.defaultMaxInactiveInterval = defaultMaxInactiveInterval;
	}

	@Override
	public void save(final ExpiringSession session)
	{
		sessions.put(session.getId(), new MapSession(session));
	}

	@Override
	public ExpiringSession getSession(final String id)
	{
		final ExpiringSession saved = sessions.get(id);
		if (saved == null)
		{
			return null;
		}
		if (saved.isExpired())
		{
			final boolean expired = true;
			deleteAndFireEvent(saved.getId(), expired);
			return null;
		}

		return new MapSession(saved);
	}

	@Override
	public void delete(final String id)
	{
		final boolean expired = false;
		deleteAndFireEvent(id, expired);
	}

	private void deleteAndFireEvent(final String id, boolean expired)
	{
		final ExpiringSession deletedSession = sessions.remove(id);

		// Fire event
		if (deletedSession != null)
		{
			if (expired)
			{
				applicationEventPublisher.publishEvent(new SessionExpiredEvent(this, id));
			}
			else
			{
				applicationEventPublisher.publishEvent(new SessionDeletedEvent(this, id));
			}
		}
	}

	@Override
	public ExpiringSession createSession()
	{
		final ExpiringSession result = new MapSession();
		if (defaultMaxInactiveInterval != null)
		{
			result.setMaxInactiveIntervalInSeconds(defaultMaxInactiveInterval);
		}

		// Fire event
		applicationEventPublisher.publishEvent(new SessionCreatedEvent(this, result.getId()));

		return result;
	}

	public void purgeExpiredSessionsNoFail()
	{
		try
		{
			purgeExpiredSessions();
		}
		catch (final Throwable ex)
		{
			logger.warn("Failed purging expired sessions. Ignored.", ex);
		}
	}

	public void purgeExpiredSessions()
	{
		final Stopwatch stopwatch = Stopwatch.createStarted();
		int countExpiredSessions = 0;

		final List<ExpiringSession> sessionsToCheck = new ArrayList<>(sessions.values());
		for (final ExpiringSession session : sessionsToCheck)
		{
			if (session.isExpired())
			{
				deleteAndFireEvent(session.getId(), true /* expired */);
				countExpiredSessions++;
			}
		}

		logger.debug("Purged {}/{} expired sessions in {}", countExpiredSessions, sessionsToCheck.size(), stopwatch);
	}
}

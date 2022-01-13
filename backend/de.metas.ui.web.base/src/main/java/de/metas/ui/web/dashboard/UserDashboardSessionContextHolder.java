/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.dashboard;

import de.metas.logging.LogManager;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.session.json.WebuiSessionId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserDashboardSessionContextHolder
{
	private static final Logger logger = LogManager.getLogger(UserDashboardSessionContextHolder.class);
	private final ConcurrentHashMap<WebuiSessionId, KPIDataContext> sessionContexts = new ConcurrentHashMap<>();

	public KPIDataContext getSessionContext(@NonNull final WebuiSessionId sessionId)
	{
		final KPIDataContext context = sessionContexts.get(sessionId);
		if (context == null)
		{
			throw new AdempiereException("No session context found for session ID: `" + sessionId + "`");
		}
		return context;
	}

	public void putSessionContext(
			@NonNull final WebuiSessionId sessionId,
			@NonNull final KPIDataContext context)
	{
		sessionContexts.put(sessionId, context);
		logger.debug("putSessionContext for sessionId={}: {}", sessionId, context);
	}

	public void clearSessionContext(@NonNull final WebuiSessionId sessionId)
	{
		sessionContexts.remove(sessionId);
		logger.debug("clearSessionContext for sessionId={}", sessionId);
	}
}

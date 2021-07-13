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

package de.metas.ui.web.dashboard.websocket;

import de.metas.ui.web.dashboard.UserDashboardDataService;
import de.metas.ui.web.dashboard.UserDashboardSessionContextHolder;
import de.metas.ui.web.kpi.data.KPIDataContext;
import de.metas.ui.web.session.json.WebuiSessionId;
import de.metas.ui.web.websocket.WebSocketProducerFactory;
import de.metas.ui.web.websocket.WebsocketTopicName;
import de.metas.ui.web.websocket.WebsocketTopicNames;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;

@Component
public class UserDashboardWebsocketProducerFactory implements WebSocketProducerFactory
{
	private final UserDashboardDataService dashboardDataService;
	private final UserDashboardSessionContextHolder contextHolder;

	public UserDashboardWebsocketProducerFactory(
			@NonNull final UserDashboardDataService dashboardDataService,
			@NonNull final UserDashboardSessionContextHolder contextHolder)
	{
		this.dashboardDataService = dashboardDataService;
		this.contextHolder = contextHolder;
	}

	@Override
	public String getTopicNamePrefix()
	{
		return WebsocketTopicNames.TOPIC_Dashboard;
	}

	@Override
	public UserDashboardWebsocketProducer createProducer(final WebsocketTopicName topicName)
	{
		final WebuiSessionId sessionId = extractSessionId(topicName);
		if (sessionId == null)
		{
			throw new AdempiereException("Invalid websocket topic name: " + topicName);
		}

		final KPIDataContext kpiDataContext = contextHolder.getSessionContext(sessionId);

		return UserDashboardWebsocketProducer.builder()
				.dashboardDataService(dashboardDataService)
				.websocketTopicName(topicName)
				.kpiDataContext(kpiDataContext)
				.build();
	}

	@Nullable
	public static WebuiSessionId extractSessionId(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();

		if (topicNameString.startsWith(WebsocketTopicNames.TOPIC_Dashboard))
		{
			final String sessionId = topicNameString.substring(WebsocketTopicNames.TOPIC_Dashboard.length() + 1).trim();
			return WebuiSessionId.ofNullableString(sessionId);
		}
		else
		{
			return null;
		}
	}

	public static WebsocketTopicName createWebsocketTopicName(@NonNull final WebuiSessionId sessionId)
	{
		return WebsocketTopicName.ofString(WebsocketTopicNames.TOPIC_Dashboard + "/" + sessionId.getAsString());
	}
}

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
import de.metas.ui.web.dashboard.UserDashboardId;
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

	public UserDashboardWebsocketProducerFactory(
			@NonNull final UserDashboardDataService dashboardDataService)
	{
		this.dashboardDataService = dashboardDataService;
	}

	public static WebsocketTopicName createWebsocketTopicName(@NonNull final UserDashboardId dashboardId)
	{
		return WebsocketTopicName.ofString(WebsocketTopicNames.TOPIC_Dashboard + "/" + dashboardId.getRepoId());
	}

	@Nullable
	public static UserDashboardId extractUserDashboardId(@NonNull final WebsocketTopicName topicName)
	{
		final String topicNameString = topicName.getAsString();

		if (topicNameString.startsWith(WebsocketTopicNames.TOPIC_Dashboard))
		{
			final String idAsString = topicNameString.substring(WebsocketTopicNames.TOPIC_Dashboard.length() + 1).trim();
			if (idAsString.isEmpty())
			{
				return null;
			}

			return UserDashboardId.ofRepoId(Integer.parseInt(idAsString));
		}
		else
		{
			return null;
		}
	}

	@Override
	public String getTopicNamePrefix()
	{
		return WebsocketTopicNames.TOPIC_Dashboard;
	}

	@Override
	public UserDashboardWebsocketProducer createProducer(final WebsocketTopicName topicName)
	{
		final UserDashboardId userDashboardId = extractUserDashboardId(topicName);
		if (userDashboardId == null)
		{
			throw new AdempiereException("Invalid websocket topic name: " + topicName);
		}

		return UserDashboardWebsocketProducer.builder()
				.dashboardDataService(dashboardDataService)
				.userDashboardId(userDashboardId)
				.build();
	}
}

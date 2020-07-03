package de.metas.ui.web.websocket;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

public class WebSocketConfigTest
{
	@Test
	public void buildViewNotificationsTopicName_then_extractIt()
	{
		buildViewNotificationsTopicName_then_extractIt("view1");
	}

	private void buildViewNotificationsTopicName_then_extractIt(final String viewId)
	{
		final String topicName = WebSocketConfig.buildViewNotificationsTopicName(viewId);
		assertThat(WebSocketConfig.extractViewIdOrNull(topicName)).isEqualTo(viewId);
	}

	@Nested
	public class extractViewIdOrNull
	{
		@Test
		public void invalidTopicNames()
		{
			assertThat(WebSocketConfig.extractViewIdOrNull(WebSocketConfig.TOPIC_View)).isNull();
			assertThat(WebSocketConfig.extractViewIdOrNull(WebSocketConfig.TOPIC_View + "/")).isNull();

			assertThat(WebSocketConfig.extractViewIdOrNull("/nonMatching/topic")).isNull();
		}
	}
}

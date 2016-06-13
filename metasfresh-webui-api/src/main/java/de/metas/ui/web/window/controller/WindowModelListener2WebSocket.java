package de.metas.ui.web.window.controller;

import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.google.common.eventbus.Subscribe;

import de.metas.logging.LogManager;
import de.metas.ui.web.config.WebSocketConfig;

/*
 * #%L
 * metasfresh-webui-vaadin
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

public class WindowModelListener2WebSocket
{
	private static final Logger logger = LogManager.getLogger(WindowModelListener2WebSocket.class);

	@Autowired
	private SimpMessagingTemplate template;

	// private final int windowNo;
	private final String windowTopicName;

	public WindowModelListener2WebSocket(final int windowNo)
	{
		super();
		Env.autowireBean(this);

		// this.windowNo = windowNo;
		this.windowTopicName = WebSocketConfig.buildWindowTopicName(windowNo);
	}

	@Subscribe
	public void onEvent(final Object event)
	{
		logger.debug("Got event: {}", event);

		try
		{
			template.convertAndSend(windowTopicName, event);
		}
		catch (Exception e)
		{
			logger.warn("Failed to dispach event to {}: {}", windowTopicName, event, e);
		}
	}

}

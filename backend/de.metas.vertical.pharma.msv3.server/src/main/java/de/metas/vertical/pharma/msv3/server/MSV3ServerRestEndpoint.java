package de.metas.vertical.pharma.msv3.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.classic.Level;
import de.metas.vertical.pharma.msv3.server.peer.service.MSV3ServerPeerService;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

@RestController
@RequestMapping(MSV3ServerConstants.REST_ENDPOINT_PATH)
public class MSV3ServerRestEndpoint
{
	private static final Logger ROOT_LOGGER = LoggerFactory.getLogger(MSV3ServerRestEndpoint.class.getPackage().getName());

	@Autowired
	private MSV3ServerPeerService msv3ServerPeerService;

	@GetMapping("/requestUpdateFromServerPeer")
	public void requestUpdateFromServerPeer()
	{
		msv3ServerPeerService.requestAllUpdates();
	}

	@PutMapping("/logLevel")
	public void setLoggerLevel(@RequestBody final String logLevelStr)
	{
		final Level level = toSLF4JLevel(logLevelStr);
		getSLF4JRootLogger().setLevel(level);
	}

	@GetMapping("/logLevel")
	public String getLoggerLevel()
	{
		Level level = getSLF4JRootLogger().getLevel();
		return level != null ? level.toString() : null;
	}

	private ch.qos.logback.classic.Logger getSLF4JRootLogger()
	{
		return (ch.qos.logback.classic.Logger)ROOT_LOGGER;
	}

	private static Level toSLF4JLevel(final String logLevelStr)
	{
		if (logLevelStr == null)
		{
			return null;
		}

		return Level.toLevel(logLevelStr.trim());
	}
}

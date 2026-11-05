package de.metas.vertical.pharma.msv3.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;

import de.metas.vertical.pharma.msv3.server.peer.protocol.MSV3PeerAuthToken;
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

@SpringBootApplication
public class Application
{
	private static final Logger logger = LoggerFactory.getLogger(Application.class);

	@Value("${msv3server.startup.requestAllData:false}")
	private boolean requestAllDataOnStartup;

	@Value("${msv3server.startup.requestConfigData:true}")
	private boolean requestConfigDataOnStartup;

	// @Lazy breaks a bean cycle that Spring Boot 2.6+ no longer tolerates by default
	// (application -> this field -> MSV3ServerPeerService -> MSV3PeerAuthToken, a @Bean defined
	// on this very class). The service is only used from requestDataOnStartup() at startup, so a
	// lazy proxy resolves the real bean on first use once the context is built.
	@Lazy
	@Autowired
	private MSV3ServerPeerService msv3ServerPeerService;

	public static void main(final String[] args)
	{
		new SpringApplicationBuilder(Application.class)
				.headless(true)
				.web(WebApplicationType.SERVLET)
				.run(args);
	}

	@Bean
	public MSV3PeerAuthToken authTokenString(@Value("${msv3server.peer.authToken:}") final String authTokenStringValue)
	{
		if (authTokenStringValue == null || authTokenStringValue.trim().isEmpty())
		{
			// a token is needed on the receiver side, even if it's not valid
			return MSV3PeerAuthToken.TOKEN_NOT_SET;
		}

		return MSV3PeerAuthToken.of(authTokenStringValue);
	}

	// Run the startup data request on ApplicationReadyEvent rather than InitializingBean, so it
	// fires AFTER the context is fully built. afterPropertiesSet() used to run while the
	// 'application' bean was still in creation; with the @Lazy field that broke the Spring Boot
	// 2.6+ bean cycle, resolving the lazy proxy that early would fail. At ApplicationReadyEvent the
	// proxy resolves the real MSV3ServerPeerService and the request actually executes.
	@EventListener(ApplicationReadyEvent.class)
	public void requestDataOnStartup()
	{
		try
		{
			if (requestAllDataOnStartup)
			{
				msv3ServerPeerService.requestAllUpdates();
			}
			else if (requestConfigDataOnStartup)
			{
				msv3ServerPeerService.requestConfigUpdates();
			}
		}
		catch (Exception ex)
		{
			logger.warn("Error while requesting ALL updates. Skipped.", ex);
		}
	}
}

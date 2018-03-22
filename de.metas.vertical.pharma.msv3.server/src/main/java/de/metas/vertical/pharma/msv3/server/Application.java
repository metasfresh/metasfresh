package de.metas.vertical.pharma.msv3.server;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

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
public class Application implements InitializingBean
{
	@Value("${msv3server.startup.requestAllData:false}")
	private boolean requestAllDataOnStartup;

	@Autowired
	private MSV3ServerPeerService msv3ServerPeerService;

	public static void main(final String[] args)
	{
		new SpringApplicationBuilder(Application.class)
				.headless(true)
				.web(true)
				.run(args);
	}

	@Bean
	public ObjectMapper jsonObjectMapper()
	{
		final ObjectMapper jsonObjectMapper = new ObjectMapper();
		jsonObjectMapper.findAndRegisterModules();
		return jsonObjectMapper;
	}

	@Override
	public void afterPropertiesSet()
	{
		if (requestAllDataOnStartup)
		{
			msv3ServerPeerService.requestAllUpdates();
		}
	}
}

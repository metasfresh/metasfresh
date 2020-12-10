package de.metas.procurement.webui.sync;

import de.metas.common.procurement.sync.IAgentSync;
import de.metas.common.procurement.sync.IServerSync;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * #%L
 * de.metas.procurement.webui
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

/**
 * Here we put up both ther server endpoint of {@link IAgentSync} which metasfresh uses to communicate with us and<br>
 * the client endpoint of {@link IServerSync} which we use to communicate with metasfresh.
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
@Configuration
public class SyncConfiguration
{
	private static final transient Logger logger = LoggerFactory.getLogger(SyncConfiguration.class);

	@Value("${mfprocurement.sync.mocked:false}")
	private boolean useMockedServer;

	/**
	 * Creates the {@link IServerSync} client endpoint which this application can use to talk to the metasfresh server.
	 */
	@Bean
	public IServerSync clientEndPoint()
	{
		if (useMockedServer)
		{
			logger.warn("Using mocked implementation for {}", IServerSync.class);
			return new MockedServerSync();
		}

		return new ProcurementWebServerSyncImpl();
	}




}

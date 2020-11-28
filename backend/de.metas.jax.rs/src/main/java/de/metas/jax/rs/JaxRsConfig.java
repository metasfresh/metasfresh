package de.metas.jax.rs;

import org.compiere.Adempiere.RunMode;
import org.compiere.util.Env;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.jms.JmsEmbeddedBrokerConfig;
import de.metas.logging.LogManager;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Configuration
@Profile(value = { Profiles.PROFILE_App, Profiles.PROFILE_SwingUI })
public class JaxRsConfig
{
	private static final Logger logger = LogManager.getLogger(JaxRsConfig.class);

	/**
	 * Register JAX-RS endpoints. The jmsInterceptor parameter is here to inform spring about the depdency.
	 */
	public JaxRsConfig(@NonNull final JmsEmbeddedBrokerConfig jmsInterceptor)
	{
		final IJaxRsBL jaxRsBL = Services.get(IJaxRsBL.class);

		final boolean serverMode = Ini.getRunMode() == RunMode.BACKEND;
		if (serverMode)
		{
			// in ServerEmbedded mode, we assume that a local JMS broker was already started by this module's AddOn implementation.
			logger.info("Creating JAX-RS server endpoints");

			// run in a dedicated thread,
			// because if no JMS broker is running because de.metas.jms.UseEmbeddedBroker='N' and no external broker is running,
			// then this method doesn't return
			final Thread thread = new Thread(() -> jaxRsBL.createServerEndPoints());
			thread.setName("IJaxRsBL.createServerEndPoints");
			thread.start();
		}

		if (!serverMode)
		{
			logger.info("Creating JAX-RS client endpoints");
			jaxRsBL.createClientEndPoints(Env.getCtx());
		}
	}

}

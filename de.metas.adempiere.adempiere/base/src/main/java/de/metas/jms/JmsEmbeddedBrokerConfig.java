package de.metas.jms;

import org.compiere.Adempiere.RunMode;
import org.compiere.db.CConnection;
import org.compiere.util.Ini;
import org.slf4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import de.metas.Profiles;
import de.metas.logging.LogManager;

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

/**
 * Module interceptor for the jms package.
 * <p>
 * Note: only the app server needs to provide JMS services (for procurement-webui and currently still for the swing client),
 * hence the {@link Profile} annotation.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Configuration
@Profile(value = { Profiles.PROFILE_App, Profiles.PROFILE_SwingUI })
public class JmsEmbeddedBrokerConfig
{
	private static final Logger logger = LogManager.getLogger(JmsEmbeddedBrokerConfig.class);

	/**
	 * Starts an embedded JMS broker if either
	 * <ul>
	 * <li>we are running on the backend and {@link JmsConstants#isUseEmbeddedBroker()} returns <code>true</code> or
	 * <li>we are running in embedded server mode, i.e. {@link CConnection#isServerEmbedded()} returns <code>true</code>.
	 * </ul>
	 */
	public JmsEmbeddedBrokerConfig()
	{
		startEmbeddedBrokerIfRequired();
	}

	/** Start embedded server if running in server mode or we were asked for embedded server */
	private void startEmbeddedBrokerIfRequired()
	{
		final boolean serverMode = Ini.getRunMode() == RunMode.BACKEND;
		if (serverMode && JmsConstants.isUseEmbeddedBroker())
		{
			logger.warn(
					"RunMode={}, IsUseEmbeddedBroker={}. Starting embedded JMS broker",
					Ini.getRunMode(),
					JmsConstants.isUseEmbeddedBroker());

			EmbeddedActiveMQBrokerService.INSTANCE.startEmbeddedBroker();
		}
	}

}

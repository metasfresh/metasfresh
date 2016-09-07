package de.metas.jms;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Ini;
import org.slf4j.Logger;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Module interceptor for the jms package.
 * <p>
 * This interceptor needs its own record in {@link org.compiere.model.I_AD_ModelValidator} and its own entitytype, because there shall never be an embedded broker when we run in minimal mode.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class JmsInterceptor extends AbstractModuleInterceptor
{
	private static final Logger logger = LogManager.getLogger(JmsInterceptor.class);

	
	/**
	 * Starts an embedded JMS broker if either
	 * <ul>
	 * <li>we are running on the backend and {@link JmsConstants#isUseEmbeddedBroker()} returns <code>true</code> or
	 * <li>we are running in embedded server mode, i.e. {@link CConnection#isServerEmbedded()} returns <code>true</code>.
	 * </ul>
	 */
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		startEmbeddedBrokerIfRequired();
	}

	/** Start embedded server if running in server mode or we were asked for embedded server */
	private void startEmbeddedBrokerIfRequired()
	{
		final boolean serverMode = Ini.getRunMode() == RunMode.BACKEND;
		if ((serverMode && JmsConstants.isUseEmbeddedBroker())
				|| CConnection.isServerEmbedded())
		{
			logger.warn(
					"RunMode={}, IsUseEmbeddedBroker={}, IsServerEmbedded={}. Starting embedded JMS broker",
					Ini.getRunMode(),
					JmsConstants.isUseEmbeddedBroker(),
					CConnection.isServerEmbedded());

			Services.get(IJMSService.class).startEmbeddedBroker();
		}
	}

}

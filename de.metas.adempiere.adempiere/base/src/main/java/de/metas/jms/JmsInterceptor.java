package de.metas.jms;

import java.util.logging.Level;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Ini;

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
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class JmsInterceptor extends AbstractModuleInterceptor
{
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

	/** Start embedded server if running in server mode and we were asked for embedded server */
	private void startEmbeddedBrokerIfRequired()
	{
		if ((Ini.getRunMode() == RunMode.BACKEND && JmsConstants.isUseEmbeddedBroker())
				|| CConnection.isServerEmbedded())
		{
			JmsConstants.getLogger().log(Level.WARNING,
					"RunMode={0}, IsUseEmbeddedBroker={1}, IsServerEmbedded={2}. Starting embedded JMS broker",
					Ini.getRunMode(),
					JmsConstants.isUseEmbeddedBroker(),
					CConnection.isServerEmbedded());

			Services.get(IJMSService.class).startEmbeddedBrocker();
		}
	}

}

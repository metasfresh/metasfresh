package de.metas.event;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.net.URI;
import java.util.logging.Level;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.util.Services;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.broker.TransportConnector;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Session;
import org.compiere.util.CLogger;
import org.compiere.util.Ini;

/**
 * Module activator which is initializing the {@link IEventBus} infrastructure.
 *
 * @author tsa
 *
 */
public final class EventBusAdempiereInterceptor extends AbstractModuleInterceptor
{
	public static final transient EventBusAdempiereInterceptor instance = new EventBusAdempiereInterceptor();

	private static final transient CLogger logger = EventBusConstants.getLogger();

	private EventBusAdempiereInterceptor()
	{
		super();
	}

	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		if (!EventBusConstants.isEnabled())
		{
			return;
		}

		startEmbeddedServerIfRequired();
	}

	/** Start embedded server if running in server mode and we were asked for embedded server */
	private void startEmbeddedServerIfRequired()
	{
		if (!Ini.isClient() && EventBusConstants.isUseEmbeddedBroker())
		{
			try
			{
				final BrokerService broker = new BrokerService();

				final String urlStr = EventBusConstants.getJmsURL();
				final TransportConnector connector = new TransportConnector();
				connector.setUri(new URI(urlStr));
				broker.addConnector(connector);
				broker.start();
				logger.log(Level.CONFIG, "JMS server started on  " + urlStr);
			}
			catch (final Exception e)
			{
				logger.log(Level.SEVERE, "Failed starting JMS server", e);
			}
		}
	}

	@Override
	public void onUserLogin(int AD_Org_ID, int AD_Role_ID, int AD_User_ID)
	{
		if (Ini.isClient())
		{
			Services.get(IEventBusFactory.class).initEventBussesWithGlobalListeners();
		}
	}

	@Override
	public void beforeLogout(final I_AD_Session session)
	{
		if (Ini.isClient())
		{
			Services.get(IEventBusFactory.class).destroyAllEventBusses();
		}
	}

}

package de.metas.jax.rs;

import org.compiere.Adempiere.RunMode;
import org.compiere.db.CConnection;
import org.compiere.db.CConnection.IStatusServiceEndPointProvider;
import org.compiere.util.Ini;

import de.metas.adempiere.addon.IAddOn;
import de.metas.session.jaxrs.IStatusService;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.jax.rs
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

public class AddOn implements IAddOn
{
	/**
	 * Provides {@link CConnection} with an {@link IStatusService} implementation which it can use to get the server status.
	 */
	@Override
	public void beforeConnection()
	{
		// make sure that CConnection can query the server
		CConnection.setStatusServiceEndPointProvider(new IStatusServiceEndPointProvider()
		{
			@Override
			public IStatusService provide(final CConnection cConnection)
			{
				if (Ini.getRunMode() == RunMode.BACKEND)
				{
					// just return the "local" implementation, don't create a client endpoint, because
					// often CConnection calls methods call other methods of themselves via this service.
					// If the called instance is actually the same that makes the call (i.e. not on another machine)
					// there are locking issues
					return Services.get(IStatusService.class);
				}

				final IJaxRsBL jaxRsBL = Services.get(IJaxRsBL.class);

				final int timeOutMillis = 2000; // only let the user wait two seconds if there is no response
				final CreateEndpointRequest<IStatusService> request = CreateEndpointRequest
						.builder(IStatusService.class)
						.setCconnection(cConnection)
						.setTimeoutMillis(timeOutMillis)
						.build();
				return jaxRsBL.createClientEndpointsProgramatically(request).get(0);
			}
		});
	}
}

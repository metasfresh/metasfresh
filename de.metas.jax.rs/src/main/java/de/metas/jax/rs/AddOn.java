package de.metas.jax.rs;

import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.db.CConnection;
import org.compiere.db.CConnection.IStatusServiceEndPointProvider;
import org.compiere.util.Ini;

import de.metas.adempiere.addon.IAddOn;
import de.metas.session.jaxrs.IStatusService;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AddOn implements IAddOn
{

	@Override
	public void beforeConnection()
	{
//		if(CConnection.isServerEmbedded())
//		{
//			// provide an embedded endpoint for the IStatusService service
//			final IJaxRsBL jaxRsBL = Services.get(IJaxRsBL.class);
//			jaxRsBL.startServerEndPoint(StatusService.class);
//		}

		// make sure that CConnection can query the server
		CConnection.setStatusServiceEndPointProvider(new IStatusServiceEndPointProvider()
		{
			@Override
			public IStatusService provide(final CConnection cConnection)
			{
				if(CConnection.isServerEmbedded() || Ini.getRunMode() == RunMode.BACKEND)
				{
					// just return the "local" implementation, don't create a client endpoint, because
					// often CConnection calls methods call other methods of themselves via this service.
					// If the called instance is actually the same that makes the call (i.e. not on another machine)
					// there are locking issues
					return Services.get(IStatusService.class);
				}

				final IJaxRsBL jaxRsBL = Services.get(IJaxRsBL.class);
				return jaxRsBL.createClientEndpoint(cConnection, IStatusService.class);
			}
		});

	}
}

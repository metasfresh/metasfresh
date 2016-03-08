package de.metas.jms;

import org.adempiere.util.Services;
import org.compiere.db.CConnection;

import de.metas.adempiere.addon.IAddOn;

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
	/**
	 * Starts an embedded JMS broker, if we are runnung in embedded-server mode.
	 */
	@Override
	public void beforeConnection()
	{
		if (!CConnection.isServerEmbedded())
		{
			return; // nothing to do
		}

		final IJMSService jmsService = Services.get(IJMSService.class);
		jmsService.startEmbeddedBrocker();
	}
}

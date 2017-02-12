package de.metas.jax.rs.model.interceptor;

import java.util.Properties;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere.RunMode;
import org.compiere.db.CConnection;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Ini;
import org.slf4j.Logger;

import de.metas.jax.rs.IJaxRsBL;
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

public class JaxRsInterceptor extends AbstractModuleInterceptor
{
	private static final Logger logger = LogManager.getLogger(JaxRsInterceptor.class);

	/**
	 * Register JAX-RS endpoints. Disable this model interceptor to avoid registering them.
	 */
	@Override
	protected void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(client);
		final IJaxRsBL jaxRsBL = Services.get(IJaxRsBL.class);

		final boolean serverMode = Ini.getRunMode() == RunMode.BACKEND;
		if (serverMode || CConnection.isServerEmbedded())
		{
			// in ServerEmbedded mode, we assume that a local JMS broker was already started by this module's AddOn implementation.
			logger.info("Creating JAX-RS server endpoints");
			jaxRsBL.createServerEndPoints();
		}

		if (!serverMode)
		{
			logger.info("Creating JAX-RS client endpoints");
			jaxRsBL.createClientEndPoints(ctx);
		}
	}

}

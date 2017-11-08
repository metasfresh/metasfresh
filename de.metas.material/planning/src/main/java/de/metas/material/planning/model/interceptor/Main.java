package de.metas.material.planning.model.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.Adempiere;
import org.compiere.Adempiere.RunMode;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Ini;

import de.metas.material.event.MaterialEventService;
import de.metas.material.planning.event.MaterialDemandEventListener;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class Main extends AbstractModuleInterceptor
{
	/**
	 * Here we are going to register interceptors whose job it will be to notify the material disposition framework about notable events.
	 */
	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(M_Product.INSTANCE, client);
		engine.addModelValidator(S_Resource.INSTANCE, client);
	}

	@Override
	protected void onAfterInit()
	{
		if (Ini.getRunMode() != RunMode.BACKEND)
		{
			return; // event based material planning can only run in the backend as of now
		}

		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		final MaterialDemandEventListener materialDemandListener = Adempiere.getBean(MaterialDemandEventListener.class);

		materialEventService.registerListener(materialDemandListener);
		materialEventService.subscribeToEventBus();
	}
}

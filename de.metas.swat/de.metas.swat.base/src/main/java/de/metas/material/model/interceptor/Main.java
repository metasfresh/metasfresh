package de.metas.material.model.interceptor;

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Client;

import de.metas.material.event.MaterialEventService;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void registerInterceptors(
			final IModelValidationEngine engine, 
			final I_AD_Client client)
	{
		engine.addModelValidator(M_ReceiptSchedule.INSTANCE, client);
		engine.addModelValidator(M_ShipmentSchedule.INSTANCE, client);
		// engine.addModelValidator(M_Transaction.INSTANCE, client); // TODO https://github.com/metasfresh/metasfresh/issues/2684
		engine.addModelValidator(M_Forecast.INSTANCE, client);
	}

	@Override
	protected void onAfterInit()
	{
		letOurselfFireAndReceiveEvents();
	}

	private void letOurselfFireAndReceiveEvents()
	{
		final MaterialEventService materialEventService = Adempiere.getBean(MaterialEventService.class);
		materialEventService.subscribeToEventBus();
	}
}

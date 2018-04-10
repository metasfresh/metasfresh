package de.metas.ordercandidate.modelvalidator;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceAwareFactoryService;
import org.adempiere.mm.attributes.api.IModelAttributeSetInstanceListenerService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.i18n.IMsgBL;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.ordercandidate.OrderCandidate_Constants;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.impl.OLCandASIAwareFactory;
import de.metas.ordercandidate.spi.impl.OLCandPricingASIListener;

/**
 * Main model interceptor of <code>de.metas.ordercandidate</code> module.
 *
 */
public class OrderCandidate extends AbstractModuleInterceptor
{
	@Override
	public void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);

		Services.get(IAttributeSetInstanceAwareFactoryService.class).registerFactoryForTableName(I_C_OLCand.Table_Name, new OLCandASIAwareFactory()); // task 08803

		engine.addModelValidator(new AD_Scheduler(), client);
		engine.addModelValidator(new AD_Note(), client);
		engine.addModelValidator(new C_OLCand(), client);
		engine.addModelValidator(new C_OLCandProcessor(), client);
		engine.addModelValidator(new C_OrderLine(), client);

		// task 08803: registering this listener *after* C_OLCand, because C_OLCand can call IOLCandValdiatorBL.validate, and this listener (which is actually a model interceptor) needs to be called
		// after that (if there is any change).
		Services.get(IModelAttributeSetInstanceListenerService.class).registerListener(new OLCandPricingASIListener());

		if (!Ini.isClient())
		{
			ensureDataDestExists();
		}
	}

	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new de.metas.ordercandidate.callout.C_OLCandAggAndOrder());
	}

	private void ensureDataDestExists()
	{
		final I_AD_InputDataSource dest = Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(Env.getCtx(), OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME, false,
				ITrx.TRXNAME_None);
		if (dest == null)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);

			final I_AD_InputDataSource newDest = InterfaceWrapperHelper.create(Env.getCtx(), I_AD_InputDataSource.class, ITrx.TRXNAME_None);
			newDest.setEntityType(OrderCandidate_Constants.ENTITY_TYPE);
			newDest.setInternalName(OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);
			newDest.setIsDestination(true);
			newDest.setName(msgBL.translate(Env.getCtx(), "C_Order_ID"));
			InterfaceWrapperHelper.save(newDest);
		}
	}
}

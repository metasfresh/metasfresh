package de.metas.procurement.base;

import java.util.List;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.compiere.model.I_AD_Client;

import com.google.common.collect.ImmutableList;

import de.metas.event.Topic;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.callout.PMM_PurchaseCandidate_TabCallout;

/*
 * #%L
 * de.metas.procurement.base
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
 * Module activator
 *
 * @author metas-dev <dev@metas-fresh.com>
 *
 */
public class Main extends AbstractModuleInterceptor
{
	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		//
		// Candidate -> Purchase order
		engine.addModelValidator(new de.metas.procurement.base.order.interceptor.C_Order(), client);
		engine.addModelValidator(new de.metas.procurement.base.order.interceptor.C_OrderLine(), client);
		engine.addModelValidator(new de.metas.procurement.base.order.interceptor.PMM_PurchaseCandidate_OrderLine(), client);

		//
		// Events
		engine.addModelValidator(new de.metas.procurement.base.event.interceptor.PMM_QtyReport_Event(), client);

		//
		// contract and master data
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_Flatrate_DataEntry.instance, client);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.PMM_Product.instance, client);
	}

	@Override
	protected void registerTabCallouts(final ITabCalloutFactory tabCalloutsRegistry)
	{
		//
		// Candidate -> Purchase order
		tabCalloutsRegistry.registerTabCalloutForTable(I_PMM_PurchaseCandidate.Table_Name, PMM_PurchaseCandidate_TabCallout.class);
	}

	@Override
	protected void registerCallouts(IProgramaticCalloutProvider calloutsRegistry)
	{
		//
		// contract and master data
		calloutsRegistry.registerAnnotatedCallout(de.metas.procurement.base.model.interceptor.C_Flatrate_DataEntry.instance);
		calloutsRegistry.registerAnnotatedCallout(de.metas.procurement.base.model.interceptor.PMM_Product.instance);
	}

	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(ProcurementConstants.EVENTBUS_TOPIC_PurchaseOrderGenerated);
	}
}

package de.metas.procurement.base;

import com.google.common.collect.ImmutableList;
import de.metas.contracts.IFlatrateTermEventService;
import de.metas.event.Topic;
import de.metas.logging.LogManager;
import de.metas.procurement.base.contracts.ProcurementFlatrateHandler;
import de.metas.procurement.base.model.I_PMM_PurchaseCandidate;
import de.metas.procurement.base.order.callout.PMM_PurchaseCandidate_TabCallout;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.service.ISysConfigBL;
import org.slf4j.Logger;

import java.util.List;

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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Main extends AbstractModuleInterceptor
{
	private static final Logger logger = LogManager.getLogger(Main.class);

	private static final String SYSCONFIG_JMS_QUEUE_RESPONSE = "de.metas.procurement.webui.jms.queue.response";
	private static final String SYSCONFIG_JMS_QUEUE_REQUEST = "de.metas.procurement.webui.jms.queue.request";

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine)
	{
		//
		// Candidate -> Purchase order
		engine.addModelValidator(new de.metas.procurement.base.order.interceptor.C_Order());
		engine.addModelValidator(de.metas.procurement.base.order.interceptor.C_OrderLine.instance);
		engine.addModelValidator(de.metas.procurement.base.order.interceptor.PMM_PurchaseCandidate.instance);
		engine.addModelValidator(new de.metas.procurement.base.order.interceptor.PMM_PurchaseCandidate_OrderLine());

		//
		// Events
		engine.addModelValidator(new de.metas.procurement.base.event.interceptor.PMM_QtyReport_Event());
		engine.addModelValidator(new de.metas.procurement.base.event.interceptor.PMM_WeekReport_Event());

		//
		// Master data: bpartner & users
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_BPartner.instance);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.AD_User.instance);
		// Master data: contracts
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_Flatrate_Term.instance);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_Flatrate_DataEntry.instance);
		// Master data: products
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.M_Product.instance);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_BPartner_Product.instance);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.PMM_Product.instance);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.M_HU_PI_Item_Product.instance);
		// Master data: messages
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.PMM_Message.instance);
		
		//
		// RfQ
		engine.addModelValidator(new de.metas.procurement.base.rfq.model.interceptor.RfqMainInterceptor());
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
		
		//
		// Purchase candidate
		calloutsRegistry.registerAnnotatedCallout(de.metas.procurement.base.order.interceptor.PMM_PurchaseCandidate.instance);
	}

	@Override
	protected List<Topic> getAvailableUserNotificationsTopics()
	{
		return ImmutableList.of(ProcurementConstants.USER_NOTIFICATIONS_TOPIC);
	}

	@Override
	protected void onAfterInit()
	{
		setupFlatrateTerms();
		setupJaxRs();
	}
	
	private void setupJaxRs()
	{		
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		final String requestQueueName = sysConfigBL.getValue(SYSCONFIG_JMS_QUEUE_REQUEST, getAD_Client_ID());
		final String responseQueueName = sysConfigBL.getValue(SYSCONFIG_JMS_QUEUE_RESPONSE, getAD_Client_ID());

		if (Check.isEmpty(requestQueueName, true) || Check.isEmpty(responseQueueName, true))
		{
			logger.error("At least one one of requestQueueName={} and responseQueueName={} is not set. \n"
					+ "Therefore this instance won't be able to actively send data to the procurement UI. \n"
					+ "To fix this, add AD_SysConfig records with AD_Client_ID={} and AD_Org_ID=0 and with the following names:\n"
					+ "{} \n"
					+ "{}",
					new Object[] { requestQueueName, responseQueueName, Math.max(getAD_Client_ID(), 0), SYSCONFIG_JMS_QUEUE_REQUEST, SYSCONFIG_JMS_QUEUE_RESPONSE });
			return;
		}
		//
		// create the client endpoint so we can reach the procurement webUI.
		// TODO ? register rabbitmq-stuff ?

		// note: ServerSync will just be a "normal" server, listening no our default queues
	}
	
	private void setupFlatrateTerms()
	{
		Services.get(IFlatrateTermEventService.class).registerEventListenerForConditionsType(new ProcurementFlatrateHandler(), ProcurementFlatrateHandler.TYPE_CONDITIONS);
	}
}

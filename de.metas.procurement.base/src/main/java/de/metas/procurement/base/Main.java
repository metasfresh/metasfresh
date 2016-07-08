package de.metas.procurement.base;

import java.util.List;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import de.metas.event.Topic;
import de.metas.flatrate.api.IFlatrateHandlersService;
import de.metas.jax.rs.CreateEndpointRequest;
import de.metas.jax.rs.IJaxRsBL;
import de.metas.logging.LogManager;
import de.metas.procurement.base.contracts.ProcurementFlatrateHandler;
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class Main extends AbstractModuleInterceptor
{
	private static final Logger logger = LogManager.getLogger(Main.class);

	private static final String SYSCONFIG_JMS_QUEUE_RESPONSE = "de.metas.procurement.webui.jms.queue.response";
	private static final String SYSCONFIG_JMS_QUEUE_REQUEST = "de.metas.procurement.webui.jms.queue.request";

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		//
		// Candidate -> Purchase order
		engine.addModelValidator(new de.metas.procurement.base.order.interceptor.C_Order(), client);
		engine.addModelValidator(de.metas.procurement.base.order.interceptor.C_OrderLine.instance, client);
		engine.addModelValidator(de.metas.procurement.base.order.interceptor.PMM_PurchaseCandidate.instance, client);
		engine.addModelValidator(new de.metas.procurement.base.order.interceptor.PMM_PurchaseCandidate_OrderLine(), client);

		//
		// Events
		engine.addModelValidator(new de.metas.procurement.base.event.interceptor.PMM_QtyReport_Event(), client);
		engine.addModelValidator(new de.metas.procurement.base.event.interceptor.PMM_WeekReport_Event(), client);

		//
		// Master data: bpartner & users
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_BPartner.instance, client);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.AD_User.instance, client);
		// Master data: contracts
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_Flatrate_Term.instance, client);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_Flatrate_DataEntry.instance, client);
		// Master data: products
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.M_Product.instance, client);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.C_BPartner_Product.instance, client);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.PMM_Product.instance, client);
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.M_HU_PI_Item_Product.instance, client);
		// Master data: messages
		engine.addModelValidator(de.metas.procurement.base.model.interceptor.PMM_Message.instance, client);
		
		//
		// RfQ
		engine.addModelValidator(new de.metas.procurement.base.rfq.model.interceptor.RfqMainInterceptor(), client);
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
		return ImmutableList.of(ProcurementConstants.EVENTBUS_TOPIC_PurchaseOrderGenerated);
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
		final IJaxRsBL jaxRsBL = Services.get(IJaxRsBL.class);

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
		final CreateEndpointRequest<IAgentSyncBL> clientEndpointRequest = CreateEndpointRequest
				.builder(IAgentSyncBL.class)
				.setRequestQueue(requestQueueName)
				.setResponseQueue(responseQueueName)
				.build();

		final IAgentSyncBL agentEndpointService = jaxRsBL.createClientEndpointsProgramatically(clientEndpointRequest).get(0);
		Services.registerService(IAgentSyncBL.class, agentEndpointService);

		// note: ServerSync will just be a "normal" server, listening no our default queues
	}
	
	private void setupFlatrateTerms()
	{
		Services.get(IFlatrateHandlersService.class).registerHandler(ProcurementFlatrateHandler.TYPE_CONDITIONS, new ProcurementFlatrateHandler());
	}
}

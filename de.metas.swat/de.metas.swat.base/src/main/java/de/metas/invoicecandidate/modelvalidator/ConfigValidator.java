/**
 *
 */
package de.metas.invoicecandidate.modelvalidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.Properties;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.housekeeping.IHouseKeepingBL;
import org.adempiere.ad.migration.logger.IMigrationLogger;
import org.adempiere.ad.modelvalidator.AbstractModuleInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.invoice.event.InvoiceGeneratedEventBus;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.ui.api.IGridTabSummaryInfoFactory;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Client;
import org.compiere.util.Env;
import org.compiere.util.Ini;

import de.metas.aggregation.api.IAggregationFactory;
import de.metas.aggregation.listeners.AggregationListenerAdapter;
import de.metas.aggregation.listeners.IAggregationListener;
import de.metas.aggregation.listeners.IAggregationListeners;
import de.metas.aggregation.model.I_C_Aggregation;
import de.metas.aggregation.model.X_C_Aggregation;
import de.metas.event.IEventBusFactory;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.invoicecandidate.agg.key.impl.ICHeaderAggregationKeyBuilder_OLD;
import de.metas.invoicecandidate.agg.key.impl.ICLineAggregationKeyBuilder_OLD;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandidate_Constants;
import de.metas.invoicecandidate.callout.C_Invoice_Candidate_TabCallout;
import de.metas.invoicecandidate.housekeeping.sqi.impl.Reset_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.ui.spi.impl.C_Invoice_Candidate_GridTabSummaryInfoProvider;

/**
 * Main Invoice Candidates validator
 *
 * @author tsa
 *
 */
public class ConfigValidator extends AbstractModuleInterceptor
{
	/** Listens on {@link I_C_Aggregation} changes and invalidates matching {@link I_C_Invoice_Candidate}s */
	private static final IAggregationListener aggregationListener = new AggregationListenerAdapter()
	{
		@Override
		protected void onEvent(I_C_Aggregation aggregation)
		{
			Services.get(IInvoiceCandDAO.class).invalidateCandsForAggregationBuilder(aggregation);
		}
	};

	@Override
	public void onInit(final IModelValidationEngine engine, final I_AD_Client client)
	{
		super.onInit(engine, client);

		if (!Ini.isClient())
		{
			ensureDataDestExists();
		}

		//
		// Register GridTabSummaryInfo entries (07985)
		final IGridTabSummaryInfoFactory gridTabSummaryInfoFactory = Services.get(IGridTabSummaryInfoFactory.class);
		gridTabSummaryInfoFactory.register(I_C_Invoice_Candidate.Table_Name, new C_Invoice_Candidate_GridTabSummaryInfoProvider());

		setupAggregations();

		// ignoring C_Invoice_Candidate_Recompute from migration scripts; otherwise it might occur that the migration script contains
		// are inserts into the table, if an embedded async processor is running somewhere in the background
		final IMigrationLogger migrationLogger = Services.get(IMigrationLogger.class);
		migrationLogger.addTableToIgnoreList(I_C_Invoice_Candidate_Recompute.Table_Name);

		//
		// Setup event bus topics on which swing client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(InvoiceGeneratedEventBus.EVENTBUS_TOPIC);

		// https://github.com/metasfresh/metasfresh/issues/251: clean up stale C_Invoice_Candidate_Recompute records that might prevent ICs from getting updated.
		Services.get(IHouseKeepingBL.class).registerStartupHouseKeepingTask(new Reset_C_Invoice_Candidate_Recompute());
	}

	@Override
	protected void registerInterceptors(final IModelValidationEngine engine, final I_AD_Client client)
	{
		engine.addModelValidator(new C_BPartner(), client);
		engine.addModelValidator(C_ILCandHandler.instance, client);
		engine.addModelValidator(new C_Invoice_Candidate_Agg(), client);
		engine.addModelValidator(new C_Invoice_Candidate(), client);
		engine.addModelValidator(new C_Invoice_Line_Alloc(), client);
		engine.addModelValidator(new C_InvoiceSchedule(), client);
		engine.addModelValidator(new C_Invoice(), client);
		engine.addModelValidator(new AD_Note(), client);
		engine.addModelValidator(new C_OrderLine(), client);
		engine.addModelValidator(new C_Order(), client);
		engine.addModelValidator(new M_InOut(), client);
		engine.addModelValidator(new M_InOutLine(), client);
		engine.addModelValidator(new M_ProductGroup_Product(), client);
		engine.addModelValidator(new M_ProductGroup(), client);
	}

	@Override
	protected void registerTabCallouts(final ITabCalloutFactory tabCalloutsRegistry)
	{
		tabCalloutsRegistry.registerTabCalloutForTable(I_C_Invoice_Candidate.Table_Name, C_Invoice_Candidate_TabCallout.class);
	}
	
	@Override
	protected void registerCallouts(final IProgramaticCalloutProvider calloutsRegistry)
	{
		calloutsRegistry.registerAnnotatedCallout(new de.metas.invoicecandidate.callout.C_Invoice_Candidate());
		calloutsRegistry.registerAnnotatedCallout(new de.metas.invoicecandidate.callout.C_Invoice_Candidate_Agg());
		calloutsRegistry.registerAnnotatedCallout(new de.metas.invoicecandidate.callout.C_ILCandHandler());
	}

	/**
	 * Setup de.metas.aggregation
	 */
	private final void setupAggregations()
	{
		//
		// In case there was no aggregation found, fallback to our legacy IC header/line aggregation key builders
		final IAggregationFactory aggregationFactory = Services.get(IAggregationFactory.class);
		aggregationFactory.setDefaultAggregationKeyBuilder(I_C_Invoice_Candidate.class, X_C_Aggregation.AGGREGATIONUSAGELEVEL_Header, ICHeaderAggregationKeyBuilder_OLD.instance);
		aggregationFactory.setDefaultAggregationKeyBuilder(I_C_Invoice_Candidate.class, X_C_Aggregation.AGGREGATIONUSAGELEVEL_Line, ICLineAggregationKeyBuilder_OLD.instance);

		//
		// On C_Aggregation master data change => invalidate candidates for that aggregation
		Services.get(IAggregationListeners.class).addListener(aggregationListener);
	}

	private void ensureDataDestExists()
	{
		final Properties ctx = Env.getCtx();
		final I_AD_InputDataSource dest = Services.get(IInputDataSourceDAO.class).retrieveInputDataSource(ctx, InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME, false, ITrx.TRXNAME_None);
		if (dest == null)
		{
			final I_AD_InputDataSource newDest = InterfaceWrapperHelper.create(ctx, I_AD_InputDataSource.class, ITrx.TRXNAME_None);
			newDest.setEntityType(InvoiceCandidate_Constants.ENTITY_TYPE);
			newDest.setInternalName(InvoiceCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME);
			newDest.setIsDestination(true);
			newDest.setName(Services.get(IMsgBL.class).translate(ctx, "C_Invoice_ID"));
			InterfaceWrapperHelper.save(newDest);
		}
	}
}

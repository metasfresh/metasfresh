package de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl;

/*
 * #%L
 * de.metas.materialtracking
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.compiere.util.CLogger;
import org.compiere.util.Env;

import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingBL;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedInvoicingDAO;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityBasedSpiProviderService;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionHandlerDAO;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorReceipt;
import de.metas.materialtracking.qualityBasedInvoicing.impl.PricingContextBuilder;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityBasedConfig;
import de.metas.materialtracking.qualityBasedInvoicing.spi.IQualityInvoiceLineGroupsBuilder;

/**
 * Producer which is able to take quality orders ({@link I_PP_Order}s) as input and creates invoice candidates. Called by
 * {@link QualityInspectionHandler#createMissingCandidates(Properties, int, String)} and {@link QualityInspectionHandler#createCandidatesFor(Object)} to do the actual creating.
 *
 * Also see {@link IQualityInvoiceLineGroupsBuilder} to create the "raw" data and {@link InvoiceCandidateBuilder} to actually create the invoice candidate from those groups.
 *
 * @author tsa
 *
 */
/* package */class PPOrder2InvoiceCandidatesProducer
{
	// Services
	private final transient CLogger logger = CLogger.getCLogger(getClass());
	private final IQualityInspectionHandlerDAO qualityInspectionHandlerDAO = Services.get(IQualityInspectionHandlerDAO.class);
	private final IQualityBasedInvoicingDAO qualityBasedInvoicingDAO = Services.get(IQualityBasedInvoicingDAO.class);
	private final IQualityBasedInvoicingBL qualityBasedInvoicingBL = Services.get(IQualityBasedInvoicingBL.class);
	private final IQualityBasedSpiProviderService qualityBasedSpiProviderService = Services.get(IQualityBasedSpiProviderService.class);

	// Parameters
	private I_C_ILCandHandler _ilCandHandler;

	public PPOrder2InvoiceCandidatesProducer()
	{
		super();
	}

	public void setC_ILCandHandler(final I_C_ILCandHandler ilCandHandler)
	{
		_ilCandHandler = ilCandHandler;
	}

	private I_C_ILCandHandler getC_ILCandHandler()
	{
		Check.assumeNotNull(_ilCandHandler, "ilCandHandler not null");
		return _ilCandHandler;
	}

	/**
	 * All quality orders which are suitable for invoices and there are no invoice candidates created yet.
	 *
	 * @param ctx
	 * @param limit how many quality orders to retrieve
	 * @param trxName
	 * @return created invoice candidates
	 */
	public Iterator<I_PP_Order> retrieveAllModelsWithMissingCandidates(final Properties ctx, final int limit, final String trxName)
	{
		return qualityInspectionHandlerDAO.retrievePPOrdersWithMissingICs(ctx, limit, trxName);
	}

	/**
	 * Creates invoice candidates for given quality order.
	 *
	 * @param ppOrder quality inspection order
	 * @return created invoice candidates
	 */
	public List<I_C_Invoice_Candidate> createInvoiceCandidates(final I_PP_Order ppOrder)
	{
		// Processing context
		final IContextAware context = InterfaceWrapperHelper.getContextAware(ppOrder);

		final ILoggable loggable = ILoggable.THREADLOCAL.getLoggableOr(ILoggable.NULL);

		//
		// Check if given manufacturing order is eligible
		if (!qualityInspectionHandlerDAO.isInvoiceable(ppOrder))
		{
			final String msg = "Skip invoice candidates creation because PP_Order " + ppOrder + " is not invoiceable:";
			logger.log(Level.INFO, msg);
			loggable.addLog(msg);
			return Collections.emptyList(); // nothing to do here
		}

		//
		// Validate manufacturing order
		Check.assume(Env.getAD_Client_ID(context.getCtx()) == ppOrder.getAD_Client_ID(), "AD_Client_ID of PP_Order {0} and of its Ctx are the same", ppOrder);

		//
		// Load material tracking documents and get our Quality Inspection Order
		final IMaterialTrackingDocuments materialTrackingDocuments = qualityBasedInvoicingDAO.retrieveMaterialTrackingDocumentsForPPOrderOrNull(ppOrder);
		if (materialTrackingDocuments == null)
		{
			// Case: ppOrder was not assigned to a material tracking (for some reason)
			final String msg = "Skip invoice candidates creation because PP_Order " + ppOrder + " is not assigned to a material tracking";
			loggable.addLog(msg);
			logger.log(Level.WARNING, msg);
			return Collections.emptyList();
		}

		final IQualityInspectionOrder qiOrder = materialTrackingDocuments.getQualityInspectionOrder(ppOrder);

		final I_M_Material_Tracking materialTracking = materialTrackingDocuments.getM_Material_Tracking();
		//
		// Make sure all original invoice candidates are valid.
		// If not, we shall postpone this computation because makes no sense. We will wait for better times (i.e. when original invoice candidates are valid)
		final List<I_C_Invoice_Candidate> originalInvoiceCandidates = qualityInspectionHandlerDAO.retrieveOriginalInvoiceCandidates(materialTracking, I_C_Invoice_Candidate.class);
		if (originalInvoiceCandidates.isEmpty())
		{
			final String msg = "Postponing invoice candidates creation for PP_Order " + ppOrder + " because there are no original C_Invoice_Candidates for M_Material_Tracking " + materialTracking;
			logger.log(Level.INFO, msg);
			loggable.addLog(msg);
			return Collections.emptyList();
		}
		for (final I_C_Invoice_Candidate originalIC : originalInvoiceCandidates)
		{
			if (originalIC.isToRecompute())
			{
				final String msg = "Postponing invoice candidates creation for PP_Order " + ppOrder + " because original C_Invoice_Candidate " + originalIC + " is not (yet?) valid";
				logger.log(Level.INFO, msg);
				loggable.addLog(msg);

				return Collections.emptyList();
			}

			if (!originalIC.isToClear())
			{
				// this can happen if the user created a tracking with a product & partner that have/has no contract. This shouldn't kill the whole IC-process.
				final String msg = "Postponing invoice candidates creation for PP_Order " + ppOrder + " because original C_Invoice_Candidate " + originalIC + " has IsToClear='N'";
				logger.log(Level.INFO, msg);
				loggable.addLog(msg);
				return Collections.emptyList();
			}
		}

		//
		// Iterate invoice candidates of original purchase order and generate invoice candidates for quality order
		final List<I_C_Invoice_Candidate> result = new ArrayList<>();

		final IVendorReceipt receiptFromVendor = new InvoiceCandidateAsVendorReceipt();
		final IVendorInvoicingInfo vendorInvoicingInfo = new InvoiceCandidateAsVendorInvoicingInfo();

		for (final I_C_Invoice_Candidate originalIc : originalInvoiceCandidates)
		{
			receiptFromVendor.add(originalIc);
			vendorInvoicingInfo.add(originalIc);
		}

		final IQualityBasedConfig config = qiOrder.getQualityBasedConfig();

		//
		// Create invoice line groups
		final List<IQualityInvoiceLineGroup> invoiceLineGroups = createQualityInvoiceLineGroups(context, qiOrder, receiptFromVendor, vendorInvoicingInfo);

		//
		// Create invoice candidates from those groups
		final InvoiceCandidateBuilder invoiceCandidateBuilder = new InvoiceCandidateBuilder(context);
		invoiceCandidateBuilder.setC_ILCandHandler(getC_ILCandHandler());
		invoiceCandidateBuilder.setVendorInvoicingInfo(vendorInvoicingInfo);
		invoiceCandidateBuilder.setMaterialTrackingDocuments(materialTrackingDocuments);
		invoiceCandidateBuilder.setQualityInspectionOrder(qiOrder);
		invoiceCandidateBuilder.setInvoiceCandidatesToClear(originalInvoiceCandidates);
		invoiceCandidateBuilder.setQualityInvoiceLineGroupTypes(config.getQualityInvoiceLineGroupTypes());

		// task: 07845 decide which doctype to use
		final int docTypeInvoice_ID;
		if (!qiOrder.isQualityInspection() || qualityBasedInvoicingBL.isLastInspection(qiOrder))
		{
			// task 08848: also for regular PP_Orders (Auslagerung), we use the final-settlement doctype.
			// That's because in the downpayment (1st inspection), we don't yet invoice any regular PP_Orders
			docTypeInvoice_ID = config.getC_DocTypeInvoice_FinalSettlement_ID();
		}
		else
		{
			docTypeInvoice_ID = config.getC_DocTypeInvoice_DownPayment_ID();
		}
		invoiceCandidateBuilder.setInvoiceDocType_ID(docTypeInvoice_ID);

		invoiceCandidateBuilder.create(invoiceLineGroups);
		final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateBuilder.getCreatedInvoiceCandidates();

		// Add generated invoice candidates to the main result
		result.addAll(invoiceCandidates);

		return result;
	}

	private List<IQualityInvoiceLineGroup> createQualityInvoiceLineGroups(final IContextAware context,
			final IQualityInspectionOrder qiOrder,
			final IVendorReceipt receiptFromVendor,
			final IVendorInvoicingInfo vendorInvoicingInfo)
	{
		Check.assumeNotNull(qiOrder, "qiOrder not null");
		Check.assumeNotNull(receiptFromVendor, "receiptFromVendor not null");

		final IQualityInvoiceLineGroupsBuilder invoiceLineGroupsBuilder = qualityBasedSpiProviderService
				.getQualityInvoiceLineGroupsBuilderProvider()
				.provideBuilderFor(qiOrder);

		//
		// Configure: Qty received from Vendor (reference Qty)
		invoiceLineGroupsBuilder.setReceiptFromVendor(receiptFromVendor);

		//
		// Configure: pricing context to be used
		final PricingContextBuilder pricingContextBuilder = new PricingContextBuilder(context);
		pricingContextBuilder.setVendorInvoicingInfo(vendorInvoicingInfo);
		pricingContextBuilder.setQualityInspectionOrder(qiOrder);
		final IPricingContext pricingContext = pricingContextBuilder.create();
		invoiceLineGroupsBuilder.setPricingContext(pricingContext);

		//
		// Execute builder and create invoice line groups
		invoiceLineGroupsBuilder.create();
		final List<IQualityInvoiceLineGroup> invoiceLineGroups = invoiceLineGroupsBuilder.getCreatedInvoiceLineGroups();

		// Log builder configuration and status
		logger.log(Level.INFO, "{0}", invoiceLineGroupsBuilder);

		//
		// Return created invoice line groups
		return invoiceLineGroups;
	}
}

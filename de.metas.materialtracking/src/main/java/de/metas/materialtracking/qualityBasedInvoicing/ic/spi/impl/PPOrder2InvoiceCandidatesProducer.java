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
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingContext;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.model.IClientOrgAware;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.util.Env;
import org.eevolution.api.IPPCostCollectorBL;
import org.eevolution.api.IPPCostCollectorDAO;
import org.eevolution.model.I_PP_Cost_Collector;
import org.slf4j.Logger;

import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.logging.LogManager;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_InOutLine;
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
 * {@link PP_Order_MaterialTracking_Handler#createCandidatesFor(InvoiceCandidateGenerateRequest)} to do the actual creating.
 * <p/>
 * Also see {@link IQualityInvoiceLineGroupsBuilder} to create the "raw" data and {@link InvoiceCandidateWriter} to actually create the invoice candidate from those groups.
 *
 * @author tsa
 */
/* package */final class PPOrder2InvoiceCandidatesProducer
{
	// Services
	private static final transient Logger logger = LogManager.getLogger(PPOrder2InvoiceCandidatesProducer.class);
	private final transient IQualityInspectionHandlerDAO qualityInspectionHandlerDAO = Services.get(IQualityInspectionHandlerDAO.class);
	private final transient IQualityBasedInvoicingDAO qualityBasedInvoicingDAO = Services.get(IQualityBasedInvoicingDAO.class);
	private final transient IQualityBasedInvoicingBL qualityBasedInvoicingBL = Services.get(IQualityBasedInvoicingBL.class);
	private final transient IQualityBasedSpiProviderService qualityBasedSpiProviderService = Services.get(IQualityBasedSpiProviderService.class);
	private final transient IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

	// Parameters
	private I_C_ILCandHandler _qualityInspectionHandlerRecord;
	private PP_Order_MaterialTracking_Handler qualityInspectionHandler;

	public PPOrder2InvoiceCandidatesProducer()
	{
		super();
	}

	public PPOrder2InvoiceCandidatesProducer setC_ILCandHandler(final I_C_ILCandHandler ilCandHandler)
	{
		_qualityInspectionHandlerRecord = ilCandHandler;
		return this;
	}

	private I_C_ILCandHandler getC_ILCandHandler()
	{
		Check.assumeNotNull(_qualityInspectionHandlerRecord, "ilCandHandler not null");
		return _qualityInspectionHandlerRecord;
	}

	public PPOrder2InvoiceCandidatesProducer setILCandHandlerInstance(final PP_Order_MaterialTracking_Handler qualityInspectionHandler)
	{
		this.qualityInspectionHandler = qualityInspectionHandler;
		return this;
	}

	/**
	 * Creates invoice candidates for given quality order.
	 *
	 * @param ppOrder quality inspection order
	 * @return created invoice candidates
	 */
	public List<I_C_Invoice_Candidate> createInvoiceCandidates(final I_PP_Order ppOrder)
	{
		Check.assumeNotNull(_qualityInspectionHandlerRecord, "Field _ilCandHandler of {} is not null", this);
		Check.assumeNotNull(qualityInspectionHandler, "Field qualityInspectionHandler of {} is not null", this);

		//
		// Validate the model
		Check.assumeNotNull(ppOrder, "Param 'ppOrder' is not null");
		final IClientOrgAware clientOrgAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(ppOrder, IClientOrgAware.class);
		Check.assumeNotNull(clientOrgAware, "Param ppOrder={} is a IClientOrgAware", ppOrder);
		final IContextAware context = InterfaceWrapperHelper.getContextAware(ppOrder);
		Check.assume(Env.getAD_Client_ID(context.getCtx()) == clientOrgAware.getAD_Client_ID(), "AD_Client_ID of PP_Order {} and of its Ctx are the same", ppOrder);

		final ILoggable loggable = Loggables.get();

		//
		// Check if given manufacturing order is eligible. It might be not eligible anymore, because it was already processed earlier this run
		if (!qualityInspectionHandler.isInvoiceable(ppOrder))
		{
			final String msg = "Skip invoice candidates creation because ppOrder={} is not invoiceable according to handler {}";
			logger.info(msg, ppOrder, qualityInspectionHandler);
			loggable.addLog(msg, ppOrder, qualityInspectionHandler);
			return Collections.emptyList(); // nothing to do here
		}

		if (!wasAnythingIssued(ppOrder))
		{
			// fresh-216: if we don't skip there will be problems later, because if nothing was issued, then we won't find a material receipt either.
			final String msg = "Skip invoice candidates creation because nothing was acutally issued to ppOrder={}.";
			logger.info(msg, ppOrder);
			loggable.addLog(msg, ppOrder);
			return Collections.emptyList(); // nothing to do here
		}

		//
		// Load material tracking documents and get our Quality Inspection Order
		final IMaterialTrackingDocuments materialTrackingDocuments = qualityBasedInvoicingDAO.retrieveMaterialTrackingDocumentsOrNullFor(ppOrder);
		if (materialTrackingDocuments == null)
		{
			// Case: ppOrder was not assigned to a material tracking (for some reason)
			final String msg = "Skip invoice candidates creation because ppOrder={} is not assigned to a material tracking";
			loggable.addLog(msg, ppOrder);
			logger.info(msg, ppOrder);
			return Collections.emptyList();
		}

		//
		// Get the quality inspection order
		final IQualityInspectionOrder qiOrder = materialTrackingDocuments.getQualityInspectionOrderOrNull();
		if (qiOrder == null)
		{
			final String msg = "Skip invoice candidates creation because there is no quality inspection for ppOrder={}";
			loggable.addLog(msg, ppOrder);
			logger.info(msg, ppOrder);
			return Collections.emptyList();
		}

		// figure out if this is a downPayment. It is, if 'ppOrder' is a quality inspection and not the last one.
		final boolean ppOrderIsQualityInpection = materialTrackingPPOrderBL.isQualityInspection(ppOrder);
		final boolean isDownPayment = ppOrderIsQualityInpection && !qualityBasedInvoicingBL.isLastInspection(qiOrder);

		// if the given ppOrder's qiOrder is a downPayment-qiOrder (i.e. there is not yet any existing finalSettlement-qiOrder),
		// and if there are already ICs for the downPayment-qiOrder, then do nothing
		if (!qualityBasedInvoicingBL.isLastInspection(qiOrder))
		{
			final List<de.metas.invoicecandidate.model.I_C_Invoice_Candidate> downPaymentICs = invoiceCandDAO.retrieveReferencing(qiOrder.getPP_Order());
			if (!downPaymentICs.isEmpty())
			{
				final de.metas.invoicecandidate.model.I_C_Invoice_Candidate firstDownPaymentIC = downPaymentICs.get(0);
				final String msg = "Skip invoice candidates creation because {} is a downpayment quality inspection and there are already C_Invoice_Candidates such as {} for it";
				loggable.addLog(msg, qiOrder.getPP_Order(), firstDownPaymentIC);
				logger.info(msg, ppOrder, firstDownPaymentIC);
				return Collections.emptyList();
			}
		}

		final I_M_Material_Tracking materialTracking = materialTrackingDocuments.getM_Material_Tracking();

		final List<I_C_Invoice_Candidate> originalInvoiceCandidates = qualityInspectionHandlerDAO.retrieveOriginalInvoiceCandidates(materialTracking, I_C_Invoice_Candidate.class);

		final Collection<I_M_PriceList_Version> plvs = materialTrackingDocuments.getPriceListVersions();
		if (plvs.isEmpty())
		{
			loggable.addLog("Found no M_PriceList_Version for materialTrackingDocuments {}; ppOrder={}", materialTrackingDocuments, ppOrder);
		}

		final List<I_C_Invoice_Candidate> result = new ArrayList<>();
		for (final I_M_PriceList_Version plv : plvs)
		{
			final IVendorReceipt<I_M_InOutLine> vendorReceiptForPLV = materialTrackingDocuments.getVendorReceiptForPLV(plv);
			Check.assumeNotNull(vendorReceiptForPLV,
					"vendorReceiptForPLV not null for M_PriceList_Version={};\nmaterialTrackingDocuments={}",
					plv, materialTrackingDocuments);

			final IVendorInvoicingInfo vendorInvoicingInfo = materialTrackingDocuments.getVendorInvoicingInfoForPLV(plv);

			final IQualityBasedConfig config = qiOrder.getQualityBasedConfig();

			//
			// Create invoice line groups
			final List<IQualityInvoiceLineGroup> invoiceLineGroups = createQualityInvoiceLineGroups(
					materialTrackingDocuments,
					vendorReceiptForPLV,
					vendorInvoicingInfo);

			//
			// Create invoice candidates from those groups
			final InvoiceCandidateWriter invoiceCandidateBuilder = new InvoiceCandidateWriter(context)
					.setC_ILCandHandler(getC_ILCandHandler())
					.setVendorInvoicingInfo(vendorInvoicingInfo)
					.setMaterialTrackingDocuments(materialTrackingDocuments)
					.setQualityInspectionOrder(qiOrder)
					.setInvoiceCandidatesToClear(originalInvoiceCandidates)
					.setQualityInvoiceLineGroupTypes(config.getQualityInvoiceLineGroupTypes());

			// task: 07845 decide which doctype to use
			final int docTypeInvoice_ID;
			if (isDownPayment)
			{
				docTypeInvoice_ID = config.getC_DocTypeInvoice_DownPayment_ID();
			}
			else
			{
				// task 08848: also for regular PP_Orders (Auslagerung), we use the final-settlement doctype.
				// That's because in the downpayment (1st inspection), we don't yet invoice any regular PP_Orders
				docTypeInvoice_ID = config.getC_DocTypeInvoice_FinalSettlement_ID();
			}
			invoiceCandidateBuilder.setInvoiceDocType_ID(docTypeInvoice_ID);

			final List<I_C_Invoice_Candidate> invoiceCandidates = invoiceCandidateBuilder
					.create(invoiceLineGroups)
					.getCreatedInvoiceCandidates();

			//
			// Add generated invoice candidates to the main result
			result.addAll(invoiceCandidates);
		}
		return result;
	}

	private boolean wasAnythingIssued(final I_PP_Order ppOrder)
	{
		final IPPCostCollectorDAO ppCostCollectorDAO = Services.get(IPPCostCollectorDAO.class);
		final IPPCostCollectorBL ppCostCollectorBL = Services.get(IPPCostCollectorBL.class);

		for (final I_PP_Cost_Collector cc : ppCostCollectorDAO.retrieveNotReversedForOrder(ppOrder))
		{
			if (ppCostCollectorBL.isMaterialIssue(cc, true) && cc.getMovementQty().signum() > 0)
			{
				return true;
			}
		}
		return false;
	}

	private List<IQualityInvoiceLineGroup> createQualityInvoiceLineGroups(
			final IMaterialTrackingDocuments materialTrackingDocuments,
			@SuppressWarnings("rawtypes") final IVendorReceipt vendorReceipt,    // only the add() and getModels methods are parameterized and the won't use them here, so it's safe to suppress
			final IVendorInvoicingInfo vendorInvoicingInfo)
	{
		Check.assumeNotNull(materialTrackingDocuments, "materialTrackingDocuments not null");
		Check.assumeNotNull(vendorReceipt,
				"vendorReceipt not null;\nmaterialTrackingDocuments={}",
				materialTrackingDocuments);

		final IQualityInspectionOrder qiOrder = materialTrackingDocuments.getQualityInspectionOrderOrNull();
		// we can be sure it's not null because if it was then this method would not be called.
		Check.assumeNotNull(qiOrder, "qiOrder of materialTrackingDocuments {} is not null", materialTrackingDocuments);

		final IQualityInvoiceLineGroupsBuilder invoiceLineGroupsBuilder = qualityBasedSpiProviderService
				.getQualityInvoiceLineGroupsBuilderProvider()
				.provideBuilderFor(materialTrackingDocuments);

		//
		// Configure: Qty received from Vendor (reference Qty)
		invoiceLineGroupsBuilder.setVendorReceipt(vendorReceipt);

		//
		// Configure: pricing context to be used
		final IPricingContext pricingContext = new PricingContextBuilder()
				.setVendorInvoicingInfo(vendorInvoicingInfo)
				.create();
		invoiceLineGroupsBuilder.setPricingContext(pricingContext);

		//
		// Execute builder and create invoice line groups
		final List<IQualityInvoiceLineGroup> invoiceLineGroups = invoiceLineGroupsBuilder
				.create()
				.getCreatedInvoiceLineGroups();

		// Log builder configuration and status
		logger.info("{}", invoiceLineGroupsBuilder);

		//
		// Return created invoice line groups
		return invoiceLineGroups;
	}
}

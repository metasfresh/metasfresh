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


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.api.IPricingResult;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;

import de.metas.flatrate.model.I_C_Invoice_Clearing_Alloc;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLine;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupByTypeComparator;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;

/**
 * Takes {@link IQualityInvoiceLineGroup}s and creates {@link I_C_Invoice_Candidate}s.
 *
 * @author tsa
 *
 */
public class InvoiceCandidateBuilder
{
	// Services
	private final ITaxBL taxBL = Services.get(ITaxBL.class);
	private final IProductAcctDAO productAcctDAO = Services.get(IProductAcctDAO.class);

	// Parameters:
	private final IContextAware _context;
	private I_C_ILCandHandler _invoiceCandidateHandler;
	private IVendorInvoicingInfo _vendorInvoicingInfo;
	private int invoiceDocTypeId = -1;
	/**
	 * Original invoice candidates that need to be cleared when a new invoice candidate is created by this builder.
	 *
	 * NOTE: please don't use this object in other scope then clearing. If you want more invoicing informations, please take them from {@link #_vendorInvoicingInfo}.
	 */
	private List<I_C_Invoice_Candidate> _invoiceCandidatesToClear;
	private QualityInvoiceLineGroupByTypeComparator groupsSorter;

	// Current Status:
	private IMaterialTrackingDocuments _materialTrackingDocuments = null;
	private IQualityInspectionOrder _qualityInspectionOrder;
	private int _lineNoNext = 10;

	// Result
	private final List<I_C_Invoice_Candidate> _createdInvoiceCandidates = new ArrayList<>();

	public InvoiceCandidateBuilder(final IContextAware context)
	{
		super();

		Check.assumeNotNull(context, "context not null");
		_context = context;
	}

	public void setC_ILCandHandler(final I_C_ILCandHandler invoiceCandidateHandler)
	{
		_invoiceCandidateHandler = invoiceCandidateHandler;
	}

	private I_C_ILCandHandler getC_ILCandHandler()
	{
		Check.assumeNotNull(_invoiceCandidateHandler, "invoiceCandidateHandler not null");
		return _invoiceCandidateHandler;
	}

	/**
	 * Sets the original invoice candidates that need to be cleared when a new invoice candidate is created by this builder
	 *
	 * @param invoiceCandidateToClear
	 */
	public void setInvoiceCandidatesToClear(final List<I_C_Invoice_Candidate> invoiceCandidatesToClear)
	{
		_invoiceCandidatesToClear = invoiceCandidatesToClear;
	}

	/**
	 * Sets which group types will be accepted and saved.
	 *
	 * Also, the {@link IQualityInvoiceLineGroup}s will be sorted exactly by the order of given types.
	 *
	 * @param types
	 */
	public void setQualityInvoiceLineGroupTypes(final List<QualityInvoiceLineGroupType> types)
	{
		if (types == null)
		{
			groupsSorter = null;
		}
		else
		{
			groupsSorter = new QualityInvoiceLineGroupByTypeComparator(types);
		}

	}

	/**
	 * Sets C_DocType_ID to be used when invoice will be created
	 *
	 * @param invoiceDocTypeId
	 */
	public void setInvoiceDocType_ID(final int invoiceDocTypeId)
	{
		this.invoiceDocTypeId = invoiceDocTypeId;
	}

	/**
	 * @return created invoice candidates
	 */
	public List<I_C_Invoice_Candidate> getCreatedInvoiceCandidates()
	{
		return new ArrayList<>(_createdInvoiceCandidates);
	}

	private final void addToCreatedInvoiceCandidates(final de.metas.materialtracking.model.I_C_Invoice_Candidate invoiceCandidate)
	{
		Check.assumeNotNull(invoiceCandidate, "invoiceCandidate not null");

		final IMaterialTrackingDocuments materialTrackingDocuments = getMaterialTrackingDocuments();

		//
		// Set invoice candidate's Line number
		invoiceCandidate.setLine(_lineNoNext);

		//
		// Bind Material Tracking to the invoice candidate
		invoiceCandidate.setM_Material_Tracking(materialTrackingDocuments.getM_Material_Tracking());

		//
		// Make sure the invoice candidate is saved, so that we can use its ID further down (e.g. to reference if from the detail lines)
		InterfaceWrapperHelper.save(invoiceCandidate);

		//
		// Invoice Clearing Allocation
		Check.assumeNotNull(_invoiceCandidatesToClear, "_invoiceCandidateToClear not null");
		for (final I_C_Invoice_Candidate invoiceCandidateToClear : _invoiceCandidatesToClear)
		{
			final IVendorInvoicingInfo vendorInvoicingInfo = getVendorInvoicingInfo();

			final I_C_Invoice_Clearing_Alloc newIca = InterfaceWrapperHelper.newInstance(I_C_Invoice_Clearing_Alloc.class, getContext());
			newIca.setAD_Org_ID(invoiceCandidate.getAD_Org_ID()); // workaround, the correct AD_Org_ID should already be there from getContext()
			newIca.setC_Flatrate_Term(vendorInvoicingInfo.getC_Flatrate_Term());
			newIca.setC_Invoice_Cand_ToClear(invoiceCandidateToClear);
			newIca.setC_Invoice_Candidate(invoiceCandidate);
			InterfaceWrapperHelper.save(newIca);
		}

		//
		// Link to material tracking
		{
			materialTrackingDocuments.linkModelToMaterialTracking(invoiceCandidate);
		}

		// add to created list
		_createdInvoiceCandidates.add(invoiceCandidate);

		// increment next line number to be used
		_lineNoNext += 10;
	}

	public void setVendorInvoicingInfo(final IVendorInvoicingInfo vendorInvoicingInfo)
	{
		_vendorInvoicingInfo = vendorInvoicingInfo;
	}

	/**
	 * @return vendor invoicing info; never return null
	 */
	private final IVendorInvoicingInfo getVendorInvoicingInfo()
	{
		Check.assumeNotNull(_vendorInvoicingInfo, "_vendorInvoicingInfo not null");
		return _vendorInvoicingInfo;
	}

	private final IContextAware getContext()
	{
		return _context;
	}

	private IMaterialTrackingDocuments getMaterialTrackingDocuments()
	{
		Check.assumeNotNull(_materialTrackingDocuments, "_materialTrackingDocuments not null");
		return _materialTrackingDocuments;
	}

	public void setMaterialTrackingDocuments(final IMaterialTrackingDocuments materialTrackingDocuments)
	{
		_materialTrackingDocuments = materialTrackingDocuments;
	}

	private IQualityInspectionOrder getQualityInspectionOrder()
	{
		Check.assumeNotNull(_qualityInspectionOrder, "_qualityInspectionOrder not null");
		return _qualityInspectionOrder;
	}

	public void setQualityInspectionOrder(final IQualityInspectionOrder qualityInspectionOrder)
	{
		_qualityInspectionOrder = qualityInspectionOrder;
	}

	public void create(final List<IQualityInvoiceLineGroup> qualityInvoiceLineGroups)
	{
		if (qualityInvoiceLineGroups == null || qualityInvoiceLineGroups.isEmpty())
		{
			return;
		}

		//
		// Discard not accepted groups and then sort them
		final List<IQualityInvoiceLineGroup> qualityInvoiceLineGroupsToProcess = new ArrayList<>(qualityInvoiceLineGroups);
		if (groupsSorter != null)
		{
			groupsSorter.filterAndSort(qualityInvoiceLineGroupsToProcess);
		}

		//
		// Iterate groups and create invoice candidates
		for (final IQualityInvoiceLineGroup qualityInvoiceLineGroup : qualityInvoiceLineGroupsToProcess)
		{
			create(qualityInvoiceLineGroup);
		}
	}

	private void create(final IQualityInvoiceLineGroup qualityInvoiceLineGroup)
	{
		Check.assumeNotNull(qualityInvoiceLineGroup, "qualityInvoiceLineGroup not null");

		//
		// Create Invoice Candidate from invoiceable line
		final IQualityInvoiceLine invoiceableLine = qualityInvoiceLineGroup.getInvoiceableLine();
		final I_C_Invoice_Candidate invoiceCandidate = createInvoiceCandidate(invoiceableLine);

		if (qualityInvoiceLineGroup.getInvoiceableLineOverride() != null)
		{
			final IQualityInvoiceLine invoiceableLineOverride = qualityInvoiceLineGroup.getInvoiceableLineOverride();
			final InvoiceDetailWriter detailsWriter = new InvoiceDetailWriter(invoiceCandidate);
			detailsWriter.setPrintOverride(true);
			detailsWriter.save(Collections.singletonList(invoiceableLineOverride));
		}

		//
		// Before details
		{
			final InvoiceDetailWriter detailsWriter = new InvoiceDetailWriter(invoiceCandidate);
			detailsWriter.setPrintBefore(true);
			detailsWriter.save(qualityInvoiceLineGroup.getDetailsBefore());
		}

		//
		// After details
		{
			final InvoiceDetailWriter detailsWriter = new InvoiceDetailWriter(invoiceCandidate);
			detailsWriter.setPrintBefore(false); // i.e. after
			detailsWriter.save(qualityInvoiceLineGroup.getDetailsAfter());
		}
	}

	/**
	 * Creates invoice candidate
	 *
	 * @param invoiceableLine
	 * @return invoice candidate; never return null
	 */
	private I_C_Invoice_Candidate createInvoiceCandidate(final IQualityInvoiceLine invoiceableLine)
	{
		Check.assumeNotNull(invoiceableLine, "invoiceableLine not null");

		//
		// Extract infos from Order
		final IQualityInspectionOrder order = getQualityInspectionOrder();
		final I_PP_Order ppOrder = order.getPP_Order();
		final Timestamp dateOfProduction = order.getDateOfProduction();
		final int orgId = ppOrder.getAD_Org_ID();
		final int modelTableId = InterfaceWrapperHelper.getModelTableId(ppOrder);
		final int modelRecordId = ppOrder.getPP_Order_ID();

		//
		// Extract infos from invoiceable line
		final I_M_Product product = invoiceableLine.getM_Product();
		final I_C_UOM uom = invoiceableLine.getC_UOM();
		final BigDecimal qtyOrdered = invoiceableLine.getQty();
		final String description = invoiceableLine.getDescription();
		final IPricingResult pricingResult = invoiceableLine.getPrice();
		final boolean printed = invoiceableLine.isDisplayed();

		//
		// Invoicing config
		final IVendorInvoicingInfo vendorInvoicingInfo = getVendorInvoicingInfo();

		//
		// Original Invoice Candidate
		// final I_C_Invoice_Candidate originalIc = getOriginalInvoiceCandidate();

		//
		// Create the new Invoice Candidate
		//
		// NOTE: don't link these invoice candidates to C_OrderLine because in that case
		// de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments.getOriginalInvoiceCandidates()
		// will consider them.... and we really don't want!
		final IContextAware context = getContext();
		final I_C_Invoice_Candidate newIc = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class, context);
		if (invoiceDocTypeId > 0)
		{
			newIc.setC_DocTypeInvoice_ID(invoiceDocTypeId);
		}
		newIc.setAD_Org_ID(orgId);
		newIc.setC_ILCandHandler(getC_ILCandHandler());
		newIc.setIsSOTrx(false);
		newIc.setDescription(description);
		newIc.setIsPrinted(printed);

		// document reference
		newIc.setAD_Table_ID(modelTableId);
		newIc.setRecord_ID(modelRecordId);

		// product
		newIc.setM_Product(product);

		// charge
		// int chargeId = olc.getC_Charge_ID();
		// ic.setC_Charge_ID(chargeId);

		newIc.setQtyOrdered(qtyOrdered);
		newIc.setQtyToInvoice(Env.ZERO); // to be computed
		newIc.setC_UOM(uom);

		// newIc.setDateInvoiced(DateInvoiced); // FIXME =dateOfProduction?
		newIc.setDateOrdered(order.getDateOfProduction());

		// bill partner data
		newIc.setBill_BPartner_ID(vendorInvoicingInfo.getBill_BPartner_ID());
		newIc.setBill_Location_ID(vendorInvoicingInfo.getBill_Location_ID());
		newIc.setBill_User_ID(vendorInvoicingInfo.getBill_User_ID());

		//
		// Pricing
		newIc.setM_PricingSystem_ID(pricingResult.getM_PricingSystem_ID());
		newIc.setPrice_UOM_ID(pricingResult.getPrice_UOM_ID());
		newIc.setPriceEntered(pricingResult.getPriceStd());
		newIc.setPriceActual(pricingResult.getPriceStd());
		newIc.setIsTaxIncluded(pricingResult.isTaxIncluded()); // fresh 08457: Configure new IC from pricing result
		newIc.setDiscount(pricingResult.getDiscount());
		newIc.setC_Currency_ID(pricingResult.getC_Currency_ID());

		//
		// InvoiceRule
		newIc.setInvoiceRule(vendorInvoicingInfo.getInvoiceRule());

		// Activity
		setC_Activity_ID(newIc);

		// Tax
		setC_Tax_ID(newIc, pricingResult, dateOfProduction);

		// NOTE: don't save it

		//
		// Add it to the list of created invoice candidates
		final de.metas.materialtracking.model.I_C_Invoice_Candidate newIcExt = InterfaceWrapperHelper.create(newIc, de.metas.materialtracking.model.I_C_Invoice_Candidate.class);
		addToCreatedInvoiceCandidates(newIcExt);

		//
		// Return it
		return newIc;
	}

	protected void setC_Activity_ID(final I_C_Invoice_Candidate invoiceCandidate)
	{
		// fresh_07442 activity; TODO: clarify (al: clarify what?)
		final IContextAware context = getContext();
		final I_C_Activity activity = productAcctDAO.retrieveActivityForAcct(context, invoiceCandidate.getAD_Org(), invoiceCandidate.getM_Product());
		invoiceCandidate.setC_Activity(activity);
	}

	protected void setC_Tax_ID(final I_C_Invoice_Candidate ic, final IPricingResult pricingResult, final Timestamp date)
	{
		final IContextAware contextProvider = getContext();

		final Properties ctx = contextProvider.getCtx();
		final String trxName = contextProvider.getTrxName();

		final int taxCategoryId = pricingResult.getC_TaxCategory_ID();
		final I_M_Warehouse warehouse = null; // warehouse: N/A
		final boolean isSOTrx = false;

		final int taxID = taxBL.getTax(
				ctx
				, ic
				, taxCategoryId
				, ic.getM_Product_ID()
				, -1 // C_Charge_ID
				, date // bill date
				, date // ship date
				, ic.getAD_Org_ID()
				, warehouse
				, ic.getBill_Location_ID()
				, -1 // shipPartnerLocation
				, isSOTrx
				, trxName
				);
		ic.setC_Tax_ID(taxID);
	}
}

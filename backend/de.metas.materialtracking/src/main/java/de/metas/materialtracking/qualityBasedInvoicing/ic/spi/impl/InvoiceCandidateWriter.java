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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.google.common.annotations.VisibleForTesting;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.lang.SOTrx;
import de.metas.materialtracking.IMaterialTrackingPPOrderBL;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_PP_Order;
import de.metas.materialtracking.qualityBasedInvoicing.IMaterialTrackingDocuments;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionOrder;
import de.metas.materialtracking.qualityBasedInvoicing.IVendorInvoicingInfo;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLine;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.IQualityInvoiceLineGroup;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupByTypeComparator;
import de.metas.materialtracking.qualityBasedInvoicing.invoicing.QualityInvoiceLineGroupType;
import de.metas.organization.OrgId;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.Quantity;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.IQuery.Aggregate;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.util.TrxRunnableAdapter;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;

/**
 * Takes {@link IQualityInvoiceLineGroup}s and creates {@link I_C_Invoice_Candidate}s.
 *
 * @author tsa
 */
public class InvoiceCandidateWriter
{
	// Services
	private final transient ITaxBL taxBL = Services.get(ITaxBL.class);
	private final transient IProductActivityProvider productActivityProvider = Services.get(IProductActivityProvider.class);
	private final transient IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final transient IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	// Parameters:
	private final IContextAware _context;
	private I_C_ILCandHandler _invoiceCandidateHandler;
	private IVendorInvoicingInfo _vendorInvoicingInfo;
	private int invoiceDocTypeId = -1;
	/**
	 * Original invoice candidates that need to be cleared when a new invoice candidate is created by this builder.
	 * <p>
	 * NOTE: please don't use this object in other scope then clearing. If you want more invoicing informations, please take them from {@link #_vendorInvoicingInfo}.
	 */
	private List<I_C_Invoice_Candidate> _invoiceCandidatesToClear;
	
	@Nullable
	private QualityInvoiceLineGroupByTypeComparator groupsSorter;

	// Current Status:
	private IMaterialTrackingDocuments _materialTrackingDocuments = null;
	private IQualityInspectionOrder _qualityInspectionOrder;

	private int _lineNoNext = 10;

	/**
	 * Set by the constructor to avoid deleting e.g. the 2nd, 3rd, 4th etc regular-pp-order IC by the after-commit-listener that was installed for the 1st one.
	 */
	private final int _maxInvoiceCandidateToDeleteID;

	// Result
	private final List<I_C_Invoice_Candidate> _createdInvoiceCandidates = new ArrayList<>();

	public InvoiceCandidateWriter(final IContextAware context)
	{
		super();

		Check.assumeNotNull(context, "context not null");
		_context = context;

		_maxInvoiceCandidateToDeleteID = queryBL.createQueryBuilder(I_C_Invoice_Candidate.class, context)
				.create()
				.aggregate(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID, Aggregate.MAX, Integer.class);
	}

	public InvoiceCandidateWriter setC_ILCandHandler(final I_C_ILCandHandler invoiceCandidateHandler)
	{
		_invoiceCandidateHandler = invoiceCandidateHandler;
		return this;
	}

	private I_C_ILCandHandler getC_ILCandHandler()
	{
		Check.assumeNotNull(_invoiceCandidateHandler, "invoiceCandidateHandler not null");
		return _invoiceCandidateHandler;
	}

	/**
	 * Sets the original invoice candidates that need to be cleared when a new invoice candidate is created by this builder
	 */
	public InvoiceCandidateWriter setInvoiceCandidatesToClear(final List<I_C_Invoice_Candidate> invoiceCandidatesToClear)
	{
		_invoiceCandidatesToClear = invoiceCandidatesToClear;
		return this;
	}

	/**
	 * Sets which group types will be accepted and saved.
	 * <p>
	 * Also, the {@link IQualityInvoiceLineGroup}s will be sorted exactly by the order of given types.
	 */
	public InvoiceCandidateWriter setQualityInvoiceLineGroupTypes(final List<QualityInvoiceLineGroupType> types)
	{
		if (types == null)
		{
			groupsSorter = null;
		}
		else
		{
			groupsSorter = new QualityInvoiceLineGroupByTypeComparator(types);
		}
		return this;
	}

	/**
	 * Sets C_DocType_ID to be used when invoice will be created
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

	private void addToCreatedInvoiceCandidates(@NonNull final I_C_Invoice_Candidate invoiceCandidate)
	{
		final boolean newIc = InterfaceWrapperHelper.isNew(invoiceCandidate);

		//
		// Make sure the invoice candidate is saved, so that we can use its ID further down (e.g. to reference if from the detail lines)
		InterfaceWrapperHelper.save(invoiceCandidate);

		Loggables.addLog((newIc ? "Created new IC " : "Updated existing IC ") + invoiceCandidate);

		//
		// delete/recreate Invoice Clearing Allocation
		Check.assumeNotNull(_invoiceCandidatesToClear, "_invoiceCandidateToClear not null");
		for (final I_C_Invoice_Candidate invoiceCandidateToClear : _invoiceCandidatesToClear)
		{
			final IVendorInvoicingInfo vendorInvoicingInfo = getVendorInvoicingInfo();

			for (final I_C_Invoice_Clearing_Alloc ica : flatrateDB.retrieveClearingAllocs(invoiceCandidate))
			{
				InterfaceWrapperHelper.delete(ica);
			}

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
			final IMaterialTrackingDocuments materialTrackingDocuments = getMaterialTrackingDocuments();
			materialTrackingDocuments.linkModelToMaterialTracking(invoiceCandidate);
		}

		// add to created list
		_createdInvoiceCandidates.add(invoiceCandidate);

		// increment next line number to be used
		_lineNoNext += 10;
	}

	public InvoiceCandidateWriter setVendorInvoicingInfo(final IVendorInvoicingInfo vendorInvoicingInfo)
	{
		_vendorInvoicingInfo = vendorInvoicingInfo;
		return this;
	}

	/**
	 * @return vendor invoicing info; never return null
	 */
	private IVendorInvoicingInfo getVendorInvoicingInfo()
	{
		Check.assumeNotNull(_vendorInvoicingInfo, "_vendorInvoicingInfo not null");
		return _vendorInvoicingInfo;
	}

	private IContextAware getContext()
	{
		return _context;
	}

	private IMaterialTrackingDocuments getMaterialTrackingDocuments()
	{
		Check.assumeNotNull(_materialTrackingDocuments, "_materialTrackingDocuments not null");
		return _materialTrackingDocuments;
	}

	public InvoiceCandidateWriter setMaterialTrackingDocuments(final IMaterialTrackingDocuments materialTrackingDocuments)
	{
		_materialTrackingDocuments = materialTrackingDocuments;
		return this;
	}

	private IQualityInspectionOrder getQualityInspectionOrder()
	{
		Check.assumeNotNull(_qualityInspectionOrder, "_qualityInspectionOrder not null");
		return _qualityInspectionOrder;
	}

	public InvoiceCandidateWriter setQualityInspectionOrder(final IQualityInspectionOrder qualityInspectionOrder)
	{
		Check.assume(qualityInspectionOrder.isQualityInspection(),
				"Given qualityInspectionOrder {} is a *real* quality inspection order", qualityInspectionOrder);
		_qualityInspectionOrder = qualityInspectionOrder;
		return this;
	}

	public InvoiceCandidateWriter create(final List<IQualityInvoiceLineGroup> qualityInvoiceLineGroups)
	{
		if (qualityInvoiceLineGroups == null || qualityInvoiceLineGroups.isEmpty())
		{
			return this;
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

		return this;
	}

	private void create(final IQualityInvoiceLineGroup qualityInvoiceLineGroup)
	{
		//
		// Create Invoice Candidate from invoiceable line
		final I_C_Invoice_Candidate invoiceCandidate = createInvoiceCandidate(qualityInvoiceLineGroup);

		invoiceCandDAO.deleteInvoiceDetails(invoiceCandidate);

		if (qualityInvoiceLineGroup.getInvoiceableLineOverride() != null)
		{
			final IQualityInvoiceLine invoiceableLineOverride = qualityInvoiceLineGroup.getInvoiceableLineOverride();
			final InvoiceDetailWriter detailsWriter = new InvoiceDetailWriter(invoiceCandidate);
			detailsWriter.setPrintOverride(true);
			detailsWriter.saveLines(Collections.singletonList(invoiceableLineOverride));
		}

		//
		// Before details
		{
			final InvoiceDetailWriter detailsWriter = new InvoiceDetailWriter(invoiceCandidate);
			detailsWriter.setPrintBefore(true);
			detailsWriter.saveLines(qualityInvoiceLineGroup.getDetailsBefore());
		}

		//
		// After details
		{
			final InvoiceDetailWriter detailsWriter = new InvoiceDetailWriter(invoiceCandidate);
			detailsWriter.setPrintBefore(false); // i.e. after
			detailsWriter.saveLines(qualityInvoiceLineGroup.getDetailsAfter());
		}
	}

	/**
	 * Creates invoice candidate
	 *
	 * @return invoice candidate; never returns <code>null</code>
	 */
	private I_C_Invoice_Candidate createInvoiceCandidate(@NonNull final IQualityInvoiceLineGroup qualityInvoiceLineGroup)
	{
		final IMaterialTrackingPPOrderBL materialTrackingPPOrderBL = Services.get(IMaterialTrackingPPOrderBL.class);

		final IQualityInvoiceLine invoiceableLine = qualityInvoiceLineGroup.getInvoiceableLine();
		Check.assumeNotNull(invoiceableLine, "invoiceableLine not null");

		//
		// Delete existing invoice candidates (if any)
		deleteExistingInvoiceCandidates(qualityInvoiceLineGroup);

		//
		// Extract infos from Order
		final IQualityInspectionOrder order = getQualityInspectionOrder();
		final I_PP_Order ppOrder = order.getPP_Order();
		final int orgId = ppOrder.getAD_Org_ID();
		final int modelTableId = InterfaceWrapperHelper.getModelTableId(ppOrder);
		final int modelRecordId = ppOrder.getPP_Order_ID();

		//
		// Extract infos from invoiceable line
		final I_M_Product product = invoiceableLine.getM_Product();
		final Quantity qty = invoiceableLine.getQty();
		final String description = invoiceableLine.getDescription();
		final IPricingResult pricingResult = invoiceableLine.getPrice();
		final boolean printed = invoiceableLine.isDisplayed();

		//
		// Invoicing config
		final IVendorInvoicingInfo vendorInvoicingInfo = getVendorInvoicingInfo();
		final I_M_PriceList_Version priceListVersion = vendorInvoicingInfo.getM_PriceList_Version();
		Check.errorUnless(priceListVersion.getM_PriceList_Version_ID() == PriceListVersionId.toRepoId(pricingResult.getPriceListVersionId()),
				"The plv of vendorInvoicingInfo {} shall have the same M_PriceList_Version_ID as the pricingResult {} of the current invoiceableLine {}",
				vendorInvoicingInfo, pricingResult, invoiceableLine);

		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.newInstance(I_C_Invoice_Candidate.class, getContext());

		//
		// Create the new Invoice Candidate
		//
		// NOTE: don't link these invoice candidates to C_OrderLine because in that case
		// IMaterialTrackingDocuments.getOriginalInvoiceCandidates()
		// will consider them; and we really don't want!

		if (invoiceDocTypeId > 0)
		{
			ic.setC_DocTypeInvoice_ID(invoiceDocTypeId);
		}
		ic.setAD_Org_ID(orgId);
		ic.setC_ILCandHandler(getC_ILCandHandler());
		ic.setIsSOTrx(false);
		ic.setDescription(description);
		ic.setIsPrinted(printed);

		// document reference
		ic.setAD_Table_ID(modelTableId);
		ic.setRecord_ID(modelRecordId);

		// product
		ic.setM_Product_ID(product.getM_Product_ID());

		// charge
		// int chargeId = olc.getC_Charge_ID();
		// ic.setC_Charge_ID(chargeId);

		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
		final Quantity qtyOrdered = uomConversionBL.convertToProductUOM(qty, ProductId.ofRepoId(product.getM_Product_ID()));
		ic.setQtyOrdered(qtyOrdered.toBigDecimal());

		ic.setQtyToInvoice(BigDecimal.ZERO); // to be computed
		ic.setQtyEntered(qty.toBigDecimal());
		ic.setC_UOM_ID(UomId.toRepoId(qty.getUomId()));

		ic.setDateOrdered(materialTrackingPPOrderBL.getDateOfProduction(order.getPP_Order()));

		// bill partner data
		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.setFrom(vendorInvoicingInfo.getBillLocation());

		//
		// Pricing
		ic.setM_PricingSystem_ID(PricingSystemId.toRepoId(pricingResult.getPricingSystemId()));
		ic.setM_PriceList_Version_ID(PriceListVersionId.toRepoId(pricingResult.getPriceListVersionId()));
		ic.setPrice_UOM_ID(UomId.toRepoId(pricingResult.getPriceUomId()));
		ic.setPriceEntered(pricingResult.getPriceStd());
		ic.setPriceActual(pricingResult.getPriceStd());
		ic.setIsTaxIncluded(pricingResult.isTaxIncluded()); // 08457: Configure new IC from pricing result
		ic.setDiscount(pricingResult.getDiscount().toBigDecimal());
		ic.setC_Currency_ID(pricingResult.getCurrencyRepoId());

		// InvoiceRule
		ic.setInvoiceRule(vendorInvoicingInfo.getInvoiceRule());

		// Activity
		setC_Activity_ID(ic);

		// Tax
		setC_Tax_ID(ic, pricingResult, priceListVersion.getValidFrom());

		// Set invoice candidate's Line number
		ic.setLine(_lineNoNext);

		// Bind Material Tracking to the invoice candidate
		final IMaterialTrackingDocuments materialTrackingDocuments = getMaterialTrackingDocuments();
		ic.setM_Material_Tracking(materialTrackingDocuments.getM_Material_Tracking());

		// task 09655
		ic.setQualityInvoiceLineGroupType(qualityInvoiceLineGroup.getQualityInvoiceLineGroupType().getAD_Ref_List_Value());

		//
		ic.setProcessed(false); // in the DB it's processed=false by default, but for decoupled AIts we need to set is explicitly, in order to select ICs by this flag

		// NOTE: don't save it

		// Add it to the list of created invoice candidates
		addToCreatedInvoiceCandidates(ic);

		// Return it
		return ic;
	}

	/**
	 * If there are any preexisting ICs to be come obsolete because of our new
	 * <p>
	 * task http://dewiki908/mediawiki/index.php/09655_Karottenabrechnung_mehrfache_Zeilen_%28105150975301%29
	 */
	private void deleteExistingInvoiceCandidates(final IQualityInvoiceLineGroup qualityInvoiceLineGroup)
	{
		//
		// first extract "primitive" data to be used inside the trx listener. We don't want any references to
		// live data models that would be stale/inconsistent when the listener is fired

		// to filter by quality inspection order
		final IQualityInspectionOrder order = getQualityInspectionOrder();
		final I_PP_Order ppOrder = order.getPP_Order();
		final int modelTableId = InterfaceWrapperHelper.getModelTableId(ppOrder);
		final int modelRecordId = ppOrder.getPP_Order_ID();

		// to filter by QualityInvoiceLineGroupType
		final QualityInvoiceLineGroupType type = qualityInvoiceLineGroup.getQualityInvoiceLineGroupType();

		final IVendorInvoicingInfo vendorInvoicingInfo = getVendorInvoicingInfo();
		final int priceListVersionID = vendorInvoicingInfo.getM_PriceList_Version().getM_PriceList_Version_ID();

		Check.errorUnless(this.invoiceDocTypeId > 0, "{} needs to have the invoiceDocTypeId set to >0 when we delete old ICs", this);
		final int invoiceDocTypeId = this.invoiceDocTypeId; // copy it, who knows that the value will be at the time we invoice the listener

		//
		// secondly, invoke the listener code
		// TODO: check if we actually have to run this code afterCommit, since we now have _maxInvoiceCandidateToDeleteID to make sure we only delete "old" ICs.
		trxManager
				.getTrxListenerManagerOrAutoCommit(getContext().getTrxName())
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> {

					trxManager.runInNewTrx(new TrxRunnableAdapter()
					{
						@Override
						public void run(final String localTrxName) throws Exception
						{
							deleteExistingInvoiceCandidates0(modelTableId,
									modelRecordId,
									type,
									priceListVersionID,
									invoiceDocTypeId,
									_maxInvoiceCandidateToDeleteID,
									localTrxName);
						}
					});
				});
	}

	private void deleteExistingInvoiceCandidates0(final int modelTableId,
			final int modelRecordId,
			final QualityInvoiceLineGroupType type,
			final int priceListVersionID,
			final int docTypeInvoiceID,
			final int maxInvoiceCandidateID,
			final String localTrxName)
	{
		final IQueryBuilder<I_C_Invoice_Candidate> queryBuilder = queryBL
				.createQueryBuilder(I_C_Invoice_Candidate.class, getContext().getCtx(), localTrxName);

		//
		// Only delete older ICs, not the ones we only just created!
		{
			queryBuilder.addCompareFilter(I_C_Invoice_Candidate.COLUMNNAME_C_Invoice_Candidate_ID, Operator.LESS_OR_EQUAL, maxInvoiceCandidateID);
		}

		//
		// Only delete ICs with the same DocType; required for the case that there are unprocessed Downpayment ("Akonto") ICs while we already deal with an Endabrechnung PP_Order
		{
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_C_DocTypeInvoice_ID, docTypeInvoiceID);
		}

		//
		// Filter by PLV; there might be other ICs for other PLVs
		{
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_M_PriceList_Version_ID, priceListVersionID);
		}

		//
		// Filter by quality inspection order
		{
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_AD_Table_ID, modelTableId);
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Record_ID, modelRecordId);
		}

		//
		// Filter by QualityInvoiceLineGroupType
		{
			Check.assumeNotNull(type, "QualityInvoiceLineGroupType not null");
			queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_QualityInvoiceLineGroupType, type.getAD_Ref_List_Value());
		}

		//
		// Filter by Product
		// => don't do it because we want to support product changing in configuration

		//
		// Avoid invoiced(i.e. processed) invoice candidates
		queryBuilder.addEqualsFilter(I_C_Invoice_Candidate.COLUMNNAME_Processed, false);

		//
		// Retrieve the existing invoice candidates
		final List<I_C_Invoice_Candidate> invoiceCandidates = queryBuilder.create().list(I_C_Invoice_Candidate.class);
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			Check.assume(!ic.isProcessed(), "Invoice candidate not already processed: {}", ic);
			Check.assume(ic.getQtyInvoiced().signum() == 0, "Invoice candidate's QtyInvoiced is zero: {}", ic);

			invoiceCandDAO.deleteAndAvoidRecreateScheduling(ic);
		}
	}

	/**
	 * @param invoiceCandidate
	 * @task 07442
	 */
	@VisibleForTesting
	protected void setC_Activity_ID(final I_C_Invoice_Candidate invoiceCandidate)
	{
		final ActivityId activityId = productActivityProvider.getActivityForAcct(
				ClientId.ofRepoId(invoiceCandidate.getAD_Client_ID()),
				OrgId.ofRepoId(invoiceCandidate.getAD_Org_ID()),
				ProductId.ofRepoId(invoiceCandidate.getM_Product_ID()));

		invoiceCandidate.setC_Activity_ID(ActivityId.toRepoId(activityId));
	}

	@VisibleForTesting
	protected void setC_Tax_ID(final I_C_Invoice_Candidate ic, final IPricingResult pricingResult, final Timestamp date)
	{
		final IContextAware contextProvider = getContext();

		final TaxCategoryId taxCategoryId = pricingResult.getTaxCategoryId();

		// TODO: we should use shipPartnerLocation
		final BPartnerLocationAndCaptureId billToLocation = InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.getBPartnerLocationAndCaptureId();
		final VatCodeId vatCodeId = VatCodeId.ofRepoIdOrNull(firstGreaterThanZero(ic.getC_VAT_Code_Override_ID(), ic.getC_VAT_Code_ID()));

		final TaxId taxID = taxBL.getTaxNotNull(
				ic,
				taxCategoryId,
				ic.getM_Product_ID(),
				date, // ship date
				OrgId.ofRepoId(ic.getAD_Org_ID()),
				(WarehouseId)null,
				billToLocation, // shipPartnerLocation TODO
				SOTrx.PURCHASE,
				vatCodeId);
		ic.setC_Tax_ID(taxID.getRepoId());
	}
}

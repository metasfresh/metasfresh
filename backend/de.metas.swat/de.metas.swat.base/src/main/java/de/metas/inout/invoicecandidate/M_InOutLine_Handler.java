package de.metas.inout.invoicecandidate;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getCtx;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.document.engine.DocStatus;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.order.IOrderLineBL;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingContext;
import de.metas.pricing.IPricingResult;
import de.metas.pricing.exceptions.ProductNotOnPriceListException;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.ImmutableMapEntry;
import de.metas.util.Services;
import de.metas.util.lang.CoalesceUtil;
import lombok.NonNull;

/**
 * Creates {@link I_C_Invoice_Candidate}s from {@link I_M_InOutLine}s which do not reference an order line.
 */
public class M_InOutLine_Handler extends AbstractInvoiceCandidateHandler
{
	//
	// Services
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);

	/**
	 * @return {@code false}, but note that this handler will be invoked to create missing invoice candidates via {@link M_InOut_Handler#expandRequest(InvoiceCandidateGenerateRequest)}.
	 */
	@Override
	public boolean isCreateMissingCandidatesAutomatically()
	{
		return false;
	}

	/**
	 * @return {@code false}, but note that this handler will be invoked to create missing invoice candidates via {@link M_InOut_Handler#expandRequest(InvoiceCandidateGenerateRequest)}.
	 */
	@Override
	public boolean isCreateMissingCandidatesAutomatically(Object model)
	{
		return false;
	}

	/**
	 * @see M_InOut_Handler#expandRequest(InvoiceCandidateGenerateRequest)
	 */
	@Override
	public Object getModelForInvoiceCandidateGenerateScheduling(final Object model)
	{
		//
		// Retrieve inout
		final I_M_InOutLine inoutLine = create(model, I_M_InOutLine.class);
		final org.compiere.model.I_M_InOut inout = inoutLine.getM_InOut();
		return inout;
	}

	@Override
	public Iterator<org.compiere.model.I_M_InOutLine> retrieveAllModelsWithMissingCandidates(final int limit)
	{
		final InOutLinesWithMissingInvoiceCandidate dao = SpringContextHolder.instance.getBean(InOutLinesWithMissingInvoiceCandidate.class);
		return dao.retrieveLinesThatNeedAnInvoiceCandidate(limit);
	}

	@Override
	public InvoiceCandidateGenerateResult createCandidatesFor(@NonNull final InvoiceCandidateGenerateRequest request)
	{
		final I_M_InOutLine inOutLine = request.getModel(I_M_InOutLine.class);

		final List<I_C_Invoice_Candidate> invoiceCandidates = createCandidatesForInOutLine(inOutLine);
		return InvoiceCandidateGenerateResult.of(this, invoiceCandidates);
	}

	/**
	 * Creates a list of invoice candidates; the list often contains 1 element, but for example
	 * <li>might also be empty, if there was no need to create any invoice candidate
	 * <li>or might have multiple items in case of a packaging inOutLine where the related material lists have different payment terms.
	 */
	@VisibleForTesting
	List<I_C_Invoice_Candidate> createCandidatesForInOutLine(@NonNull final I_M_InOutLine inOutLine)
	{
		// Don't create any invoice candidate if already created
		if (inOutLine.isInvoiceCandidate())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<I_C_Invoice_Candidate> createdInvoiceCandidates = ImmutableList.builder();

		if (inOutLine.isPackagingMaterial() && inOutLine.getM_InOut().isSOTrx()) // split only in case of sales
		{
			createdInvoiceCandidates.addAll(createCandidatesBasedOnReferencingInOutLines(inOutLine));
		}
		else
		{
			final PaymentTermId paymentTermId = extractPaymentTermIdOrNull(inOutLine);
			final I_C_Invoice_Candidate ic = createInvoiceCandidateForInOutLineOrNull(inOutLine, paymentTermId, null);
			addIfNotNullAndReturnQty(createdInvoiceCandidates, ic);
		}
		return createdInvoiceCandidates.build();
	}

	private List<I_C_Invoice_Candidate> createCandidatesBasedOnReferencingInOutLines(
			@NonNull final I_M_InOutLine inOutLine)
	{
		final List<I_M_InOutLine> referencingLines = retrieveActiveReferencingInoutLines(inOutLine);

		final ImmutableListMultimap<PaymentTermId, I_M_InOutLine> paymentTermId2referencingLines = //
				referencingLines.stream()
						.map(referencingLine -> GuavaCollectors.entry(
								extractPaymentTermIdOrNull(referencingLine),
								referencingLine))
						.filter(ImmutableMapEntry::isKeyNotNull)
						.collect(GuavaCollectors.toImmutableListMultimap());

		BigDecimal qtyLeftToAllocate = inOutLine.getMovementQty();

		PaymentTermId lastPaymentTermId = null; // will be used if some qty is left after we iterated all paymentTermIds

		// needed to figure out when we are looking at the last paymentTermId
		final int numberOfPaymentTermIds = paymentTermId2referencingLines.keySet().size();
		int counter = 0;

		final ImmutableList.Builder<I_C_Invoice_Candidate> createdInvoiceCandidates = ImmutableList.builder();

		for (final PaymentTermId paymentTermId : paymentTermId2referencingLines.keySet())
		{
			counter++;

			// for the last IC, we allocate all that we got left;
			final boolean lastIcToCreate = counter >= numberOfPaymentTermIds;
			final BigDecimal forcedQtyToAllocate = lastIcToCreate ? qtyLeftToAllocate : null;

			final I_C_Invoice_Candidate ic = createInvoiceCandidateForInOutLineOrNull(inOutLine, paymentTermId, forcedQtyToAllocate);

			final BigDecimal allocatedQty = addIfNotNullAndReturnQty(createdInvoiceCandidates, ic);
			qtyLeftToAllocate = qtyLeftToAllocate.subtract(allocatedQty.abs());

			lastPaymentTermId = paymentTermId;
		}

		// maybe we didn't have any paymentTermId>0 at all; in this case, we now explicitly create one IC
		if (qtyLeftToAllocate.signum() > 0)
		{
			final I_C_Invoice_Candidate ic = createInvoiceCandidateForInOutLineOrNull(inOutLine, lastPaymentTermId, qtyLeftToAllocate);
			final BigDecimal allocatedQty = addIfNotNullAndReturnQty(createdInvoiceCandidates, ic);

			final boolean qtyLeftShouldHaveBeenAllocated = DocStatus.ofCode(inOutLine.getM_InOut().getDocStatus()).isCompletedOrClosed();
			final boolean qtyLeftWasAllocated = qtyLeftToAllocate.abs().compareTo(allocatedQty.abs()) == 0;

			Check.errorIf(qtyLeftShouldHaveBeenAllocated && !qtyLeftWasAllocated,
					"We invoked createInvoiceCandidateForInOutLineOrNull with forcedQtyOrdered={}, but allocatedQty={}; inOutLine={}; inOut={}",
					qtyLeftToAllocate, allocatedQty, inOutLine, inOutLine.getM_InOut());
		}

		return createdInvoiceCandidates.build();
	}

	private List<I_M_InOutLine> retrieveActiveReferencingInoutLines(@NonNull final I_M_InOutLine inOutLine)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final List<I_M_InOutLine> referencingLines = inOutDAO
				.retrieveAllReferencingLinesBuilder(inOutLine)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_InOutLine.class);
		return referencingLines;
	}

	private BigDecimal addIfNotNullAndReturnQty(
			@NonNull final ImmutableList.Builder<I_C_Invoice_Candidate> createdInvoiceCandidates,
			@Nullable final I_C_Invoice_Candidate ic)
	{
		if (ic != null)
		{
			createdInvoiceCandidates.add(ic);
			return ic.getQtyDelivered();
		}
		return ZERO;
	}

	@VisibleForTesting
	enum Mode
	{
		CREATE, UPDATE
	}

	private I_C_Invoice_Candidate createInvoiceCandidateForInOutLineOrNull(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@Nullable final PaymentTermId paymentTermId,
			@Nullable BigDecimal forcedQtyToAllocate)
	{

		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final I_M_InOut inOut = create(inOutLineRecord.getM_InOut(), I_M_InOut.class);
		final I_C_Invoice_Candidate icRecord = newInstance(I_C_Invoice_Candidate.class, inOutLineRecord);

		// extractQtyDelivered() depends on the C_PaymentTerm to be set
		icRecord.setC_PaymentTerm_ID(PaymentTermId.toRepoId(paymentTermId));

		TableRecordCacheLocal.setReferencedValue(icRecord, inOutLineRecord);
		icRecord.setIsPackagingMaterial(inOutLineRecord.isPackagingMaterial());

		// order & delivery stuff
		final boolean callerCanCreateAdditionalICs = true; // our calling code will create further ICs if needed
		setOrderedData(icRecord, forcedQtyToAllocate, callerCanCreateAdditionalICs);
		if (icRecord.getQtyOrdered().signum() == 0)
		{
			return null; // we won't create a new IC that has qtyOrdered=zero right from the start
		}

		//
		// Product & Charge - product and its UOM are needed when setting the delivered data
		final ProductId productId = ProductId.ofRepoId(inOutLineRecord.getM_Product_ID());
		final int chargeId = inOutLineRecord.getC_Charge_ID();
		{
			icRecord.setM_Product_ID(productId.getRepoId());
			icRecord.setC_Charge_ID(chargeId);
		}
		icRecord.setC_UOM_ID(inOutLineRecord.getC_UOM_ID());

		// we use our nice&generic code to set the "real" delivered data, and for that, we need the IC to be saved
		// setDeliveredData(ic);
		// .. however, we need the movementQty to be present in the IC for createCandidatesForInOutLine to work:
		icRecord.setQtyDelivered(icRecord.getQtyOrdered());

		final ClientId clientId = ClientId.ofRepoId(inOutLineRecord.getAD_Client_ID());

		final OrgId orgId = OrgId.ofRepoId(inOutLineRecord.getAD_Org_ID());
		icRecord.setAD_Org_ID(orgId.getRepoId());

		icRecord.setC_ILCandHandler(getHandlerRecord());

		//
		// Handle Transaction Type: Shipment / Receipt
		final boolean isSOTrx = inOut.isSOTrx();
		icRecord.setIsSOTrx(isSOTrx); // 05265

		//
		// Set the bill related details
		{
			setBPartnerData(icRecord, inOutLineRecord);
		}

		icRecord.setQtyToInvoice(ZERO); // to be computed

		//
		// Pricing Informations
		final PriceAndTax priceAndQty = calculatePriceAndQuantityAndUpdate(icRecord, inOutLineRecord);

		//
		// Description
		icRecord.setDescription(inOut.getDescription());

		//
		// Set invoice rule form linked order (if exists)
		if (inOut.getC_Order_ID() > 0)
		{
			icRecord.setInvoiceRule(inOut.getC_Order().getInvoiceRule()); // the rule set in order
		}
		// Set Invoice Rule from BPartner
		else
		{
			final I_C_BPartner billBPartner = bpartnerDAO.getById(icRecord.getBill_BPartner_ID());
			final String invoiceRule = billBPartner.getInvoiceRule();
			if (!Check.isEmpty(invoiceRule))
			{
				icRecord.setInvoiceRule(invoiceRule);
			}
			else
			{
				icRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate); // Immediate
			}
		}

		//
		// Set C_Activity from Product (07442)
		final ActivityId activityId = Services.get(IProductAcctDAO.class).retrieveActivityForAcct(clientId, orgId, productId);
		icRecord.setC_Activity_ID(ActivityId.toRepoId(activityId));

		//
		// Set C_Tax from Product (07442)
		final Properties ctx = getCtx(inOutLineRecord);
		final TaxCategoryId taxCategoryId = priceAndQty != null ? priceAndQty.getTaxCategoryId() : null;
		final Timestamp shipDate = inOut.getMovementDate();
		final int locationId = inOut.getC_BPartner_Location_ID();

		final int taxId = Services.get(ITaxBL.class).getTax(
				ctx,
				icRecord,
				taxCategoryId,
				productId.getRepoId(),
				shipDate,
				orgId,
				WarehouseId.ofRepoId(inOut.getM_Warehouse_ID()),
				locationId, // shipC_BPartner_Location_ID
				isSOTrx);
		icRecord.setC_Tax_ID(taxId);

		//
		// Save the Invoice Candidate, so that we can use it's ID further down
		saveRecord(icRecord);

		// set Quality Issue Percentage Override

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(inOutLineRecord.getM_AttributeSetInstance_ID());
		final ImmutableAttributeSet attributes = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asiId);

		Services.get(IInvoiceCandBL.class).setQualityDiscountPercent_Override(icRecord, attributes);

		//
		// Update InOut Line and flag it as Invoice Candidate generated
		inOutLineRecord.setIsInvoiceCandidate(true);
		saveRecord(inOutLineRecord);

		//
		// Create IC-IOL association (07969)
		// Even if our IC is directly linked to M_InOutLine (by AD_Table_ID/Record_ID),
		// we need this association in order to let our engine know this and create the M_MatchInv records.
		final I_C_InvoiceCandidate_InOutLine iciol = newInstance(I_C_InvoiceCandidate_InOutLine.class, icRecord);
		if (icRecord.isPackagingMaterial())
		{
			// icRecord might be one of many Ics that are associated with inOutLineRecord
			iciol.setC_Invoice_Candidate_ID(icRecord.getC_Invoice_Candidate_ID());
			iciol.setM_InOutLine_ID(inOutLineRecord.getM_InOutLine_ID());
			iciol.setC_UOM_ID(icRecord.getC_UOM_ID());

			// this is a bit hacky; we might have negated qtyOrdered; in that case, we need to re-negate it here back to the way it was
			final BigDecimal qtyMultiplier = getQtyMultiplier(icRecord);
			iciol.setQtyDelivered(icRecord.getQtyOrdered().multiply(qtyMultiplier));
			iciol.setQtyDeliveredInUOM_Nominal(icRecord.getQtyEntered().multiply(qtyMultiplier));

			saveRecord(iciol);
		}
		else
		{
			// the common case; icRecord is the only one associated with inOutLineRecord; and inOutLineRecord *might* have a catch quantity
			iciol.setC_Invoice_Candidate(icRecord);
			Services.get(IInvoiceCandBL.class).updateICIOLAssociationFromIOL(iciol, inOutLineRecord);
		}
		return icRecord;
	}

	@Override
	public void invalidateCandidatesFor(final Object model)
	{
		final I_M_InOutLine inoutLine = create(model, I_M_InOutLine.class);
		invalidateCandidateForInOutLine(inoutLine);
	}

	private void invalidateCandidateForInOutLine(final I_M_InOutLine inoutLine)
	{
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = invoiceCandDAO.retrieveInvoiceCandidatesForInOutLineQuery(inoutLine);

		invoiceCandDAO.invalidateCandsFor(icQueryBuilder);
	}

	@Override
	public String getSourceTable()
	{
		return org.compiere.model.I_M_InOutLine.Table_Name;
	}

	@Override
	public boolean isUserInChargeUserEditable()
	{
		return false;
	}

	/**
	 * Qty Sign Multiplier
	 *
	 * @param ic
	 * @return
	 *         <ul>
	 *         <li>+1 on regular shipment/receipt
	 *         <li>-1 on material returns
	 *         </ul>
	 */
	private BigDecimal getQtyMultiplier(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inoutLine = getM_InOutLine(ic);
		final org.compiere.model.I_M_InOut inout = inoutLine.getM_InOut();
		final String movementType = inout.getMovementType();

		if (inOutBL.isReturnMovementType(movementType))
		{
			return ONE.negate();
		}
		return ONE;
	}

	/**
	 * <ul>
	 * <li>QtyOrdered := M_InOutLine.QtyDelivered
	 * <li>DateOrdered := if the inOutLine's M_InOut references an order, then that order's DateOrdered. Otherwise the MovementDate of inOutLine's M_InOut.
	 * <li>C_Order_ID: the C_Order_ID (if any) of the inOutLine's M_InOut
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setOrderedData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setOrderedData(@NonNull final I_C_Invoice_Candidate ic)
	{
		// we won't create another IC, so the method we call needs to allocate it all to the given IC
		final boolean callerCanCreateAdditionalICs = false;
		setOrderedData(ic, null/* forceQtyOrdered */, callerCanCreateAdditionalICs);
	}

	private void setOrderedData(
			@NonNull final I_C_Invoice_Candidate icRecord,
			@Nullable BigDecimal forcedQtyOrdered,
			final boolean callerCanCreateAdditionalICs)
	{
		final I_M_InOutLine inOutLine = getM_InOutLine(icRecord);
		final org.compiere.model.I_M_InOut inOut = inOutLine.getM_InOut();

		final I_C_Order order = inOut.getC_Order();

		if (inOut.getC_Order_ID() > 0)
		{
			icRecord.setC_Order(order);  // also set the order; even if the iol does not directly refer to an order line, it is there because of that order
			icRecord.setDateOrdered(order.getDateOrdered());
		}
		else if (icRecord.getC_Order_ID() <= 0)
		{
			// don't attempt to "clear" the order data if it is already set/known.
			icRecord.setC_Order(null);
			icRecord.setDateOrdered(inOut.getMovementDate());
		}

		final DocStatus docStatus = DocStatus.ofCode(inOut.getDocStatus());
		if (docStatus.isCompletedOrClosed())
		{
			final BigDecimal qtyMultiplier = getQtyMultiplier(icRecord);
			final BigDecimal qtyOrdered = CoalesceUtil.coalesceSuppliers(
					() -> forcedQtyOrdered,
					() -> extractQtyDelivered(icRecord, callerCanCreateAdditionalICs));

			final BigDecimal qtyDelivered = qtyOrdered.multiply(qtyMultiplier);
			icRecord.setQtyOrdered(qtyDelivered);

			final BigDecimal qtyEntered = inOutLine.getQtyEntered().multiply(qtyMultiplier);
			icRecord.setQtyEntered(qtyEntered);
		}
		else
		{
			// not yet delivered (e.g. IP), reversed, voided etc. Set qty to zero.
			icRecord.setQtyOrdered(ZERO);
			icRecord.setQtyEntered(ZERO);
		}
	}

	@VisibleForTesting
	BigDecimal extractQtyDelivered(
			@NonNull final I_C_Invoice_Candidate ic,
			final boolean callerCanCreateAdditionalICs)
	{
		if (!ic.isPackagingMaterial())
		{
			final I_M_InOutLine inOutLine = getM_InOutLine(ic);
			return inOutLine.getMovementQty();
		}

		final I_M_InOutLine packagingInOutLine = getM_InOutLine(ic);

		// get all material lines that reference to packagingInOutLine
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);
		final List<I_M_InOutLine> referencingInOutLines = inOutDAO.retrieveAllReferencingLinesBuilder(packagingInOutLine)
				.addOnlyActiveRecordsFilter()
				.create()
				.list(I_M_InOutLine.class);
		if (referencingInOutLines.isEmpty())
		{
			final I_M_InOutLine inOutLine = getM_InOutLine(ic);
			return inOutLine.getMovementQty();
		}

		final PaymentTermId icPaymentTermId = PaymentTermId.ofRepoIdOrNull(ic.getC_PaymentTerm_ID());

		BigDecimal qtyDeliveredLeftToAllocateForAnyPaymentTerm = packagingInOutLine.getMovementQty();
		BigDecimal qtyDeliveredLeftToAllocateForCurentICsPaymentTerm = packagingInOutLine.getMovementQty();
		BigDecimal qtyAllocatedViaReferencingInoutLines = ZERO;
		for (final I_M_InOutLine referencingInOutLine : referencingInOutLines)
		{
			final BigDecimal qtyOfReferencingInOutLine = referencingInOutLine.getQtyEnteredTU();

			if (inoutLineFitsWithPaymentTermId(referencingInOutLine, icPaymentTermId))
			{
				final BigDecimal qtyToAddForThisIC = qtyOfReferencingInOutLine.min(qtyDeliveredLeftToAllocateForAnyPaymentTerm);
				qtyAllocatedViaReferencingInoutLines = qtyAllocatedViaReferencingInoutLines.add(qtyToAddForThisIC);
				qtyDeliveredLeftToAllocateForCurentICsPaymentTerm = qtyDeliveredLeftToAllocateForCurentICsPaymentTerm.subtract(qtyOfReferencingInOutLine);
			}
			qtyDeliveredLeftToAllocateForAnyPaymentTerm = qtyDeliveredLeftToAllocateForAnyPaymentTerm.subtract(qtyOfReferencingInOutLine);

			if (qtyDeliveredLeftToAllocateForAnyPaymentTerm.signum() <= 0)
			{
				break; // we saw enough
			}
		}

		if (qtyDeliveredLeftToAllocateForAnyPaymentTerm.signum() <= 0)
		{
			// the referencingInOutLines' QtyEnteredTUs were large enough to cover the whole packagingInOutLine.getMovementQty()
			return qtyAllocatedViaReferencingInoutLines;
		}

		if (callerCanCreateAdditionalICs)
		{
			// the IC creating code will create another IC or do whatever it takes to take care of the not-yet allocated quantity.
			return qtyAllocatedViaReferencingInoutLines;
		}

		// check which of the packagingInOutLine's qty is already allocated to another IC
		final List<I_C_Invoice_Candidate> icsForPackagingInOutLine = Services.get(IInvoiceCandDAO.class).retrieveInvoiceCandidatesForInOutLine(packagingInOutLine);
		for (final I_C_Invoice_Candidate currentIcForPackagingInOutLine : icsForPackagingInOutLine)
		{
			boolean currentIcIsTheGivenIc = currentIcForPackagingInOutLine.getC_Invoice_Candidate_ID() == ic.getC_Invoice_Candidate_ID();
			if (currentIcIsTheGivenIc)
			{
				continue;
			}
			final BigDecimal currentIcQty = currentIcForPackagingInOutLine.getQtyDelivered().abs();
			qtyDeliveredLeftToAllocateForCurentICsPaymentTerm = qtyDeliveredLeftToAllocateForCurentICsPaymentTerm.subtract(currentIcQty);
		}
		if (qtyDeliveredLeftToAllocateForAnyPaymentTerm.signum() <= 0)
		{
			return qtyAllocatedViaReferencingInoutLines.add(qtyDeliveredLeftToAllocateForAnyPaymentTerm.max(ZERO));
		}

		return qtyAllocatedViaReferencingInoutLines.add(qtyDeliveredLeftToAllocateForCurentICsPaymentTerm);
	}

	private static boolean inoutLineFitsWithPaymentTermId(
			@NonNull final I_M_InOutLine inOutLine,
			@Nullable final PaymentTermId paymentTermId)
	{
		final PaymentTermId paymentTermIdOfInOutLine = extractPaymentTermIdOrNull(inOutLine);
		return paymentTermIdOfInOutLine == null
				|| PaymentTermId.equals(paymentTermIdOfInOutLine, paymentTermId);
	}

	@VisibleForTesting
	static PaymentTermId extractPaymentTermIdOrNull(@NonNull final I_M_InOutLine inOutLine)
	{
		if (inOutLine.getC_OrderLine_ID() > 0)
		{
			// this won't be the case for the actual iol this handler is dealing with, but maybe for one of its related iols
			return extractPaymentTermIdViaOrderLineOrNull(inOutLine);
		}

		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		// extract the C_PaymentTerm_ID from the first iol that references 'inOutLine'
		final List<I_M_InOutLine> referencingLines = inOutDAO.retrieveAllReferencingLinesBuilder(inOutLine)
				.addOnlyActiveRecordsFilter()
				.addNotEqualsFilter(I_M_InOutLine.COLUMN_C_OrderLine_ID, null)
				.create()
				.list(I_M_InOutLine.class);
		for (final I_M_InOutLine refencingLine : referencingLines)
		{
			final PaymentTermId paymentTermId = extractPaymentTermIdViaOrderLineOrNull(refencingLine);
			if (paymentTermId != null)
			{
				return paymentTermId;
			}
		}

		// fallback: get the C_PaymentTerm_ID from the first "sibling" line
		final List<I_M_InOutLine> lines = inOutDAO.retrieveLines(inOutLine.getM_InOut(), I_M_InOutLine.class);
		for (final I_M_InOutLine line : lines)
		{
			final PaymentTermId paymentTermId = extractPaymentTermIdViaOrderLineOrNull(line);
			if (paymentTermId != null)
			{
				return paymentTermId;
			}
		}

		// last fallback
		if (inOutLine.getM_InOut().getC_Order_ID() > 0)
		{
			return PaymentTermId.ofRepoId(inOutLine.getM_InOut().getC_Order().getC_PaymentTerm_ID());
		}

		return null;
	}

	/**
	 * Note that the ioLines handled by {@link M_InOut_Handler} per se don't have an order-line themselves.
	 *
	 * @param inOutLine an inout line that is somehow related to the lines which this handler handles.
	 */
	@VisibleForTesting
	static PaymentTermId extractPaymentTermIdViaOrderLineOrNull(@NonNull final org.compiere.model.I_M_InOutLine inOutLine)
	{
		if (inOutLine.getC_OrderLine_ID() <= 0)
		{
			return null;
		}

		final I_C_OrderLine ol = inOutLine.getC_OrderLine(); //
		return Services.get(IOrderLineBL.class).getPaymentTermId(ol);
	}

	/**
	 * <ul>
	 * <li>QtyDelivered := QtyOrdered
	 * <li>DeliveryDate := MovementDate of the inOutLine's M_InOut
	 * <li>M_InOut_ID: the ID of the inOutLine's M_InOut
	 * </ul>
	 *
	 * @see IInvoiceCandidateHandler#setDeliveredData(I_C_Invoice_Candidate)
	 */
	@Override
	public void setDeliveredData(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		//
		// Get delivered quantity and then set it to IC
		// NOTE: setOrderedData() method sets QtyOrdered as inout lines' movement quantity,
		final InvoiceCandidateRecordService invoiceCandidateRecordService = SpringContextHolder.instance.getBean(InvoiceCandidateRecordService.class);

		final StockQtyAndUOMQty qtysDelivered = invoiceCandidateRecordService
				.ofRecord(icRecord)
				.computeQtysDelivered(); // this is based on icRecord's QtyOrdered

		icRecord.setQtyDelivered(qtysDelivered.getStockQty().toBigDecimal());
		icRecord.setQtyDeliveredInUOM(qtysDelivered.getUOMQtyNotNull().toBigDecimal());

		//
		// Set other delivery informations by fetching them from first shipment/receipt.
		final I_M_InOutLine inOutLine = getM_InOutLine(icRecord);
		final org.compiere.model.I_M_InOut inOut = inOutLine.getM_InOut();
		setDeliveredDataFromFirstInOut(icRecord, inOut);
	}

	public static I_M_InOutLine getM_InOutLine(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inoutLine = getM_InOutLineOrNull(ic);
		Check.assumeNotNull(inoutLine, "Error: no inout line found for candidate {}", ic);
		return inoutLine;

	}

	public static I_M_InOutLine getM_InOutLineOrNull(final I_C_Invoice_Candidate ic)
	{
		return TableRecordCacheLocal.getReferencedValue(ic, I_M_InOutLine.class);
	}

	@Override
	public void setBPartnerData(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inOutLine = getM_InOutLine(ic);
		setBPartnerData(ic, inOutLine);
	}

	private void setBPartnerData(final I_C_Invoice_Candidate ic, final I_M_InOutLine fromInOutLine)
	{
		Check.assumeNotNull(fromInOutLine, "fromInOutLine not null");

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

		final I_M_InOut inOut = create(fromInOutLine.getM_InOut(), I_M_InOut.class);

		final BPartnerLocationId billBPLocationId;
		final BPartnerContactId billBPContactId;
		// The bill related info cannot be changed in the schedule
		// Therefore, it's safe to set them in the invoice candidate directly from the order (if we have it)
		final I_C_Order inoutOrder = inOut.getC_Order();
		if (inoutOrder != null && inoutOrder.getC_Order_ID() > 0)
		{
			billBPLocationId = BPartnerLocationId.ofRepoIdOrNull(inoutOrder.getBill_BPartner_ID(), inoutOrder.getBill_Location_ID());
			billBPContactId = BPartnerContactId.ofRepoIdOrNull(inoutOrder.getBill_BPartner_ID(), inoutOrder.getBill_User_ID());
		}
		// Otherwise, take it from the inout, but don't use the inout's location and user. They might not be "billto" after all.
		else
		{
			final boolean alsoTryBilltoRelation = true;
			final Properties ctx = getCtx(ic);

			final I_C_BPartner_Location billBPLocation = bPartnerDAO.retrieveBillToLocation(ctx, inOut.getC_BPartner_ID(), alsoTryBilltoRelation, ITrx.TRXNAME_None);
			billBPLocationId = BPartnerLocationId.ofRepoId(billBPLocation.getC_BPartner_ID(), billBPLocation.getC_BPartner_Location_ID());

			final User billBPContact = bPartnerBL
					.retrieveContactOrNull(RetrieveContactRequest.builder()
							.bpartnerId(billBPLocationId.getBpartnerId())
							.bPartnerLocationId(billBPLocationId)
							.build());
			billBPContactId = billBPContact != null
					? BPartnerContactId.of(billBPLocationId.getBpartnerId(), billBPContact.getId())
					: null;
		}

		Check.assumeNotNull(billBPLocationId, "billBPLocation not null");
		// Bill_User_ID isn't mandatory in C_Order, and isn't considered a must in OLHandler either
		// Check.assumeNotNull(billBPContactId, "billBPContact not null");

		// Set BPartner / Location / Contact
		ic.setBill_BPartner_ID(billBPLocationId.getBpartnerId().getRepoId());
		ic.setBill_Location_ID(billBPLocationId.getRepoId());
		ic.setBill_User_ID(BPartnerContactId.toRepoId(billBPContactId));
	}

	@Override
	public PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inoutLine = getM_InOutLine(ic);
		return calculatePriceAndTax(ic, inoutLine);
	}

	public static PriceAndTax calculatePriceAndTax(final I_C_Invoice_Candidate ic, final org.compiere.model.I_M_InOutLine inoutLine)
	{
		final IPricingResult pricingResult = calculatePricingResult(inoutLine);

		final boolean taxIncluded;
		if (ic.getC_Order_ID() > 0)
		{
			// task 08451: if the ic has an order, we use the order's IsTaxIncuded value, to make sure that we will be able to invoice them together
			taxIncluded = ic.getC_Order().isTaxIncluded();
		}
		else
		{
			taxIncluded = pricingResult.isTaxIncluded();
		}

		return PriceAndTax.builder()
				.pricingSystemId(pricingResult.getPricingSystemId())
				// #367: there is a corner case where we need to know the PLV is order to later know the correct M_PriceList_ID.
				// also see the javadoc of inOutBL.createPricingCtx(fromInOutLine)
				.priceListVersionId(pricingResult.getPriceListVersionId())
				.currencyId(pricingResult.getCurrencyId())
				.taxCategoryId(pricingResult.getTaxCategoryId())
				//
				.priceEntered(pricingResult.getPriceStd())
				.priceActual(pricingResult.getPriceStd())
				.priceUOMId(pricingResult.getPriceUomId()) // 07090 when we set PriceActual, we shall also set PriceUOM.
				.invoicableQtyBasedOn(pricingResult.getInvoicableQtyBasedOn())
				.taxIncluded(taxIncluded)
				//
				.discount(pricingResult.getDiscount())
				.build();
	}

	private static IPricingResult calculatePricingResult(final org.compiere.model.I_M_InOutLine fromInOutLine)
	{
		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		final IPricingContext pricingCtx = inOutBL.createPricingCtx(fromInOutLine);
		return inOutBL.getProductPrice(pricingCtx);
	}

	public static PriceAndTax calculatePriceAndQuantityAndUpdate(final I_C_Invoice_Candidate ic, final org.compiere.model.I_M_InOutLine fromInOutLine)
	{
		try
		{
			final PriceAndTax priceAndTax = calculatePriceAndTax(ic, fromInOutLine);
			IInvoiceCandInvalidUpdater.updatePriceAndTax(ic, priceAndTax);
			return priceAndTax;
		}
		catch (final ProductNotOnPriceListException e)
		{
			final boolean askForDeleteRegeneration = true; // ask for re-generation
			setError(ic, e, askForDeleteRegeneration);
			return null;
		}
		catch (final Exception e)
		{
			final boolean askForDeleteRegeneration = false; // default; don't ask for re-generation
			setError(ic, e, askForDeleteRegeneration);
			return null;
		}
	}

	private static final void setError(final I_C_Invoice_Candidate ic, final Exception ex, final boolean askForDeleteRegeneration)
	{
		ic.setIsInDispute(true); // 07193 - Mark's request

		final I_AD_Note note = null; // we don't have a note
		Services.get(IInvoiceCandBL.class).setError(ic, ex.getLocalizedMessage(), note, askForDeleteRegeneration);
	}
}

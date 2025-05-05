package de.metas.inout.invoicecandidate;

import ch.qos.logback.classic.Level;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import de.metas.async.AsyncBatchId;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerDocumentLocationHelper;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerBL.RetrieveContactRequest;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.document.dimension.Dimension;
import de.metas.document.dimension.DimensionService;
import de.metas.document.engine.DocStatus;
import de.metas.document.location.DocumentLocation;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutId;
import de.metas.inout.location.adapter.InOutDocumentLocationAdapterFactory;
import de.metas.inout.model.I_M_InOut;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.IInvoiceCandInvalidUpdater;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.location.adapter.InvoiceCandidateLocationAdapterFactory;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_M_InOutLine;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.AbstractInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
import de.metas.lang.SOTrx;
import de.metas.logging.LogManager;
import de.metas.material.MovementType;
import de.metas.order.IOrderLineBL;
import de.metas.order.InvoiceRule;
import de.metas.order.OrderId;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.payment.paymentterm.PaymentTermId;
import de.metas.pricing.IPricingResult;
import de.metas.product.IProductActivityProvider;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.product.acct.api.ActivityId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.Tax;
import de.metas.tax.api.TaxId;
import de.metas.tax.api.VatCodeId;
import de.metas.user.User;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.ILoggable;
import de.metas.util.ImmutableMapEntry;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
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

/**
 * Creates {@link I_C_Invoice_Candidate}s from {@link I_M_InOutLine}s which do not reference an order line.
 */
public class M_InOutLine_Handler extends AbstractInvoiceCandidateHandler
{
	private static final Logger logger = LogManager.getLogger(M_InOutLine_Handler.class);

	// Services
	private final transient IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final transient IInOutBL inOutBL = Services.get(IInOutBL.class);
	private final transient DimensionService dimensionService = SpringContextHolder.instance.getBean(DimensionService.class);
	private final transient OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository = SpringContextHolder.instance.getBean(OrderEmailPropagationSysConfigRepository.class);
	private final transient IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	/**
	 * @return {@code false}, but note that this handler will be invoked to create missing invoice candidates via {@link M_InOut_Handler#expandRequest(InvoiceCandidateGenerateRequest)}.
	 */
	@Override
	public CandidatesAutoCreateMode getGeneralCandidatesAutoCreateMode()
	{
		return CandidatesAutoCreateMode.DONT;
	}

	/**
	 * @return {@code false}, but note that this handler will be invoked to create missing invoice candidates via {@link M_InOut_Handler#expandRequest(InvoiceCandidateGenerateRequest)}.
	 */
	@Override
	public CandidatesAutoCreateMode getSpecificCandidatesAutoCreateMode(final Object model)
	{
		return CandidatesAutoCreateMode.DONT;
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
		return inoutLine.getM_InOut();
	}

	@Override
	public Iterator<org.compiere.model.I_M_InOutLine> retrieveAllModelsWithMissingCandidates(@NonNull final QueryLimit limit)
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
		if (!invoiceCandBL.isAllowedToCreateInvoiceCandidateFor(inOutLine))
		{
			return ImmutableList.of();
		}
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

	@Nullable
	private I_C_Invoice_Candidate createInvoiceCandidateForInOutLineOrNull(
			@NonNull final I_M_InOutLine inOutLineRecord,
			@Nullable final PaymentTermId paymentTermId,
			@Nullable final BigDecimal forcedQtyToAllocate)
	{
		final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);

		final I_M_InOut inOut = create(inOutLineRecord.getM_InOut(), I_M_InOut.class);
		final I_C_Invoice_Candidate icRecord = newInstance(I_C_Invoice_Candidate.class, inOutLineRecord);

		// extractQtyDelivered() depends on the C_PaymentTerm to be set
		icRecord.setC_PaymentTerm_ID(PaymentTermId.toRepoId(paymentTermId));

		TableRecordCacheLocal.setReferencedValue(icRecord, inOutLineRecord);
		icRecord.setIsPackagingMaterial(inOutLineRecord.isPackagingMaterial());
		icRecord.setExternalHeaderId(inOut.getExternalId());

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
		final org.compiere.model.I_M_InOutLine inOutLineRecordToUse = inOutLineRecord.getReturn_Origin_InOutLine_ID() > 0 ? inOutLineRecord.getReturn_Origin_InOutLine() : inOutLineRecord;
		calculatePriceAndTaxAndUpdate(icRecord, inOutLineRecordToUse);

		//
		// Description
		icRecord.setDescription(inOut.getDescription());

		//
		// Set invoice rule form linked order (if exists)
		if (inOut.getC_Order_ID() > 0)
		{
			final I_C_Order order = inOut.getC_Order();
			icRecord.setInvoiceRule(order.getInvoiceRule()); // the rule set in order
		}
		// Set Invoice Rule from BPartner
		else
		{
			final I_C_BPartner billBPartner = bpartnerDAO.getById(icRecord.getBill_BPartner_ID());

			final InvoiceRule invoiceRule = inOut.isSOTrx() ?
					InvoiceRule.ofNullableCode(billBPartner.getInvoiceRule()) :
					InvoiceRule.ofNullableCode(billBPartner.getPO_InvoiceRule());

			if (invoiceRule != null)
			{
				icRecord.setInvoiceRule(invoiceRule.getCode());
			}
			else
			{
				icRecord.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_Immediate); // Immediate
			}
		}

		Dimension inOutLineDimension = dimensionService.getFromRecord(inOutLineRecord);

		if (inOutLineDimension.getActivityId() == null)
		{
			//
			// Set C_Activity from Product (07442)
			final ActivityId activityId = Services.get(IProductActivityProvider.class).getActivityForAcct(clientId, orgId, productId);
			inOutLineDimension = inOutLineDimension.withActivityId(activityId);
		}

		dimensionService.updateRecord(icRecord, inOutLineDimension);


		// task 13022 : set inout's project if dimension didn't already
		if(icRecord.getC_Project_ID() <= 0)
		{
			if(inOut.getC_Project_ID() > 0)
			{
				icRecord.setC_Project_ID(inOut.getC_Project_ID());
			}
			// get order's project if exists
			else if(inOut.getC_Order_ID() > 0
					&& inOut.getC_Order().getC_Project_ID() > 0)
			{
				final I_C_Order order = inOut.getC_Order();
				icRecord.setC_Project_ID(order.getC_Project_ID());
			}
		}

		//DocType
		final DocTypeId invoiceDocTypeId = extractDocTypeId(inOutLineRecord);
		if (invoiceDocTypeId != null)
		{
			icRecord.setC_DocTypeInvoice_ID(invoiceDocTypeId.getRepoId());
		}
		else
		{
			setDefaultInvoiceDocType(icRecord);
		}

		icRecord.setC_Incoterms_ID(inOut.getC_Incoterms_ID());
		icRecord.setIncotermLocation(inOut.getIncotermLocation());

		icRecord.setC_Shipping_Location_ID(inOut.getC_BPartner_Location_Value_ID());

		setWarehouseId(icRecord);
		setHarvestingDetails(icRecord);
		//
		// Save the Invoice Candidate, so that we can use its ID further down
		invoiceCandBL.setPaymentTermIfMissing(icRecord);
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
			Loggables.withLogger(logger, Level.DEBUG)
					.addLog("Setting qtyDelivered={} on icIol_ID={}",
							icRecord.getQtyOrdered().multiply(qtyMultiplier), iciol.getC_InvoiceCandidate_InOutLine_ID());
		}
		else
		{
			// the common case; icRecord is the only one associated with inOutLineRecord; and inOutLineRecord *might* have a catch quantity
			iciol.setC_Invoice_Candidate(icRecord);
			Services.get(IInvoiceCandBL.class).updateICIOLAssociationFromIOL(iciol, inOutLineRecord);
		}
		return icRecord;
	}

	@Nullable
	private DocTypeId extractDocTypeId(final I_M_InOutLine inOutLineRecord)
	{
		final ILoggable loggable = Loggables.withLogger(logger, Level.DEBUG);

		final org.compiere.model.I_M_InOut inOutRecord = inOutLineRecord.getM_InOut();

		if (inOutRecord.getC_DocType_ID() > 0)
		{
			final DocTypeId inoutDocTypeId = DocTypeId.ofRepoId(inOutRecord.getC_DocType_ID());
			final I_C_DocType inOutDocType = docTypeBL.getById(inoutDocTypeId);
			if (inOutDocType.getC_DocTypeInvoice_ID() > 0)
			{
				loggable.addLog("extractDocTypeId - M_InOutLine_ID={} has M_InOut_ID={} => C_DocType_ID={} => C_DocTypeInvoice_ID={}; -> return that as DocTypeId",
						inOutLineRecord.getM_InOutLine_ID(), inOutLineRecord.getM_InOut_ID(), inOutRecord.getC_DocType_ID(), inOutDocType.getC_DocTypeInvoice_ID());
				return DocTypeId.ofRepoId(inOutDocType.getC_DocTypeInvoice_ID());
			}
		}
		if (inOutLineRecord.getC_OrderLine_ID() > 0)
		{
			final I_C_Order order = inOutLineRecord.getC_OrderLine().getC_Order();
			final I_C_DocType orderDocType = extractOrderDocTypeRecord(order);
			if (orderDocType.getC_DocTypeInvoice_ID() > 0)
			{
				loggable.addLog("extractDocTypeId - M_InOutLine_ID={} has C_OrderLine_ID={} => C_Order_ID={} => doctype-ID={} => C_DocTypeInvoice_ID={}; -> return that as DocTypeId",
						inOutLineRecord.getM_InOutLine_ID(), inOutLineRecord.getC_OrderLine_ID(), order.getC_Order_ID(),
						orderDocType.getC_DocType_ID(), orderDocType.getC_DocTypeInvoice_ID());
				return DocTypeId.ofRepoId(orderDocType.getC_DocTypeInvoice_ID());
			}
		}
		if (inOutRecord.getC_Order_ID() > 0)
		{
			final I_C_Order order = inOutRecord.getC_Order();
			final I_C_DocType orderDocType = extractOrderDocTypeRecord(order);
			if (orderDocType.getC_DocTypeInvoice_ID() > 0)
			{
				loggable.addLog("extractDocTypeId - M_InOutLine_ID={} has M_InOut_ID={} => C_Order_ID={} => doctype-ID={} => C_DocTypeInvoice_ID={}; -> return that as DocTypeId",
						inOutLineRecord.getM_InOutLine_ID(), inOutLineRecord.getC_OrderLine_ID(), order.getC_Order_ID(),
						orderDocType.getC_DocType_ID(), orderDocType.getC_DocTypeInvoice_ID());
				return DocTypeId.ofRepoId(orderDocType.getC_DocTypeInvoice_ID());
			}
		}
		loggable.addLog("extractDocTypeId - Unable to determine invoice doctype for M_InOutLine_ID={}; -> return null", inOutLineRecord.getM_InOutLine_ID());
		return null;
	}

	@NonNull
	private I_C_DocType extractOrderDocTypeRecord(final I_C_Order order)
	{
		final DocTypeId orderDocTypeId = CoalesceUtil.coalesceSuppliers(
				() -> DocTypeId.ofRepoIdOrNull(order.getC_DocType_ID()),
				() -> DocTypeId.ofRepoId(order.getC_DocTypeTarget_ID()));
		return docTypeBL.getById(orderDocTypeId);
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

		final org.compiere.model.I_M_InOut shipment = inOutBL.getById(InOutId.ofRepoId(inoutLine.getM_InOut_ID()));

		final IQueryBuilder<I_C_Invoice_Candidate> icQueryBuilder = invoiceCandDAO.retrieveInvoiceCandidatesForInOutLineQuery(inoutLine);

		final AsyncBatchId asyncBatchId = AsyncBatchId.ofRepoIdOrNull(shipment.getC_Async_Batch_ID());

		if (asyncBatchId != null)
		{
			final ImmutableSet<InvoiceCandidateId> invoiceCandidateIds = icQueryBuilder.create()
					.listIds()
					.stream()
					.map(InvoiceCandidateId::ofRepoId)
					.collect(ImmutableSet.toImmutableSet());

			invoiceCandDAO.invalidateCandsFor(invoiceCandidateIds);
			return;
		}

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
	 * @return <ul>
	 * <li>+1 on regular shipment/receipt
	 * <li>-1 on material returns
	 * </ul>
	 */
	private BigDecimal getQtyMultiplier(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inoutLine = getM_InOutLine(ic);
		final org.compiere.model.I_M_InOut inout = inoutLine.getM_InOut();
		final String movementType = inout.getMovementType();

		if (MovementType.isMaterialReturn(movementType))
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
			@Nullable final BigDecimal forcedQtyOrdered,
			final boolean callerCanCreateAdditionalICs)
	{
		final I_M_InOutLine inOutLine = getM_InOutLine(icRecord);
		final org.compiere.model.I_M_InOut inOut = inOutLine.getM_InOut();

		final I_C_Order order = inOut.getC_Order();

		if (inOut.getC_Order_ID() > 0)
		{
			icRecord.setC_Order(order);  // also set the order; even if the iol does not directly refer to an order line, it is there because of that order
			icRecord.setPaymentRule(order.getPaymentRule());
			icRecord.setDateOrdered(order.getDateOrdered());
			
			if(Check.isBlank(icRecord.getExternalHeaderId()))
			{ // only set if it wasn't already set from the M_Inout
				icRecord.setExternalHeaderId(order.getExternalId());
			}
			
			final boolean propagateToCInvoice = orderEmailPropagationSysConfigRepository.isPropagateToCInvoice(ClientAndOrgId.ofClientAndOrg(order.getAD_Client_ID(), order.getAD_Org_ID()));
			if (Check.isBlank(icRecord.getEMail()) && propagateToCInvoice)
			{
				icRecord.setEMail(order.getEMail());
			}
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
			final BigDecimal qtyOrdered = CoalesceUtil.coalesceSuppliersNotNull(
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
			final boolean currentIcIsTheGivenIc = currentIcForPackagingInOutLine.getC_Invoice_Candidate_ID() == ic.getC_Invoice_Candidate_ID();
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

	@Nullable
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
	@Nullable
	static PaymentTermId extractPaymentTermIdViaOrderLineOrNull(@NonNull final org.compiere.model.I_M_InOutLine inOutLine)
	{
		if (inOutLine.getC_OrderLine_ID() <= 0)
		{
			return null;
		}

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final I_C_OrderLine ol = inOutLine.getC_OrderLine(); //

		return orderLineBL.getPaymentTermId(ol);
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

	@Override
	public void setIsInEffect(final I_C_Invoice_Candidate ic)
	{
		final I_M_InOutLine inOutLine = getM_InOutLine(ic);

		final DocStatus inOutDocStatus = inOutBL.getDocStatus(InOutId.ofRepoId(inOutLine.getM_InOut_ID()));
		invoiceCandBL.computeIsInEffect(inOutDocStatus, ic);
	}

	private void setBPartnerData(final I_C_Invoice_Candidate ic, final I_M_InOutLine fromInOutLine)
	{
		Check.assumeNotNull(fromInOutLine, "fromInOutLine not null");

		final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
		final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

		final I_M_InOut inOut = create(fromInOutLine.getM_InOut(), I_M_InOut.class);

		final DocumentLocation billLocation;
		//final BPartnerLocationId billBPLocationId;
		//final BPartnerContactId billBPContactId;
		// The bill related info cannot be changed in the schedule
		// Therefore, it's safe to set them in the invoice candidate directly from the order (if we have it)
		final I_C_Order inoutOrder = inOut.getC_Order();
		if (inoutOrder != null && inoutOrder.getC_Order_ID() > 0)
		{
			billLocation = OrderDocumentLocationAdapterFactory
					.billLocationAdapter(inoutOrder)
					.toDocumentLocation();
		}
		// Otherwise, take it from the inout, but don't use the inout's location and user. They might not be "billto" after all.
		else
		{
			final boolean alsoTryBilltoRelation = true;
			final Properties ctx = getCtx(ic);

			final DocumentLocation billLocationFromInOut = BPartnerDocumentLocationHelper.extractDocumentLocation(
					bPartnerDAO.retrieveBillToLocation(ctx, inOut.getC_BPartner_ID(), alsoTryBilltoRelation, ITrx.TRXNAME_None)
			);

			final User billUser = bPartnerBL.retrieveContactOrNull(RetrieveContactRequest.builder()
					.bpartnerId(billLocationFromInOut.getBpartnerId())
					.bPartnerLocationId(billLocationFromInOut.getBpartnerLocationId())
					.build());
			final BPartnerContactId billBPContactId = billUser != null
					? BPartnerContactId.of(billLocationFromInOut.getBpartnerId(), billUser.getId())
					: null;

			if (billBPContactId != null)
			{
				billLocation = billLocationFromInOut.toBuilder()
						.contactId(billBPContactId)
						.bpartnerAddress(null) // shall be recomputed
						.build();
			}
			else
			{
				billLocation = billLocationFromInOut;
			}
		}

		// Set BPartner / Location / Contact
		InvoiceCandidateLocationAdapterFactory
				.billLocationAdapter(ic)
				.setFrom(billLocation);
	}

	@Override
	public PriceAndTax calculatePriceAndTax(@NonNull final I_C_Invoice_Candidate icRecord)
	{
		final I_M_InOutLine inoutLine = getM_InOutLine(icRecord);
		final org.compiere.model.I_M_InOutLine inOutLineRecordToUse = inoutLine.getReturn_Origin_InOutLine_ID() > 0 ? inoutLine.getReturn_Origin_InOutLine() : inoutLine;

		return calculatePriceAndTax(icRecord, inOutLineRecordToUse);
	}

	public static PriceAndTax calculatePriceAndTax(
			final I_C_Invoice_Candidate icRecord,
			final org.compiere.model.I_M_InOutLine inoutLineRecord)
	{
		final IPricingResult pricingResult = calculatePricingResult(inoutLineRecord);

		final boolean taxIncluded;
		if (icRecord.getC_Order_ID() > 0)
		{
			// task 08451: if the ic has an order, we use the order's IsTaxIncuded value, to make sure that we will be able to invoice them together
			taxIncluded = icRecord.getC_Order().isTaxIncluded();
		}
		else
		{
			taxIncluded = pricingResult.isTaxIncluded();
		}

		//
		// Set C_Tax from Product (07442)
		final OrgId orgId = OrgId.ofRepoId(inoutLineRecord.getAD_Org_ID());
		final org.compiere.model.I_M_InOut inOutRecord = inoutLineRecord.getM_InOut();
		final Timestamp shipDate = inOutRecord.getMovementDate();
		final VatCodeId vatCodeId = VatCodeId.ofRepoIdOrNull(firstGreaterThanZero(icRecord.getC_VAT_Code_Override_ID(), icRecord.getC_VAT_Code_ID()));

		final BPartnerLocationAndCaptureId deliveryLocation = InOutDocumentLocationAdapterFactory
				.locationAdapter(inOutRecord)
				.getBPartnerLocationAndCaptureId();

		final TaxId taxId = Services.get(ITaxBL.class).getTaxNotNull(
				icRecord,
				pricingResult.getTaxCategoryId(),
				ProductId.toRepoId(pricingResult.getProductId()),
				shipDate,
				orgId,
				WarehouseId.ofRepoId(inOutRecord.getM_Warehouse_ID()),
				deliveryLocation, // shipC_BPartner_Location_ID
				SOTrx.ofBoolean(inOutRecord.isSOTrx()),
				vatCodeId);

		final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);
		final ProductPrice pp = inoutLineRecord.getC_OrderLine_ID() != 0 ? orderLineBL.getPriceActual(inoutLineRecord.getC_OrderLine()) : null;

		return PriceAndTax.builder()
				.pricingSystemId(pricingResult.getPricingSystemId())
				// #367: there is a corner case where we need to know the PLV is order to later know the correct M_PriceList_ID.
				// also see the javadoc of inOutBL.createPricingCtx(fromInOutLine)
				.priceListVersionId(pricingResult.getPriceListVersionId())
				.pricingSystemId(pricingResult.getPricingSystemId())
				.currencyId(pricingResult.getCurrencyId())
				.taxCategoryId(pricingResult.getTaxCategoryId())
				.taxIncluded(taxIncluded)
				.taxId(taxId)
				//
				.priceEntered(pricingResult.getPriceStd())
				.priceActual(pp != null ? pp.toMoney().toBigDecimal() : pricingResult.getPriceStd())
				.priceUOMId(pp != null ? pp.getUomId() : pricingResult.getPriceUomId()) // 07090 when we set PriceActual, we shall also set PriceUOM.
				.invoicableQtyBasedOn(pricingResult.getInvoicableQtyBasedOn())
				//
				.discount(pricingResult.getDiscount())
				.build();
	}

	private static IPricingResult calculatePricingResult(@NonNull final org.compiere.model.I_M_InOutLine fromInOutLine)
	{
		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		return inOutBL.getProductPrice(fromInOutLine);
	}

	@Nullable
	public static PriceAndTax calculatePriceAndTaxAndUpdate(
			@NonNull final I_C_Invoice_Candidate icRecord,
			final org.compiere.model.I_M_InOutLine fromInOutLine)
	{
		try
		{
			final PriceAndTax priceAndTax = calculatePriceAndTax(icRecord, fromInOutLine);
			IInvoiceCandInvalidUpdater.updatePriceAndTax(icRecord, priceAndTax);
			return priceAndTax;
		}
		catch (final Exception e)
		{
			if (icRecord.getC_Tax_ID() <= 0)
			{
				icRecord.setC_Tax_ID(Tax.C_TAX_ID_NO_TAX_FOUND); // make sure that we will be able to save the icRecord
			}

			setError(icRecord, e);
			return null;
		}
	}

	private static void setError(
			@NonNull final I_C_Invoice_Candidate ic,
			@NonNull final Exception ex)
	{

		logger.debug("Set IsInDispute=true, because an error occured");
		ic.setIsInDispute(true); // 07193 - Mark's request

		final boolean askForDeleteRegeneration = false; // default; don't ask for re-generation

		final I_AD_Note note = null; // we don't have a note
		Services.get(IInvoiceCandBL.class).setError(ic, ex.getLocalizedMessage(), note, askForDeleteRegeneration);
	}

	@Override
	public void setWarehouseId(@NonNull final I_C_Invoice_Candidate ic)
	{
		final OrderId referencedOrderId = OrderId.ofRepoIdOrNull(ic.getC_Order_ID());
		if (referencedOrderId == null)
		{
			return;
		}

		final I_C_Order order = ic.getC_Order();
		ic.setM_Warehouse_ID(order.getM_Warehouse_ID());
	}

	@Override
	public void setHarvestingDetails(final @NonNull I_C_Invoice_Candidate ic)
	{
		final OrderId referencedOrderId = OrderId.ofRepoIdOrNull(ic.getC_Order_ID());
		if (referencedOrderId == null)
		{
			return;
		}

		final I_C_Order order = ic.getC_Order();
		ic.setC_Harvesting_Calendar_ID(order.getC_Harvesting_Calendar_ID());
		ic.setHarvesting_Year_ID(order.getHarvesting_Year_ID());
	}
}

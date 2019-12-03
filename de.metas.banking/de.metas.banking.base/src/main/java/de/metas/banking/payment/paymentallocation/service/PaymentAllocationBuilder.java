package de.metas.banking.payment.paymentallocation.service;

/*
 * #%L
 * de.metas.banking.swingui
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.allocation.api.IAllocationBL;
import de.metas.allocation.api.IAllocationBuilder;
import de.metas.allocation.api.IAllocationLineBuilder;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.banking.payment.paymentallocation.IPaymentAllocationBL;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.OptionalDeferredException;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Builds one {@link I_C_AllocationHdr} of all given {@link PayableDocument}s and {@link IPaymentDocument}s.
 *
 * @author tsa
 *
 */
public class PaymentAllocationBuilder
{
	public static final PaymentAllocationBuilder newBuilder()
	{
		return new PaymentAllocationBuilder();
	}

	// services
	private final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	private final transient IAllocationBL allocationBL = Services.get(IAllocationBL.class);

	// Parameters
	private OrgId _adOrgId;
	private CurrencyId _currencyId;
	private LocalDate _dateTrx;
	private LocalDate _dateAcct;
	private ImmutableList<PayableDocument> _payableDocuments = ImmutableList.of();
	private ImmutableList<IPaymentDocument> _paymentDocuments = ImmutableList.of();
	private boolean allowOnlyOneVendorDoc = true;
	private boolean allowPartialAllocations = false;
	private boolean dryRun = false;

	// Status
	private boolean _built = false;

	private PaymentAllocationBuilder()
	{
	}

	/**
	 * @return result message (to be displayed in UI status bar)
	 * @throws PaymentAllocationException in case of any error
	 */
	public PaymentAllocationResult build()
	{
		markAsBuilt();

		//
		// Create allocation candidates
		final ImmutableList<AllocationLineCandidate> candidates = createAllocationLineCandidates();

		final OptionalDeferredException<PaymentAllocationException> fullyAllocatedCheck;
		if (!allowPartialAllocations)
		{
			fullyAllocatedCheck = checkFullyAllocated();
			if (!dryRun)
			{
				fullyAllocatedCheck.throwIfError();
			}
		}
		else
		{
			fullyAllocatedCheck = OptionalDeferredException.noError();
		}

		//
		// Create & process allocation documents
		final ImmutableSet<PaymentAllocationId> paymentAllocationIds;
		if (!candidates.isEmpty() && !dryRun)
		{
			try
			{
				paymentAllocationIds = trxManager.callInNewTrx(() -> createAndCompleteAllocations(candidates));
			}
			catch (final Exception ex)
			{
				throw PaymentAllocationException.wrapIfNeeded(ex);
			}
		}
		else
		{
			paymentAllocationIds = ImmutableSet.of();
		}

		return PaymentAllocationResult.builder()
				.candidates(candidates)
				.fullyAllocatedCheck(fullyAllocatedCheck)
				.paymentAllocationIds(paymentAllocationIds)
				.build();
	}

	private final ImmutableSet<PaymentAllocationId> createAndCompleteAllocations(final List<AllocationLineCandidate> lines)
	{
		final ImmutableSet.Builder<PaymentAllocationId> paymentAllocationIds = ImmutableSet.builder();

		for (final AllocationLineCandidate line : lines)
		{
			final PaymentAllocationId paymentAllocationId = createAndCompleteAllocation(line);
			if (paymentAllocationId != null)
			{
				paymentAllocationIds.add(paymentAllocationId);
			}
		}

		return paymentAllocationIds.build();
	}

	/**
	 * Create and complete one {@link I_C_AllocationHdr} for given candidate.
	 */
	private final PaymentAllocationId createAndCompleteAllocation(final AllocationLineCandidate line)
	{
		final OrgId adOrgId = getOrgId();
		final CurrencyId currencyId = getCurrencyId();
		final Timestamp dateTrx = TimeUtil.asTimestamp(getDateTrx());
		final Timestamp dateAcct = TimeUtil.asTimestamp(getDateAcct());

		final IContextAware context = PlainContextAware.newWithThreadInheritedTrx();
		final IAllocationBuilder allocationBuilder = allocationBL.newBuilder(context)
				.setAD_Org_ID(adOrgId.getRepoId())
				.setC_Currency_ID(currencyId.getRepoId())
				.setDateAcct(dateAcct)
				.setDateTrx(dateTrx)
				.setManual(true); // flag it as manually created by user

		final IAllocationLineBuilder payableLineBuilder = allocationBuilder.addLine()
				.setAD_Org_ID(adOrgId.getRepoId())
				.setC_BPartner_ID(BPartnerId.toRepoId(line.getBpartnerId()))
				//
				// Amounts
				.setAmount(line.getAmount())
				.setDiscountAmt(line.getDiscountAmt())
				.setWriteOffAmt(line.getWriteOffAmt())
				.setOverUnderAmt(line.getPayableOverUnderAmt())
				.setSkipIfAllAmountsAreZero();

		final ITableRecordReference payableDocRef = line.getPayableDocumentRef();
		final String payableDocTableName = payableDocRef.getTableName();
		final ITableRecordReference paymentDocRef = line.getPaymentDocumentRef();
		final String paymentDocTableName = paymentDocRef == null ? null : paymentDocRef.getTableName();

		//
		// Invoice - Payment
		if (I_C_Invoice.Table_Name.equals(payableDocTableName)
				&& I_C_Payment.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.setC_Invoice_ID(payableDocRef.getRecord_ID());
			payableLineBuilder.setC_Payment_ID(paymentDocRef.getRecord_ID());
		}
		//
		// Invoice - CreditMemo invoice or Sales invoice - Purchase Invoice
		else if (I_C_Invoice.Table_Name.equals(payableDocTableName)
				&& I_C_Invoice.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.setC_Invoice_ID(payableDocRef.getRecord_ID());
			//

			// Credit memo line
			allocationBuilder.addLine()
					.setAD_Org_ID(adOrgId.getRepoId())
					.setC_BPartner_ID(BPartnerId.toRepoId(line.getBpartnerId()))
					//
					// Amounts
					.setAmount(line.getAmount().negate())
					.setOverUnderAmt(line.getPaymentOverUnderAmt())
					.setSkipIfAllAmountsAreZero()
					//
					.setC_Invoice_ID(paymentDocRef.getRecord_ID());
		}
		//
		// Invoice - just Discount/WriteOff
		else if (I_C_Invoice.Table_Name.equals(payableDocTableName)
				&& paymentDocTableName == null)
		{
			payableLineBuilder.setC_Invoice_ID(payableDocRef.getRecord_ID());
			// allow only if the line's amount is zero, because else, we need to have a document where to allocate.
			Check.assume(line.getAmount().signum() == 0, "zero amount: {}", line);
		}
		//
		// Outgoing payment - Incoming payment
		else if (I_C_Payment.Table_Name.equals(payableDocTableName)
				&& I_C_Payment.Table_Name.equals(paymentDocTableName))
		{
			payableLineBuilder.setC_Payment_ID(payableDocRef.getRecord_ID());
			// Incoming payment line
			allocationBuilder.addLine()
					.setAD_Org_ID(adOrgId.getRepoId())
					.setC_BPartner_ID(BPartnerId.toRepoId(line.getBpartnerId()))
					//
					// Amounts
					.setAmount(line.getAmount().negate())
					.setOverUnderAmt(line.getPaymentOverUnderAmt())
					.setSkipIfAllAmountsAreZero()
					//
					.setC_Payment_ID(paymentDocRef.getRecord_ID());
		}
		else
		{
			throw new InvalidDocumentsPaymentAllocationException(payableDocRef, paymentDocRef);
		}

		final I_C_AllocationHdr allocationHdr = allocationBuilder.createAndComplete();
		if (allocationHdr == null)
		{
			return null;
		}

		//
		final List<I_C_AllocationLine> lines = allocationBuilder.getC_AllocationLines();
		updateCounter_AllocationLine_ID(lines);

		return PaymentAllocationId.ofRepoId(allocationHdr.getC_AllocationHdr_ID());
	}

	/**
	 * Sets the counter allocation line - that means the mathcing line
	 * The id is set only if we have 2 line: credit memo - invoice; purchase invoice - sales invoice; incoming payment - outgoing payment
	 *
	 * @param lines
	 */
	private static void updateCounter_AllocationLine_ID(final List<I_C_AllocationLine> lines)
	{
		if (lines.size() != 2)
		{
			return;
		}

		//
		final I_C_AllocationLine al1 = lines.get(0);
		final I_C_AllocationLine al2 = lines.get(1);

		al1.setCounter_AllocationLine_ID(al2.getC_AllocationLine_ID());
		InterfaceWrapperHelper.save(al1);

		//
		al2.setCounter_AllocationLine_ID(al1.getC_AllocationLine_ID());
		InterfaceWrapperHelper.save(al2);
	}

	/**
	 * Allocate {@link #getPayableDocuments()} and {@link #getPaymentDocuments()}.
	 *
	 * @return created allocation candidates
	 */
	private ImmutableList<AllocationLineCandidate> createAllocationLineCandidates()
	{
		//
		// Make sure we have something to allocate
		final List<PayableDocument> payableDocuments = getPayableDocuments();
		final List<IPaymentDocument> paymentDocuments = getPaymentDocuments();
		if (payableDocuments.isEmpty() && paymentDocuments.isEmpty())
		{
			throw new NoDocumentsPaymentAllocationException();
		}

		//
		// Make sure that we allow allocation one document per type for vendor documents
		assertOnlyOneVendorDocType(payableDocuments, paymentDocuments);

		final ImmutableList.Builder<AllocationLineCandidate> allocationCandidates = ImmutableList.builder();

		//
		// Try to allocate credit memos to regular invoices
		allocationCandidates.addAll(createAllocationLineCandidates_CreditMemosToInvoices(payableDocuments));

		//
		// Try to allocate purchase invoices to sales invoices
		if (Services.get(IPaymentAllocationBL.class).isPurchaseSalesInvoiceCompensationAllowed())
		{
			allocationCandidates.addAll(createAllocationLineCandidates_PurchaseInvoicesToSaleInvoices(payableDocuments));
		}

		//
		// Allocate payments to invoices
		allocationCandidates.addAll(createAllocationLineCandidates(
				AllocationLineCandidateType.InvoiceToPayment,
				payableDocuments,
				paymentDocuments));

		//
		// Try allocate payment reversals to payments
		allocationCandidates.addAll(createAllocationLineCandidates_ForPayments(paymentDocuments));

		// Try allocate the payable remaining Discounts and WriteOffs.
		allocationCandidates.addAll(createAllocationLineCandidates_DiscountAndWriteOffs(payableDocuments));

		return allocationCandidates.build();
	}

	/***
	 * Do not allow to allocate more then one document type for vendor documents
	 *
	 * @param payableDocuments
	 * @param paymentDocuments
	 */
	private void assertOnlyOneVendorDocType(
			final List<PayableDocument> payableDocuments,
			final List<IPaymentDocument> paymentDocuments)
	{
		if (!allowOnlyOneVendorDoc)
		{
			return; // task 09558: nothing to do
		}

		final List<IPaymentDocument> paymentVendorDocuments = paymentDocuments.stream()
				.filter(IPaymentDocument::isVendorDocument)
				.collect(ImmutableList.toImmutableList());

		final List<PayableDocument> payableVendorDocuments_NoCreditMemos = new ArrayList<>();
		final List<IPaymentDocument> paymentVendorDocuments_CreditMemos = new ArrayList<>();
		for (final PayableDocument payable : payableDocuments)
		{
			if (!payable.isVendorDocument())
			{
				continue;
			}

			if (payable.isCreditMemo())
			{
				paymentVendorDocuments_CreditMemos.add(CreditMemoInvoiceAsPaymentDocument.wrap(payable));
			}
			else
			{
				payableVendorDocuments_NoCreditMemos.add(payable);

			}
		}

		if (paymentVendorDocuments.size() > 1
				|| payableVendorDocuments_NoCreditMemos.size() > 1
				|| paymentVendorDocuments_CreditMemos.size() > 1)
		{
			final List<IPaymentDocument> paymentVendorDocs = new ArrayList<>();
			paymentVendorDocs.addAll(paymentVendorDocuments);
			paymentVendorDocs.addAll(paymentVendorDocuments_CreditMemos);

			throw new MultipleVendorDocumentsException(paymentVendorDocs, payableVendorDocuments_NoCreditMemos);
		}
	}

	/**
	 * Allocate given payments to given payable documents.
	 *
	 * @param payableDocuments
	 * @param paymentDocuments
	 * @return created allocation candidates
	 */
	private final List<AllocationLineCandidate> createAllocationLineCandidates(
			@NonNull final AllocationLineCandidateType type,
			@NonNull final List<PayableDocument> payableDocuments,
			@NonNull final List<IPaymentDocument> paymentDocuments)
	{
		if (payableDocuments.isEmpty() || paymentDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();

		//
		// First, loop through all Invoices, then, in every single Invoice loop, loop through all Payments.
		// Allocate the value of every Payment with the Invoice, until the Invoice value is reached.
		// In most cases this should only be one Payment.
		for (final PayableDocument payable : payableDocuments)
		{
			for (final IPaymentDocument payment : paymentDocuments)
			{
				// If the invoice was fully allocated, stop here and go to next invoice.
				if (payable.isFullyAllocated())
				{
					break;
				}

				// Skip fully allocated payments
				if (payment.isFullyAllocated())
				{
					continue;
				}

				// Skip if the invoice and payment are not compatible
				if (!isCompatible(payable, payment))
				{
					continue;
				}

				//
				// Calculate the amounts to allocate:
				final AllocationAmounts payableAmountsToAllocate = calculateAmountToAllocate(payable, payment);
				final Money payableOverUnderAmt = payable.calculateProjectedOverUnderAmt(payableAmountsToAllocate);
				final Money paymentOverUnderAmt = payment.calculateProjectedOverUnderAmt(payableAmountsToAllocate.getPayAmt());

				// Create new Allocation Line
				final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
						.type(type)
						//
						.bpartnerId(payable.getBpartnerId())
						//
						.payableDocumentRef(payable.getReference())
						.paymentDocumentRef(payment.getReference())
						// Amounts:
						.amount(payableAmountsToAllocate.getPayAmt().toBigDecimal())
						.discountAmt(payableAmountsToAllocate.getDiscountAmt().toBigDecimal())
						.writeOffAmt(payableAmountsToAllocate.getWriteOffAmt().toBigDecimal())
						.payableOverUnderAmt(payableOverUnderAmt.toBigDecimal())
						.paymentOverUnderAmt(paymentOverUnderAmt.toBigDecimal())
						//
						.build();
				allocationLineCandidates.add(allocationLine);

				// Update how much was allocated on current invoice and payment.
				payable.addAllocatedAmounts(payableAmountsToAllocate);
				payment.addAllocatedAmt(payableAmountsToAllocate.getPayAmt());
			}	// loop through payments for invoice
		}   // invoice loop

		return allocationLineCandidates;
	}

	private final List<AllocationLineCandidate> createAllocationLineCandidates_CreditMemosToInvoices(final List<PayableDocument> payableDocuments)
	{
		final List<PayableDocument> invoices = new ArrayList<>();
		final List<IPaymentDocument> creditMemos = new ArrayList<>();

		for (final PayableDocument payable : payableDocuments)
		{
			if (payable.isCreditMemo())
			{
				creditMemos.add(CreditMemoInvoiceAsPaymentDocument.wrap(payable));
			}
			else
			{
				invoices.add(payable);
			}
		}

		return createAllocationLineCandidates(
				AllocationLineCandidateType.InvoiceToCreditMemo,
				invoices,
				creditMemos);
	}

	private final List<AllocationLineCandidate> createAllocationLineCandidates_PurchaseInvoicesToSaleInvoices(final List<PayableDocument> payableDocuments)
	{
		final List<PayableDocument> salesInvoices = new ArrayList<>();
		final List<IPaymentDocument> purchaseInvoices = new ArrayList<>();

		for (final PayableDocument payable : payableDocuments)
		{
			// do not support credit memo
			if (payable.isCreditMemo())
			{
				continue;
			}

			if (payable.isCustomerDocument())
			{
				salesInvoices.add(payable);
			}
			else
			{
				purchaseInvoices.add(PurchaseInvoiceAsPaymentDocument.wrap(payable));
			}
		}

		return createAllocationLineCandidates(
				AllocationLineCandidateType.SalesInvoiceToPurchaseInvoice,
				salesInvoices,
				purchaseInvoices);
	}

	/**
	 * Iterate all payment documents and try to allocate incoming payments to outgoing payments
	 *
	 * @param paymentDocuments
	 */
	private final List<AllocationLineCandidate> createAllocationLineCandidates_ForPayments(final List<IPaymentDocument> paymentDocuments)
	{
		if (paymentDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Build the incoming payments and outgoing payments lists.
		final List<IPaymentDocument> paymentDocumentsIn = new ArrayList<>();
		final List<IPaymentDocument> paymentDocumentsOut = new ArrayList<>();
		for (final IPaymentDocument payment : paymentDocuments)
		{
			if (payment.isFullyAllocated())
			{
				continue;
			}
			if (payment.getAmountToAllocate().signum() > 0)
			{
				paymentDocumentsIn.add(payment);
			}
			else
			{
				paymentDocumentsOut.add(payment);
			}
		}
		// No documents => nothing to do
		if (paymentDocumentsIn.isEmpty() || paymentDocumentsOut.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Iterate incoming payments and try allocating them to outgoing payments.
		final List<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();
		for (final IPaymentDocument paymentIn : paymentDocumentsIn)
		{
			for (final IPaymentDocument paymentOut : paymentDocumentsOut)
			{
				if (paymentOut.isFullyAllocated())
				{
					continue;
				}

				final Money paymentOut_amtToAllocate = paymentOut.getAmountToAllocate(); // => negative
				final Money paymentIn_amtToAllocate = paymentIn.getAmountToAllocate().negate(); // => negative
				final Money amtToAllocate = paymentIn_amtToAllocate.max(paymentOut_amtToAllocate);

				final Money payableOverUnderAmt = paymentOut.calculateProjectedOverUnderAmt(amtToAllocate);
				final Money paymentOverUnderAmt = paymentIn.calculateProjectedOverUnderAmt(amtToAllocate.negate()).negate();

				// Create new Allocation Line
				final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
						.type(AllocationLineCandidateType.InboundPaymentToOutboundPayment)
						//
						.bpartnerId(paymentOut.getBpartnerId())
						//
						.payableDocumentRef(paymentOut.getReference())
						.paymentDocumentRef(paymentIn.getReference())
						// Amounts:
						.amount(amtToAllocate.toBigDecimal())
						.payableOverUnderAmt(payableOverUnderAmt.toBigDecimal())
						.paymentOverUnderAmt(paymentOverUnderAmt.toBigDecimal())
						//
						.build();
				allocationLineCandidates.add(allocationLine);

				// Update how much was allocated on current invoice and payment.
				paymentOut.addAllocatedAmt(amtToAllocate);
				paymentIn.addAllocatedAmt(amtToAllocate.negate());
			}
		}

		return allocationLineCandidates;
	}

	/**
	 * Iterate all given payable documents and create an allocation only for Discount and WriteOff amounts.
	 *
	 * @param payableDocuments
	 * @return created allocation candidates.
	 */
	private List<AllocationLineCandidate> createAllocationLineCandidates_DiscountAndWriteOffs(final List<PayableDocument> payableDocuments)
	{
		if (payableDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();
		for (final PayableDocument payable : payableDocuments)
		{
			final AllocationAmounts amountsToAllocate = payable.getAmountsToAllocate().withZeroPayAmt();
			if (amountsToAllocate.isZero())
			{
				continue;
			}

			final Money payableOverUnderAmt = payable.calculateProjectedOverUnderAmt(amountsToAllocate);
			final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
					.type(AllocationLineCandidateType.InvoiceDiscountOrWriteOff)
					//
					.bpartnerId(payable.getBpartnerId())
					//
					.payableDocumentRef(payable.getReference())
					.paymentDocumentRef(null) // nop
					// Amounts:
					.amount(amountsToAllocate.getPayAmt().toBigDecimal())
					.discountAmt(amountsToAllocate.getDiscountAmt().toBigDecimal())
					.writeOffAmt(amountsToAllocate.getWriteOffAmt().toBigDecimal())
					.payableOverUnderAmt(payableOverUnderAmt.toBigDecimal())
					.paymentOverUnderAmt(BigDecimal.ZERO)
					//
					.build();
			allocationLineCandidates.add(allocationLine);

			payable.addAllocatedAmounts(amountsToAllocate);
		}

		return allocationLineCandidates;
	}

	private final OptionalDeferredException<PaymentAllocationException> checkFullyAllocated()
	{
		//
		// Check payables (invoices, prepaid orders etc)
		{
			final List<PayableDocument> payableDocumentsNotFullyAllocated = getPayableDocumentsNotFullyAllocated();
			if (!payableDocumentsNotFullyAllocated.isEmpty())
			{
				return OptionalDeferredException.of(() -> new PayableDocumentNotAllocatedException(payableDocumentsNotFullyAllocated));
			}
		}

		//
		// Check payments
		{
			final List<IPaymentDocument> paymentDocumentsNotFullyAllocated = getPaymentDocumentsNotFullyAllocated();
			if (!paymentDocumentsNotFullyAllocated.isEmpty())
			{
				return OptionalDeferredException.of(() -> new PaymentDocumentNotAllocatedException(paymentDocumentsNotFullyAllocated));
			}
		}

		return OptionalDeferredException.noError();
	}

	private List<IPaymentDocument> getPaymentDocumentsNotFullyAllocated()
	{
		return getPaymentDocuments()
				.stream()
				.filter(payment -> !payment.isFullyAllocated())
				.collect(ImmutableList.toImmutableList());
	}

	private List<PayableDocument> getPayableDocumentsNotFullyAllocated()
	{
		return getPayableDocuments()
				.stream()
				.filter(payable -> !payable.isFullyAllocated())
				.collect(ImmutableList.toImmutableList());
	}

	/**
	 * Check if given payment document can be allocated to payable document.
	 *
	 * @param payable
	 * @param payment
	 * @return true if the invoice and payment are compatible and we could try to do an allocation
	 */
	private final boolean isCompatible(final PayableDocument payable, final IPaymentDocument payment)
	{
		// Given payment does not support payable's type
		if (!payment.canPay(payable))
		{
			return false;
		}

		//
		// Check invoice-payment compatibility: same sign
		final boolean positiveInvoiceAmtToAllocate = payable.getAmountsToAllocateInitial().getPayAmt().signum() >= 0;
		final boolean positivePaymentAmtToAllocate = payment.getAmountToAllocateInitial().signum() >= 0;
		if (positiveInvoiceAmtToAllocate != positivePaymentAmtToAllocate)
		{
			return false;
		}

		//
		// Check invoice-payment compatibility: same BPartner
		// NOTE: we don't check this because we are allowed to allocate invoice-payments of different BPartners
		// Think about BP relations.

		return true;
	}

	/**
	 *
	 * @param invoice
	 * @param payment
	 * @return how much we maximum allocate between given invoice and given payment.
	 */
	private final AllocationAmounts calculateAmountToAllocate(final PayableDocument invoice, final IPaymentDocument payment)
	{
		final AllocationAmounts invoiceAmountsToAllocate = invoice.getAmountsToAllocate();
		final Money invoicePayAmtToAllocate = invoiceAmountsToAllocate.getPayAmt();
		final Money paymentAmountToAllocate = payment.getAmountToAllocate();

		if (invoicePayAmtToAllocate.signum() >= 0)
		{
			// Invoice(+), Payment(+)
			if (paymentAmountToAllocate.signum() >= 0)
			{
				final Money payAmt = invoicePayAmtToAllocate.min(paymentAmountToAllocate);
				return invoiceAmountsToAllocate.withPayAmt(payAmt);
			}
			// Invoice(+), Payment(-)
			else
			{
				return invoiceAmountsToAllocate.withZeroPayAmt();
			}
		}
		else
		{
			// Invoice(-), Payment(+)
			if (paymentAmountToAllocate.signum() >= 0)
			{
				return invoiceAmountsToAllocate.withZeroPayAmt();
			}
			// Invoice(-), Payment(-)
			else
			{
				final Money payAmt = invoicePayAmtToAllocate.max(paymentAmountToAllocate);
				return invoiceAmountsToAllocate.withPayAmt(payAmt);
			}
		}
	}

	private final void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	private final void assertNotBuilt()
	{
		Check.assume(!_built, "Not already built");
	}

	public PaymentAllocationBuilder orgId(final OrgId adOrgId)
	{
		assertNotBuilt();
		_adOrgId = adOrgId;
		return this;
	}

	private final OrgId getOrgId()
	{
		if (_adOrgId == null || !_adOrgId.isRegular())
		{
			throw new PaymentAllocationException("@Org0NotAllowed@");
		}
		return _adOrgId;
	}

	private final CurrencyId getCurrencyId()
	{
		Check.assumeNotNull(_currencyId, "currencyId shall be set");
		return _currencyId;
	}

	/** Sets the currency of all invoices and payments set. */
	public PaymentAllocationBuilder currencyId(final CurrencyId currencyId)
	{
		assertNotBuilt();
		_currencyId = currencyId;
		return this;
	}

	private final LocalDate getDateTrx()
	{
		Check.assumeNotNull(_dateTrx, "date not null");
		return _dateTrx;
	}

	public PaymentAllocationBuilder dateTrx(final LocalDate dateTrx)
	{
		assertNotBuilt();
		_dateTrx = dateTrx;
		return this;
	}

	private final LocalDate getDateAcct()
	{
		Check.assumeNotNull(_dateAcct, "date not null");
		return _dateAcct;
	}

	public PaymentAllocationBuilder dateAcct(final LocalDate dateAcct)
	{
		assertNotBuilt();
		_dateAcct = dateAcct;
		return this;
	}

	public PaymentAllocationBuilder allowOnlyOneVendorDoc(final boolean allowOnlyOneVendorDoc)
	{
		assertNotBuilt();
		this.allowOnlyOneVendorDoc = allowOnlyOneVendorDoc;
		return this;
	}

	public PaymentAllocationBuilder allowPartialAllocations(final boolean allowPartialAllocations)
	{
		assertNotBuilt();
		this.allowPartialAllocations = allowPartialAllocations;
		return this;
	}

	public PaymentAllocationBuilder dryRun()
	{
		this.dryRun = true;
		return this;
	}

	public PaymentAllocationBuilder payableDocuments(final Collection<PayableDocument> payableDocuments)
	{
		_payableDocuments = ImmutableList.copyOf(payableDocuments);
		return this;
	}

	public PaymentAllocationBuilder paymentDocuments(final Collection<? extends IPaymentDocument> paymentDocuments)
	{
		_paymentDocuments = ImmutableList.copyOf(paymentDocuments);
		return this;
	}

	public PaymentAllocationBuilder paymentDocument(final IPaymentDocument paymentDocument)
	{
		return paymentDocuments(ImmutableList.of(paymentDocument));
	}

	private final List<PayableDocument> getPayableDocuments()
	{
		return _payableDocuments;
	}

	private final List<IPaymentDocument> getPaymentDocuments()
	{
		return _paymentDocuments;
	}
}

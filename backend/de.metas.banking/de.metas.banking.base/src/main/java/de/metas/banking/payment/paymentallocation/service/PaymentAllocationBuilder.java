package de.metas.banking.payment.paymentallocation.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.allocation.api.PaymentAllocationId;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.money.Money;
import de.metas.util.Check;
import de.metas.util.OptionalDeferredException;
import de.metas.util.Services;
import lombok.NonNull;

/**
 * Builds one {@link I_C_AllocationHdr} of all given {@link PayableDocument}s and {@link PaymentDocument}s.
 *
 * @author tsa
 *
 */
public class PaymentAllocationBuilder
{
	public enum PayableRemainingOpenAmtPolicy
	{
		DO_NOTHING, WRITE_OFF, DISCOUNT
	}

	public static final PaymentAllocationBuilder newBuilder()
	{
		return new PaymentAllocationBuilder();
	}

	// services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final AllocationLineCandidateSaver candidatesSaver = new AllocationLineCandidateSaver();

	// Parameters
	private LocalDate _dateTrx;
	private LocalDate _dateAcct;
	private ImmutableList<PayableDocument> _payableDocuments = ImmutableList.of();
	private ImmutableList<PaymentDocument> _paymentDocuments = ImmutableList.of();
	private boolean allowOnlyOneVendorDoc = true;
	private boolean allowPartialAllocations = false;
	private boolean allowPurchaseSalesInvoiceCompensation;
	private PayableRemainingOpenAmtPolicy payableRemainingOpenAmtPolicy = PayableRemainingOpenAmtPolicy.DO_NOTHING;
	private boolean dryRun = false;
	private InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;

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
			paymentAllocationIds = processCandidates(candidates);
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

	private ImmutableSet<PaymentAllocationId> processCandidates(final Collection<AllocationLineCandidate> candidates)
	{
		return trxManager.callInThreadInheritedTrx(() -> processCandidatesInTrx(candidates));
	}

	private ImmutableSet<PaymentAllocationId> processCandidatesInTrx(final Collection<AllocationLineCandidate> candidates)
	{
		try
		{
			ImmutableList<AllocationLineCandidate> candidatesEffective = ImmutableList.copyOf(candidates);

			candidatesEffective = candidatesEffective.stream()
					.map(this::processInvoiceProcessingFeeCandidate)
					.collect(ImmutableList.toImmutableList());

			return candidatesSaver.save(candidatesEffective);
		}
		catch (final Exception ex)
		{
			throw PaymentAllocationException.wrapIfNeeded(ex);
		}
	}

	private AllocationLineCandidate processInvoiceProcessingFeeCandidate(@NonNull final AllocationLineCandidate candidate)
	{
		if (!AllocationLineCandidateType.InvoiceProcessingFee.equals(candidate.getType()))
		{
			return candidate;
		}

		if (invoiceProcessingServiceCompanyService == null)
		{
			throw new AdempiereException("Cannot process invoice fee candidates because no service was configured");
		}

		final AllocationAmounts amounts = AllocationAmounts.builder()
				.discountAmt(candidate.getAmounts().getDiscountAmt())
				.writeOffAmt(candidate.getAmounts().getWriteOffAmt())
				.invoiceProcessingFee(candidate.getAmounts().getInvoiceProcessingFee())
				.build();
		Check.assumeEquals(amounts, candidate.getAmounts());

		final InvoiceId serviceInvoiceId = invoiceProcessingServiceCompanyService.generateServiceInvoice(
				candidate.getInvoiceProcessingFeeCalculation(),
				candidate.getAmounts().getInvoiceProcessingFee());

		return candidate.toBuilder()
				.type(AllocationLineCandidateType.SalesInvoiceToPurchaseInvoice)
				.amounts(amounts.toBuilder()
						.payAmt(amounts.getInvoiceProcessingFee())
						.invoiceProcessingFee(null)
						.build())
				.paymentDocumentRef(TableRecordReference.of(I_C_Invoice.Table_Name, serviceInvoiceId))
				.build();
	}

	/**
	 * Create and complete one {@link I_C_AllocationHdr} for given candidate.
	 */

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
		final List<PaymentDocument> paymentDocuments = getPaymentDocuments();
		if (payableDocuments.isEmpty() && paymentDocuments.isEmpty())
		{
			throw new NoDocumentsPaymentAllocationException();
		}

		//
		// Make sure that we allow allocation one document per type for vendor documents
		assertOnlyOneVendorDocType(payableDocuments, paymentDocuments);

		final ImmutableList.Builder<AllocationLineCandidate> allocationCandidates = ImmutableList.builder();

		//
		// Allocate invoice processing fees first
		allocationCandidates.addAll(createAllocationLineCandidates_InvoiceProcessingFee(payableDocuments));

		//
		// Try to allocate credit memos to regular invoices
		allocationCandidates.addAll(createAllocationLineCandidates_CreditMemosToInvoices(payableDocuments));

		//
		// Try to allocate purchase invoices to sales invoices
		if (allowPurchaseSalesInvoiceCompensation)
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
		allocationCandidates.addAll(createAllocationLineCandidates_InboundPaymentToOutboundPayment(paymentDocuments));

		// Try allocate the payable remaining Discounts and WriteOffs.
		allocationCandidates.addAll(createAllocationLineCandidates_DiscountAndWriteOffs(payableDocuments));

		return allocationCandidates.build();
	}

	/***
	 * Do not allow to allocate more then one document type for vendor documents
	 */
	private void assertOnlyOneVendorDocType(
			final List<PayableDocument> payableDocuments,
			final List<PaymentDocument> paymentDocuments)
	{
		if (!allowOnlyOneVendorDoc)
		{
			return; // task 09558: nothing to do
		}

		final List<PaymentDocument> paymentVendorDocuments = paymentDocuments.stream()
				.filter(paymentDocument -> paymentDocument.getPaymentDirection().isOutboundPayment())
				.collect(ImmutableList.toImmutableList());

		final List<PayableDocument> payableVendorDocuments_NoCreditMemos = new ArrayList<>();
		final List<CreditMemoInvoiceAsPaymentDocumentWrapper> paymentVendorDocuments_CreditMemos = new ArrayList<>();
		for (final PayableDocument payable : payableDocuments)
		{
			if (!payable.getSoTrx().isPurchase())
			{
				continue;
			}

			if (payable.isCreditMemo())
			{
				paymentVendorDocuments_CreditMemos.add(CreditMemoInvoiceAsPaymentDocumentWrapper.wrap(payable));
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

	private final List<AllocationLineCandidate> createAllocationLineCandidates(
			@NonNull final AllocationLineCandidateType type,
			@NonNull final List<PayableDocument> payableDocuments,
			@NonNull final List<? extends IPaymentDocument> paymentDocuments)
	{
		if (payableDocuments.isEmpty() || paymentDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();

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
				final Money payableOverUnderAmt = payable.computeProjectedOverUnderAmt(payableAmountsToAllocate);
				final Money paymentOverUnderAmt = payment.calculateProjectedOverUnderAmt(payableAmountsToAllocate.getPayAmt());

				// Create new Allocation Line
				final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
						.type(type)
						//
						.orgId(payable.getOrgId())
						.bpartnerId(payable.getBpartnerId())
						//
						.payableDocumentRef(payable.getReference())
						.paymentDocumentRef(payment.getReference())
						//
						.dateTrx(getDateTrx())
						.dateAcct(getDateAcct())
						//
						// Amounts:
						.amounts(payableAmountsToAllocate)
						.payableOverUnderAmt(payableOverUnderAmt)
						.paymentOverUnderAmt(paymentOverUnderAmt)
						//
						.build();
				allocationLineCandidates.add(allocationLine);

				// Update how much was allocated on current invoice and payment.
				payable.addAllocatedAmounts(payableAmountsToAllocate);
				payment.addAllocatedAmt(payableAmountsToAllocate.getPayAmt());
			}	// loop through payments for current payable (aka invoice or prepay order)

			if (!payable.isFullyAllocated())
			{
				final AllocationLineCandidate allocationLine = createAllocationLineCandidate_ForRemainingOpenAmt(payable);
				if (allocationLine != null)
				{
					allocationLineCandidates.add(allocationLine);
				}
			}
		}   // payables loop (aka invoice or prepay order)

		return allocationLineCandidates;
	}

	private final AllocationLineCandidate createAllocationLineCandidate_ForRemainingOpenAmt(@NonNull final PayableDocument payable)
	{
		if (payable.isFullyAllocated())
		{
			return null;
		}

		if (PayableRemainingOpenAmtPolicy.DO_NOTHING.equals(payableRemainingOpenAmtPolicy))
		{
			// do nothing
			return null;
		}
		else if (PayableRemainingOpenAmtPolicy.DISCOUNT.equals(payableRemainingOpenAmtPolicy))
		{
			payable.moveRemainingOpenAmtToDiscount();
			return createAllocationLineCandidate_DiscountAndWriteOff(payable);
		}
		else if (PayableRemainingOpenAmtPolicy.WRITE_OFF.equals(payableRemainingOpenAmtPolicy))
		{
			payable.moveRemainingOpenAmtToWriteOff();
			return createAllocationLineCandidate_DiscountAndWriteOff(payable);
		}
		else
		{
			throw new AdempiereException("Unknown payableRemainingOpenAmtPolicy: " + payableRemainingOpenAmtPolicy); // shall not happen
		}
	}

	private final List<AllocationLineCandidate> createAllocationLineCandidates_CreditMemosToInvoices(final List<PayableDocument> payableDocuments)
	{
		if (payableDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<PayableDocument> invoices = new ArrayList<>();
		final List<CreditMemoInvoiceAsPaymentDocumentWrapper> creditMemos = new ArrayList<>();
		for (final PayableDocument payable : payableDocuments)
		{
			if (payable.isCreditMemo())
			{
				creditMemos.add(CreditMemoInvoiceAsPaymentDocumentWrapper.wrap(payable));
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
		if (payableDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<PayableDocument> salesInvoices = new ArrayList<>();
		final List<PurchaseInvoiceAsInboundPaymentDocumentWrapper> purchaseInvoices = new ArrayList<>();
		for (final PayableDocument payable : payableDocuments)
		{
			// do not support credit memo
			if (payable.isCreditMemo())
			{
				continue;
			}

			if (payable.getSoTrx().isSales())
			{
				salesInvoices.add(payable);
			}
			else
			{
				purchaseInvoices.add(PurchaseInvoiceAsInboundPaymentDocumentWrapper.wrap(payable));
			}
		}

		return createAllocationLineCandidates(
				AllocationLineCandidateType.SalesInvoiceToPurchaseInvoice,
				salesInvoices,
				purchaseInvoices);
	}

	private final List<AllocationLineCandidate> createAllocationLineCandidates_InboundPaymentToOutboundPayment(final List<PaymentDocument> paymentDocuments)
	{
		if (paymentDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Build the incoming payments and outgoing payments lists.
		final List<PaymentDocument> paymentDocumentsIn = new ArrayList<>();
		final List<PaymentDocument> paymentDocumentsOut = new ArrayList<>();
		for (final PaymentDocument payment : paymentDocuments)
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
		for (final PaymentDocument paymentIn : paymentDocumentsIn)
		{
			for (final PaymentDocument paymentOut : paymentDocumentsOut)
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
						.orgId(paymentOut.getOrgId())
						.bpartnerId(paymentOut.getBpartnerId())
						//
						.payableDocumentRef(paymentOut.getReference())
						.paymentDocumentRef(paymentIn.getReference())
						//
						.dateTrx(getDateTrx())
						.dateAcct(getDateAcct())
						//
						// Amounts:
						.amounts(AllocationAmounts.ofPayAmt(amtToAllocate))
						.payableOverUnderAmt(payableOverUnderAmt)
						.paymentOverUnderAmt(paymentOverUnderAmt)
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

		final ArrayList<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();
		for (final PayableDocument payable : payableDocuments)
		{
			final AllocationLineCandidate allocationLine = createAllocationLineCandidate_DiscountAndWriteOff(payable);
			if (allocationLine != null)
			{
				allocationLineCandidates.add(allocationLine);
			}
		}

		return allocationLineCandidates;
	}

	@Nullable
	private AllocationLineCandidate createAllocationLineCandidate_DiscountAndWriteOff(@NonNull final PayableDocument payable)
	{
		final AllocationAmounts amountsToAllocate = AllocationAmounts.builder()
				.discountAmt(payable.getAmountsToAllocate().getDiscountAmt())
				.writeOffAmt(payable.getAmountsToAllocate().getWriteOffAmt())
				.build();
		if (amountsToAllocate.isZero())
		{
			return null;
		}

		final Money payableOverUnderAmt = payable.computeProjectedOverUnderAmt(amountsToAllocate);
		final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
				.type(AllocationLineCandidateType.InvoiceDiscountOrWriteOff)
				//
				.orgId(payable.getOrgId())
				.bpartnerId(payable.getBpartnerId())
				//
				.payableDocumentRef(payable.getReference())
				.paymentDocumentRef(null) // nop
				//
				.dateTrx(getDateTrx())
				.dateAcct(getDateAcct())
				//
				// Amounts:
				.amounts(amountsToAllocate)
				.payableOverUnderAmt(payableOverUnderAmt)
				// .paymentOverUnderAmt(ZERO)
				//
				.build();

		payable.addAllocatedAmounts(amountsToAllocate);

		return allocationLine;
	}

	private List<AllocationLineCandidate> createAllocationLineCandidates_InvoiceProcessingFee(final List<PayableDocument> payableDocuments)
	{
		if (payableDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final ArrayList<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();
		for (final PayableDocument payable : payableDocuments)
		{
			final AllocationLineCandidate allocationLine = createAllocationLineCandidate_InvoiceProcessingFee(payable);
			if (allocationLine != null)
			{
				allocationLineCandidates.add(allocationLine);
			}
		}

		return allocationLineCandidates;
	}

	private AllocationLineCandidate createAllocationLineCandidate_InvoiceProcessingFee(@NonNull final PayableDocument payable)
	{
		final AllocationAmounts amountsToAllocate = AllocationAmounts.builder()
				.invoiceProcessingFee(payable.getAmountsToAllocate().getInvoiceProcessingFee())
				.build();
		if (amountsToAllocate.isZero())
		{
			return null;
		}

		final Money payableOverUnderAmt = payable.computeProjectedOverUnderAmt(amountsToAllocate);
		final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
				.type(AllocationLineCandidateType.InvoiceProcessingFee)
				//
				.orgId(payable.getOrgId())
				.bpartnerId(payable.getBpartnerId())
				//
				.payableDocumentRef(payable.getReference())
				.paymentDocumentRef(null) // nop
				//
				.dateTrx(getDateTrx())
				.dateAcct(getDateAcct())
				//
				// Amounts:
				.amounts(amountsToAllocate)
				.payableOverUnderAmt(payableOverUnderAmt)
				.invoiceProcessingFeeCalculation(payable.getInvoiceProcessingFeeCalculation())
				//
				.build();

		payable.addAllocatedAmounts(amountsToAllocate);

		return allocationLine;
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
			final List<PaymentDocument> paymentDocumentsNotFullyAllocated = getPaymentDocumentsNotFullyAllocated();
			if (!paymentDocumentsNotFullyAllocated.isEmpty())
			{
				return OptionalDeferredException.of(() -> new PaymentDocumentNotAllocatedException(paymentDocumentsNotFullyAllocated));
			}
		}

		return OptionalDeferredException.noError();
	}

	private List<PaymentDocument> getPaymentDocumentsNotFullyAllocated()
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
	private static final boolean isCompatible(final PayableDocument payable, final IPaymentDocument payment)
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

	public PaymentAllocationBuilder allowPurchaseSalesInvoiceCompensation(final boolean allowPurchaseSalesInvoiceCompensation)
	{
		assertNotBuilt();
		this.allowPurchaseSalesInvoiceCompensation = allowPurchaseSalesInvoiceCompensation;
		return this;
	}

	public PaymentAllocationBuilder payableRemainingOpenAmtPolicy(@NonNull final PayableRemainingOpenAmtPolicy payableRemainingOpenAmtPolicy)
	{
		assertNotBuilt();
		this.payableRemainingOpenAmtPolicy = payableRemainingOpenAmtPolicy;
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

	public PaymentAllocationBuilder paymentDocuments(final Collection<PaymentDocument> paymentDocuments)
	{
		_paymentDocuments = ImmutableList.copyOf(paymentDocuments);
		return this;
	}

	public PaymentAllocationBuilder paymentDocument(final PaymentDocument paymentDocument)
	{
		return paymentDocuments(ImmutableList.of(paymentDocument));
	}

	@VisibleForTesting
	final ImmutableList<PayableDocument> getPayableDocuments()
	{
		return _payableDocuments;
	}

	@VisibleForTesting
	final ImmutableList<PaymentDocument> getPaymentDocuments()
	{
		return _paymentDocuments;
	}

	public PaymentAllocationBuilder invoiceProcessingServiceCompanyService(@NonNull final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService)
	{
		assertNotBuilt();
		this.invoiceProcessingServiceCompanyService = invoiceProcessingServiceCompanyService;
		return this;
	}
}

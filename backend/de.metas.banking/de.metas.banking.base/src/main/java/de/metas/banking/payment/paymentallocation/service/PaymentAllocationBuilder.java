/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.banking.payment.paymentallocation.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.allocation.api.PaymentAllocationId;
import de.metas.banking.payment.paymentallocation.service.AllocationLineCandidate.AllocationLineCandidateType;
import de.metas.currency.CurrencyConversionContext;
import de.metas.currency.CurrencyRate;
import de.metas.currency.FixedConversionRate;
import de.metas.currency.ICurrencyBL;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.OptionalDeferredException;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Builds one {@link I_C_AllocationHdr} of all given {@link PayableDocument}s and {@link PaymentDocument}s.
 */
public class PaymentAllocationBuilder
{
	public enum PayableRemainingOpenAmtPolicy
	{
		DO_NOTHING, WRITE_OFF, DISCOUNT
	}

	public static PaymentAllocationBuilder newBuilder()
	{
		return new PaymentAllocationBuilder();
	}

	// services
	private final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final AllocationLineCandidateSaver candidatesSaver = new AllocationLineCandidateSaver();

	// Parameters
	private LocalDate _defaultDateTrx;
	private ImmutableList<PayableDocument> _payableDocuments = ImmutableList.of();
	private ImmutableList<PaymentDocument> _paymentDocuments = ImmutableList.of();
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
		final ImmutableMap<PaymentAllocationId, AllocationLineCandidate> paymentAllocations;
		if (!candidates.isEmpty() && !dryRun)
		{
			paymentAllocations = processCandidates(candidates);
		}
		else
		{
			paymentAllocations = ImmutableMap.of();
		}

		return PaymentAllocationResult.builder()
				.candidates(candidates)
				.fullyAllocatedCheck(fullyAllocatedCheck)
				.paymentAllocationIds(paymentAllocations)
				.build();
	}

	private ImmutableMap<PaymentAllocationId, AllocationLineCandidate> processCandidates(final Collection<AllocationLineCandidate> candidates)
	{
		return trxManager.callInThreadInheritedTrx(() -> processCandidatesInTrx(candidates));
	}

	private ImmutableMap<PaymentAllocationId, AllocationLineCandidate> processCandidatesInTrx(final Collection<AllocationLineCandidate> candidates)
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
		// Make sure that we allow allocation one payment for vendor documents
		assertOnlyOnePaymentVendorDocType(paymentDocuments);

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

		// Try allocate the payable remaining WriteOffs.
		allocationCandidates.addAll(createAllocationLineCandidates_WriteOffs(payableDocuments));

		return allocationCandidates.build();
	}

	/***
	 * Do not allow to allocate more then one payment for vendor documents
	 */
	private void assertOnlyOnePaymentVendorDocType(final List<PaymentDocument> paymentDocuments)
	{
		final List<PaymentDocument> paymentVendorDocuments = paymentDocuments.stream()
				.filter(paymentDocument -> paymentDocument.getPaymentDirection().isOutboundPayment())
				.collect(ImmutableList.toImmutableList());

		if (paymentVendorDocuments.size() > 1)
		{
			throw new MultipleVendorDocumentsException(paymentVendorDocuments);
		}
	}

	private List<AllocationLineCandidate> createAllocationLineCandidates(
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
				final InvoiceAndPaymentAmountsToAllocate amountsToAllocate = calculateAmountToAllocate(payable, payment);
				final Money payableOverUnderAmt = payable.computeProjectedOverUnderAmt(amountsToAllocate.getInvoiceAmountsToAllocateInInvoiceCurrency());
				final Money paymentOverUnderAmt = computePaymentOverUnderAmtInInvoiceCurrency(payment, amountsToAllocate);

				// Update how much was allocated on current invoice and payment.
				payable.addAllocatedAmounts(amountsToAllocate.getInvoiceAmountsToAllocateInInvoiceCurrency());
				payment.addAllocatedAmt(amountsToAllocate.getPayAmtInPaymentCurrency());

				//dev-note: register payment discount when fully allocated
				final Money paymentDiscountAmtInInvoiceCurrency = computePaymentDiscountAmtInInvoiceCurrency(payment, amountsToAllocate);

				// Create new Allocation Line
				final LocalDate dateTrx = TimeUtil.max(payable.getDate(), payment.getDate());
				final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
						.type(type)
						//
						.orgId(payable.getClientAndOrgId().getOrgId())
						.bpartnerId(payable.getBpartnerId())
						//
						.payableDocumentRef(payable.getReference())
						.paymentDocumentRef(payment.getReference())
						//
						.dateTrx(dateTrx)
						.dateAcct(dateTrx)
						//
						// Amounts:
						.amounts(amountsToAllocate.getInvoiceAmountsToAllocateInInvoiceCurrency())
						.payableOverUnderAmt(payableOverUnderAmt)
						.paymentOverUnderAmt(paymentOverUnderAmt)
						.payAmtDiscountInInvoiceCurrency(paymentDiscountAmtInInvoiceCurrency)
						//
						.build();
				allocationLineCandidates.add(allocationLine);

			}    // loop through payments for current payable (aka invoice or prepay order)

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

	@NonNull
	private static Money computePaymentOverUnderAmtInInvoiceCurrency(@NonNull final IPaymentDocument payment, @NonNull final InvoiceAndPaymentAmountsToAllocate amountsToAllocate)
	{
		final Money paymentOverUnderAmtInPaymentCurrency = payment.calculateProjectedOverUnderAmt(amountsToAllocate.getPayAmtInPaymentCurrency());
		return amountsToAllocate.currencyRate.convertAmount(paymentOverUnderAmtInPaymentCurrency);
	}

	@NonNull
	private static Money computePaymentDiscountAmtInInvoiceCurrency(
			@NonNull final IPaymentDocument payment,
			@NonNull final InvoiceAndPaymentAmountsToAllocate amountsToAllocate)
	{
		final Money paymentDiscountAmtInPaymentCurrency = payment.isFullyAllocated()
				? payment.getPaymentDiscountAmt()
				: payment.getPaymentDiscountAmt().toZero();

		return amountsToAllocate.currencyRate.convertAmount(paymentDiscountAmtInPaymentCurrency);
	}
	
	@Nullable
	private AllocationLineCandidate createAllocationLineCandidate_ForRemainingOpenAmt(@NonNull final PayableDocument payable)
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

	private List<AllocationLineCandidate> createAllocationLineCandidates_CreditMemosToInvoices(@NonNull final List<PayableDocument> payableDocuments)
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

	private List<AllocationLineCandidate> createAllocationLineCandidates_PurchaseInvoicesToSaleInvoices(@NonNull final List<PayableDocument> payableDocuments)
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

	private List<AllocationLineCandidate> createAllocationLineCandidates_InboundPaymentToOutboundPayment(@NonNull final List<PaymentDocument> paymentDocuments)
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
				final LocalDate dateTrx = TimeUtil.max(paymentOut.getDate(), paymentIn.getDate());
				final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
						.type(AllocationLineCandidateType.InboundPaymentToOutboundPayment)
						//
						.orgId(paymentOut.getClientAndOrgId().getOrgId())
						.bpartnerId(paymentOut.getBpartnerId())
						//
						.payableDocumentRef(paymentOut.getReference())
						.paymentDocumentRef(paymentIn.getReference())
						//
						.dateTrx(dateTrx)
						.dateAcct(dateTrx)
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
	 * Iterate all given payable documents and create an allocation only for WriteOff amounts.
	 *
	 * @return created allocation candidates.
	 */
	private List<AllocationLineCandidate> createAllocationLineCandidates_WriteOffs(final List<PayableDocument> payableDocuments)
	{
		if (payableDocuments.isEmpty())
		{
			return ImmutableList.of();
		}

		final ArrayList<AllocationLineCandidate> allocationLineCandidates = new ArrayList<>();
		for (final PayableDocument payable : payableDocuments)
		{
			if (payable.getAmountsToAllocate().getWriteOffAmt().isZero())
			{
				continue;
			}

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

		final LocalDate dateTrx = getDefaultDateTrx();
		final Money payableOverUnderAmt = payable.computeProjectedOverUnderAmt(amountsToAllocate);
		final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
				.type(AllocationLineCandidateType.InvoiceDiscountOrWriteOff)
				//
				.orgId(payable.getClientAndOrgId().getOrgId())
				.bpartnerId(payable.getBpartnerId())
				//
				.payableDocumentRef(payable.getReference())
				.paymentDocumentRef(null) // nop
				//
				.dateTrx(dateTrx)
				.dateAcct(dateTrx)
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

	@Nullable
	private AllocationLineCandidate createAllocationLineCandidate_InvoiceProcessingFee(@NonNull final PayableDocument payable)
	{
		final AllocationAmounts amountsToAllocate = AllocationAmounts.builder()
				.invoiceProcessingFee(payable.getAmountsToAllocate().getInvoiceProcessingFee())
				.build();
		if (amountsToAllocate.isZero())
		{
			return null;
		}

		final OrgId orgId = payable.getClientAndOrgId().getOrgId();
		final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation = payable.getInvoiceProcessingFeeCalculation();
		final LocalDate dateTrx = TimeUtil.asLocalDate(invoiceProcessingFeeCalculation.getEvaluationDate(), orgDAO.getTimeZone(orgId));
		final Money payableOverUnderAmt = payable.computeProjectedOverUnderAmt(amountsToAllocate);
		final AllocationLineCandidate allocationLine = AllocationLineCandidate.builder()
				.type(AllocationLineCandidateType.InvoiceProcessingFee)
				//
				.orgId(orgId)
				.bpartnerId(payable.getBpartnerId())
				//
				.payableDocumentRef(payable.getReference())
				.paymentDocumentRef(null) // nop
				//
				.dateTrx(dateTrx)
				.dateAcct(dateTrx)
				//
				// Amounts:
				.amounts(amountsToAllocate)
				.payableOverUnderAmt(payableOverUnderAmt)
				.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
				//
				.build();

		payable.addAllocatedAmounts(amountsToAllocate);

		return allocationLine;
	}

	private OptionalDeferredException<PaymentAllocationException> checkFullyAllocated()
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
	 * @return true if the invoice and payment are compatible and we could try to do an allocation
	 */
	private static boolean isCompatible(@NonNull final PayableDocument payable, @NonNull final IPaymentDocument payment)
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
			if (!payable.isAllowAllocateAgainstDifferentSignumPayment())
			{
				return false;
			}
		}

		//
		// Check invoice-payment compatibility: same BPartner
		// NOTE: we don't check this because we are allowed to allocate invoice-payments of different BPartners
		// Think about BP relations.

		return true;
	}

	/**
	 * The amounts returned here are equal.
	 * The only difference is that they could be represented in different currencies in case the 2 documents are in different currencies.
	 */
	@Value
	@Builder
	private static class InvoiceAndPaymentAmountsToAllocate
	{
		@NonNull
		AllocationAmounts invoiceAmountsToAllocateInInvoiceCurrency;

		@NonNull
		Money payAmtInPaymentCurrency;

		@NonNull
		CurrencyRate currencyRate;
	}

	/**
	 * @return how much we maximum allocate between given invoice and given payment.
	 */
	@NonNull
	private InvoiceAndPaymentAmountsToAllocate calculateAmountToAllocate(@NonNull final PayableDocument invoice, @NonNull final IPaymentDocument payment)
	{
		final CurrencyId paymentCurrencyId = payment.getCurrencyId();
		final CurrencyId invoiceCurrencyId = invoice.getCurrencyId();

		final AllocationAmounts invoiceAmountsToAllocate = invoice.getAmountsToAllocate();
		final Money invoicePayAmtToAllocate = invoiceAmountsToAllocate.getPayAmt();

		final CurrencyConversionContext conversionContext = getCurrencyConversionContext(payment);
		final CurrencyRate currencyRate = currencyBL.getCurrencyRate(conversionContext, paymentCurrencyId, invoiceCurrencyId);

		final Money paymentAmountToAllocate = currencyRate.convertAmount(payment.getAmountToAllocate());

		if (invoicePayAmtToAllocate.signum() >= 0)
		{
			// Invoice(+), Payment(+)
			if (paymentAmountToAllocate.signum() >= 0)
			{
				final Money payAmtInInvoiceCurrency = invoicePayAmtToAllocate.min(paymentAmountToAllocate);
				final Money payAmtInPaymentCurrency = currencyRate.reverseConvertAmount(payAmtInInvoiceCurrency);

				return InvoiceAndPaymentAmountsToAllocate.builder()
						.invoiceAmountsToAllocateInInvoiceCurrency(getEffectiveInvoiceAmountsToAllocate(invoiceAmountsToAllocate, payAmtInInvoiceCurrency))
						.payAmtInPaymentCurrency(payAmtInPaymentCurrency)
						.currencyRate(currencyRate)
						.build();
			}
			// Invoice(+), Payment(-)
			else
			{
				return InvoiceAndPaymentAmountsToAllocate.builder()
						.invoiceAmountsToAllocateInInvoiceCurrency(invoiceAmountsToAllocate.withZeroPayAmt())
						.payAmtInPaymentCurrency(Money.zero(paymentCurrencyId))
						.currencyRate(currencyRate)
						.build();
			}
		}
		else
		{
			// Invoice(-), Payment(+)
			if (paymentAmountToAllocate.signum() >= 0)
			{
				// case: we get an incoming payment and have a sales credit memo. Roughly speaking, without the credit memo, the payment would be  bigger.
				// but there can be a payment-discount (skonto) from the payment of the credited invoice, and in the payment we might be back that skonto.
				final Money payAmtInInvoiceCurrency = invoicePayAmtToAllocate.max(paymentAmountToAllocate.negate());
				final Money payAmtInPaymentCurrency = currencyRate.reverseConvertAmount(payAmtInInvoiceCurrency);

				return InvoiceAndPaymentAmountsToAllocate.builder()
						.invoiceAmountsToAllocateInInvoiceCurrency(getEffectiveInvoiceAmountsToAllocate(invoiceAmountsToAllocate, payAmtInInvoiceCurrency))
						.payAmtInPaymentCurrency(payAmtInPaymentCurrency)
						.currencyRate(currencyRate)
						.build();
			}
			// Invoice(-), Payment(-)
			else
			{
				final Money payAmtInInvoiceCurrency = invoicePayAmtToAllocate.max(paymentAmountToAllocate);
				final Money payAmtInPaymentCurrency = currencyRate.reverseConvertAmount(payAmtInInvoiceCurrency);

				return InvoiceAndPaymentAmountsToAllocate.builder()
						.invoiceAmountsToAllocateInInvoiceCurrency(getEffectiveInvoiceAmountsToAllocate(invoiceAmountsToAllocate, payAmtInInvoiceCurrency))
						.payAmtInPaymentCurrency(payAmtInPaymentCurrency)
						.currencyRate(currencyRate)
						.build();
			}
		}
	}

	/**
	 * Payable discount is granted only when payable is fully paid.
	 */
	@NonNull
	private static AllocationAmounts getEffectiveInvoiceAmountsToAllocate(
			@NonNull final AllocationAmounts invoiceAmountsToAllocate,
			@NonNull final Money payAmtInInvoiceCurrency)
	{
		return invoiceAmountsToAllocate.getPayAmt().isEqualByComparingTo(payAmtInInvoiceCurrency)
				? invoiceAmountsToAllocate
				: invoiceAmountsToAllocate.toBuilder()
				.discountAmt(invoiceAmountsToAllocate.getDiscountAmt().toZero())
				.payAmt(payAmtInInvoiceCurrency)
				.build();
	}

	private CurrencyConversionContext getCurrencyConversionContext(@NonNull final IPaymentDocument payment)
	{
		CurrencyConversionContext conversionContext = moneyService.createConversionContext(
				LocalDateAndOrgId.ofLocalDate(payment.getDate(), payment.getClientAndOrgId().getOrgId()),
				payment.getPaymentCurrencyContext().getCurrencyConversionTypeId(),
				payment.getClientAndOrgId().getClientId());

		final FixedConversionRate fixedConversionRate = payment.getPaymentCurrencyContext().toFixedConversionRateOrNull();
		if (fixedConversionRate != null)
		{
			conversionContext = conversionContext.withFixedConversionRate(fixedConversionRate);
		}

		return conversionContext;
	}

	private void markAsBuilt()
	{
		assertNotBuilt();
		_built = true;
	}

	private void assertNotBuilt()
	{
		Check.assume(!_built, "Not already built");
	}

	private LocalDate getDefaultDateTrx()
	{
		final LocalDate defaultDateTrx = _defaultDateTrx;
		if (defaultDateTrx == null)
		{
			throw new AdempiereException("Default DateTrx was not defined for " + this);
		}
		return defaultDateTrx;
	}

	public PaymentAllocationBuilder defaultDateTrx(final LocalDate defaultDateTrx)
	{
		assertNotBuilt();
		_defaultDateTrx = defaultDateTrx;
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

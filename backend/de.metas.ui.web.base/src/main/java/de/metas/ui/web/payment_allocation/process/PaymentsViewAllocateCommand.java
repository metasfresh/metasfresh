/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.payment_allocation.process;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import de.metas.allocation.api.WriteOffType;
import de.metas.banking.payment.paymentallocation.service.AllocationAmounts;
import de.metas.banking.payment.paymentallocation.service.PayableDocument;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.banking.payment.paymentallocation.service.PaymentDocument;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.i18n.AdMessageKey;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingContext;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeWithPrecalculatedAmountRequest;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.invoice.paymentbonus.PaymentBonusCreditMemoService;
import de.metas.invoice.paymentbonus.PaymentBonusDeduction;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.payment.PaymentAmtMultiplier;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.PaymentRow;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.UserMessagePresentation;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class PaymentsViewAllocateCommand
{
	@VisibleForTesting
	static final AdMessageKey MSG_PAYMENT_BONUS_ABOVE_WHAT_THE_CUSTOMER_PAYS = AdMessageKey.of("de.metas.ui.web.payment_allocation.PaymentBonusAboveOpenAmt");
	@VisibleForTesting
	static final AdMessageKey MSG_PAYMENT_BONUS_NOTHING_TO_DEDUCT = AdMessageKey.of("de.metas.ui.web.payment_allocation.PaymentBonusNothingToDeduct");

	private final MoneyService moneyService;
	private final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;
	private final PaymentBonusCreditMemoService paymentBonusCreditMemoService;

	private final ImmutableList<PaymentRow> paymentRows;
	private final ImmutableList<InvoiceRow> invoiceRows;
	private final PayableRemainingOpenAmtPolicy payableRemainingOpenAmtPolicy;
	private final boolean allowPurchaseSalesInvoiceCompensation;
	private final LocalDate defaultDateTrx;

	@Builder
	private PaymentsViewAllocateCommand(
			@NonNull final MoneyService moneyService,
			@NonNull final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService,
			@NonNull final PaymentBonusCreditMemoService paymentBonusCreditMemoService,
			//
			@NonNull @Singular final ImmutableList<PaymentRow> paymentRows,
			@NonNull @Singular final ImmutableList<InvoiceRow> invoiceRows,
			@Nullable final PayableRemainingOpenAmtPolicy payableRemainingOpenAmtPolicy,
			@NonNull final Boolean allowPurchaseSalesInvoiceCompensation,
			@Nullable final LocalDate defaultDateTrx)
	{
		this.moneyService = moneyService;
		this.invoiceProcessingServiceCompanyService = invoiceProcessingServiceCompanyService;
		this.paymentBonusCreditMemoService = paymentBonusCreditMemoService;

		this.paymentRows = paymentRows;
		this.invoiceRows = invoiceRows;
		this.payableRemainingOpenAmtPolicy = CoalesceUtil.coalesce(payableRemainingOpenAmtPolicy, PayableRemainingOpenAmtPolicy.DO_NOTHING);
		this.allowPurchaseSalesInvoiceCompensation = allowPurchaseSalesInvoiceCompensation;
		this.defaultDateTrx = defaultDateTrx != null ? defaultDateTrx : SystemTime.asLocalDate();
	}

	public Optional<PaymentAllocationResult> dryRun()
	{
		final PaymentAllocationBuilder builder = preparePaymentAllocationBuilder();
		if (builder == null)
		{
			return Optional.empty();
		}

		final PaymentAllocationResult result = builder.dryRun().build();
		return Optional.of(result);
	}

	public PaymentAllocationResult run()
	{
		final PaymentAllocationBuilder builder = preparePaymentAllocationBuilder();
		if (builder == null)
		{
			throw new AdempiereException("Invalid allocation")
					.appendParametersToMessage()
					.setParameter("paymentRows", paymentRows)
					.setParameter("invoiceRows", invoiceRows)
					.setParameter("payableRemainingOpenAmtPolicy", payableRemainingOpenAmtPolicy);
		}
		return builder.build();
	}

	@Nullable
	private PaymentAllocationBuilder preparePaymentAllocationBuilder()
	{
		if (paymentRows.isEmpty() && invoiceRows.isEmpty())
		{
			return null;
		}

		final ImmutableList<PaymentDocument> paymentDocuments = paymentRows.stream()
				.map(this::toPaymentDocument)
				.collect(ImmutableList.toImmutableList());

		final ImmutableList<PayableDocument> invoiceDocuments = invoiceRows.stream()
				.map(row -> toPayableDocument(row, paymentDocuments, moneyService, invoiceProcessingServiceCompanyService))
				.collect(ImmutableList.toImmutableList());

		return PaymentAllocationBuilder.newBuilder()
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				.paymentBonusCreditMemoService(paymentBonusCreditMemoService)
				//
				.defaultDateTrx(defaultDateTrx)
				.paymentDocuments(paymentDocuments)
				.payableDocuments(invoiceDocuments)
				.allowPartialAllocations(true)
				.payableRemainingOpenAmtPolicy(payableRemainingOpenAmtPolicy)
				.allowPurchaseSalesInvoiceCompensation(allowPurchaseSalesInvoiceCompensation);
	}

	@VisibleForTesting
	static PayableDocument toPayableDocument(
			@NonNull final InvoiceRow row,
			@NonNull final List<PaymentDocument> paymentDocuments,
			@NonNull final MoneyService moneyService,
			@NonNull final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService)
	{
		// NOTE: assuming InvoiceRow amounts are already CreditMemo adjusted,
		// BUT they are not Sales/Purchase sign adjusted.
		// So we will have to do this bellow.

		final Money openAmt = moneyService.toMoney(row.getOpenAmt());
		final Money discountAmt = moneyService.toMoney(row.getDiscountAmt());
		final CurrencyId currencyId = openAmt.getCurrencyId();

		@Nullable
		final Amount serviceFeeAmt = row.getServiceFeeAmt();
		@Nullable
		final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation;
		if (serviceFeeAmt != null && !serviceFeeAmt.isZero())
		{
			final InvoiceProcessingContext invoiceProcessingContext = extractInvoiceProcessingContext(row, paymentDocuments, invoiceProcessingServiceCompanyService);

			invoiceProcessingFeeCalculation = invoiceProcessingServiceCompanyService.createFeeCalculationForPayment(
							InvoiceProcessingFeeWithPrecalculatedAmountRequest.builder()
									.orgId(row.getClientAndOrgId().getOrgId())
									.paymentDate(invoiceProcessingContext.getPaymentDate())
									.customerId(row.getBPartnerId())
									.invoiceId(row.getInvoiceId())
									.feeAmountIncludingTax(serviceFeeAmt)
									.serviceCompanyBPartnerId(invoiceProcessingContext.getServiceCompanyId())
									.build())
					.orElseThrow(() -> new AdempiereException("Cannot find Invoice Processing Service Company for the selected Payment"));
		}
		else
		{
			invoiceProcessingFeeCalculation = null;
		}

		final Money invoiceProcessingFee = invoiceProcessingFeeCalculation != null
				? moneyService.toMoney(invoiceProcessingFeeCalculation.getFeeAmountIncludingTax())
				: Money.zero(currencyId);

		// Bank fee: mapped into writeOffAmt with WriteOffType=BF
		@Nullable final Amount bankFeeAmtRaw = row.getBankFeeAmt();
		final Money bankFeeAmt = bankFeeAmtRaw != null && !bankFeeAmtRaw.isZero()
				? moneyService.toMoney(bankFeeAmtRaw)
				: Money.zero(currencyId);
		final WriteOffType writeOffType = bankFeeAmt.signum() != 0 ? WriteOffType.BankFee : WriteOffType.WriteOff;

		// Bonus that the customer deducted when paying: the amount in the row wins over the computed one
		@Nullable final PaymentBonusDeduction paymentBonusDeduction = extractPaymentBonusDeduction(row, moneyService);
		final Money paymentBonus = paymentBonusDeduction != null
				? paymentBonusDeduction.getGrossAmount()
				: Money.zero(currencyId);
		// the customer cannot deduct more than it pays: the open amount minus the discount and the fees
		final Money maxPaymentBonus = openAmt.subtract(discountAmt).subtract(invoiceProcessingFee).subtract(bankFeeAmt);
		if (paymentBonusDeduction != null && paymentBonus.isGreaterThan(maxPaymentBonus))
		{
			throw new PaymentBonusAboveWhatTheCustomerPaysException(MSG_PAYMENT_BONUS_ABOVE_WHAT_THE_CUSTOMER_PAYS, moneyService.toAmount(paymentBonus), moneyService.toAmount(maxPaymentBonus), row.getDocumentNo());
		}

		final Money payAmt = openAmt.subtract(discountAmt).subtract(invoiceProcessingFee).subtract(bankFeeAmt).subtract(paymentBonus);

		final SOTrx soTrx = row.getDocBaseType().getSoTrx();

		return PayableDocument.builder()
				.invoiceId(row.getInvoiceId())
				.bpartnerId(row.getBPartnerId())
				.documentNo(row.getDocumentNo())
				.soTrx(soTrx)
				.creditMemo(row.getDocBaseType().isCreditMemo())
				.openAmt(openAmt.negateIf(soTrx.isPurchase()))
				.amountsToAllocate(AllocationAmounts.builder()
										   .payAmt(payAmt)
										   .discountAmt(discountAmt)
										   .writeOffAmt(bankFeeAmt)
										   .invoiceProcessingFee(invoiceProcessingFee)
										   .paymentBonus(paymentBonus)
										   .build()
										   .convertToRealAmounts(row.getInvoiceAmtMultiplier()))
				.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
				.paymentBonusDeduction(paymentBonusDeduction)
				.date(row.getDateInvoiced())
				.dateAcct(row.getDateAcct())
				.clientAndOrgId(row.getClientAndOrgId())
				.currencyConversionTypeId(row.getCurrencyConversionTypeId())
				.writeOffType(writeOffType)
				.build();
	}

	@Nullable
	private static PaymentBonusDeduction extractPaymentBonusDeduction(@NonNull final InvoiceRow row, @NonNull final MoneyService moneyService)
	{
		@Nullable final Amount paymentBonusAmt = row.getPaymentBonusAmt();
		if (paymentBonusAmt == null || paymentBonusAmt.isZero())
		{
			return null;
		}

		final PaymentBonusDeduction computedDeduction = row.getPaymentBonusDeduction();
		if (computedDeduction == null)
		{
			throw new AdempiereException(MSG_PAYMENT_BONUS_NOTHING_TO_DEDUCT, row.getDocumentNo())
					.markAsUserValidationError()
					.setUserMessagePresentation(UserMessagePresentation.ACKNOWLEDGE_DIALOG) // a message to the user, not a "Server error" toast
					.setParameter("C_Invoice_ID", InvoiceId.toRepoId(row.getInvoiceId()));
		}
		return computedDeduction.withGrossAmount(moneyService.toMoney(paymentBonusAmt));
	}

	private static InvoiceProcessingContext extractInvoiceProcessingContext(
			@NonNull final InvoiceRow row,
			@NonNull final List<PaymentDocument> paymentDocuments,
			@NonNull final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService)
	{
		final ImmutableList<InvoiceProcessingContext> paymentContexts = paymentDocuments.stream()
				.map(PaymentDocument::toInvoiceProcessingContext)
				.collect(ImmutableList.toImmutableList());

		return invoiceProcessingServiceCompanyService.extractInvoiceProcessingContext(
				row.getBPartnerId(),
				paymentContexts,
				() -> new AdempiereException("Invoice with Service Fees: no config found for invoice-C_BPartner_ID=" + BPartnerId.toRepoId(row.getBPartnerId()))
						.appendParametersToMessage()
						.setParameter("C_Invoice_ID", InvoiceId.toRepoId(row.getInvoiceId()))
						.setParameter("C_Invoice.DocumentNo", row.getDocumentNo()));
	}

	private PaymentDocument toPaymentDocument(@NonNull final PaymentRow row)
	{
		return toPaymentDocument(row, moneyService);
	}

	@VisibleForTesting
	static PaymentDocument toPaymentDocument(
			@NonNull final PaymentRow row,
			@NonNull final MoneyService moneyService)
	{
		final PaymentAmtMultiplier amtMultiplier = row.getPaymentAmtMultiplier();
		final Money openAmt = amtMultiplier.convertToRealValue(row.getOpenAmt())
				.toMoney(moneyService::getCurrencyIdByCurrencyCode);

		return PaymentDocument.builder()
				.paymentId(row.getPaymentId())
				.bpartnerId(row.getBPartnerId())
				.documentNo(row.getDocumentNo())
				.paymentDirection(row.getPaymentDirection())
				.openAmt(openAmt)
				.amountToAllocate(openAmt)
				.dateTrx(row.getDateTrx())
				.dateAcct(row.getDateAcct())
				.clientAndOrgId(row.getClientAndOrgId())
				.paymentCurrencyContext(row.getPaymentCurrencyContext())
				.build();
	}

}

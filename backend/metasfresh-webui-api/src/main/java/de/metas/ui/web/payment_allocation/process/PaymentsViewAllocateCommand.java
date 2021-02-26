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
import com.google.common.collect.ImmutableSet;
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
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeWithPrecalculatedAmountRequest;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfig;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.payment.PaymentDirection;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.PaymentRow;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public class PaymentsViewAllocateCommand
{
	private final MoneyService moneyService;
	private final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;

	private final PaymentRow paymentRow;
	private final List<InvoiceRow> invoiceRows;
	private final PayableRemainingOpenAmtPolicy payableRemainingOpenAmtPolicy;
	private final boolean allowPurchaseSalesInvoiceCompensation;
	private final LocalDate dateTrx;

	@Builder
	private PaymentsViewAllocateCommand(
			@NonNull final MoneyService moneyService,
			@NonNull final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService,
			//
			@Nullable final PaymentRow paymentRow,
			@NonNull @Singular final ImmutableList<InvoiceRow> invoiceRows,
			@Nullable final PayableRemainingOpenAmtPolicy payableRemainingOpenAmtPolicy,
			@NonNull final Boolean allowPurchaseSalesInvoiceCompensation,
			@Nullable final LocalDate dateTrx)
	{
		this.moneyService = moneyService;
		this.invoiceProcessingServiceCompanyService = invoiceProcessingServiceCompanyService;

		this.paymentRow = paymentRow;
		this.invoiceRows = invoiceRows;
		this.payableRemainingOpenAmtPolicy = CoalesceUtil.coalesce(payableRemainingOpenAmtPolicy, PayableRemainingOpenAmtPolicy.DO_NOTHING);
		this.allowPurchaseSalesInvoiceCompensation = allowPurchaseSalesInvoiceCompensation;
		this.dateTrx = dateTrx != null ? dateTrx : SystemTime.asLocalDate();
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
					.setParameter("paymentRow", paymentRow)
					.setParameter("invoiceRows", invoiceRows)
					.setParameter("payableRemainingOpenAmtPolicy", payableRemainingOpenAmtPolicy);
		}
		return builder.build();
	}

	@Nullable
	private PaymentAllocationBuilder preparePaymentAllocationBuilder()
	{
		if (paymentRow == null && invoiceRows.isEmpty())
		{
			return null;
		}

		final List<PaymentDocument> paymentDocuments = paymentRow != null
				? ImmutableList.of(toPaymentDocument(paymentRow))
				: ImmutableList.of();

		final ImmutableList<PayableDocument> invoiceDocuments = invoiceRows.stream()
				.map(row -> toPayableDocument(row, paymentDocuments, moneyService, invoiceProcessingServiceCompanyService))
				.collect(ImmutableList.toImmutableList());

		return PaymentAllocationBuilder.newBuilder()
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				//
				.dateTrx(dateTrx)
				.dateAcct(dateTrx)
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

		@Nullable final Amount serviceFeeAmt = row.getServiceFeeAmt();
		@Nullable final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation;
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

		final Money payAmt = openAmt.subtract(discountAmt).subtract(invoiceProcessingFee);

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
						.invoiceProcessingFee(invoiceProcessingFee)
						.build()
						.negateIf(soTrx.isPurchase()))
				.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
				.date(row.getDateInvoiced())
				.clientAndOrgId(row.getClientAndOrgId())
				.currencyConversionTypeId(row.getCurrencyConversionTypeId())
				.build();
	}

	@Value
	@Builder
	private static class InvoiceProcessingContext
	{
		@NonNull
		BPartnerId serviceCompanyId;
		@NonNull
		ZonedDateTime paymentDate;
	}

	private static InvoiceProcessingContext extractInvoiceProcessingContext(
			@NonNull final InvoiceRow row,
			@NonNull final List<PaymentDocument> paymentDocuments,
			@NonNull final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService)
	{
		if (paymentDocuments.isEmpty())
		{
			final @NonNull ZonedDateTime evaluationDate = SystemTime.asZonedDateTime();
			final InvoiceProcessingServiceCompanyConfig config = invoiceProcessingServiceCompanyService.getByCustomerId(row.getBPartnerId(), evaluationDate)
					.orElseThrow(() -> new AdempiereException("Invoice with Service Fees: no config found for invoice " + row.getDocumentNo()));

			return InvoiceProcessingContext.builder()
					.serviceCompanyId(config.getServiceCompanyBPartnerId())
					.paymentDate(evaluationDate)
					.build();
		}
		else
		{
			final ImmutableSet<InvoiceProcessingContext> contexts = paymentDocuments.stream()
					.map(PaymentsViewAllocateCommand::extractInvoiceProcessingContext)
					.collect(ImmutableSet.toImmutableSet());
			if (contexts.size() != 1)
			{
				throw new AdempiereException("Invoice with Service Fees: Please select exactly 1 Payment at a time for Allocation.");
			}
			return contexts.iterator().next();
		}
	}

	private static InvoiceProcessingContext extractInvoiceProcessingContext(@NonNull final PaymentDocument paymentDocument)
	{
		return InvoiceProcessingContext.builder()
				.serviceCompanyId(paymentDocument.getBpartnerId())
				.paymentDate(TimeUtil.asZonedDateTime(paymentDocument.getDateTrx()))
				.build();
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
		final PaymentDirection paymentDirection = row.getPaymentDirection();
		final Money openAmt = moneyService.toMoney(row.getOpenAmt())
				.negateIf(paymentDirection.isOutboundPayment());

		return PaymentDocument.builder()
				.paymentId(row.getPaymentId())
				.bpartnerId(row.getBPartnerId())
				.documentNo(row.getDocumentNo())
				.paymentDirection(paymentDirection)
				.openAmt(openAmt)
				.amountToAllocate(openAmt)
				.dateTrx(row.getDateTrx())
				.clientAndOrgId(row.getClientAndOrgId())
				.currencyConversionTypeId(row.getCurrencyConversionTypeId())
				.build();
	}

}

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
import de.metas.banking.payment.paymentallocation.service.AllocationAmounts;
import de.metas.banking.payment.paymentallocation.service.PayableDocument;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationResult;
import de.metas.banking.payment.paymentallocation.service.PaymentDocument;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeWithPrecalculatedAmountRequest;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.lang.SOTrx;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.util.lang.CoalesceUtil;
import de.metas.util.time.SystemTime;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
		final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation;

		final CurrencyCode currencyCode = moneyService.getCurrencyCodeByCurrencyId(currencyId);
		if (row.getServiceFeeAmt() != null && !Amount.zero(currencyCode).equals(row.getServiceFeeAmt()))
		{
			if (paymentDocuments.size() != 1)
			{
				throw new AdempiereException("Invoice with Service Fees: Please select exactly 1 Payment at a time for Allocation.");
			}

			final PaymentDocument singlePaymentDocument = paymentDocuments.get(0);
			final BPartnerId serviceCompanyBPartnerId = singlePaymentDocument.getBpartnerId();
			final ZonedDateTime paymentDate = TimeUtil.asZonedDateTime(singlePaymentDocument.getDateTrx());

			final Optional<InvoiceProcessingFeeCalculation> calculatedFeeOptional = invoiceProcessingServiceCompanyService.createFeeCalculationForPayment(
					InvoiceProcessingFeeWithPrecalculatedAmountRequest.builder()
							.orgId(row.getOrgId())
							.paymentDate(paymentDate)
							.customerId(row.getBPartnerId())
							.invoiceId(row.getInvoiceId())
							.feeAmountIncludingTax(row.getServiceFeeAmt())
							.serviceCompanyBPartnerId(serviceCompanyBPartnerId)
							.build());

			invoiceProcessingFeeCalculation = calculatedFeeOptional.orElseThrow(() -> new AdempiereException("Cannot find Invoice Processing Service Company for the selected Payment"));
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
				.orgId(row.getOrgId())
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
		final Money openAmt = moneyService.toMoney(row.getOpenAmt());

		return PaymentDocument.builder()
				.orgId(row.getOrgId())
				.paymentId(row.getPaymentId())
				.bpartnerId(row.getBPartnerId())
				.documentNo(row.getDocumentNo())
				.paymentDirection(row.getPaymentDirection())
				.openAmt(openAmt)
				.amountToAllocate(openAmt)
				.dateTrx(row.getDateTrx())
				.build();
	}

}

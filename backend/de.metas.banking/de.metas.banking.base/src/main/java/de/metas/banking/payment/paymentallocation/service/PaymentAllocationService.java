/*
 * #%L
 * de.metas.banking.base
 * %%
 * Copyright (C) 2021 metas GmbH
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
import de.metas.banking.payment.paymentallocation.IPaymentAllocationBL;
import de.metas.banking.payment.paymentallocation.PaymentAllocationCriteria;
import de.metas.banking.payment.paymentallocation.PaymentAllocationPayableItem;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.Amount;
import de.metas.invoice.InvoiceAmtMultiplier;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeCalculation;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingFeeWithPrecalculatedAmountRequest;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyConfig;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.PaymentCurrencyContext;
import de.metas.payment.PaymentDirection;
import de.metas.payment.PaymentId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Payment;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class PaymentAllocationService
{
	private final MoneyService moneyService;
	private final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService;
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	public PaymentAllocationService(final MoneyService moneyService, final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService)
	{
		this.moneyService = moneyService;
		this.invoiceProcessingServiceCompanyService = invoiceProcessingServiceCompanyService;
	}

	private final IPaymentAllocationBL paymentAllocationBL = Services.get(IPaymentAllocationBL.class);

	@VisibleForTesting
	public PaymentAllocationResult allocatePayment(@NonNull final PaymentAllocationCriteria paymentAllocationCriteria)
	{
		final PaymentAllocationBuilder builder = preparePaymentAllocationBuilder(paymentAllocationCriteria)
				.orElseThrow(() -> new AdempiereException("Invalid allocation")
						.appendParametersToMessage()
						.setParameter("paymentAllocationCriteria", paymentAllocationCriteria));

		return builder.build();
	}

	@NonNull
	private Optional<PaymentAllocationBuilder> preparePaymentAllocationBuilder(@NonNull final PaymentAllocationCriteria paymentAllocationCriteria)
	{
		final boolean paymentAllocationItemsMissing = CollectionUtils.isEmpty(paymentAllocationCriteria.getPaymentAllocationPayableItems());

		if (paymentAllocationItemsMissing)
		{
			return Optional.empty();
		}

		final I_C_Payment payment = paymentAllocationCriteria.getPayment();

		final PaymentDocument paymentDocument = toPaymentDocument(payment);
		final ZonedDateTime paymentDate = TimeUtil.asZonedDateTime(paymentDocument.getDateTrx(), ZoneId.systemDefault());

		final ImmutableList<PayableDocument> invoiceDocuments = paymentAllocationCriteria.getPaymentAllocationPayableItems()
				.stream()
				.map(paymentAllocationPayableItem -> toPayableDocument(paymentAllocationPayableItem, paymentDate))
				.collect(ImmutableList.toImmutableList());

		final LocalDate dateTrx = TimeUtil.asLocalDate(paymentAllocationCriteria.getDateTrx());
		final PaymentAllocationBuilder builder = PaymentAllocationBuilder.newBuilder()
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				.defaultDateTrx(dateTrx)
				.paymentDocuments(ImmutableList.of(paymentDocument))
				.payableDocuments(invoiceDocuments)
				.allowPartialAllocations(paymentAllocationCriteria.isAllowPartialAllocations())
				.payableRemainingOpenAmtPolicy(PaymentAllocationBuilder.PayableRemainingOpenAmtPolicy.DO_NOTHING)
				.allowPurchaseSalesInvoiceCompensation(paymentAllocationBL.isPurchaseSalesInvoiceCompensationAllowed());

		return Optional.of(builder);
	}

	@VisibleForTesting
	PaymentDocument toPaymentDocument(@NonNull final I_C_Payment payment)
	{
		final PaymentDirection paymentDirection = extractPaymentDirection(payment);

		final Money openAmt = extractPayAmt(payment).negateIf(paymentDirection.isOutboundPayment());
		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoIdOrAny(payment.getAD_Org_ID()));

		return PaymentDocument.builder()
				.paymentId(PaymentId.ofRepoId(payment.getC_Payment_ID()))
				.bpartnerId(BPartnerId.ofRepoId(payment.getC_BPartner_ID()))
				.documentNo(payment.getDocumentNo())
				.paymentDirection(paymentDirection)
				.openAmt(openAmt)
				.amountToAllocate(openAmt)
				.dateTrx(TimeUtil.asLocalDate(payment.getDateTrx(), timeZone))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(payment.getAD_Client_ID(), payment.getAD_Org_ID()))
				.paymentCurrencyContext(PaymentCurrencyContext.ofPaymentRecord(payment))
				.build();
	}

	private PaymentDirection extractPaymentDirection(final I_C_Payment payment)
	{
		return PaymentDirection.ofReceiptFlag(payment.isReceipt());
	}

	private Money extractPayAmt(@NonNull final I_C_Payment payment)
	{
		final CurrencyId currencyId = CurrencyId.ofRepoId(payment.getC_Currency_ID());
		return Money.of(payment.getPayAmt(), currencyId);
	}

	private PayableDocument toPayableDocument(
			final PaymentAllocationPayableItem paymentAllocationPayableItem,
			final ZonedDateTime paymentDate)
	{
		final Money openAmt = moneyService.toMoney(paymentAllocationPayableItem.getOpenAmt());
		final Money payAmt = moneyService.toMoney(paymentAllocationPayableItem.getPayAmt());
		final Money discountAmt = moneyService.toMoney(paymentAllocationPayableItem.getDiscountAmt());
		final CurrencyId currencyId = payAmt.getCurrencyId();

		final Amount serviceFeeAmt = paymentAllocationPayableItem.getServiceFeeAmt();

		final InvoiceProcessingFeeCalculation invoiceProcessingFeeCalculation;

		if (serviceFeeAmt != null && !serviceFeeAmt.isZero())
		{
			final @NonNull ZonedDateTime evaluationDate = SystemTime.asZonedDateTime();

			final InvoiceProcessingServiceCompanyConfig config = invoiceProcessingServiceCompanyService.getByCustomerId(paymentAllocationPayableItem.getInvoiceBPartnerId(), evaluationDate)
					.orElseThrow(() -> new AdempiereException("Invoice with Service Fees: no config found for invoice-C_BPartner_ID=" + BPartnerId.toRepoId(paymentAllocationPayableItem.getInvoiceBPartnerId()))
							.appendParametersToMessage()
							.setParameter("C_Invoice_ID", InvoiceId.toRepoId(paymentAllocationPayableItem.getInvoiceId()))
							.setParameter("C_Invoice.DocumentNo", paymentAllocationPayableItem.getDocumentNo())
					);

			invoiceProcessingFeeCalculation = invoiceProcessingServiceCompanyService.createFeeCalculationForPayment(
					InvoiceProcessingFeeWithPrecalculatedAmountRequest.builder()
							.orgId(paymentAllocationPayableItem.getClientAndOrgId().getOrgId())
							.paymentDate(paymentDate)
							.customerId(paymentAllocationPayableItem.getBPartnerId())
							.invoiceId(paymentAllocationPayableItem.getInvoiceId())
							.feeAmountIncludingTax(serviceFeeAmt)
							.serviceCompanyBPartnerId(config.getServiceCompanyBPartnerId())
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

		final InvoiceAmtMultiplier amtMultiplier = paymentAllocationPayableItem.getAmtMultiplier();

		// for purchase invoices and sales credit memos, we need to negate
		// but not for sales invoices and purchase credit memos
		final boolean invoiceIsCreditMemo = paymentAllocationPayableItem.isInvoiceIsCreditMemo();
		final boolean negateAmounts = paymentAllocationPayableItem.getSoTrx().isPurchase() ^ invoiceIsCreditMemo;
		
		return PayableDocument.builder()
				.invoiceId(paymentAllocationPayableItem.getInvoiceId())
				.bpartnerId(paymentAllocationPayableItem.getBPartnerId())
				.documentNo(paymentAllocationPayableItem.getDocumentNo())
				.soTrx(paymentAllocationPayableItem.getSoTrx())
				.openAmt(amtMultiplier.convertToRealValue(openAmt))
				.amountsToAllocate(AllocationAmounts.builder()
										   .payAmt(payAmt)
										   .discountAmt(discountAmt)
										   .invoiceProcessingFee(invoiceProcessingFee)
										   .build()
										   .convertToRealAmounts(amtMultiplier))
				.invoiceProcessingFeeCalculation(invoiceProcessingFeeCalculation)
				.date(paymentAllocationPayableItem.getDateInvoiced())
				.clientAndOrgId(paymentAllocationPayableItem.getClientAndOrgId())
				//.creditMemo(paymentAllocationPayableItem.isInvoiceIsCreditMemo()) // we don't want the credit memo to be wrapped as IPaymentDocument
				.allowAllocateAgainstDifferentSignumPayment(negateAmounts) // we want the invoice with negative amount to be allocated against the payment with positive amount. the credit-memo and the payment need to be added up in a way 
				.build();
	}

}

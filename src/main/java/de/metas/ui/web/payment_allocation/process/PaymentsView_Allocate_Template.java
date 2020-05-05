package de.metas.ui.web.payment_allocation.process;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.compiere.SpringContextHolder;

import de.metas.banking.payment.paymentallocation.IPaymentAllocationBL;
import de.metas.invoice.invoiceProcessingServiceCompany.InvoiceProcessingServiceCompanyService;
import de.metas.money.MoneyService;
import de.metas.ui.web.payment_allocation.process.PaymentsViewAllocateCommand.PaymentsViewAllocateCommandBuilder;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

abstract class PaymentsView_Allocate_Template extends PaymentsViewBasedProcess
{
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);
	private final InvoiceProcessingServiceCompanyService invoiceProcessingServiceCompanyService = SpringContextHolder.instance.getBean(InvoiceProcessingServiceCompanyService.class);
	private final IPaymentAllocationBL paymentAllocationBL = Services.get(IPaymentAllocationBL.class);

	@Override
	protected final String doIt()
	{
		preparePaymentAllocationBuilder()
				.build();

		// NOTE: the payment and invoice rows will be automatically invalidated (via a cache reset),
		// when the payment allocation is processed

		return MSG_OK;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		// FIXME: until https://github.com/metasfresh/me03/issues/3388 is fixed,
		// as a workaround we have to invalidate the whole views
		invalidatePaymentsAndInvoicesViews();
	}

	@OverridingMethodsMustInvokeSuper
	protected PaymentAllocationBuilder preparePaymentAllocationBuilder()
	{
		final PaymentsViewAllocateCommandBuilder builder = PaymentsViewAllocateCommand.builder()
				.moneyService(moneyService)
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				//
				.paymentRow(getSingleSelectedPaymentRowOrNull())
				.invoiceRows(getSelectedInvoiceRows())
				.allowPurchaseSalesInvoiceCompensation(paymentAllocationBL.isPurchaseSalesInvoiceCompensationAllowed());

		final List<PaymentDocument> paymentDocuments = paymentRow != null
				? ImmutableList.of(toPaymentDocument(paymentRow))
				: ImmutableList.of();

		final List<InvoiceRow> invoiceRows = getSelectedInvoiceRows();
		final ImmutableList<PayableDocument> invoiceDocuments = invoiceRows.stream()
				.map(this::toPayableDocument)
				.collect(ImmutableList.toImmutableList());

		final LocalDate dateTrx = SystemTime.asLocalDate();
		final Money paymentOpenAmt = moneyService.toMoney(paymentRow.getOpenAmt());

		return PaymentAllocationBuilder.newBuilder()
				.orgId(paymentRow.getClientAndOrgId().getOrgId())
				.currencyId(paymentOpenAmt.getCurrencyId())
				.dateTrx(dateTrx)
				.dateAcct(dateTrx)
				.paymentDocuments(paymentDocuments)
				.payableDocuments(invoiceDocuments)
				.allowPartialAllocations(true);
	}

	private PayableDocument toPayableDocument(final InvoiceRow row)
	{
		final Money openAmt = moneyService.toMoney(row.getOpenAmt());
		final Money discountAmt = moneyService.toMoney(row.getDiscountAmt());

		return PayableDocument.builder()
				.invoiceId(row.getInvoiceId())
				.bpartnerId(row.getBPartnerId())
				.documentNo(row.getDocumentNo())
				.isSOTrx(row.getSoTrx().toBoolean())
				.creditMemo(row.isCreditMemo())
				.openAmt(openAmt)
				.amountsToAllocate(AllocationAmounts.builder()
						.payAmt(openAmt)
						.discountAmt(discountAmt)
						.build())
				.build();
	}

	private PaymentDocument toPaymentDocument(final PaymentRow row)
	{
		final Money openAmt = moneyService.toMoney(row.getOpenAmt());

		return PaymentDocument.builder()
				.paymentId(row.getPaymentId())
				.bpartnerId(row.getBPartnerId())
				.documentNo(row.getDocumentNo())
				.isSOTrx(row.isInboundPayment())
				.openAmt(openAmt)
				.amountToAllocate(openAmt)
				.build();
	}
}

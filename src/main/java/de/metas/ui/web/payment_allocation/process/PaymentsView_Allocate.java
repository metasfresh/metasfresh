package de.metas.ui.web.payment_allocation.process;

import java.time.LocalDate;
import java.util.List;

import org.compiere.SpringContextHolder;

import com.google.common.collect.ImmutableList;

import de.metas.banking.payment.paymentallocation.service.AllocationAmounts;
import de.metas.banking.payment.paymentallocation.service.PayableDocument;
import de.metas.banking.payment.paymentallocation.service.PaymentAllocationBuilder;
import de.metas.banking.payment.paymentallocation.service.PaymentDocument;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.ui.web.payment_allocation.InvoiceRow;
import de.metas.ui.web.payment_allocation.PaymentRow;
import de.metas.util.time.SystemTime;

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

public class PaymentsView_Allocate extends PaymentsViewBasedProcess
{
	private final MoneyService moneyService = SpringContextHolder.instance.getBean(MoneyService.class);

	@Override
	protected String doIt()
	{
		final PaymentRow paymentRow = getSingleSelectedPaymentRow();
		final List<InvoiceRow> invoiceRows = getSelectedInvoiceRows();

		final LocalDate dateTrx = SystemTime.asLocalDate();
		final Money paymentOpenAmt = moneyService.toMoney(paymentRow.getAmount());

		PaymentAllocationBuilder.newBuilder()
				.orgId(paymentRow.getOrgId())
				.currencyId(paymentOpenAmt.getCurrencyId())
				.dateTrx(dateTrx)
				.dateAcct(dateTrx)
				.paymentDocument(toPaymentDocument(paymentRow))
				.payableDocuments(invoiceRows.stream()
						.map(this::toPayableDocument)
						.collect(ImmutableList.toImmutableList()))
				.build();

		// TODO: invalidate views and kick out what was fully allocated
		
		return MSG_OK;
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
		final Money openAmt = moneyService.toMoney(row.getAmount());

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

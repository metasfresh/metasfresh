package de.metas.ui.web.payment_allocation.process;

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
		newPaymentsViewAllocateCommand()
				.run();

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

	protected final PaymentsViewAllocateCommand newPaymentsViewAllocateCommand()
	{
		final PaymentsViewAllocateCommandBuilder builder = PaymentsViewAllocateCommand.builder()
				.moneyService(moneyService)
				.invoiceProcessingServiceCompanyService(invoiceProcessingServiceCompanyService)
				//
				.paymentRow(getSingleSelectedPaymentRowOrNull())
				.invoiceRows(getSelectedInvoiceRows())
				.allowPurchaseSalesInvoiceCompensation(paymentAllocationBL.isPurchaseSalesInvoiceCompensationAllowed());

		customizePaymentsViewAllocateCommandBuilder(builder);

		return builder.build();
	}

	protected void customizePaymentsViewAllocateCommandBuilder(@NonNull final PaymentsViewAllocateCommandBuilder builder)
	{
		// nothing on this level
	}
}

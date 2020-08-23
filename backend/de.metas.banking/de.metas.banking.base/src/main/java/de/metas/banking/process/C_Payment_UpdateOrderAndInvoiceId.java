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

package de.metas.banking.process;

import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.payment.api.IPaymentBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.compiere.model.I_C_Payment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class C_Payment_UpdateOrderAndInvoiceId extends JavaProcess implements IProcessPrecondition
{

	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IOrderBL orderBL = Services.get(IOrderBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);
	private final ProcessInfo.ProcessInfoBuilder processInfoBuilder = ProcessInfo.builder();

	private final String PAYPAL_INTERNAL_NAME = "SOURCE.webshop.paypal.prepaid";
	private final String CREDITCARD_INTERNAL_NAME = "SOURCE.webshop.other.paymenttypes";

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No Selection");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		List<I_C_Payment> payments = getSelectedPayments();
		final InputDataSourceId paypalDataSourceId = inputDataSourceDAO.retrieveInputDataSourceIdByInternalNameOrNull(PAYPAL_INTERNAL_NAME);
		final InputDataSourceId creditCardDataSourceId = inputDataSourceDAO.retrieveInputDataSourceIdByInternalNameOrNull(CREDITCARD_INTERNAL_NAME);

		List<I_C_Payment> filteredPayments = payments
				.stream()
				.filter(p -> !paymentBL.getPaymentRuleSymbol(p, paypalDataSourceId.getRepoId(), creditCardDataSourceId.getRepoId()).equals("L")
						&& !paymentBL.getPaymentRuleSymbol(p, paypalDataSourceId.getRepoId(), creditCardDataSourceId.getRepoId()).equals("K")
						&& !p.isAllocated())
				.collect(Collectors.toList());

		paymentBL.setPaypalOrCreditCardPaymentRules(filteredPayments, paypalDataSourceId.getRepoId(), creditCardDataSourceId.getRepoId());
		final List<ExternalId> externalIds = paymentBL.getExternalIdsList(filteredPayments);
		final Map<ExternalId, OrderId> orderIdsForExternalIds = orderDAO.getOrderIdsForExternalIds(externalIds);
		paymentBL.setPaymentOrderIds(filteredPayments, orderIdsForExternalIds);
		final List<OrderId> orderIds = paymentBL.getOrderIdsList(filteredPayments);
		final Map<OrderId, InvoiceId> orderIdInvoiceIdMap = invoiceDAO.getInvoiceIdsForOrderIds(orderIds);
		paymentBL.setPaymentInvoiceIds(filteredPayments, orderIdInvoiceIdMap);

		return MSG_OK;
	}

	private List<I_C_Payment> getSelectedPayments()
	{
		return retrieveSelectedRecordsQueryBuilder(I_C_Payment.class)
				.create()
				.list();
	}

}

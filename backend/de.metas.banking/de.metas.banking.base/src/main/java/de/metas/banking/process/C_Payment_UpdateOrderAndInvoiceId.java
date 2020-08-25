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

import com.google.common.collect.ImmutableSet;
import de.metas.impex.InputDataSourceId;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderBL;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.payment.PaymentRule;
import de.metas.payment.api.IPaymentBL;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;
import de.metas.util.lang.ExternalId;
import lombok.NonNull;
import org.apache.commons.lang3.EnumUtils;
import org.compiere.model.I_C_Payment;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static de.metas.payment.PaymentRule.CreditCard;
import static de.metas.payment.PaymentRule.PayPal;

public class C_Payment_UpdateOrderAndInvoiceId extends JavaProcess implements IProcessPrecondition
{

	private final IPaymentBL paymentBL = Services.get(IPaymentBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

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

		List<I_C_Payment> filteredPayments = payments
				.stream()
				.filter(p -> !p.isAllocated())
				.collect(Collectors.toList());

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

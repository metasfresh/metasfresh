/*
 * #%L
 * de.metas.business
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

package de.metas.payment.interceptor;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationBL;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Payment;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_Payment.class)
@Component
public class C_Payment
{
	public static final C_Payment INSTANCE = new C_Payment();

	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IPaymentDAO paymentDAO = Services.get(IPaymentDAO.class);
	private final IAllocationBL allocationBL = Services.get(IAllocationBL.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	boolean canAllocateOrderPaymentToInvoice(final I_C_Order order)
	{
		if (order == null)
		{
			return false;
		}
		if (order.getC_Payment_ID() <= 0)
		{
			return false;
		}

		final boolean isPrepayOrder = docTypeBL.isPrepay(DocTypeId.ofRepoId(order.getC_DocType_ID()));
		if (isPrepayOrder)
		{
			return true;
		}

		final I_C_Payment payment = paymentDAO.getById(PaymentId.ofRepoId(order.getC_Payment_ID()));
		return payment.getC_Order_ID() == order.getC_Order_ID();
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_Payment.COLUMNNAME_C_Invoice_ID })
	public void allocateInvoiceAgainstPaymentIfNecessary(final I_C_Payment payment)
	{
		final I_C_Invoice invoice;
		final I_C_Order order;
		if (payment.getC_Order_ID() > 0)
		{
			order = orderDAO.getById(OrderId.ofRepoId(payment.getC_Order_ID()));
			invoice = invoiceDAO.getByOrderId(OrderId.ofRepoId(payment.getC_Order_ID()));

			if (canAllocateOrderPaymentToInvoice(order))
			{
				if (invoice != null)
				{
					allocationBL.autoAllocateSpecificPayment(invoice, payment, true);
				}
			}
		}
	}
}

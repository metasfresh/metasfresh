package de.metas.inoutcandidate.modelvalidator;

import com.google.common.collect.ImmutableList;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;
import de.metas.inoutcandidate.api.IShipmentConstraintsBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.interfaces.I_C_OrderLine;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderId;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;

import java.util.List;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_C_Order.class)
public class C_Order
{
	private final IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final IShipmentSchedulePA shipmentSchedulePA = Services.get(IShipmentSchedulePA.class);
	private final  IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);
	private final IInvoiceDAO invoiceDAO = Services.get(IInvoiceDAO.class);

	private static final AdMessageKey MSG_CannotCompleteOrder_DeliveryStop = AdMessageKey.of("CannotCompleteOrder_DeliveryStop");
	private static final AdMessageKey MSG_REACTIVATION_VOID_NOT_ALLOWED = AdMessageKey.of("salesorder.shipmentschedule.exported");
	private static final AdMessageKey MSG_PO_REACTIVATION_VOID_NOT_ALLOWED = AdMessageKey.of("purchaseorder.shipmentschedule.exported");
	private static final AdMessageKey MSG_REACTIVATION_NOT_ALLOWED = AdMessageKey.of("order.invoice.completed");

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_PREPARE)
	public void assertNotDeliveryStopped(final I_C_Order order)
	{
		// Makes sense only for sales orders
		if (!order.isSOTrx())
		{
			return;
		}

		final int billPartnerId = order.getBill_BPartner_ID();
		final int deliveryStopShipmentConstraintId = Services.get(IShipmentConstraintsBL.class).getDeliveryStopShipmentConstraintId(billPartnerId);
		final boolean isDeliveryStop = deliveryStopShipmentConstraintId > 0;
		if (isDeliveryStop)
		{
			throw new AdempiereException(MSG_CannotCompleteOrder_DeliveryStop)
					.markAsUserValidationError();
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_CLOSE })
	public void assertReActivationAllowed(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return; // we can spare us the effort
		}
		if (shipmentSchedulePA.existsExportedShipmentScheduleForOrder(OrderId.ofRepoId(order.getC_Order_ID())))
		{
			throw new AdempiereException(MSG_REACTIVATION_VOID_NOT_ALLOWED)
					.markAsUserValidationError();
		}

		OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		if (canOrderBeReactivated(orderId))
		{
			//delete Invoice candidates for the order
			invoiceCandDAO.deleteAllICByOrderId(orderId);
		}
		else
		{
			throw new AdempiereException(MSG_REACTIVATION_NOT_ALLOWED)
					.markAsUserValidationError();
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_BEFORE_CLOSE })
	public void assertPOReActivationAllowed(final I_C_Order order)
	{
		if (order.isSOTrx())
		{
			return; // we can spare us the effort
		}
		if (receiptScheduleDAO.existsExportedReceiptScheduleForOrder(OrderId.ofRepoId(order.getC_Order_ID())))
		{
			throw new AdempiereException(MSG_PO_REACTIVATION_VOID_NOT_ALLOWED)
					.markAsUserValidationError();
		}

		OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());
		if (canOrderBeReactivated(orderId))
		{
			//delete Invoice candidates for the order
			invoiceCandDAO.deleteAllICByOrderId(orderId);
		}
		else
		{
			throw new AdempiereException(MSG_REACTIVATION_NOT_ALLOWED)
					.markAsUserValidationError();
		}
	}

	private boolean canOrderBeReactivated(final OrderId orderId)
	{
		final List<OrderId> orders = ImmutableList.of(orderId);
		final boolean hasCompletedInvoices = invoiceDAO
				.getInvoicesForOrderIds(orders)
				.stream()
				.filter(inv -> DocStatus.Completed.getCode().equals(inv.getDocStatus()))
				.count() > 0;

		return !hasCompletedInvoices;
	}

}

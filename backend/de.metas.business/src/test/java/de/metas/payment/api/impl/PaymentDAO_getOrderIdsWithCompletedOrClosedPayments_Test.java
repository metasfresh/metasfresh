/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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


package de.metas.payment.api.impl;

import de.metas.order.OrderId;
import de.metas.payment.PaymentId;
import de.metas.payment.api.IPaymentDAO;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Payment;
import org.compiere.model.X_C_Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class PaymentDAO_getOrderIdsWithCompletedOrClosedPayments_Test
{
	private IPaymentDAO paymentDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		paymentDAO = Services.get(IPaymentDAO.class);
	}

	private OrderId payment(final int orderRepoId, final String docStatus, final boolean active)
	{
		final I_C_Payment payment = newInstance(I_C_Payment.class);
		payment.setC_Order_ID(orderRepoId);
		payment.setDocStatus(docStatus);
		payment.setIsActive(active);
		saveRecord(payment);
		return OrderId.ofRepoId(orderRepoId);
	}

	/** Only an active completed or closed payment counts (human decision 2026-11-04: "yes, CO and CL!"); orders without such a payment are not returned. */
	@Test
	void onlyActiveCompletedOrClosedPaymentsCount()
	{
		final OrderId drafted = payment(1, X_C_Payment.DOCSTATUS_Drafted, true);
		final OrderId completed = payment(2, X_C_Payment.DOCSTATUS_Completed, true);
		final OrderId voided = payment(3, X_C_Payment.DOCSTATUS_Voided, true);
		final OrderId reversed = payment(4, X_C_Payment.DOCSTATUS_Reversed, true);
		final OrderId inactive = payment(5, X_C_Payment.DOCSTATUS_Completed, false);
		final OrderId withoutPayment = OrderId.ofRepoId(6);
		final OrderId inProgress = payment(7, X_C_Payment.DOCSTATUS_InProgress, true);
		final OrderId invalid = payment(8, X_C_Payment.DOCSTATUS_Invalid, true);
		final OrderId closed = payment(9, X_C_Payment.DOCSTATUS_Closed, true);
		final OrderId approved = payment(10, X_C_Payment.DOCSTATUS_Approved, true);
		final OrderId notApproved = payment(11, X_C_Payment.DOCSTATUS_NotApproved, true);
		final OrderId waitingPayment = payment(12, X_C_Payment.DOCSTATUS_WaitingPayment, true);

		assertThat(paymentDAO.getOrderIdsWithCompletedOrClosedPayments(ImmutableList.of(
				drafted, completed, voided, reversed, inactive, withoutPayment, inProgress, invalid, closed, approved, notApproved, waitingPayment)))
				.containsExactlyInAnyOrder(completed, closed);
		assertThat(paymentDAO.getOrderIdsWithCompletedOrClosedPayments(ImmutableList.of(voided, withoutPayment))).isEmpty();
	}

	/** Only an active completed or closed payment is returned; none if only other payments point to the order. */
	@Test
	void getFirstCompletedOrClosedPaymentIdByOrderId_skipsOtherAndInactivePayments()
	{
		payment(1, X_C_Payment.DOCSTATUS_Approved, true);
		payment(1, X_C_Payment.DOCSTATUS_Drafted, true);
		payment(1, X_C_Payment.DOCSTATUS_Voided, true);
		payment(1, X_C_Payment.DOCSTATUS_Reversed, true);
		payment(1, X_C_Payment.DOCSTATUS_Completed, false);
		assertThat(paymentDAO.getFirstCompletedOrClosedPaymentIdByOrderId(OrderId.ofRepoId(1))).isEmpty();

		final I_C_Payment carrying = newInstance(I_C_Payment.class);
		carrying.setC_Order_ID(1);
		carrying.setDocStatus(X_C_Payment.DOCSTATUS_Completed);
		saveRecord(carrying);

		assertThat(paymentDAO.getFirstCompletedOrClosedPaymentIdByOrderId(OrderId.ofRepoId(1))).contains(PaymentId.ofRepoId(carrying.getC_Payment_ID()));
		assertThat(paymentDAO.getFirstCompletedOrClosedPaymentIdByOrderId(OrderId.ofRepoId(2))).isEmpty();
	}
}

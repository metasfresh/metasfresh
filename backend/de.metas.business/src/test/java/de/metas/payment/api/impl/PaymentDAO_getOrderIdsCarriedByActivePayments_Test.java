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

public class PaymentDAO_getOrderIdsCarriedByActivePayments_Test
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

	/** Only an active payment that is neither voided nor reversed carries its order; orders without such a payment are not returned. */
	@Test
	void onlyActivePaymentsThatAreNotVoidedOrReversedCarryTheirOrder()
	{
		final OrderId drafted = payment(1, X_C_Payment.DOCSTATUS_Drafted, true);
		final OrderId completed = payment(2, X_C_Payment.DOCSTATUS_Completed, true);
		final OrderId voided = payment(3, X_C_Payment.DOCSTATUS_Voided, true);
		final OrderId reversed = payment(4, X_C_Payment.DOCSTATUS_Reversed, true);
		final OrderId inactive = payment(5, X_C_Payment.DOCSTATUS_Completed, false);
		final OrderId withoutPayment = OrderId.ofRepoId(6);

		assertThat(paymentDAO.getOrderIdsCarriedByActivePayments(ImmutableList.of(drafted, completed, voided, reversed, inactive, withoutPayment)))
				.containsExactlyInAnyOrder(drafted, completed);
		assertThat(paymentDAO.getOrderIdsCarriedByActivePayments(ImmutableList.of(voided, withoutPayment))).isEmpty();
	}
}

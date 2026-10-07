/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.order;

import de.metas.order.OrderId;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;

import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Makes the next completion of an armed order fail once with a DB deadlock (SQLSTATE {@code 40P01}) after the order was
 * prepared and completed in that attempt, so that the document engine rolls back and retries the completion, as it does
 * when PostgreSQL picks the completing transaction as a deadlock victim.
 */
@Interceptor(I_C_Order.class)
final class C_Order_SimulatedDeadlockOnCompletion
{
	private static final C_Order_SimulatedDeadlockOnCompletion INSTANCE = new C_Order_SimulatedDeadlockOnCompletion();
	private static boolean registered = false;

	private final Set<OrderId> armedOrderIds = ConcurrentHashMap.newKeySet();

	private C_Order_SimulatedDeadlockOnCompletion() {}

	static synchronized void arm(@NonNull final OrderId orderId)
	{
		if (!registered)
		{
			ModelValidationEngine.get().addModelValidator(INSTANCE);
			registered = true;
		}
		INSTANCE.armedOrderIds.add(orderId);
	}

	/**
	 * @return whether the deadlock is still pending for the given order, i.e. no completion of it has hit the deadlock yet
	 */
	static boolean isArmed(@NonNull final OrderId orderId)
	{
		return INSTANCE.armedOrderIds.contains(orderId);
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void afterComplete(final I_C_Order order)
	{
		if (armedOrderIds.remove(OrderId.ofRepoId(order.getC_Order_ID())))
		{
			throw new AdempiereException(
					"Simulated DB deadlock while completing C_Order_ID=" + order.getC_Order_ID(),
					new SQLException("ERROR: deadlock detected (simulated)", "40P01"));
		}
	}
}

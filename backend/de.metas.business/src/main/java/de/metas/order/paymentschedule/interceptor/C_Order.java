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

package de.metas.order.paymentschedule.interceptor;

import de.metas.i18n.AdMessageKey;
import de.metas.order.OrderId;
import de.metas.order.paymentschedule.core.OrderPaySchedule;
import de.metas.order.paymentschedule.core.OrderPayScheduleStatus;
import de.metas.order.paymentschedule.core.service.OrderPayScheduleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Blocks reactivation of a {@code C_Order} whose {@link OrderPaySchedule} has any line whose
 * {@link OrderPayScheduleStatus} is no longer {@code Pending}
 * (i.e. {@code Awaiting_Pay} or {@code Paid}).
 *
 * <p>Rationale: the standard metasfresh re-activate flow drops {@code C_OrderPaySchedule} rows,
 * but those rows carry meaningful per-receipt state (Status, BaseAmt, {@code C_Invoice_ID} link,
 * allocations); a subsequent re-completion would recreate them from scratch and silently destroy
 * that state.
 *
 * <p>Reactivation is allowed when ALL pay-schedule lines are still {@code Pending} (nothing has
 * happened yet — the standard drop-and-rebuild path is safe), or when the order has no
 * pay-schedule at all.
 */
@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order
{
	private static final AdMessageKey MSG_OrderReactivateBlocked = AdMessageKey.of("Order_Reactivate_Blocked_By_PaySchedule_Activity");

	@NonNull private final OrderPayScheduleService orderPayScheduleService;

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void blockReactivateWhenScheduleNotPending(@NonNull final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		orderPayScheduleService.getByOrderId(orderId)
				.filter(this::hasAnyNonPendingLine)
				.ifPresent(schedule -> {
					throw new AdempiereException(MSG_OrderReactivateBlocked)
							.markAsUserValidationError();
				});
	}

	private boolean hasAnyNonPendingLine(@NonNull final OrderPaySchedule schedule)
	{
		return schedule.streamLines()
				.anyMatch(line -> !line.getStatus().isPending());
	}
}

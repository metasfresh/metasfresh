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
import de.metas.order.paymentschedule.OrderPaySchedule;
import de.metas.order.paymentschedule.service.OrderPayScheduleService;
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
 * {@link de.metas.order.paymentschedule.OrderPayScheduleStatus} is no longer {@code Pending}
 * (i.e. {@code Awaiting_Pay} or {@code Paid}).
 *
 * <p>Rationale (TODO-1 of iter-3 https://github.com/metasfresh/me03/issues/29369): the standard
 * metasfresh re-activate flow drops {@code C_OrderPaySchedule} rows. Iter-3 stores meaningful
 * per-receipt state on those rows (Status, BaseAmt, {@code C_Invoice_ID} link, allocations); a
 * subsequent re-completion would recreate them from scratch and silently destroy that state.
 *
 * <p>Reactivation is allowed when:
 * <ul>
 *   <li>ALL pay-schedule lines are still {@code Pending} — nothing has happened yet (no receipts,
 *       proforma payment cycle not started); the standard drop-and-rebuild path is safe.</li>
 *   <li>The order has no pay-schedule at all — non-iter-2 / non-complex-payment-term order; the
 *       guard is dormant in that case (mirrors the iter-3 AC #22 dormancy contract: any iter-3
 *       component that touches a non-iter-2 order is a bug).</li>
 * </ul>
 *
 * <p>Lives in the iter-3 package {@code de.metas.order.paymentschedule.interceptor} (next to
 * {@link C_Payment_LCStep}) rather than in {@code de.metas.order.model.interceptor.C_Order} —
 * keeps iter-3 concerns scoped to the iter-3 package boundary.
 */
@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order_PayScheduleReactivateGuard
{
	private static final AdMessageKey MSG_OrderReactivateBlocked =
			AdMessageKey.of("Order_Reactivate_Blocked_By_PaySchedule_Activity");

	@NonNull private final OrderPayScheduleService orderPayScheduleService;

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void blockReactivateWhenScheduleNotPending(@NonNull final I_C_Order order)
	{
		// RepoIdAware boundary: wrap the raw int once at the entry point (java-general.md §6).
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		orderPayScheduleService.getByOrderId(orderId)
				.filter(this::hasAnyNonPendingLine)
				.ifPresent(schedule -> {
					throw new AdempiereException(MSG_OrderReactivateBlocked)
							.markAsUserValidationError();
				});
	}

	/**
	 * Returns {@code true} when the schedule has at least one line whose status is not
	 * {@code Pending}. Uses {@link de.metas.order.paymentschedule.OrderPayScheduleStatus#isPending()}
	 * — the same predicate used by {@link OrderPaySchedule#updateStatusFromContext} to gate
	 * status-update side effects, keeping the two paths in lockstep.
	 */
	private boolean hasAnyNonPendingLine(@NonNull final OrderPaySchedule schedule)
	{
		return schedule.getLines().stream()
				.anyMatch(line -> !line.getStatus().isPending());
	}
}

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
import de.metas.order.paymentschedule.core.OrderPayScheduleLine;
import de.metas.order.paymentschedule.core.service.OrderPayScheduleService;
import de.metas.order.paymentschedule.referenced_docs.proforma_invoice.OrderPayScheduleProformaService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_Order;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

/**
 * Blocks reactivation of a {@code C_Order} whose {@link OrderPaySchedule} reflects committed
 * downstream activity — i.e. at least one of:
 * <ul>
 *   <li>any pay-schedule line has a goods-receipt link ({@code inoutId != null}), or</li>
 *   <li>any pay-schedule line has a matched-invoice link ({@code invoiceId != null}), or</li>
 *   <li>a proforma allocation exists for the order (detected via
 *       {@link OrderPayScheduleProformaService#getByOrderId}; the LC/proforma row carries no
 *       per-line link, so it must be detected through the proforma service).</li>
 * </ul>
 *
 * <p>A {@code Paid} line always implies one of the above, so no separate status guard is needed.
 *
 * <p>Reactivation is allowed when no pay-schedule line carries any downstream link AND no proforma
 * allocation exists — meaning nothing has been committed yet and the standard drop-and-rebuild
 * reactivation path is safe. Reactivation is also allowed when the order has no pay-schedule at all.
 */
@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order
{
	private static final AdMessageKey MSG_OrderReactivateBlocked = AdMessageKey.of("Order_Reactivate_Blocked_By_PaySchedule_Activity");

	@NonNull private final OrderPayScheduleService orderPayScheduleService;
	@NonNull private final OrderPayScheduleProformaService orderPayScheduleProformaService;

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void blockReactivateWhenScheduleNotPending(@NonNull final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		// The proforma check inside reflectsDownstreamActivity is reached only when a pay-schedule
		// exists. That is safe because an allocated LC/proforma always creates at least one
		// pay-schedule line (OrderPayScheduleLCStepService), so "a proforma exists but there is no
		// pay-schedule" is not a producible state — an order with no pay-schedule at all has nothing
		// downstream to protect and is always reactivatable.
		orderPayScheduleService.getByOrderId(orderId)
				.filter(schedule -> reflectsDownstreamActivity(orderId, schedule))
				.ifPresent(schedule -> {
					throw new AdempiereException(MSG_OrderReactivateBlocked).markAsUserValidationError();
				});
	}

	private boolean reflectsDownstreamActivity(@NonNull final OrderId orderId, @NonNull final OrderPaySchedule schedule)
	{
		final boolean anyLineLinked = schedule.streamLines()
				.anyMatch(line -> line.getInoutId() != null || line.getInvoiceId() != null);
		return anyLineLinked || orderPayScheduleProformaService.getByOrderId(orderId).isPresent();
	}
}

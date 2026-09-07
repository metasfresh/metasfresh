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
 * Blocks reactivation of a {@code C_Order} whose {@link OrderPaySchedule} reflects committed
 * downstream activity — i.e. at least one pay-schedule line has a goods-receipt link
 * ({@code inoutId != null}) or a matched-invoice link ({@code invoiceId != null}).
 *
 * <p>The line's status is irrelevant to the block. A {@code Paid} line does <b>not</b> imply a
 * downstream link — a proforma prepayment marks its row {@code Paid} with neither ID set — and it cuts
 * both ways: an {@code Awaiting_Pay} line carrying no link does not block, while a still-{@code Pending}
 * line carrying an {@code M_InOut_ID} does. The block depends solely on those two IDs.
 *
 * <p>A proforma allocation alone does <b>not</b> block reactivation: the allocation link and its
 * prepayment both survive reactivation (only the derived {@link OrderPaySchedule} rows are dropped),
 * so re-completion can re-derive the proforma-driven schedule state from them. Only a goods receipt
 * or a matched invoice actually commits state a drop-and-rebuild reactivation would orphan.
 *
 * <p>Reactivation is allowed when no pay-schedule line carries any downstream link — meaning nothing
 * has been committed yet and the standard drop-and-rebuild reactivation path is safe. Reactivation is
 * also allowed when the order has no pay-schedule at all.
 */
@Interceptor(I_C_Order.class)
@Component
@RequiredArgsConstructor
public class C_Order
{
	private static final AdMessageKey MSG_OrderReactivateBlocked = AdMessageKey.of("Order_Reactivate_Blocked_By_PaySchedule_Activity");

	@NonNull private final OrderPayScheduleService orderPayScheduleService;

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_REACTIVATE)
	public void blockReactivationIfScheduleLinkedToDownstreamDocument(@NonNull final I_C_Order order)
	{
		final OrderId orderId = OrderId.ofRepoId(order.getC_Order_ID());

		orderPayScheduleService.getByOrderId(orderId)
				.filter(OrderPaySchedule::hasLineLinkedToDownstreamDocument)
				.ifPresent(schedule -> {
					throw new AdempiereException(MSG_OrderReactivateBlocked).markAsUserValidationError();
				});
	}
}

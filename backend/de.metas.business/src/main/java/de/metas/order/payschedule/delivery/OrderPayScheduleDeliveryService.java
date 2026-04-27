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

package de.metas.order.payschedule.delivery;

import de.metas.order.OrderId;
import de.metas.order.payschedule.delivery.OrderPayScheduleDeliveryRepository.DesiredDeliveryRow;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Authority service for the Delivery step of {@code C_OrderPaySchedule}.
 *
 * <p>This service is the <em>sole writer</em> of all Delivery rows for a given order —
 * symmetric sibling to iter-2's {@code OrderPayScheduleLCService}.
 * {@link #recomputeDeliverySteps(OrderId)} is idempotent and total: it derives the
 * complete desired set of sub-rows from current truth and converges the DB to that set.
 *
 * <p><strong>Stub state</strong>: the business logic will be filled in Tasks 21/22.
 *
 * @see <a href="https://github.com/metasfresh/me03/issues/29369">me03 #29369 Split-Payment Iter 3</a>
 */
@Service
@RequiredArgsConstructor
public class OrderPayScheduleDeliveryService
{
	@NonNull private final OrderPayScheduleDeliveryRepository repository;

	/**
	 * Recomputes the Delivery step for the given order.
	 *
	 * <p>Loads inputs via the repository, calls {@link #computeDesired(DeliveryStepInputs)},
	 * and writes the result back.
	 *
	 * @throws UnsupportedOperationException TODO Task 21c — wire to repository
	 */
	public void recomputeDeliverySteps(@NonNull final OrderId orderId)
	{
		throw new UnsupportedOperationException("TODO Task 21c");
	}

	/**
	 * Pure function: derives the desired set of Delivery sub-rows from the given inputs.
	 *
	 * <p>Rules (REQUIREMENTS.md §3.1 + §3.2 + AC #1, #3, #5, #7, #10, #19):
	 * <ul>
	 *   <li>One sub-row per completed receipt ({@code ReceiptInfo.mInOutId}).
	 *   <li>One remainder row ({@code mInOutId = null}) with
	 *       {@code BaseAmt = max(0, orderGrandTotal − Σ receipt.withTaxValue)}. Omitted if
	 *       {@code BaseAmt ≤ 0} (over-delivery).
	 *   <li>Status derivation per receipt: if no matched invoice (or DR/RE) → Pending;
	 *       if CO/CL with {@code OpenAmt > 0} → Awaiting_Pay; if CO/CL with
	 *       {@code OpenAmt = 0} → Paid.
	 *   <li>Reversed invoice (RE) → C_Invoice_ID cleared on the sub-row, Status = Pending.
	 * </ul>
	 *
	 * <p>{@code DueAmt = round(BaseAmt × deliveryPercent / 100, 2)} (HALF_UP).
	 *
	 * @throws UnsupportedOperationException TODO Task 21a — implement pure function
	 */
	public List<DesiredDeliveryRow> computeDesired(@NonNull final DeliveryStepInputs inputs)
	{
		throw new UnsupportedOperationException("TODO Task 21a");
	}
}

/*
 * #%L
 * de.metas.deliveryplanning.base
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

package de.metas.deliveryplanning;

import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.sql.Timestamp;

/**
 * One planning's share of a delivery instruction: the allocation to create, plus what goes on the
 * {@code M_ShippingPackage} it gets.
 * <p>
 * Everything the instruction holds once - forwarder, its business partner and location, the shipping date - is
 * read off the instruction itself rather than repeated here, so the two can never disagree.
 */
@Value
@Builder
public class DeliveryPlanningAllocCreateRequest
{
	@NonNull DeliveryPlanningId deliveryPlanningId;

	@NonNull ProductId productId;

	@NonNull Quantity qtyLoaded;

	@NonNull Quantity qtyDischarged;

	@Nullable String batchNo;

	@Nullable OrderLineId orderLineId;

	/**
	 * The order behind this planning's allocation, resolved by the caller from {@code M_Delivery_Planning.C_Order_ID}
	 * - never derived here, so it lands on the created {@code M_ShippingPackage} exactly as the caller resolved it,
	 * including {@code null} for a planning that genuinely has no order.
	 */
	@Nullable OrderId orderId;

	boolean toBeFetched;

	/**
	 * The planning's own dates, carried here so the instruction's fill-if-empty defaulting can read them
	 * without a second load: every caller builds this request from an already-loaded planning record.
	 */
	@Nullable Timestamp etd;

	@Nullable Timestamp eta;

	@Nullable String loadingTime;

	@Nullable String deliveryTime;
}

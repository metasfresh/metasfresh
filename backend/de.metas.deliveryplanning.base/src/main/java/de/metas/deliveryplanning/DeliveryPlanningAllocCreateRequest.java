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

import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

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

	boolean toBeFetched;
}

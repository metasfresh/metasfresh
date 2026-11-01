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
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class DeliveryPlanningAllocCreateRequest
{
	@NonNull DeliveryPlanningId deliveryPlanningId;

	@NonNull ShippingPackageData shippingPackage;

	/** Defaults to {@link HeaderDateCandidate#none()} - a request that contributes no dates need not build one. */
	@Builder.Default
	@NonNull HeaderDateCandidate headerDateCandidate = HeaderDateCandidate.none();

	/**
	 * No quantity here: the package's four quantity figures are derived ({@code ColumnSQL}) from the planning
	 * through the allocation, so all that survives of the planning's own quantity is the unit it is expressed in.
	 */
	@Value
	@Builder
	public static class ShippingPackageData
	{
		@NonNull ProductId productId;

		@NonNull UomId uomId;

		@Nullable String batchNo;

		@Nullable OrderLineId orderLineId;

		/** Lands on the created {@code M_ShippingPackage}; {@code null} for a planning that has no order. */
		@Nullable OrderId orderId;

		boolean toBeFetched;
	}

	/**
	 * Offered as a candidate value for the instruction header's fill-if-empty defaulting. A request whose header is
	 * filled some other way contributes {@link #none()}.
	 */
	@Value
	@Builder
	public static class HeaderDateCandidate
	{
		@Nullable Instant etd;

		@Nullable Instant eta;

		@Nullable String loadingTime;

		@Nullable String deliveryTime;

		static HeaderDateCandidate none()
		{
			return HeaderDateCandidate.builder().build();
		}
	}
}

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
import java.sql.Timestamp;

/**
 * One planning's contribution to a delivery instruction. Grouped by who actually owns each field - re-derived
 * against the code as it stands (Task Q14 already stopped copying quantities onto the package; see below):
 * <ul>
 * <li>{@link #deliveryPlanningId} is the only field {@code M_Delivery_Planning_Alloc} itself owns -
 * {@code M_ShippingPackage_ID} and {@code M_ShipperTransportation_ID} are derived inside
 * {@link DeliveryInstructionService}, never supplied here.</li>
 * <li>{@link #shippingPackage} is what {@link DeliveryInstructionRepository#createShippingPackage} needs to build the
 * allocation's {@code M_ShippingPackage}.</li>
 * <li>{@link #headerDateCandidate} is what this planning offers the delivery instruction header's fill-if-empty
 * date defaulting - consumed by {@link DeliveryPlanningService#resolveInstructionDatesForAllocation}, never by
 * {@link DeliveryInstructionService} itself.</li>
 * </ul>
 * Fields the instruction already holds (forwarder, its business partner and location, the shipping date) are read
 * off the instruction, not repeated here.
 */
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
	 * What {@link DeliveryInstructionRepository#createShippingPackage} writes onto the created
	 * {@code M_ShippingPackage}. Note there is no quantity here: the package's four quantity figures are derived
	 * (Task Q14, {@code ColumnSQL}) from the planning through the allocation, so all that survives from the
	 * planning's own quantity is the unit it is expressed in.
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
	 * The planning's own {@code ETD}/{@code ETA}/{@code LoadingTime}/{@code DeliveryTime}, offered as a candidate
	 * value for the instruction header's fill-if-empty defaulting. A request whose header is filled some other way
	 * (e.g. {@link DeliveryInstructionService#generateDeliveryInstruction}, which sets the header directly from its
	 * own {@code DeliveryInstructionCreateRequest} before ever building this type) contributes {@link #none()}.
	 */
	@Value
	@Builder
	public static class HeaderDateCandidate
	{
		@Nullable Timestamp etd;

		@Nullable Timestamp eta;

		@Nullable String loadingTime;

		@Nullable String deliveryTime;

		static HeaderDateCandidate none()
		{
			return HeaderDateCandidate.builder().build();
		}
	}
}

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.inout.InOutId;
import de.metas.incoterms.IncotermsId;
import de.metas.organization.OrgId;
import de.metas.quantity.Quantity;
import de.metas.shipping.ShipperId;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import java.time.Instant;

/**
 * One delivery planning, loaded for in-memory evaluation by {@link DeliveryPlanningList}. Carries the fields the
 * aggregation rules ask about - everything the delivery-instruction header can hold only one of (see
 * {@link DeliveryPlanningList.AggregationKeyField}) - plus whether it is closed and which delivery instructions it
 * is allocated to. Not a full mirror of {@code M_Delivery_Planning}.
 */
@Value
@Builder
public class DeliveryPlanning
{
	@NonNull DeliveryPlanningId id;

	@NonNull OrgId orgId;

	@NonNull TransportDirection transportDirection;

	/** The forwarder. Nullable: a planning may exist before one is chosen. */
	@Nullable ShipperId shipperId;

	@Nullable IncotermsId incotermsId;

	@Nullable String incotermLocation;

	@Nullable MeansOfTransportationId meansOfTransportationId;

	/** Where the transport starts - the header's loading address. */
	@Nullable BPartnerLocationId loadingLocationId;

	/** Where the transport ends - the header's delivery address. */
	@Nullable BPartnerLocationId deliveryLocationId;

	/**
	 * The planned departure. Nullable: a planning exists before one is planned. Carried here because it is the
	 * primary sort key of {@link DeliveryPlanningList#getIdsInAllocationOrder()}.
	 */
	@Nullable Instant etd;

	boolean closed;

	/**
	 * Read straight off the stored column - never re-derived here; task Q10 maintains the invariant
	 * {@code Processed == (IsClosed || IsDelivered)} at every write point.
	 * <p>
	 * UNLIKE the {@code @Nullable} fields below, this is a primitive whose unset value ({@code false}) is the
	 * PERMISSIVE answer for a guard, so EVERY mapper building a {@link DeliveryPlanning} from a record must set
	 * it - leaving it out silently waves a processed planning through.
	 */
	boolean processed;

	/**
	 * Read straight off the stored column, which the {@code M_Delivery_Planning_Alloc} interceptor keeps in
	 * step: it is true exactly while the planning has an ACTIVE allocation to a delivery instruction in
	 * {@code DocStatus = Completed}. Receiving before that is what it guards.
	 * <p>
	 * NOTE the polarity is the OPPOSITE of {@link #processed} above: there the unset value {@code false} is
	 * the permissive answer, so a mapper that forgets it waves a processed planning through. Here {@code false}
	 * is the RESTRICTIVE answer - a mapper that forgets it blocks a receivable planning instead of allowing an
	 * unreceivable one. That fails safe, but it is why a test building a receivable planning has to say so.
	 */
	boolean readyForReceipt;

	/**
	 * The five quantities are {@code @NonNull} because their columns all carry {@code AD_IsMandatory='Y'} - a
	 * planning always has them. They were {@code @Nullable} only because three partial record mappers used to
	 * omit them for callers that "never ask"; with one mapper setting every record-derived field, that reason
	 * is gone and the model can state what the dictionary already guarantees.
	 */
	@NonNull Quantity qtyOrdered;

	/** This planning's own planned LOAD figure - the load half of {@link DeliveryPlanningList#openPlanQty}'s pool. */
	@NonNull Quantity plannedLoadedQty;

	@NonNull Quantity actualLoadedQty;

	@NonNull Quantity plannedDischargeQty;

	@NonNull Quantity actualDischargeQty;

	/**
	 * Genuinely optional, unlike the quantities above: {@code M_InOut_ID} carries {@code AD_IsMandatory='N'} and
	 * is null on the majority of rows - a planning has no shipment/receipt until one is generated.
	 */
	@Nullable InOutId inOutId;

	/**
	 * This planning's ACTIVE allocations, one per delivery instruction it sits on. A list rather than a single id
	 * because multi-leg transport puts one planning on several instructions; no consumer may assume at most one.
	 */
	@NonNull
	@Builder.Default
	ImmutableList<DeliveryPlanningAlloc> allocations = ImmutableList.of();

	public boolean isAllocated() {return !allocations.isEmpty();}

	/** How many delivery instructions this planning is currently on. */
	public int getAllocationCount() {return allocations.size();}

	/**
	 * WHICH delivery instructions this planning is on, distinct - callers ask about documents, not allocation rows.
	 */
	public ImmutableSet<ShipperTransportationId> getDeliveryInstructionIds()
	{
		return allocations.stream()
				.map(DeliveryPlanningAlloc::getDeliveryInstructionId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isWithoutShipper() {return shipperId == null;}

	/**
	 * The same definition {@code M_Delivery_Planning.IsDelivered}'s {@code ColumnSQL} evaluates in SQL, so the two
	 * layers cannot diverge. On an incoming planning "delivered" means received.
	 */
	public boolean isDelivered() {return inOutId != null;}
}

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
	 * The order line's ordered quantity, replicated onto every planning of that line - {@code null} for a
	 * planning loaded by a caller that never asks {@link DeliveryPlanningList#openPlanQty} about it (e.g. the
	 * aggregation preconditions), which is why this is not {@code @NonNull}.
	 */
	@Nullable Quantity qtyOrdered;

	/** This planning's own planned LOAD figure - the load half of {@link DeliveryPlanningList#openPlanQty}'s pool. */
	@Nullable Quantity plannedLoadedQty;

	/** This planning's own ACTUAL load figure - {@code null}/zero until something is recorded against it. */
	@Nullable Quantity actualLoadedQty;

	/** This planning's own planned DISCHARGE figure - the discharge half of the pool. */
	@Nullable Quantity plannedDischargeQty;

	/** This planning's own ACTUAL discharge figure - a receipt's, once booked. */
	@Nullable Quantity actualDischargeQty;

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
}

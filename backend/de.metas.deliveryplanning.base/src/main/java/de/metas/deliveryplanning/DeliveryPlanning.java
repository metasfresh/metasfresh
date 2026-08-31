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
import de.metas.shipping.ShipperId;
import de.metas.shipping.TransportDirection;
import de.metas.shipping.model.ShipperTransportationId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

import java.time.Instant;

/**
 * One delivery planning, loaded for in-memory evaluation by {@link DeliveryPlanningList}.
 * <p>
 * Carries exactly the fields the aggregation rules ask about: everything the delivery-instruction header
 * can hold only one of (see {@link DeliveryPlanningList.AggregationKeyField}), plus the two pieces of state the
 * guards need: whether it is closed, and which delivery instructions it is allocated to. It is deliberately not a
 * full mirror of {@code M_Delivery_Planning}.
 */
@Value
@Builder
public class DeliveryPlanning
{
	@NonNull DeliveryPlanningId id;

	@NonNull OrgId orgId;

	/** The transport direction. */
	@NonNull TransportDirection type;

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
	 * The planned departure - {@code M_Delivery_Planning.ETD}, nullable because a planning exists before one is
	 * planned. Carried here for one reason: it is the primary key of the order the plannings of one delivery
	 * instruction are numbered in (see {@link DeliveryPlanningList#getIdsInAllocationOrder()}).
	 */
	@Nullable Instant etd;

	boolean closed;

	/**
	 * This planning's ACTIVE allocations - one per delivery instruction it currently sits on, empty for a planning
	 * on none. Never {@code null}.
	 * <p>
	 * A list and not a single instruction id: multi-leg transport puts one planning on several instructions, one
	 * per leg. Today the partial unique index {@code M_Delivery_Planning_Alloc_Planning_UQ} still permits only one
	 * active allocation per planning, so this list holds at most one entry - but no consumer may assume that.
	 */
	@NonNull
	@Builder.Default
	ImmutableList<DeliveryPlanningAlloc> allocations = ImmutableList.of();

	public boolean isAllocated() {return !allocations.isEmpty();}

	/**
	 * How many delivery instructions this planning is currently on - answerable from an already-loaded list,
	 * without reading the allocations again.
	 */
	public int getAllocationCount() {return allocations.size();}

	/**
	 * WHICH delivery instructions this planning is currently on, distinct - the counterpart of
	 * {@link #getAllocationCount()}, and empty for an unallocated planning.
	 * <p>
	 * Distinct rather than one entry per allocation: a caller asking "is it on THIS instruction" or "what are the
	 * doc statuses of its instructions" asks about documents, not about allocation rows.
	 */
	public ImmutableSet<ShipperTransportationId> getDeliveryInstructionIds()
	{
		return allocations.stream()
				.map(DeliveryPlanningAlloc::getDeliveryInstructionId)
				.collect(ImmutableSet.toImmutableSet());
	}

	public boolean isWithoutShipper() {return shipperId == null;}
}

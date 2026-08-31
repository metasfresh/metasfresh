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
 * can hold only one of (see {@link DeliveryPlanningList.AggregationKeyField}), plus the two state flags the
 * guards need. It is deliberately not a full mirror of {@code M_Delivery_Planning}.
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

	/** The delivery instruction this planning's active allocation points at, if it has one. */
	@Nullable ShipperTransportationId deliveryInstructionId;

	public boolean isAllocated() {return deliveryInstructionId != null;}

	public boolean isWithoutShipper() {return shipperId == null;}
}

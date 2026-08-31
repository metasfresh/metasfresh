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

import de.metas.shipping.model.ShipperTransportationId;
import de.metas.shipping.model.ShippingPackageId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/**
 * One ACTIVE allocation of a delivery planning to a delivery instruction - IDS ONLY.
 * <p>
 * Deliberately nothing but the four ids: this is what {@link DeliveryPlanning} carries so that "how many
 * instructions is this planning on" and "which ones" can be answered from an already-loaded list, and every
 * field beyond an id would turn that into a second load. Quantities, dates and the instruction's own
 * {@code DocStatus} are read from the instruction or the shipping package, which own them.
 * <p>
 * A planning carries a LIST of these rather than a single instruction id because multi-leg transport allocates
 * one planning to several instructions - one per leg. The single-active-allocation partial unique index
 * {@code M_Delivery_Planning_Alloc_Planning_UQ} still enforces one leg today; the domain simply stops
 * asserting it.
 */
@Value
@Builder
public class DeliveryPlanningAlloc
{
	@NonNull DeliveryPlanningAllocId id;

	@NonNull DeliveryPlanningId deliveryPlanningId;

	@NonNull ShipperTransportationId deliveryInstructionId;

	/** Never null: {@code M_Delivery_Planning_Alloc.M_ShippingPackage_ID} is mandatory and foreign-keyed. */
	@NonNull ShippingPackageId shippingPackageId;
}

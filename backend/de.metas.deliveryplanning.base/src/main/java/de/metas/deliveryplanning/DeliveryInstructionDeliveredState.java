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

import de.metas.shipping.model.X_M_ShipperTransportation;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.Getter;
import lombok.NonNull;

/**
 * The three-state delivered indicator of a delivery instruction ({@code M_ShipperTransportation.DeliveredState}),
 * spec &sect; 5.7: a boolean would collapse "nothing has moved" and "six of seven plannings have moved" into a
 * single "not fully", which is exactly the distinction an operator needs to act on a consolidated instruction.
 * <p>
 * Defined over the instruction's ACTIVELY allocated plannings' own {@link DeliveryPlanning#isDelivered()} - see
 * {@link DeliveryPlanningList#getDeliveredState()}, the one place this is computed. Never re-derived from
 * {@code M_InOut} directly, so the two levels (planning and instruction) cannot disagree.
 */
public enum DeliveryInstructionDeliveredState implements ReferenceListAwareEnum
{
	/** No allocation's planning is delivered - vacuously true for an instruction with no active allocation. */
	NotDelivered(X_M_ShipperTransportation.DELIVEREDSTATE_NotDelivered),
	/** Some allocations' plannings are delivered, some are not - the normal intermediate state of a consolidated instruction. */
	PartlyDelivered(X_M_ShipperTransportation.DELIVEREDSTATE_PartlyDelivered),
	/** Every allocation's planning is delivered. */
	FullyDelivered(X_M_ShipperTransportation.DELIVEREDSTATE_FullyDelivered);

	private static final ReferenceListAwareEnums.ValuesIndex<DeliveryInstructionDeliveredState> valuesByCode = ReferenceListAwareEnums.index(values());

	@Getter
	private final String code;

	DeliveryInstructionDeliveredState(@NonNull final String code)
	{
		this.code = code;
	}

	public static DeliveryInstructionDeliveredState ofCode(@NonNull final String code)
	{
		return valuesByCode.ofCode(code);
	}
}

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.inoutcandidate.api;

import com.google.common.collect.ImmutableList;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class ShipmentScheduleAllowConsolidatePredicateComposite
{
	private final ImmutableList<IShipmentScheduleAllowConsolidatePredicate> shipmentScheduleAllowConsolidatePredicates;

	public ShipmentScheduleAllowConsolidatePredicateComposite(@NonNull final ImmutableList<IShipmentScheduleAllowConsolidatePredicate> shipmentScheduleAllowConsolidatePredicates)
	{
		this.shipmentScheduleAllowConsolidatePredicates = shipmentScheduleAllowConsolidatePredicates;
	}
	
	public boolean isSchedAllowsConsolidate(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleAllowConsolidatePredicates
				.stream()
				.allMatch(predicate -> predicate.isSchedAllowsConsolidate(shipmentSchedule));
	}
}

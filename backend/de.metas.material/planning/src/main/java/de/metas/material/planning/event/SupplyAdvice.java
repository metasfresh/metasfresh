/*
 * #%L
 * metasfresh-material-planning
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

package de.metas.material.planning.event;

import com.google.common.collect.ImmutableList;
import de.metas.material.event.MaterialEvent;
import de.metas.quantity.Quantity;
import lombok.NonNull;
import lombok.Value;

import java.util.Collection;

/**
 * Return value from {@link SupplyRequiredAdvisor#createAdvisedEvents}:
 * the advised events the advisor wants fired plus the portion of the demand
 * this advisor has claimed. The remainder is passed to the next advisor in
 * priority order by {@link SupplyRequiredHandler}.
 */
@Value
public class SupplyAdvice
{
	@NonNull ImmutableList<MaterialEvent> events;
	@NonNull Quantity consumedQty;

	public static SupplyAdvice nothing(@NonNull final Quantity remainingQty)
	{
		return new SupplyAdvice(ImmutableList.of(), remainingQty.toZero());
	}

	public static SupplyAdvice of(
			@NonNull final Collection<? extends MaterialEvent> events,
			@NonNull final Quantity consumedQty)
	{
		return new SupplyAdvice(ImmutableList.copyOf(events), consumedQty);
	}
}

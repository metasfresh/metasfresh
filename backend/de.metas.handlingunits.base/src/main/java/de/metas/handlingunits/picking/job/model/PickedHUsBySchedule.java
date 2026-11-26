package de.metas.handlingunits.picking.job.model;

/*
 * #%L
 * de.metas.handlingunits.base
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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;
import de.metas.handlingunits.HuId;
import de.metas.inout.ShipmentScheduleId;
import lombok.EqualsAndHashCode;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * The HUs that the picking of each shipment schedule recorded as picked (the picking job steps' picked HUs).
 * <p>
 * A schedule that has some: only those CUs of a TU count for it. A schedule that has none (or nothing is known at all,
 * see {@link #EMPTY}): every CU of a TU counts for it.
 */
@EqualsAndHashCode
@ToString
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class PickedHUsBySchedule
{
	public static final PickedHUsBySchedule EMPTY = new PickedHUsBySchedule(ImmutableSetMultimap.of());

	@NonNull private final ImmutableSetMultimap<ShipmentScheduleId, HuId> huIdsBySchedule;

	public static PickedHUsBySchedule of(@NonNull final ImmutableSetMultimap<ShipmentScheduleId, HuId> huIdsBySchedule)
	{
		return huIdsBySchedule.isEmpty() ? EMPTY : new PickedHUsBySchedule(huIdsBySchedule);
	}

	/**
	 * @return the HUs recorded as picked for the schedule; empty if the schedule has none
	 */
	public ImmutableSet<HuId> getPickedHuIds(@NonNull final ShipmentScheduleId scheduleId)
	{
		return huIdsBySchedule.get(scheduleId);
	}
}

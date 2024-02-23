/*
 * #%L
 * de.metas.handlingunits.base
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

package de.metas.handlingunits.picking.job.model;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.util.GuavaCollectors;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Stream;

@EqualsAndHashCode
@ToString
public class PickingJobReferenceList
{
	private static final PickingJobReferenceList EMPTY = new PickingJobReferenceList(ImmutableList.of());
	private final ImmutableList<PickingJobReference> list;

	private PickingJobReferenceList(@NonNull final List<PickingJobReference> list)
	{
		this.list = ImmutableList.copyOf(list);
	}

	private static PickingJobReferenceList ofList(final List<PickingJobReference> list)
	{
		return !list.isEmpty() ? new PickingJobReferenceList(list) : EMPTY;
	}

	public static Collector<PickingJobReference, ?, PickingJobReferenceList> collect()
	{
		return GuavaCollectors.collectUsingListAccumulator(PickingJobReferenceList::ofList);
	}

	public boolean isEmpty() {return list.isEmpty();}

	public Stream<PickingJobReference> stream() {return list.stream();}

	public Stream<PickingJobReference> streamNotInProcessing()
	{
		return stream().filter(existingPickingJob -> !existingPickingJob.isShipmentSchedulesLocked());
	}

	public ImmutableSet<ShipmentScheduleId> getShipmentScheduleIds()
	{
		return list.stream()
				.flatMap(existingPickingJob -> existingPickingJob.getShipmentScheduleIds().stream())
				.collect(ImmutableSet.toImmutableSet());
	}
}

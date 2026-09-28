package de.metas.handlingunits.inout.impl;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.inout.InOutLineId;
import de.metas.logging.LogManager;
import de.metas.project.ProjectId;
import lombok.NonNull;
import lombok.Value;
import org.compiere.model.I_M_InOut;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Safety-net detector for a packing unit (TU/LU HU, or the default-pallet packing instruction) that ends up
 * serving shipment lines of more than one Positions Nr. ({@code C_Project_ID}). Pure function, no persistence.
 */
public class ShipmentPackingUnitProjectConflictDetector
{
	private static final Logger logger = LogManager.getLogger(ShipmentPackingUnitProjectConflictDetector.class);

	private ShipmentPackingUnitProjectConflictDetector()
	{
	}

	public static ImmutableList<Conflict> detect(@NonNull final List<Usage> usagesInLineOrder)
	{
		final Map<String, List<Usage>> usagesByUnitKey = new LinkedHashMap<>();
		for (final Usage usage : usagesInLineOrder)
		{
			usagesByUnitKey.computeIfAbsent(usage.getUnitKey(), key -> new ArrayList<>()).add(usage);
		}

		final ImmutableList.Builder<Conflict> conflicts = ImmutableList.builder();
		for (final Map.Entry<String, List<Usage>> entry : usagesByUnitKey.entrySet())
		{
			final String unitKey = entry.getKey();
			final List<Usage> usages = entry.getValue();

			final ImmutableSet<ProjectId> projectIds = usages.stream()
					.map(Usage::getProjectId)
					.filter(Objects::nonNull)
					.distinct()
					.collect(ImmutableSet.toImmutableSet());

			if (projectIds.size() <= 1)
			{
				continue;
			}

			final ProjectId bookedProjectId = usages.stream()
					.filter(Usage::isBooked)
					.findFirst()
					.map(Usage::getProjectId)
					.orElse(null);

			conflicts.add(new Conflict(unitKey, projectIds, bookedProjectId));
		}

		return conflicts.build();
	}

	public static void logWarnings(@NonNull final I_M_InOut shipment, @NonNull final List<Conflict> conflicts)
	{
		for (final Conflict conflict : conflicts)
		{
			final Object bookedProjectRepoId = conflict.getBookedProjectId() != null
					? conflict.getBookedProjectId().getRepoId()
					: "none";

			logger.warn("Shipment {} (M_InOut_ID={}): packing unit {} is shared by more than one Positions Nr. (C_Project_ID) {}; booked to {}",
					shipment.getDocumentNo(),
					shipment.getM_InOut_ID(),
					conflict.getUnitKey(),
					conflict.getProjectIds(),
					bookedProjectRepoId);
		}
	}

	@Value
	public static class Usage
	{
		@NonNull String unitKey;
		@NonNull InOutLineId inOutLineId;
		@Nullable ProjectId projectId;
		boolean booked;
	}

	@Value
	public static class Conflict
	{
		@NonNull String unitKey;
		@NonNull ImmutableSet<ProjectId> projectIds;
		@Nullable ProjectId bookedProjectId;
	}
}

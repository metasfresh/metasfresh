package de.metas.ui.web.pickingV2.packageable;

import java.util.Collection;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.inoutcandidate.ShipmentScheduleId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.util.GuavaCollectors;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

final class PackageableRowsIndex
{
	public static PackageableRowsIndex of(final Collection<PackageableRow> rows)
	{
		return new PackageableRowsIndex(rows);
	}

	private final ImmutableMap<DocumentId, PackageableRow> rowsById;
	private final ImmutableListMultimap<ShipmentScheduleId, PackageableRow> rowsByShipmentScheduleId;

	private PackageableRowsIndex(final Collection<PackageableRow> rows)
	{
		rowsById = Maps.uniqueIndex(rows, PackageableRow::getId);
		rowsByShipmentScheduleId = rows.stream()
				.flatMap(row -> row.getShipmentScheduleIds()
						.stream()
						.map(shipmentScheduleId -> GuavaCollectors.entry(shipmentScheduleId, row)))
				.collect(GuavaCollectors.toImmutableListMultimap());
	}

	public ImmutableMap<DocumentId, PackageableRow> getRowsIndexedById()
	{
		return rowsById;
	}

	private ImmutableList<PackageableRow> getRowsByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return rowsByShipmentScheduleId.get(shipmentScheduleId);
	}

	public ImmutableSet<DocumentId> getRowIdsByShipmentScheduleId(@NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		return getRowsByShipmentScheduleId(shipmentScheduleId)
				.stream()
				.map(PackageableRow::getId)
				.collect(ImmutableSet.toImmutableSet());

	}
}

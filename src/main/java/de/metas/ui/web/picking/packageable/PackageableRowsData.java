package de.metas.ui.web.picking.packageable;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;
import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ui.web.view.AbstractCustomView.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
import lombok.ToString;

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

@ToString
final class PackageableRowsData implements IRowsData<PackageableRow>
{
	public static PackageableRowsData ofSupplier(final Supplier<List<PackageableRow>> rowsSupplier)
	{
		return new PackageableRowsData(rowsSupplier);
	}

	public static PackageableRowsData cast(final IRowsData<PackageableRow> rowsData)
	{
		return (PackageableRowsData)rowsData;
	}

	public static final PackageableRowsData EMPTY = new PackageableRowsData(ImmutableList::of);

	private final ExtendedMemorizingSupplier<Map<DocumentId, PackageableRow>> topLevelRows;

	private final ImmutableListMultimap<TableRecordReference, DocumentId> initialDocumentIdsByRecordRef;

	private PackageableRowsData(@NonNull final Supplier<List<PackageableRow>> rowsSupplier)
	{
		topLevelRows = ExtendedMemorizingSupplier.of(() -> Maps.uniqueIndex(rowsSupplier.get(), PackageableRow::getId));

		//
		// Remember initial rows
		// We will use this map to figure out what we can invalidate,
		// because we want to cover the case of rows which just vanished (e.g. everything was delivered)
		// and the case of rows which appeared back (e.g. the picking candidate was reactivated so we still have QtyToDeliver).
		initialDocumentIdsByRecordRef = getAllRows()
				.stream()
				.collect(ImmutableListMultimap.toImmutableListMultimap(PackageableRow::getTableRecordReference, PackageableRow::getId));
	}

	@Override
	public Map<DocumentId, PackageableRow> getDocumentId2TopLevelRows()
	{
		return topLevelRows.get();
	}

	@Override
	public ListMultimap<TableRecordReference, PackageableRow> getTableRecordReference2rows()
	{
		return getAllRows()
				.stream()
				.collect(GuavaCollectors.toImmutableListMultimap(PackageableRow::getTableRecordReference));
	}

	@Override
	public void invalidateAll()
	{
		topLevelRows.forget();
	}

	@Override
	public Stream<DocumentId> streamDocumentIdsToInvalidate(final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();
		if (I_M_ShipmentSchedule.Table_Name.equals(tableName))
		{
			final int shipmentScheduleId = recordRef.getRecord_ID();
			final TableRecordReference recordRefEffective = PackageableRow.createTableRecordReferenceFromShipmentScheduleId(shipmentScheduleId);
			return initialDocumentIdsByRecordRef.get(recordRefEffective).stream();
		}
		else
		{
			return Stream.empty();
		}
	}
}

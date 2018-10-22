package de.metas.ui.web.pickingV2.packageable;

import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableSet;

import de.metas.inoutcandidate.api.ShipmentScheduleId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ui.web.view.AbstractCustomView.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
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

final class PackageableRowsData implements IRowsData<PackageableRow>
{
	public static PackageableRowsData cast(final IRowsData<PackageableRow> rowsData)
	{
		return (PackageableRowsData)rowsData;
	}

	public static PackageableRowsData newInstance(final PackageableRowsRepository repo)
	{
		return new PackageableRowsData(repo);
	}

	private final ExtendedMemorizingSupplier<PackageableRowsIndex> rowsIndexSupplier;
	private final PackageableRowsIndex initialRowsIndex;

	private PackageableRowsData(@NonNull final PackageableRowsRepository repo)
	{
		rowsIndexSupplier = ExtendedMemorizingSupplier.of(() -> PackageableRowsIndex.of(repo.retrieveRows()));

		//
		// Remember initial rows
		// We will use this map to figure out what we can invalidate,
		// because we want to cover the case of rows which just vanished (e.g. everything was delivered)
		// and the case of rows which appeared back (e.g. the picking candidate was reactivated so we still have QtyToDeliver).
		initialRowsIndex = rowsIndexSupplier.get();
	}

	@Override
	public Map<DocumentId, PackageableRow> getDocumentId2TopLevelRows()
	{
		return getPackageableRowsIndex().getRowsIndexedById();
	}

	private PackageableRowsIndex getPackageableRowsIndex()
	{
		return rowsIndexSupplier.get();
	}

	@Override
	public void invalidateAll()
	{
		rowsIndexSupplier.forget();
	}

	@Override
	public Stream<DocumentId> streamDocumentIdsToInvalidate(final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();
		if (I_M_ShipmentSchedule.Table_Name.equals(tableName))
		{
			final ShipmentScheduleId shipmentScheduleId = ShipmentScheduleId.ofRepoId(recordRef.getRecord_ID());
			return streamDocumentIdsToInvalidate(shipmentScheduleId);
		}
		else
		{
			return Stream.empty();
		}
	}

	private Stream<DocumentId> streamDocumentIdsToInvalidate(final ShipmentScheduleId shipmentScheduleId)
	{
		// TODO: handle the case when a row has to be added, when a how was deleted

		return ImmutableSet.<DocumentId> builder()
				.addAll(getPackageableRowsIndex().getRowIdsByShipmentScheduleId(shipmentScheduleId))
				.addAll(initialRowsIndex.getRowIdsByShipmentScheduleId(shipmentScheduleId))
				.build()
				.stream();
	}
}

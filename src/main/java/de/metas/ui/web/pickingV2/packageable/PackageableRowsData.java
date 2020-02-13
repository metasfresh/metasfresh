package de.metas.ui.web.pickingV2.packageable;

import java.util.Map;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.Getter;
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

	@Getter
	private final DocumentFilterList stickyFilters;
	@Getter
	private final DocumentFilterList filters;

	private final ExtendedMemorizingSupplier<PackageableRowsIndex> rowsIndexSupplier;

	@Builder
	private PackageableRowsData(
			@NonNull final PackageableRowsRepository repo,
			@NonNull final DocumentFilterList stickyFilters,
			@NonNull final DocumentFilterList filters)
	{
		this.filters = filters;
		this.stickyFilters = stickyFilters;

		final DocumentFilterList allFilters = filters.mergeWith(stickyFilters);

		rowsIndexSupplier = ExtendedMemorizingSupplier.of(() -> PackageableRowsIndex.of(repo.retrieveRows(allFilters)));
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
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return recordRefs.matchesTableName(I_M_ShipmentSchedule.Table_Name) ? DocumentIdsSelection.ALL : DocumentIdsSelection.EMPTY;
	}
}

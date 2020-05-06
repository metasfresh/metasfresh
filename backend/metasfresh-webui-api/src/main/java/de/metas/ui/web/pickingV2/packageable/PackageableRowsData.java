package de.metas.ui.web.pickingV2.packageable;

import java.util.List;
import java.util.Map;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import com.google.common.collect.ImmutableList;

import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.Builder;
import lombok.Getter;

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
	private final ImmutableList<DocumentFilter> stickyFilters;
	@Getter
	private final ImmutableList<DocumentFilter> filters;

	private final ExtendedMemorizingSupplier<PackageableRowsIndex> rowsIndexSupplier;

	@Builder
	private PackageableRowsData(
			final PackageableRowsRepository repo,
			final List<DocumentFilter> stickyFilters,
			final List<DocumentFilter> filters)
	{
		this.filters = ImmutableList.copyOf(filters);
		this.stickyFilters = ImmutableList.copyOf(stickyFilters);

		final ImmutableList<DocumentFilter> allFilters = ImmutableList.<DocumentFilter> builder()
				.addAll(filters)
				.addAll(stickyFilters)
				.build();

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

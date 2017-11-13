package de.metas.ui.web.order.purchase.view;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;

import de.metas.handlingunits.model.I_C_OLCand;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

class OLCandRowsCollection
{
	private static final int DEFAULT_PAGE_LENGTH = 30;

	public static final OLCandRowsCollection ofSupplier(final OLCandRowsSupplier rowsSupplier)
	{
		return new OLCandRowsCollection(rowsSupplier);
	}

	private final ExtendedMemorizingSupplier<Map<DocumentId, OLCandRow>> rowsSupplier;

	private OLCandRowsCollection(@NonNull final OLCandRowsSupplier rowsSupplier)
	{
		this.rowsSupplier = ExtendedMemorizingSupplier.of(() -> Maps.uniqueIndex(rowsSupplier.retrieveRows(), OLCandRow::getId));
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this).addValue(rowsSupplier).toString();
	}

	public void invalidateAll()
	{
		rowsSupplier.forget();
	}

	private final Map<DocumentId, OLCandRow> getRowsMap()
	{
		return rowsSupplier.get();
	}

	public long size()
	{
		return getRowsMap().size();
	}

	public List<OLCandRow> getPage(final int firstRow, final int pageLength)
	{
		return getRowsMap().values().stream()
				.skip(firstRow >= 0 ? firstRow : 0)
				.limit(pageLength > 0 ? pageLength : DEFAULT_PAGE_LENGTH)
				.collect(ImmutableList.toImmutableList());
	}

	public OLCandRow getById(@NonNull final DocumentId rowId) throws EntityNotFoundException
	{
		final OLCandRow row = getRowsMap().get(rowId);
		if (row == null)
		{
			throw new EntityNotFoundException("Row not found").setParameter("rowId", rowId);
		}
		return row;
	}

	public Stream<? extends IViewRow> streamByIds(final DocumentIdsSelection rowIds)
	{
		if (rowIds.isAll())
		{
			return getRowsMap().values().stream();
		}
		else
		{
			return rowIds.stream().map(this::getById);
		}
	}

	public boolean notifyRecordsChanged(Set<TableRecordReference> recordRefs)
	{
		final Set<DocumentId> rowIds = getRowsMap().keySet();

		final boolean matches = recordRefs.stream()
				.filter(record -> I_C_OLCand.Table_Name.equals(record.getTableName()))
				.map(record -> DocumentId.of(record.getRecord_ID()))
				.anyMatch(rowIds::contains);
		
		if(matches)
		{
			invalidateAll();
			return true;
		}
		else
		{
			return false;
		}
	}
}

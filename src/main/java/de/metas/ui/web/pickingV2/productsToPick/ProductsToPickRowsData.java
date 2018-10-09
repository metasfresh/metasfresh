package de.metas.ui.web.pickingV2.productsToPick;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableList;

import de.metas.ui.web.view.AbstractCustomView.IRowsData;
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

class ProductsToPickRowsData implements IRowsData<ProductsToPickRow>
{

	public static ProductsToPickRowsData ofRows(final List<ProductsToPickRow> rows)
	{
		return new ProductsToPickRowsData(rows);
	}

	private final ImmutableList<DocumentId> rowIdsOrdered;
	private final ConcurrentHashMap<DocumentId, ProductsToPickRow> rowsById;

	private ProductsToPickRowsData(final List<ProductsToPickRow> rows)
	{
		rowIdsOrdered = rows.stream()
				.map(ProductsToPickRow::getId)
				.distinct()
				.collect(ImmutableList.toImmutableList());

		rowsById = rows.stream()
				.map(row -> GuavaCollectors.entry(row.getId(), row))
				.collect(GuavaCollectors.toMap(ConcurrentHashMap::new));
	}

	@Override
	public Map<DocumentId, ProductsToPickRow> getDocumentId2TopLevelRows()
	{
		return rowsById;
	}

	@Override
	public Collection<ProductsToPickRow> getTopLevelRows()
	{
		return rowIdsOrdered.stream()
				.map(rowsById::get)
				.collect(ImmutableList.toImmutableList());
	}

	public void changeRow(@NonNull final DocumentId rowId, @NonNull UnaryOperator<ProductsToPickRow> mapper)
	{
		rowsById.compute(rowId, (k, row) -> {
			if (row == null)
			{
				throw new AdempiereException("No row found for id: " + k);
			}
			else
			{
				return mapper.apply(row);
			}
		});
	}

	@Override
	public Stream<DocumentId> streamDocumentIdsToInvalidate(final TableRecordReference recordRef)
	{
		return Stream.empty();
	}

	@Override
	public void invalidateAll()
	{
	}

}

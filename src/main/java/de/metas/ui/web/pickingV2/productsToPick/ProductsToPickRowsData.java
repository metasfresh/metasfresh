package de.metas.ui.web.pickingV2.productsToPick;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.util.lang.impl.TableRecordReference;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.view.AbstractCustomView.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;

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

	private final ImmutableMap<DocumentId, ProductsToPickRow> topLevelRowsByDocumentId;

	private ProductsToPickRowsData(final List<ProductsToPickRow> rows)
	{
		topLevelRowsByDocumentId = Maps.uniqueIndex(rows, ProductsToPickRow::getId);
	}

	@Override
	public Map<DocumentId, ProductsToPickRow> getDocumentId2TopLevelRows()
	{
		return topLevelRowsByDocumentId;
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

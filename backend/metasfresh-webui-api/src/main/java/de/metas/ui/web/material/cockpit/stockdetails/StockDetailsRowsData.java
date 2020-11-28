package de.metas.ui.web.material.cockpit.stockdetails;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.util.lang.impl.TableRecordReferenceSet;

import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.stock.HUStockInfo;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
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

public class StockDetailsRowsData implements IRowsData<StockDetailsRow>
{
	public static StockDetailsRowsData of(@NonNull final Stream<HUStockInfo> huStockInfos)
	{
		return new StockDetailsRowsData(huStockInfos);
	}

	private final Map<DocumentId, StockDetailsRow> document2StockDetailsRow;

	private StockDetailsRowsData(@NonNull final Stream<HUStockInfo> huStockInfos)
	{
		final ImmutableMap.Builder<DocumentId, StockDetailsRow> builder = ImmutableMap.builder();

		final Iterator<HUStockInfo> iterator = huStockInfos.iterator();
		while (iterator.hasNext())
		{
			final HUStockInfo huStockInfo = iterator.next();
			final StockDetailsRow row = StockDetailsRow.of(huStockInfo);

			builder.put(row.getId(), row);
		}

		document2StockDetailsRow = builder.build();
	}

	@Override
	public Map<DocumentId, StockDetailsRow> getDocumentId2TopLevelRows()
	{
		return document2StockDetailsRow;
	}

	@Override
	public DocumentIdsSelection getDocumentIdsToInvalidate(final TableRecordReferenceSet recordRefs)
	{
		return DocumentIdsSelection.EMPTY;
	}

	@Override
	public void invalidateAll()
	{
	}
}

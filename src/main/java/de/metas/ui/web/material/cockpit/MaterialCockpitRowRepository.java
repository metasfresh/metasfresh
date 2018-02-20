package de.metas.ui.web.material.cockpit;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_M_Product;
import org.compiere.util.CCache;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;

import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.ui.web.document.filter.DocumentFilter;
import de.metas.ui.web.material.cockpit.filters.MaterialCockpitFilters;
import de.metas.ui.web.material.cockpit.filters.StockFilters;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory.CreateRowsRequest;
import de.metas.ui.web.view.AbstractCustomView.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
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

@Repository
public class MaterialCockpitRowRepository
{
	private final transient CCache<Integer, List<I_M_Product>> orgIdToproducts = CCache.newCache(
			"MaterialCockpitRowRepository#" + I_M_Product.Table_Name,
			10, // initial size
			CCache.EXPIREMINUTES_Never);

	private final MaterialCockpitFilters materialCockpitFilters;

	private final MaterialCockpitRowFactory materialCockpitRowFactory;

	public MaterialCockpitRowRepository(
			@NonNull final MaterialCockpitFilters materialCockpitFilters,
			@NonNull final MaterialCockpitRowFactory materialCockpitRowFactory)
	{
		this.materialCockpitFilters = materialCockpitFilters;
		this.materialCockpitRowFactory = materialCockpitRowFactory;
	}

	public IRowsData<MaterialCockpitRow> createRowsData(@NonNull final List<DocumentFilter> filters)
	{
		return new IRowsData<MaterialCockpitRow>()
		{
			private final ExtendedMemorizingSupplier<List<MaterialCockpitRow>> topLevelRows = //
					ExtendedMemorizingSupplier.of(() -> retrieveRows(filters));

			@Override
			public Map<DocumentId, MaterialCockpitRow> getDocumentId2TopLevelRows()
			{
				return Maps.uniqueIndex(topLevelRows.get(), row -> row.getId());
			}

			@Override
			public ListMultimap<TableRecordReference, MaterialCockpitRow> getTableRecordReference2rows()
			{
				return extractTableRecordReference2DocumentId2(getDocumentId2AllRows().values());
			}

			@Override
			public void invalidateAll()
			{
				topLevelRows.forget();
			}
		};
	}

	private List<MaterialCockpitRow> retrieveRows(@NonNull final List<DocumentFilter> filters)
	{
		final Date date = materialCockpitFilters.getFilterByDate(filters);
		if (date == null)
		{
			return ImmutableList.of();
		}

		final List<I_MD_Cockpit> cockpitRecords = materialCockpitFilters
				.createQuery(filters)
				.list();

		final List<I_MD_Stock> stockRecords = StockFilters
				.createStockQueryFor(filters)
				.list();

		final CreateRowsRequest request = CreateRowsRequest.builder()
				.date(TimeUtil.asTimestamp(date))
				.productsToListEvenIfEmpty(retrieveRelevantProducts(filters))
				.cockpitRecords(cockpitRecords)
				.stockRecords(stockRecords)
				.build();
		return materialCockpitRowFactory.createRows(request);
	}

	private List<I_M_Product> retrieveRelevantProducts(@NonNull final List<DocumentFilter> filters)
	{
		final int orgId = Env.getAD_Org_ID(Env.getCtx());
		final List<I_M_Product> allProducts = orgIdToproducts
				.getOrLoad(orgId, () -> retrieveAllProducts(orgId));

		return allProducts.stream()
				.filter(materialCockpitFilters.toProductFilterPredicate(filters))
				.collect(ImmutableList.toImmutableList());
	}

	private List<I_M_Product> retrieveAllProducts(final int adOrgId)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryFilter<I_M_Product> relevantProductFilter = //
				queryBL.createCompositeQueryFilter(I_M_Product.class)
						.setJoinOr()
						.addEqualsFilter(I_M_Product.COLUMN_IsSold, true)
						.addEqualsFilter(I_M_Product.COLUMN_IsPurchased, true)
						.addEqualsFilter(I_M_Product.COLUMN_IsStocked, true);

		final List<I_M_Product> products = //
				queryBL.createQueryBuilder(I_M_Product.class)
						.addOnlyActiveRecordsFilter()
						.addInArrayFilter(I_M_Product.COLUMN_AD_Org_ID, adOrgId, 0)
						.filter(relevantProductFilter)
						.create()
						.list();
		return products;
	}

	private static ListMultimap<TableRecordReference, MaterialCockpitRow> extractTableRecordReference2DocumentId2(
			@NonNull final Collection<MaterialCockpitRow> allRows)
	{
		final ListMultimap<TableRecordReference, MaterialCockpitRow> recordReference2DocumentId = ArrayListMultimap.create();

		for (final MaterialCockpitRow materialCockpitRow : allRows)
		{
			materialCockpitRow
					.getAllIncludedCockpitRecordIds()
					.forEach(cockpitRecordId -> recordReference2DocumentId
							.put(TableRecordReference.of(I_MD_Cockpit.Table_Name, cockpitRecordId), materialCockpitRow));

			materialCockpitRow
					.getAllIncludedStockRecordIds()
					.forEach(stockRecordId -> recordReference2DocumentId
							.put(TableRecordReference.of(I_MD_Stock.Table_Name, stockRecordId), materialCockpitRow));
		}

		return recordReference2DocumentId;
	}
}

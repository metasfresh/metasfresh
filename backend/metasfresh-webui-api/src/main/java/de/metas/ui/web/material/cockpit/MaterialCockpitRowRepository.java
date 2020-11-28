package de.metas.ui.web.material.cockpit;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.ExtendedMemorizingSupplier;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import de.metas.cache.CCache;
import de.metas.cache.CCache.CacheMapType;
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.cockpit.model.I_MD_Stock;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.ui.web.document.filter.DocumentFilterList;
import de.metas.ui.web.material.cockpit.filters.MaterialCockpitFilters;
import de.metas.ui.web.material.cockpit.filters.ProductFilterUtil;
import de.metas.ui.web.material.cockpit.filters.ProductFilterVO;
import de.metas.ui.web.material.cockpit.filters.StockFilters;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory;
import de.metas.ui.web.material.cockpit.rowfactory.MaterialCockpitRowFactory.CreateRowsRequest;
import de.metas.ui.web.view.template.IRowsData;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;

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
	private static final String SYSCONFIG_EMPTY_PRODUCTS_LIMIT = "de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.Limit";
	private static final int DEFAULT_EMPTY_PRODUCTS_LIMIT = 2000;

	private static final String SYSCONFIG_EMPTY_PRODUCTS_CACHESIZE = "de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.CacheSize";
	private static final int DEFAULT_EMPTY_PRODUCTS_CACHESIZE = 10;

	private final transient CCache<CacheKey, ImmutableSet<ProductId>> productFilterVOToProducts;

	@Value
	private static class CacheKey
	{
		@NonNull
		OrgId orgId;

		@NonNull
		ProductFilterVO productFilterVO;

		int limit;
	}

	private final MaterialCockpitFilters materialCockpitFilters;

	private final MaterialCockpitRowFactory materialCockpitRowFactory;

	public MaterialCockpitRowRepository(
			@NonNull final MaterialCockpitFilters materialCockpitFilters,
			@NonNull final MaterialCockpitRowFactory materialCockpitRowFactory)
	{
		this.materialCockpitFilters = materialCockpitFilters;
		this.materialCockpitRowFactory = materialCockpitRowFactory;

		// setup caching
		final int cacheSize = Services
				.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_EMPTY_PRODUCTS_CACHESIZE, DEFAULT_EMPTY_PRODUCTS_CACHESIZE);

		productFilterVOToProducts = CCache
				.<CacheKey, ImmutableSet<ProductId>> builder()
				.tableName(I_M_Product.Table_Name)
				.cacheMapType(CacheMapType.LRU)
				.initialCapacity(cacheSize)
				.build();
	}

	public IRowsData<MaterialCockpitRow> createRowsData(@NonNull DocumentFilterList filters)
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
			public DocumentIdsSelection getDocumentIdsToInvalidate(@NonNull final TableRecordReferenceSet recordRefs)
			{
				final TableRecordReferenceSet recordRefsEligible = recordRefs.filter(recordRef -> isEligibleRecordRef(recordRef));
				if (recordRefsEligible.isEmpty())
				{
					return DocumentIdsSelection.EMPTY;
				}

				return getAllRows()
						.stream()
						.filter(row -> isRowMatching(row, recordRefsEligible))
						.map(MaterialCockpitRow::getId)
						.collect(DocumentIdsSelection.toDocumentIdsSelection());
			}

			@Override
			public void invalidateAll()
			{
				topLevelRows.forget();
			}
		};
	}

	private List<MaterialCockpitRow> retrieveRows(@NonNull final DocumentFilterList filters)
	{
		final LocalDate date = materialCockpitFilters.getFilterByDate(filters);
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

		final boolean includePerPlantDetailRows = retrieveIsIncludePerPlantDetailRows();

		final CreateRowsRequest request = CreateRowsRequest
				.builder()
				.date(date)
				.productIdsToListEvenIfEmpty(retrieveRelevantProductIds(filters))
				.cockpitRecords(cockpitRecords)
				.stockRecords(stockRecords)
				.includePerPlantDetailRows(includePerPlantDetailRows)
				.build();
		return materialCockpitRowFactory.createRows(request);
	}

	private boolean retrieveIsIncludePerPlantDetailRows()
	{
		final boolean includePerPlantDetailRows = Services.get(ISysConfigBL.class).getBooleanValue(
				MaterialCockpitUtil.SYSCONFIG_INCLUDE_PER_PLANT_DETAIL_ROWS,
				false,
				Env.getAD_Client_ID(),
				Env.getAD_Org_ID(Env.getCtx()));
		return includePerPlantDetailRows;
	}

	private ImmutableSet<ProductId> retrieveRelevantProductIds(@NonNull final DocumentFilterList filters)
	{
		final OrgId orgId = OrgId.ofRepoIdOrAny(Env.getAD_Org_ID(Env.getCtx()));

		final int limit = Services
				.get(ISysConfigBL.class)
				.getIntValue(SYSCONFIG_EMPTY_PRODUCTS_LIMIT, DEFAULT_EMPTY_PRODUCTS_LIMIT);

		final CacheKey cacheKey = new CacheKey(
				orgId,
				ProductFilterUtil.extractProductFilterVO(filters),
				limit);

		final ImmutableSet<ProductId> productIds = productFilterVOToProducts
				.getOrLoad(cacheKey, () -> retrieveProductsFor(cacheKey));

		return productIds;
	}

	private static ImmutableSet<ProductId> retrieveProductsFor(@NonNull final CacheKey cacheKey)
	{
		final OrgId orgId = cacheKey.getOrgId();
		final ProductFilterVO productFilterVO = cacheKey.getProductFilterVO();
		final int limit = cacheKey.getLimit();

		final IQueryFilter<I_M_Product> productQueryFilter = ProductFilterUtil.createProductQueryFilterOrNull(
				productFilterVO,
				false/* nullForEmptyFilterVO */);

		final IQueryBuilder<I_M_Product> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_Product.class)
				.addInArrayFilter(I_M_Product.COLUMNNAME_AD_Org_ID, orgId.getRepoId(), 0)
				.addEqualsFilter(I_M_Product.COLUMN_IsStocked, true)
				.filter(productQueryFilter)
				.orderBy(I_M_Product.COLUMN_Value);

		if (limit > 0)
		{
			queryBuilder.setLimit(limit);
		}

		return queryBuilder
				.create()
				.listIds(ProductId::ofRepoId);
	}

	private static boolean isEligibleRecordRef(final TableRecordReference recordRef)
	{
		final String tableName = recordRef.getTableName();
		return I_MD_Cockpit.Table_Name.equals(tableName)
				|| I_MD_Stock.Table_Name.equals(tableName);
	}

	private static boolean isRowMatching(final MaterialCockpitRow row, final TableRecordReferenceSet recordRefs)
	{
		return recordRefs.containsRecordIds(I_MD_Cockpit.Table_Name, row.getAllIncludedCockpitRecordIds())
				|| recordRefs.containsRecordIds(I_MD_Stock.Table_Name, row.getAllIncludedStockRecordIds());
	}
}

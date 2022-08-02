/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ui.web.material.cockpit;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
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
import de.metas.util.Services;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class MaterialCockpitRowsLoader
{
	private static final String SYSCONFIG_EMPTY_PRODUCTS_LIMIT = "de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.Limit";
	private static final int DEFAULT_EMPTY_PRODUCTS_LIMIT = 2000;
	
	private static final String SYSCONFIG_EMPTY_PRODUCTS_CACHESIZE = "de.metas.ui.web.material.cockpit.MaterialCockpitRowRepository.EmptyProducts.CacheSize";
	private static final int DEFAULT_EMPTY_PRODUCTS_CACHESIZE = 10;

	private final transient CCache<CacheKey, ImmutableSet<ProductId>> productFilterVOToProducts;
	private final MaterialCockpitFilters materialCockpitFilters;
	private final MaterialCockpitRowFactory materialCockpitRowFactory;

	public MaterialCockpitRowsLoader(
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
				.<CacheKey, ImmutableSet<ProductId>>builder()
				.tableName(I_M_Product.Table_Name)
				.cacheMapType(CCache.CacheMapType.LRU)
				.initialCapacity(cacheSize)
				.build();
	}
	
	public List<MaterialCockpitRow> getMaterialCockpitRows(
			@NonNull final DocumentFilterList filters,
			@NonNull final LocalDate date,
			final boolean includePerPlantDetailRows)
	{
		final List<I_MD_Cockpit> cockpitRecords = materialCockpitFilters
				.createQuery(filters)
				.list();

		final List<I_MD_Stock> stockRecords = StockFilters
				.createStockQueryFor(filters)
				.list();

		final MaterialCockpitRowFactory.CreateRowsRequest request = MaterialCockpitRowFactory.CreateRowsRequest
				.builder()
				.date(date)
				.productIdsToListEvenIfEmpty(retrieveRelevantProductIds(filters))
				.cockpitRecords(cockpitRecords)
				.stockRecords(stockRecords)
				.includePerPlantDetailRows(includePerPlantDetailRows)
				.build();
		return materialCockpitRowFactory.createRows(request);
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

		return productFilterVOToProducts
				.getOrLoad(cacheKey, () -> retrieveProductsFor(cacheKey));
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
			queryBuilder.setLimit(QueryLimit.ofInt(limit));
		}

		return queryBuilder
				.create()
				.listIds(ProductId::ofRepoId);
	}

	@Value
	private static class CacheKey
	{
		@NonNull
		OrgId orgId;

		@NonNull
		ProductFilterVO productFilterVO;

		int limit;
	}
}

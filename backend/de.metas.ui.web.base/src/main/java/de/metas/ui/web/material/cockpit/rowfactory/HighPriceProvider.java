/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.ui.web.material.cockpit.rowfactory;

import com.google.common.collect.ImmutableSet;
import de.metas.cache.CCache;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_purchase_prices_in_stock_uom_plv_v;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.GREATER;
import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.LESS_OR_EQUAL;

public class HighPriceProvider
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IProductBL productBL = Services.get(IProductBL.class);

	private final CCache<HighPriceRequest, HighPriceResponse> cache = CCache.<HighPriceRequest, HighPriceResponse> builder()
			.tableName(I_M_ProductPrice.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(5000)
			.build();

	public HighPriceResponse getHighestPrice(final HighPriceRequest request)
	{
		return cache.getOrLoad(request, this::computeHighestPrice);
	}

	private HighPriceResponse computeHighestPrice(final HighPriceRequest request)
	{
		return computeHighestPrices(ImmutableSet.of(request)).get(request);
	}

	private Map<HighPriceRequest, HighPriceResponse> computeHighestPrices(final Set<HighPriceRequest> requests)
	{
		final Map<HighPriceRequest, HighPriceResponse> resultMap = new HashMap<>();
		for (final HighPriceRequest request : requests)
		{
			final ProductId productId = ProductId.ofRepoId(request.getProductDescriptor().getProductId());
			final I_M_Product productRecord = productBL.getById(productId);
			if (!productRecord.isActive() || !productRecord.isPurchased() || !productRecord.isDiscontinued())
			{
				resultMap.put(
						request,
						HighPriceResponse.builder()
								.maxPurchasePrice(null)
								.build());
			}

			final I_purchase_prices_in_stock_uom_plv_v record = queryBL.createQueryBuilder(I_purchase_prices_in_stock_uom_plv_v.class)
					.addEqualsFilter(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_M_Product_ID, productId)
					.addCompareFilter(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_ValidFrom, LESS_OR_EQUAL, request.getEvalDate())
					.addCompareFilter(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_ValidTo, GREATER, request.getEvalDate())
					.orderByDescending(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_ProductPriceInStockUOM)
					.create()
					.first(I_purchase_prices_in_stock_uom_plv_v.class);

			// Currency is taken from de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.CurrencyCode in View
			final BigDecimal price = record != null ? record.getProductPriceInStockUOM() : BigDecimal.ZERO;
			final CurrencyId currencyId = record != null ? CurrencyId.ofRepoIdOrNull(record.getC_Currency_ID()) : null;
			final HighPriceResponse response = HighPriceResponse.builder()
					.maxPurchasePrice(Money.ofOrNull(price, currencyId))
					.build();
			resultMap.put(request, response);

		}
		return resultMap;
	}

	public void warmUp(@NonNull final Set<ProductId> productIds, @NonNull final LocalDate date)
	{
		//TODO option to warm up with mass compute and result set product_id | ProductPriceInStockUOM ?
		final Set<HighPriceRequest> requests = productIds.stream().map((productId) -> toHighPriceRequest(productId, date)).collect(Collectors.toSet());
		cache.getAllOrLoad(requests, this::computeHighestPrices);
	}

	private HighPriceRequest toHighPriceRequest(@NonNull final ProductId productId, @NonNull final LocalDate date)
	{
		return HighPriceRequest.builder()
				.productDescriptor(ProductDescriptor.forProductAndAttributes(
						productId.getRepoId(),
						AttributesKey.NONE))
				.evalDate(date)
				.build();
	}




	@Value
	@Builder
	public static class HighPriceRequest
	{
		@NonNull
		ProductDescriptor productDescriptor;

		@NonNull
		LocalDate evalDate;
	}

	@Value
	@Builder
	public static class HighPriceResponse
	{
		@Nullable
		Money maxPurchasePrice;
	}
}

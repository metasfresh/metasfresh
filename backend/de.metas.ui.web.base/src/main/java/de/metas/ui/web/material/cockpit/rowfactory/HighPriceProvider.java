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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.cache.CCache;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HighPriceProvider
{
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private static final String SYSCFG_HIGHPRICEPROVIDER_ACTIVE = "de.metas.ui.web.material.cockpit.rowfactory.HighPriceProvider.isActive";

	private final CCache<HighPriceRequest, HighPriceResponse> cache = CCache.<HighPriceRequest, HighPriceResponse>builder()
			.tableName(I_M_ProductPrice.Table_Name)
			.additionalTableNameToResetFor(I_M_Product.Table_Name)
			.additionalTableNameToResetFor(I_M_PriceList.Table_Name)
			.additionalTableNameToResetFor(I_M_PriceList_Version.Table_Name)
			.cacheMapType(CCache.CacheMapType.LRU)
			.initialCapacity(5000)
			.build();

	public HighPriceResponse getHighestPrice(final HighPriceRequest request)
	{
		if (!sysConfigBL.getBooleanValue(SYSCFG_HIGHPRICEPROVIDER_ACTIVE, false))
		{
			return HighPriceResponse.builder().build();
		}
		return cache.getOrLoad(request, this::computeHighestPrice);
	}

	private HighPriceResponse computeHighestPrice(final HighPriceRequest request)
	{
		return computeHighestPrices(ImmutableSet.of(request)).get(request);
	}

	private Map<HighPriceRequest, HighPriceResponse> computeHighestPrices(final Set<HighPriceRequest> requests)
	{
		if (requests.isEmpty())
		{
			return ImmutableMap.of();
		}

		final LocalDate evalDate = CollectionUtils.extractSingleElement(requests, HighPriceRequest::getEvalDate);
		final ImmutableMap<ProductId, HighPriceRequest> requestsByProductId = Maps.uniqueIndex(requests, HighPriceRequest::getProductId);
		final ImmutableSet<ProductId> productIds = requestsByProductId.keySet();

		final ArrayList<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("SELECT M_Product_ID"
															+ ", max(ProductPriceInStockUOM) as ProductPriceInStockUOM"
															+ ", max(C_Currency_ID) as C_Currency_ID" // expect one single currency anyways
															+ " FROM purchase_prices_in_stock_uom_plv_v");

		sql.append(" WHERE validfrom <= ? AND validto > ?");
		sqlParams.add(evalDate);
		sqlParams.add(evalDate);

		sql.append(" AND ").append(DB.buildSqlList("M_Product_ID", productIds, sqlParams));

		sql.append(" GROUP BY M_Product_ID");

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final HashMap<HighPriceRequest, HighPriceResponse> results = new HashMap<>();
			while (rs.next())
			{
				final ProductId productId = ProductId.ofRepoId(rs.getInt("M_Product_ID"));
				final BigDecimal highPrice = rs.getBigDecimal("ProductPriceInStockUOM");
				final CurrencyId currencyId = CurrencyId.ofRepoId(rs.getInt("C_Currency_ID"));

				final HighPriceResponse response = HighPriceResponse.builder()
						.maxPurchasePrice(Money.of(highPrice, currencyId))
						.build();

				final HighPriceRequest request = requestsByProductId.get(productId);
				results.put(request, response);
			}

			requests.forEach(request -> results.computeIfAbsent(request, this::addEmptyResponse));

			return results;
		}
		catch (final Exception ex)
		{
			throw new DBException(ex, sql, sqlParams);
		}
		finally
		{
			DB.close(rs, pstmt);
		}
	}

	private HighPriceResponse addEmptyResponse(@NonNull final HighPriceRequest request)
	{
		return HighPriceResponse.builder().build();
	}

	public void warmUp(@NonNull final Set<ProductId> productIds, @NonNull final LocalDate date)
	{
		if (!sysConfigBL.getBooleanValue(SYSCFG_HIGHPRICEPROVIDER_ACTIVE, false))
		{
			return;
		}
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

		public ProductId getProductId()
		{
			return ProductId.ofRepoId(productDescriptor.getProductId());
		}
	}

	@Value
	@Builder
	public static class HighPriceResponse
	{
		@Nullable
		Money maxPurchasePrice;
	}
}

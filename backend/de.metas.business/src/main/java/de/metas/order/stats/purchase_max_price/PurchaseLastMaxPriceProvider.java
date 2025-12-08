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

package de.metas.order.stats.purchase_max_price;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.currency.CurrencyCode;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.product.ProductId;
import de.metas.util.StringUtils;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.DBException;
import org.adempiere.service.ISysConfigBL;
import org.compiere.util.DB;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PurchaseLastMaxPriceProvider
{
	private static final String SYSCONFIG_CurrencyCode = "de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.CurrencyCode";
	private static final String SYSCONFIG_LastDays = "de.metas.ui.web.material.cockpit.field.HighestPurchasePrice_AtDate.LastDays";
	private static final String SYSCONFIG_IsActive = "de.metas.ui.web.material.cockpit.rowfactory.HighPriceProvider.isActive";

	private final HashMap<PurchaseLastMaxPriceRequest, PurchaseLastMaxPriceResponse> cache = new HashMap<>();
	private final boolean isDisabled;
	private final CurrencyId currencyId;
	private final int lastDays;

	@Builder
	private PurchaseLastMaxPriceProvider(
			@NonNull final ISysConfigBL sysConfigBL,
			@NonNull final MoneyService moneyService)
	{
		this.isDisabled = !sysConfigBL.getBooleanValue(SYSCONFIG_IsActive, false);

		final CurrencyCode currencyCode = StringUtils.trimBlankToOptional(sysConfigBL.getValue(SYSCONFIG_CurrencyCode, (String)null)).map(CurrencyCode::ofThreeLetterCode).orElse(CurrencyCode.CHF);
		this.currencyId = moneyService.getCurrencyIdByCurrencyCode(currencyCode);

		this.lastDays = Math.max(sysConfigBL.getIntValue(SYSCONFIG_LastDays, 7), 0);
	}

	public void warmUp(@NonNull final Set<ProductId> productIds, @NonNull final LocalDate date)
	{
		if (productIds.isEmpty())
		{
			return;
		}

		if (isDisabled)
		{
			return;
		}

		CollectionUtils.getAllOrLoad(cache, PurchaseLastMaxPriceRequest.ofProductIds(productIds, date), this::computeNow);
	}

	public PurchaseLastMaxPriceResponse getPrice(final PurchaseLastMaxPriceRequest request)
	{
		if (isDisabled)
		{
			return PurchaseLastMaxPriceResponse.NONE;
		}

		return cache.computeIfAbsent(request, this::computeNow);
	}

	private PurchaseLastMaxPriceResponse computeNow(final PurchaseLastMaxPriceRequest request)
	{
		return computeNow(ImmutableSet.of(request)).get(request);
	}

	private Map<PurchaseLastMaxPriceRequest, PurchaseLastMaxPriceResponse> computeNow(final Set<PurchaseLastMaxPriceRequest> requests)
	{
		if (requests.isEmpty())
		{
			return ImmutableMap.of();
		}

		final LocalDate evalDate = CollectionUtils.extractSingleElement(requests, PurchaseLastMaxPriceRequest::getEvalDate);
		final ImmutableMap<ProductId, PurchaseLastMaxPriceRequest> requestsByProductId = Maps.uniqueIndex(requests, PurchaseLastMaxPriceRequest::getProductId);
		final ImmutableSet<ProductId> productIds = requestsByProductId.keySet();

		updateMaterializedView(productIds);

		final ArrayList<Object> sqlParams = new ArrayList<>();
		final StringBuilder sql = new StringBuilder("SELECT p.m_product_id"
				+ ", (SELECT MAX(v.max_price) FROM purchase_order_highestprice_per_day_mv v WHERE v.m_product_id = p.m_product_id AND v.date > ?::date - INTERVAL '" + lastDays + " DAYS' AND v.date <= ?::date) AS max_price_last_days"
				+ ", (SELECT v.max_price FROM purchase_order_highestprice_per_day_mv v WHERE v.m_product_id = p.m_product_id AND v.date <= ?::date ORDER BY v.date DESC LIMIT 1) AS last_price"
				+ " FROM m_product p"
				+ " WHERE true");
		sqlParams.add(evalDate);
		sqlParams.add(evalDate);
		sqlParams.add(evalDate);

		sql.append(" AND ").append(DB.buildSqlList("p.M_Product_ID", productIds, sqlParams));

		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			pstmt = DB.prepareStatement(sql.toString(), ITrx.TRXNAME_ThreadInherited);
			DB.setParameters(pstmt, sqlParams);
			rs = pstmt.executeQuery();

			final HashMap<PurchaseLastMaxPriceRequest, PurchaseLastMaxPriceResponse> results = new HashMap<>();
			while (rs.next())
			{
				final ProductId productId = ProductId.ofRepoId(rs.getInt("m_product_id"));

				Money maxPurchasePrice = retrieveMoney(rs, "max_price_last_days");
				if (maxPurchasePrice == null)
				{
					maxPurchasePrice = retrieveMoney(rs, "last_price");
				}

				final PurchaseLastMaxPriceResponse response = PurchaseLastMaxPriceResponse.builder()
						.maxPurchasePrice(maxPurchasePrice)
						.build();

				final PurchaseLastMaxPriceRequest request = requestsByProductId.get(productId);
				results.put(request, response);
			}

			requests.forEach(request -> results.computeIfAbsent(request, k -> PurchaseLastMaxPriceResponse.NONE));

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

	@Nullable
	private Money retrieveMoney(@NonNull final ResultSet rs, @NonNull String columnName) throws SQLException
	{
		final BigDecimal value = rs.getBigDecimal(columnName);
		return value != null ? Money.of(value, currencyId) : null;
	}

	private void updateMaterializedView(@NonNull final Set<ProductId> productIds)
	{
		if (productIds.isEmpty())
		{
			return;
		}

		final ArrayList<Object> sqlParams = new ArrayList<>();
		final String sql = "SELECT purchase_order_highestprice_per_day_mv$update(p_m_product_ids := " + DB.TO_ARRAY(productIds, sqlParams) + ")";
		DB.executeFunctionCallEx(ITrx.TRXNAME_ThreadInherited, sql, sqlParams.toArray());
	}
}

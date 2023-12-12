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
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.material.cockpit.model.I_MD_Cockpit;
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
import org.adempiere.ad.dao.IQueryBL;
import org.compiere.model.I_purchase_prices_in_stock_uom_plv_v;
import org.compiere.util.Env;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.GREATER;
import static org.adempiere.ad.dao.impl.CompareQueryFilter.Operator.LESS_OR_EQUAL;

public class HighPriceProvider
{
	private final IAcctSchemaDAO acctSchemaDAO = Services.get(IAcctSchemaDAO.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final HashMap<HighPriceRequest, HighPriceResponse> cache = new HashMap<>();

	public HighPriceResponse getHighestPrice(final HighPriceRequest request)
	{
		return cache.computeIfAbsent(request, this::computeHighestPrice);
	}

	private HighPriceResponse computeHighestPrice(final HighPriceRequest request)
	{
		return computeHighestPrices(ImmutableSet.of(request)).get(request);
	}

	private Map<HighPriceRequest, HighPriceResponse> computeHighestPrices(final Set<HighPriceRequest> requests)
	{
		final CurrencyId currencyId = acctSchemaDAO.getByClientAndOrg(Env.getCtx()).getCurrencyId();

		final Map<HighPriceRequest, HighPriceResponse> resultMap = new HashMap<>();
		for (final HighPriceRequest request : requests)
		{
			final ProductId productId = ProductId.ofRepoId(request.getProductDescriptor().getProductId());
			final I_purchase_prices_in_stock_uom_plv_v record = queryBL.createQueryBuilder(I_purchase_prices_in_stock_uom_plv_v.class)
					.addEqualsFilter(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_M_Product_ID, productId)
					.addEqualsFilter(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_C_Currency_ID, currencyId)
					.addCompareFilter(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_ValidFrom, LESS_OR_EQUAL, request.getEvalDate())
					.addCompareFilter(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_ValidTo, GREATER, request.getEvalDate())
					.orderByDescending(I_purchase_prices_in_stock_uom_plv_v.COLUMNNAME_ProductPriceInStockUOM)
					.create()
					.firstOnlyOrNull(I_purchase_prices_in_stock_uom_plv_v.class);

			final BigDecimal price = record != null ? record.getProductPriceInStockUOM() : BigDecimal.ZERO;
			final HighPriceResponse response = HighPriceResponse.builder()
					.maxPurchasePrice(Money.of(price, currencyId))
					.build();
			resultMap.put(request, response);

		}
		return resultMap;
	}

	public void warmUp(final List<I_MD_Cockpit> cockpitRecords)
	{
		final Set<HighPriceRequest> requests = cockpitRecords.stream().map(HighPriceProvider::toHighPriceRequest).collect(Collectors.toSet());
		CollectionUtils.getAllOrLoad(cache, requests, this::computeHighestPrices);
	}

	private static HighPriceRequest toHighPriceRequest(final I_MD_Cockpit record)
	{
		return HighPriceRequest.builder()
				.productDescriptor(ProductDescriptor.forProductAndAttributes(
						record.getM_Product_ID(),
						AttributesKey.ofString(record.getAttributesKey())))
				.evalDate(record.getDateGeneral().toLocalDateTime().toLocalDate())
				.build();
	}


	@Value
	@Builder
	public static class HighPriceRequest
	{
		@NonNull ProductDescriptor productDescriptor;
		@NonNull LocalDate evalDate;
	}

	@Value
	@Builder
	public static class HighPriceResponse
	{
		@NonNull Money maxPurchasePrice;
	}
}

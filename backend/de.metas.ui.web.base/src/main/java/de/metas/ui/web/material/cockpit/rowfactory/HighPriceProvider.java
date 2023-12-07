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
import de.metas.material.cockpit.model.I_MD_Cockpit;
import de.metas.material.event.commons.AttributesKey;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.uom.UomId;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class HighPriceProvider
{
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
		// TODO mass compute!!!

		final Map<HighPriceRequest, HighPriceResponse> resultMap = new HashMap<>();
		for (final HighPriceRequest request : requests)
		{
			final HighPriceResponse response = HighPriceResponse.builder()
					.maxPurchasePrice(ProductPrice.builder()
											  .productId(request.getProductId())
											  .money(Money.of(5, CurrencyId.ofRepoId(318)))
											  .uomId(UomId.EACH)
											  .build())
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
				.productId(ProductId.ofRepoId(record.getM_Product_ID()))
				.evalDate(record.getDateGeneral().toInstant())
				.attributesKey(AttributesKey.ofString(record.getAttributesKey()))
				.build();
	}


	@Value
	@Builder
	public static class HighPriceRequest
	{
		@NonNull ProductId productId;
		@Nullable AttributesKey attributesKey;
		@NonNull Instant evalDate;
	}

	@Value
	@Builder
	public static class HighPriceResponse
	{
		@NonNull ProductPrice maxPurchasePrice;
	}
}

package org.eevolution.costing;

import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Stream;

import java.util.Objects;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.product.ProductId;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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

@ToString
public class BOMCostPrice
{
	public static BOMCostPrice empty(@NonNull final ProductId productId)
	{
		return builder().productId(productId).build();
	}

	@Getter
	private final ProductId productId;
	private final HashMap<CostElementId, BOMCostElementPrice> pricesByElementId;

	@Builder
	private BOMCostPrice(
			@NonNull final ProductId productId,
			@NonNull @Singular final Collection<BOMCostElementPrice> costElementPrices)
	{
		this.productId = productId;
		pricesByElementId = costElementPrices
				.stream()
				.collect(GuavaCollectors.toHashMapByKey(BOMCostElementPrice::getCostElementId));
	}

	public Stream<CostElementId> streamCostElementIds()
	{
		return pricesByElementId.keySet().stream();
	}

	public BOMCostElementPrice getCostElementPriceOrNull(@NonNull final CostElementId costElementId)
	{
		return pricesByElementId.get(costElementId);
	}

	public void clearOwnCostPrice(@NonNull final CostElementId costElementId)
	{
		final BOMCostElementPrice elementCostPrice = getCostElementPriceOrNull(costElementId);
		if (elementCostPrice != null)
		{
			elementCostPrice.clearOwnCostPrice();
		}
	}

	public void setComponentsCostPrice(@NonNull final CostAmount costPrice, @NonNull final CostElementId costElementId)
	{
		pricesByElementId.computeIfAbsent(costElementId, k -> BOMCostElementPrice.zero(costElementId, costPrice.getCurrencyId()))
				.setComponentsCostPrice(costPrice);
	}

	public void clearComponentsCostPrice(@NonNull final CostElementId costElementId)
	{
		final BOMCostElementPrice elementCostPrice = getCostElementPriceOrNull(costElementId);
		if (elementCostPrice != null)
		{
			elementCostPrice.clearComponentsCostPrice();
		}
	}

	Collection<BOMCostElementPrice> getElementPrices()
	{
		return pricesByElementId.values();
	}

	<T extends RepoIdAware> Stream<T> streamIds(@NonNull final Class<T> idType)
	{
		return getElementPrices()
				.stream()
				.map(BOMCostElementPrice::getId)
				.filter(Objects::nonNull)
				.map(idType::cast);
	}
}

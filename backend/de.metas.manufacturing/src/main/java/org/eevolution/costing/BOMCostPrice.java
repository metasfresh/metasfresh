package org.eevolution.costing;

import com.google.common.collect.ImmutableList;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.RepoIdAware;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Objects;
import java.util.stream.Stream;

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

@EqualsAndHashCode
@ToString
public class BOMCostPrice
{
	@Getter
	private final ProductId productId;
	@Getter
	private final UomId uomId;
	private final HashMap<CostElementId, BOMCostElementPrice> pricesByElementId;

	@Builder
	private BOMCostPrice(
			@NonNull final ProductId productId,
			@NonNull final UomId uomId,
			@NonNull @Singular final Collection<BOMCostElementPrice> costElementPrices)
	{
		if (!costElementPrices.isEmpty())
		{
			final UomId priceUomId = BOMCostElementPrice.extractUniqueUomId(costElementPrices);
			if (!UomId.equals(uomId, priceUomId))
			{
				throw new AdempiereException("Expected " + uomId + " to " + costElementPrices);
			}
		}
		this.productId = productId;
		this.uomId = uomId;
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

	public void setComponentsCostPrice(
			@NonNull final CostAmount costPrice,
			@NonNull final CostElementId costElementId)
	{
		pricesByElementId.computeIfAbsent(costElementId, k -> BOMCostElementPrice.zero(costElementId, costPrice.getCurrencyId(), uomId))
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

	ImmutableList<BOMCostElementPrice> getElementPrices()
	{
		return ImmutableList.copyOf(pricesByElementId.values());
	}

	<T extends RepoIdAware> Stream<T> streamIds(@NonNull final Class<T> idType)
	{
		return getElementPrices()
				.stream()
				.map(elementCostPrice -> elementCostPrice.getId(idType))
				.filter(Objects::nonNull);
	}
}

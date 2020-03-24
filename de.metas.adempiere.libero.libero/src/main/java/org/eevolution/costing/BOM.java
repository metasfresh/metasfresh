package org.eevolution.costing;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;

import java.util.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyPrecision;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import de.metas.util.lang.RepoIdAware;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

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

/** BOM from costing point of view */
@Builder
@Value
public final class BOM
{
	@NonNull
	ProductId productId;
	@NonNull
	@Default
	final AttributeSetInstanceId asiId = AttributeSetInstanceId.NONE;

	@NonNull
	@Default
	final BigDecimal qty = BigDecimal.ONE;

	@NonNull
	@Singular
	ImmutableList<BOMLine> lines;

	@NonNull
	@Getter(AccessLevel.PACKAGE)
	final BOMCostPrice costPrice;

	private final CurrencyPrecision precision = CurrencyPrecision.ofInt(4); // FIXME: hardcoded precision

	public void rollupCosts()
	{
		final BOMCostPrice bomCostPrice = getCostPrice();

		for (final CostElementId costElementId : getCostElementIds())
		{
			final CostAmount componentsCostPrice = computeComponentsCostPrice(costElementId).orElse(null);
			final CostAmount componentsCostPriceWithoutCoProducts = distributeToCoProductBOMLines(componentsCostPrice, costElementId);
			if (componentsCostPriceWithoutCoProducts != null)
			{
				bomCostPrice.setComponentsCostPrice(componentsCostPriceWithoutCoProducts, costElementId);
			}
			else
			{
				bomCostPrice.clearComponentsCostPrice(costElementId);
			}
		}
	}

	public void clearBOMOwnCostPrice(@NonNull final CostElementId costElementId)
	{
		getCostPrice().clearOwnCostPrice(costElementId);
	}

	private Optional<CostAmount> computeComponentsCostPrice(@NonNull final CostElementId costElementId)
	{
		final Optional<CostAmount> componentsTotalAmt = getLines()
				.stream()
				.filter(BOMLine::isInboundBOMCosts)
				.map(bomLine -> bomLine.getCostAmountOrNull(costElementId))
				.filter(Objects::nonNull)
				.reduce(CostAmount::add);

		return componentsTotalAmt.map(amt -> amt.divide(qty, precision));
	}

	private CostAmount distributeToCoProductBOMLines(@Nullable CostAmount bomCostPrice, @NonNull final CostElementId costElementId)
	{
		CostAmount bomCostPriceWithoutCoProducts = bomCostPrice;

		for (final BOMLine bomLine : getLines())
		{
			if (!bomLine.isCoProduct())
			{
				continue;
			}

			if (bomCostPrice != null && !bomCostPrice.isZero())
			{
				final Percent costAllocationPerc = bomLine.getCoProductCostDistributionPercent();
				final CostAmount coProductCostPrice = bomCostPrice.multiply(costAllocationPerc, precision);

				bomLine.setComponentsCostPrice(coProductCostPrice, costElementId);

				bomCostPriceWithoutCoProducts = bomCostPriceWithoutCoProducts.subtract(coProductCostPrice);
			}
			else
			{
				bomLine.clearComponentsCostPrice(costElementId);
			}
		}

		return bomCostPriceWithoutCoProducts;
	}

	Stream<BOMCostPrice> streamCostPrices()
	{
		final Stream<BOMCostPrice> linesCostPrices = getLines().stream().map(BOMLine::getCostPrice);
		return Stream.concat(Stream.of(getCostPrice()), linesCostPrices);
	}

	private ImmutableSet<CostElementId> getCostElementIds()
	{
		return streamCostPrices()
				.flatMap(BOMCostPrice::streamCostElementIds)
				.distinct()
				.collect(ImmutableSet.toImmutableSet());
	}

	<T extends RepoIdAware> Set<T> getCostIds(@NonNull final Class<T> idType)
	{
		return streamCostPrices()
				.flatMap(bomCostPrice -> bomCostPrice.streamIds(idType))
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	public Set<ProductId> getProductIds()
	{
		final ImmutableSet.Builder<ProductId> productIds = ImmutableSet.builder();
		productIds.add(getProductId());
		getLines().forEach(bomLine -> productIds.add(bomLine.getComponentId()));
		return productIds.build();
	}
}

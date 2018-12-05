package org.eevolution.costing;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.Nullable;

import org.adempiere.mm.attributes.AttributeSetInstanceId;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
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
	@Singular
	ImmutableList<BOMLine> lines;

	@NonNull
	@Getter(AccessLevel.PACKAGE)
	final BOMCostPrice costPrice;

	public void rollupCosts()
	{
		final BOMCostPrice costPrice = getCostPrice();

		for (final CostElementId costElementId : getCostElementIds())
		{
			final CostAmount componentsCostPrice = computeComponentsCostPrice(costElementId).orElse(null);
			final CostAmount componentsCostPriceWithoutCoProducts = distributeToCoProductBOMLines(componentsCostPrice, costElementId);
			if (componentsCostPriceWithoutCoProducts != null)
			{
				costPrice.setComponentsCostPrice(componentsCostPriceWithoutCoProducts, costElementId);
			}
			else
			{
				costPrice.clearComponentsCostPrice(costElementId);
			}
		}
	}

	private Optional<CostAmount> computeComponentsCostPrice(@NonNull final CostElementId costElementId)
	{
		return getLines()
				.stream()
				.filter(BOMLine::isRelevantForBOMCosts)
				.map(bomLine -> bomLine.getCostAmountOrNull(costElementId))
				.filter(Predicates.notNull())
				.reduce(CostAmount::add);
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
				final Percent costAllocationPerc = bomLine.getCoProductCostAllocationPerc();
				final CostAmount coProductCostPrice = bomCostPrice.multiply(costAllocationPerc, 4); // FIXME: hardcoded precision

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

	Set<Integer> getCostRepoIds()
	{
		return streamCostPrices()
				.flatMap(BOMCostPrice::streamRepoIds)
				.filter(repoId -> repoId > 0)
				.collect(ImmutableSet.toImmutableSet());
	}
}

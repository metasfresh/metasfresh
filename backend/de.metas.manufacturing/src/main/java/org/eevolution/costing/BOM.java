package org.eevolution.costing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyPrecision;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import de.metas.util.lang.RepoIdAware;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
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

/**
 * BOM from costing point of view
 */
@Value
public class BOM
{
	@NonNull
	ProductId productId;

	@NonNull
	AttributeSetInstanceId asiId;

	@NonNull
	Quantity qty;

	@NonNull
	ImmutableList<BOMLine> lines;

	@NonNull
	@Getter(AccessLevel.PACKAGE)
	BOMCostPrice costPrice;

	CurrencyPrecision precision = CurrencyPrecision.ofInt(4); // FIXME: hardcoded precision

	@Builder
	private BOM(
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@NonNull final Quantity qty,
			@Singular @NonNull final ImmutableList<BOMLine> lines,
			@NonNull final BOMCostPrice costPrice)
	{
		if (!UomId.equals(qty.getUomId(), costPrice.getUomId()))
		{
			throw new AdempiereException("UOM not matching: " + qty + ", " + costPrice);
		}
		if (qty.signum() <= 0)
		{
			throw new AdempiereException("Qty of finished goods to produce shall be greater than zero");
		}

		this.productId = productId;
		this.asiId = asiId != null ? asiId : AttributeSetInstanceId.NONE;
		this.qty = qty;
		this.lines = lines;
		this.costPrice = costPrice;
	}

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

	@Nullable
	private CostAmount distributeToCoProductBOMLines(
			@Nullable final CostAmount bomCostPrice,
			@NonNull final CostElementId costElementId)
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
				// here we compute the percentage out of the issued product amount
				final CostAmount coProductCostPrice = bomCostPrice.multiply(costAllocationPerc, precision);

				bomLine.setComponentsCostPrice(coProductCostPrice, costElementId);

				bomCostPriceWithoutCoProducts = (bomCostPriceWithoutCoProducts // 0.9334
						.multiply(bomLine.getQtyIncludingScrap().add(this.getQty())) // *100.005 = 93.344667
						.subtract(coProductCostPrice.multiply(bomLine.getQtyIncludingScrap())))// -7.7816 = 85.5630
					 		.divide(this.getQty(), precision) // /75 = 1.14084
							.subtract(coProductCostPrice); // - 0.3112 = 0.8296


				// validation
				// co-product amount: 0.3112 * 25 = 7.78
				// main product amount: 0.8296 * 75 = 62.22
				// issued amount: 7 * 100 = 70 => the amounts fit


				//bomCostPriceWithoutCoProducts = bomCostPriceWithoutCoProducts.subtract(coProductCostPrice);
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

	public ImmutableList<BOMCostElementPrice> getElementPrices()
	{
		return getCostPrice().getElementPrices();
	}
}

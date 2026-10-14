package org.eevolution.costing;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.currency.CurrencyPrecision;
import de.metas.i18n.AdMessageKey;
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
import java.util.stream.Collectors;
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
	/**
	 * Σ(co-product distribution percents) &gt; 100% guard message. Shared with the PP_Order post-calculation
	 * guard ({@code PPOrderCosts.assertValidTotalCoProductDistributionPercent}); localized via AD_Message so a
	 * German user gets a German message. Params: {0} = the offending sum, {1} = the offending product(s).
	 * Backing migration: {@code 5825070_sys_AD_Message_CoProductCostDistributionPercentSum_ExceedsMax.sql}.
	 */
	static final AdMessageKey MSG_COPRODUCT_COST_DISTRIBUTION_PERCENT_SUM_EXCEEDS_MAX =
			AdMessageKey.of("de.metas.manufacturing.CoProductCostDistributionPercentSum_ExceedsMax");

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

	/**
	 * True when this BOM is the per-order Average/MAI cost rollup ({@code OrderBOMCostCalculatorRepository});
	 * false for the Standard-cost definitional rollup ({@code BatchProcessBOMCostCalculatorRepository}) and for
	 * a directly-built BOM. The Σp ≤ 100% guard is enforced during the rollup ONLY when this is false: on the
	 * per-order path the single rejection point is the PP_Order post-calculation guard
	 * ({@code PPOrderCosts.assertValidTotalCoProductDistributionPercent}, fired when each cost collector is
	 * costed), so enforcing here too would pre-empt it at the wrong point (order-cost creation) with the wrong
	 * message.
	 */
	boolean perOrderRollup;

	CurrencyPrecision precision = CurrencyPrecision.ofInt(4); // FIXME: hardcoded precision

	@Builder
	private BOM(
			@NonNull final ProductId productId,
			@Nullable final AttributeSetInstanceId asiId,
			@NonNull final Quantity qty,
			@Singular @NonNull final ImmutableList<BOMLine> lines,
			@NonNull final BOMCostPrice costPrice,
			final boolean perOrderRollup)
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
		this.perOrderRollup = perOrderRollup;
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
		if (!perOrderRollup)
		{
			assertValidTotalCoProductDistributionPercent();
		}

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

	/**
	 * Guards the co-product cost carve-out on the definitional (non-{@code perOrderRollup}) rollup only: the
	 * co-product BOM lines' {@code CoProductCostDistributionPercent} must not sum to more than 100%, else the
	 * carve-out in {@link #distributeToCoProductBOMLines} would subtract more than the whole BOM cost price and
	 * silently drive the main product's Standard cost negative.
	 * <p>
	 * Uses the same arithmetic and AD_Message as the PP_Order post-calculation guard
	 * {@code PPOrderCosts.assertValidTotalCoProductDistributionPercent} — strictly {@code > 100%} is rejected,
	 * exactly {@code 100.00%} is allowed, null / non-positive percents are ignored — but is deliberately skipped
	 * on the per-order path (see {@link #perOrderRollup}), where that post-calculation guard is the single
	 * rejection point.
	 */
	private void assertValidTotalCoProductDistributionPercent()
	{
		Percent totalCoProductDistributionPercent = Percent.ZERO;
		for (final BOMLine bomLine : getLines())
		{
			if (!bomLine.isCoProduct())
			{
				continue;
			}

			final Percent percent = bomLine.getCoProductCostDistributionPercent();
			if (percent != null && percent.signum() > 0)
			{
				totalCoProductDistributionPercent = totalCoProductDistributionPercent.add(percent);
			}
		}

		if (totalCoProductDistributionPercent.isOverOneHundred())
		{
			final String offendingCoProductIds = getLines().stream()
					.filter(BOMLine::isCoProduct)
					.filter(bomLine -> {
						final Percent percent = bomLine.getCoProductCostDistributionPercent();
						return percent != null && percent.signum() > 0;
					})
					.map(bomLine -> String.valueOf(bomLine.getComponentId().getRepoId()))
					.collect(Collectors.joining(", "));
			throw new AdempiereException(MSG_COPRODUCT_COST_DISTRIBUTION_PERCENT_SUM_EXCEEDS_MAX,
					totalCoProductDistributionPercent, offendingCoProductIds);
		}
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

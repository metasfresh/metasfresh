package org.eevolution.api;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchemaId;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostingMethod;
import de.metas.currency.CurrencyPrecision;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverter;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

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
@EqualsAndHashCode
public final class PPOrderCosts
{
	/**
	 * The costing methods under which the manual co-product {@code CoProductFixedCostPrice} valuation is supported.
	 * Both legs of the feature (the post-calc relief here and the co-product receipt valuation in the costing-method
	 * handlers) exist ONLY for these two methods; a fixed price set under any other method drives the two legs out of
	 * sync (leg A relieves, leg B does not), so it is rejected at posting rather than silently applied.
	 */
	private static final ImmutableSet<CostingMethod> COSTING_METHODS_SUPPORTING_FIXED_CO_PRODUCT_PRICE =
			ImmutableSet.of(CostingMethod.AveragePO, CostingMethod.MovingAverageInvoice);

	@Getter
	private final PPOrderId orderId;
	private final HashMap<CostSegmentAndElement, PPOrderCost> costs;

	@Builder
	private PPOrderCosts(
			@NonNull final PPOrderId orderId,
			@NonNull @Singular final Collection<PPOrderCost> costs)
	{
		Check.assumeNotEmpty(costs, "costs shall not be empty for {}", orderId);

		this.orderId = orderId;

		this.costs = costs.stream()
				.collect(GuavaCollectors.toHashMapByKeyFailOnDuplicates(PPOrderCost::getCostSegmentAndElement));
	}

	public Collection<PPOrderCost> toCollection()
	{
		return costs.values();
	}

	public List<PPOrderCost> getByProductAndCostElements(
			@NonNull final ProductId productId,
			@NonNull final Set<CostElementId> costElementIds)
	{
		Check.assumeNotEmpty(costElementIds, "costElementIds is not empty");

		return filterAndList(PPOrderCostFilter.builder()
				.productId(productId)
				.costElementIds(costElementIds)
				.build());
	}

	public Optional<CostPrice> getPriceByCostSegmentAndElement(final CostSegmentAndElement costSegmentAndElement)
	{
		return getByCostSegmentAndElement(costSegmentAndElement)
				.map(PPOrderCost::getPrice);
	}

	public Optional<PPOrderCost> getByCostSegmentAndElement(final CostSegmentAndElement costSegmentAndElement)
	{
		final PPOrderCost cost = costs.get(costSegmentAndElement);
		if (cost == null)
		{
			return Optional.empty();
		}
		else
		{
			return Optional.of(cost);
		}
	}

	private List<PPOrderCost> filterAndList(@NonNull final Predicate<PPOrderCost> filter)
	{
		return costs.values()
				.stream()
				.filter(filter)
				.collect(ImmutableList.toImmutableList());
	}

	public void removeByProductsAndCostElements(
			final Set<ProductId> productIds,
			final Set<CostElementId> costElementIds)
	{
		costs.values().removeIf(cost -> productIds.contains(cost.getProductId()) && costElementIds.contains(cost.getCostElementId()));
	}

	public void addCosts(final Collection<PPOrderCost> costsToAdd)
	{
		for (final PPOrderCost costToAdd : costsToAdd)
		{
			costs.merge(costToAdd.getCostSegmentAndElement(), costToAdd, (oldCost, costToAdd2) -> {
				throw new AdempiereException("Cannot add " + costToAdd2 + " on " + costToAdd2.getCostSegmentAndElement() + " because we already have " + oldCost);
			});
		}
	}

	public void accumulateInboundCostAmount(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		changeExistingCost(costSegmentAndElement, cost -> cost.addingAccumulatedAmountAndQty(amt, qty, uomConverter));
	}

	/**
	 * Discharges {@code amt} onto the main-product line: the accumulated amount moves by {@code +amt} while
	 * the accumulated qty is left untouched, so the line records value leaving the order without recording a
	 * further movement of goods.
	 */
	public void dischargeOntoMainProduct(
			@NonNull final PPOrderCost mainProductCost,
			@NonNull final CostAmount amt,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		// accumulateOutboundCostAmount negates again, so passing -amt moves the accumulated amount by +amt.
		accumulateOutboundCostAmount(
				mainProductCost.getCostSegmentAndElement(),
				amt.negate(),
				mainProductCost.getAccumulatedQty().toZero(),
				uomConverter);
	}

	public void accumulateOutboundCostAmount(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CostAmount amt,
			@NonNull final Quantity qty,
			@NonNull final QuantityUOMConverter uomConverter)
	{
		changeExistingCost(costSegmentAndElement, cost -> cost.subtractingAccumulatedAmountAndQty(amt, qty, uomConverter));
	}

	/**
	 * Sets the current-cost price snapshot on the existing cost row for the given segment.
	 * Callers must ensure {@code newPrice}'s UOM matches the existing row's {@code accumulatedQty} UOM; this
	 * is currently true because they derive from the product's stocking UOM at every current call site
	 * ({@code ManufacturingAveragePOCostingMethodHandler}, {@code ManufacturingMovingAverageInvoiceCostingMethodHandler}
	 * and {@code ManufacturingLastPOCostingMethodHandler}),
	 * but it is NOT structurally enforced by
	 * {@link CostSegmentAndElement}. The underlying {@code withPrice} rebuild throws {@code AdempiereException}
	 * if {@code newPrice}'s UOM diverges from the row's {@code accumulatedQty} UOM.
	 */
	public void updatePriceForCostSegmentAndElement(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CostPrice newPrice)
	{
		changeExistingCost(costSegmentAndElement, cost -> cost.withPrice(newPrice));
	}

	private void changeExistingCost(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final UnaryOperator<PPOrderCost> mapper)
	{
		costs.compute(costSegmentAndElement, (k, cost) -> {
			if (cost == null)
			{
				throw new AdempiereException("No order costs found for " + costSegmentAndElement);
			}
			else
			{
				return mapper.apply(cost);
			}
		});
	}

	@Value
	@Builder
	private static class PPOrderCostFilter implements Predicate<PPOrderCost>
	{
		ProductId productId;

		@NonNull
		@Singular
		ImmutableSet<CostElementId> costElementIds;

		@Override
		public boolean test(final PPOrderCost cost)
		{
			return (productId == null || productId.equals(cost.getProductId()))
					&& (costElementIds.isEmpty() || costElementIds.contains(cost.getCostElementId()));
		}
	}

	public void updatePostCalculationAmounts(
			final CurrencyPrecision precision,
			@NonNull final CostingMethod costingMethod,
			@NonNull final FixedCostPriceProvider fixedCostPriceProvider)
	{
		for (final CostElementId costElementId : getCostElementIds())
		{
			updatePostCalculationAmountsForCostElement(precision, costElementId, costingMethod, fixedCostPriceProvider);
		}
	}

	public void updatePostCalculationAmountsForCostElement(
			final CurrencyPrecision precision,
			final CostElementId costElementId,
			@NonNull final CostingMethod costingMethod,
			@NonNull final FixedCostPriceProvider fixedCostPriceProvider)
	{
		final List<PPOrderCost> costs = filterAndList(PPOrderCostFilter.builder()
				.costElementId(costElementId)
				.build());

		final List<PPOrderCost> inboundCosts = costs.stream()
				.filter(PPOrderCost::isInboundCost)
				.collect(ImmutableList.toImmutableList());

		final PPOrderCost mainProductCost = costs.stream()
				.filter(PPOrderCost::isMainProduct)
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("Single main product cost could not be found in " + costs)));

		final ImmutableList<PPOrderCost> coProductCosts = costs.stream()
				.filter(PPOrderCost::isCoProduct)
				.collect(ImmutableList.toImmutableList());

		//
		// Update inbound costs and calculate total inbound costs
		inboundCosts.forEach(PPOrderCost::setPostCalculationAmountAsAccumulatedAmt);
		final CostAmount totalInboundCostAmount = inboundCosts.stream()
				.map(PPOrderCost::getPostCalculationAmount)
				.reduce(CostAmount::add)
				.orElseThrow(() -> new AdempiereException("No inbound costs found in " + costs));

		//
		// Update co-product costs and calculate total co-product costs.
		// A co-product whose product carries a manual CoProductFixedCostPrice is valued at fixedPrice x received-qty
		// (so the main product is relieved by the remainder); a blank price keeps today's qty-distribution behaviour.
		final List<ProductId> fixedPricedCoProductIds = new ArrayList<>();
		for (final PPOrderCost coProductCost : coProductCosts)
		{
			final BigDecimal fixedCostPrice = fixedCostPriceProvider.getFixedCostPrice(coProductCost.getProductId()).orElse(null);
			final CostAmount coProductAmount;
			if (fixedCostPrice != null)
			{
				// C3 method-gate: the ORDER's costing method (the acct schema's — passed in as costingMethod, NOT
				// the invoking handler's; see the handlers' getAcctSchemaCostingMethod) must be Average PO or Moving
				// Average Invoice. Under any other method leg B never books the co-product receipt at the fixed price,
				// so applying the relief would silently unbalance the order — reject at posting instead. Gated on the
				// co-product having actually been received (accumulatedQty != 0 — the point the fixed price is applied),
				// mirroring the qty-driven negative-main and by-product guards so the reject binds at the co-product
				// receipt rather than at an earlier component issue.
				if (!coProductCost.getAccumulatedQty().isZero()
						&& !COSTING_METHODS_SUPPORTING_FIXED_CO_PRODUCT_PRICE.contains(costingMethod))
				{
					throw new AdempiereException("Co-product fixed cost price for " + describeProducts(fixedCostPriceProvider, ImmutableList.of(coProductCost.getProductId()))
							+ " is set, but the fixed-price co-product valuation is supported only under the Average PO and Moving Average Invoice costing methods, not " + costingMethod);
				}
				coProductAmount = CostAmount.of(
								fixedCostPrice.multiply(coProductCost.getAccumulatedQty().toBigDecimal()),
								totalInboundCostAmount.getCurrencyId())
						.roundToPrecisionIfNeeded(precision);
				fixedPricedCoProductIds.add(coProductCost.getProductId());
			}
			else
			{
				coProductAmount = computeBlankCoProductAmount(totalInboundCostAmount, coProductCost, precision);
			}
			coProductCost.setPostCalculationAmount(coProductAmount);
		}
		final CostAmount totalCoProductsCostAmount = coProductCosts.stream()
				.map(PPOrderCost::getPostCalculationAmount)
				.reduce(CostAmount::add)
				.orElseGet(totalInboundCostAmount::toZero);

		//
		// Guard: the co-products must not consume more than the order's input cost pool, which would drive the
		// main product's value negative. Reject here, BEFORE persisting the negative main-product amount below.
		final CostAmount mainProductAmount = totalInboundCostAmount.subtract(totalCoProductsCostAmount);
		if (mainProductAmount.signum() < 0)
		{
			final List<ProductId> offendingCoProductIds = !fixedPricedCoProductIds.isEmpty()
					? fixedPricedCoProductIds
					: coProductCosts.stream().map(PPOrderCost::getProductId).collect(Collectors.toList());
			throw new AdempiereException("Co-product fixed cost price for " + describeProducts(fixedCostPriceProvider, offendingCoProductIds)
					+ " values the co-products at " + totalCoProductsCostAmount
					+ ", which exceeds the production order's input cost pool of " + totalInboundCostAmount
					+ " and would drive the main product negative");
		}

		//
		// Clear by-product costs.
		// C2 by-product guard: the fixed-price valuation applies to co-product (CP) lines ONLY, symmetric with
		// leg B. Once a by-product line whose product carries a fixed price is actually RECEIVED (accumulatedQty
		// != 0), its receipt would capitalize at the fixed price (leg B) while this post-calc zeroes it (leg A) —
		// a silent divergence — so reject at posting instead. Mirrors the qty-driven negative-main guard above:
		// the reject binds at the point the fixed price would actually be applied, not at an earlier component
		// issue when the by-product has not been produced yet. Runs BEFORE the main-product mutation below so a
		// reject throws before any post-calculation amount is changed, like the two guards above.
		costs.stream()
				.filter(PPOrderCost::isByProduct)
				.forEach(byProductCost -> {
					if (!byProductCost.getAccumulatedQty().isZero()
							&& fixedCostPriceProvider.getFixedCostPrice(byProductCost.getProductId()).isPresent())
					{
						throw new AdempiereException("Co-product fixed cost price for " + describeProducts(fixedCostPriceProvider, ImmutableList.of(byProductCost.getProductId()))
								+ " is set on a by-product (BY) line, but the fixed-price valuation is supported for co-product (CP) lines only, not by-products");
					}
					byProductCost.setPostCalculationAmountAsZero();
				});

		//
		// Update main product cost
		mainProductCost.setPostCalculationAmount(mainProductAmount);
	}

	/**
	 * The amount a blank-fixed-price co-product receipt must capitalize to inventory: the co-product's share of
	 * the order's inbound cost pool (qty-distribution) for its cost element - the IDENTICAL amount
	 * {@link #updatePostCalculationAmountsForCostElement} books as the co-product's post-calculation relief
	 * (leg A). A costing-method handler values the co-product receipt (leg B) at this amount so both legs book
	 * the same value, cost is conserved and the order's WIP clears.
	 */
	public CostAmount getBlankCoProductReceiptAmount(
			@NonNull final CostSegmentAndElement costSegmentAndElement,
			@NonNull final CurrencyPrecision precision)
	{
		final PPOrderCost coProductCost = getByCostSegmentAndElement(costSegmentAndElement)
				.orElseThrow(() -> new AdempiereException("No co-product cost row found for " + costSegmentAndElement));
		final CostAmount totalInboundCostAmount = getTotalInboundCostAmount(coProductCost.getCostElementId());
		return computeBlankCoProductAmount(totalInboundCostAmount, coProductCost, precision);
	}

	private CostAmount getTotalInboundCostAmount(@NonNull final CostElementId costElementId)
	{
		final List<PPOrderCost> costsForElement = filterAndList(PPOrderCostFilter.builder()
				.costElementId(costElementId)
				.build());
		return costsForElement.stream()
				.filter(PPOrderCost::isInboundCost)
				.map(PPOrderCost::getAccumulatedAmount)
				.reduce(CostAmount::add)
				.orElseThrow(() -> new AdempiereException("No inbound costs found in " + costsForElement));
	}

	/**
	 * The blank-fixed-price co-product's qty-distribution share of the order's inbound cost pool:
	 * {@code totalInbound × coProductCostDistributionPercent}. The distribution percent is nullable (the DAO
	 * leaves it unset, especially under Moving Average Invoice), so a null / non-positive percent yields a zero
	 * share - nothing to capitalise - rather than an NPE.
	 */
	private static CostAmount computeBlankCoProductAmount(
			@NonNull final CostAmount totalInboundCostAmount,
			@NonNull final PPOrderCost coProductCost,
			@NonNull final CurrencyPrecision precision)
	{
		final Percent distributionPercent = coProductCost.getCoProductCostDistributionPercent();
		if (distributionPercent == null || distributionPercent.signum() <= 0)
		{
			return totalInboundCostAmount.toZero();
		}
		return totalInboundCostAmount.multiply(distributionPercent, precision);
	}

	/**
	 * The order's not-yet-discharged WIP cost for the given accounting schema and cost element.
	 * Positive =&gt; more was issued into the order than was received out of it.
	 *
	 * @return {@code null} if there is no main-product cost row for that schema and cost element - the costing
	 * engine explodes the client's cost elements against the schema being posted, so a handler can be asked for
	 * a costing method this order has no rows for.
	 */
	@Nullable
	public CostAmount getResidualCost(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId)
	{
		final PPOrderCost mainProductCost = getMainProductCostOrNull(acctSchemaId, costElementId);
		if (mainProductCost == null)
		{
			return null;
		}

		return mainProductCost.getResidualCost();
	}

	/**
	 * When the inbound rows sum to zero the order received value out of no input cost, so the post-calculation
	 * amount stays zero and the residual degenerates into minus the whole receipt - not a cost difference.
	 */
	public boolean hasInboundCosts(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId)
	{
		return costs.values().stream()
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> costElementId.equals(cost.getCostElementId()))
				.filter(PPOrderCost::isInboundCost)
				.map(PPOrderCost::getAccumulatedAmount)
				.reduce(CostAmount::add)
				.map(totalInboundAmount -> !totalInboundAmount.isZero())
				.orElse(false);
	}

	/** @return the single main-product cost row for the given schema and cost element, or {@code null}. */
	@Nullable
	public PPOrderCost getMainProductCostOrNull(
			@NonNull final AcctSchemaId acctSchemaId,
			@NonNull final CostElementId costElementId)
	{
		final List<PPOrderCost> mainProductCosts = costs.values().stream()
				.filter(cost -> acctSchemaId.equals(cost.getAcctSchemaId()))
				.filter(cost -> costElementId.equals(cost.getCostElementId()))
				.filter(PPOrderCost::isMainProduct)
				.collect(ImmutableList.toImmutableList());

		if (mainProductCosts.size() > 1)
		{
			throw new AdempiereException("Expected at most one main-product PP_Order_Cost row for acctSchema=" + acctSchemaId
					+ ", costElement=" + costElementId + " in " + this);
		}

		return mainProductCosts.isEmpty() ? null : mainProductCosts.get(0);
	}

	private Set<CostElementId> getCostElementIds()
	{
		return costs.keySet()
				.stream()
				.map(CostSegmentAndElement::getCostElementId)
				.collect(ImmutableSet.toImmutableSet());
	}

	/** @return the given products' names (comma-separated), for the negative-main guard message. */
	private static String describeProducts(
			@NonNull final FixedCostPriceProvider fixedCostPriceProvider,
			@NonNull final List<ProductId> productIds)
	{
		return productIds.stream()
				.map(fixedCostPriceProvider::getProductName)
				.collect(Collectors.joining(", "));
	}

}

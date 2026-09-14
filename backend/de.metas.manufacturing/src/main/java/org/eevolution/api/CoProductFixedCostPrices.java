package org.eevolution.api;

import de.metas.product.IProductDAO;
import de.metas.product.ProductId;
import lombok.NonNull;

import java.math.BigDecimal;
import java.util.Optional;

/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2026 metas GmbH
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
 * Reads a co-product's manual {@code M_Product.CoProductFixedCostPrice} LIVE and shares that single read
 * across the two valuation legs so that both produce the identical co-product amount (cost conservation):
 * <ul>
 *     <li>leg A — the method-agnostic post-calculation relief in
 *     {@link PPOrderCosts#updatePostCalculationAmountsForCostElement}, and</li>
 *     <li>leg B — the co-product receipt valuation in the Average-PO / Moving-Average-Invoice costing-method
 *     handlers (see {@code de.metas.costing.methods}).</li>
 * </ul>
 * <p>
 * A blank field (null / not-positive) means the product has NOT opted into fixed-price valuation, so the
 * co-product keeps today's qty-distribution ({@code coProductCostDistributionPercent}) behaviour. The product
 * master is read through its owning repository ({@link IProductDAO#getById(ProductId)}), which is fail-loud: an
 * orphaned co-product FK surfaces as an exception rather than being silently swallowed into the distribution
 * path — in production every co-product {@code PP_Order_Cost} references a real, FK-backed product.
 * <p>
 * The repository is passed in by the caller (leg A's {@link PPOrderCosts} is a plain value object and leg B's
 * costing handlers are Spring beans) so this helper holds no service state of its own.
 * <p>
 * The read is deliberately live (not snapshotted per order): the computed result is what gets frozen in
 * {@code PP_Order_Cost} / {@code Fact_Acct}, never the input price itself.
 */
public final class CoProductFixedCostPrices
{
	private CoProductFixedCostPrices()
	{
	}

	/**
	 * @return the product's manual fixed co-product cost price, or {@link Optional#empty()} when the field is
	 * blank (null / not-positive) — i.e. the product has not opted into fixed-price valuation. Throws (via
	 * {@link IProductDAO#getById(ProductId)}) when the product master cannot be resolved — an orphaned
	 * co-product FK must surface, never fall back to the distribution path.
	 */
	public static Optional<BigDecimal> getFixedCostPrice(
			@NonNull final IProductDAO productDAO,
			@NonNull final ProductId productId)
	{
		return Optional.ofNullable(productDAO.getById(productId).getCoProductFixedCostPrice())
				.filter(fixedCostPrice -> fixedCostPrice.signum() > 0);
	}

	/**
	 * @return the product's name. Used to name the offending co-product(s) in the negative-main-product guard
	 * message. Throws (via {@link IProductDAO#getById(ProductId)}) when the product master cannot be resolved.
	 */
	public static String getProductName(
			@NonNull final IProductDAO productDAO,
			@NonNull final ProductId productId)
	{
		return productDAO.getById(productId).getName();
	}
}

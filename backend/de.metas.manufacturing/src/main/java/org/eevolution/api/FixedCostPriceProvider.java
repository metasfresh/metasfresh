package org.eevolution.api;

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
 * The narrow seam through which the co-product valuation legs obtain a co-product's manual fixed cost price,
 * shared by both legs so they book the identical co-product amount (cost conservation):
 * <ul>
 *     <li>leg A — the method-agnostic post-calculation relief in
 *     {@link PPOrderCosts#updatePostCalculationAmountsForCostElement}, and</li>
 *     <li>leg B — the co-product receipt valuation in the Average-PO / Moving-Average-Invoice costing-method
 *     handlers (see {@code de.metas.costing.methods}).</li>
 * </ul>
 * The costing code depends only on this interface, never on the product repository: the actual
 * {@code M_Product.CoProductFixedCostPrice} lookup lives in the service-layer implementation
 * ({@link org.eevolution.api.impl.PPOrderCostBL}, reached as an {@link IPPOrderCostBL}).
 */
@FunctionalInterface
public interface FixedCostPriceProvider
{
	/**
	 * @return the co-product product's manual fixed cost price, or {@link Optional#empty()} when the field is
	 * blank (null / not-positive) — i.e. the product has not opted into fixed-price valuation, so the co-product
	 * keeps today's qty-distribution behaviour.
	 */
	Optional<BigDecimal> getFixedCostPrice(@NonNull ProductId productId);

	/**
	 * The product's name — used ONLY to name the offending co-product(s) in the negative-main-product guard
	 * message. Defaults to the repo-id so a lambda provider need supply nothing; the product-master-backed
	 * implementation overrides it with the real name.
	 */
	default String getProductName(@NonNull final ProductId productId)
	{
		return String.valueOf(productId.getRepoId());
	}
}

package de.metas.product.model.interceptor;

/*
 * #%L
 * de.metas.business
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.business.BusinessTestHelper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * Tests {@link M_Product#beforeSave(I_M_Product)} — the per-product data-entry range guard on
 * {@code M_Product.CoProductCostDistributionPercent}: only {@code [0, 100]} is legal (inclusive on
 * both ends); blank/NULL stays legal.
 * <p>
 * This is distinct from the per-order {@code Σp ≤ 100%} guard ({@code PPOrderCosts}, at
 * post-calculation), which bounds the sum of percentages across an order at consumption time —
 * this guard bounds a single product's value at data-entry time.
 */
public class M_ProductTest
{
	private M_Product interceptor;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		interceptor = new M_Product();
	}

	private I_M_Product createProduct(final BigDecimal coProductCostDistributionPercent)
	{
		final I_M_Product product = BusinessTestHelper.createProduct("product1", (I_C_UOM)null);
		product.setCoProductCostDistributionPercent(coProductCostDistributionPercent);
		return product;
	}

	@Test
	public void percent120_rejected()
	{
		final I_M_Product product = createProduct(new BigDecimal("120"));
		assertThatThrownBy(() -> interceptor.beforeSave(product))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(M_Product.MSG_COPRODUCT_COST_DISTRIBUTION_PERCENT_OUT_OF_RANGE.toAD_Message());
	}

	@Test
	public void percentMinus5_rejected()
	{
		final I_M_Product product = createProduct(new BigDecimal("-5"));
		assertThatThrownBy(() -> interceptor.beforeSave(product))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(M_Product.MSG_COPRODUCT_COST_DISTRIBUTION_PERCENT_OUT_OF_RANGE.toAD_Message());
	}

	@Test
	public void percent10_67_accepted()
	{
		final I_M_Product product = createProduct(new BigDecimal("10.67"));
		assertDoesNotThrow(() -> interceptor.beforeSave(product));
	}

	@Test
	public void percentBlank_accepted()
	{
		final I_M_Product product = createProduct(null);
		assertDoesNotThrow(() -> interceptor.beforeSave(product));
	}

	/**
	 * Boundary pin: {@code 0} is the inclusive lower bound of the {@code [0, 100]} range guard — accepted.
	 */
	@Test
	public void percent0_boundary_accepted()
	{
		final I_M_Product product = createProduct(new BigDecimal("0"));
		assertDoesNotThrow(() -> interceptor.beforeSave(product));
	}

	/**
	 * Boundary pin: {@code 100} is the inclusive upper bound of the {@code [0, 100]} range guard — accepted.
	 */
	@Test
	public void percent100_boundary_accepted()
	{
		final I_M_Product product = createProduct(new BigDecimal("100"));
		assertDoesNotThrow(() -> interceptor.beforeSave(product));
	}

	/**
	 * Boundary pin: {@code 100.01} is just above the inclusive upper bound — rejected.
	 */
	@Test
	public void percent100_01_boundary_rejected()
	{
		final I_M_Product product = createProduct(new BigDecimal("100.01"));
		assertThatThrownBy(() -> interceptor.beforeSave(product))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(M_Product.MSG_COPRODUCT_COST_DISTRIBUTION_PERCENT_OUT_OF_RANGE.toAD_Message());
	}

	/**
	 * Boundary pin: {@code -0.01} is just below the inclusive lower bound — rejected.
	 */
	@Test
	public void percentMinus0_01_boundary_rejected()
	{
		final I_M_Product product = createProduct(new BigDecimal("-0.01"));
		assertThatThrownBy(() -> interceptor.beforeSave(product))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining(M_Product.MSG_COPRODUCT_COST_DISTRIBUTION_PERCENT_OUT_OF_RANGE.toAD_Message());
	}
}

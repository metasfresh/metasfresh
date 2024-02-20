package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
 * %%
 * Copyright (C) 2015 metas GmbH
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

import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.PlainContextAware;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.eevolution.util.ProductBOMBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;

public class ProductBOMCycleDetectionTest
{
	private MRPTestHelper helper;
	private ITrxManager trxManager;

	private HashMap<String, I_M_Product> productsCache;

	@BeforeEach
	public void beforeEach()
	{
		POJOWrapper.setDefaultStrictValues(false);
		helper = new MRPTestHelper();
		trxManager = Services.get(ITrxManager.class);

		productsCache = new HashMap<>();
	}

	private I_M_Product product(final String name)
	{
		return productsCache.computeIfAbsent(name, helper::createProduct);
	}

	private void bom(final String bomProductName, final String... bomLineProductNames)
	{
		final I_M_Product bomProduct = product(bomProductName);
		final I_PP_Product_BOMVersions bomVersionsA = helper.createBOMVersions(ProductId.ofRepoId(bomProduct.getM_Product_ID()));
		final ProductBOMBuilder builder = helper.newProductBOM()
				.setContext(PlainContextAware.newWithThreadInheritedTrx())
				.product(bomProduct)
				.bomVersions(bomVersionsA);

		final StringBuilder info = new StringBuilder();
		info.append("BOM ").append(bomProduct.getValue()).append("(").append(bomProduct.getM_Product_ID()).append("): ");

		for (final String bomLineProductName : bomLineProductNames)
		{
			final I_M_Product bomLineProduct = product(bomLineProductName);
			builder.newBOMLine().product(bomLineProduct).setIsQtyPercentage(false).setQtyBOM(BigDecimal.ONE).endLine();
			info.append(bomLineProduct.getValue()).append("(").append(bomLineProduct.getM_Product_ID()).append("); ");
		}

		builder.build();

		System.out.println(info);
	}

	/**
	 * Test BOM cycle detection for following hierarchy:
	 *
	 * <pre>
	 * 		      A
	 *           / \
	 *          B   C
	 *         / \
	 *        D   E
	 *           / \
	 *          F  (A)
	 * </pre>
	 */
	@Test
	public void testBOMCycles()
	{
		Assertions.assertThatThrownBy(() -> {
					trxManager.runInNewTrx(() -> {
						bom("A", "B", "C");
						bom("B", "D", "E");
						bom("E", "F", "A"); // throws exception
					});
				})
				.hasMessageStartingWith("Product_BOM_Cycle_Error - A");
	}

	@Test
	public void bomWithMultipleIdenticalProducts()
	{
		trxManager.runInNewTrx(() -> {
			bom("A", "B", "B", "B");
			bom("B", "C", "D", "C", "D");
		});
	}

}

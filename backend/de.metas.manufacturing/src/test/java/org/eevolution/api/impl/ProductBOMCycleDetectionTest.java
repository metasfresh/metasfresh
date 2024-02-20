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
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_Product;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.mrp.api.impl.MRPTestHelper;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ProductBOMCycleDetectionTest
{
	private MRPTestHelper helper;

	@Before
	public void init()
	{
		POJOWrapper.setDefaultStrictValues(false);
		helper = new MRPTestHelper();
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
	@Test(expected = AdempiereException.class)
	public void testBOMCycles()
	{
		final I_M_Product pA = helper.createProduct("A");
		final I_M_Product pB = helper.createProduct("B");
		final I_M_Product pC = helper.createProduct("C");
		final I_M_Product pD = helper.createProduct("D");
		final I_M_Product pE = helper.createProduct("E");
		final I_M_Product pF = helper.createProduct("F");

		final I_PP_Product_BOMVersions bomVersionsA = helper.createBOMVersions(ProductId.ofRepoId(pA.getM_Product_ID()));
		helper.newProductBOM()
				.product(pA)
				.bomVersions(bomVersionsA)
				.newBOMLine().product(pB).setIsQtyPercentage(false).setQtyBOM(BigDecimal.ONE).endLine()
				.newBOMLine().product(pC).setIsQtyPercentage(false).setQtyBOM(BigDecimal.ONE).endLine()
				.build();

		final I_PP_Product_BOMVersions bomVersionsB = helper.createBOMVersions(ProductId.ofRepoId(pB.getM_Product_ID()));
		helper.newProductBOM()
				.product(pB)
				.bomVersions(bomVersionsB)
				.newBOMLine().product(pD).setIsQtyPercentage(false).setQtyBOM(BigDecimal.ONE).endLine()
				.newBOMLine().product(pE).setIsQtyPercentage(false).setQtyBOM(BigDecimal.ONE).endLine()
				.build();

		final I_PP_Product_BOMVersions bomVersionsE = helper.createBOMVersions(ProductId.ofRepoId(pE.getM_Product_ID()));
		helper.newProductBOM()
				.product(pE)
				.newBOMLine().product(pF).setIsQtyPercentage(false).setQtyBOM(BigDecimal.ONE).endLine()
				.bomVersions(bomVersionsE)
				.newBOMLine().product(pA).setIsQtyPercentage(false).setQtyBOM(BigDecimal.ONE).endLine()
				.build();
	}

}

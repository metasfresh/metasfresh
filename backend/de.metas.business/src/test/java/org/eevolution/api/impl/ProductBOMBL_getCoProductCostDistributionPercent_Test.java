package org.eevolution.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import de.metas.business.BusinessTestHelper;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link ProductBOMBL#getCoProductCostDistributionPercent(I_PP_Product_BOMLine)}.
 */
public class ProductBOMBL_getCoProductCostDistributionPercent_Test
{
	private final ProductBOMBL productBOMBL = new ProductBOMBL();

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void coProductDistributionPercent_comesFromProduct_notFromQty()
	{
		// Given: a co-product whose M_Product.CoProductCostDistributionPercent is 30%
		final I_C_UOM uomEa = BusinessTestHelper.createUOM("ea", 0, 0);
		final I_M_Product coProduct = BusinessTestHelper.createProduct("Co-Product", uomEa);
		coProduct.setCoProductCostDistributionPercent(new BigDecimal("30"));
		InterfaceWrapperHelper.saveRecord(coProduct);
		final ProductId coProductId = ProductId.ofRepoId(coProduct.getM_Product_ID());

		final I_PP_Product_BOMLine coProductBomLine = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMLine.class);
		coProductBomLine.setComponentType(BOMComponentType.CoProduct.getCode());
		coProductBomLine.setM_Product_ID(coProductId.getRepoId());
		coProductBomLine.setC_UOM_ID(uomEa.getC_UOM_ID());
		// IMPORTANT: qty is deliberately NOT 1/0.3333.. -- if the method still derived
		// the percent from 1/qty, this assertion would fail.
		coProductBomLine.setQtyBOM(new BigDecimal("-7")); // co-products are stored negated

		// When
		final Percent distributionPercent = productBOMBL.getCoProductCostDistributionPercent(coProductBomLine);

		// Then: the percent is the one carried by the product, not 1/qty (which would be ~14.29%)
		assertThat(distributionPercent).isEqualTo(Percent.of(new BigDecimal("30")));
	}
}

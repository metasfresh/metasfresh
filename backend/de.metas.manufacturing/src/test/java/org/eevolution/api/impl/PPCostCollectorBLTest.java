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

package org.eevolution.api.impl;

import de.metas.product.ProductId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.eevolution.api.CostCollectorType;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.eevolution.model.X_PP_Product_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class PPCostCollectorBLTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(1_000_000);

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private I_PP_Order_BOMLine createOrderBOMLine(
			final String componentType,
			final BigDecimal qtyBatch,
			final BigDecimal qtyBOM)
	{
		final I_PP_Order_BOMLine orderBOMLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class);
		orderBOMLine.setM_Product_ID(PRODUCT_ID.getRepoId());
		orderBOMLine.setComponentType(componentType);
		orderBOMLine.setQtyBatch(qtyBatch);
		orderBOMLine.setQtyBOM(qtyBOM);
		InterfaceWrapperHelper.save(orderBOMLine);
		return orderBOMLine;
	}

	/**
	 * A co/by-product RECEIPT whose BOM line has QtyBatch==0 && QtyBOM==0 (a zero-planned "Rework"
	 * co-product yield) must be classified as {@link CostCollectorType#MixVariance} so it enters the
	 * co-product capitalization path -- NOT {@link CostCollectorType#MethodChangeVariance}, which would
	 * expense the stocked co-product value instead of capitalizing it.
	 * <p>
	 * A co/by-product receipt shall ALWAYS be MixVariance, so {@code isCoOrByProduct} must win over
	 * {@code isMethodChangeVariance} (the latter being QtyBatch==0 && QtyBOM==0) for such a line.
	 */
	@Test
	void coProductReceipt_withZeroQtyBatchAndQtyBOM_isMixVariance()
	{
		final I_PP_Order_BOMLine coProductLine = createOrderBOMLine(
				X_PP_Product_BOMLine.COMPONENTTYPE_Co_Product,
				BigDecimal.ZERO,
				BigDecimal.ZERO);

		final CostCollectorType type = PPCostCollectorBL.extractCostCollectorTypeToUseForComponentIssue(coProductLine, PRODUCT_ID);

		assertThat(type).isEqualTo(CostCollectorType.MixVariance);
	}

	/**
	 * Constraint guard: a genuine COMPONENT issue with QtyBatch==0 && QtyBOM==0 is a real unplanned
	 * material method-change variance and MUST still be typed {@link CostCollectorType#MethodChangeVariance}.
	 * The co/by-product fix must not change this.
	 */
	@Test
	void componentIssue_withZeroQtyBatchAndQtyBOM_staysMethodChangeVariance()
	{
		final I_PP_Order_BOMLine componentLine = createOrderBOMLine(
				X_PP_Product_BOMLine.COMPONENTTYPE_Component,
				BigDecimal.ZERO,
				BigDecimal.ZERO);

		final CostCollectorType type = PPCostCollectorBL.extractCostCollectorTypeToUseForComponentIssue(componentLine, PRODUCT_ID);

		assertThat(type).isEqualTo(CostCollectorType.MethodChangeVariance);
	}
}

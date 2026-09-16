package de.metas.material.planning.pporder.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.product.ProductId;
import de.metas.uom.impl.UOMTestHelper;
import de.metas.util.lang.Percent;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.eevolution.api.BOMComponentType;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test {@link PPOrderBOMBL#getCoProductCostDistributionPercent(I_PP_Order_BOMLine)}.
 */
public class PPOrderBOMBL_getCoProductCostDistributionPercent_Test
{
	private UOMTestHelper helper;

	/**
	 * Service under test
	 */
	private PPOrderBOMBL ppOrderBOMBL;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new UOMTestHelper(Env.getCtx());
		ppOrderBOMBL = PPOrderBOMBL.newInstanceForUnitTesting();
	}

	@Test
	public void coProductDistributionPercent_comesFromProduct_notFromQty()
	{
		// Given: a finished good, and a co-product whose M_Product.CoProductCostDistributionPercent is 30%
		final I_C_UOM uomEa = helper.createUOM("ea", 0);
		final ProductId finishedGood = helper.createProduct("Finished Good", uomEa);
		final ProductId coProductId = helper.createProduct("Co-Product", uomEa);

		final I_M_Product coProduct = InterfaceWrapperHelper.load(coProductId, I_M_Product.class);
		coProduct.setCoProductCostDistributionPercent(new BigDecimal("30"));
		InterfaceWrapperHelper.saveRecord(coProduct);

		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(finishedGood.getRepoId());
		ppOrder.setC_UOM_ID(uomEa.getC_UOM_ID());
		PPOrderBOMBL_TestUtils.setCommonValues(ppOrder);

		final I_PP_Order_BOMLine coProductBomLine = InterfaceWrapperHelper.newInstance(I_PP_Order_BOMLine.class);
		coProductBomLine.setPP_Order(ppOrder);
		coProductBomLine.setComponentType(BOMComponentType.CoProduct.getCode());
		coProductBomLine.setM_Product_ID(coProductId.getRepoId());
		coProductBomLine.setC_UOM_ID(uomEa.getC_UOM_ID());
		// IMPORTANT: qty required is deliberately NOT 1/0.3333.. -- if the method still derived
		// the percent from 1/qty, this assertion would fail.
		coProductBomLine.setQtyRequiered(new BigDecimal("-7")); // co-products are stored negated
		PPOrderBOMBL_TestUtils.setCommonValues(coProductBomLine);

		// When
		final Percent distributionPercent = ppOrderBOMBL.getCoProductCostDistributionPercent(coProductBomLine);

		// Then: the percent is the one carried by the product, not 1/qty (which would be ~14.29%)
		assertThat(distributionPercent).isEqualTo(Percent.of(new BigDecimal("30")));
	}
}

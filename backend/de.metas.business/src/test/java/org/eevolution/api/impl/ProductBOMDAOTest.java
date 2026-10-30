package org.eevolution.api.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.product.ProductId;
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
 * Tests {@link ProductBOMDAO#isComponent(ProductId)}.
 */
public class ProductBOMDAOTest
{
	private final ProductBOMDAO productBOMDAO = new ProductBOMDAO();

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void isComponent_packingOnlyLine_true()
	{
		// A product that appears only as a Packing line must count as a component (foil / packaging material)
		final ProductId productId = createProductOnlyOnBOMLine(BOMComponentType.Packing);

		assertThat(productBOMDAO.isComponent(productId)).isTrue();
	}

	@Test
	public void isComponent_coProductOnlyLine_false()
	{
		// A co-product is an output, never a component: the filter must not be widened to every ComponentType
		final ProductId productId = createProductOnlyOnBOMLine(BOMComponentType.CoProduct);

		assertThat(productBOMDAO.isComponent(productId)).isFalse();
	}

	private static ProductId createProductOnlyOnBOMLine(final BOMComponentType componentType)
	{
		final I_C_UOM uom = BusinessTestHelper.createUOM("ea", 0, 0);
		final I_M_Product product = BusinessTestHelper.createProduct("P-" + componentType.getCode(), uom);
		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());

		final I_PP_Product_BOMLine line = InterfaceWrapperHelper.newInstance(I_PP_Product_BOMLine.class);
		line.setComponentType(componentType.getCode());
		line.setM_Product_ID(productId.getRepoId());
		line.setC_UOM_ID(uom.getC_UOM_ID());
		line.setQtyBOM(BigDecimal.ONE);
		InterfaceWrapperHelper.saveRecord(line);

		return productId;
	}
}

package de.metas.handlingunits.pporder.api.impl;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.HUTestHelper;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_PP_Order;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.product.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class PPOrderDocumentLUTUConfigurationHandlerTest
{
	private static final BigDecimal THREE = new BigDecimal("3");
	private I_C_UOM productUOM;
	private ProductId productId;
	private I_M_HU_PI_Item_Product piTU_Item_Product;
	private I_M_HU_PI_Item_Product huDefItemProductVirtual;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final HUTestHelper huTestHelper = new HUTestHelper();

		productId = huTestHelper.pTomatoProductId;
		productUOM = huTestHelper.uomEach;

		// 10 CUs fit into one TU
		final I_M_HU_PI piTU = huTestHelper.createHUDefinition("TU", X_M_HU_PI_Version.HU_UNITTYPE_TransportUnit);
		final I_M_HU_PI_Item piTU_Item = huTestHelper.createHU_PI_Item_Material(piTU);
		piTU_Item_Product = huTestHelper.assignProduct(piTU_Item, productId, TEN, productUOM);

		// 3 TUs fit onto 1 LU
		final I_M_HU_PI piLU = huTestHelper.createHUDefinition("LU", X_M_HU_PI_Version.HU_UNITTYPE_LoadLogistiqueUnit);
		huTestHelper.createHU_PI_Item_IncludedHU(piLU, piTU, THREE);

		huDefItemProductVirtual = huTestHelper.huDefItemProductVirtual;
	}

	@Test
	public void createNewLUTUConfiguration_with_TU_and_LU()
	{
		final I_PP_Order ppOrder = createPPOrder(piTU_Item_Product, TEN);

		// invoke the method under test
		final I_M_HU_LUTU_Configuration lutuConfiguration = PPOrderDocumentLUTUConfigurationHandler.instance
				.createNewLUTUConfiguration(ppOrder);

		assertThat(lutuConfiguration.getQtyCUsPerTU()).isEqualByComparingTo(TEN);
		assertThat(lutuConfiguration.getQtyTU()).as("our CUs fit into one TU").isEqualByComparingTo(ONE);
		assertThat(lutuConfiguration.getQtyLU()).isEqualByComparingTo("1");
	}

	@Test
	public void createNewLUTUConfiguration_with_MultipleTUandLU()
	{
		final I_PP_Order ppOrder = createPPOrder(piTU_Item_Product, BigDecimal.valueOf(101));

		assertThat(ppOrder.getQtyOrdered()).isEqualByComparingTo("101");

		// invoke the method under test
		final I_M_HU_LUTU_Configuration lutuConfiguration = PPOrderDocumentLUTUConfigurationHandler.instance
				.createNewLUTUConfiguration(ppOrder);

		assertThat(lutuConfiguration.getQtyCUsPerTU()).isEqualByComparingTo("10");
		assertThat(lutuConfiguration.getQtyTU()).isEqualByComparingTo("3");
		assertThat(lutuConfiguration.getQtyLU()).isEqualByComparingTo("4");
	}

	@Test
	public void createNewLUTUConfiguration_with_virtual_CU()
	{
		final I_PP_Order ppOrder = createPPOrder(huDefItemProductVirtual, TEN);

		// invoke the method under test
		final I_M_HU_LUTU_Configuration lutuConfiguration = PPOrderDocumentLUTUConfigurationHandler.instance
				.createNewLUTUConfiguration(ppOrder);

		assertThat(lutuConfiguration.isInfiniteQtyCU()).isTrue();
		assertThat(lutuConfiguration.getQtyCUsPerTU()).isEqualByComparingTo(ZERO);
	}

	private I_PP_Order createPPOrder(final I_M_HU_PI_Item_Product piTU_Item_Product, final BigDecimal qtyOrdered)
	{
		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setM_HU_PI_Item_Product(piTU_Item_Product);
		orderLine.setM_Product_ID(productId.getRepoId());
		save(orderLine);

		final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(productId.getRepoId());
		ppOrder.setC_OrderLine_ID(orderLine.getC_OrderLine_ID());
		ppOrder.setC_UOM_ID(productUOM.getC_UOM_ID());
		ppOrder.setQtyOrdered(qtyOrdered);
		save(ppOrder);
		return ppOrder;
	}
}

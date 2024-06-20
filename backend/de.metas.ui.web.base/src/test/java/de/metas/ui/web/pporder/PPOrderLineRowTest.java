package de.metas.ui.web.pporder;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_PP_Order_Qty;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.ui.web.handlingunits.HUEditorRowType;
import de.metas.ui.web.view.IViewRowAttributesProvider;
import de.metas.ui.web.window.datatypes.ColorValue;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.eevolution.api.BOMComponentIssueMethod;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class PPOrderLineRowTest
{
	private IViewRowAttributesProvider viewRowAttributesProvider;

	private I_C_UOM uom;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		uom = newInstance(I_C_UOM.class);
		uom.setUOMSymbol("Ea");
		save(uom);

		viewRowAttributesProvider = Mockito.mock(IViewRowAttributesProvider.class);
	}

	@SuppressWarnings("SameParameterValue")
	private ProductId createProduct(final String name)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uom.getC_UOM_ID());
		save(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Test
	public void canBuildForPPOrder()
	{
		final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
		ppOrder.setM_Product_ID(createProduct("main").getRepoId());
		ppOrder.setC_UOM_ID(uom.getC_UOM_ID());
		save(ppOrder);

		final PPOrderLineRow result = PPOrderLineRow.builderForPPOrder()
				.attributesProvider(viewRowAttributesProvider)
				.includedRows(ImmutableList.of())
				.packingInfoOrNull("packingInfo")
				.ppOrder(ppOrder)
				.processed(true)
				.build();
		assertThat(result.getPackingInfo()).isEqualTo("packingInfo");
		assertThat(result.isTopLevelHU()).isFalse();
		assertThat(result.isHUStatusActive()).isFalse();
	}

	@Test
	public void canBuildForPPOrderBomLine()
	{
		final I_PP_Order ppOrder = newInstance(I_PP_Order.class);
		save(ppOrder);

		final I_PP_Order_BOMLine ppOrderBomLine = newInstance(I_PP_Order_BOMLine.class);
		ppOrderBomLine.setIssueMethod(BOMComponentIssueMethod.IssueOnlyForReceived.getCode());
		ppOrderBomLine.setPP_Order(ppOrder);
		ppOrderBomLine.setM_Product_ID(createProduct("ComponentProduct").getRepoId());
		save(ppOrderBomLine);

		final PPOrderLineRow result = PPOrderLineRow.builderForPPOrderBomLine()
				.attributesProvider(viewRowAttributesProvider)
				.includedRows(ImmutableList.of())
				.packingInfoOrNull(null)
				.ppOrderBomLine(ppOrderBomLine)
				.qtyPlan(Quantity.of(10, uom))
				.qtyProcessedIssuedOrReceived((Quantity.zero(uom)))
				.type(PPOrderLineType.BOMLine_Component)
				.processed(true)
				.build();
		assertThat(result.getPackingInfo()).isNull();
		assertThat(result.getType()).isEqualTo(PPOrderLineType.BOMLine_Component);
		assertThat(result.isTopLevelHU()).isFalse();
		assertThat(result.isHUStatusActive()).isFalse();
		assertThat(result.getIssueMethod()).isEqualTo(BOMComponentIssueMethod.IssueOnlyForReceived);
	}

	@Test
	public void canBuildForSourceHU()
	{
		final PPOrderLineRow result = PPOrderLineRow.builderForSourceHU()
				.code("code")
				.huId(HuId.ofRepoId(30))
				.packingInfo("packingInfo")
				.product(JSONLookupValue.of(35, "product"))
				.qty(Quantity.of(10, uom))
				.rowId(PPOrderLineRowId.ofSourceHU(DocumentId.of(40), HuId.ofRepoId(30)))
				.type(PPOrderLineType.HU_TU)
				.uom(JSONLookupValue.of(50, "uom"))
				.attributesSupplier(() -> null)
				.topLevelHU(false)
				.huStatus(JSONLookupValue.of(X_M_HU.HUSTATUS_Planning, "Planning"))
				.build();
		assertThat(result.getPackingInfo()).isEqualTo("packingInfo");
		assertThat(result.isTopLevelHU()).isFalse();
		assertThat(result.isHUStatusActive()).isFalse();
	}

	@Test
	public void canBuildForIssuedOrReceivedHU()
	{
		final I_PP_Order_Qty ppOrderQty = newInstance(I_PP_Order_Qty.class);
		ppOrderQty.setPP_Order_ID(1); // dummy
		ppOrderQty.setM_HU_ID(10);
		save(ppOrderQty);

		final PPOrderLineRow result = PPOrderLineRow.builderForIssuedOrReceivedHU()
				.attributesSupplier(() -> null)
				.code("code")
				.includedRows(ImmutableList.of())
				.packingInfo("packingInfo")
				.ppOrderQty(ppOrderQty)
				.parentRowReadonly(true)
				.product(JSONLookupValue.of(35, "product"))
				.quantity(new Quantity(BigDecimal.TEN, uom))
				.rowId(PPOrderLineRowId.ofIssuedOrReceivedHU(DocumentId.of(40), HuId.ofRepoId(10)))
				.type(HUEditorRowType.TU)
				.topLevelHU(true)
				.huStatus(JSONLookupValue.of(X_M_HU.HUSTATUS_Active, "Active"))
				.build();
		assertThat(result.getPackingInfo()).isEqualTo("packingInfo");
		assertThat(result.isTopLevelHU()).isTrue();
		assertThat(result.isHUStatusActive()).isTrue();
	}

	@Nested
	class computeLineStatusColor
	{
		@Nested
		class positiveQtyPlan
		{
			@Test
			void issuedLessThanRequired()
			{
				assertThat(PPOrderLineRow.computeLineStatusColor(Quantity.of(10, uom), Quantity.of(2, uom)))
						.isEqualTo(ColorValue.RED);
			}

			@Test
			void issuedAsRequired()
			{
				assertThat(PPOrderLineRow.computeLineStatusColor(Quantity.of(10, uom), Quantity.of(10, uom)))
						.isEqualTo(ColorValue.GREEN);
			}

			@Test
			void issuedMoreThanRequired()
			{
				assertThat(PPOrderLineRow.computeLineStatusColor(Quantity.of(10, uom), Quantity.of(12, uom)))
						.isEqualTo(ColorValue.GREEN);
			}
		}

		@Nested
		class negativeQtyPlan
		{
			@Test
			void issuedLessThanRequired()
			{
				assertThat(PPOrderLineRow.computeLineStatusColor(Quantity.of(-10, uom), Quantity.of(-2, uom)))
						.isEqualTo(ColorValue.RED);
			}

			@Test
			void issuedAsRequired()
			{
				assertThat(PPOrderLineRow.computeLineStatusColor(Quantity.of(-10, uom), Quantity.of(-10, uom)))
						.isEqualTo(ColorValue.GREEN);
			}

			@Test
			void issuedMoreThanRequired()
			{
				assertThat(PPOrderLineRow.computeLineStatusColor(Quantity.of(-10, uom), Quantity.of(-12, uom)))
						.isEqualTo(ColorValue.GREEN);
			}
		}

	}

}

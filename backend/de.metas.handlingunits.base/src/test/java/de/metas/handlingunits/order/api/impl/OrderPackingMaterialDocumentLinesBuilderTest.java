package de.metas.handlingunits.order.api.impl;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import de.metas.adempiere.model.I_M_Product;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PackingMaterial;
import de.metas.handlingunits.model.X_M_HU_PI_Item;
import de.metas.order.IOrderLineBL;
import de.metas.order.OrderLinePriceUpdateRequest;
import de.metas.order.impl.OrderLineBL;
import de.metas.util.Services;

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

public class OrderPackingMaterialDocumentLinesBuilderTest
{

	private static final BigDecimal TWENTY = new BigDecimal("20");
	private static final BigDecimal TWO = new BigDecimal("2");
	private I_M_HU_PI_Item_Product huPiItemProduct;
	private I_M_Product materialProduct;
	private I_M_Product packageProduct;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// we don't want to invoke the pricing logic
		final OrderLineBL orderLineBL = Mockito.spy(new OrderLineBL());
		Mockito.doNothing().when(orderLineBL).updatePrices(Matchers.any(OrderLinePriceUpdateRequest.class));
		Services.registerService(IOrderLineBL.class, orderLineBL);

		final I_C_UOM packageProductUom = newInstance(I_C_UOM.class);
		saveRecord(packageProductUom);

		packageProduct = newInstance(I_M_Product.class);
		packageProduct.setC_UOM_ID(packageProductUom.getC_UOM_ID());
		saveRecord(packageProduct);

		final I_C_UOM materialProductUOM = newInstance(I_C_UOM.class);
		saveRecord(materialProductUOM);

		materialProduct = newInstance(I_M_Product.class);
		materialProduct.setC_UOM_ID(materialProductUOM.getC_UOM_ID());
		saveRecord(materialProduct);

		final I_M_HU_PI_Item miHuPiItem = newInstance(I_M_HU_PI_Item.class);
		miHuPiItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_Material);
		miHuPiItem.setM_HU_PI_Version_ID(10);
		saveRecord(miHuPiItem);

		final I_M_HU_PackingMaterial huPackingMaterial = newInstance(I_M_HU_PackingMaterial.class);
		huPackingMaterial.setM_Product_ID(packageProduct.getM_Product_ID());
		saveRecord(huPackingMaterial);

		final I_M_HU_PI_Item pmHuPiItem = newInstance(I_M_HU_PI_Item.class);
		pmHuPiItem.setItemType(X_M_HU_PI_Item.ITEMTYPE_PackingMaterial);
		pmHuPiItem.setM_HU_PackingMaterial(huPackingMaterial);
		pmHuPiItem.setM_HU_PI_Version_ID(10);
		saveRecord(pmHuPiItem);

		huPiItemProduct = newInstance(I_M_HU_PI_Item_Product.class);
		huPiItemProduct.setM_HU_PI_Item(pmHuPiItem);
		huPiItemProduct.setM_Product_ID(materialProduct.getM_Product_ID());
		huPiItemProduct.setQty(TEN);
		saveRecord(huPiItemProduct);

	}

	@Test
	public void test_packageOl_doesnt_exists()
	{
		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setDatePromised(TimeUtil.parseTimestamp("2018-11-21"));
		orderRecord.setDateOrdered(TimeUtil.parseTimestamp("2018-11-20"));
		saveRecord(orderRecord);

		final I_C_OrderLine olRecord1 = newInstance(I_C_OrderLine.class);
		olRecord1.setC_Order(orderRecord);
		olRecord1.setIsPackagingMaterial(false);
		olRecord1.setM_HU_PI_Item_Product(huPiItemProduct);
		olRecord1.setQtyItemCapacity(TEN);
		olRecord1.setQtyOrdered(TWENTY);
		olRecord1.setM_Product_ID(materialProduct.getM_Product_ID());
		saveRecord(olRecord1);

		final OrderPackingMaterialDocumentLinesBuilder orderPackingMaterialDocumentLinesBuilder = new OrderPackingMaterialDocumentLinesBuilder(orderRecord);

		// invoke the methods under test
		orderPackingMaterialDocumentLinesBuilder.addAllOrderLinesFromOrder();
		orderPackingMaterialDocumentLinesBuilder.create();

		final List<I_C_OrderLine> orderLineRecords = POJOLookupMap.get().getRecords(I_C_OrderLine.class);

		assertThat(orderLineRecords).hasSize(2);
		final I_C_OrderLine packageOrderLine = orderLineRecords.get(1); // we assume that they are ordered by C_OrderLineID
		assertThat(packageOrderLine.isPackagingMaterial()).isTrue();
		assertThat(packageOrderLine.isActive()).isTrue();
		assertThat(packageOrderLine.getM_Product_ID()).isEqualTo(packageProduct.getM_Product_ID());
		assertThat(packageOrderLine.getDateOrdered()).isEqualTo(orderRecord.getDateOrdered());
		assertThat(packageOrderLine.getDatePromised()).isEqualTo(orderRecord.getDatePromised());
		assertThat(packageOrderLine.getQtyOrdered()).isEqualByComparingTo(TWO);
	}

	@Test
	public void test_inactive_packageOl_exists()
	{
		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setDatePromised(TimeUtil.parseTimestamp("2018-11-21"));
		orderRecord.setDateOrdered(TimeUtil.parseTimestamp("2018-11-20"));
		saveRecord(orderRecord);

		final I_C_OrderLine olRecord1 = newInstance(I_C_OrderLine.class);
		olRecord1.setC_Order(orderRecord);
		olRecord1.setIsPackagingMaterial(false);
		olRecord1.setM_HU_PI_Item_Product(huPiItemProduct);
		olRecord1.setQtyItemCapacity(TEN);
		olRecord1.setQtyOrdered(TWENTY);
		olRecord1.setM_Product_ID(materialProduct.getM_Product_ID());
		olRecord1.setC_UOM_ID(materialProduct.getC_UOM_ID());
		saveRecord(olRecord1);

		final I_C_OrderLine olRecord2 = newInstance(I_C_OrderLine.class);
		olRecord2.setIsActive(false);
		olRecord2.setQtyOrdered(ZERO); // set to zero by the system when it was deactivated
		olRecord2.setC_Order(orderRecord);
		olRecord2.setIsPackagingMaterial(true);
		olRecord2.setM_HU_PI_Item_Product_ID(101);
		olRecord2.setM_Product_ID(packageProduct.getM_Product_ID());
		olRecord2.setC_UOM_ID(packageProduct.getC_UOM_ID());
		olRecord2.setDatePromised(TimeUtil.parseTimestamp("2018-10-21")); // has outdated values
		olRecord2.setDateOrdered(TimeUtil.parseTimestamp("2018-10-20"));
		saveRecord(olRecord2);

		final OrderPackingMaterialDocumentLinesBuilder orderPackingMaterialDocumentLinesBuilder = new OrderPackingMaterialDocumentLinesBuilder(orderRecord);

		// invoke the methods under test
		orderPackingMaterialDocumentLinesBuilder.addAllOrderLinesFromOrder();
		orderPackingMaterialDocumentLinesBuilder.create();

		final List<I_C_OrderLine> orderLineRecords = POJOLookupMap.get().getRecords(I_C_OrderLine.class);

		assertThat(orderLineRecords).hasSize(2);
		final I_C_OrderLine packageOrderLine = orderLineRecords.get(1); // we assume that they are ordered by C_OrderLineID
		assertThat(packageOrderLine.isPackagingMaterial()).isTrue();
		assertThat(packageOrderLine.isActive()).isTrue();
		assertThat(packageOrderLine.getM_Product_ID()).isEqualTo(packageProduct.getM_Product_ID());
		assertThat(packageOrderLine.getDateOrdered()).isEqualTo(orderRecord.getDateOrdered());
		assertThat(packageOrderLine.getDatePromised()).isEqualTo(orderRecord.getDatePromised());
		assertThat(packageOrderLine.getQtyOrdered()).isEqualByComparingTo(TWO);
	}
}

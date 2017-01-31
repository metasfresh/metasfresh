package de.metas.handlingunits.ddorder.intergrationtest;

/*
 * #%L
 * de.metas.handlingunits.client
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.collections.ListUtils;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_OrderLine;
import org.eevolution.model.I_PP_MRP;
import org.hamcrest.Matchers;
import org.junit.Assert;

import de.metas.handlingunits.client.terminal.ddorder.model.DDOrderTableRow;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSTableRow;
import de.metas.handlingunits.expectations.PackingMaterialsExpectation;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.handlingunits.util.TraceUtils;

public class DD_Order_StandardCase_IntegrationTest extends AbstractHUDDOrderProcessIntegrationTest
{
	@Override
	protected void setupMRP_MasterData()
	{
		mrpHelper.setToday(2014, 12, 15);
		mrpMasterData.createStandardProductPlannings();
	}

	@Override
	protected void step010_PrepareData_CreateSalesOrderDemand()
	{
		final Timestamp dateOrdered = TimeUtil.getDay(2014, 12, 20);
		final I_PP_MRP mrpDemand = mrpHelper.createMRPDemand(
				mrpMasterData.pSalad_2xTomato_1xOnion,
				new BigDecimal("100"), // qty
				dateOrdered, // date
				mrpMasterData.plant01, // plant
				mrpMasterData.warehouse_picking01 // picking warehouse
				);
		mrpDemand.setC_BPartner(bpartner_Customer01);
		InterfaceWrapperHelper.save(mrpDemand);
	}

	@Override
	protected void step029_CreateRawMaterialHUs_On_RawMaterialsWarehouse()
	{
		/* final List<I_M_HU> luHUs = */ generateLUs(piTU_Item_Product_Tomato, mrpMasterData.warehouse_rawMaterials01_locator, 1);
		//luHUs.forEach(hu -> helper.commitAndDumpHU(hu));
	}

	@Override
	protected I_M_Warehouse step030_DDOrderPOS_getWarehouseToSelect()
	{
		return mrpMasterData.warehouse_plant01;
	}

	@Override
	protected IPOSTableRow step030_DDOrderPOS_selectDDOrderTableRow(final List<IPOSTableRow> rows)
	{
		// We expect 2 rows, one for Tomatoes, and one for Onions
		Assert.assertEquals("Invalid number of rows expected: " + rows, 2, rows.size());

		//
		// Search and pick the Tomatoes line.
		// There shall be one and only one
		final IPOSTableRow row_Tomatoes = ListUtils.singleElement(rows, new Predicate<IPOSTableRow>()
		{
			@Override
			public boolean evaluate(final IPOSTableRow row)
			{
				final DDOrderTableRow ddOrderLineRow = (DDOrderTableRow)row;
				final Set<Integer> productIds = ddOrderLineRow.getM_Product_IDs();
				return productIds.size() == 1 && productIds.contains(mrpMasterData.pTomato.getM_Product_ID());
			}
		});

		return row_Tomatoes;
	}

	@Override
	protected void step030_DDOrderPOS_selectHUsToMove(final HUEditorModel huEditorModel)
	{
		final List<IHUKey> topLevelHUKeys = huEditorModel.getRootHUKey().getChildren();
		huEditorModel.setSelected(topLevelHUKeys.get(0)); // pick first
	}

	@Override
	protected void step030_DDOrderPOS_ValidateGeneratedMovements()
	{
		final DDOrderTableRow rowSelected = ddOrderPOS_HUSelectModel.getRowSelected();
		final I_DD_OrderLine ddOrderLine = ListUtils.singleElement(rowSelected.getDD_OrderLines());
		InterfaceWrapperHelper.refresh(ddOrderLine);
		final I_M_Warehouse warehouseInTransit = ddOrderLine.getDD_Order().getM_Warehouse();

		//
		// Validate DD_OrderLine
		Assert.assertEquals("Invalid DD OrderLine - Product", mrpMasterData.pTomato, ddOrderLine.getM_Product());
		Assert.assertThat("Invalid DD OrderLine - QtyOrdered", ddOrderLine.getQtyOrdered(), Matchers.comparesEqualTo(new BigDecimal("200")));
		Assert.assertThat("Invalid DD OrderLine - QtyDelivered", ddOrderLine.getQtyDelivered(), Matchers.comparesEqualTo(new BigDecimal("1")));
		Assert.assertThat("Invalid DD OrderLine - QtyInTrasit", ddOrderLine.getQtyInTransit(), Matchers.comparesEqualTo(new BigDecimal("0")));
		Assert.assertEquals("Invalid DD OrderLine - Locator From", mrpMasterData.warehouse_rawMaterials01_locator, ddOrderLine.getM_Locator());
		Assert.assertEquals("Invalid DD OrderLine - Locator To", mrpMasterData.warehouse_plant01_locator, ddOrderLine.getM_LocatorTo());
		//
		// Validate DD_OrderLine's MRP records
		mrpHelper.newMRPExpectation()
				.addDDOrderLineToInclude(ddOrderLine)
				//
				.warehouse(mrpMasterData.warehouse_plant01)
				.qtyDemand(0)
				.qtySupply(199)
				.qtyOnHandReserved(0)
				.notBalanced()
				.assertExpected()
				//
				.warehouse(mrpMasterData.warehouse_rawMaterials01)
				.qtyDemand(199)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.notBalanced()
				.assertExpected();

		//
		// Validate DD_OrderLine's Shipment Movement (from Source Warehouse to InTransit)
		{
			final I_M_MovementLine shipMovementLine = getDDOrderLineMovementLine(ddOrderLine, false);
			Assert.assertEquals("Invalid Shipment Movement - Product", mrpMasterData.pTomato, shipMovementLine.getM_Product());
			Assert.assertThat("Invalid Shipment Movement - MovementQty", shipMovementLine.getMovementQty(), Matchers.comparesEqualTo(new BigDecimal("1")));
			Assert.assertEquals("Invalid Shipment Movement - Locator From", ddOrderLine.getM_Locator(), shipMovementLine.getM_Locator());
			Assert.assertEquals("Invalid Shipment Movement - Warehouse To", warehouseInTransit, shipMovementLine.getM_LocatorTo().getM_Warehouse());

			// Validate shipment movement's packing materials
			PackingMaterialsExpectation.newExpectation()
					.addProductAndQty(piLU_Item_PackingMaterial, new BigDecimal("1"))
					.addProductAndQty(piTU_Item_PackingMaterial, new BigDecimal("1"))
					.assertExpected(shipMovementLine.getM_Movement());
		}
		//
		// Validate DD_OrderLine's Receipt Movement (from InTransit Warehouse to Target Warehouse)
		{
			final I_M_MovementLine receiptMovementLine = getDDOrderLineMovementLine(ddOrderLine, true);
			Assert.assertEquals("Invalid Receipt Movement - Product", mrpMasterData.pTomato, receiptMovementLine.getM_Product());
			Assert.assertThat("Invalid Receipt Movement - MovementQty", receiptMovementLine.getMovementQty(), Matchers.comparesEqualTo(new BigDecimal("1")));
			Assert.assertEquals("Invalid Receipt Movement - Warehouse From", warehouseInTransit, receiptMovementLine.getM_Locator().getM_Warehouse());
			Assert.assertEquals("Invalid Receipt Movement - Locator To", ddOrderLine.getM_LocatorTo(), receiptMovementLine.getM_LocatorTo());
		}
	}
}

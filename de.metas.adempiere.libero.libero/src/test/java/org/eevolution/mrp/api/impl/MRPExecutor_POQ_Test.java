package org.eevolution.mrp.api.impl;

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
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;
import org.eevolution.api.IPPOrderBL;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.AbstractMRPTestBase;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.document.engine.IDocument;

public class MRPExecutor_POQ_Test extends AbstractMRPTestBase
{

	private MRPTestDataSimple masterData;
	private I_C_BPartner bpartner01;
	private I_C_BPartner bpartner02;

	@Override
	protected void afterInit()
	{
		masterData = new MRPTestDataSimple(helper);

		bpartner01 = helper.createBPartner("bpartner01");
		bpartner02 = helper.createBPartner("bpartner02");

		// Product Planning: Picking <- Manufacturing
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_picking01)
				.plant(helper.plant_any)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
				//
				.setOrderPolicy(X_PP_Product_Planning.ORDER_POLICY_Lot_For_Lot)
				.build();
		// Product Planning: Manufacturing
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.plant(masterData.plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.setIsManufactured(X_PP_Product_Planning.ISMANUFACTURED_Yes)
				.setPP_Product_BOM(masterData.pSalad_2xTomato_1xOnion_BOM)
				.setAD_Workflow(masterData.workflow_Standard)
				.setDeliveryTime_Promised(1)
				//
				.setOrderPolicy(X_PP_Product_Planning.ORDER_POLICY_PeriodOrderQuantity)
				.setOrderPeriod(1) // same day
				.setPOQAggregateOnBPartnerLevel(true) // aggregate on BPartner Level
				//
				.build();
		// Product Planning: Manufacturing <- Raw materials warehouse
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.plant(masterData.plant01)
				.product(masterData.pTomato)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
				.build();
		helper.newProductPlanning()
				.warehouse(masterData.warehouse_plant01)
				.plant(masterData.plant01)
				.product(masterData.pOnion)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
				.build();

		helper.mrpExecutor.setDisallowMRPNotes(true);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_rawMaterials01)
				.setM_Product(masterData.pTomato)
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_rawMaterials01)
				.setM_Product(masterData.pOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_120_NoProductPlanning);
	}

	/**
	 * Test case
	 * <ul>
	 * <li>planning: warehouse_plant01, POQ, orderPeriod=1(same day)
	 * <li>Create Sales Orders: 2x100items for BPartner01, 1x100items for BPartner02, 1x100items for BPartner01
	 * <li>run MRP
	 * <li>expect 4 x DD Order from Plant to Picking
	 * <li>expect 1 x PP Order for BPartner01, Qty=300
	 * <li>expect 1 x PP Order for BPartner02, Qty=100
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public final void test_100xBP01_100xBP01_100xBP02_100xBP01_SameDate()
	{
		helper.assumeMRP_POQ_Enabled();

		helper.setToday(2014, 12, 1); // 1st of Dec
		final Timestamp dateOrdered = TimeUtil.addDays(helper.getToday(), +20);

		//
		// Create Sales Orders
		// * 2x100items for BPartner01
		// * 1x100items for BPartner02
		// * 1x100items for BPartner01
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01, // Warehouse
				bpartner01 // BPartner
		);
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01, // Warehouse
				bpartner01 // BPartner
		);
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01, // Warehouse
				bpartner02 // BPartner
		);
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01, // Warehouse
				bpartner01 // BPartner
		);

		//
		// Execute MRP and validate
		// (we are executing it several times to make sure nothing changes on subsequent runs)
		for (int runNo = 1; runNo <= 3; runNo++)
		{
			test_100xBP01_100xBP01_100xBP02_100xBP01_runMRP_AndValidate();
		}
	}

	private final void test_100xBP01_100xBP01_100xBP02_100xBP01_runMRP_AndValidate()
	{
		//
		// Run MRP & Validate
		runMRP();
		{
			// Validate: Warehouse=Picking
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_picking01)
					// .product(masterData.pSalad_2xTomato_1xOnion) // does not matter, we have only one
					.balanced()
					.assertExpected();
			// Validate: Warehouse=Plant01
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_plant01)
					.product(masterData.pSalad_2xTomato_1xOnion)
					.balanced()
					.assertExpected();

			// Validate: Warehouse=Plant01, BPartner01
			final List<I_PP_Order> ppOrders_bpartner01 = new ArrayList<>();
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_plant01)
					.product(masterData.pSalad_2xTomato_1xOnion)
					.bpartner(bpartner01)
					.qtyDemand(300)
					.qtySupply(300)
					.balanced()
					.collectDocuments(I_PP_Order.class, ppOrders_bpartner01)
					.assertExpected();
			final I_PP_Order ppOrder_bpartner01 = ListUtils.singleElement(ppOrders_bpartner01);
			Assert.assertEquals("Invalid PP_Order.Qty", 300, ppOrder_bpartner01.getQtyOrdered().intValueExact());

			// Validate: Warehouse=Plant01, BPartner02
			final List<I_PP_Order> ppOrders_bpartner02 = new ArrayList<>();
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_plant01)
					.product(masterData.pSalad_2xTomato_1xOnion)
					.bpartner(bpartner02)
					.qtyDemand(100)
					.qtySupply(100)
					.balanced()
					.collectDocuments(I_PP_Order.class, ppOrders_bpartner02)
					.assertExpected();
			final I_PP_Order ppOrder_bpartner02 = ListUtils.singleElement(ppOrders_bpartner02);
			Assert.assertEquals("Invalid PP_Order.Qty", 100, ppOrder_bpartner02.getQtyOrdered().intValueExact());
		}
	}

	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_3x100Demands_CompleteTheMO_FullyReceiveMO_New100Demand()
	{
		helper.assumeMRP_POQ_Enabled();

		helper.setToday(2014, 12, 1); // 1st of Dec
		final Timestamp dateOrdered = TimeUtil.addDays(helper.getToday(), +20);

		// Create 3x100items Demands
		for (int i = 1; i <= 3; i++)
		{
			helper.createMRPDemand(
					masterData.pSalad_2xTomato_1xOnion, // Product
					new BigDecimal("100"), // Demanded Qty
					dateOrdered, // Date
					masterData.plant01, // Plant
					masterData.warehouse_picking01, // Warehouse
					bpartner01 // BPartner
			);
		}

		//
		// Run MRP and validate
		helper.runMRP();
		helper.dumpMRPRecords("After first run");
		final I_PP_Order ppOrder;
		{
			final List<I_PP_Order> ppOrders = new ArrayList<>();
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_plant01)
					.product(masterData.pSalad_2xTomato_1xOnion)
					.qtyDemand(300)
					.qtySupply(300)
					.qtyOnHandReserved(0)
					.balanced()
					.collectDocuments(I_PP_Order.class, ppOrders)
					.assertExpected();

			ppOrder = ListUtils.singleElement(ppOrders);
		}

		//
		// Complete the MO
		docActionBL.processEx(ppOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		// Fully receive the MO
		helper.receiveFromPPOrder(ppOrder, ppOrder.getQtyOrdered());
		helper.dumpMRPRecords("After receiving all from MO");

		//
		// Run MRP again
		helper.mrpExecutor.createAllowMRPNodeRule() // ignore MRP-060 on DD Orders, because they are in Draft status atm
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased)
				.setM_Product(masterData.pSalad_2xTomato_1xOnion)
				.setM_Warehouse(masterData.warehouse_picking01);
		helper.runMRP();

		helper.dumpMRPRecords();
	}

	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_3x100Demands_CompleteTheMO_PartiallyReceiveMO_PartiallyShipTheDemands_RunMRPAgain()
	{
		//
		// Initial config
		helper.assumeMRP_POQ_Enabled();
		helper.setToday(2014, 12, 1); // 1st of Dec
		final Timestamp dateOrdered = TimeUtil.addDays(helper.getToday(), +20);

		//
		// Create 3x100items Demands
		final I_PP_MRP mrpDemand1 = helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_plant01, // Warehouse
				bpartner01 // BPartner
				);
		final I_PP_MRP mrpDemand2 = helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_plant01, // Warehouse
				bpartner01 // BPartner
				);
		final I_PP_MRP mrpDemand3 = helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_plant01, // Warehouse
				bpartner01 // BPartner
		);

		//
		// Run MRP and validate
		helper.runMRP();
		helper.dumpMRPRecords("After first run");
		final I_PP_Order ppOrder;
		{
			final List<I_PP_Order> ppOrders = new ArrayList<>();
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_plant01)
					.product(masterData.pSalad_2xTomato_1xOnion)
					.qtyDemand(300)
					.qtySupply(300)
					.qtyOnHandReserved(0)
					.qtyInTransitReserved(0)
					.balanced()
					.collectDocuments(I_PP_Order.class, ppOrders)
					.assertExpected();

			ppOrder = ListUtils.singleElement(ppOrders);
		}

		//
		// Complete the MO
		docActionBL.processEx(ppOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		//
		// Partially ship from demands (i.e. decrease their quantity)
		mrpDemand1.setQty(new BigDecimal("60")); // let's say we shipped 40items
		InterfaceWrapperHelper.save(mrpDemand1);
		mrpDemand2.setQty(new BigDecimal("90")); // let's say we shipped 10items
		InterfaceWrapperHelper.save(mrpDemand2);
		// Partially receive from MO
		helper.receiveFromPPOrder(ppOrder, new BigDecimal("70")); // receive 70items => 230items remaining

		//
		// Run MRP again and validate
		helper.dumpMRPRecords("Before 2nd run");
		helper.runMRP();
		helper.dumpMRPRecords("After 2nd run");
		{
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_plant01)
					.product(masterData.pSalad_2xTomato_1xOnion)
					.qtyDemand(60 + 90 + 100)
					.qtySupply(Services.get(IPPOrderBL.class).getQtyOpen(ppOrder)) // = 300 - 70 = 230
					.qtyOnHandReserved(70)
					.qtyInTransitReserved(70)
					.notAvailable()
					.assertExpected();
		}
		
		//
		// Receive everything from MO
		helper.receiveFromPPOrder(ppOrder, new BigDecimal("230"));
		
		//
		// Run MRP again and validate
		helper.dumpMRPRecords("Before 3nd run");
		helper.runMRP();
		helper.dumpMRPRecords("After 3nd run");
		{
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_plant01)
					.product(masterData.pSalad_2xTomato_1xOnion)
					.qtyDemand(60 + 90 + 100)
					.qtySupply(0)
					.qtyOnHandReserved(250)
					.qtyInTransitReserved(0)
					.notAvailable()
					.balanced()
					.assertExpected();
		}

		//
		// Ship remaining demands
		mrpDemand1.setQty(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(mrpDemand1);
		mrpDemand2.setQty(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(mrpDemand2);
		mrpDemand3.setQty(BigDecimal.ZERO);
		InterfaceWrapperHelper.save(mrpDemand3);
		

		//
		// Run MRP again and validate
		helper.dumpMRPRecords("Before 3rd run");
		helper.runMRP();
		helper.dumpMRPRecords("After 3rd run");
		{
			helper.newMRPExpectation()
					.warehouse(masterData.warehouse_plant01)
					.product(masterData.pSalad_2xTomato_1xOnion)
					.qtyDemand(0)
					.qtySupply(0)
					.qtyOnHandReserved(0)
					.qtyInTransitReserved(0)
					.notAvailable()
					.assertExpected();
		}

	}
}

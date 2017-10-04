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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.collections.ListUtils;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.TimeUtil;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.AbstractMRPTestBase;
import org.eevolution.mrp.expectations.MRPExpectation;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.document.engine.IDocument;
import de.metas.logging.LogManager;

/**
 * Simple MRP execution tests
 * 
 * @author tsa
 *
 */
public class MRPExecutor_Simple_Test extends AbstractMRPTestBase
{
	// Master data
	private MRPTestDataSimple masterData;
	private I_PP_Product_Planning productPlanning_whPicking_pSalad;
	private I_C_BPartner bparter01;

	@Override
	protected void afterInit()
	{
		masterData = new MRPTestDataSimple(helper);

		// Master data (misc)
		bparter01 = helper.createBPartner("BPartner01");

		// Product Planning: Picking <- Manufacturing
		productPlanning_whPicking_pSalad = helper.newProductPlanning()
				.warehouse(masterData.warehouse_picking01)
				.plant(helper.plant_any)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.setIsRequireDRP(true)
				.setDD_NetworkDistribution(masterData.ddNetwork)
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
	 * Test:
	 * <ul>
	 * <li>create a dummy MRP demand for 100 items in "warehouse_picking01"
	 * <li>run MRP
	 * <li>expect DD order is created to move qty from plant01 warehouse to picking warehouse
	 * <li>expect a PP order is created to produce 100 item on plant01 warehouse
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_LFL_SimpleTest()
	{
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				helper.getToday(), // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01 // Warehouse
		);

		//
		// Run MRP
		runMRP();

		//
		// Validate Warehouse Picking01
		helper.newMRPExpectation().warehouse(masterData.warehouse_picking01)
				.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
				.balanced().assertExpected();
		//
		// Validate Warehouse Plant01
		helper.newMRPExpectation().warehouse(masterData.warehouse_plant01).product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(100).qtySupply(100).qtyOnHandReserved(0)
				.balanced().assertExpected();
	}

	/**
	 * Test:
	 * <ul>
	 * <li>create a dummy MRP demand for 100 items in "warehouse_picking01"
	 * <li>run MRP
	 * <li>expect DD order is created to move qty from plant01 warehouse to picking warehouse
	 * <li>expect a PP order is created to produce 100 item on plant01 warehouse
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future 
	public void test_LFL_WithInitialQtyOnHand()
	{
		helper.mrpDAO.setQtyOnHand(masterData.warehouse_plant01, masterData.pSalad_2xTomato_1xOnion, new BigDecimal("70"));

		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				helper.getToday(), // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01 // Warehouse
		);
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_picking01).product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(100)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.notBalanced()
				.assertExpected();

		//
		// Run MRP
		runMRP();

		//
		// Validate Warehouse Picking01
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_picking01).product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(100)
				.qtySupply(100)
				.qtyOnHandReserved(0)
				.balanced()
				.assertExpected();
		//
		// Validate Warehouse Plant01
		helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01).product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(100)
				.qtySupply(30)
				.qtyOnHandReserved(70)
				.balanced()
				.assertExpected();
	}

	/**
	 * Test case to validate: http://dewiki908/mediawiki/index.php/07603_CMP_makes_Manufacturing_Order_although_Stock_available_%28100136163619%29 , NOK5
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_LFL_WithInitialQtyOnHand_MultipleMRPRuns()
	{
		helper.mrpDAO.setQtyOnHand(masterData.warehouse_plant01, masterData.pSalad_2xTomato_1xOnion, new BigDecimal("70"));
		helper.mrpDAO.setQtyOnHand(masterData.warehouse_plant01, masterData.pTomato, new BigDecimal("10000")); // let them be enough

		// Assumptions:
		// * we expect to need 2kg of tomatoes for one salad (wtf, i know, but that's our master data)

		helper.setToday(2014, 12, 1); // 1st of Dec
		final Timestamp dateOrdered = TimeUtil.addDays(helper.getToday(), +20);
		//
		// Create a "Sales Order" demand
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01 // Warehouse
		);

		// Define initial validations (we will change them we we go on)
		final MRPExpectation<Object> picking01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_picking01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(100)
				.qtyOnHandReserved(0)
				.qtySupply(0)
				.balanced(false)
				.assertExpected();
		final MRPExpectation<Object> plant01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(0)
				.qtyOnHandReserved(0)
				.qtySupply(0)
				.balanced(true)
				.assertExpected();
		final MRPExpectation<Object> plant01_pTomato_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pTomato)
				.qtyDemand(0)
				.qtyOnHandReserved(0)
				.qtySupply(0)
				.balanced(true)
				.assertExpected();
		//
		// Run MRP
		runMRP();
		// Validate Warehouse Picking01 after first MRP run
		picking01_pSalad_MRPExpectation
				.qtyDemand(100)
				.qtyOnHandReserved(0)
				.qtySupply(100)
				.balanced()
				.assertExpected();
		plant01_pSalad_MRPExpectation
				.qtyDemand(100)
				.qtyOnHandReserved(70)
				.qtySupply(30)
				.balanced()
				.assertExpected();
		plant01_pTomato_MRPExpectation
				.qtyDemand(60)
				.qtyOnHandReserved(60)
				.qtySupply(0)
				.balanced()
				.assertExpected();

		//
		// Complete all documents that we generated so far
		helper.completeAllMRPDocuments();

		//
		// Create another "Sales Order" demand
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("120"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01 // Warehouse
		);
		picking01_pSalad_MRPExpectation
				.qtyDemand(100 + 120)
				.qtySupply(100)
				.notBalanced()
				.assertExpected();

		//
		// Run MRP Again (second run)
		helper.dumpMRPRecords("Before second run");
		LogManager.setLoggerLevel(helper.getMRPLogger(), Level.INFO);
		runMRP();
		helper.dumpMRPRecords("After second run");
		// Validate
		picking01_pSalad_MRPExpectation
				// .qtyDemand(100 + 120)
				.qtyOnHandReserved(0)
				.qtySupply(100 + 120)
				.balanced()
				.assertExpected();
		plant01_pSalad_MRPExpectation
				.qtyDemand(100 + 120)
				.qtyOnHandReserved(70 + 0)
				.qtySupply(30 + 120)
				.balanced()
				.assertExpected();
		plant01_pTomato_MRPExpectation
				.qtyDemand(60 + 240)
				.qtyOnHandReserved(60 + 240)
				.qtySupply(0)
				.balanced()
				.assertExpected();
	}

	/**
	 * Test case
	 * <ul>
	 * <li>create sales order
	 * <li>run MRP
	 * <li>complete the MO
	 * <li>receive everything from MO
	 * <li>run MRP again
	 * <li>test!
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_LFL_1Order_RunMRP_CompletePPOrder_ReceiveFromMO_RunMRP()
	{
		helper.setToday(2014, 12, 1); // 1st of Dec
		final Timestamp dateOrdered = TimeUtil.addDays(helper.getToday(), +20);
		// final Timestamp dateOrdered = helper.getToday();

		//
		// Create a "Sales Order" demand
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01 // Warehouse
		);

		// Define initial validations (we will change them we we go on)
		final MRPExpectation<Object> picking01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_picking01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(100)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.balanced(false)
				.assertExpected();
		final MRPExpectation<Object> plant01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(0)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.balanced(true)
				.assertExpected();
		//
		// Run MRP
		runMRP();
		// Validate
		final I_PP_Order ppOrder;
		{
			final List<I_PP_Order> ppOrders = new ArrayList<>();
			picking01_pSalad_MRPExpectation
					.qtyDemand(100)
					.qtySupply(100)
					.qtyOnHandReserved(0)
					.balanced()
					.assertExpected();
			plant01_pSalad_MRPExpectation
					.qtyDemand(100)
					.qtySupply(100)
					.qtyOnHandReserved(0)
					.balanced()
					.collectDocuments(I_PP_Order.class, ppOrders)
					.assertExpected();
			ppOrder = ListUtils.singleElement(ppOrders);
		}

		//
		// Complete the MO
		docActionBL.processEx(ppOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		//
		// Simulate that we are receiving the whole qty from MO
		helper.receiveFromPPOrder(ppOrder, ppOrder.getQtyOrdered());

		//
		// Run MRP Again (second run)
		helper.mrpExecutor.createAllowMRPNodeRule()
				// .setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pSalad_2xTomato_1xOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		// skip MRP-060 on DD orders which are bringing the raw materials to Manufacturing Order because those ones are not completed but only MRP_AllowCleanup was set to false.
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pTomato)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		helper.runMRP();
		helper.dumpMRPRecords("Before second run");
		LogManager.setLoggerLevel(helper.getMRPLogger(), Level.INFO);
		runMRP();
		// Validate
		{
			picking01_pSalad_MRPExpectation
					.qtyDemand(100)
					.qtySupply(100)
					.qtyOnHandReserved(0)
					.balanced()
					.assertExpected();
			plant01_pSalad_MRPExpectation
					.qtyDemand(100)
					.qtySupply(0)
					.qtyOnHandReserved(100)
					.balanced()
					.assertExpected();
		}

		// TODO: theoretically this is the test case of the fucked up case we have with MRP but this test passes.
		// In adit, sometimes we got following issues:
		// * MRP was not allocating the QtyOnHand to the forward DD Order
		// * or MRP was creating a new manufacturing order
		// I think it's important to already have other orders/documents with that product, in order to have some noise

		helper.dumpMRPRecords("After second run");
	}

	/**
	 * Test case
	 * <ul>
	 * <li>create sales order
	 * <li>run MRP
	 * <li>complete the MO
	 * <li>receive everything from MO, but use some of the QtyOnHand that was just received
	 * <li>run MRP again
	 * <li>test!
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_LFL_1Order_RunMRP_CompletePPOrder_ReceiveFromMO_PartiallyUseThatQty_RunMRP()
	{
		helper.setToday(2014, 12, 1); // 1st of Dec
		final Timestamp dateOrdered = TimeUtil.addDays(helper.getToday(), +20);
		// final Timestamp dateOrdered = helper.getToday();

		//
		// Create a "Sales Order" demand
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01 // Warehouse
		);

		// Define initial validations (we will change them we we go on)
		final MRPExpectation<Object> picking01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_picking01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(100)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.balanced(false)
				.assertExpected();
		final MRPExpectation<Object> plant01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(0)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.balanced(true)
				.assertExpected();
		//
		// Run MRP
		helper.dumpMRPRecords("Before first run");
		runMRP();
		helper.dumpMRPRecords("After first run");
		// Validate
		final I_PP_Order ppOrder;
		{
			final List<I_PP_Order> ppOrders = new ArrayList<>();
			picking01_pSalad_MRPExpectation
					.qtyDemand(100)
					.qtySupply(100)
					.qtyOnHandReserved(0)
					.balanced()
					.assertExpected();
			plant01_pSalad_MRPExpectation
					.qtyDemand(100)
					.qtySupply(100)
					.qtyOnHandReserved(0)
					.balanced()
					.collectDocuments(I_PP_Order.class, ppOrders)
					.assertExpected();
			ppOrder = ListUtils.singleElement(ppOrders);
		}

		//
		// Complete the MO
		docActionBL.processEx(ppOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		helper.dumpMRPRecords("After Manufacturing Order Completed");

		//
		// Simulate:
		// * receiving the whole qty from MO.
		// * from the received QtyOnHand, partially use it
		{
			final BigDecimal qtyToReceive = ppOrder.getQtyOrdered();
			helper.receiveFromPPOrder(ppOrder, qtyToReceive);

			// Partially use a part of that qtyDelivered (i.e. subtract it from QtyOnHand)
			final BigDecimal qtyOnHandToUse = qtyToReceive.divide(BigDecimal.valueOf(2), 0, RoundingMode.UP);
			helper.mrpDAO.addQtyOnHand(ppOrder.getM_Warehouse(), ppOrder.getM_Product(), qtyOnHandToUse.negate());
		}
		helper.dumpMRPRecords("After Received from MO");

		//
		// Run MRP Again (second run)
		// NOTE: we will run it several times to make sure that nothing changes
		helper.mrpExecutor.createAllowMRPNodeRule()
				// .setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pSalad_2xTomato_1xOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		// skip MRP-060 on DD orders which are bringing the raw materials to Manufacturing Order because those ones are not completed but only MRP_AllowCleanup was set to false.
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pTomato)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		for (int iteration = 1; iteration <= 3; iteration++)
		{
			helper.runMRP();
			helper.dumpMRPRecords("After second run (iteration " + iteration + ")");
			// Validate
			{
				picking01_pSalad_MRPExpectation
						.qtyDemand(100)
						.qtySupply(100)
						.qtyOnHandReserved(0)
						.balanced()
						.assertExpected();
				plant01_pSalad_MRPExpectation
						.qtyDemand(100)
						.qtySupply(0) // nothing to supply because we received everything from our MO
						.qtyOnHandReserved(50) // qty received from MO, minus how much we consumed
						.notBalanced()
						.assertExpected();
			}
		}
	}

	/**
	 * Test case
	 * <ul>
	 * <li>configure: Picking, use Period Order Quantity (POQ)
	 * <li>create 3 x 100 sales orders demand
	 * <li>run MRP
	 * <li>expect 1 x DD Order, 1 x PP Order
	 * <li>complete the PP Order
	 * <li>create another 100 sales order demand
	 * <li>run MRP again
	 * <li>expect 1 x DD Order, 1 x PP Order ONLY for 100items
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_POQ_3SalesOrders_CompleteTheMO_NewSalesOrder()
	{
		helper.assumeMRP_POQ_Enabled();

		// Product Planning: Picking Warehouse, Salad Product: POQ, same day
		productPlanning_whPicking_pSalad.setOrder_Policy(X_PP_Product_Planning.ORDER_POLICY_PeriodOrderQuantity);
		productPlanning_whPicking_pSalad.setOrder_Period(BigDecimal.valueOf(1)); // same day
		InterfaceWrapperHelper.save(productPlanning_whPicking_pSalad);

		helper.setToday(2014, 12, 1); // 1st of Dec
		final Timestamp dateOrdered = TimeUtil.addDays(helper.getToday(), +20);

		//
		// Create 3 "Sales Order" demand
		for (int i = 1; i <= 3; i++)
		{
			helper.createMRPDemand(
					masterData.pSalad_2xTomato_1xOnion, // Product
					new BigDecimal("100"), // Demanded Qty
					dateOrdered, // Date
					masterData.plant01, // Plant
					masterData.warehouse_picking01, // Warehouse
					bparter01 // BPartner
			);
		}

		final MRPExpectation<Object> picking01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_picking01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(300)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.balanced(false)
				.bpartner(bparter01)
				.assertExpected()
				.bpartner(null)
				.assertExpected();
		final MRPExpectation<Object> plant01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(0)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.balanced(false)
				.assertExpected();

		//
		// Run MRP (first run)
		helper.runMRP();

		//
		// After first run:
		// * validate data
		// * get the generated PP Order
		{
			final List<I_PP_Order> ppOrdersAfterFirstRun = new ArrayList<>();
			final List<I_DD_Order> ddOrdersAfterFirstRun = new ArrayList<>();
			// Validate
			picking01_pSalad_MRPExpectation
					.qtyDemand(300)
					.qtySupply(300)
					.qtyOnHandReserved(0)
					.balanced()
					//
					.bpartner(bparter01)
					.collectDocuments(I_DD_Order.class, ddOrdersAfterFirstRun)
					.assertExpected()
					//
					.bpartner(null)
					.collectDocuments(I_DD_Order.class, null)
					.assertExpected()
			//
			;
			plant01_pSalad_MRPExpectation
					.qtyDemand(300) // i.e. the DD Order
					.qtySupply(300) // i.e. the manufacturing order
					.qtyOnHandReserved(0)
					.balanced()
					//
					.bpartner(bparter01)
					.collectDocuments(I_PP_Order.class, ppOrdersAfterFirstRun)
					.assertExpected()
					//
					.bpartner(null)
					.collectDocuments(I_PP_Order.class, null)
					.assertExpected()
			//
			;

			//
			// Get the Manufacturing Order generated to produce 300 x Salad
			final I_PP_Order ppOrder = ListUtils.singleElement(ppOrdersAfterFirstRun);
			// Validate
			Assert.assertEquals("Invalid PP Order - BPartner", bparter01, ppOrder.getC_BPartner());
			// Complete it
			docActionBL.processEx(ppOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			//
			// Get the DD Order to move 300 x Salad from Manufacturing Warehouse to Picking Warehouse
			final I_DD_Order ddOrder_PlantToPicking = ListUtils.singleElement(ddOrdersAfterFirstRun);
			// Make sure that the DD Order is same as getting the "forward DD Order of the manufacturing order"
			InterfaceWrapperHelper.refresh(ddOrder_PlantToPicking);
			Assert.assertEquals("Invalid forward DD Order",
					ddOrder_PlantToPicking, // expected
					helper.retrieveSingleForwardDDOrder(ppOrder));
		}

		//
		// Create another MRP demand
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01, // Warehouse
				bparter01 // BPartner
		);

		//
		// Run MRP (second run)
		// skip MRP-060 on DD order which moves from plant to picking because our dd order is not deleted anymore...
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_picking01)
				.setM_Product(masterData.pSalad_2xTomato_1xOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		// skip MRP-060 on DD orders which are bringing the raw materials to Manufacturing Order because those ones are not completed but only MRP_AllowCleanup was set to false.
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pTomato)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		MRPTracer.dumpMRPRecords("Before MRP second run");
		helper.runMRP();
		//
		// After second run:
		// * validate data
		{
			// Validate
			picking01_pSalad_MRPExpectation
					.qtyDemand(400)
					.qtySupply(400)
					.qtyOnHandReserved(0)
					.balanced()
					.assertExpected();
			plant01_pSalad_MRPExpectation
					.qtyDemand(400) // i.e. the DD Order
					.qtySupply(400) // i.e. the manufacturing order
					.qtyOnHandReserved(0)
					.balanced()
					.assertExpected();

			// TODO: validate the forward DD Order for the newly generated Manufacturing Order (for additional 100xpSalad)
		}

		helper.dumpMRPRecords();
	}

	/**
	 * Test case
	 * <ul>
	 * <li>configure: Picking, use Period Order Quantity (POQ)
	 * <li>create 3 x 100 sales orders demand
	 * <li>run MRP
	 * <li>expect 1 x DD Order, 1 x PP Order
	 * <li>complete the PP Order
	 * <li>receive from completed PP_Order
	 * <li>create another 100 sales order demand
	 * <li>run MRP again
	 * </ul>
	 */
	@Test
	@Ignore // gh #523: test doesn't work right now, and we might drop it in future
	public void test_POQ_3SalesOrders_CompleteTheMO_NewSalesOrder_ReceiveFromMO_RunMRPAgain()
	{
		helper.assumeMRP_POQ_Enabled();

		// Product Planning: Picking Warehouse, Salad Product: POQ, same day
		productPlanning_whPicking_pSalad.setOrder_Policy(X_PP_Product_Planning.ORDER_POLICY_PeriodOrderQuantity);
		productPlanning_whPicking_pSalad.setOrder_Period(BigDecimal.valueOf(1)); // same day
		InterfaceWrapperHelper.save(productPlanning_whPicking_pSalad);

		helper.setToday(2014, 12, 1); // 1st of Dec
		final Timestamp dateOrdered = TimeUtil.addDays(helper.getToday(), +20);

		//
		// Create 3 "Sales Order" demand
		for (int i = 1; i <= 3; i++)
		{
			helper.createMRPDemand(
					masterData.pSalad_2xTomato_1xOnion, // Product
					new BigDecimal("100"), // Demanded Qty
					dateOrdered, // Date
					masterData.plant01, // Plant
					masterData.warehouse_picking01, // Warehouse
					bparter01 // BPartner
			);
		}

		final MRPExpectation<Object> picking01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_picking01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(300)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.balanced(false)
				.bpartner(bparter01)
				.assertExpected()
				.bpartner(null)
				.assertExpected();
		final MRPExpectation<Object> plant01_pSalad_MRPExpectation = helper.newMRPExpectation()
				.warehouse(masterData.warehouse_plant01)
				.product(masterData.pSalad_2xTomato_1xOnion)
				.qtyDemand(0)
				.qtySupply(0)
				.qtyOnHandReserved(0)
				.balanced(false)
				.assertExpected();

		//
		// Run MRP (first run)
		helper.runMRP();

		//
		// After first run:
		// * validate data
		// * get the generated PP Order
		{
			final List<I_PP_Order> ppOrdersAfterFirstRun = new ArrayList<>();
			final List<I_DD_Order> ddOrdersAfterFirstRun = new ArrayList<>();
			// Validate
			picking01_pSalad_MRPExpectation
					.qtyDemand(300)
					.qtySupply(300)
					.qtyOnHandReserved(0)
					.balanced()
					//
					.bpartner(bparter01)
					.collectDocuments(I_DD_Order.class, ddOrdersAfterFirstRun)
					.assertExpected()
					//
					.bpartner(null)
					.collectDocuments(I_DD_Order.class, null)
					.assertExpected()
			//
			;
			plant01_pSalad_MRPExpectation
					.qtyDemand(300) // i.e. the DD Order
					.qtySupply(300) // i.e. the manufacturing order
					.qtyOnHandReserved(0)
					.balanced()
					//
					.bpartner(bparter01)
					.collectDocuments(I_PP_Order.class, ppOrdersAfterFirstRun)
					.assertExpected()
					//
					.bpartner(null)
					.collectDocuments(I_PP_Order.class, null)
					.assertExpected()
			//
			;

			//
			// Get the Manufacturing Order generated to produce 300 x Salad
			final I_PP_Order ppOrder = ListUtils.singleElement(ppOrdersAfterFirstRun);
			// Validate
			Assert.assertEquals("Invalid PP Order - BPartner", bparter01, ppOrder.getC_BPartner());
			Assert.assertEquals("Invalid PP Order - QtyOrdered", 300, ppOrder.getQtyOrdered().intValueExact());
			// Complete it
			docActionBL.processEx(ppOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

			//
			// Receive 125 items from MO
			helper.receiveFromPPOrder(ppOrder, BigDecimal.valueOf(125));
			// Consume 10items from QtyOnHand
			helper.mrpDAO.addQtyOnHand(ppOrder.getM_Warehouse(), ppOrder.getM_Product(), BigDecimal.valueOf(-10));

			//
			// Get the DD Order to move 300 x Salad from Manufacturing Warehouse to Picking Warehouse
			final I_DD_Order ddOrder_PlantToPicking = ListUtils.singleElement(ddOrdersAfterFirstRun);
			// Make sure that the DD Order is same as getting the "forward DD Order of the manufacturing order"
			InterfaceWrapperHelper.refresh(ddOrder_PlantToPicking);
			Assert.assertEquals("Invalid forward DD Order",
					ddOrder_PlantToPicking, // expected
					helper.retrieveSingleForwardDDOrder(ppOrder));
		}

		//
		// Create another MRP demand
		helper.createMRPDemand(
				masterData.pSalad_2xTomato_1xOnion, // Product
				new BigDecimal("100"), // Demanded Qty
				dateOrdered, // Date
				masterData.plant01, // Plant
				masterData.warehouse_picking01, // Warehouse
				bparter01 // BPartner
		);

		//
		// Run MRP (second run)
		// skip MRP-060 on DD order which moves from plant to picking because our dd order is not deleted anymore...
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_picking01)
				.setM_Product(masterData.pSalad_2xTomato_1xOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		// skip MRP-060 on DD orders which are bringing the raw materials to Manufacturing Order because those ones are not completed but only MRP_AllowCleanup was set to false.
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pTomato)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		helper.mrpExecutor.createAllowMRPNodeRule()
				.setM_Warehouse(masterData.warehouse_plant01)
				.setM_Product(masterData.pOnion)
				.setMRPCode(MRPExecutor.MRP_ERROR_060_SupplyDueButNotReleased);
		helper.runMRP();
		//
		// After second run:
		// * validate data
		{
			// Validate
			picking01_pSalad_MRPExpectation
					.qtyDemand(400)
					.qtySupply(400)
					.qtyOnHandReserved(0)
					.balanced()
					.assertExpected();
			plant01_pSalad_MRPExpectation
					.qtyDemand(400) // i.e. the DD Order
					.qtySupply(275) // i.e. the manufacturing order (400 to produce - 125 already produced)
					.qtyOnHandReserved(115) // 125 - 10items which we "consumed"
					.notBalanced() // because we have those 10items which "shall arrive"
					.assertExpected();

			// TODO: validate the forward DD Order for the newly generated Manufacturing Order (for additional 100xpSalad)
		}

		MRPTracer.dumpMRPRecords();
	}
}

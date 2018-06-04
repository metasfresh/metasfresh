package de.metas.inoutcandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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
import java.util.Collections;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;

import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.interfaces.I_M_Movement;

public class ReceiptSchedule_WarehouseDest_Test extends ReceiptScheduleTestBase
{
	private org.adempiere.warehouse.model.I_M_Warehouse warehouseForIssues;
	private I_M_Locator locatorForIssues;

	@Override
	public void setup()
	{
		warehouseForIssues = InterfaceWrapperHelper.create(ctx, org.adempiere.warehouse.model.I_M_Warehouse.class, ITrx.TRXNAME_None);
		warehouseForIssues.setIsIssueWarehouse(true);
		warehouseForIssues.setName("Warehouse for Issues");
		warehouseForIssues.setValue("Warehouse for Issues");
		InterfaceWrapperHelper.save(warehouseForIssues);

		locatorForIssues = createLocator(warehouseForIssues);

	}

	/**
	 * Regression integration test for http://dewiki908/mediawiki/index.php/05946_Wareneingang_Lagerumbuchung_%28101188685084%29
	 * 
	 * Case:
	 * <ul>
	 * <li>checks if M_Warehouse_ID and M_Warehouse_Dest_ID are set correctly in M_ReceiptSchedule and M_InOut
	 * <li>checks if Movement from warehouse to warehouse Dest is correctly made
	 * </ul>
	 */
	@Test
	public void testOrderLineReceiptScheduleProducer()
	{
		//
		// Create Order & Line
		// Warehouse: 1
		// Product's warehouse: 2
		final I_C_Order order1 = createOrder(warehouse1);
		final I_C_OrderLine order1_line1_product1_wh1 = createOrderLine(order1, product3_wh2);

		//
		// Call producer to generate the schedule from order's line
		final IReceiptScheduleProducer rsProducer = Services.get(IReceiptScheduleProducerFactory.class)
				.createProducer(I_C_Order.Table_Name, false); // async=false
		final List<I_M_ReceiptSchedule> previousSchedules = Collections.emptyList();
		final List<I_M_ReceiptSchedule> schedules = rsProducer.createOrUpdateReceiptSchedules(order1, previousSchedules);

		//
		// Check produced schedules count: it shall be only one
		Assert.assertEquals("Only one receipt schedule shall be produced", 1, schedules.size());
		final I_M_ReceiptSchedule schedule = schedules.get(0);

		//
		// Check Schedule's Warehouses
		Assert.assertEquals("Invalid M_ReceiptSchedule.M_Warehouse_ID",
				order1.getM_Warehouse(),
				schedule.getM_Warehouse());
		Assert.assertEquals("Invalid M_ReceiptSchedule.C_BPartner",
				order1_line1_product1_wh1.getC_BPartner(),
				schedule.getC_BPartner());
		Assert.assertEquals("Invalid M_ReceiptSchedule.M_Warehouse_Override_ID",
				null, // shall not be set
				schedule.getM_Warehouse_Override());
		Assert.assertEquals("Invalid M_ReceiptSchedule.M_Warehouse_Dest_ID",
				order1_line1_product1_wh1.getM_Product().getM_Locator().getM_Warehouse(),
				schedule.getM_Warehouse_Dest());
		// Guard agaist testing error
		Assert.assertFalse("M_ReceiptSchedule M_Warehouse_ID != M_Warehouse_Dest_ID: " + schedule,
				schedule.getM_Warehouse_ID() == schedule.getM_Warehouse_Dest_ID());

		//
		// Generate Receipt
		final InOutGenerateResult receiptGenerateResult = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		final IInOutProducer receiptProducer = receiptScheduleBL.createInOutProducer(receiptGenerateResult, false); // complete=false
		receiptScheduleBL.generateInOuts(ctx, receiptProducer, schedules.iterator());

		//
		// Check generated receipts count: it shall be only one
		final List<I_M_InOut> receipts = receiptGenerateResult.getInOuts();
		Assert.assertEquals("Only one receipt shall be produced", 1, receipts.size());
		final I_M_InOut receipt = receipts.get(0);
		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt,I_M_InOutLine.class);
		final I_M_InOutLine receiptLine1 = InterfaceWrapperHelper.create(receiptLines.get(0), I_M_InOutLine.class);

		// Check receipt's warehouse
		Assert.assertEquals("Invalid M_InOut.M_Warehouse_ID",
				order1.getM_Warehouse(),
				receipt.getM_Warehouse());
		Assert.assertEquals("Invalid M_ReceiptSchedule.M_Warehouse_Dest_ID",
				order1_line1_product1_wh1.getM_Product().getM_Locator().getM_Warehouse(),
				receiptLine1.getM_Warehouse_Dest());

		//
		// Generate Movement from receipt
		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(receiptLines);
		Assert.assertNotNull("Movement shall be generated", movements);
		Assert.assertEquals("Only one movement shall be generated", 1, movements.size());
		final I_M_Movement movement = movements.get(0);

		//
		// Check Movement Line
		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movement);
		Assert.assertEquals("Movement shall have only one line", 1, movementLines.size());
		final I_M_MovementLine movementLine = movementLines.get(0);
		// Check Movement Line warehouses
		Assert.assertEquals("Invalid movement line Locator (from)",
				receipt.getM_Warehouse(),
				movementLine.getM_Locator().getM_Warehouse());
		Assert.assertEquals("Invalid movement line Locator (to)",
				receiptLine1.getM_Warehouse_Dest(),
				movementLine.getM_LocatorTo().getM_Warehouse());
		// Check Movement Line product & qty
		Assert.assertEquals("Invalid movement line product (compared with order line's product)",
				order1_line1_product1_wh1.getM_Product(),
				movementLine.getM_Product());
		Assert.assertThat("Invalid movement line Qty (compared with order line's qty)",
				movementLine.getMovementQty(), // actual
				Matchers.comparesEqualTo(order1_line1_product1_wh1.getQtyOrdered()));
	}

	@Test
	public void testGenerateMovementFromReceipt_2Movements()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");

		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);

		final BigDecimal qty1 = new BigDecimal(300);
		createReceiptLine("Product1", receiptLocator, receipt, qty1, false);
		final BigDecimal qty2 = new BigDecimal(13);
		createReceiptLine("Product2", receiptLocator, receipt, qty2, true);

		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt,I_M_InOutLine.class);
		
		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(receiptLines);

		Assert.assertEquals("2 movements shall be created", 2, movements.size());

		Assert.assertEquals("M_InOut_ID shall be set in the movements", receipt, movements.get(0).getM_InOut());
		Assert.assertEquals("M_InOut_ID shall be set in the movements", receipt, movements.get(1).getM_InOut());

	}

	@Test
	public void testGenerateMovementFromReceipt_1MovementWithIssues()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");

		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);

		final BigDecimal qty2 = new BigDecimal(13);
		final I_M_InOutLine receiptLine = createReceiptLine("Product2", receiptLocator, receipt, qty2, true);

		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt,I_M_InOutLine.class);
		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(receiptLines);

		Assert.assertEquals("1 movements shall be created", 1, movements.size());

		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movements.get(0));

		Assert.assertEquals("1 movement line shall be created", 1, movementLines.size());

		Assert.assertEquals("Wrong qty in movement line", receiptLine.getMovementQty(), movementLines.get(0).getMovementQty());
		Assert.assertEquals("Wrong product in movement line", receiptLine.getM_Product(), movementLines.get(0).getM_Product());
		Assert.assertEquals("Wrong locator in movement line", receiptLocator, movementLines.get(0).getM_Locator());
		Assert.assertEquals("Wrong locator To in movement line", locatorForIssues, movementLines.get(0).getM_LocatorTo());

	}

	@Test
	public void testGenerateMovementFromReceipt_1MovementWithoutIssues()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");

		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);

		final BigDecimal qty1 = new BigDecimal(300);
		final I_M_InOutLine receiptLine = createReceiptLine("Product1", receiptLocator, receipt, qty1, false);

		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt,I_M_InOutLine.class);
		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(receiptLines);

		Assert.assertEquals("1 movements shall be created", 1, movements.size());

		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movements.get(0));

		Assert.assertEquals("1 movement line shall be created", 1, movementLines.size());

		Assert.assertEquals("Wrong qty in movement line", receiptLine.getMovementQty(), movementLines.get(0).getMovementQty());
		Assert.assertEquals("Wrong product in movement line", receiptLine.getM_Product(), movementLines.get(0).getM_Product());
		Assert.assertEquals("Wrong locator in movement line", receiptLocator, movementLines.get(0).getM_Locator());
		Assert.assertEquals("Wrong warehouse To in movement line", receipt.getM_Warehouse_Dest(), movementLines.get(0).getM_LocatorTo().getM_Warehouse());

	}

	@Test
	public void testGenerateMovementFromReceipt_SameWarehouse_NoIssues()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");

		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);
		receipt.setM_Warehouse_Dest_ID(receiptWarehouse.getM_Warehouse_ID());
		InterfaceWrapperHelper.save(receipt);

		final BigDecimal qty1 = new BigDecimal(300);
		createReceiptLine("Product1", receiptLocator, receipt, qty1, false);

		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt,I_M_InOutLine.class);
		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(receiptLines);

		Assert.assertTrue("No movement shall be created is the warehouses from receipt are identical", movements.isEmpty());

	}

	@Test
	public void testGenerateMovementFromReceipt_SameWarehouse_WithIssues()
	{
		final I_M_Warehouse receiptWarehouse = createWarehouse("Receipt Warehouse");

		final I_M_Locator receiptLocator = createLocator(receiptWarehouse);

		final I_M_InOut receipt = createReceipt(receiptLocator);
		receipt.setM_Warehouse_Dest_ID(receiptWarehouse.getM_Warehouse_ID());
		InterfaceWrapperHelper.save(receipt);

		final BigDecimal qty1 = new BigDecimal(300);
		createReceiptLine("Product1", receiptLocator, receipt, qty1, false);

		final BigDecimal qty2 = new BigDecimal(13);
		final I_M_InOutLine receiptLine = createReceiptLine("Product2", receiptLocator, receipt, qty2, true);

		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt,I_M_InOutLine.class);
		
		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(receiptLines);

		Assert.assertEquals("1 movements shall be created", 1, movements.size());

		final List<I_M_MovementLine> movementLines = Services.get(IMovementDAO.class).retrieveLines(movements.get(0));

		Assert.assertEquals("1 movement line shall be created", 1, movementLines.size());

		Assert.assertEquals("Wrong qty in movement line", receiptLine.getMovementQty(), movementLines.get(0).getMovementQty());
		Assert.assertEquals("Wrong product in movement line", receiptLine.getM_Product(), movementLines.get(0).getM_Product());
		Assert.assertEquals("Wrong locator in movement line", receiptLocator, movementLines.get(0).getM_Locator());
		Assert.assertEquals("Wrong locator To in movement line", locatorForIssues, movementLines.get(0).getM_LocatorTo());

	}

	
	public I_M_InOut createReceipt(final I_M_Locator receiptLocator)
	{

		final I_M_Warehouse destinationWarehouse = createWarehouse("Destination Warehouse");

		final I_C_BPartner receiptPartner = createBPartner("Receipt Partner");

		// NOTE: we need to use some dummy transaction, else movement generation will fail
		final String trxName = Services.get(ITrxManager.class).createTrxName("DummyTrx", true);

		final I_M_InOut receipt = InterfaceWrapperHelper.create(ctx, I_M_InOut.class, trxName);
		receipt.setAD_Org_ID(receiptLocator.getAD_Org_ID());
		receipt.setM_Warehouse_ID(receiptLocator.getM_Warehouse_ID());
		receipt.setM_Warehouse_Dest_ID(destinationWarehouse.getM_Warehouse_ID());
		receipt.setC_BPartner_ID(receiptPartner.getC_BPartner_ID());
		InterfaceWrapperHelper.save(receipt);

		return receipt;
	}

	public I_M_InOutLine createReceiptLine(final String productName,
			final I_M_Locator locator,
			final I_M_InOut receipt,
			final BigDecimal qty,
			final boolean isInDispute)
	{
		final I_M_Product product = createProduct(productName, locator);

		final I_M_InOutLine line = InterfaceWrapperHelper.create(ctx, I_M_InOutLine.class, ITrx.TRXNAME_None);

		line.setAD_Org_ID(receipt.getAD_Org_ID());
		line.setM_Product_ID(product.getM_Product_ID());
		line.setMovementQty(qty);
		line.setIsInDispute(isInDispute);
		line.setM_InOut_ID(receipt.getM_InOut_ID());
		line.setM_Locator_ID(locator.getM_Locator_ID());

		// NOTE: we are apply this "warehouseForIssues" rule here, because it is not anymore a generic rule
		// but a rule only for particular receipt generation
		if (line.isInDispute())
		{
			line.setM_Warehouse_Dest(warehouseForIssues);
		}

		InterfaceWrapperHelper.save(line);

		return line;
	}
}

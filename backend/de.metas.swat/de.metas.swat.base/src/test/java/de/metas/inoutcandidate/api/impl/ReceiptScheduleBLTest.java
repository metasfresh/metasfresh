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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Test;

import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;

public class ReceiptScheduleBLTest extends ReceiptScheduleTestBase
{
	final boolean complete = true;

	@Override
	protected void setup()
	{
		// nothing
	}

	/**
	 * The schedules must be ordered by bpartner, warehouse and ordered date.
	 */
	@Test
	public void testGenerateReceiptScheduleOrderedByBpartner()
	{
		final Iterator<I_M_ReceiptSchedule> schedules = Arrays.asList(
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product2_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10)
				).iterator();

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedules, result, complete);

		Assert.assertEquals("2 receipts shall be generated", 2, result.getInOutCount());
	}

	@Test
	public void testGenerateReceiptScheduleNotOrderedByBpartner()
	{
		final List<I_M_ReceiptSchedule> schedules = Arrays.asList(
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product2_wh1, 10)
				);

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedules.iterator(), result, complete);

		Assert.assertEquals("Invalid amount of generated receipts", 4, result.getInOutCount());

		final List<I_M_InOut> receipts = result.getInOuts();
		assertMatches(schedules.get(0), receipts.get(0));
		assertMatches(schedules.get(1), receipts.get(0));
		assertMatches(schedules.get(2), receipts.get(1));
		assertMatches(schedules.get(3), receipts.get(2));
		assertMatches(schedules.get(4), receipts.get(3));
	}

	@Test
	public void testGenerateReceiptScheduleOrderedByBpartnerWarehouseDate()
	{
		final Iterator<I_M_ReceiptSchedule> schedules = Arrays.asList(
				createReceiptSchedule(bpartner1, warehouse1, SystemTime.asDayTimestamp(), product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, SystemTime.asDayTimestamp(), product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, SystemTime.asDayTimestamp(), product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, SystemTime.asDayTimestamp(), product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, SystemTime.asDayTimestamp(), product1_wh1, 10)
				).iterator();

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedules, result, complete);

		Assert.assertEquals("Invalid amount of generated receipts", 1, result.getInOutCount());
	}

	@Test
	public void testGenerateReceiptScheduleOrderedByBpartnerWarehouse()
	{
		final Iterator<I_M_ReceiptSchedule> schedules = Arrays.asList(
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10)
				).iterator();

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedules, result, complete);

		Assert.assertEquals("Invalid amount of generated receipts", 2, result.getInOutCount());
	}

	@Test
	public void testRetrieveMInOutsFromReceiptSchedule()
	{
		final List<I_M_ReceiptSchedule> schedulesList = Arrays.asList(createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10));

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedulesList.iterator(), result, complete);

		final I_M_ReceiptSchedule schedule = schedulesList.get(0);
		final List<I_M_InOut> receipts = result.getInOuts();

		assertMatches(schedule, receipts.get(0));
	}

	/**
	 * 3 receipt schedules, 2 receipts
	 */
	@Test
	public void testRetrieveMInOutsFromReceiptSchedule2()
	{
		final List<I_M_ReceiptSchedule> schedulesList = Arrays.asList(
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10));

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedulesList.iterator(), result, complete);

		final I_M_ReceiptSchedule schedule1 = schedulesList.get(0);
		final I_M_ReceiptSchedule schedule2 = schedulesList.get(1);
		final I_M_ReceiptSchedule schedule3 = schedulesList.get(2);
		final List<I_M_InOut> receipts = result.getInOuts();

		assertMatches(schedule1, receipts.get(0));
		assertMatches(schedule2, receipts.get(0));
		assertMatches(schedule3, receipts.get(1));
	}

	/**
	 * 1 receipt schedule, 1 receipt line
	 */
	@Test
	public void testReceiptLines()
	{
		final List<I_M_ReceiptSchedule> schedulesList = Arrays.asList(createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10));
		schedulesList.get(0).setC_OrderLine_ID(111);
		schedulesList.get(0).setC_UOM_ID(1);
		schedulesList.get(0).setAD_Org_ID(3);
		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedulesList.iterator(), result, complete);
		final IInOutDAO inOutDao = Services.get(IInOutDAO.class);

		final I_M_ReceiptSchedule schedule = schedulesList.get(0);
		final List<I_M_InOut> receipts = result.getInOuts();

		final List<I_M_InOutLine> receiptLine = inOutDao.retrieveLines(receipts.get(0));
		assertLineMatches(schedule, receiptLine.get(0));

	}

	/**
	 * 3 receipt schedules 2 receipt lines
	 */
	@Test
	public void testReceiptLines2()
	{
		final List<I_M_ReceiptSchedule> schedulesList = Arrays.asList(
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10));

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedulesList.iterator(), result, complete);
		final IInOutDAO inOutDao = Services.get(IInOutDAO.class);

		final I_M_ReceiptSchedule schedule1 = schedulesList.get(0);
		final I_M_ReceiptSchedule schedule2 = schedulesList.get(1);
		final I_M_ReceiptSchedule schedule3 = schedulesList.get(2);
		final List<I_M_InOut> receipts = result.getInOuts();

		final List<I_M_InOutLine> receiptLines1 = inOutDao.retrieveLines(receipts.get(0));
		final List<I_M_InOutLine> receiptLines2 = inOutDao.retrieveLines(receipts.get(1));

		assertLineMatches(schedule1, receiptLines1.get(0));
		assertLineMatches(schedule2, receiptLines1.get(1));
		assertLineMatches(schedule3, receiptLines2.get(0));

	}

	private void assertMatches(final I_M_ReceiptSchedule schedule, final I_M_InOut receipt)
	{
		final Timestamp movementDateExpected = Env.getDate(ctx);
		Assert.assertEquals("AD_User_IDs do not match", schedule.getAD_User_ID(), receipt.getAD_User_ID());
		Assert.assertEquals("AD_Org_IDs do not match", schedule.getAD_Org_ID(), receipt.getAD_Org_ID());
		Assert.assertEquals("C_BPartner_IDs do not match", schedule.getC_BPartner_ID(), receipt.getC_BPartner_ID());
		Assert.assertEquals("DateOrdered do not match", schedule.getDateOrdered(), receipt.getDateOrdered());
		Assert.assertEquals("MovementDate do not match", movementDateExpected, receipt.getMovementDate());
		Assert.assertEquals("M_Warehouse_IDs do not match", schedule.getM_Warehouse_ID(), receipt.getM_Warehouse_ID());
		// ts: the doctypes cannot match because 'schedule' has the purchase order's doctype and the receipt has a receipt-doctype
		// Assert.assertEquals("C_DocType_IDs do not match", schedule.getC_DocType_ID(), receipt.getC_DocType_ID());
	}

	private void assertLineMatches(final I_M_ReceiptSchedule schedule, final I_M_InOutLine receiptLine)
	{
		Assert.assertEquals("AD_Org_IDs do not match", schedule.getAD_Org_ID(), receiptLine.getAD_Org_ID());
		Assert.assertEquals("M_Product_IDs do not match", schedule.getM_Product_ID(), receiptLine.getM_Product_ID());
		Assert.assertEquals("QtysEntered do not match", schedule.getQtyOrdered(), receiptLine.getQtyEntered());

		Assert.assertEquals("MovementQtys do not match", schedule.getQtyToMove(), receiptLine.getMovementQty());
		// Assert.assertEquals("MovementQtys do not match", receiptScheduleBL.getQtyToMove(schedule), receiptLine.getMovementQty());

		Assert.assertEquals("C_UOM_IDs do not match", schedule.getC_UOM_ID(), receiptLine.getC_UOM_ID());
		Assert.assertEquals("C_OrderLine_IDs do not match", schedule.getC_OrderLine_ID(), receiptLine.getC_OrderLine_ID());
	}
}

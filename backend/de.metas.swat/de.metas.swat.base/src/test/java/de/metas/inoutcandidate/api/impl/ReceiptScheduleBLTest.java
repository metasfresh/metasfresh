package de.metas.inoutcandidate.api.impl;

import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.inout.IInOutDAO;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.product.ProductId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_InOutLine;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
public class ReceiptScheduleBLTest extends ReceiptScheduleTestBase
{
	final boolean complete = true;

	@Override
	protected void setup()
	{
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
				createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10)).iterator();

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedules, result, complete);

		Assertions.assertEquals(2, result.getInOutCount(), "2 receipts shall be generated");
	}

	@Test
	public void testGenerateReceiptScheduleNotOrderedByBpartner()
	{
		final List<I_M_ReceiptSchedule> schedules = Arrays.asList(
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner2, warehouse1, date, product2_wh1, 10));

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedules.iterator(), result, complete);

		Assertions.assertEquals(4, result.getInOutCount(), "Invalid amount of generated receipts");

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
				createReceiptSchedule(bpartner1, warehouse1, de.metas.common.util.time.SystemTime.asDayTimestamp(), product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, de.metas.common.util.time.SystemTime.asDayTimestamp(), product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, SystemTime.asDayTimestamp(), product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, de.metas.common.util.time.SystemTime.asDayTimestamp(), product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, de.metas.common.util.time.SystemTime.asDayTimestamp(), product1_wh1, 10)).iterator();

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedules, result, complete);

		Assertions.assertEquals(1, result.getInOutCount(), "Invalid amount of generated receipts");
	}

	@Test
	public void testGenerateReceiptScheduleOrderedByBpartnerWarehouse()
	{
		final Iterator<I_M_ReceiptSchedule> schedules = Arrays.asList(
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10),
				createReceiptSchedule(bpartner1, warehouse2, date, product1_wh1, 10)).iterator();

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true
		receiptScheduleBL.generateInOuts(ctx, schedules, result, complete);

		Assertions.assertEquals(2, result.getInOutCount(), "Invalid amount of generated receipts");
	}

	@Test
	public void testRetrieveMInOutsFromReceiptSchedule()
	{
		final List<I_M_ReceiptSchedule> schedulesList = Arrays.asList(createReceiptSchedule(bpartner1, warehouse1, date, product1_wh1, 10));

		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true

		// invoke method under test
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

		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("ReceiptScheduleUOM");
		BusinessTestHelper.createUOMConversion(CreateUOMConversionRequest.builder()
				.productId(ProductId.ofRepoId(product1_wh1.getM_Product_ID()))
				.fromUomId(UomId.ofRepoId(product1_wh1.getC_UOM_ID()))
				.toUomId(UomId.ofRepoId(uomRecord.getC_UOM_ID()))
				.fromToMultiplier(BigDecimal.ONE)
				.build());

		schedulesList.get(0).setC_OrderLine_ID(111);
		schedulesList.get(0).setC_UOM_ID(uomRecord.getC_UOM_ID());
		schedulesList.get(0).setAD_Org_ID(3);
		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class).createEmptyInOutGenerateResult(true); // storeReceipts=true

		// invoke the method under test
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
		Assertions.assertEquals(schedule.getAD_User_ID(), receipt.getAD_User_ID(), "AD_User_IDs do not match");
		Assertions.assertEquals(schedule.getAD_Org_ID(), receipt.getAD_Org_ID(), "AD_Org_IDs do not match");
		Assertions.assertEquals(schedule.getC_BPartner_ID(), receipt.getC_BPartner_ID(), "C_BPartner_IDs do not match");
		Assertions.assertEquals(schedule.getDateOrdered(), receipt.getDateOrdered(), "DateOrdered do not match");
		Assertions.assertEquals(movementDateExpected, receipt.getMovementDate(), "MovementDate do not match");
		Assertions.assertEquals(schedule.getM_Warehouse_ID(), receipt.getM_Warehouse_ID(), "M_Warehouse_IDs do not match");
		// ts: the doctypes cannot match because 'schedule' has the purchase order's doctype and the receipt has a receipt-doctype
		// Assertions.assertEquals( schedule.getC_DocType_ID(),  receipt.getC_DocType_ID(), "C_DocType_IDs do not match");
	}

	private void assertLineMatches(final I_M_ReceiptSchedule schedule, final I_M_InOutLine receiptLine)
	{
		Assertions.assertEquals(schedule.getAD_Org_ID(), receiptLine.getAD_Org_ID(), "AD_Org_IDs do not match");
		Assertions.assertEquals(schedule.getM_Product_ID(), receiptLine.getM_Product_ID(), "M_Product_IDs do not match");
		Assertions.assertEquals(schedule.getQtyOrdered(), receiptLine.getQtyEntered(), "QtysEntered do not match");

		Assertions.assertEquals(schedule.getQtyToMove(), receiptLine.getMovementQty(), "MovementQtys do not match");
		// Assertions.assertEquals( receiptScheduleBL.getQtyToMove(schedule),  receiptLine.getMovementQty(), "MovementQtys do not match");

		if (schedule.getC_UOM_ID() > 0)
		{
			Assertions.assertEquals(schedule.getC_UOM_ID(), receiptLine.getC_UOM_ID(), "C_UOM_IDs do not match");
		}
		Assertions.assertEquals(schedule.getC_OrderLine_ID(), receiptLine.getC_OrderLine_ID(), "C_OrderLine_IDs do not match");
	}
}

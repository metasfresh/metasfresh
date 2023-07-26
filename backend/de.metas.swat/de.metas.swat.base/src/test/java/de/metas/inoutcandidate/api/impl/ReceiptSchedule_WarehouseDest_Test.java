package de.metas.inoutcandidate.api.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.metas.document.dimension.DimensionFactory;
import de.metas.document.dimension.DimensionService;
import de.metas.document.dimension.InOutLineDimensionFactory;
import de.metas.document.dimension.OrderLineDimensionFactory;
import de.metas.inoutcandidate.document.dimension.ReceiptScheduleDimensionFactory;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mmovement.api.IMovementDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.adempiere.warehouse.model.I_M_Warehouse;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MovementLine;
import org.compiere.model.I_M_Product;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import de.metas.acct.api.IProductAcctDAO;
import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IInOutProducer;
import de.metas.inoutcandidate.api.IReceiptScheduleProducerFactory;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.spi.IReceiptScheduleProducer;
import de.metas.interfaces.I_M_Movement;
import de.metas.product.IProductActivityProvider;
import de.metas.product.IProductDAO;
import de.metas.util.Services;

public class ReceiptSchedule_WarehouseDest_Test extends ReceiptScheduleTestBase
{

	@Override
	public void setup()
	{
		final I_M_Warehouse warehouseForIssues = InterfaceWrapperHelper.create(ctx, I_M_Warehouse.class, ITrx.TRXNAME_None);
		warehouseForIssues.setIsIssueWarehouse(true);
		warehouseForIssues.setName("Warehouse for Issues");
		warehouseForIssues.setValue("Warehouse for Issues");
		InterfaceWrapperHelper.save(warehouseForIssues);
		createLocator(warehouseForIssues);

		Services.registerService(IProductActivityProvider.class, Services.get(IProductAcctDAO.class));
	}

	/**
	 * Regression integration test for http://dewiki908/mediawiki/index.php/05946_Wareneingang_Lagerumbuchung_%28101188685084%29
	 * <p>
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
							order1.getM_Warehouse_ID(),
							schedule.getM_Warehouse_ID());
		Assert.assertEquals("Invalid M_ReceiptSchedule.C_BPartner",
							order1_line1_product1_wh1.getC_BPartner_ID(),
							schedule.getC_BPartner_ID());
		Assert.assertEquals("Invalid M_ReceiptSchedule.M_Warehouse_Override_ID",
							0, // shall not be set
							schedule.getM_Warehouse_Override_ID());

		Assert.assertEquals("Invalid M_ReceiptSchedule.M_Warehouse_Dest_ID",
							extractProductWarehouseId(order1_line1_product1_wh1),
							WarehouseId.ofRepoIdOrNull(schedule.getM_Warehouse_Dest_ID()));
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
		List<I_M_InOutLine> receiptLines = Services.get(IInOutDAO.class).retrieveLines(receipt, I_M_InOutLine.class);

		// Check receipt's warehouse
		Assert.assertEquals("Invalid M_InOut.M_Warehouse_ID",
							order1.getM_Warehouse_ID(),
							receipt.getM_Warehouse_ID());

		//
		// Generate Movement from receipt
		final List<I_M_Movement> movements = Services.get(IInOutMovementBL.class).generateMovementFromReceiptLines(receiptLines, null);
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
							receipt.getM_Warehouse_ID(),
							movementLine.getM_Locator().getM_Warehouse_ID());
		Assert.assertEquals("Invalid movement line Locator (to)",
							schedule.getM_Warehouse_Dest_ID(),
							movementLine.getM_LocatorTo().getM_Warehouse_ID());
		// Check Movement Line product & qty
		Assert.assertEquals("Invalid movement line product (compared with order line's product)",
							order1_line1_product1_wh1.getM_Product_ID(),
							movementLine.getM_Product_ID());
		Assert.assertThat("Invalid movement line Qty (compared with order line's qty)",
						  movementLine.getMovementQty(), // actual
						  Matchers.comparesEqualTo(order1_line1_product1_wh1.getQtyOrdered()));
	}

	private WarehouseId extractProductWarehouseId(final I_C_OrderLine orderLine)
	{
		final I_M_Product product = Services.get(IProductDAO.class).getById(orderLine.getM_Product_ID());

		final IWarehouseDAO warehousesRepo = Services.get(IWarehouseDAO.class);
		return warehousesRepo.getWarehouseIdByLocatorRepoId(product.getM_Locator_ID());
	}
}

package de.metas.fresh.ordercheckup;

/*
 * #%L
 * de.metas.fresh.base
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


import de.metas.adempiere.model.I_M_Product;
import de.metas.fresh.model.I_C_Order_MFGWarehouse_Report;
import de.metas.document.engine.DocStatus;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.user.UserId;
import org.adempiere.ad.wrapper.POJOLookupMap;
import de.metas.document.engine.DocumentWrapper;
import de.metas.document.engine.IDocument;
import de.metas.util.Services;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test case:
 * <ul>
 * <li>create an order with products
 * <li>generate {@link I_C_Order_MFGWarehouse_Report}s
 * <li>enqueue all reports to printing
 * <li>assert: reports were correctly generated
 * <li>assert: printing queue item has the right user to print set
 * </ul>
 *
 * @author tsa
 *
 */
public class OrderCheckup_IntegrationTest
{
	private OrderCheckupTestHelper helper;
	private Masterdata masterdata;

	@BeforeEach
	public void init()
	{
		helper = new OrderCheckupTestHelper();
		helper.init();

		masterdata = helper.createMasterdata();
	}

	@Test
	public void test()
	{
		final I_C_Order order = helper.createSalesOrder(masterdata.plant01.warehouse01.warehouse);

		final I_C_OrderLine ol1 = helper.createOrderLine(order, masterdata.plant01.warehouse01.product01);
		final I_C_OrderLine ol2 = helper.createOrderLine(order, masterdata.plant01.warehouse01.product02);
		final I_C_OrderLine ol3 = helper.createOrderLine(order, masterdata.plant01.warehouse02.product01);
		final I_C_OrderLine ol4 = helper.createOrderLine(order, masterdata.plant01.warehouse02.product02);
		//
		final I_C_OrderLine ol5 = helper.createOrderLine(order, masterdata.plant02.warehouse01.product01);
		final I_C_OrderLine ol6 = helper.createOrderLine(order, masterdata.plant02.warehouse01.product02);
		final I_C_OrderLine ol7 = helper.createOrderLine(order, masterdata.plant02.warehouse02.product01);
		final I_C_OrderLine ol8 = helper.createOrderLine(order, masterdata.plant02.warehouse02.product02);

		// Generate reports:
		helper.generateReportsAndEnqueueToPrinting(order);

		//
		// Expectations
		// masterdata.plant01.assertPlantReportOrderLines(ol1, ol2, ol3, ol4); // old expectation, now we expect ALL lines
		masterdata.plant01.assertPlantReportOrderLines(ol1, ol2, ol3, ol4, ol5, ol6, ol7, ol8);
		masterdata.plant01.warehouse01.assertWarehouseReportOrderLines(ol1, ol2);
		masterdata.plant01.warehouse02.assertWarehouseReportOrderLines(ol3, ol4);

		// masterdata.plant02.assertPlantReportOrderLines(ol5, ol6, ol7, ol8); // old expectation, now we expect ALL lines
		masterdata.plant01.assertPlantReportOrderLines(ol1, ol2, ol3, ol4, ol5, ol6, ol7, ol8);
		masterdata.plant02.warehouse01.assertWarehouseReportOrderLines(ol5, ol6);
		masterdata.plant02.warehouse02.assertWarehouseReportOrderLines(ol7, ol8);
	}

	/**
	 * AC-P1: a Warehouse (Produktion) report whose manufacturing routing has no Betreuer
	 * (AD_User_InCharge_ID) must fall back to the plant resource's user, so its printing-queue item
	 * stays active instead of being cancelled for lack of a print user.
	 */
	@Test
	public void warehouseReport_fallsBackToPlantUser_whenRoutingHasNoBetreuer()
	{
		final I_AD_User plantUser = helper.createAD_User("plantUser");
		final I_S_Resource plant = helper.createPlant("plantWithUser", plantUser);
		final I_M_Warehouse warehouse = helper.createWarehouse("whNoBetreuer", plant);
		final I_M_Product product = helper.createProductWithoutRoutingUserInCharge("prodNoBetreuer", warehouse);

		final I_C_Order order = helper.createSalesOrder(warehouse);
		helper.createOrderLine(order, product);

		Services.get(IOrderCheckupBL.class).generateReportsIfEligible(order);

		final I_C_Order_MFGWarehouse_Report whReport =
				helper.retrieveReport(OrderCheckupDocumentType.Warehouse, warehouse, plant);
		assertThat(whReport).as("Warehouse report exists").isNotNull();
		assertThat(whReport.getAD_User_Responsible_ID())
				.as("Warehouse report should fall back to the plant user when the routing has no Betreuer")
				.isEqualTo(plantUser.getAD_User_ID());

		// and it must actually stay active in the printing queue (recipient = the fallback user)
		helper.enqueueToPrinting(order);
	}

	/**
	 * AC-P2: the OrderCheckupReport document handler makes the record wrap-able as a document that exposes
	 * its DocStatus column - which is what the document engine copies onto the C_Doc_Outbound_Log. The
	 * provider-lookup itself only runs with a Spring context (AbstractDocumentBL short-circuits to an empty
	 * map otherwise), so the end-to-end "log carries CO" is covered at integration/UAT level; here we pin
	 * the unit-level contract: wrapping the record through the handler yields its DocStatus.
	 */
	@Test
	public void documentHandler_exposesTheRecordDocStatus()
	{
		final I_C_Order_MFGWarehouse_Report report = org.adempiere.model.InterfaceWrapperHelper.newInstance(I_C_Order_MFGWarehouse_Report.class);
		report.setDocStatus("CO");
		org.adempiere.model.InterfaceWrapperHelper.save(report);

		final IDocument doc = DocumentWrapper.wrapModelUsingHandler(report, new OrderCheckupReportDocumentHandler());

		assertThat(DocStatus.ofNullableCode(doc.getDocStatus()))
				.as("wrapping the report through its handler must expose the record's DocStatus")
				.isEqualTo(DocStatus.Completed);
		assertThat(doc.getSummary()).as("summary must not throw").isNotBlank();
	}

	/**
	 * AC-P3: with a printer routing + matching configured, an ACTIVE Produktion queue item (kept active by the
	 * plant-user fallback) flows all the way through PrintJobBL.createPrintJobs to a real C_Print_Job. This is
	 * the end-to-end proof that, once the printer is configured, the Produktion sheet actually prints -- the
	 * customer's original "nothing comes out" symptom. The per-instance AD_PrinterRouting stays customer config.
	 */
	@Test
	public void activeProduktionQueueItem_withPrinterConfigured_producesPrintJob()
	{
		final I_AD_User plantUser = helper.createAD_User("plantUser");
		final I_S_Resource plant = helper.createPlant("plantWithUser", plantUser);
		final I_M_Warehouse warehouse = helper.createWarehouse("whPrintJob", plant);
		final I_M_Product product = helper.createProductWithoutRoutingUserInCharge("prodPrintJob", warehouse);

		final I_C_Order order = helper.createSalesOrder(warehouse);
		helper.createOrderLine(order, product);

		// a printer routing + matching for the plant user and the Produktion doctype
		helper.createPrinterMatchingAndRouting("printer01", masterdata.docTypeProduction.getC_DocType_ID(),
				UserId.ofRepoId(plantUser.getAD_User_ID()));

		// the print routing prints to the queue item's creator, so create it as the plant user
		helper.setLoggedInUser(UserId.ofRepoId(plantUser.getAD_User_ID()));

		// generate the reports and enqueue -> the Produktion queue item stays active (plant-user fallback)
		helper.generateReportsAndEnqueueToPrinting(order);

		helper.createAllPrintJobs(UserId.ofRepoId(plantUser.getAD_User_ID()));

		final java.util.List<I_C_Print_Job> printJobs =
				POJOLookupMap.get().getRecords(I_C_Print_Job.class);
		assertThat(printJobs)
				.as("an active Produktion queue item with a configured printer must produce a C_Print_Job")
				.isNotEmpty();
	}

}

package test.integration.swat.ad;

/*
 * #%L
 * de.metas.swat.ait
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

import org.adempiere.model.CopyRecordFactory;
import org.adempiere.model.CopyRecordSupport;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.TableInfoVO;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.GridTab;
import org.compiere.model.GridTab.DataNewCopyMode;
import org.compiere.model.GridWindow;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MQuery;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.model.X_C_DocType;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.freighcost.api.IFreightCostBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_C_OrderLine;

@RunWith(IntegrationTestRunner.class)
public class CopyRecordTests extends AIntegrationTestDriver
{
	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	private GridTab openGridTab(Object model)
	{
		final PO po = InterfaceWrapperHelper.getPO(model);
		final String tableName = po.get_TableName();
		String keyColumn = po.get_KeyColumns()[0];
		final int recordId = po.get_ID();

		final int windowNo = Env.createWindowNo(null);
		final int AD_Window_ID = MTable.get(getHelper().getCtx(), tableName).getAD_Window_ID();

		final GridWindow gridWindow = GridWindow.get(getHelper().getCtx(), windowNo, AD_Window_ID, false);
		Assert.assertNotNull("Can not load window AD_Window_ID=" + AD_Window_ID, gridWindow);

		GridTab gridTab = gridWindow.getTab(0);
		Assert.assertEquals(tableName, gridTab.get_TableName());

		if (!gridTab.isLoadComplete())
			gridWindow.initTab(0);
		Assert.assertTrue("GridTab " + gridTab + " should be loaded", gridTab.isLoadComplete());

		MQuery query = MQuery.getEqualQuery(keyColumn, recordId);
		gridTab.setQuery(query);
		gridTab.query(false);
		Assert.assertEquals("Invalid row count - " + gridTab, 1, gridTab.getRowCount());

		gridTab.setCurrentRow(0);
		Assert.assertEquals("Wrong order selected on " + gridTab, recordId, gridTab.getValue(keyColumn));

		return gridTab;
	}

	private <T> T copyRecordWithDetails(GridTab gridTab, T model, Class<T> interfaceClass, boolean save)
	{
		final PO po = InterfaceWrapperHelper.getPO(model);

		final CopyRecordSupport childCRS = CopyRecordFactory.getCopyRecordSupport(gridTab.getTableName());
		final List<TableInfoVO> suggestedChildren = new ArrayList<TableInfoVO>();
		for (Object ti : childCRS.getSuggestedChildren(po, gridTab))
		{
			suggestedChildren.add((TableInfoVO)ti);
		}
		// TODO: check suggested children
		gridTab.setSuggestedCopyWithDetailsList(suggestedChildren);

		boolean ok = gridTab.dataNew(DataNewCopyMode.CopyWithDetails);
		Assert.assertTrue("dataNew failed on " + gridTab, ok);

		if (save)
		{
			return saveGridTab(gridTab, interfaceClass);
		}
		else
		{
			return InterfaceWrapperHelper.create(gridTab, interfaceClass);
		}
	}

	private <T> T saveGridTab(GridTab gridTab, Class<T> interfaceClass)
	{
		final String keyColumn = gridTab.getKeyColumnName();
		Assert.assertFalse("No key column found for " + gridTab, Util.isEmpty(keyColumn, true));

		boolean ok = gridTab.dataSave(true);
		Assert.assertTrue("dataSave failed on " + gridTab, ok);
		Assert.assertNotNull("New " + keyColumn + " not set", gridTab.getValue(keyColumn));
		final int newRecordId = (Integer)gridTab.getValue(keyColumn);
		Assert.assertNotNull("New " + keyColumn + " not set", newRecordId > 0);

		T modelNew = InterfaceWrapperHelper.create(getHelper().getCtx(), newRecordId, interfaceClass, null);
		return modelNew;
	}

	@Test
	public void testOrders()
	{
		final I_M_Product freightCostProduct = getHelper().getM_Product_FreightCost("TestFC(*)");

		I_C_Order order = getHelper().mkOrderHelper()
				.setPricingSystemValue("TestPS1")
				.setDocSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.addLine("TestP1", 10, 12)
				.addLine(freightCostProduct.getValue(), 20, 21)
				.setComplete(DocAction.STATUS_Completed)
				.createOrder();

		final GridTab orderGridTab = openGridTab(order);
		final I_C_Order orderNew = copyRecordWithDetails(orderGridTab, order, I_C_Order.class, true);

		//
		// Compare values:
		Assert.assertEquals(order.getC_BPartner_ID(), orderNew.getC_BPartner_ID());
		Assert.assertEquals(order.getC_BPartner_Location_ID(), orderNew.getC_BPartner_Location_ID());
		Assert.assertEquals(order.getAD_User_ID(), orderNew.getAD_User_ID());
		//
		Assert.assertEquals(order.getBill_BPartner_ID(), orderNew.getBill_BPartner_ID());
		Assert.assertEquals(order.getBill_Location_ID(), orderNew.getBill_Location_ID());
		Assert.assertEquals(order.getBill_User_ID(), orderNew.getBill_User_ID());
		//
		Assert.assertEquals(0, orderNew.getC_CashLine_ID());
		Assert.assertEquals(0, orderNew.getC_Payment_ID());
		Assert.assertEquals(order.getFreightCostRule(), orderNew.getFreightCostRule());
		Assert.assertEquals(order.getInvoiceRule(), orderNew.getInvoiceRule());
		Assert.assertEquals(order.getM_FreightCategory_ID(), orderNew.getM_FreightCategory_ID());
		Assert.assertEquals(order.getPromotionCode(), orderNew.getPromotionCode());
		Assert.assertFalse(orderNew.isProcessed());

		//
		// Check lines
		final List<I_C_OrderLine> linesNew = getHelper().retrieveLines(orderNew);
		Assert.assertEquals("Only one line shall be copied", 1, linesNew.size());
		for (I_C_OrderLine oLine : linesNew)
		{
			boolean isFreightCost = Services.get(IFreightCostBL.class).isFreightCostProduct(getHelper().getCtx(), oLine.getM_Product_ID(), getHelper().getTrxName());
			Assert.assertFalse("freight cost line shall not be copied " + oLine, isFreightCost);
			Assert.assertFalse("Line shall not be processed", oLine.isProcessed());
		}
	}

	@Test
	public void testCalculatedColumnsWithDefaultSQL()
	{
		// Check preconditions:
		{
			final POInfo poInfo = POInfo.getPOInfo(getHelper().getCtx(), MTable.getTable_ID(I_C_OrderLine.Table_Name));
			final int lineIndex = poInfo.getColumnIndex(I_C_OrderLine.COLUMNNAME_Line);
			Assert.assertEquals("Not expected default value for C_OrderLine.Line",
					"@SQL=SELECT COALESCE(MAX(Line),0)+10 AS DefaultValue FROM C_OrderLine WHERE C_Order_ID=@C_Order_ID@",
					poInfo.getDefaultLogic(lineIndex));
			Assert.assertTrue("C_OrderLine.Line shall be marked as Calculated", poInfo.isCalculated(lineIndex));
		}

		final Timestamp dateOrdered = TimeUtil.getDay(2011, 01, 01);
		final Timestamp datePromised = TimeUtil.getDay(2011, 01, 10);

		I_C_Order order = getHelper().mkOrderHelper()
				.setPricingSystemValue("TestPS1")
				.setDocSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.setDateOrdered(dateOrdered)
				.setDatePromised(datePromised)
				.addLine("TestP1", 10, 11)
				.addLine("TestP1", 10, 12)
				.addLine("TestP1", 10, 13)
				.addLine("TestP1", 10, 14)
				.addLine("TestP1", 10, 15)
				// .setComplete(DocAction.STATUS_Completed) // not necessary to complete
				.createOrder();

		List<I_C_OrderLine> lines = getHelper().retrieveLines(order);
		int cnt = 0;
		for (I_C_OrderLine line : lines)
		{
			cnt++;
			line.setLine(1000000 * cnt);
			InterfaceWrapperHelper.save(line);
		}

		//
		// Copy with details:
		final GridTab orderGridTab = openGridTab(order);
		I_C_Order orderNew = copyRecordWithDetails(orderGridTab, order, I_C_Order.class, true);
		logger.info("OrderNew: " + orderNew);

		//
		// Check order header
		final Timestamp dateOrderedNew = orderNew.getDateOrdered();
		final Timestamp datePromisedNew = orderNew.getDatePromised();
		final Timestamp today = TimeUtil.trunc(SystemTime.asTimestamp(), TimeUtil.TRUNC_DAY);
		Assert.assertEquals(today, dateOrderedNew);
		Assert.assertEquals(today, datePromisedNew);

		//
		// Checking lines on new Order
		List<I_C_OrderLine> linesNew = getHelper().retrieveLines(orderNew);
		Assert.assertEquals("Not same line number", lines.size(), linesNew.size());
		cnt = 0;
		for (I_C_OrderLine lineNew : linesNew)
		{
			Assert.assertEquals("DateOrdered not correctly set on " + lineNew, dateOrderedNew, lineNew.getDateOrdered());
			Assert.assertEquals("DatePromised not correctly set on " + lineNew, datePromisedNew, lineNew.getDatePromised());

			cnt++;
			int expectedLine = cnt * 10;
			Assert.assertEquals("Line column was not computed correctly", expectedLine, lineNew.getLine());
		}
	}

	@Test
	public void testOrdersWithShipmentAndInvoice()
	{
		I_C_Order order = getHelper().mkOrderHelper().quickCreateOrder("TestPS1", "TestP1", X_C_DocType.DOCSUBTYPE_StandardOrder, 10, 12);
		System.out.println("Order: " + order);
		getHelper().runProcess_UpdateShipmentScheds();
		I_M_InOut inOut = getHelper().createInOut(order);
		System.out.println("Shipment: " + inOut);
		I_C_Invoice invoice = getHelper().createInvoice(order);
		System.out.println("Invoice: " + invoice);

		final GridTab orderGridTab = openGridTab(order);
		final I_C_Order orderNew = copyRecordWithDetails(orderGridTab, order, I_C_Order.class, true);

		//
		// Compare values:
		Assert.assertEquals(order.getC_BPartner_ID(), orderNew.getC_BPartner_ID());
		Assert.assertEquals(order.getC_BPartner_Location_ID(), orderNew.getC_BPartner_Location_ID());
		Assert.assertEquals(order.getAD_User_ID(), orderNew.getAD_User_ID());
		//
		Assert.assertEquals(order.getBill_BPartner_ID(), orderNew.getBill_BPartner_ID());
		Assert.assertEquals(order.getBill_Location_ID(), orderNew.getBill_Location_ID());
		Assert.assertEquals(order.getBill_User_ID(), orderNew.getBill_User_ID());
		//
		Assert.assertEquals(0, orderNew.getC_CashLine_ID());
		Assert.assertEquals(0, orderNew.getC_Payment_ID());
		Assert.assertEquals(order.getFreightCostRule(), orderNew.getFreightCostRule());
		Assert.assertEquals(order.getInvoiceRule(), orderNew.getInvoiceRule());
		Assert.assertEquals(order.getM_FreightCategory_ID(), orderNew.getM_FreightCategory_ID());
		Assert.assertEquals(order.getPromotionCode(), orderNew.getPromotionCode());
		Assert.assertFalse(orderNew.isProcessed());
		//
		// lines
		//
		for (I_C_OrderLine oLine : getHelper().retrieveLines(orderNew))
		{
			Assert.assertFalse(oLine.isProcessed());
			Assert.assertEquals(BigDecimal.ZERO, oLine.getQtyDelivered());
			Assert.assertEquals(BigDecimal.ZERO, oLine.getQtyInvoiced());
		}

	}

	@Test
	public void testInvoices()
	{
		I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice("TestPS1", "TestP1", 10, 12);

		final GridTab invoiceGridTab = openGridTab(invoice);
		final I_C_Invoice invoiceNew = copyRecordWithDetails(invoiceGridTab, invoice, I_C_Invoice.class, true);

		//
		// Compare values:
		Assert.assertEquals(invoice.getC_BPartner_ID(), invoiceNew.getC_BPartner_ID());
		Assert.assertEquals(invoice.getC_BPartner_Location_ID(), invoiceNew.getC_BPartner_Location_ID());
		Assert.assertEquals(invoice.getAD_User_ID(), invoiceNew.getAD_User_ID());
		//
		Assert.assertEquals(0, invoiceNew.getC_CashLine_ID());
		Assert.assertEquals(0, invoiceNew.getC_Payment_ID());
		Assert.assertEquals(0, invoiceNew.getC_Order_ID());
		Assert.assertEquals(0, invoiceNew.getReversal_ID());
		//
		// Lines
		for (I_C_InvoiceLine iLine : getHelper().retrieveLines(invoiceNew))
		{
			Assert.assertFalse(iLine.isProcessed());
		}
	}

	@Test
	public void testInvoicesWithPayment()
	{
		I_C_Order order = getHelper().mkOrderHelper().quickCreateOrder("TestPS1", "TestP1",
				X_C_DocType.DOCSUBTYPE_StandardOrder, 10, 12);
		I_C_Invoice invoice = getHelper().createInvoice(order);
		getHelper().mkPaymentHelper().quickCreatePayment(invoice);

		final GridTab orderGridTab = openGridTab(invoice);
		final I_C_Invoice invoiceNew = copyRecordWithDetails(orderGridTab, invoice, I_C_Invoice.class, true);

		//
		// Compare values:
		Assert.assertEquals(invoice.getC_BPartner_ID(), invoiceNew.getC_BPartner_ID());
		Assert.assertEquals(invoice.getC_BPartner_Location_ID(), invoiceNew.getC_BPartner_Location_ID());
		Assert.assertEquals(invoice.getAD_User_ID(), invoiceNew.getAD_User_ID());
		//
		Assert.assertEquals(0, invoiceNew.getC_CashLine_ID());
		Assert.assertEquals(0, invoiceNew.getC_Payment_ID());
		Assert.assertEquals(0, invoiceNew.getC_Order_ID());
		// lines
		//
		for (I_C_InvoiceLine iLine : getHelper().retrieveLines(invoiceNew))
		{
			Assert.assertFalse(iLine.isProcessed());
			Assert.assertEquals(0, iLine.getC_OrderLine_ID());
		}
	}

	@Test
	public void testInvoicesWithShipmentAndPayed()
	{
		I_C_Order order = getHelper().mkOrderHelper().quickCreateOrder("TestPS1", "TestP1", X_C_DocType.DOCSUBTYPE_StandardOrder, 10, 12);
		System.out.println("Order: " + order);
		getHelper().runProcess_UpdateShipmentScheds();
		I_M_InOut inOut = getHelper().createInOut(order);
		System.out.println("InOut: " + inOut);
		I_C_Invoice invoice = getHelper().createInvoice(order);
		System.out.println("Invoice: " + invoice);
		I_C_Payment payment = getHelper().mkPaymentHelper().quickCreatePayment(invoice);
		System.out.println("Payment: " + payment);

		final GridTab orderGridTab = openGridTab(invoice);
		final I_C_Invoice invoiceNew = copyRecordWithDetails(orderGridTab, invoice, I_C_Invoice.class, true);

		//
		// Compare values:
		Assert.assertEquals(invoice.getC_BPartner_ID(), invoiceNew.getC_BPartner_ID());
		Assert.assertEquals(invoice.getC_BPartner_Location_ID(), invoiceNew.getC_BPartner_Location_ID());
		Assert.assertEquals(invoice.getAD_User_ID(), invoiceNew.getAD_User_ID());
		//
		Assert.assertEquals(0, invoiceNew.getC_CashLine_ID());
		Assert.assertEquals(0, invoiceNew.getC_Payment_ID());
		Assert.assertEquals(0, invoiceNew.getC_Order_ID());
		// lines
		//
		for (I_C_InvoiceLine iLine : getHelper().retrieveLines(invoiceNew))
		{
			Assert.assertFalse(iLine.isProcessed());
			Assert.assertEquals(0, iLine.getM_InOutLine_ID());
			Assert.assertEquals(0, iLine.getC_OrderLine_ID());
		}
	}

	public static void main(String[] args) throws Exception
	{
		CopyRecordTests test = new CopyRecordTests();
		test.setupAdempiere();
		//
		test.testOrders();
		//
		test.testInvoices();
		//
		test.cleanUp();
		System.out.println("DONE!");
	}
}

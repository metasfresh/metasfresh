package test.integration.swat.sales;

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


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_Payment;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MAllocationHdr;
import org.compiere.model.MBPartner;
import org.compiere.model.MDocType;
import org.compiere.model.MInvoice;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPayment;
import org.compiere.model.MPriceList;
import org.compiere.model.Query;
import org.compiere.model.X_C_Invoice;
import org.compiere.model.X_C_Order;
import org.compiere.model.X_C_Payment;
import org.compiere.process.DocAction;
import org.compiere.util.Env;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.OrderHelper;
import de.metas.adempiere.ait.helper.OrderHelper.Order_InvoiceRule;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;
import de.metas.adempiere.exception.OrderInvoicePricesNotMatchException;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.service.IInvoiceLineBL;
import de.metas.document.engine.IDocActionBL;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_C_OrderLine;
import test.integration.swat.sales.scenario.SalesScenario;

@RunWith(IntegrationTestRunner.class)
public class SalesTestDriver extends AIntegrationTestDriver
{

	// TODO move it from here:
	public static final String SYSCONFIG_AutoPayZeroAmt = "org.compiere.model.MInvoice.AutoPayZeroAmt";

	@DataPoints
	public static Order_InvoiceRule[] order_invoiceRule =
	{ Order_InvoiceRule.IMMEDIATE, Order_InvoiceRule.AFTER_DELIVERY, Order_InvoiceRule.AFTER_ORDER_DELIVERED };

	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	@IntegrationTest(
			tasks = "01671",
			desc = "Creates, deliveres and invoices a standard sales order with a new product")
	@Test
	public void standardOrder()
	{
		final String productValue = IHelper.DEFAULT_ProductValue;
		final String bPartnerName = IHelper.DEFAULT_BPName;
		
		new SalesScenario(this).standartOrderWithProduct(productValue, bPartnerName);
	}

	/**
	 * @see http://dewiki908/mediawiki/index.php/01955:_Fenster_Zahlung-Zuordnung_zeigt_Gutschrift_nicht_an_%
	 *      282011080910000037%29
	 */
	@IntegrationTest(
			tasks = "01955")
	@Test
	public void testZeroAmtInvoices()
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		getHelper().setAutomaticPeriodControl();

		{
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_AutoPayZeroAmt, true, 0);
			I_C_Invoice invoice = createZeroAmtInvoice(trxName);
			Assert.assertFalse(invoice.isPaid()); // is paid is not set automatically after complete

			MInvoice.setIsPaid(ctx, invoice.getC_BPartner_ID(), trxName);
			InterfaceWrapperHelper.refresh(invoice);
			Assert.assertTrue(invoice.isPaid());
		}
		{
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_AutoPayZeroAmt, false, 0);
			I_C_Invoice invoice = createZeroAmtInvoice(trxName);
			Assert.assertFalse(invoice.isPaid());

			MInvoice.setIsPaid(ctx, invoice.getC_BPartner_ID(), trxName);
			InterfaceWrapperHelper.refresh(invoice);
			Assert.assertFalse(invoice.isPaid());
		}
	}

	/**
	 * @see http://dewiki908/mediawiki/index.php/01955:_Fenster_Zahlung-Zuordnung_zeigt_Gutschrift_nicht_an_%
	 *      282011080910000037%29
	 */
	@Test
	public void testZeroAmtPayments()
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		getHelper().setAutomaticPeriodControl();

		{
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_AutoPayZeroAmt, true, 0);
			I_C_Payment payment = createZeroAmtPayment(trxName);
			Assert.assertTrue(payment.isAllocated()); // is allocated is set automatically after complete

			payment.setIsAllocated(false);
			InterfaceWrapperHelper.save(payment);

			MPayment.setIsAllocated(ctx, payment.getC_BPartner_ID(), trxName);
			InterfaceWrapperHelper.refresh(payment);
			Assert.assertTrue(payment.isAllocated());
		}
		{
			Services.get(ISysConfigBL.class).setValue(SYSCONFIG_AutoPayZeroAmt, false, 0);
			I_C_Payment payment = createZeroAmtPayment(trxName);
			Assert.assertFalse(payment.isAllocated()); // is paid is not set automatically after complete

			MPayment.setIsAllocated(ctx, payment.getC_BPartner_ID(), trxName);
			InterfaceWrapperHelper.refresh(payment);
			Assert.assertFalse(payment.isAllocated());
		}
	}

	private I_C_Payment createZeroAmtPayment(String trxName)
	{
		I_C_Payment payment = getHelper().mkPaymentHelper()
				.setPayAmt(Env.ZERO)
				.setIsReceipt(true)
				.createPayment();
		assertThat(payment.getPayAmt(), comparesEqualTo(Env.ZERO));
		return payment;
	}

	private I_C_Invoice createZeroAmtInvoice(String trxName)
	{
		I_C_Invoice invoice = getHelper().mkInvoiceHelper()
				.addLine("TestP1", new BigDecimal(10), new BigDecimal("10.23"))
				.addLine("TestP1", new BigDecimal(10), new BigDecimal("-10.23"))
				.setComplete(MInvoice.DOCSTATUS_Completed)
				.createInvoice();
		assertThat(invoice.getGrandTotal(), comparesEqualTo(Env.ZERO));

		return invoice;
	}

	@IntegrationTest(
			tasks = "US1184",
			desc = "Creates two orders with different pricing systems, consolidates them to one invoice *using the packaging-BL* and verifies that the original order prices have been preserved")
	@Test
	public void ordersWithDifferentPricingSystems()
	{
		ordersWithDifferentPricingSystems(Order_InvoiceRule.IMMEDIATE, Order_InvoiceRule.IMMEDIATE);
	}

	/**
	 * @see http://dewiki908/mediawiki/index.php/US1184:_Zwei_Auftr%C3%
	 *      A4ge_mit_unterschiedlichen_Preissystemen_werden_in_Rechnung_und_Lieferschein_zusammengefasst_jedoch_mit_einem_
	 *      %282011021510000063%29
	 */
	@Theory
	public void ordersWithDifferentPricingSystems(
			Order_InvoiceRule invoiceRule1,
			Order_InvoiceRule invoiceRule2)
	{
		final String trxName = getTrxName();

		final String productValue = "TestP1";

		getHelper().addInventory(productValue, 100);

		final I_C_Order order1 = getHelper().mkOrderHelper()
				.setInvoiceRule(invoiceRule1)
				// NOTE: if Freight Cost Rule is FreightIncluded or FixPrice then the Shipment won't be aggregated
				// see org.adempiere.inout.shipment.impl.ShipmentFactory.isConsolidate(OlAndSched, ShipmentParams, String)
				.setFreighCostRule(X_C_Order.FREIGHTCOSTRULE_Calculated)
				.setPricingSystemValue("TestPS1")
				.addLine(productValue, 10, 11)
				.setComplete(DocAction.STATUS_Completed)
				.createOrder();
		
		final I_C_Order order2 = getHelper().mkOrderHelper()
				.setInvoiceRule(invoiceRule2)
				// NOTE: if Freight Cost Rule is FreightIncluded or FixPrice then the Shipment won't be aggregated  
				// see org.adempiere.inout.shipment.impl.ShipmentFactory.isConsolidate(OlAndSched, ShipmentParams, String)
				.setFreighCostRule(X_C_Order.FREIGHTCOSTRULE_Calculated)
				.setPricingSystemValue("TestPS2")
				.addLine(productValue, 13, 14)
				.setComplete(DocAction.STATUS_Completed)
				.createOrder();

		final BigDecimal ordersNetAmt = order1.getTotalLines().add(order2.getTotalLines());

		final int[] orderIds = new int[] { order1.getC_Order_ID(), order2.getC_Order_ID() };

		getHelper().runProcess_UpdateShipmentScheds();
		getHelper().runProcess_InOutGenerate(orderIds);

		List<I_M_InOut> ioList = getHelper().retrieveInOutsForOrders(orderIds, trxName);
		Assert.assertEquals("Only one shipment should be generated", 1, ioList.size());

		final I_M_InOut shipment = ioList.get(0);
		final org.compiere.model.I_C_Invoice invoice = Services.get(IInvoiceBL.class).createAndCompleteForInOut(shipment, getHelper().getNow(), trxName);
		System.out.println("Invoice: " + invoice);

		Assert.assertEquals("Invoce price list should be None", 100, invoice.getM_PriceList_ID());

		// Compute invoice net amount (without freight costs)
		BigDecimal invoiceNetAmt = Env.ZERO;
		for (I_C_InvoiceLine iline : Services.get(IInvoiceDAO.class).retrieveLines(invoice, trxName))
		{
			if (iline.getC_OrderLine_ID() <= 0)
				continue;
			I_C_OrderLine oline = InterfaceWrapperHelper.create(iline.getC_OrderLine(), I_C_OrderLine.class);
			Assert.assertEquals("Invoice line net amount is not equal with order line net amount", oline.getLineNetAmt(), iline.getLineNetAmt());
			invoiceNetAmt = invoiceNetAmt.add(iline.getLineNetAmt());
		}
		Assert.assertEquals("Invoice net amount not equal with orders net amount", ordersNetAmt, invoiceNetAmt);

		// Reverse it:
		Services.get(IDocActionBL.class).processEx(invoice, DocAction.ACTION_Reverse_Correct, DocAction.STATUS_Reversed);
		
		final I_C_Invoice invoiceReversal = InterfaceWrapperHelper.create(invoice.getReversal(), I_C_Invoice.class);
		System.out.println("Invoice reversal: " + invoiceReversal);
		Assert.assertEquals("Reversal invoice net amount is not equal with original invoice net amt", invoiceReversal.getGrandTotal(), invoice.getGrandTotal().negate());

		// fire event to allow further tests
		fireTestEvent(EventType.INVOICE_UNPAID_REVERSE_AFTER, invoice);
	}

	/**
	 * @see http://dewiki908/mediawiki/index.php/US1184:_Zwei_Auftr%C3%
	 *      A4ge_mit_unterschiedlichen_Preissystemen_werden_in_Rechnung_und_Lieferschein_zusammengefasst_jedoch_mit_einem_
	 *      %282011021510000063%29#Mark_o_14:07.2C_17._Okt._2011_.28CEST.29
	 */
	@IntegrationTest(
			tasks = "us1184",
			desc = "Creates two orders with different pricing systems, consolidates them to one invoice *using the InvoiceGenerate process* and verifies that the original order prices have been preserved")
	@Test
	public void ordersWithDifferentPricingSystems2()
	{
		getHelper().addInventory("TestP1", 100);
		getHelper().addInventory("TestP2", 100);

		getHelper().setProductPrice("TestPS1", getHelper().getCurrencyCode(), getHelper().getCountryCode(), "TestP1", new BigDecimal(10), true);
		getHelper().setProductPrice("TestPS2", getHelper().getCurrencyCode(), getHelper().getCountryCode(), "TestP1", new BigDecimal(13), true);
		getHelper().setProductPrice("TestPS1", getHelper().getCurrencyCode(), getHelper().getCountryCode(), "TestP2", new BigDecimal(100), true);
		getHelper().setProductPrice("TestPS2", getHelper().getCurrencyCode(), getHelper().getCountryCode(), "TestP2", new BigDecimal(130), true);

		I_C_Order order1 = getHelper().mkOrderHelper().setPricingSystemValue("TestPS1").addLine("TestP1", 10, 11).setComplete(DocAction.STATUS_Completed).createOrder();
		I_C_Order order2 = getHelper().mkOrderHelper().setPricingSystemValue("TestPS2").addLine("TestP2", 130, 140).setComplete(DocAction.STATUS_Completed).createOrder();

		final int[] orderIds = new int[] { order1.getC_Order_ID(), order2.getC_Order_ID() };

		getHelper().runProcess_InvoiceGenerate(orderIds);

		getHelper().assertInvoiced(order1);
		getHelper().assertInvoiced(order2);

		List<de.metas.adempiere.model.I_C_Invoice> invoices = getHelper().retrieveInvoicesForOrders(orderIds, getTrxName());
		Assert.assertTrue("No invoices were generated", invoices != null && !invoices.isEmpty());
		Assert.assertTrue("Only one invoice should be generated and not: " + invoices, invoices.size() == 1);

		I_C_Invoice invoice = invoices.get(0);
		System.out.println("Invoice: " + invoice);
		for (I_C_InvoiceLine iline : getHelper().retrieveLines(invoice))
		{
			de.metas.adempiere.modelvalidator.InvoiceLine.assertOrderInvoicePricesMatch(iline);
		}
	}

	@IntegrationTest(
			desc = "If an invoice is created with a normal pricelist, then prices of invoice lines should be overwritten from that pricelist")
	@Test
	public void invoicesWithPriceList() throws Exception
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		//
		// First we test original functionality
		{
			BigDecimal price = new BigDecimal("123");
			I_M_PriceList pl = getHelper().getM_PriceList(IHelper.DEFAULT_PricingSystemValue, getHelper().getCurrencyCode(), getHelper().getCountryCode());
			getHelper().setProductPrice(pl, IHelper.DEFAULT_ProductValue, price);

			MInvoice invoice = new MInvoice(ctx, 0, trxName);
			invoice.setBPartner((MBPartner)InterfaceWrapperHelper.getPO(getHelper().mkBPartnerHelper().getC_BPartner(getHelper().getConfig())));
			invoice.setDateInvoiced(SystemTime.asTimestamp());
			invoice.setDateAcct(SystemTime.asTimestamp());
			invoice.setM_PriceList_ID(pl.getM_PriceList_ID());
			invoice.setC_Currency_ID(getHelper().getC_Currency_ID(getHelper().getCurrencyCode()));
			invoice.saveEx();

			MInvoiceLine line1 = new MInvoiceLine(invoice);
			line1.setM_Product_ID(getHelper().getM_Product(IHelper.DEFAULT_ProductValue).getM_Product_ID(), true);
			line1.setPriceActual(Env.ZERO);
			line1.setPriceList(Env.ZERO);
			line1.setPriceLimit(Env.ZERO);
			line1.saveEx();

			assertThat("PriceActual not match", line1.getPriceActual(), comparesEqualTo(price));
			assertThat("PriceList not match", line1.getPriceList(), comparesEqualTo(price));
		}
	}

	@IntegrationTest(
			desc = "If an invoice is created with 'NO-PricleList', then prices of invoice lines should not be overwritten from any pricelist")
	@Test
	public void testInvoicesWithNoPriceList()
	{
		final Properties ctx = getCtx();
		final String trxName = getTrxName();

		final MInvoice invoice = new MInvoice(ctx, 0, trxName);
		invoice.setBPartner((MBPartner)InterfaceWrapperHelper.getPO(getHelper().mkBPartnerHelper().getC_BPartner(getHelper().getConfig())));
		invoice.setDateInvoiced(SystemTime.asTimestamp());
		invoice.setDateAcct(SystemTime.asTimestamp());
		invoice.setM_PriceList_ID(MPriceList.M_PriceList_ID_None);
		invoice.saveEx();

		final BigDecimal priceLimit = new BigDecimal("1234");
		MInvoiceLine line1 = new MInvoiceLine(invoice);
		line1.setM_Product_ID(getHelper().getM_Product("TestP1").getM_Product_ID(), true);
		line1.setPriceActual(Env.ZERO);
		line1.setPriceList(Env.ZERO);
		line1.setPriceLimit(priceLimit);
		line1.saveEx();

		// Normally, having PriceActual and PriceList equals to ZERO would trigger price calculation
		// which in turn will set PriceLimit to ZERO, because there is no price
		// BUT now we check that the line is not modified if the M_PriceList_ID=None
		assertThat("PriceActual changed", line1.getPriceActual(), comparesEqualTo(BigDecimal.ZERO));
		assertThat("PriceList changed", line1.getPriceList(), comparesEqualTo(BigDecimal.ZERO));
		assertThat("PriceLimit changed", line1.getPriceLimit(), comparesEqualTo(priceLimit));
	}

	@Test
	public void orderInvoicePriceMatch()
	{
		// only execute if the prices are actually locked
		Assume.assumeTrue(Services.get(IInvoiceLineBL.class).isPriceLocked(null));
		
		final OrderHelper orderHelper = getHelper().mkOrderHelper();

		final I_C_Order order = orderHelper
				.addLine(IHelper.DEFAULT_ProductValue, 10, 11)
				.setComplete(DocAction.STATUS_Completed)
				.createOrder();
		final MOrder orderPO = orderHelper.getOrderPO(order);
		MInvoice invoice = new MInvoice(orderPO, -1, getHelper().getNow());
		invoice.saveEx();

		final MOrderLine oline = orderPO.getLines(true, null)[0];
		final MInvoiceLine iline = new MInvoiceLine(invoice);
		iline.setOrderLine(oline);

		// Tests for BEFORE_NEW events
		testOrderInvoicePriceMatch(iline);

		// Make sure is saved
		InterfaceWrapperHelper.save(iline);
		Assert.assertTrue("Invoice line should be saved " + iline, iline.getC_InvoiceLine_ID() > 0);

		// Tests for BEFORE_CHANGE events
		testOrderInvoicePriceMatch(iline);
	}

	@Test
	public void prepayOrder()
	{
		prepayOrder(Order_InvoiceRule.IMMEDIATE);
	}

	/**
	 * Creates a prepay order and a payment.
	 */
	@Theory
	public void prepayOrder(final Order_InvoiceRule invoiceRule)
	{
		Assume.assumeTrue(getHelper().getConfig().isProjectWithMetasPrepayOrder());

		final OrderHelper orderHelper = getHelper().mkOrderHelper();

		final I_C_Order order = orderHelper
				.setDocSubType(de.metas.prepayorder.model.I_C_DocType.DOCSUBTYPE_PrepayOrder_metas)
				.setInvoiceRule(invoiceRule)
				.setComplete(DocAction.STATUS_WaitingPayment)
				.addLine(IHelper.DEFAULT_ProductValue, 10, 10)
				.createOrder();

		fireTestEvent(EventType.ORDER_PREPAY_COMPLETE_AFTER, order);

		final I_C_Payment payment = InterfaceWrapperHelper.create(getCtx(), I_C_Payment.class, getTrxName());
		payment.setC_Order_ID(order.getC_Order_ID());
		payment.setIsReceipt(true);
		payment.setC_BPartner_ID(order.getBill_BPartner_ID());
		payment.setPayAmt(order.getGrandTotal());

		final I_C_BP_BankAccount account =
				new Query(getCtx(), I_C_BP_BankAccount.Table_Name, I_C_BP_BankAccount.COLUMNNAME_AD_Org_ID + "=?", getTrxName())
						.setParameters(payment.getAD_Org_ID())
						.setOrderBy(I_C_BP_BankAccount.COLUMNNAME_C_BP_BankAccount_ID)
						.first(I_C_BP_BankAccount.class);
		payment.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		payment.setC_Currency_ID(order.getC_Currency_ID());

		InterfaceWrapperHelper.save(payment);

		final MPayment paymentPO = (MPayment)InterfaceWrapperHelper.getPO(payment);

		getHelper().process(paymentPO, DocAction.ACTION_Complete, X_C_Payment.DOCSTATUS_Completed);

		// reload order
		final MOrder orderPO = orderHelper.getOrderPO(order);
		orderPO.load(getTrxName());
		assertEquals(X_C_Order.DOCSTATUS_Completed, orderPO.getDocStatus());

		fireTestEvent(EventType.PAYMENT_ORDER_COMPLETE_AFTER, paymentPO);
	}

	@Test
	public void createPOSOrderReverseInvoice()
	{
		createPOSOrderReverseInvoice(Order_InvoiceRule.IMMEDIATE);
	}

	/**
	 * Creates a point of sales order (DocSubType=WR) and calls {@link #posOrderCreated(MOrder)} to perform tests.
	 */
	@Theory
	public void createPOSOrderReverseInvoice(Order_InvoiceRule invoiceRule)
	{
		// we don't have to iterate different invoice rules
		Assume.assumeTrue(Order_InvoiceRule.IMMEDIATE.equals(invoiceRule));

		final MOrder order = createPOSOrder(invoiceRule);

		final MInvoice invoice = retrieveAndCheckPOSInvoice(order);

		// firing event to allow further tests
		fireTestEvent(EventType.ORDER_POS_COMPLETE_AFTER, order);

		// now reversing the order
		getHelper().process(invoice, X_C_Invoice.DOCACTION_Reverse_Correct, X_C_Invoice.DOCSTATUS_Reversed);

		assertEquals("QtyInvoiced of ol should be 0", BigDecimal.ZERO, order.getLines(true, "")[0].getQtyInvoiced());

		// fire event to allow further tests
		fireTestEvent(EventType.INVOICE_UNPAID_REVERSE_AFTER, invoice);
	}

	/**
	 * Loads an invoice that references the given order. Asserts that there is exactly one such invoice and that it is
	 * completed.
	 * 
	 * @param order
	 * @return
	 */
	private MInvoice retrieveAndCheckPOSInvoice(final MOrder order)
	{
		final MInvoice invoice = new Query(order.getCtx(), I_C_Invoice.Table_Name, I_C_Invoice.COLUMNNAME_C_Order_ID + "=?", order.get_TrxName())
				.setParameters(order.getC_Order_ID())
				.setOnlyActiveRecords(true)
				.setApplyAccessFilter(true)
				.firstOnly();

		assertNotNull(order + " has no invoice", invoice);
		assertEquals(invoice + " not complete", X_C_Invoice.DOCSTATUS_Completed, invoice.getDocStatus());
		return invoice;
	}

	private MOrder createPOSOrder(Order_InvoiceRule invoiceRule)
	{
		final OrderHelper orderHelper = getHelper().mkOrderHelper();

		final I_C_Order order = orderHelper
				.setDocSubType(MDocType.DOCSUBTYPE_POSOrder)
				.setInvoiceRule(invoiceRule)
				.setComplete(DocAction.STATUS_Completed)
				.addLine(IHelper.DEFAULT_ProductValue, 10, 10)
				.createOrder();

		getHelper().assertInvoiced(order);
		return orderHelper.getOrderPO(order);
	}

	@Test
	public void createAndReactivatePOSOrder()
	{
		createAndReactivatePOSOrder(Order_InvoiceRule.IMMEDIATE);
	}

	@Theory
	public void createAndReactivatePOSOrder(Order_InvoiceRule invoiceRule)
	{
		final MOrder order = createPOSOrder(invoiceRule);

		final MInvoice invoice = retrieveAndCheckPOSInvoice(order);

		getHelper().process(order, X_C_Order.DOCACTION_Re_Activate, X_C_Order.DOCSTATUS_InProgress);

		// refresh and assert that the invoice is reversed
		invoice.load(order.get_TrxName());
		assertEquals(invoice + " not reversed", X_C_Invoice.DOCSTATUS_Reversed, invoice.getDocStatus());

		assertEquals("QtyInvoiced of ol should be 0", BigDecimal.ZERO, order.getLines(true, "")[0].getQtyInvoiced());

		// fire event to allow further tests
		fireTestEvent(EventType.ORDER_POS_REACTIVATE_AFTER, order);
	}

	@Test
	public void createAndReactivatePaidPOSOrder()
	{
		final MOrder order = createPOSOrder(Order_InvoiceRule.IMMEDIATE);

		// firing event to allow further tests
		fireTestEvent(EventType.ORDER_POS_COMPLETE_AFTER, order);

		final MInvoice invoice = retrieveAndCheckPOSInvoice(order);

		final I_C_Payment payment = InterfaceWrapperHelper.create(getCtx(), I_C_Payment.class, getTrxName());
		payment.setC_Invoice_ID(invoice.getC_Invoice_ID());
		payment.setIsReceipt(true);
		payment.setC_BPartner_ID(invoice.getC_BPartner_ID());
		payment.setPayAmt(invoice.getGrandTotal());

		final I_C_BP_BankAccount account =
				new Query(getCtx(), I_C_BP_BankAccount.Table_Name, I_C_BP_BankAccount.COLUMNNAME_AD_Org_ID + "=?", getTrxName())
						.setParameters(payment.getAD_Org_ID())
						.first(I_C_BP_BankAccount.class);
		payment.setC_BP_BankAccount_ID(account.getC_BP_BankAccount_ID());
		payment.setC_Currency_ID(invoice.getC_Currency_ID());

		InterfaceWrapperHelper.save(payment);

		final MPayment paymentPO = (MPayment)InterfaceWrapperHelper.getPO(payment);

		getHelper().process(paymentPO, DocAction.ACTION_Complete, X_C_Payment.DOCSTATUS_Completed);

		invoice.load(getTrxName());
		assertTrue(invoice.isPaid());

		// assert/check the current status
		fireTestEvent(EventType.PAYMENT_INVOICE_CREATE_AFTER, paymentPO);

		// now reactivate the order
		getHelper().process(order, X_C_Order.DOCACTION_Re_Activate, X_C_Order.DOCSTATUS_InProgress);

		// refresh and assert that the invoice is reversed
		invoice.load(getTrxName());
		assertEquals(invoice + " not reversed", X_C_Invoice.DOCSTATUS_Reversed, invoice.getDocStatus());

		assertEquals("QtyInvoiced of ol should be 0", BigDecimal.ZERO, order.getLines(true, "")[0].getQtyInvoiced());

		fireTestEvent(EventType.INVOICE_PAID_REVERSE_AFTER, invoice);
	}

	/**
	 * Check when prices from invoice line are changed, and they differ from order prices then
	 * {@link OrderInvoicePricesNotMatchException} should be thrown
	 * 
	 * @param iline
	 *            invoice line
	 */
	private void testOrderInvoicePriceMatch(org.compiere.model.I_C_InvoiceLine iline)
	{
		BigDecimal price = iline.getPriceActual();
		iline.setPriceActual(price.add(Env.ONE));
		try
		{
			InterfaceWrapperHelper.save(iline);
			Assert.fail("OrderInvoicePricesNotMatchException should be thrown");
		}
		// Unfortunately we cannot catch OrderInvoicePricesNotMatchException due to how model validation engine and
		// persistance engine are working
		// catch (OrderInvoicePricesNotMatchException e)
		catch (Exception e)
		{
		}

		iline.setPriceActual(price);

		price = iline.getPriceList();
		iline.setPriceList(price.add(Env.ONE));
		try
		{
			InterfaceWrapperHelper.save(iline);
			Assert.fail("OrderInvoicePricesNotMatchException should be thrown");
		}
		catch (Exception e)
		{
		}

		iline.setPriceList(price); // putting back the old price

		// now it should be ok
		InterfaceWrapperHelper.save(iline);
	}

	/**
	 * 
	 * @throws Exception
	 * @see http://dewiki908/mediawiki/index.php/02181:_Verbuchungsfehler_bei_Zuordnungen_%282011092910000015%29
	 */
	@Test
	public void testInvoiceAllocation_reverseCorrectIt() throws Exception
	{
		I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(123);
		I_C_Payment payment = getHelper().mkPaymentHelper().quickCreatePayment(invoice);

		MAllocationHdr allocation = assertOnlyOneCompletedAndGet(payment);
		System.out.println("Allocation: " + allocation);

		InterfaceWrapperHelper.refresh(invoice);
		final MInvoice invoicePO = (MInvoice)InterfaceWrapperHelper.getPO(invoice);
		assertThat("Invoice open amount should be zero - " + invoice, invoicePO.getOpenAmt(), comparesEqualTo(Env.ZERO));

		//
		// Test reverseCorrectIt
		getHelper().process(allocation, DocAction.ACTION_Reverse_Correct, DocAction.STATUS_Reversed);
		Assert.assertTrue("Reversal_ID should be set", allocation.getReversal_ID() > 0);
		Assert.assertFalse("Allocation and reversal should be different", allocation.getC_AllocationHdr_ID() == allocation.getReversal_ID());
		I_C_AllocationHdr allocationReversal = allocation.getReversal();
		System.out.println("Allocation(reversal): " + allocationReversal);
		Assert.assertEquals("Reversal_ID should not ok - " + allocation, allocationReversal.getC_AllocationHdr_ID(), allocation.getReversal_ID());
		Assert.assertEquals("Reveral allocation should have Reversed status", MAllocationHdr.DOCSTATUS_Reversed, allocationReversal.getDocStatus());
		Assert.assertEquals("Reversal_ID should not ok - " + allocationReversal, allocation.getC_AllocationHdr_ID(), allocationReversal.getReversal_ID());
		Assert.assertFalse("Allocation and reversal should have different document numbers", allocation.getDocumentNo().compareTo(allocationReversal.getDocumentNo()) == 0);

		//
		// Check: invoice and payment should be not paid and not allocated (again)
		InterfaceWrapperHelper.refresh(invoice);
		InterfaceWrapperHelper.refresh(payment);
		Assert.assertFalse("Invoice should not be paid - " + invoice, invoice.isPaid());
		assertThat("Invoice open amount not correct - " + invoice, invoicePO.getOpenAmt(), comparesEqualTo(invoicePO.getGrandTotal(true)));
		Assert.assertFalse("Payment should not be allocated - " + payment, payment.isAllocated());

		System.out.println("Invoice: " + invoice);
	}

	@Test
	public void prePayOrderWithZeroPayment()
	{
		Assume.assumeTrue(getHelper().getConfig().isProjectWithMetasPrepayOrder());

		final I_C_Order order =
				getHelper().mkOrderHelper()
						.setDocSubType(de.metas.prepayorder.model.I_C_DocType.DOCSUBTYPE_PrepayOrder_metas)
						.setComplete(DocAction.STATUS_WaitingPayment)
						.addLine(Helper.DEFAULT_ProductValue, 10, 10)
						.createOrder();

		fireTestEvent(EventType.ORDER_PREPAY_COMPLETE_AFTER, order);

		final I_C_Payment payment = getHelper().mkPaymentHelper()
				.setC_Order(order)
				.setPayAmt(BigDecimal.ZERO)
				.createPayment();

		assertThat(payment.getDocStatus(), equalTo(X_C_Payment.DOCSTATUS_Completed));

		fireTestEvent(EventType.PAYMENT_ORDER_COMPLETE_AFTER, payment);
	}

	private MAllocationHdr assertOnlyOneCompletedAndGet(I_C_Payment payment)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);

		final MAllocationHdr[] allocations = MAllocationHdr.getOfPayment(ctx, payment.getC_Payment_ID(), trxName);
		Assert.assertNotNull("No allocations were created", allocations);
		Assert.assertTrue("No allocations were created", allocations != null && allocations.length > 0);

		MAllocationHdr allocation = null;
		for (MAllocationHdr al : allocations)
		{
			if (MAllocationHdr.DOCSTATUS_Completed.equals(al.getDocStatus()))
			{
				Assert.assertNull("More then one completed allocations found - " + al + ", " + allocation, allocation);
				allocation = al;
			}
		}

		return allocation;
	}

	@AfterClass
	public static void allListenersCalled()
	{
		assertAllListenersCalled(SalesTestDriver.class);
	}

}

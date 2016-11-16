package test.integration.banking;

/*
 * #%L
 * de.metas.banking.ait
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
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Payment;
import org.compiere.model.MBankStatement;
import org.compiere.model.MPayment;
import org.compiere.model.MQuery;
import org.compiere.model.Query;
import org.compiere.process.DocAction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.DocumentationType;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.interfaces.I_C_BankStatementLine_Ref;
import de.metas.banking.model.I_C_BankStatement;
import de.metas.banking.model.I_C_BankStatementLine;
import de.metas.interfaces.I_C_BP_BankAccount;
import de.metas.interfaces.I_C_BPartner;
import de.metas.process.SvrProcess;
import test.integration.swat.sales.SalesTestDriver;

@RunWith(IntegrationTestRunner.class)
public class PaymentTestDriver extends AIntegrationTestDriver
{
	private static final Class<? extends SvrProcess> ProcessClass_BankStatementPayment = de.metas.banking.payment.process.C_Payment_CreateFrom_BankStatement.class;
	private static final int ProcessIdBankStatementPayment = 257;

	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	@IntegrationTest(
			tasks = "04203",
			desc = "test flag IsOverUnderPayment")
	@Test
	public void testIsOverUnderPaymentChange()
	{
		final I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(100);
		final I_C_Payment paymentPO = getHelper().mkPaymentHelper()
				.setTenderType(MPayment.TENDERTYPE_Cash)
				.quickCreatePayment(invoice, false); // completePayment = false

		paymentPO.setIsOverUnderPayment(true);
		InterfaceWrapperHelper.save(paymentPO);

		// open payment window
		final GridWindowHelper window =
				getHelper().mkGridWindowHelper()
						.openTabForTable(I_C_Payment.Table_Name,
								true,
								MQuery.getEqualQuery(I_C_Payment.COLUMNNAME_C_Payment_ID, paymentPO.getC_Payment_ID())
						);
		window
				.selectTab(I_C_Payment.Table_Name)
				.refreshAllRows();
		window.assertFieldDisplayed(I_C_Payment.COLUMNNAME_OverUnderAmt, true);

		final I_C_Payment paymentGrid = InterfaceWrapperHelper.create(window.getGridTab(), I_C_Payment.class);
		//
		paymentGrid.setOverUnderAmt(BigDecimal.valueOf(10));
		InterfaceWrapperHelper.save(paymentGrid);
		window.refreshAllRows();

		assertThat(paymentGrid.getPayAmt(), comparesEqualTo(invoice.getGrandTotal().subtract(BigDecimal.valueOf(10))));
		assertThat(paymentGrid.getOverUnderAmt(), comparesEqualTo(BigDecimal.valueOf(10)));

		//
		paymentGrid.setIsOverUnderPayment(false);
		InterfaceWrapperHelper.save(paymentGrid);
		window.refreshAllRows();
		window.assertFieldDisplayed(I_C_Payment.COLUMNNAME_OverUnderAmt, false);

		InterfaceWrapperHelper.refresh(paymentGrid);
		assertThat(paymentGrid.getWriteOffAmt(), comparesEqualTo(BigDecimal.valueOf(10)));
		assertThat(paymentGrid.getOverUnderAmt(), comparesEqualTo(BigDecimal.ZERO));
	}

	@IntegrationTest(
			tasks = "04203",
			desc = "test Disocunt amount")
	@Test
	public void testDisocuntAmtChange()
	{
		final I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(100);
		final I_C_Payment paymentPO = getHelper().mkPaymentHelper()
				.setTenderType(MPayment.TENDERTYPE_Cash)
				.quickCreatePayment(invoice, false); // completePayment = false

		// open payment window
		final GridWindowHelper window = getHelper().mkGridWindowHelper().openTabForTable(I_C_Payment.Table_Name, true,
				MQuery.getEqualQuery(I_C_Payment.COLUMNNAME_C_Payment_ID, paymentPO.getC_Payment_ID()));
		window.selectTab(I_C_Payment.Table_Name)
				.refreshAllRows();

		final I_C_Payment paymentGrid = InterfaceWrapperHelper.create(window.getGridTab(), I_C_Payment.class);

		//
		paymentGrid.setDiscountAmt(BigDecimal.valueOf(20));
		InterfaceWrapperHelper.save(paymentGrid);
		window.refreshAllRows();
		assertThat(paymentGrid.getDiscountAmt(), comparesEqualTo(new BigDecimal(20)));
		assertThat(paymentGrid.getPayAmt(), comparesEqualTo(invoice.getGrandTotal().subtract(new BigDecimal(20))));
	}

	@IntegrationTest(
			tasks = "04203",
			desc = "test Pay amount")
	@Test
	public void testPayAmtChange()
	{
		final I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(100);
		final I_C_Payment paymentPO = getHelper().mkPaymentHelper()
				.setTenderType(MPayment.TENDERTYPE_Cash)
				.quickCreatePayment(invoice, false); // completePayment = false

		// open payment window
		final GridWindowHelper window = getHelper().mkGridWindowHelper().openTabForTable(I_C_Payment.Table_Name, true,
				MQuery.getEqualQuery(I_C_Payment.COLUMNNAME_C_Payment_ID, paymentPO.getC_Payment_ID()));
		window.selectTab(I_C_Payment.Table_Name)
				.refreshAllRows();

		final I_C_Payment paymentGrid = InterfaceWrapperHelper.create(window.getGridTab(), I_C_Payment.class);
		
		//
		paymentGrid.setPayAmt(BigDecimal.valueOf(90));
		
		InterfaceWrapperHelper.save(paymentGrid);
		window.refreshAllRows();
		assertThat(paymentGrid.getWriteOffAmt(), comparesEqualTo(invoice.getGrandTotal().subtract(BigDecimal.valueOf(90))));
		assertThat(paymentGrid.getPayAmt(), comparesEqualTo(BigDecimal.valueOf(90)));
	}

	@IntegrationTest(
			tasks = "025b",
			desc = "Creates a payment with tender type 'Cash'")
	@Test
	public void testCashPayments()
	{
		final I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(100);
		final I_C_Payment payment = getHelper().mkPaymentHelper()
				.setTenderType(MPayment.TENDERTYPE_Cash)
				.quickCreatePayment(invoice, false); // completePayment = false

		fireTestEvent(EventType.PAYMENT_CASH_COMPLETE_AFTER, payment);
	}

	@IntegrationTest(
			tasks = "025b",
			desc = "Creates a partial payment with tender type 'Cash'")
	@Test
	public void testCashPaymentsAmt()
	{
		final I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(100);
		final I_C_Payment payment = getHelper().mkPaymentHelper()
				.setTenderType(MPayment.TENDERTYPE_Cash)
				.setC_Invoice(invoice)
				.setExpectInvoicePaid(false)
				.setPayAmt(invoice.getGrandTotal().subtract(BigDecimal.valueOf(20)))
				.setDiscountAmt(BigDecimal.valueOf(10))
				.setWriteOffAmt(BigDecimal.valueOf(4))
				.setOverUnderAmt(BigDecimal.valueOf(6))
				.createPayment();

		Assert.assertTrue("Expected OverUnderPayment=Y for " + payment,
				payment.isOverUnderPayment());

		fireTestEvent(EventType.PAYMENT_CASH_COMPLETE_AFTER, payment);
	}

	@IntegrationTest(
			tasks = "025b",
			desc = "Creates a partial payment with tender type 'Cash' and completes the payment's cash-bank statement")
	@Test
	public void testCashPaymentVoid()
	{
		final I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(100);
		final I_C_Payment payment = getHelper().mkPaymentHelper()
				.setTenderType(MPayment.TENDERTYPE_Cash)
				.setC_Invoice(invoice)
				.setExpectInvoicePaid(false)
				.setPayAmt(invoice.getGrandTotal().subtract(BigDecimal.TEN))
				.setDiscountAmt(BigDecimal.valueOf(3))
				.setWriteOffAmt(BigDecimal.valueOf(2))
				.setOverUnderAmt(BigDecimal.valueOf(5))
				.createPayment();

		Assert.assertTrue("Expected OverUnderPayment=Y for " + payment,
				payment.isOverUnderPayment());

		fireTestEvent(EventType.PAYMENT_CASH_COMPLETE_AFTER, payment);

		final List<I_C_BankStatementLine> bsls = BankStatementHelper.fetchBankStatementLinesForPayment(payment, I_C_BankStatementLine.class);
		Assert.assertEquals("We should have one BSL for " + payment, 1, bsls.size());

		final I_C_BankStatementLine bsl = bsls.get(0);
		// complete bs
		I_C_BankStatement bs = InterfaceWrapperHelper.create(bsl.getC_BankStatement(), I_C_BankStatement.class);
		MBankStatement bsPO = InterfaceWrapperHelper.getPO(bs);
		bsPO.completeIt();
		bsPO.saveEx();

		fireTestEvent(EventType.BANKSTATEMENT_COMPLETE_AFTER, bsPO);
	}

	@IntegrationTest(
			tasks = "025b",
			desc = "Creates a partial payment with tender type 'Cash' and voids it (after completion!)")
	@Test
	public void testCashVoidProcessedBSL()
	{
		final I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(100);
		final I_C_Payment payment = getHelper().mkPaymentHelper()
				.setTenderType(MPayment.TENDERTYPE_Cash)
				.setC_Invoice(invoice)
				.setExpectInvoicePaid(false)
				.setPayAmt(invoice.getGrandTotal().subtract(BigDecimal.TEN))
				.setDiscountAmt(BigDecimal.valueOf(3))
				.setWriteOffAmt(BigDecimal.valueOf(2))
				.setOverUnderAmt(BigDecimal.valueOf(5))
				.createPayment();

		fireTestEvent(EventType.PAYMENT_CASH_COMPLETE_AFTER, payment);

		// void payment
		final MPayment pay = new MPayment(InterfaceWrapperHelper.getCtx(payment), payment.getC_Payment_ID(), InterfaceWrapperHelper.getTrxName(payment));

		assertTrue("Expected voiding of " + pay + " to be successfull", pay.voidIt());
		pay.saveEx();

		fireTestEvent(EventType.PAYMENT_CASH_COMPLETED_VOID_AFTER, payment);
	}

	/**
	 * Scenario: <li>Create an payment <li>allocate 4 invoices to it <li>complete the payment <li>check payment header amounts <li>check if the invoices are marked as paid
	 */
	@Test
	public void testPaymentAllocate()
	{
		I_C_Invoice invoice1 = getHelper().mkInvoiceHelper().quickCreateInvoice(10);
		I_C_Invoice invoice2 = getHelper().mkInvoiceHelper().quickCreateInvoice(20);
		I_C_Invoice invoice3 = getHelper().mkInvoiceHelper().quickCreateInvoice(30);
		I_C_Invoice invoice4 = getHelper().mkInvoiceHelper().quickCreateInvoice(40);

		BigDecimal invoiceAmt = invoice1.getGrandTotal()
				.add(invoice2.getGrandTotal())
				.add(invoice3.getGrandTotal())
				.add(invoice4.getGrandTotal());

		I_C_Payment payment = getHelper().mkPaymentHelper()
				.setC_BPartner(InterfaceWrapperHelper.create(invoice1.getC_BPartner(), I_C_BPartner.class))
				.setC_Currency_ID(invoice1.getC_Currency_ID())
				.setAD_Org_ID(invoice1.getAD_Org_ID())
				.addPaymentAllocate(invoice1)
				.addPaymentAllocate(invoice2)
				.addPaymentAllocate(invoice3)
				.addPaymentAllocate(invoice4)
				.createPayment();

		assertThat("Expecting grandToal of all invoices to be equal to payAmt of " + payment, payment.getPayAmt(), comparesEqualTo(invoiceAmt));

		InterfaceWrapperHelper.refresh(invoice1);
		InterfaceWrapperHelper.refresh(invoice2);
		InterfaceWrapperHelper.refresh(invoice3);
		InterfaceWrapperHelper.refresh(invoice4);
		Assert.assertTrue("Invoice " + invoice1 + " shall be paid", invoice1.isPaid());
		Assert.assertTrue("Invoice " + invoice2 + " shall be paid", invoice2.isPaid());
		Assert.assertTrue("Invoice " + invoice3 + " shall be paid", invoice3.isPaid());
		Assert.assertTrue("Invoice " + invoice4 + " shall be paid", invoice4.isPaid());
	}

	@Test
	public void testBankStatementWindow()
	{
		I_C_Invoice invoice = getHelper().mkInvoiceHelper().quickCreateInvoice(100);

		final I_C_BP_BankAccount ba = getHelper().getC_BP_BankAccount(false);
		final I_C_BankStatement bs = getHelper().createPO(I_C_BankStatement.class);

		bs.setStatementDate(getHelper().getToday());
		bs.setAD_Org_ID(ba.getAD_Org_ID());
		bs.setC_BP_BankAccount_ID(ba.getC_BP_BankAccount_ID());
		bs.setName(getClass() + "_" + System.currentTimeMillis());
		InterfaceWrapperHelper.save(bs);

		final GridWindowHelper window = getHelper().mkGridWindowHelper();
		final I_C_BankStatementLine bsl = window.openFor(bs)
				.selectTab(I_C_BankStatementLine.Table_Name)
				.newRecord()
				.getGridTabInterface(I_C_BankStatementLine.class);

		Assert.assertEquals(true, bsl.isManual());

		//
		// Test IsMultiplePaymentOrInvoice and IsMultiplePayment flags
		fireTestEvent(DocumentationType.GRIDTAB_SET_FIELD_BEFORE, "IsMultiplePaymentOrInvoice=false");
		bsl.setIsMultiplePaymentOrInvoice(false);
		bsl.setIsMultiplePayment(false);
		{
			window.assertFieldDisplayed(I_C_BankStatementLine.COLUMNNAME_IsMultiplePayment, false);
			window.assertFieldDisplayed(I_C_BankStatementLine.COLUMNNAME_C_Payment_ID, true);
			window.assertFieldDisplayed(I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID, true);
			bsl.setIsMultiplePaymentOrInvoice(true);
			window.assertFieldDisplayed(I_C_BankStatementLine.COLUMNNAME_IsMultiplePayment, true);
			window.assertFieldDisplayed(I_C_BankStatementLine.COLUMNNAME_C_Payment_ID, true);
			window.assertFieldDisplayed(I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID, false);
			bsl.setIsMultiplePayment(true);
			window.assertFieldDisplayed(I_C_BankStatementLine.COLUMNNAME_C_Payment_ID, false);
			window.assertFieldDisplayed(I_C_BankStatementLine.COLUMNNAME_C_Invoice_ID, false);
		}
		bsl.setIsMultiplePaymentOrInvoice(false);
		bsl.setIsMultiplePayment(false);

		bsl.setStatementLineDate(getHelper().getToday());
		bsl.setC_Invoice_ID(invoice.getC_Invoice_ID());
		assertThat(bsl.getStmtAmt(), comparesEqualTo(invoice.getGrandTotal()));
		assertThat(bsl.getTrxAmt(), comparesEqualTo(invoice.getGrandTotal()));
		Assert.assertEquals(invoice.getC_BPartner_ID(), bsl.getC_BPartner_ID());
		Assert.assertEquals(invoice.getC_Currency_ID(), bsl.getC_Currency_ID());

		window.save();

		getHelper().mkProcessHelper()
				.setProcessClass(ProcessClass_BankStatementPayment)
				.setExpectedProcessId(ProcessIdBankStatementPayment) // just to be sure
				.setPO(bsl)
				.run();

		window.refresh();

		Assert.assertTrue(bsl.getC_Payment_ID() > 0);
		final I_C_Payment payment = InterfaceWrapperHelper.create(bsl.getC_Payment(), I_C_Payment.class);
		Assert.assertEquals(bsl.getC_Invoice_ID(), payment.getC_Invoice_ID());
		Assert.assertEquals(bsl.getStmtAmt(), payment.getPayAmt());
		Assert.assertEquals(bsl.getTrxAmt(), payment.getPayAmt());
	}

	@Test
	public void testPaymentAllocateFromBankStatement()
	{
		final I_C_Invoice invoice1 = getHelper().mkInvoiceHelper().quickCreateInvoice(10);
		final I_C_Invoice invoice2 = getHelper().mkInvoiceHelper().quickCreateInvoice(20);
		final I_C_Invoice invoice3 = getHelper().mkInvoiceHelper().quickCreateInvoice(30);
		final I_C_Invoice invoice4 = getHelper().mkInvoiceHelper().quickCreateInvoice(40);

		final I_C_BP_BankAccount ba = getHelper().getC_BP_BankAccount(false);
		final I_C_BankStatement bs = getHelper().createPO(I_C_BankStatement.class);

		bs.setStatementDate(getHelper().getToday());
		bs.setAD_Org_ID(ba.getAD_Org_ID());
		bs.setC_BP_BankAccount_ID(ba.getC_BP_BankAccount_ID());
		bs.setName(getClass() + "_" + System.currentTimeMillis());
		InterfaceWrapperHelper.save(bs);

		final GridWindowHelper window = getHelper().mkGridWindowHelper();
		final I_C_BankStatementLine bsl = window.openFor(bs)
				.selectTab(I_C_BankStatementLine.Table_Name)
				.newRecord()
				.getGridTabInterface(I_C_BankStatementLine.class);

		bsl.setIsMultiplePaymentOrInvoice(true);
		bsl.setIsMultiplePayment(false);
		bsl.setStatementLineDate(getHelper().getToday());
		window.save();

		window.selectTab(I_C_BankStatementLine_Ref.Table_Name);
		createBankStatementLineRef(window, invoice1);
		createBankStatementLineRef(window, invoice2);
		createBankStatementLineRef(window, invoice3);
		createBankStatementLineRef(window, invoice4);

		// TODO: check BSL amounts

		window.selectTab(I_C_BankStatementLine.Table_Name);
		window.mkProcessHelper(I_C_BankStatementLine.COLUMNNAME_CreatePayment)
				.setExpectedClass(ProcessClass_BankStatementPayment)
				.run();
		window.refresh();
		Assert.assertTrue("Payment was not set on " + bsl, bsl.getC_Payment_ID() > 0);

		I_C_Payment payment = InterfaceWrapperHelper.create(bsl.getC_Payment(), I_C_Payment.class);
		assertAllocated(payment, bsl, invoice1, invoice2, invoice3, invoice4);
	}

	private void createBankStatementLineRef(GridWindowHelper window, I_C_Invoice invoice)
	{
		I_C_BankStatementLine_Ref bslRef = window.newRecord()
				.getGridTabInterface(I_C_BankStatementLine_Ref.class);
		bslRef.setC_Invoice_ID(invoice.getC_Invoice_ID());
		window.save();
	}

	private List<I_C_AllocationLine> fetchAllocationLines(I_C_Payment payment)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(payment);
		final String trxName = InterfaceWrapperHelper.getTrxName(payment);

		final String whereClause = I_C_AllocationLine.COLUMNNAME_C_Payment_ID + "=?";
		List<I_C_AllocationLine> list = new Query(ctx, I_C_AllocationLine.Table_Name, whereClause, trxName)
				.setParameters(payment.getC_Payment_ID())
				.list(I_C_AllocationLine.class);

		return list;
	}

	private void assertAllocated(I_C_Payment payment, I_C_BankStatementLine bsl, I_C_Invoice... invoices)
	{
		Assert.assertNotNull(payment);
		Assert.assertTrue(payment.getC_Payment_ID() > 0);
		Assert.assertEquals("Invalid payment docstatus " + payment.getDocStatus(), DocAction.STATUS_Completed, payment.getDocStatus());

		InterfaceWrapperHelper.refresh(payment);
		Assert.assertTrue("Payment is not allocateded: " + payment, payment.isAllocated());

		final Map<Integer, I_C_Invoice> invoicesMap = new HashMap<Integer, I_C_Invoice>();
		for (I_C_Invoice invoice : invoices)
		{
			Assert.assertNotNull(invoice);
			Assert.assertTrue(invoice.getC_Invoice_ID() > 0);
			invoicesMap.put(invoice.getC_Invoice_ID(), invoice);
		}

		final Map<Integer, I_C_Invoice> unallocatedInvoices = new HashMap<Integer, I_C_Invoice>(invoicesMap);
		final List<I_C_AllocationLine> allocationLines = fetchAllocationLines(payment);
		for (I_C_AllocationLine allocationLine : allocationLines)
		{
			final int invoiceId = allocationLine.getC_Invoice_ID();
			Assert.assertTrue("Invalid " + allocationLine, invoiceId > 0);
			Assert.assertNotNull("Invoice from " + allocationLine + " not found in invoices list", invoicesMap.get(invoiceId));

			final I_C_Invoice invoice = unallocatedInvoices.remove(invoiceId);
			Assert.assertNotNull("Invoice from " + allocationLine + " not found in unallocated invoices", invoice);

			InterfaceWrapperHelper.refresh(invoice);
			Assert.assertTrue("Invoice " + invoice + " shall be marked as paid", invoice.isPaid());
		}
		Assert.assertTrue("Unallocated invoices: " + unallocatedInvoices.values(), unallocatedInvoices.isEmpty());

		//
		// Validate bank statement line
		// TODO
	}

	@AfterClass
	public static void allListenersCalled()
	{
		assertAllListenersCalled(SalesTestDriver.class);
	}

}

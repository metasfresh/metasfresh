package de.metas.payment.esr.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;

/*
 * #%L
 * de.metas.payment.esr
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

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.junit.Test;

import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.ESRTestUtil;
import de.metas.payment.esr.actionhandler.impl.DunningESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.MoneyTransferedBackESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.UnableToAssignESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.WriteoffESRActionHandler;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;

public class ESRActionHandlerTest extends ESRTestBase
{

	protected I_C_Currency currencyEUR;

	/**
	 * Tests that this action basically does nothing
	 */
	@Test
	public void testDunningESRAction()
	{
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("000120686", "10", false, "000000010501536417000120686", "536417000120686", "01-059931-0", "15364170", "40", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		esrImportBL.evaluateLine(esrImportLine.getESR_Import(), esrImportLine);

		final ITrxRunConfig trxRunConfig = ESRTestUtil.createTrxRunconfig();
		esrImportBL.process(esrImport, trxRunConfig);

		refresh(esrImportLine, true);
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning);
		save(esrImportLine);

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning, new DunningESRActionHandler());
		esrImportBL.complete(esrImport, "", trxRunConfig);

		refresh(esrImport, true);
		refresh(esrImportLine, true);
		// Mark for dunning does nothing other than set the line to processed.
		assertTrue("Import should be processed", esrImport.isProcessed());
		assertTrue("Line should be processed", esrImportLine.isProcessed());

	}

	/**
	 * Tests that this action basically does nothing
	 */
	@Test
	public void testUnableToAssignAction()
	{
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("000120686", "10", false, "000000010501536417000120686", "536417000120686", "01-059931-0", "15364170", "40", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		esrImportBL.evaluateLine(esrImportLine.getESR_Import(), esrImportLine);

		final ITrxRunConfig trxRunConfig = ESRTestUtil.createTrxRunconfig();
		esrImportBL.process(esrImport, trxRunConfig);

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);

		save(esrImportLine);// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());

		esrImportBL.complete(esrImport, "", trxRunConfig);

		refresh(esrImport, true);
		refresh(esrImportLine, true);
		assertTrue("Import should be processed", esrImport.isProcessed());
		assertTrue("Line should be processed", esrImportLine.isProcessed());
	}

	/**
	 * Tests {@link X_ESR_ImportLine#ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner} with a payment of 40 matched against an invoice of 10.<br>
	 * Expects an outgoing payment payment of <b>40</b> to be created and allocated against the incoming payment.
	 */
	@Test
	public void testMoneyTransferedBackESRAction()
	{
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(
				"000120686", /*invoice doc number*/
				"10" /* invoice grandtotal */,  
				false, /*isInvoicePaid*/
				"000000010501536417000120686", /*complete ESR reference*/
				"536417000120686", /*invoice reference*/
				"01-059931-0", /*ESR account number*/
				"15364170", 
				"40", /* esr transaction amount*/
				false /*createAllocation*/);

		esrImportBL.evaluateLine(esrImportLine.getESR_Import(), esrImportLine);
		assertThat(esrImportLine.getC_Invoice(), notNullValue());
		assertThat(esrImportLine.getC_Invoice().getGrandTotal(), comparesEqualTo(new BigDecimal("10"))); // guard

		final I_ESR_Import esrImport = esrImportLine.getESR_Import();
		final ITrxRunConfig trxRunConfig = ESRTestUtil.createTrxRunconfig();
		esrImportBL.process(esrImport, trxRunConfig);

		assertThat("Expecting one payment after esrImportBL.process()", POJOLookupMap.get().getRecords(I_C_Payment.class).size(), is(1));

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
		save(esrImportLine);

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, new MoneyTransferedBackESRActionHandler());
		esrImportBL.complete(esrImport, "", trxRunConfig);

		refresh(esrImport, true);
		refresh(esrImportLine, true);

		assertTrue("Import should be processed", esrImport.isProcessed());
		assertTrue("Line should be processed", esrImportLine.isProcessed());

		assertEquals("Incorrect number of payments", 3, POJOLookupMap.get().getRecords(I_C_Payment.class).size());
		assertEquals("Incorrect number of allocations", 2, POJOLookupMap.get().getRecords(I_C_AllocationHdr.class).size());
		assertEquals("Incorrect number of allocation lines", 3, POJOLookupMap.get().getRecords(I_C_AllocationLine.class).size());

		final BigDecimal firstAmount = POJOLookupMap.get().getRecords(I_C_Payment.class).get(0).getPayAmt();
		final BigDecimal secondAmount = POJOLookupMap.get().getRecords(I_C_Payment.class).get(1).getPayAmt();
		final BigDecimal thirdAmount = POJOLookupMap.get().getRecords(I_C_Payment.class).get(2).getPayAmt();
		final BigDecimal firstOverUnder = POJOLookupMap.get().getRecords(I_C_Payment.class).get(0).getOverUnderAmt();
		final BigDecimal secondOverUnder = POJOLookupMap.get().getRecords(I_C_Payment.class).get(1).getOverUnderAmt();
		final BigDecimal thirdOverUnder = POJOLookupMap.get().getRecords(I_C_Payment.class).get(2).getOverUnderAmt();

		// Check values for payments.
		assertTrue("First payment has the wrong amount", (firstAmount.compareTo(new BigDecimal(40)) == 0));
		assertTrue("Second payment has the wrong amount", (secondAmount.compareTo(new BigDecimal(40)) == 0));
		assertTrue("Third payment has the wrong amount", (thirdAmount.compareTo(new BigDecimal(30)) == 0));
		assertTrue("First over/under has the wrong amount", (firstOverUnder.compareTo(new BigDecimal(0)) == 0));
		assertTrue("Second over/under has the wrong amount", (secondOverUnder.compareTo(new BigDecimal(30)) == 0));
		assertTrue("Third over/under has the wrong amount", (thirdOverUnder.compareTo(new BigDecimal(0)) == 0));

		final BigDecimal firstAllocLineAmount = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(0).getAmount();
		final BigDecimal secondAllocLineAmount = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(1).getAmount();
		final BigDecimal thirdAllocLineAmount = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(2).getAmount();

		// Check values for allocation lines.
		assertThat("First allocation has the wrong amount", firstAllocLineAmount, comparesEqualTo(new BigDecimal(10)));
		assertThat("Second allocation has the wrong amount", secondAllocLineAmount, comparesEqualTo(new BigDecimal(30)));
		assertThat("Third allocation has the wrong amount", thirdAllocLineAmount, comparesEqualTo(new BigDecimal(-30)));
	}

	@Test
	public void testWriteoffESRAction()
	{
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("000120686", "10", false, "000000010501536417000120686", "536417000120686", "01-059931-0", "15364170", "40", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		final I_C_Invoice invoice = POJOLookupMap.get().getRecords(I_C_Invoice.class).get(0);
		invoice.setGrandTotal(new BigDecimal(50.0));
		invoice.setIsSOTrx(true);
		invoice.setProcessed(true);
		save(invoice);

		esrImportBL.evaluateLine(esrImportLine.getESR_Import(), esrImportLine);

		final ITrxRunConfig trxRunConfig = ESRTestUtil.createTrxRunconfig();
		esrImportBL.process(esrImport, trxRunConfig);

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);

		save(esrImportLine);

		final I_C_Payment payment = POJOLookupMap.get().getRecords(I_C_Payment.class).get(0);
		assertThat(payment.getPayAmt(), comparesEqualTo(new BigDecimal("40"))); // guard

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount, new WriteoffESRActionHandler());
		esrImportBL.complete(esrImport, "", trxRunConfig);

		refresh(esrImport, true);
		refresh(esrImportLine, true);

		assertTrue("Import should be processed", esrImport.isProcessed());
		assertTrue("Line should be processed", esrImportLine.isProcessed());

		assertEquals("Incorrect number of allocations", 2, POJOLookupMap.get().getRecords(I_C_AllocationHdr.class).size());
		assertEquals("Incorrect number of allocation lines", 2, POJOLookupMap.get().getRecords(I_C_AllocationLine.class).size());

		// testAllocation sets the invoice isPaid status, if appropriate. Here, we test that it does not modify it a second time.
		// testAllocation is called the first time on esrImportBL.process.
		final boolean ignoreProcessed = false;
		Services.get(IInvoiceBL.class).testAllocation(invoice, ignoreProcessed);
		save(invoice);
		assertTrue("Invoice " + invoice + " shall be allocated", invoice.isPaid());

		final I_C_AllocationLine firstAllocationLine = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(0);
		final I_C_AllocationLine secondAllocationLine = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(1);

		// Test the invoice and allocations.
		assertThat("Invoice doesn't have the correct total", invoice.getGrandTotal(), comparesEqualTo(new BigDecimal("50"))); // guard this is the granttotal we set above. should still be the same
		assertThat("First allocation line doesn't have the correct total", firstAllocationLine.getAmount(), comparesEqualTo(new BigDecimal("40"))); // guard: this is the allocation line we created
																																					 // above. amoutn should be unchanged

		final BigDecimal openAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice);
		assertThat("Invoice still has an open amount", openAmt, comparesEqualTo(BigDecimal.ZERO));
		assertTrue("Invoice hasn't been paid", invoice.isPaid());
		assertThat("Second allocation line doesn't have the correct total", secondAllocationLine.getAmount(), comparesEqualTo(BigDecimal.ZERO));
		assertThat("Second allocation line doesn't have the correct writeoff", secondAllocationLine.getWriteOffAmt(), comparesEqualTo(new BigDecimal("10")));
		assertThat("Second allocation line doesn't reference our invoice", secondAllocationLine.getC_Invoice_ID(), greaterThan(0));
		assertThat("Second allocation line references a payment", secondAllocationLine.getC_Payment_ID(), is(-1));
	}
}

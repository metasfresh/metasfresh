package de.metas.payment.esr.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.ITrxRunConfig;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableFail;
import org.adempiere.ad.trx.api.ITrxRunConfig.OnRunnableSuccess;
import org.adempiere.ad.trx.api.ITrxRunConfig.TrxPropagation;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.junit.Assert;
import org.junit.Test;

import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.api.impl.ESRLineMatcher;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;

public class ESRActionHandlerTest extends ESRTestBase
{

	private ESRLineMatcher matcher;
	protected I_C_Currency currencyEUR;

	@Override
	public void init()
	{
		matcher = new ESRLineMatcher();
	}

	/**
	 * Tests that this action basically does nothing
	 */
	@Test
	public void testDunningESRAction()
	{
		final String esrImportLineText = "00201059931000000010501536417000120686900000040000012  190013011813011813012100015000400000000000000";
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("10", esrImportLineText, "536417000120686", "01-059931-0", "15364170", "000120686", false, false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		matcher.match(esrImportLine);
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		
		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		esrImportBL.process(esrImport, trxRunConfig);

		InterfaceWrapperHelper.refresh(esrImportLine, true);
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning);
		db.save(esrImportLine);

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning, new DunningESRActionHandler());
		esrImportBL.complete(esrImport, "", trxRunConfig);

		InterfaceWrapperHelper.refresh(esrImport, true);
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		// Mark for dunning does nothing other than set the line to processed.
		Assert.assertTrue("Import should be processed", esrImport.isProcessed());
		Assert.assertTrue("Line should be processed", esrImportLine.isProcessed());

	}

	/**
	 * Tests that this action basically does nothing
	 */
	@Test
	public void testUnableToAssignAction()
	{
		final String esrImportLineText = "00201059931000000010501536417000120686900000040000012  190013011813011813012100015000400000000000000";
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("10", esrImportLineText, "536417000120686", "01-059931-0", "15364170", "000120686", false, false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		matcher.match(esrImportLine);

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		esrImportBL.process(esrImport, trxRunConfig);

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);

		db.save(esrImportLine);// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());

		esrImportBL.complete(esrImport, "", trxRunConfig);

		InterfaceWrapperHelper.refresh(esrImport, true);
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		Assert.assertTrue("Import should be processed", esrImport.isProcessed());
		Assert.assertTrue("Line should be processed", esrImportLine.isProcessed());
	}

	/**
	 * Tests {@link X_ESR_ImportLine#ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner} with a payment of 40 matched against an invoice of 10. Expects only one payment of 40 to be created and
	 * allocated against the incoming payment.
	 */
	@Test
	public void testMoneyTransferedBackESRAction()
	{
		final String esrImportLineText = "00201059931000000010501536417000120686900000040000012  190013011813011813012100015000400000000000000";
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("10", esrImportLineText, "536417000120686", "01-059931-0", "15364170", "000120686", false, false);
				
		matcher.match(esrImportLine);
		assertThat(esrImportLine.getC_Invoice().getGrandTotal(), comparesEqualTo(new BigDecimal("10"))); // guard
		
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();
		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		esrImportBL.process(esrImport, trxRunConfig);
		
		assertThat("Expecting one payment after esrImportBL.process()", db.getRecords(I_C_Payment.class).size(), is(1));

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
		db.save(esrImportLine);

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, new MoneyTransferedBackESRActionHandler());
		esrImportBL.complete(esrImport, "", trxRunConfig);


		InterfaceWrapperHelper.refresh(esrImport, true);
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		
		Assert.assertTrue("Import should be processed", esrImport.isProcessed());
		Assert.assertTrue("Line should be processed", esrImportLine.isProcessed());

		Assert.assertEquals("Incorrect number of payments", 3, db.getRecords(I_C_Payment.class).size());
		Assert.assertEquals("Incorrect number of allocations", 2, db.getRecords(I_C_AllocationHdr.class).size());
		Assert.assertEquals("Incorrect number of allocation lines", 3, db.getRecords(I_C_AllocationLine.class).size());

		final BigDecimal firstAmount = db.getRecords(I_C_Payment.class).get(0).getPayAmt();
		final BigDecimal secondAmount = db.getRecords(I_C_Payment.class).get(1).getPayAmt();
		final BigDecimal thirdAmount = db.getRecords(I_C_Payment.class).get(2).getPayAmt();
		final BigDecimal firstOverUnder = db.getRecords(I_C_Payment.class).get(0).getOverUnderAmt();
		final BigDecimal secondOverUnder = db.getRecords(I_C_Payment.class).get(1).getOverUnderAmt();
		final BigDecimal thirdOverUnder = db.getRecords(I_C_Payment.class).get(2).getOverUnderAmt();

		// Check values for payments.
		Assert.assertTrue("First payment has the wrong amount", (firstAmount.compareTo(new BigDecimal(40)) == 0));
		Assert.assertTrue("Second payment has the wrong amount", (secondAmount.compareTo(new BigDecimal(40)) == 0));
		Assert.assertTrue("Third payment has the wrong amount", (thirdAmount.compareTo(new BigDecimal(30)) == 0));
		Assert.assertTrue("First over/under has the wrong amount", (firstOverUnder.compareTo(new BigDecimal(0)) == 0));
		Assert.assertTrue("Second over/under has the wrong amount", (secondOverUnder.compareTo(new BigDecimal(30)) == 0));
		Assert.assertTrue("Third over/under has the wrong amount", (thirdOverUnder.compareTo(new BigDecimal(0)) == 0));

		final BigDecimal firstAllocLineAmount = db.getRecords(I_C_AllocationLine.class).get(0).getAmount();
		final BigDecimal secondAllocLineAmount = db.getRecords(I_C_AllocationLine.class).get(1).getAmount();
		final BigDecimal thirdAllocLineAmount = db.getRecords(I_C_AllocationLine.class).get(2).getAmount();

		// Check values for allocation lines.
		assertThat("First allocation has the wrong amount", firstAllocLineAmount, comparesEqualTo(new BigDecimal(10)));
		assertThat("Second allocation has the wrong amount", secondAllocLineAmount, comparesEqualTo(new BigDecimal(30)));
		assertThat("Third allocation has the wrong amount", thirdAllocLineAmount, comparesEqualTo(new BigDecimal(-30)));
	}

	@Test
	public void testWriteoffESRAction()
	{
		final String esrImportLineText = "00201059931000000010501536417000120686900000040000012  190013011813011813012100015000400000000000000";
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("10", esrImportLineText, "536417000120686", "01-059931-0", "15364170", "000120686", false, false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		final I_C_Invoice invoice = db.getRecords(I_C_Invoice.class).get(0);
		invoice.setGrandTotal(new BigDecimal(50.0));
		invoice.setIsSOTrx(true);
		invoice.setProcessed(true);
		db.save(invoice);

		matcher.match(esrImportLine);

		final ITrxRunConfig trxRunConfig = Services.get(ITrxManager.class).createTrxRunConfig(TrxPropagation.REQUIRES_NEW, OnRunnableSuccess.COMMIT, OnRunnableFail.ASK_RUNNABLE);
		esrImportBL.process(esrImport, trxRunConfig);

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);

		db.save(esrImportLine);

		final I_C_Payment payment = db.getRecords(I_C_Payment.class).get(0);
		assertThat(payment.getPayAmt(), comparesEqualTo(new BigDecimal("40"))); // guard

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount, new WriteoffESRActionHandler());
		esrImportBL.complete(esrImport, "", trxRunConfig);

		InterfaceWrapperHelper.refresh(esrImport, true);
		InterfaceWrapperHelper.refresh(esrImportLine, true);
		
		Assert.assertTrue("Import should be processed", esrImport.isProcessed());
		Assert.assertTrue("Line should be processed", esrImportLine.isProcessed());

		Assert.assertEquals("Incorrect number of allocations", 2, db.getRecords(I_C_AllocationHdr.class).size());
		Assert.assertEquals("Incorrect number of allocation lines", 2, db.getRecords(I_C_AllocationLine.class).size());

		// testAllocation sets the invoice isPaid status, if appropriate. Here, we test that it does not modify it a second time.
		// testAllocation is called the first time on esrImportBL.process.
		final boolean ignoreProcessed = false;
		Services.get(IInvoiceBL.class).testAllocation(invoice, ignoreProcessed);
		InterfaceWrapperHelper.save(invoice);
		Assert.assertTrue("Invoice " + invoice + " shall be allocated", invoice.isPaid());

		final I_C_AllocationLine firstAllocationLine = db.getRecords(I_C_AllocationLine.class).get(0);
		final I_C_AllocationLine secondAllocationLine = db.getRecords(I_C_AllocationLine.class).get(1);

		// Test the invoice and allocations.
		assertThat("Invoice doesn't have the correct total", invoice.getGrandTotal(), comparesEqualTo(new BigDecimal("50"))); // guard this is the granttotal we set above. should still be the same
		assertThat("First allocation line doesn't have the correct total", firstAllocationLine.getAmount(), comparesEqualTo(new BigDecimal("40"))); // guard: this is the allocation line we created
																																					// above. amoutn should be unchanged

		final BigDecimal openAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(invoice);
		assertThat("Invoice still has an open amount", openAmt, comparesEqualTo(BigDecimal.ZERO));
		Assert.assertTrue("Invoice hasn't been paid", invoice.isPaid());
		assertThat("Second allocation line doesn't have the correct total", secondAllocationLine.getAmount(), comparesEqualTo(BigDecimal.ZERO));
		assertThat("Second allocation line doesn't have the correct writeoff", secondAllocationLine.getWriteOffAmt(), comparesEqualTo(new BigDecimal("10")));
		assertThat("Second allocation line doesn't reference our invoice", secondAllocationLine.getC_Invoice_ID(), greaterThan(0));
		assertThat("Second allocation line references a payment", secondAllocationLine.getC_Payment_ID(), is(-1));
	}
}

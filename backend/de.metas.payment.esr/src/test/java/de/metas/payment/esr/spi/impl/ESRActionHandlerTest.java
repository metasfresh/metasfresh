package de.metas.payment.esr.spi.impl;

import de.metas.currency.Amount;
import de.metas.currency.CurrencyCode;
import de.metas.invoice.InvoiceId;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.invoice.service.IInvoiceDAO;
import de.metas.payment.esr.ESRTestBase;
import de.metas.payment.esr.actionhandler.impl.DunningESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.MoneyTransferedBackESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.UnableToAssignESRActionHandler;
import de.metas.payment.esr.actionhandler.impl.WriteoffESRActionHandler;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.payment.esr.model.I_ESR_ImportLine;
import de.metas.payment.esr.model.X_ESR_ImportLine;
import de.metas.util.Services;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ESRActionHandlerTest extends ESRTestBase
{
	/**
	 * Tests that this action basically does nothing
	 */
	@Test
	public void testDunningESRAction()
	{
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("000120686", "10", false, "000000010501536417000120686", /* "536417000120686", */ "01-059931-0", "15364170", "40", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		esrImportBL.evaluateLine(esrImportLine);

		esrImportBL.process(esrImport);

		refresh(esrImportLine, true);
		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning);
		save(esrImportLine);

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Keep_For_Dunning, new DunningESRActionHandler());
		esrImportBL.complete(esrImport, "");

		refresh(esrImport, true);
		refresh(esrImportLine, true);
		// Mark for dunning does nothing other than set the line to processed.
		assertTrue(esrImport.isProcessed(), "Import should be processed");
		assertTrue(esrImportLine.isProcessed(), "Line should be processed");

	}

	/**
	 * Tests that this action basically does nothing
	 */
	@Test
	public void testUnableToAssignAction()
	{
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("000120686", "10", false, "000000010501536417000120686", /* "536417000120686", */ "01-059931-0", "15364170", "40", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		esrImportBL.evaluateLine(esrImportLine);

		esrImportBL.process(esrImport);

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income);

		save(esrImportLine);// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Unable_To_Assign_Income, new UnableToAssignESRActionHandler());

		esrImportBL.complete(esrImport, "");

		refresh(esrImport, true);
		refresh(esrImportLine, true);
		assertTrue(esrImport.isProcessed(), "Import should be processed");
		assertTrue(esrImportLine.isProcessed(), "Line should be processed");
	}

	/**
	 * Tests {@link X_ESR_ImportLine#ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner} with a payment of 40 matched against an invoice of 10.<br>
	 * Expects an outgoing payment payment of <b>40</b> to be created and allocated against the incoming payment.
	 */
	@Test
	public void testMoneyTransferedBackESRAction()
	{
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine(
				"000120686", /* invoice doc number */
				"10" /* invoice grandtotal */,
				false, /* isInvoicePaid */
				"000000010501536417000120686", /* complete ESR reference incl check digit */
				"01-059931-0", /* ESR account number */
				"15364170",
				"40", /* esr transaction amount */
				false /* createAllocation */);

		esrImportBL.evaluateLine(esrImportLine);
		assertThat(esrImportLine.getC_Invoice()).isNotNull();
		assertThat(esrImportLine.getC_Invoice().getGrandTotal()).isEqualByComparingTo("10"); // guard

		final I_ESR_Import esrImport = esrImportLine.getESR_Import();
		esrImportBL.process(esrImport);

		assertThat(POJOLookupMap.get().getRecords(I_C_Payment.class))
				.as("Expecting one payment after esrImportBL.process()")
				.hasSize(1);

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner);
		save(esrImportLine);

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Money_Was_Transfered_Back_to_Partner, new MoneyTransferedBackESRActionHandler());
		esrImportBL.complete(esrImport, "");

		refresh(esrImport, true);
		refresh(esrImportLine, true);

		assertTrue(esrImport.isProcessed(), "Import should be processed");
		assertTrue(esrImportLine.isProcessed(), "Line should be processed");

		assertThat(POJOLookupMap.get().getRecords(I_C_Payment.class)).hasSize(3);
		assertThat(POJOLookupMap.get().getRecords(I_C_AllocationHdr.class)).hasSize(2);
		assertThat(POJOLookupMap.get().getRecords(I_C_AllocationLine.class)).hasSize(3);

		final BigDecimal firstAmount = POJOLookupMap.get().getRecords(I_C_Payment.class).get(0).getPayAmt();
		final BigDecimal secondAmount = POJOLookupMap.get().getRecords(I_C_Payment.class).get(1).getPayAmt();
		final BigDecimal thirdAmount = POJOLookupMap.get().getRecords(I_C_Payment.class).get(2).getPayAmt();
		final BigDecimal firstOverUnder = POJOLookupMap.get().getRecords(I_C_Payment.class).get(0).getOverUnderAmt();
		final BigDecimal secondOverUnder = POJOLookupMap.get().getRecords(I_C_Payment.class).get(1).getOverUnderAmt();
		final BigDecimal thirdOverUnder = POJOLookupMap.get().getRecords(I_C_Payment.class).get(2).getOverUnderAmt();

		// Check values for payments.
		assertThat(firstAmount).isEqualByComparingTo("40");
		assertThat(secondAmount).isEqualByComparingTo("40");
		assertThat(thirdAmount).isEqualByComparingTo("30");
		assertThat(firstOverUnder).isEqualByComparingTo("0");
		assertThat(secondOverUnder).isEqualByComparingTo("30");
		assertThat(thirdOverUnder).isEqualByComparingTo("0");

		final BigDecimal firstAllocLineAmount = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(0).getAmount();
		final BigDecimal secondAllocLineAmount = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(1).getAmount();
		final BigDecimal thirdAllocLineAmount = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(2).getAmount();

		// Check values for allocation lines.
		assertThat(firstAllocLineAmount).isEqualByComparingTo("10");
		assertThat(secondAllocLineAmount).isEqualByComparingTo("30");
		assertThat(thirdAllocLineAmount).isEqualByComparingTo("-30");
	}

	@Test
	public void testWriteoffESRAction()
	{
		final I_ESR_ImportLine esrImportLine = setupESR_ImportLine("000120686", "10", false, "000000010501536417000120686", "01-059931-0", "15364170", "40", false);
		final I_ESR_Import esrImport = esrImportLine.getESR_Import();

		final I_C_Invoice invoice = getC_Invoice();
		invoice.setGrandTotal(new BigDecimal("50.0"));
		invoice.setIsSOTrx(true);
		invoice.setProcessed(true);
		save(invoice);

		esrImportBL.evaluateLine(esrImportLine);

		esrImportBL.process(esrImport);

		esrImportLine.setESR_Payment_Action(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount);

		save(esrImportLine);

		final I_C_Payment payment = POJOLookupMap.get().getRecords(I_C_Payment.class).get(0);
		assertThat(payment.getPayAmt()).isEqualByComparingTo("40"); // guard

		// We need to register the action handler
		esrImportBL.registerActionHandler(X_ESR_ImportLine.ESR_PAYMENT_ACTION_Write_Off_Amount, new WriteoffESRActionHandler());
		esrImportBL.complete(esrImport, "");

		refresh(esrImport, true);
		refresh(esrImportLine, true);

		assertTrue(esrImport.isProcessed(), "Import should be processed");
		assertTrue(esrImportLine.isProcessed(), "Line should be processed");

		assertThat(POJOLookupMap.get().getRecords(I_C_AllocationHdr.class)).hasSize(2);
		assertThat(POJOLookupMap.get().getRecords(I_C_AllocationLine.class)).hasSize(2);

		// testAllocation sets the invoice isPaid status, if appropriate. Here, we test that it does not modify it a second time.
		// testAllocation is called the first time on esrImportBL.process.
		final boolean ignoreProcessed = false;
		Services.get(IInvoiceBL.class).testAllocation(invoice, ignoreProcessed);
		save(invoice);
		assertInvoiceFullyPaid(invoice);

		final I_C_AllocationLine firstAllocationLine = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(0);
		final I_C_AllocationLine secondAllocationLine = POJOLookupMap.get().getRecords(I_C_AllocationLine.class).get(1);

		// Test the invoice and allocations.
		assertThat(invoice.getGrandTotal()).isEqualByComparingTo("50"); // guard this is the granttotal we set above. should still be the same
		assertThat(firstAllocationLine.getAmount()).isEqualByComparingTo("40"); // guard: this is the allocation line we created. above. amount should be unchanged

		final Amount openAmt = Services.get(IInvoiceDAO.class).retrieveOpenAmt(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
		assertThat(openAmt.toBigDecimal()).isZero();
		assertInvoiceFullyPaid(invoice);
		assertThat(secondAllocationLine.getAmount()).isZero();
		assertThat(secondAllocationLine.getWriteOffAmt()).isEqualByComparingTo("10");
		assertThat(secondAllocationLine.getC_Invoice_ID()).isGreaterThan(0);
		assertThat(secondAllocationLine.getC_Payment_ID()).isLessThanOrEqualTo(0);
	}
}

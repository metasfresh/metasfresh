package de.metas.invoicecandidate.api.impl.aggregationEngine;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolRepository;
import de.metas.document.invoicingpool.DocTypeInvoicingPoolService;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.X_C_DocType;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

/**
 *
 * <b>IMPORTANT:</b> these tests are still valid! Just the way the tests are implemented is "legacy".
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class LegacyAggregationEngineTests extends AbstractAggregationEngineTestBase
{
	private static final BigDecimal HUNDRET = new BigDecimal("100");

	@BeforeEach
	public void init()
	{
		super.init();

		SpringContextHolder.registerJUnitBean(new DocTypeInvoicingPoolService(new DocTypeInvoicingPoolRepository()));
	}
	
	@Test
	public void test_simple01()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(ic1, "ic1");

		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(ic2, "ic2");

		final I_C_Invoice_Candidate ic3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(ic3, "ic3");

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);
		InterfaceWrapperHelper.refresh(ic2);
		InterfaceWrapperHelper.refresh(ic3);

		final AggregationEngine engine = AggregationEngine.newInstance();
		engine.addInvoiceCandidate(ic1);
		engine.addInvoiceCandidate(ic2);
		engine.addInvoiceCandidate(ic3);

		final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);
		Assert.assertEquals("We are expecting only one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice = invoices.get(0);
		Assert.assertEquals("Invalid DocBaseType", InvoiceDocBaseType.CustomerInvoice, invoice.getDocBaseType());
		validateInvoiceHeader("Invoice", invoice, ic1);

		final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
		Assert.assertEquals("We are expecting one invoice line per IC: " + invoiceLines, 3, invoiceLines.size());

		assertLineCorrect(invoiceLines.get(0));
		assertLineCorrect(invoiceLines.get(1));
		assertLineCorrect(invoiceLines.get(2));
		// System.out.println(invoices);
	}

	private void assertLineCorrect(final IInvoiceLineRW invoiceLine1)
	{
		assertThat(invoiceLine1.getPriceActual().toMoney().toBigDecimal()).as("Invalid PriceActual").isEqualByComparingTo(ONE);
		assertThat(invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal()).as("Invalid QtyToInvoice").isEqualByComparingTo(ONE);
		assertThat(invoiceLine1.getNetLineAmt().toBigDecimal()).as("Invalid NetLineAmt").isEqualByComparingTo(TEN);
	}

	@Test
	public void test_API()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(false)
				.build();
		POJOWrapper.setInstanceName(ic1, "ic1");

		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(false)
				.build();
		POJOWrapper.setInstanceName(ic2, "ic2");

		final I_C_Invoice_Candidate ic3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(false)
				.build();
		POJOWrapper.setInstanceName(ic3, "ic3");

		updateInvalidCandidates();

		final AggregationEngine engine = AggregationEngine.newInstance();
		engine.addInvoiceCandidate(ic1);
		engine.addInvoiceCandidate(ic2);
		engine.addInvoiceCandidate(ic3);

		final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);
		Assert.assertEquals("We are expecting only one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice = invoices.get(0);
		Assert.assertEquals("Invalid DocBaseType", InvoiceDocBaseType.VendorInvoice, invoice.getDocBaseType());
		validateInvoiceHeader("Invoice", invoice, ic1);

		final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
		Assert.assertEquals("We are expecting one invoice line per IC: " + invoiceLines, 3, invoiceLines.size());

		assertLineCorrect(invoiceLines.get(0));
		assertLineCorrect(invoiceLines.get(1));
		assertLineCorrect(invoiceLines.get(2));
		// System.out.println(invoices);
	}

	@Test
	public void test_creditMemoLine()
	{
		// we also need a regular invoice candidate, or 'updateInvalidCandidates()' won't set the NetAmtToInvoice of the manual candidate to a positive value.
		// but note that we will only invoice the manual candidate, and not 'regularIc1'.

		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		final I_C_Invoice_Candidate regularIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(10)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();
		InterfaceWrapperHelper.save(regularIc1);

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-10)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();
		manualIc1.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc1);

		updateInvalidCandidates();

		final AggregationEngine engine = AggregationEngine.newInstance();
		engine.addInvoiceCandidate(manualIc1);

		final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);
		Assert.assertEquals("We are expecting only one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice = invoices.get(0);
		Assert.assertEquals("Invalid DocBaseType", InvoiceDocBaseType.CustomerCreditMemo, invoice.getDocBaseType());
		validateInvoiceHeader("Invoice", invoice, manualIc1);

		final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
		Assert.assertEquals("We are expecting only one invoice line: " + invoiceLines, 1, invoiceLines.size());

		final IInvoiceLineRW invoiceLine = invoiceLines.get(0);
		assertThat(invoiceLine.getPriceActual().toMoney().toBigDecimal()).as("Invalid PriceActual").isEqualByComparingTo(TEN);
		assertThat(invoiceLine.getQtysToInvoice().getStockQty().toBigDecimal()).as("Invalid QtyToInvoice").isEqualByComparingTo(ONE);
		assertThat(invoiceLine.getNetLineAmt().toBigDecimal()).as("Invalid NetLineAmt").isEqualByComparingTo(HUNDRET); // price=10 times qtyInUom=10

		// System.out.println(invoices);
	}

	@Test
	public void test_APIcreditMemoLine()
	{
		// we also need a regular invoice candidate, or 'updateInvalidCandidates()' won't set the NetAmtToInvoice of the manual candidate to a positive value.
		// but note that we will only invoice the manual candidate, and not 'regularIc1'.

		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		final I_C_Invoice_Candidate regularIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(10)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(false)
				.build();
		InterfaceWrapperHelper.save(regularIc1);

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-10)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(false)
				.build();

		manualIc1.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc1);

		updateInvalidCandidates();

		final AggregationEngine engine = AggregationEngine.newInstance();
		engine.addInvoiceCandidate(manualIc1);

		final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);
		Assert.assertEquals("We are expecting only one invoice: " + invoices, 1, invoices.size());

		final IInvoiceHeader invoice = invoices.get(0);
		Assert.assertEquals("Invalid DocBaseType", InvoiceDocBaseType.VendorCreditMemo, invoice.getDocBaseType());
		validateInvoiceHeader("Invoice", invoice, manualIc1);

		final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
		Assert.assertEquals("We are expecting only one invoice line: " + invoiceLines, 1, invoiceLines.size());

		final IInvoiceLineRW invoiceLine = invoiceLines.get(0);
		assertThat(invoiceLine.getPriceActual().toMoney().toBigDecimal()).as("Invalid PriceActual").isEqualByComparingTo(TEN);
		assertThat(invoiceLine.getQtysToInvoice().getStockQty().toBigDecimal()).as("Invalid QtyToInvoice").isEqualByComparingTo(ONE);
		assertThat(invoiceLine.getNetLineAmt().toBigDecimal()).as("Invalid NetLineAmt").isEqualByComparingTo(HUNDRET); // price=10 times qtyInUom=10

		// System.out.println(invoices);
	}

	@Test
	@Ignore("this test is not working atm, maybe it's also too old")
	// FIXME: this test is not working atm, maybe it's also too old
	public void test_regularLines_with_CreditMemo_notFullyInvoiced()
	{
		// ic1 has netAmtToInvoice = 50
		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(1, 10, 5, false, true); // BP,Price,Qty
		POJOWrapper.setInstanceName(ic1, "ic1");
		InterfaceWrapperHelper.save(ic1);
		InterfaceWrapperHelper.refresh(ic1);

		// ic2 has netAmtToInvoice = -60, so we will expect a splitAmount of -10
		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(1, -60, 1, true, true); // BP,Price,Qty
		ic2.setC_ILCandHandler(manualHandler);
		POJOWrapper.setInstanceName(ic2, "ic2");
		InterfaceWrapperHelper.save(ic2);
		InterfaceWrapperHelper.refresh(ic2);

		//
		// Update candidates
		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);
		InterfaceWrapperHelper.refresh(ic2);

		// Check IC1: NetAmtToInvoice=50
		assertThat("IC1 - Invalid NetAmtToInvoice", ic1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		// Check IC2: NetAmtToInvoice=-50 (balanced with IC1), SplitAmt=-10
		assertThat("IC2 - Invalid NetAmtToInvoice", ic2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat("IC2 - Invalid SplitAmt", ic2.getSplitAmt(), comparesEqualTo(new BigDecimal("-10")));

		//
		// Generate invoice
		{
			final AggregationEngine engine = AggregationEngine.newInstance();
			engine.addInvoiceCandidate(ic1);
			engine.addInvoiceCandidate(ic2);

			final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);
			Assert.assertEquals("We are expecting only one invoice: " + invoices, 1, invoices.size());

			final IInvoiceHeader invoice = invoices.get(0);
			Assert.assertEquals("Invalid DocBaseType", X_C_DocType.DOCBASETYPE_ARInvoice, invoice.getDocBaseType());
			validateInvoiceHeader("Invoice", invoice, ic1);

			final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
			Assert.assertEquals("We are expecting 2 invoice lines: " + invoiceLines, 2, invoiceLines.size());

			// Invoice Line 1:
			final IInvoiceLineRW invoiceLine1 = getInvoiceLineByCandidate(invoice, ic1);
			assertThat(invoiceLine1.getPriceActual().toMoney().toBigDecimal()).as("InvoiceLine1 - Invalid PriceActual").isEqualByComparingTo(TEN);
			assertThat(invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal()).as("InvoiceLine1 - Invalid QtyToInvoice").isEqualByComparingTo("5");
			assertThat(invoiceLine1.getNetLineAmt().toBigDecimal()).as("InvoiceLine1 - Invalid NetLineAmt").isEqualByComparingTo("50");

			// Invoice Line 2:
			// NOTE: only -50 was invoiced. "-10" was left in SplitAmt
			final IInvoiceLineRW invoiceLine2 = getInvoiceLineByCandidate(invoice, ic2);
			assertThat(invoiceLine2.getPriceActual().toMoney().toBigDecimal()).as("InvoiceLine2 - Invalid PriceActual").isEqualByComparingTo("-50");
			assertThat(invoiceLine2.getQtysToInvoice().getStockQty().toBigDecimal()).as("InvoiceLine2 - Invalid QtyToInvoice").isEqualByComparingTo(ONE);
			assertThat(invoiceLine2.getNetLineAmt().toBigDecimal()).as("InvoiceLine2 - Invalid NetLineAmt").isEqualByComparingTo("-50");
		}

		//
		// Update candidates
		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);
		InterfaceWrapperHelper.refresh(ic2);

		//
		// Manually updating the Invoice Candidates (as it should be after invoicing)
		{
			ic1.setQtyInvoiced(ic1.getQtyToInvoice());
			ic1.setNetAmtInvoiced(ic1.getNetAmtToInvoice());
			// getInvoiceCandidateValidator().updateProcessedFlag(ic1);
			ic1.setProcessed(true); // need to set it manually; updateProcessedFlag() also checks for C_Invoice_Line_Allocs (created from de.metas.invoicecandidate.modelvalidator.C_Invoice) and we
			// don't have any.
			InterfaceWrapperHelper.save(ic1);
			Assert.assertTrue("IC1 - shall be marked as Processed: " + ic1, ic1.isProcessed());
		}
		{
			ic2.setQtyInvoiced(ic2.getQtyToInvoice());
			ic2.setNetAmtInvoiced(ic2.getNetAmtToInvoice());
			// getInvoiceCandidateValidator().updateProcessedFlag(ic2);
			ic2.setProcessed(true); // need to set it manually; updateProcessedFlag() also checks for C_Invoice_Line_Allocs (created from de.metas.invoicecandidate.modelvalidator.C_Invoice) and we
			// don't have any.
			InterfaceWrapperHelper.save(ic2);
			Assert.assertTrue("IC2 - shall be marked as Processed: " + ic2, ic2.isProcessed());
		}

		//
		//
		// SECOND RUN
		//
		//

		//
		// Manually split the credit memo line.
		// In production this will be generated when we generate the invoice
		final I_C_Invoice_Candidate ic2_split = invoiceCandBL.splitCandidate(ic2);
		POJOWrapper.setInstanceName(ic2_split, "ic2_split");
		getInvoiceCandidateValidator().invalidateCandidatesAfterChange(ic2_split);
		InterfaceWrapperHelper.save(ic2_split);

		//
		// Update candidates
		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic1);
		InterfaceWrapperHelper.refresh(ic2);
		InterfaceWrapperHelper.refresh(ic2_split);

		//
		// Validate ic2's split candidate
		// expecting getNetAmtToInvoice to be 0 because we don't have a "regular" IC to "split against"
		assertThat("IC2_split - Invalid NetAmtToInvoice", ic2_split.getNetAmtToInvoice(), comparesEqualTo(BigDecimal.ZERO));
		assertThat("IC2_split - Invalid SplitAmt", ic2_split.getSplitAmt(), comparesEqualTo(new BigDecimal("-10")));

		//
		// Creating another non-manual invoice candidate: Price=7, Qty=2 => NetAmtToInvoice=14
		// We will expect the NetAmtToInvoice of ic2_split to be unaffected, because ic3 was created after ic2_split
		final I_C_Invoice_Candidate ic3 = createInvoiceCandidate(1, 7, 2, false, true); // BP, Price, Qty, IsManual, IsSOTrx
		POJOWrapper.setInstanceName(ic3, "ic3");
		InterfaceWrapperHelper.save(ic3);

		//
		// Update candidates
		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(ic2_split);
		InterfaceWrapperHelper.refresh(ic3);

		// Validate IC3
		assertThat("IC3 - Invalid NetAmtToInvoice", ic3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("14")));
		assertThat("IC3 - Invalid SplitAmt", ic3.getSplitAmt(), comparesEqualTo(new BigDecimal("0")));
		//
		// @formatter:off
		//
		// Validate ic2's split: check how it changed
		// Before IC3:
		//				IC1:		NetAmtToInvoice=50
		//				IC2:		NetAmtToInvoice=-60
		//				IC2_split:	NetAmtToInvoice=0, SplitAmt=-10
		//							=> SUM: 50-60 = -10 (negative)
		//	=> SplitAmt=-10, NetAmtToInvoice=0
		//
		//
		// After IC3:
		//				IC1:		NetAmtToInvoice=50 (did not changed)
		//				IC2:		NetAmtToInvoice=-60 (did not changed)
		//				IC3:		NetAmtToInvoice=14 (the new one)
		//							=> SUM: 50-60+14 = +4 (positive)
		//	=> SplitAmt=-6, NetAmtToInvoice=-4
		//
		// @formatter:on
		//
		// FIXME: need to find out why we have this numbers (maybe I am too tired)
		assertThat("IC2_split - Invalid NetAmtToInvoice", ic2_split.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-4")));
		assertThat("IC2_split - Invalid SplitAmt", ic2_split.getSplitAmt(), comparesEqualTo(new BigDecimal("-6")));

		//
		// Generate invoice (again) for IC2_split, IC3
		{
			final AggregationEngine engine = AggregationEngine.newInstance();
			// engine2.addIC(ic1); // already processed
			// engine2.addIC(ic2); // already processed
			engine.addInvoiceCandidate(ic2_split);
			engine.addInvoiceCandidate(ic3);

			final List<IInvoiceHeader> invoices = engine.aggregate();
			Assert.assertEquals("RUN2 - We are expecting only one invoice: " + invoices, 1, invoices.size());

			final IInvoiceHeader invoice = invoices.get(0);
			Assert.assertEquals("RUN2 - Invalid DocBaseType", X_C_DocType.DOCBASETYPE_ARInvoice, invoice.getDocBaseType());
			final boolean invoiceReferencesOrder = false;
			validateInvoiceHeader("Invoice", invoice, ic3, invoiceReferencesOrder);

			final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
			Assert.assertEquals("RUN2 - invalid expected lines count: " + invoiceLines, 2, invoiceLines.size());

			// Invoice Line 1:
			final IInvoiceLineRW invoiceLine1 = getInvoiceLineByCandidate(invoice, ic3);
			assertThat(invoiceLine1.getPriceActual().toMoney().toBigDecimal()).as("InvoiceLine1 - Invalid PriceActual").isEqualByComparingTo("7");
			assertThat(invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal()).as("InvoiceLine1 - Invalid QtyToInvoice").isEqualByComparingTo("2");
			assertThat(invoiceLine1.getNetLineAmt().toBigDecimal()).as("InvoiceLine1 - Invalid NetLineAmt").isEqualByComparingTo("14");

			// Invoice Line 2:
			final IInvoiceLineRW invoiceLine2 = getInvoiceLineByCandidate(invoice, ic2_split);
			assertThat(invoiceLine2.getPriceActual().toMoney().toBigDecimal()).as("InvoiceLine2 - Invalid PriceActual").isEqualByComparingTo("-4");
			assertThat(invoiceLine2.getQtysToInvoice().getStockQty().toBigDecimal()).as("InvoiceLine2 - Invalid QtyToInvoice").isEqualByComparingTo("1");
			assertThat(invoiceLine2.getNetLineAmt().toBigDecimal()).as("InvoiceLine2 - Invalid NetLineAmt").isEqualByComparingTo("-4");
		}
	}

	/**
	 * If Negative amount is above positive amount, then NetAmtToInvoice is adjusted but Qty shall be ONE.
	 *
	 * @task http://dewiki908/mediawiki/index.php/03908_Gutschriften_Verrechnung_%282013021410000034%29
	 */
	@Test
	public void test_regularLines_with_PartialCreditMemo_QtyNotOne()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(bPartner.getC_BPartner_ID(), 10, 5, true, true);
		InterfaceWrapperHelper.save(ic1);

		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(bPartner.getC_BPartner_ID(), -30, 2, true, true);
		ic2.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(ic2);
		Assert.assertEquals("IC2- IsError", false, ic2.isError());

		// shall throw exception
		// but because the exception is catched, the IC's IsError shall be set to true
		updateInvalidCandidates();

		InterfaceWrapperHelper.refresh(ic2);
		Assert.assertEquals("IC2- IsError", true, ic2.isError());
	}
}

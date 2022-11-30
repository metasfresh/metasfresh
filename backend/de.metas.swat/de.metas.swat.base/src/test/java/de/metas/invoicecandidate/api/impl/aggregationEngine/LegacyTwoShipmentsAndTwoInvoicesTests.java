package de.metas.invoicecandidate.api.impl.aggregationEngine;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.inout.model.I_M_InOut;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoice.InvoiceDocBaseType;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.IInvoiceLineRW;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_MatchInv;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * <b>IMPORTANT:</b> these tests are still valid! It's just the way they are implemented that is "legacy".
 */
public class LegacyTwoShipmentsAndTwoInvoicesTests extends AbstractAggregationEngineTestBase
{
	@Override
	public void init()
	{
		super.init();

		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
	}

	/**
	 * 06630 (Invoice Rule: "Nach Lieferung")<br>
	 * <br>
	 * Case 1: Partial shipment existing (32)<br>
	 * -> partial invoice<br>
	 * -> create a shipment for the rest that wasn't shipped yet (8)<br>
	 * -> another partial invoice
	 */
	@Test
	public void test_2StepShipment_WithDifferentInvoicesPerStep_IR_NachLieferung()
	{
		// FIXME: consider using theories
		test_2StepShipment_WithDifferentInvoicesPerStep(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
	}

	@SuppressWarnings("unused")
	private void test_2StepShipment_WithDifferentInvoicesOverall(final String invoiceRuleOverride)
	{
		final I_C_Invoice_Candidate ic = test_2StepShipment_CommonSetup_Step01(false, null).get(0); // isSOTrx, priceEntered_Override

		//
		// Partially invoice both at the same time
		final I_M_InOut inOut1;
		final I_M_InOutLine iol11;
		final StockQtyAndUOMQty partialQty1_32 = StockQtyAndUOMQtys.create(new BigDecimal("32"), productId, new BigDecimal("320"), uomId);
		{
			final String inOutDocumentNo = "1";
			inOut1 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo); // DocumentNo
			iol11 = createInvoiceCandidateInOutLine(ic, inOut1, partialQty1_32, inOutDocumentNo); // inOutLineDescription
			completeInOut(inOut1);
		}

		final I_M_InOut inOut2;

		final StockQtyAndUOMQty partialQty2_8 = StockQtyAndUOMQtys.create(new BigDecimal("8"), productId, new BigDecimal("80"), uomId);
		final I_M_InOutLine iol21;
		{
			final String inOutDocumentNo = "2";
			inOut2 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), inOutDocumentNo); // DocumentNo
			iol21 = createInvoiceCandidateInOutLine(ic, inOut2, partialQty2_8, inOutDocumentNo); // inOutLineDescription
			completeInOut(inOut2);
		}

		ic.setInvoiceRule_Override(invoiceRuleOverride);
		InterfaceWrapperHelper.save(ic);

		// updateInvalidCandidates(); makes no sense because we didn't invalidate it
		// InterfaceWrapperHelper.refresh(ic);

		// guard; this is tested more in-depth in InvoiceCandBLUpdateInvalidCandidatesTest
		assertThat("Invalid QtyToDeliver on the IC level", ic.getQtyDelivered(), comparesEqualTo(partialQty1_32.add(partialQty2_8).getStockQty().toBigDecimal()));
		assertThat("Invalid QtyToInvoice on the IC level", ic.getQtyToInvoice(), comparesEqualTo(partialQty1_32.add(partialQty2_8).getStockQty().toBigDecimal()));

		final AggregationEngine engine = AggregationEngine.newInstance();
		engine.addInvoiceCandidate(ic);

		final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);
		Assert.assertEquals("We were expecting two invoices but we got: " + invoices, 2, invoices.size());

		//
		// Assume first invoice is OK
		{
			final IInvoiceHeader invoice1 = removeInvoiceHeaderForInOutId(invoices, inOut1.getM_InOut_ID());
			Assert.assertEquals("Invalid DocBaseType", InvoiceDocBaseType.VendorInvoice, invoice1.getDocBaseType());
			Assert.assertEquals("Invalid M_InOut_ID", inOut1.getM_InOut_ID(), invoice1.getM_InOut_ID());
			validateInvoiceHeader("Invoice", invoice1, ic);

			final List<IInvoiceLineRW> invoiceLines1 = getInvoiceLines(invoice1);
			Assert.assertEquals("We are expecting only one invoice line: " + invoiceLines1, 1, invoiceLines1.size());

			final IInvoiceLineRW invoiceLine1 = getSingleForInOutLine(invoiceLines1, iol11);
			assertNotNull("Missing IInvoiceLineRW for iol11=" + iol11, invoiceLine1);
			assertThat(invoiceLine1.getC_InvoiceCandidate_InOutLine_IDs().size(), equalTo(1));
			Assert.assertEquals("Invalid PriceActual", 1, invoiceLine1.getPriceActual().toBigDecimal().intValueExact());
			assertThat("Invalid QtyToInvoice", invoiceLine1.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(partialQty1_32.getStockQty().toBigDecimal()));
			assertThat("Invalid NetLineAmt", invoiceLine1.getNetLineAmt().toBigDecimal(), comparesEqualTo(partialQty1_32.getUOMQtyNotNull().toBigDecimal()) /* because price=1 */);
		}

		//
		// Assume second invoice is OK
		{
			final IInvoiceHeader invoice2 = removeInvoiceHeaderForInOutId(invoices, inOut2.getM_InOut_ID());
			Assert.assertEquals("Invalid DocBaseType", InvoiceDocBaseType.VendorInvoice, invoice2.getDocBaseType());
			Assert.assertEquals("Invalid M_InOut_ID", inOut2.getM_InOut_ID(), invoice2.getM_InOut_ID());
			validateInvoiceHeader("Invoice", invoice2, ic);

			final List<IInvoiceLineRW> invoiceLines2 = getInvoiceLines(invoice2);
			Assert.assertEquals("We are expecting only one invoice line: " + invoiceLines2, 1, invoiceLines2.size());

			final IInvoiceLineRW invoiceLine2 = getSingleForInOutLine(invoiceLines2, iol21);
			assertNotNull("Missing IInvoiceLineRW for iol21=" + iol21, invoiceLine2);
			assertThat(invoiceLine2.getC_InvoiceCandidate_InOutLine_IDs().size(), equalTo(1));
			Assert.assertEquals("Invalid PriceActual", 1, invoiceLine2.getPriceActual().toBigDecimal().intValueExact());
			assertThat("Invalid QtyToInvoice", invoiceLine2.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(partialQty2_8.getStockQty().toBigDecimal()));
			assertThat("Invalid NetLineAmt", invoiceLine2.getNetLineAmt().toBigDecimal(), comparesEqualTo(partialQty2_8.getUOMQtyNotNull().toBigDecimal()) /* remember, price=1 */);
		}

		//
		// Make sure all invoices were evaluated
		Assert.assertEquals("All generated invoices should be evaluated", Collections.emptyList(), invoices);
	}

	private void test_2StepShipment_WithDifferentInvoicesPerStep(final String invoiceRuleOverride)
	{
		final int qtyOrdered = 40;

		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		
		final I_C_Invoice_Candidate ic = createInvoiceCandidate()
				.setInstanceName("ic")
				.setBillBPartnerId(bPartner.getC_BPartner_ID())
				.setPriceEntered(1)
				.setQtyOrdered(qtyOrdered)
				.setSOTrx(false)
				.setOrderDocNo("order1")
				.setOrderLineDescription("orderline1_1")
				.build();

		final I_M_InOut inOut1;
		final I_M_InOutLine iol11;

		//
		// Partially invoice 1
		final StockQtyAndUOMQty partialQty1_32 = StockQtyAndUOMQtys.create(new BigDecimal("32"), productId, new BigDecimal("320"), uomId);
		{
			{
				inOut1 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), "1"); // DocumentNo
				iol11 = createInvoiceCandidateInOutLine(ic, inOut1, partialQty1_32, "1"); // inOutLineDescription
				completeInOut(inOut1);

				//
				// Invoice partially
				ic.setInvoiceRule_Override(invoiceRuleOverride);
				InterfaceWrapperHelper.save(ic);
			}

			updateInvalidCandidates();
			InterfaceWrapperHelper.refresh(ic);
			// guard; this is tested more in-depth in InvoiceCandBLUpdateInvalidCandidatesTest
			assertThat("Invalid QtyDelivered on the IC level", ic.getQtyDelivered(), comparesEqualTo(partialQty1_32.getStockQty().toBigDecimal()));
			if (X_C_Invoice_Candidate.INVOICERULE_AfterDelivery.equals(invoiceRuleOverride))
			{
				assertThat("Invalid QtyToInvoice on the IC level", ic.getQtyToInvoice(), comparesEqualTo(partialQty1_32.getStockQty().toBigDecimal()));
			}
			else
			{
				assertThat("Invalid QtyToInvoice on the IC level", ic.getQtyToInvoice(), comparesEqualTo(new BigDecimal(qtyOrdered)));
			}

			final AggregationEngine engine = AggregationEngine.newInstance();
			engine.addInvoiceCandidate(ic);

			final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);
			Assert.assertEquals("We are expecting only one invoice: " + invoices, 1, invoices.size());

			final IInvoiceHeader invoice = invoices.get(0);
			Assert.assertEquals("Invalid DocBaseType", InvoiceDocBaseType.VendorInvoice, invoice.getDocBaseType());
			Assert.assertEquals("Invalid M_InOut_ID", inOut1.getM_InOut_ID(), invoice.getM_InOut_ID());
			validateInvoiceHeader("Invoice", invoice, ic);

			final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
			Assert.assertEquals("We are expecting only one invoice line: " + invoiceLines, 1, invoiceLines.size());

			final IInvoiceLineRW invoiceLine = getSingleForInOutLine(invoiceLines, iol11);
			assertNotNull("Missing IInvoiceLineRW for iol21=" + iol11, invoiceLine);
			assertThat(invoiceLine.getC_InvoiceCandidate_InOutLine_IDs().size(), equalTo(1));
			assertThat("Invalid PriceActual", invoiceLine.getPriceActual().toBigDecimal(), comparesEqualTo(BigDecimal.ONE) /* the price set above */);
			assertThat("Invalid QtyToInvoice", invoiceLine.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(partialQty1_32.getStockQty().toBigDecimal()));
			assertThat("Invalid NetLineAmt", invoiceLine.getNetLineAmt().toBigDecimal(), comparesEqualTo(partialQty1_32.getUOMQtyNotNull().toBigDecimal()));
		}

		//
		// create a matchInv record for iol11 to make sure that the system know that qty is already invoiced.
		final I_M_MatchInv im11 = InterfaceWrapperHelper.newInstance(I_M_MatchInv.class, iol11);
		im11.setQty(partialQty1_32.getStockQty().toBigDecimal());
		im11.setQtyInUOM(partialQty1_32.getUOMQtyNotNull().toBigDecimal());
		im11.setC_UOM_ID(partialQty1_32.getUOMQtyNotNull().getUomId().getRepoId());
		im11.setM_InOut_ID(iol11.getM_InOut_ID());
		im11.setM_InOutLine(iol11);
		InterfaceWrapperHelper.save(im11);

		// makes no sense, because currently revalidation would fail anyways, because it tries to get the invoice's docStatus and we don't have any
		// final I_C_Invoice_Line_Alloc ila = InterfaceWrapperHelper.newInstance(I_C_Invoice_Line_Alloc.class, ic);
		// ila.setC_Invoice_Candidate(ic);
		// ila.setQtyInvoiced(partialQty1);
		// InterfaceWrapperHelper.save(ila);
		// InterfaceWrapperHelper.save(ic);

		//
		// Partially invoice 2
		final StockQtyAndUOMQty partialQty2_8 = StockQtyAndUOMQtys.create(new BigDecimal("8"), productId, new BigDecimal("80"), uomId);
		{
			final I_M_InOut inOut2;
			{
				inOut2 = createInOut(ic.getBill_BPartner_ID(), ic.getC_Order_ID(), "2"); // DocumentNo
				createInvoiceCandidateInOutLine(ic, inOut2, partialQty2_8, "2"); // inOutLineDescription
				completeInOut(inOut2);

				//
				// Invoice partially
				ic.setInvoiceRule_Override(invoiceRuleOverride);

				ic.setQtyInvoiced(partialQty1_32.getStockQty().toBigDecimal());
				ic.setQtyInvoicedInUOM(partialQty1_32.getUOMQtyNotNull().toBigDecimal());

				ic.setQtyToInvoice(partialQty2_8.getStockQty().toBigDecimal());
				ic.setQtyToInvoiceInUOM(partialQty2_8.getUOMQtyNotNull().toBigDecimal());
				InterfaceWrapperHelper.save(ic);
			}

			// updateInvalidCandidates();
			InterfaceWrapperHelper.refresh(ic);
			// guard; this is tested more in-depth in InvoiceCandBLUpdateInvalidCandidatesTest
			assertThat("Invalid QtyDelivered on the IC level", ic.getQtyDelivered(), comparesEqualTo(partialQty1_32.add(partialQty2_8).getStockQty().toBigDecimal()));
			// TODO this is not working atm (instead of 8, we get 32)
			// assertThat("Invalid QtyToInvoice on the IC level", ic.getQtyToInvoice(), comparesEqualTo(partialQty2));

			final AggregationEngine engine = AggregationEngine.newInstance();
			engine.addInvoiceCandidate(ic);

			final List<IInvoiceHeader> invoices = invokeAggregationEngine(engine);
			// System.out.println(invoices);
			Assert.assertEquals("We are expecting only one invoice: " + invoices, 1, invoices.size());

			final IInvoiceHeader invoice = invoices.get(0);
			Assert.assertEquals("Invalid DocBaseType", InvoiceDocBaseType.VendorInvoice, invoice.getDocBaseType());
			// Assert.assertEquals("Invalid M_InOut_ID", inOut2.getM_InOut_ID(), invoice.getM_InOut_ID());
			validateInvoiceHeader("Invoice", invoice, ic);

			final List<IInvoiceLineRW> invoiceLines = getInvoiceLines(invoice);
			Assert.assertEquals("We are expecting only one invoice line: " + invoiceLines, 1, invoiceLines.size());

			final IInvoiceLineRW invoiceLine = invoiceLines.get(0);
			Assert.assertEquals("Invalid PriceActual", 1, invoiceLine.getPriceActual().toBigDecimal().intValue());
			assertThat("Invalid QtysToInvoice", invoiceLine.getQtysToInvoice().getStockQty().toBigDecimal(), comparesEqualTo(partialQty2_8.getStockQty().toBigDecimal()));
			assertThat("Invalid NetLineAmt", invoiceLine.getNetLineAmt().toBigDecimal(), comparesEqualTo(partialQty2_8.getUOMQtyNotNull().toBigDecimal()) /* price=1 */);
		}
	}
}

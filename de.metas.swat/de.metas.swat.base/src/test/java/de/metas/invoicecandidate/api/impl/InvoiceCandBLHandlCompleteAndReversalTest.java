/**
 *
 */
package de.metas.invoicecandidate.api.impl;

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

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Test;

import de.metas.document.engine.IDocument;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.C_Invoice_Candidate_Builder;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;

/**
 *
 */
public class InvoiceCandBLHandlCompleteAndReversalTest extends AbstractICTestSupport
{

	private static final BigDecimal INVOICE_QTY_INVOICED_TEN = new BigDecimal("10");
	private static final BigDecimal CREDI_MEMO_QTY_INVOICE_NINE = new BigDecimal("9");

	private static final String creditmemoDocTypeBaseName = X_C_DocType.DOCBASETYPE_ARCreditMemo;
	private static final String invoiceDocTypeBaseName = X_C_DocType.DOCBASETYPE_ARInvoice;

	private InvoiceCandBL invoiceCandBL;

	@Before
	public void init()
	{
		invoiceCandBL = new InvoiceCandBL();

		// registerModelInterceptors(); we don't want any MV action..we are only interested in testing the particular BL methods onder test.
	}

	@Test
	public void testReversal()
	{
		final Properties ctx = Env.getCtx();
		final String trxName = Services.get(ITrxManager.class).createTrxName("testReversal");
		final PlainContextAware contextProvider = PlainContextAware.newWithTrxName(ctx, trxName);

		final I_C_Invoice_Candidate ic = new C_Invoice_Candidate_Builder(this)
				.setSOTrx(true)
				.build();

		final BigDecimal invoiceQtyInvoiced = new BigDecimal("10");

		final ImmutablePair<I_C_Invoice, I_C_InvoiceLine> invoiceAndLine = creatInvoiceWithOneLine(contextProvider, invoiceQtyInvoiced, IDocument.STATUS_Reversed, X_C_DocType.DOCBASETYPE_ARInvoice);
		final ImmutablePair<I_C_Invoice, I_C_InvoiceLine> reversalAndLine = creatInvoiceWithOneLine(contextProvider, invoiceQtyInvoiced.negate(), IDocument.STATUS_Reversed, X_C_DocType.DOCBASETYPE_ARInvoice);

		final I_C_Invoice_Line_Alloc ila = InterfaceWrapperHelper.newInstance(I_C_Invoice_Line_Alloc.class, contextProvider);
		ila.setC_InvoiceLine(invoiceAndLine.getRight());
		ila.setC_Invoice_Candidate(ic);
		ila.setQtyInvoiced(invoiceQtyInvoiced);
		InterfaceWrapperHelper.save(ila);

		invoiceAndLine.getLeft().setReversal_ID(reversalAndLine.getLeft().getC_Invoice_ID());
		InterfaceWrapperHelper.save(invoiceAndLine.getLeft());

		invoiceCandBL.handleReversalForInvoice(invoiceAndLine.getLeft());

		final List<I_C_Invoice_Line_Alloc> ilaForIc = Services.get(IInvoiceCandDAO.class).retrieveIlaForIc(ic);
		assertThat(ilaForIc.isEmpty(), is(false));

		for (final I_C_Invoice_Line_Alloc currentIla : ilaForIc)
		{
			if (currentIla.equals(ila))
			{
				continue; // skip the invoice's ila..we only look at the credit memo's ila
			}
			assertThat(currentIla.getQtyInvoiced(), comparesEqualTo(invoiceQtyInvoiced.negate()));
		}
	}

	@Test
	public void testCompleteCreditmemoNotReinvoicable()
	{
		final BigDecimal creditMemoQtyInvoiced = new BigDecimal("9");

		final boolean isCreditedInvoiceReinvoicable = false;
		final BigDecimal expectedNewIlaQtyInvoiced = BigDecimal.ZERO;

		doTest(isCreditedInvoiceReinvoicable, creditMemoQtyInvoiced, expectedNewIlaQtyInvoiced);
	}

	/**
	 * This is not a hard requirement, but it illustrates how the system currently behaves and that it is currently supposed to be acceptable.
	 */
	@Test
	public void testCompleteCreditmemoReinvoicableCreditMemoExceedsInvoice()
	{
		final BigDecimal creditMemoQtyInvoiced = new BigDecimal("12");

		final boolean isCreditedInvoiceReinvoicable = true;
		final BigDecimal expectedNewIlaQtyInvoiced = new BigDecimal("-12");

		doTest(isCreditedInvoiceReinvoicable, creditMemoQtyInvoiced, expectedNewIlaQtyInvoiced);
	}

	@Test
	public void testCompleteCreditmemoReinvoicable()
	{
		final BigDecimal creditMemoQtyInvoiced = new BigDecimal("9");

		final boolean isCreditedInvoiceReinvoicable = true;
		final BigDecimal expectedNewIlaQtyInvoiced = new BigDecimal("-9"); // the credit memo has

		doTest(isCreditedInvoiceReinvoicable, creditMemoQtyInvoiced, expectedNewIlaQtyInvoiced);
	}

	/**
	 *
	 * <ul>
	 * <li>Invoice (10)
	 * <li>=> QtyInvoiced = 10
	 * <li>reinvoiceable credit memo (9)
	 * <li>=> QtyInvoiced = 1
	 * <li>then reversal of the credit memo (i.e. -9)
	 * <li>=> ExpectedQtyInvoiced = 10 once again
	 * </ul>
	 */
	@Test
	public void testCreditMemoReinvoicableReversed()
	{
		final BigDecimal reversalIlaExpectedQtyInvoiced = new BigDecimal("9"); // the original credit memo's needs to be subtracted (i.e. -9), so the reversal needs to have a positive qty.
		final boolean reverseInvoice = false;

		doTestCreditMemoReinvoicableAndReversal(reverseInvoice, reversalIlaExpectedQtyInvoiced);
	}

	/**
	 * <ul>
	 * <li>Invoice (10)
	 * <li>=> QtyInvoiced = 10
	 * <li>reinvoiceable credit memo (9)
	 * <li>=> QtyInvoiced = 1
	 * <li>then reversal of the invoice (i.e. -10)
	 * <li>=> Expected QtyInvoiced value = 0
	 * </ul>
	 */
	@Test
	public void testCreditMemoReinvoicableInvoiceReversed()
	{
		final BigDecimal reversalIlaExpectedQtyInvoiced = BigDecimal.ONE.negate();
		final boolean reverseInvoice = true;

		doTestCreditMemoReinvoicableAndReversal(reverseInvoice, reversalIlaExpectedQtyInvoiced);
	}

	/**
	 *
	 * @param reverseInvoice <code>true</code>: reverse the original invoice; <code>false</code> reverse the invoice's credit memo.
	 * @param reversalIlaExpectedQtyInvoiced
	 */
	private void doTestCreditMemoReinvoicableAndReversal(final boolean reverseInvoice, final BigDecimal reversalIlaExpectedQtyInvoiced)
	{
		//
		// preparation

		final Properties ctx = Env.getCtx();
		final String trxName = Services.get(ITrxManager.class).createTrxName("testCompleteCreditmemo");
		final PlainContextAware contextProvider =  PlainContextAware.newWithTrxName(ctx, trxName);

		final I_C_Invoice_Candidate ic = new C_Invoice_Candidate_Builder(this)
				.setSOTrx(true)
				.build();

		final ImmutablePair<I_C_Invoice, I_C_InvoiceLine> invoiceAndLine = creatInvoiceWithOneLine(contextProvider, INVOICE_QTY_INVOICED_TEN, IDocument.STATUS_Completed, invoiceDocTypeBaseName);

		final I_C_Invoice_Line_Alloc invoiceIla = InterfaceWrapperHelper.newInstance(I_C_Invoice_Line_Alloc.class, contextProvider);
		invoiceIla.setC_InvoiceLine(invoiceAndLine.getRight());
		invoiceIla.setC_Invoice_Candidate(ic);
		invoiceIla.setQtyInvoiced(INVOICE_QTY_INVOICED_TEN);
		InterfaceWrapperHelper.save(invoiceIla);

		final ImmutablePair<I_C_Invoice, I_C_InvoiceLine> creditMemoAndLine = creatInvoiceWithOneLine(contextProvider, CREDI_MEMO_QTY_INVOICE_NINE, IDocument.STATUS_Completed, creditmemoDocTypeBaseName);
		creditMemoAndLine.getLeft().setIsCreditedInvoiceReinvoicable(true);

		//creditMemoAndLine.getLeft().setRef_CreditMemo_ID(invoiceAndLine.getLeft().getC_Invoice_ID());

		creditMemoAndLine.getLeft().setRef_Invoice_ID(invoiceAndLine.getLeft().getC_Invoice_ID());
		InterfaceWrapperHelper.save(creditMemoAndLine.getLeft());

		creditMemoAndLine.getRight().setRef_InvoiceLine_ID(invoiceAndLine.getRight().getC_InvoiceLine_ID());
		InterfaceWrapperHelper.save(creditMemoAndLine.getRight());

		invoiceCandBL.handleCompleteForInvoice(creditMemoAndLine.getLeft());

		// guard: ad this point, we expect 1 out of the original 10 to be still invoicable
		I_C_Invoice_Line_Alloc creditMemoIla = null;
		final List<I_C_Invoice_Line_Alloc> ilasForIcAfterCreditMemo = Services.get(IInvoiceCandDAO.class).retrieveIlaForIc(ic);
		assertThat(ilasForIcAfterCreditMemo.isEmpty(), is(false));
		for (final I_C_Invoice_Line_Alloc currentIla : ilasForIcAfterCreditMemo)
		{
			if (currentIla.equals(invoiceIla))
			{
				continue; // skip the invoice's ila..we only look at the credit memo's ila
			}
			assertThat(currentIla.getQtyInvoiced(), comparesEqualTo(CREDI_MEMO_QTY_INVOICE_NINE.negate()));
			creditMemoIla = currentIla;
		}
		assertThat(invoiceCandBL.sumupQtyInvoicedAndNetAmtInvoiced(ic).getLeft(), comparesEqualTo(BigDecimal.ONE)); // 10 invoiced, 9 credited

		// create a reversal for the invoice or credit memo
		// the actual test starts with invoiceCandBL.handleReversalForInvoice()
		final BigDecimal expectedQtyInvoicedSumAfterReversal;
		if (reverseInvoice)
		{
			final ImmutablePair<I_C_Invoice, I_C_InvoiceLine> invoiceReversalAndLine = creatInvoiceWithOneLine(contextProvider, INVOICE_QTY_INVOICED_TEN.negate(), IDocument.STATUS_Reversed, invoiceDocTypeBaseName);
			invoiceAndLine.getLeft().setReversal_ID(invoiceReversalAndLine.getLeft().getC_Invoice_ID());
			InterfaceWrapperHelper.save(invoiceAndLine.getLeft());

			invoiceCandBL.handleReversalForInvoice(invoiceAndLine.getLeft()); // the actual test starts here
			expectedQtyInvoicedSumAfterReversal = BigDecimal.ZERO; // with the original invoice reversed, qtyInvoiced shall be 0
		}
		else
		{
			final ImmutablePair<I_C_Invoice, I_C_InvoiceLine> creditMemoReversalAndLine = creatInvoiceWithOneLine(contextProvider, CREDI_MEMO_QTY_INVOICE_NINE.negate(), IDocument.STATUS_Reversed,
					creditmemoDocTypeBaseName);
			creditMemoAndLine.getLeft().setReversal_ID(creditMemoReversalAndLine.getLeft().getC_Invoice_ID());
			InterfaceWrapperHelper.save(creditMemoAndLine.getLeft());

			invoiceCandBL.handleReversalForInvoice(creditMemoAndLine.getLeft()); // the actual test starts here
			expectedQtyInvoicedSumAfterReversal = INVOICE_QTY_INVOICED_TEN; // with the credit memo reversed, qtyInvoiced shall be beack to where it was prior to the credit memo.
		}

		//
		// checking the result
		final List<I_C_Invoice_Line_Alloc> ilasForIcAfterReversal = Services.get(IInvoiceCandDAO.class).retrieveIlaForIc(ic);
		assertThat(ilasForIcAfterCreditMemo.isEmpty(), is(false));
		for (final I_C_Invoice_Line_Alloc currentIla : ilasForIcAfterReversal)
		{
			if (currentIla.equals(invoiceIla))
			{
				assertThat(currentIla.getQtyInvoiced(), comparesEqualTo(INVOICE_QTY_INVOICED_TEN)); // should be unchanged since the beginning
				continue;
			}
			if (currentIla.equals(creditMemoIla))
			{
				assertThat(currentIla.getQtyInvoiced(), comparesEqualTo(CREDI_MEMO_QTY_INVOICE_NINE.negate())); // should be unchanged since we last checked
				continue;
			}

			assertThat(currentIla.getQtyInvoiced(), comparesEqualTo(reversalIlaExpectedQtyInvoiced));
		}

		assertThat(invoiceCandBL.sumupQtyInvoicedAndNetAmtInvoiced(ic).getLeft(), comparesEqualTo(expectedQtyInvoicedSumAfterReversal));
	}

	private void doTest(final boolean isCreditedInvoiceReinvoicable,
			final BigDecimal creditMemoQtyInvoiced,
			final BigDecimal expectedNewIlaQtyInvoiced)
	{
		final Properties ctx = Env.getCtx();
		final String trxName = Services.get(ITrxManager.class).createTrxName("testCompleteCreditmemo");
		final PlainContextAware contextProvider = PlainContextAware.newWithTrxName(ctx, trxName);

		final I_C_Invoice_Candidate ic = new C_Invoice_Candidate_Builder(this)
				.setSOTrx(true)
				.build();

		final ImmutablePair<I_C_Invoice, I_C_InvoiceLine> invoiceAndLine = creatInvoiceWithOneLine(contextProvider, INVOICE_QTY_INVOICED_TEN, IDocument.STATUS_Completed, invoiceDocTypeBaseName);

		final I_C_Invoice_Line_Alloc ila = InterfaceWrapperHelper.newInstance(I_C_Invoice_Line_Alloc.class, contextProvider);
		ila.setC_InvoiceLine(invoiceAndLine.getRight());
		ila.setC_Invoice_Candidate(ic);
		ila.setQtyInvoiced(new BigDecimal("-1")); // shall not be taken into account by the code under test
		InterfaceWrapperHelper.save(ila);

		final ImmutablePair<I_C_Invoice, I_C_InvoiceLine> creditMemoAndLine = creatInvoiceWithOneLine(contextProvider, creditMemoQtyInvoiced, IDocument.STATUS_Completed, creditmemoDocTypeBaseName);
		creditMemoAndLine.getLeft().setIsCreditedInvoiceReinvoicable(isCreditedInvoiceReinvoicable);
		creditMemoAndLine.getLeft().setRef_Invoice_ID(invoiceAndLine.getLeft().getC_Invoice_ID());
		InterfaceWrapperHelper.save(creditMemoAndLine.getLeft());

		creditMemoAndLine.getRight().setRef_InvoiceLine_ID(invoiceAndLine.getRight().getC_InvoiceLine_ID());
		InterfaceWrapperHelper.save(creditMemoAndLine.getRight());

		invoiceCandBL.handleCompleteForInvoice(creditMemoAndLine.getLeft());

		final List<I_C_Invoice_Line_Alloc> ilaForIc = Services.get(IInvoiceCandDAO.class).retrieveIlaForIc(ic);
		assertThat(ilaForIc.isEmpty(), is(false));

		for (final I_C_Invoice_Line_Alloc currentIla : ilaForIc)
		{
			if (currentIla.equals(ila))
			{
				continue; // skip the invoice's ila..we only look at the credit memo's ila
			}
			assertThat(currentIla.getQtyInvoiced(), comparesEqualTo(expectedNewIlaQtyInvoiced));
		}
	}

	private ImmutablePair<I_C_Invoice, I_C_InvoiceLine> creatInvoiceWithOneLine(
			final PlainContextAware contextProvider,
			final BigDecimal creditMemoQtyInvoiced,
			final String docStatus,
			final String docbasetypeArcreditmemo)
	{
		final I_C_DocType dt = InterfaceWrapperHelper.newInstance(I_C_DocType.class, contextProvider);
		dt.setDocBaseType(docbasetypeArcreditmemo);
		InterfaceWrapperHelper.save(dt);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, contextProvider);
		invoice.setC_DocType_ID(dt.getC_DocType_ID());
		invoice.setDocStatus(docStatus);
		InterfaceWrapperHelper.save(invoice);

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, contextProvider);
		invoiceLine.setC_Invoice(invoice);
		invoiceLine.setQtyInvoiced(creditMemoQtyInvoiced);
		invoiceLine.setLine(10);
		InterfaceWrapperHelper.save(invoiceLine);
		return ImmutablePair.of(invoice, invoiceLine);
	}
}

package test.integration.swat.sales.invoice;

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
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Iterator;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.invoice.service.IInvoiceBL;
import org.adempiere.invoice.service.IInvoiceCreditContext;
import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.invoice.service.impl.InvoiceCreditContext;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_M_Product;
import org.compiere.util.TrxRunnable;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;
import de.metas.adempiere.model.I_C_Invoice;
import de.metas.allocation.api.IAllocationDAO;
import de.metas.document.engine.IDocument;

@RunWith(IntegrationTestRunner.class)
public class InvoiceTests extends AIntegrationTestDriver
{

	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	@IntegrationTest(tasks = "04054", desc = "Creates an invoice, creates a partial payment and then a credit memo for the invoice")
	@Test
	public void testCreditPartiallyPaidInvoice_1_TaxIncl()
	{
		testCreditPartiallyPaidInvoice(true, null, 1);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("0.01"), 1);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.88"), 1);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.89"), 1);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.98"), 1);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.99"), 1);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.00"), 1);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.01"), 1);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.02"), 1);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.11"), 1);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.12"), 1);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("-0.01"), 1);
	}

	@IntegrationTest(tasks = "04054", desc = "Creates an invoice, creates a partial payment and then a credit memo for the invoice")
	@Test
	public void testCreditPartiallyPaidInvoice_1_TaxExcl()
	{
		testCreditPartiallyPaidInvoice(false, null, 1);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("0.01"), 1);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.88"), 1);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.89"), 1);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.98"), 1);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.99"), 1);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.00"), 1);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.01"), 1);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.02"), 1);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.11"), 1);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.12"), 1);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("-0.01"), 1);
	}

	@IntegrationTest(tasks = "04054", desc = "Creates an invoice, creates a partial payment and then a credit memo for the invoice")
	@Test
	public void testCreditPartiallyPaidInvoice_2_TaxIncl()
	{
		testCreditPartiallyPaidInvoice(true, null, 2);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("0.01"), 2);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.88"), 2);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.89"), 2);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.98"), 2);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.99"), 2);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.00"), 2);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.01"), 2);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.02"), 2);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.11"), 2);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.12"), 2);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("-0.01"), 2);
	}

	@IntegrationTest(tasks = "04054", desc = "Creates an invoice, creates a partial payment and then a credit memo for the invoice")
	@Test
	public void testCreditPartiallyPaidInvoice_2_TaxExcl()
	{
		testCreditPartiallyPaidInvoice(false, null, 2);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("0.01"), 2);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.88"), 2);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.89"), 2);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.98"), 2);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.99"), 2);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.00"), 2);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.01"), 2);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.02"), 2);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.11"), 2);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.12"), 2);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("-0.01"), 2);
	}

	@IntegrationTest(tasks = "04054", desc = "Creates an invoice, creates a partial payment and then a credit memo for the invoice")
	@Test
	public void testCreditPartiallyPaidInvoice_3_TaxIncl()
	{
		testCreditPartiallyPaidInvoice(true, null, 3);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("0.01"), 3);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.88"), 3);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.89"), 3);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.98"), 3);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("225.99"), 3);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.00"), 3);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.01"), 3);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.02"), 3);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.11"), 3);
		testCreditPartiallyPaidInvoice(true, new BigDecimal("226.12"), 3);

		testCreditPartiallyPaidInvoice(true, new BigDecimal("-0.01"), 3);
	}

	@IntegrationTest(tasks = "04054", desc = "Creates an invoice, creates a partial payment and then a credit memo for the invoice")
	@Test
	public void testCreditPartiallyPaidInvoice_3_TaxExcl()
	{
		testCreditPartiallyPaidInvoice(false, null, 3);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("0.01"), 3);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.88"), 3);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.89"), 2);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.98"), 3);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("225.99"), 3);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.00"), 3);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.01"), 3);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.02"), 3);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.11"), 3);
		testCreditPartiallyPaidInvoice(false, new BigDecimal("226.12"), 3);

		testCreditPartiallyPaidInvoice(false, new BigDecimal("-0.01"), 3);
	}

	/**
	 *
	 * @param taxIncluded
	 * @param payAmtParam
	 * @param invoiceNo
	 */
	private void testCreditPartiallyPaidInvoice(final boolean taxIncluded, final BigDecimal payAmtParam, final int invoiceNo)
	{
		final I_C_Invoice invoice = mkInvoice(taxIncluded, invoiceNo);

		final BigDecimal amountToCredit;

		if (payAmtParam == null || payAmtParam.signum() == 0)
		{
			amountToCredit = invoice.getGrandTotal();
		}
		else
		{
			final BigDecimal partialPayAmt;
			if (payAmtParam.signum() < 0)
			{
				partialPayAmt = invoice.getGrandTotal().subtract(payAmtParam.negate());
			}
			else
			{
				partialPayAmt = payAmtParam;
			}
			getHelper().mkPaymentHelper()
					.setC_Invoice(invoice)
					.setPayAmt(partialPayAmt)
					.setExpectInvoicePaid(false)
					.createPayment();

			amountToCredit = invoice.getGrandTotal().subtract(partialPayAmt);
		}
		InterfaceWrapperHelper.refresh(invoice);
		assertThat(invoice.isPaid(), is(false));
		assertThat(Services.get(IAllocationDAO.class).retrieveOpenAmt(invoice, false), comparesEqualTo(amountToCredit));

		final boolean isCreditedInvoiceReinvoicable = false;

		final IInvoiceCreditContext creditCtx = InvoiceCreditContext.builder()
				.C_DocType_ID(getHelper().getConfig().getC_DocType_CreditMemo_ID())
				.completeAndAllocate(true)
				.referenceOriginalOrder(true)
				.referenceInvoice(true)
				.creditedInvoiceReinvoicable(isCreditedInvoiceReinvoicable)
				.build();

		final Mutable<I_C_Invoice> creditMemoRef = new Mutable<I_C_Invoice>();
		Services.get(ITrxManager.class).run(new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				final String trxNameBkp = InterfaceWrapperHelper.getTrxName(invoice);
				InterfaceWrapperHelper.setTrxName(invoice, localTrxName);
				creditMemoRef.setValue(Services.get(IInvoiceBL.class).creditInvoice(invoice, creditCtx));
				InterfaceWrapperHelper.setTrxName(invoice, trxNameBkp);
			}
		});
		final I_C_Invoice creditMemo = creditMemoRef.getValue();

		InterfaceWrapperHelper.refresh(invoice);
		InterfaceWrapperHelper.refresh(creditMemo);

		assertThat(invoice.getC_Invoice_ID(), is(not(creditMemo.getC_Invoice_ID())));
		assertThat(creditMemo + " has wrong GrantTotal", creditMemo.getGrandTotal(), comparesEqualTo(amountToCredit));
		if (creditCtx.isReferenceInvoice())
		{
			assertThat(creditMemo.getRef_Invoice_ID(), is(invoice.getC_Invoice_ID()));

			final Iterator<I_C_Invoice> creditMemosForInvoice = Services.get(IInvoiceDAO.class).retrieveCreditMemosForInvoice(invoice);

			boolean found = false;
			while (creditMemosForInvoice.hasNext())
			{
				if (creditMemosForInvoice.next().getC_Invoice_ID() == creditMemo.getC_Invoice_ID())
				{
					found = true;
					break;
				}
			}
			Assert.assertTrue(found);
		}
		assertThat(invoice.isPaid(), is(true));
		assertThat(creditMemo.isPaid(), is(true));
	}

	/**
	 * Creates a test invoice. The invoice contains lines with two different taxes.
	 *
	 * @param taxIncluded specifies if the invoice is created with <code>IsTaxIncluded='Y'</code>
	 * @param invoiceNo decided which test invoice the method shall create:
	 *
	 *            <pre>
	 * No	 Line	TaxCategory	PriceActual
	 * 1	 10	 	8%	 		130
	 * 1	 20	 	8%	 		50
	 * 1	 30	 	2,5%		250
	 * 1	 40	 	2,5%		60
	 *
	 * 2	 10	 	8%	 		179.99
	 * 2	 20	 	8%	 		0.01
	 * 2	 30	 	2,5%		0.01
	 * 2	 40	 	2,5%		309.99
	 *
	 * 3	 10	 	8%	 		0.01
	 * 3	 20	 	2,5%		179.99
	 * 3	 30	 	2,5%		309.99
	 *            </pre>
	 *
	 * @return
	 */
	private I_C_Invoice mkInvoice(final boolean taxIncluded, final int invoiceNo)
	{
		final I_M_Product prod1 = getHelper().getM_Product("prod1_(*)");
		addProductPriceWithTaxCategory(prod1, getHelper().getConfig().getC_TaxCategory_Normal_ID());

		final I_M_Product prod2 = getHelper().getM_Product("prod2_(*)");
		addProductPriceWithTaxCategory(prod2, getHelper().getConfig().getC_TaxCategory_Normal_ID());

		final I_M_Product prod3 = getHelper().getM_Product("prod3_(*)");
		addProductPriceWithTaxCategory(prod3, getHelper().getConfig().getC_TaxCategory_Reduced_ID());

		final I_M_Product prod4 = getHelper().getM_Product("prod4_(*)");
		addProductPriceWithTaxCategory(prod4, getHelper().getConfig().getC_TaxCategory_Reduced_ID());

		final I_C_Invoice invoice;
		if (invoiceNo == 1)
		{
			invoice = getHelper()
					.mkInvoiceHelper()
					.setPricingSystemValue(IHelper.DEFAULT_PricingSystemValue) // make sure it matches the pricing setup from above
					.setCurrencyCode(getHelper().getCurrencyCode())
					.setCountryCode(getHelper().getCountryCode())
					.addLine(prod1.getValue(), new BigDecimal("130"), new BigDecimal("130"))
					.addLine(prod2.getValue(), new BigDecimal("50"), new BigDecimal("50"))
					.addLine(prod3.getValue(), new BigDecimal("250"), new BigDecimal("250"))
					.addLine(prod4.getValue(), new BigDecimal("60"), new BigDecimal("60"))
					.setIsTaxIncluded(taxIncluded)
					.setComplete(IDocument.STATUS_Completed)
					.createInvoice();
		}
		else if (invoiceNo == 2)
		{
			invoice = getHelper()
					.mkInvoiceHelper()
					.addLine(prod1.getValue(), new BigDecimal("179.99"), new BigDecimal("179.99"))
					.addLine(prod2.getValue(), new BigDecimal("0.01"), new BigDecimal("0.01"))
					.addLine(prod4.getValue(), new BigDecimal("0.01"), new BigDecimal("0.01"))
					.addLine(prod3.getValue(), new BigDecimal("309.99"), new BigDecimal("309.99"))
					.setIsTaxIncluded(taxIncluded)
					.setComplete(IDocument.STATUS_Completed)
					.createInvoice();
		}
		else
		{
			invoice = getHelper()
					.mkInvoiceHelper()
					.addLine(prod1.getValue(), new BigDecimal("0.01"), new BigDecimal("0.01"))
					.addLine(prod3.getValue(), new BigDecimal("179.99"), new BigDecimal("179.99"))
					.addLine(prod4.getValue(), new BigDecimal("309.99"), new BigDecimal("309.99"))
					.setIsTaxIncluded(taxIncluded)
					.setComplete(IDocument.STATUS_Completed)
					.createInvoice();
		}
		if (taxIncluded)
		{
			assertThat(invoice.getGrandTotal(), comparesEqualTo(invoice.getTotalLines()));
		}
		return invoice;
	}

	private void addProductPriceWithTaxCategory(final I_M_Product prod1, final int C_TaxCategory_ID)
	{
		getHelper().setProductPrice(
				IHelper.DEFAULT_PricingSystemValue,
				getHelper().getCurrencyCode(),
				getHelper().getCountryCode(),
				prod1.getValue(),
				new BigDecimal("-1"), // doesn't matter
				C_TaxCategory_ID,
				true); // IsSO = true
	}
}

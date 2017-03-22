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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.compiere.util.Trx;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.service.IOrderLineBL;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.IInvoiceCandAggregate;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.impl.InvoiceCandBLCreateInvoices.IInvoiceGeneratorRunnable;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class InvoiceCandBLCreateInvoicesTest extends AbstractICTestSupport
{
	// services
	private InvoiceCandBLCreateInvoices invoiceCandBLCreateInvoices;
	protected IOrderLineBL orderLineBL;

	/**
	 * Dummy Invoice Generator which:
	 * <ul>
	 * <li>makes sure that only NOT processed candidates reach this point
	 * <li>generates a dummy invoice
	 * </ul>
	 *
	 */
	public static class MockedDummyInvoiceGenerator implements IInvoiceGeneratorRunnable
	{
		private Properties ctx;
		private IInvoiceHeader header;

		private I_C_Invoice invoice = null;

		@Override
		public void init(Properties ctx, IInvoiceHeader header)
		{
			this.ctx = ctx;
			this.header = header;

			assumeInvoiceCandidatesAreNotProcessed();
		}

		@Override
		public void run(String localTrxName) throws Exception
		{
			assumeInvoiceCandidatesAreNotProcessed();

			// just create a dummy invoice
			invoice = InterfaceWrapperHelper.create(ctx, I_C_Invoice.class, localTrxName);
			InterfaceWrapperHelper.save(invoice);
		}

		private void assumeInvoiceCandidatesAreNotProcessed()
		{
			final InvoiceCandidateExpectation<Object> expectation = InvoiceCandidateExpectation.newExpectation()
					.processed(false);

			for (IInvoiceCandAggregate line : header.getLines())
			{
				for (I_C_Invoice_Candidate ic : line.getAllCands())
				{
					expectation.assertExpected(ic);
				}
			}
		}

		@Override
		public I_C_Invoice getC_Invoice()
		{
			return invoice;
		}

		@Override
		public List<I_AD_Note> getNotifications()
		{
			return Collections.emptyList();
		}
	}

	@Before
	public void init()
	{
		this.invoiceCandBLCreateInvoices = new InvoiceCandBLCreateInvoices();
		this.orderLineBL = Services.get(IOrderLineBL.class);

		//
		// Register C_Invoice_Candidate model interceptor
		registerModelInterceptors();
	}

	/**
	 * Test: if we process an invoice candidate which does not have an user in charge, then don't create the AD_Note but flag it IsError=Y
	 */
	@Test
	public void test_InvalidInvoiceCandidate_NoUserInCharge_FlagItAsError()
	{
		final I_C_BPartner bpartner = bpartner("test-bp");
		final I_C_Invoice_Candidate ic = createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, false, true);
		InterfaceWrapperHelper.save(ic);

		final Properties ctx = Env.getCtx();
		final String trxName = Trx.createTrxName();
		// final boolean ignoreInvoiceSchedule = true;
		// final IInvoiceGenerateResult existingResult = null;
		// invoiceCandBLCreateInvoices.generateInvoices(ctx, Collections.singletonList(ic).iterator(), ignoreInvoiceSchedule, existingResult, NullLoggable.instance, trxName);
		invoiceCandBLCreateInvoices
				.setContext(ctx, trxName)
				.setIgnoreInvoiceSchedule(true)
				.generateInvoices(Collections.singletonList(ic).iterator());

		Assert.assertEquals("Invalid " + ic + ": IsError", true, ic.isError());
		Assert.assertNotNull("Invalid " + ic + ": ErrorMsg", ic.getErrorMsg());
		Assert.assertNull("Invalid " + ic + ": AD_Note", ic.getAD_Note());
	}

	/**
	 * Test: processed invoice candidates shall be skipped when generating invoices
	 *
	 * User Story: there can be cases where invoice candidates had Processed=N when fetched, but in meantime, some of them were already processed and so we need to skip those
	 *
	 * @task http://dewiki908/mediawiki/index.php/04533_Erstellung_einer_Rechnung_%282013070810000082%29
	 */
	@Test
	public void test_submitAlreadyProcessedCandidate()
	{
		invoiceCandBLCreateInvoices.setInvoiceGeneratorClass(MockedDummyInvoiceGenerator.class);

		final Properties ctx = Env.getCtx();
		final String trxName = Trx.createTrxName();

		final I_C_BPartner bpartner = bpartner("test-bp");

		// creating with: bpartner, price, qty, isManual=false, isSOTrx=true
		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, false, true);
		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, false, true);
		final I_C_Invoice_Candidate ic3 = createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, false, true);
		final List<I_C_Invoice_Candidate> invoiceCandidates = Arrays.asList(ic1, ic2, ic3);

		//
		// Update/refresh invalid candidates
		updateInvalid(invoiceCandidates);

		final InvoiceCandidateExpectation<Object> expectation = newInvoiceCandidateExpectation()
				.error(false)
				.netAmtToInvoice(30);

		//
		// Check NetAmtToInvoice
		expectation.assertExpected("ic1", ic1);
		expectation.assertExpected("ic2", ic2);
		expectation.assertExpected("ic3", ic3);

		//
		// Simulate IC1 was already processed
		{
			ic1.setProcessed(true);
			InterfaceWrapperHelper.save(ic1);
		}

		//
		// Generate the dummy invoice
		final IInvoiceGenerateResult result = invoiceCandBL.createInvoiceGenerateResult(true); // shallStoreInvoices=true
		// final boolean ignoreInvoiceSchedule = true;
		// invoiceCandBLCreateInvoices.generateInvoices(ctx, invoiceCandidates.iterator(), ignoreInvoiceSchedule, result, NullLoggable.instance, trxName);
		invoiceCandBLCreateInvoices
				.setContext(ctx, trxName)
				.setCollector(result)
				.setIgnoreInvoiceSchedule(true)
				.generateInvoices(invoiceCandidates.iterator());

		Assert.assertEquals("Invalid invoice count: " + result, 1, result.getInvoiceCount());
		// NOTE: the rest of the assumptions are in MockedDummyInvoiceGenerator
	}

	/**
	 * Test: Invoice candidadates with discount
	 *
	 * @task http://dewiki908/mediawiki/index.php/04868_Fehler_beim_Abrechen_von_Rechnungskandidaten_%28102205076842%29
	 */
	@Test
	public void test_DiscountInvoiceCandidates()
	{
		invoiceCandBLCreateInvoices.setInvoiceGeneratorClass(MockedDummyInvoiceGenerator.class);

		final Properties ctx = Env.getCtx();
		final String trxName = Trx.createTrxName();
		;

		final I_C_BPartner bpartner = bpartner("test-bp");

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true);
		ic1.setDescription("IC1 - normal");
		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true);
		ic2.setDescription("IC2 - partial qty");
		ic2.setQtyToInvoice_Override(BigDecimal.ONE);

		final List<I_C_Invoice_Candidate> invoiceCandidates = Arrays.asList(ic1, ic2);

		//
		// Save all invoice candidates
		for (I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			InterfaceWrapperHelper.save(ic);
		}

		//
		// Update/refresh invalid candidates
		updateInvalid(invoiceCandidates);

		final BigDecimal discount1 = ic1.getDiscount();
		final BigDecimal discount_override1 = ic1.getDiscount_Override();
		//
		final BigDecimal discount2 = ic2.getDiscount();
		final BigDecimal discount_override2 = ic2.getDiscount_Override();

		final IInvoiceGenerateResult result = invoiceCandBL.createInvoiceGenerateResult(true); // shallStoreInvoices=true
		// final boolean ignoreInvoiceSchedule = true;
		// invoiceCandBLCreateInvoices.generateInvoices(ctx, invoiceCandidates.iterator(), ignoreInvoiceSchedule, result, NullLoggable.instance, trxName);
		invoiceCandBLCreateInvoices
				.setContext(ctx, trxName)
				.setCollector(result)
				.setIgnoreInvoiceSchedule(true)
				.generateInvoices(invoiceCandidates.iterator());

		final BigDecimal discount1After = ic1.getDiscount();
		final BigDecimal discount_override1After = ic1.getDiscount_Override();
		//
		final BigDecimal discount2After = ic2.getDiscount();
		final BigDecimal discount_override2After = ic2.getDiscount_Override();

		Check.assume(discount1.compareTo(discount1After) == 0, "Discount is not the same with discount after update", ic1.getDescription());
		Check.assume(discount_override1.compareTo(Env.ZERO) == 0, "Discount Override should be null!");
		Check.assume(discount_override1After.compareTo(Env.ZERO) == 0, "Discount Override should be null!");

		//
		Check.assume(discount2.compareTo(discount2After) == 0, "Discount is not the same with discount after update", ic2.getDescription());
		Check.assume(discount_override2.compareTo(Env.ZERO) == 0, "Discount Override should be null!");
		Check.assume(discount_override2After.compareTo(Env.ZERO) == 0, "Disocunt Override should be null!");

	}

	/**
	 * Test: priceEntered in Invoice candidadates
	 *
	 * @task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_PriceEnteredInvoiceCandidates()
	{

		invoiceCandBLCreateInvoices.setInvoiceGeneratorClass(MockedDummyInvoiceGenerator.class);

		final Properties ctx = Env.getCtx();
		final String trxName = Trx.createTrxName();

		final I_C_BPartner bpartner = bpartner("test-bp");

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // priceEntered, qty, discount
		ic1.setDescription("IC1 - normal");

		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // priceEntered, qty, discount
		ic2.setDescription("IC2 - partial qty");
		ic2.setQtyToInvoice_Override(BigDecimal.ONE);

		final BigDecimal discount1 = ic1.getDiscount();
		BigDecimal discount_override1 = ic1.getDiscount_Override();
		final int precision1 = invoiceCandBL.getPrecisionFromCurrency(ic1);

		//
		final BigDecimal discount2 = ic2.getDiscount();
		final BigDecimal discount_override2 = ic2.getDiscount_Override();
		final int precision2 = invoiceCandBL.getPrecisionFromCurrency(ic2);

		// initial check
		Check.assume(discount_override1.signum() == 0, "Discount Override should be null!", ic1.getDescription());
		Check.assume(discount_override2.signum() == 0, "Discount Override should be null!", ic2.getDescription());
		Check.assume(ic1.getPriceActual_Override().signum() == 0, "Price Actual Override should be null!", ic1.getDescription());
		Check.assume(ic2.getPriceActual_Override().signum() == 0, "Price Actual Override should be null!", ic2.getDescription());

		// change discount
		ic1.setDiscount_Override(BigDecimal.valueOf(20));
		final BigDecimal priceActual_OverrideComputed1 = orderLineBL.subtractDiscount(ic1.getPriceEntered(), ic1.getDiscount_Override(), precision1);
		discount_override1 = ic1.getDiscount_Override();

		// change priceEntered
		ic2.setPriceEntered_Override(BigDecimal.valueOf(5));
		InterfaceWrapperHelper.save(ic2);
		final BigDecimal priceActual_OverrideComputed2 = orderLineBL.subtractDiscount(ic2.getPriceEntered_Override(), ic2.getDiscount(), precision2);

		final List<I_C_Invoice_Candidate> invoiceCandidates = Arrays.asList(ic1, ic2);

		//
		// Make sure everything is saved until now:
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			InterfaceWrapperHelper.save(ic);
		}

		//
		// Update/refresh invalid candidates
		updateInvalid(invoiceCandidates);

		newInvoiceCandidateExpectation()
				.priceActualOverride(priceActual_OverrideComputed1)
				.assertExpected("Price Actual Override should be same with price actual computed!", ic1);
		newInvoiceCandidateExpectation()
				.priceActualOverride(priceActual_OverrideComputed2)
				.assertExpected("Price Actual Override should be same with price actual computed!", ic2);

		final IInvoiceGenerateResult result = invoiceCandBL.createInvoiceGenerateResult(true); // shallStoreInvoices=true
		// final boolean ignoreInvoiceSchedule = true;
		// invoiceCandBLCreateInvoices.generateInvoices(ctx, invoiceCandidates.iterator(), ignoreInvoiceSchedule, result, NullLoggable.instance, trxName);
		invoiceCandBLCreateInvoices
				.setContext(ctx, trxName)
				.setCollector(result)
				.setIgnoreInvoiceSchedule(true)
				.generateInvoices(invoiceCandidates.iterator());

		InterfaceWrapperHelper.refresh(ic1);
		final BigDecimal discount1After = ic1.getDiscount();
		final BigDecimal discount_override1After = ic1.getDiscount_Override();
		//
		InterfaceWrapperHelper.refresh(ic2);
		final BigDecimal discount2After = ic2.getDiscount();
		final BigDecimal discount_override2After = ic2.getDiscount_Override();

		Assert.assertThat("Discount is not the same with discount after update; ic: " + ic1.getDescription(),
				discount1After, Matchers.comparesEqualTo(discount1));
		Check.assume(discount_override1.compareTo(Env.ZERO) != 0, "Discount Override should not be null!");
		Check.assume(discount_override1After.compareTo(Env.ZERO) != 0, "Discount Override should not be null!");

		Check.assume(discount2.compareTo(discount2After) == 0, "Discount is not the same with discount after update; ic: " + ic2.getDescription());
		Check.assume(discount_override2.compareTo(Env.ZERO) == 0, "Discount Override should be null!");
		Check.assume(discount_override2After.compareTo(Env.ZERO) == 0, "Disocunt Override should be null!");

	}
}

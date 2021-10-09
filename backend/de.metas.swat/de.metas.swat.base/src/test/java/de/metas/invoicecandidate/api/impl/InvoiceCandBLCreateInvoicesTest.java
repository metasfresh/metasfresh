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

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import de.metas.business.BusinessTestHelper;
import de.metas.greeting.GreetingRepository;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Note;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.bpartner.service.impl.BPartnerStatisticsUpdater;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandBL.IInvoiceGenerateResult;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.impl.InvoiceCandBLCreateInvoices.IInvoiceGeneratorRunnable;
import de.metas.invoicecandidate.expectations.InvoiceCandidateExpectation;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.invoicecandidate.spi.impl.aggregator.standard.DefaultAggregator;
import de.metas.money.MoneyService;
import de.metas.order.IOrderLineBL;
import de.metas.user.UserRepository;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;

@ExtendWith(AdempiereTestWatcher.class)
public class InvoiceCandBLCreateInvoicesTest
{
	// services
	private InvoiceCandBLCreateInvoices invoiceCandBLCreateInvoices;
	protected IOrderLineBL orderLineBL;

	private AbstractICTestSupport icTestSupport;

	private IInvoiceCandBL invoiceCandBL;

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

			for (I_C_Invoice_Candidate ic : header.getAllInvoiceCandidates())
			{
				expectation.assertExpected(ic);
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

	@BeforeEach
	public void init()
	{
		icTestSupport = new AbstractICTestSupport();
		icTestSupport.initStuff();
		icTestSupport.registerModelInterceptors();

		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		
		invoiceCandBL = Services.get(IInvoiceCandBL.class);

		this.invoiceCandBLCreateInvoices = new InvoiceCandBLCreateInvoices();
		this.orderLineBL = Services.get(IOrderLineBL.class);

		final BPartnerStatisticsUpdater asyncBPartnerStatisticsUpdater = new BPartnerStatisticsUpdater();
		Services.registerService(IBPartnerStatisticsUpdater.class, asyncBPartnerStatisticsUpdater);
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new GreetingRepository());
	}

	/**
	 * Test: if we process an invoice candidate which does not have an user in charge, then don't create the AD_Note but flag it IsError=Y
	 *
	 * Note: the error is caused in {@link DefaultAggregator}, because the IC's LineAggregationKey is empty and there is no C_Invoice_Candidate_Recompute tag.
	 */
	@Test
	public void test_InvalidInvoiceCandidate_NoUserInCharge_FlagItAsError()
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");
		final I_C_Invoice_Candidate ic = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10/* priceEntered */, 3/* qty */, false/* isManual */, true/* isSOTrx */);
		InterfaceWrapperHelper.save(ic);

		// clear C_Invoice_Candidate_Recompute; otherwise we won't get our error out of DefaultAggregator.mkLineAggregationKeyToUse()
		final POJOLookupMap pojoLookupMap = POJOLookupMap.get();
		pojoLookupMap.getRecords(I_C_Invoice_Candidate_Recompute.class).forEach(pojoLookupMap::delete);

		invoiceCandBLCreateInvoices
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setIgnoreInvoiceSchedule(true)
				.generateInvoices(Collections.singletonList(ic).iterator());

		assertThat(ic.isError()).isTrue();
		assertThat(ic.getErrorMsg()).isNotNull();
		assertThat(ic.getAD_Note_ID()).isLessThanOrEqualTo(0);
	}

	/**
	 * Test: processed invoice candidates shall be skipped when generating invoices
	 *
	 * User Story: there can be cases where invoice candidates had Processed=N when fetched, but in meantime, some of them were already processed and so we need to skip those
	 *
	 * Task http://dewiki908/mediawiki/index.php/04533_Erstellung_einer_Rechnung_%282013070810000082%29
	 */
	@Test
	public void test_submitAlreadyProcessedCandidate()
	{
		invoiceCandBLCreateInvoices.setInvoiceGeneratorClass(MockedDummyInvoiceGenerator.class);
		
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

		final I_C_Invoice_Candidate ic1 = icTestSupport.createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(10)
				.setQtyOrdered(3)
				.setManual(false)
				.setSOTrx(true)
				.build();

		final I_C_Invoice_Candidate ic2 = icTestSupport.createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(10)
				.setQtyOrdered(3)
				.setManual(false)
				.setSOTrx(true)
				.build();

		final I_C_Invoice_Candidate ic3 = icTestSupport.createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(10)
				.setQtyOrdered(3)
				.setManual(false)
				.setSOTrx(true)
				.build();
		final List<I_C_Invoice_Candidate> invoiceCandidates = Arrays.asList(ic1, ic2, ic3);

		//
		// Update/refresh invalid candidates
		icTestSupport.updateInvalid(invoiceCandidates);

		final InvoiceCandidateExpectation<Object> expectation = icTestSupport.newInvoiceCandidateExpectation()
				.error(false)
				.netAmtToInvoice(300); // priceEntered=10 and uomQty=30

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

		final IInvoiceGenerateResult result = invoiceCandBL.createInvoiceGenerateResult(true); // shallStoreInvoices=true
		// final boolean ignoreInvoiceSchedule = true;
		// invoiceCandBLCreateInvoices.generateInvoices(ctx, invoiceCandidates.iterator(), ignoreInvoiceSchedule, result, NullLoggable.instance, trxName);
		invoiceCandBLCreateInvoices
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setCollector(result)
				.setIgnoreInvoiceSchedule(true)
				.generateInvoices(invoiceCandidates.iterator());

		assertThat(result.getInvoiceCount())
				.as("invoice count for " + result)
				.isEqualTo(1);
		// NOTE: the rest of the assumptions are in MockedDummyInvoiceGenerator
	}

	/**
	 * Test: Invoice candidates with discount
	 *
	 * Task http://dewiki908/mediawiki/index.php/04868_Fehler_beim_Abrechen_von_Rechnungskandidaten_%28102205076842%29
	 */
	@Test
	public void test_DiscountInvoiceCandidates()
	{
		invoiceCandBLCreateInvoices.setInvoiceGeneratorClass(MockedDummyInvoiceGenerator.class);

		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic1 = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true);
		ic1.setDescription("IC1 - normal");
		final I_C_Invoice_Candidate ic2 = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true);
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
		icTestSupport.updateInvalid(invoiceCandidates);

		final BigDecimal discount1 = ic1.getDiscount();
		final BigDecimal discount_override1 = ic1.getDiscount_Override();
		//
		final BigDecimal discount2 = ic2.getDiscount();
		final BigDecimal discount_override2 = ic2.getDiscount_Override();

		final IInvoiceGenerateResult result = invoiceCandBL.createInvoiceGenerateResult(true); // shallStoreInvoices=true
		// final boolean ignoreInvoiceSchedule = true;
		// invoiceCandBLCreateInvoices.generateInvoices(ctx, invoiceCandidates.iterator(), ignoreInvoiceSchedule, result, NullLoggable.instance, trxName);
		invoiceCandBLCreateInvoices
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
				.setCollector(result)
				.setIgnoreInvoiceSchedule(true)
				.generateInvoices(invoiceCandidates.iterator());

		final BigDecimal discount1After = ic1.getDiscount();
		final BigDecimal discount_override1After = ic1.getDiscount_Override();
		//
		final BigDecimal discount2After = ic2.getDiscount();
		final BigDecimal discount_override2After = ic2.getDiscount_Override();

		assertThat(discount1After)
				.as("Discount is not the same with discount after update; ic.getdescription()=" + ic1.getDescription())
				.isEqualByComparingTo(discount1);
		assertThat(discount_override1).isEqualByComparingTo("0");
		assertThat(discount_override1After).isEqualByComparingTo("0");

		//
		assertThat(discount2After)
				.as("Discount is not the same with discount after update; ic.getdescription()=" + ic2.getDescription())
				.isEqualByComparingTo(discount2);
		assertThat(discount_override2).isEqualByComparingTo("0");
		assertThat(discount_override2After).isEqualByComparingTo("0");
	}

	/**
	 * Test: priceEntered in Invoice candidadates
	 *
	 * Task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_PriceEnteredInvoiceCandidates()
	{
		invoiceCandBLCreateInvoices.setInvoiceGeneratorClass(MockedDummyInvoiceGenerator.class);

		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic1 = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // priceEntered, qty, discount
		POJOWrapper.setInstanceName(ic1, "ic1");
		ic1.setDescription("IC1 - normal");

		final I_C_Invoice_Candidate ic2 = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // priceEntered, qty, discount
		POJOWrapper.setInstanceName(ic2, "ic2");
		ic2.setDescription("IC2 - partial qty");
		ic2.setQtyToInvoice_Override(BigDecimal.ONE);

		final BigDecimal discount1 = ic1.getDiscount();
		BigDecimal discount_override1 = ic1.getDiscount_Override();
		final CurrencyPrecision precision1 = invoiceCandBL.getPrecisionFromCurrency(ic1);

		//
		final BigDecimal discount2 = ic2.getDiscount();
		final BigDecimal discount_override2 = ic2.getDiscount_Override();
		final CurrencyPrecision precision2 = invoiceCandBL.getPrecisionFromCurrency(ic2);

		// initial check
		Check.assume(discount_override1.signum() == 0, "Discount Override should be null!", ic1.getDescription());
		Check.assume(discount_override2.signum() == 0, "Discount Override should be null!", ic2.getDescription());
		Check.assume(ic1.getPriceActual_Override().signum() == 0, "Price Actual Override should be null!", ic1.getDescription());
		Check.assume(ic2.getPriceActual_Override().signum() == 0, "Price Actual Override should be null!", ic2.getDescription());

		// change discount
		ic1.setDiscount_Override(BigDecimal.valueOf(20));

		final BigDecimal priceActual_OverrideComputed1 = Percent.of(ic1.getDiscount_Override()).subtractFromBase(ic1.getPriceEntered(), precision1.toInt());
		discount_override1 = ic1.getDiscount_Override();

		// change priceEntered
		ic2.setPriceEntered_Override(BigDecimal.valueOf(5));
		InterfaceWrapperHelper.save(ic2);

		final BigDecimal priceActual_OverrideComputed2 = Percent.of(ic2.getDiscount()).subtractFromBase(ic2.getPriceEntered_Override(), precision2.toInt());
		final List<I_C_Invoice_Candidate> invoiceCandidates = Arrays.asList(ic1, ic2);

		//
		// Make sure everything is saved until now:
		for (final I_C_Invoice_Candidate ic : invoiceCandidates)
		{
			InterfaceWrapperHelper.save(ic);
		}

		//
		// Update/refresh invalid candidates
		icTestSupport.updateInvalid(invoiceCandidates);

		icTestSupport.newInvoiceCandidateExpectation()
				.priceActualOverride(priceActual_OverrideComputed1)
				.assertExpected("Price Actual Override should be same with price actual computed!", ic1);
		icTestSupport.newInvoiceCandidateExpectation()
				.priceActualOverride(priceActual_OverrideComputed2)
				.assertExpected("Price Actual Override should be same with price actual computed!", ic2);

		final IInvoiceGenerateResult result = invoiceCandBL.createInvoiceGenerateResult(true); // shallStoreInvoices=true
		// final boolean ignoreInvoiceSchedule = true;
		// invoiceCandBLCreateInvoices.generateInvoices(ctx, invoiceCandidates.iterator(), ignoreInvoiceSchedule, result, NullLoggable.instance, trxName);
		invoiceCandBLCreateInvoices
				.setContext(Env.getCtx(), ITrx.TRXNAME_ThreadInherited)
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

		assertThat(discount1After)
				.as("Discount is not the same with discount after update; ic.getdescription()=" + ic1.getDescription())
				.isEqualByComparingTo(discount1);
		assertThat(discount_override1).isNotEqualByComparingTo("0");
		assertThat(discount_override1After).isNotEqualByComparingTo("0");

		//
		assertThat(discount2After)
				.as("Discount is not the same with discount after update; ic.getdescription()=" + ic2.getDescription())
				.isEqualByComparingTo(discount2);
		assertThat(discount_override2).isEqualByComparingTo("0");
		assertThat(discount_override2After).isEqualByComparingTo("0");
	}
}

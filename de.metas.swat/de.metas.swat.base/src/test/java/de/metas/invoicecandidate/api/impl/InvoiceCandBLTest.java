/**
 *
 */
package de.metas.invoicecandidate.api.impl;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;


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
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_M_InOut;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.impl.BPartnerStatisticsUpdater;
import de.metas.currency.CurrencyPrecision;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.IDocument;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidate;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_InvoiceCandidate_InOutLine;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.lang.Percent;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS) // without this, this test fails when run in eclipse together with all tests of this project
public class InvoiceCandBLTest
{
	private InvoiceCandBL invoiceCandBL;
	private AbstractICTestSupport icTestSupport;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		invoiceCandBL = new InvoiceCandBL();
		icTestSupport = new AbstractICTestSupport();
		icTestSupport.initStuff();
		icTestSupport.registerModelInterceptors();

		POJOWrapper.setDefaultStrictValues(false);

		final BPartnerStatisticsUpdater asyncBPartnerStatisticsUpdater = new BPartnerStatisticsUpdater();
		Services.registerService(IBPartnerStatisticsUpdater.class, asyncBPartnerStatisticsUpdater);
	}

	/**
	 * Test: priceEntered in Invoice candidates
	 *
	 * @task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_PriceEnteredInvoiceCandidates()
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic1 = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // priceEntered, qty, discount
		ic1.setDescription("IC1 - normal");
		save(ic1);

		final I_C_Invoice_Candidate ic2 = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // priceEntered, qty, discount
		ic2.setDescription("IC2 - partial qty");
		ic2.setQtyToInvoice_Override(BigDecimal.ONE);
		save(ic2);

		final BigDecimal discount1 = ic1.getDiscount();
		BigDecimal discount_override1 = ic1.getDiscount_Override();
		final CurrencyPrecision precision1 = invoiceCandBL.getPrecisionFromCurrency(ic1);

		//
		final BigDecimal discount2 = ic2.getDiscount();
		final BigDecimal discount_override2 = ic2.getDiscount_Override();
		final CurrencyPrecision precision2 = invoiceCandBL.getPrecisionFromCurrency(ic2);

		// initial check
		Check.assume(discount_override1.compareTo(BigDecimal.ZERO) == 0, "Discount Override should be null!", ic1.getDescription());
		Check.assume(discount_override2.compareTo(BigDecimal.ZERO) == 0, "Discount Override should be null!", ic2.getDescription());
		Check.assume(ic1.getPriceActual_Override().compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic1.getDescription());
		Check.assume(ic2.getPriceActual_Override().compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic2.getDescription());

		// change discount
		ic1.setDiscount_Override(BigDecimal.valueOf(20));
		final BigDecimal priceActual_OverrideComputed1 = subtractDiscount(ic1.getPriceEntered(), ic1.getDiscount_Override(), precision1);
		discount_override1 = ic1.getDiscount_Override();
		save(ic1);

		// change priceEntered
		ic2.setPriceEntered_Override(BigDecimal.valueOf(5));
		final BigDecimal priceActual_OverrideComputed2 = subtractDiscount(ic2.getPriceEntered_Override(), ic2.getDiscount(), precision2);
		save(ic2);

		final List<I_C_Invoice_Candidate> invoiceCandidates = Arrays.asList(ic1, ic2);
		icTestSupport.updateInvalid(invoiceCandidates);

		assertThat("Price Actual Override should be same with price actual computed for " + ic1.getDescription(), ic1.getPriceActual_Override(), comparesEqualTo(priceActual_OverrideComputed1));
		assertThat("Price Actual Override should be same with price actual computed for " + ic2.getDescription(), ic2.getPriceActual_Override(), comparesEqualTo(priceActual_OverrideComputed2));

		final BigDecimal discount1After = ic1.getDiscount();
		final BigDecimal discount_override1After = ic1.getDiscount_Override();
		//
		final BigDecimal discount2After = ic2.getDiscount();
		final BigDecimal discount_override2After = ic2.getDiscount_Override();

		Check.assume(discount1.compareTo(discount1After) == 0, "Discount is not the same with discount after update", ic1.getDescription());
		Check.assume(discount_override1.compareTo(BigDecimal.ZERO) != 0, "Discount Override should not be null!");
		Check.assume(discount_override1After.compareTo(BigDecimal.ZERO) != 0, "Discount Override should not be null!");

		//

		Check.assume(discount2.compareTo(discount2After) == 0, "Discount is not the same with discount after update", ic2.getDescription());
		Check.assume(discount_override2.compareTo(BigDecimal.ZERO) == 0, "Discount Override should be null!");
		Check.assume(discount_override2After.compareTo(BigDecimal.ZERO) == 0, "Disocunt Override should be null!");

	}

	/**
	 * Test: priceEntered_Override in Invoice candidates
	 *
	 * @task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_PriceEnteredOverrideInvoiceCandidates()
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // partner, priceEntered, qty, discount, isManual
		ic.setDescription("IC - normal");

		final BigDecimal initialPriceActual = ic.getPriceActual();
		final BigDecimal initialPriceActualOverride = ic.getPriceActual_Override();
		final Percent discount = invoiceCandBL.getDiscount(ic);
		final CurrencyPrecision precision = invoiceCandBL.getPrecisionFromCurrency(ic);

		final BigDecimal priceActualComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), discount.toBigDecimal(), precision);

		Check.assume(priceActualComputed.compareTo(initialPriceActual) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);

		Check.assume(initialPriceActualOverride.compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());

		//
		ic.setPriceEntered_Override(BigDecimal.valueOf(20));
		save(ic);

		final BigDecimal priceActual_OverrideComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), discount.toBigDecimal(), precision);
		Check.assume(priceActual_OverrideComputed.compareTo(ic.getPriceActual_Override()) == 0, "Price Actual Override should equal with the one computed!", ic.getDescription(),
				ic.getPriceActual_Override(), priceActual_OverrideComputed);
	}

	/**
	 * Test: Discount_Override in Invoice candidates
	 *
	 * @task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_DiscountOverrideInvoiceCandidates()
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // partner, priceEntered, qty, discount, isManual
		ic.setDescription("IC - normal");

		final BigDecimal initialPriceActual = ic.getPriceActual();
		final BigDecimal initialPriceActualOverride = ic.getPriceActual_Override();
		final Percent intialDiscount = invoiceCandBL.getDiscount(ic);
		final CurrencyPrecision precision = invoiceCandBL.getPrecisionFromCurrency(ic);

		final BigDecimal priceActualComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), intialDiscount.toBigDecimal(), precision);

		Check.assume(priceActualComputed.compareTo(initialPriceActual) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);

		Check.assume(initialPriceActualOverride.compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());

		//
		ic.setDiscount_Override(BigDecimal.valueOf(20));
		save(ic);

		final Percent discount = invoiceCandBL.getDiscount(ic);
		final BigDecimal priceActual_OverrideComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), discount.toBigDecimal(), precision);
		Check.assume(priceActual_OverrideComputed.compareTo(ic.getPriceActual_Override()) == 0, "Price Actual Override should equal with the one computed!", ic.getDescription(),
				ic.getPriceActual_Override(), priceActual_OverrideComputed);
	}

	/**
	 * Test: Discount_Override and PriceEntered_Override in Invoice candidates
	 *
	 * @task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_PriceEnteredOverride_DiscountOverrideInvoiceCandidates()
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // partner, priceEntered, qty, discount, isManual
		ic.setDescription("IC - normal");

		final BigDecimal initialPriceActual = ic.getPriceActual();
		final BigDecimal initialPriceActualOverride = ic.getPriceActual_Override();
		final Percent intialDiscount = invoiceCandBL.getDiscount(ic);
		final CurrencyPrecision precision = invoiceCandBL.getPrecisionFromCurrency(ic);

		final BigDecimal priceActualComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), intialDiscount.toBigDecimal(), precision);

		Check.assume(priceActualComputed.compareTo(initialPriceActual) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);

		Check.assume(initialPriceActualOverride.compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());

		//
		ic.setDiscount_Override(BigDecimal.valueOf(20));
		ic.setPriceEntered_Override(BigDecimal.valueOf(20));
		save(ic);

		final Percent discount = invoiceCandBL.getDiscount(ic);
		final BigDecimal priceActual_OverrideComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), discount.toBigDecimal(), precision);
		Check.assume(priceActual_OverrideComputed.compareTo(ic.getPriceActual_Override()) == 0, "Price Actual Override should equal with the one computed!", ic.getDescription(),
				ic.getPriceActual_Override(), priceActual_OverrideComputed);
	}

	/**
	 * Test: remove priceEntered_Override in Invoice candidates
	 *
	 * @task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_RemovePriceEnteredOverrideInvoiceCandidates()
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // partner, priceEntered, qty, discount, isManual
		ic.setDescription("IC - normal");

		final BigDecimal initialPriceActual = ic.getPriceActual();
		final BigDecimal initialPriceActualOverride = ic.getPriceActual_Override();
		final Percent discount = invoiceCandBL.getDiscount(ic);
		final CurrencyPrecision precision = invoiceCandBL.getPrecisionFromCurrency(ic);

		final BigDecimal priceActualComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), discount.toBigDecimal(), precision);

		Check.assume(priceActualComputed.compareTo(initialPriceActual) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);

		Check.assume(initialPriceActualOverride.compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());

		//
		ic.setPriceEntered_Override(BigDecimal.valueOf(20));
		save(ic);

		final BigDecimal priceActual_OverrideComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), discount.toBigDecimal(), precision);
		Check.assume(priceActual_OverrideComputed.compareTo(ic.getPriceActual_Override()) == 0, "Price Actual Override should equal with the one computed!", ic.getDescription(),
				ic.getPriceActual_Override(), priceActual_OverrideComputed);

		//
		// remove price entered override
		ic.setPriceEntered_Override(null);
		save(ic);

		Check.assume(priceActualComputed.compareTo(ic.getPriceActual()) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);
		Check.assume(ic.getPriceActual_Override().compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());
	}

	/**
	 * Test: remove Discount_Override in Invoice candidates
	 *
	 * @task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_RemoveDiscountOverrideInvoiceCandidates()
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // partner, priceEntered, qty, discount, isManual
		ic.setDescription("IC - normal");

		final BigDecimal initialPriceActual = ic.getPriceActual();
		final BigDecimal initialPriceActualOverride = ic.getPriceActual_Override();
		final Percent intialDiscount = invoiceCandBL.getDiscount(ic);
		final CurrencyPrecision precision = invoiceCandBL.getPrecisionFromCurrency(ic);

		final BigDecimal priceActualComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), intialDiscount.toBigDecimal(), precision);

		Check.assume(priceActualComputed.compareTo(initialPriceActual) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);

		Check.assume(initialPriceActualOverride.compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());

		//
		ic.setDiscount_Override(BigDecimal.valueOf(20));
		save(ic);

		// remove discount override
		ic.setDiscount_Override(null);
		save(ic);

		Check.assume(priceActualComputed.compareTo(ic.getPriceActual()) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);
		Check.assume(ic.getPriceActual_Override().compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());
	}

	/**
	 * Test: Remove Discount_Override and PriceEntered_Override in Invoice candidates
	 *
	 * @task http://dewiki908/mediawiki/index.php/04917_Add_PriceEntered_in_Invoice_candiates_%28104928745590%29
	 */
	@Test
	public void test_RemovePriceEnteredOverride_DiscountOverrideInvoiceCandidates()
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");

		final I_C_Invoice_Candidate ic = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID(), 10, 3, 10, false, true); // partner, priceEntered, qty, discount, isManual, isSOTrx
		ic.setDescription("IC - normal");

		final BigDecimal initialPriceActual = ic.getPriceActual();
		final BigDecimal initialPriceActualOverride = ic.getPriceActual_Override();
		final Percent intialDiscount = invoiceCandBL.getDiscount(ic);
		final CurrencyPrecision precision = invoiceCandBL.getPrecisionFromCurrency(ic);

		final BigDecimal priceActualComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), intialDiscount.toBigDecimal(), precision);

		Check.assume(priceActualComputed.compareTo(initialPriceActual) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);

		Check.assume(initialPriceActualOverride.compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());

		//
		ic.setDiscount_Override(BigDecimal.valueOf(20));
		ic.setPriceEntered_Override(BigDecimal.valueOf(20));
		save(ic);

		final Percent discount = invoiceCandBL.getDiscount(ic);
		final BigDecimal priceActual_OverrideComputed = subtractDiscount(invoiceCandBL.getPriceEnteredEffective(ic).toBigDecimal(), discount.toBigDecimal(), precision);
		Check.assume(priceActual_OverrideComputed.compareTo(ic.getPriceActual_Override()) == 0, "Price Actual Override should equal with the one computed!", ic.getDescription(),
				ic.getPriceActual_Override(), priceActual_OverrideComputed);

		//
		ic.setDiscount_Override(null);
		ic.setPriceEntered_Override(null);
		save(ic);

		Check.assume(priceActualComputed.compareTo(ic.getPriceActual()) == 0, "Price Actual should equal with the one computed!", ic.getDescription(), initialPriceActual, initialPriceActual);
		Check.assume(ic.getPriceActual_Override().compareTo(BigDecimal.ZERO) == 0, "Price Actual Override should be null!", ic.getDescription());
	}

	@Test
	public void test_updateQtyToInvoiceFromQualityDiscountPercent_WithOutPercent_Override()
	{
		final BigDecimal qtyToDelivered = new BigDecimal("10");
		final BigDecimal qtyWithIssues = new BigDecimal("1");
		final BigDecimal qualityDiscountPercent = new BigDecimal("30"); // shall be ignored, because it's not used in this method at all..QtyWithIssues is used instea
		final BigDecimal qualityDiscountPercent_Override = null;
		final BigDecimal expectedQtyDeliveredEffective = new BigDecimal("9"); // 10 - 1

		assertMethodUpdateQtyToInvoiceFromQualityDiscountPercentIsCorrect(qtyToDelivered, qtyWithIssues, qualityDiscountPercent, qualityDiscountPercent_Override, expectedQtyDeliveredEffective);
	}

	@Test
	public void test_updateQtyToInvoiceFromQualityDiscountPercent_WithPercent_Override()
	{
		final BigDecimal qtyDelivered = new BigDecimal("10");
		final BigDecimal qtyWithIssues = new BigDecimal("1"); // shall be ignored, because we set QualityDiscountPercent_Override
		final BigDecimal qualityDiscountPercent = new BigDecimal("30"); // shall be ignored, because it's not used in this method at all..QtyWithIssues is used instead
		final BigDecimal qualityDiscountPercent_Override = new BigDecimal("20");
		final BigDecimal expectedQtyDelivered_Effective = new BigDecimal("8"); // 10 - 20%

		assertMethodUpdateQtyToInvoiceFromQualityDiscountPercentIsCorrect(qtyDelivered, qtyWithIssues, qualityDiscountPercent, qualityDiscountPercent_Override, expectedQtyDelivered_Effective);
	}

	private void assertMethodUpdateQtyToInvoiceFromQualityDiscountPercentIsCorrect(
			final BigDecimal qtyDelivered,
			final BigDecimal qtyWithIssues,
			final BigDecimal qualityDiscountPercent,
			final BigDecimal qualityDiscountPercent_Override,
			final BigDecimal expectedQtyDelivered_Effective)
	{
		final I_C_BPartner bpartner = icTestSupport.bpartner("test-bp");
		final I_C_Invoice_Candidate icRecord = icTestSupport.createInvoiceCandidate(bpartner.getC_BPartner_ID()/* partner */, 10/* priceEntered */, 3/* qty */, 10/* discount */, false/* isManual */, false/* isSOTrx */);

		// the qtys we set here don't really matter; the invoice candidate will be updated
		icRecord.setM_Product_ID(icTestSupport.getProductId().getRepoId());
		icRecord.setQtyDelivered(qtyDelivered);
		icRecord.setQtyWithIssues(qtyWithIssues);
		icRecord.setQualityDiscountPercent(qualityDiscountPercent); // shall be ignored, because it's not used in this method at all..QtyWithIssues is used instead
		save(icRecord);

		final Properties ctx = InterfaceWrapperHelper.getCtx(icRecord);
		final String trxName = InterfaceWrapperHelper.getTrxName(icRecord);

		// gh #1566: we need an active and completed inout; otherwise, the iol won't be counted properly
		final I_M_InOut inOut = newInstance(I_M_InOut.class);
		inOut.setIsActive(true);
		inOut.setDocStatus(IDocument.STATUS_Completed);
		save(inOut);

		//
		final de.metas.inout.model.I_M_InOutLine iol = InterfaceWrapperHelper.create(ctx, de.metas.inout.model.I_M_InOutLine.class, trxName);
		iol.setM_InOut(inOut);
		iol.setM_Product_ID(icTestSupport.getProductId().getRepoId());
		iol.setMovementQty(qtyDelivered.subtract(qtyWithIssues));
		iol.setC_UOM_ID(icTestSupport.getUomId().getRepoId());
		iol.setQtyEntered(qtyDelivered.subtract(qtyWithIssues).multiply(TEN));
		iol.setIsInDispute(false);
		save(iol);

		final I_C_InvoiceCandidate_InOutLine icIol = InterfaceWrapperHelper.create(ctx, I_C_InvoiceCandidate_InOutLine.class, trxName);
		icIol.setC_Invoice_Candidate(icRecord);
		invoiceCandBL.updateICIOLAssociationFromIOL(icIol, iol);
		save(icIol);

		final de.metas.inout.model.I_M_InOutLine iol2 = InterfaceWrapperHelper.create(ctx, de.metas.inout.model.I_M_InOutLine.class, trxName);
		iol2.setM_InOut(inOut);
		iol2.setM_Product_ID(icTestSupport.getProductId().getRepoId());
		iol2.setMovementQty(qtyWithIssues);
		iol2.setC_UOM_ID(icTestSupport.getUomId().getRepoId());
		iol2.setQtyEntered(qtyWithIssues.multiply(TEN));
		iol2.setIsInDispute(true);
		save(iol2);

		final I_C_InvoiceCandidate_InOutLine icIol2 = InterfaceWrapperHelper.create(ctx, I_C_InvoiceCandidate_InOutLine.class, trxName);
		icIol2.setC_Invoice_Candidate(icRecord);
		invoiceCandBL.updateICIOLAssociationFromIOL(icIol2, iol2);
		save(icIol);

		// only *now* we set this override, because those icIols would have reset it anyways
		icRecord.setQualityDiscountPercent_Override(qualityDiscountPercent_Override);
		save(inOut);

		// invoke the stuff under test
		final InvoiceCandidateRecordService invoiceCandidateRecordService = new InvoiceCandidateRecordService();
		final InvoiceCandidate invoiceCandidate = invoiceCandidateRecordService.ofRecord(icRecord);
		invoiceCandidateRecordService.updateRecord(invoiceCandidate, icRecord);

		assertThat(invoiceCandBL.getQtyDelivered_Effective(icRecord), comparesEqualTo(expectedQtyDelivered_Effective));
	}

	private static BigDecimal subtractDiscount(BigDecimal baseAmount, BigDecimal discount, CurrencyPrecision precision)
	{
		return Percent.of(discount).subtractFromBase(baseAmount, precision.toInt());
	}
}

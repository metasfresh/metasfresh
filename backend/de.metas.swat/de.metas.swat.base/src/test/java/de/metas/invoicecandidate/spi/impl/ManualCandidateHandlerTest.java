package de.metas.invoicecandidate.spi.impl;

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

import ch.qos.logback.classic.Level;
import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.impl.BPartnerStatisticsUpdater;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseType;
import de.metas.document.engine.DocStatus;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.api.InvoiceCandRecomputeTag;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.logging.LogManager;
import de.metas.money.MoneyService;
import de.metas.process.PInstanceId;
import de.metas.util.Services;
import de.metas.util.collections.IteratorUtils;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_Tax;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.model.X_C_Order;
import org.compiere.util.Env;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Properties;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.junit.Assert.assertThat;

@Ignore
@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class })
public class ManualCandidateHandlerTest extends AbstractICTestSupport
{
	private I_C_ILCandHandler manualHandler;
	private IInvoiceCandBL invoiceCandBL;
	private IInvoiceCandDAO invoiceCandDAO;

	@Rule
	public final TestWatcher testWatcher = new AdempiereTestWatcher();

	@Before
	public void init()
	{
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_CH");

		this.invoiceCandBL = Services.get(IInvoiceCandBL.class);
		this.invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		this.manualHandler = InterfaceWrapperHelper.create(ctx, I_C_ILCandHandler.class, ITrx.TRXNAME_None);
		this.manualHandler.setTableName(ManualCandidateHandler.MANUAL);
		this.manualHandler.setClassname(ManualCandidateHandler.class.getName());
		InterfaceWrapperHelper.save(manualHandler);

		//
		// Register model interceptors
		registerModelInterceptors();

		final BPartnerStatisticsUpdater asyncBPartnerStatisticsUpdater = new BPartnerStatisticsUpdater();
		Services.registerService(IBPartnerStatisticsUpdater.class, asyncBPartnerStatisticsUpdater);

		LogManager.setLevel(Level.DEBUG);
	}

	/**
	 * Creating one regular IC with priceActual 160 two manual ICs with priceActual -50 each, one with 50 and -1 as quantity. Expected result: all 3 manual ICs are fully invoicable and there are no
	 * splitAmounts.
	 */
	@Test
	public void test_noSplitAmtNegativeQty()
	{
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(160)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();

		ic1.setQtyToInvoice_Override(BigDecimal.ONE);
		POJOWrapper.setInstanceName(ic1, "ic1");
		InterfaceWrapperHelper.save(ic1);

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc1, "manualIc1");
		manualIc1.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc1);

		final I_C_Invoice_Candidate manualIc2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc2, "manualIc2");
		manualIc2.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc2);

		final I_C_Invoice_Candidate manualIc3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(-1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc3, "manualIc3");
		manualIc3.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc3);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(manualIc1);
		InterfaceWrapperHelper.refresh(manualIc2);
		InterfaceWrapperHelper.refresh(manualIc3);

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));
	}

	/**
	 * Creating one regular IC with priceActual 160 three manual ICs with priceActual -50 each. Expected result: all 3 manual ICs are fully invoicable and there are no splitAmounts.
	 */
	@Test
	public void test_noSplitAmt()
	{
		updateInvalidCandidates();

		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc1, "manualIc1");
		manualIc1.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc1);

		final I_C_Invoice_Candidate manualIc2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(manualIc2, "manualIc2");
		manualIc2.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc2);

		final I_C_Invoice_Candidate manualIc3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc3, "manualIc3");
		manualIc3.setIsManual(true);
		manualIc3.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc3);

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(160)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();

		ic1.setQtyToInvoice_Override(BigDecimal.ONE);
		POJOWrapper.setInstanceName(ic1, "ic1");
		InterfaceWrapperHelper.save(ic1);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(manualIc1);
		InterfaceWrapperHelper.refresh(manualIc2);
		InterfaceWrapperHelper.refresh(manualIc3);
		InterfaceWrapperHelper.refresh(ic1);

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(ic1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("160")));

	}

	/**
	 * Creating one regular IC with priceActual 70 and three manual ICs with priceActual -50 each. Expected result:
	 * <ul>
	 * <li>the 1st manual ICs is fully invoicable (NetAmtToInvoice -50, SplitAmount 0)</li>
	 * <li>the 2nd manual ICs is partially invoicable (NetAmtToInvoice -20, SplitAmount -30)</li>
	 * <li>the 3rd manual ICs is not invoicable (NetAmtToInvoice 0, SplitAmount -50)</li>
	 * </ul>
	 */
	@Test
	public void test_splitAmt()
	{
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(70)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(ic1, "ic1");

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc1, "manualIc1");
		manualIc1.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc1);

		final I_C_Invoice_Candidate manualIc2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc2, "manualIc2");
		manualIc2.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc2);

		final I_C_Invoice_Candidate manualIc3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc3, "manualIc3");
		manualIc3.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc3);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(manualIc1);
		InterfaceWrapperHelper.refresh(manualIc2);
		InterfaceWrapperHelper.refresh(manualIc3);

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-20")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(new BigDecimal("-30")));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(new BigDecimal("-50")));
	}

	/**
	 * Similar to {@link #test_splitAmt()}, but the regular IC is not created after the 2nd manual IC.
	 */
	@Test
	public void test_splitAmtDifferentOrder()
	{

		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc1, "manualIc1");
		manualIc1.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc1);

		final I_C_Invoice_Candidate manualIc2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc2, "manualIc2");
		manualIc2.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc2);

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(70)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(ic1, "ic1");

		final I_C_Invoice_Candidate manualIc3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc3, "manualIc3");
		manualIc3.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc3);

		updateInvalidCandidates();

		InterfaceWrapperHelper.refresh(manualIc1);
		InterfaceWrapperHelper.refresh(manualIc2);
		InterfaceWrapperHelper.refresh(manualIc3);

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-20")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(new BigDecimal("-30")));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(new BigDecimal("-50")));
	}

	/**
	 * Creates an invoice candidate {@code ic1} with Price 160 and qtyDelivered = 0. Depending on the candidates' {@code InvoiceRule}, we expect the manual invoice candidates' {@code NetAmtToInvoice}
	 * values to be
	 * <ul>
	 * <li>equal to their priceActual (if {@code ic1} is invoicable according to the invoice rule)</li>
	 * <li>zero, if {@code ic1} is not invoicable</li>
	 * </ul>
	 */
	@Test
	public void test_invoiceRules()
	{

		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_CH");

		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(160)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();

		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_Immediate);
		ic1.setQtyDelivered(BigDecimal.ZERO);
		POJOWrapper.setInstanceName(ic1, "ic1");
		InterfaceWrapperHelper.save(ic1);

		invoiceCandDAO.invalidateCandsForBPartnerInvoiceRule(billBPartnerAndLocationId.getBpartnerId());

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc1, "manualIc1");

		manualIc1.setC_ILCandHandler(manualHandler);
		invoiceCandBL.set_QtyInvoiced_NetAmtInvoiced_Aggregation(manualIc1);
		InterfaceWrapperHelper.save(manualIc1);

		final I_C_Invoice_Candidate manualIc2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc2, "manualIc2");
		manualIc2.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc2);

		final I_C_Invoice_Candidate manualIc3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(-1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc3, "manualIc3");
		manualIc3.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc3);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(manualIc1);
		InterfaceWrapperHelper.refresh(manualIc2);
		InterfaceWrapperHelper.refresh(manualIc3);

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_AfterOrderDelivered);
		InterfaceWrapperHelper.save(ic1);

		invoiceCandDAO.invalidateAllCands(ctx, ITrx.TRXNAME_None);

		updateInvalidCandidates();
		InterfaceWrapperHelper.refresh(manualIc1);
		InterfaceWrapperHelper.refresh(manualIc2);
		InterfaceWrapperHelper.refresh(manualIc3);

		assertThat(ic1.getQtyToInvoice(), comparesEqualTo(BigDecimal.ZERO)); // guard

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(new BigDecimal("-50")));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(new BigDecimal("-50")));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery);
		ic1.setIsToRecompute(true);
		invoiceCandDAO.invalidateAllCands(ctx, ITrx.TRXNAME_None);

		final InvoiceCandRecomputeTag recomputeTag = invoiceCandDAO.generateNewRecomputeTag();
		final Iterator<I_C_Invoice_Candidate> candIterator = invoiceCandDAO.fetchInvalidInvoiceCandidates(ctx, recomputeTag, ITrx.TRXNAME_None);
		updateInvalid(IteratorUtils.asList(candIterator));

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(new BigDecimal("-50")));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(BigDecimal.ZERO));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(new BigDecimal("-50")));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		manualIc1.setIsToClear(true);

		invoiceCandBL.set_QtyInvoiced_NetAmtInvoiced_Aggregation(manualIc1);

		// Tests isCreditMemo. Needs to be manual and have a price actual override (which is why manualIc2 would return false)
		manualIc1.setPriceActual_Override(new BigDecimal("-50"));
		Assertions.assertThat(invoiceCandBL.isCreditMemo(manualIc1)).isTrue();
		Assertions.assertThat(invoiceCandBL.isCreditMemo(manualIc2)).isFalse();
		Assertions.assertThat(invoiceCandBL.isCreditMemo(ic1)).isFalse();

		// Currently not tested. We must have an invoice rule that is in the list, but not covered in BL.
		// ic1.setInvoiceRule_Override("Invalid rule");
		// ic1.setIsToRecompute(true);
		// Services.get(IInvoiceCandDAO.class).invalidateAllCands(ctx, ITrx.TRXNAME_None);
		//
		// updateInvalidCandidates();
		//
		// assertThat(ic1.getQtyToInvoice(), comparesEqualTo(BigDecimal.ZERO));

	}

	// We cannot test the part with "last date of week/month" because de.metas.invoicecandidate.api.impl.InvoiceCandBL.set_DateToInvoice(Properties, I_C_Invoice_Candidate, String)
	// uses the system calendar to determine "today" and "last date of week/month"
	@Test
	public void testSchedules()
	{

		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(160)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();

		ic1.setC_InvoiceSchedule(schedule("1", X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily));
		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_CustomerScheduleAfterDelivery);
		ic1.setQtyDelivered(ic1.getQtyOrdered());
		POJOWrapper.setInstanceName(ic1, "ic1");
		InterfaceWrapperHelper.save(ic1);

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc1, "manualIc1");
		manualIc1.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc1);

		final I_C_Invoice_Candidate manualIc2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();

		POJOWrapper.setInstanceName(manualIc2, "manualIc2");
		manualIc2.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc2);

		final I_C_Invoice_Candidate manualIc3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(-1)
				.setManual(true)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(manualIc3, "manualIc3");
		manualIc3.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc3);

		invoiceCandBL.invalidateForInvoiceSchedule(schedule("1", "aaaa"));

		updateInvalidCandidates();

		InterfaceWrapperHelper.refresh(ic1);
		InterfaceWrapperHelper.refresh(manualIc1);
		InterfaceWrapperHelper.refresh(manualIc2);
		InterfaceWrapperHelper.refresh(manualIc3);

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		ic1.setC_InvoiceSchedule(schedule("2", X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly));

		invoiceCandBL.invalidateForInvoiceSchedule(schedule("2", "aaaa"));

		updateInvalidCandidates();

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		ic1.setC_InvoiceSchedule(schedule("3", X_C_InvoiceSchedule.INVOICEFREQUENCY_Weekly));

		invoiceCandBL.invalidateForInvoiceSchedule(schedule("3", "aaaa"));

		updateInvalidCandidates();

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		ic1.setC_InvoiceSchedule(schedule("4", X_C_InvoiceSchedule.INVOICEFREQUENCY_TwiceMonthly));

		invoiceCandBL.invalidateForInvoiceSchedule(schedule("4", "aaaa"));

		updateInvalidCandidates();

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		ic1.setC_InvoiceSchedule(schedule("5", X_C_InvoiceSchedule.INVOICEFREQUENCY_TwiceMonthly));

		schedule("5", "aaaa").setIsAmount(true);
		schedule("4", "aaaa").setAmt(new BigDecimal("40"));

		invoiceCandBL.invalidateForInvoiceSchedule(schedule("5", "aaaa"));

		updateInvalidCandidates();

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

	}

	@Test
	public void testWithOrder()
	{
		final Properties ctx = Env.getCtx();
		Env.setContext(ctx, Env.CTXNAME_AD_Client_ID, 1);
		Env.setContext(ctx, Env.CTXNAME_AD_Language, "de_CH");

		final I_C_Order order1 = order("1");
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(160)
				.setQtyOrdered(1)
				.setManual(false)
				.setSOTrx(true)
				.build();
		ic1.setC_InvoiceSchedule(schedule("1", X_C_InvoiceSchedule.INVOICEFREQUENCY_Daily));
		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_CustomerScheduleAfterDelivery);
		ic1.setC_Order(order1);
		ic1.setC_OrderLine(orderLine("1"));
		ic1.setQtyDelivered(ic1.getQtyOrdered());
		POJOWrapper.setInstanceName(ic1, "ic1");
		InterfaceWrapperHelper.save(ic1);

		order1.setDocStatus(DocStatus.Completed.getCode());
		order1.setDocAction(X_C_Order.DOCACTION_Close);
		InterfaceWrapperHelper.save(order1);

		final I_C_Invoice_Candidate manualIc1 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(manualIc1, "manualIc1");
		manualIc1.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc1);

		final I_C_Invoice_Candidate manualIc2 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(1)
				.setManual(true)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(manualIc2, "manualIc2");
		manualIc2.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc2);

		final I_C_Invoice_Candidate manualIc3 = createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(-50)
				.setQtyOrdered(-1)
				.setManual(true)
				.setSOTrx(true)
				.build();
		POJOWrapper.setInstanceName(manualIc3, "manualIc3");
		manualIc3.setC_ILCandHandler(manualHandler);
		InterfaceWrapperHelper.save(manualIc3);

		updateInvalidCandidates();

		InterfaceWrapperHelper.refresh(ic1);
		InterfaceWrapperHelper.refresh(manualIc1);
		InterfaceWrapperHelper.refresh(manualIc2);
		InterfaceWrapperHelper.refresh(manualIc3);

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_AfterOrderDelivered);

		invoiceCandDAO.invalidateAllCands(ctx, ITrx.TRXNAME_None);

		updateInvalidCandidates();

		assertThat(manualIc1.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc1.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc2.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("-50")));
		assertThat(manualIc2.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

		assertThat(manualIc3.getNetAmtToInvoice(), comparesEqualTo(new BigDecimal("50")));
		assertThat(manualIc3.getSplitAmt(), comparesEqualTo(BigDecimal.ZERO));

	}

	@Test
	public void testInvoiceGenerate()
	{
		final Properties ctx = Env.getCtx();

		final I_C_Tax tax = tax(new BigDecimal("4"));
		docType(DocBaseType.ARInvoice, null);

		final I_AD_User user = user("1");
		user.setC_BPartner_ID(bpartner("1").getC_BPartner_ID());

		final I_C_Invoice_Candidate ic1 = createInvoiceCandidate(1, 160, 1, false, true); // BP, Price, Qty, IsManual
		ic1.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_CustomerScheduleAfterDelivery);
		ic1.setBill_BPartner_ID(bpartner("1").getC_BPartner_ID());
		ic1.setC_Order(order("1"));
		ic1.setC_OrderLine(orderLine("1"));
		ic1.setQtyDelivered(ic1.getQtyOrdered());
		ic1.setIsToRecompute(true);
		ic1.setAD_User_InCharge_ID(user.getAD_User_ID());
		ic1.setC_Tax_ID(tax.getC_Tax_ID());
		POJOWrapper.setInstanceName(ic1, "ic1");
		InterfaceWrapperHelper.save(ic1);

		final I_C_Invoice_Candidate ic2 = createInvoiceCandidate(1, 160, 1, false, true); // BP, Price, Qty, IsManual
		ic2.setInvoiceRule_Override(X_C_Invoice_Candidate.INVOICERULE_CustomerScheduleAfterDelivery);
		ic2.setBill_BPartner_ID(bpartner("1").getC_BPartner_ID());
		ic2.setC_Order(order("2"));
		ic2.setC_OrderLine(orderLine("2"));
		ic2.setQtyDelivered(ic2.getQtyOrdered());
		ic2.setIsToRecompute(true);
		ic2.setAD_User_InCharge_ID(user.getAD_User_ID());
		ic2.setC_Tax_ID(tax.getC_Tax_ID());
		POJOWrapper.setInstanceName(ic2, "ic2");
		InterfaceWrapperHelper.save(ic2);

		invoiceCandDAO.invalidateAllCands(ctx, ITrx.TRXNAME_None);

		final String trxName = InterfaceWrapperHelper.getTrxName(ic1);

		/* final IInvoiceGenerateResult result = */
		invoiceCandBL.generateInvoicesFromSelection(ctx, PInstanceId.ofRepoIdOrNull(0), true, trxName);

		// FIXME Commented out for now, as we need to persist the transaction between the order and invoice.
		// assertThat(result.getInvoiceCount(), comparesEqualTo(2));
	}

}

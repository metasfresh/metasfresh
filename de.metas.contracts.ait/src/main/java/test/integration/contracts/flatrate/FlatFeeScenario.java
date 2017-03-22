package test.integration.contracts.flatrate;

/*
 * #%L
 * de.metas.contracts.ait
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPeriod;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.adempiere.ait.helper.OrderHelper;
import de.metas.adempiere.ait.helper.TestConfig;
import de.metas.adempiere.model.I_C_Order;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_DataEntry;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.model.I_C_Invoice_Clearing_Alloc;
import de.metas.flatrate.model.X_C_Flatrate_DataEntry;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.flatrate.process.C_Flatrate_Term_Extend;
import de.metas.flatrate.process.C_Flatrate_Term_Prepare_Closing;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import test.integration.contracts.ContractsHelper;
import test.integration.contracts.ContractsTestConfig;
import test.integration.swat.sales.invoicecand.InvoiceCandHelper;

public class FlatFeeScenario
{

	public static final String PARAM_BD_ACTUAL_QTY = "ActualQty";

	public static final String PARAM_BD_UNITS_REPORTED = "UnitsReported";

	public static final String PARAM_BOOL_IS_SIMULATION = "IsSimulation";

	public static final String PARAM_BD_PLANNED_QTY_PER_UNIT = "PlannedQtyPerUnit";

	public static final String PARAM_BD_PRICE_PER_UNIT = "PricePerUnit";

	public static final String PARAM_BD_PRICE_PER_UNIT_CLOSING = "PricePerUnitClosing";

	public static final String PARAM_BD_EXP_ACTUAL_AMOUNT = "ExpectedActualAmt";

	public static final String PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT = "ExpectedActualQtyPerUnit";

	public static final String PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT = "ExpectedActualDiffPerUnit";

	public static final String PARAM_BD_EXP_ACTUAL_DIFF_PERCENT = "ExpectedActualDiffPerPercent";

	public static final String PARAM_BD_EXP_ACTUAL_CORR_AMOUNT = "ExpectedCorrectionAmt";

	public static final String PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT = "ExpectedActualEffectiveDiffPerPercent";

	public static final String PARAM_STR_TYPE_CLEARING = "TypeFlatFeeClearing";

	public static final String PARAM_STR_PRODUCT_ACTUAL_VALUE = "ProductActualValue";
	
	public static final String PARAM_STR_PRODUCT_FLATRATE_VALUE = "ProductFlatrateValue";

	public static final String PARAM_BOOL_IS_CORR_AFTER_CLOSING = "IsCorrectionAfterClosing";

	public static final String PARAM_BOOL_IS_CREATE_ORDER_LINES = "IsCreateOrderLines";

	private final AIntegrationTestDriver driver;

	private final ContractsHelper helper;

	private final InvoiceCandHelper invoiceCandHelper;

	public FlatFeeScenario(AIntegrationTestDriver driver)
	{
		this.driver = driver;
		helper = (ContractsHelper)driver.getHelper();
		invoiceCandHelper = new InvoiceCandHelper(driver.getHelper());
	}

	public void fullScenario()
	{
		final TermAndOrder termAndOrder = createTermThenOrder();
		finishTerm(termAndOrder);

		extendTermManually(termAndOrder);
	}

	public TermAndOrder createOrderThenTerm()
	{
		final ContractsTestConfig testConfig = helper.getContractsTestConfig();

		// creating a standard order with our product under flatrate
		final I_C_Order order = helper.mkOrderHelper()
				.setDocSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.setBPartnerName(testConfig.getC_BPartner_Value())
				.setInvoiceRule(OrderHelper.Order_InvoiceRule.IMMEDIATE)
				.setFreighCostRule(X_C_Order.FREIGHTCOSTRULE_FreightIncluded)
				.setComplete(DocAction.STATUS_Completed)

				.addLine( // note: the price doesn't really matter, so I set it to 99
						testConfig.getM_Product_Matching_Flatfee_Value(),
						testConfig.getCustomParamBD(PARAM_BD_ACTUAL_QTY), 99, 99)
				.createOrder();

		driver.fireTestEvent(EventType.ORDER_STANDARD_COMPLETE_AFTER, order);

		invoiceCandHelper.runProcess_UpdateInvoiceCands();

		final I_C_Flatrate_Term term = contractTerm();
		final TermAndOrder termAndOrder = new TermAndOrder(term, order);

		invoiceCandHelper.runProcess_UpdateInvoiceCands();

		afterTermAndOrderCreation(termAndOrder);
		return termAndOrder;
	}

	/**
	 * Creates a flat rate contract with term and an order with a product under flatrate. Checks that the order and it's
	 * invoice candidate is correctly processed with regard to the flat rate term.
	 * 
	 * @return
	 */
	public TermAndOrder createTermThenOrder()
	{
		final I_C_Flatrate_Term term = contractTerm();

		final ContractsTestConfig testConfig = helper.getContractsTestConfig();

		// creating a standard order with our product under flatrate
		final I_C_Order order = helper.mkOrderHelper()
				.setDocSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.setBPartnerName(testConfig.getC_BPartner_Value())
				.setInvoiceRule(OrderHelper.Order_InvoiceRule.IMMEDIATE)
				.setComplete(DocAction.STATUS_Completed)

				.addLine( // note: the price doesn't really matter, so I set it to 99
						testConfig.getM_Product_Matching_Flatfee_Value(),
						testConfig.getCustomParamBD(PARAM_BD_ACTUAL_QTY), 99, 99)
				.createOrder();

		driver.fireTestEvent(EventType.ORDER_STANDARD_COMPLETE_AFTER, order);

		invoiceCandHelper.runProcess_UpdateInvoiceCands();

		final TermAndOrder termAndOrder = new TermAndOrder(term, order);
		afterTermAndOrderCreation(termAndOrder);

		return termAndOrder;
	}

	/**
	 * Creates a I_C_Flatrate_Transition, I_C_Flatrate_Conditions and I_C_Flatrate_Term.
	 * 
	 * @return
	 */
	private I_C_Flatrate_Term contractTerm()
	{
		final TestConfig testConfig = helper.getConfig();

		final I_C_Flatrate_Transition ft = helper.createTransistion(driver);
		final I_C_Flatrate_Conditions fc = helper.createFlatFeeContract(driver, ft);
		final I_C_Flatrate_Term term = helper.createFlatFeeTerm(driver, fc);

		// just some little guards
		assertThat(term + " has wrong IsSimulation",
				term.isSimulation(), is(testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION)));

		assertThat(term + " has wrong DocStatus",
				term.getDocStatus(), equalTo(X_C_Flatrate_Term.DOCSTATUS_Completed));

		return term;
	}

	private void afterTermAndOrderCreation(final TermAndOrder termAndOrder)
	{
		//
		driver.logger.info("Checking the invoice candidates before the data entry has been completed");

		final TestConfig testConfig = helper.getConfig();
		final I_C_Order order = termAndOrder.order;
		final I_C_Flatrate_Term term = termAndOrder.term;
		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		final GridWindowHelper dataEntryHelper = helper.retrieveMainInvoicingEntry(fc, order);
		final I_C_Flatrate_DataEntry dataEntry = dataEntryHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);

		checkInvoicingDataEntry(order, dataEntry, term);

		if (testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION))
		{
			// In a simulation, we set the Qty ourselves
			dataEntry.setActualQty(testConfig.getCustomParamBD(PARAM_BD_ACTUAL_QTY));

			// Note: in a non-sim, the Qty should have been set from the order's invoice candidate, this is verified in
			// checkInvoicingDataEntry()
		}

		//
		//
		driver.logger.info("Completing " + dataEntry);
		dataEntry.setQty_Reported(testConfig.getCustomParamBD(PARAM_BD_UNITS_REPORTED));
		dataEntryHelper.save();
		helper.completeEntry(dataEntryHelper, X_C_Flatrate_DataEntry.DOCSTATUS_Completed);

		//
		//
		driver.logger.info("Checking the invoice candidates *after* the data entry has been completed");

		invoiceCandHelper.runProcess_UpdateInvoiceCands();
		dataEntryHelper.refresh();

		checkInvoicingDataEntry(order, dataEntry, term);
	}

	/**
	 * Reactivates an already completed entry and completes it once again.
	 * 
	 * @param termAndOrder
	 */
	public void reactivateInvoicingEntryAndCompleteAgain(final TermAndOrder termAndOrder)
	{
		final I_C_Flatrate_Term term = termAndOrder.term;

		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();
		final I_C_Order order = termAndOrder.order;

		final GridWindowHelper dataEntryHelper = helper.retrieveMainInvoicingEntry(fc, order);
		final I_C_Flatrate_DataEntry dataEntry = dataEntryHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);

		reactivateAndComplete(dataEntryHelper, dataEntry);

		checkInvoicingDataEntry(order, dataEntry, term);
	}

	private void reactivateAndComplete(final GridWindowHelper dataEntryHelper, final I_C_Flatrate_DataEntry dataEntry)
	{
		assertThat(dataEntry + " has wrong DocStatus", dataEntry.getDocStatus(), is(X_C_Flatrate_DataEntry.DOCSTATUS_Completed));

		final I_C_Invoice_Candidate icBeforeReactivation = dataEntry.getC_Invoice_Candidate();
		final I_C_Invoice_Candidate icCorrBeforeReactivation = dataEntry.getC_Invoice_Candidate_Corr();

		helper.reactivateEntry(dataEntryHelper);

		assertThat(dataEntry + " has wrong C_Invoice_Candidate_ID", dataEntry.getC_Invoice_Candidate_ID(), is(0));
		assertThat(dataEntry + " has wrong C_Invoice_Candidate_Corr_ID", dataEntry.getC_Invoice_Candidate_Corr_ID(), is(0));

		// invoice cand(s) should have been deleted
		final I_C_Invoice_Candidate icAfterReactivation = InterfaceWrapperHelper.create(driver.getCtx(), icBeforeReactivation.getC_Invoice_Candidate_ID(), I_C_Invoice_Candidate.class, driver.getTrxName());
		assertThat(icAfterReactivation, nullValue());
		final I_C_Invoice_Candidate icCorrAfterReactivation = InterfaceWrapperHelper.create(driver.getCtx(), icCorrBeforeReactivation.getC_Invoice_Candidate_ID(), I_C_Invoice_Candidate.class, driver.getTrxName());
		assertThat(icCorrAfterReactivation, nullValue());

		//
		// completing again
		//

		// expecting completion to fail, because depending invoice candidates have no been updated yet
		helper.completeEntry(dataEntryHelper, X_C_Flatrate_DataEntry.DOCSTATUS_Invalid);

		// running UpdateInvoiceCands and expecting the completIt to succeed
		invoiceCandHelper.runProcess_UpdateInvoiceCands();
		try
		{
			Thread.sleep(1000); // do nothing for 1000 miliseconds (1 second)
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		helper.completeEntry(dataEntryHelper, X_C_Flatrate_DataEntry.DOCSTATUS_Completed);

		invoiceCandHelper.runProcess_UpdateInvoiceCands();
		dataEntryHelper.refresh();
	}

	public void finishTerm(final TermAndOrder termAndOrder)
	{
		final TestConfig testConfig = helper.getConfig();

		final I_C_Flatrate_Term term = termAndOrder.term;
		final I_C_Order order = termAndOrder.order;
		final I_C_Flatrate_Conditions fc = term.getC_Flatrate_Conditions();

		final GridWindowHelper dataEntryHelper = helper.retrieveMainInvoicingEntry(fc, order);
		final I_C_Flatrate_DataEntry dataEntry = dataEntryHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);

		final boolean paramIsSimulation =
				testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION);

		if (!paramIsSimulation)
		{
			assertThat(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_AMOUNT + " for " + dataEntry,
					dataEntry.getFlatrateAmt(), comparesEqualTo(testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_AMOUNT)));
			assertThat(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT + " for " + dataEntry,
					dataEntry.getActualQtyPerUnit(), comparesEqualTo(testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT)));
			assertThat(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT + " for " + dataEntry,
					dataEntry.getActualQtyDiffPerUOM(), comparesEqualTo(testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT)));

			assertThat(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT + " for " + dataEntry,
					dataEntry.getActualQtyDiffPercentEff(), comparesEqualTo(testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT)));
			assertThat(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PERCENT + " for " + dataEntry,
					dataEntry.getActualQtyDiffPercent(), comparesEqualTo(testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PERCENT)));
			assertThat(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_CORR_AMOUNT + " for " + dataEntry,
					dataEntry.getFlatrateAmtCorr(), comparesEqualTo(testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_CORR_AMOUNT)));

			final I_C_Invoice_Candidate invoiceCand = dataEntry.getC_Invoice_Candidate();

			assertThat(invoiceCand.getM_Product_ID(), is(fc.getM_Product_Flatrate_ID()));

			if (fc.getM_Product_Actual_ID() == fc.getM_Product_Flatrate_ID() && !fc.isCorrectionAmtAtClosing())
			{
				assertThat(dataEntry.getC_Invoice_Candidate_Corr_ID(), is(0));

				assertThat(invoiceCand.getNetAmtToInvoice(), comparesEqualTo(dataEntry.getFlatrateAmt().add(dataEntry.getFlatrateAmtCorr())));

				processCands(invoiceCandHelper, invoiceCand);

				assertThat(invoiceCand.getNetAmtInvoiced(), comparesEqualTo(dataEntry.getFlatrateAmt().add(dataEntry.getFlatrateAmtCorr())));
			}
			else
			{
				assertThat(invoiceCand.getNetAmtToInvoice(), comparesEqualTo(dataEntry.getFlatrateAmt()));

				if (testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING))
				{
					assertThat(fc.isCorrectionAmtAtClosing(), is(true)); // just a little guard
					assertThat(dataEntry + " has wrong C_Invoice_Candidate_Corr_ID", dataEntry.getC_Invoice_Candidate_Corr_ID(), is(0));
					processCands(invoiceCandHelper, invoiceCand);
				}
				else
				{
					assertThat(fc.isCorrectionAmtAtClosing(), is(false)); // just a little guard
					assertThat(dataEntry.getC_Invoice_Candidate_Corr_ID(), greaterThan(0));
					final I_C_Invoice_Candidate invoiceCandCorr = dataEntry.getC_Invoice_Candidate_Corr();

					assertThat(invoiceCandCorr.getM_Product_ID(), is(fc.getM_Product_Actual_ID()));
					assertThat(invoiceCandCorr.getNetAmtToInvoice(), comparesEqualTo(dataEntry.getFlatrateAmtCorr()));

					processCands(invoiceCandHelper, invoiceCand, invoiceCandCorr);

					assertThat(invoiceCandCorr.getNetAmtInvoiced(), comparesEqualTo(dataEntry.getFlatrateAmtCorr()));
				}

				assertThat(invoiceCand.getNetAmtInvoiced(), comparesEqualTo(dataEntry.getFlatrateAmt()));
			}
		}

		// now complete all dataEntries with a Qty_reported of 1 each
		final List<I_C_Invoice_Candidate> allCandidates = helper.completeAllInvoicingEntries(term, BigDecimal.ONE);
		if (!paramIsSimulation)
		{
			assertThat(allCandidates.size(), greaterThan(0));
		}
		processCands(invoiceCandHelper, allCandidates.toArray(new I_C_Invoice_Candidate[allCandidates.size()]));

		if (!term.isClosingWithCorrectionSum())
		{
			// we are done
			return;
		}

		final MPeriod closingPeriod = MPeriod.findByCalendar(driver.getCtx(), term.getEndDate(), fc.getC_Flatrate_Transition().getC_Calendar_Contract_ID(), driver.getTrxName());

		helper.mkProcessHelper()
				.setProcessClass(C_Flatrate_Term_Prepare_Closing.class)
				.setPO(term)
				.setParameter(I_C_Period.COLUMNNAME_C_Period_ID, closingPeriod.getC_Period_ID())
				.run();

		final GridWindowHelper entryGridWindowHelper = helper.retrieveMainCorrectionEntry(fc, term);
		final I_C_Flatrate_DataEntry correctionEntry = entryGridWindowHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);

		// expecting <PARAM_TRANSITION_TERM_DURATION> times 1 plus <PARAM_BD_UNITS_REPORTED>
		assertThat(correctionEntry + " has wrong Qty_Planned", correctionEntry.getQty_Planned(),
				comparesEqualTo(
				new BigDecimal(testConfig.getCustomParamInt(ContractsHelper.PARAM_TRANSITION_TERM_DURATION))
						.add(testConfig.getCustomParamBD(PARAM_BD_UNITS_REPORTED))));

		final BigDecimal correctionQty = testConfig.getCustomParamBD(PARAM_BD_UNITS_REPORTED).add(new BigDecimal("2"));
		correctionEntry.setQty_Reported(correctionQty);

		helper.completeEntry(entryGridWindowHelper, X_C_Flatrate_DataEntry.DOCSTATUS_Completed);
		invoiceCandHelper.runProcess_UpdateInvoiceCands();

		assertThat(correctionEntry + " wrong FlatrateAmtPerUOM",
				correctionEntry.getFlatrateAmtPerUOM(), comparesEqualTo(testConfig.getCustomParamBD(PARAM_BD_PRICE_PER_UNIT_CLOSING)));
		assertThat(correctionEntry + " wrong FlatrateAmt",
				correctionEntry.getFlatrateAmt(), comparesEqualTo(correctionQty.subtract(correctionEntry.getQty_Planned()).multiply(correctionEntry.getFlatrateAmtPerUOM())));

		if (testConfig.getCustomParamBool(PARAM_BOOL_IS_SIMULATION))
		{
			assertThat(correctionEntry.getC_Invoice_Candidate_ID(), is(0));
			assertThat(correctionEntry.getC_Invoice_Candidate_Corr_ID(), is(0));

			if (testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING))
			{
				assertThat(correctionEntry + " has wrong ActualQty",
						correctionEntry.getActualQty(), comparesEqualTo(testConfig.getCustomParamBD(PARAM_BD_ACTUAL_QTY)));
				assertThat(correctionEntry + " has wrong FlatrateAmtCorr.signum()",
						correctionEntry.getFlatrateAmtCorr().signum(), not(is(0)));
			}
		}
		else
		{
			assertThat(correctionEntry.getC_Invoice_Candidate_ID(), greaterThan(0));
			final I_C_Invoice_Candidate correctionEntryCand = correctionEntry.getC_Invoice_Candidate();

			assertThat(correctionEntryCand + " wrong M_Product_ID",
					correctionEntryCand.getM_Product_ID(), is(fc.getM_Product_Correction_ID()));
			assertThat(correctionEntryCand + " wrong NetAmtToInvoice",
					correctionEntryCand.getNetAmtToInvoice(), comparesEqualTo(correctionEntry.getFlatrateAmt()));

			if (testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING))
			{
				assertThat(correctionEntry + " has wrong ActualQty",
						correctionEntry.getActualQty(), comparesEqualTo(testConfig.getCustomParamBD(PARAM_BD_ACTUAL_QTY)));

				// note: the actual numbers are checked in a unit test
				assertThat(correctionEntry + " has wrong FlatrateAmtCorr().signum()", correctionEntry.getFlatrateAmtCorr().signum(), greaterThan(0));
				assertThat(correctionEntry.getC_Invoice_Candidate_Corr_ID(), greaterThan(0));

				final I_C_Invoice_Candidate correctionEntryCorrCand = correctionEntry.getC_Invoice_Candidate_Corr();
				assertThat(correctionEntryCorrCand.getM_Product_ID(), is(fc.getM_Product_Actual_ID()));
				assertThat(correctionEntryCorrCand.getNetAmtToInvoice(), comparesEqualTo(correctionEntry.getFlatrateAmtCorr()));
			}
			else
			{
				assertThat(correctionEntry.getFlatrateAmtCorr().signum(), equalTo(0));
				assertThat(correctionEntry.getC_Invoice_Candidate_Corr_ID(), equalTo(0));
			}
		}
	}

	public I_C_Flatrate_Term extendTermManually(final TermAndOrder termAndOrder)
	{
		final TestConfig testConfig = helper.getConfig();

		final I_C_Flatrate_Term term = termAndOrder.term;

		helper.mkProcessHelper()
				.setProcessClass(C_Flatrate_Term_Extend.class)
				.setPO(term)
				.setParameter(I_C_Flatrate_Transition.COLUMNNAME_IsAutoCompleteNewTerm, "Y")
				.run();

		final GridWindowHelper termGridWindowHelper = helper.retrievTermGridWindowHelper(term);
		final I_C_Flatrate_Term termExtendedAndReloaded = termGridWindowHelper.getGridTabInterface(I_C_Flatrate_Term.class);
		assertThat(termExtendedAndReloaded.getAD_PInstance_EndOfTerm_ID(), greaterThan(0));
		assertThat(termExtendedAndReloaded.getC_FlatrateTerm_Next_ID(), greaterThan(0));
		termGridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Term.COLUMNNAME_ExtendTerm, false);

		final boolean paramIsSimulation =
				testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION);

		final I_C_Flatrate_Term nextTerm = termExtendedAndReloaded.getC_FlatrateTerm_Next();
		// just some little guards
		assertThat(nextTerm.isSimulation(), is(paramIsSimulation));
		assertThat(nextTerm.getDocStatus(), equalTo(X_C_Flatrate_Term.DOCSTATUS_Completed));

		return nextTerm;
	}

	/**
	 * Makes an invoice run with the given <code>cands</code>.
	 * 
	 * @param invoiceCandHelper
	 * @param cands
	 */
	private void processCands(final InvoiceCandHelper invoiceCandHelper, final I_C_Invoice_Candidate... cands)
	{
		invoiceCandHelper.runProcess_UpdateInvoiceCands();
		invoiceCandHelper.runProcess_InvoiceGenerateFromCands(cands);
		invoiceCandHelper.runProcess_UpdateInvoiceCands();

		for (final I_C_Invoice_Candidate cand : cands)
		{
			InterfaceWrapperHelper.refresh(cand);
			assertThat(cand + " has wrong 'Processed' status", cand.isProcessed(), is(true));
		}
	}

	private void checkInvoicingDataEntry(
			final I_C_Order order,
			final I_C_Flatrate_DataEntry dataEntry,
			final I_C_Flatrate_Term term)
	{
		assertThat(dataEntry.isSimulation(), equalTo(term.isSimulation()));
		assertThat(term.getC_Flatrate_Term_ID(), equalTo(dataEntry.getC_Flatrate_Term_ID()));

		final TestConfig testConfig = helper.getConfig();
		final boolean paramIsSimulation =
				testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION);

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		BigDecimal sum = BigDecimal.ZERO;

		final boolean dataEntryCompleted = dataEntry.getDocStatus().equals(X_C_Flatrate_DataEntry.DOCSTATUS_Completed);

		final List<I_C_Invoice_Clearing_Alloc> clearingAllocs = flatrateDB.retrieveClearingAllocs(dataEntry);

		if (paramIsSimulation)
		{
			assertThat("Expected *no* C_Invoice_Clearing_Allocs for " + dataEntry,
					clearingAllocs.size(), equalTo(0));
		}
		else
		{
			// note that we expect the C_Invoice_Clearing_Allocs no matter whether 'dataEntry' has already been
			// completed
			assertThat("Expected C_Invoice_Clearing_Allocs for " + dataEntry,
					clearingAllocs.size(), greaterThan(0));
		}

		for (final MOrderLine ol : driver.getHelper().mkOrderHelper().getOrderPO(order).getLines(true, null))
		{
			for (final I_C_Invoice_Clearing_Alloc ica : clearingAllocs)
			{
				assertThat(ica.getC_Invoice_Cand_ToClear_ID(), greaterThan(0));

				assertThat(ica.getC_Flatrate_Term_ID(), equalTo(term.getC_Flatrate_Term_ID()));
				assertThat(ica.getC_Flatrate_DataEntry_ID(), equalTo(dataEntry.getC_Flatrate_DataEntry_ID()));

				final I_C_Invoice_Candidate icToClear = ica.getC_Invoice_Cand_ToClear();

				assertThat(icToClear + " has wrong C_OrderLine_ID", ol.getC_OrderLine_ID(), equalTo(icToClear.getC_OrderLine_ID()));

				assertThat("Sales test driver should have called the update process", icToClear.isToRecompute(), is(false));
				assertThat(icToClear.isToClear(), is(true));

				if (dataEntryCompleted)
				{
					assertThat(ica.getC_Invoice_Candidate_ID(), greaterThan(0));
					assertThat(ica.getC_Invoice_Candidate_ID(), equalTo(dataEntry.getC_Invoice_Candidate_ID()));

					final I_C_Invoice_Candidate newCand = ica.getC_Invoice_Candidate();
					assertThat("Sales test driver should have called the update process",
							newCand.isToRecompute(), is(false));

					assertThat(newCand.isToClear(), is(false));
					assertThat(newCand.isProcessed(), is(false));
					assertThat(icToClear + " has been marked a processed", icToClear.isProcessed(), is(true));
					assertThat(icToClear.getQtyInvoiced(), not(comparesEqualTo(BigDecimal.ZERO)));
					assertThat(icToClear.getQtyToInvoice(), comparesEqualTo(BigDecimal.ZERO));

					sum = sum.add(icToClear.getQtyInvoiced());
				}
				else
				{
					assertThat(ica.getC_Invoice_Candidate_ID(), equalTo(0));

					assertThat(icToClear + " has wrong 'Processed'", icToClear.isProcessed(), is(false));
					assertThat(icToClear + " has wrong 'QtyInvoiced'", icToClear.getQtyInvoiced(), comparesEqualTo(BigDecimal.ZERO));
					assertThat(icToClear + " has wrong 'QtyToInvoice'", icToClear.getQtyToInvoice(), not(comparesEqualTo(BigDecimal.ZERO)));

					sum = sum.add(icToClear.getQtyToInvoice());
				}

			}
		}

		if (!paramIsSimulation)
		{
			assertThat("Expecting test config param 'PARAM_ACTUAL_QTY' to match C_FlatRate_DataEntry.ActualQty of " + dataEntry,
					testConfig.getCustomParamBD(PARAM_BD_ACTUAL_QTY), comparesEqualTo(dataEntry.getActualQty()));
			assertThat("Expecting sum of invoice candidates to match C_FlatRate_DataEntry.ActualQty of " + dataEntry,
					sum, comparesEqualTo(dataEntry.getActualQty()));
		}
	}

	public static class TermAndOrder
	{
		// private final I_C_Flatrate_Transition transition;
		// private final I_C_Flatrate_Conditions conditions;
		private final I_C_Flatrate_Term term;
		private final I_C_Order order;

		private TermAndOrder(
				I_C_Flatrate_Term term,
				I_C_Order order)
		{
			this.term = term;
			this.order = order;
		}

		@Override
		public String toString()
		{
			return "[C_Flatrate_Term_ID=" + term.getC_Flatrate_Term_ID() + ", C_Order_ID=" + order.getC_Order_ID() + "]";
		}
	}
}

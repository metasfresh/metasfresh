package test.integration.contracts;

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


import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MUOM;
import org.compiere.model.Query;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.adempiere.ait.helper.HelperDelegator;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.TestConfig;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.contracts.flatrate.api.IFlatrateDAO;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Matching;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Contract_Change;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.currency.ICurrencyDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.interfaces.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import test.integration.contracts.flatrate.FlatFeeScenario;

public class ContractsHelper extends HelperDelegator
{
	public static final String PARAM_CONTRACT_CANGE_CANCEL_DEADLINE = "C_Contract_Cange.cancel.Deadline";

	public static final String PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT = "C_Contract_Cange.cancel.DeadlineUnit";

	public static final String PARAM_TRANSITION_TERM_DURATION = "C_Flatrate_Transition.term.Duration";

	public static final String PARAM_TRANSITION_TERM_DURATION_UNIT = "C_Flatrate_Transition.term.DurationUnit";

	public static final String PARAM_TRANSITION_DELIVERY_INTERVAL = "C_Flatrate_Transition.term.DeliveryInterval";

	public static final String PARAM_TRANSITION_DELIVERY_INTERVAL_UNIT = "C_Flatrate_Transition.term.DeliveryIntervalUnit";

	public static final String PARAM_TERM_CURRENCY_ID = "C_Currency_ID";
	private ContractsTestConfig contractsTestConfig = null;

	public ContractsHelper(IHelper helper)
	{
		super(helper);
	}

	@Override
	public TestConfig getConfig()
	{
		if (contractsTestConfig == null)
		{
			return super.getConfig();
		}

		return getContractsTestConfig();
	}

	public ContractsTestConfig getContractsTestConfig()
	{
		if (contractsTestConfig == null)
		{
			contractsTestConfig = new ContractsTestConfig(getConfig());
		}
		return contractsTestConfig;
	}

	/**
	 * Creates a transition record with the following properties
	 * <ul>
	 * <li>a duration of six months</li>
	 * <li>a cancellation deadline of 2 months</li>
	 * <li>a cancellation charge of four
	 * </ul>
	 *
	 * @param driver
	 * @return
	 */
	public I_C_Flatrate_Transition createTransistion(final AIntegrationTestDriver driver)
	{
		final GridWindowHelper gridWindowHelper = mkGridWindowHelper();

		I_C_Flatrate_Transition trans = gridWindowHelper
				.openTabForTable(I_C_Flatrate_Transition.Table_Name, true)
				.newRecord()
				.getGridTabInterface(I_C_Flatrate_Transition.class);

		final ContractsTestConfig testConfig = getContractsTestConfig();

		trans.setName(testConfig.getPrefix() + "_" + driver.getHelper().getNow());
		trans.setIsAutoCompleteNewTerm(true);
		trans.setIsAutoRenew(true);
		trans.setIsNotifyUserInCharge(true);

		trans.setTermDuration(testConfig.getCustomParamInt(PARAM_TRANSITION_TERM_DURATION));
		trans.setTermDurationUnit(testConfig.getCustomParamStr(PARAM_TRANSITION_TERM_DURATION_UNIT));

		trans.setTermOfNotice(4);
		trans.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_WocheN);

		trans.setDeliveryInterval(testConfig.getCustomParamInt(PARAM_TRANSITION_DELIVERY_INTERVAL));
		trans.setDeliveryIntervalUnit(testConfig.getCustomParamStr(PARAM_TRANSITION_DELIVERY_INTERVAL_UNIT));

		final String calendarName = testConfig.getC_Calendar_Flatrate_Name();
		assertNotNull("Expected testconfig to have a C_Calendar_Flatrate_Name set", calendarName);
		final int calendarId = retrieveCalendarId(calendarName);
		assertThat("Expected a calendar with name '" + calendarName + "' to exist", calendarId, greaterThan(0));
		trans.setC_Calendar_Contract_ID(calendarId);
		trans.setDocAction(X_C_Flatrate_Transition.DOCACTION_Complete);

		gridWindowHelper.save();

		final I_C_Contract_Change change = gridWindowHelper.selectTab(I_C_Contract_Change.Table_Name)
				.newRecord()
				.getGridTabInterface(I_C_Contract_Change.class);

		change.setAction(X_C_Contract_Change.ACTION_Statuswechsel);
		change.setContractStatus(X_C_Contract_Change.CONTRACTSTATUS_Gekuendigt);

		// setting cancel deadline params from custom properties that might have been set by the calling test case
		change.setDeadLine(testConfig.getCustomParamInt(PARAM_CONTRACT_CANGE_CANCEL_DEADLINE));
		change.setDeadLineUnit(testConfig.getCustomParamStr(PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT));

		final I_M_Product chargeProduct = getM_Product("Contract_Cancel_Charge");
		setProductPrice(IHelper.DEFAULT_PricingSystemValue, getCurrencyCode(), getCountryCode(),
				"Contract_Cancel_Charge", new BigDecimal("4"), true);

		change.setM_Product_ID(chargeProduct.getM_Product_ID());
		gridWindowHelper.save();

		Services.get(IDocActionBL.class).processEx(trans, X_C_Flatrate_Transition.DOCACTION_Complete, X_C_Flatrate_Transition.DOCSTATUS_Completed);

		InterfaceWrapperHelper.refresh(trans);
		assertThat(trans.getDocStatus(), equalTo(X_C_Flatrate_Transition.DOCSTATUS_Completed));

		return trans;
	}

	public I_C_Flatrate_Term createSubscriptionTerm(
			final AIntegrationTestDriver driver,
			final I_C_Flatrate_Conditions conditions)
	{
		assertThat(conditions.getType_Conditions(), equalTo(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription));

		final GridWindowHelper gridWindowHelper = mkGridWindowHelper();
		final ContractsTestConfig testConfig = getContractsTestConfig();

		final I_C_Flatrate_Term term = createTermCommonBegin(conditions, gridWindowHelper, testConfig);

		term.setDeliveryRule(X_C_Flatrate_Term.DELIVERYRULE_Availability);
		term.setDeliveryViaRule(X_C_Flatrate_Term.DELIVERYVIARULE_Shipper);

		final I_M_Product productUnderContract = getM_Product(testConfig.getM_Product_Matching_Subcr_Value());
		term.setM_Product_ID(productUnderContract.getM_Product_ID());

		term.setPlannedQtyPerUnit(BigDecimal.ONE);

		createTermCommonEnd(driver, gridWindowHelper, term);

		return term;
	}

	public I_C_Flatrate_Term createHoldingFeeTerm(
			final AIntegrationTestDriver driver,
			final I_C_Flatrate_Conditions conditions)
	{
		assertThat(conditions.getType_Conditions(), equalTo(X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee));

		final GridWindowHelper gridWindowHelper = mkGridWindowHelper();
		final TestConfig testConfig = getConfig();

		final I_C_Flatrate_Term term = createTermCommonBegin(conditions, gridWindowHelper, testConfig);

		createTermCommonEnd(driver, gridWindowHelper, term);

		return term;
	}

	public I_C_Flatrate_Term createFlatFeeTerm(
			final AIntegrationTestDriver driver,
			final I_C_Flatrate_Conditions conditions)
	{
		assertThat(conditions.getType_Conditions(), equalTo(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee));

		final GridWindowHelper gridWindowHelper = mkGridWindowHelper();
		final TestConfig testConfig = getConfig();

		final I_C_Flatrate_Term term = createTermCommonBegin(conditions, gridWindowHelper, testConfig);

		term.setPlannedQtyPerUnit(testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT));

		createTermCommonEnd(driver, gridWindowHelper, term);

		return term;
	}

	private I_C_Flatrate_Term createTermCommonBegin(
			final I_C_Flatrate_Conditions conditions,
			final GridWindowHelper gridWindowHelper,
			final TestConfig testConfig)
	{
		final I_C_Flatrate_Data data = gridWindowHelper.openTabForTable(I_C_Flatrate_Data.Table_Name, true)
				.newRecord()
				.getGridTabInterface(I_C_Flatrate_Data.class);

		final I_C_BPartner bp = mkBPartnerHelper().getC_BPartner(testConfig);
		assertTrue("Expecting " + bp + " to be a customer", bp.isCustomer());

		data.setC_BPartner_ID(bp.getC_BPartner_ID());

		gridWindowHelper.save();

		final I_C_Flatrate_Term term =
				gridWindowHelper
						.selectTab(I_C_Flatrate_Term.Table_Name)
						.newRecord()
						.getGridTabInterface(I_C_Flatrate_Term.class);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());

		term.setStartDate(getToday());

		final MBPartnerLocation[] bPartnerLocs = MBPartnerLocation.getForBPartner(getCtx(), bp.getC_BPartner_ID());
		assertThat("Expecting " + bp + " to have at least one location", bPartnerLocs.length, greaterThan(0));

		term.setBill_BPartner_ID(bp.getC_BPartner_ID());
		term.setBill_Location_ID(bPartnerLocs[0].getC_BPartner_Location_ID());

		term.setDropShip_BPartner_ID(bp.getC_BPartner_ID());
		term.setDropShip_Location_ID(bPartnerLocs[0].getC_BPartner_Location_ID());

		// hardcoded. is this ok?
		term.setC_Currency_ID(318);

		final boolean isSimulation =
				testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION);
		term.setIsSimulation(isSimulation);

		term.setAD_User_InCharge_ID(testConfig.getAD_User_Normal_ID());

		return term;
	}

	private void createTermCommonEnd(final AIntegrationTestDriver driver, final GridWindowHelper gridWindowHelper, final I_C_Flatrate_Term term)
	{
		final I_C_Currency currency = Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(getCtx(), driver.getHelper().getCurrencyCode());
		term.setC_Currency_ID(currency.getC_Currency_ID());

		gridWindowHelper.save();
		gridWindowHelper.refresh();

		driver.fireTestEvent(EventType.FLATRATE_TERM_CREATE_AFTER, term);

		term.setDocAction(X_C_Flatrate_Term.DOCACTION_Complete);
		gridWindowHelper.runProcess(I_C_Flatrate_Term.COLUMNNAME_DocAction);

		assertThat("Expected correct docStatus",
				term.getDocStatus(), equalTo(X_C_Flatrate_Term.DOCSTATUS_Completed));

		driver.fireTestEvent(EventType.FLATRATE_TERM_COMPLETE_AFTER, term);
	}

	public I_C_Flatrate_Conditions createSubscriptionContract(final AIntegrationTestDriver driver, final I_C_Flatrate_Transition transition)
	{
		final GridWindowHelper gridWindowHelper = mkGridWindowHelper();
		final I_C_Flatrate_Conditions conditions = createConditionsCommonBegin(driver, gridWindowHelper, transition);

		final ContractsTestConfig testConfig = getContractsTestConfig();

		// this is the part specific to subscription
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Subscription);

		createConditionsCommonEnd(driver, gridWindowHelper, conditions,
				testConfig.getM_Product_Matching_Subcr_Value(),
				testConfig.getM_Product_Matching_Subcr_IsStocked());

		return conditions;
	}

	public I_C_Flatrate_Conditions createHoldingFeeContract(final AIntegrationTestDriver driver, final I_C_Flatrate_Transition transition)
	{
		final GridWindowHelper gridWindowHelper = mkGridWindowHelper();
		final I_C_Flatrate_Conditions conditions = createConditionsCommonBegin(driver, gridWindowHelper, transition);

		createCommonForNonSubscription(conditions);

		// this is the part specific to holding fee
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee);

		final ContractsTestConfig testConfig = getContractsTestConfig();

		createConditionsCommonEnd(driver, gridWindowHelper, conditions,
				testConfig.getM_Product_Matching_Holdingfee_Value(),
				testConfig.getM_Product_Matching_Holdingfee_IsStocked());

		return conditions;
	}

	public I_C_Flatrate_Conditions createFlatFeeContract(final AIntegrationTestDriver driver, final I_C_Flatrate_Transition transition)
	{
		final GridWindowHelper gridWindowHelper = mkGridWindowHelper();
		final I_C_Flatrate_Conditions conditions = createConditionsCommonBegin(driver, gridWindowHelper, transition);

		createCommonForNonSubscription(conditions);

		// this is the part specific to flat fee
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee);

		final ContractsTestConfig testConfig = getContractsTestConfig();
		conditions.setUOMType(testConfig.getFlatrateUOMType());
		conditions.setC_UOM_ID(MUOM.get(getCtx(), testConfig.getC_UOM_Flatrate_Name(), getTrxName()).get_ID());

		conditions.setIsClosingWithActualSum(true);

		conditions.setType_Flatrate(X_C_Flatrate_Conditions.TYPE_FLATRATE_Corridor_Percent);
		conditions.setMargin_Max(new BigDecimal("5"));
		conditions.setMargin_Min(new BigDecimal("5"));

		conditions.setType_Clearing(testConfig.getCustomParamStr(FlatFeeScenario.PARAM_STR_TYPE_CLEARING));

		final String productValueActual = testConfig.getCustomParamStr(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE);
		final I_M_Product flatrateActualProduct = getM_Product(productValueActual);

		if (flatrateActualProduct.getM_Product_ID() != conditions.getM_Product_Flatrate_ID())
		{
			// 'flatrateActualProduct' is a product of its own, so we need to set its price
			setProductPrice(
					IHelper.DEFAULT_PricingSystemValue, getCurrencyCode(), getCountryCode(),
					flatrateActualProduct.getValue(), new BigDecimal("12"), true);
		}
		conditions.setM_Product_Actual_ID(flatrateActualProduct.getM_Product_ID());

		conditions.setIsClosingWithCorrectionSum(true);

		final I_M_Product flatrateCorrectionProduct = getM_Product();
		setProductPrice(
				IHelper.DEFAULT_PricingSystemValue, getCurrencyCode(), getCountryCode(),
				flatrateCorrectionProduct.getValue(),
				testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING), true);

		conditions.setM_Product_Correction_ID(flatrateCorrectionProduct.getM_Product_ID());

		conditions.setIsCorrectionAmtAtClosing(
				testConfig.getCustomParamBool(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING));

		createConditionsCommonEnd(driver, gridWindowHelper, conditions,
				testConfig.getM_Product_Matching_Flatfee_Value(),
				testConfig.getM_Product_Matching_Flatfee_IsStocked());

		return conditions;
	}

	private I_C_Flatrate_Conditions createConditionsCommonBegin(
			final AIntegrationTestDriver driver,
			final GridWindowHelper gridWindowHelper,
			final I_C_Flatrate_Transition transition)
	{
		final I_C_Flatrate_Conditions conditions = gridWindowHelper.openTabForTable(I_C_Flatrate_Conditions.Table_Name, true)
				.newRecord()
				.getGridTabInterface(I_C_Flatrate_Conditions.class);

		final TestConfig testConfig = getConfig();

		conditions.setName(testConfig.getPrefix() + "_" + driver.getHelper().getNow());

		conditions.setC_Flatrate_Transition_ID(transition.getC_Flatrate_Transition_ID());

		final I_M_PricingSystem ps = getM_PricingSystem(IHelper.DEFAULT_PricingSystemValue);
		conditions.setM_PricingSystem_ID(ps.getM_PricingSystem_ID());

		return conditions;
	}

	private void createCommonForNonSubscription(final I_C_Flatrate_Conditions conditions)
	{
		final TestConfig testConfig = getConfig();

		final I_M_Product flatrateProduct =
				getM_Product(testConfig.getCustomParamStr(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE));

		conditions.setM_Product_Flatrate_ID(flatrateProduct.getM_Product_ID());
		setProductPrice(
				IHelper.DEFAULT_PricingSystemValue, getCurrencyCode(), getCountryCode(),
				flatrateProduct.getValue(),
				testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT), true);
	}

	private void createConditionsCommonEnd(
			final AIntegrationTestDriver driver,
			final GridWindowHelper gridWindowHelper,
			final I_C_Flatrate_Conditions conditions,
			final String productValue, final boolean productIsStocked)
	{
		gridWindowHelper.validateLookups().save();

		final I_C_Flatrate_Matching matching = gridWindowHelper
				.selectTab(I_C_Flatrate_Matching.Table_Name)
				.newRecord()
				.getGridTabInterface(I_C_Flatrate_Matching.class);

		final I_M_Product productUnderContract = getM_Product(productValue);
		productUnderContract.setIsStocked(productIsStocked);
		InterfaceWrapperHelper.save(productUnderContract);

		// 'productUnderContract' is is the subscription product, so it needs to have a price
		setProductPrice(
				IHelper.DEFAULT_PricingSystemValue, getCurrencyCode(), getCountryCode(),
				productUnderContract.getValue(), new BigDecimal("12"), true);

		matching.setM_Product_Category_Matching_ID(productUnderContract.getM_Product_Category_ID());
		matching.setM_Product_ID(productUnderContract.getM_Product_ID());

		gridWindowHelper.validateLookups().save();

		final int contractIdBkp = conditions.getC_Flatrate_Conditions_ID();
		gridWindowHelper.selectTab(I_C_Flatrate_Conditions.Table_Name);
		gridWindowHelper.selectRecordById(contractIdBkp);

		conditions.setDocAction(X_C_Flatrate_Conditions.DOCACTION_Complete);

		gridWindowHelper.runProcess(I_C_Flatrate_Conditions.COLUMNNAME_DocAction);

		assertThat("Expected " + conditions + " co be completed", conditions.getDocStatus(), equalTo(X_C_Flatrate_Conditions.DOCSTATUS_Completed));
	}

	private int retrieveCalendarId(final String calendarName)
	{
		final int calendarClosingId =
				new Query(getCtx(), I_C_Calendar.Table_Name, I_C_Calendar.COLUMNNAME_Name + "=?", getTrxName())
						.setParameters(calendarName)
						.setApplyAccessFilter(true)
						.firstIdOnly();
		return calendarClosingId;
	}

	public List<I_C_Invoice_Candidate> completeAllInvoicingEntries(final I_C_Flatrate_Term term, final BigDecimal qtyReported)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final List<I_C_Invoice_Candidate> candidates = new ArrayList<I_C_Invoice_Candidate>();

		final List<I_C_Flatrate_DataEntry> entriestoComplete =
				flatrateDB.retrieveDataEntries(term, null, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, false);
		assertThat(entriestoComplete.size(), greaterThan(0));

		for (final I_C_Flatrate_DataEntry entry : entriestoComplete)
		{
			if (X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(entry.getDocStatus()))
			{
				continue;
			}

			final GridWindowHelper entryGridWindowHelper = retrieveEntryGridWindowHelper(entry);
			final I_C_Flatrate_DataEntry entryGrid = entryGridWindowHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);
			entryGrid.setQty_Reported(qtyReported);
			entryGridWindowHelper.save();

			completeEntry(entryGridWindowHelper, X_C_Flatrate_DataEntry.DOCSTATUS_Completed);

			if (entryGrid.getC_Invoice_Candidate_ID() > 0)
			{
				candidates.add(entryGrid.getC_Invoice_Candidate());
			}
			if (entryGrid.getC_Invoice_Candidate_Corr_ID() > 0)
			{
				candidates.add(entryGrid.getC_Invoice_Candidate_Corr());
			}
		}
		return candidates;
	}

	public void completeEntry(final GridWindowHelper gridWindowHelper, final String expectedDocStatus)
	{
		final I_C_Flatrate_DataEntry gridWindowEntry = gridWindowHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);

		gridWindowEntry.setDocAction(X_C_Flatrate_DataEntry.DOCACTION_Complete);
		gridWindowHelper.save();

		gridWindowHelper.runProcess(I_C_Flatrate_DataEntry.COLUMNNAME_DocAction);

		assertThat("dataEntry " + gridWindowEntry.getC_Flatrate_DataEntry_ID() + " has wrong DocStatus",
				gridWindowEntry.getDocStatus(), equalTo(expectedDocStatus));
	}

	public void reactivateEntry(final GridWindowHelper gridWindowHelper)
	{
		final I_C_Flatrate_DataEntry gridWindowEntry = gridWindowHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);

		gridWindowEntry.setDocAction(X_C_Flatrate_DataEntry.DOCACTION_Re_Activate);
		gridWindowHelper.save();

		gridWindowHelper.runProcess(I_C_Flatrate_DataEntry.COLUMNNAME_DocAction);

		assertThat("dataEntry " + gridWindowEntry.getC_Flatrate_DataEntry_ID() + " has wrong DocStatus",
				gridWindowEntry.getDocStatus(), equalTo(X_C_Flatrate_DataEntry.DOCSTATUS_InProgress));
	}

	public GridWindowHelper retrieveMainInvoicingEntry(final I_C_Flatrate_Conditions fc, final I_C_Order order)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final List<I_C_Flatrate_DataEntry> dataEntries =
				flatrateDB.retrieveDataEntries(fc, order.getDateOrdered(), X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, fc.getC_UOM(), false);
		assertThat(dataEntries.size(), equalTo(1));

		final I_C_Flatrate_DataEntry entry = dataEntries.get(0);

		return retrieveEntryGridWindowHelper(entry);
	}

	public GridWindowHelper retrieveMainCorrectionEntry(final I_C_Flatrate_Conditions fc, final I_C_Flatrate_Term term)
	{
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		final List<I_C_Flatrate_DataEntry> correctionEntries =
				flatrateDB.retrieveDataEntries(term, X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased, fc.getC_UOM());

		assertThat("Wrong number of main correction entries for " + term,
				correctionEntries.size(), equalTo(1));

		final I_C_Flatrate_DataEntry correctionEntry = correctionEntries.get(0);

		return retrieveEntryGridWindowHelper(correctionEntry);
	}

	public GridWindowHelper retrieveEntryGridWindowHelper(final I_C_Flatrate_DataEntry entry)
	{
		final I_C_Flatrate_Term term = entry.getC_Flatrate_Term();

		final GridWindowHelper helper = mkGridWindowHelper()
				.openFor(term.getC_Flatrate_Data())
				.selectTab(I_C_Flatrate_Term.Table_Name)
				.selectRecordById(term.getC_Flatrate_Term_ID())
				.selectTab(I_C_Flatrate_DataEntry.Table_Name, entry.getType())
				.selectRecordById(entry.getC_Flatrate_DataEntry_ID());

		return helper;
	}

	public GridWindowHelper retrievTermGridWindowHelper(final I_C_Flatrate_Term term)
	{
		final GridWindowHelper helper = mkGridWindowHelper()
				.openFor(term.getC_Flatrate_Data())
				.selectTab(I_C_Flatrate_Term.Table_Name)
				.selectRecordById(term.getC_Flatrate_Term_ID());

		return helper;
	}

}

package test.integration.contracts.subscription;

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
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.util.time.TimeSource;
import org.compiere.model.MBPartnerLocation;
import org.compiere.model.MUOM;
import org.compiere.model.Query;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_C_Order;
import org.compiere.process.DocAction;
import org.compiere.util.TimeUtil;
import org.junit.Assume;
import org.junit.Test;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.OrderHelper;
import de.metas.adempiere.ait.helper.TestConfig;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;
import de.metas.adempiere.model.I_C_Order;
import de.metas.adempiere.model.I_M_Product;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import de.metas.currency.ICurrencyDAO;
import de.metas.flatrate.Contracts_Constants;
import de.metas.flatrate.api.IContractsDAO;
import de.metas.flatrate.exceptions.SubscriptionChangeException;
import de.metas.flatrate.interfaces.I_C_OLCand;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Data;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_Contract_Change;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.flatrate.process.C_Flatrate_Term_Create_From_OLCand;
import de.metas.flatrate.process.C_Flatrate_Term_Extend;
import de.metas.flatrate.process.C_Flatrate_Term_Change;
import de.metas.impex.api.IInputDataSourceDAO;
import de.metas.impex.model.I_AD_InputDataSource;
import de.metas.inoutcandidate.api.IInOutCandidateConfig;
import de.metas.interfaces.I_C_BPartner;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.process.C_Invoice_Candidate_Update;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.OrderCandidate_Constants;
import test.integration.contracts.ContractsHelper;
import test.integration.contracts.ContractsOrderLineHelper;
import test.integration.contracts.ContractsTestConfig;
import test.integration.contracts.flatrate.FlatFeeScenario;

public class SubscriptionTestDriver extends AIntegrationTestDriver
{
	@Override
	public IHelper newHelper()
	{
		return new ContractsHelper(new Helper());
	}

	@IntegrationTest(
			tasks = "03152",
			desc = "sets up a running subscription from an order line")
	@Test
	public void orderCreatesTerm()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_Subscr_Disabled());

		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final ContractsTestConfig testConfig = helper.getContractsTestConfig();
		testConfig.setPrefix("SubscrOdr1st");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrOdr1st_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("3"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, true);

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);

		final OrderHelper orderHelper = helper.mkOrderHelper();

		//
		// create an order line with our subscription conditions
		// @formatter:off
		final I_C_Order order = orderHelper
				.setDocSubType(X_C_DocType.DOCSUBTYPE_StandardOrder)
				.setBPartnerName(testConfig.getC_BPartner_Value())
				.setInvoiceRule(OrderHelper.Order_InvoiceRule.AFTER_DELIVERY)
				.setComplete(DocAction.STATUS_Completed)
				.addLine(ContractsOrderLineHelper.class)
				.setContractConditions(conditions)
				.setProductValue(testConfig.getM_Product_Matching_Subcr_Value())
				.setQty(testConfig.getCustomParamBD(FlatFeeScenario.PARAM_BD_ACTUAL_QTY))
				.setPriceList(new BigDecimal("99")) // note: the price doesn't really matter, so I set it to 99
				.setPriceActual(new BigDecimal("99"))
				.endLine()
				.createOrder();
		// @formatter:on

		checkSubscriptionOrder(order);

		final I_C_OrderLine ol =
				new Query(getCtx(), I_C_OrderLine.Table_Name, I_C_OrderLine.COLUMNNAME_C_Order_ID + "=?", null)
						.setParameters(order.getC_Order_ID())
						.firstOnly(I_C_OrderLine.class);

		//
		// make sure that the system created and completed a subscription term
		final I_C_Flatrate_Term term =
				new Query(getCtx(), I_C_Flatrate_Term.Table_Name, I_C_Flatrate_Term.COLUMNNAME_C_OrderLine_Term_ID + "=?", null)
						.setParameters(ol.getC_OrderLine_ID())
						.firstOnly(I_C_Flatrate_Term.class);

		checkNewSubscriptionTerm(conditions, ft, term);

		checkSubscriptionOrderLine(ol, term);
	}

	@IntegrationTest(tasks = "03152",
			desc = "creates a running subscription and makes sure that an order line is created")
	@Test
	public void termCreatesOrder()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_Subscr_Disabled());

		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("SubscrTrm1st");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrTrm1st_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, true);

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);
		final I_C_Flatrate_Term term = helper.createSubscriptionTerm(this, conditions);

		checkNewSubscriptionTerm(conditions, ft, term);

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(term.getC_OrderLine_Term(), I_C_OrderLine.class);
		checkSubscriptionOrderLine(ol, term);

		final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
		checkSubscriptionOrder(order);

		// update invoice candidates and make sure no one was created for 'term'
		helper.mkProcessHelper().setProcessClass(C_Invoice_Candidate_Update.class).run();

		final List<I_C_Invoice_Candidate> candsForTerm = Services.get(IInvoiceCandDAO.class).retrieveReferencing(term);
		assertTrue(candsForTerm.isEmpty());
	}

	/**
	 * This method create a {@link I_C_Flatrate_Conditions} with <code>IsNewTermCreatesOrder='N'</code> and amkes sur that the new term doesn't create an order.
	 */
	@IntegrationTest(tasks = "03152",
			desc = "creates a running subscription and makes sure that *no* order line is created")
	@Test
	public void termDoesNotCreateOrder()
	{
		Assume.assumeTrue(Services.get(IInOutCandidateConfig.class).isSupportForSchedsWithoutOrderLine());

		// prepare
		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("SubscrTrmNoOrder");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrTrm1st_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, false);

		// create the term
		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);
		final I_C_Flatrate_Term term = helper.createSubscriptionTerm(this, conditions);

		checkNewSubscriptionTerm(conditions, ft, term);

		assertThat(term.getC_OrderLine_Term_ID(), equalTo(0));

		// create invoice candidates
		helper.mkProcessHelper().setProcessClass(C_Invoice_Candidate_Update.class).run();

		final List<I_C_Invoice_Candidate> candsForTerm = Services.get(IInvoiceCandDAO.class).retrieveReferencing(term);
		assertThat(candsForTerm.size(), equalTo(1));
	}

	/**
	 * This test does the following
	 * <ul>
	 * <li>Create a {@link I_C_Flatrate_Conditions} record
	 * <li>Create a {@link I_C_OLCand} record with the "contract" destination and the previously created conditions.
	 * <li>process the {@link I_C_OLCand} into a {@link I_C_Flatrate_Term} (bypassing C_OrderLine!)
	 * </ul>
	 */
	@IntegrationTest(tasks = "03660",
			desc = "Creates a running subscription from an C_OLCand and creates a C_Invoice_Cand from that subscription")
	@Test
	public void createTermFromOLCand()
	{
		Assume.assumeTrue(Services.get(IInOutCandidateConfig.class).isSupportForSchedsWithoutOrderLine());

		// prepare
		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final ContractsTestConfig testConfig = helper.getContractsTestConfig();
		testConfig.setPrefix("createTermFromOLCand");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("createTermFromOLCand_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, false);

		// create the conditions
		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);

		// create the C_OLCand
		// we should provide an oncand-helper..
		final GridWindowHelper gridWindowHelper = helper.mkGridWindowHelper().openTabForTable(I_C_OLCand.Table_Name, true);

		final I_C_OLCand olCand = gridWindowHelper
				.newRecord()
				.getGridTabInterface(I_C_OLCand.class);

		final I_M_Product prod = helper.getM_Product(testConfig.getM_Product_Matching_Subcr_Value());
		olCand.setM_Product_ID(prod.getM_Product_ID());
		olCand.setM_PricingSystem_ID(conditions.getM_PricingSystem_ID());

		final IInputDataSourceDAO inputDataSourceDAO = Services.get(IInputDataSourceDAO.class);

		final I_AD_InputDataSource dest = inputDataSourceDAO.retrieveInputDataSource(getCtx(), Contracts_Constants.DATA_DESTINATION_INTERNAL_NAME, true, getTrxName());
		olCand.setAD_DataDestination_ID(dest.getAD_InputDataSource_ID());

		final I_AD_InputDataSource source = inputDataSourceDAO.retrieveInputDataSource(getCtx(), OrderCandidate_Constants.DATA_DESTINATION_INTERNAL_NAME, true, getTrxName());
		olCand.setAD_InputDataSource_ID(source.getAD_InputDataSource_ID());

		final I_C_BPartner bp = helper.mkBPartnerHelper().getC_BPartner(testConfig);
		assertTrue("Expecting " + bp + " to be a customer", bp.isCustomer());

		final MBPartnerLocation[] bPartnerLocs = MBPartnerLocation.getForBPartner(getCtx(), bp.getC_BPartner_ID());
		assertThat("Expecting " + bp + " to have at least one location", bPartnerLocs.length, greaterThan(0));

		olCand.setAD_User_EnteredBy_ID(testConfig.getAD_User_Normal_ID());

		olCand.setC_BPartner_ID(bp.getC_BPartner_ID());
		olCand.setC_BPartner_Location_ID(bPartnerLocs[0].getC_BPartner_Location_ID());

		olCand.setBill_BPartner_ID(bp.getC_BPartner_ID());
		olCand.setBill_Location_ID(bPartnerLocs[0].getC_BPartner_Location_ID());

		olCand.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		olCand.setQty(BigDecimal.ONE);

		olCand.setC_Currency_ID(Services.get(ICurrencyDAO.class).retrieveCurrencyByISOCode(getCtx(), helper.getCurrencyCode()).getC_Currency_ID());

		olCand.setC_UOM_ID(MUOM.getDefault_UOM_ID(getCtx()));

		olCand.setDateCandidate(helper.getToday());

		gridWindowHelper
				.validateLookups()
				.save();

		// do the actual invocations and testing
		helper.mkProcessHelper()
				.setProcessClass(C_Flatrate_Term_Create_From_OLCand.class)
				.run();

		gridWindowHelper.refresh();

		assertThat(olCand.getErrorMsg(), olCand.isError(), is(false));
		assertThat(olCand.isProcessed(), is(true));

		// get the newly created term

		final List<I_C_Flatrate_Term> terms = Services.get(ISubscriptionDAO.class).retrieveTermsForOLCand(olCand);
		assertThat(terms.size(), equalTo(1));
		final I_C_Flatrate_Term term = terms.get(0);

		checkNewSubscriptionTerm(conditions, ft, term);

		assertThat(term.getC_OrderLine_Term_ID(), equalTo(0));

		// create invoice candidates
		helper.mkProcessHelper().setProcessClass(C_Invoice_Candidate_Update.class).run();

		final List<I_C_Invoice_Candidate> candsForTerm = Services.get(IInvoiceCandDAO.class).retrieveReferencing(term);
		assertThat(candsForTerm.size(), equalTo(1));
	}

	@IntegrationTest(tasks = "03204",
			desc = "Creates a running subscription that would be extended automatically and renews it using the 'extend now' button")
	@Test
	public void manualRenewSubscription()
	{
		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("SubscrManualRenew");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrManualRenew(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, true);

		// explicitly setting the deadline params here, because we wan to have a 'changeDate' that is before the
		// deadline
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE, 4);
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);
		final I_C_Flatrate_Term term = helper.createSubscriptionTerm(this, conditions);

		checkNewSubscriptionTerm(conditions, ft, term);
		assertThat(term.isAutoRenew(), is(true)); // guard, otherwise this test doesn't make sense

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(term.getC_OrderLine_Term(), I_C_OrderLine.class);
		checkSubscriptionOrderLine(ol, term);

		final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
		checkSubscriptionOrder(order);

		// now open the window, navigate to the term and hit the button
		helper.mkGridWindowHelper()
				.openTabForTable(I_C_Flatrate_Data.Table_Name, true)
				.selectRecordById(term.getC_Flatrate_Data_ID())
				.selectTab(I_C_Flatrate_Term.Table_Name)
				.selectRecordById(term.getC_Flatrate_Term_ID())
				.runProcess(I_C_Flatrate_Term.COLUMNNAME_ExtendTerm);

		InterfaceWrapperHelper.refresh(term);

		final I_C_Flatrate_Term nextFlatrateTerm = term.getC_FlatrateTerm_Next();

		checkNextFlatrateTerm(nextFlatrateTerm, term);

		assertThat(term + " should NOT have a C_OrderLine_TermChange", term.getC_OrderLine_TermChange_ID(), is(0));
	}

	@IntegrationTest(tasks = "03204",
			desc = "Creates a running subscription that would be extended automatically and renews it using the process button")
	@Test
	public void processRenewSubscription()
	{
		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("SubscrProcessRenew");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrProcessRenew(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, true);

		// explicitly setting the deadline params here, because we wan to have a 'changeDate' that is before the
		// deadline
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE, 4);
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);
		final I_C_Flatrate_Term term = helper.createSubscriptionTerm(this, conditions);

		checkNewSubscriptionTerm(conditions, ft, term);
		assertThat(term.isAutoRenew(), is(true)); // guard, otherwise this test doesn't make sense

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(term.getC_OrderLine_Term(), I_C_OrderLine.class);
		checkSubscriptionOrderLine(ol, term);

		final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
		checkSubscriptionOrder(order);

		// set the system time to the term's NoticeDate + 1 day
		// this way, we can expect our process to extend the term
		final Timestamp testDate = TimeUtil.addDays(term.getNoticeDate(), +1);
		SystemTime.setTimeSource(new TimeSource()
		{
			@Override
			public long millis()
			{
				return testDate.getTime();
			}
		});

		// now run the process
		helper.mkProcessHelper()
				.setProcessClass(C_Flatrate_Term_Extend.class)
				.setParameter(I_C_Flatrate_Transition.COLUMNNAME_IsAutoCompleteNewTerm, true)
				.run();

		// restore the current time
		SystemTime.resetTimeSource();

		InterfaceWrapperHelper.refresh(term);
		assertThat(term + " should NOT have a C_OrderLine_TermChange", term.getC_OrderLine_TermChange_ID(), is(0));

		final I_C_Flatrate_Term nextFlatrateTerm = term.getC_FlatrateTerm_Next();
		checkNextFlatrateTerm(nextFlatrateTerm, term);

		checkNewSubscriptionTerm(nextFlatrateTerm.getC_Flatrate_Conditions(), nextFlatrateTerm.getC_Flatrate_Transition(), nextFlatrateTerm);
	}

	private void checkNextFlatrateTerm(final I_C_Flatrate_Term nextTerm, final I_C_Flatrate_Term originalTerm)
	{
		// check it 'nextTerm' is consistent with 'originalTerm' and the settings
		assertThat(nextTerm, notNullValue());
		assertThat("AD_Client_ID should be equal", nextTerm.getAD_Client_ID(), equalTo(originalTerm.getAD_Client_ID()));
		assertThat("AD_Org_ID should be equal", nextTerm.getAD_Org_ID(), equalTo(originalTerm.getAD_Org_ID()));
		assertThat("AD_User_InCharge_ID should be equal", nextTerm.getAD_User_InCharge_ID(), equalTo(originalTerm.getAD_User_InCharge_ID()));
		assertThat("Bill_BPartner_ID should be equal", nextTerm.getBill_BPartner_ID(), equalTo(originalTerm.getBill_BPartner_ID()));
		assertThat("Bill_Location_ID should be equal", nextTerm.getBill_Location_ID(), equalTo(originalTerm.getBill_Location_ID()));
		assertThat("Bill_User_ID should be equal", nextTerm.getBill_User_ID(), equalTo(originalTerm.getBill_User_ID()));
		assertThat("C_Currency_ID should be equal", nextTerm.getC_Currency_ID(), equalTo(originalTerm.getC_Currency_ID()));
		assertThat("C_Flatrate_Data_ID should be equal", nextTerm.getC_Flatrate_Data_ID(), equalTo(originalTerm.getC_Flatrate_Data_ID()));
		assertThat(nextTerm.getC_FlatrateTerm_Next_ID(), equalTo(0));
		assertThat("C_UOM_ID should be equal", nextTerm.getC_UOM_ID(), equalTo(originalTerm.getC_UOM_ID()));
		assertThat("DeliveryRule should be equal", nextTerm.getDeliveryRule(), equalTo(originalTerm.getDeliveryRule()));
		assertThat("DeliveryViaRule should be equal", nextTerm.getDeliveryViaRule(), equalTo(originalTerm.getDeliveryViaRule()));
		assertThat("DropShip_BPartner_ID should be equal", nextTerm.getDropShip_BPartner_ID(), equalTo(originalTerm.getDropShip_BPartner_ID()));
		assertThat("DropShip_Location_ID should be equal", nextTerm.getDropShip_Location_ID(), equalTo(originalTerm.getDropShip_Location_ID()));
		assertThat("DropShip_User_ID should be equal", nextTerm.getDropShip_User_ID(), equalTo(originalTerm.getDropShip_User_ID()));
		assertThat("M_PricingSystem_ID should be equal", nextTerm.getM_PricingSystem_ID(), equalTo(originalTerm.getM_PricingSystem_ID()));
		assertThat("M_Product_ID should be equal", nextTerm.getM_Product_ID(), equalTo(originalTerm.getM_Product_ID()));
		assertThat("PlannedQtyPerUnit should be equal", nextTerm.getPlannedQtyPerUnit(), comparesEqualTo(originalTerm.getPlannedQtyPerUnit()));
		assertThat("PriceActual should be equal", nextTerm.getPriceActual(), comparesEqualTo(originalTerm.getPriceActual()));

		if (originalTerm.getC_Flatrate_Transition().getC_Flatrate_Conditions_Next_ID() > 0)
		{
			assertThat("'nextFlatrateTerm' should have the conditions as set by the transition rule of 'term'",
					nextTerm.getC_Flatrate_Conditions_ID(), equalTo(originalTerm.getC_Flatrate_Transition().getC_Flatrate_Conditions_Next_ID()));
		}
		else
		{
			assertThat("'nextFlatrateTerm' should have the same conditions as 'term'",
					nextTerm.getC_Flatrate_Conditions_ID(), equalTo(originalTerm.getC_Flatrate_Conditions_ID()));
		}

		assertThat("'nextFlatrateTerm' should have the transition rule as set by its conditions",
				nextTerm.getC_Flatrate_Transition_ID(), equalTo(nextTerm.getC_Flatrate_Conditions().getC_Flatrate_Transition_ID()));
	}

	@IntegrationTest(tasks = "03152",
			desc = "Creates a running subscription that would be extended automatically and cancels it with EventDate=<end of the term>")
	@Test
	public void cancelSubscriptionRenew()
	{
		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("SubscrCancelRenew");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrCancelRenew_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, true);

		// explicitly setting the deadline params here, because we wan to have a 'changeDate' that is before the
		// deadline
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE, 4);
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);
		final I_C_Flatrate_Term term = helper.createSubscriptionTerm(this, conditions);

		checkNewSubscriptionTerm(conditions, ft, term);
		assertThat("*before* the cancel, " + term + " needs to have AutoRenew=true", term.isAutoRenew(), is(true));

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(term.getC_OrderLine_Term(), I_C_OrderLine.class);
		checkSubscriptionOrderLine(ol, term);

		final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
		checkSubscriptionOrder(order);

		// we expect the process to run successfully
		helper.mkProcessHelper().setProcessClass(C_Flatrate_Term_Change.class)
				.setRecordId(term.getC_Flatrate_Term_ID())
				.setParameter(C_Flatrate_Term_Change.PARAM_ACTION, C_Flatrate_Term_Change.ChangeTerm_ACTION_Cancel)
				.setParameter(C_Flatrate_Term_Change.PARAM_CHANGE_DATE, term.getEndDate())
				.run();

		InterfaceWrapperHelper.refresh(term);

		assertThat(term + " should now have AutoRenew=false", term.isAutoRenew(), is(false));
		assertThat(term + " should NOT have a C_OrderLine_TermChange", term.getC_OrderLine_TermChange_ID(), is(0));
	}

	@IntegrationTest(tasks = "03152",
			desc = "Creates a running and cancels early, but within the allowed deadline")
	@Test
	public void cancelSubscriptionWithinDeadLine()
	{
		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("SubscrCancelOK");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrCancelOK_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, true);

		// explicitly setting the duration and deadline params here, because we want to have
		// a 'changeDate' that is before the deadline
		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_TERM_DURATION, 6);
		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_TERM_DURATION_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE, 1);
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_DELIVERY_INTERVAL, 1);
		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_DELIVERY_INTERVAL_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);
		final I_C_Flatrate_Term term = helper.createSubscriptionTerm(this, conditions);

		checkNewSubscriptionTerm(conditions, ft, term);

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(term.getC_OrderLine_Term(), I_C_OrderLine.class);
		checkSubscriptionOrderLine(ol, term);

		final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
		checkSubscriptionOrder(order);

		final Timestamp cancelDate = TimeUtil.addMonths(term.getStartDate(), 2);

		// we expect the process to run successfully
		helper.mkProcessHelper().setProcessClass(C_Flatrate_Term_Change.class)
				.setRecordId(term.getC_Flatrate_Term_ID())
				.setParameter(C_Flatrate_Term_Change.PARAM_ACTION, C_Flatrate_Term_Change.ChangeTerm_ACTION_Cancel)
				.setParameter(C_Flatrate_Term_Change.PARAM_CHANGE_DATE, cancelDate)
				.run();

		InterfaceWrapperHelper.refresh(term);

		assertThat(term.isAutoRenew(), is(false));
		assertThat("After a early change, " + term + " should have C_OrderLine_TermChange_ID>0", term.getC_OrderLine_TermChange_ID(), greaterThan(0));
	}

	@IntegrationTest(tasks = "03152",
			desc = "Creates a running and cancels early, but within the allowed deadline")
	@Test
	public void cancelOrderlessSubscriptionWithinDeadLine()
	{
		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("SubscrCancelOKNoOrder");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrCancelOK_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, false);

		// explicitly setting the duration and deadline params here, because we want to have
		// a 'changeDate' that is before the deadline
		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_TERM_DURATION, 6);
		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_TERM_DURATION_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE, 1);
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_DELIVERY_INTERVAL, 1);
		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_DELIVERY_INTERVAL_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);
		final I_C_Flatrate_Term term = helper.createSubscriptionTerm(this, conditions);

		checkNewSubscriptionTerm(conditions, ft, term);

		assertThat(term.getC_OrderLine_Term_ID(), is(0));

		// update invoice candidates and make sure that our invoice candidate has been correctly created
		helper.mkProcessHelper().setProcessClass(C_Invoice_Candidate_Update.class).run();

		List<I_C_Invoice_Candidate> candsForTerm = Services.get(IInvoiceCandDAO.class).retrieveReferencing(term);
		assertThat(candsForTerm.size(), equalTo(1));

		final I_C_Invoice_Candidate candForTermBeforeCancel = candsForTerm.get(0);
		assertThat(candForTermBeforeCancel.getQtyOrdered(), comparesEqualTo(Services.get(IContractsDAO.class).retrieveSubscriptionProgressQtyForTerm(term)));
		assertThat(candForTermBeforeCancel.isProcessed(), is(false));

		// now cancel the contract
		final Timestamp cancelDate = TimeUtil.addMonths(term.getStartDate(), 2);

		// we expect the process to run successfully
		helper.mkProcessHelper().setProcessClass(C_Flatrate_Term_Change.class)
				.setRecordId(term.getC_Flatrate_Term_ID())
				.setParameter(C_Flatrate_Term_Change.PARAM_ACTION, C_Flatrate_Term_Change.ChangeTerm_ACTION_Cancel)
				.setParameter(C_Flatrate_Term_Change.PARAM_CHANGE_DATE, cancelDate)
				.run();

		InterfaceWrapperHelper.refresh(term);
		assertThat(term.isAutoRenew(), is(false));
		assertThat(term.getContractStatus(), equalTo(X_C_Flatrate_Term.CONTRACTSTATUS_Gekuendigt));
		assertThat("After a early change, " + term + " without order should have C_OrderLine_TermChange_ID==0", term.getC_OrderLine_TermChange_ID(), is(0));

		InterfaceWrapperHelper.refresh(candForTermBeforeCancel);
		assertThat(candForTermBeforeCancel.isToRecompute(), is(true));

		// update invoice candidates
		helper.mkProcessHelper().setProcessClass(C_Invoice_Candidate_Update.class).run();

		candsForTerm = Services.get(IInvoiceCandDAO.class).retrieveReferencing(term);
		assertThat(candsForTerm.size(), equalTo(1));

		final I_C_Invoice_Candidate candForTermAfterCancel = candsForTerm.get(0);
		InterfaceWrapperHelper.refresh(candForTermAfterCancel);
		assertThat(candForTermAfterCancel.isToRecompute(), is(false));
		assertThat(candForTermAfterCancel.getC_Invoice_Candidate_ID(), is(candForTermBeforeCancel.getC_Invoice_Candidate_ID()));
		assertThat(candForTermAfterCancel.getQtyOrdered(), comparesEqualTo(candForTermAfterCancel.getQtyInvoiced()));
		assertThat(candForTermAfterCancel.isProcessed(), is(true));
	}

	@IntegrationTest(tasks = "03152",
			desc = "Creates a running and tries to cancel after the deadline has passed")
	@Test
	public void cancelSubscriptionFail()
	{
		LogManager.setLevel(Level.WARN);
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("SubscrCancelFail");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("SubscrCancelFail(*)"));

		// explicitly setting the duration and deadline params here, because we want to have
		// a 'changeDate' that is before the deadline
		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_TERM_DURATION, 6);
		testConfig.setCustomParam(ContractsHelper.PARAM_TRANSITION_TERM_DURATION_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE, 4);
		testConfig.setCustomParam(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, true);

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createSubscriptionContract(this, ft);
		final I_C_Flatrate_Term term = helper.createSubscriptionTerm(this, conditions);

		checkNewSubscriptionTerm(conditions, ft, term);

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(term.getC_OrderLine_Term(), I_C_OrderLine.class);
		checkSubscriptionOrderLine(ol, term);

		final I_C_Order order = InterfaceWrapperHelper.create(ol.getC_Order(), I_C_Order.class);
		checkSubscriptionOrder(order);

		final Timestamp cancelDate = TimeUtil.addMonths(term.getStartDate(), 5);

		helper.mkProcessHelper().setProcessClass(C_Flatrate_Term_Change.class)
				.setRecordId(term.getC_Flatrate_Term_ID())
				.setParameter(C_Flatrate_Term_Change.PARAM_ACTION, C_Flatrate_Term_Change.ChangeTerm_ACTION_Cancel)
				.setParameter(C_Flatrate_Term_Change.PARAM_CHANGE_DATE, cancelDate)
				.setExpectedException(SubscriptionChangeException.class)
				.run();
	}

	/**
	 * Checks if the newly created term is consistent with the conditions and transition it is based on.
	 * 
	 * @param conditions
	 * @param trans
	 * @param term
	 */
	private void checkNewSubscriptionTerm(
			final I_C_Flatrate_Conditions conditions,
			final I_C_Flatrate_Transition trans,
			final I_C_Flatrate_Term term)
	{
		assertThat(term, notNullValue());

		if (term.isNewTermCreatesOrder())
		{
			assertThat("term has a C_OrderLine_Term_ID>0", term.getC_OrderLine_Term_ID(), greaterThan(0));
		}
		if (trans.isAutoCompleteNewTerm())
		{
			assertThat(term.getDocStatus(), equalTo(X_C_Flatrate_Term.DOCSTATUS_Completed));
		}
		assertThat(term.getC_Flatrate_Conditions_ID(), equalTo(conditions.getC_Flatrate_Conditions_ID()));
		assertThat(term.isAutoRenew(), equalTo(trans.isAutoRenew()));

		assertThat(term.getM_PricingSystem_ID(), greaterThan(0));

		final List<I_C_SubscriptionProgress> deliveries =
				new Query(getCtx(), I_C_SubscriptionProgress.Table_Name, I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID + "=?", getTrxName())
						.setParameters(term.getC_Flatrate_Term_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(I_C_SubscriptionProgress.COLUMNNAME_SeqNo)
						.list(I_C_SubscriptionProgress.class);

		assertThat(deliveries.size(), greaterThan(0));
		assertTrue("Expected last delivery to be before term end date",
				deliveries.get(deliveries.size() - 1).getEventDate().before(term.getEndDate()));
	}

	private void checkSubscriptionOrderLine(final I_C_OrderLine ol, final I_C_Flatrate_Term term)
	{
		assertThat(ol.getLineNetAmt(), greaterThan(BigDecimal.ZERO));
		assertThat("QtyEntered of " + ol + " is not equal to PlannedQtyPerUnit of " + term,
				ol.getQtyEntered(), comparesEqualTo(term.getPlannedQtyPerUnit()));

		final BigDecimal sum =
				new Query(getCtx(), I_C_SubscriptionProgress.Table_Name, I_C_SubscriptionProgress.COLUMNNAME_C_Flatrate_Term_ID + "=?", getTrxName())
						.setParameters(term.getC_Flatrate_Term_ID())
						.setOnlyActiveRecords(true)
						.setClient_ID()
						.setOrderBy(I_C_SubscriptionProgress.COLUMNNAME_SeqNo)
						.sum(I_C_SubscriptionProgress.COLUMNNAME_Qty);

		assertThat("QtyOrdered of " + ol + " is not equal to the SUM of delivery qtys of " + term,
				ol.getQtyOrdered(), comparesEqualTo(sum));

		assertThat("QtyReserved of " + ol + " is not equal to the SUM of delivery qtys of " + term,
				ol.getQtyReserved(), comparesEqualTo(sum));

	}

	/**
	 * 
	 * @param order
	 */
	private void checkSubscriptionOrder(final I_C_Order order)
	{
		assertThat(order + " has wrong DocStatus", order.getDocStatus(), equalTo(X_C_Order.DOCSTATUS_Completed));
		assertThat(order + " has wrong GrandTotal", order.getGrandTotal(), greaterThan(BigDecimal.ZERO));
	}
}

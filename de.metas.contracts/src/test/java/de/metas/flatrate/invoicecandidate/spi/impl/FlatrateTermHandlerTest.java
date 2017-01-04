package de.metas.flatrate.invoicecandidate.spi.impl;

/*
 * #%L
 * de.metas.contracts
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

import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.flatrate.ContractsTestBase;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_Transition;
import de.metas.invoicecandidate.InvoiceCandidatesTestHelper;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.IInvoiceCandidateHandler;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;
import mockit.Expectations;
import mockit.Mocked;

public class FlatrateTermHandlerTest extends ContractsTestBase
{

	// task 07442
	private I_AD_Org org;
	private I_C_Activity activity;

	@Mocked
	protected IProductAcctDAO productAcctDAO;
	@Mocked
	protected ITaxBL taxBL;

	// task 07442 end

	@BeforeClass
	public static void configure()
	{
		Adempiere.enableUnitTestMode();
		Check.setDefaultExClass(AdempiereException.class);
	}

	@Before
	public void before()
	{
		org = InterfaceWrapperHelper.newInstance(I_AD_Org.class, getContext());
		InterfaceWrapperHelper.save(org);

		activity = InterfaceWrapperHelper.newInstance(I_C_Activity.class, getContext());
		InterfaceWrapperHelper.save(activity);
	}

	@Test
	public void test_isCorrectDateForTerm_TermOfNotice_ThreeMonths_FirstDay()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class, getContext());
		transition.setTermDuration(1);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermOfNotice(3);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		term.setStartDate(TimeUtil.getDay(2013, 5, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermHandler handler = new FlatrateTermHandler();

		final boolean resultActual = handler.isCorrectDateForTerm(term);
		final boolean resultExpected = true;
		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test
	public void test_isCorrectDateForTerm_ThreeMonths_InsideInterval()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class, getContext());
		transition.setTermDuration(1);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermOfNotice(3);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		term.setStartDate(TimeUtil.getDay(2013, 4, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermHandler handler = new FlatrateTermHandler();

		final boolean resultActual = handler.isCorrectDateForTerm(term);
		final boolean resultExpected = true;
		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test
	public void test_isCorrectDateForTerm_ThreeMonths_OutsideInterval()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class, getContext());
		transition.setTermDuration(1);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermOfNotice(3);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		term.setStartDate(TimeUtil.getDay(2013, 10, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermHandler handler = new FlatrateTermHandler();

		final boolean resultActual = handler.isCorrectDateForTerm(term);
		final boolean resultExpected = false;
		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test
	public void test_isCorrectDateForTerm_InThePast()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class, getContext());
		transition.setTermDuration(1);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermOfNotice(3);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		term.setStartDate(TimeUtil.getDay(2011, 10, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermHandler handler = new FlatrateTermHandler();

		final boolean resultActual = handler.isCorrectDateForTerm(term);
		final boolean resultExpected = true;
		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test(expected = AdempiereException.class)
	public void test_isCorrectDateForTerm_no_TermOfNoticeUnit()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class, getContext());

		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		term.setStartDate(TimeUtil.getDay(2013, 5, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermHandler handler = new FlatrateTermHandler();
		handler.isCorrectDateForTerm(term);
	}

	@Test
	public void test_createMissingCandidates()
	{

		SystemTime.setTimeSource(new FixedTimeSource(2013, 5, 28)); // today

		final IInvoiceCandidateHandler invoiceCandHandler = new FlatrateTermHandler();

		final I_C_ILCandHandler ilCandHandler = InterfaceWrapperHelper.newInstance(I_C_ILCandHandler.class, getContext());
		InterfaceWrapperHelper.save(ilCandHandler);

		final I_M_Product product1 = InterfaceWrapperHelper.newInstance(I_M_Product.class, getContext());
		POJOWrapper.setInstanceName(product1, "product1");
		InterfaceWrapperHelper.save(product1);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		conditions.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term1 = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		POJOWrapper.setInstanceName(term1, "term1");

		term1.setAD_Org(org);
		term1.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term1.setC_Flatrate_Conditions(conditions);
		term1.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Abonnement);
		term1.setM_Product(product1);
		term1.setStartDate(TimeUtil.getDay(2013, 5, 27)); // yesterday
		InterfaceWrapperHelper.save(term1);

		invoiceCandHandler.setHandlerRecord(ilCandHandler);

		Services.registerService(IProductAcctDAO.class, productAcctDAO);
		Services.registerService(ITaxBL.class, taxBL);

		// 07442
		// @formatter:off
		new Expectations()
		{{
				productAcctDAO.retrieveActivityForAcct((IContextAware)any, org, product1); result = activity;

				productAcctDAO.retrieveActivityForAcct((IContextAware)any, withNotEqual(org), withNotEqual(product1)); result = null;

				final Properties ctx = Env.getCtx();
				final String trxName = ITrx.TRXNAME_None;

				final int taxCategoryId = -1;
				final I_M_Warehouse warehouse = null;
				final boolean isSOTrx = true;

				taxBL.getTax(
						ctx
						, term1
						, taxCategoryId
						, term1.getM_Product_ID()
						, -1 // chargeId
						, term1.getStartDate()
						, term1.getStartDate()
						, term1.getAD_Org_ID()
						, warehouse
						, term1.getBill_BPartner_ID()
						, -1 // ship location ID
						, isSOTrx
						, trxName);
				result = 3;
		}};
		// @formatter:on
		final List<I_C_Invoice_Candidate> candidates =
				InvoiceCandidatesTestHelper.createMissingCandidates(invoiceCandHandler,
						getContext().getCtx(),
						5,
						getContext().getTrxName());

		Assert.assertTrue("Candidates not created", !candidates.isEmpty());
		Assert.assertTrue("Wrong number of candidates created", candidates.size() == 1);

		final I_C_Invoice_Candidate cand1 = candidates.get(0);
		Assert.assertTrue("Wrong product", product1.equals(cand1.getM_Product()));
	}
}

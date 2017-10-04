package de.metas.contracts.invoicecandidate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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
import de.metas.contracts.impl.ContractsTestBase;
import de.metas.contracts.invoicecandidate.FlatrateTermInvoiceCandidateHandler;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Transition;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateResult;
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
		org = newInstance(I_AD_Org.class);
		InterfaceWrapperHelper.save(org);

		activity = newInstance(I_C_Activity.class);
		InterfaceWrapperHelper.save(activity);
	}

	@Test
	public void test_isCorrectDateForTerm_TermOfNotice_ThreeMonths_FirstDay()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setTermDuration(1);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermOfNotice(3);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setStartDate(TimeUtil.getDay(2013, 5, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermInvoiceCandidateHandler handler = new FlatrateTermInvoiceCandidateHandler();

		final boolean resultActual = handler.isCorrectDateForTerm(term);
		final boolean resultExpected = true;
		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test
	public void test_isCorrectDateForTerm_ThreeMonths_InsideInterval()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setTermDuration(1);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermOfNotice(3);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setStartDate(TimeUtil.getDay(2013, 4, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermInvoiceCandidateHandler handler = new FlatrateTermInvoiceCandidateHandler();

		final boolean resultActual = handler.isCorrectDateForTerm(term);
		final boolean resultExpected = true;
		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test
	public void test_isCorrectDateForTerm_ThreeMonths_OutsideInterval()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setTermDuration(1);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermOfNotice(3);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setStartDate(TimeUtil.getDay(2013, 10, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermInvoiceCandidateHandler handler = new FlatrateTermInvoiceCandidateHandler();

		final boolean resultActual = handler.isCorrectDateForTerm(term);
		final boolean resultExpected = false;
		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test
	public void test_isCorrectDateForTerm_InThePast()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);
		transition.setTermDuration(1);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermOfNotice(3);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_MonatE);
		transition.setName("Transition1");
		transition.setC_Calendar_Contract_ID(1000000);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setStartDate(TimeUtil.getDay(2011, 10, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermInvoiceCandidateHandler handler = new FlatrateTermInvoiceCandidateHandler();

		final boolean resultActual = handler.isCorrectDateForTerm(term);
		final boolean resultExpected = true;
		Assert.assertEquals(resultExpected, resultActual);
	}

	@Test(expected = AdempiereException.class)
	public void test_isCorrectDateForTerm_no_TermOfNoticeUnit()
	{
		SystemTime.setTimeSource(new FixedTimeSource(2013, 2, 7));

		final I_C_Flatrate_Transition transition = newInstance(I_C_Flatrate_Transition.class);

		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setStartDate(TimeUtil.getDay(2013, 5, 7));
		term.setIsAutoRenew(true);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);

		final FlatrateTermInvoiceCandidateHandler handler = new FlatrateTermInvoiceCandidateHandler();
		handler.isCorrectDateForTerm(term);
	}

	@Test
	public void test_createCandidatesForTerm()
	{

		SystemTime.setTimeSource(new FixedTimeSource(2013, 5, 28)); // today

		final I_M_Product product1 = newInstance(I_M_Product.class);
		POJOWrapper.setInstanceName(product1, "product1");
		save(product1);

		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription);
		save(conditions);

		final I_C_Flatrate_Term term1 = newInstance(I_C_Flatrate_Term.class);
		POJOWrapper.setInstanceName(term1, "term1");

		term1.setAD_Org(org);
		term1.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term1.setC_Flatrate_Conditions(conditions);
		term1.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Subscription);
		term1.setM_Product(product1);
		term1.setStartDate(TimeUtil.getDay(2013, 5, 27)); // yesterday
		save(term1);

		Services.registerService(IProductAcctDAO.class, productAcctDAO);
		Services.registerService(ITaxBL.class, taxBL);

		// 07442
		// @formatter:off
		new Expectations()
		{{
				productAcctDAO.retrieveActivityForAcct((IContextAware)any, org, product1); minTimes = 0; result = activity;

				productAcctDAO.retrieveActivityForAcct((IContextAware)any, withNotEqual(org), withNotEqual(product1)); minTimes = 0; result = null;

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
				minTimes = 0;
				result = 3;
		}};
		// @formatter:on

		final FlatrateTermInvoiceCandidateHandler flatrateTermHandler = new FlatrateTermInvoiceCandidateHandler();
		final InvoiceCandidateGenerateResult candidates = flatrateTermHandler.createCandidatesFor(InvoiceCandidateGenerateRequest.of(flatrateTermHandler, term1));

		assertThat(candidates.getC_Invoice_Candidates()).hasSize(1);
		final I_C_Invoice_Candidate invoiceCandidate = candidates.getC_Invoice_Candidates().get(0);
		assertThat(invoiceCandidate.getM_Product_ID()).isEqualTo(product1.getM_Product_ID());
	}
}

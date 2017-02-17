package de.metas.flatrate.api.impl;

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

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.pricing.model.I_C_PricingRule;
import org.adempiere.pricing.spi.impl.rules.MockedPricingRule;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Activity;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Calendar;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_Year;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_Warehouse;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_M_Product;
import de.metas.flatrate.ContractsTestBase;
import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_DataEntry;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.model.X_C_Flatrate_Conditions;
import de.metas.flatrate.model.X_C_Flatrate_DataEntry;
import de.metas.flatrate.model.X_C_Flatrate_Transition;
import de.metas.invoicecandidate.model.I_C_ILCandHandler;
import de.metas.product.acct.api.IProductAcctDAO;
import de.metas.tax.api.ITaxBL;
import mockit.Expectations;
import mockit.Mocked;

public class FlatrateBLTest extends ContractsTestBase
{

	I_C_Flatrate_Transition transition = null;
	I_C_Flatrate_Term term = null;

	// periods:
	I_C_Period period1 = null;
	I_C_Period period2 = null;
	I_C_Period period3 = null;
	I_C_Period period4 = null;
	I_C_Period period5 = null;
	I_C_Period period6 = null;

	// task 07442
	private I_AD_Org org;
	private I_C_Activity activity;

	@Mocked
	protected IProductAcctDAO productAcctDAO;
	@Mocked
	protected ITaxBL taxBL;

	// task 07442 end

	@Override
	protected void init()
	{
		MockedPricingRule.INSTANCE.reset();
		final I_C_PricingRule pricingRule = InterfaceWrapperHelper.newInstance(I_C_PricingRule.class, getContext());
		pricingRule.setSeqNo(10);
		pricingRule.setClassname(MockedPricingRule.class.getName());
		InterfaceWrapperHelper.save(pricingRule);

		final I_C_ILCandHandler handler = InterfaceWrapperHelper.newInstance(I_C_ILCandHandler.class, getContext());
		handler.setClassname("de.metas.flatrate.invoicecandidate.spi.impl.FlatrateDataEntryHandler");
		InterfaceWrapperHelper.save(handler);
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
	public void beforeCompleteDataEntry_test()
	{

		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final I_C_Flatrate_Transition flatrateTransition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class, getContext());
		flatrateTransition.setDeliveryInterval(1);
		flatrateTransition.setDeliveryIntervalUnit(X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_JahrE);
		InterfaceWrapperHelper.save(flatrateTransition);

		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class, getContext());
		InterfaceWrapperHelper.save(pricingSystem);

		final I_C_Flatrate_Conditions flatrateConditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		flatrateConditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Depotgebuehr);
		flatrateConditions.setM_PricingSystem(pricingSystem);
		InterfaceWrapperHelper.save(flatrateConditions);

		final I_C_Country country = InterfaceWrapperHelper.newInstance(I_C_Country.class, getContext());
		InterfaceWrapperHelper.save(country);

		final I_C_Location location = InterfaceWrapperHelper.newInstance(I_C_Location.class, getContext());
		location.setC_Country_ID(country.getC_Country_ID());
		InterfaceWrapperHelper.save(location);

		final I_C_BPartner_Location bpLocation = InterfaceWrapperHelper.newInstance(I_C_BPartner_Location.class, getContext());
		bpLocation.setName("Location1");
		bpLocation.setC_Location_ID(location.getC_Location_ID());
		InterfaceWrapperHelper.save(bpLocation);

		final I_C_Flatrate_Term currentTerm = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		currentTerm.setAD_Org(org);
		currentTerm.setStartDate(TimeUtil.getDay(2013, 1, 1));
		currentTerm.setEndDate(TimeUtil.getDay(2014, 7, 27));
		currentTerm.setC_Flatrate_Transition(flatrateTransition);
		currentTerm.setC_Flatrate_Conditions(flatrateConditions);
		currentTerm.setM_PricingSystem(pricingSystem);
		currentTerm.setBill_Location(bpLocation);
		InterfaceWrapperHelper.save(currentTerm);

		final I_M_PriceList priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class, getContext());
		priceList.setC_Country_ID(country.getC_Country_ID());
		priceList.setIsSOPriceList(true);
		priceList.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		priceList.setIsActive(true);
		InterfaceWrapperHelper.save(priceList);

		final I_M_PriceList_Version priceListVersion = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class, getContext());
		priceListVersion.setM_PriceList_ID(priceList.getM_PriceList_ID());
		priceListVersion.setValidFrom(TimeUtil.getDay(2013, 1, 1));
		priceListVersion.setIsActive(true);
		InterfaceWrapperHelper.save(priceListVersion);

		final I_M_Product product = InterfaceWrapperHelper.newInstance(I_M_Product.class, getContext());
		InterfaceWrapperHelper.save(product);

		final I_C_Period period = InterfaceWrapperHelper.newInstance(I_C_Period.class, getContext());
		period.setStartDate(TimeUtil.getDay(2013, 1, 1));
		InterfaceWrapperHelper.save(period);

		final I_C_Flatrate_DataEntry dataEntry = InterfaceWrapperHelper.newInstance(I_C_Flatrate_DataEntry.class, getContext());
		dataEntry.setAD_Org(org);
		dataEntry.setC_Flatrate_Term(currentTerm);
		dataEntry.setType(X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
		dataEntry.setM_Product_DataEntry(product);
		dataEntry.setC_Period(period);
		InterfaceWrapperHelper.save(dataEntry);

		setupTaxAndActivity(product, dataEntry, currentTerm);

		flatrateBL.beforeCompleteDataEntry(dataEntry);
	}

	private void setupTaxAndActivity(final I_M_Product product, final I_C_Flatrate_DataEntry dataEntry, final I_C_Flatrate_Term currentTerm)
	{
		Services.registerService(IProductAcctDAO.class, productAcctDAO);
		Services.registerService(ITaxBL.class, taxBL);

		new Expectations()
		{
			{
				productAcctDAO.retrieveActivityForAcct((IContextAware)any, org, product);
				minTimes = 0;
				result = activity;

				final Properties ctx = Env.getCtx();
				final String trxName = ITrx.TRXNAME_None;

				final int taxCategoryId = -1;
				final I_M_Warehouse warehouse = null;
				final boolean isSOTrx = true;

				taxBL.getTax(
						ctx
						, currentTerm
						, taxCategoryId
						, currentTerm.getM_Product_ID()
						, -1 // chargeId
						, dataEntry.getDate_Reported()
						, dataEntry.getDate_Reported()
						, dataEntry.getAD_Org_ID()
						, warehouse
						, currentTerm.getBill_BPartner_ID()
						, -1 // ship location ID
						, isSOTrx
						, trxName);
				minTimes = 0;
				result = 3;
			}
		};
	}

	@Test
	public void testUpdateNoticeDateAndEndDate_endsWithCalendarYear_CorrectData()
	{
		prepareForUpdateEndDate();

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		Assert.assertTrue("End date incorrectly calculated", TimeUtil.getDay(2014, 12, 31).compareTo(term.getEndDate()) == 0);

		// term duration: 24 months
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(24);
		InterfaceWrapperHelper.save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		Assert.assertTrue("End date incorrectly calculated", TimeUtil.getDay(2014, 12, 31).compareTo(term.getEndDate()) == 0);
	}

	@Test(expected = AdempiereException.class)
	public void testUpdateNoticeDateAndEndDate_endsWithCalendarYear_IncorrectTermDuration()
	{
		prepareForUpdateEndDate();
		// term duration: 25 months
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(25);
		InterfaceWrapperHelper.save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);
	}

	@Test(expected = AdempiereException.class)
	public void testUpdateNoticeDateAndEndDate_endsWithCalendarYear_YearNotInCalendar()
	{
		prepareForUpdateEndDate();

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(48); // the year of now + 4 years is not in calendar
		InterfaceWrapperHelper.save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);
	}

	@Test
	public void testUpdateNoticeDateAndEndDate_noEndsWithCalendarYear()
	{
		prepareForUpdateEndDate();

		transition.setEndsWithCalendarYear(false);

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_MonatE);
		transition.setTermDuration(5);
		InterfaceWrapperHelper.save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		Assert.assertTrue("End date incorrectly calculated", TimeUtil.getDay(2013, 8, 2).compareTo(term.getEndDate()) == 0);

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_TagE);
		transition.setTermDuration(30);
		InterfaceWrapperHelper.save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		Assert.assertTrue("End date incorrectly calculated", TimeUtil.getDay(2013, 4, 1).compareTo(term.getEndDate()) == 0);

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermDuration(2);
		InterfaceWrapperHelper.save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		Assert.assertTrue("End date incorrectly calculated", TimeUtil.getDay(2015, 3, 2).compareTo(term.getEndDate()) == 0);

		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_WocheN);
		transition.setTermDuration(2);
		InterfaceWrapperHelper.save(transition);

		Services.get(IFlatrateBL.class).updateNoticeDateAndEndDate(term);

		Assert.assertTrue("End date incorrectly calculated", TimeUtil.getDay(2013, 3, 16).compareTo(term.getEndDate()) == 0);

	}

	private void prepareForUpdateEndDate()
	{
		final I_C_Calendar calendar = InterfaceWrapperHelper.newInstance(I_C_Calendar.class, getContext());
		InterfaceWrapperHelper.save(calendar);

		// first year
		final I_C_Year year1 = InterfaceWrapperHelper.newInstance(I_C_Year.class, getContext());
		year1.setC_Calendar_ID(calendar.getC_Calendar_ID());
		InterfaceWrapperHelper.save(year1);

		period1 = InterfaceWrapperHelper.newInstance(I_C_Period.class, getContext());
		period1.setStartDate(TimeUtil.getDay(2013, 1, 1));
		period1.setEndDate(TimeUtil.getDay(2013, 5, 1));
		period1.setC_Year_ID(year1.getC_Year_ID());
		InterfaceWrapperHelper.save(period1);

		period2 = InterfaceWrapperHelper.newInstance(I_C_Period.class, getContext());
		period2.setStartDate(TimeUtil.getDay(2013, 5, 2));
		period2.setEndDate(TimeUtil.getDay(2013, 10, 1));
		period2.setC_Year_ID(year1.getC_Year_ID());
		InterfaceWrapperHelper.save(period2);

		period3 = InterfaceWrapperHelper.newInstance(I_C_Period.class, getContext());
		period3.setStartDate(TimeUtil.getDay(2013, 10, 2));
		period3.setEndDate(TimeUtil.getDay(2013, 12, 31));
		period3.setC_Year_ID(year1.getC_Year_ID());
		InterfaceWrapperHelper.save(period3);

		// second year

		final I_C_Year year2 = InterfaceWrapperHelper.newInstance(I_C_Year.class, getContext());
		year2.setC_Calendar_ID(calendar.getC_Calendar_ID());
		InterfaceWrapperHelper.save(year2);

		period4 = InterfaceWrapperHelper.newInstance(I_C_Period.class, getContext());
		period4.setStartDate(TimeUtil.getDay(2014, 1, 1));
		period4.setEndDate(TimeUtil.getDay(2014, 5, 1));
		period4.setC_Year_ID(year2.getC_Year_ID());
		InterfaceWrapperHelper.save(period4);

		period5 = InterfaceWrapperHelper.newInstance(I_C_Period.class, getContext());
		period5.setStartDate(TimeUtil.getDay(2014, 5, 2));
		period5.setEndDate(TimeUtil.getDay(2014, 10, 1));
		period5.setC_Year_ID(year2.getC_Year_ID());
		InterfaceWrapperHelper.save(period5);

		period6 = InterfaceWrapperHelper.newInstance(I_C_Period.class, getContext());
		period6.setStartDate(TimeUtil.getDay(2014, 10, 2));
		period6.setEndDate(TimeUtil.getDay(2014, 12, 31));
		period6.setC_Year_ID(year2.getC_Year_ID());
		InterfaceWrapperHelper.save(period6);

		transition = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Transition.class, getContext());
		transition.setEndsWithCalendarYear(true);
		transition.setTermDurationUnit(X_C_Flatrate_Transition.TERMDURATIONUNIT_JahrE);
		transition.setTermDuration(2);
		transition.setTermOfNoticeUnit(X_C_Flatrate_Transition.TERMOFNOTICEUNIT_WocheN);
		transition.setTermOfNotice(2);
		transition.setC_Calendar_Contract(calendar);
		InterfaceWrapperHelper.save(transition);

		final I_C_Flatrate_Conditions conditions = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Conditions.class, getContext());
		conditions.setC_Flatrate_Transition(transition);
		InterfaceWrapperHelper.save(conditions);

		term = InterfaceWrapperHelper.newInstance(I_C_Flatrate_Term.class, getContext());
		term.setStartDate(TimeUtil.getDay(2013, 3, 3));
		term.setC_Flatrate_Transition(transition);
		term.setC_Flatrate_Conditions(conditions);
		InterfaceWrapperHelper.save(term);
	}
}

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
import static org.junit.Assert.assertThat;
import static org.junit.Assume.assumeThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.MProductPricing;
import org.compiere.util.Env;
import org.junit.Assume;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.event.EventType;
import de.metas.adempiere.ait.event.TestEvent;
import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.TestConfig;
import de.metas.adempiere.ait.test.annotation.ITEventListener;
import de.metas.adempiere.model.I_M_Product;
import de.metas.adempiere.service.ICalendarDAO;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Data;
import de.metas.flatrate.model.I_C_Flatrate_DataEntry;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_DataEntry;
import de.metas.flatrate.model.X_C_Flatrate_Term;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.product.IProductPA;
import test.integration.contracts.ContractsHelper;
import test.integration.contracts.ContractsTestConfig;
import test.integration.swat.sales.SalesTestDriver;

public class FlatFeeTestListener
{

	@ITEventListener(
			tasks = "01671",
			desc = "Verifies that settings from conditions are correctly copied to the term",
			driver = FlatFeeTestDriver.class,
			eventTypes = EventType.FLATRATE_TERM_CREATE_AFTER)
	public void afterFlatrateTermSave(final TestEvent evt)
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)evt.getSource().getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		final I_C_Flatrate_Term term = (I_C_Flatrate_Term)evt.getObj();

		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();
		assertThat(conditions.getC_Flatrate_Conditions_ID(), greaterThan(0));

		if (conditions.isSimulation())
		{
			assertThat("Expected 'IsSimulation' of '" + term + "' to be 'Y', if it is 'Y' at the conditions",
					term.isSimulation(), equalTo(conditions.isSimulation()));
		}

		assertThat("Expected 'ClosingWithCorrectionSum' to be copied over from conditions",
				term.isClosingWithCorrectionSum(), equalTo(conditions.isClosingWithCorrectionSum()));

		assertThat("Expected 'ClosingWithActualSum' to be copied over from conditions",
				term.isClosingWithActualSum(), equalTo(conditions.isClosingWithActualSum()));

		assertThat("Expected 'UOMType' to be copied over from conditions",
				term.getUOMType(), equalTo(conditions.getUOMType()));

		assertThat("Expected 'Type_Conditions' to be copied over from conditions",
				term.getType_Conditions(), equalTo(conditions.getType_Conditions()));

		assertThat("Expected 'C_Currency_ID' to be copied over from conditions",
				term.getC_UOM_ID(), equalTo(conditions.getC_UOM_ID()));
	}

	@ITEventListener(
			tasks = "01671",
			desc = "Verifies for a term with Type_Conditions='FlatFee' that there the data entries have been created correctly.",
			driver = FlatFeeTestDriver.class,
			eventTypes = EventType.FLATRATE_TERM_COMPLETE_AFTER)
	public void afterFlatFeeTermComplete(final TestEvent evt)
	{
		final I_C_Flatrate_Term term = (I_C_Flatrate_Term)evt.getObj();
		assumeThat(term.getType_Conditions(), equalTo(X_C_Flatrate_Term.TYPE_CONDITIONS_Pauschalengebuehr));

		final Properties ctx = evt.getSource().getCtx();
		final String trxName = evt.getSource().getTrxName();

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);
		final ICalendarDAO calendarDAO = Services.get(ICalendarDAO.class);
		final List<I_C_Period> periodsOfTerm =
				calendarDAO.retrievePeriods(ctx, term.getC_Flatrate_Conditions().getC_Flatrate_Transition().getC_Calendar_Contract(), term.getStartDate(), term.getEndDate(), trxName);

		for (final I_C_UOM uom : flatrateDB.retrieveUOMs(ctx, term, trxName))
		{
			final List<I_C_Flatrate_DataEntry> entries =
					flatrateDB.retrieveDataEntries(term, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, uom);

			assertThat("Expected '" + term + "' to have one entry with UOM=" + uom.getName() + " per term period",
					entries.size(), equalTo(periodsOfTerm.size()));
		}
	}

	@ITEventListener(
			tasks = "01671",
			desc = "Verfifies correctness of a flatrate data entry if the conditions has Type_Conditions='FlatFee', IsUseCurrentPricing='Y' and IsSimulation='Y'",
			driver = FlatFeeTestDriver.class,
			eventTypes = EventType.FLATRATE_TERM_COMPLETE_AFTER)
	public void onNewSimulatedDataEntry(final TestEvent evt)
	{
		final I_C_Flatrate_Term term = (I_C_Flatrate_Term)evt.getObj();

		if (!equalTo(X_C_Flatrate_Term.TYPE_CONDITIONS_Pauschalengebuehr).matches(term.getType_Conditions()))
		{
			// tests are not applicable
			return;
		}
		if (!term.isSimulation())
		{
			// tests are not applicable
			return;
		}

		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();

		final AIntegrationTestDriver driver = evt.getSource();

		final IHelper helper = driver.getHelper();
		final TestConfig testConfig = helper.getConfig();

		final GridWindowHelper gridWindowHelper = openDataEntryTab(term, conditions, helper);

		// guard: field should be writable, as we are in simulation mode
		gridWindowHelper.assertReadOnly(I_C_Flatrate_DataEntry.COLUMNNAME_ActualQty, false);

		final I_C_Flatrate_DataEntry dataEntryGrid =
				gridWindowHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);

		final BigDecimal qty_Reported = new BigDecimal("23");
		final BigDecimal actualQty = new BigDecimal("138");

		dataEntryGrid.setQty_Reported(qty_Reported);
		dataEntryGrid.setActualQty(actualQty);

		gridWindowHelper.save();

		final I_M_Product flatrateProduct = helper.getM_Product(testConfig.getCustomParamStr(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE));
		final MProductPricing pp = new MProductPricing(flatrateProduct.getM_Product_ID(), term.getBill_BPartner_ID(), qty_Reported, true);

		final IProductPA productPA = Services.get(IProductPA.class);
		final I_M_PriceList priceList = productPA.retrievePriceListByPricingSyst(driver.getCtx(), conditions.getM_PricingSystem_ID(), term.getBill_Location_ID(), true, driver.getTrxName());

		assertThat(
				"Expected correct C_Currency_ID for I_C_Flatrate_DataEntry_ID=" + dataEntryGrid.getC_Flatrate_DataEntry_ID(),
				dataEntryGrid.getC_Currency_ID(), is(priceList.getC_Currency_ID()));

		pp.setM_PriceList_ID(priceList.getM_PriceList_ID());
		final BigDecimal pricePerUOM = pp.getPriceStd();

		assertThat("Expected FlatrateAmt = Qty_Reported * PricePerUOM for I_C_Flatrate_DataEntry_ID=" + dataEntryGrid.getC_Flatrate_DataEntry_ID(),
				dataEntryGrid.getFlatrateAmt(), comparesEqualTo(qty_Reported.multiply(pricePerUOM)));

		assertThat("Expected ActualQtyPerUnit = ActualQty / Qty_Reported for I_C_Flatrate_DataEntry_ID=" + dataEntryGrid.getC_Flatrate_DataEntry_ID(),
				dataEntryGrid.getActualQtyPerUnit(), comparesEqualTo(actualQty.divide(qty_Reported)));

		final BigDecimal plannedQty = term.getPlannedQtyPerUnit().multiply(qty_Reported);

		assertThat("Expected ActualQtyDiffAbs = ActualQty - (PlannedQtyPerUnit * Qty_Reported) for I_C_Flatrate_DataEntry_ID=" + dataEntryGrid.getC_Flatrate_DataEntry_ID(),
				dataEntryGrid.getActualQtyDiffAbs(), comparesEqualTo(actualQty.subtract(plannedQty)));

		assertThat("Expected ActualQtyDiffPercent = (ActualQty / PlannedQty * 100) - 100 for I_C_Flatrate_DataEntry_ID=" + dataEntryGrid.getC_Flatrate_DataEntry_ID(),
				dataEntryGrid.getActualQtyDiffPercent(), comparesEqualTo(actualQty.divide(plannedQty, 4, RoundingMode.HALF_UP).multiply(Env.ONEHUNDRED).subtract(Env.ONEHUNDRED)));

		assertThat("Expected ActualQtyDiffPerUOM = ActualQtyPerUnit - PlannedQtyPerUnit for I_C_Flatrate_DataEntry_ID=" + dataEntryGrid.getC_Flatrate_DataEntry_ID(),
				dataEntryGrid.getActualQtyDiffPerUOM(), comparesEqualTo(actualQty.divide(qty_Reported, 4, RoundingMode.HALF_UP).subtract(term.getPlannedQtyPerUnit())));
	}

	@ITEventListener(
			tasks = "01671",
			desc = "Verfifies correctness of a flatrate data entry if the conditions has Type_Conditions='FlatFee', IsUseCurrentPricing='Y' and IsSimulation='N'",
			driver = FlatFeeTestDriver.class,
			eventTypes = EventType.FLATRATE_TERM_COMPLETE_AFTER)
	public void onNewDataEntry(final TestEvent evt)
	{
		final I_C_Flatrate_Term term = (I_C_Flatrate_Term)evt.getObj();

		if (!equalTo(X_C_Flatrate_Term.TYPE_CONDITIONS_Pauschalengebuehr).matches(term.getType_Conditions()))
		{
			// tests are not applicable
			return;
		}
		if (term.isSimulation())
		{
			// tests are not applicable
			return;
		}

		final I_C_Flatrate_Conditions conditions = term.getC_Flatrate_Conditions();

		final IHelper helper = evt.getSource().getHelper();

		final GridWindowHelper gridWindowHelper = openDataEntryTab(term, conditions, helper);

		// guard: field should be read-only, as we are *not* in simulation mode
		gridWindowHelper.assertReadOnly(I_C_Flatrate_DataEntry.COLUMNNAME_ActualQty, true);
	}

	private GridWindowHelper openDataEntryTab(final I_C_Flatrate_Term term, final I_C_Flatrate_Conditions conditions, final IHelper helper)
	{
		final I_C_UOM conditionsUOM = conditions.getC_UOM();

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		assertThat(term + " has wrong DocStatus", term.getDocStatus(), equalTo(X_C_Flatrate_Term.DOCSTATUS_Completed));

		final List<I_C_Flatrate_DataEntry> entries =
				flatrateDB.retrieveDataEntries(term, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased, conditionsUOM);
		assertThat(term + " has no C_Flatrate_DataEntries", entries.size(), greaterThan(0));

		final I_C_Flatrate_DataEntry firstEntryPO = entries.get(0);

		final GridWindowHelper gridWindowHelper = helper.mkGridWindowHelper();

		gridWindowHelper
				.openTabForTable(I_C_Flatrate_Data.Table_Name, true)
				.selectRecordById(term.getC_Flatrate_Data_ID())
				.selectTab(I_C_Flatrate_Term.Table_Name)
				.selectRecordById(term.getC_Flatrate_Term_ID())
				.selectTab(I_C_Flatrate_DataEntry.Table_Name)
				.selectRecordById(firstEntryPO.getC_Flatrate_DataEntry_ID());

		final I_C_Flatrate_DataEntry dataEntry = gridWindowHelper.getGridTabInterface(I_C_Flatrate_DataEntry.class);
		assertThat("Expected IsSimulation to be same for I_C_Flatrate_DataEntry_ID=" + dataEntry.getC_Flatrate_DataEntry_ID() + " and its term",
				dataEntry.isSimulation(), equalTo(term.isSimulation()));

		return gridWindowHelper;
	}

	@ITEventListener(
			tasks = "01671",
			desc = "",
			driver = SalesTestDriver.class,
			eventTypes = EventType.INVOICECAND_CREATE_AFTER)
	public void onNewInvoiceCandidate(final TestEvent evt)
	{
		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(evt.getObj(), I_C_Invoice_Candidate.class);

		final I_M_Product icProduct = InterfaceWrapperHelper.create(ic.getM_Product(), I_M_Product.class);
		final AIntegrationTestDriver driver = evt.getSource();
		final ContractsHelper helper = new ContractsHelper(driver.getHelper());

		final ContractsTestConfig testConfig = helper.getContractsTestConfig();
		final boolean isProductUnderFlatRate = icProduct.getValue().equals(testConfig.getM_Product_Matching_Flatfee_Value());

		if (isProductUnderFlatRate)
		{
			assertThat(ic.isToClear(), is(true));
		}
		else
		{
			assertThat(ic.isToClear(), is(false));
		}

	}
}

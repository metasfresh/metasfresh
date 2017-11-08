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


import java.math.BigDecimal;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

import test.integration.contracts.ContractsHelper;
import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.TestConfig;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;
import de.metas.contracts.model.X_C_Flatrate_Conditions;

@RunWith(IntegrationTestRunner.class)
public class FlatFeeTestDriver extends AIntegrationTestDriver
{

	@Override
	public IHelper newHelper()
	{
		return new ContractsHelper(new Helper());
	}

	@IntegrationTest(
			tasks = "01671",
			desc = "Creates first an order and then a flatrate term for the order's invoice candidate")
	@Test
	public void flatFeeOrderFirst()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeOrderThenTerm");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FeeOrderFirstFlatrate_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FeeOrderFirstActual_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));

		final FlatFeeScenario scenario = new FlatFeeScenario(this);
		scenario.createOrderThenTerm();
	}

	@Test
	public void flatFeeTermFirst()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeTermFirst");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FeeOrderFirstFlatrate_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FeeOrderFirstActual_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));

		final FlatFeeScenario scenario = new FlatFeeScenario(this);
		scenario.createTermThenOrder();
	}

	@IntegrationTest(
			tasks = "01671",
			desc = "Sets up a 'Pflegetage' contract and a flat rate term with IsSimulation='Y'")
	@Test
	public void flatFeeContractWithSimulatedTerm()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeSim");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, true);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FlatfeeFlatrate_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FlatfeeActual_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));

		new FlatFeeScenario(this).fullScenario();
	}

	@IntegrationTest(
			tasks = "01671",
			desc = "Sets up a 'Pflegetage' contract and a flat rate term with IsSimulation='N' and Type_Clearing='EX'")
	@Test
	public void flatFeeExContractWithTerm()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		// make sure that we have a a unique BPartner
		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeEX");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, false);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FlatfeeFlatrateEX_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FlatfeeActualEX_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_AMOUNT, new BigDecimal("46"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT, new BigDecimal("6"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT, new BigDecimal("1"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PERCENT, new BigDecimal("20"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT, new BigDecimal("15"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_CORR_AMOUNT, new BigDecimal("6.9"));

		new FlatFeeScenario(this).fullScenario();
	}

	@IntegrationTest(
			tasks = "01671",
			desc = "Sets up a 'Pflegetage' contract and a flat rate term with IsSimulation='N' and Type_Clearing='EX'")
	@Test
	public void flatFeeExContractWithTermSameActual()
	{		
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		// make sure that we have a a unique BPartner
		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeSameActEX");
		testConfig.setC_BPartner_Name(testConfig.getPrefix() + "_(*)");

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, false);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FlatfeeEX_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FlatfeeEX_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_AMOUNT, new BigDecimal("46"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT, new BigDecimal("6"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT, new BigDecimal("1"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PERCENT, new BigDecimal("20"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT, new BigDecimal("15"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_CORR_AMOUNT, new BigDecimal("6.9"));

		new FlatFeeScenario(this).fullScenario();
	}

	@IntegrationTest(
			tasks = "01671",
			desc = "Sets up a 'Pflegetage' contract and a flat rate term with IsSimulation='N' and Type_Clearing='CO'")
	@Test
	public void flatFeeCoContractWithTermSameActual()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		// make sure that we have a a unique BPartner
		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeSameActCO");

		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, false);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Complete);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FlatfeeCO_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FlatfeeCO_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_AMOUNT, new BigDecimal("46"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT, new BigDecimal("6"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT, new BigDecimal("1"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PERCENT, new BigDecimal("20"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT, new BigDecimal("20"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_CORR_AMOUNT, new BigDecimal("9.2"));

		new FlatFeeScenario(this).fullScenario();
	}

	@IntegrationTest(
			tasks = "01671",
			desc = "Sets up a 'Pflegetage' contract and a flat rate term with IsSimulation='N' and Type_Clearing='EX'")
	@Test
	public void flatFeeExContractWithTermCorrAfterClosing()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		// make sure that we have a a unique BPartner
		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeEXCorrAC");

		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, true);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FlatfeeFlatrateEX_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FlatfeeActualEX_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_AMOUNT, new BigDecimal("46"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT, new BigDecimal("6"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT, new BigDecimal("1"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PERCENT, new BigDecimal("20"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT, new BigDecimal("15"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_CORR_AMOUNT, new BigDecimal("6.9"));

		new FlatFeeScenario(this).fullScenario();
	}

	@IntegrationTest(
			tasks = "01671",
			desc = "Sets up a 'Pflegetage' contract and a flat rate term with IsSimulation='N' and Type_Clearing='CO'")
	@Test
	public void flatFeeCoContractWithTermCorrAfterClosing()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		// make sure that we have a a unique BPartner
		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeCOCorrAC");

		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, true);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Complete);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FlatfeeFlatrateCO_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FlatfeeActualCO_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_AMOUNT, new BigDecimal("46"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT, new BigDecimal("6"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT, new BigDecimal("1"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PERCENT, new BigDecimal("20"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT, new BigDecimal("20"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_CORR_AMOUNT, new BigDecimal("9.2"));

		new FlatFeeScenario(this).fullScenario();
	}

	@IntegrationTest(
			tasks = "03768",
			desc = "Extend a flatrate term using the process FlatrateBL.extendContract")
	@Test
	public void termExtention()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		// make sure that we have a a unique BPartner
		final TestConfig testConfig = getHelper().getConfig();
		testConfig.setPrefix("FlatfeeEX");

		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CORR_AFTER_CLOSING, false);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PLANNED_QTY_PER_UNIT, new BigDecimal("5"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT_CLOSING, new BigDecimal("3"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_UNITS_REPORTED, new BigDecimal("23"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("138"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_TYPE_CLEARING, X_C_Flatrate_Conditions.TYPE_CLEARING_Exceeding);

		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("FlatfeeFlatrateEX_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_ACTUAL_VALUE, Helper.parseName("FlatfeeActualEX_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_AMOUNT, new BigDecimal("46"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_QTY_PER_UNIT, new BigDecimal("6"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PER_UNIT, new BigDecimal("1"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_PERCENT, new BigDecimal("20"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_DIFF_EFF_PERCENT, new BigDecimal("15"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_EXP_ACTUAL_CORR_AMOUNT, new BigDecimal("6.9"));

		testConfig.setCustomParam(ContractsHelper.PARAM_TERM_CURRENCY_ID, 318);

		new FlatFeeScenario(this).fullScenario();
	}
}
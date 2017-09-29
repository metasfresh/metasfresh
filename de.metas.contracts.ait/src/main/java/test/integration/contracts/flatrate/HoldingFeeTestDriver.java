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

import test.integration.contracts.ContractsHelper;
import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.helper.TestConfig;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Transition;

public class HoldingFeeTestDriver extends AIntegrationTestDriver
{
	@Override
	public IHelper newHelper()
	{
		return new ContractsHelper(new Helper());
	}

	@IntegrationTest(
			tasks = "01672",
			desc = "Sets up a holding contract")
	@Test
	public void holdingFee()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_HoldingFee_Disabled());

		final ContractsHelper helper = (ContractsHelper)getHelper();

		final TestConfig testConfig = helper.getConfig();
		testConfig.setPrefix("HoldingFee");
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName("HoldingfeeFlatrate_(*)"));

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		final I_C_Flatrate_Conditions conditions = helper.createHoldingFeeContract(this, ft);
		/* final I_C_Flatrate_Term term = */helper.createHoldingFeeTerm(this, conditions);

	}

}

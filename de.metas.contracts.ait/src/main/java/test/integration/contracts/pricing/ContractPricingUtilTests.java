package test.integration.contracts.pricing;

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

import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_M_Product;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.flatrate.interfaces.IFlatrateConditionsAware;
import de.metas.flatrate.model.I_C_Flatrate_Conditions;
import de.metas.flatrate.model.I_C_Flatrate_Transition;
import de.metas.flatrate.pricing.spi.impl.ContractPricingUtil;
import test.integration.contracts.ContractsHelper;
import test.integration.contracts.ContractsTestConfig;
import test.integration.contracts.flatrate.FlatFeeScenario;

/**
 * Tests the behavior of {@link ContractPricingUtil}.
 *
 * @author ts
 *
 */
@RunWith(IntegrationTestRunner.class)
public class ContractPricingUtilTests extends AIntegrationTestDriver
{

	@Test
	public void testWithBean()
	{
		final I_C_Flatrate_Conditions fc = setupContract("ContractPricingUtilTestWithBean");

		final IFlatrateConditionsAware bean = new IFlatrateConditionsAware()
		{
			@Override
			public void setC_Flatrate_Conditions(I_C_Flatrate_Conditions C_Flatrate_Conditions)
			{
				Assert.fail("unexpected method invocation");
			}

			@Override
			public I_C_Flatrate_Conditions getC_Flatrate_Conditions()
			{
				return fc;
			}

			@Override
			public void setC_Flatrate_Conditions_ID(int C_Flatrate_Conditions_ID)
			{
				Assert.fail("unexpected method invocation");
			}

			@Override
			public int getC_Flatrate_Conditions_ID()
			{
				return fc.getC_Flatrate_Conditions_ID();
			}
		};

		final I_C_Flatrate_Conditions result = ContractPricingUtil.getC_Flatrate_Conditions(bean);
		Assert.assertSame(fc, result);
	}

	/**
	 * {@link ContractPricingUtil} is called with a {@link I_C_OrderLine}. Note that the instance we call the util with does not implement {@link IFlatrateConditionsAware}. However, the underlying
	 * PO has a <code>C_Flatrate_conditions_ID</code> column and {@link InterfaceWrapperHelper} should be able to create a IFlatrateConditionsProvider instance.
	 */
	@Test
	public void testWithSupportingPO()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_Subscr_Disabled());

		final I_C_Flatrate_Conditions fc = setupContract("ContractPricingUtilTestWithSupportingPO");

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(getCtx(), I_C_OrderLine.class, getTrxName());

		InterfaceWrapperHelper.create(ol, de.metas.contracts.subscription.model.I_C_OrderLine.class).setC_Flatrate_Conditions_ID(fc.getC_Flatrate_Conditions_ID());

		final I_C_Flatrate_Conditions result = ContractPricingUtil.getC_Flatrate_Conditions(ol);
		Assert.assertEquals(fc.getC_Flatrate_Conditions_ID(), result.getC_Flatrate_Conditions_ID());
	}

	/**
	 * {@link ContractPricingUtil} is called with a {@link I_M_Product}. Note that the product PO <b>does not</b> have a <code>C_Flatrate_conditions_ID</code> column.
	 */
	@Test
	public void testWithOtherPO()
	{
		// only execute this test if subscription tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_Subscr_Disabled());

		final I_M_Product p = InterfaceWrapperHelper.create(getCtx(), I_M_Product.class, getTrxName());

		final I_C_Flatrate_Conditions result = ContractPricingUtil.getC_Flatrate_Conditions(p);
		Assert.assertNull(result);

	}

	@Override
	public IHelper newHelper()
	{
		return new ContractsHelper(new Helper());
	}

	public I_C_Flatrate_Conditions setupContract(final String id)
	{
		final ContractsHelper helper = (ContractsHelper)getHelper();

		final ContractsTestConfig testConfig = helper.getContractsTestConfig();
		testConfig.setPrefix(id);
		testConfig.setC_BPartner_Name(Helper.parseName(testConfig.getPrefix() + "_(*)"));

		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_SIMULATION, false);
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_PRICE_PER_UNIT, new BigDecimal("2"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_STR_PRODUCT_FLATRATE_VALUE, Helper.parseName(id + "_(*)"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BD_ACTUAL_QTY, new BigDecimal("3"));
		testConfig.setCustomParam(FlatFeeScenario.PARAM_BOOL_IS_CREATE_ORDER_LINES, true); // this value shouldn't really matter for the test

		final I_C_Flatrate_Transition ft = helper.createTransistion(this);
		return helper.createSubscriptionContract(this, ft);
	}

}

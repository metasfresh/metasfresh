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


import test.integration.ordercandidates.OrderCandidatesTestConfig;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.TestConfig;
import de.metas.contracts.model.X_C_Contract_Change;

/**
 * This config contains parameters for the testing of flatrate/flatfee/subscription contracts.
 */
public class ContractsTestConfig extends OrderCandidatesTestConfig
{
	public ContractsTestConfig(final TestConfig parent)
	{
		super(parent);

		// make sure the parameters are set, but don't override them in case they were already set in 'parent'
		setCustomParamIfEmpty("M_Product_Matching_Flatfee_Value", Helper.parseName(getProp("M_Product_Matching_Flatfee_Value")));
		setCustomParamIfEmpty("M_Product_Matching_Flatfee_IsStocked", Boolean.parseBoolean(getProp("M_Product_Matching_Flatfee_IsStocked")));

		setCustomParamIfEmpty("M_Product_Matching_Holdingfee_Value", Helper.parseName(getProp("M_Product_Matching_Holdingfee_Value")));
		setCustomParamIfEmpty("M_Product_Matching_Holdingfee_IsStocked", Boolean.parseBoolean(getProp("M_Product_Matching_Holdingfee_IsStocked")));

		setCustomParamIfEmpty("M_Product_Matching_Subcr_Value", Helper.parseName(getProp("M_Product_Matching_Subcr_Value")));
		setCustomParamIfEmpty("M_Product_Matching_Subcr_IsStocked", Boolean.parseBoolean(getProp("M_Product_Matching_Subcr_IsStocked")));

		setCustomParamIfEmpty("C_UOM_Flatrate_Name", getProp("C_UOM_Flatrate_Name"));

		setCustomParamIfEmpty("FlatrateUOMType", getProp("FlatrateUOMType"));

		setCustomParamIfEmpty("C_Calendar_Flatrate_Name", getProp("C_Calendar_Flatrate_Name"));

		//
		// all types of contracts are enabled, unless set otherwise in the test configs
		setCustomParamIfEmpty("TYPE_CONDITIONS_FlatFee_Disabled", Boolean.parseBoolean(getProp("TYPE_CONDITIONS_FlatFee_Disabled", "false")));
		setCustomParamIfEmpty("TYPE_CONDITIONS_HoldingFee_Disabled", Boolean.parseBoolean(getProp("TYPE_CONDITIONS_HoldingFee_Disabled", "false")));
		setCustomParamIfEmpty("TYPE_CONDITIONS_Subscr_Disabled", Boolean.parseBoolean(getProp("TYPE_CONDITIONS_Subscr_Disabled", "false")));
		setCustomParamIfEmpty("TYPE_CONDITIONS_Refundable_Disabled", Boolean.parseBoolean(getProp("TYPE_CONDITIONS_Refundable_Disabled", "false")));

		//
		// setting further custom params that can be altered from specific tests
		// note: we choose a default term duration of six month, because then even tests that include a second term
		// wont result in term end dates that are more than a year from now
		// that way, the calendar of the system under tests only needs to have one additional business year (and not two or more)
		setCustomParamIfEmpty(ContractsHelper.PARAM_TRANSITION_TERM_DURATION, 6);
		setCustomParamIfEmpty(ContractsHelper.PARAM_TRANSITION_TERM_DURATION_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		setCustomParamIfEmpty(ContractsHelper.PARAM_TRANSITION_DELIVERY_INTERVAL, 1);
		setCustomParamIfEmpty(ContractsHelper.PARAM_TRANSITION_DELIVERY_INTERVAL_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		setCustomParamIfEmpty(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE, 4);
		setCustomParamIfEmpty(ContractsHelper.PARAM_CONTRACT_CANGE_CANCEL_DEADLINE_UNIT, X_C_Contract_Change.DEADLINEUNIT_MonatE);

		setCustomParamIfEmpty(ContractsHelper.PARAM_TERM_CURRENCY_ID, 318);
	}

	public String getFlatrateUOMType()
	{
		return (String)getCustomParam("FlatrateUOMType");
	}

	public String getC_UOM_Flatrate_Name()
	{
		return (String)getCustomParam("C_UOM_Flatrate_Name");
	}

	/**
	 * If setting up a 'Pauschalenvertrag' (C_FlatrateCondition with TYPE_CONDITIONS_Pauschalengebuehr), then this is the product that should fall under the flat rate.
	 * 
	 * @param value
	 * @return
	 */
	public String getM_Product_Matching_Flatfee_Value()
	{
		return (String)getCustomParam("M_Product_Matching_Flatfee_Value");
	}

	public boolean getM_Product_Matching_Flatfee_IsStocked()
	{
		return getCustomParamBool("M_Product_Matching_Flatfee_IsStocked");
	}

	public String getM_Product_Matching_Holdingfee_Value()
	{
		return (String)getCustomParam("M_Product_Matching_Holdingfee_Value");
	}

	public boolean getM_Product_Matching_Holdingfee_IsStocked()
	{
		return getCustomParamBool("M_Product_Matching_Holdingfee_IsStocked");
	}

	public String getM_Product_Matching_Subcr_Value()
	{
		return (String)getCustomParam("M_Product_Matching_Subcr_Value");
	}

	public boolean getM_Product_Matching_Subcr_IsStocked()
	{
		return getCustomParamBool("M_Product_Matching_Subcr_IsStocked");
	}

	public String getC_Calendar_Flatrate_Name()
	{
		return (String)getCustomParam("C_Calendar_Flatrate_Name");
	}

	public boolean is_TYPE_CONDITIONS_FlatFee_Disabled()
	{
		return getCustomParamBool("TYPE_CONDITIONS_FlatFee_Disabled");
	}

	public boolean is_TYPE_CONDITIONS_HoldingFee_Disabled()
	{
		return getCustomParamBool("TYPE_CONDITIONS_HoldingFee_Disabled");
	}

	public boolean is_TYPE_CONDITIONS_Subscr_Disabled()
	{
		return getCustomParamBool("TYPE_CONDITIONS_Subscr_Disabled");
	}

	public boolean is_TYPE_CONDITIONS_Refundable_Disabled()
	{
		return getCustomParamBool("TYPE_CONDITIONS_Refundable_Disabled");
	}

	@Override
	public String toString()
	{
		return super.toString()
				+ "\nContractsTestConfig ["
				+ ", uomType=" + getFlatrateUOMType()
				+ ", uomName=" + getC_UOM_Flatrate_Name()
				+ ", C_Calendar_Flatrate_Name=" + getC_Calendar_Flatrate_Name()
				+ ", M_Product_Matching_Flatfee_Value=" + getM_Product_Matching_Flatfee_Value() + "]";
	}
}

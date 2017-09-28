package test.integration.contracts.ui;

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


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Assume;
import org.junit.Test;

import test.integration.contracts.ContractsHelper;
import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.GridWindowHelper;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.annotation.IntegrationTest;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_Conditions;

public class FlatrateTests extends AIntegrationTestDriver
{

	@Override
	public IHelper newHelper()
	{
		return new Helper();
	}

	@IntegrationTest(
			desc = "Verifies that the fields are shown and hidden as expected for Type_Conditions=Pauschalengebuehr",
			tasks = "01671")
	@Test
	public void headerPauschalenGebuehr()
	{
		// only execute this test if flatfee tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());
		
		final GridWindowHelper gridWindowHelper = getHelper().mkGridWindowHelper();

		final I_C_Flatrate_Conditions conditions = gridWindowHelper.openTabForTable(I_C_Flatrate_Conditions.Table_Name, true)
				.newRecord()
				.getGridTabInterface(I_C_Flatrate_Conditions.class);

		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee);

		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Flatrate_ID, true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_UOMType, true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_C_UOM_ID, true);

		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_InvoiceRule, true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_PricingSystem_ID, true);


		assertFalse("'IsClosingWithCorrectionSum' should be disabled by default", conditions.isClosingWithCorrectionSum());
		assertFalse("'IsClosingWithActualSum' should be disabled by default", conditions.isClosingWithActualSum());

		conditions.setIsClosingWithActualSum(true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Type_Flatrate, true);

		assertEquals("Expencting Type_Flatrate to be 'NONE' by default", X_C_Flatrate_Conditions.TYPE_FLATRATE_NONE, conditions.getType_Flatrate());
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Type_Clearing, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Margin_Max, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Margin_Min, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Actual_ID, false);

		conditions.setType_Flatrate(X_C_Flatrate_Conditions.TYPE_FLATRATE_Corridor_Percent);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Type_Clearing, true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Margin_Max, true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Margin_Min, true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Actual_ID, true);
		
		
		conditions.setIsClosingWithCorrectionSum(true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Correction_ID, true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_IsCorrectionAmtAtClosing, true); 

		conditions.setIsClosingWithCorrectionSum(false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Correction_ID, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_IsCorrectionAmtAtClosing, false); 
		
	}

	@IntegrationTest(
			desc = "Verifies that the fields are shown and hidden as expected for Type_Conditions=Depotgebuehr",
			tasks = "01671")
	@Test
	public void headerDepotGebuehr()
	{
		final GridWindowHelper gridWindowHelper = getHelper().mkGridWindowHelper();

		final I_C_Flatrate_Conditions conditions = gridWindowHelper.openTabForTable(I_C_Flatrate_Conditions.Table_Name, true)
				.newRecord()
				.getGridTabInterface(I_C_Flatrate_Conditions.class);

		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee);

		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Flatrate_ID, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_UOMType, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_C_UOM_ID, false);

		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_InvoiceRule, true);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_PricingSystem_ID, true);
		
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Type_Flatrate, false);

		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Type_Clearing, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Margin_Max, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Margin_Min, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Actual_ID, false);
		
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Correction_ID, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_IsCorrectionAmtAtClosing, false); 
		
	}

	@IntegrationTest(
			desc = "Verifies that there are no fields specific to 'Pauschalengebuehr' are left on display after a switch",
			tasks = "01671")
	@Test
	public void headerRemoveFieldsOnSwitch()
	{
		final GridWindowHelper gridWindowHelper = getHelper().mkGridWindowHelper();

		final I_C_Flatrate_Conditions conditions = gridWindowHelper.openTabForTable(I_C_Flatrate_Conditions.Table_Name, true)
				.newRecord()
				.getGridTabInterface(I_C_Flatrate_Conditions.class);

		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_FlatFee);
		
		conditions.setIsClosingWithCorrectionSum(true);
		conditions.setIsClosingWithActualSum(true);
		// now a number of 'FlatFee/Pauschalengebuehr' specific fields are shown

		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Type_Flatrate, true);
		
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_HoldingFee);
		// now the 'FlatFee/Pauschalengebuehr' specific fields should all be gone
				
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Type_Flatrate, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Type_Clearing, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Margin_Max, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_Margin_Min, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Actual_ID, false);
		
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_M_Product_Correction_ID, false);
		gridWindowHelper.assertFieldDisplayed(I_C_Flatrate_Conditions.COLUMNNAME_IsCorrectionAmtAtClosing, false); 
	}
	
}

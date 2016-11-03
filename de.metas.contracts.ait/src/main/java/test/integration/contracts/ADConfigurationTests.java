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


import static de.metas.adempiere.ait.helper.ADAssert.assertDocTypeAvail;
import static de.metas.adempiere.ait.helper.ADAssert.assertMsgExists;
import static de.metas.adempiere.ait.helper.ADAssert.assertProcessExists;
import static de.metas.adempiere.ait.helper.ADAssert.assertProcessSchedulerExists;

import org.junit.Assume;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.ait.event.AIntegrationTestDriver;
import de.metas.adempiere.ait.helper.Helper;
import de.metas.adempiere.ait.helper.IHelper;
import de.metas.adempiere.ait.test.IntegrationTestRunner;
import de.metas.flatrate.interfaces.I_C_DocType;
import de.metas.flatrate.modelvalidator.C_Flatrate_Conditions;
import de.metas.flatrate.modelvalidator.C_Invoice_Candidate;
import de.metas.flatrate.process.C_Flatrate_Term_Create_From_OLCand;
import de.metas.flatrate.process.C_Flatrate_Term_Extend;
import de.metas.flatrate.process.C_Flatrate_Term_Prepare_Closing;

@RunWith(IntegrationTestRunner.class)
public class ADConfigurationTests extends AIntegrationTestDriver
{

	@Override
	public IHelper newHelper()
	{
		return new ContractsHelper(new Helper());
	}

	@Test
	public void config()
	{
		assertProcessExists(getCtx(), "C_Flatrate_Term_Extend", C_Flatrate_Term_Extend.class, getTrxName());
		assertProcessExists(C_Flatrate_Term_Prepare_Closing.class, null);

		// 03660
		assertProcessExists(C_Flatrate_Term_Create_From_OLCand.class, null);

		assertProcessExists(getCtx(), "C_Flatrate_Term_ProcessNoticeDates", C_Flatrate_Term_Extend.class, getTrxName());

		assertProcessSchedulerExists(getCtx(), "C_Flatrate_Term_ProcessNoticeDates", getTrxName());

		assertMsgExists(C_Flatrate_Conditions.MSG_CONDITIONS_ERROR_ALREADY_IN_USE_0P);
		assertMsgExists(C_Flatrate_Conditions.MSG_CONDITIONS_ERROR_MATCHING_MISSING_0P);
		assertMsgExists(C_Flatrate_Conditions.MSG_CONDITIONS_ERROR_INVALID_TRANSITION_2P);
		assertMsgExists(C_Flatrate_Conditions.MSG_CONDITIONS_ERROR_TRANSITION_NOT_CO_0P);
		assertMsgExists(C_Flatrate_Conditions.MSG_CONDITIONS_ERROR_ORDERLESS_SUBSCRIPTION_NOT_SUPPORTED_0P);

		assertMsgExists(C_Invoice_Candidate.MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_0P);
	}

	@Test
	public void checkSubscrDocType()
	{
		// only execute this test if flatfee tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_Subscr_Disabled());

		assertDocTypeAvail(
				getHelper().getConfig().getAD_Client_ID(),
				getHelper().getConfig().getAD_Org_ID(),
				I_C_DocType.DocBaseType_CustomerContract,
				I_C_DocType.DocSubType_Abonnement,
				true);
	}

	@Test
	public void checkHoldingFeeDocType()
	{
		// only execute this test if flatfee tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_HoldingFee_Disabled());

		assertDocTypeAvail(
				getHelper().getConfig().getAD_Client_ID(),
				getHelper().getConfig().getAD_Org_ID(),
				I_C_DocType.DocBaseType_CustomerContract,
				I_C_DocType.DocSubType_Depotgebuehr,
				true);
	}

	@Test
	public void checkFlatFeeDocType()
	{
		// only execute this test if flatfee tests have been enabled
		Assume.assumeTrue(!((ContractsHelper)getHelper()).getContractsTestConfig().is_TYPE_CONDITIONS_FlatFee_Disabled());

		assertDocTypeAvail(
				getHelper().getConfig().getAD_Client_ID(),
				getHelper().getConfig().getAD_Org_ID(),
				I_C_DocType.DocBaseType_CustomerContract,
				I_C_DocType.DocSubType_Pauschalengebuehr,
				true);
	}
	
	

}
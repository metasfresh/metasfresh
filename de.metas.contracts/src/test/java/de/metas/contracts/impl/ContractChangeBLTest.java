package de.metas.contracts.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

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


import java.sql.Timestamp;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.TimeUtil;
import org.junit.Test;

import de.metas.contracts.IContractChangeBL;
import de.metas.contracts.model.I_C_Contract_Change;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Flatrate_Transition;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_Contract_Change;
import de.metas.contracts.model.X_C_Flatrate_Transition;

public class ContractChangeBLTest extends ContractsTestBase
{
	@Override
	protected void init()
	{
		POJOWrapper.setDefaultStrictValues(false);
	}

	@Test
	public void cancelContract_test()
	{
		final IContractChangeBL contractChangeBL = Services.get(IContractChangeBL.class);

		SystemTime.setTimeSource(new FixedTimeSource(2013, 5, 28)); // today

		final I_C_Flatrate_Transition flatrateTransition = newInstance(I_C_Flatrate_Transition.class);
		flatrateTransition.setDeliveryInterval(1);
		flatrateTransition.setDeliveryIntervalUnit(X_C_Flatrate_Transition.DELIVERYINTERVALUNIT_JahrE);
		InterfaceWrapperHelper.save(flatrateTransition);

		final I_C_Flatrate_Conditions flatrateConditions = newInstance(I_C_Flatrate_Conditions.class);
		flatrateConditions.setC_Flatrate_Transition(flatrateTransition);
		InterfaceWrapperHelper.save(flatrateConditions);

		final I_C_Contract_Change contractChange = newInstance(I_C_Contract_Change.class);
		contractChange.setAction(X_C_Contract_Change.ACTION_Statuswechsel);
		contractChange.setC_Flatrate_Transition(flatrateTransition);
		contractChange.setC_Flatrate_Conditions(flatrateConditions);
		contractChange.setContractStatus( X_C_Contract_Change.CONTRACTSTATUS_Gekuendigt);
		contractChange.setDeadLine(1);
		contractChange.setDeadLineUnit(X_C_Contract_Change.DEADLINEUNIT_MonatE);
		InterfaceWrapperHelper.save(contractChange);


		final I_C_Flatrate_Term currentTerm = newInstance(I_C_Flatrate_Term.class);
		currentTerm.setStartDate(TimeUtil.getDay(1013, 1, 1));
		currentTerm.setEndDate(TimeUtil.getDay(2014,7,27));
		currentTerm.setC_Flatrate_Conditions(flatrateConditions);
		InterfaceWrapperHelper.save(currentTerm);

		final I_C_SubscriptionProgress progress = newInstance(I_C_SubscriptionProgress.class);
		progress.setC_Flatrate_Term(currentTerm);
		progress.setEventDate(TimeUtil.getDay(2013, 5, 30));
		InterfaceWrapperHelper.save(progress);

		final Timestamp changeTime = TimeUtil.getDay(2013,7,27);

		contractChangeBL.cancelContract(currentTerm, changeTime, true);

	}
}

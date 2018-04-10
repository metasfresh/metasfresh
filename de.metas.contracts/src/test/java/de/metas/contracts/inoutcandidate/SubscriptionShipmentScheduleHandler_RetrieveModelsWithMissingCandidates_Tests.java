package de.metas.contracts.inoutcandidate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Test;

import de.metas.contracts.inoutcandidate.SubscriptionShipmentScheduleHandler;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class SubscriptionShipmentScheduleHandler_RetrieveModelsWithMissingCandidates_Tests
{
	private I_C_SubscriptionProgress firstRecord;
	private I_C_SubscriptionProgress secondRecord;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		firstRecord = newInstance(I_C_SubscriptionProgress.class);
		firstRecord.setEventDate(SystemTime.asTimestamp());
		firstRecord.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		firstRecord.setStatus(X_C_SubscriptionProgress.STATUS_Planned);
		save(firstRecord);

		secondRecord = newInstance(I_C_SubscriptionProgress.class);
		secondRecord.setEventDate(TimeUtil.addDays(SystemTime.asTimestamp(), 4));
		secondRecord.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
		secondRecord.setStatus(X_C_SubscriptionProgress.STATUS_Planned);
		save(secondRecord);
	}

	@Test
	public void retrieveModelsWithMissingCandidates_No_SysConfig()
	{
		assertOnlyFirstRecordIsReturned();
	}

	@Test
	public void retrieveModelsWithMissingCandidates_Different_Advance_Days_Values()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

		// guard
		{
			sysConfigBL.setValue(SubscriptionShipmentScheduleHandler.SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS, 3, 0);
			assertOnlyFirstRecordIsReturned();
		}

		sysConfigBL.setValue(SubscriptionShipmentScheduleHandler.SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS, 4, 0);
		assertBothRecordsAreReturned();

		sysConfigBL.setValue(SubscriptionShipmentScheduleHandler.SYSCONFIG_CREATE_SHIPMENT_SCHEDULES_IN_ADVANCE_DAYS, 5, 0);
		assertBothRecordsAreReturned();
	}

	private void assertOnlyFirstRecordIsReturned()
	{
		final List<Object> result = new SubscriptionShipmentScheduleHandler().retrieveModelsWithMissingCandidates(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		
		assertThat(result).hasSize(1);
		assertThat(result.get(0)).isInstanceOf(I_C_SubscriptionProgress.class);

		final I_C_SubscriptionProgress retrievedProgress = (I_C_SubscriptionProgress)result.get(0);
		assertThat(retrievedProgress.getC_SubscriptionProgress_ID()).isEqualTo(firstRecord.getC_SubscriptionProgress_ID());
	}

	private void assertBothRecordsAreReturned()
	{
		final List<Object> secondResult = new SubscriptionShipmentScheduleHandler().retrieveModelsWithMissingCandidates(Env.getCtx(), ITrx.TRXNAME_ThreadInherited);
		assertThat(secondResult).hasSize(2);

		assertThat(secondResult).allSatisfy(r -> {
			assertThat(r).isInstanceOf(I_C_SubscriptionProgress.class);
		});

		final I_C_SubscriptionProgress retrievedProgress = (I_C_SubscriptionProgress)secondResult.get(0);
		assertThat(retrievedProgress.getC_SubscriptionProgress_ID()).isEqualTo(firstRecord.getC_SubscriptionProgress_ID());

		final I_C_SubscriptionProgress retrievedProgressInTheFuture = (I_C_SubscriptionProgress)secondResult.get(1);
		assertThat(retrievedProgressInTheFuture.getC_SubscriptionProgress_ID()).isEqualTo(secondRecord.getC_SubscriptionProgress_ID());
	}

}

package de.metas.contracts.subscription.impl.subscriptioncommands;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.contracts.subscription.impl.SubscriptionCommand;
import de.metas.contracts.subscription.impl.SubscriptionTestUtil;
import lombok.NonNull;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class RemovePausesTest
{
	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private ISubscriptionDAO subscriptionDAO;
	
	private final Timestamp pauseFrom = TimeUtil.parseTimestamp("2017-09-12");
	private final Timestamp pauseUntil = TimeUtil.parseTimestamp("2017-10-12");

	private I_C_Flatrate_Term term;
	private I_C_SubscriptionProgress middle;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		subscriptionDAO = Services.get(ISubscriptionDAO.class);

		term = newInstance(I_C_Flatrate_Term.class);
		save(term);

		SubscriptionTestUtil.createDeliverySubscriptionProgress(term, "2017-09-10", 1);
		
		final I_C_SubscriptionProgress pauseStart = SubscriptionTestUtil.createDeliverySubscriptionProgress(term, "2017-09-12", 2);
		pauseStart.setEventType(X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause);
		pauseStart.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
		save(pauseStart);
		
		middle = SubscriptionTestUtil.createDeliverySubscriptionProgress(term, "2017-10-10", 3);
		middle.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
		save(middle);
		
		final I_C_SubscriptionProgress pauseEnd = SubscriptionTestUtil.createDeliverySubscriptionProgress(term, "2017-10-12", 4);
		pauseEnd.setEventType(X_C_SubscriptionProgress.EVENTTYPE_EndOfPause);
		pauseEnd.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
		save(pauseEnd);
		
		SubscriptionTestUtil.createDeliverySubscriptionProgress(term, "2017-11-10", 5);

		assertThat(retrieveAllforTerm()).hasSize(5); // guard
	}
	
	private List<I_C_SubscriptionProgress> retrieveAllforTerm()
	{
		return subscriptionDAO.retrieveSubscriptionProgresses(SubscriptionProgressQuery.term(term).build());
	}
	
	@Test
	public void removePauses_RetrievePauseRecords_FromBeforeFirstPauseRecord_UntilAfterLastPauseRecord()
	{
		final Timestamp dateFromBeforeFirstPauseRecord = TimeUtil.addDays(pauseFrom, -1);
		final Timestamp dateUntilAfterLastPauseRecord = TimeUtil.addDays(pauseUntil, 1);

		final RemovePauses removePauses = new RemovePauses(SubscriptionCommand.get());
		final List<I_C_SubscriptionProgress> pausedRecordsCompletelyWithinFromAndUntil = removePauses.retrieveAllPauseRecords(term, dateFromBeforeFirstPauseRecord, dateUntilAfterLastPauseRecord);
		assertAllPauseRecords(pausedRecordsCompletelyWithinFromAndUntil);
	}

	@Test
	public void removePauses_RetrievePauseRecords_UntilBeforeLastPauseRecord()
	{
		final RemovePauses removePauses = new RemovePauses(SubscriptionCommand.get());
		final List<I_C_SubscriptionProgress> pausedRecordsCompletelyWithinFromAndUntil = removePauses.retrieveAllPauseRecords(term, TimeUtil.addDays(pauseFrom, -1), TimeUtil.addDays(pauseUntil, -1));
		assertAllPauseRecords(pausedRecordsCompletelyWithinFromAndUntil);
	}

	@Test
	public void removePauses_RetrievePauseRecords_FromBeforeFirstPauseRecord_UntilBeforeLastPauseRecord()
	{
		final RemovePauses removePauses = new RemovePauses(SubscriptionCommand.get());
		final List<I_C_SubscriptionProgress> pausedRecordsCompletelyWithinFromAndUntil = removePauses.retrieveAllPauseRecords(term, TimeUtil.addDays(pauseFrom, -1), TimeUtil.addDays(pauseUntil, -1));
		assertAllPauseRecords(pausedRecordsCompletelyWithinFromAndUntil);
	}

	@Test
	public void removePauses_RetrievePauseRecords_FromAfterFirstPauseRecord_UntilBeforeLastPauseRecord()
	{
		final RemovePauses removePauses = new RemovePauses(SubscriptionCommand.get());
		final List<I_C_SubscriptionProgress> pausedRecordsCompletelyWithinFromAndUntil = removePauses.retrieveAllPauseRecords(term, TimeUtil.addDays(pauseFrom, +1), TimeUtil.addDays(pauseUntil, -1));
		assertAllPauseRecords(pausedRecordsCompletelyWithinFromAndUntil);
	}

	@Test
	public void removePauses_RetrievePauseRecords_FromAndUntilBeforeFirstPauseRecord()
	{
		final RemovePauses removePauses = new RemovePauses(SubscriptionCommand.get());
		final List<I_C_SubscriptionProgress> pausedRecords = removePauses.retrieveAllPauseRecords(term, TimeUtil.addDays(pauseFrom, -20), TimeUtil.addDays(pauseFrom, -5));
		assertThat(pausedRecords).isEmpty();
	}

	@Test
	public void removePauses_RetrievePauseRecords_FromAndUntilAfterLastPauseRecord()
	{
		final RemovePauses removePauses = new RemovePauses(SubscriptionCommand.get());
		final List<I_C_SubscriptionProgress> pausedRecords = removePauses.retrieveAllPauseRecords(term, TimeUtil.addDays(pauseUntil, +5), TimeUtil.addDays(pauseUntil, +20));
		assertThat(pausedRecords).isEmpty();
	}

	private void assertAllPauseRecords(@NonNull final List<I_C_SubscriptionProgress> pausedRecords)
	{
		assertThat(pausedRecords).hasSize(3);

		assertThat(pausedRecords.get(0)).satisfies(record -> {
			assertThat(record.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause);
			assertThat(record.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
		});

		assertThat(pausedRecords.get(1)).satisfies(record -> {
			assertThat(record.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
			assertThat(record.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
		});

		assertThat(pausedRecords.get(2)).satisfies(record -> {
			assertThat(record.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_EndOfPause);
			assertThat(record.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
		});
	}
}

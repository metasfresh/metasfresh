package de.metas.contracts.subscription.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Services;

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

public class SubscriptionCommandTest
{
	@Rule
	public final AdempiereTestWatcher testWatcher = new AdempiereTestWatcher();

	private ISubscriptionDAO subscriptionDAO;

	private final Timestamp pauseFrom = TimeUtil.parseTimestamp("2017-09-12");
	private final Timestamp pauseUntil = TimeUtil.parseTimestamp("2017-10-12");

	private I_C_Flatrate_Term term;
	private I_C_SubscriptionProgress first;
	private I_C_SubscriptionProgress middle;
	private I_C_SubscriptionProgress last;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		subscriptionDAO = Services.get(ISubscriptionDAO.class);

		Services.registerService(IShipmentScheduleBL.class, ShipmentScheduleBL.newInstanceForUnitTesting());

		term = newInstance(I_C_Flatrate_Term.class);
		save(term);

		first = SubscriptionTestUtil.createDeliverySubscriptionProgress(term, "2017-09-10", 1);
		middle = SubscriptionTestUtil.createDeliverySubscriptionProgress(term, "2017-10-10", 2);
		last = SubscriptionTestUtil.createDeliverySubscriptionProgress(term, "2017-11-10", 3);

		assertThat(retrieveAllforTerm()).hasSize(3); // guard
	}

	@Test
	public void insertPauseAndCancelOverlappingItems()
	{
		performInsertPause(true);
	}

	@Test
	public void removePauses()
	{
		performInsertPause(true);
		SubscriptionService.get().removePausesAroundTimeframe(term, TimeUtil.addDays(pauseFrom, 2), TimeUtil.addDays(pauseUntil, 3));

		assertAllGoodAfterRemovePauses();
	}

	@Test
	public void removePausesEndDateAfterLastDelivery()
	{
		performInsertPause(true);
		SubscriptionService.get().removePausesAroundTimeframe(term, TimeUtil.addDays(middle.getEventDate(), 2), TimeUtil.addDays(last.getEventDate(), 2));

		assertAllGoodAfterRemovePauses();
	}

	@Test
	public void removePausesStartAndEndOnTheSameDay()
	{
		performInsertPause(true);
		SubscriptionService.get().removePausesAroundTimeframe(term, pauseFrom, pauseFrom);

		assertAllGoodAfterRemovePauses();
	}

	@Test
	public void removePausesStartAndEndOnTheSameDay2()
	{
		performInsertPause(true);
		SubscriptionService.get().removePausesAroundTimeframe(term, middle.getEventDate(), middle.getEventDate());

		assertAllGoodAfterRemovePauses();
	}

	@Test
	public void removePausesStartAndEndOnTheSameDay3()
	{
		performInsertPause(true);
		SubscriptionService.get().removePausesAroundTimeframe(term, pauseUntil, pauseUntil);

		assertAllGoodAfterRemovePauses();
	}

	private void assertAllGoodAfterRemovePauses()
	{
		final List<I_C_SubscriptionProgress> allAfterPauseRemoval = retrieveAllforTerm();
		assertThat(allAfterPauseRemoval).hasSize(3);

		assertThat(allAfterPauseRemoval.get(0).getSeqNo()).isEqualTo(1);
		assertThat(allAfterPauseRemoval.get(1).getSeqNo()).isEqualTo(2);
		assertThat(allAfterPauseRemoval.get(2).getSeqNo()).isEqualTo(3);

		assertThat(allAfterPauseRemoval).allSatisfy(record -> {

			assertThat(record.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
			assertThat(record.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
		});
	}

	private List<I_C_SubscriptionProgress> retrieveAllforTerm()
	{
		return subscriptionDAO.retrieveSubscriptionProgresses(SubscriptionProgressQuery.term(term).build());
	}

	@Test
	public void ignoreRecordsWithShipmentSchedule()
	{
		final I_M_ShipmentSchedule shipmentSchedule = newInstance(I_M_ShipmentSchedule.class);
		save(shipmentSchedule);

		middle.setStatus(X_C_SubscriptionProgress.STATUS_Open);
		middle.setM_ShipmentSchedule(shipmentSchedule);
		save(middle);
		assertThat(middle.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running); // guard

		final boolean assertThatMiddleRecordIsPaused = true;
		performInsertPause(assertThatMiddleRecordIsPaused);

		final List<I_C_SubscriptionProgress> all = retrieveAllforTerm();
		final I_C_SubscriptionProgress middleAfterUpdate = all.get(2);

		assertThat(middleAfterUpdate)
				.as("this record may not be paused because it's already on progress")
				.satisfies(record -> {
					assertThat(record.getC_SubscriptionProgress_ID()).isEqualTo(middle.getC_SubscriptionProgress_ID());
					assertThat(record.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
					assertThat(record.getM_ShipmentSchedule().isClosed()).isTrue();
				});
	}

	private List<I_C_SubscriptionProgress> performInsertPause(final boolean assertMiddleRecordIsPaused)
	{
		final Timestamp middleEventDateBefore = middle.getEventDate();
		final Timestamp lastEventDateBefore = last.getEventDate();

		SubscriptionService.get().insertPause(term, pauseFrom, pauseUntil);

		final List<I_C_SubscriptionProgress> all = retrieveAllforTerm();
		final int numberOfPauseRecords = 2;
		final int numberOfDeliveryRecords = 3;
		assertThat(all).hasSize(numberOfPauseRecords + numberOfDeliveryRecords);

		assertThat(all.get(0).getC_SubscriptionProgress_ID()).isEqualTo(first.getC_SubscriptionProgress_ID());
		assertThat(all.get(4).getC_SubscriptionProgress_ID()).isEqualTo(last.getC_SubscriptionProgress_ID());

		final I_C_SubscriptionProgress createdPauseStart = all.get(1);
		assertThat(createdPauseStart.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause);
		assertThat(createdPauseStart.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
		assertThat(createdPauseStart.getEventDate()).isEqualTo(pauseFrom);
		assertThat(createdPauseStart.getSeqNo()).isEqualByComparingTo(2);

		assertThat(all.get(2).getC_SubscriptionProgress_ID()).isEqualTo(middle.getC_SubscriptionProgress_ID());
		refresh(middle);
		assertThat(middle.getEventDate()).isEqualTo(middleEventDateBefore);
		assertThat(middle.getSeqNo()).isEqualTo(3);
		assertThat(middle.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Delivery);

		if (assertMiddleRecordIsPaused)
		{
			assertThat(middle.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
		}

		final I_C_SubscriptionProgress createdPauseEnd = all.get(3);
		assertThat(createdPauseEnd.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_EndOfPause);
		assertThat(createdPauseEnd.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
		assertThat(createdPauseEnd.getEventDate()).isEqualTo(pauseUntil);
		assertThat(createdPauseEnd.getSeqNo()).isEqualByComparingTo(4);

		refresh(last);
		assertThat(last.getEventDate()).isEqualTo(lastEventDateBefore);
		assertThat(last.getSeqNo()).isEqualTo(5);

		return all;
	}

	@Test
	public void insertPauseAndThenModifyIt()
	{
		performInsertPause(true);

		SubscriptionService.get().insertPause(term, TimeUtil.addDays(middle.getEventDate(), 2), TimeUtil.addDays(last.getEventDate(), 2));

		final List<I_C_SubscriptionProgress> all = retrieveAllforTerm();
		assertThat(all).hasSize(5);

		assertThat(all.get(0)).satisfies(firstResult -> {
			assertThat(firstResult.getC_SubscriptionProgress_ID()).isEqualTo(first.getC_SubscriptionProgress_ID());
			assertThat(firstResult.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
			assertThat(firstResult.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
			assertThat(firstResult.getSeqNo()).isEqualTo(1);
		});

		assertThat(all.get(1)).satisfies(secondResult -> {
			assertThat(secondResult.getC_SubscriptionProgress_ID()).isEqualTo(middle.getC_SubscriptionProgress_ID());
			assertThat(secondResult.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
			assertThat(secondResult.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
			assertThat(secondResult.getSeqNo()).isEqualTo(2);
		});

		assertThat(all.get(2)).satisfies(thirdResult -> {
			assertThat(thirdResult.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
			assertThat(thirdResult.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause);
			assertThat(thirdResult.getSeqNo()).isEqualTo(3);
		});

		assertThat(all.get(3)).satisfies(fourthResult -> {
			assertThat(fourthResult.getC_SubscriptionProgress_ID()).isEqualTo(last.getC_SubscriptionProgress_ID());
			assertThat(fourthResult.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause);
			assertThat(fourthResult.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Delivery);
			assertThat(fourthResult.getSeqNo()).isEqualTo(4);
		});

		assertThat(all.get(4)).satisfies(fithResult -> {
			assertThat(fithResult.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);
			assertThat(fithResult.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_EndOfPause);
			assertThat(fithResult.getSeqNo()).isEqualTo(5);
		});
	}
}

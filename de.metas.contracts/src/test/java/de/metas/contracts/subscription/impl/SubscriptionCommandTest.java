package de.metas.contracts.subscription.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.adempiere.util.Services;
import org.compiere.util.TimeUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_SubscriptionProgress;

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

	private I_C_Flatrate_Term term;
	private I_C_SubscriptionProgress first;
	private I_C_SubscriptionProgress middle;
	private I_C_SubscriptionProgress last;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		subscriptionDAO = Services.get(ISubscriptionDAO.class);

		term = newInstance(I_C_Flatrate_Term.class);
		save(term);

		first = createSubscriptionProgress(term, "2017-09-10", 1);
		middle = createSubscriptionProgress(term, "2017-10-10", 2);
		last = createSubscriptionProgress(term, "2017-11-10", 3);

		assertThat(subscriptionDAO.retrieveSubscriptionProgress(term)).hasSize(3); // guard
	}

	private I_C_SubscriptionProgress createSubscriptionProgress(
			final I_C_Flatrate_Term term,
			final String eventDate,
			final int seqNo)
	{
		final I_C_SubscriptionProgress subscriptionProgress = newInstance(I_C_SubscriptionProgress.class);
		POJOWrapper.setInstanceName(subscriptionProgress, eventDate);
		subscriptionProgress.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Lieferung);
		subscriptionProgress.setC_Flatrate_Term(term);
		subscriptionProgress.setEventDate(TimeUtil.parseTimestamp(eventDate));
		subscriptionProgress.setSeqNo(seqNo);
		save(subscriptionProgress);

		return subscriptionProgress;
	}

	@Test
	public void insertPauseAndShiftOverlappingItems()
	{
		final Timestamp pauseFrom = TimeUtil.parseTimestamp("2017-09-12");
		final Timestamp pauseEnd = TimeUtil.parseTimestamp("2017-10-12");

		SubscriptionCommand.insertPauseAndShiftOverlappingItems(term, pauseFrom, pauseEnd);

		final List<I_C_SubscriptionProgress> all = subscriptionDAO.retrieveSubscriptionProgress(term);
		assertThat(all).hasSize(5);
		assertThat(all.get(0).getC_SubscriptionProgress_ID()).isEqualTo(first.getC_SubscriptionProgress_ID());
		assertThat(all.get(4).getC_SubscriptionProgress_ID()).isEqualTo(last.getC_SubscriptionProgress_ID());

		final I_C_SubscriptionProgress createdPauseStart = all.get(1);
		assertThat(createdPauseStart.getEventDate()).isEqualTo(pauseFrom);
		assertThat(createdPauseStart.getSeqNo()).isEqualByComparingTo(2);

		final I_C_SubscriptionProgress createdPauseEnd = all.get(2);
		assertThat(createdPauseEnd.getEventDate()).isEqualTo(pauseEnd);
		assertThat(createdPauseEnd.getSeqNo()).isEqualByComparingTo(3);

		refresh(middle);
		assertThat(middle.getEventDate()).isEqualTo(TimeUtil.addDays(pauseEnd, 1));
		assertThat(middle.getSeqNo()).isEqualTo(4);

		refresh(last);
		assertThat(last.getEventDate()).isEqualTo(TimeUtil.parseTimestamp("2017-11-13"));
		assertThat(last.getSeqNo()).isEqualTo(5);
	}

	@Test
	public void insertPauseAndCancelOverlappingItems()
	{
		final Timestamp pauseFrom = TimeUtil.parseTimestamp("2017-09-12");
		final Timestamp pauseEnd = TimeUtil.parseTimestamp("2017-10-12");

		final Timestamp middleEventDateBefore = middle.getEventDate();
		final Timestamp lastEventDateBefore = last.getEventDate();

		SubscriptionCommand.insertPauseAndPauseOverlappingItems(term, pauseFrom, pauseEnd);

		final List<I_C_SubscriptionProgress> all = subscriptionDAO.retrieveSubscriptionProgress(term);
		assertThat(all).hasSize(5);
		assertThat(all.get(0).getC_SubscriptionProgress_ID()).isEqualTo(first.getC_SubscriptionProgress_ID());
		assertThat(all.get(4).getC_SubscriptionProgress_ID()).isEqualTo(last.getC_SubscriptionProgress_ID());

		final I_C_SubscriptionProgress createdPauseStart = all.get(1);
		assertThat(createdPauseStart.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Abopause_Beginn);
		assertThat(createdPauseStart.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Lieferpause);
		assertThat(createdPauseStart.getEventDate()).isEqualTo(pauseFrom);
		assertThat(createdPauseStart.getSeqNo()).isEqualByComparingTo(2);

		assertThat(all.get(2).getC_SubscriptionProgress_ID()).isEqualTo(middle.getC_SubscriptionProgress_ID());
		refresh(middle);
		assertThat(middle.getEventDate()).isEqualTo(middleEventDateBefore);
		assertThat(middle.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Lieferung);
		assertThat(middle.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Lieferpause);
		assertThat(middle.getSeqNo()).isEqualTo(3);
		
		
		final I_C_SubscriptionProgress createdPauseEnd = all.get(3);
		assertThat(createdPauseEnd.getEventType()).isEqualTo(X_C_SubscriptionProgress.EVENTTYPE_Abopause_Ende);
		assertThat(createdPauseEnd.getContractStatus()).isEqualTo(X_C_SubscriptionProgress.CONTRACTSTATUS_Laufend);
		assertThat(createdPauseEnd.getEventDate()).isEqualTo(pauseEnd);
		assertThat(createdPauseEnd.getSeqNo()).isEqualByComparingTo(4);

		refresh(last);
		assertThat(last.getEventDate()).isEqualTo(lastEventDateBefore);
		assertThat(last.getSeqNo()).isEqualTo(5);
	}

}

package de.metas.contracts.subscription.impl.subscriptioncommands;

import static org.adempiere.model.InterfaceWrapperHelper.delete;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.util.Services;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.contracts.subscription.impl.SubscriptionService;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class RemovePauses
{
	private final SubscriptionService subscriptionCommand;

	public RemovePauses(@NonNull final SubscriptionService subscriptionCommand)
	{
		this.subscriptionCommand = subscriptionCommand;
	}

	public void removePausesAroundTimeframe(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp pausesFrom,
			@NonNull final Timestamp pausesUntil)
	{
		final List<I_C_SubscriptionProgress> allPauseRecords = retrieveAllPauseRecords(term, pausesFrom, pausesUntil);
		if (allPauseRecords.isEmpty())
		{
			return;
		}

		final I_C_SubscriptionProgress lastPauseRecord = allPauseRecords.get(allPauseRecords.size() - 1);
		final SubscriptionProgressQuery query = SubscriptionProgressQuery.startingWith(lastPauseRecord).build(); // get that query now, because the record is a pause-end record and will be deleted.

		final int seqNoOffset = deletePauseBeginEndRecordsAndUpdatePausedRecords(allPauseRecords);

		final List<I_C_SubscriptionProgress> recordsAfterLastPause = Services.get(ISubscriptionDAO.class).retrieveSubscriptionProgresses(query);
		for (final I_C_SubscriptionProgress record : recordsAfterLastPause)
		{
			subtractFromSeqNoAndSave(record, seqNoOffset);

		}
	}

	@VisibleForTesting
	public List<I_C_SubscriptionProgress> retrieveAllPauseRecords(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp pauseFrom,
			@NonNull final Timestamp pauseUntil)
	{
		final List<I_C_SubscriptionProgress> sps = subscriptionCommand.retrieveNextSPsAndLogIfEmpty(term, pauseFrom);
		if (sps.isEmpty())
		{
			return sps;
		}

		final List<I_C_SubscriptionProgress> allPauseRecords;

		final I_C_SubscriptionProgress firstSp = sps.get(0);

		if (isWithinPause(firstSp))
		{
			final I_C_SubscriptionProgress firstPauseRecord = retrieveFirstPauseRecordSearchBackwards(firstSp);
			allPauseRecords = retrieveAllPauseRecords(firstPauseRecord, pauseUntil);
		}
		else
		{
			final I_C_SubscriptionProgress firstPauseRecord = retrieveFirstPauseRecordSearchForwards(sps, pauseUntil);
			allPauseRecords = retrieveAllPauseRecords(firstPauseRecord, pauseUntil);
		}
		return allPauseRecords;
	}

	private static I_C_SubscriptionProgress retrieveFirstPauseRecordSearchForwards(
			@NonNull final List<I_C_SubscriptionProgress> sps,
			@NonNull final Timestamp pauseUntil)
	{
		for (final I_C_SubscriptionProgress sp : sps)
		{
			final boolean spAfterOrAtDate = !sp.getEventDate().before(pauseUntil);
			if (spAfterOrAtDate)
			{
				return null;
			}
			if (isWithinPause(sp))
			{
				return sp;
			}
		}
		return null;
	}

	private static List<I_C_SubscriptionProgress> retrieveAllPauseRecords(
			@Nullable final I_C_SubscriptionProgress firstPauseRecord,
			@NonNull final Timestamp pauseUntil)
	{
		if (firstPauseRecord == null)
		{
			return ImmutableList.of();
		}

		final Builder<I_C_SubscriptionProgress> builder = ImmutableList.builder();

		final List<I_C_SubscriptionProgress> allPauseRecords;
		final SubscriptionProgressQuery query = SubscriptionProgressQuery.startingWith(firstPauseRecord)
				.build();
		allPauseRecords = Services.get(ISubscriptionDAO.class).retrieveSubscriptionProgresses(query);

		boolean afterPauseUntil = firstPauseRecord.getEventDate().after(pauseUntil);

		for (final I_C_SubscriptionProgress sp : allPauseRecords)
		{
			afterPauseUntil = sp.getEventDate().after(pauseUntil);
			if (afterPauseUntil && !isWithinPause(sp))
			{
				break;
			}

			if (isWithinPause(sp))
			{
				builder.add(sp);
			}
		}

		return builder.build();
	}

	private static boolean isWithinPause(final I_C_SubscriptionProgress subscriptionProgress)
	{
		final boolean withinPause = subscriptionProgress.getContractStatus().equals(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause)
				|| subscriptionProgress.getEventType().equals(X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause)
				|| subscriptionProgress.getEventType().equals(X_C_SubscriptionProgress.EVENTTYPE_EndOfPause);
		return withinPause;
	}

	private static I_C_SubscriptionProgress retrieveFirstPauseRecordSearchBackwards(final I_C_SubscriptionProgress firstSp)
	{
		final SubscriptionProgressQuery query = SubscriptionProgressQuery.endingRightBefore(firstSp)
				.includedContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_DeliveryPause)
				.build();
		final I_C_SubscriptionProgress preceedingPauseRecord = Services.get(ISubscriptionDAO.class).retrieveFirstSubscriptionProgress(query);

		final I_C_SubscriptionProgress firstPauseRecord = preceedingPauseRecord != null ? preceedingPauseRecord : firstSp;
		return firstPauseRecord;
	}

	private static int deletePauseBeginEndRecordsAndUpdatePausedRecords(@NonNull final List<I_C_SubscriptionProgress> allPauseRecords)
	{
		int seqNoOffset = 0;

		for (final I_C_SubscriptionProgress pauseRecord : allPauseRecords)
		{

			final boolean pauseStartOrEnd = X_C_SubscriptionProgress.EVENTTYPE_BeginOfPause.equals(pauseRecord.getEventType()) || X_C_SubscriptionProgress.EVENTTYPE_EndOfPause.equals(pauseRecord.getEventType());
			if (pauseStartOrEnd)
			{
				delete(pauseRecord);
				seqNoOffset++;
				continue;
			}
			else if (isWithinPause(pauseRecord))
			{
				pauseRecord.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Running);

				if (pauseRecord.getM_ShipmentSchedule_ID() > 0)
				{
					Services.get(IShipmentScheduleBL.class).openShipmentSchedule(pauseRecord.getM_ShipmentSchedule());
				}
			}
			subtractFromSeqNoAndSave(pauseRecord, seqNoOffset);
		}
		return seqNoOffset;
	}

	private static void subtractFromSeqNoAndSave(final I_C_SubscriptionProgress record, int seqNoOffset)
	{
		record.setSeqNo(record.getSeqNo() - seqNoOffset); // might be a not-within-pause-record if there are multiple pause periods between pausesFrom and pausesUntil
		save(record);
	}

}

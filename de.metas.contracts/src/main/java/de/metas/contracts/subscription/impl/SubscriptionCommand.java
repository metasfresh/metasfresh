package de.metas.contracts.subscription.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.dao.ICompositeQueryUpdater;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.CompareQueryFilter.Operator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableList.Builder;
import com.jgoodies.common.base.Objects;

import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.contracts.subscription.impl.subscriptioncommands.RemovePauses;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_SubscriptionProgress;
import de.metas.i18n.IMsgBL;
import lombok.Builder.Default;
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

public class SubscriptionCommand
{
	public static final String MSG_NO_SPS_AFTER_DATE_1P = "Subscription.NoSpsAfterDate_1P";

	public static SubscriptionCommand get()
	{
		return new SubscriptionCommand();
	}

	private SubscriptionCommand()
	{
	}

	public void insertPause(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp pauseFrom,
			@NonNull final Timestamp pauseUntil)
	{
		removePauses(term, pauseFrom, pauseUntil);

		final List<I_C_SubscriptionProgress> sps = retrieveNextSPsAndLogIfEmpty(term, pauseFrom);
		if (sps.isEmpty())
		{
			return;
		}

		final I_C_SubscriptionProgress firstSpAfterBeginOfPause = sps.get(0);
		createBeginOfPause(term, pauseFrom, firstSpAfterBeginOfPause);

		final ImmutableList<I_C_SubscriptionProgress> updatedSpsWithinPause = updateAndCollectRecordWithinPause(sps, pauseUntil);

		final int endOfPauseSeqNo = computeEndOfPauseSeqNo(firstSpAfterBeginOfPause, updatedSpsWithinPause);

		createEndOfPause(term, pauseUntil, endOfPauseSeqNo);

		final ImmutableList<I_C_SubscriptionProgress> spsAfterPause = collectSpsAfterPause(sps, pauseUntil);

		increaseSeqNosByTwo(spsAfterPause);

		sps.forEach(InterfaceWrapperHelper::save);
	}

	public final List<I_C_SubscriptionProgress> retrieveNextSPsAndLogIfEmpty(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp pauseFrom)
	{
		final ISubscriptionDAO subscriptionDAO = Services.get(ISubscriptionDAO.class);
		final SubscriptionProgressQuery query = SubscriptionProgressQuery.builder()
				.term(term)
				.eventDateNotBefore(pauseFrom)
				.build();

		final List<I_C_SubscriptionProgress> sps = subscriptionDAO.retrieveSubscriptionProgresses(query);
		if (sps.isEmpty())
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			Loggables.get().addLog(msgBL.getMsg(Env.getCtx(), MSG_NO_SPS_AFTER_DATE_1P, new Object[] { pauseFrom }));
		}
		return sps;
	}

	private void createBeginOfPause(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp pauseFrom,
			@NonNull final I_C_SubscriptionProgress nextSP)
	{
		final I_C_SubscriptionProgress pauseBegin = newInstance(I_C_SubscriptionProgress.class);

		pauseBegin.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Abopause_Beginn);
		pauseBegin.setC_Flatrate_Term(term);
		pauseBegin.setStatus(X_C_SubscriptionProgress.STATUS_Geplant);
		pauseBegin.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Lieferpause);
		pauseBegin.setEventDate(pauseFrom);
		pauseBegin.setSeqNo(nextSP.getSeqNo());
		save(pauseBegin);
	}

	private ImmutableList<I_C_SubscriptionProgress> updateAndCollectRecordWithinPause(
			@NonNull final List<I_C_SubscriptionProgress> sps,
			@NonNull final Timestamp pauseUntil)
	{
		final Builder<I_C_SubscriptionProgress> spsWithinPause = ImmutableList.builder();

		for (final I_C_SubscriptionProgress sp : sps)
		{
			if (sp.getEventDate().after(pauseUntil))
			{
				continue;
			}
			spsWithinPause.add(sp);
			sp.setSeqNo(sp.getSeqNo() + 1);

			if (Objects.equals(sp.getStatus(), X_C_SubscriptionProgress.STATUS_Geplant))
			{
				sp.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Lieferpause);
			}
		}

		return spsWithinPause.build();
	}

	private static int computeEndOfPauseSeqNo(final I_C_SubscriptionProgress firstSpAfterBeginOfPause, final ImmutableList<I_C_SubscriptionProgress> spsWithinPause)
	{
		return spsWithinPause.isEmpty() ? firstSpAfterBeginOfPause.getSeqNo() + 1 : spsWithinPause.get(spsWithinPause.size() - 1).getSeqNo() + 1;
	}

	private void createEndOfPause(
			final I_C_Flatrate_Term term,
			final Timestamp pauseUntil,
			final int seqNoForPauseEndSp)
	{
		final I_C_SubscriptionProgress pauseEnd = newInstance(I_C_SubscriptionProgress.class);

		pauseEnd.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Abopause_Ende);
		pauseEnd.setC_Flatrate_Term(term);
		pauseEnd.setStatus(X_C_SubscriptionProgress.STATUS_Geplant);
		pauseEnd.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Laufend);
		pauseEnd.setEventDate(pauseUntil);
		pauseEnd.setSeqNo(seqNoForPauseEndSp);
		save(pauseEnd);
	}

	private ImmutableList<I_C_SubscriptionProgress> collectSpsAfterPause(final List<I_C_SubscriptionProgress> sps, Timestamp pauseUntil)
	{
		final ImmutableList<I_C_SubscriptionProgress> spsAfterPause = sps.stream()
				.filter(sp -> sp.getEventDate().after(pauseUntil))
				.collect(ImmutableList.toImmutableList());
		return spsAfterPause;
	}

	private void increaseSeqNosByTwo(@NonNull final List<I_C_SubscriptionProgress> sps)
	{
		final int seqNoOffSet = 2;
		for (final I_C_SubscriptionProgress currentSP : sps)
		{
			currentSP.setSeqNo(currentSP.getSeqNo() + seqNoOffSet);
		}
	}

	public static int changeRecipient(@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final ICompositeQueryUpdater<I_C_SubscriptionProgress> updater = queryBL.createCompositeQueryUpdater(I_C_SubscriptionProgress.class)
				.addSetColumnValue(I_C_SubscriptionProgress.COLUMNNAME_DropShip_BPartner_ID, changeRecipientsRequest.getDropShip_BPartner_ID())
				.addSetColumnValue(I_C_SubscriptionProgress.COLUMNNAME_DropShip_Location_ID, changeRecipientsRequest.getDropShip_Location_ID())
				.addSetColumnValue(I_C_SubscriptionProgress.COLUMNNAME_DropShip_User_ID, changeRecipientsRequest.getDropShip_User_ID());

		return queryBL
				.createQueryBuilder(I_C_SubscriptionProgress.class)
				.addOnlyActiveRecordsFilter()
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.GREATER_OR_EQUAL, changeRecipientsRequest.getDateFrom())
				.addCompareFilter(I_C_SubscriptionProgress.COLUMN_EventDate, Operator.LESS_OR_EQUAL, changeRecipientsRequest.getDateTo())
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_C_Flatrate_Term_ID, changeRecipientsRequest.getTerm().getC_Flatrate_Term_ID())
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_EventType, X_C_SubscriptionProgress.EVENTTYPE_Lieferung)
				.addEqualsFilter(I_C_SubscriptionProgress.COLUMN_Status, X_C_SubscriptionProgress.STATUS_Geplant)
				.create()
				.update(updater);

	}

	@lombok.Builder
	@lombok.Value
	public static final class ChangeRecipientsRequest
	{
		@NonNull
		I_C_Flatrate_Term term;

		@NonNull
		Timestamp dateFrom;

		@NonNull
		Timestamp dateTo;

		@NonNull
		Integer DropShip_BPartner_ID;

		@NonNull
		Integer DropShip_Location_ID;

		@Default
		Integer DropShip_User_ID = 0;
	}

	public void removePauses(I_C_Flatrate_Term term, Timestamp pauseFrom, Timestamp pauseUntil)
	{
		new RemovePauses(this).removePauses(term, pauseFrom, pauseUntil);
	}
}

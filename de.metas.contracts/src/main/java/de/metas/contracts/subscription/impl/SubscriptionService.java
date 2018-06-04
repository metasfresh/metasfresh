package de.metas.contracts.subscription.impl;

import java.sql.Timestamp;
import java.util.List;

import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import com.google.common.base.Preconditions;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.contracts.subscription.impl.subscriptioncommands.ChangeRecipient;
import de.metas.contracts.subscription.impl.subscriptioncommands.InsertPause;
import de.metas.contracts.subscription.impl.subscriptioncommands.RemovePauses;
import de.metas.i18n.IMsgBL;
import lombok.Builder;
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

public class SubscriptionService
{
	public static final String MSG_NO_SPS_AFTER_DATE_1P = "Subscription.NoSpsAfterDate_1P";

	public static SubscriptionService get()
	{
		return new SubscriptionService();
	}

	private SubscriptionService()
	{
	}

	public void insertPause(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp dateFrom,
			@NonNull final Timestamp dateTo)
	{
		new InsertPause(this).insertPause(term, dateFrom, dateTo);
	}

	public static int changeRecipient(@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		return ChangeRecipient.changeRecipient(changeRecipientsRequest);
	}

	@lombok.Value
	public static final class ChangeRecipientsRequest
	{
		@NonNull
		I_C_Flatrate_Term term;
		Timestamp dateFrom;
		Timestamp dateTo;

		int DropShip_BPartner_ID;
		int DropShip_Location_ID;
		int DropShip_User_ID;

		boolean IsPermanentRecipient;

		@Builder
		private ChangeRecipientsRequest(
				@NonNull final I_C_Flatrate_Term term,
				final Timestamp dateFrom,
				final Timestamp dateTo,
				final int DropShip_BPartner_ID,
				final int DropShip_Location_ID,
				final int DropShip_User_ID,
				boolean IsPermanentRecipient)
		{
			this.term = term;

			this.IsPermanentRecipient = IsPermanentRecipient;

			if (!IsPermanentRecipient)
			{
				Preconditions.checkNotNull(dateFrom, "The dateForm shall not be null");
				Preconditions.checkNotNull(dateTo, "The dateTo shall not be null");
				Preconditions.checkArgument(!dateTo.before(dateFrom), "The given dateTo may not be before the given dateFrom; dateFrom=%s; dateTo=%s", dateFrom, dateTo);
			}
			this.dateFrom = dateFrom;
			this.dateTo = dateTo;

			Preconditions.checkArgument(DropShip_BPartner_ID > 0, "The given DropShip_BPartner_ID may not be <= 0");
			this.DropShip_BPartner_ID = DropShip_BPartner_ID;

			Preconditions.checkArgument(DropShip_Location_ID > 0, "The given DropShip_Location_ID may not be <= 0");
			this.DropShip_Location_ID = DropShip_Location_ID;
			this.DropShip_User_ID = DropShip_User_ID;

		}
	}

	public void removePausesAroundTimeframe(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp pauseFrom,
			@NonNull final Timestamp pauseUntil)
	{
		new RemovePauses(this).removePausesAroundTimeframe(term, pauseFrom, pauseUntil);
	}

	public void removePausesAroundDate(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp date)
	{
		new RemovePauses(this).removePausesAroundTimeframe(term, date, date);
	}


	public void removeAllPauses(final I_C_Flatrate_Term term)
	{
		final Timestamp distantPast = TimeUtil.getDay(1970, 1, 1);
		final Timestamp distantFuture = TimeUtil.getDay(9999, 12, 31);

		new RemovePauses(this).removePausesAroundTimeframe(term, distantPast, distantFuture);

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

}

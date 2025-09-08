package de.metas.contracts.subscription.impl;

import com.google.common.base.Preconditions;
import de.metas.bpartner.BPartnerId;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;
import de.metas.cache.model.ModelCacheInvalidationService;
import de.metas.cache.model.ModelCacheInvalidationTiming;
import de.metas.contracts.FlatrateDataId;
import de.metas.contracts.model.I_C_Flatrate_Data;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.contracts.subscription.ISubscriptionDAO.SubscriptionProgressQuery;
import de.metas.contracts.subscription.impl.subscriptioncommands.ChangeRecipient;
import de.metas.contracts.subscription.impl.subscriptioncommands.InsertPause;
import de.metas.contracts.subscription.impl.subscriptioncommands.RemovePauses;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import java.sql.Timestamp;
import java.util.List;

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
	public static final AdMessageKey MSG_NO_SPS_AFTER_DATE_1P = AdMessageKey.of("Subscription.NoSpsAfterDate_1P");

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
		resetCache(BPartnerId.ofRepoId(term.getBill_BPartner_ID()), FlatrateDataId.ofRepoId(term.getC_Flatrate_Data_ID()));
	}

	public static int changeRecipient(@NonNull final ChangeRecipientsRequest changeRecipientsRequest)
	{
		final int result = ChangeRecipient.changeRecipient(changeRecipientsRequest);
		final I_C_Flatrate_Term term = changeRecipientsRequest.getTerm();
		resetCache(BPartnerId.ofRepoId(term.getBill_BPartner_ID()), FlatrateDataId.ofRepoId(term.getC_Flatrate_Data_ID()));
		return result;

	}

	@lombok.Value
	public static class ChangeRecipientsRequest
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
				final boolean IsPermanentRecipient)
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
		resetCache(BPartnerId.ofRepoId(term.getBill_BPartner_ID()), FlatrateDataId.ofRepoId(term.getC_Flatrate_Data_ID()));
	}

	public void removePausesAroundDate(
			@NonNull final I_C_Flatrate_Term term,
			@NonNull final Timestamp date)
	{
		new RemovePauses(this).removePausesAroundTimeframe(term, date, date);
		resetCache(BPartnerId.ofRepoId(term.getBill_BPartner_ID()), FlatrateDataId.ofRepoId(term.getC_Flatrate_Data_ID()));
	}

	public void removeAllPauses(final I_C_Flatrate_Term term)
	{
		final Timestamp distantPast = TimeUtil.getDay(1970, 1, 1);
		final Timestamp distantFuture = TimeUtil.getDay(9999, 12, 31);

		new RemovePauses(this).removePausesAroundTimeframe(term, distantPast, distantFuture);
		resetCache(BPartnerId.ofRepoId(term.getBill_BPartner_ID()), FlatrateDataId.ofRepoId(term.getC_Flatrate_Data_ID()));
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
			Loggables.addLog(msgBL.getMsg(Env.getCtx(), MSG_NO_SPS_AFTER_DATE_1P, new Object[] { pauseFrom }));
		}
		return sps;
	}

	/**
	 * This is needed because no direct parent-child relationship can be established between these tables, as the {@code I_C_SubscriptionProgress} tabs would want to show
	 * records for which the C_BPartner_ID in context is either the recipient or the contract holder.
	 */
	private static void resetCache(@NonNull final BPartnerId bpartnerId, @NonNull final FlatrateDataId flatrateDataId)
	{
		final ModelCacheInvalidationService modelCacheInvalidationService = ModelCacheInvalidationService.get();
		modelCacheInvalidationService.invalidate(
				CacheInvalidateMultiRequest.of(
						CacheInvalidateRequest.allChildRecords(I_C_Flatrate_Data.Table_Name, flatrateDataId, I_C_SubscriptionProgress.Table_Name),
						CacheInvalidateRequest.allChildRecords(I_C_BPartner.Table_Name, bpartnerId, I_C_SubscriptionProgress.Table_Name)),
				ModelCacheInvalidationTiming.AFTER_CHANGE
		);
	}

}

package de.metas.contracts.subscription;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.contracts.flatrate.interfaces.I_C_OLCand;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.subscription.model.I_C_OrderLine;
import lombok.Builder.Default;
import lombok.NonNull;
import lombok.Singular;

public interface ISubscriptionDAO extends ISingletonService
{
	List<I_C_SubscriptionProgress> retrieveSubscriptionProgresses(SubscriptionProgressQuery query);

	I_C_SubscriptionProgress retrieveFirstSubscriptionProgress(SubscriptionProgressQuery query);

	@lombok.Value
	@lombok.Builder
	public class SubscriptionProgressQuery
	{
		public static SubscriptionProgressQueryBuilder startingWith(@NonNull final I_C_SubscriptionProgress subscriptionProgress)
		{
			final I_C_Flatrate_Term term = subscriptionProgress.getC_Flatrate_Term();
			return term(term).seqNoNotLessThan(subscriptionProgress.getSeqNo());
		}

		public static SubscriptionProgressQueryBuilder endingRightBefore(@NonNull final I_C_SubscriptionProgress subscriptionProgress)
		{
			final I_C_Flatrate_Term term = subscriptionProgress.getC_Flatrate_Term();
			return term(term).seqNoLessThan(subscriptionProgress.getSeqNo());
		}
		
		public static SubscriptionProgressQueryBuilder term(@NonNull final I_C_Flatrate_Term term)
		{
			return builder().term(term);
		}

		@NonNull
		I_C_Flatrate_Term term;

		Timestamp eventDateNotBefore;

		Timestamp eventDateNotAfter;
		
		@Default
		int seqNoNotLessThan = 0;

		@Default
		int seqNoLessThan = 0;

		/** never {@code null}. Empty means "don't filter by status altogether". */
		@Singular
		List<String> excludedStatuses;

		/** never {@code null}. Empty means "don't filter by status altogether". */
		@Singular
		List<String> includedStatuses;
		
		/** never {@code null}. Empty means "don't filter by status altogether". */
		@Singular
		List<String> includedContractStatuses;
	}

	/**
	 * Loads all {@link I_C_SubscriptionProgress} records that have event type
	 * {@link X_C_SubscriptionProgress#EVENTTYPE_Lieferung} and status {@link X_C_SubscriptionProgress#STATUS_Geplant}
	 * or {@link X_C_SubscriptionProgress#STATUS_Verzoegert}, ordered by
	 * {@link I_C_SubscriptionProgress#COLUMNNAME_EventDate} and {@link I_C_SubscriptionProgress#COLUMNNAME_SeqNo}.
	 * 
	 * @param date
	 *            the records' event date is before or equal.
	 * @param trxName
	 * @return
	 */
	List<I_C_SubscriptionProgress> retrievePlannedAndDelayedDeliveries(Properties ctx, Timestamp date, String trxName);

	/**
	 * 
	 * @param ol
	 * @return {@code true} if there is at lease one term that references the given <code>ol</code> via its <code>C_OrderLine_Term_ID</code> column.
	 */
	boolean existsTermForOl(I_C_OrderLine ol);

	/**
	 * Retrieves the terms that are connection to the given <code>olCand</code> via an active
	 * <code>C_Contract_Term_Alloc</code> record.
	 * 
	 * @param olCand
	 * @return
	 */
	List<I_C_Flatrate_Term> retrieveTermsForOLCand(I_C_OLCand olCand);

	/**
	 * Insert a new {@link I_C_SubscriptionProgress} after the given predecessor. All values from the predecessor are
	 * copied to the new one, only <code>SeqNo</code> is set to the predecessor's <code>SeqNo+1</code>. The
	 * <code>SeqNo</code>s of all existing {@link I_C_SubscriptionProgress} s that are already after
	 * <code>predecessor</code> are increased by one.
	 * 
	 * @param predecessor
	 * @param trxName
	 * @return
	 */
	I_C_SubscriptionProgress insertNewDelivery(I_C_SubscriptionProgress predecessor);

	<T extends I_C_OLCand> List<T> retrieveOLCands(I_C_Flatrate_Term term, Class<T> clazz);

}

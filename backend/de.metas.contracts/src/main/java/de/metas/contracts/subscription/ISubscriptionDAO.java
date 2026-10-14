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
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import de.metas.contracts.flatrate.interfaces.I_C_OLCand;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_SubscriptionProgress;
import de.metas.contracts.model.X_C_SubscriptionProgress;
import de.metas.contracts.order.model.I_C_OrderLine;
import de.metas.util.ISingletonService;
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
	 * Iterates the delivery {@link I_C_SubscriptionProgress} records of {@code ctx}'s client with status {@link X_C_SubscriptionProgress#STATUS_Planned}
	 * or {@link X_C_SubscriptionProgress#STATUS_Delayed} and an event date before or equal to {@code date}, of terms whose product is an Item,
	 * ordered by {@code EventDate}, {@code C_Flatrate_Term_ID}, {@code SeqNo}, loading {@code pageSize} records at a time.
	 * The caller must close the iterator.
	 */
	Iterator<I_C_SubscriptionProgress> retrievePlannedAndDelayedDeliveries(Properties ctx, Timestamp date, String trxName, int pageSize);

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

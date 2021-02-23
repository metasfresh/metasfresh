package de.metas.material.dispo.commons.candidate;

import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.stock.ResetStockPInstanceId;
import de.metas.util.Check;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

/*
 * #%L
 * metasfresh-material-dispo-commons
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

@Value
public class TransactionDetail
{

	public static TransactionDetail forQuery(final int transactionId)
	{
		return new TransactionDetail(
				null /* quantity */,
				null /* storageAttributesKey */,
				-1 /* attributeSetInstanceId */,
				transactionId,
				-1 /* stockId */,
				null /* resetStockAdPinstanceId */,
				null, /* transactionDate */
				false, /* complete */
				null /* rebookedFromCandidateId */);
	}

	/**
	 * true means that this detail can be persisted; false means that id can still be part of a query.
	 */
	boolean complete;

	BigDecimal quantity;

	/**
	 * Used in queries if > 0.
	 */
	int transactionId;

	/**
	 * If there was no inventory, but MD_Stock had to be reset from M_HU_Storage.
	 * Also used in queries if set.
	 */
	ResetStockPInstanceId resetStockPInstanceId;

	AttributesKey storageAttributesKey;

	int attributeSetInstanceId;

	/**
	 * {@code MD_Stock_ID}
	 */
	int stockId;

	Instant transactionDate;

	/**
	 * Often there is a transaction-event that is related to an existing candidate, but has a different date or attributes.
	 * <p>
	 * E.g. a supply-candidate might anticipate a PP_Order receipt,
	 * but the actual transaction for one of the PP_Order's receipts might have a date before the supply candidate's date.
	 * <p>
	 * In such a case, the transaction-quantity is subtracted from the preexisting supply-candidate (roughly speaking),
	 * and a new candidate is created.
	 * <p>
	 * The field {@code rebookedFromCandidateId} from the new candidate then references the preexisting supply-candidate.
	 */
	CandidateId rebookedFromCandidateId;

	@Builder(toBuilder = true)
	private TransactionDetail(
			@Nullable final BigDecimal quantity,
			@Nullable final AttributesKey storageAttributesKey,
			final int attributeSetInstanceId,
			final int transactionId,
			final int stockId,
			@Nullable final ResetStockPInstanceId resetStockPInstanceId,
			@Nullable final Instant transactionDate,
			final boolean complete,
			@Nullable final CandidateId rebookedFromCandidateId)
	{
		this.complete = complete;

		Check.assume(transactionId > 0 || resetStockPInstanceId != null,
				"From the given parameters transactionId={} and resetStockPInstanceId={}, at least one needs to be set", transactionId, resetStockPInstanceId);
		this.transactionId = transactionId;

		Check.assume(!complete || quantity != null, "The given parameter quantity may not be null because complete=true; transactionId={}; resetStockAdPinstanceId={}", transactionId, resetStockPInstanceId);
		this.quantity = quantity;

		Check.assume(!complete || transactionDate != null, "The given parameter transactionDate may not be null because complete=true; transactionId={}; resetStockAdPinstanceId={}", transactionId, resetStockPInstanceId);
		this.transactionDate = transactionDate;

		this.storageAttributesKey = storageAttributesKey;
		this.attributeSetInstanceId = attributeSetInstanceId;

		this.stockId = stockId;
		this.resetStockPInstanceId = resetStockPInstanceId;

		this.rebookedFromCandidateId = rebookedFromCandidateId;
	}

	public TransactionDetail withRebookedFromCandidateId(@NonNull final CandidateId candidateId)
	{
		return toBuilder().rebookedFromCandidateId(candidateId).build();
	}
}

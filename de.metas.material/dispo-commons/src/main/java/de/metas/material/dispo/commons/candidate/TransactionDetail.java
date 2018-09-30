package de.metas.material.dispo.commons.candidate;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

import de.metas.material.event.commons.AttributesKey;
import lombok.NonNull;
import lombok.Value;

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
	public static TransactionDetail forCandidateOrQuery(
			@NonNull final BigDecimal quantity,
			@NonNull final AttributesKey storageAttributesKey,
			final int attributeSetInstanceId,
			final int transactionId)
	{
		return new TransactionDetail(
				quantity,
				storageAttributesKey,
				attributeSetInstanceId,
				transactionId,
				true /*complete*/);
	}

	public static TransactionDetail forQuery(final int transactionId)
	{
		return new TransactionDetail(
				null /*quantity*/,
				null /*storageAttributesKey*/,
				-1 /*attributeSetInstanceId*/,
				transactionId,
				false /*complete*/);
	}

	boolean complete;

	BigDecimal quantity;

	int transactionId;

	AttributesKey storageAttributesKey;

	int attributeSetInstanceId;

	public TransactionDetail(
			final BigDecimal quantity,
			final AttributesKey storageAttributesKey,
			final int attributeSetInstanceId,
			final int transactionId,
			final boolean complete)
	{
		this.complete = complete;

		Preconditions.checkArgument(transactionId > 0, "The given parameter transactionId=%s needs to be > 0", transactionId);
		this.transactionId = transactionId;

		Preconditions.checkArgument(!complete || quantity != null, "The given parameter quantity may not be null because complete=true; transactionId=%s", transactionId);
		this.quantity = quantity;

		this.storageAttributesKey = storageAttributesKey;
		this.attributeSetInstanceId = attributeSetInstanceId;
	}
}

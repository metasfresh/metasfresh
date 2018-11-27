package de.metas.material.dispo.commons.candidate;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

import com.google.common.base.Preconditions;

import de.metas.material.event.commons.AttributesKey;

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
				-1 /* resetStockAdPinstanceId */,
				false /* complete */);
	}

	/** true means that this detail can be persisted; false means that id can still be part of a query. */
	boolean complete;

	BigDecimal quantity;

	/** 
	 * Used in queries if > 0. 
	 */
	int transactionId;

	/** 
	 * If there was no inventory, but MD_Stock had to be reset from M_HU_Storage.
	 * Also used in queries if > 0. 
	 */
	int resetStockAdPinstanceId;
	
	AttributesKey storageAttributesKey;

	int attributeSetInstanceId;

	/** {@code MD_Stock_ID} */
	int stockId;


	@Builder
	private TransactionDetail(
			final BigDecimal quantity,
			final AttributesKey storageAttributesKey,
			final int attributeSetInstanceId,
			final int transactionId,
			final int stockId,
			final int resetStockAdPinstanceId,
			final boolean complete)
	{
		this.complete = complete;

		Preconditions.checkArgument(transactionId > 0 || resetStockAdPinstanceId > 0,
				"From the given parameters transactionId=%s and resetStockAdPinstanceId=%s, at least one needs to be > 0", transactionId, resetStockAdPinstanceId);
		this.transactionId = transactionId;

		Preconditions.checkArgument(!complete || quantity != null, "The given parameter quantity may not be null because complete=true; transactionId=%s", transactionId);
		this.quantity = quantity;

		this.storageAttributesKey = storageAttributesKey;
		this.attributeSetInstanceId = attributeSetInstanceId;

		this.stockId = stockId;
		this.resetStockAdPinstanceId = resetStockAdPinstanceId;
	}
}

package de.metas.vendor.gateway.api.order;

import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.util.Check;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

/*
 * #%L
 * de.metas.vendor.gateway.api
 * %%
 * Copyright (C) 2018 metas GmbH
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
public class RemotePurchaseOrderCreated
{
	// his is not the ID of a particular purchase order. There might be many or none.
	// even if no purchase order could be done, we still have a record about the respective transaction.
	int transactionRecordId;
	String transactionTableName;

	RuntimeException exception;

	List<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems;

	@Builder
	private RemotePurchaseOrderCreated(
			int transactionRecordId,
			@NonNull String transactionTableName,
			@Nullable RuntimeException exception,
			@Singular List<RemotePurchaseOrderCreatedItem> purchaseOrderResponseItems)
	{
		Check.assume(transactionRecordId > 0, "Given parameter transactionRecordId > 0");
		Check.assumeNotEmpty(transactionTableName, "Given parameter transactionTableName is not empty");

		this.transactionRecordId = transactionRecordId;
		this.transactionTableName = transactionTableName;

		this.exception = exception;
		this.purchaseOrderResponseItems = purchaseOrderResponseItems;
	}
}

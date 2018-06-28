package de.metas.purchasecandidate;

import org.adempiere.util.Check;
import org.compiere.model.I_C_OrderLine;

import de.metas.order.OrderAndLineId;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-webui-api
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

/**
 * Note: doesn't contain the demand reference string; that string is currently just a reference for the users,
 * so the can see which purchase candidates belong together.
 */
@Value
public class DELETEME_PurchaseDemandReference
{
	public static DELETEME_PurchaseDemandReference ofTableAndRecordId(final String tableName, final int recordId)
	{
		return new DELETEME_PurchaseDemandReference(tableName, recordId);
	}

	public static DELETEME_PurchaseDemandReference ofOrderAndLineId(@NonNull final OrderAndLineId orderAndLineId)
	{
		return ofOrderLineRepoId(orderAndLineId.getOrderLineRepoId());
	}

	public static DELETEME_PurchaseDemandReference ofOrderLineRepoId(final int orderLineRepoId)
	{
		return ofTableAndRecordId(I_C_OrderLine.Table_Name, orderLineRepoId);
	}

//	/** @return in memory ad-hoc aggregate ID */
//	public static PurchaseDemandReference newAggregateId()
//	{
//		return ofTableAndRecordId(TABLENAME_AGGREGATE, nextAggregateId.getAndIncrement());
//	}
//
//	private static final String TABLENAME_AGGREGATE = "$ad-hoc-aggregate$";
//	private static final AtomicInteger nextAggregateId = new AtomicInteger(1);

	String tableName;
	int recordId;

	public DELETEME_PurchaseDemandReference(final String tableName, final int recordId)
	{
		Check.assumeNotEmpty(tableName, "tableName is not empty");
		Check.assumeGreaterThanZero(recordId, "recordId");

		this.tableName = tableName;
		this.recordId = recordId;
	}

//	public boolean isAggregate()
//	{
//		return TABLENAME_AGGREGATE.equals(tableName);
//	}
//
//	public boolean isTable()
//	{
//		return !isAggregate();
//	}
}

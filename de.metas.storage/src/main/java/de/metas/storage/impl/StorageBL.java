package de.metas.storage.impl;

/*
 * #%L
 * de.metas.storage
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.math.BigDecimal;
import java.util.Collection;

import de.metas.storage.IStorageBL;
import de.metas.storage.IStorageRecord;
import de.metas.storage.IStorageSegmentBuilder;

public class StorageBL implements IStorageBL
{
	@Override
	public BigDecimal calculateQtyOnHandSum(final Collection<? extends IStorageRecord> storageRecords)
	{
		// NOTE: in future we can consider to have a IStorageRecordPool object which will contain a group of IStorageRecords,
		// and where methods like this can be placed

		if (storageRecords == null || storageRecords.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		BigDecimal qtyOnHandTotal = BigDecimal.ZERO;
		for (final IStorageRecord storageRecord : storageRecords)
		{
			if (storageRecord == null)
			{
				// shall not happen
				continue;
			}

			final BigDecimal qtyOnHand = storageRecord.getQtyOnHand();
			if (qtyOnHand == null)
			{
				// shall not happen!!!
				continue;
			}

			// Accumulate QtyOnHand
			qtyOnHandTotal = qtyOnHandTotal.add(qtyOnHand);
		}

		return qtyOnHandTotal;
	}

	@Override
	public IStorageSegmentBuilder createStorageSegmentBuilder()
	{
		return new StorageSegmentBuilder();
	}

}

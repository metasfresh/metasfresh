package de.metas.inventory.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InventoryLine;

import com.google.common.collect.ImmutableList;

import de.metas.inventory.IInventoryDAO;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class InventoryDAO implements IInventoryDAO
{
	@Override
	public boolean hasLines(final int inventoryId)
	{
		if (inventoryId <= 0)
		{
			return false;
		}

		return createLinesQueryForInventoryId(inventoryId)
				.create()
				.match();
	}

	@Override
	public <T extends I_M_InventoryLine> List<T> retrieveLinesForInventoryId(final int inventoryId, @NonNull final Class<T> type)
	{
		if (inventoryId <= 0)
		{
			return ImmutableList.of();
		}

		return createLinesQueryForInventoryId(inventoryId)
				.create()
				.list(type);
	}

	private IQueryBuilder<I_M_InventoryLine> createLinesQueryForInventoryId(final int inventoryId)
	{
		Check.assume(inventoryId > 0, "inventoryId > 0");
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryId)
				.orderBy(I_M_InventoryLine.COLUMN_Line)
				.orderBy(I_M_InventoryLine.COLUMN_M_InventoryLine_ID);
	}

	@Override
	public void setInventoryLinesProcessed(final int inventoryId, final boolean processed)
	{
		Services.get(IQueryBL.class).createQueryBuilder(I_M_InventoryLine.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine.COLUMNNAME_M_Inventory_ID, inventoryId)
				.addNotEqualsFilter(I_M_InventoryLine.COLUMNNAME_Processed, processed)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_M_InventoryLine.COLUMNNAME_Processed, processed)
				.execute();
	}

}

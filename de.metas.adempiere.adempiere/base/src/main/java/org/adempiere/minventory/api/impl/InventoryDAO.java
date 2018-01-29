package org.adempiere.minventory.api.impl;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.minventory.api.IInventoryDAO;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;

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
	public List<I_M_InventoryLine> retrieveLinesForInventory(final I_M_Inventory inventory)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_InventoryLine.class, inventory)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InventoryLine.COLUMN_M_Inventory_ID, inventory.getM_Inventory_ID())
				.orderBy(I_M_InventoryLine.COLUMN_Line)
				.orderBy(I_M_InventoryLine.COLUMN_M_InventoryLine_ID)
				.create()
				.list(I_M_InventoryLine.class);
	}

}

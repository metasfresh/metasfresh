package org.adempiere.minventory.api;

import java.util.List;

import org.adempiere.util.ISingletonService;
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

public interface IInventoryDAO extends ISingletonService
{

	/**
	 * Retrieve all the active lines of the given inventory
	 * 
	 * @param inventory
	 * @return
	 */
	<T extends I_M_InventoryLine> List<T> retrieveLinesForInventoryId(int inventoryId, Class<T> type);
	
	default List<I_M_InventoryLine> retrieveLinesForInventoryId(final int inventoryId)
	{
		return retrieveLinesForInventoryId(inventoryId, I_M_InventoryLine.class);
	}


	boolean hasLines(int inventoryId);

	void setInventoryLinesProcessed(int inventoryId, boolean processed);
}

package de.metas.handlingunits.inventory;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_InventoryLine;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_Inventory;

/*
 * #%L
 * de.metas.handlingunits.base
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

public interface IHUInventoryBL extends ISingletonService
{

	/**
	 * Move products from the warehouse to garbage (waste disposal)
	 * After this process an internal use inventory is created.
	 */
	List<I_M_Inventory> moveToGarbage(Collection<I_M_HU> husToDestroy, Timestamp movementDate);

	/**
	 * Check if the given inventory is a material disposal
	 * It must have <code>X_C_DocType.DOCBASETYPE_MaterialPhysicalInventory</code> and <code>X_C_DocType.DOCSUBTYPE_MaterialDisposal</code>
	 * 
	 * @param inventory
	 * @return
	 */
	boolean isMaterialDisposal(I_M_Inventory inventory);

	void assignHU(I_M_InventoryLine inventoryLine, I_M_HU topLevelHU);

}

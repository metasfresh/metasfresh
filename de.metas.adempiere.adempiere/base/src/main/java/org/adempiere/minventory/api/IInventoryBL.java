package org.adempiere.minventory.api;

import java.math.BigDecimal;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Inventory;
import org.compiere.model.I_M_InventoryLine;

/**
 * @author ad
 * 
 */
public interface IInventoryBL extends ISingletonService
{
	/**
	 * Gets the charge we use for internal inventory charge (from a sysconfig). Used in quick input and automatically generated inventory lines.
	 * 
	 * @param ctx
	 * @return
	 */
	int getDefaultInternalChargeId(Properties ctx);

	void addDescription(I_M_Inventory inventory, String descriptionToAdd);

	void addDescription(I_M_InventoryLine inventoryLine, String descriptionToAdd);

	boolean isComplete(I_M_Inventory inventory);

	boolean isSOTrx(I_M_InventoryLine inventoryLine);

	boolean isInternalUseInventory(I_M_InventoryLine inventoryLine);

	/**
	 * Get Movement Qty (absolute value)
	 * <li>negative value means outgoing trx
	 * <li>positive value means incoming trx
	 *
	 * @return movement qty
	 */
	BigDecimal getMovementQty(I_M_InventoryLine inventoryLine);

}

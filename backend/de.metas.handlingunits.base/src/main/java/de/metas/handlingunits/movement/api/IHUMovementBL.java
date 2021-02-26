/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.movement.api;

import de.metas.handlingunits.IHUAssignmentBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_MovementLine;
import de.metas.interfaces.I_M_Movement;
import de.metas.util.ISingletonService;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Warehouse;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Properties;

public interface IHUMovementBL extends ISingletonService
{

	String SYSCONFIG_DirectMove_Warehouse_ID = "de.metas.handlingunits.client.terminal.inventory.model.InventoryHUEditorModel#DirectMove_Warehouse_ID";

	/**
	 * After the movement lines for product were created, we are creating packing material lines for them the packing material lines contain the qtys aggregated for all the product lines of the same
	 * locatorFrom, locatorTo
	 *
	 * @param movement
	 */
	void createPackingMaterialMovementLines(I_M_Movement movement);

	/**
	 * NOTE: Only use for packing material Movement Lines!!!! Note 2: the movement line is saved
	 * <p>
	 * Set the correct activity in the movement line In the case of packing material movement lines, this is always the activity of the prosuct ( Usually Gebinde)
	 *
	 * @param movementLine
	 * @task 07689, 07690
	 */
	void setPackingMaterialCActivity(I_M_MovementLine movementLine);

	/**
	 * Create movements to move given HUs to warehouseTo
	 */
	HUMovementResult moveHUsToWarehouse(List<I_M_HU> hus, I_M_Warehouse warehouseTo);

	HUMovementResult moveHUsToLocator(List<I_M_HU> hus, I_M_Locator locatorTo);

	/**
	 * Method uses <code>AD_SysConfig</code> {@value #SYSCONFIG_DirectMove_Warehouse_ID} to get the {@link I_M_Warehouse} for direct movements.
	 *
	 * @param ctx     the context we use to get the <code>AD_Client_ID</code> and <code>AD_Org_ID</code> used to retrieve the AD_SysConfig value.
	 * @param throwEx if <code>true</code> then the method throws a exception rather than of returning <code>null</code>.
	 * @return {@link I_M_Warehouse} for direct movements or <code>null</code>.
	 * @task http://dewiki908/mediawiki/index.php/08205_HU_Pos_Inventory_move_Button_%28105838505937%29
	 */
	@Nullable
	I_M_Warehouse getDirectMove_Warehouse(Properties ctx, boolean throwEx);

	/**
	 * Assigns given HU to given movement line
	 *
	 * @param movementLine
	 * @param hu
	 * @param isTransferPackingMaterials
	 * @param trxName
	 * @see IHUAssignmentBL#assignHU(Object, I_M_HU, boolean, String)
	 */
	void assignHU(org.compiere.model.I_M_MovementLine movementLine, I_M_HU hu, boolean isTransferPackingMaterials, String trxName);

	void moveHandlingUnits(I_M_Movement movement, boolean doReversal);
}

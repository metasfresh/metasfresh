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
import de.metas.handlingunits.movement.generate.HUMovementGenerateRequest;
import de.metas.handlingunits.movement.generate.HUMovementGeneratorResult;
import de.metas.interfaces.I_M_Movement;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;

import java.util.Collection;
import java.util.List;

public interface IHUMovementBL extends ISingletonService
{

	String SYSCONFIG_DirectMove_Warehouse_ID = "de.metas.handlingunits.client.terminal.inventory.model.InventoryHUEditorModel#DirectMove_Warehouse_ID";

	/**
	 * After the movement lines for product were created, we are creating packing material lines for them the packing material lines contain the qtys aggregated for all the product lines of the same
	 * locatorFrom, locatorTo
	 */
	void createPackingMaterialMovementLines(I_M_Movement movement);

	/**
	 * NOTE: Only use for packing material Movement Lines!!!! Note 2: the movement line is saved
	 * <p>
	 * Set the correct activity in the movement line In the case of packing material movement lines, this is always the activity of the prosuct ( Usually Gebinde)
	 * @implNote tasks 07689, 07690
	 */
	void setPackingMaterialCActivity(I_M_MovementLine movementLine);

	/**
	 * Create movements to move given HUs to warehouseTo
	 */
	HUMovementGeneratorResult moveHUsToWarehouse(Collection<I_M_HU> hus, WarehouseId warehouseToId);

	void moveHUs(@NonNull HUMovementGenerateRequest request);

	HUMovementGeneratorResult moveHUsToLocator(Collection<I_M_HU> hus, LocatorId locatorToId);

	LocatorId getDirectMoveLocatorId();

	/**
	 * Assigns given HU to given movement line
	 *
	 * @see IHUAssignmentBL#assignHU(Object, I_M_HU, boolean, String)
	 */
	void assignHU(org.compiere.model.I_M_MovementLine movementLine, I_M_HU hu, boolean isTransferPackingMaterials, String trxName);

	void setAssignedHandlingUnits(org.compiere.model.I_M_MovementLine movementLine, List<I_M_HU> hus);

	void moveHandlingUnits(I_M_Movement movement, boolean doReversal);
}

package de.metas.handlingunits.client.terminal.inventory.model;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.editor.model.IHUKey;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.interfaces.I_M_Movement;

/**
 *
 * @task http://dewiki908/mediawiki/index.php/07050_Eigenverbrauch_metas_in_Existing_Window_Handling_Unit_Pos
 */
public class InventoryHUEditorModel extends HUEditorModel
{
	private final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);

	private final I_M_Warehouse _warehouse;

	public InventoryHUEditorModel(final ITerminalContext terminalContext, final int warehouseId)
	{
		super(terminalContext);

		_warehouse = InterfaceWrapperHelper.create(terminalContext.getCtx(), warehouseId, I_M_Warehouse.class, ITrx.TRXNAME_None);
		Check.assumeNotNull(_warehouse, "warehouse not null");
	}

	public I_M_Warehouse getM_Warehouse()
	{
		return _warehouse;
	}

	/**
	 * Move selected HUs to the warehouse for direct movements
	 *
	 * @task http://dewiki908/mediawiki/index.php/08205_HU_Pos_Inventory_move_Button_%28105838505937%29
	 */
	public I_M_Movement doDirectMoveToWarehouse()
	{
		// services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

		//
		// Get selected HUs
		final Set<HUKey> huKeys = new HashSet<>(getSelectedHUKeys());
		for (final Iterator<HUKey> huKeysIterator = huKeys.iterator(); huKeysIterator.hasNext();)
		{
			final HUKey huKey = huKeysIterator.next();
			final I_M_HU hu = huKey.getM_HU();

			// Exclude pure virtual HUs (i.e. those HUs which are linked to material HU Items)
			if (handlingUnitsBL.isPureVirtual(hu))
			{
				huKeysIterator.remove();
				continue;
			}
		}
		if (Check.isEmpty(huKeys))
		{
			throw new TerminalException("@NoSelection@");
		}

		//
		// Warehouse from: we are moving from this warehouse
		final I_M_Warehouse warehouseFrom = getM_Warehouse(); // shall not be null

		//
		// Warehouse to: we are moving the warehouses for direct movements
		final boolean exceptionIfNull = false;
		final I_M_Warehouse warehouseTo = huMovementBL.getDirectMove_Warehouse(getTerminalContext().getCtx(), exceptionIfNull);
		Check.assumeNotNull(warehouseTo, "warehouseTo not null"); // shall not happen, because if it's null the action button shall be hidden

		//
		// create our list of HUs to pass to the API service
		final List<I_M_HU> hus = new ArrayList<I_M_HU>();
		for (final HUKey huKey : huKeys)
		{
			final I_M_HU hu = huKey.getM_HU();
			hus.add(hu);

			// guard: verify the the HU's current warehouse matches the selected warehouseFrom
			final int huWarehouseID = hu.getM_Locator().getM_Warehouse_ID();
			Check.errorUnless(huWarehouseID == warehouseFrom.getM_Warehouse_ID(),
					"The selected HU {} has a M_Locator {} with M_Warehouse_ID {} which is != the M_Warehouse_ID {} of warehouse {}",
					hu, hu.getM_Locator(), huWarehouseID, warehouseFrom.getM_Warehouse_ID(), warehouseFrom);
		}

		// make the movement-creating API call
		final List<I_M_Movement> movements = huMovementBL.generateMovementsToWarehouse(warehouseTo, hus, getTerminalContext());
		Check.assume(movements.size() <= 1, "We called the API with HUs {} that are all in the same warehouse {}, so there is just one movement created", hus, warehouseFrom);

		if (movements.isEmpty())
		{
			throw new TerminalException("@NotCreated@ @M_Movement_ID@");
		}
		final I_M_Movement movement = movements.get(0);

		//
		// Refresh the HUKeys
		{
			// Remove huKeys from their parents
			for (final HUKey huKey : huKeys)
			{
				removeHUKeyFromParentRecursivelly(huKey);
			}

			// Move back to Root HU Key
			setRootHUKey(getRootHUKey());

			//
			// Clear (attribute) cache (because it could be that we changed the attributes too)
			clearCache();
		}

		return movement;
	}

	private final void removeHUKeyFromParentRecursivelly(final IHUKey huKey)
	{
		if (huKey == null)
		{
			return;
		}

		final IHUKey parentKey = huKey.getParent();
		if (parentKey == null)
		{
			return;
		}

		// Remove child from it's parent
		parentKey.removeChild(huKey);

		// If after removing, the parent become empty then remove it from it's parent... and so on
		if (parentKey.getChildren().isEmpty())
		{
			removeHUKeyFromParentRecursivelly(parentKey);
		}
		else
		{
			// Update parent's name recursivelly up to the top
			// FIXME: this is a workaround because even though the name is updated, it's not udpated recursivelly up to the top
			IHUKey p = parentKey;
			while (p != null)
			{
				p.updateName();
				p = p.getParent();
			}

		}
	}
}

package de.metas.handlingunits.client.terminal.inventory.view;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Movement;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.event.UIPropertyChangeListener;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUKey;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.inventory.model.InventoryHUEditorModel;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;

public final class InventoryHUEditorPanel extends HUEditorPanel
{
	/**
	 * Button used directly move selected HUs to a predefined Warehouse
	 */
	public static final String ACTION_DirectMoveToWarehouse = "de.metas.handlingunits.client.terminal.inventory.view.InventoryHUEditorPanel#DirectMoveToWarehouse";

	private final transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	private final transient IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

	public InventoryHUEditorPanel(final HUEditorModel model)
	{
		super(model);

		enableMarkAsQualityInspectionButton(); // task 08639
	}

	@Override
	protected void createAndAddActionButtons(final IContainer buttonsPanel)
	{
		final IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
		final ITerminalFactory factory = getTerminalFactory();
		final InventoryHUEditorModel huEditorModel = getHUEditorModel();

		//
		// Button: Direct Move to Warehouse (08205)
		// (we are creating it only if Direct Move Warehouse was configured and we are not already on that warehouse)
		final boolean exceptionIfNull = false;

		final I_M_Warehouse directMoveWarehouse = huMovementBL.getDirectMove_Warehouse(
				huEditorModel.getTerminalContext().getCtx(),
				exceptionIfNull);
		final I_M_Warehouse warehouse = huEditorModel.getM_Warehouse();
		if (directMoveWarehouse != null && directMoveWarehouse.getM_Warehouse_ID() != warehouse.getM_Warehouse_ID())
		{
			final ITerminalButton bDirectMoveToWarehouse = factory.createButton(ACTION_DirectMoveToWarehouse);
			bDirectMoveToWarehouse.setTextAndTranslate(ACTION_DirectMoveToWarehouse);
			bDirectMoveToWarehouse.addListener(new UIPropertyChangeListener(factory, bDirectMoveToWarehouse)
			{
				@Override
				public void propertyChangeEx(final PropertyChangeEvent evt)
				{
					doDirectMoveToWarehouse();
				}
			});
			buttonsPanel.add(bDirectMoveToWarehouse, "");

		}

		buttonsPanel.add(bMoveToQualityWarehouse, "newline");
		buttonsPanel.add(bMoveToAnotherWarehouse, "");
		buttonsPanel.add(bCreateVendorReturn, "");
		buttonsPanel.add(bMoveToGarbage, "");
	}

	@Override
	protected I_M_Warehouse getCurrentWarehouse()
	{
		return getHUEditorModel().getM_Warehouse();
	}

	@Override
	protected InventoryHUEditorModel getHUEditorModel()
	{
		return (InventoryHUEditorModel)super.getHUEditorModel();
	}

	@Override
	protected final void onDialogOkAfterSave(final ITerminalDialog dialog)
	{
		// nothing
	}

	/**
	 * Move selected HUs to the warehouse for direct movements
	 *
	 * @task http://dewiki908/mediawiki/index.php/08205_HU_Pos_Inventory_move_Button_%28105838505937%29
	 */
	private final void doDirectMoveToWarehouse()
	{
		final boolean exceptionIfNull = false;
		//
		// Generate the movement

		// Warehouse from: We are moving from the currently selected warehouse

		final I_M_Warehouse warehouseFrom = getCurrentWarehouse();

		// Warehouse to: we are moving the warehouses for direct movements
		final I_M_Warehouse warehouseTo = huMovementBL.getDirectMove_Warehouse(getTerminalContext().getCtx(), exceptionIfNull);
		Check.assumeNotNull(warehouseTo, "warehouseTo not null"); // shall not happen, because if it's null the action button shall be hidden

		final List<I_M_HU> hus = getSelectedHUs(warehouseFrom);
		final List<I_M_Movement> movements = huMovementBL.moveHUsToWarehouse(hus, warehouseTo)
				.getMovements();
		if (movements.isEmpty())
		{
			return;
		}

		getHUEditorModel().refreshSelectedHUKeys();

		//
		// Inform the user about which movement was created
		final ITerminalFactory terminalFactory = getTerminalFactory();
		final Properties ctx = getTerminalContext().getCtx();
		final String message = msgBL.parseTranslation(ctx, "@M_Movement_ID@ #" + movements.get(0).getDocumentNo());
		terminalFactory.showInfo(this, "Created", message);
	}

	private List<I_M_HU> getSelectedHUs(final I_M_Warehouse warehouseFrom)
	{
		//
		// Get selected HUs
		final Set<HUKey> huKeys = new HashSet<>(getHUEditorModel().getSelectedHUKeys());
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
		// create our list of HUs to pass to the API service
		final List<I_M_HU> hus = new ArrayList<I_M_HU>();
		for (final HUKey huKey : huKeys)
		{
			final I_M_HU hu = huKey.getM_HU();
			hus.add(hu);

			// guard: verify the the HU's current warehouse matches the selected warehouseFrom
			if (warehouseFrom != null)
			{
				final int huWarehouseID = hu.getM_Locator().getM_Warehouse_ID();
				Check.errorUnless(huWarehouseID == warehouseFrom.getM_Warehouse_ID(),
						"The selected HU {} has a M_Locator {} with M_Warehouse_ID {} which is != the M_Warehouse_ID {} of warehouse {}",
						hu, hu.getM_Locator(), huWarehouseID, warehouseFrom.getM_Warehouse_ID(), warehouseFrom);
			}
		}

		return hus;
	}

}

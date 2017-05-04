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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.beans.PropertyChangeEvent;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.event.UIPropertyChangeListener;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.inventory.model.InventoryHUEditorModel;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.interfaces.I_M_Movement;

public final class InventoryHUEditorPanel extends HUEditorPanel
{
	/**
	 * Button used directly move selected HUs to a predefined Warehouse
	 */
	public static final String ACTION_DirectMoveToWarehouse = "de.metas.handlingunits.client.terminal.inventory.view.InventoryHUEditorPanel#DirectMoveToWarehouse";

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
		//
		// Generate the movement
		final I_M_Movement movement = getHUEditorModel().doDirectMoveToWarehouse();
		if (movement == null)
		{
			return;
		}

		//
		// Inform the user about which movement was created
		final ITerminalFactory terminalFactory = getTerminalFactory();
		final Properties ctx = getTerminalContext().getCtx();
		final String message = msgBL.parseTranslation(ctx, "@M_Movement_ID@ #" + movement.getDocumentNo());
		terminalFactory.showInfo(this, "Created", message);
	}
}

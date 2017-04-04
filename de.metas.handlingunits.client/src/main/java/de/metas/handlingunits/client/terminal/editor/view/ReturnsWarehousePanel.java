package de.metas.handlingunits.client.terminal.editor.view;

import java.util.List;
import java.util.Properties;

import org.adempiere.util.Services;
import org.compiere.model.I_M_Warehouse;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.ReturnsWarehouseModel;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.AbstractMaterialMovementPanel;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.movement.api.IHUMovementBL;
import de.metas.interfaces.I_M_Movement;

/*
 * #%L
 * de.metas.handlingunits.client
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

public class ReturnsWarehousePanel extends AbstractMaterialMovementPanel<ReturnsWarehouseModel>
{
	private static transient IHUMovementBL huMovementBL = Services.get(IHUMovementBL.class);
	
	private ITerminalContext terminalContext;
	private ReturnsWarehouseModel model;

	public ReturnsWarehousePanel(final ITerminalContext terminalContext, final ReturnsWarehouseModel model)
	{
		super(model);
		this.setModel(model);
		this.terminalContext = terminalContext;

	}

	protected ITerminalKeyPanel warehousePanel;

//	@Override
//	protected void initLayout(final ITerminalFactory factory)
//	{
//		addReturnWarehouseLane();
//	}
//
//	private void initComponents()
//	{
//		final ITerminalFactory factory = getTerminalFactory();
//
//		final ReturnsWarehouseModel model = getModel();
//		//
//		// warehouse
//		final IKeyLayout warehouseKeyLayout = model.getWarehouseKeyLayout();
//		warehousePanel = factory.createTerminalKeyPanel(warehouseKeyLayout);
//	}

	private void addReturnWarehouseLane()
	{
		createPanel(warehousePanel, "dock north, growx, hmin 40%");

	}

	protected final IComponent createPanel(final IComponent component, final Object constraints)
	{
		panel.add(component, constraints);

		final ITerminalScrollPane scroll = getTerminalFactory().createScrollPane(panel);
		scroll.setUnitIncrementVSB(16);
		final IContainer card = getTerminalFactory().createContainer();
		card.add(scroll, "growx, growy");
		return card;
	}

	/**
	 * task #1065
	 * Move the selected HUs to the quality returns warehouse
	 */
	private void doMoveToQualityWarehouse()
	{
		// Move from the selected warehouse
		final I_M_Warehouse warehouseFrom = model.getM_WarehouseFrom(); // shall not be null
		final I_M_Warehouse warehouseTo = getModel().getM_WarehouseTo();
		final List<I_M_HU> hus = model.getHUs();
		final I_M_Movement movement = huMovementBL.moveToQualityWarehouse(terminalContext, warehouseFrom, warehouseTo, hus);
		
	

		//
		// Inform the user about which movement was created
//		final ITerminalFactory terminalFactory = getTerminalFactory();
//		final Properties ctx = getTerminalContext().getCtx();
//		final String message = msgBL.parseTranslation(ctx, "@M_Movement_ID@ #" + movement.getDocumentNo());
//		terminalFactory.showInfo(this, "Created", message);

	}

	@Override
	protected void loadFromModel()
	{
		// TODO Auto-generated method stub

	}

	@Override
	protected void doOnDialogCanceling(ITerminalDialog dialog)
	{
		// nothing

	}

	

	public void setModel(ReturnsWarehouseModel model)
	{
		this.model = model;
	}

}

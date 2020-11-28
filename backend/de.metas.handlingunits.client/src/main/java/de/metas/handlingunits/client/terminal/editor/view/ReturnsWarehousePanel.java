package de.metas.handlingunits.client.terminal.editor.view;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.AbstractMovementsWarehouseModel;
import de.metas.handlingunits.client.terminal.mmovement.view.impl.AbstractMaterialMovementPanel;

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

public class ReturnsWarehousePanel extends AbstractMaterialMovementPanel<AbstractMovementsWarehouseModel>
{

	public ReturnsWarehousePanel(final ITerminalContext terminalContext, final AbstractMovementsWarehouseModel model)
	{
		super(model);

		initComponents();
		initLayout();

	}

	/**
	 * panel for all the warehouses eligible for quality returns
	 */
	protected ITerminalKeyPanel warehousePanel;

	protected void initLayout()
	{
		addReturnWarehouseLane();
	}

	private void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		final AbstractMovementsWarehouseModel model = getModel();
		//
		// warehouse
		final IKeyLayout warehouseKeyLayout = model.getWarehouseKeyLayout();
		warehousePanel = factory.createTerminalKeyPanel(warehouseKeyLayout);
	}

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

	@Override
	protected void loadFromModel()
	{
		// nothing for now
	}

	@Override
	protected void doOnDialogCanceling(ITerminalDialog dialog)
	{
		// nothing for now
	}
}

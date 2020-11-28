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


import javax.annotation.OverridingMethodsMustInvokeSuper;

import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.editor.view.HUEditorPanel;
import de.metas.handlingunits.client.terminal.inventory.model.InventoryHUSelectModel;
import de.metas.handlingunits.client.terminal.select.model.HUEditorCallbackAdapter;
import de.metas.handlingunits.client.terminal.select.view.AbstractHUSelectPanel;

public class InventoryHUSelectPanel<MT extends InventoryHUSelectModel> extends AbstractHUSelectPanel<MT>
{
	// Key panels
	protected ITerminalKeyPanel bpartnerLocationsPanel;

	public InventoryHUSelectPanel(final MT model)
	{
		super(model.getTerminalContext(), model);

		initComponents();
		initLayout();
	}

	protected void initLayout()
	{
		//
		// Warehouse Keys Panel
		{
			createPanel(warehousePanel, "dock north, growx, hmin 40%");
		}

		//
		// BPartner Keys Panel (if displayed)
		final InventoryHUSelectModel model = getModel();
		if (model.isDisplayVendorKeys())
		{
			createPanel(bpartnersPanel, "dock north, growx, hmin 40%");
		}

		//
		// BPartner Location Keys Panel (if displayed)
		if (model.isDisplayVendorKeys() && bpartnerLocationsPanel != null)
		{
			// NOTE: atm, BPartner Keys are not displayed on this level, so this panel will not be added.
			// More, classes which are extending this class are defining their own layouts, so this part is kind of pointless.
			createPanel(bpartnerLocationsPanel, "dock north, growx");
		}

		//
		// Confirmation panel (i.e. bottom buttons)
		{
			final IConfirmPanel confirmPanel = getConfirmPanel();
			createPanel(confirmPanel, "dock south, hmax 15%");
		}
	}

	@OverridingMethodsMustInvokeSuper
	protected void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		final InventoryHUSelectModel model = getModel();

		//
		// Warehouse Key Layout
		{
			final IKeyLayout warehouseKeyLayout = model.getWarehouseKeyLayout();
			warehousePanel = factory.createTerminalKeyPanel(warehouseKeyLayout);
		}

		//
		// BPartner Key Layout
		if (model.isDisplayVendorKeys())
		{
			final IKeyLayout bpartnersKeyLayout = model.getVendorKeyLayout();
			bpartnersPanel = factory.createTerminalKeyPanel(bpartnersKeyLayout);
		}

		//
		// BPartner Locations Key Layout
		if (model.isDisplayVendorKeys())
		{
			final IKeyLayout bpartnerLocationsKeyLayout = model.getBPartnerLocationKeyLayout();
			bpartnerLocationsPanel = factory.createTerminalKeyPanel(bpartnerLocationsKeyLayout);
			bpartnerLocationsPanel.setRenderer(BPartnerLocationKeyLayoutRenderer.instance);
		}
	}

	@Override
	protected final void doProcessSelectedLines(final InventoryHUSelectModel model)
	{
		model.doProcessSelectedLines(new HUEditorCallbackAdapter<HUEditorModel>()
		{
			@Override
			public boolean editHUs(final HUEditorModel huEditorModel)
			{
				return InventoryHUSelectPanel.this.editHUs(huEditorModel);
			}
		}); // We ignore the rows.
	}

	@Override
	protected HUEditorPanel createHUEditorPanelInstance(final HUEditorModel huEditorModel)
	{
		final InventoryHUEditorPanel editorPanel = new InventoryHUEditorPanel(huEditorModel);
		return editorPanel;
	}

	@OverridingMethodsMustInvokeSuper
	protected InventoryHUSelectModel getHUSelectModel()
	{
		return getModel();
	}
}

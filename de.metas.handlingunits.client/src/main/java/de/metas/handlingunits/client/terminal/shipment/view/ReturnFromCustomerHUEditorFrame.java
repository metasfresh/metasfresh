package de.metas.handlingunits.client.terminal.shipment.view;

import javax.swing.JFrame;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.AbstractHUEditorFrame;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.impl.InventoryHUFiltering;
import de.metas.handlingunits.client.terminal.shipment.model.ReturnFromCustomerHUEditorModel;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class ReturnFromCustomerHUEditorFrame extends AbstractHUEditorFrame<ReturnFromCustomerHUEditorModel>
{

	public ReturnFromCustomerHUEditorFrame(final JFrame frame, final int windowNo)
	{
		super(frame, windowNo);
	}

	@Override
	protected ReturnFromCustomerHUEditorPanel createHUEditorPanel()
	{
		final ReturnFromCustomerHUEditorModel huEditorModel = createHUEditorModel();
		final ReturnFromCustomerHUEditorPanel huEditorPanel = new ReturnFromCustomerHUEditorPanel(huEditorModel);
		return huEditorPanel;
	}

	private ReturnFromCustomerHUEditorModel createHUEditorModel()
	{
		final ITerminalContext terminalContext = getTerminalContext();

		// filter for all warehouses in profile
		terminalContext.registerService(IPOSFiltering.class, new InventoryHUFiltering());
		final ReturnFromCustomerHUEditorModel documentSelectModel = new ReturnFromCustomerHUEditorModel(terminalContext);
//		// don't display vendor keys (bpartners)
//		documentSelectModel.setDisplayVendorKeys(false);
//		// We display all HUs.
//		documentSelectModel.setAdditionalHUFilter(null);
		return documentSelectModel;
	}
}

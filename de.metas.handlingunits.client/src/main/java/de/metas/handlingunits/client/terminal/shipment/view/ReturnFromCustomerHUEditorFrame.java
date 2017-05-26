package de.metas.handlingunits.client.terminal.shipment.view;

import javax.swing.JFrame;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.AbstractHUEditorFrame;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.impl.InventoryHUFiltering;
import de.metas.handlingunits.client.terminal.shipment.model.ReturnFromCustomerHUEditorModel;
import de.metas.handlingunits.model.I_M_InOut;

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

public class ReturnFromCustomerHUEditorFrame extends AbstractHUEditorFrame<ReturnFromCustomerHUEditorModel>
{

	public ReturnFromCustomerHUEditorFrame(final JFrame frame, final int windowNo, final I_M_InOut inout)
	{
		super(frame, windowNo, inout);
	}

	@Override
	protected ReturnFromCustomerHUEditorPanel createHUEditorPanel(final I_M_InOut inout)
	{
		final ReturnFromCustomerHUEditorModel huEditorModel = createHUEditorModel(inout);

		huEditorModel.loadFromShipment();
		final ReturnFromCustomerHUEditorPanel huEditorPanel = new ReturnFromCustomerHUEditorPanel(huEditorModel);
		return huEditorPanel;
	}

	private ReturnFromCustomerHUEditorModel createHUEditorModel(final I_M_InOut inout)
	{
		final ITerminalContext terminalContext = getTerminalContext();

		terminalContext.registerService(IPOSFiltering.class, new InventoryHUFiltering());
		final ReturnFromCustomerHUEditorModel documentSelectModel = new ReturnFromCustomerHUEditorModel(terminalContext, inout);

		return documentSelectModel;
	}

}

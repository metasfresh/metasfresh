package de.metas.handlingunits.client.terminal.ddorder.view;

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

import javax.swing.JFrame;

import org.eevolution.model.I_PP_Order;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.ddorder.api.impl.DDOrderFiltering;
import de.metas.handlingunits.client.terminal.ddorder.model.DDOrderHUSelectModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.view.AbstractHUSelectFrame;
import de.metas.handlingunits.client.terminal.select.view.IHUSelectPanel;

public class DDOrderHUSelectFrame extends AbstractHUSelectFrame<DDOrderHUSelectModel>
{
	/**
	 * Note: its superclass creates a terminal context registers itself as a disposable component.
	 *
	 * @param frame
	 * @param windowNo
	 */
	public DDOrderHUSelectFrame(final JFrame frame, final int windowNo)
	{
		super(frame, windowNo);
	}

	@Override
	protected IHUSelectPanel createHUSelectPanel()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		terminalContext.registerService(IPOSFiltering.class, new DDOrderFiltering());

		return new DDOrderHUSelectPanel(terminalContext);
	}

	/**
	 * Used when manually opening the DD order panel for a particular order.
	 *
	 * @param ppOrder
	 */
	public void setContextManufacturingOrder(final I_PP_Order ppOrder)
	{
		final DDOrderHUSelectPanel documentSelectPanel = (DDOrderHUSelectPanel)getHUSelectPanel();
		documentSelectPanel.setContextManufacturingOrder(ppOrder);
	}

	public void setWarehouseOverrideId(final int warehouseOverrideId)
	{
		final DDOrderHUSelectPanel documentSelectPanel = (DDOrderHUSelectPanel)getHUSelectPanel();
		documentSelectPanel.setWarehouseOverrideId(warehouseOverrideId);
	}
}

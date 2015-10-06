package de.metas.handlingunits.client.terminal.receipt.view;

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


import javax.swing.JFrame;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.receipt.model.ReceiptScheduleHUSelectModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.impl.ReceiptScheduleFiltering;
import de.metas.handlingunits.client.terminal.select.view.AbstractHUSelectFrame;

/**
 * Wareneingang (POS).
 *
 * Generates Material Receipts from receipt schedules.
 *
 * @author cg
 *
 */
public class ReceiptScheduleHUSelectFrame extends AbstractHUSelectFrame<ReceiptScheduleHUSelectModel>
{

	public ReceiptScheduleHUSelectFrame(final JFrame frame, final int windowNo)
	{
		super(frame, windowNo);
	}

	@Override
	protected ReceiptScheduleHUSelectPanel createHUSelectPanel()
	{
		final ITerminalContext terminalContext = getTerminalContext();

		terminalContext.registerService(IPOSFiltering.class, new ReceiptScheduleFiltering());

		return new ReceiptScheduleHUSelectPanel(terminalContext);
	}
}

package de.metas.handlingunits.client.terminal.pporder.view;

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
import de.metas.handlingunits.client.terminal.select.view.AbstractHUSelectFrame;
import de.metas.handlingunits.client.terminal.select.view.IHUSelectPanel;

/**
 * Decorates an given frame and embeds {@link HUIssuePanel} inside it
 *
 */
public class HUIssueFrame extends AbstractHUSelectFrame<HUIssuePanel>
{
	public HUIssueFrame(final JFrame frame, final int windowNo)
	{
		super(frame, windowNo);
	}

	@Override
	protected IHUSelectPanel createHUSelectPanel()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		return new HUIssuePanel(terminalContext);
	}
}

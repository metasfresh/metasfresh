package de.metas.handlingunits.client.terminal.aggregate.view;

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
import de.metas.handlingunits.client.terminal.aggregate.model.AggregateHUSelectModel;
import de.metas.handlingunits.client.terminal.select.api.IPOSFiltering;
import de.metas.handlingunits.client.terminal.select.api.impl.AggregateHUFiltering;
import de.metas.handlingunits.client.terminal.select.view.AbstractHUSelectFrame;

/**
 * Verdichtung (POS) frame panel
 *
 * @author tsa
 *
 */
public final class AggregateHUSelectFrame extends AbstractHUSelectFrame<AggregateHUSelectModel>
{
	public AggregateHUSelectFrame(final JFrame frame, final int windowNo)
	{
		super(frame, windowNo);
	}

	@Override
	protected AggregateHUSelectPanel createHUSelectPanel()
	{
		final AggregateHUSelectModel huSelectModel = createHUSelectModel();
		final AggregateHUSelectPanel huSelectPanel = new AggregateHUSelectPanel(huSelectModel);
		return huSelectPanel;
	}

	/**
	 * Creates and configures {@link AggregateHUSelectModel}.
	 *
	 * @return {@link AggregateHUSelectModel}
	 */
	protected final AggregateHUSelectModel createHUSelectModel()
	{
		final ITerminalContext terminalContext = getTerminalContext();

		// Register the service to be used
		terminalContext.registerService(IPOSFiltering.class, new AggregateHUFiltering());

		final AggregateHUSelectModel huSelectModel = new AggregateHUSelectModel(terminalContext);
		return huSelectModel;
	}
}

package de.metas.handlingunits.client.terminal.mmovement.model.impl;

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


import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;
import de.metas.handlingunits.client.terminal.mmovement.model.ILTCUModel;

/**
 * Mock class for {@link ILTCUModel}s
 *
 * @author al
 */
public class MockedLTCUModel extends AbstractLTCUModel
{
	public MockedLTCUModel(final ITerminalContext terminalContext)
	{
		super(terminalContext);
	}

	@Override
	public final void execute() throws MaterialMovementException
	{
		// nothing
	}

	@Override
	protected final void onCUPressed(final ITerminalKey key)
	{
		// nothing
	}

	@Override
	protected final void onTUPressed(final ITerminalKey key)
	{
		// nothing
	}

	@Override
	protected final void onLUPressed(final ITerminalKey key)
	{
		// nothing
	}
}

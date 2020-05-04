package de.metas.handlingunits.client.terminal.mmovement.model;

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


import java.beans.PropertyChangeListener;
import java.util.Properties;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.mmovement.exception.MaterialMovementException;

/**
 * Model which should stay at the base of any dialog which handles material movements (e.g Split, Join).
 *
 * @author al
 */
public interface IMaterialMovementModel extends IDisposable
{
	/**
	 * Add property change listener
	 *
	 * @param listener
	 */
	void addPropertyChangeListener(PropertyChangeListener listener);

	/**
	 * Add property change listener for given <code>propertyName</code>
	 *
	 * @param propertyName
	 * @param listener
	 */
	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * @return model terminal context
	 */
	ITerminalContext getTerminalContext();

	/**
	 * Called when user presses OK.
	 *
	 * Execute main model functionality (equivalent of a doOk)
	 *
	 * @throws MaterialMovementException
	 */
	void execute() throws MaterialMovementException;

	/**
	 * Clean up model
	 */
	@Override
	void dispose();

	/**
	 * @return layout constants
	 */
	Properties getLayoutConstants();
}

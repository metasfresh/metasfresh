/**
 *
 */
package de.metas.adempiere.form.terminal;

/*
 * #%L
 * de.metas.swat.base
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


/**
 * Interface for listening to on-screen key events
 *
 * @author tsa
 *
 */
public interface ITerminalKeyListener
{
	/**
	 * Called when user presses a Key, before any other listeners are called, key is selected.
	 *
	 * If there are exceptions thrown from this method, no further actions will be performed.<br/>
	 * You can implement this method if you want to do validations before switching current selected key.
	 *
	 * @param key
	 */
	void keyReturning(final ITerminalKey key);

	/**
	 * Called after using pressed a key.
	 *
	 * NOTE: at this stage, the key is already selected in UI
	 *
	 * @param key
	 */
	void keyReturned(ITerminalKey key);
}

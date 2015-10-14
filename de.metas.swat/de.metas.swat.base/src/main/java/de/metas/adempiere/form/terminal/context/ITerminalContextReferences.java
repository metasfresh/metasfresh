package de.metas.adempiere.form.terminal.context;

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


import org.adempiere.util.beans.WeakPropertyChangeSupport;

import de.metas.adempiere.form.terminal.IDisposable;

/**
 * {@link ITerminalContext}'s references.
 * 
 * Implementations of this interface holds a link to all created {@link IDisposable} components, {@link WeakPropertyChangeSupport}s etc.
 * 
 * When the {@link ITerminalContext} is destroyed the {@link #dispose()} method will be called which will destroy all components, clear all {@link WeakPropertyChangeSupport}s, so the memory leaks will
 * be avoided.
 * 
 * @author tsa
 *
 */
public interface ITerminalContextReferences
{
	/**
	 * Destroy all references. i.e.
	 * <ul>
	 * <li>dispose all disposable components (see {@link #addToDisposableComponents(IDisposable)})
	 * <li>remove all listeners from all {@link WeakPropertyChangeSupport}s
	 * </ul>
	 */
	void dispose();

	/**
	 * Adds given component to the internal list of disposable components.
	 * 
	 * @param comp
	 */
	void addToDisposableComponents(IDisposable comp);

	/**
	 * Creates a new {@link WeakPropertyChangeSupport} instance.
	 * 
	 * A link to this instance will be remembered so {@link #dispose()} will be able to clear the listeners.
	 * 
	 * @param sourceBean
	 * @return {@link WeakPropertyChangeSupport} instance
	 */
	WeakPropertyChangeSupport createPropertyChangeSupport(Object sourceBean);

	/**
	 * Creates a new {@link WeakPropertyChangeSupport} instance.
	 * 
	 * A link to this instance will be remembered so {@link #dispose()} will be able to clear the listeners.
	 * 
	 * @param sourceBean
	 * @param weakDefault
	 * @return {@link WeakPropertyChangeSupport} instance
	 */
	WeakPropertyChangeSupport createPropertyChangeSupport(Object sourceBean, boolean weakDefault);
}

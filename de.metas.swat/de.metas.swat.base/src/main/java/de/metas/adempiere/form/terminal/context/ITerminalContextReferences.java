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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.util.beans.WeakPropertyChangeSupport;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;

/**
 * A {@link ITerminalContext}'s references.
 * Use {@link ITerminalContext#newReferences()} in a try-with-resources statement to get and destroy an instance for a particular terminal dialog.<br>
 * If try-with-resources is not feasible, use {@link ITerminalContext#deleteReferences(ITerminalContextReferences)} to destroy an instance.
 *
 * Implementations of this interface hold a link to all created {@link IDisposable} components and {@link WeakPropertyChangeSupport}s.
 *
 * When {@link ITerminalContext#deleteReferences(ITerminalContextReferences)} is called,
 * then all {@link IDisposable} components registered with {@link #addToDisposableComponents(IDisposable)} are disposed.
 * Also, all property change support instances created with {@link #createPropertyChangeSupport(Object)} or {@link #createPropertyChangeSupport(Object, boolean)} are cleared.
 * The goal of this is to avoid memory leaks.
 *
 * @author tsa
 *
 */
public interface ITerminalContextReferences extends AutoCloseable
{
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

	/**
	 * Also see {@link ITerminalContext#getTextKeyLayout()}.
	 *
	 * @return default on-screen text (QWERTY) keyboard to be used
	 */
	IKeyLayout getTextKeyLayout();

	/**
	 * Set default on-screen text (QWERTY) keyboard to be used. Also see {@link ITerminalContext#setTextKeyLayout(IKeyLayout)}.
	 *
	 * @param keyLayout
	 */
	void setTextKeyLayout(IKeyLayout keyLayout);

	/**
	 * Analog to {@link #getTextKeyLayout()}.
	 *
	 * @return default on-screen numeric keyboard to be used
	 */
	IKeyLayout getNumericKeyLayout();

	/**
	 * Sets default on-screen numeric keyboard to be used. Analog to {@link #setTextKeyLayout(IKeyLayout)}.
	 *
	 * @param keyLayout
	 */
	void setNumericKeyLayout(IKeyLayout keyLayout);

	/**
	 * Call {@link ITerminalContext#deleteReferences(ITerminalContextReferences)} with this instance.
	 * Hint: please don't directly call this method. It's intended to be called by a {@code try}-with-resources statement.
	 *
	 */
	@Override
	void close();
}

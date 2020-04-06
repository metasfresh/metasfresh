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

/**
 * A {@link ITerminalContext}'s references.
 * Use {@link ITerminalContext#newReferences()} in a try-with-resources statement to get and destroy an instance for a particular terminal dialog.<br>
 * If try-with-resources is not feasible, use {@link ITerminalContext#deleteReferences(ITerminalContextReferences)} to destroy an instance.<br>
 * If you need to do something else, check out {@link ITerminalContext#closeCurrentReferences()} and {@link ITerminalContext#detachReferences(ITerminalContextReferences)}.
 *
 * Implementations of this interface hold a link to all created {@link IDisposable} components and {@link WeakPropertyChangeSupport}s.
 *
 * When {@link ITerminalContext#deleteReferences(ITerminalContextReferences)} is called,
 * then all {@link IDisposable} components that were registered with {@link ITerminalContext#addToDisposableComponents(IDisposable)} are disposed.
 * Also, all property change support instances created with {@link ITerminalContext#createPropertyChangeSupport(Object)} or {@link ITerminalContext#createPropertyChangeSupport(Object, boolean)} are cleared.
 * The goal of this is to avoid memory leaks.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ITerminalContextReferences extends AutoCloseable
{
	/**
	 * Call {@link ITerminalContext#deleteReferences(ITerminalContextReferences)} with this instance.
	 * <p>
	 * Please don't directly call this method. It's intended to be called by a {@code try}-with-resources statement.
	 *
	 */
	@Override
	void close();

	/**
	 * Disposes all components that were added to this instance and unregisters/destroys all property change support instances.
	 * <p>
	 * Hint: it is intended to only call this method on instances that were detached using {@link ITerminalContext#detachReferences(ITerminalContextReferences)}.
	 * In all other cases you might want to call {@link ITerminalContext#deleteReferences(ITerminalContextReferences)} with this instance as parameters.
	 *
	 * @task https://github.com/metasfresh/metasfresh/issues/458
	 */
	void dispose();
}

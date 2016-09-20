package de.metas.adempiere.form.terminal;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

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

/**
 * Implementations of this interface support {@link #dispose()} capability.<br>
 * Each graphical terminal component should implement it in order to avoid memory leak bug.
 * This interface works together closely with {@link ITerminalContext}.
 * <p>
 * Here are some <b>usage guidelines:</b>
 * <ul>
 * <li>when a disposable instance is created, it shall be added to the terminal context using {@link ITerminalContext#addToDisposableComponents(IDisposable)}.
 * That way, you dont't need to keep a reference around just in order to make sure the new disposable is disposed, but instead, the {@link ITerminalContext} will take care of the disposing.
 * Hint: either do it directly in your disposable implementaton's constructor, or make sure that the {@link ITerminalFactory} implementation makes the call to add you new instance.
 * <li>Keep your {@link #dispose()} implementation "local", i.e. if your implementation holds references to other disposables, please <b>do not</b> call their {@link #dispose()} method.
 * Calling {@link #dispose()} is the terminal context's job, while it's the IDisposable implementation's job to take care of disposing itself.
 * <li>Multiple {@link #dispose()} invocations shall do no harm.
 * </ul>
 *
 *
 * @author tsa
 *
 */
public interface IDisposable
{
	/**
	 * Clean up all internal state of this object/component (i.e. remove listeners, destroy underlying not {@link IDisposable} components etc).
	 * This method should be called only by the API. Also check out the guideline further up.
	 *
	 * After calling this method, this object is no longer usable.
	 */
	void dispose();

	/**
	 * @return <code>true</code> if {@link #dispose()} was already called.
	 * @return
	 */
	boolean isDisposed();
}

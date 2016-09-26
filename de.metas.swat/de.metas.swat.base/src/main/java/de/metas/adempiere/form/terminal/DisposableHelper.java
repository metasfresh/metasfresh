package de.metas.adempiere.form.terminal;

import java.util.Collection;

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

import org.slf4j.Logger;

import de.metas.logging.LogManager;

/**
 * Little tool class for {@link IDisposable}. The methods are lenient against parameters that are empty, <code>null</code> or have a non-IDisposable type.
 *
 * @author tsa
 *
 */
public final class DisposableHelper
{
	private static final Logger logger = LogManager.getLogger(DisposableHelper.class);

	/**
	 * Calls {@link IDisposable#dispose()} on all given <code>disposables</code>.
	 *
	 * @param disposables can be <code>null</code> or empty
	 */
	public static final void disposeAll(final IDisposable... disposables)
	{
		if (disposables == null || disposables.length <= 0)
		{
			return;  // nothing to do
		}

		for (final IDisposable disposable : disposables)
		{
			dispose(disposable);
		}
	}

	/**
	 * From the given collection, this method calls {@link IDisposable#dispose()} on those elements that are instance of {@link IDisposable} and that are not yet disposed.
	 *
	 * @param disposables can be <code>null</code> or empty
	 */
	public static final void disposeAll(final Collection<?> disposables)
	{
		if (disposables == null || disposables.isEmpty())
		{
			return; // nothing to do
		}

		final IDisposable[] array = disposables.stream()
				.filter(item -> (item instanceof IDisposable))
				.toArray(IDisposable[]::new);
		disposeAll(array);
	}

	/**
	 * If the given <code>disposable</code> is not <code>null</code> this method invokes its {@link IDisposable#dispose()} method.
	 *
	 * @param disposable may be <code>null</code>
	 * @return always return null (for convenience use)
	 */
	public static final <T extends IDisposable> T dispose(final T disposable)
	{
		if (disposable == null || disposable.isDisposed())
		{
			return null;
		}

		try
		{
			disposable.dispose();
		}
		catch (Exception e)
		{
			logger.warn("Cannot dispose " + disposable + ". Skipped.", e);
		}

		return null;
	}

	/**
	 * Iterates the given objects and calls {@link IDisposable#dispose()} on those of them that are actually <code>instanceof IDisposable</code>.
	 *
	 * @param disposables may be <code>null</code> or empty
	 */
	public static final void disposeAllObjects(final Object... disposables)
	{
		if (disposables == null || disposables.length <= 0)
		{
			return;
		}

		for (final Object object : disposables)
		{
			if (object == null)
			{
				continue;
			}
			if (object instanceof IDisposable)
			{
				final IDisposable disposable = (IDisposable)object;
				dispose(disposable);
			}
		}
	}
}

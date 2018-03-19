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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.SwingUtilities;

import org.adempiere.util.Check;
import org.adempiere.util.WeakList;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.DisposableHelper;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.logging.LogManager;

/* package */final class TerminalContextReferences implements ITerminalContextReferences
{
	private static final transient Logger logger = LogManager.getLogger(TerminalContextReferences.class);

	private List<WeakPropertyChangeSupport> propertyChangeSupports = null;
	private final List<IDisposable> _disposableComponents = new ArrayList<>();

	private final ITerminalContext terminalContext;

	private IKeyLayout keyLayoutNumeric;
	private IKeyLayout keyLayoutText;

	private boolean referencesClosed;

	/**
	 *
	 * @param terminalContext
	 */
	public TerminalContextReferences(final ITerminalContext terminalContext)
	{
		Check.assumeNotNull(terminalContext, "Parameter 'terminalContext"); // to make sure our close() implementation works
		this.terminalContext = terminalContext;
	}

	@Override
	public void dispose()
	{
		SwingUtilities.invokeLater(() -> {
			disposeComponents();

			// to this last, because some components wand to fire one last event while they are disposed.
			disposePropertyChangeSupports();
		});
	}

	public WeakPropertyChangeSupport createPropertyChangeSupport(final Object sourceBean)
	{
		final boolean weakDefault = false;
		return createPropertyChangeSupport(sourceBean, weakDefault);
	}

	public WeakPropertyChangeSupport createPropertyChangeSupport(final Object sourceBean, final boolean weakDefault)
	{
		if (propertyChangeSupports == null)
		{
			propertyChangeSupports = new WeakList<>(false); // weakDefault = false, we want to see problems
		}

		final WeakPropertyChangeSupport pcs = new WeakPropertyChangeSupport(sourceBean, weakDefault);
		propertyChangeSupports.add(pcs);

		return pcs;
	}

	public final void addToDisposableComponents(final IDisposable comp)
	{
		if (comp == null)
		{
			return;
		}
		_disposableComponents.add(comp);
	}

	public IKeyLayout getNumericKeyLayout()
	{
		return keyLayoutNumeric;
	}

	public void setNumericKeyLayout(final IKeyLayout keyLayoutNumeric)
	{
		this.keyLayoutNumeric = keyLayoutNumeric;
	}

	public IKeyLayout getTextKeyLayout()
	{
		return keyLayoutText;
	}

	public void setTextKeyLayout(final IKeyLayout keyLayoutText)
	{
		this.keyLayoutText = keyLayoutText;
	}

	/**
	 * Dispose all created components
	 */
	private final void disposeComponents()
	{
		// NOTE: we are disposing the components in the reverse order of their creation,
		// just because we want to give them the opportunity to dispose nicely
		final List<IDisposable> disposables = new ArrayList<>(_disposableComponents);
		Collections.reverse(disposables);
		DisposableHelper.disposeAll(disposables);
	}

	/**
	 * Clear all {@link WeakPropertyChangeSupport}s.
	 */
	private void disposePropertyChangeSupports()
	{
		if (propertyChangeSupports == null)
		{
			// already cleared
			return;
		}

		final int countAll = propertyChangeSupports.size();
		for (final WeakPropertyChangeSupport pcs : propertyChangeSupports)
		{
			pcs.clear();
		}
		propertyChangeSupports.clear();
		propertyChangeSupports = null;

		logger.info("Cleared {} property change supports", countAll);
	}

	@Override
	public String toString()
	{
		return "TerminalContextReferences [referencesClosed=" + referencesClosed + ", propertyChangeSupports=" + propertyChangeSupports + ", _disposableComponents=" + _disposableComponents + "]";
	}

	@Override
	public void close()
	{
		terminalContext.deleteReferences(this);
	}

	/**
	 * Not to be mixed up with the {@link AutoCloseable} method {@link #close()}.<br>
	 * See {@link ITerminalContext#closeCurrentReferences()} for what this is about.
	 */
	/* package */ void closeReferences()
	{
		referencesClosed = true;
	}

	boolean isReferencesClosed()
	{
		return referencesClosed;
	}
}

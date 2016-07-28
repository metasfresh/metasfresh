package de.metas.adempiere.form.terminal.event;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IClientUIAsyncInvoker.ClientUIAsyncRunnableAdapter;
import de.metas.adempiere.form.IClientUIAsyncInvoker.IClientUIAsyncExecutor;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.ITerminalFactory;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Wraps a {@link PropertyChangeListener} and triggers a long running operation which is executed in a background thread.
 * 
 * By default, the exceptions are caught and shown to user (see {@link #handleExceptionInUI(IClientUIAsyncExecutor, Throwable)}).
 * 
 * Briefly, following you shall implement following methods:
 * <ul>
 * <li>{@link #prepareInUI(IClientUIAsyncExecutor)}: (optional) prepare the UI before running the long operation
 * <li>{@link #runInBackground(IClientUIAsyncExecutor)}: do actual processing in a background thread
 * <li>{@link #partialUpdateUI(IClientUIAsyncExecutor, Object)}: (optional) update the UI with partial results (only if you are using {@link IClientUIAsyncExecutor#publishPartialResult(Object)} methods).
 * <li>{@link #finallyUpdateUI(IClientUIAsyncExecutor, Object)}: (optional) update the UI after everything was processed.
 * </ul>
 * 
 * If you want to access the {@link PropertyChangeEvent} which started this processing, please use {@link IClientUIAsyncExecutor#getInitialValue()}.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <ResultType>
 * @param <PartialResultType>
 */
public abstract class UIAsyncPropertyChangeListener<ResultType, PartialResultType>
		extends ClientUIAsyncRunnableAdapter<PropertyChangeEvent, ResultType, PartialResultType>
		implements PropertyChangeListener
{
	// NOTE: to prevent memory leaks we are storing terminalFactory and parent references as weak references
	private final WeakReference<ITerminalFactory> terminalFactoryRef;
	private final WeakReference<IComponent> parentRef;

	public UIAsyncPropertyChangeListener(final ITerminalFactory terminalFactory, final IComponent parent)
	{
		super();

		Check.assumeNotNull(terminalFactory, "terminalFactory not null");
		terminalFactoryRef = new WeakReference<ITerminalFactory>(terminalFactory);

		Check.assumeNotNull(parent, "parent not null");
		parentRef = new WeakReference<IComponent>(parent);
	}

	public UIAsyncPropertyChangeListener(final IComponent parent)
	{
		this(parent.getTerminalContext().getTerminalFactory(), parent);
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("parent", parentRef.get())
				.toString();
	}

	protected final IComponent getParent()
	{
		return parentRef.get();
	}

	@Override
	public final void propertyChange(final PropertyChangeEvent evt)
	{
		final Object component = parentRef.get().getComponent();
		Services.get(IClientUI.class).invokeAsync()
				.setParentComponent(component)
				.setInitialValue(evt)
				.invoke(this);
	}

	@Override
	public abstract ResultType runInBackground(IClientUIAsyncExecutor<PropertyChangeEvent, ResultType, PartialResultType> executor);

	@Override
	public void handleExceptionInUI(final IClientUIAsyncExecutor<PropertyChangeEvent, ResultType, PartialResultType> executor, final Throwable ex)
	{
		final ITerminalFactory terminalFactory = terminalFactoryRef.get();

		// If terminalFactory reference expired, we fallback to IClientUI
		if (terminalFactory == null)
		{
			final int windowNo = Env.WINDOW_MAIN;
			Services.get(IClientUI.class).warn(windowNo, ex);
		}
		else
		{
			final IComponent parent = parentRef.get();
			terminalFactory.showWarning(parent, "Error", ex);
		}
	}

}

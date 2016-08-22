package de.metas.adempiere.form.terminal.event;

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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.ref.WeakReference;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.base.MoreObjects;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.IClientUIInvoker.OnFail;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.ITerminalFactory;

/**
 * Wraps a {@link PropertyChangeListener} but catches the exceptions and show them to user.
 * 
 * If your {@link #propertyChangeEx(PropertyChangeEvent)} will perform a long running operation, please consider using {@link UIAsyncPropertyChangeListener}.
 *
 * @author tsa
 *
 */
public abstract class UIPropertyChangeListener implements PropertyChangeListener
{
	// NOTE: to prevent memory leaks we are storing terminalFactory and parent references as weak references
	private final WeakReference<ITerminalFactory> terminalFactoryRef;
	private final WeakReference<IComponent> parentRef;

	private boolean showGlassPane = false;

	public UIPropertyChangeListener(final ITerminalFactory terminalFactory, final IComponent parent)
	{
		super();

		Check.assumeNotNull(terminalFactory, "terminalFactory not null");
		terminalFactoryRef = new WeakReference<ITerminalFactory>(terminalFactory);

		Check.assumeNotNull(parent, "parent not null");
		parentRef = new WeakReference<IComponent>(parent);

		init();
	}

	public UIPropertyChangeListener(final IComponent parent)
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
		//
		// Display cursor "loading" (if possible)
		final Object component = parentRef.get().getComponent();
		if (component == null)
		{
			try
			{
				propertyChangeEx(evt);
			}
			catch (Exception e)
			{
				handleException(e);
			}
			return;
		}
		else
		{
			Services.get(IClientUI.class)
					.invoke()
					.setParentComponent(component)
					.setLongOperation(true)
					.setShowGlassPane(showGlassPane)
					.setOnFail(OnFail.UseHandler)
					.setExceptionHandler((ex) -> handleException(ex))
					.invoke(() -> propertyChangeEx(evt));
		}
	}

	private void handleException(final Exception e)
	{
		final ITerminalFactory terminalFactory = terminalFactoryRef.get();
		final IComponent parent = parentRef.get();

		// If terminalFactory reference expired, we fallback to IClientUI
		if (terminalFactory == null)
		{
			final int windowNo = Env.WINDOW_MAIN;
			Services.get(IClientUI.class).warn(windowNo, e);
		}
		else
		{
			terminalFactory.showWarning(parent, "Error", e);
		}
	}

	/**
	 * Sets if we shall display a window glass pane while running this listener.
	 * 
	 * @param showGlassPane
	 */
	protected final void setShowGlassPane(final boolean showGlassPane)
	{
		this.showGlassPane = showGlassPane;
	}

	/**
	 * Method called when this listener is initialized.
	 * 
	 * Does nothing at this level.
	 * Extending classes shall override it.
	 */
	protected void init()
	{
		// nothing on this level
	}

	protected abstract void propertyChangeEx(PropertyChangeEvent evt);
}

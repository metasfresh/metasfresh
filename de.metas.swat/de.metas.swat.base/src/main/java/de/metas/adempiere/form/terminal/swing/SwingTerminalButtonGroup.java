package de.metas.adempiere.form.terminal.swing;

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

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JToggleButton;

import org.adempiere.util.Check;

import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalButtonGroup;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/* package */class SwingTerminalButtonGroup<T> implements ITerminalButtonGroup<T>
{
	private final ITerminalFactory terminalFactory;

	private final PropertyChangeSupport listeners;

	private final List<ITerminalButton> buttons = new ArrayList<ITerminalButton>();
	private final List<ITerminalButton> buttonsRO = Collections.unmodifiableList(buttons);
	private final Map<ButtonModel, T> buttonModel2value = new IdentityHashMap<ButtonModel, T>();
	private final Map<T, ButtonModel> value2buttonModel = new IdentityHashMap<T, ButtonModel>();

	private final ButtonGroup buttonGroup = new ButtonGroup()
	{
		private static final long serialVersionUID = -8394244813841231487L;

		private final void fireSelectionChanged(final ButtonModel selectedButtonModelOld)
		{
			final ButtonModel selectedButtonModelNew = getSelection();
			if (selectedButtonModelOld == selectedButtonModelNew)
			{
				return;
			}

			final T valueOld = getValue(selectedButtonModelOld);
			final T valueNew = getValue(selectedButtonModelNew);

			listeners.firePropertyChange(ACTION_ValueChanged, valueOld, valueNew);
		}

		@Override
		public void add(final AbstractButton b)
		{
			final ButtonModel selectedButtonModelOld = getSelection();
			super.add(b);
			fireSelectionChanged(selectedButtonModelOld);
		}

		@Override
		public void remove(final AbstractButton b)
		{
			final ButtonModel selectedButtonModelOld = getSelection();
			super.remove(b);
			fireSelectionChanged(selectedButtonModelOld);
		}

		@Override
		public void clearSelection()
		{
			final ButtonModel selectedButtonModelOld = getSelection();
			super.clearSelection();
			fireSelectionChanged(selectedButtonModelOld);
		}

		@Override
		public void setSelected(final ButtonModel m, final boolean b)
		{
			final ButtonModel selectedButtonModelOld = getSelection();
			super.setSelected(m, b);
			fireSelectionChanged(selectedButtonModelOld);
		}
	};

	private boolean disposed = false;

	/* package */ SwingTerminalButtonGroup(final ITerminalFactory terminalFactory)
	{
		Check.assumeNotNull(terminalFactory, "terminalFactory not null");
		this.terminalFactory = terminalFactory;

		final ITerminalContext terminalContext = terminalFactory.getTerminalContext();
		this.listeners = terminalContext.createPropertyChangeSupport(this);

		terminalContext.addToDisposableComponents(this);
	}

	@Override
	public final void addListener(final PropertyChangeListener listener)
	{
		listeners.addPropertyChangeListener(listener);
	}

	@Override
	public final void addListener(final String propertyName, final PropertyChangeListener listener)
	{
		listeners.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public final void removeListener(final PropertyChangeListener listener)
	{
		listeners.removePropertyChangeListener(listener);
	}

	@Override
	public final void removeListener(final String propertyName, final PropertyChangeListener listener)
	{
		listeners.removePropertyChangeListener(propertyName, listener);
	}

	@Override
	public ITerminalButton addButton(final T value, final String buttonText)
	{
		//
		// Validate button value
		Check.assumeNotNull(value, "value not null");
		if (value2buttonModel.containsKey(value))
		{
			throw new IllegalArgumentException("Button value already exist");
		}

		//
		// Create Swing Button
		final JToggleButton buttonSwing = new JToggleButton(buttonText, false);
		buttonSwing.setFocusable(false); // it's anoying to have these buttons focusable
		// Validate Swing Button model
		final ButtonModel buttonModel = buttonSwing.getModel();
		Check.assumeNotNull(buttonModel, "buttonModel not null"); // shall not happen, but just in case...

		//
		// Create terminal button
		final ITerminalButton button = new SwingTerminalButtonWrapper(terminalFactory.getTerminalContext(), buttonSwing);

		//
		// Add button to all maps and lists (for optimized later use and search)
		value2buttonModel.put(value, buttonModel);
		buttonModel2value.put(buttonModel, value);
		buttonGroup.add(buttonSwing);
		buttons.add(button);

		//
		// Return our button
		return button;
	}

	private final T getValue(final ButtonModel buttonModel)
	{
		if (buttonModel == null)
		{
			return null;
		}
		final T value = buttonModel2value.get(buttonModel);
		Check.assumeNotNull(value, "value shall exist for {}", buttonModel);
		return value;
	}

	@Override
	public T getValue()
	{
		final ButtonModel buttonModel = buttonGroup.getSelection();
		final T value = getValue(buttonModel);
		return value;
	}

	@Override
	public void setValue(final T value)
	{
		if (value == null)
		{
			buttonGroup.clearSelection();
			return;
		}

		final ButtonModel buttonModel = value2buttonModel.get(value);
		if (buttonModel == null)
		{
			throw new IllegalArgumentException("value '" + value + "'was not found in " + value2buttonModel.keySet());
		}

		buttonGroup.setSelected(buttonModel, true);
	}

	@Override
	public List<ITerminalButton> getButtons()
	{
		return buttonsRO;
	}

	/**
	 * Does nothing besides setting the internal disposed flag.
	 */
	@Override
	public void dispose()
	{
		disposed = true;

	}

	@Override
	public boolean isDisposed()
	{
		return disposed;
	}
}

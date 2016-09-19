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


import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.beans.WeakPropertyChangeSupport;

import de.metas.adempiere.form.IInputMethod;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;

public abstract class AbstractPropertiesPanelModel implements IPropertiesPanelModel, IDisposable
{
	private final ITerminalContext terminalContext;
	private final WeakPropertyChangeSupport pcs;
	private boolean disposed = false;

	public AbstractPropertiesPanelModel(final ITerminalContext terminalContext)
	{
		super();

		this.terminalContext = terminalContext;
		pcs = terminalContext.createPropertyChangeSupport(this);

		terminalContext.addToDisposableComponents(this);
	}

	/**
	 * Dispose this component.
	 *
	 * NOTE: The {@link #isDisposed()} flag will be eagerly set, so it's highly recommended, in your extending method to call this first.
	 */
	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		// Eagerly set the disposed flag.
		// We do this because other methods are rellying on this while they are disposing.
		this.disposed = true;
		// pcs.clear(); not needed. pcs will be cleared by the terminalContext
	}

	/** @return true if this component is already disposed or disposing */
	@Override
	public boolean isDisposed()
	{
		return disposed;
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	@Override
	public final void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
		pcs.addPropertyChangeListener(listener);
	}

	@Override
	public final void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(propertyName, listener);
		pcs.addPropertyChangeListener(propertyName, listener);
	}

	@Override
	public final void removePropertyChangeListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(listener);
	}

	@Override
	public final void removePropertyChangeListener(final String propertyName, final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(propertyName, listener);
	}

	protected final void firePropertyChanged(final String propertyName, final Object valueOld, final Object valueNew)
	{
		pcs.firePropertyChange(propertyName, valueOld, valueNew);
	}

	/**
	 * Fires {@link IPropertiesPanelModel#PROPERTY_ValueChanged} event.
	 *
	 * @param propertyName
	 */
	protected final void firePropertyValueChanged(final String propertyName)
	{
		firePropertyChanged(PROPERTY_ValueChanged, null, propertyName);
	}

	/**
	 * Fires {@link IPropertiesPanelModel#PROPERTY_ContentChanged} event.
	 *
	 * @param debugPropertyName
	 */
	protected final void fireContentChanged()
	{
		firePropertyChanged(IPropertiesPanelModel.PROPERTY_ContentChanged, false, true);
	}

	/**
	 * By default, no constraints necessary. Will be overridden in extending classes as necessary.
	 *
	 * @return <code>null</code>
	 */
	@Override
	public ITerminalFieldConstraint<Object> getConstraint(final String propertyName)
	{
		return null;
	}

	/**
	 * By default there are no additional actions, so this method returns the empty list. Will be overridden in extending classes as necessary.
	 *
	 * @return an empty list
	 */
	@Override
	public List<IInputMethod<?>> getAdditionalInputMethods(final String propertyName)
	{
		return Collections.emptyList();
	}

	@Override
	public void validateUI()
	{
		firePropertyChanged(PROPERTY_ValidateUIRequest, false, true);
	}
}

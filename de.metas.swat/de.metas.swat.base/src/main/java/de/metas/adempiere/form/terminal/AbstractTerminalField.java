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
import java.util.Properties;
import java.util.logging.Level;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.adempiere.util.Check;
import org.adempiere.util.beans.WeakPropertyChangeSupport;
import org.compiere.util.CLogger;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.field.constraint.CompositeTerminalFieldConstraint;
import de.metas.adempiere.form.terminal.field.constraint.ITerminalFieldConstraint;

public abstract class AbstractTerminalField<T> implements ITerminalField<T>
{
	private final transient CLogger logger = CLogger.getCLogger(getClass());

	private final ITerminalContext terminalContext;
	private final WeakPropertyChangeSupport listeners;
	private final CompositeTerminalFieldConstraint<T> constraints = new CompositeTerminalFieldConstraint<T>();
	private boolean disposed = false;

	public AbstractTerminalField(final ITerminalContext terminalContext)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = terminalContext;

		this.listeners = terminalContext.createPropertyChangeSupport(this);
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public final Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	public final ITerminalFactory getTerminalFactory()
	{
		return getTerminalContext().getTerminalFactory();
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

	protected final void firePropertyChange(final String propertyName, final Object oldValue, final Object newValue)
	{
		listeners.firePropertyChange(propertyName, oldValue, newValue);
	}

	@Override
	public final void setValue(final Object value)
	{
		final boolean fireEvent = true;
		setValue(value, fireEvent);
	}

	@Override
	public final void setValue(final Object value, final boolean fireEvent)
	{
		try
		{
			// Make sure we are not setting values to an disposed component.
			if (isDisposed())
			{
				final TerminalException ex = new TerminalException("Atempt to set value but field is disposed. Set was ignored."
						+ "\n field: " + this
						+ "\n value: " + value
						+ "\n fireEvent: " + fireEvent);
				logger.log(Level.WARNING, ex.getLocalizedMessage(), ex);
				return;
			}

			final T valueConv = convertValueToType(value);
			constraints.evaluate(this, valueConv);
			setFieldValue(valueConv, fireEvent);
		}
		catch (final Exception ex)
		{
			//
			// Reset field value
			// TODO: find a way to use a default value fallback / or fallback to the old value
			setFieldValue(null, fireEvent);
			showWarningAndRequestFocus(ex);
		}
	}

	/**
	 * @param value
	 * @return value converted to {@link ITerminalField} returnType
	 */
	protected abstract T convertValueToType(final Object value);

	/**
	 * Set field value. Use <code>fireEvent</code>, to decide whether to fire events in the implementing class or not.<br>
	 * <br>
	 * Do not worry about constraints, as they're already evaluated in {@link #setValue(Object)}.
	 * 
	 * WARNING: never ever call this method directly, it's supposed to be called only by {@link #setValue(Object, boolean)}.
	 * 
	 * @param value
	 * @param fireEvent
	 */
	protected abstract void setFieldValue(final T value, final boolean fireEvent);

	@Override
	public final T getValue()
	{
		final T value;
		try
		{
			value = getFieldValue();
			// constraints.evaluate(this, value);
		}
		catch (final Exception ex)
		{
			final WrongValueException wve = wrapExceptionInWVE(ex);
			throw wve;
		}

		return value;
	}

	/**
	 * @return field value
	 * @throws WrongValueException
	 */
	protected abstract T getFieldValue() throws WrongValueException;

	@Override
	public final void addConstraint(final ITerminalFieldConstraint<T> constraint)
	{
		constraints.addConstraint(constraint);
	}

	/**
	 * Calls the terminalContext's {@link ITerminalFactory#showWarning(IComponent, String, String, Exception)}.
	 * 
	 * @param ex
	 */
	protected final void showWarning(final Exception ex)
	{
		//
		// Wrap exception in a WrongValueException if type differs
		final WrongValueException wve = wrapExceptionInWVE(ex);

		final ITerminalContext terminalContext = getTerminalContext();
		Check.assumeNotNull(terminalContext, "terminalContext not null");
		final ITerminalFactory terminalFactory = terminalContext.getTerminalFactory();
		Check.assumeNotNull(terminalFactory, "terminalFactory not null");
		terminalFactory.showWarning(this, ITerminalFactory.TITLE_ERROR, wve.getLocalizedMessage(), wve);
	}

	/**
	 * Calls the terminalContext's {@link ITerminalFactory#showWarning(IComponent, String, String, Exception)} and requestFocus on the component.
	 * 
	 * @param ex
	 */
	protected final void showWarningAndRequestFocus(final Exception ex)
	{
		showWarning(ex);
		requestFocus();
	}

	private final WrongValueException wrapExceptionInWVE(final Exception ex)
	{
		if (ex == null)
		{
			// TODO see if this case needs to be fine-tuned or replaced with a Check.assumeNotNull
			return new WrongValueException(this, "WrongValue");
		}
		else if (ex instanceof WrongValueException)
		{
			return (WrongValueException)ex;
		}
		else
		{
			return new WrongValueException(this, ex.getLocalizedMessage(), ex);
		}
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		disposed = true;
		constraints.clear();
		listeners.clear();
	}

	protected final boolean isDisposed()
	{
		return disposed;
	}

	/**
	 * @return true
	 */
	@Override
	public boolean isValid()
	{
		return true;
	}

}

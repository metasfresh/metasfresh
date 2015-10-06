/**
 * 
 */
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


import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.logging.Level;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.compiere.util.CLogger;
import org.compiere.util.DisplayType;
import org.compiere.util.KeyNamePair;
import org.compiere.util.Util;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author tsa
 */
public abstract class AbstractTerminalLookupField
		extends AbstractTerminalField<KeyNamePair>
		implements ITerminalLookupField
{
	protected final CLogger logger = CLogger.getCLogger(getClass());

	public static enum Status
	{
		None, Validated, Error
	};

	private ITerminalTextField textField;
	private ITerminalLookup lookup;

	private KeyNamePair value;
	private Status status;

	private class FieldListener implements PropertyChangeListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			if (evt.getSource() != textField)
			{
				return;
			}

			if (ITerminalTextField.PROPERTY_FocusLost.equals(evt.getPropertyName()))
			{
				lookupValidate();
			}
			else if (ITerminalTextField.PROPERTY_TextChanged.equals(evt.getPropertyName())
					|| ITerminalTextField.PROPERTY_TextChanged.equals(evt.getPropertyName()))
			{
				lookupValidate();
			}
		}
	}

	protected AbstractTerminalLookupField(final ITerminalContext tc, final String title, final ITerminalLookup lookup)
	{
		super(tc);

		// NOTE: could be null too
		this.lookup = lookup;

		textField = getTerminalFactory().createTerminalTextField(title, DisplayType.String);
		textField.registerFactory(ITerminalKeyDialog.class, new IFactory<ITerminalKeyDialog>()
		{
			@Override
			public ITerminalKeyDialog create(final Object source)
			{
				assert Util.same(source, textField);

				final ITerminalKeyDialog keyboard = getTerminalFactory().createTerminalKeyDialog(textField);
				keyboard.setTerminalLookupField(AbstractTerminalLookupField.this);
				return keyboard;
			}
		});

		setStatus(Status.None);

		textField.addListener(new FieldListener());
	}

	@Override
	public int getID()
	{
		return value != null ? value.getKey() : -1;
	}

	private void lookupValidate()
	{
		validate();
	}

	@Override
	public void validate()
	{
		final KeyNamePair value = getValue();
		final String text = getText();

		if (value != null && value.getName() != null && value.getName().equals(text))
		{
			logger.fine("Already validated: value=" + value + ", text=" + text);
			return;
		}

		//
		// Try to validate it using a lookup
		if (lookup != null)
		{
			final KeyNamePair knp;
			try
			{
				knp = lookup.resolve(text);

				if (!Check.isEmpty(text) && knp == null)
				{
					setStatus(Status.Error);
				}
			}
			catch (final TerminalException ex)
			{
				showError(ex);
				setFieldValue0(null, false, true); // reset value, don't reset text, fire event
				return;
			}

			setFieldValue0(knp, false, true); // set value, don't reset text if is null, fire event
		}
	}

	protected void showError(final TerminalException e)
	{
		setStatus(Status.Error);
		// getTerminalFactory().showWarning(this, ITerminalFactory.TITLE_WARN, e);
		if (logger.isLoggable(Level.FINE))
		{
			logger.log(Level.FINE, e.getLocalizedMessage(), e);
		}
	}

	@Override
	public Object getComponent()
	{
		return textField.getComponent();
	}

	@Override
	public String getText()
	{
		return textField.getText();
	}

	public void setText(final String text)
	{
		textField.setText(text);
		lookupValidate();
	}

	@Override
	protected KeyNamePair convertValueToType(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof Number)
		{
			Check.assumeNotNull(lookup, "lookup shall be set");
			final int id = ((Number)value).intValue();
			final KeyNamePair knp = lookup.resolveById(id);
			return knp;
		}
		else if (value instanceof KeyNamePair)
		{
			return (KeyNamePair)value;
		}
		else
		{
			throw new AdempiereException("Value class not supported - " + value + " (class: " + value.getClass() + ")");
		}
	}

	/**
	 * Not implemented with <code>fireEvent</code>
	 * 
	 * @see {@link #setFieldValue(Object)}
	 */
	@Override
	protected void setFieldValue(final KeyNamePair value, final boolean fireEvent)
	{
		final boolean resetTextOnNull = true;
		setFieldValue0(value, resetTextOnNull, fireEvent);
	}

	private void setFieldValue0(final KeyNamePair valueNew, final boolean resetTextOnNull, final boolean fireEvent)
	{
		if (logger.isLoggable(Level.FINE))
		{
			logger.fine("start: valueNew=" + valueNew + ", resetTextOnNull=" + resetTextOnNull + ", fireEvent=" + fireEvent);
		}

		final KeyNamePair valueOld = value;
		final int oldId = value == null ? -1 : value.getKey();
		final int newId = valueNew == null ? -1 : valueNew.getKey();

		value = valueNew;

		if (newId > 0 && valueNew != null)
		{
			textField.setText(valueNew.getName());
		}
		else if (resetTextOnNull)
		{
			textField.setText("");
		}

		// Logging
		if (logger.isLoggable(Level.FINE))
		{
			logger.fine("value=" + (valueOld == null ? null : valueOld.toStringX()) + "->" + (valueNew == null ? null : valueNew.toStringX()) + ", text=" + textField.getText());
		}

		//
		// Update field status
		if (value != null)
		{
			setStatus(Status.Validated);
		}
		else if (!Check.isEmpty(getText(), false))
		{
			setStatus(Status.Error);
		}
		else
		{
			setStatus(Status.None);
		}

		if (fireEvent && oldId != newId)
		{
			firePropertyChange(ITerminalField.ACTION_ValueChanged, oldId, newId);
		}
	}

	@Override
	public void setID(final int id, final boolean fireEvent)
	{
		Check.assumeNotNull(lookup, "lookup shall be set");
		final KeyNamePair knp = lookup.resolveById(id);
		final boolean resetTextOnNull = true;
		setFieldValue0(knp, resetTextOnNull, fireEvent);
	}

	@Override
	protected KeyNamePair getFieldValue()
	{
		return value;
	}

	protected boolean isTextChanged()
	{
		if (value == null)
		{
			return true;
		}
		return !getText().equals(value.getName());
	}

	@Override
	public void requestFocus()
	{
		textField.requestFocus();
	}

	@Override
	public String getName()
	{
		return textField.getName();
	}

	@Override
	public ITerminalLookup getLookup()
	{
		return lookup;
	}

	@Override
	public void setLookup(final ITerminalLookup lookup)
	{
		if (this.lookup == lookup)
		{
			return;
		}

		this.lookup = lookup;
		lookupValidate();
	}

	public Status getStatus()
	{
		return status;
	}

	private void setStatus(final Status statusNew)
	{
		if (logger.isLoggable(Level.FINE))
		{
			logger.fine("Status change: " + status + " -> " + statusNew);
		}

		status = statusNew;

		switch (status)
		{
			case Validated:
				textField.setBackground(new Color(200, 255, 200));
				break;
			case Error:
				textField.setBackground(new Color(255, 200, 200));
				break;
			case None:
			default:
				textField.setBackground(new Color(255, 255, 255));
				break;
		}
	}

	@Override
	public ITerminalTextField getTextField()
	{
		return textField;
	}

	@Override
	public boolean isEditable()
	{
		return textField.isEditable();
	}

	@Override
	public void setEditable(final boolean editable)
	{
		textField.setEditable(editable);
	}

	@Override
	public void dispose()
	{
		super.dispose();

		textField.dispose();

		lookup = null;
	}

	@Override
	public final boolean isValid()
	{
		final Status status = getStatus();
		return status != Status.Error;
	}
}

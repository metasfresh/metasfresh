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


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;

import org.adempiere.util.Check;
import org.adempiere.util.NumberUtils;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.logging.LogManager;

/**
 * @author tsa
 */
public abstract class AbstractTerminalNumericField
		extends AbstractTerminalField<BigDecimal>
		implements ITerminalNumericField
{
	private final transient Logger log = LogManager.getLogger(getClass());

	private final String name;
	private final int displayType;
	private final float fontSize;
	private final boolean withButtons;
	private final boolean withLabel;
	private boolean editable = true;

	protected ITerminalButton bPlus;
	protected ITerminalButton bMinus;
	protected IContainer panel;
	protected ITerminalTextField fNumber;

	protected String constraints = SwingTerminalFactory.BUTTON_Constraints;

	private BigDecimal increment = BigDecimal.ONE;

	@Override
	public void setMinusRO(final boolean ro)
	{
		if (withButtons)
		{
			bMinus.setEnabled(!ro);
		}
	}

	@Override
	public void setPlusRO(final boolean ro)
	{
		if (withButtons)
		{
			bPlus.setEnabled(!ro);
		}
	}

	@Override
	public void setNumberRO(final boolean ro)
	{
		fNumber.setEditable(!ro);
	}

	private class MinusButtonAction implements PropertyChangeListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			decValue();
		}
	}

	private class PlusButtonAction implements PropertyChangeListener
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			incValue();
		}
	}

	/**
	 * Note: does not care for "ValueChanged" or "TextChanged" events from our inner text component. That's fine because those are generally coming from within this very component
	 */
	private final PropertyChangeListener numberChangeListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			if (ITerminalTextField.PROPERTY_ActionPerformed.equals(evt.getPropertyName()))
			{
				final BigDecimal valueOldBD = convertValueToType(evt.getOldValue());
				final BigDecimal valueNewBD = convertValueToType(evt.getNewValue());
				firePropertyChange(ITerminalField.ACTION_ValueChanged, valueOldBD, valueNewBD);
			}
			else if (ITerminalTextField.PROPERTY_FocusLost.equals(evt.getPropertyName()))
			{
				try
				{
					setValue(getValue(), true);
				}
				catch (final Exception ex)
				{
					showWarningAndRequestFocus(ex);
				}
			}
		}
	};

	protected AbstractTerminalNumericField(final ITerminalContext tc, final String name, final int displayType)
	{
		this(tc, name, displayType, true, true);
	}

	protected AbstractTerminalNumericField(final ITerminalContext tc,
			final String name,
			final int displayType,
			final float fontSize,
			final boolean withButtons,
			final boolean withLabel,
			final String constr)
	{
		super(tc);

		this.name = name;
		this.displayType = displayType;
		if (fontSize > 0)
		{
			this.fontSize = fontSize;
		}
		else
		{
			this.fontSize = tc.getDefaultFontSize();
		}

		this.withButtons = withButtons;
		this.withLabel = withLabel;
		if (!Check.isEmpty(constr, true))
		{
			constraints = constr;
		}

		initComponents();
		initUI();
		setValue(BigDecimal.ZERO, false);
	}

	protected AbstractTerminalNumericField(final ITerminalContext tc, final String name, final int displayType, final boolean withButtons, final boolean withLabel, final String constr)
	{
		this(tc, name, displayType, 0f, withButtons, withLabel, constr);
	}

	protected AbstractTerminalNumericField(final ITerminalContext tc, final String name, final int displayType, final boolean withButtons, final boolean withLabel)
	{
		this(tc, name, displayType, withButtons, withLabel, null);
	}

	private void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		fNumber = factory.createTerminalTextField(getName(), getDisplayType(), getFontSize());
		fNumber.addListener(numberChangeListener);

		final String panelColumnConstraints;
		if (withButtons)
		{
			bMinus = factory.createButtonAction(ITerminalNumericField.ACTION_Minus);
			bMinus.addListener(new MinusButtonAction());
			bPlus = factory.createButtonAction(ITerminalNumericField.ACTION_Plus);
			bPlus.addListener(new PlusButtonAction());

			panelColumnConstraints = "[][][]"; // 3 Columns: Minus button, Qty numeric field, Plus button

		}
		else
		{
			panelColumnConstraints = ""; // nothing, we have only the Qty numberic field
		}

		final String panelRowConstraints;
		if (withLabel)
		{
			panelRowConstraints = "[]" // Label row
					+ "0" // gap between rows
					+ "[shrink 0]"; // Qty field row
		}
		else
		{
			panelRowConstraints = "[shrink 0]"; // Qty field row only
		}

		final String panelLayoutConstraints = "insets 0";
		panel = factory.createContainer(
				panelLayoutConstraints, // Layout Constraints
				panelColumnConstraints, // Column constraints
				panelRowConstraints // Row constrants
				);
	}

	@Override
	public void setReadOnly(final boolean ro)
	{
		final boolean editable = !ro;
		setEditable(editable);
	}

	@Override
	public void setEditable(final boolean editable)
	{
		this.editable = editable;

		fNumber.setEditable(editable);
		if (withButtons)
		{
			bMinus.setEnabled(editable);
			bPlus.setEnabled(editable);
		}
	}

	@Override
	public boolean isEditable()
	{
		return editable;
	}

	protected abstract void initUI();

	@Override
	protected BigDecimal getFieldValue()
	{
		String text = fNumber.getText();
		if (text == null)
		{
			return BigDecimal.ZERO;
		}
		text = text.trim();
		if (text.isEmpty())
		{
			return BigDecimal.ZERO;
		}

		final Format format = fNumber.getFormat();
		try
		{
			if (format == null)
			{
				return new BigDecimal(text);
			}
			else if (format instanceof DecimalFormat)
			{
				final DecimalFormat df = (DecimalFormat)format;
				df.setParseBigDecimal(true);
				final BigDecimal bd = (BigDecimal)df.parse(text);
				return bd;
			}
			else
			{
				log.info("Invalid Format '{}' to be used to convert text '{}' to BigDecimal. Assuming ZERO.", new Object[] { format, text });
				return BigDecimal.ZERO;
			}
		}
		catch (final Exception e)
		{
			throw new WrongValueException(this, "@" + ITerminalField.MSG_ErrorParsingText + "@ " + text, e);
		}
	}

	@Override
	protected BigDecimal convertValueToType(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof BigDecimal)
		{
			return (BigDecimal)value;
		}
		else if (value instanceof Integer)
		{
			return BigDecimal.valueOf((Integer)value);
		}
		else if (value instanceof String)
		{
			final String valueStr = value.toString().trim();
			if (valueStr.isEmpty())
			{
				return null;
			}
			else
			{
				return new BigDecimal(valueStr);
			}
		}
		else
		{
			return new BigDecimal(value.toString());
		}
	}

	@Override
	protected final void setFieldValue(final BigDecimal value, final boolean fireEvent)
	{
		final BigDecimal valueOld = this._valueOld; // backup for event

		//
		// Fix value to set
		final BigDecimal valueNew;
		if (value == null)
		{
			valueNew = BigDecimal.ZERO;
		}
		else
		{
			// Qty Editor it shall be a small component and we don't want to clutter it with pointless trailing zeros (06112)
			valueNew = NumberUtils.stripTrailingDecimalZeros(value);
		}

		//
		// Get the number editor
		final ITerminalTextField fNumber = this.fNumber;
		if (isDisposed() || fNumber == null)
		{
			// shall not happen but we are throwing an exception because we got a weird case (FRESH-331)
			new TerminalException("Atempt to set value but field is disposed."
					+ "\n field: " + this
					+ "\n value: " + valueOld + "->" + valueNew
					+ "\n fireEvent: " + fireEvent
					+ "\n fNumber: " + fNumber)
							.throwIfDeveloperModeOrLogWarningElse(log);
			return;
		}

		//
		// Actually setting the new value
		fNumber.setText(valueNew.toString());

		this._valueOld = valueNew;

		//
		// Fire event
		if (fireEvent)
		{
			final boolean changed = valueOld == null || valueOld.compareTo(valueNew) != 0;
			if(changed)
			{
				firePropertyChange(ITerminalField.ACTION_ValueChanged, valueOld, valueNew);
			}
		}
	}

	private BigDecimal _valueOld = null;

	public void incValue()
	{
		setValue(getValue().add(increment));
	}

	public void decValue()
	{
		setValue(getValue().subtract(increment));
	}

	public BigDecimal getIncrement()
	{
		return increment;
	}

	public void setIncrement(final BigDecimal increment)
	{
		this.increment = increment;
	}

	@Override
	public String getName()
	{
		return name;
	}

	public int getDisplayType()
	{
		return displayType;
	}

	public float getFontSize()
	{
		return fontSize;
	}

	@Override
	public void requestFocus()
	{
		fNumber.requestFocus();
	}

	public boolean isWithButtons()
	{
		return withButtons;
	}

	public boolean isWithLabel()
	{
		return withLabel;
	}
}

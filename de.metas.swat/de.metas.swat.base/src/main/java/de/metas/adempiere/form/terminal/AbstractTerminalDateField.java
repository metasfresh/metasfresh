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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.adempiere.util.Check;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import de.metas.adempiere.form.terminal.context.ITerminalContext;

public abstract class AbstractTerminalDateField
		extends AbstractTerminalField<Date>
		implements ITerminalDateField

{
	private static final String ACTION_PreviousDay = "PreviousDay";
	private static final String ACTION_PreviousMonth = "PreviousMonth";
	private static final String ACTION_NextDay = "NextDay";
	private static final String ACTION_NextMonth = "NextMonth";

	private String name;
	private final SimpleDateFormat dateFormat;
	private Date valueOld;

	//
	private IContainer panel;
	private ITerminalButton bPreviousDay;
	private ITerminalButton bPreviousMonth;
	private ITerminalButton bNextDay;
	private ITerminalButton bNextMonth;
	private ITerminalTextField fText;

	/** Listener triggered when user is changing the date's text field */
	private final PropertyChangeListener textChangeListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(final PropertyChangeEvent evt)
		{
			try
			{
				if (ITerminalTextField.PROPERTY_ActionPerformed.equals(evt.getPropertyName()))
				{
					final Date valueOld = convertValueToType(evt.getOldValue());
					final Date valueNew = convertValueToType(evt.getNewValue());
					firePropertyChange(ITerminalField.ACTION_ValueChanged, valueOld, valueNew);
				}
				else if (ITerminalTextField.PROPERTY_FocusLost.equals(evt.getPropertyName()))
				{
					final boolean fireEvent = true;
					setValue(getValue(), fireEvent);
				}
			}
			catch (final Exception ex)
			{
				showWarningAndRequestFocus(ex);
			}
		}
	};

	public AbstractTerminalDateField(final ITerminalContext terminalContext, final String name)
	{
		super(terminalContext);
		this.name = name;
		this.dateFormat = DisplayType.getDateFormat(DisplayType.Date);

		initComponents();
		initUI();
	}

	private void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		fText = factory.createTerminalTextField(getName(), DisplayType.String, getFontSize());
		fText.setEditable(true);
		fText.setKeyLayout(null); // FIXME: introduce X_C_POSKeyLayout.POSKEYLAYOUTTYPE_Date or have a keyboard which is date friendly, or a calendar
		fText.addListener(textChangeListener);

		bPreviousMonth = createActionButton(ACTION_PreviousMonth, "<<", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				onActionPreviousMonth();
			}
		});
		bPreviousDay = createActionButton(ACTION_PreviousDay, "<", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				onActionPreviousDay();
			}
		});
		bNextDay = createActionButton(ACTION_NextDay, ">", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				onActionNextDay();
			}
		});
		bNextMonth = createActionButton(ACTION_NextMonth, ">>", new PropertyChangeListener()
		{
			@Override
			public void propertyChange(PropertyChangeEvent evt)
			{
				onActionNextMonth();
			}
		});

		//
		// Component Panel
		panel = factory.createContainer(
				"insets 0", // Layout Constraints
				"[][][][][]", // Column constraints
				"[]" // Row constraints
		);
		// task 08502: increasing the width of the buttons from 25, hopefully now this works with both windows and x2go
		final String buttonConstraints = "wmin 35, hmin 25, sizegroup button";
		panel.add(bPreviousMonth, buttonConstraints);
		panel.add(bPreviousDay, buttonConstraints);
		panel.add(fText, "wmin 90px, grow");
		panel.add(bNextDay, buttonConstraints);
		panel.add(bNextMonth, buttonConstraints);
	}

	private final ITerminalButton createActionButton(final String action, final String text, final PropertyChangeListener listener)
	{
		final ITerminalFactory factory = getTerminalFactory();
		final ITerminalButton button = factory.createButtonAction(action);
		button.setText(text);
		button.setFocusable(false);
		button.addListener(listener);
		return button;
	}

	protected abstract void initUI();

	@Override
	public void dispose()
	{
		super.dispose();

		bPreviousMonth = null;
		bPreviousDay = null;
		fText = null;
		bNextDay = null;
		bNextMonth = null;
		panel = null;
	}

	private float getFontSize()
	{
		return getTerminalContext().getDefaultFontSize();
	}

	private final void onActionPreviousDay()
	{
		final Date date = getFieldValue();
		final Date dateNew;
		if (date == null)
		{
			dateNew = getToday();
		}
		else
		{
			dateNew = TimeUtil.addDays(date, -1);
		}
		setFieldValue(dateNew, true);
	}

	private final void onActionNextDay()
	{
		final Date date = getFieldValue();
		final Date dateNew;
		if (date == null)
		{
			dateNew = getToday();
		}
		else
		{
			dateNew = TimeUtil.addDays(date, +1);
		}
		setFieldValue(dateNew, true);
	}

	private final void onActionPreviousMonth()
	{
		final Date date = getFieldValue();
		final Date dateNew;
		if (date == null)
		{
			dateNew = getToday();
		}
		else
		{
			dateNew = TimeUtil.addMonths(date, -1);
		}
		setFieldValue(dateNew, true);
	}

	private final void onActionNextMonth()
	{
		final Date date = getFieldValue();
		final Date dateNew;
		if (date == null)
		{
			dateNew = getToday();
		}
		else
		{
			dateNew = TimeUtil.addMonths(date, +1);
		}
		setFieldValue(dateNew, true);
	}

	private final Date getToday()
	{
		return Env.getDate(getCtx());
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void requestFocus()
	{
		fText.requestFocus();
	}

	@Override
	public boolean isEditable()
	{
		return fText.isEditable();
	}

	@Override
	public void setEditable(boolean editable)
	{
		fText.setEditable(editable);
	}

	protected final IContainer getPanelComponent()
	{
		return panel;
	}

	@Override
	protected Date convertValueToType(final Object value)
	{
		if (value == null)
		{
			return null;
		}
		else if (value instanceof Date)
		{
			return (Date)value;
		}
		else if (value instanceof String)
		{
			final String valueStr = value.toString().trim();
			if (valueStr.isEmpty())
			{
				return null;
			}

			try
			{
				return dateFormat.parse(valueStr);
			}
			catch (ParseException e)
			{
				throw new TerminalException("Cannot convert value '" + value + "' to date", e);
			}
		}
		else
		{
			throw new IllegalArgumentException("Cannot convert value '" + value + "' (" + value.getClass() + ") to " + Date.class);
		}
	}

	@Override
	protected void setFieldValue(Date value, boolean fireEvent)
	{
		final Date valueOld2 = this.valueOld; // backup for event
		final Date valueNew2 = value; // backup for event
		final boolean changed = !Check.equals(this.valueOld, value);

		final String valueStr = value == null ? null : dateFormat.format(value);
		fText.setText(valueStr);
		this.valueOld = value;

		if (fireEvent && changed)
		{
			firePropertyChange(ITerminalField.ACTION_ValueChanged, valueOld2, valueNew2);
		}
	}

	@Override
	protected Date getFieldValue() throws WrongValueException
	{
		final String valueStr = fText.getText();
		if (valueStr == null || valueStr.trim().isEmpty())
		{
			return null;
		}
		try
		{
			return convertValueToType(valueStr.trim());
		}
		catch (Exception e)
		{
			throw new WrongValueException(this, "@" + ITerminalField.MSG_ErrorParsingText + "@ " + valueStr, e);
		}
	}

}

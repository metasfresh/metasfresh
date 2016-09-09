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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.text.DecimalFormat;
import java.text.Format;

import org.compiere.util.DisplayType;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.logging.LogManager;

/**
 * @author tsa
 */
public abstract class AbstractTerminalTextField
		extends AbstractTerminalField<String>
		implements ITerminalTextField
{
	protected final Logger logger = LogManager.getLogger(getClass());

	private final FactorySupport factories = new FactorySupport();

	private IKeyLayout keyLayout;
	private final String title;
	private final int displayType;
	private final Format format;
	private String action;
	private float fontSize;

	private boolean showKeyboardButton = false;

	private ITerminalKeyDialog activeKeyboard = null;

	@Override
	public String getAction()
	{
		return action;
	}

	@Override
	public void setAction(final String action)
	{
		this.action = action;
	}

	protected AbstractTerminalTextField(final ITerminalContext tc, final String title, final int displayType, final float fontSize)
	{
		super(tc);

		this.title = title;
		this.displayType = displayType;

		if (fontSize > 0)
		{
			this.fontSize = fontSize;
		}
		else
		{
			this.fontSize = tc.getDefaultFontSize();
		}

		if (DisplayType.isText(displayType))
		{
			format = null;
			setKeyLayout(tc.getTextKeyLayout());
		}
		else if (displayType == ITerminalTextField.TYPE_Password)
		{
			format = null;
			setKeyLayout(tc.getTextKeyLayout());
		}
		else if (DisplayType.isNumeric(displayType))
		{
			final DecimalFormat df = DisplayType.getNumberFormat(displayType);
			// don't use digits grouping because else we will have problems when we will convert it back
			df.setGroupingUsed(false);
			format = df;
			setKeyLayout(tc.getNumericKeyLayout());
		}
		else
		{
			format = null;
		}
		createUI();
	}

	protected AbstractTerminalTextField(final ITerminalContext tc, final String title, final int displayType)
	{
		this(tc, title, displayType, 0f);
	}

	protected AbstractTerminalTextField(final ITerminalContext tc, final String title)
	{
		this(tc, title, DisplayType.String);
	}

	protected abstract void createUI();

	protected final void firePropertyChanged(final String propertyName, final Object valueOld, final Object valueNew)
	{
		logger.debug("this-ID={}, Name={} Property={}: {} -> {} on this={}", System.identityHashCode(this), getName(), propertyName, valueOld, valueNew, this);

		// Case: valueOld=valueNew=null
		if (valueOld == valueNew)
		{
			return;
		}

		super.firePropertyChange(propertyName, valueOld, valueNew);
	}

	@Override
	public void setKeyLayout(final IKeyLayout keyLayout)
	{
		this.keyLayout = keyLayout;
	}

	@Override
	public IKeyLayout getKeyLayout()
	{
		return keyLayout;
	}

	@Override
	public String getName()
	{
		return title;
	}

	@Override
	public String getTitle()
	{
		return title;
	}

	/**
	 * Trigger automatically keyboard showing
	 */
	protected void showKeyboard()
	{
		// If keyboard is already active, don't show it again
		if (activeKeyboard != null)
		{
			return;
		}

		// If there is no KeyLayout configured => there is no keyboard to show
		if (keyLayout == null)
		{
			return;
		}

		// If component is not editable, there is no point to show a keyboard
		if (!isEditable())
		{
			return;
		}

		// If this component is not the focus owner, there is no point to automatically show keyboard
		if (!isFocusOwner())
		{
			return;
		}

		try
		{
			logger.debug("Show keyboard");
			final AbstractTerminalTextField textField = AbstractTerminalTextField.this;

			activeKeyboard = factories.create(ITerminalKeyDialog.class, textField);
			if (activeKeyboard == null)
			{
				activeKeyboard = getTerminalFactory().createTerminalKeyDialog(textField);
			}

			final Object oldValue = textField.getText();

			// Show Keybord and wait until user closes it (by pressing OK or Cancel)
			activeKeyboard.activate();

			final String action = TerminalKeyDialog.ACTION_Cancel;
			activeKeyboard = null;
			if (action.equals(textField.getAction()))
			{
				textField.setValue(oldValue.toString());
				textField.setText(oldValue.toString());
			}
			else
			{
				textField.processPendingEvents();
			}
			textField.setAction(null);
		}
		finally
		{
			activeKeyboard = null;
		}
	}

	@Override
	public int getDisplayType()
	{
		return displayType;
	}

	@Override
	public <T> void registerFactory(final Class<T> clazz, final IFactory<T> factory)
	{
		factories.registerFactory(clazz, factory);
	}

	@Override
	public final boolean isShowKeyboardButton()
	{
		return showKeyboardButton;
	}

	@Override
	public void setShowKeyboardButton(final boolean showKeyboardButton)
	{
		this.showKeyboardButton = showKeyboardButton;
	}

	@Override
	public final ITerminalKeyDialog getActiveKeyboard()
	{
		return activeKeyboard;
	}

	@Override
	public final boolean isKeyboardActive()
	{
		return activeKeyboard != null;
	}

	@Override
	public final Format getFormat()
	{
		return format;
	}

	@Override
	protected String convertValueToType(final Object value)
	{
		if (value == null)
		{
			return null;
		}

		return value.toString();
	}

	@Override
	public void dispose()
	{
		super.dispose();

		factories.clear();
		keyLayout = null;

		if (activeKeyboard != null)
		{
			activeKeyboard.dispose();
			activeKeyboard = null;
		}
	}

	protected float getFontSize()
	{
		return fontSize;
	}
}

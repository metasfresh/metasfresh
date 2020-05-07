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


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.util.List;

import org.adempiere.util.Check;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.logging.LogManager;

public abstract class TerminalKeyDialog
		implements ITerminalKeyDialog
{
	private static final String ERR_LOOKUP_FIELD_DOES_NOT_MATCH_TEXT_FIELD = "@LookupFieldDoesNotMatchTextField@";

	protected final transient Logger log = LogManager.getLogger(getClass());

	public static final String ACTION_Reset = "Reset";
	public static final String ACTION_Cancel = "Cancel";
	public static final String ACTION_OK = "Ok";

	private final ITerminalContext tc;
	/** Text field on which this keyboard dialog binds */
	private ITerminalTextField textField;

	private boolean initialized = false;

	/** Main panel of this component */
	private IContainer panel;

	/** panel containing keys */
	private ITerminalKeyPanel keysPanel;

	private ITerminalLookupField lookupField;
	private ITerminalKeySuggestionsPanel suggestionsPanel;

	private boolean disposed = false;

	protected TerminalKeyDialog(final ITerminalTextField textField)
	{
		this.textField = textField;
		this.tc = textField.getTerminalContext();

		tc.addToDisposableComponents(this);
	}

	@Override
	public final void init()
	{
		// prevent calling this method twice
		if (initialized)
		{
			return;
		}

		initComponents();
		initUI();

		initialized = true;
	}

	private void initComponents()
	{
		final ITerminalFactory factory = getTerminalFactory();

		this.panel = factory.createContainer();

		final IKeyLayout keyLayout = textField.getKeyLayout();
		final TerminalKeyTextAdapter adapter = new TerminalKeyTextAdapter(textField, keyLayout);
		this.keysPanel = factory.createTerminalKeyPanel(keyLayout, adapter);
	}

	protected abstract void initUI();

	public final void onAction(String action)
	{
		final IKeyLayout keylayout = textField.getKeyLayout();
		textField.setAction(action);
		if (ACTION_Reset.equals(action))
		{
			if (keylayout.isNumeric())
				textField.setText("0");
			else
				textField.setText("");
			try
			{
				textField.commitEdit();
			}
			catch (ParseException e1)
			{
				log.debug("JFormattedTextField commit failed", e1);
			}
		}
		else if (ACTION_Cancel.equals(action))
		{
			dispose();
		}
		else if (ACTION_OK.equals(action))
		{
			if (lookupField != null && suggestionsPanel != null)
			{
				final List<KeyNamePair> suggestions = suggestionsPanel.getSuggestions();
				if (suggestions != null && suggestions.size() == 1)
				{
					lookupField.setValue(suggestions.get(0));
				}
			}
			// Regular text/number field
			else
			{
				// make sure text field contains the new/fresh value
				// This is needed because setText and getText can be different
				// (depends on Document interface implementation)
				textField.setText(textField.getText());

				String text = textField.getText();
				text = text.trim();
				if (Check.isEmpty(text, true))
				{
					if (keylayout.isNumeric())
					{
						textField.setValue(BigDecimal.ZERO);
					}
				}

				final Format format = textField.getFormat();
				try
				{
					if (keylayout.isNumeric())
					{
						if (format == null)
						{
							textField.setValue(new BigDecimal(textField.getText()));
						}
						else if (format instanceof DecimalFormat)
						{
							final DecimalFormat df = (DecimalFormat)format;
							df.setParseBigDecimal(true);
							final BigDecimal bd = (BigDecimal)df.parse(text);
							textField.setValue(bd);
						}
						else
						{
							log.info("Invalid Format '{}' to be used to convert text '{}' to BigDecimal. Assuming ZERO.", new Object[] { format, text });
							textField.setValue(BigDecimal.ZERO);
						}
					}
				}
				catch (Exception e)
				{
					log.info("Error parsing text: " + text, e);
					textField.setValue(BigDecimal.ZERO);
				}

				try
				{
					textField.commitEdit();
				}
				catch (ParseException e1)
				{
					log.debug("JFormattedTextField commit failed", e1);
				}
			}
			dispose();
		}
	}

	@Override
	protected void finalize() throws Throwable
	{
		dispose();
	};

	@Override
	public void dispose()
	{
		textField = null;
		lookupField = null;

		disposed  = true;
	}

	@Override
	public boolean isDisposed()
	{
		return disposed ;
	}

	public final ITerminalContext getTerminalContext()
	{
		return tc;
	}

	public final ITerminalFactory getTerminalFactory()
	{
		return tc.getTerminalFactory();
	}

	/**
	 * @return Main panel of this component
	 */
	protected final IContainer getPanel()
	{
		return panel;
	}

	/**
	 * @return panel containing keys
	 */
	protected final ITerminalKeyPanel getKeysPanel()
	{
		return keysPanel;
	}

	protected abstract void setTitle(String title);

	@Override
	public final void setTerminalLookupField(ITerminalLookupField lookupField)
	{
		if (lookupField != null && lookupField.getTextField() != this.textField)
		{
			throw new TerminalException(ERR_LOOKUP_FIELD_DOES_NOT_MATCH_TEXT_FIELD);
		}
		this.lookupField = lookupField;

		// If we have a lookup field, create suggestions panel for it
		if (this.lookupField != null)
		{
			this.suggestionsPanel = createTerminalKeySuggestionsPanel(this.lookupField);
		}
	}

	protected final ITerminalLookupField getTerminalLookupField()
	{
		return lookupField;
	}

	protected final ITerminalKeySuggestionsPanel getTerminalKeySuggestionsPanel()
	{
		return suggestionsPanel;
	}

	protected abstract ITerminalKeySuggestionsPanel createTerminalKeySuggestionsPanel(final ITerminalLookupField lookupField);

	protected final void requestTextFieldFocus()
	{
		if (textField == null)
		{
			return;
		}

		final boolean selectAllText = false;
		textField.requestFocus(selectAllText);
	}

	/**
	 * @return Text field on which this keyboard dialog binds
	 */
	protected final ITerminalTextField getTextField()
	{
		return textField;
	}
}

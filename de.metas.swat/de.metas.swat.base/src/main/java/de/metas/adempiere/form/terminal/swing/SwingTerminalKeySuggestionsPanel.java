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

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;
import javax.swing.JButton;
import javax.swing.Timer;

import org.compiere.swing.CPanel;
import org.compiere.util.KeyNamePair;
import org.slf4j.Logger;

import de.metas.adempiere.form.terminal.ITerminalKeySuggestionsPanel;
import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.adempiere.form.terminal.ITerminalLookupField;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.logging.LogManager;

/* package */class SwingTerminalKeySuggestionsPanel
		implements IComponentSwing, ITerminalKeySuggestionsPanel
{
	private static final String ATTR_Suggestion = SwingTerminalKeySuggestionsPanel.class.getName() + "#Suggestion";

	private static final int PopupDelayMillis = 500;

	private final Logger logger = LogManager.getLogger(getClass());

	private SwingTerminalKeyDialog parent;
	private ITerminalLookupField lookupField;
	private final ITerminalContext terminalContext;
	private final int maxSuggestions = 10;

	private CPanel panelSwing;
	private JButton[] buttons;

	private ActionListener timerListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			updateSuggestionsNow();
		}
	};
	private final Timer timer = new Timer(PopupDelayMillis, timerListener);

	private PropertyChangeListener textChangedListener = new PropertyChangeListener()
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			updateSuggestions();
		}
	};

	private ActionListener pickSuggestionActionListener = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			final JButton but = (JButton)e.getSource();
			KeyNamePair suggestion = (KeyNamePair)but.getClientProperty(ATTR_Suggestion);
			lookupField.setValue(suggestion);
			parent.onAction(SwingTerminalKeyDialog.ACTION_OK);
		}
	};

	private List<KeyNamePair> suggestions;

	private boolean _disposed = false;

	public SwingTerminalKeySuggestionsPanel(final SwingTerminalKeyDialog parent,
			final ITerminalLookupField lookupField)
	{
		this.parent = parent;
		this.lookupField = lookupField;
		this.terminalContext = parent.getTerminalContext();

		this.panelSwing = new CPanel();
		this.panelSwing.setLayout(new FlowLayout(FlowLayout.LEFT));

		initUI();

		updateSuggestionsNow();

		final ITerminalTextField textField = lookupField.getTextField();
		textField.addListener(ITerminalTextField.PROPERTY_TextChanged, textChangedListener);

		terminalContext.addToDisposableComponents(this);
	}

	private void initUI()
	{
		panelSwing.setLayout(new FlowLayout(FlowLayout.LEFT));
		panelSwing.setVisible(true);

		this.buttons = new JButton[maxSuggestions];
		for (int i = 0; i < 10; i++)
		{
			JButton but = new JButton();
			but.setVisible(false);
			but.setEnabled(false);
			but.setFocusable(false);
			but.setText("");
			but.setToolTipText("");
			but.addActionListener(pickSuggestionActionListener);
			panelSwing.add(but);
			buttons[i] = but;
		}
	}

	@Override
	@OverridingMethodsMustInvokeSuper
	public void dispose()
	{
		if (_disposed)
		{
			return;
		}

		if (timer != null)
		{
			if (timerListener != null)
			{
				timer.removeActionListener(timerListener);
			}
			timer.stop();
		}
		timerListener = null;

		parent = null;

		final ITerminalTextField textField = lookupField.getTextField();
		textField.removeListener(ITerminalTextField.PROPERTY_TextChanged, textChangedListener);
		lookupField = null;

		if (panelSwing != null)
		{
			panelSwing.removeAll();
			panelSwing = null;
		}

		if (buttons != null)
		{
			for (int i = 0; i < buttons.length; i++)
			{
				if (pickSuggestionActionListener != null)
				{
					buttons[i].removeActionListener(pickSuggestionActionListener);
				}
				buttons[i] = null;
			}
			buttons = null;
		}
		pickSuggestionActionListener = null;

		if (suggestions != null)
		{
			suggestions.clear();
			suggestions = null;
		}

		//
		//
		_disposed = true;
	}

	@Override
	public boolean isDisposed()
	{
		return _disposed;
	}

	@Override
	public Component getComponent()
	{
		return panelSwing;
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	@Override
	public void updateSuggestions()
	{
		logger.debug("Loading suggestions (delayed)..");
		timer.setRepeats(false);
		timer.start();
	}

	/**
	 * Update suggestions now. Method is also called by timer.
	 */
	private void updateSuggestionsNow()
	{
		logger.debug("begin");

		final ITerminalLookup lookup = lookupField.getLookup();
		final String text = lookupField.getText();
		final List<KeyNamePair> result = lookup.suggest(text, maxSuggestions);
		setSuggestions(result);
	}

	private void setSuggestions(List<KeyNamePair> suggestions)
	{
		this.suggestions = suggestions;

		if (logger.isDebugEnabled())
			logger.debug("begin: suggestions=" + suggestions);

		int size = 0;
		if (suggestions != null)
		{
			size = suggestions.size();
		}
		if (size > buttons.length)
			size = buttons.length;

		for (int i = 0; i < size; i++)
		{
			final KeyNamePair suggestion = suggestions.get(i);
			String text = suggestion.toString();
			String tooltip = "";
			if (text.length() > 20)
			{
				tooltip = text;
				text = text.substring(0, 20);
			}
			buttons[i].setText(text);
			buttons[i].setToolTipText(tooltip);
			buttons[i].putClientProperty(ATTR_Suggestion, suggestion);
			buttons[i].setVisible(true);
			buttons[i].setEnabled(true);

			if (logger.isDebugEnabled())
				logger.debug("suggestion " + i + ": " + suggestion.getID() + " - " + text);
		}

		for (int i = size; i >= 0 && i < buttons.length; i++)
		{
			buttons[i].setText("");
			buttons[i].setToolTipText("");
			buttons[i].putClientProperty(ATTR_Suggestion, null);
			buttons[i].setVisible(false);
			buttons[i].setEnabled(false);
		}
	}

	@Override
	public List<KeyNamePair> getSuggestions()
	{
		if (suggestions == null)
			return new ArrayList<KeyNamePair>();
		else
			return Collections.unmodifiableList(this.suggestions);
	}
}

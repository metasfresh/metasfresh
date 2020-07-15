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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;

import org.compiere.apps.ConfirmPanel;
import org.compiere.apps.ConfirmPanelListener;
import org.compiere.swing.CDialog;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalKeySuggestionsPanel;
import de.metas.adempiere.form.terminal.ITerminalLookupField;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * On Screen Keyboard
 *
 * @author Paul Bowden Adaxa Pty Ltd
 * @author Teo Sarca, metas.ro SRL
 */
/* package */class SwingTerminalKeyDialog
		extends de.metas.adempiere.form.terminal.TerminalKeyDialog
{
	private CDialog dialog;

	private class TermListenerConfirmPanelListener extends ConfirmPanelListener
	{
		@Override
		public void okButtonPressed(final ActionEvent e)
		{
			onAction(ACTION_OK);
		}

		@Override
		public void cancelButtonPressed(final ActionEvent e)
		{
			onAction(ACTION_Cancel);
		}

		@Override
		public void resetButtonPressed(final ActionEvent e)
		{
			onAction(ACTION_Reset);
		}
	}

	private class KeyboardDispacher implements KeyEventDispatcher
	{
		@Override
		public boolean dispatchKeyEvent(final KeyEvent e)
		{
			if (e.getKeyChar() == KeyEvent.VK_ENTER)
			{
				e.consume();
				onAction(ACTION_OK);
				return true;
			}
			else if (e.getKeyChar() == KeyEvent.VK_ESCAPE)
			{
				e.consume();
				onAction(ACTION_Cancel);
				return true;
			}

			return false;
		}
	};

	private KeyboardDispacher keyboardDispacher;

	protected SwingTerminalKeyDialog(final ITerminalTextField field)
	{
		super(field);
	}

	@Override
	protected void initUI()
	{
		final ITerminalFactory factory = getTerminalFactory();
		final ITerminalTextField textField = getTextField();

		//
		// Create Swing Dialog
		dialog = new CDialog(SwingTerminalFactory.getFrame(textField), true);
		// dialog.setTitle(text.getTitle());
		dialog.setUndecorated(true);
		dialog.setModal(true);

		//
		// Customize main panel and add it to dialog
		final IContainer panel = getPanel();
		final JComponent panelSwing = (JComponent)panel.getComponent();
		panelSwing.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		dialog.getContentPane().add(SwingTerminalFactory.getUI(panel));

		//
		// Add lane with suggestion keys, if any (top)
		final ITerminalKeySuggestionsPanel suggestionsPanel = getTerminalKeySuggestionsPanel();
		if (suggestionsPanel != null)
		{
			panel.add(suggestionsPanel, "north, w 600!, h 30!, gap 0 0 0 0");
		}

		//
		// Add Keys Panel
		final ITerminalKeyPanel keysPanel = getKeysPanel();
		panel.add(keysPanel, "center, growx, growy");

		//
		// Add confirmation panel (bottom)
		{
			final ConfirmPanel confirm = ConfirmPanel.builder()
					.withCancelButton(true)
					.withResetButton(true)
					.build();
			confirm.setConfirmPanelListener(new TermListenerConfirmPanelListener());
			final Dimension buttonDim = new Dimension(50, 50);

			confirm.getResetButton().setPreferredSize(buttonDim);
			confirm.getResetButton().setFocusable(false);

			confirm.getOKButton().setPreferredSize(buttonDim);
			confirm.getOKButton().setFocusable(false);
			// addKeyStroke(confirm.getOKButton(), KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0));

			confirm.getCancelButton().setPreferredSize(buttonDim);
			confirm.getCancelButton().setFocusable(false);

			panel.add(factory.wrapComponent(confirm), "south");
		}

		//
		// Pack dialog & pimp it
		dialog.pack();
		dialog.setLocationByPlatform(true); // not sure if this is needed

		//
		// Request focus on parent text field component
		requestTextFieldFocus();

		//
		// Create Keyboard dispatcher and register it
		// NOTE: it will be unregistered on dispose
		keyboardDispacher = new KeyboardDispacher();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyboardDispacher);
	}

	@Override
	protected ITerminalKeySuggestionsPanel createTerminalKeySuggestionsPanel(final ITerminalLookupField lookupField)
	{
		return new SwingTerminalKeySuggestionsPanel(this, lookupField);
	}

	@Override
	protected void setTitle(final String title)
	{
		dialog.setTitle(title);
	}

	@Override
	public void dispose()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyboardDispacher);

		dialog.dispose();

		super.dispose();
	}

	/**
	 * Finished this dialog and sets it visible. Before doing so, it invoices {@link ITerminalContext#closeCurrentReferences()}, so make sure that at this point all components that belong to the on-screen keyboard are created.
	 */
	@Override
	public void activate()
	{
		// make sure is initialized
		init();

		//
		// Set dialog location under text field:
		final ITerminalTextField textField = getTextField();
		final Component textFieldComp = SwingTerminalFactory.getUI(textField);
		final Point loc = getValidLocationOnScreen(textFieldComp);
		dialog.setLocation(loc);

		requestTextFieldFocus();
		getTerminalContext().closeCurrentReferences();
		dialog.setVisible(true);
	}

	private Point getValidLocationOnScreen(final Component textFieldComp)
	{
		//
		// Get screen location of the text field and set the component right below it.
		final Point loc = textFieldComp.getLocationOnScreen();

		//
		// Make sure our window fits the screen
		dialog.pack();
		final Dimension dialogSize = dialog.getSize();
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		//
		// Establish valid Y location
		{
			final double dialogYDelta = dialogSize.getHeight() + loc.y + textFieldComp.getHeight();
			if (dialogYDelta > screenSize.getHeight())
			{
				//
				// if going below won't fit, place the dialog above
				loc.y -= dialogSize.getHeight();
			}
			else
			{
				//
				// place the dialog below
				loc.y += textFieldComp.getHeight();
			}

			if (loc.y < 0)
			{
				loc.y = 0;
			}
		}
		//
		// Establish valid X location
		{
			final double dialogXDelta = dialogSize.getWidth() + loc.x;
			if (dialogXDelta > screenSize.getWidth())
			{
				//
				// if going below won't fit, place the dialog to the left
				loc.x -= dialogXDelta - screenSize.getWidth();
			}
			if (loc.x < 0)
			{
				loc.x = 0;
			}
		}

		return loc;
	}
}

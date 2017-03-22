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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.adempiere.plaf.MetasfreshGlassPane;
import org.compiere.apps.AEnv;
import org.compiere.swing.CDialog;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.TerminalDialog;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

class SwingTerminalDialog extends TerminalDialog
{
	private JDialog dialogSwing;

	SwingTerminalDialog(final ITerminalFactory factory,
			final IComponent parent,
			final IComponent content)
	{
		super(factory, parent, content);

		final JFrame parentSwing = SwingTerminalFactory.getFrame(parent);
		dialogSwing = new CDialog(parentSwing, true); // modal=true

		// When user closes the window from "X" button, do nothing because we will handle it
		// using our custom WindowAdapter
		dialogSwing.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// Install the glass pane
		MetasfreshGlassPane.install(dialogSwing);
	}

	@Override
	protected void initComponentsUI()
	{
		final ITerminalContext terminalContext = getTerminalContext();
		final Dimension screenResolution = terminalContext.getScreenResolution();

		dialogSwing.setResizable(false);
		dialogSwing.setMaximumSize(screenResolution);

		//
		// Bind Registered Listeners to AWT window events
		dialogSwing.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowOpened(final WindowEvent e)
			{
				doOnDialogOpened();
			}

			@Override
			public void windowActivated(final WindowEvent e)
			{
				doOnDialogActivated();
			}

			@Override
			public void windowDeactivated(final WindowEvent e)
			{
				doOnDialogDeactivated();
			}

			@Override
			public void windowClosing(final WindowEvent e)
			{
				doOnDialogClosing();
			}

			@Override
			public void windowClosed(final WindowEvent e)
			{
				doOnDialogClosed();
			}
		});

		//
		// Focus and disposal window listener
		dialogSwing.addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowOpened(final WindowEvent e)
			{
				requestFocusOnComponentPanel();
			}

			@Override
			public void windowActivated(final WindowEvent e)
			{
				requestFocusOnComponentPanel();
			}

			@Override
			public void windowClosing(final WindowEvent e)
			{
				// NOTE: we are cancelling on windowClosing and not on windowClosed
				// because on windowClosed() the control is returned to dialog caller but the our Canceled flag is not yet set
				doCancel();
			}
		});
	}

	@Override
	protected void initLayoutUI()
	{
		dialogSwing.setLayout(new BorderLayout());

		dialogSwing.add(SwingTerminalFactory.getUI(getContentPanel()), BorderLayout.CENTER);
		dialogSwing.add(SwingTerminalFactory.getUI(getConfirmPanel()), BorderLayout.SOUTH);
	}

	@Override
	protected void activateUI()
	{
		dialogSwing.pack();

		// NOTE: because we are dealing with a JDialog, following will show the dialog but it will "stop" the UI until the dialog is closed
		AEnv.showCenterScreen(dialogSwing);
	}

	@Override
	public void setTitle(final String title)
	{
		dialogSwing.setTitle(title);
	}

	@Override
	public void setSize(final Dimension size)
	{
		dialogSwing.setMinimumSize(size);
		dialogSwing.setPreferredSize(size);
	}

	@Override
	protected void disposeUI()
	{
		if (dialogSwing != null)
		{
			dialogSwing.dispose();
			dialogSwing = null;
		}
	}

	@Override
	public Object getComponent()
	{
		return dialogSwing;
	}
}

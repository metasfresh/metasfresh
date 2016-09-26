/**
 *
 */
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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.compiere.apps.AEnv;
import org.compiere.swing.CDialog;

import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.TerminalLoginDialog;
import net.miginfocom.swing.MigLayout;

/**
 * @author tsa
 *
 */
public class SwingTerminalLoginDialog extends TerminalLoginDialog
{
	private CDialog dialog;

	private class DialogListener extends WindowAdapter
	{
		@Override
		public void windowClosed(final WindowEvent e)
		{
			doExit();
		}

	}

	public SwingTerminalLoginDialog(final ITerminalBasePanel parent)
	{
		super(parent);
	}

	@Override
	protected void initLayout()
	{
		dialog = new CDialog(SwingTerminalFactory.getFrame(parent), true);
		dialog.addWindowListener(new DialogListener());
		dialog.setResizable(true);

		final IContainer panel = getTerminalFactory().createContainer();
		SwingTerminalFactory.getUI(panel).setLayout(new MigLayout("ins 0 0", "[grow][grow][grow][grow]", "[nogrid, grow]"));
		dialog.getContentPane().add((Component)panel.getComponent());

		panel.add(usersPanel, "growx, growy, wrap");
		panel.add(passwordLabel, "growx, growy");
		panel.add(passwordField, "w 100, growx, growy, wrap");
		panel.add(confirmPanel, "span, growx, growy, wrap");

		dialog.pack();
	}

	@Override
	public void activate()
	{
		AEnv.showCenterScreen(dialog);
	}

	@Override
	protected void disposeUI()
	{
		if (dialog != null)
		{
			dialog.dispose();
			dialog = null;
		}
	}
}

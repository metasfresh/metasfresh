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


import java.awt.Component;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.swing.CButton;
import org.compiere.util.Env;

import de.metas.adempiere.form.terminal.ConfirmPanel;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import net.miginfocom.swing.MigLayout;

public class SwingConfirmPanel extends ConfirmPanel
{
	private final org.compiere.apps.ConfirmPanel panel;
	private final ButtonsActionListener buttonsActionListener;
	private String buttonSize = "";

	public String getButtonSize()
	{
		return buttonSize;
	}

	public void setButtonSize(final String buttonSize)
	{
		this.buttonSize = buttonSize;
	}

	private class ButtonsActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(final ActionEvent e)
		{
			final String actionSwing = e.getActionCommand();
			String action = actionSwing;
			if (org.compiere.apps.ConfirmPanel.A_OK.equals(actionSwing))
			{
				action = IConfirmPanel.ACTION_OK;
			}
			else if (org.compiere.apps.ConfirmPanel.A_CANCEL.equals(actionSwing))
			{
				action = IConfirmPanel.ACTION_Cancel;
			}
			try
			{
				firePropertyChange(IConfirmPanel.PROP_Action, null, action);
			}
			catch (final Exception ex)
			{
				final ITerminalFactory factory = getTerminalFactory();
				factory.showWarning(SwingConfirmPanel.this, ITerminalFactory.TITLE_ERROR, new TerminalException(ex.getLocalizedMessage(), ex));
			}
		}
	}

	protected SwingConfirmPanel(final ITerminalContext tc, final boolean withCancel, final String buttonSize)
	{
		super(tc);

		this.buttonSize = buttonSize;

		panel = org.compiere.apps.ConfirmPanel.builder()
				.withCancelButton(true)
				.build();

		final MigLayout migLayout = new MigLayout("ins 5 5 15 5", "[grow]", "[]");
		panel.setLayout(migLayout);

		panel.add(new Label(), "dock south, h 5:5:5");

		// Neither of the buttons (OK and Cancel) should be focusable!
		// Reason: Buttons being focusable interferes with components which request permanent focus (e.g. the product search field)
		final CButton bOK = panel.getOKButton();
		bOK.setFocusable(false);
		panel.add(bOK, "dock east, gap 5px, gapafter rel, " + buttonSize);

		final CButton bCancel = panel.getCancelButton();
		if (bCancel != null)
		{
			bCancel.setFocusable(false);
			panel.add(bCancel, "dock east, " + buttonSize);
		}

		buttonsActionListener = new ButtonsActionListener();

		panel.setActionListener(buttonsActionListener);
	}

	@Override
	public ITerminalButton addButton(final String action)
	{
		final boolean toogle = false;
		return addButton(action, toogle);
	}

	@Override
	public ITerminalButton addButton(final String action, final boolean toogle)
	{
		final AbstractButton buttonSwing = panel.addButton(action, null, null, toogle);
		buttonSwing.addActionListener(buttonsActionListener);
		// Button shall be not focusable so it doesn't interfere with components which need permanent focus
		buttonSwing.setFocusable(false);

		final String buttonText = Services.get(IMsgBL.class).translate(Env.getCtx(), action);
		buttonSwing.setText(buttonText);

		panel.add(buttonSwing, "dock west, " + buttonSize);

		final ITerminalButton button = new SwingTerminalButtonWrapper(getTerminalContext(), buttonSwing);
		return button;
	}

	@Override
	public void addComponent(final IComponent component, final Object constraints)
	{
		final Component compSwing = SwingTerminalFactory.getUI(component);
		panel.add(compSwing, constraints);
	}

	@Override
	public org.compiere.apps.ConfirmPanel getComponent()
	{
		return panel;
	}

	@Override
	public void dispose()
	{
		super.dispose();

		if (panel != null)
		{
			panel.dispose();
		}
	}

	@Override
	public String toString()
	{
		return "SwingConfirmPanel [panel=" + panel 
				+ ", buttonsActionListener=" + buttonsActionListener
				+ ", buttonSize=" + buttonSize + "]";
	}
}

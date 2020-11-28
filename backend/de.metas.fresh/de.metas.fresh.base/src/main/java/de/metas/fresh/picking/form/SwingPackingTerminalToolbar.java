/**
 *
 */
package de.metas.fresh.picking.form;

import java.awt.Event;
import java.awt.event.KeyEvent;

/*
 * #%L
 * de.metas.fresh.base
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

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.KeyStroke;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.form.FormFrame;

import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.swing.TerminalSubPanel;
import net.miginfocom.swing.MigLayout;

/**
 * Contains Toolbar.
 *
 * @author cg
 *
 */
public class SwingPackingTerminalToolbar extends TerminalSubPanel implements PropertyChangeListener
{
	private static final String ERR_UNSUPPORTED_ACTION = "@UnsupportedAction@";

	private ITerminalButton bPrint;
	private ITerminalButton bOk;

	private static final String ACTION_Print = "Print";
	private static final String ACTION_OK = "Ok";

	public SwingPackingTerminalToolbar(final SwingPackingTerminalPanel packingTerminalPanel)
	{
		super(packingTerminalPanel);
		setReadOnly(true); // qty field shall be readonly by default
	}

	@Override
	protected void init()
	{
		initComponents();
		initLayout();
	}

	protected void initComponents()
	{
		bPrint = createButtonAction(ACTION_Print, KeyStroke.getKeyStroke(KeyEvent.VK_PRINTSCREEN, Event.PRINT_SCREEN), 17f);
		bPrint.addListener(this);
		bOk = createButtonAction(ACTION_OK, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, Event.ENTER), 17f);
		bOk.setEnabled(false);
		bOk.addListener(this);
	}

	private void initLayout()
	{
		final String buttonSize = SwingPickingTerminalConstants.getButtonSize();

		getUI().setLayout(new MigLayout("ins 5 5 5 10", "[fill|fill|fill]", "[nogrid]unrel[||]"));

		add(bPrint, buttonSize);
		add(bOk, buttonSize);

		final StringBuilder closeHUConstraints = new StringBuilder()
				.append(buttonSize)
				.append(" dock center"); // fresh_05749: this will ensure that the OK button is far, far away from Close_HU
		add(getCloseCurrentHUButton(), closeHUConstraints.toString());

		bOk.setEnabled(true);
		bPrint.setEnabled(false);
	}

	public SwingPackingTerminalPanel getPackingTerminalPanel()
	{
		return SwingPackingTerminalPanel.cast(super.getTerminalBasePanel());
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		if (ACTION_Print.equals(evt.getNewValue()))
		{
			// nothing to do now
			throw new UnsupportedOperationException();
		}
		else if (ACTION_OK.equals(evt.getNewValue()))
		{
			final SwingPackingTerminal packingTerminalWindow = getPackingTerminalPanel().getParent();
			final FormFrame pickingFrame = packingTerminalWindow.getPickingFrame();
			if (pickingFrame != null)
			{
				pickingFrame.toFront();
			}
			packingTerminalWindow.dispose();
		}
		else
		{
			throw new AdempiereException(ERR_UNSUPPORTED_ACTION);
		}
	}

	private ITerminalButton getCloseCurrentHUButton()
	{
		final ITerminalButton bCloseCurrentHU = getPackingTerminalPanel().getCloseCurrentHUButton();
		if (bCloseCurrentHU == null)
		{
			throw new AdempiereException("CloseHUButton not initialized!");
		}
		return bCloseCurrentHU;
	}

	public void setReadOnly(final boolean ro)
	{
		getPackingTerminalPanel().setQtyFieldReadOnly(ro);
	}

	public void setEditable(final boolean editable)
	{
		final boolean ro = !editable;
		setReadOnly(ro);
	}
}

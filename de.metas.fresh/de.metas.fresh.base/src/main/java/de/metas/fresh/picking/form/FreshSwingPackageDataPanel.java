/**
 *
 */
package de.metas.fresh.picking.form;

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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.apps.form.FormFrame;

import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.fresh.picking.form.swing.FreshSwingPackageItems;
import de.metas.picking.terminal.Utils;
import de.metas.picking.terminal.form.swing.AbstractPackageDataPanel;
import de.metas.picking.terminal.form.swing.AbstractPackageTerminal;
import net.miginfocom.swing.MigLayout;

/**
 * Contains Toolbar.
 *
 * @author cg
 *
 */
public class FreshSwingPackageDataPanel extends AbstractPackageDataPanel
{
	public static FreshSwingPackageDataPanel cast(final AbstractPackageDataPanel panel)
	{
		return (FreshSwingPackageDataPanel)panel;
	}

	private static final String ERR_UNSUPPORTED_ACTION = "@UnsupportedAction@";

	public FreshSwingPackageDataPanel(final FreshSwingPackageTerminalPanel basePanel)
	{
		super(basePanel);
		setReadOnly(true); // qty field shall be readonly by default
	}

	@Override
	protected void initLayout()
	{
		getUI().setLayout(new MigLayout("ins 5 5 5 10", "[fill|fill|fill]", "[nogrid]unrel[||]"));
		if (getButtonSize() == null)
		{
			setButtonSize(Utils.getButtonSize());
		}

		add(getbPrint(), getButtonSize());
		add(getbOK(), getButtonSize());

		final StringBuilder closeHUConstraints = new StringBuilder()
				.append(getButtonSize())
				.append(" dock center"); // fresh_05749: this will ensure that the OK button is far, far away from Close_HU
		add(getCloseCurrentHUButton(), closeHUConstraints.toString());

		setupPackingItemPanel();
	}

	@Override
	public void setupPackingItemPanel()
	{
		getbOK().setEnabled(true);
		getbPrint().setEnabled(false);
	}

	public void setQty(final String qty)
	{
		if (qty != null)
		{
			getProductKeysPanel().setQtyNoFire(qty);
		}
	}

	private FreshSwingPackageItems getProductKeysPanel()
	{
		return getBasePanel().getProductKeysPanel();
	}

	public FreshSwingPackageTerminalPanel getBasePanel()
	{
		return FreshSwingPackageTerminalPanel.cast(packageTerminalPanel);
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt)
	{
		if (ACTION_Print.equals(evt.getNewValue()))
		{
			// nothing to do now
		}
		else if (ACTION_OK.equals(evt.getNewValue()))
		{
			final AbstractPackageTerminal parent = getBasePanel().getParent();
			final FormFrame pickingFrame = parent.getPickingFrame();
			if (pickingFrame != null)
			{
				pickingFrame.toFront();
			}
			parent.dispose();
		}
		else
		{
			throw new AdempiereException(ERR_UNSUPPORTED_ACTION);
		}
	}

	@Override
	public void validateModel()
	{
		// nothing to do
	}

	private ITerminalButton getCloseCurrentHUButton()
	{
		final ITerminalButton bCloseCurrentHU = getProductKeysPanel().getbCloseCurrentHU();
		if (bCloseCurrentHU == null)
		{
			throw new AdempiereException("CloseHUButton not initialized!");
		}
		return bCloseCurrentHU;
	}

	public void setReadOnly(final boolean ro)
	{
		getProductKeysPanel().setQtyFieldReadOnly(ro);
	}

	public void setEditable(final boolean editable)
	{
		final boolean ro = !editable;
		setReadOnly(ro);
	}
}

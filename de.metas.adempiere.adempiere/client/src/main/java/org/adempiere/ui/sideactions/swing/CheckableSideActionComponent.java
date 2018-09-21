package org.adempiere.ui.sideactions.swing;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JCheckBox;

import org.adempiere.ui.sideactions.model.ISideAction;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Env;

import de.metas.adempiere.form.IClientUI;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Renders a {@link ISideAction} as a checkbox which can be toggled on/off.
 * 
 * @author tsa
 *
 */
class CheckableSideActionComponent extends JCheckBox
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1583557541057868151L;
	private final ISideAction action;

	public CheckableSideActionComponent(final ISideAction action)
	{
		super();

		Check.assumeNotNull(action, "action not null");
		this.action = action;

		updateFromAction();

		this.addItemListener(new ItemListener()
		{

			@Override
			public void itemStateChanged(ItemEvent e)
			{
				onActionPerformed();
			}
		});
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	private final void updateFromAction()
	{
		final String displayName = action.getDisplayName();
		setText(displayName);
		// setToolTipText(displayName);
		setOpaque(false);
		setChecked(action.isToggled());
	}

	public final boolean isChecked()
	{
		return this.isSelected();
	}

	private final void setChecked(final boolean checked)
	{
		this.setSelected(checked);
	}

	protected void onActionPerformed()
	{
		if (!isEnabled())
		{
			return;
		}

		setEnabled(false);
		try
		{
			action.setToggled(isChecked());
			action.execute();
		}
		catch (Exception e)
		{
			final int windowNo = Env.getWindowNo(this);
			Services.get(IClientUI.class).error(windowNo, e);
		}
		finally
		{
			setEnabled(true);
		}
	}

}

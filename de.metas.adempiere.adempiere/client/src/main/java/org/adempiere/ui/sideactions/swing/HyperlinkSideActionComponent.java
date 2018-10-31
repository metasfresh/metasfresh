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


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.adempiere.ui.sideactions.model.ISideAction;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.util.Env;
import org.jdesktop.swingx.JXHyperlink;

import de.metas.adempiere.form.IClientUI;
import de.metas.util.Check;
import de.metas.util.Services;

/**
 * Renders a {@link ISideAction} as a "button" which looks like a "web browser URL link".
 * 
 * @author tsa
 *
 */
class HyperlinkSideActionComponent extends JXHyperlink
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -39482230916850468L;
	private final ISideAction action;

	public HyperlinkSideActionComponent(final ISideAction action)
	{
		super();

		setClickedColor(getUnclickedColor()); // use the same color for clicked and not clicked links
		setOpaque(false);
		setFocusPainted(false);
		setFocusable(false); // there is no point to have the link focusable, user will always click on it

		Check.assumeNotNull(action, "action not null");
		this.action = action;
		updateFromAction();

		this.addActionListener(new ActionListener()
		{

			@Override
			public void actionPerformed(ActionEvent e)
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

	/**
	 * Update component from model
	 */
	private final void updateFromAction()
	{
		final String displayName = action.getDisplayName();
		setText(displayName);
		// setToolTipText(displayName);
	}

	private void onActionPerformed()
	{
		if (!isEnabled())
		{
			return;
		}

		setEnabled(false);
		try
		{
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

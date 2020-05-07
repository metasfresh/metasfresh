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


import javax.swing.JLabel;

import org.adempiere.ui.sideactions.model.ISideAction;

import de.metas.util.Check;

/**
 * Renders a {@link ISideAction} as a "label" which looks like a "line/paragraph of text".
 * 
 * @author tsa
 *
 */
class LabelSideActionComponent extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3033615233129832109L;

	private final ISideAction action;

	public LabelSideActionComponent(final ISideAction action)
	{
		super();
		setFocusable(false); // there is no point to have the link focusable

		Check.assumeNotNull(action, "action not null");
		this.action = action;
		updateFromAction();
	}

	private void updateFromAction()
	{
		final String displayName = action.getDisplayName();
		setText(displayName);
	}
}

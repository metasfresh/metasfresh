package org.compiere.grid.ed;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

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

/**
 * Base {@link Action} class to be used for implementing {@link VEditor} actions.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
abstract class VEditorAction extends AbstractAction
{
	private static final long serialVersionUID = 1L;

	public VEditorAction(final String name, final KeyStroke accelerator)
	{
		super(name);

		putValue(Action.ACCELERATOR_KEY, accelerator);
	}

	@Override
	public abstract void actionPerformed(final ActionEvent e);

	public final String getName()
	{
		final Object nameObj = getValue(Action.NAME);
		return nameObj == null ? "" : nameObj.toString();
	}

	public final KeyStroke getAccelerator()
	{
		return (KeyStroke)getValue(ACCELERATOR_KEY);
	}

	/**
	 * Install the action and the key bindings to given component.
	 *
	 * @param comp
	 * @param inputMapCondition one of WHEN_IN_FOCUSED_WINDOW, WHEN_FOCUSED, WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
	 */
	public final void installTo(final JComponent comp, final int inputMapCondition)
	{
		if (comp == null)
		{
			return;
		}

		final String actionName = getName();

		final ActionMap actionMap = comp.getActionMap();
		actionMap.put(actionName, this);

		final KeyStroke accelerator = getAccelerator();
		if (accelerator != null)
		{
			final InputMap inputMap = comp.getInputMap(inputMapCondition);
			inputMap.put(accelerator, actionName);
		}
	}
}

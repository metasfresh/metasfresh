package de.metas.adempiere.form.terminal;

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

import java.beans.PropertyChangeListener;

public interface IConfirmPanel extends IComponent
{
	String PROP_Action = "ConfirmPanelAction";

	String ACTION_OK = "Ok";
	String ACTION_Cancel = "Cancel";

	void addListener(PropertyChangeListener listener);

	/**
	 * Call {@link #addButton(String, boolean)} with <code>toggle=false</code>.
	 *
	 * @param action
	 * @return
	 */
	ITerminalButton addButton(String action);

	/**
	 * Create a terminal button
	 * Note: add the {@link ITerminalButton} that we return here to the terminal context as disposable component.
	 *
	 * @param action
	 * @param toogle
	 * @return
	 */
	ITerminalButton addButton(String action, boolean toogle);

	void addComponent(IComponent component, Object constraints);
}

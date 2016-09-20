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

import de.metas.adempiere.form.terminal.ITerminalScrollPane.ScrollPolicy;

public interface IPropertiesPanel extends IComponent
{
	/**
	 * Set the model and register our listener with it. Might replace the formerly set model. In that case, also unregister our listener from the old model.
	 *
	 * @param model model or <code>null</code>
	 */
	void setModel(IPropertiesPanelModel model);

	/**
	 * @return underlying model or null
	 */
	IPropertiesPanelModel getModel();

	/**
	 * Disable firing ValueChanged on all fields when key pressed and rely on commitEdit and/or focus lost.
	 */
	void disableFireValueChangedOnFocusLost();

	/**
	 * Set MigLayout constraints to be used when adding property's label to panel
	 *
	 * @param constraintsLabel
	 */
	void setLabelConstraints(String constraintsLabel);

	/**
	 * Set MigLayout constraints to be used when adding property's editor to panel
	 *
	 * @param constraintsLabel
	 */
	void setEditorConstraints(String constraintsEditor);

	/**
	 * Validate properties values.
	 *
	 * In case there is a property which is not valid, an exception is thrown.
	 *
	 * @throws WrongValueException if at least a property is not valid
	 */
	void validate() throws WrongValueException;

	void setVerticalScrollBarPolicy(ScrollPolicy scrollPolicy);
}

package de.metas.handlingunits.client.terminal.select.view;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.beans.PropertyChangeListener;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;

public interface IHUSelectPanel extends IComponent
{

	public static final String PROPERTY_Disposed = "Disposed";

	@Override
	ITerminalContext getTerminalContext();

	ITerminalFactory getTerminalFactory();

	@Override
	void dispose();

	@Override
	Object getComponent();

	void addPropertyChangeListener(PropertyChangeListener listener);

	void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

	/**
	 * Open HU Editor for editing given HUs.
	 *
	 * @param huEditorModel
	 * @return true if editor was closed with OK; false if editor was closed with Cancel.
	 */
	boolean editHUs(HUEditorModel huEditorModel);

}
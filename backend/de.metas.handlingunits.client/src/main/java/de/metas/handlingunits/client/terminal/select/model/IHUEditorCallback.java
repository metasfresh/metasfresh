package de.metas.handlingunits.client.terminal.select.model;

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


import de.metas.handlingunits.client.terminal.editor.model.impl.HUEditorModel;
import de.metas.handlingunits.client.terminal.lutuconfig.model.LUTUConfigurationEditorModel;

/**
 * Interface used to control editors used to manipulate handling units.
 *
 * @author ad
 *
 * @param <T> HU editor model type
 */
public interface IHUEditorCallback<T extends HUEditorModel>
{
	/**
	 * Used in {@link AbstractHUSelectModel} to open the HU editor dialog.
	 *
	 * @param huEditorModel
	 * @return true if user actually did something; false if user canceled
	 */
	boolean editHUs(T huEditorModel);

	/**
	 * Opens the user interface which is letting the user to change the LU/TU configuration.
	 *
	 * @param lutuConfigurationModel
	 * @return true if user actually did something; false if user canceled
	 */
	boolean editLUTUConfiguration(LUTUConfigurationEditorModel lutuConfigurationModel);
}

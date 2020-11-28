package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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


import java.util.List;
import java.util.function.Function;

import de.metas.handlingunits.IDocumentLUTUConfigurationHandler;
import de.metas.handlingunits.ILUTUConfigurationEditor;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Interface used to manage (create, update, edit etc) the LU/TU configuration of a particular document line.
 * 
 * Under the hood, it uses {@link IDocumentLUTUConfigurationHandler}.
 *
 * @author tsa
 *
 * @param <T> document line type
 */
public interface IDocumentLUTUConfigurationManager
{
	/**
	 * Convenience method to create a new configuration and use the given <code>converter</code> to alter it, then return the result.
	 *
	 * NOTE: the edited configuration will be pushed back to the model.
	 *
	 * @param lutuConfigurationEditor
	 * @return
	 */
	I_M_HU_LUTU_Configuration createAndEdit(final Function<I_M_HU_LUTU_Configuration, I_M_HU_LUTU_Configuration> converter);

	/**
	 * Gets current LU/TU configuration if exists. If not, a new one is created but is not set back to underlying document line.
	 *
	 * @return
	 */
	I_M_HU_LUTU_Configuration getCreateLUTUConfiguration();

	/**
	 *
	 * @return
	 * @see IDocumentLUTUConfigurationHandler#getCurrentLUTUConfigurationOrNull(Object)
	 */
	I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull();

	List<I_M_HU_LUTU_Configuration> getCurrentLUTUConfigurationAlternatives();

	/**
	 * Sets given configuration to underlying document line and save that line.
	 *
	 * @param lutuConfiguration
	 */
	void setCurrentLUTUConfigurationAndSave(I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * Updates given LU/TU Configuration from underlying document line.
	 *
	 * @param lutuConfiguration
	 *
	 * @see IDocumentLUTUConfigurationHandler#updateLUTUConfiguration(I_M_HU_LUTU_Configuration, Object).
	 */
	void updateLUTUConfigurationFromModel(I_M_HU_LUTU_Configuration lutuConfiguration);

	ILUTUConfigurationEditor startEditing();

	/**
	 * Suggest {@link I_M_HU_PI_Item_Product} to be used.
	 *
	 * @param documentLine
	 * @return PI Item Product or Virtual PI's Item Product if nothing else found; never return null
	 * @see IDocumentLUTUConfigurationHandler#getM_HU_PI_Item_Product(Object)
	 */
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product();
}

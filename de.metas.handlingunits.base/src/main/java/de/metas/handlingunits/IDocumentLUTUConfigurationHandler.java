package de.metas.handlingunits;

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

import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;

/**
 * Implementations of this interface are able to manage the relation between {@link I_M_HU_LUTU_Configuration} and particular document line.
 *
 * @author tsa
 *
 * @param <T> document line type
 */
public interface IDocumentLUTUConfigurationHandler<T>
{
	/**
	 * Create a <b>NEW</b> LU/TU Configuration based on given <code>documentLine</code>.
	 *
	 * NOTE:
	 * <ul>
	 * <li>don't check if there is an already LU/TU configuration created for that document line.
	 * <li>don't save the created LU/TU configuration.
	 * </ul>
	 *
	 * @param documentLine
	 * @return newly created (but not saved) configuration
	 */
	I_M_HU_LUTU_Configuration createNewLUTUConfiguration(final T documentLine);

	/**
	 * Update the given LU/TU Configuration from underlying document line.
	 *
	 * @param lutuConfiguration
	 */
	void updateLUTUConfiguration(final I_M_HU_LUTU_Configuration lutuConfiguration, final T documentLine);

	/**
	 * Set documentLine's current LU/TU Configuration.
	 *
	 * NOTE: document line is not saved.
	 *
	 * @param documentLine
	 * @param lutuConfiguration
	 */
	void setCurrentLUTUConfiguration(final T documentLine, final I_M_HU_LUTU_Configuration lutuConfiguration);

	/**
	 * Gets documentLine's current LU/TU configuration
	 *
	 * @param documentLine
	 * @return current LU/TU configuration or <code>null</code>.
	 */
	I_M_HU_LUTU_Configuration getCurrentLUTUConfigurationOrNull(final T documentLine);

	/**
	 * Get documentLine's current LU/TU configuration alternatives (if any)
	 *
	 * @param documentLine
	 * @return current LU/TU configuration alternatives or empty; never return <code>null</code>
	 */
	List<I_M_HU_LUTU_Configuration> getCurrentLUTUConfigurationAlternatives(final T documentLine);

	/**
	 * Suggest {@link I_M_HU_PI_Item_Product} to be used.
	 *
	 * @param documentLine
	 * @return PI Item Product or Virtual PI's Item Product if nothing else found; never return null
	 */
	I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(T documentLine);

	void save(T documentLine);
}

package de.metas.handlingunits;

import java.util.function.Function;

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


import org.adempiere.util.collections.Converter;

import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;

/**
 * Implementors of this interface can safely alter an {@link I_M_HU_LUTU_Configuration}.
 */
public interface ILUTUConfigurationEditor
{
	/**
	 * Tells the caller if this instance is still in the editing mode.
	 *
	 * @return <code>true</code>, unless {@link #save()} was already called.
	 */
	boolean isEditing();

	/**
	 * Returns the configuration that was created by invoking {@link #edit(Converter)} (an arbitrary number of times) on the initial configuration. If {@link #edit(Converter)} was not yet called, then
	 * an exact copy of the initial configuration is returned. If {@link #save()} was already called, then the method returns <code>null</code>, because there isn't anything to edit anymore.
	 *
	 * @return
	 * @see #getLUTUConfiguration()
	 */
	I_M_HU_LUTU_Configuration getEditingLUTUConfiguration();

	/**
	 *
	 *
	 * @return the initial configuration which will not be changed by invocations of {@link #edit(Converter)}.
	 */
	I_M_HU_LUTU_Configuration getLUTUConfiguration();

	/**
	 * Applies the given converter to change this instance's editing configuration (see {@link #getEditingLUTUConfiguration()}).
	 *
	 * @param lutuConfigurationEditor
	 * @return this instance
	 */
	ILUTUConfigurationEditor edit(final Function<I_M_HU_LUTU_Configuration, I_M_HU_LUTU_Configuration> lutuConfigurationEditor);

	/**
	 * Updates this instance's editing-configuration (see {@link #getEditingLUTUConfiguration()}) from the underlying document.
	 *
	 * @return this instance
	 * @see IDocumentLUTUConfigurationManager#updateLUTUConfigurationFromModel(I_M_HU_LUTU_Configuration)
	 */
	ILUTUConfigurationEditor updateFromModel();

	/**
	 * Saves the changes made to the {@link I_M_HU_LUTU_Configuration} by this editor back to the DB and exists the editing mode.
	 *
	 * @return this instance
	 */
	ILUTUConfigurationEditor save();

	/**
	 * Synchronizes this editing-configuration (see {@link #getEditingLUTUConfiguration()}) back to the underlying document.
	 *
	 * @return this instance
	 */
	ILUTUConfigurationEditor pushBackToModel();

}

package de.metas.handlingunits;

import java.util.function.Function;

import de.metas.handlingunits.impl.IDocumentLUTUConfigurationManager;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.util.collections.Converter;

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
	 * Save the changes made to the {@link I_M_HU_LUTU_Configuration} by this editor back to the DB and exist the editing mode.
	 *
	 * @return this instance
	 */
	ILUTUConfigurationEditor save();

	/**
	 * Synchronize this editing-configuration (see {@link #getEditingLUTUConfiguration()}) back to the underlying {@link I_M_HU_LUTU_Configuration}.
	 *
	 * @return this instance
	 */
	ILUTUConfigurationEditor pushBackToModel();

}

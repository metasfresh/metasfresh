package de.metas.handlingunits.attribute;

import de.metas.handlingunits.attribute.impl.HUAttributesBySeqNoComparator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.util.ISingletonService;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;

/**
 * Note: there are multiple implementations of this API. One (the "default" one) of them is returned by {@link de.metas.util.Services#get(Class)}, the others are only instantiated and returned by
 * factories.
 */
public interface IHUAttributesDAO extends ISingletonService
{
	/**
	 * @return new {@link I_M_HU_Attribute} (not saved)
	 */
	I_M_HU_Attribute newHUAttribute(Object contextProvider);

	void save(I_M_HU_Attribute huAttribute);

	void delete(I_M_HU_Attribute huAttribute);

	/**
	 * Load the given <code>hu</code>'s attributes, ordered by their <code>M_HU_PI_Attribute</code>'s <code>SeqNo</code> (see {@link HUAttributesBySeqNoComparator}).
	 *
	 * @return sorted HU attributes
	 */
	HUAndPIAttributes retrieveAttributesOrdered(I_M_HU hu);

	/**
	 * @return the attribute or <code>null</code>
	 */
	I_M_HU_Attribute retrieveAttribute(I_M_HU hu, AttributeId attributeId);

	/**
	 * If the DAO implementation supports "autoflush" this method will disable it
	 * and it will return an {@link IAutoCloseable} which when closed it will enable back the "autoflush".
	 * <p>
	 * If DAO implementation does not support "autoflush", this method will return a {@link NullAutoCloseable}.
	 * <p>
	 * If the autoflush is already disabled, this method will return {@link NullAutoCloseable}.
	 *
	 * @return {@link IAutoCloseable} to enable back the autoflush or {@link NullAutoCloseable}
	 */
	IAutoCloseable temporaryDisableAutoflush();

	/**
	 * Clears internal cache if the implementation has an internal cache.
	 * If there is no internal cache, this method will do nothing.
	 */
	@Deprecated
	void flushAndClearCache();

	/**
	 * Saves cached data to database
	 */
	void flush();
}

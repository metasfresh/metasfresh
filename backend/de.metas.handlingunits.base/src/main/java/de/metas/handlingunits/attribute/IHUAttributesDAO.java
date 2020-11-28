package de.metas.handlingunits.attribute;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.NullAutoCloseable;

import de.metas.handlingunits.attribute.impl.HUAttributesBySeqNoComparator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.util.ISingletonService;

/**
 * Note: there are multiple implementations of this API. One (the "default" one) of them is returned by {@link de.metas.util.Services#get(Class)}, the others are only instantiated and returned by
 * factories.
 *
 *
 */
public interface IHUAttributesDAO extends ISingletonService
{
	/**
	 * @param contextProvider
	 * @return new {@link I_M_HU_Attribute} (not saved)
	 */
	I_M_HU_Attribute newHUAttribute(Object contextProvider);

	/** Save given {@link I_M_HU_Attribute} */
	void save(I_M_HU_Attribute huAttribute);

	/** Delete gven {@link I_M_HU_Attribute} */
	void delete(I_M_HU_Attribute huAttribute);

	/**
	 * Load the given <code>hu</code>'s attributes, ordered by their <code>M_HU_PI_Attribute</code>'s <code>SeqNo</code> (see {@link HUAttributesBySeqNoComparator}).
	 *
	 * @param hu
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
	 *
	 * If DAO implementation does not support "autoflush", this method will return a {@link NullAutoCloseable}.
	 *
	 * If the autoflush is already disabled, this method will return {@link NullAutoCloseable}.
	 *
	 * @return {@link IAutoCloseable} to enable back the autoflush or {@link NullAutoCloseable}
	 */
	IAutoCloseable temporaryDisableAutoflush();

	/**
	 * Clears internal cache if the implementation has an internal cache.
	 * If there is no internal cache, this method will do nothing.
	 */
	void flushAndClearCache();
}

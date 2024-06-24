package de.metas.handlingunits.attribute;

import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.impl.HUAttributesBySeqNoComparator;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Attribute;
import de.metas.util.ISingletonService;
import org.adempiere.mm.attributes.AttributeId;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;

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

	List<I_M_HU_Attribute> retrieveAllAttributesNoCache(Collection<HuId> huIds);

	/**
	 * Load the given <code>hu</code>'s attributes, ordered by their <code>M_HU_PI_Attribute</code>'s <code>SeqNo</code> (see {@link HUAttributesBySeqNoComparator}).
	 *
	 * @return sorted HU attributes
	 */
	HUAndPIAttributes retrieveAttributesOrdered(I_M_HU hu);

	/**
	 * @return the attribute or <code>null</code>
	 */
	@Nullable
	I_M_HU_Attribute retrieveAttribute(I_M_HU hu, AttributeId attributeId);

	/**
	 * Saves cached data to database
	 */
	void flush();
}

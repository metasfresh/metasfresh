package de.metas.handlingunits.attribute;

import java.util.Set;

import de.metas.handlingunits.HuPackingInstructionsAttributeId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.util.ISingletonService;

import javax.annotation.Nullable;

public interface IHUPIAttributesDAO extends ISingletonService
{
	/**
	 * Retrieve available PI Attributes for given PI Version.
	 *
	 * Mainly, returned list consists of
	 * <ul>
	 * <li>direct attributes - PI Attributes which are directly defined on given <code>version</code>
	 * <li>attributes from template ( {@link HuPackingInstructionsVersionId#TEMPLATE} )
	 * </ul>
	 *
	 * <br>
	 * NOTE: all attributes will be returned out of transaction no matter what transaction <code>version</code> has (this is because we want to make use of caching)
	 *
	 * @return available PI attributes
	 */
	PIAttributes retrievePIAttributes(HuPackingInstructionsVersionId piVersionId);

	PIAttributes retrievePIAttributesByIds(Set<Integer> piAttributeIds);

	/**
	 * @return
	 *         <ul>
	 *         <li>true if this attribute was defined by the standard template
	 *         <li>false if this attribute is a customization on a particular element (e.g.HU, ASI etc)
	 *         </ul>
	 * task FRESH-578 #275
	 */
	boolean isTemplateAttribute(I_M_HU_PI_Attribute huPIAttribute);

	void deleteByVersionId(HuPackingInstructionsVersionId versionId);

	@Nullable I_M_HU_PI_Attribute getById(@Nullable HuPackingInstructionsAttributeId huPIAttributeId);

}

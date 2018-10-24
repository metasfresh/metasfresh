package de.metas.handlingunits.attribute;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.util.ISingletonService;

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
	 * @param piVersionId
	 * @return available PI attributes
	 */
	PIAttributes retrievePIAttributes(HuPackingInstructionsVersionId piVersionId);

	/**
	 *
	 * @param huPIAttribute
	 * @return
	 *         <ul>
	 *         <li>true if this attribute was defined by the standard template
	 *         <li>false if this attribute is a customization on a particular element (e.g.HU, ASI etc)
	 *         </ul>
	 * @task FRESH-578 #275
	 */
	boolean isTemplateAttribute(I_M_HU_PI_Attribute huPIAttribute);

	void deleteByVersionId(HuPackingInstructionsVersionId versionId);
}

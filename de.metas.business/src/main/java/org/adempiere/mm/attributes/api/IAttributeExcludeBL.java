package org.adempiere.mm.attributes.api;

import org.adempiere.mm.attributes.AttributeSetId;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetExclude;

import de.metas.lang.SOTrx;
import de.metas.util.ISingletonService;

public interface IAttributeExcludeBL extends ISingletonService
{
	/**
	 * Returns true if all the attributes in the AttributeSet are to be excluded from this table.
	 * This can be done either by having no AttributeSetExcludeLine entries or by having all attributes marked to be excluded.
	 * 
	 * @param attributeSetExclude
	 * @return
	 */
	boolean isFullExclude(I_M_AttributeSetExclude attributeSetExclude);

	/**
	 * Checks if an attribute should be excluded or not in the current table and transaction type.
	 * 
	 * @param attribute
	 * @param attributeSetId
	 * @param columnId
	 * @param soTrx
	 * @return
	 */
	boolean isExcludedAttribute(I_M_Attribute attribute, AttributeSetId attributeSetId, int columnId, SOTrx soTrx);
	
	/**
	 * Gets the attribute set exclude for the current table and transaction type.
	 */
	I_M_AttributeSetExclude getAttributeSetExclude(AttributeSetId attributeSetId, int columnId, SOTrx soTrx);
}

package org.adempiere.mm.attributes.api;

import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetExclude;

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
	 * @param attributeSet
	 * @param columnId
	 * @param isSOTrx
	 * @return
	 */
	boolean isExcludedAttribute(I_M_Attribute attribute, I_M_AttributeSet attributeSet, int columnId, boolean isSOTrx);
	
	/**
	 * Gets the attribute set exclude for the current table and transaction type.
	 * 
	 * @param attributeSet
	 * @param columnId
	 * @param isSOTrx
	 * @return
	 */
	I_M_AttributeSetExclude getAttributeSetExclude(I_M_AttributeSet attributeSet, int columnId, boolean isSOTrx);
}

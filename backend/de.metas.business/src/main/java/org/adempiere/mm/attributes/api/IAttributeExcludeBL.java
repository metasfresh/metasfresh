package org.adempiere.mm.attributes.api;

import de.metas.lang.SOTrx;
import de.metas.util.ISingletonService;
import org.adempiere.mm.attributes.AttributeSetId;
import org.compiere.model.I_M_AttributeSetExclude;

public interface IAttributeExcludeBL extends ISingletonService
{
	/**
	 * Returns true if all the attributes in the AttributeSet are to be excluded from this table.
	 * This can be done either by having no AttributeSetExcludeLine entries or by having all attributes marked to be excluded.
	 *
	 */
	boolean isFullExclude(I_M_AttributeSetExclude attributeSetExclude);

	/**
	 * Checks if an attribute should be excluded or not in the current table and transaction type.
	 *
	 */
	boolean isExcludedAttribute(Attribute attribute, AttributeSetId attributeSetId, int columnId, SOTrx soTrx);
	
	/**
	 * Gets the attribute set exclude for the current table and transaction type.
	 */
	I_M_AttributeSetExclude getAttributeSetExclude(AttributeSetId attributeSetId, int columnId, SOTrx soTrx);
}

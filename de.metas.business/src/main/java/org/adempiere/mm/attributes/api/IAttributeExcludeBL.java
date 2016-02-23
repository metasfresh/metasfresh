package org.adempiere.mm.attributes.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetExclude;

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

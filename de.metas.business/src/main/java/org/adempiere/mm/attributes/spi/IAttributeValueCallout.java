package org.adempiere.mm.attributes.spi;

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


import org.adempiere.mm.attributes.api.IAttributeSet;
import org.compiere.model.I_M_Attribute;

/**
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IAttributeValueCallout
{
	/**
	 * Fired when attribute value was changed in given context.
	 * 
	 * @param attributeValueContext
	 * @param attributeSet
	 * @param attribute
	 * @param valueOld
	 * @param valueNew
	 */
	void onValueChanged(IAttributeValueContext attributeValueContext, IAttributeSet attributeSet, I_M_Attribute attribute, Object valueOld, Object valueNew);

	/**
	 * Generates an initial value for the current attribute.
	 * 
	 * @param attributeSet
	 * @param attribute
	 * @param valueInitialDefault default suggested by default or <code>null</code> if there is no suggestion
	 * @return seed value
	 */
	Object generateSeedValue(IAttributeSet attributeSet, I_M_Attribute attribute, Object valueInitialDefault);

	/**
	 * @param ctx evaluation context
	 * @param attributeSet
	 * @param attribute
	 * @return true if given attribute is readonly for user
	 */
	boolean isReadonlyUI(final IAttributeValueContext ctx, IAttributeSet attributeSet, I_M_Attribute attribute);

	boolean isAlwaysEditableUI(IAttributeValueContext ctx, IAttributeSet attributeSet, I_M_Attribute attribute);
}

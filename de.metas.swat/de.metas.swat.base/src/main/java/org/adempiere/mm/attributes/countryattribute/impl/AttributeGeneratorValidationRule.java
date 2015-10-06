package org.adempiere.mm.attributes.countryattribute.impl;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.appdict.IJavaClassBL;
import org.adempiere.appdict.IJavaClassDAO;
import org.adempiere.appdict.model.I_AD_JavaClass;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.NamePair;

/**
 * Validation rule used to filter out attribute handler {@link I_AD_JavaClass}es which are not compatible with current {@link I_M_Attribute}.
 * 
 * @author tsa
 * 
 */
public class AttributeGeneratorValidationRule extends AbstractJavaValidationRule
{

	@Override
	public boolean isImmutable()
	{
		return false;
	}

	@Override
	public boolean accept(IValidationContext evalCtx, NamePair item)
	{
		final String valueType = evalCtx.get_ValueAsString(I_M_Attribute.COLUMNNAME_AttributeValueType);

		// AttributeValueType not set yet => don't accept any JavaClass
		if (Check.isEmpty(valueType))
		{
			return false;
		}

		// If AttributeValueType is List we accept this JavaClass right away
		// because attribute's handler  supported value type can only be Number or String so we cannot validate
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(valueType))
		{
			return true;
		}


		//
		// Get I_AD_JavaClass
		final int adJavaClassId = Integer.parseInt(item.getID());
		final I_AD_JavaClass javaClass = Services.get(IJavaClassDAO.class).retriveJavaClassOrNull(Env.getCtx(), adJavaClassId);
		if (null == javaClass)
		{
			return false;
		}

		//
		// Create Attribute Handler's instance
		final IAttributeValueGenerator generator = Services.get(IJavaClassBL.class).newInstance(javaClass);
		if (null == generator)
		{
			return false;
		}

		//
		// Handler shall have the same type as our attribute
		if (!valueType.equals(generator.getAttributeValueType()))
		{
			return false;
		}
		
		//
		// Default: accept
		return true;
	}

	@Override
	public List<String> getParameters(IValidationContext evalCtx)
	{
		return Arrays.asList(I_M_Attribute.COLUMNNAME_AttributeValueType);
	}

}

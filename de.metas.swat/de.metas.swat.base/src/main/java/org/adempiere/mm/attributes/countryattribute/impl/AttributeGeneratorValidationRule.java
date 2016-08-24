package org.adempiere.mm.attributes.countryattribute.impl;

import java.util.List;

import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableList;

import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.model.I_AD_JavaClass;

/**
 * Validation rule used to filter out attribute handler {@link I_AD_JavaClass}es which are not compatible with current {@link I_M_Attribute}.
 * 
 * @author tsa
 * 
 */
public class AttributeGeneratorValidationRule extends AbstractJavaValidationRule
{

	private static final List<String> PARAMETERS = ImmutableList.of(I_M_Attribute.COLUMNNAME_AttributeValueType);

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
	public List<String> getParameters()
	{
		return PARAMETERS;
	}

}

package org.adempiere.mm.attributes;

import java.util.Set;

import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValueHandler;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.NamePair;

import com.google.common.collect.ImmutableSet;

import de.metas.javaclasses.IJavaClassBL;
import de.metas.javaclasses.IJavaClassDAO;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.annotation.Nullable;

/**
 * Validation rule used to filter out attribute handler {@link I_AD_JavaClass}es which are not compatible with current {@link I_M_Attribute}.
 *
 * @author tsa
 *
 */
public class AttributeGeneratorValidationRule extends AbstractJavaValidationRule
{
	private static final Set<String> PARAMETERS = ImmutableSet.of(I_M_Attribute.COLUMNNAME_AttributeValueType);

	@Override
	public boolean accept(final IValidationContext evalCtx, final NamePair item)
	{
		final String valueType = evalCtx.get_ValueAsString(I_M_Attribute.COLUMNNAME_AttributeValueType);

		// AttributeValueType not set yet => don't accept any JavaClass
		if (Check.isEmpty(valueType))
		{
			return false;
		}

		// If AttributeValueType is List we accept this JavaClass right away
		// because attribute's handler supported value type can only be Number or String so we cannot validate
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
		final IJavaClassBL javaClassBL = Services.get(IJavaClassBL.class);

		final IAttributeValueHandler handler = javaClassBL.newInstance(javaClass);
		if (null == handler)
		{
			return false;
		}

		if (handler instanceof IAttributeValueGenerator)
		{
			//
			// generator shall have the same type as our attribute
			final IAttributeValueGenerator generator = (IAttributeValueGenerator)handler;
			final String generatorAcceptsValueType = generator.getAttributeValueType();
			return generatorAcceptsValueType == null || valueType.equals(generatorAcceptsValueType);
		}

		//
		// Default: accept
		return true;
	}

	@Override
	public Set<String> getParameters(@Nullable final String contextTableName)
	{
		return PARAMETERS;
	}

}

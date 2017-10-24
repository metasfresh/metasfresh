package org.adempiere.mm.attributes.api.impl;

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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.callout.M_Attribute;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_AD_Ref_List;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeUse;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_AttributeValue_Mapping;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.junit.Ignore;

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.javaclasses.model.I_AD_JavaClass_Type;

/**
 * Base context and helpers for {@link M_Attribute}s related tests.
 * 
 * @author tsa
 *
 */
@Ignore
public class AttributesTestHelper
{
	public final Properties ctx;
	public PlainContextAware context;

	public I_AD_JavaClass_Type attributeGeneratorType;

	public AttributesTestHelper()
	{
		ctx = Env.getCtx();
		context = PlainContextAware.newOutOfTrx(ctx);

		attributeGeneratorType = createAD_JavaClass_Type(IAttributeValueGenerator.class.getName());

	}

	public I_M_AttributeSet createM_AttributeSet(final I_M_Attribute... attributes)
	{
		final I_M_AttributeSet as = InterfaceWrapperHelper.create(ctx, I_M_AttributeSet.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(as);

		for (final I_M_Attribute attribute : attributes)
		{
			createM_AttributeUse(as, attribute);
		}
		return as;
	}

	public void createM_AttributeUse(final I_M_AttributeSet as, final I_M_Attribute attribute)
	{
		final I_M_AttributeUse attributeUse = InterfaceWrapperHelper.newInstance(I_M_AttributeUse.class, as);
		attributeUse.setM_AttributeSet_ID(as.getM_AttributeSet_ID());
		attributeUse.setM_Attribute_ID(attribute.getM_Attribute_ID());
		InterfaceWrapperHelper.save(attributeUse);
	}

	public I_M_Attribute createM_Attribute(final I_AD_JavaClass javaClass)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.create(ctx, I_M_Attribute.class, ITrx.TRXNAME_None);
		attribute.setAD_JavaClass(javaClass);
		InterfaceWrapperHelper.save(attribute);
		return attribute;
	}
	
	public I_M_Attribute createM_Attribute_TypeList(final String name)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class, context);
		attribute.setValue(name);
		attribute.setName(name);
		attribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_List);
		InterfaceWrapperHelper.save(attribute);
		return attribute;
	}

	public I_M_AttributeValue createM_AttributeValue(final org.compiere.model.I_M_Attribute attribute, final String value)
	{
		final I_M_AttributeValue attributeValue = InterfaceWrapperHelper.newInstance(I_M_AttributeValue.class, context);
		attributeValue.setM_Attribute(attribute);
		attributeValue.setValue(value);
		attributeValue.setName("Name_" + value);
		InterfaceWrapperHelper.save(attributeValue);
		return attributeValue;
	}
	
	public I_M_AttributeValue_Mapping createM_AttributeValue_Mapping(final I_M_AttributeValue attributeValue, final I_M_AttributeValue attributeValueTo)
	{
		final I_M_AttributeValue_Mapping attributeValueMapping = InterfaceWrapperHelper.newInstance(I_M_AttributeValue_Mapping.class, context);
		attributeValueMapping.setM_AttributeValue(attributeValue);
		attributeValueMapping.setM_AttributeValue_To(attributeValueTo);
		InterfaceWrapperHelper.save(attributeValueMapping);
		return attributeValueMapping;
	}

	public I_AD_JavaClass createAD_JavaClass(final String classname)
	{
		final I_AD_JavaClass javaClassDef = InterfaceWrapperHelper.create(ctx, I_AD_JavaClass.class, ITrx.TRXNAME_None);
		javaClassDef.setAD_JavaClass_Type(attributeGeneratorType);
		javaClassDef.setClassname(classname);
		InterfaceWrapperHelper.save(javaClassDef);
		return javaClassDef;
	}

	public I_AD_JavaClass createAD_JavaClass(final Class<? extends IAttributeValueGenerator> clazz)
	{
		return createAD_JavaClass(clazz.getName());
	}

	private I_AD_JavaClass_Type createAD_JavaClass_Type(final String interfaceClassname)
	{
		final I_AD_JavaClass_Type javaClassTypeDef = InterfaceWrapperHelper.create(ctx, I_AD_JavaClass_Type.class, ITrx.TRXNAME_None);
		javaClassTypeDef.setClassname(interfaceClassname);
		InterfaceWrapperHelper.save(javaClassTypeDef);
		return javaClassTypeDef;
	}
	
	public void createAD_Ref_List_Items(final int adReferenceId, final String ...values)
	{
		for (final String value : values)
		{
			final I_AD_Ref_List item = InterfaceWrapperHelper.create(ctx, I_AD_Ref_List.class, ITrx.TRXNAME_None);
			item.setAD_Reference_ID(adReferenceId);
			item.setValue(value);
			item.setName(value);
			item.setValueName(value);
			InterfaceWrapperHelper.save(item);
		}
	}
}

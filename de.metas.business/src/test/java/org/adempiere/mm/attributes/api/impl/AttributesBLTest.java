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

import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.AbstractAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.util.Services;

public class AttributesBLTest
{
	private AttributesBL attributesBL;

	private AttributesTestHelper helper;
	private I_AD_JavaClass attributeGenerator;

	public static class MockedAttributeValueGenerator extends AbstractAttributeValueGenerator
	{
		public static String attributeValueType;
		public static String generatedValue;

		@Override
		public String getAttributeValueType()
		{
			return attributeValueType;
		}

		@Override
		public boolean canGenerateValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
		{
			return true;
		}

		@Override
		public String generateStringValue(Properties ctx, IAttributeSet attributeSet, I_M_Attribute attribute)
		{
			return generatedValue;
		}
	}

	@BeforeClass
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new AttributesTestHelper();
		attributesBL = (AttributesBL)Services.get(IAttributesBL.class);

		attributeGenerator = helper.createAD_JavaClass(MockedAttributeValueGenerator.class.getName());
	}

	@Test
	public void testGenerateValue()
	{
		final I_M_Attribute attribute = helper.createM_Attribute(attributeGenerator);

		final String generatedValueExpected = "12345";

		MockedAttributeValueGenerator.generatedValue = generatedValueExpected;
		final IAttributeValueGenerator generator = attributesBL.getAttributeValueGenerator(attribute);

		final IAttributeSet attributeSet = null; // N/A, shall be fine in tests
		final String generatedValueActual = generator.generateStringValue(Env.getCtx(), attributeSet, attribute);

		Assert.assertEquals(generatedValueExpected, generatedValueActual);
	}

	@Test
	public void testGenerateValueType()
	{
		final I_M_Attribute attribute = helper.createM_Attribute(attributeGenerator);

		final String attributeValueTypeExpected = "abc";

		MockedAttributeValueGenerator.attributeValueType = attributeValueTypeExpected;

		final IAttributeValueGenerator generator = attributesBL.getAttributeValueGenerator(attribute);

		final String attributeValueTypeActual = generator.getAttributeValueType();

		Assert.assertEquals(attributeValueTypeExpected, attributeValueTypeActual);
	}
}

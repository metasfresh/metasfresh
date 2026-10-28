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

import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.product.ProductId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.spi.AbstractAttributeValueGenerator;
import org.adempiere.mm.attributes.spi.IAttributeValueGenerator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_Product;
import org.compiere.util.Env;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Properties;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

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

	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
	}

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		helper = new AttributesTestHelper();
		attributesBL = (AttributesBL)Services.get(IAttributesBL.class);

		attributeGenerator = helper.createAD_JavaClass(MockedAttributeValueGenerator.class.getName());
	}

	private I_M_Attribute createStringAttribute()
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_Attribute.class);
		attribute.setValue("attribute");
		attribute.setName("attribute");
		attribute.setAttributeValueType(AttributeValueType.STRING.getCode());
		attribute.setAD_JavaClass_ID(attributeGenerator.getAD_JavaClass_ID());
		save(attribute);
		return attribute;
	}

	@Test
	public void testGenerateValue()
	{
		final I_M_Attribute attribute = createStringAttribute();

		final String generatedValueExpected = "12345";

		MockedAttributeValueGenerator.generatedValue = generatedValueExpected;
		final IAttributeValueGenerator generator = attributesBL.getAttributeValueGenerator(attribute);

		final IAttributeSet attributeSet = null; // N/A, shall be fine in tests
		final String generatedValueActual = generator.generateStringValue(Env.getCtx(), attributeSet, attribute);

		Assertions.assertEquals(generatedValueExpected, generatedValueActual);
	}

	@Test
	public void testGenerateValueType()
	{
		final I_M_Attribute attribute = createStringAttribute();

		final String attributeValueTypeExpected = "abc";

		MockedAttributeValueGenerator.attributeValueType = attributeValueTypeExpected;

		final IAttributeValueGenerator generator = attributesBL.getAttributeValueGenerator(attribute);

		final String attributeValueTypeActual = generator.getAttributeValueType();

		Assertions.assertEquals(attributeValueTypeExpected, attributeValueTypeActual);
	}

	private ProductId createProductWithBestBeforeDays(final int bestBeforeDays)
	{
		final I_M_Product product = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_Product.class);
		product.setValue("product");
		product.setName("product");
		product.setBestBeforeDays(bestBeforeDays);
		save(product);
		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Test
	public void computeBestBeforeDefault_bestBeforeDaysSet_receiptDayIsDayZero()
	{
		final ProductId productId = createProductWithBestBeforeDays(10);

		final Optional<LocalDate> result = attributesBL.computeBestBeforeDefault(productId, LocalDate.of(2026, 10, 19));

		assertThat(result).contains(LocalDate.of(2026, 10, 29));
	}

	@Test
	public void computeBestBeforeDefault_bestBeforeDaysUnset_empty()
	{
		final ProductId productId = createProductWithBestBeforeDays(0);

		assertThat(attributesBL.computeBestBeforeDefault(productId, LocalDate.of(2026, 10, 19))).isEmpty();
	}

	@Test
	public void computeBestBeforeDefault_bestBeforeDaysNegative_empty()
	{
		final ProductId productId = createProductWithBestBeforeDays(-5);

		assertThat(attributesBL.computeBestBeforeDefault(productId, LocalDate.of(2026, 10, 19))).isEmpty();
	}
}

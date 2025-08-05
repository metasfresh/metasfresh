package de.metas.handlingunits.attributes.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.AbstractHUTest;
import de.metas.handlingunits.StaticHUAssert;
import de.metas.handlingunits.attribute.exceptions.AttributeNotFoundException;
import de.metas.handlingunits.attribute.exceptions.InvalidAttributeValueException;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class HUAttributeSetTest extends AbstractHUTest
{
	private I_M_Attribute attrString1;
	private I_M_Attribute attrNumber1;

	private I_M_Attribute attrList1;
	private static final String ATTRLIST1_Value1 = "listvalue1";
	private static final String ATTRLIST1_Value2 = "listvalue2";
	private static final String ATTRLIST1_Value3 = "listvalue3";

	@Override
	protected void initialize()
	{
		final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();

		attrString1 = attributesTestHelper.createM_Attribute("String1", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		attrNumber1 = attributesTestHelper.createM_Attribute("Number1", X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);

		attrList1 = attributesTestHelper.createM_Attribute("List1", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
		helper.createAttributeListValues(attrList1, HUAttributeSetTest.ATTRLIST1_Value1, HUAttributeSetTest.ATTRLIST1_Value2, HUAttributeSetTest.ATTRLIST1_Value3);
	}

	@Test
	public void testStringAttribute()
	{
		final IAttributeStorage as = helper.createAttributeSetStorage(helper.createAttributeValue(attrString1, "value1"));

		assertThat(as.getValue(attrString1))
				.as("Invalid value for " + attrString1)
				.isEqualTo("value1");

		as.setValueNoPropagate(attrString1, "value2");
		assertThat(as.getValue(attrString1))
				.as("Invalid value for " + attrString1)
				.isEqualTo("value2");
	}

	@Test
	public void testNumericAttribute()
	{
		final IAttributeStorage as = helper.createAttributeSetStorage(helper.createAttributeValue(attrNumber1, BigDecimal.ONE));

		assertThat((BigDecimal)as.getValue(attrNumber1))
				.as("Invalid value for " + attrNumber1)
				.isEqualByComparingTo(BigDecimal.ONE);

		as.setValueNoPropagate(attrNumber1, new BigDecimal("2"));
		assertThat((BigDecimal)as.getValue(attrNumber1))
				.as("Invalid value for " + attrNumber1)
				.isEqualByComparingTo(new BigDecimal("2"));
	}

	@Test
	public void testSettingInvalidAttributeValue()
	{
		assertThatThrownBy(() -> {
			final IAttributeStorage as = helper.createAttributeSetStorage(helper.createAttributeValue(attrNumber1, BigDecimal.ONE));
			as.setValueNoPropagate(attrNumber1, "string value");
			StaticHUAssert.assertMock("mock");
		})
				.isInstanceOf(InvalidAttributeValueException.class);
	}

	@Test
	public void testAttributeNotFound()
	{
		assertThatThrownBy(() -> {
			final IAttributeStorage as = helper.createAttributeSetStorage(helper.createAttributeValue(attrNumber1, BigDecimal.ONE));
			as.getValue(attrString1);
			StaticHUAssert.assertMock("mock");
		})
				.isInstanceOf(AttributeNotFoundException.class);
	}

	@Test
	public void testListAttribute()
	{
		final IAttributeStorage as = helper.createAttributeSetStorage(helper.createAttributeValue(attrList1, HUAttributeSetTest.ATTRLIST1_Value1));

		assertThat(as.getValue(attrList1))
				.as("Invalid value for " + attrList1)
				.isEqualTo(HUAttributeSetTest.ATTRLIST1_Value1);

		as.setValueNoPropagate(attrList1, HUAttributeSetTest.ATTRLIST1_Value2);
		assertThat(as.getValue(attrList1))
				.as("Invalid value for " + attrList1)
				.isEqualTo(HUAttributeSetTest.ATTRLIST1_Value2);
	}

	@Test
	public void testListAttribute_InvalidValue_OnSet()
	{
		assertThatThrownBy(() -> {
			final IAttributeStorage as = helper.createAttributeSetStorage(helper.createAttributeValue(attrList1, HUAttributeSetTest.ATTRLIST1_Value1));
			as.setValueNoPropagate(attrList1, HUAttributeSetTest.ATTRLIST1_Value2 + "_invalid_value");
			StaticHUAssert.assertMock("mock");
		})
				.isInstanceOf(InvalidAttributeValueException.class);
	}

	@Test
	public void test_getAttributes()
	{
		final IAttributeStorage as = helper.createAttributeSetStorage(
				helper.createAttributeValue(attrString1, "value1"),
				helper.createAttributeValue(attrNumber1, BigDecimal.ONE)
		);

		final Collection<I_M_Attribute> attributes = as.getAttributes();
		assertThat(attributes)
				.as("Invalid attributes list size: " + attributes)
				.hasSize(2)
				.as("Attributes collection should contain both attributes")
				.contains(attrString1, attrNumber1);
	}

	@Test
	public void test_hasAttribute()
	{
		final IAttributeStorage as = helper.createAttributeSetStorage(
				helper.createAttributeValue(attrString1, "value1"),
				helper.createAttributeValue(attrNumber1, BigDecimal.ONE)
		);

		assertThat(as.hasAttribute(attrString1))
				.as("Attribute " + attrString1 + " shall be present in " + as)
				.isTrue();
		
		assertThat(as.hasAttribute(attrNumber1))
				.as("Attribute " + attrNumber1 + " shall be present in " + as)
				.isTrue();
		
		assertThat(as.hasAttribute(attrList1))
				.as("Attribute " + attrList1 + " shall NOT be present in " + as)
				.isFalse();
	}
}
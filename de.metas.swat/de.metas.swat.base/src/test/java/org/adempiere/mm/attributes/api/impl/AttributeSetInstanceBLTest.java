package org.adempiere.mm.attributes.api.impl;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.mm.attributes.api.IAttributeSet;
import org.adempiere.mm.attributes.spi.IAttributeValueCallout;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.time.SystemTime;
import org.assertj.core.api.Condition;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class AttributeSetInstanceBLTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void createASIFromAttributeSet()
	{
		final TestAttributeSet testAttributeSet = new TestAttributeSet();

		final I_M_AttributeSetInstance result = new AttributeSetInstanceBL().createASIFromAttributeSet(testAttributeSet);

		final List<I_M_AttributeInstance> createdAttributeInstances = POJOLookupMap.get().getRecords(I_M_AttributeInstance.class);

		assertThat(createdAttributeInstances).allSatisfy(ai -> assertThat(ai.getM_AttributeSetInstance_ID()).isEqualTo(result.getM_AttributeSetInstance_ID()));

		final Condition<I_M_AttributeInstance> dateAttributeCondition = new Condition<>(
				ai -> ai.getM_Attribute_ID() == testAttributeSet.dateAttribute.getM_Attribute_ID() //
						&& ai.getValueDate().equals(testAttributeSet.dateAttributeValue),
				"dateAttribute with M_Attribute_ID=%s and ValueDate=%s",
				testAttributeSet.dateAttribute.getM_Attribute_ID(),
				testAttributeSet.dateAttributeValue);
		assertThat(createdAttributeInstances).haveExactly(1, dateAttributeCondition);

		final Condition<I_M_AttributeInstance> stringAttributeCondition = new Condition<>(
				ai -> ai.getM_Attribute_ID() == testAttributeSet.stringAttribute.getM_Attribute_ID() //
						&& ai.getValue().equals("stringValue"),
				"stringAttribute with M_Attribute_ID=%s and ValueDate=%s",
				testAttributeSet.stringAttribute.getM_Attribute_ID(),
				"stringValue");
		assertThat(createdAttributeInstances).haveExactly(1, stringAttributeCondition);

		final Condition<I_M_AttributeInstance> listAttributeCondition = new Condition<>(
				ai -> ai.getM_Attribute_ID() == testAttributeSet.listAttribute.getM_Attribute_ID() //
						&& ai.getValue().equals("listValue"),
				"listAttribute with M_Attribute_ID=%s and ValueDate=%s",
				testAttributeSet.listAttribute.getM_Attribute_ID(),
				"listValue");
		assertThat(createdAttributeInstances).haveExactly(1, listAttributeCondition);

		final Condition<I_M_AttributeInstance> numberAttributeCondition = new Condition<>(
				ai -> ai.getM_Attribute_ID() == testAttributeSet.numberAttribute.getM_Attribute_ID() //
						&& ai.getValueNumber().equals(BigDecimal.ONE),
				"numberAttribute with M_Attribute_ID=%s and ValueNumber=%s",
				testAttributeSet.numberAttribute.getM_Attribute_ID(),
				BigDecimal.ONE);
		assertThat(createdAttributeInstances).haveExactly(1, numberAttributeCondition);
	}

	private static class TestAttributeSet implements IAttributeSet
	{
		private final I_M_Attribute dateAttribute;
		private final Timestamp dateAttributeValue;

		private final I_M_Attribute listAttribute;
		private final I_M_Attribute numberAttribute;
		private final I_M_Attribute stringAttribute;
		private final ImmutableMap<I_M_Attribute, ? extends Object> attribute2value;

		private TestAttributeSet()
		{
			final AttributesTestHelper attributesTestHelper = new AttributesTestHelper();
			dateAttribute = attributesTestHelper.createM_Attribute("DateAttribute", X_M_Attribute.ATTRIBUTEVALUETYPE_Date, true);
			listAttribute = attributesTestHelper.createM_Attribute("ListAttribute", X_M_Attribute.ATTRIBUTEVALUETYPE_List, true);
			numberAttribute = attributesTestHelper.createM_Attribute("NumberAttribute", X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);
			stringAttribute = attributesTestHelper.createM_Attribute("StringAttribute", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);

			dateAttributeValue = SystemTime.asTimestamp();
			attribute2value = ImmutableMap.of(
					dateAttribute, dateAttributeValue,
					listAttribute, "listValue",
					numberAttribute, BigDecimal.ONE,
					stringAttribute, "stringValue");
		}

		@Override
		public Collection<I_M_Attribute> getAttributes()
		{
			return ImmutableList.of(stringAttribute, numberAttribute, listAttribute, dateAttribute);
		}

		@Override
		public boolean hasAttribute(final I_M_Attribute attribute)
		{
			throw new UnsupportedOperationException("The method hasAttribute is expected not to be called by the method under test");
		}

		@Override
		public I_M_Attribute getAttributeByIdIfExists(final int attributeId)
		{
			throw new UnsupportedOperationException("The method getAttributeByIdIfExists is expected not to be called by the method under test");
		}

		@Override
		public String getAttributeValueType(final I_M_Attribute attribute)
		{
			return attribute.getAttributeValueType();
		}

		@Override
		public Object getValue(final I_M_Attribute attribute)
		{
			assertThat(attribute2value).containsKey(attribute);
			return attribute2value.get(attribute);
		}

		@Override
		public BigDecimal getValueAsBigDecimal(final I_M_Attribute attribute)
		{
			assertThat(attribute).isSameAs(numberAttribute);
			return (BigDecimal)attribute2value.get(attribute);
		}

		@Override
		public int getValueAsInt(final I_M_Attribute attribute)
		{
			return getValueAsBigDecimal(attribute).intValue();
		}

		@Override
		public Date getValueAsDate(final I_M_Attribute attribute)
		{
			assertThat(attribute).isSameAs(dateAttribute);
			return (Date)attribute2value.get(attribute);
		}

		@Override
		public String getValueAsString(final I_M_Attribute attribute)
		{
			assertThat(ImmutableSet.of(stringAttribute, listAttribute)).contains(attribute);
			return (String)attribute2value.get(attribute);
		}

		@Override
		public void setValue(final I_M_Attribute attribute, final Object value)
		{
			throw new UnsupportedOperationException("The method setValue is expected not to be called by the method under test");
		}

		@Override
		public IAttributeValueCallout getAttributeValueCallout(final I_M_Attribute attribute)
		{
			throw new UnsupportedOperationException("The method getAttributeValueCallout is expected not to be called by the method under test");
		}

		@Override
		public boolean isNew(final I_M_Attribute attribute)
		{
			throw new UnsupportedOperationException("The method isNew is expected not to be called by the method under test");
		}

	}
}

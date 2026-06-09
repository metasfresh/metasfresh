/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.handlingunits.rest_api;

import de.metas.handlingunits.rest_api.ExtractCounterAttributesCommand.CounterAttribute;
import de.metas.handlingunits.rest_api.ExtractCounterAttributesCommand.ParsedAttributeCode;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

class ExtractCounterAttributesCommandTest
{
	@Nested
	class parsedAttributeCode
	{
		@Test
		void attributeCodeWithIsOnlyNumber()
		{
			final ParsedAttributeCode parsedAttributeCode = ExtractCounterAttributesCommand.parsedAttributeCode(AttributeCode.ofString("1000001"));
			Assertions.assertThat(parsedAttributeCode)
					.isEqualTo(ParsedAttributeCode.builder()
							.attributeCode(AttributeCode.ofString("1000001"))
							.prefix("1000001")
							.suffix(null)
							.build());
		}

		@Test
		void attributeNotEndingWithNumber()
		{
			final ParsedAttributeCode parsedAttributeCode = ExtractCounterAttributesCommand.parsedAttributeCode(AttributeCode.ofString("indexedAttribute"));
			Assertions.assertThat(parsedAttributeCode)
					.isEqualTo(ParsedAttributeCode.builder()
							.attributeCode(AttributeCode.ofString("indexedAttribute"))
							.prefix("indexedAttribute")
							.suffix(null)
							.build());
		}

		@Test
		void indexedAttribute1()
		{
			final ParsedAttributeCode parsedAttributeCode = ExtractCounterAttributesCommand.parsedAttributeCode(AttributeCode.ofString("indexedAttribute1"));
			Assertions.assertThat(parsedAttributeCode)
					.isEqualTo(ParsedAttributeCode.builder()
							.attributeCode(AttributeCode.ofString("indexedAttribute1"))
							.prefix("indexedAttribute")
							.suffix("1")
							.build());
		}
	}

	@Nested
	class execute
	{
		@BeforeEach
		void beforeEach()
		{
			AdempiereTestHelper.get().init();

			createAttribute("attributeOne", AttributeValueType.NUMBER);
			createAttribute("attributeTwo", AttributeValueType.NUMBER);
			createAttribute("indexedAttribute1", AttributeValueType.STRING);
			createAttribute("indexedAttribute2", AttributeValueType.STRING);
			createAttribute("indexedAttribute3", AttributeValueType.STRING);
			createAttribute("indexedAttribute4", AttributeValueType.STRING);
			createAttribute("indexedAttribute5", AttributeValueType.STRING);
			createAttribute("indexedAttribute6", AttributeValueType.STRING);
		}

		private void createAttribute(final String attributeCode, final AttributeValueType type)
		{
			final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
			attribute.setValue(attributeCode);
			attribute.setName(attributeCode);
			attribute.setAttributeValueType(type.getCode());
			InterfaceWrapperHelper.saveRecord(attribute);
		}

		@Test
		void standardCase()
		{
			final ImmutableAttributeSet attributes = ImmutableAttributeSet.builder()
					.attributeValue(AttributeCode.ofString("attributeOne"), 1)
					.attributeValue(AttributeCode.ofString("attributeTwo"), 2)
					.attributeValue(AttributeCode.ofString("indexedAttribute1"), "value1")
					.attributeValue(AttributeCode.ofString("indexedAttribute2"), "value2")
					.attributeValue(AttributeCode.ofString("indexedAttribute3"), "value3")
					.attributeValue(AttributeCode.ofString("indexedAttribute4"), "value4")
					.attributeValue(AttributeCode.ofString("indexedAttribute5"), null)
					.attributeValue(AttributeCode.ofString("indexedAttribute6"), null)
					.build();

			final List<CounterAttribute> counterAttributes = new ExtractCounterAttributesCommand(attributes).execute();
			Assertions.assertThat(counterAttributes)
					.containsExactly(
							CounterAttribute.builder().attributeCode("indexedAttribute_count").counter(4).build()
					);
		}
	}
}
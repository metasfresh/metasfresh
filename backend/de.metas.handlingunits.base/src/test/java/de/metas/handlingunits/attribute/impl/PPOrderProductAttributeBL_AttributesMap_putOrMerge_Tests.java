package de.metas.handlingunits.attribute.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.attribute.impl.PPOrderProductAttributeBL.AttributeWithValue;
import de.metas.handlingunits.attribute.impl.PPOrderProductAttributeBL.AttributesMap;
import de.metas.handlingunits.model.I_M_Attribute;
import de.metas.handlingunits.model.I_PP_Order_ProductAttribute;

/*
 * #%L
 * de.metas.handlingunits.base
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

/**
 * Verifies the behavior of {@link AttributesMap#putOrMerge(I_PP_Order_ProductAttribute)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/810
 */
public class PPOrderProductAttributeBL_AttributesMap_putOrMerge_Tests
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void transferWhenNullWithThreeAttributesOneFilled_AllNull()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, null);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesOneFilled_AllNull()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, null);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesOneFilled_Value()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final String expectedValue = "ppOrderAttributeValue";

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, null);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.EQUALS, expectedValue // expected
		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesOneFilled_ValueNumber()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, new BigDecimal("2"));
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, null);

		final BigDecimal expectedValue = new BigDecimal("2");

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(), // actual
				AssertionType.COMPARABLE_EQUALS, expectedValue // expected
		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesTwoFilledEqual_Value()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final String expectedValue = "ppOrderAttributeValue";

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, expectedValue);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.EQUALS, expectedValue // expected
		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesTwoFilledEqual_Value_IgnoreWhiteSpace()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final String expectedValue = "ppOrderAttributeValue";

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, "  " + expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, expectedValue + "  ");

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.EQUALS, expectedValue // expected
		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesTwoFilledEqual_ValueNumber()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final BigDecimal expectedValue = new BigDecimal("3");

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, expectedValue.setScale(3)/* equal but not same number */);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(),// actual
				AssertionType.COMPARABLE_EQUALS, expectedValue // expected

		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesTwoFilledDifferent_Value()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueA");
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueB");

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesTwoFilledDifferent_ValueNumber()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, new BigDecimal("3"));
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, new BigDecimal("4"));

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesThreeFilledDifferent_Value()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueA");
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueA");
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueB");

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void transferWhenNullWithThreeAttributesThreeFilledDifferent_ValueNumber()
	{
		final boolean transferWhenNull = true;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, new BigDecimal("3"));
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, new BigDecimal("3"));
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, new BigDecimal("4"));

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesOneFilled_Value()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final String expectedValue = "ppOrderAttributeValue";

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, null);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesOneFilled_ValueNumber()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, new BigDecimal("2"));
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, null);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesTwoFilledEqual_Value()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final String expectedValue = "ppOrderAttributeValue";

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, expectedValue);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesAllFilledEqual_Value_IgnoreWhiteSpace()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final String expectedValue = "ppOrderAttributeValue";

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, "  " + expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, expectedValue + "  ");

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.EQUALS, expectedValue // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesTwoFilledEqual_ValueNumber()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final BigDecimal value = new BigDecimal("3");

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, value);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, value.setScale(3)/* equal but not same number */);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(),// actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesAllFilledEqual_ValueNumber()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final BigDecimal expectedValue = new BigDecimal("3");

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, expectedValue.setScale(3)/* equal but not same number */);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(),// actual
				AssertionType.COMPARABLE_EQUALS, expectedValue // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesTwoFilledDifferent_Value()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueA");
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueB");

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesTwoFilledDifferent_ValueNumber()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, new BigDecimal("3"));
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, new BigDecimal("4"));

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesThreeFilledDifferent_Value()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueA");
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueA");
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeStr(attribute, "ppOrderAttributeValueB");

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueString(), // actual
				AssertionType.NULL // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesThreeFilledDifferent_ValueNumber()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, new BigDecimal("3"));
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, new BigDecimal("3"));
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, new BigDecimal("4"));

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(), // actual
				AssertionType.NULL // expected
		);
	}

	private I_PP_Order_ProductAttribute mkPPOrderAttributeStr(final I_M_Attribute attribute, final String value)
	{
		return mkPPOrderAttribute(attribute, value, null);
	}

	private I_PP_Order_ProductAttribute mkPPOrderAttributeNum(final I_M_Attribute attribute, final BigDecimal valueNumber)
	{
		return mkPPOrderAttribute(attribute, null, valueNumber);
	}

	private I_PP_Order_ProductAttribute mkPPOrderAttribute(final I_M_Attribute attribute, final String value, final BigDecimal valueNumber)
	{
		final I_PP_Order_ProductAttribute ppOrderAttribute1 = InterfaceWrapperHelper.newInstance(I_PP_Order_ProductAttribute.class);
		ppOrderAttribute1.setM_Attribute_ID(attribute.getM_Attribute_ID());
		ppOrderAttribute1.setValue(value);
		ppOrderAttribute1.setValueNumber(valueNumber);
		InterfaceWrapperHelper.save(ppOrderAttribute1);
		return ppOrderAttribute1;
	}

	/**
	 * Calls "putOrMerge" multiple times, with the given three attributes, but in different ordering.
	 * The result always needs to be the same.
	 *
	 * @param attribute
	 * @param ppOrderAttribute1
	 * @param ppOrderAttribute2
	 * @param ppOrderAttribute3
	 * @param actualValue function that will be used to get the actual value (i.e. {@code value} or {@code valueNumber}) when checking the results
	 * @param assertionType type of assertion to perform
	 * @param expectedValue expected value for comparison (optional, not used for NULL assertions)
	 */
	private <T> void performTestWithThreeAttributes(
			final I_M_Attribute attribute, 
			final I_PP_Order_ProductAttribute ppOrderAttribute1, 
			final I_PP_Order_ProductAttribute ppOrderAttribute2, 
			final I_PP_Order_ProductAttribute ppOrderAttribute3, 
			final Function<PPOrderProductAttributeBL.AttributeWithValue, T> actualValue, 
			final AssertionType assertionType,
			final Object... expectedValue)
	{

		// we want to check each different ordering (i.e. all possible permutations).
		final Collection<List<I_PP_Order_ProductAttribute>> permutations = Collections2.permutations(ImmutableList.of(ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3));

		permutations.forEach(permutation -> {
			final AttributesMap attributesMap = new AttributesMap();

			// invoke the code under test
			permutation.forEach(ppOrderAttribute -> attributesMap.putOrMerge(ppOrderAttribute));

			// verify
			try
			{
				verifyInvariants(attribute, attributesMap, actualValue, assertionType, expectedValue);
			}
			catch (final AssertionError e)
			{
				e.printStackTrace();
				fail(e.getMessage()
						+ "\n Permutation:\n" + Joiner.on("\n").join(permutation));
			}
		});

	}

	/**
	 * Called by {@link #performTestWithThreeAttributes(I_M_Attribute, I_PP_Order_ProductAttribute, I_PP_Order_ProductAttribute, I_PP_Order_ProductAttribute, Function, AssertionType, Object...)} to verify each single result.
	 */
	private <T> void verifyInvariants(
			final I_M_Attribute attribute,
			final AttributesMap attributesMap,
			final Function<PPOrderProductAttributeBL.AttributeWithValue, T> actualValue,
			final AssertionType assertionType,
			final Object... expectedValue)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		final AttributeWithValue attributeFromMap = attributesMap.getByAttributeId(attributeId);
		
		switch (assertionType)
		{
			case NULL:
				if (attributeFromMap == null)
				{
					// This is expected for NULL assertion
					return;
				}
				final Object actualForNull = actualValue.apply(attributeFromMap);
				assertThat(actualForNull).isNull();
				break;
				
			case EQUALS:
				assertThat(attributeFromMap).isNotNull();
				final Object actualForEquals = actualValue.apply(attributeFromMap);
				assertThat(actualForEquals).isEqualTo(expectedValue[0]);
				break;
				
			case COMPARABLE_EQUALS:
				assertThat(attributeFromMap).isNotNull();
				final Object actualForComparable = actualValue.apply(attributeFromMap);
				if (actualForComparable instanceof BigDecimal && expectedValue[0] instanceof BigDecimal)
				{
					assertThat((BigDecimal) actualForComparable)
						.isEqualByComparingTo((BigDecimal) expectedValue[0]);
				}
				else
				{
					assertThat(actualForComparable).isEqualTo(expectedValue[0]);
				}
				break;
		}
	}

	private I_M_Attribute mkAttribute(final boolean transferWhenNull)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute.setName("testAttribute");
		attribute.setIsTransferWhenNull(transferWhenNull);
		InterfaceWrapperHelper.save(attribute);
		return attribute;
	}

	private enum AssertionType
	{
		NULL, EQUALS, COMPARABLE_EQUALS
	}
}
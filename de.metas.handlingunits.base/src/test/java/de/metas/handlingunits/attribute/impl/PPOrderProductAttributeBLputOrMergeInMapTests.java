package de.metas.handlingunits.attribute.impl;

import static org.hamcrest.Matchers.comparesEqualTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;

import de.metas.handlingunits.attribute.impl.PPOrderProductAttributeBL.AttributesWithValues;
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
 * Verifies the behavior of {@link PPOrderProductAttributeBL#putOrMergeInMap(Map, I_PP_Order_ProductAttribute)}.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @task https://github.com/metasfresh/metasfresh/issues/810
 */
public class PPOrderProductAttributeBLputOrMergeInMapTests
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
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
				a -> a.getValue(), // actual
				is(expectedValue) // expected
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
				comparesEqualTo(expectedValue) // expected
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
				a -> a.getValue(), // actual
				is(expectedValue) // expected
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
				a -> a.getValue(), // actual
				is(expectedValue) // expected
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
				comparesEqualTo(expectedValue) // expected

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
				a -> a.getValue(), // actual
				nullValue() // expected
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
				nullValue() // expected
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
				a -> a.getValue(), // actual
				nullValue() // expected
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
				nullValue() // expected
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
				a -> a.getValue(), // actual
				nullValue() // expected
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
				nullValue() // expected
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
				a -> a.getValue(), // actual
				nullValue() // expected
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
				a -> a.getValue(), // actual
				is(expectedValue) // expected
		);
	}

	@Test
	public void dontTransferWhenNullWithThreeAttributesTwoFilledEqual_ValueNumber()
	{
		final boolean transferWhenNull = false;
		final I_M_Attribute attribute = mkAttribute(transferWhenNull);

		final BigDecimal expectedValue = new BigDecimal("3");

		final I_PP_Order_ProductAttribute ppOrderAttribute1 = mkPPOrderAttributeNum(attribute, null);
		final I_PP_Order_ProductAttribute ppOrderAttribute2 = mkPPOrderAttributeNum(attribute, expectedValue);
		final I_PP_Order_ProductAttribute ppOrderAttribute3 = mkPPOrderAttributeNum(attribute, expectedValue.setScale(3)/* equal but not same number */);

		performTestWithThreeAttributes(attribute,
				ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3,
				a -> a.getValueNumber(),// actual
				nullValue() // expected
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
				comparesEqualTo(expectedValue) // expected
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
				a -> a.getValue(), // actual
				nullValue() // expected
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
				nullValue() // expected
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
				a -> a.getValue(), // actual
				nullValue() // expected
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
				nullValue() // expected
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
		ppOrderAttribute1.setM_Attribute(attribute);
		ppOrderAttribute1.setValue(value);
		ppOrderAttribute1.setValueNumber(valueNumber);
		InterfaceWrapperHelper.save(ppOrderAttribute1);
		return ppOrderAttribute1;
	}

	/**
	 * Calls {@link PPOrderProductAttributeBL#putOrMergeInMap(Map, I_PP_Order_ProductAttribute)} multiple times, with the given three attributes, but in different ordering.
	 * The result always needs to be the same.
	 *
	 * @param attribute
	 * @param ppOrderAttribute1
	 * @param ppOrderAttribute2
	 * @param ppOrderAttribute3
	 * @param actualValue function that will be used to get the actual value (i.e. {@code value} or {@code valueNumber}) when checking the results
	 * @param expectedValue matcher that is applied to find out if the actual value is OK
	 */
	private <T> void performTestWithThreeAttributes(final I_M_Attribute attribute,
			final I_PP_Order_ProductAttribute ppOrderAttribute1,
			final I_PP_Order_ProductAttribute ppOrderAttribute2,
			final I_PP_Order_ProductAttribute ppOrderAttribute3,
			final Function<PPOrderProductAttributeBL.AttributesWithValues, T> actualValue,
			final Matcher<?> expectedValue)
	{
		final Map<Integer, PPOrderProductAttributeBL.AttributesWithValues> attributesMap = new HashMap<>();

		// we want to check each different ordering (i.e. all possible permutations).
		final Collection<List<I_PP_Order_ProductAttribute>> permutations = Collections2.permutations(ImmutableList.of(ppOrderAttribute1, ppOrderAttribute2, ppOrderAttribute3));
		permutations
				.forEach(permulation -> {

					// invoke the code under test
					permulation
							.forEach(ppOrderAttribute -> new PPOrderProductAttributeBL().putOrMergeInMap(attributesMap, ppOrderAttribute));

					// verify
					try
					{
						verifyInvariants(attribute, attributesMap, actualValue, expectedValue);
						attributesMap.clear();
					}
					catch (final AssertionError e)
					{
						fail("Failed with permulation=" + permulation + ":" + e.getMessage());
					}
				});

	}

	/**
	 * Called by {@link #performTestWithThreeAttributes(I_M_Attribute, I_PP_Order_ProductAttribute, I_PP_Order_ProductAttribute, I_PP_Order_ProductAttribute, Function, Matcher)} to verify each single result.
	 *
	 * @param attribute
	 * @param attributesMap
	 * @param actualValue
	 * @param expectedValue
	 */
	private <T> void verifyInvariants(final I_M_Attribute attribute,
			final Map<Integer, PPOrderProductAttributeBL.AttributesWithValues> attributesMap,
			final Function<PPOrderProductAttributeBL.AttributesWithValues, T> actualValue,
			final Matcher<?> expectedValue)
	{
		final AttributesWithValues attributeFromMap = attributesMap.get(attribute.getM_Attribute_ID());
		if (attributeFromMap == null)
		{
			if (!expectedValue.equals(nullValue()))
			{
				fail("expected=" + expectedValue.toString() + "; actual=null");
			}
		}
		else
		{
			final Object actual = actualValue.apply(attributeFromMap);
			assertTrue("expected=" + expectedValue.toString() + "; actual=" + actual, expectedValue.matches(actual));
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

}

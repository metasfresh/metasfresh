package de.metas.handlingunits.attribute.strategy.impl;

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


import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.impl.NullAttributeStorage;
import de.metas.handlingunits.attribute.strategy.IAttributeSplitResult;
import de.metas.util.Services;

public class LinearDistributionAttributeSplitterStrategyTest
{
	private LinearDistributionAttributeSplitterStrategy splitter;
	private I_M_Attribute attribute;

	@Before
	public void beforeTest()
	{
		//
		// Create a dummy attribute
		AdempiereTestHelper.get().init(); // NOTE: the only reason why we init adempiere is because we need to create 1 fucking attribute
		attribute = InterfaceWrapperHelper.create(Env.getCtx(), I_M_Attribute.class, ITrx.TRXNAME_None);
		InterfaceWrapperHelper.save(attribute);

		//
		// Create splitter strategy
		splitter = new LinearDistributionAttributeSplitterStrategy();
	}

	/**
	 * Test liniar distribution of value 100 to 33 children
	 */
	@Test
	public void test_100_divideTo_33()
	{
		test(
				new BigDecimal("100"), // ValueToSplit
				33, // childrenCount
				Services.get(IAttributesBL.class).getMathContext(attribute) // MathContext
		);
	}

	/**
	 * Test liniar distribution of value 7 to 3 children
	 */
	@Test
	public void test_7_divideTo_3()
	{
		test(
				new BigDecimal("7"), // ValueToSplit
				3, // childrenCount
				Services.get(IAttributesBL.class).getMathContext(attribute) // MathContext
		);
	}

	/**
	 * Test liniar distribution of value 7.7777777777777 to 3 children
	 */
	@Test
	public void test_7_7777777777777_divideTo_3()
	{
		test(
				new BigDecimal("7.7777777777777"), // ValueToSplit
				3, // childrenCount
				Services.get(IAttributesBL.class).getMathContext(attribute) // MathContext
		);
	}

	private void test(final BigDecimal valueToSplit, final int childrenCount, final MathContext mathContext)
	{
		final String info = "ValueToSplit=" + valueToSplit + ", childrenCount=" + childrenCount + ", mathContext=" + mathContext;
		final int precision = mathContext.getPrecision();

		final BigDecimal splitValueExpected = valueToSplit.divide(BigDecimal.valueOf(childrenCount), mathContext);

		// Delta: tolerated difference between calculated split value and actual split value
		// i.e. 1^(-precision) = 1/(1^precision) ... because BigDecimal does not support negative powers
		final BigDecimal splitValueExpectedDelta = BigDecimal.ONE.divide(BigDecimal.ONE.pow(precision));

		// Example: For ValueToSplit=100, childrenCount=33, precision=2
		// splitValueExpected = 0.03
		// splitValueExpectedDelta = 1^(-2) = 0.01

		final MutableAttributeSplitRequest request = createRequest(valueToSplit, childrenCount);

		BigDecimal splitValueSUM = BigDecimal.ZERO;
		for (int index = 0; index < childrenCount; index++)
		{
			//
			// Update request current index
			request.setAttributeStorageCurrent(request.getAttributeStorages().get(index));
			request.setAttributeStorageCurrentIndex(index);

			//
			// Execute splitting
			final IAttributeSplitResult result = splitter.split(request);

			//
			// Get and check split value
			final BigDecimal splitValue = (BigDecimal)result.getSplitValue();
			assertEquals("Invalid split value on index=" + index + "\n" + info,
					splitValueExpected, // expected
					splitValue, // actual
					splitValueExpectedDelta // delta
			);

			//
			// Update SUM of split values
			splitValueSUM = splitValueSUM.add(splitValue);

			//
			// Update: request's ValueToSplit = last split remaining value
			final BigDecimal remainingValue = (BigDecimal)result.getRemainingValue();
			final BigDecimal remainingValueExpected = valueToSplit.subtract(splitValueSUM);
			assertEquals("Invalid remaining value on index=" + index + "\n" + info,
					remainingValueExpected, // expected
					remainingValue, // actual
					BigDecimal.ZERO // delta=exact
			);
			request.setValueToSplit(remainingValue);
		}

		//
		// Assume: sum of all split values shall be the same as initial value to split
		Assert.assertThat("Invalid splitValues SUM" + "\n" + info,
				splitValueSUM, // actual
				Matchers.comparesEqualTo(valueToSplit) // expected
		);
	}

	private MutableAttributeSplitRequest createRequest(final Object value, final int childrenCount)
	{
		final IAttributeStorage parentAttributeStorage = NullAttributeStorage.instance;
		final List<IAttributeStorage> attributeStorages = new ArrayList<IAttributeStorage>(childrenCount);
		for (int i = 0; i < childrenCount; i++)
		{
			attributeStorages.add(NullAttributeStorage.instance);
		}

		final MutableAttributeSplitRequest request = new MutableAttributeSplitRequest(parentAttributeStorage, attributeStorages, attribute);
		request.setValueInitial(value);
		request.setValueToSplit(value);

		return request;
	}

	public static void assertEquals(final String message,
			final BigDecimal expected,
			final BigDecimal actual,
			final BigDecimal delta)
	{
		if (expected == actual)
		{
			return;
		}

		if (expected.compareTo(actual) == 0)
		{
			return;
		}

		final BigDecimal deltaActual = expected.subtract(actual).abs();
		if (deltaActual.compareTo(delta) <= 0)
		{
			return;
		}

		Assert.fail(
				(message == null ? "" : message)
						+ "\nExpected=<" + expected + ">"
						+ "\nActual=<" + actual + ">"
						+ "\nDelta=<" + delta + ">"
				);
	}
}

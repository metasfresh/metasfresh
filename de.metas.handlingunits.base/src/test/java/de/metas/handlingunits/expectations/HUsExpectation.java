package de.metas.handlingunits.expectations;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.ArrayList;
import java.util.List;

import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.compiere.util.Env;
import org.junit.Assert;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;

/**
 * Allows a tester to declare HU related expectations.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUsExpectation extends AbstractHUExpectation<Object>
{
	private final List<HUExpectation<HUsExpectation>> expectations = new ArrayList<>();

	public HUsExpectation()
	{
		super(
				(Object)null // parentExpectation
		);

		setContext(PlainContextAware.newOutOfTrx(Env.getCtx()));
	}

	public HUsExpectation assertExpected(final List<I_M_HU> hus)
	{
		final String message = "";
		return assertExpected(message, hus);
	}

	public HUsExpectation assertExpected(final String message, final List<I_M_HU> hus)
	{
		final int count = hus.size();
		final int expectedCount = expectations.size();

		Assert.assertEquals(message + " lines count", expectedCount, count);

		for (int i = 0; i < count; i++)
		{
			final I_M_HU hu = hus.get(i);

			final String prefix = (message == null ? "" : message)
					+ "\n HU Index: " + (i + 1) + "/" + count;

			expectations.get(i).assertExpected(prefix, hu);
		}

		return this;
	}

	public List<I_M_HU> createHUs()
	{
		if (expectations == null || expectations.isEmpty())
		{
			Assert.fail("Cannot create HUs because there are no expectations defined");
		}

		//
		// Create HUs
		final List<I_M_HU> result = new ArrayList<>();
		for (final HUExpectation<HUsExpectation> expectation : expectations)
		{
			final I_M_HU_Item parentHUItem = null; // no parent
			final I_M_HU hu = expectation.createHU(parentHUItem);
			Check.assumeNotNull(hu, "hu not null");
			result.add(hu);
		}

		//
		// Make sure HUs were created as expected
		assertExpected("Internal error: HUs were not created as expected", result);

		return result;
	}

	public HUExpectation<HUsExpectation> newHUExpectation()
	{
		final HUExpectation<HUsExpectation> expectation = new HUExpectation<>(this);
		expectations.add(expectation);
		return expectation;
	}

	public HUExpectation<HUsExpectation> huExpectation(final int index)
	{
		return expectations.get(index);
	}

	public List<I_M_HU> getCapturedHUs()
	{
		final List<I_M_HU> hus = new ArrayList<>();
		for (final HUExpectation<HUsExpectation> expectation : expectations)
		{
			final I_M_HU hu = expectation.getCapturedHU();
			hus.add(hu);
		}

		return hus;
	}
}

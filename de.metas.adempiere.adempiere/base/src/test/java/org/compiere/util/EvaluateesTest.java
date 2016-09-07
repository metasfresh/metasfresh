package org.compiere.util;

import java.util.Arrays;
import java.util.List;

import org.compiere.util.Evaluatees.CompositeEvaluatee;
import org.junit.Assert;
import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class EvaluateesTest
{
	@Test
	public void test_composeNotNulls()
	{
		final Evaluatee e1 = dummyEvaluatee("e1");
		final Evaluatee e2 = dummyEvaluatee("e2");
		final Evaluatee e3 = dummyEvaluatee("e3");

		test_composeNotNulls(Arrays.asList(e1), e1);
		test_composeNotNulls(Arrays.asList(e1), null, e1, null);
		test_composeNotNulls(Arrays.asList(e1, e2, e3), e1, e2, e3);
		test_composeNotNulls(Arrays.asList(e1, e2, e3), null, e1, null, e2, null, e3, null, null, null, null);
	}

	private void test_composeNotNulls(final List<Evaluatee> expectedSources, final Evaluatee... evaluatees)
	{
		final CompositeEvaluatee composite = (CompositeEvaluatee)Evaluatees.composeNotNulls(evaluatees);
		final List<Evaluatee> actualSources = composite.getSources();
		Assert.assertEquals(expectedSources, actualSources);
	}

	private static Evaluatee dummyEvaluatee(final String name)
	{
		return new Evaluatee()
		{
			@Override
			public String toString()
			{
				return name;
			}

			@Override
			public String get_ValueAsString(final String variableName)
			{
				return null;
			}
		};
	}

}

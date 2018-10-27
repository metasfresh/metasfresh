package de.metas.util.collections;

/*
 * #%L
 * de.metas.util
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


import org.junit.Assert;
import org.junit.Test;

import de.metas.util.collections.CompositePredicate;

public class CompositePredicateTest
{
	@Test(expected = Exception.class)
	public void testEmpty()
	{
		final CompositePredicate<Object> predicate = new CompositePredicate<Object>();

		// shall throw exception because no predicate was added
		predicate.test(new Object());
	}

	@Test
	public void testAnd_AllReturnTrue()
	{
		final CompositePredicate<Object> predicate = new CompositePredicate<Object>();
		predicate.setAnd(true);

		predicate.addPredicate(new MockedPredicate<Object>(true));
		predicate.addPredicate(new MockedPredicate<Object>(true));
		predicate.addPredicate(new MockedPredicate<Object>(true));

		Assert.assertEquals("Invalid result", true, predicate.test(new Object()));
	}

	@Test
	public void testAnd_NotAllReturnTrue()
	{
		final CompositePredicate<Object> predicate = new CompositePredicate<Object>();
		predicate.setAnd(true);

		predicate.addPredicate(new MockedPredicate<Object>(true));
		predicate.addPredicate(new MockedPredicate<Object>(false));
		predicate.addPredicate(new MockedPredicate<Object>(true));

		Assert.assertEquals("Invalid result", false, predicate.test(new Object()));
	}

	@Test
	public void testOr_AllReturnTrue()
	{
		final CompositePredicate<Object> predicate = new CompositePredicate<Object>();
		predicate.setAnd(false);

		predicate.addPredicate(new MockedPredicate<Object>(true));
		predicate.addPredicate(new MockedPredicate<Object>(true));
		predicate.addPredicate(new MockedPredicate<Object>(true));

		Assert.assertEquals("Invalid result", true, predicate.test(new Object()));
	}

	@Test
	public void testOr_NotAllReturnTrue()
	{
		final CompositePredicate<Object> predicate = new CompositePredicate<Object>();
		predicate.setAnd(false);

		predicate.addPredicate(new MockedPredicate<Object>(true));
		predicate.addPredicate(new MockedPredicate<Object>(false));
		predicate.addPredicate(new MockedPredicate<Object>(true));

		Assert.assertEquals("Invalid result", true, predicate.test(new Object()));
	}

}

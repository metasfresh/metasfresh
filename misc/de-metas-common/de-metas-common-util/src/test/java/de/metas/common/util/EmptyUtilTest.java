/*
 * #%L
 * de-metas-common-util
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class EmptyUtilTest
{
	@Test
	public void test_isEmpty_collection()
	{
		test_isEmpty_collection(true, null);
		test_isEmpty_collection(true, Collections.emptyList());
		test_isEmpty_collection(true, Collections.emptySet());
		test_isEmpty_collection(true, new ArrayList<Object>());
		test_isEmpty_collection(true, new HashSet<Object>());

		test_isEmpty_collection(false, Arrays.asList("1"));

		final List<Object> listNotEmpty = new ArrayList<Object>();
		listNotEmpty.add("one element");
		test_isEmpty_collection(false, listNotEmpty);
	}

	public void test_isEmpty_collection(final boolean emptyExpected, final Collection<?> collection)
	{
		final boolean emptyActual = EmptyUtil.isEmpty(collection);
		Assert.assertEquals("Invalid isEmpty result for collection: " + collection, emptyExpected, emptyActual);
	}
}

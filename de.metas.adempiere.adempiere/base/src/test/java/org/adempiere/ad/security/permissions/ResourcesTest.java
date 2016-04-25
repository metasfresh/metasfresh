package org.adempiere.ad.security.permissions;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.HashSet;
import java.util.LinkedHashMap;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

public class ResourcesTest
{
	@Before
	public void init()
	{
		// needed for OrgResource.toString()
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testHashCodeAndEquals_ElementResource()
	{
		testHashCodeAndEquals(new Supplier<Resource>()
		{
			int nextId = 1;

			@Override
			public Resource get()
			{
				return ElementResource.of("AD_Window", nextId++);
			}
		});
	}

	@Test
	public void testHashCodeAndEquals_OrgResource()
	{
		testHashCodeAndEquals(new Supplier<Resource>()
		{
			int nextId = 1;

			@Override
			public Resource get()
			{
				final int adClientId = 0;
				final int adOrgId = nextId++;
				return OrgResource.of(adClientId, adOrgId);
			}
		});
	}

	@Test
	public void testHashCodeAndEquals_TableResource()
	{
		testHashCodeAndEquals(new Supplier<Resource>()
		{
			int nextId = 1;

			@Override
			public Resource get()
			{
				return TableResource.ofAD_Table_ID(nextId++);
			}
		});
	}

	@Test
	public void testHashCodeAndEquals_TableColumnResource()
	{
		testHashCodeAndEquals(new Supplier<Resource>()
		{
			int nextId = 1;

			@Override
			public Resource get()
			{
				int adTableId = 1;
				int adColumnId = nextId++;
				return TableColumnResource.of(adTableId, adColumnId);
			}
		});
	}

	@Test
	public void testHashCodeAndEquals_TableRecordResource()
	{
		testHashCodeAndEquals(new Supplier<Resource>()
		{
			int nextId = 1;

			@Override
			public Resource get()
			{
				int adTableId = 1;
				int recordId = nextId++;
				return TableRecordResource.of(adTableId, recordId);
			}
		});
	}

	/**
	 * Make sure adding multiple permissions work.
	 * Mainly this is to prevent a fucked up bug that we had: the {@link ElementResource} hashcode was not correctly build,
	 * and {@link ImmutableSet#copyOf(java.util.Collection)} was throwing duplicate keys exception.
	 */

	public void testHashCodeAndEquals(final Supplier<? extends Resource> newResourceSupplier)
	{
		final HashSet<Resource> resources_hashSet = new HashSet<>();
		final LinkedHashMap<Resource, Boolean> resources_map = new LinkedHashMap<>();

		// NOTE: we need a big amount of items to reproduce this issue all the time.
		for (int i = 1; i <= 1000; i++)
		{
			final Resource resource = newResourceSupplier.get();
			Assert.assertTrue("Resource not duplicate: " + resource, resources_hashSet.add(resource));

			Assert.assertNull("Resource does not already exist: " + resource, resources_map.put(resource, true));
		}

		// test covert it to immutable map
		// before this was failing because a bug in EqualsBuilder.
		ImmutableMap.copyOf(resources_map);
	}
}

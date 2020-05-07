package org.adempiere.ad.persistence;

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


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.persistence.EntityTypesCache.EntityTypeEntry;
import org.compiere.model.I_AD_EntityType;
import org.compiere.util.CacheMgt;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Ignore;

/**
 * Helper class used to test {@link TableModelClassLoader}.
 *
 * Use it to make sure the right model classes are loaded by table name.
 *
 * @author tsa
 *
 */
@Ignore
public class TableModelClassLoaderTester
{
	private Map<String, String> tableName2EntityType = new HashMap<>();
	private TableModelClassLoader mockedModelClassLoader = new TableModelClassLoader()
	{
		@Override
		String getEntityTypeForTableName(String tableName)
		{
			return tableName2EntityType.get(tableName);
		};
	};

	private Map<String, EntityTypeEntry> entityTypeEntries = new HashMap<>();
	private IEntityTypesCache entityTypesCache = new EntityTypesCache()
	{
		@Override
		Map<String, EntityTypeEntry> retrieveEntityTypeEntries()
		{
			return entityTypeEntries;
		};
	};

	public TableModelClassLoaderTester()
	{
		super();
		mockedModelClassLoader.setEntityTypesCache(entityTypesCache);
	}

	public TableModelClassLoaderTester setEntityTypeModelPackage(final String entityType, final String modelPackage)
	{
		final EntityTypeEntry entry = EntityTypeEntry.builder()
				.setEntityType(entityType)
				.setModelPackage(modelPackage)
				.build();
		entityTypeEntries.put(entityType, entry);
		return this;
	}

	/**
	 * Sets the entity type to be considered for given table name.
	 *
	 * @param tableName
	 * @param entityType
	 */
	public TableModelClassLoaderTester setTableNameEntityType(final String tableName, final String entityType)
	{
		tableName2EntityType.put(tableName, entityType);
		return this;
	}

	/**
	 * @return mocked {@link TableModelClassLoader}
	 */
	public TableModelClassLoader getTableModelClassLoader()
	{
		return mockedModelClassLoader;
	}

	/**
	 * Asserts the expected class is loaded for given tableName.
	 *
	 * @param tableName
	 * @param expectedClass
	 */
	public TableModelClassLoaderTester assertClass(final String tableName, final Class<?> expectedClass)
	{
		final Class<?> clazz = mockedModelClassLoader.getClass(tableName);
		Assert.assertSame("Invalid class was loaded for " + tableName,
				expectedClass,
				clazz);

		return this;
	}

	public TableModelClassLoaderTester assertEntityTypeExists(final String entityType)
	{
		final List<String> entityTypeNames = entityTypesCache.getEntityTypeNames();
		Assert.assertThat(entityTypeNames, Matchers.hasItem(entityType));
		return this;
	}

	public TableModelClassLoaderTester assertEntityTypeNotExists(final String entityType)
	{
		final List<String> entityTypeNames = entityTypesCache.getEntityTypeNames();
		Assert.assertThat(entityTypeNames, Matchers.not(Matchers.hasItem(entityType)));
		return this;
	}

	/**
	 * Reset cache of currently loaded entity types.
	 */
	public TableModelClassLoaderTester cacheReset()
	{
		CacheMgt.get().reset(I_AD_EntityType.Table_Name);
		return this;
	}

}

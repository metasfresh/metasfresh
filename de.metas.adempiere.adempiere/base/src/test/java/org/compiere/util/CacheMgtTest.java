package org.compiere.util;

import org.adempiere.test.AdempiereTestHelper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

public class CacheMgtTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@After
	public void clearCache()
	{
		CacheMgt.get().clear();
	}

	@Test
	public void resetByTableNameAndRecordId()
	{
		final AssertTableName assertTable1 = new AssertTableName("Table1");
		CacheMgt.get().register(assertTable1);
		CacheMgt.get().register(new CCache<>("Table2", 1));

		CacheMgt.get().reset("Table2", 100);
		assertTable1.assertResetForRecordIdWasNotCalled();
		CacheMgt.get().reset("Table1", 100);
		assertTable1.assertResetForRecordIdWasCalled();
	}

	@Test
	public void resetByTableNameAndRecordId_CCache_alwaysCalled()
	{
		final CCache_resetForRecordId_Mocked<Object, Object> cache = new CCache_resetForRecordId_Mocked<>("Table1", 1);
		CacheMgt.get().register(cache);
		CacheMgt.get().register(new CCache<>("Table2", 1));

		CacheMgt.get().reset("Table2", 100);
		cache.assertResetForRecordIdWasCalled();
	}

	private static class AssertTableName implements ITableAwareCacheInterface
	{
		private final String tableName;
		private boolean resetForRecordIdWasCalled;

		private AssertTableName(final String tableName)
		{
			this.tableName = tableName;
		}

		@Override
		public int resetForRecordId(final String tableName, final Object key)
		{
			Assert.assertEquals("resetForRecordId shall not be invoked for table name", this.tableName, tableName);

			resetForRecordIdWasCalled = true;
			return 1;
		}

		public void assertResetForRecordIdWasCalled()
		{
			Assert.assertTrue("resetForRecordIdWasCalled", resetForRecordIdWasCalled);
		}

		public void assertResetForRecordIdWasNotCalled()
		{
			Assert.assertFalse("resetForRecordIdWasCalled", resetForRecordIdWasCalled);
		}

		@Override
		public int reset()
		{
			return 1;
		}

		@Override
		public int size()
		{
			return 1;
		}

		@Override
		public String getName()
		{
			return getTableName();
		}

		@Override
		public String getTableName()
		{
			return tableName;
		}
	}

	private static final class CCache_resetForRecordId_Mocked<K, V> extends CCache<K, V>
	{

		private boolean resetForRecordIdWasCalled;

		public CCache_resetForRecordId_Mocked(final String name, final int initialCapacity)
		{
			super(name, initialCapacity);
		}

		@Override
		public int resetForRecordId(final String tableName, final Object key)
		{
			final int count = super.resetForRecordId(tableName, key);

			resetForRecordIdWasCalled = true;

			return count;
		}

		public void assertResetForRecordIdWasCalled()
		{
			Assert.assertTrue("resetForRecordIdWasCalled", resetForRecordIdWasCalled);
		}
	}
}

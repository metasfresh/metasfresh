package de.metas.cache;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.LinkedHashSet;
import java.util.Set;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.cache.CacheMgt.ResetMode;
import de.metas.cache.model.CacheInvalidateMultiRequest;
import de.metas.cache.model.CacheInvalidateRequest;

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

	@Test
	public void resetByTableNameAndRecordId()
	{
		final CacheMgt cacheManager = CacheMgt.get();

		final AssertTableName assertTable1 = new AssertTableName("Table1");
		cacheManager.register(assertTable1);
		cacheManager.register(new CCache<>("Table2", 1));

		cacheManager.reset("Table2", 100);
		assertTable1.assertResetForRecordIdWasNotCalled();
		cacheManager.reset("Table1", 100);
		assertTable1.assertResetForRecordIdWasCalled();
	}

	@Test
	public void resetByTableNameAndRecordId_CCache_alwaysCalled()
	{
		final CacheMgt cacheManager = CacheMgt.get();

		final MockedCCache cache = MockedCCache.newForTableName("Table1");

		// NOTE: cache instances are automatically registered
		// cacheManager.register(cache);
		// cacheManager.register(new CCache<>("Table2", 1));

		cacheManager.reset("Table2", 100);
		cache.assertResetForRecordIdWasNotCalled();

		cacheManager.reset("Table1", 100);
		cache.assertResetForRecordIdWasCalled();
	}

	@Test
	public void resetRootAndChildRecord()
	{
		final CacheMgt cacheManager = CacheMgt.get();

		final MockedCCache invoiceCache = MockedCCache.newForTableName("C_Invoice");
		final MockedCCache invoiceLineCache = MockedCCache.newForTableName("C_InvoiceLine");

		invoiceCache.assertResetForRecordIdWasNotCalled();
		invoiceLineCache.assertResetForRecordIdWasNotCalled();

		final CacheInvalidateMultiRequest request = CacheInvalidateMultiRequest.of(CacheInvalidateRequest.builder()
				.rootRecord("C_Invoice", 1)
				.childRecord("C_InvoiceLine", 2)
				.build());
		cacheManager.reset(request, ResetMode.LOCAL);

		invoiceCache.assertRecordInvalidated(TableRecordReference.of("C_Invoice", 1));
		invoiceLineCache.assertRecordInvalidated(TableRecordReference.of("C_InvoiceLine", 2));
	}

	private static class AssertTableName implements CacheInterface
	{
		private final String tableName;
		private boolean resetForRecordIdWasCalled;

		private AssertTableName(final String tableName)
		{
			this.tableName = tableName;
		}

		@Override
		public long getCacheId()
		{
			return 1;
		}

		@Override
		public long resetForRecordId(final TableRecordReference recordRef)
		{
			Assert.assertEquals("resetForRecordId shall not be invoked for table name", tableName, tableName);

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
		public long reset()
		{
			return 1;
		}

		@Override
		public long size()
		{
			return 1;
		}

		@Override
		public Set<CacheLabel> getLabels()
		{
			return ImmutableSet.of(CacheLabel.ofTableName(tableName));
		}
	}

	private static final class MockedCCache extends CCache<Object, Object>
	{
		public static MockedCCache newForTableName(final String tableName)
		{
			return new MockedCCache(tableName);
		}

		private final LinkedHashSet<TableRecordReference> resetRecords = new LinkedHashSet<>();

		private MockedCCache(final String tableName)
		{
			super(tableName, 1);
		}

		@Override
		public long resetForRecordId(final TableRecordReference recordRef)
		{
			final long count = super.resetForRecordId(recordRef);

			resetRecords.add(recordRef);

			return count;
		}

		public void assertResetForRecordIdWasNotCalled()
		{
			assertThat(resetRecords).as("reset record method was called").isEmpty();
		}

		public void assertResetForRecordIdWasCalled()
		{
			assertThat(resetRecords).as("reset record method was NOT called").isNotEmpty();
		}

		public void assertRecordInvalidated(final TableRecordReference recordRef)
		{
			assertThat(resetRecords).as("reset record was called for given record").contains(recordRef);
		}
	}
}

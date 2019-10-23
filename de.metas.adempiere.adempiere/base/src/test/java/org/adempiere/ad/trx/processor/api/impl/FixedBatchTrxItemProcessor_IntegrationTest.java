package org.adempiere.ad.trx.processor.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrxManager;
import org.adempiere.ad.trx.api.impl.PredictableTrxNameGenerator;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.TrxItemProcessorAdapter;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.metas.util.Services;

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

public class FixedBatchTrxItemProcessor_IntegrationTest
{
	private PlainTrxManager trxManager;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		trxManager = (PlainTrxManager)Services.get(ITrxManager.class);

		// Configure a predictable trxName generator.
		// We rely on this in tests, to check which item in which transaction was processed.
		final PredictableTrxNameGenerator trxNameGenerator = new PredictableTrxNameGenerator("trx", 1);
		trxManager.setTrxNameGenerator(trxNameGenerator);

		// NOTE: In production code, the commit and rollback silently ignores if transaction was never started before.
		// It seems that our trx TrxItemExecutor is relying on that feature.
		trxManager.setFailCommitIfTrxNotStarted(false);
		trxManager.setFailRollbackIfTrxNotStarted(false);
	}

	@Test
	public void test_10items_3itemsPerBatch()
	{
		final List<Item> items = Arrays.asList(
				new Item(1, "trx1"), new Item(2, "trx1"), new Item(3, "trx1")
				, new Item(4, "trx2"), new Item(5, "trx2"), new Item(6, "trx2")
				, new Item(7, "trx3"), new Item(8, "trx3"), new Item(9, "trx3")
				, new Item(10, "trx4")
				);

		final Result result = Services.get(ITrxItemProcessorExecutorService.class)
				.<Item, Result> createExecutor()
				.setContext(Env.getCtx())
				.setItemsPerBatch(3)
				.setProcessor(new TrxItemProcessorAdapter<Item, Result>()
				{
					private final Result result = new Result();

					@Override
					public void process(final Item item) throws Exception
					{
						Assert.assertEquals("TrxName for " + item, item.getExpectedTrxName(), getTrxName());
						result.add(item);
					}

					@Override
					public Result getResult()
					{
						return result;
					}
				})
				.process(items.iterator());

		Assert.assertEquals("Result item's count", items.size(), result.getItemsCount());
	}

	private static class Item
	{
		private final String expectedTrxName;
		private final String name;

		public Item(final int index, final String expectedTrxName)
		{
			super();
			name = String.valueOf(index);
			this.expectedTrxName = expectedTrxName;
		}

		@Override
		public String toString()
		{
			return "Item[" + name + ", expectedTrxName=" + expectedTrxName + "]";
		}

		public String getExpectedTrxName()
		{
			return expectedTrxName;
		}
	}

	private static class Result
	{
		private final List<Item> items = new ArrayList<>();

		public void add(final Item item)
		{
			items.add(item);
		}

		public int getItemsCount()
		{
			return items.size();
		}
	}
}

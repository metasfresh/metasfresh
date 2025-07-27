package org.adempiere.ad.trx.processor.api.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.impl.PlainTrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;

import java.util.ArrayList;
import java.util.List;

/**
 * A mocked processor for testing.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
@Disabled
public class MockedItemProcessor implements ITrxItemChunkProcessor<Item, ItemProcessorResult>
{
	//
	// Services
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	//
	// Parameters
	private ITrxItemProcessorContext processorCtx;
	//
	private final List<Item> errorItems = new ArrayList<Item>();
	private final List<String> errorOnNewChunkGroupKeys = new ArrayList<String>();
	private final List<String> errorOnChunkCompleteGroupKeys = new ArrayList<String>();
	private final List<String> errorOnChunkCancelGroupKeys = new ArrayList<String>();
	private Boolean expectTrxSavepoints = null;

	//
	// State
	private ItemProcessorResult result = null;
	private AggregatedItem currentAggregatedItem = null;

	public MockedItemProcessor()
	{
		super();
		reset();
	}

	public void reset()
	{
		processorCtx = null;
		result = new ItemProcessorResult();
		currentAggregatedItem = null;
	}

	@Override
	public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	@Override
	public void process(final Item item)
	{
		item.setProcessed();

		Assertions.assertNotNull(item, "item shall not be null");
		Assertions.assertNotNull(currentAggregatedItem, "currentAggregatedItem shall be initialized");

		assertThreadInheritedTrxSet();

		if (errorItems.contains(item))
		{
			throw new RuntimeException("Test exception for item: " + item);
		}
		currentAggregatedItem.addItem(item);
	}

	@Override
	public ItemProcessorResult getResult()
	{
		return result;
	}

	@Override
	public boolean isSameChunk(final Item item)
	{
		assertThreadInheritedTrxSet();

		if (currentAggregatedItem == null)
		{
			return false;
		}

		if (!currentAggregatedItem.getGroupKey().equals(item.getGroupKey()))
		{
			return false;
		}

		return true;
	}

	@Override
	public void newChunk(final Item item)
	{
		Assertions.assertNull(currentAggregatedItem, "currentAggregatedItem shall be null at this point");
		Assertions.assertNotNull(processorCtx, "processorCtx shall be set at this point");
		Assertions.assertNotNull(processorCtx.getTrx(), "processorCtx transaction shall be set at this point");
		assertThreadInheritedTrxSet();

		final String groupKey = item.getGroupKey();
		if (errorOnNewChunkGroupKeys.contains(groupKey))
		{
			throw new RuntimeException("Test exception on newChunk for group: " + groupKey);
		}
		currentAggregatedItem = new AggregatedItem(groupKey, processorCtx.getTrx().getTrxName());
	}

	@Override
	public void completeChunk()
	{
		Assertions.assertNotNull(currentAggregatedItem, "currentAggregatedItem shall not be null at this point");
		assertThreadInheritedTrxSet();

		final String groupKey = currentAggregatedItem.getGroupKey();
		if (errorOnChunkCompleteGroupKeys.contains(groupKey))
		{
			throw new RuntimeException("Test exception on completeChunk for group: " + groupKey);
		}

		result.addAggregatedItem(currentAggregatedItem);
		currentAggregatedItem = null;
	}

	@Override
	public void cancelChunk()
	{
		Assertions.assertNotNull(currentAggregatedItem, "currentAggregatedItem shall not be null at this point");
		assertThreadInheritedTrxSet();

		final String groupKey = currentAggregatedItem.getGroupKey();
		if (errorOnChunkCancelGroupKeys.contains(groupKey))
		{
			// If we need to throw exception on cancel it means entire processing will fail
			// so there is no point to set "currentAggregatedItem" to null
			// More, we want to let it as is because it will be investigated in tests
			// currentAggregatedItem = null;

			throw new RuntimeException("Test exception on cancelChunk for group: " + groupKey);
		}

		currentAggregatedItem = null;
	}

	public void setThrowExceptionIfItem(final Item item)
	{
		Check.assumeNotNull(item, "item not null");
		errorItems.add(item);
	}

	public void setThrowExceptionOnCompleteChunk(final String groupKey)
	{
		Check.assumeNotEmpty(groupKey, "groupKey not null");
		errorOnChunkCompleteGroupKeys.add(groupKey);
	}

	public void setThrowExceptionOnCancelChunk(final String groupKey)
	{
		Check.assumeNotEmpty(groupKey, "groupKey not null");
		errorOnChunkCancelGroupKeys.add(groupKey);
	}

	public void setThrowExceptionOnNewChunk(final String groupKey)
	{
		Check.assumeNotEmpty(groupKey, "groupKey not null");
		errorOnNewChunkGroupKeys.add(groupKey);
	}

	public void setExpectTrxSavepoints(final boolean expectTrxSavepoints)
	{
		this.expectTrxSavepoints = expectTrxSavepoints;
	}

	private void assertThreadInheritedTrxSet()
	{
		final String trxName = trxManager.getThreadInheritedTrxName();
		Assertions.assertTrue(!trxManager.isNull(trxName), "Thread inherited transaction shall be set at this point");

		Assertions.assertEquals(processorCtx.getTrxName(), trxName, "Thread inherited transaction shall match context transaction");

		if (expectTrxSavepoints != null)
		{
			final PlainTrx trx = getTrx(PlainTrx.class);
			Assertions.assertEquals(expectTrxSavepoints, trx.hasActiveSavepoints(), "Active savepoints for " + trx);
		}
	}

	public final <T extends PlainTrx> T getTrx(final Class<T> trxClass)
	{
		@SuppressWarnings("unchecked") final T trx = (T)processorCtx.getTrx();
		return trx;
	}
}

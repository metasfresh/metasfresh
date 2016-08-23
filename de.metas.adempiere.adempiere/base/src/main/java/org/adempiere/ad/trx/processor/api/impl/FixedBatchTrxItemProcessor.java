package org.adempiere.ad.trx.processor.api.impl;

import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.util.Check;

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

/**
 * Wraps an {@link ITrxItemProcessor} and groups the items in batches of a given fixed maximum size.
 * If the inner processor is a {@link ITrxItemChunkProcessor}, then it can still have chunks
 * with less than the maximum size, but this wrapper will cause a new chunk to be created when the maximum size is reached.
 * <p>
 * Use {@link org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder#setItemsPerBatch(int)} in order to get an instance.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @param <IT> item type
 * @param <RT> result type
 */
class FixedBatchTrxItemProcessor<IT, RT> implements ITrxItemChunkProcessor<IT, RT>
{
	/**
	 *
	 * @param processor the processor to be wrapped into the new instance.
	 * @param itemsPerBatch the maximum number of items per chunk. If less or equal zero, then the given <code>processor</code> is not wrapped but just returned as-is
	 *
	 * @return an instance of this class that wraps the given <code>processor</code> (if <code>itemsPerBatch>0</code>).
	 */
	public static <IT, RT> ITrxItemProcessor<IT, RT> of(final ITrxItemProcessor<IT, RT> processor, final int itemsPerBatch)
	{
		if (itemsPerBatch <= 0)
		{
			return processor;
		}
		// in case of itemsPerBatch == 1 we might return the given processor *if* that given processor is not a ITrxItem*Chunk*Processor.
		return new FixedBatchTrxItemProcessor<>(processor, itemsPerBatch);
	}

	// Parameters
	private final ITrxItemChunkProcessor<IT, RT> processor;
	private final boolean ignoreProcessorIsSameChunkMethod;
	private final int maxItemsPerBatch;

	// State
	private int itemsPerBatch = 0;

	private FixedBatchTrxItemProcessor(final ITrxItemProcessor<IT, RT> processor, final int itemsPerBatch)
	{
		super();
		Check.assumeNotNull(processor, "processor not null");
		this.processor = TrxItemProcessor2TrxItemChunkProcessorWrapper.wrapIfNeeded(processor);
		this.ignoreProcessorIsSameChunkMethod = (this.processor instanceof TrxItemProcessor2TrxItemChunkProcessorWrapper);

		Check.assume(itemsPerBatch > 0, "itemsPerBatch > 0");
		this.maxItemsPerBatch = itemsPerBatch;
	}

	@Override
	public String toString()
	{
		return "FixedBatchTrxItemProcessor["
				+ "itemsPerBatch=" + itemsPerBatch + "/" + maxItemsPerBatch
				+ ", " + processor
				+ "]";
	}

	@Override
	public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		processor.setTrxItemProcessorCtx(processorCtx);
	}

	@Override
	public RT getResult()
	{
		return processor.getResult();
	}

	@Override
	public void process(final IT item) throws Exception
	{
		itemsPerBatch++;
		processor.process(item);
	}

	@Override
	public boolean isSameChunk(final IT item)
	{
		// If we are about to exceed the maximum number of items per batch
		// => return false (we need a new chunk/batch)
		if (itemsPerBatch >= maxItemsPerBatch)
		{
			return false;
		}

		//
		// Ask processor is the item is on the same chunk, if this is allowed.
		if (!ignoreProcessorIsSameChunkMethod)
		{
			return processor.isSameChunk(item);
		}

		// Consider this item on same chunk
		return true;

	}

	@Override
	public void newChunk(final IT item)
	{
		itemsPerBatch = 0;
		processor.newChunk(item);
	}

	@Override
	public void completeChunk()
	{
		processor.completeChunk();
	}

	@Override
	public void cancelChunk()
	{
		processor.cancelChunk();
	}
}

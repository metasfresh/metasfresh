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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.spi.ITrxItemChunkProcessor;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.util.Check;

/**
 * Wraps an {@link ITrxItemProcessor} to {@link ITrxItemChunkProcessor}.
 *
 * @author tsa
 *
 * @param <IT> item type
 * @param <RT> result type
 */
/* package */class TrxItemProcessor2TrxItemChunkProcessorWrapper<IT, RT> implements ITrxItemChunkProcessor<IT, RT>
{
	public static final <IT, RT> ITrxItemChunkProcessor<IT, RT> wrapIfNeeded(final ITrxItemProcessor<IT, RT> processor)
	{
		if (processor instanceof ITrxItemChunkProcessor)
		{
			final ITrxItemChunkProcessor<IT, RT> chunkProcessor = (ITrxItemChunkProcessor<IT, RT>)processor;
			return chunkProcessor;
		}

		return new TrxItemProcessor2TrxItemChunkProcessorWrapper<>(processor);
	}

	private final ITrxItemProcessor<IT, RT> processor;

	private TrxItemProcessor2TrxItemChunkProcessorWrapper(final ITrxItemProcessor<IT, RT> processor)
	{
		super();
		Check.assumeNotNull(processor, "processor not null");
		this.processor = processor;
	}

	@Override
	public String toString()
	{
		return "TrxItemChunkProcessorWrapper[" + processor + "]";
	}

	@Override
	public void setTrxItemProcessorCtx(ITrxItemProcessorContext processorCtx)
	{
		processor.setTrxItemProcessorCtx(processorCtx);
	}

	@Override
	public void process(IT item) throws Exception
	{
		processor.process(item);
	}

	@Override
	public RT getResult()
	{
		return processor.getResult();
	}

	/**
	 * Always return <code>false</code>. Each item is a separated chunk
	 *
	 * @return <code>false</code>
	 */
	@Override
	public boolean isSameChunk(IT item)
	{
		return false;
	}

	@Override
	public void newChunk(IT item)
	{
		// nothing
	}

	@Override
	public void completeChunk()
	{
		// nothing
	}

	@Override
	public void cancelChunk()
	{
		// nothing
	}
};

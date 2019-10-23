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

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.util.api.IParams;

public class TrxItemProcessorExecutorService implements ITrxItemProcessorExecutorService
{
	@Override
	public ITrxItemProcessorContext createProcessorContext(final Properties ctx, final ITrx trx)
	{
		final IParams params = null;
		return createProcessorContext(ctx, trx, params);
	}

	@Override
	public ITrxItemProcessorContext createProcessorContext(final Properties ctx, final ITrx trx, final IParams params)
	{
		final TrxItemProcessorContext processorCtx = new TrxItemProcessorContext(ctx);
		processorCtx.setTrx(trx);
		processorCtx.setParams(params);
		return processorCtx;
	}

	@Override
	public <IT, RT> ITrxItemProcessorExecutor<IT, RT> createExecutor(final ITrxItemProcessorContext processorCtx, final ITrxItemProcessor<IT, RT> processor)
	{
		final ITrxItemExecutorBuilder<IT, RT> builder = createExecutor();
		return builder
				.setContext(processorCtx)
				.setProcessor(processor)
				.build();
	}

	@Override
	public <IT, RT> ITrxItemExecutorBuilder<IT, RT> createExecutor()
	{
		return new TrxItemExecutorBuilder<IT, RT>(this);
	}
}

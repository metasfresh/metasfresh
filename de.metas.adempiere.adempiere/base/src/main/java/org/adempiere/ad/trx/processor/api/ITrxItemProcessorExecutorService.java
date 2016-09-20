package org.adempiere.ad.trx.processor.api;

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

import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.util.ISingletonService;
import org.adempiere.util.api.IParams;

/**
 * Item processor executor service
 *
 * @author tsa
 *
 */
public interface ITrxItemProcessorExecutorService extends ISingletonService
{
	/**
	 * Creates context with <code>null</code> params.<br>
	 * Note: instead of using this method, you can also call {@link #createExecutor()} to get a builder and then call {@link ITrxItemExecutorBuilder#setContext(Properties, String)}.
	 *
	 * @param ctx
	 * @param trx
	 * @return context
	 */
	ITrxItemProcessorContext createProcessorContext(Properties ctx, ITrx trx);

	/**
	 * Creates context
	 *
	 * @param ctx
	 * @param trx
	 * @param params
	 * @return context
	 */
	ITrxItemProcessorContext createProcessorContext(Properties ctx, ITrx trx, IParams params);

	/**
	 * Creates executor for given <code>processor</code>, using the defaults declared in the constants of {@link ITrxItemProcessorExecutor}.
	 *
	 * @param processorCtx
	 * @param processor
	 * @return executor
	 */
	<IT, RT> ITrxItemProcessorExecutor<IT, RT> createExecutor(ITrxItemProcessorContext processorCtx, ITrxItemProcessor<IT, RT> processor);

	/**
	 * Creates an executor builder which will help you to configure and execute a given processor.
	 *
	 * @return builder
	 */
	<IT, RT> ITrxItemExecutorBuilder<IT, RT> createExecutor();
}

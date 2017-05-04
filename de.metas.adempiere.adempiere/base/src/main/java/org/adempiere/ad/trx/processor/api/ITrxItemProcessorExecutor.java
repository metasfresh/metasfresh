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

import java.util.Iterator;

import org.adempiere.ad.trx.processor.api.ITrxItemExecutorBuilder.OnItemErrorPolicy;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;

/**
 * {@link ITrxItemProcessor} executor. Ise {@link ITrxItemExecutorBuilder} to get yours.
 *
 * Implementations of this class is responsible with {@link ITrxItemProcessor} methods invocations, error handling and transaction management.
 *
 * @author tsa
 *
 * @param <IT> item type
 * @param <RT> result type
 */
public interface ITrxItemProcessorExecutor<IT, RT>
{
	/**
	 * Default exception handler used.
	 */
	ITrxItemExceptionHandler DEFAULT_ExceptionHandler = LoggerTrxItemExceptionHandler.instance;

	/**
	 * Default policy for the case that processing one item fails.
	 */
	OnItemErrorPolicy DEFAULT_OnItemErrorPolicy = OnItemErrorPolicy.CancelChunkAndRollBack;

	/**
	 * default: true - backward compatibility;
	 */
	boolean DEFAULT_UseTrxSavepoints = true;

	/**
	 * Process given items.
	 *
	 * @param items
	 * @return result
	 */
	RT execute(Iterator<? extends IT> items);

	/**
	 * Gets used item processor.
	 *
	 * @return item processor
	 */
	ITrxItemProcessor<IT, RT> getProcessor();

	/**
	 * Instead of setting the exception handler here, you can also use {@link ITrxItemExecutorBuilder#setExceptionHandler(ITrxItemExceptionHandler)}.
	 *
	 * @param trxItemExceptionHandler
	 * @deprecated please use {@link ITrxItemExecutorBuilder#setExceptionHandler(ITrxItemExceptionHandler)} instead.
	 */
	@Deprecated
	ITrxItemProcessorExecutor<IT, RT> setExceptionHandler(ITrxItemExceptionHandler trxItemExceptionHandler);

	/**
	 * See {@link ITrxItemExecutorBuilder#setUseTrxSavepoints(boolean)}.
	 *
	 * @param useTrxSavepoints
	 * @deprecated please use {@link ITrxItemExecutorBuilder#setUseTrxSavepoints(boolean)} instead.
	 */
	@Deprecated
	ITrxItemProcessorExecutor<IT, RT> setUseTrxSavepoints(boolean useTrxSavepoints);

}

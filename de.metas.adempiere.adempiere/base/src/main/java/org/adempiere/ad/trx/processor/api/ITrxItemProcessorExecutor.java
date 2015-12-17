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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;

import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;

/**
 * {@link ITrxItemProcessor} executor.
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
	ITrxItemExceptionHandler DEFAULT_ExceptionHandler = LogTrxItemExceptionHandler.instance;

	/**
	 * Sets exception handler to be used if processing fails.
	 * 
	 * If not configured then {@link #DEFAULT_ExceptionHandler} it's used.
	 * 
	 * @param exceptionHandler
	 * @return this
	 */
	ITrxItemProcessorExecutor<IT, RT> setExceptionHandler(ITrxItemExceptionHandler exceptionHandler);

	/**
	 * Sets if the executor shall use transaction savepoints internally.
	 * 
	 * @param useTrxSavepoints
	 */
	ITrxItemProcessorExecutor<IT, RT> setUseTrxSavepoints(final boolean useTrxSavepoints);

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

}

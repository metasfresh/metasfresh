package org.adempiere.ad.trx.processor.api;

/*
 * #%L
 * ADempiere ERP - Base
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
import java.util.Properties;

import org.adempiere.ad.trx.processor.api.ITrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;

/**
 * Helper interface which can assist you configure and execute an {@link ITrxItemProcessor}.
 * 
 * @author tsa
 *
 * @param <IT> input processing type
 * @param <RT> processing result type
 */
public interface ITrxItemExecutorBuilder<IT, RT>
{
	/**
	 * Runs the processor on given items and return the result
	 * 
	 * @param items
	 * @return result
	 */
	RT process(Iterator<? extends IT> items);

	/** Configures the context */
	ITrxItemExecutorBuilder<IT, RT> setContext(Properties ctx);

	/**
	 * Configures the context
	 * 
	 * @param ctx
	 * @param trxName use {@link org.adempiere.ad.trx.api.ITrx#TRXNAME_None} if you want each chunk to be processed in a single transaction.
	 */
	ITrxItemExecutorBuilder<IT, RT> setContext(Properties ctx, String trxName);

	/** Configures the processor to be used. Here you will inject your nice code ;) */
	ITrxItemExecutorBuilder<IT, RT> setProcessor(ITrxItemProcessor<IT, RT> processor);

	ITrxItemExecutorBuilder<IT, RT> setExceptionHandler(ITrxItemExceptionHandler exceptionHandler);
}
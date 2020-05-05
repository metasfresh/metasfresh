package org.adempiere.ad.trx.processor.spi;

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

/**
 * Simple item processor.
 *
 * Implementations of this interface are responsible with processing given item (see {@link #process(Object)}).
 * <p>
 * Hint: you might want to subclass {@link TrxItemProcessorAdapter} instead of directly implementing this interface.
 *
 * @author tsa
 *
 * @param <IT> input type
 * @param <RT> result type
 */
public interface ITrxItemProcessor<IT, RT>
{
	/**
	 * Called by API to set the initial running context or when running context changes.
	 *
	 * @param processorCtx
	 */
	void setTrxItemProcessorCtx(ITrxItemProcessorContext processorCtx);

	/**
	 * Process given item
	 *
	 * @param item
	 * @throws Exception on any error
	 */
	void process(IT item) throws Exception;

	/**
	 *
	 * @return current processing aggregated result
	 */
	RT getResult();
}

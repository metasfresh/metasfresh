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

/**
 * {@link ITrxItemProcessorExecutor}'s exception handler.
 *
 * In case any of these methods are throwing an exception then entire batch processing will stop and exception will pass-through.
 *
 * @author tsa
 *
 */
public interface ITrxItemExceptionHandler
{
	/**
	 * Called when starting a new chunk fails
	 *
	 * @param e exception
	 * @param item item which was used to start the new chunk
	 *
	 */
	void onNewChunkError(final Exception e, Object item);

	/**
	 * Called when an item processing fails
	 *
	 * @param e exception
	 * @param item item that failed on processing
	 */
	void onItemError(final Exception e, Object item);

	/**
	 * Called when completing a chunk fails.
	 *
	 * This method is called before transaction is commited.
	 *
	 * @param e exception
	 */
	void onCompleteChunkError(Exception e);

	/**
	 * Called after completing a chunk, if commiting the transaction fails.
	 *
	 * @param e exception
	 */
	void onCommitChunkError(Exception e);

	/**
	 * Called after trying to cancel the current chunk.
	 *
	 * @param e exception
	 */
	void onCancelChunkError(Exception e);

}

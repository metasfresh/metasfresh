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


/**
 * An {@link ITrxItemProcessor} extension which can also separate the items to chunks.
 * 
 * @author tsa
 * 
 * @param <IT> input type
 * @param <RT> result type
 */
public interface ITrxItemChunkProcessor<IT, RT> extends ITrxItemProcessor<IT, RT>
{
	/**
	 * 
	 * @param item
	 * @return true if given item is part of current chunk
	 */
	boolean isSameChunk(IT item);

	/**
	 * Start a new chunk
	 * 
	 * @param item
	 */
	void newChunk(IT item);

	/**
	 * Called by API when chunk is completed
	 */
	void completeChunk();

	/**
	 * Called by API when a chunk was canceled.
	 * 
	 * A chunk is canceled when for any reason chunk could not be completed (e.g. transaction commit failed, item processing failed etc).
	 */
	void cancelChunk();
}

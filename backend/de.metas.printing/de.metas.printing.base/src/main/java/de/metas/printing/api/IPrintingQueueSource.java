package de.metas.printing.api;

/*
 * #%L
 * de.metas.printing.base
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

import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Printing_Queue;

import java.util.Iterator;

/**
 * Source of {@link I_C_Printing_Queue} items to be used when generating {@link I_C_Print_Job}s.
 *
 * @author tsa
 */
public interface IPrintingQueueSource
{
	/**
	 * @return the per-source info used to process the queue.
	 */
	PrintingQueueProcessingInfo getProcessingInfo();

	/**
	 * Gets iterator of {@link I_C_Printing_Queue} items which needs to be processed
	 */
	Iterator<I_C_Printing_Queue> createItemsIterator();

	/**
	 * Get iterator of related items of given <code>item</code>. When processing, those related items can be used to group them together into same {@link I_C_Print_Job}.
	 */
	Iterator<I_C_Printing_Queue> createRelatedItemsIterator(I_C_Printing_Queue item);

	String getTrxName();

	/**
	 * Check if item was already processed/printed (i.e. included in a printjob)
	 *
	 * @return true if item already processed/printed
	 */
	boolean isPrinted(I_C_Printing_Queue item);

	/**
	 * Mark given item as processed/printed.
	 * <p>
	 * NOTE: this method it is also saves the <code>item</code> if necessary.
	 */
	void markPrinted(I_C_Printing_Queue item);

	String getName();

	void setName(String name);
}

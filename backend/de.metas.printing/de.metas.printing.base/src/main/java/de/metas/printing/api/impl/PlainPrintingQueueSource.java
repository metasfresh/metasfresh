/*
 * #%L
 * de.metas.printing.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.printing.api.impl;

import de.metas.printing.api.PrintingQueueProcessingInfo;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.collections.SingletonIterator;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;

import java.util.Iterator;

public class PlainPrintingQueueSource extends AbstractPrintingQueueSource
{
	@Getter
	private final PrintingQueueProcessingInfo processingInfo;

	private final I_C_Printing_Queue item;

	private final Iterator<I_C_Printing_Queue> relatedItems;

	public PlainPrintingQueueSource(
			@NonNull final I_C_Printing_Queue item,
			@NonNull final Iterator<I_C_Printing_Queue> relatedItems,
			@NonNull final PrintingQueueProcessingInfo processingInfo)
	{
		this.item = item;
		this.relatedItems = relatedItems;
		this.processingInfo = processingInfo;
	}

	@Override
	public Iterator<I_C_Printing_Queue> createItemsIterator()
	{
		return new SingletonIterator<>(item);
	}

	/**
	 * @return empty iterator.
	 */
	@Override
	public Iterator<I_C_Printing_Queue> createRelatedItemsIterator(@NonNull final I_C_Printing_Queue item)
	{
		return relatedItems;
	}

	/**
	 * @return ITrx#TRXNAME_ThreadInherited
	 */
	@Override
	public String getTrxName()
	{
		return ITrx.TRXNAME_ThreadInherited;
	}
}

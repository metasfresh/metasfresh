package de.metas.printing.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.concurrent.CopyOnWriteArrayList;

import com.google.common.base.Preconditions;

import de.metas.document.archive.model.I_AD_Archive;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.spi.IPrintingQueueHandler;

public final class CompositePrintingQueueHandler implements IPrintingQueueHandler
{
	private final CopyOnWriteArrayList<IPrintingQueueHandler> handlers = new CopyOnWriteArrayList<>();

	/**
	 * Execute this handler last, no matter what other handlers were registered.
	 * 
	 * @task https://github.com/metasfresh/metasfresh/issues/1081
	 */
	private final IPrintingQueueHandler lastHandler;

	/**
	 * 
	 * @param lastHandler the handler to apply last (gh #1081). May not be {@code null}.
	 */
	public CompositePrintingQueueHandler(final IPrintingQueueHandler lastHandler)
	{
		Preconditions.checkNotNull(lastHandler, "Param 'lastHandler' may not be null");
		this.lastHandler = lastHandler;
	}

	public void addHandler(final IPrintingQueueHandler handler)
	{
		if (handler == null)
		{
			return;
		}

		handlers.addIfAbsent(handler);
	}

	@Override
	public void afterEnqueueBeforeSave(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut)
	{
		for (final IPrintingQueueHandler handler : handlers)
		{
			afterEnqueueBeforeSave(queueItem, printOut, handler);
		}
		afterEnqueueBeforeSave(queueItem, printOut, lastHandler);
	}

	private void afterEnqueueBeforeSave(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut, final IPrintingQueueHandler handler)
	{
		if (handler.isApplyHandler(queueItem, printOut))
		{
			handler.afterEnqueueBeforeSave(queueItem, printOut);
		}
	}

	@Override
	public void afterEnqueueAfterSave(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut)
	{
		for (final IPrintingQueueHandler handler : handlers)
		{
			afterEnqueueAfterSave(queueItem, printOut, handler);
		}
		afterEnqueueAfterSave(queueItem, printOut, lastHandler);
	}

	private void afterEnqueueAfterSave(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut, final IPrintingQueueHandler handler)
	{
		if (handler.isApplyHandler(queueItem, printOut))
		{
			handler.afterEnqueueAfterSave(queueItem, printOut);
		}
	}

	/**
	 * Returns <code>true</code>, but note that handlers which are contained in this instance have their {@link #isApplyHandler(I_C_Printing_Queue, I_AD_Archive)} method invoked individually.
	 */
	@Override
	public boolean isApplyHandler(I_C_Printing_Queue queueItem, I_AD_Archive printOut)
	{
		return true;
	}
}

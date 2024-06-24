package de.metas.printing.spi;

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


import de.metas.document.archive.model.I_AD_Archive;
import de.metas.printing.model.I_C_Printing_Queue;

public abstract class PrintingQueueHandlerAdapter implements IPrintingQueueHandler
{

	@Override
	public void afterEnqueueBeforeSave(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut)
	{
		// nothing
	}

	@Override
	public void afterEnqueueAfterSave(final I_C_Printing_Queue queueItem, final I_AD_Archive printOut)
	{
		// nothing
	}

}

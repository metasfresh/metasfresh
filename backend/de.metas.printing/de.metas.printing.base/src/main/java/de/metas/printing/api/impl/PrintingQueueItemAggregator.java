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

import de.metas.logging.LogManager;
import de.metas.printing.model.I_C_Printing_Queue;
import org.compiere.model.I_AD_Archive;
import org.slf4j.Logger;

public class PrintingQueueItemAggregator
{
	private final transient Logger logger = LogManager.getLogger(PrintingQueueItemAggregator.class);

	public void add(I_C_Printing_Queue printingQueueRecord)
	{
		final I_AD_Archive archiveRecord = printingQueueRecord.getAD_Archive();
		final PrintingArchiveData archiveData = new PrintingArchiveData(archiveRecord);
		if (!archiveData.hasData())
		{
			logger.info("Print Job Line's Archive has no data: {}. Skipping it", archiveData);
			return;
		}
	}
}

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

package de.metas.printing;

import com.google.common.collect.ImmutableList;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.impl.PlainPrintingQueueSource;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.printingdata.PrintingData;
import de.metas.printing.printingdata.PrintingDataFactory;
import de.metas.printing.printingdata.PrintingDataToPDFFileStorer;
import de.metas.user.UserId;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveEventManager;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@Service
public class PrintOutputFacade
{
	private final static transient Logger logger = LogManager.getLogger(PrintOutputFacade.class);

	private final PrintingDataFactory printingDataFactory;
	private final PrintingDataToPDFFileStorer printingDataToPDFFileStorer;
	private final IPrintJobBL printJobBL = Services.get(IPrintJobBL.class);
	private final IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);

	public PrintOutputFacade(
			@NonNull final PrintingDataFactory printingDataFactory,
			@NonNull final PrintingDataToPDFFileStorer printingDataToPDFFileStorer)
	{
		this.printingDataFactory = printingDataFactory;
		this.printingDataToPDFFileStorer = printingDataToPDFFileStorer;
	}

	public void print(@NonNull final IPrintingQueueSource source)
	{
		print(source, IPrintJobBL.ContextForAsyncProcessing.builder().build());
	}

	public void print(@NonNull final IPrintingQueueSource source, @NonNull final IPrintJobBL.ContextForAsyncProcessing printJobContext)
	{
		final Iterator<I_C_Printing_Queue> it = source.createItemsIterator();
		while (it.hasNext())
		{
			final I_C_Printing_Queue item = it.next();
			try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(item))
			{
				if (source.isPrinted(item))
				{
					logger.debug("According to IPrintingQueueSource, C_Printing_Queue is already printed, maybe in meantime; -> skipping");// (i.e. it was processed as a related item)
					continue;
				}
				final ImmutableList<PrintingData> printingData = printingDataFactory.createPrintingDataForQueueItem(item);
				for (PrintingData printingDataItem : printingData)
				{
					final PrintingData printingDataToStore = printingDataItem.onlyWithType(OutputType.Store);
					final boolean hasSegmentsToStoreOnDisk = !printingDataToStore.getSegments().isEmpty();
					if (hasSegmentsToStoreOnDisk)
					{
						logger.debug("At least a part of C_Printing_Queue shall be stored directly to disk; -> invoke printingDataToPDFFileStorer; printingData={}; ", printingData);
						storePDFAndFireEvent(source, item, printingDataToStore);
						source.markPrinted(item);
					}

					// with there is a config with a specific hostKey, then printingData.getSegments() might be empty, but still we might need print-jobs
					if (printingDataItem.getSegments().isEmpty() || printingDataItem.getSegments().size() != printingDataToStore.getSegments().size())
					{
						logger.debug("Also invoke printJobBL, in case there are also items to be printed");

						// task: 08958: note that that all items' related items have the same copies value as item
						final PlainPrintingQueueSource plainSource = new PlainPrintingQueueSource(
								item,
								source.createRelatedItemsIterator(item),
								source.getProcessingInfo());
						printJobBL.createPrintJobs(plainSource, printJobContext);
					}
				}
			}
		}
	}

	private void storePDFAndFireEvent(
			@NonNull final IPrintingQueueSource source,
			@NonNull final I_C_Printing_Queue item,
			@NonNull final PrintingData printingDataToStore)
	{
		printingDataToPDFFileStorer.storeInFileSystem(printingDataToStore);

		final ImmutableList<String> printerNames = CollectionUtils.extractDistinctElements(
				printingDataToStore.getSegments(),
				s -> s.getPrinter().getName());

		for (final String printerName : printerNames)
		{
			archiveEventManager.firePrintOut(
					item.getAD_Archive(),
					source.getProcessingInfo().getAD_User_PrintJob_ID(),
					printerName,
					IArchiveEventManager.COPIES_ONE,
					IArchiveEventManager.STATUS_Success);
		}
	}
}

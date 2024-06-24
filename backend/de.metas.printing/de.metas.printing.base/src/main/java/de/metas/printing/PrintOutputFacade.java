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
import de.metas.audit.data.ExternalSystemParentConfigId;
import de.metas.i18n.AdMessageKey;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.impl.PlainPrintingQueueSource;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.printingdata.PrintingData;
import de.metas.printing.printingdata.PrintingDataFactory;
import de.metas.printing.printingdata.PrintingDataToPDFFileStorer;
import de.metas.printing.printingdata.PrintingSegment;
import de.metas.printing.spi.impl.ExternalSystemsPrintingNotifier;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.archive.api.ArchivePrintOutStatus;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;
import org.slf4j.MDC;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class PrintOutputFacade
{
	private final static Logger logger = LogManager.getLogger(PrintOutputFacade.class);

	private final PrintingDataFactory printingDataFactory;
	private final PrintingDataToPDFFileStorer printingDataToPDFFileStorer;
	private final IPrintJobBL printJobBL = Services.get(IPrintJobBL.class);
	private final IArchiveEventManager archiveEventManager = Services.get(IArchiveEventManager.class);

	private final ExternalSystemsPrintingNotifier externalSystemsPrintingNotifier = SpringContextHolder.instance.getBean(ExternalSystemsPrintingNotifier.class);

	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private static final AdMessageKey ERROR_MSG_EXTERNAL_SYSTEM_CONFIG = AdMessageKey.of("de.metas.printing.external.system.config.error");

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

	public void print(@NonNull final IPrintingQueueSource source,
			@NonNull final IPrintJobBL.ContextForAsyncProcessing printJobContext)
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
				final List<PrintingData> printingDataForExternalSystems = new ArrayList<>();
				for (final PrintingData printingDataItem : printingData)
				{
					final PrintingData printingDataToStore = printingDataItem.onlyWithType(OutputType.Store);
					final boolean hasSegmentsToStoreOnDisk = !printingDataToStore.getSegments().isEmpty();
					if (hasSegmentsToStoreOnDisk)
					{
						logger.debug("At least a part of C_Printing_Queue shall be stored directly to disk; -> invoke printingDataToPDFFileStorer; printingData={}; ", printingData);
						storePDFAndFireEvent(source, item, printingDataToStore);
						source.markPrinted(item);
					}

					// if there is a config with a specific hostKey, then printingData.getSegments() might be empty, but still we might need print-jobs
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

					final PrintingData printingDataToExternalSystem = printingDataItem.onlyQueuedForExternalSystems();
					if(!printingDataToExternalSystem.getSegments().isEmpty())
					{
						printingDataForExternalSystems.add(printingDataItem);
					}
				}
				if(!printingDataForExternalSystems.isEmpty())
				{
					if(hasMultipleExternalSystemConfigs(printingDataForExternalSystems))
					{
						throw new AdempiereException(ERROR_MSG_EXTERNAL_SYSTEM_CONFIG).markAsUserValidationError();
					}
					final ExternalSystemParentConfigId configId = printingDataForExternalSystems.get(0).getSegments().get(0).getPrinter().getExternalSystemParentConfigId();
					Check.assumeNotNull(configId,"ExternalSystemParentConfigId shouldn't be null");
					final int firstExternalSystemId = configId.getRepoId();
					final PrintingClientRequest request = PrintingClientRequest.builder()
							.printingQueueId(item.getC_Printing_Queue_ID())
							.orgId(item.getAD_Org_ID())
							.pInstanceId(printJobContext.getAdPInstanceId() != null ? printJobContext.getAdPInstanceId().getRepoId() : null)
							.externalSystemParentConfigId(firstExternalSystemId)
							.build();

					//notify external systems printers *after commit*, because the item need to be in the DB for access via the API
					trxManager.runAfterCommit(() -> externalSystemsPrintingNotifier.notifyExternalSystemsIfNeeded(request));
				}
			}
		}
	}

	private boolean hasMultipleExternalSystemConfigs(@NonNull final List<PrintingData> printingDataList)
	{
		final ExternalSystemParentConfigId configId = printingDataList.get(0).getSegments().get(0).getPrinter().getExternalSystemParentConfigId();
		Check.assumeNotNull(configId,"ExternalSystemParentConfigId shouldn't be null");
		final int firstExternalSystemId = configId.getRepoId();
		for (final PrintingData printingData : printingDataList)
		{
			for (final PrintingSegment segment : printingData.getSegments())
			{
				final ExternalSystemParentConfigId segmentConfigId = segment.getPrinter().getExternalSystemParentConfigId();
				Check.assumeNotNull(segmentConfigId,"ExternalSystemParentConfigId shouldn't be null");
				if (firstExternalSystemId != segmentConfigId.getRepoId())
				{
					return true;
				}
			}
		}
		return false;
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
					ArchivePrintOutStatus.Success);
		}
	}
}

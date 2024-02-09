/**
 *
 */
package de.metas.printing.api.impl;

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

import ch.qos.logback.classic.Level;
import com.google.common.collect.ImmutableList;
import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.adempiere.service.PrinterRoutingsQuery;
import de.metas.async.AsyncBatchId;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintClientsBL;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrinterBL;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.api.IPrintingQueueSource;
import de.metas.printing.api.PrintingQueueProcessingInfo;
import de.metas.printing.async.spi.impl.PDFDocPrintingWorkpackageProcessor;
import de.metas.printing.model.I_AD_PrinterRouting;
import de.metas.printing.model.I_AD_Printer_Config;
import de.metas.printing.model.I_C_Print_Job;
import de.metas.printing.model.I_C_Print_Job_Detail;
import de.metas.printing.model.I_C_Print_Job_Instructions;
import de.metas.printing.model.I_C_Print_Job_Line;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.process.PInstanceId;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import de.metas.util.collections.IteratorChain;
import de.metas.util.collections.IteratorUtils;
import de.metas.util.collections.PeekIterator;
import de.metas.util.collections.SingletonIterator;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.experimental.Delegate;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.Mutable;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class PrintJobBL implements IPrintJobBL
{
	public final static int DEFAULT_MAX_JOBPRINTLINES = 500;

	public final static String SYSCONFIG_MAX_LINES_PER_JOB = Printing_Constants.SYSCONFIG_Printing_PREFIX + "MaxLinesPerJob";

	private final static transient Logger logger = LogManager.getLogger(PrintJobBL.class);

	private final IPrinterRoutingDAO printerRoutingDAO = Services.get(IPrinterRoutingDAO.class);
	private final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IPrinterBL printerBL = Services.get(IPrinterBL.class);
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IPrintClientsBL printClientsBL = Services.get(IPrintClientsBL.class);

	private int maxLinesPerJob = -1;

	/**
	 * Allows it to set maxLinesPerJob from outside (intended use is for testing). If set, then this value overrides the <code>AD_SysConfig</code> setting {@value #SYSCONFIG_MAX_LINES_PER_JOB}.
	 */
	public int getMaxLinesPerJob()
	{
		return maxLinesPerJob;
	}

	public void setMaxLinesPerJob(int maxLinesPerJob)
	{
		this.maxLinesPerJob = maxLinesPerJob;
	}

	@Override
	public void createPrintJobs(@NonNull final IPrintingQueueSource source, @NonNull final ContextForAsyncProcessing printJobContext)
	{
		final PrintingQueueProcessingInfo printingQueueProcessingInfo = source.getProcessingInfo();

		int printJobCount = 0;
		final List<I_C_Print_Job_Instructions> pdfPrintingJobInstructions = new ArrayList<>();

		try
		{
			// important: we shall poll the queue respecting the FIFO order
			final Iterator<I_C_Printing_Queue> it = source.createItemsIterator();
			while (it.hasNext())
			{
				final I_C_Printing_Queue item = it.next();
				try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(item))
				{
					pdfPrintingJobInstructions.addAll(createPrintJob(source, printingQueueProcessingInfo, item));
				}
			}
		}
		finally
		{
			final PrintingAsyncBatch printingAsyncBatch = PrintingAsyncBatch.builder()
					.name(source.getName())
					.printJobContext(printJobContext)
					.printJobCount(printJobCount)
					.build();
			enqueuePrintJobInstructionsForPDFPrintingIfNeeded(pdfPrintingJobInstructions, printingAsyncBatch);
		}
	}

	private List<I_C_Print_Job_Instructions> createPrintJob(
			final @NonNull IPrintingQueueSource source,
			final @NonNull PrintingQueueProcessingInfo printingQueueProcessingInfo,
			final @NonNull I_C_Printing_Queue item)
	{
		if (source.isPrinted(item))
		{
			logger.debug("According to IPrintingQueueSource, C_Printing_Queue is already printed, maybe in meantime; -> skipping");// (i.e. it was processed as a related item)
			return ImmutableList.of();
		}

		final List<I_C_Print_Job_Instructions> pdfPrintingJobInstructions = new ArrayList<>();

		final Iterator<I_C_Printing_Queue> relatedItems = source.createRelatedItemsIterator(item);

		// task: 08958: note that that all items' related items have the same copies value as item
		final PeekIterator<I_C_Printing_Queue> currentItems = IteratorUtils.asPeekIterator(
				new IteratorChain<I_C_Printing_Queue>()
						.addIterator(new SingletonIterator<>(item))
						.addIterator(relatedItems));
		try
		{
			skipPrinted(source, currentItems);

			pdfPrintingJobInstructions.addAll(processItemGroup(source, printingQueueProcessingInfo, currentItems));
		}
		finally
		{
			IteratorUtils.close(currentItems);
		}
		return pdfPrintingJobInstructions;
	}

	private List<I_C_Print_Job_Instructions> processItemGroup(
			@NonNull final IPrintingQueueSource source,
			@NonNull final PrintingQueueProcessingInfo printingQueueProcessingInfo,
			@NonNull final PeekIterator<I_C_Printing_Queue> currentItems)
	{
		final ImmutableList.Builder<I_C_Print_Job_Instructions> pdfPrintingJobInstructions = ImmutableList.builder();
		while (currentItems.hasNext())
		{
			final List<I_C_Print_Job_Instructions> printJobInstructions = createPrintJobInstructionsAndPrintJobs(source,
					currentItems,
					printingQueueProcessingInfo);
			Loggables.withLogger(logger, Level.DEBUG).addLog("Created {} C_Print_Job_Instructions for related C_Printing_Queues", printJobInstructions.size());
			if (printJobInstructions.isEmpty())
			{
				break;
			}
			else
			{
				pdfPrintingJobInstructions.addAll(collectPrintJobInstructionsToAttach(printJobInstructions));
			}

			skipPrinted(source, currentItems);
		}
		return pdfPrintingJobInstructions.build();
	}

	/**
	 * Navigates items iterator and skips all printed items.
	 *
	 * @return number of items that were skipped
	 */
	private int skipPrinted(final IPrintingQueueSource source, final PeekIterator<I_C_Printing_Queue> items)
	{
		int count = 0;
		while (items.hasNext())
		{
			final I_C_Printing_Queue item = items.peek();
			if (!source.isPrinted(item))
			{
				break;
			}

			items.next();
			count++;
		}

		if (count > 0)
		{
			logger.info("Skipped {} already processed records", count);
		}
		return count;
	}

	private List<I_C_Print_Job_Instructions> createPrintJobInstructionsAndPrintJobs(final IPrintingQueueSource source,
			final Iterator<I_C_Printing_Queue> items,
			final PrintingQueueProcessingInfo printingQueueProcessingInfo)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final Mutable<List<I_C_Print_Job_Instructions>> instructionsMutable = new Mutable<>();

		trxManager.run(ITrx.TRXNAME_ThreadInherited, (TrxRunnable)localTrxName -> instructionsMutable.setValue(createPrintJobInstructionsAndPrintJobs0(source, items, printingQueueProcessingInfo, localTrxName)));

		if (instructionsMutable.getValue() == null)
		{
			return null;
		}

		//
		// Reload instructions (in base transaction), to be used by monitor or other processors
		InterfaceWrapperHelper.refreshAll(instructionsMutable.getValue(), ITrx.TRXNAME_ThreadInherited);
		return instructionsMutable.getValue();
	}

	private List<I_C_Print_Job_Instructions> collectPrintJobInstructionsToAttach(final List<I_C_Print_Job_Instructions> printJobInstructions)
	{
		return printJobInstructions.stream()
				.filter(pji -> X_C_Print_Job_Instructions.STATUS_Pending.equals(pji.getStatus())
						&& pji.getAD_PrinterHW_ID() > 0
						&& printerBL.isAttachToPrintPackagePrinter(pji.getAD_PrinterHW()))
				.collect(Collectors.toList());
	}

	@Value
	@Builder
	private static class PrintingAsyncBatch
	{
		String name;
		int printJobCount;

		@NonNull
		@Delegate
		ContextForAsyncProcessing printJobContext;
	}

	private void enqueuePrintJobInstructionsForPDFPrintingIfNeeded(@NonNull final List<I_C_Print_Job_Instructions> printJobInstructions, @NonNull PrintingAsyncBatch printingAsyncBatch)
	{
		if (printJobInstructions.isEmpty())
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(printJobInstructions.get(0));
		final I_C_Async_Batch asyncBatch = createAsyncBatchForPDFPrinting(ctx, printingAsyncBatch);
		printJobInstructions.forEach(pji -> enqueuePrintJobInstructions(pji, asyncBatch));
	}

	private I_C_Async_Batch createAsyncBatchForPDFPrinting(@NonNull final Properties ctx, @NonNull PrintingAsyncBatch printingAsyncBatch)
	{
		final String name = Check.isEmpty(printingAsyncBatch.getName(), true) ? "Print to pdf" : printingAsyncBatch.getName();
		final int printJobCount = printingAsyncBatch.getPrintJobCount();
		final PInstanceId adPInstanceId = getAdPInstanceId(printingAsyncBatch);
		final int parentAsyncBatchId = printingAsyncBatch.getParentAsyncBatchId();

		return asyncBatchBL.newAsyncBatch()
				.setContext(ctx)
				.setC_Async_Batch_Type(Printing_Constants.C_Async_Batch_InternalName_PDFPrinting)
				.setAD_PInstance_Creator_ID(adPInstanceId)
				.setParentAsyncBatchId(AsyncBatchId.ofRepoIdOrNull(parentAsyncBatchId))
				.setCountExpected(printJobCount)
				.setName(name)
				.build();
	}

	private PInstanceId getAdPInstanceId(@NonNull PrintingAsyncBatch printingAsyncBatch)
	{
		final I_C_Async_Batch parentAsyncBatch = getParentAsyncPatchIfExists(printingAsyncBatch.getParentAsyncBatchId());
		return parentAsyncBatch == null ? printingAsyncBatch.getAdPInstanceId() : PInstanceId.ofRepoIdOrNull(parentAsyncBatch.getAD_PInstance_ID());
	}

	private I_C_Async_Batch getParentAsyncPatchIfExists(final int parentAsyncBatchId)
	{
		return parentAsyncBatchId > 0 ? InterfaceWrapperHelper.load(parentAsyncBatchId, I_C_Async_Batch.class) : null;
	}

	@Override
	public void enqueuePrintJobInstructions(final I_C_Print_Job_Instructions jobInstructions, final I_C_Async_Batch asyncBatch)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(jobInstructions);

		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, PDFDocPrintingWorkpackageProcessor.class);
		queue
				.newWorkPackage()
				.setC_Async_Batch(asyncBatch) // set the async batch in workpackage in order to track it
				.addElement(jobInstructions)
				.buildAndEnqueue();
	}

	/**
	 * Creates a print job, print job lines and a print job instructions for at most <code>maxLines</code> items (see {@link #getMaxLinesPerJob(I_C_Print_Job)}) for the given <code>items</code>.<br>
	 *
	 * Note that <code>items</code> contains both the source's items and related items.
	 *
	 * @return one print job instruction per user-to-print
	 */
	private List<I_C_Print_Job_Instructions> createPrintJobInstructionsAndPrintJobs0(final IPrintingQueueSource source,
			final Iterator<I_C_Printing_Queue> items,
			final PrintingQueueProcessingInfo printingQueueProcessingInfo,
			final String trxName)
	{
		I_C_Print_Job printJob = null;
		int lineCount = 0;
		I_C_Print_Job_Line firstLine = null;
		I_C_Print_Job_Line lastLine = null;
		int lastItemCopies = -1; // -1 means "not yet set"

		while (items.hasNext())
		{
			final I_C_Printing_Queue item = items.next();
			try (final MDCCloseable printingQueueMDC = TableRecordMDC.putTableRecordReference(item))
			{
				if (source.isPrinted(item))
				{
					logger.debug("Skipping {} because is already printed", item);
					continue;
				}

				if (lastItemCopies >= 0 && item.getCopies() != lastItemCopies)
				{
					logger.info("The last item had copies = {}, the current one has copies = {}; not adding further items to printJob = {}",
							lastItemCopies, item.getCopies(), printJob);
					break;
				}

				if (printJob == null)
				{
					printJob = createPrintJob(item, trxName, printingQueueProcessingInfo.getAD_User_PrintJob_ID());
				}

				final int maxLinesPerJobToUse = getMaxLinesPerJob(printJob);
				if (maxLinesPerJobToUse > 0 && lineCount >= maxLinesPerJobToUse)
				{
					logger.info("Max lines per print job = {} reached; not adding further items", maxLinesPerJobToUse);
					break;
				}

				lastLine = createPrintJobLine(source, printJob, item, lineCount + 1);
				lineCount++;
				lastItemCopies = item.getCopies();

				if (firstLine == null)
				{
					firstLine = lastLine;
				}
			}
		}

		if (printJob == null)
		{
			logger.info("No print job created");
			return null;
		}

		return createPrintJobInstructionsForUsersToPrint(printingQueueProcessingInfo, firstLine, lastLine);
	}

	private I_C_Print_Job createPrintJob(
			@NonNull final I_C_Printing_Queue item,
			@NonNull final String trxName,
			final UserId adUserId)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(item);
		final I_C_Print_Job printJob = InterfaceWrapperHelper.create(ctx, I_C_Print_Job.class, trxName);
		printJob.setAD_Org_ID(item.getAD_Org_ID());
		printJob.setAD_User_ID(UserId.toRepoId(adUserId));
		printJob.setIsActive(true);
		printJob.setProcessed(false);
		InterfaceWrapperHelper.save(printJob);

		return printJob;
	}

	/**
	 * If a value has been set via {@link #setMaxLinesPerJob(int)}, then value is returned; otherwise use <code>AD_SysConfig</code> ({@value #SYSCONFIG_MAX_LINES_PER_JOB} with default value
	 * {@value #DEFAULT_MAX_JOBPRINTLINES}).
	 *
	 * @param printJob
	 * @return
	 */
	private int getMaxLinesPerJob(final I_C_Print_Job printJob)
	{
		final int maxLinesPerJobToUse;
		if (getMaxLinesPerJob() > 0)
		{
			maxLinesPerJobToUse = getMaxLinesPerJob();
		}
		else
		{
			maxLinesPerJobToUse = Services.get(ISysConfigBL.class).getIntValue(SYSCONFIG_MAX_LINES_PER_JOB,
					DEFAULT_MAX_JOBPRINTLINES,
					printJob.getAD_Client_ID(),
					printJob.getAD_Org_ID());
		}
		return maxLinesPerJobToUse;
	}

	private I_C_Print_Job_Line createPrintJobLine(final IPrintingQueueSource source,
			final I_C_Print_Job printJob,
			final I_C_Printing_Queue item,
			final int seqNo)
	{
		// Create print job line
		final I_C_Print_Job_Line printJobLine = InterfaceWrapperHelper.newInstance(I_C_Print_Job_Line.class, printJob);
		printJobLine.setAD_Org_ID(item.getAD_Org_ID());
		printJobLine.setC_Print_Job(printJob);
		printJobLine.setC_Printing_Queue(item);
		printJobLine.setIsActive(true);
		printJobLine.setSeqNo(seqNo);

		InterfaceWrapperHelper.save(printJobLine);

		// Mark Print Queue item as processed
		source.markPrinted(item);

		// Create print job detail
		for (final I_AD_PrinterRouting printerRouting : findPrinterRoutings(item))
		{
			Check.assumeNotNull(printerRouting, "AD_PrinterRouting {} found for C_Printing_Queue {}", printerRouting, item);
			createPrintJobDetail(printJobLine, printerRouting);
		}
		return printJobLine;
	}

	private List<I_C_Print_Job_Instructions> createPrintJobInstructionsForUsersToPrint(
			@NonNull final PrintingQueueProcessingInfo printingQueueProcessingInfo,
			@NonNull final I_C_Print_Job_Line firstLine, @NonNull final I_C_Print_Job_Line lastLine)
	{
		final int lastItemCopies = getItemCopies(lastLine);
		final List<UserId> userIDsToPrint = printingQueueProcessingInfo.getAD_User_ToPrint_IDs();
		return userIDsToPrint.stream()
				.map(adUserToPrintId -> createPrintJobInstructions(adUserToPrintId,
						printingQueueProcessingInfo.isCreateWithSpecificHostKey(),
						firstLine,
						lastLine,
						lastItemCopies))
				.collect(ImmutableList.toImmutableList());
	}

	private int getItemCopies(@NonNull final I_C_Print_Job_Line jobLine)
	{
		final I_C_Printing_Queue pq = jobLine.getC_Printing_Queue();
		return pq.getCopies();
	}

	@Override
	public I_C_Print_Job_Instructions createPrintJobInstructions(
			@Nullable final UserId userToPrintId,
			final boolean createWithSpecificHostKey,
			@NonNull final I_C_Print_Job_Line firstLine,
			@NonNull final I_C_Print_Job_Line lastLine,
			final int copies)
	{
		Check.assume(firstLine.getSeqNo() <= lastLine.getSeqNo(), "First line's sequence({}) <= Last line's sequence({})", firstLine.getSeqNo(), lastLine.getSeqNo());

		final I_C_Print_Job printJob = firstLine.getC_Print_Job();

		final I_C_Print_Job_Instructions instructions = InterfaceWrapperHelper.newInstance(I_C_Print_Job_Instructions.class, printJob);
		instructions.setC_Print_Job(printJob);
		instructions.setAD_Org_ID(printJob.getAD_Org_ID());
		instructions.setStatus(X_C_Print_Job_Instructions.STATUS_Pending); // initial status
		instructions.setC_PrintJob_Line_From(firstLine);
		instructions.setC_PrintJob_Line_To(lastLine);
		instructions.setCopies(copies);

		final Properties ctx = InterfaceWrapperHelper.getCtx(instructions);
		final String hostKey = printClientsBL.getHostKeyOrNull(ctx);

		if (createWithSpecificHostKey)
		{
			final String hostKeyToUse;
			final UserId userToPrintIdToUse;
			final I_AD_Printer_Config printerConfig = printingDAO.retrievePrinterConfig(hostKey, userToPrintId);
			Check.errorIf(printerConfig == null,
					"Missing AD_Printer_Config record for hostKey={}, userToPrintId={}",
					hostKey, UserId.toRepoId(userToPrintId));

			if (printerConfig.getAD_Printer_Config_Shared_ID() > 0)
			{
				final I_AD_Printer_Config ad_Printer_Config_Shared = printerConfig.getAD_Printer_Config_Shared();
				hostKeyToUse = ad_Printer_Config_Shared.getConfigHostKey();
				if(ad_Printer_Config_Shared.getC_Workplace_ID() <= 0)
				{
					userToPrintIdToUse = UserId.ofRepoId(ad_Printer_Config_Shared.getAD_User_PrinterMatchingConfig_ID());
				}
				else
				{
					userToPrintIdToUse = userToPrintId;
				}
			}
			else
			{
				hostKeyToUse = hostKey;
				userToPrintIdToUse = userToPrintId;
			}
			instructions.setHostKey(hostKeyToUse); // note that hostkey is not mandatory here
			instructions.setAD_User_ToPrint_ID(UserId.toRepoId(userToPrintIdToUse));
			// task 09028: workaround: don't set the hostkey.
			// therefore the next print client of the given user will be able to print this
		}
		else
		{
			instructions.setAD_User_ToPrint_ID(UserId.toRepoId(userToPrintId));
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(instructions);
		instructions.setAD_PrinterHW(printingDAO.retrieveAttachToPrintPackagePrinter(ctx, hostKey, trxName));

		InterfaceWrapperHelper.save(instructions);
		return instructions;
	}

	@Override
	public List<I_C_Print_Job_Detail> getCreatePrintJobDetails(final I_C_Print_Job_Line printJobLine)
	{
		//
		// Retrieve details if any
		List<I_C_Print_Job_Detail> printJobDetails = printingDAO.retrievePrintJobDetailsIfAny(printJobLine);
		if (printJobDetails != null && !printJobDetails.isEmpty())
		{
			return printJobDetails;
		}

		//
		// Try to create them now
		logger.info("Print Job Line has no details: {}. Creating them now...", printJobLine);
		final I_C_Printing_Queue item = printJobLine.getC_Printing_Queue();
		printJobDetails = createPrintJobDetails(printJobLine, item);

		return printJobDetails;
	}

	private List<I_C_Print_Job_Detail> createPrintJobDetails(final I_C_Print_Job_Line printJobLine, final I_C_Printing_Queue item)
	{
		final List<I_AD_PrinterRouting> printerRoutings = findPrinterRoutings(item);
		Check.errorIf(printerRoutings.isEmpty(), "Found no AD_PrinterRouting record(s) for C_Printing_Queue {}", item);
		if (printerRoutings.isEmpty())
		{
			return Collections.emptyList(); // just for the case that we configured Check not to throw an exception
		}

		final List<I_C_Print_Job_Detail> printJobDetails = new ArrayList<>(printerRoutings.size());
		for (final I_AD_PrinterRouting printerRouting : printerRoutings)
		{
			Check.assumeNotNull(printerRouting, "AD_PrinterRouting {} found for C_Printing_Queue {}", printerRouting, item);
			final I_C_Print_Job_Detail printJobDetail = createPrintJobDetail(printJobLine, printerRouting);
			if (printJobDetail != null)
			{
				printJobDetails.add(printJobDetail);
			}
		}

		return printJobDetails;
	}

	private I_C_Print_Job_Detail createPrintJobDetail(
			final I_C_Print_Job_Line printJobLine,
			final I_AD_PrinterRouting routing)
	{
		final I_C_Print_Job_Detail printJobDetail = InterfaceWrapperHelper.newInstance(I_C_Print_Job_Detail.class, printJobLine);

		printJobDetail.setAD_Org_ID(printJobLine.getAD_Org_ID());
		printJobDetail.setIsActive(true);
		printJobDetail.setAD_PrinterRouting_ID(routing.getAD_PrinterRouting_ID());
		printJobDetail.setC_Print_Job_Line(printJobLine);

		InterfaceWrapperHelper.save(printJobDetail);

		return printJobDetail;
	}

	private List<I_AD_PrinterRouting> findPrinterRoutings(final I_C_Printing_Queue item)
	{
		final PrinterRoutingsQuery query = printingQueueBL.createPrinterRoutingsQueryForItem(item);

		final List<de.metas.adempiere.model.I_AD_PrinterRouting> rs = printerRoutingDAO.fetchPrinterRoutings(query);
		return InterfaceWrapperHelper.createList(rs, I_AD_PrinterRouting.class);
	}

	@Override
	public String getSummary(final I_C_Print_Job printJob)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printJob);
		return Services.get(IMsgBL.class).translate(ctx, I_C_Print_Job.COLUMNNAME_C_Print_Job_ID)
				+ " "
				+ printJob.getC_Print_Job_ID();
	}
}

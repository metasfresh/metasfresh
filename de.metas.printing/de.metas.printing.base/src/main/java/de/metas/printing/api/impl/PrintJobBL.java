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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.collections.IteratorChain;
import org.adempiere.util.collections.IteratorUtils;
import org.adempiere.util.collections.PeekIterator;
import org.adempiere.util.collections.SingletonIterator;
import org.adempiere.util.lang.Mutable;
import org.compiere.model.I_AD_PInstance;
import org.compiere.util.TrxRunnable;
import org.slf4j.Logger;

import de.metas.adempiere.service.IPrinterRoutingDAO;
import de.metas.async.api.IAsyncBatchBL;
import de.metas.async.api.IAsyncBatchDAO;
import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.printing.Printing_Constants;
import de.metas.printing.api.IPrintJobBL;
import de.metas.printing.api.IPrintPackageBL;
import de.metas.printing.api.IPrinterBL;
import de.metas.printing.api.IPrintingDAO;
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
import de.metas.printing.model.X_AD_PrinterHW;
import de.metas.printing.model.X_C_Print_Job_Instructions;
import de.metas.printing.spi.IPrintJobBatchMonitor;
import de.metas.printing.spi.IPrintJobMonitor;

/**
 * @author cg
 *
 */
public class PrintJobBL implements IPrintJobBL
{
	public final static int DEFAULT_MAX_JOBPRINTLINES = 500;

	public final static String SYSCONFIG_MAX_LINES_PER_JOB = Printing_Constants.SYSCONFIG_Printing_PREFIX + "MaxLinesPerJob";

	private final static transient Logger logger = LogManager.getLogger(PrintJobBL.class);
	
	private final IPrintingDAO dao = Services.get(IPrintingDAO.class);
	private final IAsyncBatchDAO asyncBatchDAO = Services.get(IAsyncBatchDAO.class);
	private final IAsyncBatchBL asyncBatchBL = Services.get(IAsyncBatchBL.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

	private int maxLinesPerJob = -1;

	/**
	 * Allows it to set maxLinesPerJob from outside (intended use is for testing). If set, then this value overrides the <code>AD_SysConfig</code> setting {@value #SYSCONFIG_MAX_LINES_PER_JOB}.
	 *
	 * @return
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
	public int createPrintJobs(final IPrintingQueueSource source,
			final IPrintJobMonitor monitor)
	{
		final String trxName = source.getTrxName();
		final PrintingQueueProcessingInfo printingQueueProcessingInfo = source.getProcessingInfo();

		int printJobCount = 0;
		final List<I_C_Print_Job_Instructions> pdfPrintingJobInstructions = new ArrayList<I_C_Print_Job_Instructions>();
		
		final IPrintJobBatchMonitor batchMonitor = monitor.createBatchMonitor();
		try
		{
			// important: we shall poll the queue respecting the FIFO order
			final Iterator<I_C_Printing_Queue> it = source.createItemsIterator();
			while (it.hasNext())
			{
				final I_C_Printing_Queue item = it.next();
				if (source.isPrinted(item))
				{
					// Item was printed in meantime. Skip it
					// (i.e. it was processed as a related item)
					continue;
				}

				final Iterator<I_C_Printing_Queue> relatedItems = source.createRelatedItemsIterator(item);

				// task: 08958: note that that all items' related items have the same copies value as item
				@SuppressWarnings("resource")
				final PeekIterator<I_C_Printing_Queue> currentItems = IteratorUtils.asPeekIterator(
						new IteratorChain<I_C_Printing_Queue>()
								.addIterator(new SingletonIterator<I_C_Printing_Queue>(item))
								.addIterator(relatedItems)
						);

				try
				{
					skipPrinted(source, currentItems);

					while (currentItems.hasNext())
					{
						if (!batchMonitor.printJobBeforeCreate())
						{
							return printJobCount;
						}

						final List<I_C_Print_Job_Instructions> printJobInstructions = createPrintJob(source,
								currentItems,
								printingQueueProcessingInfo,
								trxName);
						if (printJobInstructions.isEmpty())
						{
							break;
						}
						else
						{
							pdfPrintingJobInstructions.addAll(collectPDFPrintJobInstructions(printJobInstructions));
						}

						printJobCount++;

						if (!batchMonitor.printJobCreated(printJobInstructions.get(0))) // doesn't matter which one we call the monitor with
						{
							return printJobCount;
						}

						skipPrinted(source, currentItems);
					}
				}
				finally
				{
					IteratorUtils.close(currentItems);
				}
			}
		}
		finally
		{
			batchMonitor.finish();
			enqueueForPDFPrinting(source, monitor, pdfPrintingJobInstructions, printJobCount);
			
		}

		return printJobCount;
	}
	
	/**
	 * Builds a list from all C_Print_Job_Instructions that have a PDF printer set
	 * @param printJobInstructions
	 * @return
	 */
	private List<I_C_Print_Job_Instructions> collectPDFPrintJobInstructions(final List<I_C_Print_Job_Instructions> printJobInstructions)
	{
		final List<I_C_Print_Job_Instructions> pdfInstructions = new ArrayList<I_C_Print_Job_Instructions>();

		for (final I_C_Print_Job_Instructions pji : printJobInstructions)
		{
			// Send to the virtual printer
			if (X_C_Print_Job_Instructions.STATUS_Pending.equals(pji.getStatus()) && pji.getAD_PrinterHW_ID() > 0
				&& Services.get(IPrinterBL.class).isPDFPrinter(pji.getAD_PrinterHW_ID()))
			{
				pdfInstructions.add(pji);
			}
		}
		
		return pdfInstructions;
	}
	
	/**
	 * enqueues the given C_Print_Job_Instructions for pdf printing
	 * @param source
	 * @param monitor
	 * @param pjis
	 * @param printJobCount
	 */
	private void enqueueForPDFPrinting(final IPrintingQueueSource source, final IPrintJobMonitor monitor, final List<I_C_Print_Job_Instructions> pjis, final int printJobCount)
	{
		if (pjis.isEmpty())
		{
			return;
		}

		// get context from first print job instructions - all have same context
		final Properties ctx = InterfaceWrapperHelper.getCtx(pjis.get(0));
		final I_C_Async_Batch asyncBatch = createAsyncBatch(ctx);
		asyncBatch.setCountExpected(printJobCount);
		final Integer AD_PInstance_ID = (Integer)monitor.getDynAttribute(I_AD_PInstance.COLUMNNAME_AD_PInstance_ID);
		if (AD_PInstance_ID != null)
		{
			asyncBatch.setAD_PInstance_ID(AD_PInstance_ID);
		}

		if (!Check.isEmpty(source.getName(), true))
		{
			asyncBatch.setName(source.getName());
		}
		//
		// if existent a parent async batch, set it in the new async batch
		final Integer parentAsyncBatchID = (Integer)monitor.getDynAttribute(I_C_Async_Batch.COLUMNNAME_C_Async_Batch_ID);
		if (parentAsyncBatchID != null && parentAsyncBatchID.intValue() > 0)
		{
			asyncBatch.setParent_Async_Batch_ID(parentAsyncBatchID);
			final I_C_Async_Batch parentAsyncBatch = InterfaceWrapperHelper.create(ctx, parentAsyncBatchID, I_C_Async_Batch.class, ITrx.TRXNAME_None);
			asyncBatch.setAD_PInstance_ID(parentAsyncBatch.getAD_PInstance_ID());
		}
		queueDAO.saveInLocalTrx(asyncBatch);

		for (final I_C_Print_Job_Instructions pji : pjis)
		{
			if (X_AD_PrinterHW.OUTPUTTYPE_PDF.equals(pji.getAD_PrinterHW().getOutputType()))
			{
				enquePrintJobInstructions(pji, asyncBatch);
			}
		}

	}

	/**
	 * creates the async batch for pdf printing
	 * 
	 * @return
	 */
	private I_C_Async_Batch createAsyncBatch(final Properties ctx)
	{

		final I_C_Async_Batch_Type asyncBatchType = asyncBatchDAO
				.retrieveAsyncBatchType(ctx, Printing_Constants.C_Async_Batch_InternalName_PDFPrinting);

		final I_C_Async_Batch asyncBatch = InterfaceWrapperHelper.create(ctx, I_C_Async_Batch.class, ITrx.TRXNAME_None);
		asyncBatch.setName("Print to pdf");
		asyncBatch.setC_Async_Batch_Type(asyncBatchType);
		queueDAO.saveInLocalTrx(asyncBatch);
		asyncBatchBL.enqueueAsyncBatch(asyncBatch);

		return asyncBatch;
	}

	/**
	 * Navigates items iterator and skips all printed items.
	 *
	 * @param source
	 *
	 * @param items
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

	private List<I_C_Print_Job_Instructions> createPrintJob(final IPrintingQueueSource source,
			final Iterator<I_C_Printing_Queue> items,
			final PrintingQueueProcessingInfo printingQueueProcessingInfo,
			final String trxName)
	{
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		final Mutable<List<I_C_Print_Job_Instructions>> instrutionsMutable = new Mutable<List<I_C_Print_Job_Instructions>>();

		trxManager.run(trxName, new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName)
			{
				instrutionsMutable.setValue(createPrintJob0(source, items, printingQueueProcessingInfo, localTrxName));
			}
		});

		if (instrutionsMutable.getValue() == null)
		{
			return null;
		}

		//
		// Reload instructions (in base transaction), to be used by monitor or other processors
		InterfaceWrapperHelper.refreshAll(instrutionsMutable.getValue(), trxName);
		return instrutionsMutable.getValue();
	}

	/**
	 * Creates a print job, print job lines and a print job instructions for at most <code>maxLines</code> items (see {@link #getMaxLinesPerJob(I_C_Print_Job)}) for the given <code>items</code>.<br>
	 *
	 * Note that <code>items</code> contains both the source's items and related items.
	 *
	 * @param source
	 * @param items
	 * @param printingQueueProcessingInfo
	 * @param trxName
	 * @return one print job instruction per user-to-print
	 */
	private List<I_C_Print_Job_Instructions> createPrintJob0(final IPrintingQueueSource source,
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
			if (source.isPrinted(item))
			{
				// Item was already printed, skip it
				logger.debug("Skipping {} because is already printed", item);
				continue;
			}
			if (lastItemCopies >= 0 && item.getCopies() != lastItemCopies)
			{
				logger.info("The lat item had copies = {}, the current one has copies = {}; not adding further items to printJob = {}",
						new Object[] { lastItemCopies, item.getCopies(), printJob });
				break;
			}

			if (printJob == null)
			{
				final Properties ctx = InterfaceWrapperHelper.getCtx(item);
				printJob = InterfaceWrapperHelper.create(ctx, I_C_Print_Job.class, trxName);
				printJob.setAD_Org_ID(item.getAD_Org_ID());
				printJob.setAD_User_ID(printingQueueProcessingInfo.getAD_User_PrintJob_ID()); // 03870
				printJob.setIsActive(true);
				printJob.setProcessed(false);
				InterfaceWrapperHelper.save(printJob);
			}
			final int maxLinesPerJobToUse = getMaxLinesPerJob(printJob);
			if (maxLinesPerJobToUse > 0 && lineCount >= maxLinesPerJobToUse)
			{
				// we just created the 500th line of 'printJob' (or whatever the value of 'MAX_JOBPRINTLINES' is)
				// that means that we stop here, we leave the iterator as is and next call of this method will process next lines
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

		if (printJob == null)
		{
			logger.info("No print job created");
			return null;
		}

		//
		// Create default instructions
		final List<I_C_Print_Job_Instructions> result = new ArrayList<I_C_Print_Job_Instructions>();
		for (final Integer adUserToPrintId : printingQueueProcessingInfo.getAD_User_ToPrint_IDs())
		{
			final I_C_Print_Job_Instructions instructions = createPrintJobInstructions(printJob,
					adUserToPrintId,
					printingQueueProcessingInfo.isCreateWithSpecificHostKey(),
					firstLine,
					lastLine,
					lastItemCopies);
			result.add(instructions);
		}
		return result;
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

	@Override
	public I_C_Print_Job_Instructions createPrintJobInstructions(final I_C_Print_Job printJob,
			final int userToPrintId,
			final boolean createWithSpecificHostKey,
			final I_C_Print_Job_Line firstLine,
			final I_C_Print_Job_Line lastLine,
			final int copies)
	{
		Check.assumeNotNull(firstLine, "firstLine not null");
		Check.assumeNotNull(lastLine, "lastLine not null");
		Check.assume(firstLine.getSeqNo() <= lastLine.getSeqNo(), "First line's sequence({}) <= Last line's sequence({})", firstLine.getSeqNo(), lastLine.getSeqNo());

		final IPrintingDAO printingDAO = Services.get(IPrintingDAO.class);

		final I_C_Print_Job_Instructions instructions = InterfaceWrapperHelper.newInstance(I_C_Print_Job_Instructions.class, printJob);
		instructions.setC_Print_Job(printJob);
		instructions.setAD_Org_ID(printJob.getAD_Org_ID());
		instructions.setAD_User_ToPrint_ID(userToPrintId);
		instructions.setStatus(X_C_Print_Job_Instructions.STATUS_Pending); // initial status
		instructions.setC_PrintJob_Line_From(firstLine);
		instructions.setC_PrintJob_Line_To(lastLine);
		instructions.setCopies(copies);

		final Properties ctx = InterfaceWrapperHelper.getCtx(instructions);
		final String hostKey = Services.get(IPrintPackageBL.class).getHostKeyOrNull(ctx);

		if (createWithSpecificHostKey)
		{
			if (Check.isEmpty(hostKey, true))
			{
				// note that without a hostkey, we also can't retrieve the I_AD_Printer_Config
				logger.info("Ignoring createWithSpecificHostKey=true, because there is no hostkey");
			}
			else
			{
				final String hostKeyToUse;
				final int userToPrintIdToUse;
				final I_AD_Printer_Config printerConfig = printingDAO.retrievePrinterConfig(new PlainContextAware(ctx), hostKey, userToPrintId);
				Check.errorIf(printerConfig == null,
						"Missing AD_Printer_Config record for hostKey={}, userToPrintId={}, ctx={}",
						hostKey, userToPrintId, ctx);

				if (printerConfig.getAD_Printer_Config_Shared_ID() > 0)
				{
					final I_AD_Printer_Config ad_Printer_Config_Shared = printerConfig.getAD_Printer_Config_Shared();
					hostKeyToUse = ad_Printer_Config_Shared.getHostKey();
					userToPrintIdToUse = ad_Printer_Config_Shared.getCreatedBy();
				}
				else
				{
					hostKeyToUse = hostKey;
					userToPrintIdToUse = userToPrintId;
				}
				instructions.setHostKey(hostKeyToUse);
				instructions.setAD_User_ToPrint_ID(userToPrintIdToUse);
				// task 09028: workaround: don't set the hostkey.
				// therefore the next print client of the given user will be able to print this
			}
		}
		else
		{
			instructions.setAD_User_ToPrint_ID(userToPrintId);
		}

		final String trxName = InterfaceWrapperHelper.getTrxName(instructions);
		// set printer for pdf printing
		instructions.setAD_PrinterHW(dao.retrieveVirtualPrinter(ctx, hostKey, trxName));
		
		InterfaceWrapperHelper.save(instructions);
		return instructions;
	}

	@Override
	public List<I_C_Print_Job_Detail> getCreatePrintJobDetails(final I_C_Print_Job_Line printJobLine)
	{
		final IPrintingDAO dao = Services.get(IPrintingDAO.class);

		//
		// Retrieve details if any
		List<I_C_Print_Job_Detail> printJobDetails = dao.retrievePrintJobDetailsIfAny(printJobLine);
		if (printJobDetails != null && !printJobDetails.isEmpty())
		{
			return printJobDetails;
		}

		//
		// Try to create them now
		logger.info("Print Job Line has no details: {}. Creating them now...", printJobLine);
		I_C_Printing_Queue item = printJobLine.getC_Printing_Queue();
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

		final List<I_C_Print_Job_Detail> printJobDetails = new ArrayList<I_C_Print_Job_Detail>(printerRoutings.size());
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

	private I_C_Print_Job_Detail createPrintJobDetail(final I_C_Print_Job_Line printJobLine, final I_AD_PrinterRouting routing)
	{
		final I_C_Print_Job_Detail printJobDetail = InterfaceWrapperHelper.newInstance(I_C_Print_Job_Detail.class, printJobLine);

		printJobDetail.setAD_Org_ID(printJobLine.getAD_Org_ID());
		printJobDetail.setIsActive(true);
		printJobDetail.setAD_PrinterRouting(routing);
		printJobDetail.setC_Print_Job_Line(printJobLine);

		InterfaceWrapperHelper.save(printJobDetail);

		return printJobDetail;
	}

	private List<I_AD_PrinterRouting> findPrinterRoutings(final I_C_Printing_Queue item)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(item);

		final int AD_Process_ID = item.getAD_Process_ID();
		final int C_DocType_ID = item.getC_DocType_ID();

		final int AD_Client_ID = item.getAD_Client_ID();
		final int AD_Org_ID = item.getAD_Org_ID();
		final int AD_Role_ID = item.getAD_Role_ID();
		final int AD_User_ID = item.getAD_User_ID();

		final List<I_AD_PrinterRouting> rs = Services.get(IPrinterRoutingDAO.class).fetchPrinterRoutings(ctx,
				AD_Client_ID, AD_Org_ID,
				AD_Role_ID, AD_User_ID,
				C_DocType_ID,
				AD_Process_ID,
				null, // printerType
				I_AD_PrinterRouting.class);

		return rs;
	}

	@Override
	public String getSummary(final I_C_Print_Job printJob)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(printJob);
		return Services.get(IMsgBL.class).translate(ctx, I_C_Print_Job.COLUMNNAME_C_Print_Job_ID)
				+ " "
				+ printJob.getC_Print_Job_ID();
	}
	
	@Override
	public void enquePrintJobInstructions(final I_C_Print_Job_Instructions jobInstructions, final I_C_Async_Batch asyncBatch)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(jobInstructions);
		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, PDFDocPrintingWorkpackageProcessor.class);
		I_C_Queue_Block queueBlock = null;

		if (queueBlock == null)
		{
			queueBlock = queue.enqueueBlock(ctx);
		}

		final I_C_Queue_WorkPackage queueWorkpackage = queue.enqueueWorkPackage(queueBlock, IWorkPackageQueue.PRIORITY_AUTO); // priority=null=Auto/Default

		// set the async batch in workpackage in order to track it
		queueWorkpackage.setC_Async_Batch(asyncBatch);
		Services.get(IQueueDAO.class).saveInLocalTrx(queueWorkpackage);

		queue.enqueueElement(queueWorkpackage, jobInstructions);
		queue.markReadyForProcessing(queueWorkpackage);
	}
}

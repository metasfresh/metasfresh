package de.metas.printing.async.spi.impl;

import ch.qos.logback.classic.Level;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.attachments.AttachmentEntryService;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.printing.api.IPrintingDAO;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.archive.api.IArchiveBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * This processor process the enqueued <code>I_C_Printing_Queue</code>
 *
 * <ul> for each C_Printing_Queue selection</ul>
 * <ul> fetches the PDF files</ul>
 * <ul> and concatenates all pdf in one big pdf file per selection</ul>
 *
 * @author cg
 */
public class PrintingQueuePDFConcatenateWorkpackageProcessor implements IWorkpackageProcessor
{
	private final Logger logger = LogManager.getLogger(PrintingQueuePDFConcatenateWorkpackageProcessor.class);

	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
	private final IPrintingDAO dao = Services.get(IPrintingDAO.class);
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IArchiveBL archiveBl = Services.get(IArchiveBL.class);

	private I_C_Async_Batch asyncBatch;

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName_NOTUSED)
	{
		File outputFile = null;
		try
		{
			outputFile = concatenateFiles(workpackage);
		}
		catch (IOException e)
		{
			Loggables.withLogger(logger, Level.ERROR).addLog(e.getLocalizedMessage());
		}

		attachmentEntryService.createNewAttachment(workpackage.getC_Async_Batch(), outputFile);
		return Result.SUCCESS;
	}

	private File concatenateFiles(final I_C_Queue_WorkPackage workpackage) throws IOException
	{
		this.asyncBatch = workpackage.getC_Async_Batch();
		Check.assumeNotNull(asyncBatch, "Async batch is not null");

		final String fileName = "PDF_" + asyncBatch.getC_Async_Batch_ID();

		final File file = File.createTempFile(fileName, ".pdf");
		final Document document = new Document();
		final FileOutputStream fos = new FileOutputStream(file, false);

		try
		{
			final PdfCopy copy = new PdfCopy(document, fos);

			document.open();

			final List<I_C_Printing_Queue> pqs = queueDAO.retrieveAllItems(workpackage, I_C_Printing_Queue.class);

			for (final I_C_Printing_Queue pq : pqs)
			{
				try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(pq))
				{
					if (pq.isProcessed())
					{
						Loggables.withLogger(logger, Level.DEBUG).addLog("*** Printing queue is already processed. Skipping it: {}", pq);
						continue;
					}
					final I_AD_Archive archive = pq.getAD_Archive();
					Check.assumeNotNull(archive, "Archive references an AD_Archive record");

					final byte[] data = archiveBl.getBinaryData(archive);
					final PdfReader reader = new PdfReader(data);

					for (int page = 0; page < reader.getNumberOfPages(); )
					{
						copy.addPage(copy.getImportedPage(reader, ++page));
					}
					copy.freeReader(reader);
					reader.close();

					printingQueueBL.setProcessedAndSave(pq);
				}
			}

		}
		catch (IOException | DocumentException e)
		{
			Loggables.withLogger(logger, Level.ERROR).addLog(e.getLocalizedMessage());
		}
		finally
		{
			document.close();
			fos.close();
			return file;
		}

	}
}

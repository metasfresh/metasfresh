package de.metas.printing.async.spi.impl;

import ch.qos.logback.classic.Level;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BadPdfFormatException;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;
import de.metas.async.api.IQueueDAO;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.attachments.AttachmentEntryService;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.printing.api.IPrintingQueueBL;
import de.metas.printing.model.I_C_Printing_Queue;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
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
	private final IQueueDAO queueDAO = Services.get(IQueueDAO.class);
	private final IPrintingQueueBL printingQueueBL = Services.get(IPrintingQueueBL.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	@Override
	public Result processWorkPackage(
			final @NonNull I_C_Queue_WorkPackage workpackage,
			final String localTrxName_NOTUSED)
	{
		try
		{
			final File outputFile = concatenateFiles(workpackage);
			if (workpackage.getC_Async_Batch_ID() > 0)
			{
				attachmentEntryService.createNewAttachment(workpackage.getC_Async_Batch(), outputFile);
			}
			else
			{
				Loggables.withLogger(logger, Level.WARN).addLog("C_Queue_WorkPackage_ID={} has no C_Async_Batch; -> attaching the file to the workpackage instead of any C_Async_Batch", workpackage.getC_Queue_WorkPackage_ID());
				attachmentEntryService.createNewAttachment(workpackage, outputFile);
			}
		}
		catch (final Exception ex)
		{
			logger.warn("Failed concatenating files", ex);
			Loggables.withLogger(logger, Level.ERROR).addLog(ex.getLocalizedMessage());
		}

		return Result.SUCCESS;
	}

	private File concatenateFiles(final I_C_Queue_WorkPackage workpackage) throws IOException, DocumentException
	{
		final File file = createNewTemporaryPDFFile(workpackage);
		final Document document = new Document();

		try (final FileOutputStream fos = new FileOutputStream(file, false))
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

					final PdfReader reader = getPdfReader(pq);
					appendToPdf(copy, reader);

					copy.freeReader(reader);
					reader.close();

					printingQueueBL.setProcessedAndSave(pq);
				}
			}
			return file;
		}
		finally
		{
			document.close();
		}
	}

	@NonNull
	private File createNewTemporaryPDFFile(final I_C_Queue_WorkPackage workpackage)
	{
		final I_C_Async_Batch asyncBatch = workpackage.getC_Async_Batch();
		Check.assumeNotNull(asyncBatch, "Async batch is not null");
		final String fileName = "PDF_" + asyncBatch.getC_Async_Batch_ID();

		try
		{
			return File.createTempFile(fileName, ".pdf");
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Failed to create temporary file with prefix: " + fileName, ex);
		}
	}

	@NonNull
	private PdfReader getPdfReader(final I_C_Printing_Queue pq)
	{
		final I_AD_Archive archive = pq.getAD_Archive();
		Check.assumeNotNull(archive, "Archive references an AD_Archive record");

		final byte[] data = archiveBL.getBinaryData(archive);

		try
		{
			return new PdfReader(data);
		}
		catch (final IOException e)
		{
			throw new AdempiereException("Failed to create PDF reader from archive=" + archive + ", printing queue=" + pq, e);
		}
	}

	private void appendToPdf(final PdfCopy pdf, final PdfReader from)
	{
		try
		{
			for (int page = 0; page < from.getNumberOfPages(); )
			{
				pdf.addPage(pdf.getImportedPage(from, ++page));
			}
		}
		catch (final IOException | BadPdfFormatException e)
		{
			throw new AdempiereException("Failed appending to pdf=" + pdf + " from " + from, e);
		}
	}
}

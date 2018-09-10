/**
 * 
 */
package de.metas.dunning.process;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import de.metas.dunning.DunningDocId;
import de.metas.dunning.invoice.DunningService;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessPreconditionsResolution;
import lombok.NonNull;

/**
 * 
 * Concatenates dunning pdf with invoices pdfs
 * 
 * @author metas-dev <dev@metasfresh.com>
 * 
 */
public class ConcatenateDunningAndInvoicesPDFs extends JavaProcess implements IProcessPrecondition
{
	private final static String MSG_NOARCHIVE = "ArchivedNone";
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	final IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	private final String contentType = "application/pdf";

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(
			@NonNull final IProcessPreconditionsContext context)
	{

		final List<I_AD_Archive> archives = archiveDAO.retrieveLastArchives(getCtx(), TableRecordReference.of(context.getSelectedModel(I_C_DunningDoc.class)), 1);
		if (archives.isEmpty())
		{
			return ProcessPreconditionsResolution.reject(msgBL.translatable(MSG_NOARCHIVE));
		}

		return ProcessPreconditionsResolution
				.acceptIf(I_C_DunningDoc.Table_Name.equals(context.getTableName()));
	}

	@Override
	protected String doIt() throws Exception
	{
		final DunningService dunningService = Adempiere.getBean(DunningService.class);
		final List<I_C_Invoice> dunnedInvoices = dunningService.retrieveDunnedInvoices(DunningDocId.ofRepoId(getRecord_ID()));
		final byte[] data = concatenate(dunnedInvoices);

		getResult().setReportData(data, String.valueOf(getRecord_ID()), contentType);

		return "OK";
	}

	private byte[] concatenate(@NonNull final List<I_C_Invoice> dunnedInvoices) throws Exception
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		print(out, dunnedInvoices);
		return out.toByteArray();
	}

	private void print(@NonNull final OutputStream bos, @NonNull final List<I_C_Invoice> dunnedInvoices) throws Exception
	{
		final Document document = new Document();

		final PdfCopy copy = new PdfCopy(document, bos);

		document.open();

		try
		{
			final List<I_AD_Archive> dunningArchives = archiveDAO.retrieveLastArchives(getCtx(), TableRecordReference.of(getRecord(I_C_DunningDoc.class)), 1);

			// get dunning data
			final byte[] dunningData = archiveBL.getBinaryData(dunningArchives.get(0));
			PdfReader reader = new PdfReader(dunningData);

			for (int page = 0; page < reader.getNumberOfPages();)
			{
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			copy.freeReader(reader);
			reader.close();

			for (final I_C_Invoice invoice : dunnedInvoices)
			{

				final List<I_AD_Archive> invoiceArchives = archiveDAO.retrieveLastArchives(getCtx(), TableRecordReference.of(invoice), 1);
				if (invoiceArchives.isEmpty())
				{
					continue;
				}
				
				final byte[] data = archiveBL.getBinaryData(invoiceArchives.get(0));

				reader = new PdfReader(data);

				for (int page = 0; page < reader.getNumberOfPages();)
				{
					copy.addPage(copy.getImportedPage(reader, ++page));
				}
				copy.freeReader(reader);
				reader.close();
			}
			document.close();

		}
		catch (Exception e)
		{
			throw new AdempiereException(e.getLocalizedMessage());
		}
	}

}

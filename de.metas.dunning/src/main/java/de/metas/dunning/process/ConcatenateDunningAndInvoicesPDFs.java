/**
 * 
 */
package de.metas.dunning.process;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.List;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Invoice;

import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfReader;

import de.metas.document.archive.model.IArchiveAware;
import de.metas.document.archive.model.I_AD_Archive;
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
	private final String contentType = "application/pdf";

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(
			@NonNull final IProcessPreconditionsContext context)
	{
		final Object model = context.getSelectedModel(Object.class);
		final IArchiveAware archiveAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(model, IArchiveAware.class);
		if (archiveAware == null)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_NOARCHIVE));
		}

		final int archiveId = archiveAware.getAD_Archive_ID();
		if (archiveId <= 0)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(MSG_NOARCHIVE));
		}

		return ProcessPreconditionsResolution
				.acceptIf(I_C_DunningDoc.Table_Name.equals(getTableName()));

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
			final IArchiveAware dunningArchiveAware = getRecord(IArchiveAware.class);
			final I_AD_Archive dunningArchive = dunningArchiveAware.getAD_Archive();

			// get dunning data
			final byte[] dunningData = archiveBL.getBinaryData(dunningArchive);
			PdfReader reader = new PdfReader(dunningData);

			for (int page = 0; page < reader.getNumberOfPages();)
			{
				copy.addPage(copy.getImportedPage(reader, ++page));
			}
			copy.freeReader(reader);
			reader.close();

			for (final I_C_Invoice invoice : dunnedInvoices)
			{

				final IArchiveAware archiveAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(invoice, IArchiveAware.class);
				final I_AD_Archive archive = archiveAware.getAD_Archive();

				final byte[] data = archiveBL.getBinaryData(archive);

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

package de.metas.global_qrcodes.service;

import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.process.AdProcessId;
import de.metas.report.DocumentReportFlavor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveRequest;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_AD_PInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalQRCodeService
{
	private static final AdProcessId qrCodeProcessId = AdProcessId.ofRepoId(584977); // hard coded process id

	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);

	public QRCodePDFResource createPDF(final List<PrintableQRCode> qrCodes)
	{
		return CreatePDFCommand.builder()
				.qrCodes(qrCodes)
				.build()
				.execute();
	}

	public void print(@NonNull final QRCodePDFResource pdf)
	{
		final ArchiveResult archiveResult = archiveBL.archive(ArchiveRequest.builder()
				.trxName(ITrx.TRXNAME_ThreadInherited)
				.flavor(DocumentReportFlavor.PRINT)
				.data(pdf)
				.archiveName(pdf.getFilename())
				.processId(qrCodeProcessId)
				.pinstanceId(pdf.getPinstanceId())
				.recordRef(TableRecordReference.of(I_AD_PInstance.Table_Name, pdf.getPinstanceId().getRepoId()))
				.isReport(true)
				.force(true)
				.save(false) // don't save it because we have to modify it afterwards anyway, so we will save it then
				.build());

		final I_AD_Archive archiveRecord = archiveResult.getArchiveRecord();
		if (archiveRecord == null)
		{
			throw new AdempiereException("Cannot archiveRecord report");
		}

		archiveRecord.setIsDirectEnqueue(true);
		archiveRecord.setIsDirectProcessQueueItem(true);
		InterfaceWrapperHelper.save(archiveRecord);
	}

}

package de.metas.global_qrcodes.service;

import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.printing.IMassPrintingService;
import de.metas.process.AdProcessId;
import lombok.NonNull;
import org.adempiere.archive.api.ArchiveInfo;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GlobalQRCodeService
{
	private static final AdProcessId qrCodeProcessId = AdProcessId.ofRepoId(584977); // hard coded process id

	private final IMassPrintingService massPrintingService;

	public GlobalQRCodeService(@NonNull final IMassPrintingService massPrintingService) {this.massPrintingService = massPrintingService;}

	public QRCodePDFResource createPDF(final List<PrintableQRCode> qrCodes)
	{
		return CreatePDFCommand.builder()
				.qrCodes(qrCodes)
				.build()
				.execute();
	}

	public void print(@NonNull final QRCodePDFResource pdf)
	{
		final TableRecordReference recordRef = TableRecordReference.of(I_AD_PInstance.Table_Name, pdf.getPinstanceId().getRepoId());
		final ArchiveInfo archiveInfo = new ArchiveInfo(pdf.getFilename(), recordRef);
		archiveInfo.setProcessId(qrCodeProcessId);
		archiveInfo.setPInstanceId(pdf.getPinstanceId());

		massPrintingService.print(pdf, archiveInfo);
	}

}

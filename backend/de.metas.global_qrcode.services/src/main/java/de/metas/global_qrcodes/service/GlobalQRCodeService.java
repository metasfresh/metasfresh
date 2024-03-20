package de.metas.global_qrcodes.service;

import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.printing.IMassPrintingService;
import de.metas.process.AdProcessId;
import de.metas.process.PInstanceId;
import de.metas.report.PrintCopies;
import lombok.NonNull;
import org.adempiere.archive.api.ArchiveInfo;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_PInstance;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.List;

@Service
public class GlobalQRCodeService
{
	private static final AdProcessId default_qrCodeProcessId = AdProcessId.ofRepoId(584977); // hard coded process id

	private final IMassPrintingService massPrintingService;

	public GlobalQRCodeService(@NonNull final IMassPrintingService massPrintingService) {this.massPrintingService = massPrintingService;}

	public QRCodePDFResource createPDF(@NonNull final PrintableQRCode qrCode)
	{
		return createPDF(ImmutableList.of(qrCode), null, default_qrCodeProcessId);
	}

	public QRCodePDFResource createPDF(@NonNull final PrintableQRCode qrCode, @NonNull final AdProcessId qrCodeProcessId)
	{
		return createPDF(ImmutableList.of(qrCode), null, qrCodeProcessId);
	}

	public QRCodePDFResource createPDF(@NonNull final List<PrintableQRCode> qrCodes)
	{
		return createPDF(qrCodes, null, default_qrCodeProcessId);
	}

	public QRCodePDFResource createPDF(@NonNull final List<PrintableQRCode> qrCodes,
									   @Nullable final PInstanceId pInstanceId,
									   @NonNull final AdProcessId qrCodeProcessId)
	{
		final QRCodePDFResource execute = CreatePDFCommand.builder()
				.qrCodes(qrCodes)
				.pInstanceId(pInstanceId)
				.qrCodeProcessId(qrCodeProcessId)
				.build()
				.execute();
		return execute;
	}

    public void print(@NonNull final QRCodePDFResource pdf)
    {
        print(pdf, PrintCopies.ONE);
    }

	public void print(@NonNull final QRCodePDFResource pdf, @NonNull final PrintCopies copies)
	{
		final TableRecordReference recordRef = TableRecordReference.of(I_AD_PInstance.Table_Name, pdf.getPinstanceId().getRepoId());
		final ArchiveInfo archiveInfo = new ArchiveInfo(pdf.getFilename(), recordRef);
		archiveInfo.setProcessId(pdf.getProcessId());
		archiveInfo.setPInstanceId(pdf.getPinstanceId());
		archiveInfo.setCopies(copies);

		massPrintingService.print(pdf, archiveInfo);
	}

}

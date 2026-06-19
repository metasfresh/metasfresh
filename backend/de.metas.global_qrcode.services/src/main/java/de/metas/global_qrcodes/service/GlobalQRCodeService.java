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
	/**
	 * Default QR-code report process used when no explicit {@link AdProcessId} is passed.
	 * <p>
	 * This service renders from the QR codes handed in <b>inline as JSON</b> ({@link CreatePDFCommand}
	 * passes {@code REPORT_PARAM_JSON_DATA}); the process MUST therefore be a {@code JasperReportsJSON}
	 * report. It needs no handling units and no DB selection.
	 * <p>
	 * Do NOT route an HU {@code JasperReportsSQL} label here (e.g. an {@code M_HU_Label_Config}
	 * {@code LabelReport_Process_ID} that queries {@code T_Selection} / {@code HU_Label(M_HU_ID, …)}):
	 * for a non-JSON process {@link CreatePDFCommand} only passes {@code AD_PInstance_ID} with nothing
	 * staged into {@code T_Selection}, so the report's query returns no rows and the PDF comes out
	 * EMPTY. Such config-driven HU labels belong to the post-receipt path (the manufacturing
	 * {@code PrintReceivedHUQRCodes} activity → {@code HUReportExecutor}), which provides the real HUs.
	 */
	private static final AdProcessId default_qrCodeProcessId = AdProcessId.ofRepoId(584977);

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
									   @Nullable final AdProcessId qrCodeProcessId)
	{
		return CreatePDFCommand.builder()
				.qrCodes(qrCodes)
				.pInstanceId(pInstanceId)
				.qrCodeProcessId(qrCodeProcessId != null ? qrCodeProcessId : default_qrCodeProcessId)
				.build()
				.execute();
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

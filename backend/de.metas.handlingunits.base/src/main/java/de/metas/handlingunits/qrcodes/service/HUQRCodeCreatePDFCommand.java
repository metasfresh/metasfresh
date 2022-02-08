package de.metas.handlingunits.qrcodes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter;
import de.metas.handlingunits.report.HUReportService;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.DocumentReportFlavor;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportConstants;
import de.metas.report.server.ReportResult;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.ArchiveRequest;
import org.adempiere.archive.api.ArchiveResult;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Archive;
import org.compiere.util.Env;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.util.List;

public class HUQRCodeCreatePDFCommand
{
	private final ImmutableList<HUQRCode> qrCodes;
	private final boolean sendToPrinter;

	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final HUReportService huReportService = HUReportService.get();

	@Builder
	private HUQRCodeCreatePDFCommand(
			@NonNull final List<HUQRCode> qrCodes,
			final boolean sendToPrinter)
	{
		Check.assumeNotEmpty(qrCodes, "qrCodes is not empty");
		this.qrCodes = ImmutableList.copyOf(qrCodes);
		this.sendToPrinter = sendToPrinter;
	}

	public Resource execute()
	{
		final String printableQRCodesJSON = toPrintableQRCodesJsonString(qrCodes);
		final ReportResult report = createPDF(printableQRCodesJSON);

		if(sendToPrinter)
		{
			print(report);
		}

		return new ByteArrayResource(report.getReportContent());
	}

	private static String toPrintableQRCodesJsonString(@NonNull final List<HUQRCode> qrCodes)
	{
		final JsonPrintableQRCodesList printableQRCodes = JsonPrintableQRCodesList.builder()
				.qrCodes(qrCodes.stream()
						.map(HUQRCodeCreatePDFCommand::toPrintableQRCode)
						.collect(ImmutableList.toImmutableList()))
				.build();

		try
		{
			return JsonObjectMapperHolder.sharedJsonObjectMapper().writeValueAsString(printableQRCodes);
		}
		catch (JsonProcessingException e)
		{
			throw new AdempiereException("Failed converting QR codes to JSON", e);
		}
	}

	private static PrintableQRCode toPrintableQRCode(final HUQRCode qrCode)
	{
		return PrintableQRCode.builder()
				.topText(extractPrintableTopText(qrCode))
				.bottomText(extractPrintableBottomText(qrCode))
				.qrCode(HUQRCodeJsonConverter.toGlobalQRCode(qrCode).getAsString())
				.build();
	}

	private static String extractPrintableTopText(final HUQRCode qrCode)
	{
		final StringBuilder result = new StringBuilder();
		result.append(qrCode.getProduct().getCode());
		result.append(" - ");
		result.append(qrCode.getProduct().getName());

		for (final HUQRCodeAttribute attribute : qrCode.getAttributes())
		{
			final String displayValue = StringUtils.trimBlankToNull(attribute.getValueRendered());
			if (displayValue != null)
			{
				result.append(", ").append(displayValue);
			}
		}

		return result.toString();
	}

	private static String extractPrintableBottomText(final HUQRCode qrCode)
	{
		return qrCode.getPackingInfo().getHuUnitType().getShortDisplayName() + " ..." + qrCode.getId().getDisplayableSuffix();
	}

	public ReportResult createPDF(@NonNull final String printableQRCodesJSON)
	{
		final AdProcessId adProcessId = huReportService.retrieveHUJsontLabelProcessIdOrNull();
		if (adProcessId == null)
		{
			throw new AdempiereException("HU json label process does not exist");
		}

		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(
				PInstanceRequest.builder()
						.processId(adProcessId)
						.processParams(ImmutableList.of(ProcessInfoParameter.of(ReportConstants.REPORT_PARAM_JSON_DATA, printableQRCodesJSON)))
						.build());

		final ProcessInfo reportProcessInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(adProcessId)
				.setPInstanceId(pinstanceId)
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		return ReportsClient.get().report(reportProcessInfo);
	}

	private void print(@NonNull final ReportResult report)
	{
		final ArchiveResult archiveResult = archiveBL.archive(ArchiveRequest.builder()
				.trxName(ITrx.TRXNAME_ThreadInherited)
				.flavor(DocumentReportFlavor.PRINT)
				.data(new ByteArrayResource(report.getReportContent()))
				.archiveName(report.getReportFilename())
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

	/**
	 * The structure of this class it's important for the jasper report, so pls don't change it!
	 */
	@Value
	@Builder
	@Jacksonized
	public static class JsonPrintableQRCodesList
	{
		@NonNull
		List<PrintableQRCode> qrCodes;
	}
}

package de.metas.global_qrcodes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportConstants;
import de.metas.report.server.ReportResult;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;

import java.util.List;

public class CreatePDFCommand
{
	private static final AdProcessId qrCodeProcessId = AdProcessId.ofRepoId(584977); // hard coded process id
	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final ImmutableList<PrintableQRCode> qrCodes;

	@Builder
	private CreatePDFCommand(
			@NonNull final List<PrintableQRCode> qrCodes)
	{
		Check.assumeNotEmpty(qrCodes, "qrCodes is not empty");
		this.qrCodes = ImmutableList.copyOf(qrCodes);
	}

	public QRCodePDFResource execute()
	{
		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(
				PInstanceRequest.builder()
						.processId(qrCodeProcessId)
						.processParams(ImmutableList.of(
								ProcessInfoParameter.of(ReportConstants.REPORT_PARAM_JSON_DATA, toJsonString(qrCodes))
						))
						.build());

		final ProcessInfo reportProcessInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(qrCodeProcessId)
				.setPInstanceId(pinstanceId)
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportResult report = ReportsClient.get().report(reportProcessInfo);

		return QRCodePDFResource.builder()
				.data(report.getReportContent())
				.filename(report.getReportFilename())
				.pinstanceId(pinstanceId)
				.build();
	}

	private static String toJsonString(@NonNull final List<PrintableQRCode> qrCodes)
	{
		try
		{
			return JsonObjectMapperHolder
					.sharedJsonObjectMapper()
					.writeValueAsString(JsonPrintableQRCodesList.builder()
							.qrCodes(qrCodes)
							.build());
		}
		catch (final JsonProcessingException e)
		{
			throw new AdempiereException("Failed converting QR codes to JSON: " + qrCodes, e);
		}
	}
}

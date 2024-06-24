package de.metas.global_qrcodes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.process.AdProcessId;
import de.metas.process.IADPInstanceDAO;
import de.metas.process.IADProcessDAO;
import de.metas.process.PInstanceId;
import de.metas.process.PInstanceRequest;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.ProcessType;
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

import javax.annotation.Nullable;
import java.util.List;

public class CreatePDFCommand
{

	private final IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);
	private final ImmutableList<PrintableQRCode> qrCodes;
	private final PInstanceId pInstanceId;
	private final AdProcessId qrCodeProcessId ;

	@Builder
	private CreatePDFCommand(
			@NonNull final List<PrintableQRCode> qrCodes,
			@Nullable final PInstanceId pInstanceId,
			@NonNull final AdProcessId qrCodeProcessId)
	{
		Check.assumeNotEmpty(qrCodes, "qrCodes is not empty");
		this.qrCodes = ImmutableList.copyOf(qrCodes);
		this.pInstanceId = pInstanceId;
		this.qrCodeProcessId = qrCodeProcessId;

	}

	public QRCodePDFResource execute()
	{
		ImmutableList<ProcessInfoParameter> processParams;
		final ProcessType processType = adProcessDAO.retrieveProcessType (qrCodeProcessId);
		if (processType.isJasperJSON())
		{

			processParams = ImmutableList.of(
					ProcessInfoParameter.of(ReportConstants.REPORT_PARAM_JSON_DATA, toJsonString(qrCodes)));
		}
		else
		{
			processParams = ImmutableList.of(
					ProcessInfoParameter.of("AD_PInstance_ID", pInstanceId));

		}

		final PInstanceId processPInstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(
				PInstanceRequest.builder()
						.processId(qrCodeProcessId)
						.processParams(processParams)
						.build());

		final ProcessInfo reportProcessInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(qrCodeProcessId)
				.setPInstanceId(processPInstanceId)
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportResult report = ReportsClient.get().report(reportProcessInfo);

		return QRCodePDFResource.builder()
				.data(report.getReportContent())
				.filename(report.getReportFilename())
				.pinstanceId(processPInstanceId)
				.processId(qrCodeProcessId)
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

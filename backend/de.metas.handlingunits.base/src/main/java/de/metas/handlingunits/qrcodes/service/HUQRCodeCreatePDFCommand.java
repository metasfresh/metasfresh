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
import de.metas.process.ProcessInfo;
import de.metas.report.client.ReportsClient;
import de.metas.report.server.OutputType;
import de.metas.report.server.ReportResult;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.Env;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.stream.Collectors;

public class HUQRCodeCreatePDFCommand
{
	private final ImmutableList<HUQRCode> qrCodes;
	private final boolean sendToPrinter;

	final private IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	final private HUReportService huReportService = HUReportService.get();

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

		// TODO:
		// 1. generate labels for those printableQRCodes
		// 2. If sendToPrinter is true then also send the PDF to mass printing
		System.out.println("printableQRCodesJSON: " + printableQRCodesJSON);
		System.out.println("QR Code: " + qrCodes.stream()
				.map(HUQRCodeCreatePDFCommand::toPrintableQRCode)
				.map(PrintableQRCode::getQrCode)
				.collect(Collectors.joining("\n\t")));
		System.out.println("sendToPrinter: " + sendToPrinter);
		throw new UnsupportedOperationException("not implemented yet");
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

	private ReportResult printLabel()
	{
		final AdProcessId adProcessId = huReportService.retrievePrintFinishedGoodsLabelProcessIdOrNull();
		if (adProcessId == null)
		{
			throw new AdempiereException("HU json label process does not exist");
		}

		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(adProcessId)
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportsClient reportsClient = ReportsClient.get();

		return reportsClient.report(jasperProcessInfo);
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

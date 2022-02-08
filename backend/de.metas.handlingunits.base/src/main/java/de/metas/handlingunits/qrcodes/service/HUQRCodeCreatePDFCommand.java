package de.metas.handlingunits.qrcodes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.async.AsyncBatchId;
import de.metas.common.util.CoalesceUtil;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.handlingunits.IHUAndItemsDAO;
import de.metas.handlingunits.impl.HUAndItemsDAO;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter;
import de.metas.handlingunits.report.HUReportService;
import de.metas.printing.model.I_AD_Archive;
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
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveStorageFactory;
import org.adempiere.archive.spi.IArchiveStorage;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.ITableRecordReference;
import org.compiere.util.Env;
import org.compiere.util.MimeType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class HUQRCodeCreatePDFCommand
{
	private final ImmutableList<HUQRCode> qrCodes;
	private final boolean sendToPrinter;

	final private IADPInstanceDAO adPInstanceDAO = Services.get(IADPInstanceDAO.class);
	final private HUReportService huReportService = HUReportService.get();
	private final IHUAndItemsDAO huDao = HUAndItemsDAO.instance;

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
		final ReportResult label =  printLabel(printableQRCodesJSON, sendToPrinter);

		return new ByteArrayResource(label.getReportContent());
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

	private ReportResult printLabel(@NonNull final String printableQRCodesJSON, final boolean sendToPrinter)
	{
		final AdProcessId adProcessId = huReportService.retrieveHUJsontLabelProcessIdOrNull();
		if (adProcessId == null)
		{
			throw new AdempiereException("HU json label process does not exist");
		}

		final PInstanceRequest pinstanceRequest = createPInstanceRequest(adProcessId, printableQRCodesJSON);
		final PInstanceId pinstanceId = adPInstanceDAO.createADPinstanceAndADPInstancePara(pinstanceRequest);


		final ProcessInfo jasperProcessInfo = ProcessInfo.builder()
				.setCtx(Env.getCtx())
				.setAD_Process_ID(adProcessId)
				.setAD_PInstance(adPInstanceDAO.getById(pinstanceId))
				.setJRDesiredOutputType(OutputType.PDF)
				.build();

		final ReportsClient reportsClient = ReportsClient.get();
		final ReportResult label = reportsClient.report(jasperProcessInfo);

		if (sendToPrinter)
		{
			archive(jasperProcessInfo, label);
		}

		return label;
	}

	private String buildFilename(@NonNull final ProcessInfo processInfo)
	{
		final String instance = String.valueOf(processInfo.getAdProcessId().getRepoId());
		final String title = processInfo.getTitle();

		return Joiner.on("_").skipNulls().join(instance, title) + ".pdf";
	}

	private void archive(@NonNull final ProcessInfo processInfo, @NonNull final ReportResult label)
	{
		final IArchiveStorageFactory archiveStorageFactory = Services.get(IArchiveStorageFactory.class);

		final String fileExtWithDot = MimeType.getExtensionByType(OutputType.PDF.getContentType());
		final String fileName = buildFilename(processInfo) + fileExtWithDot;
		final byte[] labelData = label.getReportContent();

		final Properties ctx = Env.getCtx();
		final IArchiveStorage archiveStorage = archiveStorageFactory.getArchiveStorage(ctx);
		final I_AD_Archive archive = InterfaceWrapperHelper.create(archiveStorage.newArchive(ctx, ITrx.TRXNAME_ThreadInherited), I_AD_Archive.class);

		archive.setName(fileName);
		archiveStorage.setBinaryData(archive, labelData);
		archive.setIsReport(true);
		archive.setIsDirectEnqueue(true);
		archive.setIsDirectProcessQueueItem(true);
		InterfaceWrapperHelper.save(archive);
	}

	private PInstanceRequest createPInstanceRequest(@NonNull final AdProcessId adProcessId, final String printableQRCodesJSON)
	{
		return PInstanceRequest.builder()
				.processId(adProcessId)
				.processParams(ImmutableList.of(
						ProcessInfoParameter.of(ReportConstants.REPORT_PARAM_JSON_DATA, printableQRCodesJSON)))
				.build();
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

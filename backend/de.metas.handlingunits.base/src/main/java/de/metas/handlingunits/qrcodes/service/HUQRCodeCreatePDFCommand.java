package de.metas.handlingunits.qrcodes.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.ImmutableList;
import de.metas.JsonObjectMapperHolder;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.json.JsonConverter;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.stream.Collectors;

public class HUQRCodeCreatePDFCommand
{
	private final ImmutableList<HUQRCode> qrCodes;
	private final boolean sendToPrinter;

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
		final ImmutableList<PrintableQRCode> printableQRCodes = qrCodes.stream()
				.map(HUQRCodeCreatePDFCommand::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());

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
				.qrCode(JsonConverter.toGlobalQRCode(qrCode).getAsString())
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
		return qrCode.getHuUnitType().getShortDisplayName() + " ..." + qrCode.getId().getDisplayableSuffix();
	}

}

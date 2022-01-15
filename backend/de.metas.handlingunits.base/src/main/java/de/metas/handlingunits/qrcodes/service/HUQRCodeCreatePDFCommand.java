package de.metas.handlingunits.qrcodes.service;

import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.json.JsonConverter;
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.springframework.core.io.Resource;

import java.util.List;

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
		final ImmutableList<PrintableQRCode> printableQRCodes = qrCodes.stream()
				.map(HUQRCodeCreatePDFCommand::toPrintableQRCode)
				.collect(ImmutableList.toImmutableList());

		// TODO: generate labels for those printableQRCodes
		// If sendToPrinter is true then also send the PDF to mass printing

		printableQRCodes.forEach(System.out::println);
		throw new UnsupportedOperationException("not implemented yet");
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

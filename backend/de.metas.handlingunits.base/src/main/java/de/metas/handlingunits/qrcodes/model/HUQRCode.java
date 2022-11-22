package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * @implNote See {@link HUQRCodeJsonConverter} for tools to convert from/to JSON, {@link de.metas.global_qrcodes.GlobalQRCode} etc.
 */
@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class HUQRCode
{
	@NonNull HUQRCodeUniqueId id;

	@NonNull HUQRCodePackingInfo packingInfo;
	@NonNull HUQRCodeProductInfo product;
	@NonNull ImmutableList<HUQRCodeAttribute> attributes;

	public static boolean equals(@Nullable final HUQRCode o1, @Nullable final HUQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	public static HUQRCode fromGlobalQRCodeJsonString(@NonNull final String qrCodeString)
	{
		return HUQRCodeJsonConverter.fromGlobalQRCodeJsonString(qrCodeString);
	}

	public static HUQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		return HUQRCodeJsonConverter.fromGlobalQRCode(globalQRCode);
	}

	@JsonIgnore
	public JsonDisplayableQRCode toRenderedJson()
	{
		return HUQRCodeJsonConverter.toRenderedJson(this);
	}

	public GlobalQRCode toGlobalQRCode()
	{
		return HUQRCodeJsonConverter.toGlobalQRCode(this);
	}

	public String toDisplayableQRCode()
	{
		return id.getDisplayableSuffix();
	}

	public PrintableQRCode toPrintableQRCode()
	{
		return PrintableQRCode.builder()
				.topText(extractPrintableTopText(this))
				.bottomText(extractPrintableBottomText(this))
				.qrCode(HUQRCodeJsonConverter.toGlobalQRCode(this).getAsString())
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
		return qrCode.getPackingInfo().getHuUnitType().getShortDisplayName() + " ..." + qrCode.toDisplayableQRCode();
	}
}

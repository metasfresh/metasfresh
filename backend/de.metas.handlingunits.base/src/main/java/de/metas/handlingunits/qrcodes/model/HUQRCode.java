package de.metas.handlingunits.qrcodes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.global_qrcodes.PrintableQRCode;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.qrcodes.model.json.HUQRCodeJsonConverter;
import de.metas.product.ProductId;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;
import org.adempiere.mm.attributes.AttributeCode;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

/**
 * @implNote See {@link HUQRCodeJsonConverter} for tools to convert from/to JSON, {@link de.metas.global_qrcodes.GlobalQRCode} etc.
 */
@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class HUQRCode implements IHUQRCode
{
	@NonNull HUQRCodeUniqueId id;

	@NonNull HUQRCodePackingInfo packingInfo;
	@NonNull HUQRCodeProductInfo product;
	@NonNull ImmutableList<HUQRCodeAttribute> attributes;

	public static boolean equals(@Nullable final HUQRCode o1, @Nullable final HUQRCode o2)
	{
		return Objects.equals(o1, o2);
	}

	@Override
	@Deprecated
	public String toString() {return toGlobalQRCodeString();}

	@NonNull
	public static HUQRCode fromGlobalQRCodeJsonString(@NonNull final String qrCodeString)
	{
		return HUQRCodeJsonConverter.fromGlobalQRCodeJsonString(qrCodeString);
	}

	@Nullable
	public static HUQRCode fromNullableGlobalQRCodeJsonString(@Nullable final String qrCodeString)
	{
		final String qrCodeStringNorm = StringUtils.trimBlankToNull(qrCodeString);
		return qrCodeStringNorm != null ? fromGlobalQRCodeJsonString(qrCodeStringNorm) : null;
	}

	public static boolean isHandled(@NonNull final GlobalQRCode globalQRCode) {return HUQRCodeJsonConverter.isHandled(globalQRCode);}

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

	public String toGlobalQRCodeString()
	{
		return HUQRCodeJsonConverter.toGlobalQRCodeJsonString(this);
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

	public static boolean isTypeMatching(@NonNull final GlobalQRCode globalQRCode)
	{
		return HUQRCodeJsonConverter.isTypeMatching(globalQRCode);
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

	public Optional<String> getAttributeValueAsString(@NonNull final AttributeCode attributeCode)
	{
		return getAttribute(attributeCode).map(HUQRCodeAttribute::getValue);
	}

	private Optional<HUQRCodeAttribute> getAttribute(@NonNull final AttributeCode attributeCode)
	{
		return attributes.stream().filter(attribute -> AttributeCode.equals(attribute.getCode(), attributeCode)).findFirst();
	}

	private static String extractPrintableBottomText(final HUQRCode qrCode)
	{
		return qrCode.getPackingInfo().getHuUnitType().getShortDisplayName() + " ..." + qrCode.toDisplayableQRCode();
	}

	public ProductId getProductId() {return getProduct().getId();}

	public HuPackingInstructionsId getPackingInstructionsId() {return getPackingInfo().getPackingInstructionsId();}
}

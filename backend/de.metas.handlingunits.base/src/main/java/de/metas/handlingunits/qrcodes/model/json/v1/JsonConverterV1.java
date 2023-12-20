package de.metas.handlingunits.qrcodes.model.json.v1;

import com.google.common.collect.ImmutableList;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodeAttribute;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeProductInfo;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.Objects;

/**
 * IMPORTANT: keep in sync with huQRCodes.js/parseQRCodePayload_HU_v1
 */
@UtilityClass
public class JsonConverterV1
{
	public static final GlobalQRCodeVersion GLOBAL_QRCODE_VERSION = GlobalQRCodeVersion.ofInt(1);

	public static GlobalQRCode toGlobalQRCode(@NonNull GlobalQRCodeType type, @NonNull final HUQRCode qrCode)
	{
		return GlobalQRCode.of(type, GLOBAL_QRCODE_VERSION, toJson(qrCode));
	}

	public static HUQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		Check.assumeEquals(globalQRCode.getVersion(), GLOBAL_QRCODE_VERSION, "HU QR Code version");

		final JsonHUQRCodeV1 json = globalQRCode.getPayloadAs(JsonHUQRCodeV1.class);
		return fromJson(json);
	}

	public static JsonHUQRCodeV1 toJson(@NonNull final HUQRCode qrCode)
	{
		return JsonHUQRCodeV1.builder()
				.id(qrCode.getId())
				.packingInfo(toJson(qrCode.getPackingInfo()))
				.product(toJson(qrCode.getProduct()))
				.attributes(qrCode.getAttributes()
						.stream()
						.map(JsonConverterV1::toJson)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static JsonHUQRCodePackingInfoV1 toJson(@NonNull final HUQRCodePackingInfo packingInfo)
	{
		return JsonHUQRCodePackingInfoV1.builder()
				.huUnitType(packingInfo.getHuUnitType())
				.packingInstructionsId(packingInfo.getPackingInstructionsId())
				.caption(packingInfo.getCaption())
				.build();
	}

	public static HUQRCode fromJson(@NonNull final JsonHUQRCodeV1 json)
	{
		return HUQRCode.builder()
				.id(json.getId())
				.packingInfo(fromJson(json.getPackingInfo()))
				.product(fromJson(json.getProduct()))
				.attributes(json.getAttributes()
						.stream()
						.map(JsonConverterV1::fromJson)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	private static HUQRCodePackingInfo fromJson(@NonNull final JsonHUQRCodePackingInfoV1 json)
	{
		return HUQRCodePackingInfo.builder()
				.huUnitType(json.getHuUnitType())
				.packingInstructionsId(json.getPackingInstructionsId())
				.caption(json.getCaption())
				.build();
	}

	private static JsonHUQRCodeProductInfoV1 toJson(@NonNull final HUQRCodeProductInfo product)
	{
		return JsonHUQRCodeProductInfoV1.builder()
				.id(product.getId())
				.code(product.getCode())
				.name(product.getName())
				.build();
	}

	private static HUQRCodeProductInfo fromJson(@NonNull final JsonHUQRCodeProductInfoV1 json)
	{
		return HUQRCodeProductInfo.builder()
				.id(json.getId())
				.code(json.getCode())
				.name(json.getName())
				.build();
	}

	private static JsonHUQRCodeAttributeV1 toJson(@NonNull final HUQRCodeAttribute attribute)
	{
		return JsonHUQRCodeAttributeV1.builder()
				.code(attribute.getCode())
				.displayName(attribute.getDisplayName())
				.value(attribute.getValue())
				// NOTE: in order to make the generated JSON shorter,
				// we will set valueRendered only if it's different from value.
				.valueRendered(
						!Objects.equals(attribute.getValue(), attribute.getValueRendered())
								? attribute.getValueRendered()
								: null)
				.build();
	}

	private static HUQRCodeAttribute fromJson(@NonNull final JsonHUQRCodeAttributeV1 json)
	{
		return HUQRCodeAttribute.builder()
				.code(json.getCode())
				.displayName(json.getDisplayName())
				.value(json.getValue())
				.valueRendered(json.getValueRendered())
				.build();
	}

}

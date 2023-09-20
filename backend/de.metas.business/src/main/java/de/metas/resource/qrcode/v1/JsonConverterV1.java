package de.metas.resource.qrcode.v1;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.product.ResourceId;
import de.metas.resource.qrcode.ResourceQRCode;
import de.metas.resource.qrcode.ResourceQRCodeJsonConverter;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonConverterV1
{
	public static final GlobalQRCodeVersion GLOBAL_QRCODE_VERSION = GlobalQRCodeVersion.ofInt(1);

	public static GlobalQRCode toGlobalQRCode(@NonNull final ResourceQRCode qrCode)
	{
		return GlobalQRCode.of(ResourceQRCodeJsonConverter.GLOBAL_QRCODE_TYPE, GLOBAL_QRCODE_VERSION, toJson(qrCode));
	}

	private static JsonResourceQRCodePayloadV1 toJson(@NonNull final ResourceQRCode qrCode)
	{
		return JsonResourceQRCodePayloadV1.builder()
				.id(qrCode.getResourceId().getRepoId())
				.type(qrCode.getResourceType())
				.caption(qrCode.getCaption())
				.build();
	}

	public static ResourceQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		Check.assumeEquals(globalQRCode.getVersion(), GLOBAL_QRCODE_VERSION, "QR Code version");
		final JsonResourceQRCodePayloadV1 payload = globalQRCode.getPayloadAs(JsonResourceQRCodePayloadV1.class);
		return fromJson(payload);
	}

	private static ResourceQRCode fromJson(@NonNull final JsonResourceQRCodePayloadV1 json)
	{
		return ResourceQRCode.builder()
				.resourceId(ResourceId.ofRepoId(json.getId()))
				.resourceType(json.getType())
				.caption(json.getCaption())
				.build();
	}
}

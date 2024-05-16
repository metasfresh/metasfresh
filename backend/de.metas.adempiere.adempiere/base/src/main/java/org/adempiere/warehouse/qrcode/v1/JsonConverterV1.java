package org.adempiere.warehouse.qrcode.v1;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.qrcode.LocatorQRCode;
import org.adempiere.warehouse.qrcode.LocatorQRCodeJsonConverter;

@UtilityClass
public class JsonConverterV1
{
	public static final GlobalQRCodeVersion GLOBAL_QRCODE_VERSION = GlobalQRCodeVersion.ofInt(1);

	public static GlobalQRCode toGlobalQRCode(@NonNull final LocatorQRCode qrCode)
	{
		return GlobalQRCode.of(LocatorQRCodeJsonConverter.GLOBAL_QRCODE_TYPE, GLOBAL_QRCODE_VERSION, toJson(qrCode));
	}

	private static JsonLocatorQRCodePayloadV1 toJson(@NonNull final LocatorQRCode qrCode)
	{
		return JsonLocatorQRCodePayloadV1.builder()
				.warehouseId(qrCode.getLocatorId().getWarehouseId().getRepoId())
				.locatorId(qrCode.getLocatorId().getRepoId())
				.caption(qrCode.getCaption())
				.build();
	}

	public static LocatorQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		Check.assumeEquals(globalQRCode.getVersion(), GLOBAL_QRCODE_VERSION, "QR Code version");
		final JsonLocatorQRCodePayloadV1 payload = globalQRCode.getPayloadAs(JsonLocatorQRCodePayloadV1.class);
		return fromJson(payload);
	}

	private static LocatorQRCode fromJson(@NonNull final JsonLocatorQRCodePayloadV1 json)
	{
		return LocatorQRCode.builder()
				.locatorId(LocatorId.ofRepoId(json.getWarehouseId(), json.getLocatorId()))
				.caption(json.getCaption())
				.build();
	}
}

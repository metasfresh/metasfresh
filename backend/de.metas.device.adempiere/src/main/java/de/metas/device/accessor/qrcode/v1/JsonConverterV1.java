package de.metas.device.accessor.qrcode.v1;

import de.metas.device.accessor.DeviceId;
import de.metas.device.accessor.qrcode.DeviceQRCode;
import de.metas.device.accessor.qrcode.DeviceQRCodeJsonConverter;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.util.Check;
import lombok.NonNull;

public class JsonConverterV1
{
	public static final GlobalQRCodeVersion GLOBAL_QRCODE_VERSION = GlobalQRCodeVersion.ofInt(1);
	
	public static GlobalQRCode toGlobalQRCode(@NonNull final DeviceQRCode qrCode)
	{
		return GlobalQRCode.of(DeviceQRCodeJsonConverter.GLOBAL_QRCODE_TYPE, GLOBAL_QRCODE_VERSION, toJson(qrCode));
	}

	private static JsonDeviceQRCodePayloadV1 toJson(@NonNull final DeviceQRCode qrCode)
	{
		return JsonDeviceQRCodePayloadV1.builder()
				.deviceId(qrCode.getDeviceId().getAsString())
				.caption(qrCode.getCaption())
				.build();
	}

	public static DeviceQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		Check.assumeEquals(globalQRCode.getVersion(), GLOBAL_QRCODE_VERSION, "QR Code version");
		final JsonDeviceQRCodePayloadV1 payload = globalQRCode.getPayloadAs(JsonDeviceQRCodePayloadV1.class);
		return fromJson(payload);
	}

	private static DeviceQRCode fromJson(@NonNull final JsonDeviceQRCodePayloadV1 json)
	{
		return DeviceQRCode.builder()
				.deviceId(DeviceId.ofString(json.getDeviceId()))
				.caption(json.getCaption())
				.build();
	}
}

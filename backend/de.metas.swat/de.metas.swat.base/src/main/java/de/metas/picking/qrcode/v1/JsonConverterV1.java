package de.metas.picking.qrcode.v1;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.picking.api.PickingSlotId;
import de.metas.picking.qrcode.PickingSlotQRCode;
import de.metas.picking.qrcode.PickingSlotQRCodeJsonConverter;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonConverterV1
{
	public static final GlobalQRCodeVersion GLOBAL_QRCODE_VERSION = GlobalQRCodeVersion.ofInt(1);

	public static GlobalQRCode toGlobalQRCode(@NonNull final PickingSlotQRCode qrCode)
	{
		return GlobalQRCode.of(PickingSlotQRCodeJsonConverter.GLOBAL_QRCODE_TYPE, GLOBAL_QRCODE_VERSION, toJson(qrCode));
	}

	private static JsonPickingSlotQRCodePayloadV1 toJson(@NonNull final PickingSlotQRCode qrCode)
	{
		return JsonPickingSlotQRCodePayloadV1.builder()
				.pickingSlotId(qrCode.getPickingSlotId().getRepoId())
				.caption(qrCode.getCaption())
				.build();
	}

	public static PickingSlotQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		Check.assumeEquals(globalQRCode.getVersion(), GLOBAL_QRCODE_VERSION, "QR Code version");
		final JsonPickingSlotQRCodePayloadV1 payload = globalQRCode.getPayloadAs(JsonPickingSlotQRCodePayloadV1.class);
		return fromJson(payload);
	}

	private static PickingSlotQRCode fromJson(@NonNull final JsonPickingSlotQRCodePayloadV1 json)
	{
		return PickingSlotQRCode.builder()
				.pickingSlotId(PickingSlotId.ofRepoId(json.getPickingSlotId()))
				.caption(json.getCaption())
				.build();
	}
}

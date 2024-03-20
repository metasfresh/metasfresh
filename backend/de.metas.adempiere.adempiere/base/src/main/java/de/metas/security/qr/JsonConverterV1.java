package de.metas.security.qr;

import de.metas.common.rest_api.common.JsonMetasfreshId;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.user.UserId;
import de.metas.util.Check;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonConverterV1
{
	public static final GlobalQRCodeVersion GLOBAL_QRCODE_VERSION = GlobalQRCodeVersion.ofInt(1);

	@NonNull
	public static GlobalQRCode toGlobalQRCode(@NonNull final UserAuthQRCode qrCode)
	{
		return GlobalQRCode.of(UserAuthQRCodeJsonConverter.GLOBAL_QRCODE_TYPE, GLOBAL_QRCODE_VERSION, toJson(qrCode));
	}

	@NonNull
	public static UserAuthQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		Check.assumeEquals(globalQRCode.getVersion(), GLOBAL_QRCODE_VERSION, "QR Code version");
		final JsonUserAuthQRCodePayloadV1 payload = globalQRCode.getPayloadAs(JsonUserAuthQRCodePayloadV1.class);
		return fromJson(payload);
	}

	@NonNull
	private static UserAuthQRCode fromJson(@NonNull final JsonUserAuthQRCodePayloadV1 json)
	{
		return UserAuthQRCode.builder()
				.token(json.getToken())
				.userId(UserId.ofRepoId(json.getUserId().getValue()))
				.build();
	}

	@NonNull
	private static JsonUserAuthQRCodePayloadV1 toJson(@NonNull final UserAuthQRCode qrCode)
	{
		return JsonUserAuthQRCodePayloadV1.builder()
				.token(qrCode.getToken())
				.userId(JsonMetasfreshId.of(qrCode.getUserId().getRepoId()))
				.build();
	}
}

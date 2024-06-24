package de.metas.handlingunits.qrcodes.model.json;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.global_qrcodes.JsonDisplayableQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.json.v1.JsonConverterV1;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class HUQRCodeJsonConverter
{
	private static final GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("HU");

	public static boolean isHandled(@NonNull final GlobalQRCode globalQRCode)
	{
		return GlobalQRCodeType.equals(GLOBAL_QRCODE_TYPE, globalQRCode.getType());
	}

	public static String toGlobalQRCodeJsonString(final HUQRCode qrCode)
	{
		return toGlobalQRCode(qrCode).getAsString();
	}

	public static GlobalQRCode toGlobalQRCode(final HUQRCode qrCode)
	{
		return JsonConverterV1.toGlobalQRCode(HUQRCodeJsonConverter.GLOBAL_QRCODE_TYPE, qrCode);
	}

	public static HUQRCode fromGlobalQRCodeJsonString(@NonNull final String qrCodeString)
	{
		return fromGlobalQRCode(GlobalQRCode.ofString(qrCodeString));
	}

	public static HUQRCode fromGlobalQRCode(final GlobalQRCode globalQRCode)
	{
		if (!isHandled(globalQRCode))
		{
			throw new AdempiereException("Invalid HU QR Code")
					.setParameter("globalQRCode", globalQRCode); // avoid adding it to error message, it might be quite long
		}

		//
		// IMPORTANT: keep in sync with huQRCodes.js
		//

		final GlobalQRCodeVersion version = globalQRCode.getVersion();
		if (GlobalQRCodeVersion.equals(globalQRCode.getVersion(), JsonConverterV1.GLOBAL_QRCODE_VERSION))
		{
			return JsonConverterV1.fromGlobalQRCode(globalQRCode);
		}
		else
		{
			throw new AdempiereException("Invalid HU QR Code version: " + version);
		}
	}

	public static JsonDisplayableQRCode toRenderedJson(@NonNull final HUQRCode huQRCode)
	{
		return JsonDisplayableQRCode.builder()
				.code(toGlobalQRCodeJsonString(huQRCode))
				.displayable(huQRCode.toDisplayableQRCode())
				.build();
	}

	public static boolean isTypeMatching(final @NonNull GlobalQRCode globalQRCode)
	{
		return GlobalQRCodeType.equals(GLOBAL_QRCODE_TYPE, globalQRCode.getType());
	}
}

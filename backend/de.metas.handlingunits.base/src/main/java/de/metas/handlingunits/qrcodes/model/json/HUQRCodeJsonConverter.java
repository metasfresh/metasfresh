package de.metas.handlingunits.qrcodes.model.json;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.json.v1.JsonConverterV1;
import de.metas.i18n.AdMessageKey;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class HUQRCodeJsonConverter
{
<<<<<<< HEAD
	public static GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("HU");
=======
	private static final AdMessageKey INVALID_QR_CODE_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.qrcodes.model.json.INVALID_QR_CODE_ERROR_MSG");
	private static final AdMessageKey INVALID_QR_VERSION_ERROR_MSG = AdMessageKey.of("de.metas.handlingunits.qrcodes.model.json.INVALID_QR_VERSION_ERROR_MSG");
	private static final GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("HU");

	public static boolean isHandled(@NonNull final GlobalQRCode globalQRCode)
	{
		return GlobalQRCodeType.equals(GLOBAL_QRCODE_TYPE, globalQRCode.getType());
	}
>>>>>>> 87370c761fe (MobileUI Picking - User Error Handling (#19112) (#19121))

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
		if (!GlobalQRCodeType.equals(GLOBAL_QRCODE_TYPE, globalQRCode.getType()))
		{
			throw new AdempiereException(INVALID_QR_CODE_ERROR_MSG)
					.setParameter("globalQRCode", globalQRCode); // avoid adding it to error message, it might be quite long
		}

		final GlobalQRCodeVersion version = globalQRCode.getVersion();
		if (GlobalQRCodeVersion.equals(globalQRCode.getVersion(), JsonConverterV1.GLOBAL_QRCODE_VERSION))
		{
			return JsonConverterV1.fromGlobalQRCode(globalQRCode);
		}
		else
		{
			throw new AdempiereException(INVALID_QR_VERSION_ERROR_MSG)
					.setParameter("version", version);
		}
	}

	public static JsonRenderedHUQRCode toRenderedJson(@NonNull final HUQRCode huQRCode)
	{
		return JsonRenderedHUQRCode.builder()
				.code(toGlobalQRCodeJsonString(huQRCode))
				.displayable(huQRCode.toDisplayableQRCode())
				.build();
	}
}

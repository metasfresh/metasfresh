package de.metas.device.accessor.qrcode;

import de.metas.device.accessor.qrcode.v1.JsonConverterV1;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class DeviceQRCodeJsonConverter
{
	public static GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("SCALE");

	public static String toGlobalQRCodeJsonString(final DeviceQRCode qrCode)
	{
		return toGlobalQRCode(qrCode).getAsString();
	}

	public static GlobalQRCode toGlobalQRCode(final DeviceQRCode qrCode)
	{
		return JsonConverterV1.toGlobalQRCode(qrCode);
	}

	public static DeviceQRCode fromGlobalQRCodeJsonString(final String qrCodeString)
	{
		return fromGlobalQRCode(GlobalQRCode.ofString(qrCodeString));
	}

	public static DeviceQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		if (!GlobalQRCodeType.equals(GLOBAL_QRCODE_TYPE, globalQRCode.getType()))
		{
			throw new AdempiereException("Invalid QR Code")
					.setParameter("globalQRCode", globalQRCode); // avoid adding it to error message, it might be quite long
		}

		final GlobalQRCodeVersion version = globalQRCode.getVersion();
		if (GlobalQRCodeVersion.equals(globalQRCode.getVersion(), JsonConverterV1.GLOBAL_QRCODE_VERSION))
		{
			return JsonConverterV1.fromGlobalQRCode(globalQRCode);
		}
		else
		{
			throw new AdempiereException("Invalid QR Code version: " + version);
		}
	}

}

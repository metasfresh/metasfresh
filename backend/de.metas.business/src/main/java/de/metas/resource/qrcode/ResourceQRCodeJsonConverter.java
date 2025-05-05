package de.metas.resource.qrcode;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.resource.qrcode.v1.JsonConverterV1;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class ResourceQRCodeJsonConverter
{
	public static GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("RES");

	public static String toGlobalQRCodeJsonString(final ResourceQRCode qrCode)
	{
		return toGlobalQRCode(qrCode).getAsString();
	}

	public static GlobalQRCode toGlobalQRCode(final ResourceQRCode qrCode)
	{
		return JsonConverterV1.toGlobalQRCode(qrCode);
	}

	public static ResourceQRCode fromGlobalQRCodeJsonString(final String qrCodeString)
	{
		return fromGlobalQRCode(GlobalQRCode.ofString(qrCodeString));
	}

	public static ResourceQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		if (!isTypeMatching(globalQRCode))
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

	public static boolean isTypeMatching(final @NonNull GlobalQRCode globalQRCode)
	{
		return GlobalQRCodeType.equals(GLOBAL_QRCODE_TYPE, globalQRCode.getType());
	}

}

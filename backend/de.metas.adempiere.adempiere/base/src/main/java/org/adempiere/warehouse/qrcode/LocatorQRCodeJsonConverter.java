package org.adempiere.warehouse.qrcode;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.qrcode.v1.JsonConverterV1;

@UtilityClass
public class LocatorQRCodeJsonConverter
{
	// note to dev: keep in sync with misc/services/mobile-webui/mobile-webui-frontend/src/utils/qrCode/locator.js
	public static GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("LOC");

	public static String toGlobalQRCodeJsonString(final LocatorQRCode qrCode)
	{
		return toGlobalQRCode(qrCode).getAsString();
	}

	public static GlobalQRCode toGlobalQRCode(final LocatorQRCode qrCode)
	{
		return JsonConverterV1.toGlobalQRCode(qrCode);
	}

	public static LocatorQRCode fromGlobalQRCodeJsonString(final String qrCodeString)
	{
		return fromGlobalQRCode(GlobalQRCode.ofString(qrCodeString));
	}

	public static LocatorQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
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

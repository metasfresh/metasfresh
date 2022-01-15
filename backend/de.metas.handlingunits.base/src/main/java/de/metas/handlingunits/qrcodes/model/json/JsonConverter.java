package de.metas.handlingunits.qrcodes.model.json;

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.json.v1.JsonConverterV1;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class JsonConverter
{
	public static GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("HU");

	public static GlobalQRCode toGlobalQRCode(final HUQRCode qrCode)
	{
		return JsonConverterV1.toGlobalQRCode(qrCode);
	}

	public static HUQRCode fromQRCodeString(final String qrCodeString)
	{
		final GlobalQRCode globalQRCode = GlobalQRCode.ofString(qrCodeString); // make sure it's valid
		if (!GlobalQRCodeType.equals(GLOBAL_QRCODE_TYPE, globalQRCode.getType()))
		{
			throw new AdempiereException("Invalid HU QR Code")
					.setParameter("qrCodeString", qrCodeString); // avoid adding it to error message, it might be quite long
		}

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
}

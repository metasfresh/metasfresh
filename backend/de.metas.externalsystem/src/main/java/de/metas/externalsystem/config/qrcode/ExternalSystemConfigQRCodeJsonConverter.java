/*
 * #%L
 * de.metas.externalsystem
 * %%
 * Copyright (C) 2022 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.externalsystem.config.qrcode;

import de.metas.externalsystem.config.qrcode.v1.JsonConverterV1;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

@UtilityClass
public class ExternalSystemConfigQRCodeJsonConverter
{
	public static GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("EXT-SYS-CALL");

	public static String toGlobalQRCodeJsonString(final ExternalSystemConfigQRCode qrCode)
	{
		return toGlobalQRCode(qrCode).getAsString();
	}

	public static GlobalQRCode toGlobalQRCode(final ExternalSystemConfigQRCode qrCode)
	{
		return JsonConverterV1.toGlobalQRCode(qrCode);
	}

	public static ExternalSystemConfigQRCode fromGlobalQRCodeJsonString(final String qrCodeString)
	{
		return fromGlobalQRCode(GlobalQRCode.ofString(qrCodeString));
	}

	public static ExternalSystemConfigQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
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

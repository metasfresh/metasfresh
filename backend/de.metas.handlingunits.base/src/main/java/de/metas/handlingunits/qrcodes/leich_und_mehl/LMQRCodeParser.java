/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.handlingunits.qrcodes.leich_und_mehl;

import com.google.common.base.Splitter;
import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.global_qrcodes.GlobalQRCodeType;
import de.metas.global_qrcodes.GlobalQRCodeVersion;
import de.metas.util.StringUtils;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Leich und Mehl QR Code parser
 */
// NOTE to dev: keep in sync with huQRCodes.js, parseQRCodePayload_LeichMehl_v1
@UtilityClass
class LMQRCodeParser
{
	private static final GlobalQRCodeType GLOBAL_QRCODE_TYPE = GlobalQRCodeType.ofString("LMQ");
	private static final GlobalQRCodeVersion VERSION_1 = GlobalQRCodeVersion.ofInt(1);

	private static final Splitter SPLITTER = Splitter.on("#");

	private static final DateTimeFormatter BEST_BEFORE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	public static boolean isHandled(@NonNull final GlobalQRCode globalQRCode)
	{
		return GlobalQRCodeType.equals(GLOBAL_QRCODE_TYPE, globalQRCode.getType());
	}

	public static LMQRCode fromGlobalQRCodeJsonString(@NonNull final String qrCodeString)
	{
		return fromGlobalQRCode(GlobalQRCode.ofString(qrCodeString));
	}

	public static LMQRCode fromGlobalQRCode(final GlobalQRCode globalQRCode)
	{
		if (!isHandled(globalQRCode))
		{
			throw new AdempiereException("Invalid Leich und Mehl QR Code")
					.setParameter("globalQRCode", globalQRCode); // avoid adding it to error message, it might be quite long
		}

		final GlobalQRCodeVersion version = globalQRCode.getVersion();
		if (GlobalQRCodeVersion.equals(globalQRCode.getVersion(), VERSION_1))
		{
			return fromGlobalQRCode_version1(globalQRCode);
		}
		else
		{
			throw new AdempiereException("Invalid Leich und Mehl QR Code version: " + version);
		}
	}

	private static LMQRCode fromGlobalQRCode_version1(final GlobalQRCode globalQRCode)
	{
		// NOTE to dev: keep in sync with huQRCodes.js, parseQRCodePayload_LeichMehl_v1
		
		try
		{
			final List<String> parts = SPLITTER.splitToList(globalQRCode.getPayloadAsJson());
			return LMQRCode.builder()
					.weightInKg(new BigDecimal(parts.get(0)))
					.bestBeforeDate(parts.size() >= 2 ? LocalDate.parse(parts.get(1), BEST_BEFORE_DATE_FORMAT) : null)
					.lotNumber(parts.size() >= 3 ? StringUtils.trimBlankToNull(parts.get(2)) : null)
					.productNo(parts.size() >= 4 ? StringUtils.trimBlankToNull(parts.get(3)) : null)
					.build();
		}
		catch (Exception ex)
		{
			throw new AdempiereException("Invalid Leich und Mehl QR Code: " + globalQRCode, ex);

		}
	}
}

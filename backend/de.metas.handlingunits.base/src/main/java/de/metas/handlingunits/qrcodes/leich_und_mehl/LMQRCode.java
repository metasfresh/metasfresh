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

import de.metas.global_qrcodes.GlobalQRCode;
import de.metas.handlingunits.qrcodes.model.IHUQRCode;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

/**
 * Leich und Mehl generated QR Code
 */
@Value
@Builder
@Jacksonized // NOTE: we are making it json friendly mainly for snapshot testing
public class LMQRCode implements IHUQRCode
{
	@NonNull BigDecimal weightInKg;
	@Nullable LocalDate bestBeforeDate;
	@Nullable String lotNumber;
	@Nullable String productNo;

	public static boolean isHandled(@NonNull final GlobalQRCode globalQRCode)
	{
		return LMQRCodeParser.isHandled(globalQRCode);
	}

	@NonNull
	public static LMQRCode fromGlobalQRCodeJsonString(@NonNull final String qrCodeString)
	{
		return LMQRCodeParser.fromGlobalQRCodeJsonString(qrCodeString);
	}

	public static LMQRCode fromGlobalQRCode(@NonNull final GlobalQRCode globalQRCode)
	{
		return LMQRCodeParser.fromGlobalQRCode(globalQRCode);
	}

	@Override
	public Optional<BigDecimal> getWeightInKg() {return Optional.of(weightInKg);}

	@Override
	public Optional<LocalDate> getBestBeforeDate() {return Optional.ofNullable(bestBeforeDate);}

	@Override
	public Optional<String> getLotNumber() {return StringUtils.trimBlankToOptional(lotNumber);}
}

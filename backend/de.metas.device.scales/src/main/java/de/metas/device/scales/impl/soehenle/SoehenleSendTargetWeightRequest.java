/*
 * #%L
 * de.metas.device.scales
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

package de.metas.device.scales.impl.soehenle;

import de.metas.device.api.IDeviceRequest;
import de.metas.device.scales.request.NoDeviceResponse;
import de.metas.util.NumberUtils;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Value
@Builder
public class SoehenleSendTargetWeightRequest implements IDeviceRequest<NoDeviceResponse>
{
	@NonNull
	BigDecimal targetWeight;
	@NonNull
	@Builder.Default
	BigDecimal positiveTolerance = BigDecimal.ZERO;
	@NonNull
	@Builder.Default
	BigDecimal negativeTolerance = BigDecimal.ZERO;

	@Override
	public Class<NoDeviceResponse> getResponseClass()
	{
		return NoDeviceResponse.class;
	}

	@NonNull
	public String getCmd()
	{
		// two digits after the comma is currently required by the scale as the target weight in KGM;
		// TODO: figure it out and create something that works in a more generic way
		return "<K180K"
				+ format(targetWeight, 2) + ";"
				+ format(negativeTolerance, 2) + ";"
				+ format(positiveTolerance, 2) + ">";
	}

	/**
	 * Formats decimal value to fit Soehenle scale specs.
	 */
	@NonNull
	private static String format(@NonNull final BigDecimal value, final int scale)
	{
		final BigDecimal absValue = value.abs();
		final BigDecimal valueToUse = absValue.setScale(scale, RoundingMode.HALF_UP);

		return NumberUtils.toStringWithCustomDecimalSeparator(valueToUse, ',');
	}
}

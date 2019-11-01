/*
 * #%L
 * de.metas.shipper.gateway.dpd
 * %%
 * Copyright (C) 2019 metas GmbH
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

package de.metas.shipper.gateway.dpd.util;

import de.metas.shipper.gateway.spi.model.PackageDimensions;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DpdConversionUtil
{
	/**
	 * dpd wants to store the volume in the format LLLWWWHHH as an Integer.
	 * how am i supposed to store the leading zeroes in an int!!?!?!?!?!?!!?
	 * eg. for
	 * - l = 10 cm
	 * - w = 20 cm
	 * - h = 30 cm
	 * => volume = Integer(magically)(010020030)
	 * wtf dpd?!?
	 * i'm not even shamed of this.
	 */
	public static int formatVolume(@NonNull final PackageDimensions packageDimensions)
	{
		return Integer.parseInt(String.format("%03d%03d%03d", packageDimensions.getLengthInCM(), packageDimensions.getWidthInCM(), packageDimensions.getHeightInCM()));
	}

	/**
	 * Return date as Integer in format `YYYYMMDD`
	 */
	public static int formatDate(@NonNull final LocalDate date)
	{
		return Integer.parseInt(date.format(DateTimeFormatter.BASIC_ISO_DATE));
	}

	/**
	 * Return Day Of Week as Integer
	 * - 0 = Sunday
	 * - 1 = Monday
	 * - etc.
	 */
	public static int getPickupDayOfTheWeek(@NonNull final PickupDate pickupDate)
	{
		final DayOfWeek dayOfWeek = pickupDate.getDate().getDayOfWeek();
		return dayOfWeek.getValue() % 7;
	}

	/**
	 * Return the time as Integer in format `hhmm`
	 */
	public static int formatTime(@NonNull final LocalTime time)
	{
		return Integer.parseInt(time.format(DateTimeFormatter.ofPattern("HHmm")));
	}
}

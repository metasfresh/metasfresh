/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2025 metas GmbH
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

package org.eevolution.productioncandidate.service;

import de.metas.util.Check;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Arrays;

@AllArgsConstructor
public enum ResourcePlanningPrecision
{
	MINUTE("m"),
	QUARTER("15m");

	@Getter
	private final String code;

	@NonNull
	public static ResourcePlanningPrecision ofCodeOrMinute(@Nullable final String code)
	{
		if (Check.isBlank(code))
		{
			return MINUTE;
		}

		return Arrays.stream(values())
				.filter(precision -> precision.getCode().equals(code))
				.findFirst()
				.orElse(MINUTE);
	}
	
	@NonNull
	public Timestamp roundDown(@NonNull final Timestamp initialDate)
	{
		switch (this)
		{
			case QUARTER:
				return TimeUtil.roundDownToNearestQuarter(initialDate);
			case MINUTE:
				return TimeUtil.roundDownToNearestMinute(initialDate);
			default:
				return initialDate;
		}
	}
}

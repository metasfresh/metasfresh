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

import de.metas.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;

import static org.compiere.util.TimeUtil.TRUNC_15M;
import static org.compiere.util.TimeUtil.TRUNC_MINUTE;

@AllArgsConstructor
public enum ResourcePlanningPrecision
{
	MINUTE("M"),
	MINUTE_15("15M");

	@Getter
	private final String code;

	@NonNull
	public static ResourcePlanningPrecision ofCodeOrMinute(@Nullable final String code)
	{
		final String codeNorm = StringUtils.trimBlankToNull(code);
		if (codeNorm == null || codeNorm.equals(MINUTE.getCode()))
		{
			return MINUTE;
		}
		else if (codeNorm.equals(MINUTE_15.getCode()))
		{
			return MINUTE_15;
		}
		else
		{
			throw new AdempiereException("Unknown " + ResourcePlanningPrecision.class + " code: " + codeNorm);
		}
	}

	@NonNull
	public Timestamp roundDown(@NonNull final Timestamp initialDate)
	{
		switch (this)
		{
			case MINUTE_15:
				return TimeUtil.trunc(initialDate, TRUNC_15M);
			case MINUTE:
				return TimeUtil.trunc(initialDate, TRUNC_MINUTE);
			default:
				throw new AdempiereException("Unknown " + ResourcePlanningPrecision.class + ": " + this);
		}
	}
}

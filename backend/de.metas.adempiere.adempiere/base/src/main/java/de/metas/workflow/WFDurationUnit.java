/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.workflow;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import de.metas.util.time.DurationUtils;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_Workflow;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Arrays;

public enum WFDurationUnit implements ReferenceListAwareEnum
{
	Year(X_AD_Workflow.DURATIONUNIT_Year, Duration.ofDays(365), ChronoUnit.YEARS),
	Month(X_AD_Workflow.DURATIONUNIT_Month, Duration.ofDays(30), ChronoUnit.MONTHS),
	Day(X_AD_Workflow.DURATIONUNIT_Day, Duration.ofDays(1), ChronoUnit.DAYS),
	Hour(X_AD_Workflow.DURATIONUNIT_Hour, Duration.ofHours(1), ChronoUnit.HOURS),
	Minute(X_AD_Workflow.DURATIONUNIT_Minute, Duration.ofMinutes(1), ChronoUnit.MINUTES),
	Second(X_AD_Workflow.DURATIONUNIT_Second, Duration.ofSeconds(1), ChronoUnit.SECONDS);

	private static final ReferenceListAwareEnums.ValuesIndex<WFDurationUnit> typesByCode = ReferenceListAwareEnums.index(values());
	private static final ImmutableMap<TemporalUnit, WFDurationUnit> typesByTemporalUnit = Maps.uniqueIndex(Arrays.asList(values()), WFDurationUnit::getTemporalUnit);

	@Getter
	@NonNull private final String code;

	@Getter
	@NonNull private final Duration duration;

	@Getter
	@NonNull private final TemporalUnit temporalUnit;

	WFDurationUnit(
			@NonNull final String code,
			@NonNull final Duration duration,
			@NonNull final TemporalUnit temporalUnit)
	{
		this.code = code;
		this.duration = duration;
		this.temporalUnit = temporalUnit;
	}

	public static WFDurationUnit ofCode(@NonNull final String code)
	{
		return typesByCode.ofCode(code);
	}

	@SuppressWarnings("unused")
	public static WFDurationUnit ofTemporalUnit(@NonNull final TemporalUnit temporalUnit)
	{
		final WFDurationUnit type = typesByTemporalUnit.get(temporalUnit);
		if (type == null)
		{
			throw new AdempiereException("No " + WFDurationUnit.class.getSimpleName() + " found for temporal unit: " + temporalUnit);
		}
		return type;
	}

	public BigDecimal toBigDecimal(@NonNull final Duration duration)
	{
		return DurationUtils.toBigDecimal(duration, getTemporalUnit());
	}

	public int toInt(@NonNull final Duration duration)
	{
		return DurationUtils.toInt(duration, getTemporalUnit());
	}

	public Duration toDuration(@NonNull final BigDecimal durationBD)
	{
		return DurationUtils.toWorkDurationRoundUp(durationBD, getTemporalUnit());
	}

}

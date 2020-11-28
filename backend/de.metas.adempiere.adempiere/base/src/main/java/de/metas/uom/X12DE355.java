package de.metas.uom;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/*
 * #%L
 * de.metas.business
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

/**
 * UOM EDI X12 Code
 */
@EqualsAndHashCode(doNotUseGetters = true)
public class X12DE355
{
	@JsonCreator
	@NonNull
	public static X12DE355 ofCode(@NonNull final String code)
	{
		final X12DE355 x12de355 = cacheByCode.get(code);
		return x12de355 != null ? x12de355 : new X12DE355(code);
	}

	@JsonCreator
	@Nullable
	public static X12DE355 ofNullableCode(@Nullable final String code)
	{
		return !Check.isBlank(code) ? ofCode(code) : null;
	}

	@NonNull
	public static X12DE355 ofTemporalUnit(@NonNull final TemporalUnit temporalUnit)
	{
		final X12DE355 x12de355 = cacheByTemporalUnit.get(temporalUnit);
		if (x12de355 == null)
		{
			throw new AdempiereException("No X12DE355 found for temporal unit `" + temporalUnit + "`");
		}
		return x12de355;
	}

	public static final X12DE355 EACH = new X12DE355("PCE");
	public static final X12DE355 KILOGRAM = new X12DE355("KGM");
	public static final X12DE355 TU = new X12DE355("TU");
	public static final X12DE355 COLI = new X12DE355("COLI");
	public static final X12DE355 CENTIMETRE = new X12DE355("CM");
	public static final X12DE355 DAY = new X12DE355("DA", ChronoUnit.DAYS);
	public static final X12DE355 SECOND = new X12DE355("03", ChronoUnit.SECONDS);
	public static final X12DE355 MONTH = new X12DE355("MO", ChronoUnit.MONTHS);
	public static final X12DE355 MINUTE = new X12DE355("MJ", ChronoUnit.MINUTES);
	public static final X12DE355 HOUR = new X12DE355("HR", ChronoUnit.HOURS);
	public static final X12DE355 DAY_WORK = new X12DE355("WD" /* , ChronoUnit.? */);
	public static final X12DE355 WEEK = new X12DE355("WK", ChronoUnit.WEEKS);
	/**
	 * X12 Element 355 Code Work Month (20 days / 4 weeks)
	 */
	public static final X12DE355 MONTH_WORK = new X12DE355("WM" /* , ChronoUnit.? */);
	public static final X12DE355 YEAR = new X12DE355("YR", ChronoUnit.YEARS);

	private static final ImmutableList<X12DE355> ALL = ImmutableList.of(
			EACH,
			KILOGRAM,
			TU,
			COLI,
			CENTIMETRE,
			DAY,
			SECOND,
			MONTH,
			MINUTE,
			HOUR,
			DAY_WORK,
			WEEK,
			MONTH_WORK,
			YEAR);

	private static final ImmutableMap<String, X12DE355> cacheByCode = ALL.stream()
			.collect(ImmutableMap.toImmutableMap(
					x12de355 -> x12de355.getCode(),
					x12de355 -> x12de355));

	private static final ImmutableMap<TemporalUnit, X12DE355> cacheByTemporalUnit = ALL.stream()
			.filter(X12DE355::isTemporalUnit)
			.collect(ImmutableMap.toImmutableMap(
					x12de355 -> x12de355.getTemporalUnit(),
					x12de355 -> x12de355));

	private final String code;
	private final TemporalUnit temporalUnit;

	private X12DE355(@NonNull final String code)
	{
		this(code, (TemporalUnit)null);
	}

	private X12DE355(
			@NonNull final String code,
			@Nullable final TemporalUnit temporalUnit)
	{
		Check.assumeNotEmpty(code, "code is not empty");
		this.code = code;

		this.temporalUnit = temporalUnit;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getCode();
	}

	@JsonValue
	public String getCode()
	{
		return code;
	}

	public boolean isTemporalUnit()
	{
		return temporalUnit != null;
	}

	@NonNull
	public TemporalUnit getTemporalUnit()
	{
		if (temporalUnit == null)
		{
			throw new AdempiereException("" + this + " is not a known temporal unit");
		}

		return temporalUnit;
	}
}

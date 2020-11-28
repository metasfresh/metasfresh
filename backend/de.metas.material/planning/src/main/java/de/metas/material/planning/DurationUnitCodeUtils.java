package de.metas.material.planning;

import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_Workflow;

import com.google.common.collect.ImmutableBiMap;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * metasfresh-material-planning
 * %%
 * Copyright (C) 2018 metas GmbH
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

@UtilityClass
public class DurationUnitCodeUtils
{
	public static TemporalUnit toTemporalUnit(@NonNull final String durationUnitCode)
	{
		final TemporalUnit durationUnit = temporalUnitsByCode.get(durationUnitCode);
		if (durationUnit == null)
		{
			throw new AdempiereException("Cannot convert durationUnit=" + durationUnitCode + " to " + TemporalUnit.class);
		}
		return durationUnit;
	}

	public static String toDurationUnitCode(@NonNull final TemporalUnit temporalUnit)
	{
		final String durationUnitCode = temporalUnitsByCode.inverse().get(temporalUnit);
		if (durationUnitCode == null)
		{
			throw new AdempiereException("Cannot convert temporalUnit=" + temporalUnit + " to DurationUnit");
		}
		return durationUnitCode;
	}

	private static final ImmutableBiMap<String, TemporalUnit> temporalUnitsByCode = ImmutableBiMap.<String, TemporalUnit> builder()
			.put(X_AD_Workflow.DURATIONUNIT_Second, ChronoUnit.SECONDS)
			.put(X_AD_Workflow.DURATIONUNIT_Minute, ChronoUnit.MINUTES)
			.put(X_AD_Workflow.DURATIONUNIT_Hour, ChronoUnit.HOURS)
			.put(X_AD_Workflow.DURATIONUNIT_Day, ChronoUnit.DAYS)
			// .put(X_AD_Workflow.DURATIONUNIT_Month, ChronoUnit.MONTHS)
			// .put(X_AD_Workflow.DURATIONUNIT_Year, ChronoUnit.YEARS)
			.build();
}

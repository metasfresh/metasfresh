/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

package de.metas.calendar;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import de.metas.util.lang.RepoIdAware;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.apache.commons.lang3.math.NumberUtils;

import javax.annotation.Nullable;
import java.util.function.Function;

@Value
public class CalendarGlobalId
{
	@NonNull CalendarServiceId calendarServiceId;
	@NonNull String localId;

	private CalendarGlobalId(
			@NonNull final CalendarServiceId calendarServiceId,
			@NonNull final String localId)
	{
		this.calendarServiceId = calendarServiceId;
		this.localId = localId;
	}

	public static CalendarGlobalId of(
			@NonNull final CalendarServiceId calendarServiceId,
			@NonNull final String localId)
	{
		return new CalendarGlobalId(calendarServiceId, localId);
	}

	public static CalendarGlobalId of(
			@NonNull final CalendarServiceId calendarServiceId,
			@NonNull final RepoIdAware id)
	{
		return new CalendarGlobalId(calendarServiceId, String.valueOf(id.getRepoId()));
	}

	@JsonCreator
	public static CalendarGlobalId ofString(@NonNull final String string)
	{
		final int idx = string.indexOf("-");
		if (idx <= 0)
		{
			throw new AdempiereException("Invalid calendar ID: " + string);
		}

		final CalendarServiceId calendarServiceId = CalendarServiceId.ofString(string.substring(0, idx));
		final String localId = string.substring(idx + 1);
		return of(calendarServiceId, localId);
	}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return calendarServiceId.getAsString() + "-" + localId;
	}

	public <T> T getAsRepoId(@NonNull final Function<Integer, T> idMapper)
	{
		final int repoIdInt = NumberUtils.toInt(localId);
		return idMapper.apply(repoIdInt);
	}

	public void assertEqualsTo(@Nullable final CalendarGlobalId id)
	{
		Check.assumeEquals(this, id, "calendarId");
	}
}

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
import lombok.NonNull;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;

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
}

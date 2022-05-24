/*
 * #%L
 * de.metas.business
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
import com.google.common.base.Splitter;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode
public class CalendarEntryId
{
	@Getter
	private final CalendarGlobalId calendarId;
	private final String entryLocalId;

	private CalendarEntryId(
			@NonNull final CalendarGlobalId calendarId,
			@NonNull final String entryLocalId)
	{
		this.calendarId = calendarId;
		this.entryLocalId = StringUtils.trimBlankToNull(entryLocalId);
		if (this.entryLocalId == null)
		{
			throw new AdempiereException("entryLocalId shall not be blank");
		}
	}

	@JsonCreator
	public static CalendarEntryId ofString(@NonNull final String string)
	{
		try
		{
			final List<String> parts = Splitter.on("-").splitToList(string);
			final CalendarGlobalId calendarId = CalendarGlobalId.of(
					CalendarServiceId.ofString(parts.get(0)),
					parts.get(1));
			final String entryLocalId = parts.get(2);
			return new CalendarEntryId(calendarId, entryLocalId);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Invalid calendar entry ID: " + string, e);
		}
	}

	@JsonValue
	public String getAsString()
	{
		return calendarId.getCalendarServiceId().getAsString()
				+ "-" + calendarId.getLocalId()
				+ "-" + entryLocalId;
	}

	@Deprecated
	@Override
	public String toString()
	{
		return getAsString();
	}

	public static boolean equals(@Nullable final CalendarEntryId id1, @Nullable final CalendarEntryId id2) {return Objects.equals(id1, id2);}

	public static CalendarEntryId ofRepoId(@NonNull final CalendarGlobalId calendarId, @NonNull final RepoIdAware id)
	{
		return new CalendarEntryId(calendarId, String.valueOf(id.getRepoId()));
	}

	public <T extends RepoIdAware> T toRepoId(@NonNull final Class<T> type)
	{
		return RepoIdAwares.ofObject(entryLocalId, type);
	}

	public CalendarServiceId getCalendarServiceId()
	{
		return getCalendarId().getCalendarServiceId();
	}
}

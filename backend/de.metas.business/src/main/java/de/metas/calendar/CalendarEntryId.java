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
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
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
	@Getter
	private final ImmutableList<String> entryLocalIds;

	private static final String SEPARATOR = "-";
	private static final Splitter STRING_SPLITTER = Splitter.on(SEPARATOR);
	private static final Joiner STRING_JOINER = Joiner.on(SEPARATOR);

	private CalendarEntryId(
			@NonNull final CalendarGlobalId calendarId,
			@NonNull final ImmutableList<String> entryLocalIds)
	{
		if (entryLocalIds.isEmpty())
		{
			throw new AdempiereException("entryLocalIds shall not be empty");
		}

		this.calendarId = calendarId;
		this.entryLocalIds = entryLocalIds;
	}

	@JsonCreator
	public static CalendarEntryId ofString(@NonNull final String string)
	{
		try
		{
			final List<String> parts = STRING_SPLITTER.splitToList(string);
			final CalendarGlobalId calendarId = CalendarGlobalId.of(
					CalendarServiceId.ofString(parts.get(0)),
					parts.get(1));
			final ImmutableList<String> entryLocalIds = ImmutableList.copyOf(parts.subList(2, parts.size()));
			return new CalendarEntryId(calendarId, entryLocalIds);
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
				+ SEPARATOR + calendarId.getLocalId()
				+ SEPARATOR + STRING_JOINER.join(entryLocalIds);
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
		return new CalendarEntryId(calendarId, ImmutableList.of(String.valueOf(id.getRepoId())));
	}

	public <T extends RepoIdAware> T toRepoId(@NonNull final Class<T> type)
	{
		return RepoIdAwares.ofObject(entryLocalIds.get(0), type);
	}

	public static CalendarEntryId ofCalendarAndLocalIds(@NonNull final CalendarGlobalId calendarId, String... entryLocalIds)
	{
		return new CalendarEntryId(calendarId, ImmutableList.copyOf(entryLocalIds));
	}

	public CalendarServiceId getCalendarServiceId()
	{
		return getCalendarId().getCalendarServiceId();
	}
}

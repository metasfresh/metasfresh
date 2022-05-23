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
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;

public class CalendarResourceId
{
	private final String string;

	private static final Interner<CalendarResourceId> interner = Interners.newStrongInterner();

	private CalendarResourceId(@NonNull final String string)
	{
		this.string = StringUtils.trimBlankToNull(string);
		if (this.string == null)
		{
			throw new AdempiereException("calendarResourceId shall not be blank");
		}
	}

	@JsonCreator
	public static CalendarResourceId ofString(@NonNull final String string)
	{
		return interner.intern(new CalendarResourceId(string));
	}

	@JsonValue
	public String getAsString()
	{
		return string;
	}

	@Deprecated
	@Override
	public String toString()
	{
		return getAsString();
	}

	public static boolean equals(@Nullable final CalendarResourceId id1, @Nullable final CalendarResourceId id2) {return Objects.equals(id1, id2);}

	public static CalendarResourceId ofRepoId(@NonNull final RepoIdAware id)
	{
		return ofString(String.valueOf(id.getRepoId()));
	}

	public <T extends RepoIdAware> T toRepoId(@NonNull final Class<T> type)
	{
		return RepoIdAwares.ofObject(string, type);
	}
}

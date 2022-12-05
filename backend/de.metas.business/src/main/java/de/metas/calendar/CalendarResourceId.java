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
import com.google.common.collect.ImmutableSet;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode
public class CalendarResourceId
{
	private final String type;
	private final int repoId;

	private static final Splitter CSV_SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

	private CalendarResourceId(@NonNull final String type, final int repoId)
	{
		final String typeNorm = StringUtils.trimBlankToNull(type);
		if (typeNorm == null)
		{
			throw new AdempiereException("Invalid CalendarResourceId: type shall not be blank");
		}
		if (repoId <= 0)
		{
			throw new AdempiereException("Invalid CalendarResourceId: repoId shall be positive");
		}

		this.type = typeNorm;
		this.repoId = repoId;
	}

	/**
	 * @implNote private method because we want to be used only by jackson deserializer. In case it's needed, feel free to make it public.
	 */
	@JsonCreator
	private static CalendarResourceId ofString(@NonNull final String string)
	{
		try
		{
			final List<String> parts = Splitter.on("-").splitToList(string);
			if (parts.size() != 2)
			{
				throw new AdempiereException("Expected 2 parts only but got " + parts);
			}

			final String type = parts.get(0);
			final int repoId = Integer.parseInt(parts.get(1));
			return new CalendarResourceId(type, repoId);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid calendar resource ID: `" + string + "`", ex);
		}
	}

	public static Optional<ImmutableSet<CalendarResourceId>> ofCommaSeparatedString(@Nullable final String string)
	{
		final String stringNorm = StringUtils.trimBlankToNull(string);
		if (stringNorm == null)
		{
			return Optional.empty();
		}

		return CSV_SPLITTER.splitToStream(stringNorm)
				.map(CalendarResourceId::ofString)
				.collect(GuavaCollectors.toOptionalImmutableSet());
	}

	@JsonValue
	public String getAsString()
	{
		return type + "-" + repoId;
	}

	@Deprecated
	@Override
	public String toString()
	{
		return getAsString();
	}

	public static boolean equals(@Nullable final CalendarResourceId id1, @Nullable final CalendarResourceId id2) {return Objects.equals(id1, id2);}

	public static <T extends RepoIdAware> CalendarResourceId ofRepoId(@NonNull final T id)
	{
		return new CalendarResourceId(extractType(id.getClass()), id.getRepoId());
	}

	@Nullable
	public static <T extends RepoIdAware> CalendarResourceId ofNullableRepoId(@Nullable final T id)
	{
		return id != null ? ofRepoId(id) : null;
	}

	private static <T extends RepoIdAware> String extractType(@NonNull final Class<T> clazz) {return clazz.getSimpleName();}

	public <T extends RepoIdAware> T toRepoId(@NonNull final Class<T> clazz)
	{
		if (!Objects.equals(this.type, extractType(clazz)))
		{
			throw new AdempiereException("Cannot convert " + this + " to " + clazz);
		}

		return RepoIdAwares.ofRepoId(repoId, clazz);
	}

	@Nullable
	public <T extends RepoIdAware> T toRepoIdOrNull(@NonNull final Class<T> clazz)
	{
		if (!Objects.equals(this.type, extractType(clazz)))
		{
			return null;
		}

		return RepoIdAwares.ofRepoId(repoId, clazz);
	}

}

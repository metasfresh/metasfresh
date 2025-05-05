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
import de.metas.resource.ResourceGroupId;
import de.metas.util.GuavaCollectors;
import de.metas.util.StringUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode
public class CalendarResourceId
{
	private final String type;
	@Getter private final String localIdPart;

	private static final Splitter CSV_SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

	private CalendarResourceId(@NonNull final String type, @NonNull final String localIdPart)
	{
		final String typeNorm = StringUtils.trimBlankToNull(type);
		if (typeNorm == null)
		{
			throw new AdempiereException("Invalid CalendarResourceId: type shall not be blank");
		}

		final String localIdPartNorm = StringUtils.trimBlankToNull(localIdPart);
		if (localIdPartNorm == null)
		{
			throw new AdempiereException("Invalid CalendarResourceId: localIdPart shall not be blank");
		}

		this.type = typeNorm;
		this.localIdPart = localIdPartNorm;
	}

	public static CalendarResourceId ofResourceGroupId(@NonNull final ResourceGroupId resourceGroupId)
	{
		return ofRepoId(resourceGroupId);
	}

	private static <T extends RepoIdAware> CalendarResourceId ofRepoId(@NonNull final T id)
	{
		return ofTypeAndLocalIdPart(extractType(id.getClass()), String.valueOf(id.getRepoId()));
	}

	/**
	 * @implNote private method because we want to be used only by jackson deserializer. In case it's needed, feel free to make it public.
	 */
	@JsonCreator
	private static CalendarResourceId ofString(@NonNull final String string)
	{
		try
		{
			final int idx = string.indexOf("-");
			final String type = string.substring(0, idx);
			final String localIdPart = string.substring(idx + 1);
			return ofTypeAndLocalIdPart(type, localIdPart);
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

		return CSV_SPLITTER.splitToList(stringNorm)
				.stream()
				.map(CalendarResourceId::ofString)
				.collect(GuavaCollectors.toOptionalImmutableSet());
	}

	public static CalendarResourceId ofTypeAndLocalIdPart(@NonNull final String type, @NonNull final String localIdPart)
	{
		return new CalendarResourceId(type, localIdPart);
	}

	@JsonValue
	public String getAsString()
	{
		return type + "-" + localIdPart;
	}

	@Deprecated
	@Override
	public String toString()
	{
		return getAsString();
	}

	public static boolean equals(@Nullable final CalendarResourceId id1, @Nullable final CalendarResourceId id2) {return Objects.equals(id1, id2);}

	private static <T extends RepoIdAware> String extractType(@NonNull final Class<T> clazz) {return clazz.getSimpleName();}

	@Nullable
	public ResourceGroupId toResourceGroupIdOrNull() {return toRepoIdOrNull(ResourceGroupId.class);}

	@SuppressWarnings("SameParameterValue")
	private <T extends RepoIdAware> T toRepoId(@NonNull final Class<T> clazz)
	{
		final T id = toRepoIdOrNull(clazz);
		if (id == null)
		{
			throw new AdempiereException("Cannot convert " + this + " to " + clazz);
		}

		return id;
	}

	@Nullable
	private <T extends RepoIdAware> T toRepoIdOrNull(@NonNull final Class<T> clazz)
	{
		if (!isType(extractType(clazz)))
		{
			return null;
		}

		return RepoIdAwares.ofObject(localIdPart, clazz);
	}

	public void assertType(@NonNull final String expectedType)
	{
		if (!isType(expectedType))
		{
			throw new AdempiereException("Expected type `" + expectedType + "` for " + this);
		}
	}

	@SuppressWarnings("BooleanMethodIsAlwaysInverted")
	public boolean isType(@NonNull final String expectedType)
	{
		return Objects.equals(this.type, expectedType);
	}
}

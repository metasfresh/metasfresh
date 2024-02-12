/*
 * #%L
 * de.metas.workflow.rest-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.workflow.rest_api.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import de.metas.util.NumberUtils;
import de.metas.util.lang.RepoIdAware;
import de.metas.util.lang.RepoIdAwares;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.stream.Stream;

@EqualsAndHashCode
public final class WorkflowLauncherId
{
	private static final Joiner JOINER = Joiner.on("$");
	private static final Splitter SPLITTER = Splitter.on("$");

	public static WorkflowLauncherId ofParts(
			@NonNull final MobileApplicationId applicationId,
			final Object... parts)
	{
		if (parts == null || parts.length <= 0)
		{
			throw new AdempiereException("more than one part is required");
		}

		return new WorkflowLauncherId(
				applicationId,
				Stream.of(parts)
						.map(part -> part != null ? part.toString() : "")
						.collect(ImmutableList.toImmutableList()));
	}

	@JsonCreator
	public static WorkflowLauncherId ofString(@NonNull final String stringRepresentation)
	{
		MobileApplicationId applicationId = null;
		final ImmutableList.Builder<String> parts = ImmutableList.builder();

		for (final String part : SPLITTER.split(stringRepresentation))
		{
			if (applicationId == null)
			{
				applicationId = MobileApplicationId.ofString(part);
			}
			else
			{
				parts.add(part);
			}
		}

		if (applicationId == null)
		{
			throw new AdempiereException("Invalid string: " + stringRepresentation);
		}

		final WorkflowLauncherId result = new WorkflowLauncherId(applicationId, parts.build());
		result._stringRepresentation = stringRepresentation;
		return result;
	}

	@Getter
	private final MobileApplicationId applicationId;
	private final ImmutableList<String> parts;
	private String _stringRepresentation;

	private WorkflowLauncherId(
			@NonNull final MobileApplicationId applicationId,
			@NonNull final ImmutableList<String> parts)
	{
		this.applicationId = applicationId;
		this.parts = parts;
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
		String stringRepresentation = _stringRepresentation;
		if (stringRepresentation == null)
		{
			_stringRepresentation = stringRepresentation = JOINER
					.join(Iterables.concat(
							ImmutableList.of(applicationId.getAsString()),
							parts));
		}
		return stringRepresentation;
	}

	public String getPartAsString(final int index)
	{
		return parts.get(index);
	}

	public Integer getPartAsInt(final int index)
	{
		return NumberUtils.asIntegerOrNull(getPartAsString(index));
	}
}

/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.picking.workflow.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.UUID;

@EqualsAndHashCode
public class PickingJobStepId
{
	@JsonCreator
	public static PickingJobStepId ofString(@NonNull final String value)
	{
		return new PickingJobStepId(value);
	}

	public static PickingJobStepId random()
	{
		return new PickingJobStepId(UUID.randomUUID().toString());
	}

	private final String value;

	private PickingJobStepId(@NonNull final String value) {this.value = Check.assumeNotEmpty(value, "value");}

	@Override
	@Deprecated
	public String toString()
	{
		return getAsString();
	}

	@JsonValue
	public String getAsString()
	{
		return value;
	}

	public static boolean equals(@Nullable final PickingJobStepId o1, @Nullable final PickingJobStepId o2)
	{
		return Objects.equals(o1, o2);
	}
}

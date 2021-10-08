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
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.UUID;

@EqualsAndHashCode
public final class WFProcessId
{
	@JsonCreator
	public static WFProcessId ofString(@NonNull final String string)
	{
		final int idx = string.indexOf(SEPARATOR);
		if (idx <= 0)
		{
			throw new AdempiereException("Invalid format: " + string);
		}

		final WFProcessHandlerId handlerId = WFProcessHandlerId.ofString(string.substring(0, idx));
		final String idPart = string.substring(idx + 1);
		if (idPart.isEmpty())
		{
			throw new AdempiereException("Invalid format: " + string);
		}

		return new WFProcessId(handlerId, idPart);
	}

	public static WFProcessId random(@NonNull final WFProcessHandlerId handlerId)
	{
		return new WFProcessId(handlerId, UUID.randomUUID().toString());
	}

	private static final String SEPARATOR = "-";

	@Getter
	private final WFProcessHandlerId handlerId;
	private final String idPart;
	private transient String stringRepresentation = null;

	private WFProcessId(
			@NonNull final WFProcessHandlerId handlerId,
			@NonNull final String idPart)
	{
		this.handlerId = handlerId;
		this.idPart = Check.assumeNotEmpty(idPart, "idPart");
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
		String stringRepresentation = this.stringRepresentation;
		if (stringRepresentation == null)
		{
			stringRepresentation = this.stringRepresentation = handlerId.getAsString() + SEPARATOR + idPart;
		}
		return stringRepresentation;
	}

	@Nullable
	public static String getAsStringOrNull(@Nullable final WFProcessId id)
	{
		return id != null ? id.getAsString() : null;
	}
}

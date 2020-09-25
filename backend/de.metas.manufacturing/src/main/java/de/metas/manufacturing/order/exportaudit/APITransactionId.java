package de.metas.manufacturing.order.exportaudit;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

@EqualsAndHashCode
public final class APITransactionId
{
	public static APITransactionId random()
	{
		return new APITransactionId(UUID.randomUUID().toString());
	}

	@JsonCreator
	public static APITransactionId ofString(@NonNull final String value)
	{
		return new APITransactionId(value);
	}

	public static Optional<APITransactionId> optionalOfString(@Nullable final String value)
	{
		return Check.isNotBlank(value) ? Optional.of(ofString(value)) : Optional.empty();
	}

	private final String value;

	private APITransactionId(@NonNull final String value)
	{
		Check.assumeNotEmpty(value, "value is not empty");
		this.value = value;
	}

	@Override
	@Deprecated
	public String toString()
	{
		return toJson();
	}

	@JsonValue
	public String toJson()
	{
		return value;
	}
}

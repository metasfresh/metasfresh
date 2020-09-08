package de.metas.manufacturing.order.exportaudit;

import java.util.UUID;

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
public final class ExportTransactionId
{
	public static ExportTransactionId random()
	{
		return new ExportTransactionId(UUID.randomUUID().toString());
	}

	@JsonCreator
	public static ExportTransactionId ofString(@NonNull final String value)
	{
		return new ExportTransactionId(value);
	}

	private final String value;

	private ExportTransactionId(@NonNull final String value)
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

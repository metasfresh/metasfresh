package de.metas.vertical.pharma.msv3.protocol.types;

import java.util.UUID;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
public class Id
{
	public static Id of(final String valueAsString)
	{
		return new Id(valueAsString);
	}

	public static Id random()
	{
		return new Id(UUID.randomUUID().toString());
	}

	private final String valueAsString;

	private Id(@NonNull final String valueAsString)
	{
		if (valueAsString.isEmpty())
		{
			throw new IllegalArgumentException("value shall not be empty");
		}

		this.valueAsString = valueAsString;
	}

	/**
	 * @deprecated please use {@link #getValueAsString()}
	 */
	@Override
	@Deprecated
	public String toString()
	{
		return getValueAsString();
	}
}

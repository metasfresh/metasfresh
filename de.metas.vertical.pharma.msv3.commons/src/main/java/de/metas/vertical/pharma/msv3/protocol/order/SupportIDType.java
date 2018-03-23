package de.metas.vertical.pharma.msv3.protocol.order;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

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

/**
 * Exclusive identification for people! Support (optional display on GUI no proof) - no technical semantics Apo-Wawi awards this; should be sufficiently selective.
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class SupportIDType
{
	@JsonCreator
	public static SupportIDType of(final int valueAsInt)
	{
		return new SupportIDType(valueAsInt);
	}

	private final int valueAsInt;

	private SupportIDType(final int valueAsInt)
	{
		if (valueAsInt < 1 || valueAsInt > 999999)
		{
			throw new IllegalArgumentException("value shall be between 1 and 999999");
		}

		this.valueAsInt = valueAsInt;
	}

	@JsonValue
	public int toJson()
	{
		return valueAsInt;
	}
}

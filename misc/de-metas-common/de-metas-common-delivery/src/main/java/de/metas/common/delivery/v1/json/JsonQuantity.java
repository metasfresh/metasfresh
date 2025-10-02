/*
 * #%L
 * de-metas-common-delivery
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.common.delivery.v1.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Splitter;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Value
@Builder
public class JsonQuantity
{
	@NonNull BigDecimal value;
	@NonNull String uomCode; // ISO or system UOM code, e.g. "PCE"

	@NonNull
	@JsonCreator
	public static JsonQuantity parseString(@NonNull final String string)
	{
		final List<String> parts = Splitter.on(" ").trimResults().omitEmptyStrings().splitToList(string);
		if (parts.size() != 2)
		{
			throw new RuntimeException("Cannot parse " + string + " to QtyAndUOMSymbol: expected 2 parts separated by space");
		}
		try
		{
			return JsonQuantity.builder()
					.value(new BigDecimal(parts.get(0)))
					.uomCode(parts.get(1))
					.build();
		}
		catch (final Exception ex)
		{
			throw new RuntimeException("Cannot parse " + string + " to QtyAndUOMSymbol", ex);
		}
	}

	@Override
	public String toString() {return toJson();}

	@JsonValue
	public String toJson() {return value + " " + uomCode;}
}

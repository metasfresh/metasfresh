package de.metas.vertical.pharma.msv3.server.stockAvailability.sync;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class JsonStockAvailability
{
	@JsonProperty("pzn")
	private long pzn;
	@JsonProperty("qty")
	private int qty;

	@JsonCreator
	@Builder
	private JsonStockAvailability(
			@JsonProperty("pzn") final long pzn,
			@JsonProperty("qty") final int qty)
	{
		if (pzn <= 0)
		{
			throw new IllegalArgumentException("pzn shall be > 0");
		}
		if (qty < 0)
		{
			throw new IllegalArgumentException("qty shall be >= 0");
		}

		this.pzn = pzn;
		this.qty = qty;
	}

}

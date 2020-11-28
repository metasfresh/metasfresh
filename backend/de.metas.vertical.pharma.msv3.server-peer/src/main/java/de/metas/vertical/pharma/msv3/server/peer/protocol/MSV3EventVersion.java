package de.metas.vertical.pharma.msv3.server.peer.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer
 * %%
 * Copyright (C) 2019 metas GmbH
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
 * Currently used in {@link MSV3StockAvailabilityUpdatedEvent}. Might likewise be used in {@link MSV3UserChangedEvent}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Value
public class MSV3EventVersion
{
	public static MSV3EventVersion of(final int asInt)
	{
		return new MSV3EventVersion(asInt);
	}

	int asInt;

	@JsonCreator
	private MSV3EventVersion(@JsonProperty("asInt") final int asInt)
	{
		if (asInt <= 0)
		{
			throw new IllegalArgumentException("asInt shall be > 0");
		}
		this.asInt = asInt;
	}
}

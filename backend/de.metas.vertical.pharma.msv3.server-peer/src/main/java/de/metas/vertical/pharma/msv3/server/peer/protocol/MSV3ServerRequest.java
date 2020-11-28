package de.metas.vertical.pharma.msv3.server.peer.protocol;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * metasfresh-pharma.msv3.server-peer
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
@Builder
public class MSV3ServerRequest
{
	/** If you have 600K items on the metasfresh server, this request should be used with caution. */
	public static MSV3ServerRequest requestAll()
	{
		return ALL;
	}

	public static MSV3ServerRequest requestConfig()
	{
		return CONFIG;
	}

	private static final MSV3ServerRequest ALL = MSV3ServerRequest.builder()
			.requestAllUsers(true)
			.requestAllStockAvailabilities(true)
			.build();

	private static final MSV3ServerRequest CONFIG = MSV3ServerRequest.builder()
			.requestAllUsers(true)
			.requestAllStockAvailabilities(false)
			.build();

	boolean requestAllUsers;
	boolean requestAllStockAvailabilities;

	@JsonCreator
	private MSV3ServerRequest(
			@JsonProperty("requestAllUsers") final boolean requestAllUsers,
			@JsonProperty("requestAllStockAvailabilities") final boolean requestAllStockAvailabilities)
	{
		this.requestAllUsers = requestAllUsers;
		this.requestAllStockAvailabilities = requestAllStockAvailabilities;
	}

}

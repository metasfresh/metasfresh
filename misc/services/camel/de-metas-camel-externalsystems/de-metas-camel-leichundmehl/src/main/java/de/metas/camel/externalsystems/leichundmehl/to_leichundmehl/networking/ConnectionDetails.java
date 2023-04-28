/*
 * #%L
 * de-metas-camel-leichundmehl
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.camel.externalsystems.leichundmehl.to_leichundmehl.networking;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

@Value
public class ConnectionDetails
{
	@NonNull
	@JsonProperty("tcpPort")
	int tcpPort;

	@NonNull
	@JsonProperty("tcpHost")
	String tcpHost;

	@Builder
	public ConnectionDetails(
			@JsonProperty("tcpPort") @NonNull final int tcpPort,
			@JsonProperty("tcpHost") @NonNull final String tcpHost)
	{
		this.tcpPort = tcpPort;
		this.tcpHost = tcpHost;
	}
}

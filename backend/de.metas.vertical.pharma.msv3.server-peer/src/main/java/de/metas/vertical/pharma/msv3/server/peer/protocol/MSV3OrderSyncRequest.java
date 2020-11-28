package de.metas.vertical.pharma.msv3.server.peer.protocol;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableList;

import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.Id;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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

@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
@Value
public class MSV3OrderSyncRequest
{
	@JsonProperty("orderId")
	Id orderId;
	@JsonProperty("bpartner")
	BPartnerId bpartner;

	@JsonProperty("orderPackages")
	List<MSV3OrderSyncRequestPackage> orderPackages;

	@Builder
	@JsonCreator
	private MSV3OrderSyncRequest(
			@JsonProperty("orderId") @NonNull final Id orderId,
			@JsonProperty("bpartner") @NonNull final BPartnerId bpartner,
			@JsonProperty("orderPackages") @NonNull @Singular final List<MSV3OrderSyncRequestPackage> orderPackages)
	{
		this.orderId = orderId;
		this.bpartner = bpartner;
		this.orderPackages = ImmutableList.copyOf(orderPackages);
	}

}

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
public class MSV3OrderSyncResponse
{
	public static MSV3OrderSyncResponseBuilder ok(@NonNull final Id orderId, @NonNull final BPartnerId bpartnerId)
	{
		return _builder()
				.orderId(orderId)
				.bpartnerId(bpartnerId);
	}

	public static MSV3OrderSyncResponse error(@NonNull final Id orderId, @NonNull final BPartnerId bpartnerId, @NonNull final Throwable ex)
	{
		String errorMsg = ex.getLocalizedMessage();
		if (errorMsg == null || errorMsg.trim().length() < 5)
		{
			errorMsg = ex.toString();
		}

		return _builder()
				.orderId(orderId)
				.bpartnerId(bpartnerId)
				.errorMsg(errorMsg)
				.build();
	}

	@JsonProperty("bpartnerId")
	BPartnerId bpartnerId;

	@JsonProperty("orderId")
	Id orderId;

	@JsonProperty("errorMsg")
	String errorMsg;

	@JsonProperty("items")
	List<MSV3OrderSyncResponseItem> items;

	@Builder(builderMethodName = "_builder")
	@JsonCreator
	private MSV3OrderSyncResponse(
			@JsonProperty("orderId") @NonNull final Id orderId,
			@JsonProperty("bpartnerId") @NonNull final BPartnerId bpartnerId,
			@JsonProperty("errorMsg") final String errorMsg,
			@JsonProperty("items") @Singular final List<MSV3OrderSyncResponseItem> items)
	{
		this.bpartnerId = bpartnerId;
		this.orderId = orderId;
		this.errorMsg = errorMsg;
		this.items = items != null ? ImmutableList.copyOf(items) : ImmutableList.of();
	}

	public boolean isError()
	{
		return errorMsg != null;
	}

}

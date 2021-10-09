/*
 * #%L
 * de-metas-common-procurement
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

package de.metas.common.procurement.sync.protocol.request_to_metasfresh;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.procurement.sync.protocol.RequestToMetasfresh;
import de.metas.common.procurement.sync.protocol.dto.SyncProductSupply;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.Collection;
import java.util.List;

@Value
public class PutProductSuppliesRequest extends RequestToMetasfresh
{
	public static PutProductSuppliesRequest of(@NonNull final SyncProductSupply syncProductSupply)
	{
		return PutProductSuppliesRequest.builder()
				.productSupply(syncProductSupply)
				.build();
	}

	public static PutProductSuppliesRequest of(final Collection<SyncProductSupply> syncProductSupplies)
	{
		final PutProductSuppliesRequestBuilder requestBuilder = PutProductSuppliesRequest.builder();
		if (syncProductSupplies != null)
		{
			requestBuilder.productSupplies(syncProductSupplies);
		}
		return requestBuilder.build();
	}

	List<SyncProductSupply> productSupplies;

	@Builder
	@JsonCreator
	public PutProductSuppliesRequest(@JsonProperty("productSupplies") @Singular final List<SyncProductSupply> productSupplies)
	{
		this.productSupplies = productSupplies;
	}
}

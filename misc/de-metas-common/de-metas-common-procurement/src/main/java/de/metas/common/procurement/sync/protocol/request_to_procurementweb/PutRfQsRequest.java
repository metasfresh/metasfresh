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

package de.metas.common.procurement.sync.protocol.request_to_procurementweb;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.common.procurement.sync.protocol.RequestToProcurementWeb;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQ;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
public class PutRfQsRequest extends RequestToProcurementWeb
{
	public static PutRfQsRequest of(@NonNull final List<SyncRfQ> syncRfqs)
	{
		return PutRfQsRequest.builder().syncRfqs(syncRfqs).build();
	}

	List<SyncRfQ> syncRfqs;

	@Builder
	@JsonCreator
	public PutRfQsRequest(@JsonProperty("syncRfqs") @Singular @NonNull final List<SyncRfQ> syncRfqs)
	{
		this.syncRfqs = syncRfqs;
	}

	@JsonIgnore
	public boolean isEmpty()
	{
		return syncRfqs.isEmpty();
	}
}

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
import de.metas.common.procurement.sync.protocol.RequestToMetasfresh;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQPriceChangeEvent;
import de.metas.common.procurement.sync.protocol.dto.SyncRfQQtyChangeEvent;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
public class PutRfQChangeRequest extends RequestToMetasfresh
{
	List<SyncRfQPriceChangeEvent> priceChangeEvents;
	List<SyncRfQQtyChangeEvent> qtyChangeEvents;

	@Builder
	@JsonCreator
	private PutRfQChangeRequest(
			@JsonProperty("priceChangeEvents") @Singular final List<SyncRfQPriceChangeEvent> priceChangeEvents,
			@JsonProperty("qtyChangeEvents") @Singular final List<SyncRfQQtyChangeEvent> qtyChangeEvents)
	{
		this.priceChangeEvents = priceChangeEvents;
		this.qtyChangeEvents = qtyChangeEvents;
	}

	@JsonIgnore
	public static boolean isEmpty(final PutRfQChangeRequest request)
	{
		if (request == null)
		{
			return true;
		}
		return request.priceChangeEvents.isEmpty()
				&& request.qtyChangeEvents.isEmpty();
	}
}

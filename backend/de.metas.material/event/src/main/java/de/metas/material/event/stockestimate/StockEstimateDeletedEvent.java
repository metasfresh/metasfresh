package de.metas.material.event.stockestimate;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.Instant;

/*
 * #%L
 * metasfresh-material-event
 * %%
 * Copyright (C) 2017 metas GmbH
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

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@JsonAutoDetect(fieldVisibility = Visibility.ANY, getterVisibility = Visibility.NONE, isGetterVisibility = Visibility.NONE, setterVisibility = Visibility.NONE)
public class StockEstimateDeletedEvent extends AbstractStockEstimateEvent
{
	public static StockEstimateCreatedEvent cast(@NonNull final AbstractStockEstimateEvent event)
	{
		return (StockEstimateCreatedEvent)event;
	}

	public static final String TYPE = "StockEstimateDeletedEvent";

	@JsonCreator
	@Builder
	public StockEstimateDeletedEvent(
			@NonNull @JsonProperty("eventDescriptor") final EventDescriptor eventDescriptor,
			@NonNull @JsonProperty("materialDescriptor") final MaterialDescriptor materialDescriptor,
			@NonNull @JsonProperty("date") final Instant date,
			@JsonProperty("plantId") final int plantId,
			@JsonProperty("freshQtyOnHandId") final int freshQtyOnHandId,
			@JsonProperty("freshQtyOnHandLineId") final int freshQtyOnHandLineId,
			@NonNull @JsonProperty("eventDate") final Instant eventDate
	)
	{
		super(eventDescriptor,
			  materialDescriptor,
			  date,
			  plantId,
			  freshQtyOnHandId,
			  freshQtyOnHandLineId,
			  null /*qtyStockEstimateSeqNo*/,
			  eventDate);
	}

	@Override
	public BigDecimal getQuantityDelta()
	{
		return getMaterialDescriptor().getQuantity().negate();
	}
}
